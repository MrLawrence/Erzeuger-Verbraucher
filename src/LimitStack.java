import java.util.Stack;
import java.util.logging.Logger;

public class LimitStack<E> extends Stack<E> {
	private final static Logger LOG = Logger.getLogger(Verbraucher.class
			.getName());

	private Integer limit;

	public LimitStack(Integer limit) {
		super();
		this.limit = limit;
	}

	@Override
	public E push(E item) {
		if (super.size() <= limit) {
			super.push(item);
		} else {
			LOG.severe("Stack voll");
		}
		return item;
	}

	@Override
	public E pop() {
		if (!this.empty()) {
			return super.pop();
		} else {
			LOG.severe("Stack leer");
			return null;
		}

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
