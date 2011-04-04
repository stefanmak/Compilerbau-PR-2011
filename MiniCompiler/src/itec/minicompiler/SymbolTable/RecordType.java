//  
//	reimplementation of the minicompiler for the sw-praktikum
//	author: Stefan Leitner, Stefan Schauer
//	last change: 29.09.2004


package itec.minicompiler.SymbolTable;

/**
 * The <code>RecordType</code> implements a RECORD-Type in the {@link SymbolTable}.
 * 
 * @author Stefan Leitner
 * @author Stefan Schauer
 * @version 1.0 (29.09.2004)
 */
public class RecordType extends Type {
	
	/**	The list of the fields of the <code>RecordType</code> 
	 *  (points to the first Symbol of the RECORD) */
	Symbol fields;
	
	
	/**
	 * Initialize the <code>RecordType</code> with a <code>Symbol</code> <code>f</code>,
	 * which is the first symbol of the RECORD. Set the size to <code>-1</code> (i.e. the
	 * size if undefined).
	 * 
	 * @param f	The first <code>Symbol</code> in the Record-scope
	 */
	public RecordType (Symbol f) {
		
		fields = f;
		setSize (-1);
		
	} // RecordType
	
	// getter-methods
	/**
	 * Return the fields of the actual <code>RecordType</code>.
	 * 
	 * @return	The first <code>Symbol</code> in the Record-scope
	 */
	public Symbol getFields () {
		
		return fields;
		
	} // getFields
	
	// setter-methods
	/**
	 * Set the fields of the actual <code>RecordType</code>.
	 * 
	 * @param f	The new first <code>Symbol</code> in the Record-scope
	 */
	public void setFields (Symbol f) {
		
		fields = f;
		
	} // setFields
	
	// implementation of abstract methods
	/**
	 * Check if the <code>Type<code> <code>t</code> is compatible to the
	 * actual <code>RecordType</code>. Two RecordTypes are compatibel, if
	 * they have the same number of fields and the fieldtypes are
	 * pairwise compatible.
	 * 
	 * @param t	The <code>Type</code> which should be checked
	 * @return	True if the two Types are equal, false otherwise
	 */
	public boolean isCompatible (Type t) {
		
		if (t instanceof RecordType) {
			RecordType rt;
			TypedSymbol f, tf;
			rt = (RecordType) t;
			f = (TypedSymbol) getFields ();
			tf = (TypedSymbol) rt.getFields ();
			while (f != null && tf != null) {
					if (!f.getType ().isCompatible (tf.getType ())) {
						return false;
					} // if !f.getType ().isCompatible (tf.getType ())
					f = (TypedSymbol) f.getNext ();
					tf = (TypedSymbol) tf.getNext ();
			} // while
			if (f == null && tf == null) {
				return true;
			} // if f == null && tf == null
		} // if t instanceof RecordType
		
		return false;
		
	} // isCompatible
	
	// output-methods
	/**
	 * Generate a String-representation of the <code>RecordType</code>.
	 * 
	 * @return	A String-representation of the <code>RecordType</code>
	 * 
	 * @see java.lang.Object#toString()
	 */
	public String toString () {
		
		return ("\tRecordType" + "\tfields: " + fields.getName() + "\tsize: " + getSize());

	} // toString
	
} // class RecordType