package erzeugerverbraucher;

import java.util.Stack;
import java.util.concurrent.Semaphore;
import java.util.logging.Logger;

public class LimitStack<E> extends Stack<E> {
	private static final long serialVersionUID = 1L;
	private final static Logger LOG = Logger.getLogger(Verbraucher.class
			.getName());

	private Semaphore mutex = new Semaphore(1, true);
	private Integer limit;

	public LimitStack(Integer limit) {
		super();
		this.limit = limit;
	}

	@Override
	public E push(E item) {
		try {
			mutex.acquire();
			if (super.size() < limit) {
				super.push(item);
			} else {
				LOG.info("Can't push: Stack full");
			}
			mutex.release();
		} catch (InterruptedException e) {
			LOG.severe("Interrupted");
		}
		return item;
	}

	@Override
	public E pop() {
		E item = null;
		try {
			mutex.acquire();
			if (!this.empty()) {
				item = super.pop();
			} else {
				LOG.info("Can't pop: Stack empty");
			}
			mutex.release();
		} catch (InterruptedException e) {
			LOG.severe("Interrupted");
		}
		return item;
	}

	public Integer limit() {
		return limit;
	}

	public boolean full() {
		return size() == limit;
	}

	@Override
	public String toString() {
		return this.size() + "/" + this.limit();
	}
}
