// Title: Heap Priority Queue Class
// Description: The HeapPriorityQueue class is a data structure that stores a priority queue
// (a queue where elements have associated priorities) using a heap 
// (a tree-like data structure where the highest priority element is always stored at the root). 

import java.util.Iterator;
import java.util.NoSuchElementException;

// class that implements a priority queue using a heap data structure
public class HeapPriorityQueue<T> implements Iterable<T> {
	// array to store the elements in the heap
	private Customer[] heap;
	// number of elements in the heap
	private int size;

	// constructor that creates a heap with the given capacity
	public HeapPriorityQueue(int capacity) {
		heap = new Customer[capacity];
	}

	// method to check if the heap is empty
	public boolean isEmpty() {
		return size == 0;
	}

	// method to insert a new element into the heap
	public void insert(Customer value) {
		if (size == heap.length) {
			throw new IllegalStateException("Heap is full");
		}

		heap[size] = value;
		fixUp(size);
		size++;
	}

	// method to remove and return the highest priority element from the heap
	public Customer poll() {
		if (isEmpty()) {
			throw new IllegalStateException("Heap is empty");
		}

		Customer value = heap[0];
		heap[0] = heap[size - 1];
		size--;
		fixDown(0);
		return value;
	}

	// method to remove and return the first element from the heap whose arrival
	// time is less than or equal to the given time
	// This method returns a Customer from the heap that has an arrival time less
	// than or equal to the given time.
	// If no such customer is found, it returns null.
	public Customer pollByTime(int time) {
		// If the heap is empty, throw an exception
		if (isEmpty()) {
			throw new IllegalStateException("Heap is empty");
		}

		// Iterate through the heap and find a customer with an arrival time less than
		// or equal to the given time
		for (int i = 0; i < heap.length; i++) {
			if (heap[i].arrivalTime <= time) {
				// If a customer is found, remove it from the heap and return it
				Customer value = heap[i];
				heap[i] = heap[size - 1];
				size--;
				fixDown(i);
				return value;
			}
		}
		// If no such customer is found, return null
		return null;
	}

	// This method does this by comparing the element at the given index with its
	// parent and
	// swapping
	// the element with the parent if necessary. It then repeats this process until
	// the heap property is restored.
	private void fixUp(int index) {
		// Find the parent of the element at the given index
		int parent = (index - 1) / 2;

		// While the element at the given index is not the root of the heap and is
		// smaller than its parent
		while (index > 0 && heap[index].compareTo(heap[parent]) < 0) {
			// Swap the element at the given index with its parent
			swap(index, parent);

			// Update the index and parent variables to continue fixing up
			index = parent;
			parent = (index - 1) / 2;
		}
	}

	// This method does this by comparing the element at the given index with its
	// children
	// and swapping
	// the element with the smallest child if necessary. It then repeats this
	// process until the heap property is restored.
	private void fixDown(int index) {
		// Find the left child of the element at the given index
		int child = index * 2 + 1;

		// While the element at the given index has at least one child
		while (child < size) {
			// Find the right child of the element, if it exists
			int right = child + 1;

			// If the right child exists and is smaller than the left child, set child to
			// the right child
			if (right < size && heap[right].compareTo(heap[child]) < 0) {
				child = right;
			}

			// If the element at the given index is larger than its smallest child, swap
			// them and continue fixing down
			if (heap[index].compareTo(heap[child]) > 0) {
				swap(index, child);
				index = child;
				child = index * 2 + 1;
			}
			// If the element at the given index is smaller than or equal to its smallest
			// child, the heap property is restored
			else {
				break;
			}
		}
	}

	// This method swaps the elements at the given indices in the heap array.
	private void swap(int i, int j) {
		// Store the element at index i in a temporary variable
		Customer temp = heap[i];

		// Replace the element at index i with the element at index j
		heap[i] = heap[j];

		// Replace the element at index j with the element stored in the temporary
		// variable
		heap[j] = temp;
	}

	// This method returns an iterator that can be used to iterate over the elements
	// in the heap.
	@Override
	public Iterator<T> iterator() {
		return new Iterator<T>() {
			// The index of the next element to be returned by the iterator
			private int index = 0;

			// This method returns true if there are more elements to be returned by the
			// iterator, and false otherwise.
			@Override
			public boolean hasNext() {
				return index < heap.length;
			}

			// This method returns the next element in the iteration and increments the
			// index.
			// If there are no more elements to be returned, it throws a
			// NoSuchElementException.
			@Override
			public T next() {
				if (!hasNext()) {
					throw new NoSuchElementException();
				}
				// The element at the current index is returned and the index is incremented.
				// The element is cast to the generic type T and the heap array is cast to an
				// array of type T.
				return (T) (Customer) heap[index++];
			}
		};
	}

	// This method returns the heap array.
	public Customer[] getHeap() {
		return heap;
	}

	// This method sets the heap array to the given array.
	public void setHeap(Customer[] heap) {
		this.heap = heap;
	}

	// This method returns the number of elements in the heap.
	public int size() {
		return size;
	}

	// This method sets the number of elements in the heap to the given size.
	public void setSize(int size) {
		this.size = size;
	}

}
