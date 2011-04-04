//  
//	reimplementation of the minicompiler for the sw-praktikum
//	author: Stefan Leitner, Stefan Schauer
//	last change: 29.09.2004


package itec.minicompiler;

import java.util.Hashtable;
import java.util.HashSet;

/**
 * The <code>FollowSet</code> implements the FOLLOW-Sets of all non-terminals in the 
 * grammar. As a most adequate structure the HashSet is used to implement a
 * particular FOLLOW-Set of a non-terminal. In a Hashtable the several HashSets are
 * brought together.
 * <p>
 * To get the several FOLLOW-Sets the algorithm in the book 
 * <blockquote>
 * "COMPILERS Principles, Techniques and Tools" by Aho, Sethi, Ullmann 
 * </blockquote>
 * was used. The entries were mainly calculated by hand.
 * 
 * @author Stefan Leitner
 * @author Stefan Schauer
 * @version 1.0 (29.09.2004)
 */
public class FollowSet implements ConstantsInterface {
	
	/**
	 * The overall structure of all the FOLLOW-Sets. The Hashtable contains the
	 * FOLLOW-Sets of the different non-terminals. Each entry is a HashSet as 
	 * implementation of a FOLLOW-Set.
	 */
	private Hashtable follow;
	
	
	/**
	 * Initialize the FOLLOW-Set of each particular non-terminal in the grammar. The
	 * values of the several HashSets were mainly calculated by hand and checked
	 * for errors with JCoco.
	 */
	public FollowSet () {

		// FOLLOW-sets of the non-terminals
		HashSet FollowActualParameters = new HashSet ();
		FollowActualParameters.add (new Integer (RPAREN));
		
		HashSet FollowArrayType = new HashSet ();
		FollowArrayType.add (new Integer (SEMICOLON));
		FollowArrayType.add (new Integer (END));
		FollowArrayType.add (new Integer (RPAREN));
		
		HashSet FollowAssignment = new HashSet ();
		FollowAssignment.add (new Integer (SEMICOLON));
		FollowAssignment.add (new Integer (END));
		FollowAssignment.add (new Integer (ELSIF));
		FollowAssignment.add (new Integer (ELSE));
		FollowAssignment.add (new Integer (UNTIL));
		
		HashSet FollowDeclarations = new HashSet ();
		FollowDeclarations.add (new Integer (BEGIN));
		FollowDeclarations.add (new Integer (END));
		
		HashSet FollowExpression = new HashSet ();
		FollowExpression.add (new Integer (SEMICOLON));
		FollowExpression.add (new Integer (END));
		FollowExpression.add (new Integer (COMMA));
		FollowExpression.add (new Integer (RPAREN));
		FollowExpression.add (new Integer (OF));
		FollowExpression.add (new Integer (RBRAK));
		FollowExpression.add (new Integer (THEN));
		FollowExpression.add (new Integer (ELSIF));
		FollowExpression.add (new Integer (ELSE));
		FollowExpression.add (new Integer (DO));
		FollowExpression.add (new Integer (UNTIL));
		
		HashSet FollowFactor = new HashSet ();
		FollowFactor.add (new Integer(TIMES));
		FollowFactor.add (new Integer(DIV));
		FollowFactor.add (new Integer(MOD));
		FollowFactor.add (new Integer(PLUS));
		FollowFactor.add (new Integer(MINUS));
		FollowFactor.add (new Integer(OR));
		FollowFactor.add (new Integer(EQUAL));
		FollowFactor.add (new Integer(NOTEQUAL));
		FollowFactor.add (new Integer(LESS));
		FollowFactor.add (new Integer(LEQ));
		FollowFactor.add (new Integer(GREATER));
		FollowFactor.add (new Integer(GEQ));
		FollowFactor.add (new Integer(SEMICOLON));
		FollowFactor.add (new Integer(END));
		FollowFactor.add (new Integer(COLON));
		FollowFactor.add (new Integer(RPAREN));
		FollowFactor.add (new Integer(OF));
		FollowFactor.add (new Integer(COMMA));
		FollowFactor.add (new Integer(RBRAK));
		FollowFactor.add (new Integer(AND));
		FollowFactor.add (new Integer(THEN));
		FollowFactor.add (new Integer(ELSIF));
		FollowFactor.add (new Integer(ELSE));
		FollowFactor.add (new Integer(DO));
		FollowFactor.add (new Integer(UNTIL));
		
		HashSet FollowFieldList = new HashSet ();
		FollowFieldList.add (new Integer (SEMICOLON));
		FollowFieldList.add (new Integer (END));
		
		HashSet FollowFormalParameters = new HashSet ();
		FollowFormalParameters.add (new Integer (RPAREN));
				
		HashSet FollowFpSection = new HashSet ();
		FollowFpSection.add (new Integer (SEMICOLON));
		FollowFpSection.add (new Integer (RPAREN));
		
		HashSet FollowIdentList = new HashSet ();
		FollowIdentList.add (new Integer (COLON));
		
		HashSet FollowIfStatement = new HashSet ();
		FollowIfStatement.add (new Integer (SEMICOLON));
		FollowIfStatement.add (new Integer (END));
		FollowIfStatement.add (new Integer (ELSIF));
		FollowIfStatement.add (new Integer (ELSE));
		FollowIfStatement.add (new Integer (UNTIL));
		
		HashSet FollowMiniComp = new HashSet ();
		FollowMiniComp.add (new Integer (EMPTY));
		
		HashSet FollowProcedureBody = new HashSet ();
		FollowProcedureBody.add (new Integer (IDENT));
		
		HashSet FollowProcedureCall = new HashSet ();
		FollowProcedureCall.add (new Integer (SEMICOLON));
		FollowProcedureCall.add (new Integer (END));
		FollowProcedureCall.add (new Integer (ELSIF));
		FollowProcedureCall.add (new Integer (ELSE));
		FollowProcedureCall.add (new Integer (UNTIL));
		
		HashSet FollowProcedureDeclaration = new HashSet ();
		FollowProcedureDeclaration.add (new Integer (SEMICOLON));
		
		HashSet FollowProcedureHeading = new HashSet ();
		FollowProcedureHeading.add (new Integer (SEMICOLON));
		
		HashSet FollowRecordType = new HashSet ();
		FollowRecordType.add (new Integer (SEMICOLON));
		FollowRecordType.add (new Integer (RPAREN));
		FollowRecordType.add (new Integer (END));
		
		HashSet FollowRepeatStatement = new HashSet ();
		FollowRepeatStatement.add (new Integer (SEMICOLON));
		FollowRepeatStatement.add (new Integer (END));
		FollowRepeatStatement.add (new Integer (ELSIF));
		FollowRepeatStatement.add (new Integer (ELSE));
		FollowRepeatStatement.add (new Integer (UNTIL));
		
		HashSet FollowSelector = new HashSet ();
		FollowSelector.add (new Integer (BECOMES));
		FollowSelector.add (new Integer (TIMES));
		FollowSelector.add (new Integer (DIV));
		FollowSelector.add (new Integer (MOD));
		FollowSelector.add (new Integer (PLUS));
		FollowSelector.add (new Integer (MINUS));
		FollowSelector.add (new Integer (OR));
		FollowSelector.add (new Integer (EQUAL));
		FollowSelector.add (new Integer (NOTEQUAL));
		FollowSelector.add (new Integer (LESS));
		FollowSelector.add (new Integer (LEQ));
		FollowSelector.add (new Integer (GREATER));
		FollowSelector.add (new Integer (GEQ));
		FollowSelector.add (new Integer (SEMICOLON));
		FollowSelector.add (new Integer (END));
		FollowSelector.add (new Integer (COMMA));
		FollowSelector.add (new Integer (RPAREN));
		FollowSelector.add (new Integer (ELSIF));
		FollowSelector.add (new Integer (ELSE));
		FollowSelector.add (new Integer (UNTIL));
		FollowSelector.add (new Integer (OF));
		FollowSelector.add (new Integer (RBRAK));
		FollowSelector.add (new Integer (AND));
		FollowSelector.add (new Integer (THEN));
		FollowSelector.add (new Integer (DO));
		
		HashSet FollowSimpleExpression = new HashSet ();
		FollowSimpleExpression.add (new Integer (EQUAL));
		FollowSimpleExpression.add (new Integer (NOTEQUAL));
		FollowSimpleExpression.add (new Integer (LESS));
		FollowSimpleExpression.add (new Integer (LEQ));
		FollowSimpleExpression.add (new Integer (GREATER));
		FollowSimpleExpression.add (new Integer (GEQ));
		FollowSimpleExpression.add (new Integer (SEMICOLON));
		FollowSimpleExpression.add (new Integer (END));
		FollowSimpleExpression.add (new Integer (COMMA));
		FollowSimpleExpression.add (new Integer (RPAREN));
		FollowSimpleExpression.add (new Integer (OF));
		FollowSimpleExpression.add (new Integer (RBRAK));
		FollowSimpleExpression.add (new Integer (THEN));
		FollowSimpleExpression.add (new Integer (ELSIF));
		FollowSimpleExpression.add (new Integer (ELSE));
		FollowSimpleExpression.add (new Integer (DO));
		FollowSimpleExpression.add (new Integer (UNTIL));
		
		HashSet FollowStatement = new HashSet ();
		FollowStatement.add (new Integer (SEMICOLON));
		FollowStatement.add (new Integer (END));
		FollowStatement.add (new Integer (ELSIF));
		FollowStatement.add (new Integer (ELSE));
		FollowStatement.add (new Integer (UNTIL));
		
		HashSet FollowStatementSequence = new HashSet ();
		FollowStatementSequence.add (new Integer (END));
		FollowStatementSequence.add (new Integer (ELSIF));
		FollowStatementSequence.add (new Integer (ELSE));
		FollowStatementSequence.add (new Integer (UNTIL));
		
		HashSet FollowTerm = new HashSet ();
		FollowTerm.add (new Integer (PLUS));
		FollowTerm.add (new Integer (MINUS));
		FollowTerm.add (new Integer (OR));
		FollowTerm.add (new Integer (EQUAL));
		FollowTerm.add (new Integer (NOTEQUAL));
		FollowTerm.add (new Integer (LESS));
		FollowTerm.add (new Integer (LEQ));
		FollowTerm.add (new Integer (GREATER));
		FollowTerm.add (new Integer (GEQ));
		FollowTerm.add (new Integer (SEMICOLON));
		FollowTerm.add (new Integer (END));
		FollowTerm.add (new Integer (COMMA));
		FollowTerm.add (new Integer (RPAREN));
		FollowTerm.add (new Integer (OF));
		FollowTerm.add (new Integer (RBRAK));
		FollowTerm.add (new Integer (THEN));
		FollowTerm.add (new Integer (ELSIF));
		FollowTerm.add (new Integer (ELSE));
		FollowTerm.add (new Integer (DO));
		FollowTerm.add (new Integer (UNTIL));
		
		HashSet FollowTyp = new HashSet ();
		FollowTyp.add (new Integer (SEMICOLON));
		FollowTyp.add (new Integer (RPAREN));
		FollowTyp.add (new Integer (END));
		
		HashSet FollowWhileStatement = new HashSet ();
		FollowWhileStatement.add (new Integer (SEMICOLON));
		FollowWhileStatement.add (new Integer (END));
		FollowWhileStatement.add (new Integer (ELSIF));
		FollowWhileStatement.add (new Integer (ELSE));
		FollowWhileStatement.add (new Integer (UNTIL));
		
		// initialize the Hashtable with the FOLLOW-sets of all non-terminals
		follow = new Hashtable ();
		
		follow.put (new Integer (ACTUALPARAMETERS), FollowActualParameters);
		follow.put (new Integer (ARRAYTYPE), FollowArrayType);
		follow.put (new Integer (ASSIGNMENT), FollowAssignment);
		follow.put (new Integer (DECLARATIONS), FollowDeclarations);
		follow.put (new Integer (EXPRESSION), FollowExpression);
		follow.put (new Integer (FACTOR), FollowExpression);
		follow.put (new Integer (FIELDLIST), FollowFieldList);
		follow.put (new Integer (FORMALPARAMETERS), FollowFormalParameters);
		follow.put (new Integer (FPSECTION), FollowFpSection);
		follow.put (new Integer (IDENTLIST), FollowIdentList);
		follow.put (new Integer (IFSTATEMENT), FollowIfStatement);
		follow.put (new Integer (MINICOMP), FollowMiniComp);
		follow.put (new Integer (PROCEDUREBODY), FollowProcedureBody);
		follow.put (new Integer (PROCEDURECALL), FollowProcedureCall);
		follow.put (new Integer (PROCEDUREDECLARATION), FollowProcedureDeclaration);
		follow.put (new Integer (PROCEDUREHEADING), FollowProcedureHeading);
		follow.put (new Integer (RECORDTYPE), FollowRecordType);
		follow.put (new Integer (REPEATSTATEMENT), FollowRepeatStatement);
		follow.put (new Integer (SELECTOR), FollowSelector);
		follow.put (new Integer (SIMPLEEXPRESSION), FollowSimpleExpression);
		follow.put (new Integer (STATEMENT), FollowStatement);
		follow.put (new Integer (STATEMENTSEQUENCE), FollowStatementSequence);
		follow.put (new Integer (TERM), FollowTerm);
		follow.put (new Integer (TYP), FollowTyp);
		follow.put (new Integer (WHILESTATEMENT), FollowWhileStatement);
		
	} // initializeFollow

	/**
	 * Return the FOLLOW-Set of a <code>nonTerminal</code>.
	 * 
	 * @param nonTerminal	A non-terminal in the grammar
	 * @return				A HashSet implementing the FOLLOW-Set of <code>nonTerminal</code>
	 */
	public HashSet getFollow (int nonTerminal) {
	
		return (HashSet) follow.get (new Integer (nonTerminal));
		
	} // getFollow
	
} // class FollowSet