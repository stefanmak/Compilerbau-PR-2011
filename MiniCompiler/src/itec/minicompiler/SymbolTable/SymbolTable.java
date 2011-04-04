//  
//	reimplementation of the minicompiler for the sw-praktikum
//	author: Stefan Leitner, Stefan Schauer
//	last change: 29.09.2004


package itec.minicompiler.SymbolTable;

/**
 * The <code>SymbolTable</code> implements a linked list of Symbols the 
 * {@link itec.minicompiler.Parser} uses to maintain the structure of the Symbols given 
 * by the source code and to do the scoping.
 * Each <code>Symbol</code> in the <code>SymbolTable</code> has a pointer to the next
 * <code>Symbol</code> in its scope (if any) and can have a pointer to a subscope 
 * (if needed).
 * The beginning of the <code>SymbolTable</code> is the <code>universeScope</code> where
 * the predefined constants, types and procedures of the language should be inserted. 
 * 
 * @author Stefan Leitner
 * @author Stefan Schauer
 * @version 1.0 (29.09.2004)
 */
public class SymbolTable {

	/** The highest Scope in the <code>SymbolTable</code> (the top of all scopes) */
	private StructuralSymbol universeScope;
	/**	The scope the next <code>Symbol</code> will be inserted in */
	private Symbol actualScope;
	/** The <code>Symbol</code> that has just been inserted in the <code>SymbolTable</code> */
	private Symbol actualSymbol;
	/** The actual level of declaration */
	private int actualLevel;
	
	
	/**
	 * Initialize the <code>SymbolTable</code> with a UNIVERSE-Symbol <code>sym</code>.
	 * 
	 * @param sym	The UNIVERSE-Symbol (root of the <code>SymbolTable</code>
	 */
	public SymbolTable (Symbol sym) {
		
		universeScope = (StructuralSymbol) sym;
		actualScope = sym;
		actualSymbol = sym;
		actualLevel = 0;
		
	} // SymbolTable
	
	// getter-methods
	/**
	 * Return the universeScope of the actual <code>SymbolTable</code>.
	 * 
	 * @return	The universeScope of the <code>SymbolTable</code>
	 */
	public StructuralSymbol getUniverseScope () {
		
		return universeScope;
		
	} // getUniverseScope
	
	/**
	 * Return the actualScope of the actual <code>SymbolTable</code>.
	 * 
	 * @return	The actualScope of the <code>SymbolTable</code>
	 */
	public Symbol getActualScope () {
		
		return actualScope;
		
	} // getActualScope
	
	/**
	 * Return the actualSymbol of the actual <code>SymbolTable</code>.
	 * 
	 * @return	The actualSymbol of the <code>SymbolTable</code>
	 */
	public Symbol getActualSymbol () {
		
		return actualSymbol;
		
	} // getActualSymbol
	
	// maintainance-methods
	/**
	 * Open a new scope in the <code>SymbolTable</code>. The actual scope of the
	 * new scope is the <code>actualSymbol</code>. As first element in the 
	 * new scope a HEAD-Symbol is inserted. The name of this HEAD-Symbol indicates
	 * the type of the new scope. It can be <code>universe_scope</code>, 
	 * <code>module_scope</code>, <code>procedure_scope</code> or 
	 * <code>record_scope</code>.
	 */
	public void openScope () {
		
		StructuralSymbol sym;
		String name;
		
		
		name = null;
		if (actualSymbol instanceof StructuralSymbol) {
			StructuralSymbol actsym = (StructuralSymbol) actualSymbol;
			if (actsym.isUniverse ()) {
				name = "universe_scope()";
			} // if actsym.isUniverse ()
			else if (actsym.isModule ()) {
				name = "module_scope(" + actualSymbol.getName () + ")";
			} // if actsym.isModule()
		} // if actualSymbol instanceof StructuralSymbol
		else if (actualSymbol instanceof AddressedSymbol) {
			AddressedSymbol actsym = (AddressedSymbol) actualSymbol;
			if (actsym.isProcedure ()) {
				name = "procedure_scope(" + actualSymbol.getName () + ")";
			} // if actsym.isProcedure ()
		} // if actualSymbol instanceof AddressedSymbol
		else {
			name = "record_scope(" + actualSymbol.getName () + ")";
		} // else
		
		sym = new StructuralSymbol (name);
		sym.setHead ();
		
		actualSymbol.setLocal (sym);
		actualScope = actualSymbol;
		actualSymbol = sym;
		actualSymbol.setUpscope (actualScope);
		actualLevel++;
		actualSymbol.setLevel (actualLevel);
		
	} // openScope
	
	/**
	 * Close the actual scope.
	 */
	public void closeScope () {
		
		actualSymbol = actualScope;
		actualScope = actualSymbol.getUpscope (); 
		actualLevel--;
	
	} // closeScope
	
	/**
	 * Insert a <code>Symbol</code> <code>sym</code> in the actual scope.
	 * 
	 * @param sym	The <code>Symbol</code> which should be inserted
	 */
	public void putSymbol (Symbol sym) {
		
		sym.setLevel (actualLevel);
		actualSymbol.setNext (sym);
		actualSymbol = sym;
		actualSymbol.setUpscope (actualScope);
	
	} // putSymbol
	
	/**
	 * Search for the <code>Symbol</code> <code>sym</code> in the <code>SymbolTable</code>.
	 * The search is restricted onto the universe scope, the module scope and the actual
	 * scope.
	 * 
	 * @param sym	The <code>Symbol</code> which should be found
	 * @return		The <code>Symbol</code> itself, if it's found in the 
	 * 				<code>SymbolTable</code>, <code>null</code> otherwise
	 */
	public Symbol getSymbol (Symbol sym) {
		
		Symbol s;
		
		
		// check if the Symbol is a recursive procedure
		s = actualScope;
		if (s.equals (sym)) {
			return s;
		} // if s.equals (sym)
		
		// check if the Symbol is present in the actual Scope
		s = s.getLocal (); // go to the head of the upscope of the actual Symbol
		while (s.getNext () != null) {
			s = s.getNext ();
			if (s.equals (sym)) {
				return s;
			} // if s.equals (sym)
		} // while s.getNext () != null
		
		// check if the Symbol is present in the universe-scope
		s = universeScope.getLocal (); // go to the head of the universe-Scope
		while (s.getNext () != null) {
			s = s.getNext ();
			if (s.equals (sym)) {
				return s;
			} // if s.equals (sym)
		} // while s.getNext () != null
		
		// check if the Symbol is present in the global (MODULE) scope
		s = s.getLocal ();
		while (s.getNext () != null) {
			s = s.getNext ();
			if (s.equals (sym)) {
				return s;
			} // if s.equals (sym)
		} // while s.getNext () != null

		return null;
		
	} // getSymbol
	
	/**
	 * Check if the <code>Symbol</code> <code>sym</code> is already present in the
	 * actual scope or in the universe scope.
	 * 
	 * @param sym	The <code>Symbol</code> which should be checked
	 * @return		True, if the <code>Symbol</code> is already present, 
	 * 				False otherwise
	 */
	public boolean isPresent (Symbol sym) {
		
		Symbol s;
		
		
		// check for the actual scope first
		s = actualScope.getLocal (); // go to the head of the actual Scope
		if (s.getNext () != null) { 
			do {
				s = s.getNext ();
				if (s.equals (sym)) {
					return true;
				} // if s.equals (sym)
			} // do
			while (s.getNext () != null);
		} // if s.getNext != null
		
		// check also if sym is defined in universeScope
		// so if it is a reserved word (INTEGER, TRUE, ...)
		s = universeScope.getLocal ();
		if (s.getNext () != null) {
			do {
				s = s.getNext ();
				if (s.equals (sym)) {
					return true;
				} // if s.equals (sym)
			} // do
			while (s.getNext () != null);
		} // if s.getNext () != null
		
		return false;
		
	} // isPresent
	
	/**
	 * Check if the <code>Symbol</code> <code>sym</code> is a reserved word (i.e. if
	 * <code>sym</code> is present in the universe-scope
	 * 
	 * @param sym	The <code>Symbol</code> which should be checked
	 * @return		True, if the <code>Symbol</code> is present in the universe-scope, 
	 * 				False otherwise
	 */
	public boolean isPredefined (Symbol sym) {
		
		Symbol s;
		
		
		// check if sym is oresent in the universeScope
		s = universeScope.getLocal ();
		if (s.getNext () != null) {
			do {
				s = s.getNext ();
				if (s.equals (sym)) {
					return true;
				} // if s.equals (sym)
			} // do
			while (s.getNext () != null);
		} // if s.getNext () != null
		
		return false;
	
	} // isPredefined	
	
	// output-methods	
	/**
	 * Generate a String-representation of the <code>SymbolTable</code>.
	 * 
	 * @return	A String-representation of the <code>SymbolTable</code>
	 * 
	 * @see java.lang.Object#toString()
	 */
	public String toString () {
		
		Symbol s;
		
		
		s = universeScope;
		return toStringRec (s);
		
	} // toString
	
	/**
	 * Recursive output of th <code>Symbol</code> <code>s</code> and all
	 * Symbols in its subscope, if any.
	 * 
	 * @param s	The <code>Symbol</code> for the output
	 * @return	A String-representation of <code>s</code> and its subscopes
	 */
	private String toStringRec (Symbol s) {
	
		String str = "";
		
		
		do {
			str = str + s.toString () + "\n";
			if (s.getLocal () != null) {
				str = str + toStringRec (s.getLocal ());
			} // if s.getLocal () != null
			s = s.getNext ();
		} // do
		while (s != null);
		
		return str;
			
	} // rec

} // class SymbolTable