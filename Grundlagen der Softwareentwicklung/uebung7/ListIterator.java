package util;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * A simple Iterator for the iteration through a {@link MyList}.
 *
 * @author Mathias Menninghaus (mathias.menninghaus@uos.de)
 */
class ListIterator<E> implements Iterator<E> {

   /**
    * The expected number of modifications on the list which belongs to this
    * Iterator.
    */
   private int expectedModCount;

   /**
    * The Entry that has been returned by the last call of the method {@link
    * #next()}
    */
   private MyEntry<E> last;

   /**
    * The next Entry which will be returned by the method {@link #next()}
    */
   private MyEntry<E> next;

   /**
    * The Entry that has been returned before {@link #last} has been
    * returned. Will be set to null after {@link #remove()} calling {@link
    * #remove()} until the next call of {@link #next()}.
    */
   private MyEntry<E> previousLast;

   /**
    * The list which this iterator iterates
    */
   private MyList list;

   /**
    * Create a new ListIterator which start iterating at the front of the given
    * list.
    */
   ListIterator(MyList<E> list, MyEntry<E> begin) {
      this.next = begin.next;
      this.last = begin;
      this.list = list;
      this.expectedModCount = list.getModCount();
      this.previousLast = null;
   }

   @Override
   public boolean hasNext() {
      return next != null;
   }

   /**
    * Returns the next element from the list that belongs to this Iterator.
    *
    * @return the next element
    * @throws ConcurrentModificationException if the list that belongs to
    *                                         this Iterator has been modified
    *                                         after this Iterator has been
    *                                         instantiated.
    * @throws NoSuchElementException          If this Iterator has already
    *                                         reached the last element of the
    *                                         list
    */
   @Override
   public E next() {
      if (expectedModCount != list.getModCount()) {
         throw new ConcurrentModificationException();
      }
      if (next == null) {
         throw new NoSuchElementException();
      }
      previousLast = last;
      last = next;
      next = next.next;

      return last.o;

   }

   /**
    * Removes the element from the list that belongs to this Iterator, that
    * has last been returned by {@link #next()}.
    *
    * @throws ConcurrentModificationException if the list that belongs to
    *                                         this Iterator has been modified
    *                                         after this Iterator has been
    *                                         instantiated.
    * @throws NoSuchElementException          If this Iterator has already
    *                                         reached the last element of the
    *                                         List
    * @throws IllegalStateException           If the {@link #next()} method
    *                                         has not yet been called
    */
   @Override
   public void remove() {
      if (expectedModCount != list.getModCount()) {
         throw new ConcurrentModificationException();
      }
      if (previousLast == null) {
         throw new IllegalStateException();
      }
      expectedModCount++;
      list.increaseModCount();
      previousLast.next = next;
      last = previousLast;
      previousLast = null;
   }

}