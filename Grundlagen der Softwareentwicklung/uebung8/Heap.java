package util;

import java.util.Arrays;
import java.util.Comparator;
import java.util.NoSuchElementException;

/**
 * A Heap is a binary tree with h levels, which is fully loaded on the levels
 * 0,1...h-2; every child on the last level h-1 is set from left to right. The
 * elements are either ordered by a {@link Comparator} or by there
 * natural ordering, given by their implementation of
 * {@link java.util.Comparable}. In order to avoid a
 * <code>ClassCastException</code> the user must only insert
 * {@link java.util.Comparable} Elements or define a
 * {@link Comparator}. Given this order, every element in a parent
 * node is smaller than the elements in both child nodes.
 * <p>
 * The first element of a Heap is always the smallest element. It can be deleted
 * by {@link #deleteFirst()} or received by {@link #getFirst()}. A new element
 * can be inserted by {@link #insert(E)}.
 * <p>
 * A Heap is unbound an will automatically grow when inserting new elements.
 * 
 * @author Mathias Menninghaus (mathias.menninghaus@uos.de)
 * 
 * @param <E>
 */
public class Heap<E> implements java.io.Serializable {

    private static final int DEFAULT_INITIAL_CAPACITY = 11;

    /*
     * The heap as Object[], it is not possible to instantiate a generic array.
     * Has to be transient, so null values won't be serialized
     */
    transient private Object[] heap;

    private int size = 0;

    private final Comparator<? super E> comparator;

    /**
     * Creates a new empty heap. <code>E</code> must implement
     * {@link java.util.Comparable} in order to avoid a
     * <code>ClassCastException</code>.
     */
    public Heap() {
        this(null);
    }

    /**
     * Creates a new empty Heap. The elements inserted will be compared using the
     * given {@link Comparator}. If <code>comparator</code> is
     * <code>null</code> <code>E</code> must implement
     * {@link java.util.Comparable} in order to avoid a
     * <code>ClassCastException</code>.
     * 
     * @param comparator
     *           a Comparator to compare the inserted elements
     */
    public Heap(Comparator<? super E> comparator) {
        this.heap = new Object[DEFAULT_INITIAL_CAPACITY];
        this.comparator = comparator;
    }

    /**
     * Increases the capacity of the array.
     * 
     * @param minCapacity
     *           the desired minimum capacity
     */
    private void grow(int minCapacity) {
        if (minCapacity < 0) // overflow
            throw new OutOfMemoryError();
        int oldCapacity = heap.length;
        // Double size if small; else grow by 50%
        int newCapacity = ((oldCapacity < 64) ? ((oldCapacity + 1) * 2)
                : ((oldCapacity / 2) * 3));
        if (newCapacity < 0) // overflow
            newCapacity = Integer.MAX_VALUE;
        if (newCapacity < minCapacity)
            newCapacity = minCapacity;
        heap = Arrays.copyOf(heap, newCapacity);
    }

    /**
     * Inserts <code>e</code> into this Heap. May throw a
     * <code>ClassCastException</code> if this Heap has been initialized without
     * a <code>Comparator</code> and E does not implement
     * {@link java.util.Comparable}. Throws a <code>NullPointerException</code>
     * if <code>e</code> is <code>null</code>.
     * 
     * @param e
     *           element to be inserted
     * @throws NullPointerException
     *            if <code>e</code> is <code>null</code>
     * @throws ClassCastException
     *            if no this Heap was not initialized with a
     *            <code>Comparator</code> an <code>E</code> does not implement
     *            <code>Comparable</code>
     */
    public void insert(E e) {
        if (e == null) {
            throw new NullPointerException();
        }
        int i = size;
        if (i >= heap.length) {
            grow(i + 1);
        }
        size = i + 1;
        if (i == 0) {
            heap[0] = e;
        } else {
            siftUp(i, e);
        }
    }

    /**
     * Returns the first, or smallest element in this Heap.
     * 
     * @return the first element in this <code>Heap</code> or <code>null</code>
     * 
     * @throw NoSuchElementException if this heap is empty
     */
    public E getFirst() {
        if (empty()) {
            throw new NoSuchElementException();
        }
        return get(0);
    }

    /**
     * Returns the size of this Heap.
     * 
     * @return number of elements hold by this Heap
     */
    public int size() {
        return size;
    }

    /**
     * Returns if this Heap is empty or not
     * 
     * @return <code>true</code> if this Heap is empty
     */
    public boolean empty() {
        return size == 0;
    }

    /**
     * Deletes the first, or smallest element in this Heap and returns. Puts the
     * last element of this Heap to the root and reorganizes this Heap such that
     * the Heap axioms are fulfilled. Throws Exception if this Heap is empty.
     * 
     * @return the first element in this Heap, which has been deleted
     * 
     * @throw NoSuchElementException if this heap is empty
     */
    public E deleteFirst() {
        if (empty()) {
            throw new NoSuchElementException();
        }
        int s = --size;
        E result = get(0);
        E x = get(s);
        heap[s] = null;
        if (s != 0) {
            siftDown(0, x);
        }
        return result;
    }

    /**
     * Inserts <code>toInsert</code> at position <code>pos</code> into this Heap
     * and sifts it up such that the Heap axioms are fulfilled. A sift operation
     * determines whether the parent node of the node at which
     * <code>toInsert</code> should be inserted is lesser than
     * <code>toInsert</code>. If not, child and parent will be swaped.
     * 
     * @param pos
     *           position where the new element should be inserted.
     * @param toInsert
     *           element to be inserted
     * @throws ClassCastException
     *            if no <code>Comparator</code> has been defined with
     *            initialization of this Heap and <code>E</code> does not
     *            implement <code>Comparable</code>
     */
    private void siftUp(int pos, final E toInsert) {
        boolean validHeap = false;
        while (pos > 0 && !validHeap) {
            int parent = (pos - 1) >>> 1;
            E e = get(parent);
            /*
             * check actual node is greater than its parent node
             */
            if (compare(toInsert, e) >= 0) {
                /*
                 * if so it is a valid heap
                 */
                validHeap = true;
            } else {
                /*
                 * else swap parent and node and move on.
                 */
                heap[pos] = e;
                pos = parent;
            }
        }
        /*
         * if no valid parent has been found, the actual element will become the
         * new root
         */
        heap[pos] = toInsert;
    }

    /**
     * Inserts <code>toInsert</code> at position <code>pos</code> into this Heap
     * and sifts it down such that the Heap axioms are fulfilled. A sift
     * operation determines whether the child nodes of the node at which
     * <code>toInsert</code> should be inserted is greater than
     * <code>toInsert</code>. If not, child and parent will be swaped.
     * 
     * @param pos
     *           position where the new element should be inserted.
     * @param toInsert
     *           element to be inserted
     * @throws ClassCastException
     *            if no <code>Comparator</code> has been defined with
     *            initialization of this Heap and <code>E</code> does not
     *            implement <code>Comparable</code>
     */
    private void siftDown(int pos, final E toInsert) {
        boolean validHeap = false;
        /*
         * loop while a non-leaf
         */
        int half = size >>> 1;
        while (pos < half && !validHeap) {
            /*
             * asssume that left child is the smaller one
             */
            int left = (pos << 1) + 1;
            E smallerChild = get(left);
            /*
             * right child
             */
            int right = left + 1;
            /*
             * get the smaller child
             */
            if (right < size && compare(smallerChild, get(right)) > 0) {
                smallerChild = get(right);
                left = right;
            }
            /*
             * if there is no child, that is smaller than x...
             */
            if (compare(toInsert, smallerChild) <= 0) {
                /*
                 * the parent may remain at the right position -> the heap is valid
                 */
                validHeap = true;
            } else {
                /*
                 * or swap with this child
                 */
                heap[pos] = smallerChild;
                pos = left;
            }
        }
        /*
         * Either the heap has become valid, such that the parent is already at
         * the right postion.
         * 
         * Or the child level has been reached, in this case, put the parent as a
         * new child to the position of the child it has been swaped with.
         */
        heap[pos] = toInsert;
    }

    @SuppressWarnings("unchecked")
    private E get(int pos) {
        return (E) heap[pos];
    }

    @SuppressWarnings("unchecked")
    private int compare(E one, E another) {
        if (comparator == null) {
            return ((Comparable<? super E>) one).compareTo(another);
        } else {
            return comparator.compare(one, another);
        }
    }

    /**
     * Serialisiert den Heap, indem nur die Elemente des Heaps serialisiert 
     * werden, welche ungleich null sind
     * @param oos Der Stream, der zum schreiben in die Datei benutzt wird
     */
    private void writeObject(java.io.ObjectOutputStream oos) throws 
        ClassNotFoundException, java.io.IOException{
        oos.defaultWriteObject();
        for(int i = 0; i < size; i++)
            oos.writeObject(heap[i]); 
    }

    /**
     * Deserialisiert einen Heap aus einer Datei
     * @param ois Der Stream, der zum lesen aus der Datei benutzt wird
     */
    private void readObject(java.io.ObjectInputStream ois) throws
        java.io.IOException, ClassNotFoundException{
        ois.defaultReadObject();
        heap = new Object[size];
        for(int i = 0; i < size; i++)
            heap[i] = ois.readObject();
    }
}
