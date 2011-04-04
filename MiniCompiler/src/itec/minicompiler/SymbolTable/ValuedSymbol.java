//  
//	reimplementation of the minicompiler for the sw-praktikum
//	author: Stefan Leitner, Stefan Schauer
//	last change: 29.09.2004


package itec.minicompiler.SymbolTable;

/**
 * The <code>ValuedSymbol</code> implements a Symbol in the {@link SymbolTable} which
 * has also a value in addition to the TypedSymbol-attributes. The 
 * <code>ValuedSymbol</code> is especially used to implement constants in
 * the SymbolTable.
 * 
 * @author Stefan Leitner
 * @author Stefan Schauer
 * @version 1.0 (29.09.2004)
 */
public class ValuedSymbol extends TypedSymbol {
	
	/** The value of the <code>ValuedSymbol</code> */
	private int value;
	
	
	/**
	 * Initialize the <code>ValuedSymbol</code> with a name <code>n</code>, 
	 * a <code>Type</code> <code>t</code> and a value <code>v</code>.
	 * 
	 * @param n	The name of the <code>ValuedSymbol</code>
	 * @param t The Type of the <code>ValuedSymbol</code>
	 * @param v	The value of the <code>ValuedSymbol</code>
	 */
	public ValuedSymbol (String n, Type t, int v) {
		
		super (n, t);
		value = v;
		
	} // ValuedSymbol
	
	// getter-methods
	/**
	 * Return the value of the actual <code>ValuedSymbol</code>.
	 * 
	 * @return	The value of the <code>ValuedSymbol</code>
	 */
	public int getValue () {
		
		return value;
		
	} // getValue
		
	// setter-methods
	/**
	 * Set the value of the actual <code>ValuedSymbol</code>.
	 * 
	 * @param v	The new value of the <code>ValuedSymbol</code>
	 */
	public void setValue (int v) {
		
		value = v;
		
	} // setValue
	
	// super-methods
	/**
	 * Return a clone of the actual <code>ValuedSymbol</code>. The cloning 
	 * is restricted on the actual <code>ValuedSymbol</code>, what means that 
	 * the pointers of the new <code>ValuedSymbol</code> have the same targets 
	 * as the pointers of the actual <code>ValuedSymbol</code> (a shallow-clone 
	 * is implemented).
	 * 
	 * @return	An Object equal to the actual <code>ValuedSymbol</code>
	 * 
	 * @see java.lang.Object#clone()
	 */
	public Object clone () {
		
		ValuedSymbol vsym;
		
		
		vsym = new ValuedSymbol (this.getName (), this.getType (), this.getValue ());
		vsym.setLevel (this.getLevel ());
		vsym.setUpscope (this.getUpscope());
		vsym.setNext (this.getNext ());
		vsym.setLocal (this.getLocal ());
		
		return vsym;
		
	} // clone
		
	// output-methods
	/**
	 * Generate a String-representation of the <code>ValuedSymbol</code>.
	 * 
	 * @return	A String-representation of the <code>ValuedSymbol</code>
	 * 
	 * @see java.lang.Object#toString()
	 */
	public String toString () {
		
		if (getUpscope() != null) {
			return ("ValuedSymbol\nname: " + getName() + "\nvalue: " + value + "\ntype: " + getType() + "\nlevel: " + getLevel() + "\nupscope: " + getUpscope().getName() + "\n");
		} // if upscope != null
		else {
			return ("ValuedSymbol\nname: " + getName() + "\nvalue: " + value + "\ntype: " + getType() + "\nlevel: " + getLevel() + "\n");
		} // if upscope == null
		
	} // toString
	
} // class ValuedSymbol