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

| Method                        |     Custom      |       JDK       |   Winner   |
|:------------------------------|:---------------:|:---------------:|:----------:|
| **`add(E)`**                  |     $O(1)$      |     $O(1)$      |    Tie     |
| **`add(int, E)`**             |     $O(N)$      |     $O(N)$      |    Tie     |
| **`addAll(Collection)`**      |     $O(M)$      |     $O(M)$      |    Tie     |
| **`addAll(int, Collection)`** |   $O(N + M)$    |   $O(N + M)$    |    Tie     |
| **`clear()`**                 |     $O(N)$      |     $O(N)$      |    Tie     |
| **`clone()`**                 |     $O(N)$      |     $O(N)$      |    Tie     |
| **`contains(Object)`**        |     $O(N)$      |     $O(N)$      |    Tie     |
| **`containsAll(Collection)`** | $O(N \times M)$ | $O(N \times M)$ |    Tie     |
| **`equals(Object)`**          |     $O(N)$      |     $O(N)$      |    Tie     |
| **`get(int)`**                |     $O(1)$      |     $O(1)$      |    Tie     |
| **`hashCode()`**              |     $O(N)$      |     $O(N)$      |    Tie     |
| **`indexOf(Object)`**         |     $O(N)$      |     $O(N)$      |    Tie     |
| **`isEmpty()`**               |     $O(1)$      |     $O(1)$      |    Tie     |
| **`lastIndexOf(Object)`**     |     $O(N)$      |     $O(N)$      |    Tie     |
| **`remove(int)`**             |     $O(N)$      |     $O(N)$      |    Tie     |
| **`remove(Object)`**          |     $O(N)$      |     $O(N)$      |    Tie     |
| **`removeAll(Collection)`**   |   $O(N + M)$    | $O(N \times M)$ | **Custom** |
| **`retainAll(Collection)`**   |   $O(N + M)$    | $O(N \times M)$ | **Custom** |
| **`set(int, E)`**             |     $O(1)$      |     $O(1)$      |    Tie     |
| **`size()`**                  |     $O(1)$      |     $O(1)$      |    Tie     |
| **`subList(int, int)`**       |     $O(1)$      |     $O(1)$      |    Tie     |
| **`toArray()`**               |     $O(N)$      |     $O(N)$      |    Tie     |
| **`toArray(T[])`**            |     $O(N)$      |     $O(N)$      |    Tie     |
| **`toString()`**              |     $O(N)$      |     $O(N)$      |    Tie     |

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
| **`removeAll(Collection)`**   | $O(M)$ | $O(1)$ |  JDK   |
| **`retainAll(Collection)`**   | $O(M)$ | $O(N)$ | Custom |
| **`set(int, E)`**             | $O(1)$ | $O(1)$ |  Tie   |
| **`size()`**                  | $O(1)$ | $O(1)$ |  Tie   |
| **`subList(int, int)`**       | $O(1)$ | $O(1)$ |  Tie   |
| **`toArray()`**               | $O(N)$ | $O(N)$ |  Tie   |
| **`toArray(T[])`**            | $O(N)$ | $O(N)$ |  Tie   |
| **`toString()`**              | $O(N)$ | $O(N)$ |  Tie   |

- N: Number of elements in the Vector.
- M: Number of elements in the input collection.

# Performance Comparison
| Method                    | JDK Vector (ns) | CustomVector (ns) | Margin  |            Winner            |
  |:--------------------------|:----------------|:------------------|:-------:|:----------------------------:|
| `add(E)`                  | 346,801         | 345,973           |  1.00×  | **Statistically Equivalent** |
| `add(int, E)`             | 15,974          | 18,190            |  1.14×  |           **JDK**            |
| `addAll(Collection)`      | 8,865           | 12,244            |  1.38×  |           **JDK**            |
| `addAll(int, Collection)` | 23,522          | 31,531            |  1.34×  |           **JDK**            |
| `clear()`                 | 10,024          | 19,742            |  1.97×  |           **JDK**            |
| `clone()`                 | 3,199           | 3,163             |  1.01×  | **Statistically Equivalent** |
| `contains(Object)`        | 12,120          | 11,353            |  1.07×  | **Statistically Equivalent** |
| `containsAll(Collection)` | 222,387,584     | 219,183,663       |  1.01×  | **Statistically Equivalent** |
| `equals(Object)`          | 498,603         | 34,628            | 14.40×  |       **CustomVector**       |
| `get(int)`                | 10.6            | 10.4              |  1.02×  | **Statistically Equivalent** |
| `hashCode()`              | 362,008         | 26,230            | 13.80×  |       **CustomVector**       |
| `indexOf(Object)`         | 11,994          | 11,336            |  1.06×  | **Statistically Equivalent** |
| `isEmpty()`               | 5.2             | 5.2               |  1.00×  | **Statistically Equivalent** |
| `lastIndexOf(Object)`     | 12,330          | 12,352            |  1.00×  | **Statistically Equivalent** |
| `remove(int)`             | 9,085           | 12,578            |  1.38×  |           **JDK**            |
| `remove(Object)`          | 22,922          | 24,434            |  1.07×  | **Statistically Equivalent** |
| `removeAll(Collection)`   | 218,475,683     | 583,614           | 374.35× |       **CustomVector**       |
| `retainAll(Collection)`   | 219,063,867     | 617,556           | 354.73× |       **CustomVector**       |
| `set(int, E)`             | 7,844           | 11,304            |  1.44×  |           **JDK**            |
| `size()`                  | 5.2             | 5.2               |  1.01×  | **Statistically Equivalent** |
| `subList(int, int)`       | 8.4             | 1,527             | 180.95× |           **JDK**            |
| `toArray()`               | 3,147           | 3,150             |  1.00×  | **Statistically Equivalent** |
| `toArray(T[])`            | 16,302          | 16,370            |  1.00×  | **Statistically Equivalent** |
| `toString()`              | 1,327,281       | 1,037,339         |  1.28×  |       **CustomVector**       |

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








