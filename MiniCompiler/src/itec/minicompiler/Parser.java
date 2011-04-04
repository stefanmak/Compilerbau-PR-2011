//  
//	reimplementation of the minicompiler for the 4h-praktikum
//	author: Stefan Leitner, Stefan Schauer
//	last change: 29.09.2004


package itec.minicompiler;

import java.util.HashSet;
import java.util.Vector;
import itec.minicompiler.SymbolTable.*;

/**
 * The <code>Parser</code> implements a top-down-parser for the PL0-language. It
 * uses the FIRST-Sets of the non-terminals and a lookahead Token, which is one 
 * step ahead of the actual position, to check for errors. WEAK- and SYNC-points 
 * are used for synchronization and a better resumption after errors. To get these 
 * WEAK- and SYNC-points the FOLLOW-Sets of the non-terminals are used.
 * 
 * @author Stefan Leitner
 * @author Stefan Schauer
 * @version 1.0 (29.09.2004)
 */
public class Parser implements ConstantsInterface {
	
	/** The FIRST-Sets of the grammar */
	private FirstSet firstset;
	/** The FOLLOW-Sets of the grammar */
	private FollowSet followset;
	
	/** The Sets of compare-operators defined in the grammar */
	private HashSet CompareOperators;
	/** The Set of line-operators defined in the grammar (+, -, OR) */
	private HashSet LineOperators;
	/** The Set of point-operators defined in the grammar (*, DIV, MOD, &) */
	private HashSet PointOperators;
	
	/** The ErrorHandler used by the actual Parser */
	private ErrorHandler errorhandler;
	/** The Scanner used by the actual Parser */
	private Scanner scanner;
	/** The SymbolTable the Parser builds up */
	private SymbolTable symboltable;
	
	/** The Token at the actual position of the Parser */
	private Token actualToken;
	/** The Token at the next position of the Parser. It is used to make decisions */
	private Token lookaheadToken;
	/** The line and column of the <code>actualToken</code> */
	private int actualLine, actualColumn;
	/** The line and column of the <code>lookaheadToken</code> */
	private int lookaheadLine, lookaheadColumn;
	

	/**
	 * Initialize the Parser with an ErrorHandler <code>e</code> and a Scanner 
	 * <code>s</code>. Furthermore initialize the FirstSet and FollowSet and the
	 * sets of compare-, line- and point-operators. Also the SymbolTable is
	 * initialized with the predefined types, constants and procedures.
	 * 
	 * @param e	The ErrorHandler the Parser should use
	 * @param s	The Scanner the Parser gets it's information from
	 */
	public Parser (ErrorHandler e, Scanner s) {
		
		BasicType itype, btype;
		StructuralSymbol ssym;
		ValuedSymbol vsym;
		AddressedSymbol asym;


		firstset = new FirstSet ();
		followset = new FollowSet ();
		
		// initialize the compare-operators
		CompareOperators = new HashSet ();
		CompareOperators.add (new Integer (EQUAL));
		CompareOperators.add (new Integer (NOTEQUAL));
		CompareOperators.add (new Integer (LESS));
		CompareOperators.add (new Integer (LEQ));
		CompareOperators.add (new Integer (GREATER));
		CompareOperators.add (new Integer (GEQ));
		
		// initialze the line-operators
		LineOperators = new HashSet ();
		LineOperators.add (new Integer (PLUS));
		LineOperators.add (new Integer (MINUS));
		LineOperators.add (new Integer (OR));
		
		// initialize the point-operators
		PointOperators = new HashSet ();
		PointOperators.add (new Integer (TIMES));
		PointOperators.add (new Integer (DIV));
		PointOperators.add (new Integer (MOD));
		PointOperators.add (new Integer (AND));

		errorhandler = e;
		scanner = s;

		// initializing the symboltable with the predefined Symbols		
		itype = new BasicType ();
		itype.setInteger ();
		btype = new BasicType ();
		btype.setBoolean ();
		
		ssym = new StructuralSymbol ("universe");
		ssym.setUniverse();
		symboltable = new SymbolTable (ssym);
		
		symboltable.openScope ();
		putSymbol (new TypedSymbol ("INTEGER", itype));
		putSymbol (new TypedSymbol ("BOOLEAN", btype));
		
		vsym = new ValuedSymbol ("TRUE", btype, 1);
		putSymbol (vsym);
		
		vsym = new ValuedSymbol ("FALSE", btype, 0);
		putSymbol (vsym);
		
		asym = new AddressedSymbol ("Read", null, -1);
		asym.setProcedure ();
		putSymbol (asym);
		symboltable.openScope ();
		asym = new AddressedSymbol ("x", itype, -1);
		asym.setParameterInOut ();
		putSymbol (asym);
		symboltable.closeScope ();
		
		asym = new AddressedSymbol ("Write", null, -1);
		asym.setProcedure ();
		putSymbol (asym);
		symboltable.openScope ();
		asym = new AddressedSymbol ("x", itype, -1);
		asym.setParameterIn ();
		putSymbol (asym);
		symboltable.closeScope ();
		
		asym = new AddressedSymbol ("WriteChar", null, -1);
		asym.setProcedure ();
		putSymbol (asym);
		symboltable.openScope ();
		asym = new AddressedSymbol ("x", itype, -1);
		asym.setParameterIn ();
		putSymbol (asym);
		symboltable.closeScope ();
		
		asym = new AddressedSymbol ("WriteHex", null, -1);
		asym.setProcedure ();
		putSymbol (asym);
		symboltable.openScope ();
		asym = new AddressedSymbol ("x", itype, -1);
		asym.setParameterIn ();
		putSymbol (asym);
		symboltable.closeScope ();
		
		actualLine = 1;
		actualColumn = 1;
		lookaheadLine = 1;
		lookaheadColumn = 1;
		
	} // Parser

	/**
	 * Call the ErrorHandler with the error number <code>n</code>.
	 * 
	 * @param n	The error number of the error which occured
	 */
	private void error (int n) {
		
		errorhandler.error (n, actualLine, actualColumn);
		
	} // error
	
	/**
	 * Get the next Token from the Scanner. 
	 */
	private void get () {
		// get the next token from the scanner
		
		actualToken = lookaheadToken;
		actualLine = lookaheadLine;
		actualColumn = lookaheadColumn;
		lookaheadToken = scanner.nextToken ();
		lookaheadLine = scanner.getLine();
		lookaheadColumn = scanner.getColumn();
		
	} // get
	
	/**
	 * Check wether the next Token is the one which is needed or not. If yes, just
	 * read along. Otherwise go to the next SYNC-point using the FOLLOW-Set of the
	 * <code>nonTerminal</code>.
	 * 
	 * @param kind			The kind of the expected Token
	 * @param nonTerminal	The non-terminal for which a SYNC-point should be reached
	 */
	private void expect (int kind, int nonTerminal) {
		
		HashSet follow;
		
		if (lookaheadToken.getKind () != EMPTY) { 
			if (lookaheadToken.getKind () == kind) {
				get ();
			} // if lookaheadToken.getKind () == kind
			else {
				error (kind);
				follow = followset.getFollow (nonTerminal);
				while (!follow.contains (new Integer (lookaheadToken.getKind ()))) {
					get();
				} // while !follow.contains (new Integer (lookaheadToken.getKind ()))
			} // if lookaheadToken.getKind () != kind
		} // if lookaheadToken.getKind () != EMPTY
				
	} // expect
	
	/**
	 * Check wether the next Token is the one which is needed or not. If yes, just
	 * read along. Otherwise throw an error but act like the token was there (i.e.
	 * no synchronization has to be done).
	 * 
	 * @param kind			The kind of the expected Token
	 */
	private void expectWeak (int kind) {
		
		if (lookaheadToken.getKind () != EMPTY) {
			if (lookaheadToken.getKind () == kind) {
				get ();
			} // if lookaheadToken.getKind () == kind
			else {
				error (kind);
			} // if lookaheadToken.getKind () != kind
		} // if lookaheadToken.getKind () != EMPTY	
		
	} // expectWeak

	/**
	 * Check wether a seperator (i.e <code>,</code> or <code>;</code>) is present.
	 * If yes, just read along. Otherwise check if the seperator is really needed
	 * or if the statement is finished anyway.
	 * 
	 * @param kind			The kind of the expected Token
	 * @param nonTerminal	The non-terminal
	 * @return				True if the seperator is needed or the 
	 * 						<code>lookaheadToken</code> is not the expected one, 
	 * 						False otherwise
	 */
	private boolean weakSeparator (int kind, int nonTerminal) {
		
		HashSet follow;
		
		if (lookaheadToken.getKind () != EMPTY) {
			follow = followset.getFollow (nonTerminal);
			if (lookaheadToken.getKind () == kind) {
				get();
				if (follow.contains (new Integer (lookaheadToken.getKind ()))) {
					return false;
				} // if follow.contains (new Integer (lookaheadToken.getKind ()))
				return true;
			} // if lookaheadToken.getKind () == kind
			else if (follow.contains (new Integer (lookaheadToken.getKind ()))) {
				return false;
			} // if follow.contains (new Integer (lookaheadToken.getKind ()))
			else {
				error (kind);
				return true;
			} // else
		} // if lookaheadToken.getKind () != EMPTY
		else {
			return false;
		} // else
		
	} // weakSeparator

	/**
	 * Return wether the <code>lookaheadToken</code> is in the FIRST-Set of
	 * <code>nonTerminal</code>
	 * 
	 * @param nonTerminal	The non-terminal for the check
	 * @return				True if the <code>lookaheadToken</code> is in the
	 * 						FIRST-Set of <code>nonTerminal</code>, False
	 * 						otherwise
	 */
	private boolean isInFirst (int nonTerminal) {
		
		HashSet first;
		
		first = firstset.getFirst (nonTerminal);
		return first.contains (new Integer (lookaheadToken.getKind ()));
		
	} // isInFirst

	/**
	 * Insert <code>sym</code> in the actual scope in the <code>SymbolTable</code> if
	 * not already present, throw an error otherwise
	 * 
	 * @param sym	The Symbol which should be inserted
	 */
	private void putSymbol (Symbol sym) {
		
		if (symboltable.isPresent (sym)) {		
			error (ERR_TYPE_ALREADY_DECLARED);
		} // if symboltable.isPresent (sym)
		else {
			symboltable.putSymbol (sym);
		} // else
		
	} // putSymbol

	/**
	 * Parse the whole source code
	 */
	public void parse() {
		
		lookaheadToken = null;
		get ();
		pl0 ();
		symboltable.closeScope ();
		
		System.out.println (symboltable);

	} // parse

	/**
	 * Implements the grammer-rule:
	 * <p><code>
	 * Pl0 -> "MODULE" ident ";" Declarations ["BEGIN" StatementSequence]
	 * 		  "END" ident "."
	 * </code>
	 */
	private void pl0 () {
		
		StructuralSymbol ssym;
		IdentToken itoken;
		
		
		expect (MODULE, MINICOMP);
		expect (IDENT, MINICOMP);
		if (actualToken instanceof IdentToken) {
			itoken = (IdentToken) actualToken;
			ssym = new StructuralSymbol (itoken.getValue ());
			ssym.setModule ();
		} // if actualToken instanceof IdentToken
		else {
			ssym = new StructuralSymbol (null);
		} // else
		putSymbol (ssym);
		symboltable.openScope ();
		expectWeak (SEMICOLON);
		declarations ();
		if (lookaheadToken.getKind () == BEGIN) {
			get ();
			statementSequence ();
		} // if lookaheadToken.getKind () == BEGIN
		expect (END, MINICOMP);
		if (lookaheadToken.getKind () == EMPTY) {
			error(IDENT);
		} // if lookaheadToken.getKind () == EMPTY
		expect (IDENT, MINICOMP);
		if (lookaheadToken.getKind () == EMPTY) {
			error(PERIOD);
		} // if lookaheadToken.getKind () == EMPTY
		expectWeak (PERIOD);
		
	} // miniCompiler

	/**
	 * Implements the grammar-rule:
	 * <p><code>
	 * Declarations -> ["CONST" {ident "=" Expression} ";"]
	 * 				   ["TYPE" {ident "=" Type ";"}]
	 * 				   ["VAR" Identlist ":" Type ";"]
	 * 				   {ProcedureDeclaration ";"}
	 * </code>
	 */
	private void declarations () {
	
		ValuedSymbol vsym;
		TypedSymbol tsym;
		AddressedSymbol asym;
		Type t;
		Vector identlist = new Vector ();
		String fe;
		
		
		while (!(isInFirst (DECLARATIONS))) {
			if (lookaheadToken.getKind () == EMPTY) {
				return;
			} // if lookaheadToken.getKind () == EMPTY
			error (ERR_NOT_EXP_DECLARATIONS); 
			get ();
		} // while !(isInFirst (DECLARATIONS))
		if (lookaheadToken.getKind () == CONST) {
			get ();
			while (lookaheadToken.getKind () == IDENT) {
				get ();
				IdentToken itoken;
				itoken = (IdentToken) actualToken;
				vsym = new ValuedSymbol (itoken.getValue (), null, -1);	
				putSymbol (vsym);
				expect (EQUAL, DECLARATIONS);
				t = expression ();
				vsym.setType (t);
				expectWeak (SEMICOLON);
			} // while lookaheadToken.getKind () == IDENT
		} // if lookaheadToken.getKind () == CONST
		if (lookaheadToken.getKind () == TYPE) {
			get ();
			while (lookaheadToken.getKind () == IDENT) {
				get ();
				IdentToken itoken;
				itoken = (IdentToken) actualToken;
				tsym = new TypedSymbol (itoken.getValue (), null);	
				putSymbol (tsym);
				expect (EQUAL, DECLARATIONS);
				tsym.setType (type (tsym));
				expectWeak (SEMICOLON);
			} // while lookaheadToken.getKind () == IDENT
		} // if lookaheadToken.getKind () == TYPE
		if (lookaheadToken.getKind () == VAR) {
			get ();
			while (lookaheadToken.getKind () == IDENT) {
				asym = null;
				identlist = identList ();
				for (int i = 0; i < identlist.size(); i++) {
					asym = new AddressedSymbol ((String) identlist.get(i), null, -1);
					asym.setVariable ();
					putSymbol (asym);
				} // for
				expect (COLON, DECLARATIONS);
				if (symboltable.isPredefined (asym)) {
					error (ERR_TYPE_PREDEFINED);
				} // if symboltable.isPredefined (asym)
				else {
					asym = (AddressedSymbol) symboltable.getSymbol (new Symbol ((String) identlist.get(0)));
					t = type (asym);
					do {
						asym.setType (t);
						asym = (AddressedSymbol) asym.getNext ();
					} // do
					while (asym != null);				
				} // else 
				expectWeak (SEMICOLON);
			} // while lookaheadToken.getKind () == IDENT
		} // if lookaheadToken.getKind () == VAR
		while (lookaheadToken.getKind () == PROCEDURE) {
			procedureDeclaration ();
			expectWeak (SEMICOLON);
		} // while lookaheadToken.getKind () == PROCEDURE
		
	} // declarations

	/**
	 * Implements the grammar-rule:
	 * <p><code>
	 * Type -> (ident|ArrayType|RecordType)
	 * </code>
	 * 
	 * @param sym	The <code>Symbol</code> the type should be determined for.
	 * 				It is mainly used to check for recursive declarations
	 * @return		The <code>Type</code> of <code>s</code>, if it could be
	 * 				determined, an undefined <code>BasicType</code> otherwise
	 */
	private Type type (Symbol sym) {
		
		TypedSymbol tsym;
		BasicType bt;
		
		bt = new BasicType ();		
		while (!isInFirst (TYP)) {
			error (ERR_NOT_EXP_TYPE);
			if (lookaheadToken.getKind () == EMPTY) {
				return bt;
			} // if lookaheadToken.getKind () == EMPTY 
			get ();
		} // if !isInFirst (TYP)
		if (lookaheadToken.getKind () == IDENT) {
			get ();
			IdentToken itoken;
			itoken = (IdentToken) actualToken;
			tsym = (TypedSymbol) symboltable.getSymbol (new Symbol (itoken.getValue ()));	
			if (tsym != null) {
				if (!(tsym.equals (sym))) {
					return tsym.getType ();
				} // if !(tsym.equals (sym))
				else {
					error(ERR_TYPE_NOT_RECURSIVE);
				} // else
			} // if sym != null
			else {
				error (ERR_TYPE_NOT_DECLARED);
			} // else
		} // if lookaheadToken.getKind () == IDENT
		else if (lookaheadToken.getKind () == ARRAY) {
			return arrayType (sym);
		} // if lookaheadToken.getKind () == ARRAY
		else if (lookaheadToken.getKind () == RECORD) {
			return recordType (sym);
		} // if lookaheadToken.getKind () == RECORD
		else error (ERR_INV_TYPE);
		
		return bt;
				
	} // type

	/**
	 * Implements the grammar-rule:
	 * <p><code>
	 * ArrayType -> "ARRAY" Expression "OF" Type
	 * </code>
	 * 
	 * @param sym	The <code>Symbol</code> the type should be determined for. 
	 * 				It is mainly used to check for recursive declarations
	 * @return		The <code>ArrayType</code> of <code>s</code>
	 */
	private Type arrayType (Symbol sym) {
		
		ArrayType at;
		Type t;
		
		
		expect (ARRAY, ARRAYTYPE);
		at = new ArrayType (null, -1);
		t = expression ();
		expect (OF, ARRAYTYPE);
		at.setBaseType (type (sym));
		
		return at;
		
	} // arrayType

	/**
	 * Implements the grammar-rule:
	 * <p><code>
	 * RecordType -> "RECORD" FieldList {";" FieldList} "END"
	 * </code>
	 * 
	 * @param sym	The <code>Symbol</code> the type should be determined for. 
	 * 				It is mainly used to check for recursive declarations
	 * @return		The <code>RecordType</code> of <code>s</code>
	 */
	private Type recordType (Symbol sym) {
		
		RecordType rt;
		
		expect (RECORD, RECORDTYPE);
		rt = new RecordType (null);
		symboltable.openScope ();
		fieldList (sym);
		while (weakSeparator (SEMICOLON, FIELDLIST)) {
			fieldList (sym);
		} // while weakSeparator (SEMICOLON, FIELDLIST)
		symboltable.closeScope ();
		
		rt.setFields (sym.getLocal ().getNext ());
		expect (END, RECORDTYPE);
		
		return rt;
	
	} // recordType

	/**
	 * Implements the grammar-rule:
	 * <p><code>
	 * FieldList -> IdentList ":" Type
	 * </code>
	 * 
	 * @param sym	The <code>Symbol</code> the type should be determined for. 
	 * 				It is mainly used to check for recursive declarations
	 */
	private void fieldList (Symbol sym) {
		
		AddressedSymbol asym;
		Type t;
		Vector identlist = new Vector ();
		
		
		if (lookaheadToken.getKind () == IDENT) {
			identlist = identList ();
			for (int i = 0; i < identlist.size(); i++) {
				asym = new AddressedSymbol ((String) identlist.get (i), null, -1);
				asym.setVariable ();
				putSymbol (asym);
			} // for 
			expect (COLON, FIELDLIST);
			t = type (sym);
			asym = (AddressedSymbol) symboltable.getSymbol (new Symbol ((String) identlist.get (0)));
			do {
				asym.setType (t);
				asym = (AddressedSymbol) asym.getNext ();
			} // do
			while (asym != null);
		} // if lookaheadToken.getKind () == IDENT
	
	} // fieldList

	/**
	 * Implements the grammar-rule:
	 * <p><code>
	 * IdentList -> ident {"," ident}
	 * </code>
	 * 
	 * @return	A Vector of String-values, which are the several idents in the
	 * 			IdentList
	 */
	private Vector identList () {
		
		IdentToken itoken;
		Vector list = new Vector ();
		
		
		expect (IDENT, IDENTLIST);
		if (actualToken instanceof IdentToken) {
			itoken = (IdentToken) actualToken;
			list.add (itoken.getValue ());	
		} // if actualToken instanceof IdentToken
		while (weakSeparator (COMMA, IDENTLIST)) {
			expect (IDENT, IDENTLIST);
			if (actualToken instanceof IdentToken) {
				itoken = (IdentToken) actualToken;
				list.add (itoken.getValue ());
			} // if actualToken instanceof IdentToken
		} // while weakSeparator (COMMA, IDENTLIST)
		
		return list;
		
	} // identList

	/**
	 * Implements the grammar-rule:
	 * <p><code>
	 * ProcedureDeclaration -> ProcedureHeading ";" ProcedureBody ident
	 * </code>
	 */
	private void procedureDeclaration () {
		
		procedureHeading ();
		expectWeak (SEMICOLON);
		procedureBody ();
		expect (IDENT, PROCEDUREDECLARATION);
		symboltable.closeScope ();
		
	} // procedureDeclaration

	/**
	 * Implements the grammar-rule:
	 * <p><code>
	 * ProcedureHeading -> "PROCEDURE" ident "(" [FormalParameters] ")"
	 * </code>
	 */
	private void procedureHeading () {
		
		AddressedSymbol asym;
		IdentToken itoken;
		
		
		expect (PROCEDURE, PROCEDUREHEADING);
		expect (IDENT, PROCEDUREHEADING);
		if (actualToken instanceof IdentToken) {
			itoken = (IdentToken) actualToken;
			asym = new AddressedSymbol (itoken.getValue (), null, -1);
			asym.setProcedure ();
		} // if actualToken instanceof IdentToken
		else {
			asym = new AddressedSymbol (null, null, -1);
		} // else
		putSymbol (asym);
		symboltable.openScope ();
		expect (LPAREN, PROCEDUREHEADING);
		if (isInFirst (FORMALPARAMETERS)) {
			formalParameters ();
		} // if isInFirst (FORMALPARAMETERS)
		expect (RPAREN, PROCEDUREHEADING);
		
	} // ProcedureHeading
	
	/**
	 * Implements the grammar-rule:
	 * <p><code>
	 * ProcedureBody -> Declarations ["BEGIN" StatementSequence] "END"
	 * </code>
	 */
	private void procedureBody () {
		
		declarations ();
		if (lookaheadToken.getKind () == BEGIN) {
			get ();
			statementSequence ();
		} // if lookaheadToken.getKind () == BEGIN
		expect (END, FORMALPARAMETERS);
		
	} // procedureBody

	/**
	 * Implements the grammar-rule:
	 * <p><code>
	 * FormalParameters -> FPSection (";" FPSection}
	 * </code>
	 */
	private void formalParameters () {
		
		fpSection ();
		while (weakSeparator (SEMICOLON, FPSECTION)) {
			fpSection ();
		} // while weakSeparator (SEMICOLON, FPSECTION)
		
	} // formalParameters

	/**
	 * Implements the grammar-rule:
	 * <p><code>
	 * FPSection -> "VAR" IdentList ":" Type
	 * </code>
	 */
	private void fpSection () {
		
		AddressedSymbol asym;		
		Type t;
		Vector identlist = new Vector ();
		String fe;
		boolean io = false;
		
		
		if (lookaheadToken.getKind () == VAR) {
			get ();
			io = true;
		} // if lookaheadToken.getKind () == VAR
		identlist = identList ();
		for (int i = 0; i < identlist.size(); i++) {
			asym = new AddressedSymbol ((String) identlist.get (i), null, -1);
			if (io) {
				asym.setParameterInOut ();
			} // if io == true
			else {
				asym.setParameterIn ();
			} // if io== false
			putSymbol (asym);
		} // for	
		expect (COLON, FPSECTION);
		asym = (AddressedSymbol) symboltable.getSymbol (new Symbol ((String) identlist.get (0)));
		t = type (asym);
		do {
			asym.setType (t);
			asym = (AddressedSymbol) asym.getNext ();
		} // do
		while (asym != null);
				
	} // fpSection

	/**
	 * Implements the grammar-rule:
	 * <p><code>
	 * StatementSequence -> Statement {";" Statement}
	 * </code>
	 */
	private void statementSequence () {
		
		statement ();
		while (weakSeparator (SEMICOLON, STATEMENT)) {
			statement ();
		} // while weakSeparator (SEMICOLON, STATEMENT)
		
	} // statement

	/**
	 * Implements the grammar-rule:
	 * <p><code>
	 * Statement -> [(ident(Assignment|ProcedureCall))
	 * 				|IfStatement
	 * 				|WhileStatement
	 * 				|RepeatStatement]
	 * </code>
	 */
	private void statement () {
		
		Symbol sym;
		

		while (!isInFirst (STATEMENTSEQUENCE)) {
			error (ERR_NOT_EXP_STATEMENT);
			if (lookaheadToken.getKind () == EMPTY) {
				return;
			} // if lookaheadToken.getKind () == EMPTY 
			get ();
		} // while !isInFirst (STATEMENTSEQUENCE)
		if (isInFirst(STATEMENT)) {
			if (lookaheadToken.getKind () == IDENT) {
				get ();
				IdentToken itoken;
				itoken = (IdentToken) actualToken;
				sym = symboltable.getSymbol (new Symbol (itoken.getValue ()));
				if (sym == null) {
					error (ERR_TYPE_NOT_DECLARED);
				} // if sym == null
				if (isInFirst (ASSIGNMENT)) {
					assignment (sym);
				} // if isInFirst (ASSIGNMENT)
				else if (isInFirst (PROCEDURECALL)) {
					if (sym instanceof AddressedSymbol) {
						AddressedSymbol asym;
						asym = (AddressedSymbol) sym;
						if (!(asym.isProcedure ())) {
							error (ERR_TYPE_NOT_PROCEDURE);
						} // if !(asym.isProcedure ())	
					} // if sym instanceof AddressedSymbol
					procedureCall ();
				} // isInFirst (PROCEDURECALL)
				else error (ERR_INV_STATEMENT);
			} // if lookaheadToken.getKind () == IDENT
			else if (lookaheadToken.getKind () == IF) {
				ifStatement ();
			} // if lookaheadToken.getKind () == IF
			else if (lookaheadToken.getKind () == WHILE) {
				whileStatement ();
			} // if lookaheadToken.getKind () == WHILE
			else if (lookaheadToken.getKind () == REPEAT){
				repeatStatement ();
			} // lookaheadToken.getKind () == REPEAT
		} // if (isInFirst (STATEMENT))
	
	} // statement

	/**
	 * Implements the grammar-rule:
	 * <p><code>
	 * Assignment -> Selector ":=" Expression
	 * </code>
	 * 
	 * @param sym	The <code>Symbol</code> on the lefthand side of the Assignment
	 * 				(i.e. the <code>Symbol</code> the Selector should be computed for
	 * 				and the Expression should be assigned to)
	 */
	private void assignment (Symbol sym) {
		
		Symbol s;
		Type rhs;
		
		
		s = selector (sym);
		expect (BECOMES, ASSIGNMENT);
		rhs = expression ();
		
	} // assignment

	/**
	 * Implements the grammar-rule:
	 * <p><code>
	 * Selector -> {("." ident)|("[" Expression "]")}
	 * </code>
	 * 
	 * @param sym	The <code>Symbol</code> on the lefthand side of the Assignment
	 * @return		The very right <code>Symbol</code> of the Selector
	 * 				(i.e. either the <code>Symbol</code> in the RECORD identified by
	 * 				ident or an <code>AddressedSymbol</code> with the very basetype
	 * 				of the ARRAY) or <code>sym</code> itself, if the Selector is empty
	 */
	private Symbol selector (Symbol sym) {
		
		Symbol s;
		TypedSymbol tsym;
		Type t;
		
		
		while (lookaheadToken.getKind () == PERIOD || lookaheadToken.getKind () == LBRAK) {
			tsym = (TypedSymbol) sym;
			if (lookaheadToken.getKind () == PERIOD) {
				RecordType rt;
				get ();
				expect (IDENT, SELECTOR);
				if ((tsym != null) && (tsym.getType () instanceof RecordType)) {
					rt = (RecordType) tsym.getType ();
					s = rt.getFields ();
					IdentToken itoken;
					if (actualToken instanceof IdentToken) {
						itoken = (IdentToken) actualToken;
						while ((s != null) && (!s.getName ().equals (itoken.getValue ()))) {
							s = s.getNext ();
						} // if s != null && !s.getName ().equals (itoken.getValue ())	
					} // if actualToken instanceof IdentToken
					else {
						s = null;
					} // else
					if (s == null) {
						error (ERR_TYPE_NOT_IN_RECORD);
					} // if s == null
					sym = s;
				} // if tsym != null && tsym.getType () instanceof RecordType
			} // if lookaheadToken.getKind () == PERIOD
			else {
				get ();
				t = expression ();
				if ((tsym != null) && (tsym.getType () instanceof ArrayType)) {
					ArrayType at;
					AddressedSymbol asym;
					at = (ArrayType) tsym.getType ();
					asym = new AddressedSymbol ("ARRAY", at.getBaseType (), -1);
					sym = asym;
				} // if sym != null && sym.getType () instanceof ArrayType
				else {
					error (ERR_TYPE_NOT_DECLARED);
				} // if sym == null || !sym.getType () instanceof ArrayType
				expect (RBRAK, SELECTOR);
			} // lookaheadToken.getKind () == LBRAK
		} // while lookaheadToken.getKind () == PERIOD OR lookaheadToken.getKind () == LBRAK
		
		return sym;
		
	} // selector

	/**
	* Implements the grammar-rule:
	* <p><code>
	* ProcedureCall -> "(" [ActualParameters] ")"
	* </code>
	*/
	private void procedureCall () {
		
		expect (LPAREN, PROCEDURECALL);
		if (isInFirst(ACTUALPARAMETERS)) {
			actualParameters ();
		} // if isInFirst(ACTUALPARAMETERS)
		expect (RPAREN, PROCEDURECALL);
	
	} // procedureCall
	
	/**
	 * Implements the grammar-rule:
	 * <p><code>
	 * ActualParameters -> Expression {"," Expression}
	 * </code>
	 */
	private void actualParameters () {
		
		Type t;
		
		
		t = expression ();
		while (weakSeparator (COMMA, EXPRESSION)) {
			t = expression ();
		} // while weakSeparator (COMMA, EXPRESSION)
		
	} // actualParameters

	/**
	 * Implements the grammar-rule:
	 * <p><code>
	 * IfStatement -> "IF" Expression "THEN" StatementSequence
	 * 				  {"ELSIF" Expression "THEN" StatementSequence}
	 * 				  ["ELSE" StatementSequence] "END"
	 * </code>
	 */
	private void ifStatement () {
		
		Type t;
		
		
		expect (IF, IFSTATEMENT);
		t = expression ();
		expect (THEN, IFSTATEMENT);
		statementSequence ();
		while (lookaheadToken.getKind () == ELSIF) {
			get ();
			t = expression ();
			expect (THEN, IFSTATEMENT);
			statementSequence ();
		} // while lookaheadToken.getKind () == ELSIF
		if (lookaheadToken.getKind () == ELSE) {
			get ();
			statementSequence ();
		} // if lookaheadToken.getKind () == ELSE
		expect (END, IFSTATEMENT);
		
	} // ifStatement

	/**
	 * Implements the grammar-rule:
	 * <p><code>
	 * WhileStatement -> "WHILE" Expression "DO" StatementSequence "END"
	 * </code>
	 */
	private void whileStatement () {
		
		Type t;
		
		
		expect (WHILE, WHILESTATEMENT);
		t = expression ();
		expect (DO, WHILESTATEMENT);
		statementSequence ();
		expect (END, WHILESTATEMENT);
		
	} // whileStatement

	/**
	 * Implements the grammar-rule:
	 * <p><code>
	 * RepeatStaement -> "REPEAT" StatementSequence "UNTIL" Expression
	 * </code>
	 */
	private void repeatStatement () {
		
		Type t;
		
		
		expect (REPEAT, REPEATSTATEMENT);
		statementSequence ();
		expect (UNTIL, REPEATSTATEMENT);
		t = expression ();
		
	} // repeatStatement

	/**
	 * Implements the grammar-rule:
	 * <p><code>
	 * Expression -> SimpleExpression [("="|"#"|"<"|"<="|">"|">=") SimpleExpression]
	 * </code>
	 * 
	 * @return	The <code>Type</code> of the lefthand side of the Expression
	 * 			(because not typechecking is made, any righthand side can be ignored)
	 */
	private Type expression () {
		
		Type lhs;
		Type rhs;
		
		
		lhs = simpleExpression ();
		if (CompareOperators.contains (new Integer (lookaheadToken.getKind ()))) {
			switch (lookaheadToken.getKind ()) {
				case EQUAL: {
					get ();
					break;
				} // case lookaheadToken.getKind () == EQUAL
				case NOTEQUAL: {
					get ();
					break;
				} // case lookaheadToken.getKind () == NOTEQUAL
				case LESS: {
					get ();
					break;
				} // case lookaheadToken.getKind () == LESS
				case LEQ: {
					get ();
					break;
				} // cas lookaheadToken.getKind () == LEQ
				case GREATER: {
					get ();
					break;
				} // case lookaheadToken.getKind () == GREATER
				case GEQ: {
					get ();
					break;
				} // case lookaheadToken.getKind () == GEQ
			} // switch lookaheadToken.getKind ()
			rhs = simpleExpression ();
		} // if CompareOperators.contains (new Integer (lookaheadToken.getKind ()))
		
		return lhs;
		
	} // expression

	/**
	 * Implements the grammar-rule:
	 * <p><code>
	 * SimpleExpression -> ["+"|"-"] Term {("+"|"-"|"OR") Term}
	 * </code>
	 * 
	 * @return	The <code>Type</code> of the lefthand side of the SimpleExpression
	 * 			(because not typechecking is made, any righthand side can be ignored)
	 */
	private Type simpleExpression () {
		
		Type lhs;
		Type rhs;
		
		
		if (lookaheadToken.getKind () == PLUS || lookaheadToken.getKind () == MINUS) {
			if (lookaheadToken.getKind () == PLUS) {
				get ();
			} // if lookaheadToken.getKind () == PLUS
			else {
				get ();
			} // if lookaheadToken == MINUS
		} // if lookaheadToken.getKind () == PLUS OR lookaheadToken.getKind () == MINUS
		lhs = term ();
		while (LineOperators.contains (new Integer (lookaheadToken.getKind ()))) {
			if (lookaheadToken.getKind () == PLUS) {
				get ();
			} // if lookaheadToken.getKind () == PLUS
			else if (lookaheadToken.getKind () == MINUS) {
				get ();
			} // if lookaheadToken.getKind () == MINUS
			else {
				get ();
			} // lookaheadToken.getKind () == OR
			rhs = term ();
		} // while LineOperators.contains (new Integer (lookaheadToken.getKind ()))
		
		return lhs;
		
	} // simpleExpression

	/**
	 * Implements the grammar-rule:
	 * <p><code>
	 * Term -> Factor {("*"|"DIV"|"MOD"|"&") Factor}
	 * </code>
	 * 
	 * @return	The <code>Type</code> of the lefthand side of the Term
	 * 			(because not typechecking is made, any righthand side can be ignored)
	 */
	private Type term () {
		
		Type lhs;
		Type rhs;
		
		
		lhs = factor ();
		while (PointOperators.contains (new Integer (lookaheadToken.getKind ()))) {
			if (lookaheadToken.getKind () == TIMES) {
				get ();
			} // if lookaheadToken.getKind () == TIMES
			else if (lookaheadToken.getKind () == DIV) {
				get ();
			} // if lookaheadToken.getKind () == DIV
			else if (lookaheadToken.getKind () == MOD) {
				get ();
			} // if lookaheadToken.getKind () == MOD
			else {
				get ();
			} // lookaheadToken == AND
			rhs = factor ();
		} // while PointOperators.contains (new Integer (lookaheadToken.getKind ()))
		
		return lhs;
		
	} // term

	/**
	 * Implements the grammar-rule:
	 * <p><code>
	 * Factor -> ((ident Selector)|integer|("("Expression")")|("~" Factor))
	 * </code>
	 * 
	 * @return	The <code>Type</code> of the <code>Symbol</code> determined in Factor
	 * 			(i.e. the <code>Type</code> of the ident, the integer, which is
	 * 			the <code>BasicType</code> INTEGER, the Expression or the Factor)
	 */
	private Type factor () {
		
		Symbol sym, s;
		BasicType bt;
		Type t = new BasicType ();
		
		
		while (!isInFirst (FACTOR)) {
			error (ERR_NOT_EXP_FACTOR); 
			if (lookaheadToken.getKind () == EMPTY) {
				return t;
			} // if lookaheadToken.getKind () == EMPTY
			get ();
		} // while !isInFirst (FACTOR)
		if (lookaheadToken.getKind () == IDENT) {
			get ();
			IdentToken itoken;
			itoken = (IdentToken) actualToken;
			sym = symboltable.getSymbol (new Symbol (itoken.getValue ()));
			if (sym == null) {
				error (ERR_TYPE_NOT_DECLARED);
				return t;
			} // if s == null
			s = selector (sym);	
			if (s != null) {
				if (s instanceof TypedSymbol) {
					TypedSymbol tsym;
					tsym = (TypedSymbol) s;
					t = tsym.getType ();
				} // if s instanceof TypedSymbol
			} // if s != null
		} // if lookaheadToken.getKind () == IDENT
		else if (lookaheadToken.getKind () == NUMBER) {
			get ();
			NumberToken ntoken;
			ntoken = (NumberToken) actualToken;
			bt = new BasicType ();
			bt.setInteger ();
			t = bt;
		} // if lookaheadToken.getKind () == NUMBER
		else if (lookaheadToken.getKind () == LPAREN) {
			get ();
			t = expression ();
			expect (RPAREN, FACTOR);
		} // if lookaheadToken.getKind () == LPAREN
		else if (lookaheadToken.getKind () == NOT) {
			get ();
			t = factor ();
		} // if lookaheadToken.getKind () == NOT
		else error (ERR_INV_FACTOR);
		
		return t;
		
	} // factor

} // class Parser