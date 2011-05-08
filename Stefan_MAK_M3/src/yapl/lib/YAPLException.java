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
		 * End <name> does not match Program <name> 
		 * End <name> does not match Procedure <name> 
		 * symbol <name> already declared in current scope (as <kind>) 
		 * identifier <name> not declared 
		 * illegal use of <kind> <name>
		 */
		
		if(errorNumber == CompilerError.EndIdentMismatch && symbol.getKind() == Symbol.Program)
			this.message = "End " 
				+ symbol.getName() 
				+ " does not match Program " 
				+ symbol.getName();					
		else if(errorNumber == CompilerError.EndIdentMismatch && symbol.getKind() == Symbol.Procedure)
			this.message = "End " 
				+ symbol.getName() 
				+ " does not match Procedure " 
				+ symbol.getName();			
		else if(errorNumber == CompilerError.SymbolExists)
			this.message = "symbol " 
				+ symbol.getName() 
				+ " already declared in current scope (as " 
				+ symbol.getKindString() + ")";
		else if(errorNumber == CompilerError.IdentNotDecl)
			this.message = "identifier " 
				+ t.toString() 
				+ " not declared"; 
		else if(errorNumber == CompilerError.SymbolIllegalUse)
			this.message = "illegal use of " 
				+ symbol.getKindString() + " "
				+ symbol.getName(); 	
		else if(errorNumber == CompilerError.SelectorNotArray){
			this.message = "expression before '[' is not an array type";			
		}
		else if(errorNumber == CompilerError.BadArraySelector){
			this.message = "array index or dimension is not an integer type";			
		}
		else if(errorNumber == CompilerError.ArrayLenNotArray){
			this.message = "expression after '#' is not an array type";			
		}
		else if(errorNumber == CompilerError.IllegalOp1Type){
			this.message = "illegal operand type for unary operator " + t.getImage();			
		}
		else if(errorNumber == CompilerError.IllegalOp2Type){
			this.message = "illegal operand types for binary operator " + t.getImage();			
		}
		else if(errorNumber == CompilerError.IllegalRelOpType){
			this.message = "non-integer operand types for relational operator " + t.getImage();			
		}
		else if(errorNumber == CompilerError.IllegalEqualOpType){
			this.message = "illegal operand types for equality operator " + t.getImage();			
		}
		else if(errorNumber == CompilerError.ProcNotFuncExpr){
			this.message = "using procedure " + t.getImage() + " (not a function) in expression";			
		}
		else if(errorNumber == CompilerError.ReadonlyAssign){
			this.message = "read-only l-value in assignment";			
		}
		else if(errorNumber == CompilerError.TypeMismatchAssign){
			this.message = "type mismatch in assignment";			
		}
		else if(errorNumber == CompilerError.ArgNotApplicable){
			this.message = "argument #" + t.getImage() + " not applicable to procedure " + symbol.getName();			
		}
		else if(errorNumber == CompilerError.ReadonlyArg){
			this.message = "read-only argument #" + t.getImage() + " passed to read-write procedure " + symbol.getName();			
		}
		else if(errorNumber == CompilerError.TooFewArgs){
			this.message = "too few arguments for procedure " + symbol.getName();			
		}
		else if(errorNumber == CompilerError.CondNotBool){
			this.message = "condition is not a boolean expression";			
		}
		else if(errorNumber == CompilerError.ReadonlyNotArray){
			this.message = "type qualified as readonly is not an array type";			
		}
		else if(errorNumber == CompilerError.MissingReturn){
			this.message = "missing Return statement in function " + symbol.getName();			
		}
		else if(errorNumber == CompilerError.InvalidReturnType){
			this.message = "returning none or invalid type from function " + symbol.getName();			
		}
		else if(errorNumber == CompilerError.IllegalRetValProc){
			this.message = "illegal return value in procedure " + symbol.getName() + " (not a function)";			
		}
		else if(errorNumber == CompilerError.IllegalRetValMain){
			this.message = "illegal return value in main program";			
		}		
		else{			
			this.message = "General Error on Symbol: " + symbol.getKindString() + " " + symbol.getKind();
		}
			
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
