//  
//	reimplementation of the minicompiler for the sw-praktikum
//	author: Stefan Leitner, Stefan Schauer
//	last change: 29.09.2004


package itec.minicompiler;

import java.util.HashSet;
import java.util.Iterator;
import java.lang.Integer;;

/**
 * The <code>Scanner</code> implements the actions necessary to extract the {@link Token} 
 * out of the source code. These are reading the source code characterwise and mainly
 * distinguishing between {@link SimpleToken}, {@link NumberToken} and {@link IdentToken} 
 * and handling the comments.
 * 
 * @author Stefan Leitner
 * @author Stefan Schauer
 * @version 1.0 (29.09.2004)
 */
public class Scanner implements ConstantsInterface{
	
	/** The set of keywords*/
	private HashSet keywords;
	/** The set of letters*/
	private HashSet letters;
	/** The set of digits*/
	private HashSet digits;
	/** The set of special characters*/
	private HashSet singles;
	
	/** The Errorhandler used by the actual Scanner*/
	private ErrorHandler errorhandler;
	/** The source code the Scanner works on*/
	private String sourceCode;
	/** The next position in <code>sourceCode</code> (column number)*/
	private int position;
	/** The actual line in <code>sourceCode</code>*/
	private int line;
	/** The actual column in <code>sourceCode</code>*/
	private int column;
	/** The character the Scanner just works on*/
	private char actChar;
		
	
	/**
	 * Initialize the Scanner with a source code <code>sc</code> and an ErrorHandler
	 * <code>e</code>. Furthermore set the <code>position</code>, the <code>line</code>
	 * and <code>column</code> to the right value in he source code (i.e. at the 
	 * position 1:0).
	 * 
	 * @param sc	The source code the Scanner should work on
	 * @param e		The ErrorHandler the Scanner should use
	 */
	public Scanner (String sc, ErrorHandler e) {
		
		sourceCode = sc;
		actChar = ' ';
		position = 0;
		line = 1;
		column = 0;
		errorhandler = e;
		
		initKeywords ();
		initLetters ();
		initDigits ();
		initSingles ();
				
	} // Scanner
	
	// getter methods
	/**
	 * Return the line of the actual <code>Scanner</code>.
	 * 
	 * @return	The line the Scanner is currently working in
	 */	
	public int getLine () {
		
		return line;
		
	} // getLine
	
	/**
	 * Return the column of the actual <code>Scanner</code>.
	 * 
	 * @return	The column the Scanner is currently working in
	 */
	public int getColumn () {
		
		return column;
		
	} // getColumn
	
	// scanner-methods
	/**
	 * Initialize the set of keywords.
	 */
	private void initKeywords () {
		
		keywords = new HashSet ();
		
		keywords.add (new SimpleToken (ARRAY));
		keywords.add (new SimpleToken (BEGIN));
		keywords.add (new SimpleToken (CONST));
		keywords.add (new SimpleToken (DIV));
		keywords.add (new SimpleToken (DO));
		keywords.add (new SimpleToken (ELSE));
		keywords.add (new SimpleToken (ELSIF));
		keywords.add (new SimpleToken (END));
		keywords.add (new SimpleToken (IF));
		keywords.add (new SimpleToken (MOD));
		keywords.add (new SimpleToken (MODULE));
		keywords.add (new SimpleToken (OF));
		keywords.add (new SimpleToken (OR));
		keywords.add (new SimpleToken (PROCEDURE));
		keywords.add (new SimpleToken (RECORD));
		keywords.add (new SimpleToken (REPEAT));
		keywords.add (new SimpleToken (THEN));
		keywords.add (new SimpleToken (TYPE));
		keywords.add (new SimpleToken (UNTIL));
		keywords.add (new SimpleToken (VAR));
		keywords.add (new SimpleToken (WHILE));
		
	} // initKeywords
	
	/**
	 * Initialize the set of letters.
	 */
	private void initLetters () {
		
		letters = new HashSet ();
		
		letters.add (new Character ('A'));
		letters.add (new Character ('B'));
		letters.add (new Character ('C'));
		letters.add (new Character ('D'));
		letters.add (new Character ('E'));
		letters.add (new Character ('F'));
		letters.add (new Character ('G'));
		letters.add (new Character ('H'));
		letters.add (new Character ('I'));
		letters.add (new Character ('J'));
		letters.add (new Character ('K'));
		letters.add (new Character ('L'));
		letters.add (new Character ('M'));
		letters.add (new Character ('N'));
		letters.add (new Character ('O'));
		letters.add (new Character ('P'));
		letters.add (new Character ('Q'));
		letters.add (new Character ('R'));
		letters.add (new Character ('S'));
		letters.add (new Character ('T'));
		letters.add (new Character ('U'));
		letters.add (new Character ('V'));
		letters.add (new Character ('W'));
		letters.add (new Character ('X'));
		letters.add (new Character ('Y'));
		letters.add (new Character ('Z'));
		letters.add (new Character ('a'));
		letters.add (new Character ('b'));
		letters.add (new Character ('c'));
		letters.add (new Character ('d'));
		letters.add (new Character ('e'));
		letters.add (new Character ('f'));
		letters.add (new Character ('g'));
		letters.add (new Character ('h'));
		letters.add (new Character ('i'));
		letters.add (new Character ('j'));
		letters.add (new Character ('k'));
		letters.add (new Character ('l'));
		letters.add (new Character ('m'));
		letters.add (new Character ('n'));
		letters.add (new Character ('o'));
		letters.add (new Character ('p'));
		letters.add (new Character ('q'));
		letters.add (new Character ('r'));
		letters.add (new Character ('s'));
		letters.add (new Character ('t'));
		letters.add (new Character ('u'));
		letters.add (new Character ('v'));
		letters.add (new Character ('w'));
		letters.add (new Character ('x'));
		letters.add (new Character ('y'));
		letters.add (new Character ('z'));
		
	} // initLetters
	
	/**
	 * Initialize the set of digits.
	 */
	private void initDigits () {
	
		digits = new HashSet ();
		
		digits.add (new Character ('0'));
		digits.add (new Character ('1'));
		digits.add (new Character ('2'));
		digits.add (new Character ('3'));
		digits.add (new Character ('4'));
		digits.add (new Character ('5'));
		digits.add (new Character ('6'));
		digits.add (new Character ('7'));
		digits.add (new Character ('8'));
		digits.add (new Character ('9'));

	} // initDigits
	
	/**
	 * Initialize the set of special characters.
	 */
	private void initSingles () {
		
		singles = new HashSet ();
		
		singles.add (new Character ('&'));
		singles.add (new Character ('*'));
		singles.add (new Character ('+'));
		singles.add (new Character ('-'));
		singles.add (new Character ('='));
		singles.add (new Character ('#'));
		singles.add (new Character (';'));
		singles.add (new Character (','));
		singles.add (new Character ('.'));
		singles.add (new Character (')'));
		singles.add (new Character ('['));
		singles.add (new Character (']'));
		singles.add (new Character ('~'));
		
	} // initSingles
	
	/**
	 * Get the next character from <code>sourceCode</code>. 
	 */
	private void nextChar () {
		
		char localChar = ' ';
		
		
		actChar = sourceCode.charAt (position);
		if (position < (sourceCode.length () - 1)) {	
			localChar = sourceCode.charAt (position + 1);
		} // if position < sourceCode.length ()
		if (actChar == '\n') {
			line ++;
			column = 0;
		} // if actChar == '\n'
		else if ((actChar == '\r') && (localChar == '\n')) {
			line ++;
			column = 0;
			position ++;
		} // if (actChar == '\r') && (localChar == '\n')
		else {
			column++;
		} // else 
		position++;
		
	} // nextChar
	
	/**
	 * Get the number starting with <code>actChar</code>. 
	 * 
	 * @return A {@link NumberToken} containing the value of the whole number
	 */
	private NumberToken number () {
		
		NumberToken token;	// returning Token
		int value;			// actual value of the number
		char lookahead;		// lookahead character
				
		
		value = actChar - '0';
		lookahead = sourceCode.charAt(position);
		while (digits.contains (new Character (lookahead))) {
			if (value > (Integer.MAX_VALUE - actChar + '0')/10) {
				errorhandler.error (ERR_NUMBER_TOO_HIGH, line, column);
			} // if value > (Integer.MAX_VALUE - actChar + '0')/10
			nextChar ();
			value = 10 * value + (actChar - '0');
			lookahead = sourceCode.charAt(position);
		} // while digits.contains (new Character (lookahead))
		token = new NumberToken (NUMBER, value);
		
		return token;
		
	} // number
	
	/**
	 * Get the identifier or keyword starting with <code>actChar</code>
	 * 
	 * @return	A {@link SimpleToken} if a keyword is found, an {@link IdentToken}
	 * 			otherwise
	 */
	private Token ident () {
		
		SimpleToken iterToken;	// actual Token of the iteration
		Iterator iter;		// iterator for keywords
		String value;		// actual value of the identifier
		char lookahead;		// lookahead character
		
		
		value = new String ();
		value = value + actChar;
		lookahead = sourceCode.charAt(position);
		while (letters.contains (new Character (lookahead)) || digits.contains (new Character (lookahead))) {
			nextChar ();
			value = value + actChar;
			lookahead = sourceCode.charAt(position);
		} // while letters.contains (new Character (lookahead)) || digits.contains (new Character (lookahead))
		
		// check wether the identifier is a keyword
		iter = keywords.iterator ();
		while (iter.hasNext () == true) {
			iterToken = (SimpleToken) iter.next ();
			if (value.equals (iterToken.stringRep ())) {
				return new SimpleToken (iterToken.getKind ());
			} // if value.equals (iterToken.value)
		} // while iter.hasNext () == true
		
		return new IdentToken (IDENT, value);
		
	} // ident
	
	/**
	 * Ignore any comment, i.e. read until a close comment (<code>*)</code>) is found or
	 * the end of <code>sourceCode</code> is reached
	 */
	private void comment () {
		
		boolean commentEnded;
		
		
		commentEnded = false;
		nextChar ();
		
		while (!commentEnded) {
			while ((actChar != '*') && (actChar != '\0')) {
				if (actChar == '(') {
					nextChar ();
					if (actChar == '*') {
						// recursively call comment to handle nested comments
						comment (); 
					} // if actChar == '*'
				} // if actChar == '('
				nextChar ();
			} // while actChar != '*' && actChar != '\0'
			
			if (actChar == '\0') {
				errorhandler.error (ERR_NOT_TERMINATED, line, column);
				return;
			} // if actChar == '\0'
			else {
				nextChar ();
				if (actChar == ')') {
						commentEnded = true;
				} // if actChar == ')'
			} // if actChar == '*'
		} // while !commentEnded
		
	} // comment
	
	/**
	 * Get the next token from <code>sourceCode</code>.
	 * 
	 * @return	An {@link IdentToken} if an identifier is found, a {@link NumberToken}
	 * 			if a number is found or a {@link SimpleToken} if a keyword or an 
	 * 			operation is found. If the end of <code>sourceCode</code> is reached, a
	 * 			{@link SimpleToken} "EMPTY" is returned.
	 */
	public Token nextToken () {
		
		Token token = new SimpleToken (EMPTY);
		char lookahead;
		
		
		do {
			nextChar ();
		} // do
		while ((actChar == ' ')||(actChar == '\t')||(actChar == '\r')||(actChar == '\n')) ;

		if (actChar == '\0') {
			return token;
		} // if actChar == '\0'
		   
		if (singles.contains (new Character (actChar))) {
			switch (actChar) {
				case '&': token = new SimpleToken (AND); break;
				case '*': token = new SimpleToken (TIMES); break;
				case '+': token = new SimpleToken (PLUS); break;
				case '-': token = new SimpleToken (MINUS); break;
				case '=': token = new SimpleToken (EQUAL); break;
				case '#': token = new SimpleToken (NOTEQUAL); break;
				case ';': token = new SimpleToken (SEMICOLON); break;
				case ',': token = new SimpleToken (COMMA); break;
				case '.': token = new SimpleToken (PERIOD); break;
				case ')': token = new SimpleToken (RPAREN); break;
				case '[': token = new SimpleToken (LBRAK); break;
				case ']': token = new SimpleToken (RBRAK); break;
				case '~': token = new SimpleToken (NOT); break;
			} // switch actChar
		} // if singles.contains (new Character (actChar))
		else if (digits.contains (new Character (actChar))) {
			token = number ();
		} // if digits.contains (new Character (actChar))
		else if (letters.contains (new Character (actChar))) {
			token = ident ();
		} // if letters.contains (new Character (actChar))
		else {
			switch (actChar) {
				case '<': { lookahead = sourceCode.charAt (position);
							if (lookahead == '=') {
								nextChar ();
								token = new SimpleToken (LEQ);
							} // if lookahead == '='
							else {
								token = new SimpleToken (LESS);
							} // else
							break;
				} // case actChar == '<'
				case '>': { lookahead = sourceCode.charAt (position);
							if (lookahead == '=') {
								nextChar ();
								token = new SimpleToken (GEQ);
							} // if lookahead == '='
							else {
								token = new SimpleToken (GREATER);
							} // else
							break;
				} // case actChar == '>'
				case ':': { lookahead = sourceCode.charAt (position);
							if (lookahead == '=') {
								nextChar ();
								token = new SimpleToken (BECOMES);
							} // if lookahead == '='
							else {
								token = new SimpleToken (COLON);
							} // else
							break;
				} // case actChar == ':'
				case '(': { lookahead = sourceCode.charAt (position);
							if (lookahead == '*') {
								nextChar ();
								comment ();
								if (actChar != '\0') {
									token = nextToken ();
								} // if actChar != '\0'
							} // if lookahead == '*'
							else {
								token = new SimpleToken (LPAREN);
							} // else
							break;
				} // case actChar == '('
				default : {
							errorhandler.error (ERR_INVALID_CHARACTER, line, column);
							if (actChar != '\0') {
								token = nextToken();
							} // if actChar != '\0' 
							break;
				} // default
			} // switch actChar
		} // else (special cases)
		
		return token;
		
	} // nextToken

} // class Scanner