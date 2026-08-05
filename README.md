# Custom Vector

# Performance Charts
Below performance is a comparison made at 100,000 operations per method.

Note: all data is an average of 100 runs.

# Time Complexity

| Method                        |      Custom       |        JDK        | Winner  |
|:------------------------------|:-----------------:|:-----------------:|:-------:|
| **`add(E)`**                  | $O(1)$ amortized  | $O(1)$ amortized  |   Tie   |
| **`add(int, E)`**             |      $O(N)$       |      $O(N)$       |   Tie   |
| **`addAll(Collection)`**      |      $O(M)$       |      $O(M)$       |   Tie   |
| **`addAll(int, Collection)`** |    $O(N + M)$     |    $O(N + M)$     |   Tie   |
| **`clear()`**                 |      $O(N)$       |      $O(N)$       |   Tie   |
| **`clone()`**                 |      $O(N)$       |      $O(N)$       |   Tie   |
| **`contains(Object)`**        |      $O(N)$       |      $O(N)$       |   Tie   |
| **`containsAll(Collection)`** |  $O(N \times M)$  |  $O(N \times M)$  |   Tie   |
| **`equals(Object)`**          |      $O(N)$       |      $O(N)$       |   Tie   |
| **`get(int)`**                |      $O(1)$       |      $O(1)$       |   Tie   |
| **`hashCode()`**              |      $O(N)$       |      $O(N)$       |   Tie   |
| **`indexOf(Object)`**         |      $O(N)$       |      $O(N)$       |   Tie   |
| **`isEmpty()`**               |      $O(1)$       |      $O(1)$       |   Tie   |
| **`lastIndexOf(Object)`**     |      $O(N)$       |      $O(N)$       |   Tie   |
| **`remove(int)`**             |      $O(N)$       |      $O(N)$       |   Tie   |
| **`remove(Object)`**          |      $O(N)$       |      $O(N)$       |   Tie   |
| **`removeAll(Collection)`**   |  $O(N \times M)$  |  $O(N \times M)$  |   Tie   |
| **`retainAll(Collection)`**   |  $O(N \times M)$  |  $O(N \times M)$  |   Tie   |
| **`set(int, E)`**             |      $O(1)$       |      $O(1)$       |   Tie   |
| **`size()`**                  |      $O(1)$       |      $O(1)$       |   Tie   |
| **`subList(int, int)`**       |      $O(1)$       |      $O(1)$       |   Tie   |
| **`toArray()`**               |      $O(N)$       |      $O(N)$       |   Tie   |
| **`toArray(T[])`**            |      $O(N)$       |      $O(N)$       |   Tie   |
| **`toString()`**              |      $O(N)$       |      $O(N)$       |   Tie   |

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

- n: Number of elements in the Vector.
- m: Number of elements in the input collection.
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








