package erzeugerverbraucher;

import java.util.Stack;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Logger;

public class LimitStack<E> extends Stack<E> {
	private static final long serialVersionUID = 1L;
	private final static Logger LOG = Logger.getLogger(Verbraucher.class
			.getName());

	private ReentrantLock access = new ReentrantLock(true);
	private Semaphore freeSlots;
	private Semaphore filledSlots;
	private Integer limit;

	public LimitStack(Integer limit) {
		super();
		this.limit = limit;
		freeSlots = new Semaphore(this.limit, true);
		filledSlots = new Semaphore(0, true);
	}

	@Override
	public E push(E item) {
		try {
			freeSlots.acquire();

		} catch (InterruptedException e) {
			LOG.info("Interrupted");
		}

		access.lock();
		super.push(item);
		access.unlock();

		filledSlots.release();
		return item;
	}

	@Override
	public E pop() {
		E item = null;
		try {
			filledSlots.acquire();
		} catch (InterruptedException e) {
			LOG.info("Interrupted");
		}
		
		access.lock();
		item = super.pop();
		access.unlock();
		
		freeSlots.release();
		return item;
	}

	public Integer limit() {
		return limit;
	}

	public boolean full() {
		return this.size() == limit;
	}

	@Override
	public String toString() {
		return this.size() + "/" + this.limit();
	}
}
