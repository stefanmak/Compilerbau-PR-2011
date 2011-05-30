package yapl.impl;

import java.util.HashMap;
import yapl.interfaces.Symbol;

public class Scope extends HashMap<String,Symbol> {

	/** Parent Symbol of the current scope*/
	private Symbol parent;
	/** isGlobal */
	private boolean isGlobal;
	
	/** Getter and Setter */

	public boolean isGlobal() {
		return isGlobal;
	}

	public void setGlobal(boolean isGlobal) {
		this.isGlobal = isGlobal;
	}

	public Symbol getParent() {
		return parent;
	}

	public void setParent(Symbol parent) {
		this.parent = parent;
	}	
	
}
