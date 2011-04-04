//  
//	reimplementation of the minicompiler for the sw-praktikum
//	author: Stefan Leitner, Stefan Schauer
//	last change: 29.09.2004


package itec.minicompiler;

import java.util.Hashtable;
import java.util.HashSet;

/**
 * The <code>FirstSet</code> implements the FIRST-Sets of all non-terminals in the 
 * grammar. As a most adequate structure the HashSet is used to implement a
 * particular FIRST-Set of a non-terminal. In a Hashtable the several HashSets are
 * brought together.
 * <p>
 * To get the several FIRST-Sets the algorithm in the book 
 * <blockquote>
 * "COMPILERS Principles, Techniques and Tools" by Aho, Sethi, Ullmann 
 * </blockquote>
 * was used. The entries were mainly calculated by hand.
 * 
 * @author Stefan Leitner
 * @author Stefan Schauer
 * @version 1.0 (29.09.2004)
 */
public class FirstSet implements ConstantsInterface {
	
	/**
	 * The overall structure of all the FIRST-Sets. The Hashtable contains the
	 * FIRST-Sets of the different non-terminals. Each entry is a HashSet as 
	 * implementation of a FIRST-Set.
	 */
	private Hashtable first;


	/**
	 * Initialize the FIRST-Set of each particular non-terminal in the grammar. The
	 * values of the several HashSets were mainly calculated by hand and checked
	 * for errors with JCoco.
	 */
	public FirstSet () {
		
		// FIRST-sets of the non-terminals
		HashSet FirstActualParameters = new HashSet ();
		FirstActualParameters.add (new Integer (PLUS));
		FirstActualParameters.add (new Integer (MINUS));
		FirstActualParameters.add (new Integer (IDENT));
		FirstActualParameters.add (new Integer (NUMBER));
		FirstActualParameters.add (new Integer (LPAREN));
		FirstActualParameters.add (new Integer (NOT));
		
		HashSet FirstArrayType = new HashSet ();
		FirstArrayType.add (new Integer (ARRAY));
		
		HashSet FirstAssignment = new HashSet ();
		FirstAssignment.add (new Integer (PERIOD));
		FirstAssignment.add (new Integer (LBRAK));
		FirstAssignment.add (new Integer (BECOMES));
		
		HashSet FirstDeclarations = new HashSet ();
		FirstDeclarations.add (new Integer (CONST));
		FirstDeclarations.add (new Integer (TYPE));
		FirstDeclarations.add (new Integer (VAR));
		FirstDeclarations.add (new Integer (PROCEDURE));
		FirstDeclarations.add (new Integer (EPSILON));
		FirstDeclarations.add (new Integer (BEGIN));
		FirstDeclarations.add (new Integer (END));
		
		HashSet FirstExpression = new HashSet ();
		FirstExpression.add (new Integer (PLUS));
		FirstExpression.add (new Integer (MINUS));
		FirstExpression.add (new Integer (IDENT));
		FirstExpression.add (new Integer (NUMBER));
		FirstExpression.add (new Integer (LPAREN));
		FirstExpression.add (new Integer (NOT));
		
		HashSet FirstFactor = new HashSet ();
		FirstFactor.add (new Integer (IDENT));
		FirstFactor.add (new Integer (NUMBER));
		FirstFactor.add (new Integer (LPAREN));
		FirstFactor.add (new Integer (NOT));
		
		HashSet FirstFieldList = new HashSet ();
		FirstFieldList.add (new Integer (IDENT));
		FirstFieldList.add (new Integer (EPSILON));
		FirstFieldList.add (new Integer (SEMICOLON));
		FirstFieldList.add (new Integer (END));		
		
		HashSet FirstFormalParameters = new HashSet ();
		FirstFormalParameters.add (new Integer (VAR));
		FirstFormalParameters.add (new Integer (IDENT));
		
		HashSet FirstFpSection = new HashSet ();
		FirstFpSection.add (new Integer (VAR));
		FirstFpSection.add (new Integer (IDENT));
		
		HashSet FirstIdentList = new HashSet ();
		FirstIdentList.add (new Integer (IDENT));
		
		HashSet FirstIfStatement = new HashSet ();
		FirstIfStatement.add (new Integer (IF));
		
		HashSet FirstMiniComp = new HashSet ();
		FirstMiniComp.add (new Integer (MODULE));
		
		HashSet FirstProcedureBody = new HashSet ();
		FirstProcedureBody.add (new Integer (CONST));
		FirstProcedureBody.add (new Integer (VAR));
		FirstProcedureBody.add (new Integer (TYPE));
		FirstProcedureBody.add (new Integer (PROCEDURE));
		FirstProcedureBody.add (new Integer (BEGIN));
		FirstProcedureBody.add (new Integer (END));
		
		HashSet FirstProcedureCall = new HashSet ();
		FirstProcedureCall.add (new Integer (LPAREN));
		
		HashSet FirstProcedureDeclaration = new HashSet ();
		FirstProcedureDeclaration.add (new Integer (PROCEDURE));
		
		HashSet FirstProcedureHeading = new HashSet ();
		FirstProcedureHeading.add (new Integer (PROCEDURE));
		
		HashSet FirstRecordType = new HashSet ();
		FirstRecordType.add (new Integer (RECORD));
		
		HashSet FirstRepeatStatement = new HashSet ();
		FirstRepeatStatement.add (new Integer (REPEAT));
		
		HashSet FirstSelector = new HashSet ();
		FirstSelector.add (new Integer (PERIOD));
		FirstSelector.add (new Integer (LBRAK));
		FirstSelector.add (new Integer (EPSILON));
		FirstSelector.add (new Integer (BECOMES));
		FirstSelector.add (new Integer (TIMES));
		FirstSelector.add (new Integer (DIV));
		FirstSelector.add (new Integer (MOD));
		FirstSelector.add (new Integer (PLUS));
		FirstSelector.add (new Integer (MINUS));
		FirstSelector.add (new Integer (OR));
		FirstSelector.add (new Integer (EQUAL));
		FirstSelector.add (new Integer (NOTEQUAL));
		FirstSelector.add (new Integer (LESS));
		FirstSelector.add (new Integer (LEQ));
		FirstSelector.add (new Integer (GREATER));
		FirstSelector.add (new Integer (GEQ));
		FirstSelector.add (new Integer (SEMICOLON));
		FirstSelector.add (new Integer (END));
		FirstSelector.add (new Integer (COMMA));
		FirstSelector.add (new Integer (RPAREN));
		FirstSelector.add (new Integer (ELSIF));
		FirstSelector.add (new Integer (ELSE));
		FirstSelector.add (new Integer (UNTIL));
		FirstSelector.add (new Integer (OF));
		FirstSelector.add (new Integer (RBRAK));
		FirstSelector.add (new Integer (AND));
		FirstSelector.add (new Integer (THEN));
		FirstSelector.add (new Integer (DO));
		
		HashSet FirstSimpleExpression = new HashSet ();
		FirstSimpleExpression.add (new Integer (PLUS));
		FirstSimpleExpression.add (new Integer (MINUS));
		FirstSimpleExpression.add (new Integer (IDENT));
		FirstSimpleExpression.add (new Integer (NUMBER));
		FirstSimpleExpression.add (new Integer (LPAREN));
		FirstSimpleExpression.add (new Integer (NOT));
		
		HashSet FirstStatement = new HashSet ();
		FirstStatement.add (new Integer (IDENT));
		FirstStatement.add (new Integer (IF));
		FirstStatement.add (new Integer (WHILE));
		FirstStatement.add (new Integer (REPEAT));
		FirstStatement.add (new Integer (EPSILON));
		FirstStatement.add (new Integer (SEMICOLON));
		FirstStatement.add (new Integer (END));
		FirstStatement.add (new Integer (ELSIF));
		FirstStatement.add (new Integer (ELSE));
		FirstStatement.add (new Integer (UNTIL));
		
		HashSet FirstStatementSequence = new HashSet ();
		FirstStatementSequence.add (new Integer (IDENT));
		FirstStatementSequence.add (new Integer (IF));
		FirstStatementSequence.add (new Integer (WHILE));
		FirstStatementSequence.add (new Integer (REPEAT));
		FirstStatementSequence.add (new Integer (SEMICOLON));
		FirstStatementSequence.add (new Integer (EPSILON));
		FirstStatementSequence.add (new Integer (END));
		FirstStatementSequence.add (new Integer (ELSIF));
		FirstStatementSequence.add (new Integer (ELSE));
		FirstStatementSequence.add (new Integer (UNTIL));
		
		HashSet FirstTerm = new HashSet ();
		FirstTerm.add (new Integer (IDENT));
		FirstTerm.add (new Integer (NUMBER));
		FirstTerm.add (new Integer (LPAREN));
		FirstTerm.add (new Integer (NOT));
		
		HashSet FirstTyp = new HashSet ();
		FirstTyp.add (new Integer (IDENT));
		FirstTyp.add (new Integer (ARRAY));
		FirstTyp.add (new Integer (RECORD));
		
		HashSet FirstWhileStatement = new HashSet ();
		FirstWhileStatement.add (new Integer (WHILE)); 
		
		// initialize the Hashtable with the FIRST-sets of all non-terminals
		first = new Hashtable ();
		
		first.put (new Integer (ACTUALPARAMETERS), FirstActualParameters);
		first.put (new Integer (ARRAYTYPE), FirstArrayType);
		first.put (new Integer (ASSIGNMENT), FirstAssignment);
		first.put (new Integer (DECLARATIONS), FirstDeclarations);
		first.put (new Integer (EXPRESSION), FirstExpression);
		first.put (new Integer (FACTOR), FirstExpression);
		first.put (new Integer (FIELDLIST), FirstFieldList);
		first.put (new Integer (FORMALPARAMETERS), FirstFormalParameters);
		first.put (new Integer (FPSECTION), FirstFpSection);
		first.put (new Integer (IDENTLIST), FirstIdentList);
		first.put (new Integer (IFSTATEMENT), FirstIfStatement);
		first.put (new Integer (MINICOMP), FirstMiniComp);
		first.put (new Integer (PROCEDUREBODY), FirstProcedureBody);
		first.put (new Integer (PROCEDURECALL), FirstProcedureCall);
		first.put (new Integer (PROCEDUREDECLARATION), FirstProcedureDeclaration);
		first.put (new Integer (PROCEDUREHEADING), FirstProcedureHeading);
		first.put (new Integer (RECORDTYPE), FirstRecordType);
		first.put (new Integer (REPEATSTATEMENT), FirstRepeatStatement);
		first.put (new Integer (SELECTOR), FirstSelector);
		first.put (new Integer (SIMPLEEXPRESSION), FirstSimpleExpression);
		first.put (new Integer (STATEMENT), FirstStatement);
		first.put (new Integer (STATEMENTSEQUENCE), FirstStatementSequence);
		first.put (new Integer (TERM), FirstTerm);
		first.put (new Integer (TYP), FirstTyp);
		first.put (new Integer (WHILESTATEMENT), FirstWhileStatement);
		
	} // FirstSet
	
	/**
	 * Return the FIRST-Set of a <code>nonTerminal</code>.
	 * 
	 * @param nonTerminal	A non-terminal in the grammar
	 * @return				A HashSet implementing the FIRST-Set of <code>nonTerminal</code>
	 */
	public HashSet getFirst (int nonTerminal) {
		
		return (HashSet) first.get(new Integer (nonTerminal));
		
	} // getFirst
	
} // class FirstSet