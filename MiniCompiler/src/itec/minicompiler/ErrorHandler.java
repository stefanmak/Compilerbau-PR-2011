//  
//	reimplementation of the minicompiler for the sw-praktikum
//	author: Stefan Leitner, Stefan Schauer
//	last change: 29.09.2004


package itec.minicompiler;

/**
 * The <code> ErrorHandler </code> implements the actions that have to be done when
 * an error occurs. These are the generation and the output of an error message
 * using the error code given to the <code>ErrorHandler</code>. The error code itself
 * is defined in the {@link ConstantsInterface}.
 * <p>
 * An error message consists of 4 parts:
 * <blockquote><menu>
 * <li>the type of error (LEX for lexical errors, SYN for syntactical errors, SEM for semantical errors)
 * <li>the position of the error (defined by line and column in the source code)
 * <li>the error message for the particular error
 * <li>a suggestion for a solution for the problem
 * </menu></blockquote>
 * <p>
 * Seperately the number of errors the <code>ErrorHandler</code> dealt with are
 * count and can be displayed.
 *  
 * @author Stefan Leitner
 * @author Stefan Schauer
 * @version 1.0 (29.09.2004)
 */
public class ErrorHandler implements ConstantsInterface {
	
	/** The number of errors the <code>ErrorHandler</code> dealt with */
	private int errorCounter;
	
	
	/** 
	 * Initialize a newly created <code>ErrorHandler</code> object with a
	 * total number of 0 occured errors
	 */
	public ErrorHandler () {
		
		errorCounter = 0;
		
	} // ErrorHandler
	
	/**
	 * Return the errorCounter of the actual <code>ErrorHandler</code>
	 * 
	 * @return	The total number of errors
	 */
	public int getErrorCounter () {
		
		return errorCounter;
		
	} // numberOf Errors
	
	/** 
	 * Generate and display the error message for the error number <code>n</code>.
	 * <p>
	 * An error message consists of 4 parts:
	 * <blockquote><menu>
	 * <li>the type of error (LEX for an error occured during the lexical analysis)
	 * <li>the position of the error (defined by line and column in the source code)
	 * <li>the error message for the particular error
	 * <li>a suggestion for a solution for the problem
	 * </menu></blockquote>
	 * <p>
	 * The error message is displayed at the standard output device 
	 * (by <code>System.out.print</code>).
	 * 
	 * @param n			internal number of the error (defined in {@link ConstantsInterface})
	 * @param line		row in the source code where the error occured
	 * @param column	column in the source code where the error occured
	 */
	public void error (int n, int line, int column) {
		
		String s;
		
		
		errorCounter++;
		s = "-- error at line " + line + ", column " + column + ": ";
		
		switch (n) {
			case ERR_NOT_TERMINATED:
				{	s = "-- LEX\n" + s + "The comment was not terminated";
					s = s + "\n-- Solution: Perhaps a \".)\" is missing";
					break;
				} // case ERR_NOT_TERMINATED
			case ERR_INVALID_CHARACTER:	
				{	s = "-- LEX\n" + s + "An unknown character was found"; 
					s = s + "\n-- Solution: The MiniCompiler doesn't support some special characters (\"_\", \"|\", ...)";
					break;
				} // case ERR_INVALID_CHARACTER
			case ERR_NUMBER_TOO_HIGH:
				{	s = "-- LEX\n" + s + "The value of the number is too high for the JVM";
					s = s + "\n-- Solution: Use a value beneath the Java-Integer bounds " + Integer.MIN_VALUE + "and" + Integer.MAX_VALUE;
				} // ERR_NUMBER_TOO_HIGH
			case ERR_EXP_ARRAY: 
				{	s = "-- SYN\n" + s + "The keyword \"ARRAY\" is expected"; 
					s = s + "\n-- Solution: Perhaps \"ARRAY\" should be inserted at this position";
					break;
				} // ERR_EXP_ARRAY
			case ERR_EXP_BECOMES: 
				{	s = "-- SYN\n" + s + "The assignment \":=\" is expected"; 
					s = s + "\n-- Solution: Perhaps a \":=\" should be inserted at this position";					
					break;
				} // ERR_EXP_BECOMES
			case ERR_EXP_COLON: 
				{	s = "-- SYN\n" + s + "The character \":\" is expected"; 
					s = s + "\n-- Solution: Perhaps a \":\" should be inserted at this position";					
					break;
				} // ERR_EXP_COLON
			case ERR_EXP_COMMA: 
				{	s = "-- SYN\n" + s + "The character \",\" is expected"; 
					s = s + "\n-- Solution: Perhaps a \",\" should be inserted at this position";
					break;
				} // ERR_EXP_COMMA
			case ERR_EXP_DO: 
				{	s = "-- SYN\n" + s + "The keyword \"DO\" is expected"; 
					s = s + "\n-- Solution: Perhaps \"DO\" should be inserted at this position";
					break;
				} // ERR_EXP_DO
			case ERR_EXP_END: 
				{	s = "-- SYN\n" + s + "The keyword \"END\" is expected"; 
					s = s + "\n-- Solution: Perhaps \"END\" should be inserted at this position";
					break;
				} // ERR_EXP_END
			case ERR_EXP_EQUAL: 
				{	s = "-- SYN\n" + s + "The operation \"=\" is expected"; 
					s = s + "\n-- Solution: Perhaps a \"=\" should be inserted at this position";
					break;
				} // ERR_EXP_EQUAL
			case ERR_EXP_IDENT: 
				{	s = "-- SYN\n" + s + "An identifier is expected"; 
					s = s + "\n-- Solution: Perhaps an identifier should be inserted at this position";
					break;
				} // ERR_EXP_IDENT
			case ERR_EXP_IF: 
				{	s = "-- SYN\n" + s + "The keyword \"IF\" is expected"; 
					s = s + "\n-- Solution: Perhaps \"IF\" should be inserted at this position";
					break;
				} // ERR_EXP_IF
			case ERR_EXP_LPAREN: 
				{	s = "-- SYN\n" + s + "The character \"(\" is expected"; 
					s = s + "\n-- Solution: Perhaps a \"(\" should be inserted at this position";
					break;
				} // ERR_EXP_LPAREN
			case ERR_EXP_MODULE: 
				{	s = "-- SYN\n" + s + "The keyword \"MODULE\" is expected"; 
					s = s + "\n-- Solution: Perhaps \"MODULE\" should be inserted at this position";
					break;
				} // ERR_EXP_MODULE
			case ERR_EXP_OF: 
				{	s = "-- SYN\n" + s + "The keyword \"OF\" is expected"; 
					s = s + "\n-- Solution: Perhaps \"OF\" should be inserted at this position";
					break;
				} // ERR_EXP_OF
			case ERR_EXP_PERIOD: 
				{	s = "-- SYN\n" + s + "The character \".\" is expected"; 
					s = s + "\n-- Solution: Perhaps a \".\" should be inserted at this position";
					break;
				} // ERR_EXP_PERIOD
			case ERR_EXP_PROCEDURE: 
				{	s = "-- SYN\n" + s + "The keyword \"PROCEDURE\" is expected"; 
					s = s + "\n-- Solution: Perhaps \"PROCEDURE\" should be inserted at this position";
					break;
				} // ERR_EXP_PROCEDURE
			case ERR_EXP_RBRAK: 
				{	s = "-- SYN\n" + s + "The character \"]\" is expected"; 
					s = s + "\n-- Solution: Perhaps a \"]\" should be inserted at this position";
					break;
				} // ERR_EXP_RBRAK
			case ERR_EXP_RECORD: 
				{	s = "-- SYN\n" + s + "The keyword \"RECORD\" is expected"; 
					s = s + "\n-- Solution: Perhaps \"RECORD\" should be inserted at this position";
					break;
				} // ERR_EXP_RECORD
			case ERR_EXP_REPEAT: 
				{	s = "-- SYN\n" + s + "The keyword \"REPEAT\" is expected"; 
					s = s + "\n-- Solution: Perhaps \"REPEAT\" should be inserted at this position";
					break;
				} // ERR_EXP_REPEAT
			case ERR_EXP_RPAREN: 
				{	s = "-- SYN\n" + s + "The character \")\" is expected"; 
					s = s + "\n-- Solution: Perhaps a \")\" should be inserted at this position";
					break;
				} // ERR_EXP_RPAREN
			case ERR_EXP_SEMICOLON: 
				{	s = "-- SYN\n" + s + "The character \";\" is expected"; 
					s = s + "\n-- Solution: Perhaps a \";\" should be inserted at this position";
					break;
				} // ERR_EXP_SEMICOLON
			case ERR_EXP_THEN: 
				{	s = "-- SYN\n" + s + "The keyword \"THEN\" is expected"; 
					s = s + "\n-- Solution: Perhaps \"THEN\" should be inserted at this position";
					break;
				} // ERR_EXP_THEN
			case ERR_EXP_UNTIL: 
				{	s = "-- SYN\n" + s + "The keyword \"UNTIL\" is expected"; 
					s = s + "\n-- Solution: Perhaps \"UNTIL\" should be inserted at this position";
					break;
				} // ERR_EXP_UNTIL
			case ERR_EXP_WHILE: 
				{	s = "-- SYN\n" + s + "The keyword \"WHILE\" is expected"; 
					s = s + "\n-- Solution: Perhaps \"WHILE\" should be inserted at this position";
					break;
				} // ERR_EXP_WHILE
			case ERR_NOT_EXP_DECLARATIONS: 
				{	s = "-- SYN\n" + s + "This Symbol is not expected in Declarations"; 
					s = s + "\n-- Solution: The Symbol has to be in the FirstSet of Declarations";
					break;
				} // ERR_NOT_EXP_DECLARATIONS
			case ERR_NOT_EXP_FACTOR: 
				{	s = "-- SYN\n" + s + "This Symbol is not expected in Factor"; 
					s = s + "\n-- Solution: The Symbol has to be in the FirstSet of Factor";
					break;
				} // ERR_NOT_EXP_FACTOR
			case ERR_NOT_EXP_STATEMENT: 
				{	s = "-- SYN\n" + s + "This Symbol is not expected in Statement"; 
					s = s + "\n-- Solution: The Symbol has to be in the FirstSet of Statement";
					break;
				} // ERR_NOT_EXP_STATEMENT
			case ERR_NOT_EXP_TYPE: 
				{	s = "-- SYN\n" + s + "This Symbol is not expected in Type"; 
					s = s + "\n-- Solution: The Symbol has to be in the FirstSet of Type";
					break;
				} // ERR_NOT_EXP_TYPE
			case ERR_INV_FACTOR: 
				{	s = "-- SYN\n" + s + "Invalid Factor"; 
					s = s + "\n-- Solution: A Symbol has to be in the FirstSet of Factor";
					break;
				} // ERR_INV_FACTOR
			case ERR_INV_STATEMENT: 
				{	s = "-- SYN\n" + s + "Invalid Statement"; 
					s = s + "\n-- Solution: A Symbol has to be in the FirstSet of Statement";
					break;
				} // ERR_INV_STATEMENT
			case ERR_INV_TYPE: 
				{	s = "-- SYN\n" + s + "Invalid Type"; 
					s = s + "\n-- Solution: A Symbol has to be in the FirstSet of Type";
					break;
				} // ERR_INV_TYPE
			case ERR_TYPE_NOT_DECLARED: 
				{	s = "-- SYN\n" + s + "This Symbol has not been declared yet"; 
					s = s + "\n-- Solution: Perhaps a declaration of this symbol is missing";
					break;
				} // ERR_TYPE_NOT_DECLARED
			case ERR_TYPE_ALREADY_DECLARED: 
				{	s = "-- SYN\n" + s + "This Symbol has already been declared"; 
					s = s + "\n-- Solution: Perhaps the symbol should get a different name";
					break;
				} // ERR_TYPE_ALREADY_DECLARED
			case ERR_TYPE_PREDEFINED:
				{	s = "-- SYN\n" + s + "This Symbol is a reserved word for the PL0-language";
					s = s + "\n-- Solution: Some identifiers (\"INTEGER\", \"BOOLEAN\", ...) can not be used";
				} // ERR_TYPE_PREDEFINED
			case ERR_TYPE_NOT_IN_RECORD: 
				{	s = "-- SYN\n" + s + "This Symbol has not been declared in the RECORD-structure"; 
					s = s + "\n-- Solution: Perhaps a declaration of this symbol in the relating RECORD-structure is missing";
					break;
				} // ERR_TYPE_NOT_IN_RECORD
			case ERR_TYPE_NOT_RECURSIVE: 
				{	s = "-- SYN\n" + s + "Recursive datastructures are not allowed"; 
					s = s + "\n-- Solution: Check the type declaration for recursive uses";
					break;
				} // ERR_TYPE_NOT_RECURSIVE
			case ERR_TYPE_NOT_PROCEDURE: 
				{	s = "-- SYN\n" + s + "This Symbol is not a PROCEDURE"; 
					s = s + "\n-- Solution: Perhaps the Symbol should be declared as a PROCEDURE first";
					break;			
				} // ERR_TYPE_NOT_PROCEDURE
			default: 
				{	s = "-- ERR\n" + s + "The error " + n + " was found"; 
					break;
				} // default
		}// switch
		
		System.out.println (s);
		
	} // error
	
	/**
	 * Print the total number of errors reported by the <code>ErrorHandler</code> on
	 * the standard output device (by <code>System.out.print</code>).
	 */
	public void printErrors () {
		
		if (errorCounter == 0) {
			System.out.println ("\nProcess completed");
		} // if count == 0
		else {	
			System.out.println ("\n" + errorCounter + " error(s) detected");
		} // if count > 0
		
	} // printErrors
	
} // class ErrorHandler