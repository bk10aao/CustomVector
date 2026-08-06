import os
import json
from collections import defaultdict

import pandas as pd
import numpy as np
import matplotlib.pyplot as plt
import seaborn as sns

# ---------------------------------------------------------------------------
# Paths
# ---------------------------------------------------------------------------
script_dir = os.path.dirname(os.path.abspath(__file__))
json_path = os.path.join(script_dir, "results_old.json")

# ---------------------------------------------------------------------------
# Load JMH results_old.json → Custom / JDK score maps
# ---------------------------------------------------------------------------
with open(json_path, "r") as f:
    results = json.load(f)

custom_scores = defaultdict(dict)  # method -> {size -> score}
jdk_scores = defaultdict(dict)

for entry in results:
    full_name = entry["benchmark"]
    method = full_name.split(".")[-1]
    size = int(entry["params"]["size"])
    score = entry["primaryMetric"]["score"]

    if "CustomVector" in full_name:
        custom_scores[method][size] = score
    elif "VectorBenchMark" in full_name or "VectorBenchmark" in full_name:
        jdk_scores[method][size] = score

# Common methods & sizes
methods = sorted(set(custom_scores) & set(jdk_scores))
sizes = sorted(
    {s for m in methods for s in custom_scores[m]}
    | {s for m in methods for s in jdk_scores[m]}
)

# ---------------------------------------------------------------------------
# Build ratio matrix (log2(Custom / JDK))
# positive → JDK faster, negative → Custom faster
# ---------------------------------------------------------------------------
heatmap_data = np.zeros((len(methods), len(sizes)))
text_labels = []

for i, m in enumerate(methods):
    row_labels = []
    for j, size in enumerate(sizes):
        custom_val = custom_scores[m].get(size, 1) or 1
        native_val = jdk_scores[m].get(size, 1) or 1

        ratio = np.log2(custom_val / native_val)
        heatmap_data[i, j] = ratio

        if native_val <= custom_val:
            # JDK faster (or equal)
            factor = custom_val / native_val
            if factor >= 100:
                row_labels.append(f"+{factor:.0f}x")
            else:
                row_labels.append(f"+{factor:.1f}x")
        else:
            # Custom faster
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

# ---------------------------------------------------------------------------
# Plot
# ---------------------------------------------------------------------------
fig, ax = plt.subplots(figsize=(16, 14), facecolor="none")
ax.set_facecolor("none")

# Clip at ±4.0 (16×) so extreme outliers don't wash out the scale
clipped_data = np.clip(heatmap_data, -4.0, 4.0)

# 240 (blue) for low/negative (Custom faster), 15 (red) for high/positive (JDK faster)
cmap = sns.diverging_palette(240, 15, as_cmap=True)

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
        "label": "← CustomVector Faster | Relative Speedup Scale (Clipped at 16x) | JDK Vector Faster →"
    },
    linewidths=0.6,
    linecolor="#444444",
    annot_kws={"size": 9, "weight": "bold"},
)

ax.set_title(
    "CustomVector vs JDK Vector Performance Comparison Matrix Heatmap\n"
    "(Positive/Red = JDK Vector Faster, Negative/Blue = CustomVector Faster)",
    color="#ffffff",
    fontsize=16,
    fontweight="bold",
    pad=20,
)

ax.set_ylabel("Vector Interface Methods", color="#aaaaaa", fontsize=13, labelpad=10)
ax.set_xlabel("Collection Size (Elements)", color="#aaaaaa", fontsize=13, labelpad=10)

ax.tick_params(colors="#ffffff", labelsize=11)
plt.xticks(rotation=45)
plt.yticks(rotation=0)

cbar = ax.collections[0].colorbar
cbar.ax.tick_params(colors="#ffffff", labelsize=10)
cbar.ax.yaxis.label.set_color("#ffffff")
cbar.ax.yaxis.label.set_fontsize(12)

plt.tight_layout()
output_path = os.path.join(script_dir, "heatmap.png")
plt.savefig(output_path, dpi=300, transparent=True)
plt.close()

print("CustomVector vs JDK Vector performance heatmap generated successfully!")
print("Min log2 ratio:", np.min(heatmap_data))
print("Max log2 ratio:", np.max(heatmap_data))
print("Sorted methods:", sorted_methods)