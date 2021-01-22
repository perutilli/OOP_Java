package hydraulic;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * Main class that act as a container of the elements for
 * the simulation of an hydraulics system 
 * 
 */
public class HSystem {
	
	private List<Element> elements = new ArrayList<>();
	
	/**
	 * Adds a new element to the system
	 * @param elem
	 */
	public void addElement(Element elem){
		this.elements.add(elem);
	}
	
	/**
	 * returns the element added so far to the system.
	 * If no element is present in the system an empty array (length==0) is returned.
	 * 
	 * @return an array of the elements added to the hydraulic system
	 */
	public Element[] getElements(){
		return this.elements.toArray(new Element[0]);
	}
	
	/**
	 * Prints the layout of the system starting at each Source
	 */
	public String layout(){
		// TODO: to be implemented
		return null;
	}
	
	/**
	 * starts the simulation of the system
	 */
	public void simulate(SimulationObserver observer){
		for (Element e: this.elements) {
			if (e instanceof Source) {
				observer.notifyFlow("source", e.getName(), observer.NO_FLOW, ((Source) e).getFlow());
				Stack<Element> next = new Stack<>();
				next.push(e.getOutput());
				while(!next.empty()) {
					if (next.pop() instanceof Split) {
						
					}
					
					//(e.getOutput() instanceof Sink);
				}
			}
		}
	}

}
