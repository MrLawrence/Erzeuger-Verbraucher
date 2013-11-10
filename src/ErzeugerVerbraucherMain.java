import java.util.logging.*;
import java.util.ArrayList;
import java.util.HashMap;

public class ErzeugerVerbraucherMain {
	private final static Logger LOG = Logger
			.getLogger(ErzeugerVerbraucherMain.class.getName());

	public static void main(String[] args) {
		Integer limit = 8;
		Integer erzeugerAmount = 5;
		Integer verbraucherAmount = 5;
		
		LimitStack<HashMap<String, Integer>> sharedStack = new LimitStack<HashMap<String, Integer>>(limit);
		LOG.info("Stack mit Limit " + limit + " erzeugt");
		ArrayList<Thread> erzeuger = new ArrayList<Thread>();
		for(int i = 0; i<erzeugerAmount; i++) {
			erzeuger.add(i, new Thread(new Erzeuger(sharedStack)));
		}
		
		ArrayList<Thread> verbraucher = new ArrayList<Thread>();
		for(int i = 0; i<verbraucherAmount; i++) {
			verbraucher.add(i, new Thread(new Verbraucher(sharedStack)));
		}

		for(Thread t: erzeuger) {
			t.start();
		}
		
		for(Thread t: verbraucher) {
			t.start();
		}

		

	}
}
