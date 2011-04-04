//  
//	reimplementation of the minicompiler for the sw-praktikum
//	author: Stefan Leitner, Stefan Schauer
//	last change: 29.09.2004


package itec.minicompiler;

/**
 * The <code>SimpleToken</code> implements a keyword or an operation found in
 * the source code. It has no further information than the kind of the keyword
 * or operation.
 * 
 * @author Stefan Leitner
 * @author Stefan Schauer
 * @version 1.0 (29.09.2004)
 */
public class SimpleToken extends Token implements ConstantsInterface {
		     
	/**
	 * Initialize the <code>SimpleToken</code> with a kind <code>k</code>.
	 * 
	 * @param k The kind of the <code>SimpleToken</code>
	 */
	public SimpleToken (int k) { 
		
		setKind (k);
		
	} // SimpleToken
		
	/**
	 * Generate a String-representation of the kind of the actual
	 * <code>SimpleToken</code>. 
	 * 
	 * @return The String-representation of the kind
	 */
	public String stringRep () {
		
		switch (getKind ()) {
			case ARRAY: return "ARRAY";
			case BEGIN: return "BEGIN";
			case CONST: return "CONST";
			case DIV: return "DIV";
			case DO: return "DO";
			case ELSE: return "ELSE";
			case ELSIF: return "ELSIF";
			case END: return "END";
			case IF: return "IF";
			case MOD: return "MOD";
			case MODULE: return "MODULE";
			case OF: return "OF";
			case OR: return "OR";
			case PROCEDURE: return "PROCEDURE";
			case RECORD: return "RECORD";
			case REPEAT: return "REPEAT";
			case THEN: return "THEN";
			case TYPE: return "TYPE";
			case UNTIL: return "UNTIL";
			case VAR: return "VAR";
			case WHILE: return "WHILE";
			case AND: return "&";
			case TIMES: return "*";
			case PLUS: return "+"; 
			case MINUS: return "-";
			case EQUAL: return "="; 
			case NOTEQUAL: return "#"; 
			case SEMICOLON: return ";"; 
			case COMMA: return ","; 
			case PERIOD: return "."; 
			case RPAREN: return ")"; 
			case LBRAK: return "["; 
			case RBRAK: return "]"; 
			case NOT: return "~"; 
			case LEQ: return "<=";
			case LESS: return "<"; 
			case GEQ: return ">="; 
			case GREATER: return ">";
			case BECOMES: return ":="; 
			case COLON: return ":"; 
			case LPAREN: return "("; 
		} // switch (getKind ())
		
		return null;
		
	} // StringRep

	// output-methods	
	/** 
	 * Generate a String-representation of the <code>SimpleToken</code> using
	 * the <code>stringRep</code> method.
	 * 
	 * @see java.lang.Object#toString()
	 */
	public String toString () {
		
		String str = null;
		
		switch (getKind ()) {
			case ARRAY: str = "ARRAY"; break;
			case BEGIN: str = "BEGIN"; break;
			case CONST: str = "CONST"; break;
			case DIV: str = "DIV"; break;
			case DO: str = "DO"; break;
			case ELSE: str = "ELSE"; break;
			case ELSIF: str = "ELSIF"; break;
			case END: str = "END"; break;
			case IF: str = "IF"; break;
			case MOD: str = "MOD"; break;
			case MODULE: str = "MODULE"; break;
			case OF: str = "OF"; break;
			case OR: str = "OR"; break;
			case PROCEDURE: str = "PROCEDURE"; break;
			case RECORD: str = "RECORD"; break;
			case REPEAT: str = "REPEAT"; break;
			case THEN: str = "THEN"; break;
			case TYPE: str = "TYPE"; break;
			case UNTIL: str = "UNTIL"; break;
			case VAR: str = "VAR"; break;
			case WHILE: str = "WHILE"; break;
			case AND: str = "AND"; break;
			case TIMES: str = "TIMES"; break;
			case PLUS: str = "PLUS"; break;
			case MINUS: str = "MINUS"; break;
			case EQUAL: str = "EQUAL"; break;
			case NOTEQUAL: str = "NOTEQUAL"; break;
			case SEMICOLON: str = "SEMICOLON"; break;
			case COMMA: str = "COMMA"; break;
			case PERIOD: str = "PERIOD"; break;
			case RPAREN: str = "RPAREN"; break;
			case LBRAK: str = "LBRAK"; break;
			case RBRAK: str = "RBRAK"; break;
			case NOT: str = "NOT"; break;
			case LEQ: str = "LEQ"; break;
			case LESS: str = "LESS"; break;
			case GEQ: str = "GEQ"; break;
			case GREATER: str = "GREATER"; break;
			case BECOMES: str = "BECOMES"; break;
			case COLON: str = "COLON"; break;
			case LPAREN: str = "LPAREN"; break;
			default: str = String.valueOf(getKind ());
		}
		
		return "token:\tkind: " + str + "\tvalue: " + stringRep ();
		
	} // toString 
			
} // class SimpleToken