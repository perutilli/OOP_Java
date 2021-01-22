package hydraulic;

/**
 * Represents the sink, i.e. the terminal element of a system
 *
 */
public class Sink extends Element {
	
	private boolean open = false;

	/**
	 * Constructor
	 * @param name
	 */
	public Sink(String name) {
		super(name);
		//TODO: complete
	}
	
	public void setOpen(boolean open) {
		this.open = open;
	}
	
	public boolean isOpen() {
		return this.open;
	}
	
}
