import java.util.List;
import java.util.Stack;
import java.util.logging.Logger;
import java.util.HashMap;

public class Verbraucher implements Runnable {
	private final static Logger LOG = Logger.getLogger(Verbraucher.class
			.getName());
	private Stack<HashMap> sharedStack;
	private HashMap<String, Integer> data;
	private final Integer LIMIT;

	public Verbraucher(Stack<HashMap> sharedStack, Integer limit) {
		this.sharedStack = sharedStack;
		this.LIMIT = limit;
	}

	public void run() {
		LOG.info("Verbraucher gestartet");
		int position = 0;

		while (true) {
			verbrauche();
		}
	}
	
	public void verbrauche(){
		data = sharedStack.pop();
		LOG.info(data.toString() + " ausgelesen");

	}
}
