//  
//	reimplementation of the minicompiler for the sw-praktikum
//	author: Stefan Leitner, Stefan Schauer
//	last change: 29.09.2004


package itec.minicompiler.SymbolTable;

/**
 * The <code>Symbol</code> implements a Symbol in the {@link SymbolTable}. 
 * 
 * @author Stefan Leitner
 * @author Stefan Schauer
 * @version 1.0 (29.09.2004)
 */
public class Symbol {
	
	/** The name of the <code>Symbol</code> */
	private String name;
	/** The scope-level in the {@link SymbolTable} */
	private int level;
	/** The scope of the <code>Symbol</code> */
	private Symbol upscope;
	/** The next <code>Symbol</code> in the actual scope */
	private Symbol next;
	/** The local scope of the <code>Symbol</code> */
	private Symbol local;
	
	
	/**
	 * Initialize the <code>Symbol</code> with the name <code>n</code>.
	 * 
	 * @param n	The name of the <code>Symbol</code>
	 */
	public Symbol (String n) {
		
		name = n;
		
	} // Symbol
		
	// getter-Methods
	/**
	 * Return the name of the actual <code>Symbol</code>.
	 * 
	 * @return The name of the <code>Symbol</code>
	 */
	public String getName () {
		
		return name;
		
	} // getName
	
	/**
	 * Return the level of the actual <code>Symbol</code>.
	 * 
	 * @return The level of the <code>Symbol</code>
	 */
	public int getLevel () {
		
		return level;
		
	} // getLevel
	
	/**
	 * Return the upscope of the actual <code>Symbol</code>.
	 * 
	 * @return The upscope of the <code>Symbol</code>
	 */
	public Symbol getUpscope () {
		
		return upscope;
		
	} // getSymbol
		
	/**
	 * Return the next <code>Symbol</code> after the actual <code>Symbol</code>.
	 * 
	 * @return The next <code>Symbol</code>
	 */
	public Symbol getNext () {
		
		return next;
		
	} // getNext
	
	/**
	 * Return the local scope of the actual <code>Symbol</code>.
	 * 
	 * @return The local scope of the <code>Symbol</code>
	 */
	public Symbol getLocal () {
		
		return local;
		
	} // getLocal
	
	// setter-methods
	/**
	 * Set the name of the actual <code>Symbol</code>.
	 * 
	 * @param n	The new name of the <code>Symbol</code>
	 */
	public void setName (String n) {
		
		name = n;
		
	} // setName
		
	/**
	 * Set the level of the actual <code>Symbol</code>.
	 * 
	 * @param l	The new level of the <code>Symbol</code>
	 */
	public void setLevel (int l) {
		
		level = l;
		
	} // setLevel
	
	/**
	 * Set the upscope of the actual <code>Symbol</code>.
	 * 
	 * @param u	The new upscope of the <code>Symbol</code>
	 */
	public void setUpscope (Symbol u) {
		
		upscope = u;
		
	} // setNext
	
	/**
	 * Set the next <code>Symbol</code> after the actual <code>Symbol</code>.
	 * 
	 * @param n	The new next <code>Symbol</code>
	 */
	public void setNext (Symbol n) {
		
		next = n;
		
	} // setNext
	
	/**
	 * Set the local scope of the actual <code>Symbol</code>.
	 * 
	 * @param l	The new local scope of the <code>Symbol</code>
	 */
	public void setLocal (Symbol l) {
		
		local = l;
		
	} // setNext
	
	// super-methods
	/**
	 * Check if an Object <code>s</code> is equal to the actual <code>Symbol</code>.
	 * Equality is implemented as equality of the names of two Symbols.
	 * 
	 * @return	True, if <code>s</code> and the actual <code>Symbol</code> are equal,
	 * 			False otherwise or if <code>s</code> is not an instance of 
	 * 			<code>Symbol</code>
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals (Object s) {
		
		Symbol sym;
		
		
		if (s instanceof Symbol) {
			sym = (Symbol) s;
			return sym.getName ().equals (this.getName ());
		} // if
		
		return false;
		
	} // equals
	
	/**
	 * Return a clone of the actual <code>Symbol</code>. The cloning is restricted
	 * on the actual <code>Symbol</code>, what means that the pointers of the new
	 * <code>Symbol</code> have the same targets as the pointers of the actual
	 * <code>Symbol</code> (a shallow-clone is implemented).
	 * 
	 * @return	An Object equal to the actual <code>Symbol</code>
	 * 
	 * @see java.lang.Object#clone()
	 */
	public Object clone () {
		
		Symbol sym;
		
		
		sym = new Symbol (this.getName ());
		sym.setLevel (this.getLevel ());
		sym.setUpscope (this.getUpscope());
		sym.setNext (this.getNext ());
		sym.setLocal (this.getLocal ());
		
		return sym;
		
	} // clone
	
	// output-methods
	/**
	 * Generate a String-representation of the <code>Symbol</code>.
	 * 
	 * @return	A String-representation of the <code>Symbol</code>
	 * 
	 * @see java.lang.Object#toString()
	 */
	public String toString () {
		
		if (upscope == null) {
			return "Symbol" + "\nname: " + name;
		} // if upscope == null
		else {
			return "Symbol" + "\nname: " + name + "\nupscope: " + upscope.getName();
		} // if upscope != null
		
	} // toString
	
} // class Symbol