//  
//	reimplementation of the minicompiler for the sw-praktikum
//	author: Stefan Leitner, Stefan Schauer
//	last change: 29.09.2004


package itec.minicompiler.SymbolTable;

/**
 * The <code>TypedSymbol</code> implements a Symbol in the {@link SymbolTable} which
 * has also a {@link Type} in addition to the Symbol-attributes. The 
 * <code>TypedSymbol</code> is especially used to implement predefined and userdefined
 * Types in the SymbolTable
 * 
 * @author Stefan Leitner
 * @author Stefan Schauer
 * @version 1.0 (29.09.2004)
 */
public class TypedSymbol extends Symbol {
	
	/** The Type of the <code>TypedSymbol</code> */
	private Type type;

	
	/**
	 * Initialize the <code>TypedSymbol</code> with a name <code>n</code> and
	 * a <code>Type</code> <code>t</code>.
	 * 
	 * @param n	The name of the <code>TypedSymbol</code>
	 * @param t	The Type of the <code>TypedSymbol</code>
	 */
	public TypedSymbol (String n, Type t) {
		
		super (n);
		type = t;
	
	} // TypeSymbol
	
	// getter-methods
	/**
	 * Return the type of the actual <code>TypedSymbol</code>.
	 * 
	 * @return	The Type of the <code>TypedSymbol</code>
	 */
	public Type getType () {
		
		return type;
		
	} // getType
	
	// setter-methods
	/**
	 * Set the Type of the actual <code>TypedSymbol</code>.
	 * 
	 * @param t	The new Type of the <code>TypedSymbol</code>
	 */
	public void setType (Type t) {
		
		type = t;
		
	} // setType
	
	// super-methods
	/**
	 * Return a clone of the actual <code>TypedSymbol</code>. The cloning 
	 * is restricted on the actual <code>TypedSymbol</code>, what means that 
	 * the pointers of the new <code>TypedSymbol</code> have the same targets 
	 * as the pointers of the actual <code>TypedSymbol</code> (a shallow-clone 
	 * is implemented).
	 * 
	 * @return	An Object equal to the actual <code>TypedSymbol</code>
	 * 
	 * @see java.lang.Object#clone()
	 */
	public Object clone () {
		
		TypedSymbol tsym;
		
		tsym = new TypedSymbol (this.getName (), this.getType());
		tsym.setLevel (this.getLevel ());
		tsym.setUpscope (this.getUpscope());
		tsym.setNext (this.getNext ());
		tsym.setLocal (this.getLocal ());
		
		return tsym;
		
	} // clone
	
	// output-methods
	/**
	 * Generate a String-representation of the <code>TypedSymbol</code>.
	 * 
	 * @return	A String-representation of the <code>TypedSymbol</code>
	 * 
	 * @see java.lang.Object#toString()
	 */
	public String toString () {
		
		if (getUpscope() != null) {
			return ("TypedSymbol:Type" + "\nname: " + getName() + "\ntype: " + getType() + "\nlevel: " + getLevel() + "\nupscope: " + getUpscope().getName() + "\n");
		} // if upscope != null
		else {
			return ("TypedSymbol:Type" + "\nname: " + getName() + "\ntype: " + getType() + "\nlevel: " + getLevel() + "\n");
		} // if upscope == null
		
	} // toString
	
} // class InformationalSymbol