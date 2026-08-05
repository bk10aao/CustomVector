import pandas as pd
import numpy as np
import matplotlib.pyplot as plt
import seaborn as sns

# Load data files (updated for CustomVector vs JDKVector)
custom_df = pd.read_csv('CustomVector_performance_data.csv', sep=',')
native_df = pd.read_csv('JDKVector_performance_data.csv', sep=',')

# Clean columns
custom_df.columns = [c.replace('"', '').strip() for c in custom_df.columns]
native_df.columns = [c.replace('"', '').strip() for c in native_df.columns]

sizes = custom_df['Size'].tolist()
methods = [c for c in custom_df.columns if c != 'Size']

heatmap_data = np.zeros((len(methods), len(sizes)))
text_labels = []

for i, m in enumerate(methods):
    row_labels = []
    for j, size in enumerate(sizes):
        custom_val = custom_df.loc[custom_df['Size'] == size, m].values[0]
        native_val = native_df.loc[native_df['Size'] == size, m].values[0]

        if custom_val == 0:
            custom_val = 1
        if native_val == 0:
            native_val = 1

        # Corrected ratio: positive means JDK Vector (native_val) is faster (lower time) than CustomVector
        ratio = np.log2(custom_val / native_val)
        heatmap_data[i, j] = ratio

        if native_val <= custom_val:
            # JDK Vector is faster or equal
            factor = custom_val / native_val
            if factor >= 100:
                row_labels.append(f"+{factor:.0f}x")
            else:
                row_labels.append(f"+{factor:.1f}x")
        else:
            # CustomVector is faster
            factor = native_val / custom_val
            if factor >= 100:
                row_labels.append(f"-{factor:.0f}x")
            else:
                row_labels.append(f"-{factor:.1f}x")
    text_labels.append(row_labels)

text_labels = np.array(text_labels)

# Sort methods by average performance ratio
avg_ratios = np.mean(heatmap_data, axis=1)
sorted_idx = np.argsort(avg_ratios)
heatmap_data = heatmap_data[sorted_idx]
text_labels = text_labels[sorted_idx]
sorted_methods = [methods[idx] for idx in sorted_idx]

# Plotting the heatmap
fig, ax = plt.subplots(figsize=(16, 14), facecolor='none')
ax.set_facecolor('none')

# Clip data at [-4.0, 4.0] (up to 16x) to preserve visualization detail
clipped_data = np.clip(heatmap_data, -4.0, 4.0)

cmap = sns.diverging_palette(15, 240, as_cmap=True)

sns.heatmap(
    clipped_data,
    annot=text_labels,
    fmt="",
    cmap=cmap,
    center=0,
    xticklabels=sizes,
    yticklabels=sorted_methods,
    ax=ax,
    cbar_kws={
        'label': '← CustomVector Faster | Relative Speedup Scale (Clipped at 16x) | JDK Vector Faster →'
    },
    linewidths=0.6,
    linecolor='#444444',
    annot_kws={'size': 9, 'weight': 'bold'}
)

ax.set_title(
    'CustomVector vs JDK Vector Performance Comparison Matrix Heatmap\n'
    '(Positive/Blue = JDK Vector Faster, Negative/Red = CustomVector Faster)',
    color='#ffffff', fontsize=16, fontweight='bold', pad=20
)

ax.set_ylabel('Vector Interface Methods', color='#aaaaaa', fontsize=13, labelpad=10)
ax.set_xlabel('Collection Size (Elements)', color='#aaaaaa', fontsize=13, labelpad=10)

ax.tick_params(colors='#ffffff', labelsize=11)
plt.xticks(rotation=45)
plt.yticks(rotation=0)

cbar = ax.collections[0].colorbar
cbar.ax.tick_params(colors='#ffffff', labelsize=10)
cbar.ax.yaxis.label.set_color('#ffffff')
cbar.ax.yaxis.label.set_fontsize(12)

plt.tight_layout()
plt.savefig('vector_heatmap.png', dpi=300, transparent=True)
plt.close()

print("CustomVector vs JDK Vector performance heatmap generated successfully!")
print("Min log2 ratio:", np.min(heatmap_data))
print("Max log2 ratio:", np.max(heatmap_data))
print("Sorted methods:", sorted_methods)