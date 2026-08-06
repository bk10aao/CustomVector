package vector;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Objects;
import java.util.Set;

import static java.util.Objects.checkFromToIndex;
import static java.util.Objects.checkIndex;
import static java.util.Objects.requireNonNull;

public class CustomVector<E> implements List<E>, Cloneable {

    private static final int MAX_ARRAY_SIZE = Integer.MAX_VALUE - 8;
    private static final int MINIMUM_CAPACITY = 32;

    private int size = 0;

    private Object[] list;

    public CustomVector() {
        this.list = new Object[MINIMUM_CAPACITY];
    }

    public CustomVector(final int initialCapacity) {
        if(initialCapacity < 0)
            throw new IllegalArgumentException();
        this.list = new Object[Math.max(initialCapacity, MINIMUM_CAPACITY)];
    }

    public CustomVector(final Collection<? extends E> values) {
        requireNonNull(values);
        this.list = new Object[Math.max(MINIMUM_CAPACITY, values.size())];
        addAll(values);
    }

    public synchronized boolean add(final E item) {
        requireNonNull(item);
        ensureCapacity(size + 1);
        list[size++] = item;
        return true;
    }
    public synchronized void add(final int index, final E element) {
        requireNonNull(element);
        checkIndex(index, size + 1);
        ensureCapacity(size + 1);
        System.arraycopy(list, index, list, index + 1, size - index);
        list[index] = element;
        size++;
    }

    public synchronized boolean addAll(final Collection<? extends E> c) {
        requireNonNull(c);
        return insert(size, c);
    }

    public synchronized boolean addAll(int index, Collection<? extends E> c) {
        return insert(index, c);
    }

    public synchronized void clear() {
        for(int i = 0; i < size; i++)
            list[i] = null;
        size = 0;
    }

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

    public synchronized boolean contains(final Object o) {
        if (o == null) {
            for (int i = 0; i < size; i++)
                if (list[i] == null)
                    return true;
        } else
            for (int i = 0; i < size; i++)
                if (o.equals(list[i])) return true;
        return false;
    }

    public synchronized boolean containsAll(Collection<?> c) {
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

    public synchronized boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof List<?> other))
            return false;
        if (size != other.size())
            return false;
        if (o instanceof CustomVector<?> otherVector) {
            synchronized (otherVector) {
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

    public synchronized E get(final int index) {
        checkIndex(index, size);
        return (E) list[index];
    }

    public synchronized int hashCode() {
        int result = 1;
        for(int i = 0; i < size; i++)
            result = 31 * result + Objects.hashCode(list[i]);
        return result;
    }

    public synchronized int indexOf(final Object o) {
        if (o == null) {
            for (int i = 0; i < size; i++)
                if (list[i] == null) return i;
        } else {
            for (int i = 0; i < size; i++)
                if (o.equals(list[i])) return i;
        }
        return -1;
    }

    public synchronized boolean isEmpty() {
        return size == 0;
    }

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

    public synchronized int lastIndexOf(Object o) {
        if (o == null) {
            for (int i = size - 1; i >= 0; i--)
                if (list[i] == null) return i;
        } else
            for (int i = size - 1; i >= 0; i--)
                if (o.equals(list[i])) return i;
        return -1;
    }

    public synchronized ListIterator<E> listIterator(int index) {
        checkIndex(index, size + 1);
        return new VectorListIterator(index);
    }

    public synchronized ListIterator<E> listIterator() {
        return listIterator(0);
    }

    public synchronized E remove(final int index) {
        checkIndex(index, size);
        E o = (E) list[index];
        System.arraycopy(list, index + 1, list, index, size - index - 1);
        list[--size] = null;
        return o;
    }

    public synchronized boolean remove(Object o) {
        int index = indexOf(o);
        if (index != -1) {
            remove(index);
            return true;
        }
        return false;
    }

    public synchronized boolean removeAll(Collection<?> c) {
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

    public synchronized boolean retainAll(Collection<?> c) {
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

    public synchronized E set(int index, E element) {
        requireNonNull(element);
        checkIndex(index, size);
        E replaced = (E) list[index];
        list[index] = element;
        return replaced;
    }

    public synchronized int size() {
        return size;
    }

    public synchronized List<E> subList(int fromIndex, int toIndex) {
        checkFromToIndex(fromIndex, toIndex, size);
        return Arrays.asList(Arrays.copyOfRange((E[]) list, fromIndex, toIndex));
    }

    public synchronized Object[] toArray() {
        return Arrays.copyOf(list, size, Object[].class);
    }

    @SuppressWarnings("SuspiciousSystemArraycopy")
    public synchronized <T> T[] toArray(T[] a) {
        requireNonNull(a);
        if(a.length < size)
            return (T[]) Arrays.copyOf(list, size, a.getClass());
        System.arraycopy(list, 0, a, 0, size);
        if(a.length > size)
            a[size] = null;
        return a;
    }

    @Override
    public synchronized String toString() {
        if(size == 0)
            return "vector.CustomVector{size=0, list=[]}";
        StringBuilder sb = new StringBuilder("vector.CustomVector{size=").append(size).append(", list=[");
        for(int i = 0; i < size; i++) {
            sb.append(list[i]);
            if(i < size - 1)
                sb.append(", ");
        }
        return sb.append("]}").toString();
    }

    private void ensureCapacity(int minCapacity) {
        if(minCapacity - list.length > 0) {
            int oldCapacity = list.length;
            int newCapacity = oldCapacity + (oldCapacity >> 1);
            if(newCapacity - minCapacity < 0)
                newCapacity = minCapacity;
            if(newCapacity - MAX_ARRAY_SIZE > 0)
                newCapacity = hugeCapacity(minCapacity);
            list = Arrays.copyOf(list, newCapacity);
        }
    }

    private boolean insert(int index, Collection<? extends E> c) {
        if (c.isEmpty())
            return false;

        Object[] a;
        if (c instanceof CustomVector<?> otherVector)
            synchronized (otherVector) {
                a = Arrays.copyOf(otherVector.list, otherVector.size);
            }
        else {
            a = c.toArray();
        }

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

    private static int hugeCapacity(int minCapacity) {
        if(minCapacity < 0)
            throw new OutOfMemoryError();
        return (minCapacity > MAX_ARRAY_SIZE) ? Integer.MAX_VALUE : MAX_ARRAY_SIZE;
    }

    private class VectorListIterator implements ListIterator<E> {
        private int cursor;
        private int lastRet = -1;

        VectorListIterator(int index) {
            this.cursor = index;
        }

        public void add(E e) {
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

        public boolean hasNext() {
            synchronized (CustomVector.this) {
                return cursor < size;
            }
        }

        public boolean hasPrevious() {
            synchronized (CustomVector.this) {
                return cursor > 0;
            }
        }

        public E next() {
            synchronized (CustomVector.this) {
                if (cursor >= size)
                    throw new java.util.NoSuchElementException();
                lastRet = cursor;
                return (E) list[cursor++];
            }
        }

        public int nextIndex() {
            synchronized (CustomVector.this) {
                return cursor;
            }
        }

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

        public int previousIndex() {
            synchronized (CustomVector.this) {
                return cursor - 1;
            }
        }

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

        public void set(E e) {
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