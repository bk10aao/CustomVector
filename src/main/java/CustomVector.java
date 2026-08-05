import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Objects;
import java.util.Set;

public class CustomVector<E> implements List<E>, Cloneable {

    private static final int MAX_ARRAY_SIZE = Integer.MAX_VALUE - 8;

    private int capacity;

    private static final int MINIMUM_CAPACITY = 32;
    private int size = 0;

    private Object[] list;

    public CustomVector() {
        this.list = new Object[MINIMUM_CAPACITY];
        this.capacity = MINIMUM_CAPACITY;
    }

    public CustomVector(final int initialCapacity) {
        if(initialCapacity < 0)
            throw new IllegalArgumentException();
        this.capacity = Math.max(initialCapacity, MINIMUM_CAPACITY);
        this.list = new Object[capacity];
    }

    public CustomVector(final Collection<? extends E> values) {
        Objects.requireNonNull(values);
        this.capacity = Math.max(MINIMUM_CAPACITY, values.size());
        this.list = new Object[capacity];
        addAll(values);
    }

    public synchronized boolean add(final E item) {
        Objects.requireNonNull(item);
        ensureCapacity(size + 1);
        list[size++] = item;
        return true;
    }
    public synchronized void add(final int index, final E element) {
        Objects.requireNonNull(element);
        checkIndexInnerRange(index);
        ensureCapacity(size + 1);
        System.arraycopy(list, index, list, index + 1, size - index);
        list[index] = element;
        size++;
    }

    public synchronized boolean addAll(final Collection<? extends E> c) {
        Objects.requireNonNull(c);
        return insert(size, c);
    }

    public synchronized boolean addAll(int index, Collection<? extends E> c) {
        return insert(index, c);
    }

    public synchronized void clear() {
        for(int i = 0; i < size; i++)
            list[i] = null;
        size = 0;
        reduce();
    }

    @Override
    public CustomVector<E> clone() {
        try {
            CustomVector<E> clone = (CustomVector<E>) super.clone();
            clone.list = Arrays.copyOf(this.list, this.capacity);
            clone.size = this.size;
            clone.capacity = this.capacity;
            return clone;
        } catch(CloneNotSupportedException e) {
            throw new InternalError(e);
        }
    }

    public synchronized boolean contains(final Object o) {
        Objects.requireNonNull(o);
        return indexOf(o) != -1;
    }

    public synchronized boolean containsAll(Collection<?> c) {
        Objects.requireNonNull(c);
        if (c.isEmpty())
            return true;
        if (c.size() <= 4) {
            for (Object item : c)
                if (!contains(item))
                    return false;
        } else
            for (Object item : (c instanceof Set<?>) ? (Set<?>) c : new HashSet<>(c))
                if (!contains(item))
                    return false;
        return true;
    }

    public synchronized boolean equals(Object o) {
        if(this == o)
            return true;
        if(!(o instanceof List<?> other))
            return false;
        if(size != other.size())
            return false;
        if(o instanceof CustomVector<?> otherVector) {
            for(int i = 0; i < size; i++)
                if(!list[i].equals(otherVector.list[i]))
                    return false;
            return true;
        }
        Iterator<?> otherIterator = other.iterator();
        for(int i = 0; i < size; i++)
            if(!Objects.equals(list[i], otherIterator.next()))
                return false;
        return true;
    }

    public synchronized E get(final int index) {
        checkIndexInRange(index);
        return (E) list[index];
    }

    public synchronized int hashCode() {
        int result = 1;
        for(int i = 0; i < size; i++)
            result = 31 * result + Objects.hashCode(list[i]);
        return result;
    }

    public synchronized int indexOf(final Object o) {
        Objects.requireNonNull(o);
        for(int i = 0; i < size; i++)
            if(list[i].equals(o))
                return i;
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
                if (!hasNext()) {
                    throw new java.util.NoSuchElementException();
                }
                synchronized (CustomVector.this) {
                    return (E) list[currentIndex++];
                }
            }
        };
    }

    public synchronized int lastIndexOf(Object o) {
        Objects.requireNonNull(o);
        for(int i = size - 1; i >= 0; i--)
            if(list[i].equals(o))
                return i;
        return -1;
    }

    public synchronized ListIterator<E> listIterator(int index) {
        checkIndexInnerRange(index);
        return new VectorListIterator(index);
    }

    public synchronized ListIterator<E> listIterator() {
        return listIterator(0);
    }

    public synchronized E remove(final int index) {
        checkIndexInRange(index);
        E o = (E) list[index];
        System.arraycopy(list, index + 1, list, index, size - index - 1);
        list[--size] = null;
        if(size < capacity / 2 && capacity > MINIMUM_CAPACITY)
            reduce();
        return o;
    }

    public synchronized boolean remove(Object o) {
        Objects.requireNonNull(o);
        int index = indexOf(o);
        if(index != -1) {
            remove(index);
            return true;
        }
        return false;
    }

    public synchronized boolean removeAll(Collection<?> c) {
        Objects.requireNonNull(c);
        if(c.isEmpty())
            return false;
        java.util.Set<?> set = (c instanceof java.util.Set) ? (java.util.Set<?>) c : new java.util.HashSet<>(c);
        boolean changed = false;
        int index = 0;
        for(int r = 0; r < size; r++)
            if(!set.contains(list[r]))
                list[index++] = list[r];
            else
                changed = true;
        Arrays.fill(list, index, size, null);
        size = index;
        if(size < capacity / 2 && capacity > MINIMUM_CAPACITY)
            reduce();
        return changed;
    }

    public synchronized boolean retainAll(Collection<?> c) {
        Objects.requireNonNull(c);
        java.util.Set<?> set = (c instanceof java.util.Set) ? (java.util.Set<?>) c : new java.util.HashSet<>(c);
        boolean changed = false;
        int index = 0;
        for(int i = 0; i < size; i++)
            if(set.contains(list[i]))
                list[index++] = list[i];
            else
                changed = true;
        Arrays.fill(list, index, size, null);
        size = index;
        if(size < capacity / 2 && capacity > MINIMUM_CAPACITY)
            reduce();
        return changed;
    }

    public synchronized E set(int index, E element) {
        Objects.requireNonNull(element);
        checkIndexInnerRange(index);
        E replaced = (E) list[index];
        list[index] = element;
        return replaced;
    }

    public synchronized int size() {
        return size;
    }

    public synchronized List<E> subList(int fromIndex, int toIndex) {
        if (fromIndex < 0 || toIndex > size || fromIndex > toIndex)
            throw new IndexOutOfBoundsException();
        return List.of(Arrays.copyOfRange((E[]) list, fromIndex, toIndex));
    }

    public synchronized Object[] toArray() {
        return Arrays.copyOf(list, size, Object[].class);
    }

    @SuppressWarnings("SuspiciousSystemArraycopy")
    public synchronized <T> T[] toArray(T[] a) {
        Objects.requireNonNull(a);
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
            return "CustomVector{size=0, list=[]}";
        StringBuilder sb = new StringBuilder("CustomVector{size=").append(size).append(", list=[");
        for(int i = 0; i < size; i++) {
            sb.append(list[i]);
            if(i < size - 1)
                sb.append(", ");
        }
        return sb.append("]}").toString();
    }

    private void checkIndexInRange(int index) {
        if(index >= size || index < 0)
            throw new IndexOutOfBoundsException();
    }

    private void checkIndexInnerRange(int index) {
        if(index < 0 || index > size)
            throw new IndexOutOfBoundsException();
    }

    private boolean insert(int index, Collection<? extends E> c) {
        if(c.isEmpty())
            return false;
        Object[] a = c.toArray();
        int newSize = a.length;
        for(Object o : a)
            Objects.requireNonNull(o);
        ensureCapacity(size + newSize);
        int numMoved = size - index;
        if(numMoved > 0)
            System.arraycopy(list, index, list, index + newSize, numMoved);
        System.arraycopy(a, 0, list, index, newSize);
        size += newSize;
        return true;
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
            this.capacity = newCapacity;
        }
    }

    private static int hugeCapacity(int minCapacity) {
        if(minCapacity < 0)
            throw new OutOfMemoryError();
        return (minCapacity > MAX_ARRAY_SIZE) ? Integer.MAX_VALUE : MAX_ARRAY_SIZE;
    }

    private void reduce() {
        int newCapacity = Math.max(capacity >> 1, MINIMUM_CAPACITY);
        if(newCapacity < capacity) {
            list = Arrays.copyOf(list, newCapacity);
            capacity = newCapacity;
        }
    }

    private class VectorListIterator implements ListIterator<E> {
        private int cursor;
        private int lastRet = -1;

        VectorListIterator(int index) {
            this.cursor = index;
        }

        public void add(E e) {
            Objects.requireNonNull(e);
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
            Objects.requireNonNull(e);
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
