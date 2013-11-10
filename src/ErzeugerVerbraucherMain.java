import java.util.Stack;
import java.util.logging.*;
import java.util.HashMap;


public class ErzeugerVerbraucherMain {
	private final static Logger LOG = Logger
			.getLogger(ErzeugerVerbraucherMain.class.getName());

	public static void main(String[] args) {
		Stack<HashMap> sharedStack = new Stack<HashMap>();
		final Integer LIMIT = 8;
		LOG.info("Fixed sized list created");
		
		Thread erzeuger = new Thread(new Erzeuger(sharedStack, LIMIT));
		Thread verbraucher = new Thread(new Verbraucher(sharedStack, LIMIT));
		
		erzeuger.start();
		verbraucher.start();
		
	}
}
