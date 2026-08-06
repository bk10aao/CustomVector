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
import java.util.Random;
import java.util.concurrent.TimeUnit;

@State(Scope.Benchmark)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Warmup(iterations = 2, time = 500, timeUnit = TimeUnit.MILLISECONDS)
@Measurement(iterations = 3, time = 500, timeUnit = TimeUnit.MILLISECONDS)
@Fork(2)
public class CustomVectorBenchmark {

    @Param({"5000", "10000", "15000", "20000", "25000", "30000", "35000", "40000", "45000", "50000"})
    private int size;

    private CustomVector<Integer> vector;
    private Collection<Integer> standardCollection;
    private Random random;

    @Setup(Level.Trial)
    public void setup() {
        random = new Random(42);
        standardCollection = new ArrayList<>(size);
        for (int i = 0; i < size; i++)
            standardCollection.add(random.nextInt());
        vector = new CustomVector<>();
        vector.addAll(standardCollection);
    }

    @Benchmark
    public void benchmarkAdd(Blackhole bh) {
        CustomVector<Integer> tempVector = new CustomVector<>();
        for (int i = 0; i < size; i++)
            tempVector.add(random.nextInt());
        bh.consume(tempVector);
    }

    @Benchmark
    public void benchmarkAddAtIndex(Blackhole bh) {
        CustomVector<Integer> tempVector = new CustomVector<>(vector);
        int targetIndex = random.nextInt(size);
        tempVector.add(targetIndex, 999);
        bh.consume(tempVector);
    }

    @Benchmark
    public void benchmarkAddAll(Blackhole bh) {
        CustomVector<Integer> tempVector = new CustomVector<>();
        tempVector.addAll(standardCollection);
        bh.consume(tempVector);
    }

    @Benchmark
    public void benchmarkAddAllAtIndex(Blackhole bh) {
        CustomVector<Integer> tempVector = new CustomVector<>(vector);
        tempVector.addAll(0, standardCollection);
        bh.consume(tempVector);
    }

    @Benchmark
    public void benchmarkClear(Blackhole bh) {
        CustomVector<Integer> tempVector = new CustomVector<>(standardCollection);
        tempVector.clear();
        bh.consume(tempVector);
    }

    @Benchmark
    public void benchmarkClone(Blackhole bh) {
        bh.consume(vector.clone());
    }

    @Benchmark
    public void benchmarkContains(Blackhole bh) {
        int query = random.nextInt(size * 2);
        bh.consume(vector.contains(query));
    }

    @Benchmark
    public void benchmarkContainsAll(Blackhole bh) {
        bh.consume(vector.containsAll(standardCollection));
    }

    @Benchmark
    public void benchmarkEquals(Blackhole bh) {
        CustomVector<Integer> other = new CustomVector<>(standardCollection);
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
            bh.consume(null);
            return;
        }
        CustomVector<Integer> workingCopy = new CustomVector<>(vector);
        int index = random.nextInt(workingCopy.size());
        workingCopy.remove(index);
        bh.consume(workingCopy);
    }

    @Benchmark
    public void benchmarkRemoveObj(Blackhole bh) {
        CustomVector<Integer> workingCopy = new CustomVector<>(vector);
        int target = random.nextInt(size * 2);
        bh.consume(workingCopy.remove((Integer) target));
    }

    @Benchmark
    public void benchmarkRemoveAll(Blackhole bh) {
        CustomVector<Integer> workingCopy = new CustomVector<>(standardCollection);
        workingCopy.removeAll(standardCollection);
        bh.consume(workingCopy);
    }

    @Benchmark
    public void benchmarkRetainAll(Blackhole bh) {
        CustomVector<Integer> workingCopy = new CustomVector<>(standardCollection);
        workingCopy.retainAll(standardCollection);
        bh.consume(workingCopy);
    }

    @Benchmark
    public void benchmarkSet(Blackhole bh) {
        CustomVector<Integer> workingCopy = new CustomVector<>(vector);
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
        bh.consume(vector.subList(0, mid));
    }

    @Benchmark
    public void benchmarkToArray(Blackhole bh) {
        bh.consume(vector.toArray());
    }

    @Benchmark
    public void benchmarkToArrayTyped(Blackhole bh) {
        Integer[] template = new Integer[vector.size()];
        bh.consume(vector.toArray(template));
    }

    @Benchmark
    public void benchmarkToString(Blackhole bh) {
        bh.consume(vector.toString());
    }
}