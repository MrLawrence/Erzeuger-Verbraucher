import java.util.HashMap;
import java.util.List;
import java.util.Stack;
import java.util.logging.Logger;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class Erzeuger implements Runnable {
	private final static Logger LOG = Logger
			.getLogger(Erzeuger.class.getName());
	static AtomicInteger nextId = new AtomicInteger();
	private Integer id;

	private LimitStack<HashMap<String, Integer>> sharedStack;
	private Integer writeId = 0;
	HashMap<String, Integer> data = new HashMap<String, Integer>();

	public Erzeuger(LimitStack<HashMap<String, Integer>> sharedStack) {
		this.id = nextId.incrementAndGet();
		data.put("erzeugerId", this.id);
		data.put("writeId", 0);
		this.sharedStack = sharedStack;
	}

	public void run() {
		LOG.info(this.toString() + " gestartet");

		while (true) {
			synchronized (sharedStack) {
				while (sharedStack.full()) {
					try {
						sharedStack.wait(20);
					} catch (InterruptedException e) {
						Thread.interrupted();
					}
				}
				erzeuge();
				sharedStack.notifyAll();

			}
			try {
				Thread.sleep(20);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public void erzeuge() {
		data.put("writeId", ++writeId);
		sharedStack.push(data);
		LOG.info(this.toString() + ":\t" + data.get("erzeugerId") + 'x'
				+ data.get("writeId") + " geschrieben. Elemente: "
				+ sharedStack.toString());
	}

	public String toString() {
		return "Erzeuger #" + id;
	}
}
