//  
//	reimplementation of the minicompiler for the sw-praktikum
//	author: Stefan Leitner, Stefan Schauer
//	last change: 29.09.2004


package itec.minicompiler.SymbolTable;

/**
 * The <code>Type</code> implements a type a {@link Symbol} in the {@link SymbolTable} 
 * can have.
 * <p>
 * The <code>Type</code> is defined abstract so that the compatibility of two Types
 * can be defined different for each <code>Type</code>.
 * 
 * @author Stefan Leitner
 * @author Stefan Schauer
 * @version 1.0 (29.09.2004)
 */
public abstract class Type {
	
	/** The size of the <code>Type</code> in bytes */
	int size;

	
	// getter-methods
	/**
	 * Return the size of the actual <code>Type</code>.
	 * 
	 * @return	The size of the <code>Type</code>
	 */
	public int getSize () {
		
		return size;
		
	} // getSize
	
	// setter-methods
	/**
	 * Set the size of the actual <code>Type</code>.
	 * 
	 * @param s	The new size of the <code>Type</code>
	 */
	public void setSize (int s) {
		
		size = s;
		
	} // setSize
	
	// abstract methods
	/**
	 * Check if the <code>Type<code> <code>t</code> is compatible to the
	 * actual <code>Type</code>.
	 * 
	 * @param t	The <code>Type</code> which should be checked
	 * @return	True if the two Types are equal, false otherwise (equality
	 * 			can be defined different for each subtype)
	 */
	public abstract boolean isCompatible (Type t);
	
} // class Type