//  
//	reimplementation of the minicompiler for the sw-praktikum
//	author: Stefan Leitner, Stefan Schauer
//	last change: 29.09.2004


package itec.minicompiler;

/**
 * The <code>IdentToken</code> implements an identifier found in the source code. 
 * It contains the value of the identifier (it's character sequence) together with 
 * the the kind.
 *
 * @author Stefan Leitner
 * @author Stefan Schauer
 * @version 1.0 (29.09.2004)
 */
public class IdentToken extends Token {
	
	/** The value of the <code>IdentToken</code> */
	private String value;
	
	
	/**
	 * Initialize the <code>IdentToken</code> with a kind <code>k</code> and
	 * a value <code>v</code>.
	 * 
	 * @param k	The kind of the <code>IdentToken</code>
	 * @param v	The value of the represented identifier
	 */
	public IdentToken (int k, String v) {
		
		setKind (k);
		value = v;
	
	} // IdentToken
	
	// getter-methods
	/**
	 * Return the value of the actual <code>IdentToken</code>.
	 * 
	 * @return The value of the represented identifier
	 */
	public String getValue () {
		
		return value;
		
	} // getValue
	
	// setter-methods
	/**
	 * Set the value of the actual <code>IdentToken</code>.
	 * 
	 * @param v The new value of the <code>IdentToken</code>
	 */
	public void setValue (String v) {
		
		value = v;
		
	} // setValue
	
	// output-methods
	/** 
	 * Generate a String-representation of the <code>IdentToken</code>.
	 * 
	 * @see java.lang.Object#toString()
	 */
	public String toString () {
		
		return "token:\tkind: IDENT\tvalue: " + value;
		
	} // toString
	
} // class IdentToken