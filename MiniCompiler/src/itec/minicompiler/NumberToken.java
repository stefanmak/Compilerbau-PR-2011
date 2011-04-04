//  
//	reimplementation of the minicompiler for the sw-praktikum
//	author: Stefan Leitner, Stefan Schauer
//	last change: 29.09.2004


package itec.minicompiler;

/**
 * The <code>NumberToken</code> implements a number found in the source code. 
 * It contains the value of the number together with the the kind.
 * 
 * @author Stefan Leitner
 * @author Stefan Schauer
 * @version 1.0 (29.09.2004)
 */
public class NumberToken extends Token {
	
	/** The value of the number represented by the <code>NumberToken</code> */
	private int value;
	
	
	/**
	 * Initialize the <code>NumberToken</code> with a kind <code>k</code> and
	 * a value <code>v</code>.
	 * 
	 * @param k	The kind of the <code>NumberToken</code>
	 * @param v The value of the represented number
	 */
	public NumberToken (int k, int v) {
		
		setKind (k);
		value = v;
	
	} // NumberToken
	
	// getter-methods
	/**
	 * Return the value of the actual <code>NumberToken</code>.
	 * 
	 * @return The value of the represented number
	 */
	public int getValue () {
		
		return value;
		
	} // getValue
	
	// setter-methods
	/**
	 * Set the value of the actual <code>NumberToken</code>.
	 * 
	 * @param v The new value of the <code>NumberToken</code>
	 */
	public void setValue (int v) {
		
		value = v;
		
	} // setValue
	
	// output-methods
	/** 
	 * Generate a String-representation of the <code>NumberToken</code>.
	 * 
	 * @see java.lang.Object#toString()
	 */
	public String toString () {
		
		return "token:\tkind: NUMBER\tvalue: " + value;
		
	} // toString
	
} // class NumberToken