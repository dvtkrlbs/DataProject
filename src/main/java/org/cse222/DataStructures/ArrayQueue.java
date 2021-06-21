package org.cse222.DataStructures;

import java.util.*;

/** Implements the Queue interface using a circular array.
 *  @author Koffman & Wolfgang
 * */

public class ArrayQueue < E >
    extends AbstractQueue < E >
    implements Queue < E > {

  // Data Fields
  /** Index of the front of the queue. */
  private int front;

  /** Index of the rear of the queue. */
  private int rear;

  /** Current size of the queue. */
  private int size;

  /** Current capacity of the queue. */
  private int capacity;

  /** Default capacity of the queue. */
  private static final int DEFAULT_CAPACITY = 10;

  /** Array to hold the data. */
  private E[] theData;

  // Constructors
  /** Construct a queue with the default initial capacity.
   */
  public ArrayQueue() {
    this(DEFAULT_CAPACITY);
  }

  /** Construct a queue with the specified initial capacity.
      @param initCapacity The initial capacity
   */
  public ArrayQueue(int initCapacity) {
    capacity = initCapacity;
    theData = (E[])new Object[capacity];
    front = 0;
    rear = capacity - 1;
    size = 0;
  }

  // Public Methods
  /** Inserts an item at the rear of the queue.
      post: item is added to the rear of the queue.
      @param item The element to add
      @return true (always successful)
   */
  public boolean offer(E item) {
    if (size == capacity) {
      reallocate();
    }
    size++;
    rear = (rear + 1) % capacity;
    theData[rear] = item;
    return true;
  }

  /** Returns the item at the front of the queue without removing it.
      @return The item at the front of the queue if successful;
              return null if the queue is empty
   */
  public E peek() {
    if (size == 0)
      return null;
    else
      return theData[front];
  }

  /** Removes the entry at the front of the queue and returns it
      if the queue is not empty.
      post: front references item that was second in the queue.
      @return The item removed if successful or null if not
   */
  public E poll() {
    if (size == 0) {
      return null;
    }
    E result = theData[front];
    front = (front + 1) % capacity;
    size--;
    return result;
  }

  /**** BEGIN EXERCISE ****/
  /** Return the size of the queue
        @return The number of items in the queue
   */
  public int size() {
    return size;
  }

  /** Determine if the queue is empty
      @return true if the queue is empty
   */
  public boolean isEmpty() {
    return size == 0;
  }

  public Iterator < E > iterator() {
    return new Iter();
  }

  /**** END EXERCISE ****/

  // Private Methods
  /** Double the capacity and reallocate the data.
      pre: The array is filled to capacity.
      post: The capacity is doubled and the first half of the
            expanded array is filled with data.
   */
  private void reallocate() {
    int newCapacity = 2 * capacity;
    E[] newData = (E[])new Object[newCapacity];
    int j = front;
    for (int i = 0; i < size; i++) {
      newData[i] = theData[j];
      j = (j + 1) % capacity;
    }
    front = 0;
    rear = size - 1;
    capacity = newCapacity;
    theData = newData;
  }

  /** Inner class to implement the Iterator<E> interface. */
  private class Iter
      implements Iterator < E > {
    // Data Fields
    // Index of next element */
   private int index;

    // Count of elements accessed so far */
   private int count = 0;

    // Methods
    // Constructor
    /** Initializes the Iter object to reference the
        first queue element.
     */
    public Iter() {
      index = front;
    }

    /** Returns true if there are more elements in the queue to access. */
    public boolean hasNext() {
      return count < size;
    }

    /** Returns the next element in the queue.
        pre: index references the next element to access.
        post: index and count are incremented.
        @return The element with subscript index
     */
    public E next() {
      if (!hasNext()) {
        throw new NoSuchElementException();
      }
      E returnValue = theData[index];
      index = (index + 1) % capacity;
      count++;
      return returnValue;
    }

    /** Remove the item accessed by the Iter object � not implemented. */
    public void remove() {
      throw new UnsupportedOperationException();
    }
  }

}
