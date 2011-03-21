package yapl.interfaces;

/** Generic YAPL compiler error interface.
 * It defines error numbers and common methods of exception and error types
 * thrown by the YAPL compiler.
 * 
 * @author Mario Taschwer
 * @version $Id: CompilerError.java 141 2010-03-16 17:17:56Z mt $
 */
public interface CompilerError {

	/* --- Error Numbers --- */
	
	/** Internal error. */
	public static final int Internal             = 1;
	/** Lexical error. */
	public static final int Lexical              = 2;
	/** Syntax error. */
	public static final int Syntax               = 3;
	
	/* Symbol check errors */
	
	/** Symbol already declared. */
	public static final int SymbolExists         = 10;
	/** Identifier not declared. */
	public static final int IdentNotDecl         = 11;
	/** Illegal use of symbol. */
	public static final int SymbolIllegalUse     = 12;
	/** End identifier does not match program|procedure. */
	public static final int EndIdentMismatch     = 13;
	
	/* Type check errors */
	
	/** Expression before '[' is not an array type. */
	public static final int SelectorNotArray     = 20;
	/** Array index or dimension is not an integer type. */
	public static final int BadArraySelector     = 21;
	/** Expression after '#' is not an array type. */
	public static final int ArrayLenNotArray     = 22;
	/** Illegal operand type for unary operator. */
	public static final int IllegalOp1Type       = 23;
	/** Illegal operand type for binary operator. */
	public static final int IllegalOp2Type       = 24;
	/** Illegal operand type for relational operator. */
	public static final int IllegalRelOpType     = 25;
	/** Illegal operand type for equality operator. */
	public static final int IllegalEqualOpType   = 26;
	/** Using procedure (not a function) in expression. */
	public static final int ProcNotFuncExpr      = 27;
	/** Read-only l-value in assignment. */
	public static final int ReadonlyAssign       = 28;
	/** Type mismatch in assignment. */
	public static final int TypeMismatchAssign   = 29;
	/** Argument not applicable to procedure. */
	public static final int ArgNotApplicable     = 30;
	/** Read-only argument passed to read-write procedure. */
	public static final int ReadonlyArg          = 31;
	/** Too few arguments for procedure. */
	public static final int TooFewArgs           = 32;
	/** Condition is not a boolean expression. */
	public static final int CondNotBool          = 33;
	/** Readonly not followed by array type. */
	public static final int ReadonlyNotArray     = 34; 
	/** Missing return statement in function. */
	public static final int MissingReturn        = 35;
	/** Returning none or invalid type from function. */
	public static final int InvalidReturnType    = 36;
	/** Illegal return value in procedure (not a function). */
	public static final int IllegalRetValProc    = 37;
	/** Illegal return value in main program. */
	public static final int IllegalRetValMain    = 38;

	/* Code generation errors */
	
	/** Too many registers used. */
	public static final int NoMoreRegs           = 50;
	
	/** Too many array dimensions. */
	public static final int TooManyDims          = 51;
	
    /* --- End of error numbers --- */
	
	/** Return the compiler error number.
	 * @return One of the constants defined by this class.
	 */
	public int errorNumber();
	
	/** Return the source code line number where the error occurred. */
	public int line();
	
	/** Return the source code column number where the error occurred. */
	public int column();
	
	/** Return the detailed error message. */
	public String getMessage();
}
