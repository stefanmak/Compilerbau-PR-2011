//  
//	reimplementation of the minicompiler for the sw-praktikum
//	author: Stefan Leitner, Stefan Schauer
//	last change: 29.09.2004


package itec.minicompiler;

/**
 * The <code>ConstantsInterface</code> contains the constants used in all parts of
 * the compiler to ensure a better readability and understanding of the source code.
 * <p> 
 * To avoid an accidential misuse all constants have different values (except some 
 * values of error numbers which are equal to keywords). 
 * The ranges for these values are:
 * <blockquote><menu>
 * <li> 100- 199 Keywords
 * <li> 200- 299 Special Characters
 * <li> 300- 399 Numbers
 * <li> 400- 499 Identifiers
 * <li> 500- 599 Grammar Constants
 * <li>1000-1099 Lexical Errors
 * <li>1100-1199 Syntactical Errors
 * <li>1200-1299 Scoping Errors
 * </blockquote></menu>
 * 
 * @author Stefan Leitner
 * @author Stefan Schauer
 * @version 1.0 (29.09.2004)
 */
public interface ConstantsInterface {
		
	// keywords
	int KEYWORD = 100;
	int ARRAY = 101;
	int BEGIN = 102;
	int CONST = 103;
	int DIV = 104;
	int DO = 105;
	int ELSE = 106;
	int ELSIF = 107;
	int END = 108;
	int IF = 109;
	int MOD = 110;
	int MODULE = 111;
	int OF = 112;
	int OR = 113;
	int PROCEDURE = 114;
	int RECORD = 115;
	int REPEAT = 116;
	int THEN = 117;
	int TYPE = 118;
	int UNTIL = 119;
	int VAR = 120;
	int WHILE = 121;
	
	// special symbols
	int SINGLE = 200;
	int AND = 201;
	int TIMES = 202;
	int PLUS = 203;
	int MINUS = 204;
	int EQUAL = 205;
	int NOTEQUAL = 206;
	int SEMICOLON = 207;
	int COMMA = 208;
	int PERIOD = 209;
	int RPAREN = 210;
	int LBRAK = 211;
	int RBRAK = 212;
	int NOT = 213;
	int LEQ = 214;
	int LESS = 215;
	int GEQ = 216;
	int GREATER = 217;
	int BECOMES = 218;
	int COLON = 219;
	int LPAREN = 220;
	
	// numbers
	int NUMBER = 300;
	
	// identifiers
	int IDENT = 400;
	
	// grammar constants
	int ACTUALPARAMETERS = 501;
	int ARRAYTYPE = 502;
	int ASSIGNMENT = 503;
	int DECLARATIONS = 504;
	int EXPRESSION = 505;
	int FACTOR = 506;
	int FIELDLIST = 507;
	int FORMALPARAMETERS = 508;
	int FPSECTION = 509;
	int IDENTLIST = 510;
	int IFSTATEMENT = 511;
	int MINICOMP = 512;
	int PROCEDUREBODY = 513;
	int PROCEDURECALL = 514;
	int PROCEDUREDECLARATION = 515;
	int PROCEDUREHEADING = 516;
	int RECORDTYPE = 517;
	int REPEATSTATEMENT = 518;
	int SELECTOR = 519;
	int SIMPLEEXPRESSION = 520;
	int STATEMENT = 521;
	int STATEMENTSEQUENCE = 522;
	int TERM = 523;
	int TYP = 524;
	int WHILESTATEMENT = 525;
	
	// special grammar constants
	int EPSILON = 526;
	int EMPTY = 527;
			
	// error messages
	int ERR_NOT_TERMINATED = 1000;
	int ERR_INVALID_CHARACTER = 1001;
	int ERR_NUMBER_TOO_HIGH = 1002;
	int ERR_EXP_ARRAY = ARRAY;
	int ERR_EXP_BECOMES = BECOMES;
	int ERR_EXP_COLON = COLON;
	int ERR_EXP_COMMA = COMMA;
	int ERR_EXP_DO = DO;
	int ERR_EXP_END = END;
	int ERR_EXP_EQUAL = EQUAL;
	int ERR_EXP_IDENT =IDENT;
	int ERR_EXP_IF = IF;
	int ERR_EXP_LPAREN = LPAREN;
	int ERR_EXP_MODULE = MODULE;
	int ERR_EXP_OF = OF;
	int ERR_EXP_PERIOD = PERIOD;
	int ERR_EXP_PROCEDURE = PROCEDURE;
	int ERR_EXP_RBRAK = RBRAK;
	int ERR_EXP_RECORD = RECORD;
	int ERR_EXP_REPEAT = REPEAT;
	int ERR_EXP_RPAREN = RPAREN;
	int ERR_EXP_SEMICOLON = SEMICOLON;
	int ERR_EXP_THEN = THEN;
	int ERR_EXP_UNTIL = UNTIL;
	int ERR_EXP_WHILE = WHILE;
	int ERR_NOT_EXP_DECLARATIONS = 1100;
	int ERR_NOT_EXP_FACTOR = 1101;
	int ERR_NOT_EXP_STATEMENT = 1102;
	int ERR_NOT_EXP_TYPE = 1103;
	int ERR_INV_FACTOR = 1104;
	int ERR_INV_STATEMENT = 1105;
	int ERR_INV_TYPE = 1106;
	int ERR_TYPE_NOT_DECLARED = 1200;
	int ERR_TYPE_ALREADY_DECLARED = 1201;
	int ERR_TYPE_PREDEFINED = 1202;
	int ERR_TYPE_NOT_IN_RECORD = 1203;
	int ERR_TYPE_NOT_RECURSIVE = 1204;
	int ERR_TYPE_NOT_PROCEDURE = 1205;
	
} // interface ConstantsInterface