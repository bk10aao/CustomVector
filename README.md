# Custom Vector

An implementation of a Vector.

All methods implemented are identical to those found in the Java List interface.

Build and Test
To build and test the project run command `./gradlew clean build`

To test the project run command `./gradlew test`

# Performance Charts

Below performance is a comparison made at 100,000 operations per method.

Note: all data is an average of 10000 runs.

# Time Complexity

| Method                        |      Custom      |       JDK       | Winner |
|:------------------------------|:----------------:|:---------------:|:------:|
| **`add(E)`**                  | $O(1)$ amortized |     $O(1)$      |  Tie   |
| **`add(int, E)`**             |      $O(N)$      |     $O(N)$      |  Tie   |
| **`addAll(Collection)`**      |      $O(M)$      |     $O(M)$      |  Tie   |
| **`addAll(int, Collection)`** |    $O(N + M)$    |   $O(N + M)$    |  Tie   |
| **`clear()`**                 |      $O(N)$      |     $O(N)$      |  Tie   |
| **`clone()`**                 |      $O(N)$      |     $O(N)$      |  Tie   |
| **`contains(Object)`**        |      $O(N)$      |     $O(N)$      |  Tie   |
| **`containsAll(Collection)`** | $O(N \times M)$  | $O(N \times M)$ |  Tie   |
| **`equals(Object)`**          |      $O(N)$      |     $O(N)$      |  Tie   |
| **`get(int)`**                |      $O(1)$      |     $O(1)$      |  Tie   |
| **`hashCode()`**              |      $O(N)$      |     $O(N)$      |  Tie   |
| **`indexOf(Object)`**         |      $O(N)$      |     $O(N)$      |  Tie   |
| **`isEmpty()`**               |      $O(1)$      |     $O(1)$      |  Tie   |
| **`lastIndexOf(Object)`**     |      $O(N)$      |     $O(N)$      |  Tie   |
| **`remove(int)`**             |      $O(N)$      |     $O(N)$      |  Tie   |
| **`remove(Object)`**          |      $O(N)$      |     $O(N)$      |  Tie   |
| **`removeAll(Collection)`**   | $O(N \times M)$  | $O(N \times M)$ |  Tie   |
| **`retainAll(Collection)`**   | $O(N \times M)$  | $O(N \times M)$ |  Tie   |
| **`set(int, E)`**             |      $O(1)$      |     $O(1)$      |  Tie   |
| **`size()`**                  |      $O(1)$      |     $O(1)$      |  Tie   |
| **`subList(int, int)`**       |      $O(1)$      |     $O(1)$      |  Tie   |
| **`toArray()`**               |      $O(N)$      |     $O(N)$      |  Tie   |
| **`toArray(T[])`**            |      $O(N)$      |     $O(N)$      |  Tie   |
| **`toString()`**              |      $O(N)$      |     $O(N)$      |  Tie   |

# Space Complexity

| Method                        | Custom  |   JDK   | Winner  |
|:------------------------------|:-------:|:-------:|:-------:|
| **`add(E)`**                  | $O(1)$  | $O(1)$  |   Tie   |
| **`add(int, E)`**             | $O(1)$  | $O(1)$  |   Tie   |
| **`addAll(Collection)`**      | $O(M)$  | $O(M)$  |   Tie   |
| **`addAll(int, Collection)`** | $O(M)$  | $O(M)$  |   Tie   |
| **`clear()`**                 | $O(1)$  | $O(1)$  |   Tie   |
| **`clone()`**                 | $O(N)$  | $O(N)$  |   Tie   |
| **`contains(Object)`**        | $O(1)$  | $O(1)$  |   Tie   |
| **`containsAll(Collection)`** | $O(1)$  | $O(1)$  |   Tie   |
| **`equals(Object)`**          | $O(1)$  | $O(1)$  |   Tie   |
| **`get(int)`**                | $O(1)$  | $O(1)$  |   Tie   |
| **`hashCode()`**              | $O(1)$  | $O(1)$  |   Tie   |
| **`indexOf(Object)`**         | $O(1)$  | $O(1)$  |   Tie   |
| **`isEmpty()`**               | $O(1)$  | $O(1)$  |   Tie   |
| **`lastIndexOf(Object)`**     | $O(1)$  | $O(1)$  |   Tie   |
| **`remove(int)`**             | $O(1)$  | $O(1)$  |   Tie   |
| **`remove(Object)`**          | $O(1)$  | $O(1)$  |   Tie   |
| **`removeAll(Collection)`**   | $O(1)$  | $O(1)$  |   Tie   |
| **`retainAll(Collection)`**   | $O(N)$  | $O(N)$  |   Tie   |
| **`set(int, E)`**             | $O(1)$  | $O(1)$  |   Tie   |
| **`size()`**                  | $O(1)$  | $O(1)$  |   Tie   |
| **`subList(int, int)`**       | $O(1)$  | $O(1)$  |   Tie   |
| **`toArray()`**               | $O(N)$  | $O(N)$  |   Tie   |
| **`toArray(T[])`**            | $O(N)$  | $O(N)$  |   Tie   |
| **`toString()`**              | $O(N)$  | $O(N)$  |   Tie   |

- N: Number of elements in the Vector.
- M: Number of elements in the input collection.

# Performance Comparison
  | Method                    | JDK Vector (ns) | CustomVector (ns) |            Winner            |  Margin  |
  |:--------------------------|----------------:|------------------:|:----------------------------:|:--------:|
  | `add(E)`                  |         444,204 |           411,762 | **Statistically Equivalent** |  1.08×   |
  | `add(int, E)`             |          20,383 |            21,717 | **Statistically Equivalent** |  1.07×   |
  | `addAll(Collection)`      |          24,450 |            40,538 |        **JDK Vector**        |  1.66×   |
  | `addAll(int, Collection)` |          40,408 |            60,100 |        **JDK Vector**        |  1.49×   |
  | `clear()`                 |          78,638 |           100,433 |        **JDK Vector**        |  1.28×   |
  | `clone()`                 |           9,542 |            11,967 |        **JDK Vector**        |  1.25×   |
  | `contains(Object)`        |          12,616 |            13,665 | **Statistically Equivalent** |  1.08×   |
  | `containsAll(Collection)` |     222,370,304 |       238,313,133 | **Statistically Equivalent** |  1.07×   |
  | `equals(Object)`          |         849,717 |           231,717 |       **CustomVector**       |  3.67×   |
  | `get(int)`                |              25 |                41 |        **JDK Vector**        |  1.65×   |
  | `hashCode()`              |         265,514 |            29,533 |       **CustomVector**       |  8.99×   |
  | `indexOf(Object)`         |          12,588 |            13,386 | **Statistically Equivalent** |  1.06×   |
  | `isEmpty()`               |              20 |                56 |        **JDK Vector**        |  2.71×   |
  | `lastIndexOf(Object)`     |          11,954 |            14,239 |        **JDK Vector**        |  1.19×   |
  | `remove(int)`             |           8,479 |             8,704 | **Statistically Equivalent** |  1.03×   |
  | `remove(Object)`          |          12,276 |            13,515 | **Statistically Equivalent** |  1.10×   |
  | `removeAll(Collection)`   |     221,051,221 |         1,029,425 |       **CustomVector**       | 214.73×  |
  | `retainAll(Collection)`   |     221,380,975 |         1,050,167 |       **CustomVector**       | 210.81×  |
  | `set(int, E)`             |           1,117 |             1,113 | **Statistically Equivalent** |  1.00×   |
  | `size()`                  |              23 |                84 |        **JDK Vector**        |  3.62×   |
  | `subList(int, int)`       |          29,783 |           195,762 |        **JDK Vector**        |  6.57×   |
  | `toArray()`               |          10,483 |            11,158 | **Statistically Equivalent** |  1.06×   |
  | `toArray(T[])`            |          36,500 |            39,858 | **Statistically Equivalent** |  1.09×   |
  | `toString()`              |       1,456,679 |         1,311,287 |       **CustomVector**       |  1.11×   |

<b>Note: The following performance charts are designed to be viewed in dark mode.</b>

![Combined Performance Charts](PerformanceTesting/vector_heatmap.png)

![Combined Performance Charts](PerformanceTesting/plot_add_E_.png)
![Combined Performance Charts](PerformanceTesting/plot_add_int_E_.png)
![Combined Performance Charts](PerformanceTesting/plot_addAll_Collection_.png)
![Combined Performance Charts](PerformanceTesting/plot_addAll_int_Collection_.png)
![Combined Performance Charts](PerformanceTesting/plot_clear__.png)
![Combined Performance Charts](PerformanceTesting/plot_contains_Object_.png)
![Combined Performance Charts](PerformanceTesting/plot_containsAll_Collection_.png)
![Combined Performance Charts](PerformanceTesting/plot_equals_Object_.png)
![Combined Performance Charts](PerformanceTesting/plot_get_int_.png)
![Combined Performance Charts](PerformanceTesting/plot_hashCode__.png)
![Combined Performance Charts](PerformanceTesting/plot_indexOf_Object_.png)
![Combined Performance Charts](PerformanceTesting/plot_isEmpty__.png)
![Combined Performance Charts](PerformanceTesting/plot_lastIndexOf_Object_.png)
![Combined Performance Charts](PerformanceTesting/plot_remove_Object_.png)
![Combined Performance Charts](PerformanceTesting/plot_remove_int_.png)
![Combined Performance Charts](PerformanceTesting/plot_removeAll_Collection_.png)
![Combined Performance Charts](PerformanceTesting/plot_retainAll_Collection_.png)
![Combined Performance Charts](PerformanceTesting/plot_set_int_E_.png)
![Combined Performance Charts](PerformanceTesting/plot_size__.png)
![Combined Performance Charts](PerformanceTesting/plot_sublist_int_int_.png)
![Combined Performance Charts](PerformanceTesting/plot_toArray__.png)
![Combined Performance Charts](PerformanceTesting/plot_toArray_T[]_.png)
![Combined Performance Charts](PerformanceTesting/plot_toString__.png)








