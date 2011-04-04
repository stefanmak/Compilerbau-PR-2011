//  
//	reimplementation of the minicompiler for the sw-praktikum
//	author: Stefan Leitner, Stefan Schauer
//	last change: 29.09.2004


package itec.minicompiler.SymbolTable;

/**
 * The <code>ArrayType</code> implements an ARRAY-Type in the {@link SymbolTable}. 
 * 
 * @author Stefan Leitner
 * @author Stefan Schauer
 * @version 1.0 (29.09.2004)
 */
public class ArrayType extends Type {
	
	/** The basetype of the <code>ArrayType</code> */
	Type baseType;
	/** The number of elements in the <code>ArrayType</code> */
	int length;
	
	
	/**
	 * Initialize the <code>ArrayType</code> with a <code>Type</code> <code>b</code>
	 * and a length <code>l</code>. Set the size to <code>-1</code> (i.e. the size 
	 * is undefined).
	 * 
	 * @param b	The basetype of the <code>ArrayType</code>
	 * @param l	The length of the <code>ArrayType</code>
	 */
	public ArrayType (Type b, int l) {
		
		baseType = b;
		length = l;
		setSize (-1);
		
	} // ArrayType
	
	// getter-methods
	/**
	 * Return the basetype of the actual <code>ArrayType</code>.
	 * 
	 * @return	The basetype of the <code>ArrayType</code>
	 */
	public Type getBaseType () {
		
		return baseType;
		
	} // getBaseType
	
	/**
	 * Return the length of the actual <code>ArrayType</code>.
	 * 
	 * @return	The length of the <code>ArrayType</code>
	 */
	public int getLength () {
		
		return length;
		
	} // getLength
	
	// setter-methods
	/**
	 * Set the basetype of the actual <code>ArrayType</code>.
	 * 
	 * @param t	The new basetype of the <code>ArrayType</code>
	 */
	public void setBaseType (Type t) {
		
		baseType = t;
		
	} // setBaseType
	
	/**
	 * Set the length of the actual <code>ArrayType</code>.
	 * 
	 * @param l	The new length of the <code>ArrayType</code>
	 */
	public void setLength (int l) {
		
		length = l;
		
	} // setLength
	
	// implementation of abstract methods
	/**
	 * Check if the <code>Type<code> <code>t</code> is compatible to the
	 * actual <code>ArrayType</code>. Two ArrayTypes are compatibel, if
	 * their basetypes are compatible and they have the equal length.
	 * 
	 * @param t	The <code>Type</code> which should be checked
	 * @return	True if the two Types are equal, false otherwise
	 */
	public boolean isCompatible (Type t) {		
		
		if (t instanceof ArrayType) {
			ArrayType at;
			at = (ArrayType) t;
			if (getBaseType ().isCompatible (at.getBaseType ())) {
				if (getLength () == at.getLength ()) {
					return true;
				} // getLength () == at.getLength ()
			} // if getBaseType ().isCompatible (at.getBaseType ())
		} // if t instanceof BasicType
		
		return false;
		
	} // isCompatible
	
	// output-methods
	/**
	 * Generate a String-representation of the <code>ArrayType</code>.
	 * 
	 * @return	A String-representation of the <code>ArrayType</code>
	 * 
	 * @see java.lang.Object#toString()
	 */
	public String toString () {
		
		return ("\tArrayType" + "\tbaseType: " + baseType + "\tlength: " + length + "\tsize: " + getSize());

	} // toString

} // RecordType