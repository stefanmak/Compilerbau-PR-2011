//  
//	reimplementation of the minicompiler for the sw-praktikum
//	author: Stefan Leitner, Stefan Schauer
//	last change: 29.09.2004


package itec.minicompiler.SymbolTable;

/**
 * The <code>StructuralSymbol</code> implements a Symbol in the {@link SymbolTable} 
 * which is used to build the structure of the {@link SymbolTable}.
 * <p>
 * A <code>StructuralSymbol</code> can be of one of three types:
 * <blockquote><menu>
 * <li>UNIVERSE	(The top-scope and the root of the {@link SymbolTable})
 * <li>MODULE	(The top-scope of all user-specific Symbols)
 * <li>HEAD		(The first Symbol in the local scope of any Symbol)
 * </menu></blockquote>
 * 
 * @author Stefan Leitner
 * @author Stefan Schauer
 * @version 1.0 (29.09.2004)
 */
public class StructuralSymbol extends Symbol {
	
	private static final int UNIVERSE = 101;
	private static final int MODULE = 102;
	private static final int HEAD = 103;
	 
	/** The kind of the <code>StructuralSymbol</code> */
	private int kind;
	 
	 
	/**
	 * Initialize the <code>StructuralSymbol</code> with a name <code>n</code>
	 * and set the kind to 0 (i.e. the <code>StructuralSymbol</code> is
	 * undefined).
	 * 
	 * @param n	The name of the <code>StructuralSymbol</code>
	 */
	public StructuralSymbol (String n) {
	 	
	 	super (n);
	 	kind = 0;
	 	
	} // StructuralSymbol
	 
	// getter-methods
	/**
	 * Check if the actual <code>StructuralSymbol</code> is a UNIVERSE-Symbol.
	 * 
	 * @return	True if the actual <code>StructuralSymbol</code> is a UNIVERSE-Symbol,
	 * 			False otherwise
	 */
	public boolean isUniverse () {
	 	
		return (kind == UNIVERSE);
	 	
	} // isUniverse
	 
	/**
	 * Check if the actual <code>StructuralSymbol</code> is a MODULE-Symbol.
	 * 
	 * @return	True if the actual <code>StructuralSymbol</code> is a MODULE-Symbol,
	 * 			False otherwise
	 */
	public boolean isModule () {
	 	
		return (kind == MODULE);
	 	
	} // isModule
	 
	/**
	 * Check if the actual <code>StructuralSymbol</code> is a HEAD-Symbol.
	 * 
	 * @return	True if the actual <code>StructuralSymbol</code> is a HEAD-Symbol,
	 * 			False otherwise
	 */
	public boolean isHead () {
	 	
	 	return (kind == HEAD);
	 	
	} // isHead
	 
	// setter-methods
	/**
	 * Make the actual <code>StructuralSymbol</code> a UNIVERSE-Symbol (i.e. set the 
	 * kind to UNIVERSE).
	 */
	public void setUniverse () {
	 	
	 	kind = UNIVERSE;
	 	
	} // setUniverse
	 
	/**
	 * Make the actual <code>StructuralSymbol</code> a MODULE-Symbol (i.e. set the 
	 * kind to MODULE).
	 */
	public void setModule () {
	 	
	 	kind = MODULE;
	 	
	} // setModule
	 
	/**
	 * Make the actual <code>StructuralSymbol</code> a HEAD-Symbol (i.e. set the 
	 * kind to HEAD).
	 */
	public void setHead () {
	 	
	 	kind = HEAD;
	 	
	} // setUniverse
	 
	// super-methods
	/**
	 * Return a clone of the actual <code>StructuralSymbol</code>. The cloning 
	 * is restricted on the actual <code>StructuralSymbol</code>, what means that 
	 * the pointers of the new <code>StructuralSymbol</code> have the same targets 
	 * as the pointers of the actual <code>StructuralSymbol</code> (a shallow-clone 
	 * is implemented).
	 * 
	 * @return	An Object equal to the actual <code>StructuralSymbol</code>
	 * 
	 * @see java.lang.Object#clone()
	 */
	public Object clone () {
	 	
	 	StructuralSymbol stsym;
	 	
	 	
	 	stsym = new StructuralSymbol (this.getName ());
	 	stsym.setLevel (this.getLevel ());
		stsym.setUpscope (this.getUpscope());
		stsym.setNext (this.getNext ());
		stsym.setLocal (this.getLocal ());
	 	if (this.isUniverse ()) {
	 		stsym.setUniverse ();
	 	} // if this.isUniverse ()
	 	else if (this.isModule ()) {
	 		stsym.setModule ();
	 	} // if this.isModule ()
	 	else if (this.isHead ()) {
	 		stsym.setHead ();
	 	} // if this.isHead ()
	 	
	 	return stsym;
	 	
	} // clone
	 
	 // output-methods
	/**
	 * Generate a String-representation of the <code>StructuralSymbol</code>.
	 * 
	 * @return	A String-representation of the <code>StructuralSymbol</code>
	 * 
	 * @see java.lang.Object#toString()
	 */
	public String toString () {
		
		String header;
		
		switch (kind) {
			case UNIVERSE:	header = "StructuralSymbol: UNIVERSE"; break;
			case MODULE: 	header = "StructuralSymbol: MODULE"; break;
			case HEAD: 		header = "StructuralSymbol: HEAD"; break;
			default: 		header = "StructuralSymbol"; break;
		} // switch
		
		if (getUpscope() != null) {
			return (header + "\nname: " + getName() + "\nlevel: " + getLevel() + "\nupscope: " + getUpscope().getName() + "\n");
		} // if upscope != null
		else {
			return (header + "\nname: " + getName() + "\nlevel: " + getLevel() + "\n");
		} // if upscope == null
		
	} // toString
	
} // class StructuralSymbol