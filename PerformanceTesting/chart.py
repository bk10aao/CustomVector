import os
import json
from collections import defaultdict

import matplotlib.pyplot as plt
from matplotlib.lines import Line2D
import pandas as pd

# ---------------------------------------------------------------------------
# Paths
# ---------------------------------------------------------------------------
script_dir = os.path.dirname(os.path.abspath(__file__))
json_path = os.path.join(script_dir, "results.json")

# ---------------------------------------------------------------------------
# Load JMH results and build two DataFrames (Custom / JDK)
# ---------------------------------------------------------------------------
with open(json_path, "r") as f:
    results = json.load(f)

# method_name -> {size -> score}
custom_scores = defaultdict(dict)
jdk_scores = defaultdict(dict)

for entry in results:
    full_name = entry["benchmark"]          # e.g. vector.CustomVectorBenchmark.benchmarkAdd
    method = full_name.split(".")[-1]       # e.g. benchmarkAdd
    size = int(entry["params"]["size"])
    score = entry["primaryMetric"]["score"]

    if "CustomVector" in full_name:
        custom_scores[method][size] = score
    elif "VectorBenchMark" in full_name or "VectorBenchmark" in full_name:
        jdk_scores[method][size] = score

# Common sizes (sorted)
all_sizes = sorted(
    {s for scores in custom_scores.values() for s in scores}
    | {s for scores in jdk_scores.values() for s in scores}
)

# Build DataFrames with columns = method names, rows = sizes
custom_df = pd.DataFrame(index=all_sizes)
jdk_df = pd.DataFrame(index=all_sizes)

for method in sorted(set(custom_scores) | set(jdk_scores)):
    custom_df[method] = [custom_scores[method].get(s) for s in all_sizes]
    jdk_df[method] = [jdk_scores[method].get(s) for s in all_sizes]

custom_df.index.name = "Size"
jdk_df.index.name = "Size"
custom_df = custom_df.reset_index()
jdk_df = jdk_df.reset_index()

# Methods that have valid numeric data in both series
valid_cols = [
    col
    for col in custom_df.columns
    if col != "Size"
    and pd.notna(custom_df[col].mean())
    and pd.notna(jdk_df[col].mean())
]

def display_title(method: str) -> str:
    """Strip leading 'benchmark' and lowercase the first remaining letter."""
    if method.startswith("benchmark"):
        method = method[len("benchmark"):]
    if method:
        method = method[0].lower() + method[1:]
    return method

# ---------------------------------------------------------------------------
# Plotting
# ---------------------------------------------------------------------------
color_custom = "#ff4d4d"  # Red for CustomVector
color_native = "#4da6ff"  # Blue for JDK Vector

for method in valid_cols:
    fig, ax = plt.subplots(figsize=(8, 5.5))

    ax.plot(
        custom_df["Size"],
        custom_df[method],
        color=color_custom,
        marker="o",
        markersize=5,
        linestyle="-",
        linewidth=2,
    )
    ax.plot(
        jdk_df["Size"],
        jdk_df[method],
        color=color_native,
        marker="o",
        markersize=5,
        linestyle="-",
        linewidth=2,
    )

    ax.set_xlim(left=5000, right=50000)

    title = display_title(method)

    # Dark-mode friendly styling
    ax.set_title(title, fontsize=14, fontweight="bold", color="white", pad=15)
    ax.set_xlabel("Size", fontsize=11, color="white")
    ax.set_ylabel("Time (ns)", fontsize=11, color="white")
    ax.tick_params(axis="both", colors="white")
    ax.grid(True, linestyle="--", alpha=0.3, color="white")
    for spine in ax.spines.values():
        spine.set_color("white")

    legend_elements = [
        Line2D(
            [0],
            [0],
            marker="o",
            color="none",
            label="Custom",
            markerfacecolor=color_custom,
            markeredgecolor=color_custom,
            markersize=8,
            linestyle="None",
        ),
        Line2D(
            [0],
            [0],
            marker="o",
            color="none",
            label="JDK",
            markerfacecolor=color_native,
            markeredgecolor=color_native,
            markersize=8,
            linestyle="None",
        ),
    ]
    legend = ax.legend(
        handles=legend_elements,
        loc="upper center",
        bbox_to_anchor=(0.5, -0.15),
        fontsize=10,
        frameon=False,
        ncol=2,
    )
    for text in legend.get_texts():
        text.set_color("white")

    fig.patch.set_alpha(0.0)
    ax.patch.set_alpha(0.0)

    plt.tight_layout()

    safe_filename = (
        method.replace("(", "_")
        .replace(")", "_")
        .replace(",", "_")
        .replace(".", "_")
    )
    output_image_path = os.path.join(script_dir, f"plot_{safe_filename}.png")
    plt.savefig(output_image_path, transparent=True, bbox_inches="tight")
    plt.close()

print(
    f"Successfully generated {len(valid_cols)} performance graphs in {script_dir}"
)