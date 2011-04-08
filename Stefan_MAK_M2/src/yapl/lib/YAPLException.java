package yapl.lib;

import yapl.interfaces.CompilerError;
import yapl.interfaces.Symbol;
import yapl.interfaces.Token;

public class YAPLException extends Error implements CompilerError {	

	
	/**
	 * Default serialVersionUID of Eclipse
	 */
	private static final long serialVersionUID = 1L;
	private int errorNumber;
	private int line;
	private int column;
	private String message;

	
	/**
	 * Constructor for SymboltablImpl
	 * @param errorNumber 
	 */
	public YAPLException(int errorNumber){	
		this.errorNumber = errorNumber;
	}
	
	/**
	 * 
	 * @param errorNumber
	 * @param symbol
	 * @param t
	 */
	public YAPLException(int errorNumber, Symbol symbol, Token t){
		this.errorNumber = errorNumber;
		this.column = t.column();
		this.line = t.line();
		
		/**
		 * • End <name> does not match Program <name> 
		 * • End <name> does not match
		 * • Procedure <name> 
		 * • symbol <name> already declared in current scope (as <kind>) 
		 * • identifier <name> not declared • illegal use of <kind> <name>
		 */
		
		if(errorNumber == CompilerError.SymbolExists)
			this.message = "symbol " 
				+ symbol.getName() 
				+ " already declared in current scope (as " 
				+ symbol.getKindString() + ")";
		else if(errorNumber == CompilerError.IdentNotDecl)
			this.message = "identifier " 
				+ symbol.getName() 
				+ " not declared"; 
		else
			this.message = "General Error";
				
		
	}
	
	
	@Override
	public int errorNumber() {
		return errorNumber;
	}
	
	public int line() {
		return line;
	}

	@Override
	public int column() {
		return this.column;
	}

	@Override
	public String getMessage() {			
		return this.message;
	}

}
