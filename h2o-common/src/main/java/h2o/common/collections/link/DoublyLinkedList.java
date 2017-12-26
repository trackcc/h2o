package h2o.common.collections.link;

import h2o.common.concurrent.Locks;

import java.util.concurrent.locks.Lock;

public class DoublyLinkedList<T> {

	private volatile int size;

	private final Lock lock = Locks.newLock();

	private volatile DNode<T> head;

	private volatile DNode<T> tail;

	public DNode<T> getHead() {
		return head;
	}

	public DNode<T> getTail() {
		return tail;
	}

	public void addToHead(DNode<T> node) {
		
		node.prev = null;
		node.next = null;

		lock.lock();
		try {
			if (null == head) {
				head = node;
				tail = node;
			} else {
				head.prev = node;
				node.next = head;
				head = node;
			}
			size++;
		} finally {
			lock.unlock();
		}
	}

	public void addToTail(DNode<T> node) {
		
		node.prev = null;
		node.next = null;
		
		lock.lock();
		try {
			if (null == head) {
				head = node;
				tail = node;
			} else {
				tail.next = node;
				node.prev = tail;
				tail = node;
			}
			size++;
		} finally {
			lock.unlock();
		}
	}

	public T remove(DNode<T> node) {
		
		lock.lock();
		try {

			DNode<T> p = node.prev;
			DNode<T> n = node.next;
			node.prev = null;
			node.next = null;
			
			if (p == null) {
				head = n;
			} else {
				p.next = n;
			}
			if (n == null) {
				tail = p;
			} else {
				n.prev = p;
			}			

			size--;
			
			return node.data;
			
		} finally {
			lock.unlock();
		}
	}

	public int size() {
		return size;
	}

	public boolean isEmpty() {
		if (null == head) {
			return true;
		}
		return false;
	}
}