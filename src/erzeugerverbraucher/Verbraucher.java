package erzeugerverbraucher;


import java.util.Stack;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Logger;
import java.util.HashMap;

public class Verbraucher implements Runnable {
	private final static Logger LOG = Logger.getLogger(Verbraucher.class
			.getName());
	static AtomicInteger nextId = new AtomicInteger();
	private Integer id;

	private Stack<HashMap<String, Integer>> sharedStack;
	private HashMap<String, Integer> data;

	public Verbraucher(Stack<HashMap<String, Integer>> sharedStack) {
		this.id = nextId.incrementAndGet();

		this.sharedStack = sharedStack;
	}

	public void run() {
		LOG.info(this.toString() + " gestartet");
		
		while (true) {
			synchronized (sharedStack) {
				while (sharedStack.empty()) {
					try {
						sharedStack.wait(20);
					} catch (InterruptedException e) {
						Thread.interrupted();
					}
				}
				verbrauche();
				sharedStack.notifyAll();
			}
		}
	}

	private void verbrauche() {
		data = sharedStack.pop();
		LOG.info(this.toString() + ":\t" + data.get("erzeugerId") + 'x'
				+ data.get("writeId") + " entnommen");
	}

	public String toString() {
		return "Verbraucher #" + id;
	}
}
