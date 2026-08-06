# Custom Vector

An implementation of a Vector.

All methods implemented are identical to those found in the Java List interface.

Build and Test
To build and test the project run command `./gradlew clean build`

To test the project run command `./gradlew test`

To run performance benchmarking `./gradlew jmh`

# Performance Charts

Performance comparison evaluated at 100,000 operations per method (averages derived from 10,000 benchmark runs).

# Time Complexity

| Method                        |     Custom      |       JDK       | Winner |
|:------------------------------|:---------------:|:---------------:|:------:|
| **`add(E)`**                  |     $O(1)$      |     $O(1)$      |  Tie   |
| **`add(int, E)`**             |     $O(N)$      |     $O(N)$      |  Tie   |
| **`addAll(Collection)`**      |     $O(M)$      |     $O(M)$      |  Tie   |
| **`addAll(int, Collection)`** |   $O(N + M)$    |   $O(N + M)$    |  Tie   |
| **`clear()`**                 |     $O(N)$      |     $O(N)$      |  Tie   |
| **`clone()`**                 |     $O(N)$      |     $O(N)$      |  Tie   |
| **`contains(Object)`**        |     $O(N)$      |     $O(N)$      |  Tie   |
| **`containsAll(Collection)`** | $O(N \times M)$ | $O(N \times M)$ |  Tie   |
| **`equals(Object)`**          |     $O(N)$      |     $O(N)$      |  Tie   |
| **`get(int)`**                |     $O(1)$      |     $O(1)$      |  Tie   |
| **`hashCode()`**              |     $O(N)$      |     $O(N)$      |  Tie   |
| **`indexOf(Object)`**         |     $O(N)$      |     $O(N)$      |  Tie   |
| **`isEmpty()`**               |     $O(1)$      |     $O(1)$      |  Tie   |
| **`lastIndexOf(Object)`**     |     $O(N)$      |     $O(N)$      |  Tie   |
| **`remove(int)`**             |     $O(N)$      |     $O(N)$      |  Tie   |
| **`remove(Object)`**          |     $O(N)$      |     $O(N)$      |  Tie   |
| **`removeAll(Collection)`**   |   $O(N + M)$    |   $O(N + M)$    |  Tie   |
| **`retainAll(Collection)`**   |   $O(N + M)$    |   $O(N + M)$    |  Tie   |
| **`set(int, E)`**             |     $O(1)$      |     $O(1)$      |  Tie   |
| **`size()`**                  |     $O(1)$      |     $O(1)$      |  Tie   |
| **`subList(int, int)`**       |     $O(1)$      |     $O(1)$      |  Tie   |
| **`toArray()`**               |     $O(N)$      |     $O(N)$      |  Tie   |
| **`toArray(T[])`**            |     $O(N)$      |     $O(N)$      |  Tie   |
| **`toString()`**              |     $O(N)$      |     $O(N)$      |  Tie   |

# Space Complexity

| Method                        | Custom |  JDK   | Winner |
|:------------------------------|:------:|:------:|:------:|
| **`add(E)`**                  | $O(1)$ | $O(1)$ |  Tie   |
| **`add(int, E)`**             | $O(1)$ | $O(1)$ |  Tie   |
| **`addAll(Collection)`**      | $O(M)$ | $O(M)$ |  Tie   |
| **`addAll(int, Collection)`** | $O(M)$ | $O(M)$ |  Tie   |
| **`clear()`**                 | $O(1)$ | $O(1)$ |  Tie   |
| **`clone()`**                 | $O(N)$ | $O(N)$ |  Tie   |
| **`contains(Object)`**        | $O(1)$ | $O(1)$ |  Tie   |
| **`containsAll(Collection)`** | $O(1)$ | $O(1)$ |  Tie   |
| **`equals(Object)`**          | $O(1)$ | $O(1)$ |  Tie   |
| **`get(int)`**                | $O(1)$ | $O(1)$ |  Tie   |
| **`hashCode()`**              | $O(1)$ | $O(1)$ |  Tie   |
| **`indexOf(Object)`**         | $O(1)$ | $O(1)$ |  Tie   |
| **`isEmpty()`**               | $O(1)$ | $O(1)$ |  Tie   |
| **`lastIndexOf(Object)`**     | $O(1)$ | $O(1)$ |  Tie   |
| **`remove(int)`**             | $O(1)$ | $O(1)$ |  Tie   |
| **`remove(Object)`**          | $O(1)$ | $O(1)$ |  Tie   |
| **`removeAll(Collection)`**   | $O(M)$ | $O(M)$ |  Tie   |
| **`retainAll(Collection)`**   | $O(M)$ | $O(M)$ |  Tie   |
| **`set(int, E)`**             | $O(1)$ | $O(1)$ |  Tie   |
| **`size()`**                  | $O(1)$ | $O(1)$ |  Tie   |
| **`subList(int, int)`**       | $O(1)$ | $O(1)$ |  Tie   |
| **`toArray()`**               | $O(N)$ | $O(N)$ |  Tie   |
| **`toArray(T[])`**            | $O(N)$ | $O(N)$ |  Tie   |
| **`toString()`**              | $O(N)$ | $O(N)$ |  Tie   |

- N: Number of elements in the Vector.
- M: Number of elements in the input collection.

# Performance Comparison
| Method                    | JDK Vector (ns) | vector.CustomVector (ns) | Margin  |            Winner            |
  |:--------------------------|----------------:|-------------------------:|:-------:|:----------------------------:|
| `add(E)`                  |         337,675 |                  351,586 |  1.04×  | **Statistically Equivalent** |
| `add(int, E)`             |          15,137 |                   18,540 |  1.22×  |           **JDK**            |
| `addAll(Collection)`      |           8,564 |                   12,389 |  1.45×  |           **JDK**            |
| `addAll(int, Collection)` |          22,798 |                   31,350 |  1.38×  |           **JDK**            |
| `clear()`                 |           9,818 |                   18,392 |  1.87×  |           **JDK**            |
| `clone()`                 |           3,127 |                    3,267 |  1.04×  | **Statistically Equivalent** |
| `contains(Object)`        |          11,960 |                   12,946 |  1.08×  | **Statistically Equivalent** |
| `containsAll(Collection)` |     218,866,173 |                  558,933 | 391.58× |          **Custom**          |
| `equals(Object)`          |         496,455 |                   33,571 | 14.79×  |          **Custom**          |
| `get(int)`                |            10.5 |                     10.4 |  1.01×  | **Statistically Equivalent** |
| `hashCode()`              |         364,550 |                   26,126 | 13.95×  |          **Custom**          |
| `indexOf(Object)`         |          12,115 |                   12,716 |  1.05×  | **Statistically Equivalent** |
| `isEmpty()`               |             5.3 |                      5.2 |  1.01×  | **Statistically Equivalent** |
| `lastIndexOf(Object)`     |          12,453 |                   12,390 |  1.01×  | **Statistically Equivalent** |
| `remove(int)`             |           8,957 |                   12,377 |  1.38×  |           **JDK**            |
| `remove(Object)`          |          22,325 |                   25,985 |  1.16×  |           **JDK**            |
| `removeAll(Collection)`   |     221,345,066 |                  586,900 | 377.14× |          **Custom**          |
| `retainAll(Collection)`   |     218,905,092 |                  621,014 | 352.50× |          **Custom**          |
| `set(int, E)`             |           8,003 |                   11,202 |  1.40×  |           **JDK**            |
| `size()`                  |             5.2 |                      5.2 |  1.00×  | **Statistically Equivalent** |
| `subList(int, int)`       |             8.5 |                      6.7 |  1.26×  |          **Custom**          |
| `toArray()`               |           3,137 |                    3,130 |  1.00×  | **Statistically Equivalent** |
| `toArray(T[])`            |          16,298 |                   16,274 |  1.00×  | **Statistically Equivalent** |
| `toString()`              |       1,321,408 |                1,013,484 |  1.30×  |          **Custom**          |
<b>Note: The following performance charts are designed to be viewed in dark mode.</b>

![Combined Performance Charts](PerformanceTesting/heatmap.png)
![Combined Performance Charts](PerformanceTesting/plot_benchmarkAdd.png)
![Combined Performance Charts](PerformanceTesting/plot_benchmarkAddAtIndex.png)
![Combined Performance Charts](PerformanceTesting/plot_benchmarkAddAll.png)
![Combined Performance Charts](PerformanceTesting/plot_benchmarkAddAllAtIndex.png)
![Combined Performance Charts](PerformanceTesting/plot_benchmarkClear.png)
![Combined Performance Charts](PerformanceTesting/plot_benchmarkClone.png)
![Combined Performance Charts](PerformanceTesting/plot_benchmarkContains.png)
![Combined Performance Charts](PerformanceTesting/plot_benchmarkContainsAll.png)
![Combined Performance Charts](PerformanceTesting/plot_benchmarkEquals.png)
![Combined Performance Charts](PerformanceTesting/plot_benchmarkGet.png)
![Combined Performance Charts](PerformanceTesting/plot_benchmarkHashCode.png)
![Combined Performance Charts](PerformanceTesting/plot_benchmarkIndexOf.png)
![Combined Performance Charts](PerformanceTesting/plot_benchmarkIsEmpty.png)
![Combined Performance Charts](PerformanceTesting/plot_benchmarkLastIndexOf.png)
![Combined Performance Charts](PerformanceTesting/plot_benchmarkRemoveObj.png)
![Combined Performance Charts](PerformanceTesting/plot_benchmarkRemoveIndex.png)
![Combined Performance Charts](PerformanceTesting/plot_benchmarkRemoveAll.png)
![Combined Performance Charts](PerformanceTesting/plot_benchmarkRetainAll.png)
![Combined Performance Charts](PerformanceTesting/plot_benchmarkSet.png)
![Combined Performance Charts](PerformanceTesting/plot_benchmarkSize.png)
![Combined Performance Charts](PerformanceTesting/plot_benchmarkSubList.png)
![Combined Performance Charts](PerformanceTesting/plot_benchmarkToArray.png)
![Combined Performance Charts](PerformanceTesting/plot_benchmarkToArrayTyped.png)
![Combined Performance Charts](PerformanceTesting/plot_benchmarkToString.png)








