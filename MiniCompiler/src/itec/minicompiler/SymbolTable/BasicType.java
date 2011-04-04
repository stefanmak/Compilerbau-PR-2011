//  
//	reimplementation of the minicompiler for the sw-praktikum
//	author: Stefan Leitner, Stefan Schauer
//	last change: 29.09.2004


package itec.minicompiler.SymbolTable;

/**
 * The <code>BasicType</code> implements an INTEGER- or BOOLEAN-Type in the
 * {@link SymbolTable}.
 * 
 * @author Stefan Leitner
 * @author Stefan Schauer
 * @version 1.0 (29.09.2004)
 */
public class BasicType extends Type {
	
	private static final int INTEGER = 101;
	private static final int BOOLEAN = 102;
	
	/** The kind of the <code>BasicType</code> */
	private int kind;
	
	
	/**
	 * Initialize the <code>BasicType</code> with the kind 0 and the size 
	 * <code>-1</code> (i.e. mark the kind and the size as undefined).
	 */
	public BasicType () {
		
		kind = 0;
		setSize (-1);
		
	} // BasicType
	
	// getter-methods
	/**
	 * Check if the actual <code>BasicType</code> is an INTEGER-Type.
	 * 
	 * @return	True if the actual <code>BasicType</code> is an INTEGER-Type,
	 * 			False otherwise
	 */
	public boolean isInteger () {
		
		return (kind == INTEGER);
		
	} // isInteger
	
	/**
	 * Check if the actual <code>BasicType</code> is an BOOLEAN-Type.
	 * 
	 * @return	True if the actual <code>BasicType</code> is an BOOLEAN-Type,
	 * 			False otherwise
	 */
	public boolean isBoolean () {
		
		return (kind == BOOLEAN);
		
	} // isBoolean
	
	// setter-methods
	/**
	 * Make the actual <code>BasicType</code> an INTEGER-Type (i.e. set the kind
	 * to INTEGER).
	 */
	public void setInteger () {
		
		kind = INTEGER;
		
	} // setInteger
	
	/**
	 * Make the actual <code>BasicType</code> an BOOLEAN-Type (i.e. set the kind
	 * to BOOLEAN).
	 */
	public void setBoolean () {
		
		kind = BOOLEAN;
		
	} // setBoolean
	
	// implementation of abstract methods
	/**
	 * Check if the <code>Type<code> <code>t</code> is compatible to the
	 * actual <code>BasicType</code>. Two BasicTypes are compatibel, if
	 * both are either of type INTEGER or of type BOOLEAN.
	 * 
	 * @param t	The <code>Type</code> which should be checked
	 * @return	True if the two Types are equal, false otherwise
	 */
	public boolean isCompatible (Type t) {
		
		if (t instanceof BasicType) {
			BasicType bt;
			bt = (BasicType) t;
			if (isBoolean ()) {
				return bt.isBoolean ();
			} // if isBoolean 
			else if (isInteger ()) {
				return bt.isInteger ();
			} // isInteger
		} // if t instanceof BasicType
		
		return false;
		
	} // isCompatible
	
	// output-methods
	/**
	 * Generate a String-representation of the <code>BasicType</code>.
	 * 
	 * @return	A String-representation of the <code>BasicType</code>
	 * 
	 * @see java.lang.Object#toString()
	 */
	public String toString () {
		
		String header;
		
		switch (kind) {
			case INTEGER:	header = "\tBasicType:INTEGER"; break;
			case BOOLEAN:	header = "\tBasicType:BOOLEAN"; break;
			default: header = "\tBasicType"; break;
		} // switch
		
		return (header + "\tsize: " + getSize());

	} // toString
	
} // class BasicType