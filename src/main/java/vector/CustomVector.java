package vector;

import java.util.AbstractList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Objects;
import java.util.Set;

import static java.util.Collections.unmodifiableList;
import static java.util.Objects.checkIndex;
import static java.util.Objects.requireNonNull;

/**
 * @author Benjamin Kane
 * A resizable array-backed implementation of the {@link List} interface that
 * permits {@code null} elements.
 *
 * <p>This implementation provides all optional list operations, runs in
 * amortized constant time for additions at the end of the vector, and resizes
 * dynamically with a growth factor of 1.5. Capacity is never reduced below
 * 32 elements.</p>
 *
 * <p>This class is thread-safe and synchronized across all methods.</p>
 *
 * @param <E> the type of elements in this vector
 * LinkedIn - <a href="https://www.linkedin.com/in/benjamin-kane-81149482/"/>
 * GitHub account bk10aao - <a href="https://github.com/bk10aao"/>
 * Repository - <a href="https://github.com/bk10aao/CustomVector"/>
 */
public class CustomVector<E> extends AbstractList<E> implements List<E>, Cloneable {

    private static final int MAX_ARRAY_SIZE = Integer.MAX_VALUE - 8;
    private static final int MINIMUM_CAPACITY = 32;

    private int size = 0;

    private Object[] list;

    /**
     * Constructs an empty vector with an initial capacity of 32.
     */
    public CustomVector() {
        this.list = new Object[MINIMUM_CAPACITY];
    }

    /**
     * Constructs an empty vector with the specified initial capacity.
     * The initial capacity will be at least 32, regardless of the specified value.
     *
     * @param initialCapacity the initial capacity of the vector
     * @throws IllegalArgumentException if the specified initial capacity is negative
     */
    public CustomVector(final int initialCapacity) {
        if (initialCapacity < 0)
            throw new IllegalArgumentException();
        this.list = new Object[Math.max(initialCapacity, MINIMUM_CAPACITY)];
    }

    /**
     * Constructs a vector containing the elements of the specified collection,
     * in the order they are returned by the collection's iterator.
     *
     * @param values the collection whose elements are to be placed into this vector
     * @throws NullPointerException if the specified collection is null
     */
    public CustomVector(final Collection<? extends E> values) {
        requireNonNull(values);
        this.list = new Object[Math.max(MINIMUM_CAPACITY, values.size())];
        addAll(values);
    }

    /**
     * Appends the specified element to the end of this vector.
     *
     * @param item element to be appended to this vector
     * @return {@code true} (as specified by {@link Collection#add})
     * @throws NullPointerException if the specified element is null
     */
    public synchronized boolean add(final E item) {
        requireNonNull(item);
        ensureCapacity(size + 1);
        list[size++] = item;
        return true;
    }

    /**
     * Inserts the specified element at the specified position in this vector.
     * Shifts the element currently at that position (if any) and any subsequent
     * elements to the right.
     *
     * @param index index at which the specified element is to be inserted
     * @param element element to be inserted
     * @throws IndexOutOfBoundsException if the index is out of range ({code index < 0 || index > size()})
     * @throws NullPointerException if the specified element is null
     */
    public synchronized void add(final int index, final E element) {
        requireNonNull(element);
        checkIndex(index, size + 1);
        ensureCapacity(size + 1);
        System.arraycopy(list, index, list, index + 1, size - index);
        list[index] = element;
        size++;
    }

    /**
     * Appends all elements in the specified collection to the end of this vector,
     * in the order they are returned by the collection's iterator.
     *
     * @param c collection containing elements to be added to this vector
     * @return {@code true} if this vector changed as a result of the call
     * @throws NullPointerException if the specified collection is null
     */
    public synchronized boolean addAll(final Collection<? extends E> c) {
        requireNonNull(c);
        return insert(size, c);
    }

    /**
     * Inserts all elements in the specified collection into this vector,
     * starting at the specified position. Shifts the element currently at
     * that position and any subsequent elements to the right.
     *
     * @param index index at which to insert the first element from the specified collection
     * @param c collection containing elements to be added to this vector
     * @return {@code true} if this vector changed as a result of the call
     * @throws IndexOutOfBoundsException if the index is out of range ({code index < 0 || index > size()})
     * @throws NullPointerException if the specified collection is null
     */
    public synchronized boolean addAll(final int index, final Collection<? extends E> c) {
        return insert(index, c);
    }

    /**
     * Removes all elements from this vector. The vector will be empty after this call returns.
     */
    public synchronized void clear() {
        for (int i = 0; i < size; i++)
            list[i] = null;
        size = 0;
    }

    /**
     * Returns a shallow copy of this {@code CustomVector} instance. (The elements
     * themselves are not cloned.)
     *
     * @return a clone of this vector instance
     */
    @Override
    public CustomVector<E> clone() {
        try {
            CustomVector<E> clone = (CustomVector<E>) super.clone();
            clone.list = Arrays.copyOf(this.list, list.length);
            clone.size = this.size;
            return clone;
        } catch(CloneNotSupportedException e) {
            throw new InternalError(e);
        }
    }

    /**
     * Returns {@code true} if this vector contains the specified element.
     * More formally, returns {@code true} if and only if this vector contains
     * at least one element {@code e} such that
     * {@code Objects.equals(o, e)}.
     *
     * @param o element whose presence in this vector is to be tested
     * @return {@code true} if this vector contains the specified element
     */
    public synchronized boolean contains(final Object o) {
        if (o == null)
            for (int i = 0; i < size; i++)
                if (list[i] == null)
                    return true;
        for (int i = 0; i < size; i++)
            if (o.equals(list[i]))
                return true;
        return false;
    }

    /**
     * Returns {@code true} if this vector contains all the elements
     * of the specified collection.
     *
     * @param c collection to be checked for containment in this vector
     * @return {@code true} if this vector contains all elements of the specified collection
     * @throws NullPointerException if the specified collection is null
     */
    public synchronized boolean containsAll(final Collection<?> c) {
        requireNonNull(c);
        if (c.isEmpty())
            return true;
        Set<?> set = (c instanceof Set<?>) ? (Set<?>) c : new HashSet<>(c);
        for (int i = 0; i < size; i++) {
            set.remove(list[i]);
            if (set.isEmpty())
                return true;
        }
        return false;
    }

    /**
     * Compares the specified object with this vector for equality. Returns
     * {@code true} if and only if the specified object is also a list, both
     * lists have the same size, and all corresponding pairs of elements in
     * the two lists are equal.
     *
     * @param o the object to be compared for equality with this vector
     * @return {@code true} if the specified object is equal to this vector
     */
    @Override
    public synchronized boolean equals(final Object o) {
        if (this == o)
            return true;
        if (!(o instanceof List<?> other))
            return false;
        if (size != other.size())
            return false;
        if (o instanceof CustomVector<?> otherVector) {
            synchronized (unmodifiableList(otherVector)) {
                for (int i = 0; i < size; i++)
                    if (!Objects.equals(list[i], otherVector.list[i]))
                        return false;
                return true;
            }
        }
        Iterator<?> otherIterator = other.iterator();
        for (int i = 0; i < size; i++)
            if (!Objects.equals(list[i], otherIterator.next()))
                return false;
        return true;
    }

    /**
     * Returns the element at the specified position in this vector.
     *
     * @param index index of the element to return
     * @return the element at the specified position in this vector
     * @throws IndexOutOfBoundsException if the index is out of range ({code index < 0 || index >= size()})
     */
    public synchronized E get(final int index) {
        checkIndex(index, size);
        return (E) list[index];
    }

    /**
     * Returns the hash code value for this vector.
     *
     * @return the hash code value for this vector
     */
    public synchronized int hashCode() {
        int result = 1;
        for (int i = 0; i < size; i++)
            result = 31 * result + Objects.hashCode(list[i]);
        return result;
    }

    /**
     * Returns the index of the first occurrence of the specified element
     * in this vector, or -1 if this vector does not contain the element.
     *
     * @param o element to search for
     * @return the index of the first occurrence of the specified element in
     *         this vector, or -1 if this vector does not contain the element
     */
    public synchronized int indexOf(final Object o) {
        if (o == null) {
            for (int i = 0; i < size; i++)
                if (list[i] == null)
                    return i;
        } else {
            for (int i = 0; i < size; i++)
                if (o.equals(list[i]))
                    return i;
        }
        return -1;
    }

    /**
     * Returns {@code true} if this vector contains no elements.
     *
     * @return {@code true} if this vector contains no elements
     */
    public synchronized boolean isEmpty() {
        return size == 0;
    }

    /**
     * Returns an iterator over the elements in this vector in proper sequence.
     *
     * @return an iterator over the elements in this vector in proper sequence
     */
    public synchronized Iterator<E> iterator() {
        return new Iterator<>() {
            private int currentIndex = 0;

            public boolean hasNext() {
                return currentIndex < size;
            }

            public E next() {
                if (!hasNext())
                    throw new java.util.NoSuchElementException();
                synchronized (CustomVector.this) {
                    return (E) list[currentIndex++];
                }
            }
        };
    }

    /**
     * Returns the index of the last occurrence of the specified element
     * in this vector, or -1 if this vector does not contain the element.
     *
     * @param o element to search for
     * @return the index of the last occurrence of the specified element in
     *         this vector, or -1 if this vector does not contain the element
     */
    public synchronized int lastIndexOf(Object o) {
        if (o == null) {
            for (int i = size - 1; i >= 0; i--)
                if (list[i] == null)
                    return i;
        } else
            for (int i = size - 1; i >= 0; i--)
                if (o.equals(list[i]))
                    return i;
        return -1;
    }

    /**
     * Returns a list iterator over the elements in this vector (in proper
     * sequence), starting at the specified position in the vector.
     *
     * @param index index of the first element to be returned from the
     *              list iterator (by a call to {@code next})
     * @return a list iterator over the elements in this vector (in proper
     *         sequence), starting at the specified position
     * @throws IndexOutOfBoundsException if the index is out of range ({code index < 0 || index > size()})
     */
    public synchronized ListIterator<E> listIterator(final int index) {
        checkIndex(index, size + 1);
        return new VectorListIterator(index);
    }

    /**
     * Returns a list iterator over the elements in this vector (in proper
     * sequence).
     *
     * @return a list iterator over the elements in this vector (in proper
     *         sequence)
     */
    public synchronized ListIterator<E> listIterator() {
        return listIterator(0);
    }

    /**
     * Removes the element at the specified position in this vector.
     * Shifts any subsequent elements to the left.
     *
     * @param index the index of the element to be removed
     * @return the element that was removed from the vector
     * @throws IndexOutOfBoundsException if the index is out of range ({code index < 0 || index >= size()})
     */
    public synchronized E remove(final int index) {
        checkIndex(index, size);
        E o = (E) list[index];
        System.arraycopy(list, index + 1, list, index, size - index - 1);
        list[--size] = null;
        return o;
    }

    /**
     * Removes the first occurrence of the specified element from this vector,
     * if it is present. If the vector does not contain the element, it is unchanged.
     *
     * @param o element to be removed from this vector, if present
     * @return {@code true} if this vector contained the specified element
     */
    public synchronized boolean remove(final Object o) {
        int index = indexOf(o);
        if (index != -1) {
            remove(index);
            return true;
        }
        return false;
    }

    /**
     * Removes from this vector all of its elements that are contained in the
     * specified collection.
     *
     * @param c collection containing elements to be removed from this vector
     * @return {@code true} if this vector changed as a result of the call
     * @throws NullPointerException if the specified collection is null
     */
    public synchronized boolean removeAll(final Collection<?> c) {
        requireNonNull(c);
        if (c.isEmpty())
            return false;
        Set<?> set = (c instanceof Set<?>) ? (Set<?>) c : new HashSet<>(c);
        int w = 0;
        for (int r = 0; r < size; r++)
            if (!set.contains(list[r]))
                list[w++] = list[r];
        if (w == size)
            return false;
        Arrays.fill(list, w, size, null);
        size = w;
        return true;
    }

    /**
     * Retains only the elements in this vector that are contained in the
     * specified collection. In other words, removes from this vector all
     * of its elements that are not contained in the specified collection.
     *
     * @param c collection containing elements to be retained in this vector
     * @return {@code true} if this vector changed as a result of the call
     * @throws NullPointerException if the specified collection is null
     */
    public synchronized boolean retainAll(final Collection<?> c) {
        requireNonNull(c);
        Set<?> set = (c instanceof Set<?>) ? (Set<?>) c : new HashSet<>(c);
        int w = 0;
        for (int r = 0; r < size; r++)
            if (set.contains(list[r]))
                list[w++] = list[r];
        if (w == size)
            return false;
        Arrays.fill(list, w, size, null);
        size = w;
        return true;
    }

    /**
     * Replaces the element at the specified position in this vector with
     * the specified element.
     *
     * @param index index of the element to replace
     * @param element element to be stored at the specified position
     * @return the element previously at the specified position
     * @throws IndexOutOfBoundsException if the index is out of range ({code index < 0 || index >= size()})
     * @throws NullPointerException if the specified element is null
     */
    public synchronized E set(final int index, final E element) {
        requireNonNull(element);
        checkIndex(index, size);
        E replaced = (E) list[index];
        list[index] = element;
        return replaced;
    }

    /**
     * Returns the number of elements in this vector.
     *
     * @return the number of elements in this vector
     */
    public synchronized int size() {
        return size;
    }

    /**
     * Returns a view of the portion of this vector between the specified
     * {@code fromIndex}, inclusive, and {@code toIndex}, exclusive.
     *
     * @param fromIndex low endpoint (inclusive) of the subList
     * @param toIndex high endpoint (exclusive) of the subList
     * @return a view of the specified range within this vector
     * @throws IndexOutOfBoundsException for an illegal endpoint index value
     *         ({code fromIndex < 0 || toIndex > size || fromIndex > toIndex})
     */
    public synchronized List<E> subList(final int fromIndex, final int toIndex) {
        return super.subList(fromIndex, toIndex);
    }

    /**
     * Returns an array containing all the elements in this vector
     * in proper sequence (from first to last element).
     *
     * <p>The returned array will be "safe" in that no references to it are
     * maintained by this vector. (In other words, this method must allocate
     * a new array). The caller is thus free to modify the returned array.
     *
     * @return an array containing all the elements in this vector in proper sequence
     */
    public synchronized Object[] toArray() {
        return Arrays.copyOf(list, size, Object[].class);
    }

    /**
     * Returns an array containing all the elements in this vector in
     * proper sequence (from first to last element); the runtime type of the
     * returned array is that of the specified array. If the vector fits in
     * the specified array, it is returned therein. Otherwise, a new array is
     * allocated with the runtime type of the specified array and the size of this vector.
     *
     * <p>If the vector fits in the specified array with room to spare
     * (i.e., the array has more elements than the vector), the element in the
     * array immediately following the end of the collection is set to {@code null}.
     *
     * @param a the array into which the elements of the vector are to
     *          be stored, if it is big enough; otherwise, a new array of the
     *          same runtime type is allocated for this purpose
     * @param <T> the runtime type of the array to contain the collection
     * @return an array containing the elements of the vector
     * @throws NullPointerException if the specified array is null
     */
    @SuppressWarnings("SuspiciousSystemArraycopy")
    public synchronized <T> T[] toArray(final T[] a) {
        requireNonNull(a);
        if (a.length < size)
            return (T[]) Arrays.copyOf(list, size, a.getClass());
        System.arraycopy(list, 0, a, 0, size);
        if (a.length > size)
            a[size] = null;
        return a;
    }

    /**
     * Returns a string representation of this vector, containing its size
     * and the string representation of each element in proper sequence.
     *
     * @return a string representation of this vector
     */
    @Override
    public synchronized String toString() {
        if (size == 0)
            return "vector.CustomVector{size=0, list=[]}";
        StringBuilder sb = new StringBuilder("vector.CustomVector{size=").append(size).append(", list=[");
        for (int i = 0; i < size; i++) {
            sb.append(list[i]);
            if (i < size - 1)
                sb.append(", ");
        }
        return sb.append("]}").toString();
    }

    private void ensureCapacity(final int minCapacity) {
        if (minCapacity <= list.length)
            return;
        int oldCapacity = list.length;
        int newCapacity = oldCapacity + (oldCapacity >> 1);
        if (newCapacity < minCapacity)
            newCapacity = minCapacity;
        if (newCapacity > MAX_ARRAY_SIZE)
            newCapacity = hugeCapacity(minCapacity);
        list = Arrays.copyOf(list, newCapacity);
    }

    private boolean insert(final int index, final Collection<? extends E> c) {
        if (c.isEmpty())
            return false;
        Object[] a;
        if (c instanceof CustomVector<?> otherVector)
            synchronized (unmodifiableList(otherVector)) {
                a = Arrays.copyOf(otherVector.list, otherVector.size);
            }
        else
            a = c.toArray();
        int newSize = a.length;
        for (Object o : a)
            requireNonNull(o);
        ensureCapacity(size + newSize);
        int numMoved = size - index;
        if (numMoved > 0)
            System.arraycopy(list, index, list, index + newSize, numMoved);
        System.arraycopy(a, 0, list, index, newSize);
        size += newSize;
        return true;
    }

    private static int hugeCapacity(final int minCapacity) {
        if (minCapacity < 0)
            throw new OutOfMemoryError();
        return (minCapacity > MAX_ARRAY_SIZE) ? Integer.MAX_VALUE : MAX_ARRAY_SIZE;
    }

    /**
     * A custom implementation of {@link ListIterator} for {@code CustomVector}.
     */
    private class VectorListIterator implements ListIterator<E> {
        private int cursor;
        private int lastRet = -1;

        /**
         * Constructs a new {@code VectorListIterator} starting at the specified index.
         *
         * @param index the starting index for the iterator
         */
        VectorListIterator(final int index) {
            this.cursor = index;
        }

        /**
         * {@inheritDoc}
         *
         * @throws NullPointerException if the specified element is null
         */
        public void add(final E e) {
            requireNonNull(e);
            synchronized (CustomVector.this) {
                try {
                    int i = cursor;
                    CustomVector.this.add(i, e);
                    cursor = i + 1;
                    lastRet = -1;
                } catch (IndexOutOfBoundsException ex) {
                    throw new java.util.ConcurrentModificationException();
                }
            }
        }

        /**
         * {@inheritDoc}
         */
        public boolean hasNext() {
            synchronized (CustomVector.this) {
                return cursor < size;
            }
        }

        /**
         * {@inheritDoc}
         */
        public boolean hasPrevious() {
            synchronized (CustomVector.this) {
                return cursor > 0;
            }
        }

        /**
         * {@inheritDoc}
         */
        public E next() {
            synchronized (CustomVector.this) {
                if (cursor >= size)
                    throw new java.util.NoSuchElementException();
                lastRet = cursor;
                return (E) list[cursor++];
            }
        }

        /**
         * {@inheritDoc}
         */
        public int nextIndex() {
            synchronized (CustomVector.this) {
                return cursor;
            }
        }

        /**
         * {@inheritDoc}
         */
        public E previous() {
            synchronized (CustomVector.this) {
                int i = cursor - 1;
                if (i < 0)
                    throw new java.util.NoSuchElementException();
                cursor = i;
                lastRet = i;
                return (E) list[i];
            }
        }

        /**
         * {@inheritDoc}
         */
        public int previousIndex() {
            synchronized (CustomVector.this) {
                return cursor - 1;
            }
        }

        /**
         * {@inheritDoc}
         */
        public void remove() {
            synchronized (CustomVector.this) {
                if (lastRet < 0)
                    throw new IllegalStateException();
                try {
                    CustomVector.this.remove(lastRet);
                    cursor = lastRet;
                    lastRet = -1;
                } catch (IndexOutOfBoundsException e) {
                    throw new java.util.ConcurrentModificationException();
                }
            }
        }

        /**
         * {@inheritDoc}
         *
         * @throws NullPointerException if the specified element is null
         */
        public void set(final E e) {
            requireNonNull(e);
            synchronized (CustomVector.this) {
                if (lastRet < 0)
                    throw new IllegalStateException();
                try {
                    CustomVector.this.set(lastRet, e);
                } catch (IndexOutOfBoundsException ex) {
                    throw new java.util.ConcurrentModificationException();
                }
            }
        }
    }
}