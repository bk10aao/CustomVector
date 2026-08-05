import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;
import java.util.Vector;

public class VectorPerformanceTest {

    private static volatile Object blackHole;
    private static final int BATCH_RUNS = 10000;

    public static void main(String[] args) {
        int[] sizes = {5000, 10000, 15000, 20000, 25000, 30000, 35000, 40000, 45000, 50000};
        ArrayList<long[]> results = new ArrayList<>();
        Random random = new Random();

        System.out.println("Warming up JVM to trigger JIT compilation for JDK Vector...");
        for (int i = 0; i < 500; i++) {
            Vector<Integer> warmUpVector = new Vector<>();
            for (int j = 0; j < 100; j++) warmUpVector.add(j);
            blackHole = warmUpVector.get(50);
            blackHole = warmUpVector.toString();
        }

        for (int size : sizes) {
            System.out.println("Profiling JDK Vector size: " + size);
            Collection<Integer> collection = generateCollection(size, random);
            Vector<Integer> vector = new Vector<>();
            vector.addAll(collection);

            long addTime = benchmarkAdd(size, random);
            long addAtIndexTime = benchmarkAddAtIndex(vector, random, size);
            long addAllTime = benchmarkAddAll(collection);
            long addAllAtIndexTime = benchmarkAddAllAtIndex(vector, collection);
            long clearTime = benchmarkClear(collection);
            long cloneTime = benchmarkClone(vector);
            long containsTime = benchmarkContains(vector, size, random);
            long containsAllTime = benchmarkContainsAll(vector, collection);
            long equalsTime = benchmarkEquals(vector, collection);
            long getTime = benchmarkGet(vector, size, random);
            long hashCodeTime = benchmarkHashCode(vector);
            long indexOfTime = benchmarkIndexOf(vector, size, random);
            long isEmptyTime = benchmarkIsEmpty(vector);
            long lastIndexOfTime = benchmarkLastIndexOf(vector, size, random);
            long removeIndexTime = benchmarkRemoveIndex(vector, random);
            long removeObjTime = benchmarkRemoveObj(vector, size, random);
            long removeAllTime = benchmarkRemoveAll(collection);
            long retainAllTime = benchmarkRetainAll(collection);
            long setTime = benchmarkSet(vector, size, random);
            long sizeTime = benchmarkSize(vector);
            long subListTime = benchmarkSubList(vector, size);
            long toArrayTime = benchmarkToArray(vector);
            long toArrayTypedTime = benchmarkToArrayTyped(vector);
            long toStringTime = benchmarkToString(vector);

            results.add(new long[]{
                    size, addTime, addAtIndexTime, addAllTime, addAllAtIndexTime, clearTime,
                    cloneTime, containsTime, containsAllTime, equalsTime, getTime, hashCodeTime,
                    indexOfTime, isEmptyTime, lastIndexOfTime, removeIndexTime, removeObjTime,
                    removeAllTime, retainAllTime, setTime, sizeTime, subListTime,
                    toArrayTime, toArrayTypedTime, toStringTime
            });
        }

        try (FileWriter writer = new FileWriter("JDKVector_performance_data.csv")) {
            writer.write("Size,add(E),add(int_E),addAll(Collection),addAll(int_Collection)," +
                    "clear(),clone(),contains(Object),containsAll(Collection),equals(Object)," +
                    "get(int),hashCode(),indexOf(Object),isEmpty(),lastIndexOf(Object)," +
                    "remove(int),remove(Object),removeAll(Collection),retainAll(Collection)," +
                    "set(int_E),size(),subList(int_int),toArray(),toArray(T[]),toString()\n");
            for (long[] result : results) {
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < result.length; i++) {
                    sb.append(result[i]);
                    if (i < result.length - 1) sb.append(",");
                }
                writer.write(sb.append("\n").toString());
            }
            System.out.println("JDK Vector performance diagnostics exported successfully.");
        } catch (IOException e) {
            System.err.println("Error writing CSV: " + e.getMessage());
        }
    }

    private static long benchmarkAdd(int size, Random random) {
        int[] items = new int[size];
        for (int i = 0; i < size; i++) items[i] = random.nextInt();
        long start = System.nanoTime();
        Vector<Integer> vector = new Vector<>();
        for (int i = 0; i < size; i++) vector.add(items[i]);
        long duration = System.nanoTime() - start;
        blackHole = vector;
        return duration;
    }

    private static long benchmarkAddAtIndex(Vector<Integer> sourceVector, Random random, int size) {
        Vector<Integer> vector = new Vector<>(sourceVector);
        int targetIndex = size > 0 ? random.nextInt(size) : 0;
        long start = System.nanoTime();
        vector.add(targetIndex, 999);
        long duration = System.nanoTime() - start;
        blackHole = vector;
        return duration;
    }

    private static long benchmarkAddAll(Collection<Integer> collection) {
        long start = System.nanoTime();
        Vector<Integer> vector = new Vector<>();
        vector.addAll(collection);
        long duration = System.nanoTime() - start;
        blackHole = vector;
        return duration;
    }

    private static long benchmarkAddAllAtIndex(Vector<Integer> sourceVector, Collection<Integer> collection) {
        Vector<Integer> vector = new Vector<>(sourceVector);
        long start = System.nanoTime();
        vector.addAll(0, collection);
        long duration = System.nanoTime() - start;
        blackHole = vector;
        return duration;
    }

    private static long benchmarkClear(Collection<Integer> initialData) {
        Vector<Integer> vector = new Vector<>(initialData);
        long start = System.nanoTime();
        vector.clear();
        long duration = System.nanoTime() - start;
        blackHole = vector;
        return duration;
    }

    private static long benchmarkClone(Vector<Integer> vector) {
        long start = System.nanoTime();
        Vector<Integer> clone = (Vector<Integer>) vector.clone();
        long duration = System.nanoTime() - start;
        blackHole = clone;
        return duration;
    }

    private static long benchmarkContains(Vector<Integer> vector, int size, Random random) {
        int[] queries = new int[BATCH_RUNS];
        for (int i = 0; i < BATCH_RUNS; i++) queries[i] = random.nextInt(size * 2);
        boolean checksum = false;
        long start = System.nanoTime();
        for (int i = 0; i < BATCH_RUNS; i++) checksum ^= vector.contains(queries[i]);
        long duration = System.nanoTime() - start;
        blackHole = checksum;
        return duration / BATCH_RUNS;
    }

    private static long benchmarkContainsAll(Vector<Integer> vector, Collection<Integer> collection) {
        long start = System.nanoTime();
        boolean res = vector.containsAll(collection);
        long duration = System.nanoTime() - start;
        blackHole = res;
        return duration;
    }

    private static long benchmarkEquals(Vector<Integer> vector, Collection<Integer> collection) {
        Vector<Integer> other = new Vector<>(collection);
        long start = System.nanoTime();
        boolean res = vector.equals(other);
        long duration = System.nanoTime() - start;
        blackHole = res;
        return duration;
    }

    private static long benchmarkGet(Vector<Integer> vector, int size, Random random) {
        int[] indices = new int[BATCH_RUNS];
        for (int i = 0; i < BATCH_RUNS; i++) indices[i] = size > 0 ? random.nextInt(size) : 0;
        long start = System.nanoTime();
        int sum = 0;
        for (int i = 0; i < BATCH_RUNS; i++) sum += vector.get(indices[i]);
        long duration = System.nanoTime() - start;
        blackHole = sum;
        return duration / BATCH_RUNS;
    }

    private static long benchmarkHashCode(Vector<Integer> vector) {
        int hash = 0;
        long start = System.nanoTime();
        for (int i = 0; i < BATCH_RUNS; i++) hash = vector.hashCode();
        long duration = System.nanoTime() - start;
        blackHole = hash;
        return duration / BATCH_RUNS;
    }

    private static long benchmarkIndexOf(Vector<Integer> vector, int size, Random random) {
        int[] queries = new int[BATCH_RUNS];
        for (int i = 0; i < BATCH_RUNS; i++) queries[i] = random.nextInt(size * 2);
        long start = System.nanoTime();
        int indexSum = 0;
        for (int i = 0; i < BATCH_RUNS; i++) indexSum += vector.indexOf(queries[i]);
        long duration = System.nanoTime() - start;
        blackHole = indexSum;
        return duration / BATCH_RUNS;
    }

    private static long benchmarkIsEmpty(Vector<Integer> vector) {
        boolean res = false;
        long start = System.nanoTime();
        for (int i = 0; i < BATCH_RUNS; i++) res ^= vector.isEmpty();
        long duration = System.nanoTime() - start;
        blackHole = res;
        return duration / BATCH_RUNS;
    }

    private static long benchmarkLastIndexOf(Vector<Integer> vector, int size, Random random) {
        int[] queries = new int[BATCH_RUNS];
        for (int i = 0; i < BATCH_RUNS; i++) queries[i] = random.nextInt(size * 2);
        long start = System.nanoTime();
        int indexSum = 0;
        for (int i = 0; i < BATCH_RUNS; i++) indexSum += vector.lastIndexOf(queries[i]);
        long duration = System.nanoTime() - start;
        blackHole = indexSum;
        return duration / BATCH_RUNS;
    }

    private static long benchmarkRemoveIndex(Vector<Integer> sourceVector, Random random) {
        if (sourceVector.isEmpty()) return 0;
        Vector<Integer> workingCopy = new Vector<>(sourceVector);
        int index = random.nextInt(workingCopy.size());
        long start = System.nanoTime();
        workingCopy.remove(index);
        long duration = System.nanoTime() - start;
        blackHole = workingCopy;
        return duration;
    }

    private static long benchmarkRemoveObj(Vector<Integer> vector, int size, Random random) {
        int[] targets = new int[BATCH_RUNS];
        for (int i = 0; i < BATCH_RUNS; i++) targets[i] = random.nextInt(size * 2);
        Vector<Integer> workingCopy = new Vector<>(vector);
        boolean res = false;
        long start = System.nanoTime();
        for (int i = 0; i < BATCH_RUNS; i++) res ^= workingCopy.remove((Integer) targets[i]);
        long duration = System.nanoTime() - start;
        blackHole = res;
        return duration / BATCH_RUNS;
    }

    private static long benchmarkRemoveAll(Collection<Integer> collection) {
        Vector<Integer> workingCopy = new Vector<>(collection);
        long start = System.nanoTime();
        workingCopy.removeAll(collection);
        long duration = System.nanoTime() - start;
        blackHole = workingCopy;
        return duration;
    }

    private static long benchmarkRetainAll(Collection<Integer> collection) {
        Vector<Integer> workingCopy = new Vector<>(collection);
        long start = System.nanoTime();
        workingCopy.retainAll(collection);
        long duration = System.nanoTime() - start;
        blackHole = workingCopy;
        return duration;
    }

    private static long benchmarkSet(Vector<Integer> vector, int size, Random random) {
        Vector<Integer> workingCopy = new Vector<>(vector);
        int index = size > 0 ? random.nextInt(size) : 0;
        long start = System.nanoTime();
        if (size > 0) workingCopy.set(index, 12345);
        long duration = System.nanoTime() - start;
        blackHole = workingCopy;
        return duration;
    }

    private static long benchmarkSize(Vector<Integer> vector) {
        int finalSize = 0;
        long start = System.nanoTime();
        for (int i = 0; i < BATCH_RUNS; i++) finalSize += vector.size();
        long duration = System.nanoTime() - start;
        blackHole = finalSize;
        return duration / BATCH_RUNS;
    }

    private static long benchmarkSubList(Vector<Integer> vector, int size) {
        int mid = size / 2;
        long start = System.nanoTime();
        List<Integer> sub = vector.subList(0, mid);
        long duration = System.nanoTime() - start;
        blackHole = sub;
        return duration;
    }

    private static long benchmarkToArray(Vector<Integer> vector) {
        long start = System.nanoTime();
        Object[] array = vector.toArray();
        long duration = System.nanoTime() - start;
        blackHole = array;
        return duration;
    }

    private static long benchmarkToArrayTyped(Vector<Integer> vector) {
        Integer[] template = new Integer[vector.size()];
        long start = System.nanoTime();
        Integer[] array = vector.toArray(template);
        long duration = System.nanoTime() - start;
        blackHole = array;
        return duration;
    }

    private static long benchmarkToString(Vector<Integer> vector) {
        long start = System.nanoTime();
        String str = vector.toString();
        long duration = System.nanoTime() - start;
        blackHole = str;
        return duration;
    }

    private static Collection<Integer> generateCollection(int size, Random random) {
        Collection<Integer> collection = new ArrayList<>(size);
        for (int i = 0; i < size; i++) collection.add(random.nextInt());
        return collection;
    }
}