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
	private Stack<HashMap> sharedStack;
	private Integer writeId = 0;
	HashMap<String, Integer> data = new HashMap();

	public Erzeuger(Stack sharedStack, Integer limit) {
		this.id = nextId.incrementAndGet();
		data.put("erzeugerId", this.id);
		data.put("writeId", 0);
		this.sharedStack = sharedStack;
	}

	public void run() {
		LOG.info("Erzeuger gestartet");
		data.put("writeId", ++writeId);
		erzeuge();
	}
	
	public void erzeuge(){
		sharedStack.push(data);
		LOG.info(data.toString() + " geschrieben");

	}
}
