package vector;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Level;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;
import org.openjdk.jmh.infra.Blackhole;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;
import java.util.Vector;
import java.util.concurrent.TimeUnit;


@State(Scope.Benchmark)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Warmup(iterations = 2, time = 500, timeUnit = TimeUnit.MILLISECONDS)
@Measurement(iterations = 3, time = 500, timeUnit = TimeUnit.MILLISECONDS)
@Fork(2)
public class VectorBenchMark {

    @Param({"5000", "10000", "15000", "20000", "25000", "30000", "35000", "40000", "45000", "50000"})
    private int size;

    private Random random;
    private Collection<Integer> collection;
    private Vector<Integer> vector;

    @Setup(Level.Trial)
    public void setUp() {
        random = new Random(42);
        collection = new ArrayList<>(size);
        for (int i = 0; i < size; i++)
            collection.add(random.nextInt());
        vector = new Vector<>();
        vector.addAll(collection);
    }

    @Benchmark
    public void benchmarkAdd(Blackhole bh) {
        Vector<Integer> localVector = new Vector<>();
        for (int i = 0; i < size; i++)
            localVector.add(random.nextInt());
        bh.consume(localVector);
    }

    @Benchmark
    public void benchmarkAddAtIndex(Blackhole bh) {
        Vector<Integer> localVector = new Vector<>(vector);
        int targetIndex = random.nextInt(size);
        localVector.add(targetIndex, 999);
        bh.consume(localVector);
    }

    @Benchmark
    public void benchmarkAddAll(Blackhole bh) {
        Vector<Integer> localVector = new Vector<>();
        localVector.addAll(collection);
        bh.consume(localVector);
    }

    @Benchmark
    public void benchmarkAddAllAtIndex(Blackhole bh) {
        Vector<Integer> localVector = new Vector<>(vector);
        localVector.addAll(0, collection);
        bh.consume(localVector);
    }

    @Benchmark
    public void benchmarkClear(Blackhole bh) {
        Vector<Integer> localVector = new Vector<>(collection);
        localVector.clear();
        bh.consume(localVector);
    }

    @Benchmark
    public void benchmarkClone(Blackhole bh) {
        Vector<Integer> clone = (Vector<Integer>) vector.clone();
        bh.consume(clone);
    }

    @Benchmark
    public void benchmarkContains(Blackhole bh) {
        int query = random.nextInt(size * 2);
        bh.consume(vector.contains(query));
    }

    @Benchmark
    public void benchmarkContainsAll(Blackhole bh) {
        bh.consume(vector.containsAll(collection));
    }

    @Benchmark
    public void benchmarkEquals(Blackhole bh) {
        Vector<Integer> other = new Vector<>(collection);
        bh.consume(vector.equals(other));
    }

    @Benchmark
    public void benchmarkGet(Blackhole bh) {
        int index = random.nextInt(size);
        bh.consume(vector.get(index));
    }

    @Benchmark
    public void benchmarkHashCode(Blackhole bh) {
        bh.consume(vector.hashCode());
    }

    @Benchmark
    public void benchmarkIndexOf(Blackhole bh) {
        int query = random.nextInt(size * 2);
        bh.consume(vector.indexOf(query));
    }

    @Benchmark
    public void benchmarkIsEmpty(Blackhole bh) {
        bh.consume(vector.isEmpty());
    }

    @Benchmark
    public void benchmarkLastIndexOf(Blackhole bh) {
        int query = random.nextInt(size * 2);
        bh.consume(vector.lastIndexOf(query));
    }

    @Benchmark
    public void benchmarkRemoveIndex(Blackhole bh) {
        if (vector.isEmpty()) {
            bh.consume(vector);
            return;
        }
        Vector<Integer> workingCopy = new Vector<>(vector);
        int index = random.nextInt(workingCopy.size());
        workingCopy.remove(index);
        bh.consume(workingCopy);
    }

    @Benchmark
    public void benchmarkRemoveObj(Blackhole bh) {
        Vector<Integer> workingCopy = new Vector<>(vector);
        int target = random.nextInt(size * 2);
        bh.consume(workingCopy.remove((Integer) target));
    }

    @Benchmark
    public void benchmarkRemoveAll(Blackhole bh) {
        Vector<Integer> workingCopy = new Vector<>(collection);
        workingCopy.removeAll(collection);
        bh.consume(workingCopy);
    }

    @Benchmark
    public void benchmarkRetainAll(Blackhole bh) {
        Vector<Integer> workingCopy = new Vector<>(collection);
        workingCopy.retainAll(collection);
        bh.consume(workingCopy);
    }

    @Benchmark
    public void benchmarkSet(Blackhole bh) {
        Vector<Integer> workingCopy = new Vector<>(vector);
        int index = random.nextInt(size);
        workingCopy.set(index, 12345);
        bh.consume(workingCopy);
    }

    @Benchmark
    public void benchmarkSize(Blackhole bh) {
        bh.consume(vector.size());
    }

    @Benchmark
    public void benchmarkSubList(Blackhole bh) {
        int mid = size / 2;
        List<Integer> sub = vector.subList(0, mid);
        bh.consume(sub);
    }

    @Benchmark
    public void benchmarkToArray(Blackhole bh) {
        Object[] array = vector.toArray();
        bh.consume(array);
    }

    @Benchmark
    public void benchmarkToArrayTyped(Blackhole bh) {
        Integer[] template = new Integer[vector.size()];
        Integer[] array = vector.toArray(template);
        bh.consume(array);
    }

    @Benchmark
    public void benchmarkToString(Blackhole bh) {
        bh.consume(vector.toString());
    }
}