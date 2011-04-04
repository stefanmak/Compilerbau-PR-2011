//  
//	reimplementation of the minicompiler for the sw-praktikum
//	author: Stefan Leitner, Stefan Schauer
//	last change: 29.09.2004


package itec.minicompiler.SymbolTable;

/**
 * The <code>AddressedSymbol</code> implements a Symbol in the {@link SymbolTable} which
 * has also an address in addition to the TypedSymbol-attributes.
 * <p>
 * An <code>AddressedSymbol</code> can be of one of four types:
 * <blockquote><menu>
 * <li>PROCEDURE		(A predefined or userdefined procedure)
 * <li>PARAMETER_IN		(An input-parameter for a procedure)
 * <li>PARAMETER_IN_OUT	(An input/output-parameter for a procedure)
 * <li>VARIABLE			(A simple global or local variable)
 * </menu></blockquote>
 * 
 * @author Stefan Leitner
 * @author Stefan Schauer
 * @version 1.0 (29.09.2004)
 */
public class AddressedSymbol extends TypedSymbol {
	
	private static final int PROCEDURE = 401;
	private static final int PARAMETER_IN = 402;
	private static final int PARAMETER_IN_OUT = 403;
	private static final int VARIABLE = 404;
	
	/** The kind of the <code>AddressedSymbol</code> */
	private int kind;
	/** The address of the <code>AddressedSymbol</code> */
	private int address;
	
	
	/**
	 * Initialize the <code>AddressedSymbol</code> with a name <code>n</code>,
	 * a <code>Type</code> <code>t</code> and an address <code>a</code>.
	 * 
	 * @param n	The name of the <code>AddressedSymbol</code>
	 * @param t	The Type of the <code>AddressedSymbol</code>
	 * @param a The address of the <code>AddressedSymbol</code>
	 */
	public AddressedSymbol (String n, Type t, int a) {
		
		super (n, t);
		address = a;
		kind = 0;
		
	} // AddressedSymbol
	
	//getter-methods
	/**
	 * Return the address of the actual <code>AddressedSymbol</code>.
	 * 
	 * @return The address of the <code>AddressedSymbol</code>
	 */
	public int getAddress () {
		
		return address;
		
	} // getAddress
	
	/**
	 * Check if the actual <code>AddressedSymbol</code> is a PROCEDURE-Symbol.
	 * 
	 * @return	True if the <code>AddressedSymbol</code> is a PROCEDURE-Symbol,
	 * 			False otherwise.
	 */
	public boolean isProcedure () {
		
		return (kind == PROCEDURE);
		
	} // isProcedure
	
	/**
	 * Check if the actual <code>AddressedSymbol</code> is a PARAMETER_IN-Symbol.
	 * 
	 * @return	True if the <code>AddressedSymbol</code> is a PARAMETER_IN-Symbol,
	 * 			False otherwise.
	 */
	public boolean isParameterIn () {
		
		return (kind == PARAMETER_IN);
		
	} // isParameterIn
	
	/**
	 * Check if the actual <code>AddressedSymbol</code> is a PARAMETER_IN_OUT-Symbol.
	 * 
	 * @return	True if the <code>AddressedSymbol</code> is a PARAMETER_IN_OUT-Symbol,
	 * 			False otherwise.
	 */
	public boolean isParameterInOut () {
		
		return (kind == PARAMETER_IN_OUT);
		
	} // isParameterInOut
	
	/**
	 * Check if the actual <code>AddressedSymbol</code> is a VARIABLE-Symbol.
	 * 
	 * @return	True if the <code>AddressedSymbol</code> is a VARIABLE-Symbol,
	 * 			False otherwise.
	 */
	public boolean isVariable () {
		
		return (kind == VARIABLE);
		
	} // isVariable
	
	// setter-methods
	/**
	 * Set the address of the actual <code>AddressedSymbol</code>.
	 * 
	 * @param a	The new address of the <code>AddressedSymbol</code>
	 */
	public void setAddress (int a) {
		
		address = a;
		
	} // setAddress
	
	/**
	 * Make the actual <code>AddressedSymbol</code> to a PROCEDURE-Symbol
	 * (i.e. set the kind to PROCEDURE).
	 */
	public void setProcedure () {
		
		kind = PROCEDURE;
		
	} // setProcedure
	
	/**
	 * Make the actual <code>AddressedSymbol</code> to a PARAMETER_IN-Symbol
	 * (i.e. set the kind to PARAMETER_IN).
	 */
	public void setParameterIn () {
		
		kind = PARAMETER_IN;
		
	} // setParameterIn
	
	/**
	 * Make the actual <code>AddressedSymbol</code> to a PARAMETER_IN_OUT-Symbol
	 * (i.e. set the kind to PARAMETER_IN_OUT).
	 */
	public void setParameterInOut () {
		
		kind = PARAMETER_IN_OUT;
		
	} // setParameterInOut
	
	/**
	 * Make the actual <code>AddressedSymbol</code> to a VARIABLE-Symbol
	 * (i.e. set the kind to VARIABLE).
	 */
	public void setVariable () {
		
		kind = VARIABLE;
		
	} // setVariable
	
	// super-methods
	/**
	 * Return a clone of the actual <code>AddressedSymbol</code>. The cloning 
	 * is restricted on the actual <code>AddressedSymbol</code>, what means that 
	 * the pointers of the new <code>AddressedSymbol</code> have the same targets 
	 * as the pointers of the actual <code>AddressedSymbol</code> (a shallow-clone 
	 * is implemented).
	 * 
	 * @return	An Object equal to the actual <code>AddressedSymbol</code>
	 * 
	 * @see java.lang.Object#clone()
	 */
	public Object clone () {
		
		AddressedSymbol asym;
		
		asym = new AddressedSymbol (this.getName (), this.getType(), this.getAddress());
		asym.setLevel (this.getLevel ());
		asym.setUpscope (this.getUpscope());
		asym.setNext (this.getNext ());
		asym.setLocal (this.getLocal ());
		if (this.isProcedure ()) {
			asym.setProcedure ();
		} // if this.isProcedure ()
		else if (this.isParameterIn ()) {
			asym.setParameterIn ();
		} // if this.isParameterIn ()
		else if (this.isParameterInOut ()) {
			asym.setParameterInOut ();
		} // if this.isParameterInOut ()
		
		return asym;
		
	} // clone
	
	// output-methods
	/**
	 * Generate a String-representation of the <code>AddressedSymbol</code>.
	 * 
	 * @return	A String-representation of the <code>AddressedSymbol</code>
	 * 
	 * @see java.lang.Object#toString()
	 */
	public String toString () {
		
		String header;
		
		switch (kind) {
			case PROCEDURE:		header = "AddressedSymbol: PROCEDURE"; break;
			case PARAMETER_IN:	header = "AddressedSymbol: PARAMETER_IN"; break;
			case PARAMETER_IN_OUT: header = "AddressedSymbol: PARAMETER_IN_OUT"; break;
			case VARIABLE:		header = "AddressedSymbol: VARIABLE"; break;
			default: 			header = "AddressedSymbol"; break;
		} // switch
		
		if (getUpscope() != null) {
			return (header + "\nname: " + getName() + "\naddress: " + address + "\ntype: " + getType() + "\nlevel: " + getLevel() + "\nupscope: " + getUpscope().getName() + "\n");
		} // if upscope != null
		else {
			return (header + "\nname: " + getName() + "\naddress: " + address + "\ntype: " + getType() + "\nlevel: " + getLevel() + "\n");
		} // if upscope == null
		
	} // toString
	
} // class AddressedSymbol