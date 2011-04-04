//  
//	reimplementation of the minicompiler for the sw-praktikum
//	author: Stefan Leitner, Stefan Schauer
//	last change: 29.09.2004


package itec.minicompiler;

/**
 * The <code>Token</code> implements a sequence of characters and/or numbers the
 * {@link Scanner} can work with.
 * <p>
 * The <code>Token</code> is defined abstract so that no instance of it can be created
 * but a subclass with more specialised attributes has to be used.
 * 
 * @author Stefan Leitner
 * @author Stefan Schauer
 * @version 1.0 (29.09.2004)
 */
public abstract class Token {
	
	/** The Kind of the Token (Keyword, Ident, Number, ...) */
	private int kind;
	

	// getter-methods	
	/**
	 * Return the kind of the actual <code>Token</code>.
	 * 
	 * @return	The kind of the <code>Token</code>
	 */
	public int getKind () {
		
		return kind;
		
	} // getKind

	// setter-methods	
	/**
	 * Set the kind of the actual <code>Token</code>.
	 * 
	 * @param k	The new kind of the <code>Token</code>
	 */
	public void setKind (int k) {
		
		kind = k;
		
	} // setKind
	
} // abstract class Token

