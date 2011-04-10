package yapl.impl;

import java.util.Stack;

import yapl.interfaces.CompilerError;
import yapl.interfaces.Symbol;
import yapl.interfaces.Symboltable;
import yapl.lib.YAPLException;

/**
 * Interface to the symbol table of the YAPL compiler.
 * <p>
 * <b>Implementation note:</b> The symbol table should maintain a stack of
 * scopes, where each scope should be implemented by a data structure that
 * supports efficient searching for symbol names (eg. a Hashtable).
 * 
 * The top element of the scope stack represents the <em>current scope</em>. The
 * {@link #openScope(boolean)} method pushes a new element to the scope stack,
 * the {@link #closeScope()} method pops the current scope off the stack.
 * </p>
 * <p>
 * Some scopes need to be linked to a <em>parent symbol</em> (eg. a procedure
 * body's scope to its procedure symbol) such that the nearest parent symbol
 * within the stack of scopes can be found within the current scope (eg. when
 * parsing a Return statement in a procedure body, the procedure symbol needs to
 * be found). This is supported by the {@link #setParentSymbol(Symbol)} and
 * {@link #getNearestParentSymbol(int)} methods.
 * </p>
 * <p>
 * The <code>YAPLException</code> class should implement the CompilerError
 * interface. The Symboltable methods specify which error number should be
 * emitted on certain error conditions.
 * </p>
 * 
 * @author Mario Taschwer
 * @version $Id: Symboltable.java 99 2009-03-23 14:30:37Z mt $
 */

public class SymboltableImpl implements Symboltable{

	/** Debugging */
	private boolean debugging = false;
	/** Stack of scopes */
	private Stack<Scope> stack = new Stack<Scope>();
	/** Copy Stack of scopes */
	private Stack<Scope> tempStack;

	/**
	 * Open a new scope inside the current scope.
	 * 
	 * @param isGlobal
	 *            set to <code>true</code> iff the new scope represents a global
	 *            scope, i.e. all symbols declared in the new scope are visible
	 *            within all following procedure declarations and within the
	 *            main program.
	 */
	public void openScope(boolean isGlobal) {		
		this.stack.push(new Scope());
	}

	/** Close the current scope. */
	public void closeScope() {
		if (!this.stack.empty())
			this.stack.pop();
	}

	/**
	 * Add a new Symbol to the current scope. Sets the global symbol property
	 * (see {@link Symbol#isGlobal()}) of the new symbol according to the
	 * current scope.
	 * 
	 * @param s
	 *            <code>s</code> and <code>s.getName()</code> must not be
	 *            <code>null</code>.
	 * @throws YAPLException
	 *             (SymbolExists) if a symbol of the same name already exists in
	 *             the current scope.
	 * @throws YAPLException
	 *             (Internal) if the new symbol's name is <code>null</code>.
	 * @see #openScope(boolean)
	 */
	public void addSymbol(Symbol s) throws YAPLException {			
		if (s != null && !(s.getName().equals(""))) {
			if (stack.peek().containsKey(s.getName())) {
				// (SymbolExists)
				throw new YAPLException(CompilerError.SymbolExists);
			}else{				
				stack.peek().put(s.getName(), s);				
			}
		} else {
			// (Internal)
			throw new YAPLException(CompilerError.Internal);
		}

	}

	/**
	 * Lookup a symbol in the stack of scopes. Symbols in an inner scope hide
	 * symbols of the same name in an outer scope.
	 * 
	 * @param name
	 *            must not be <code>null</code>.
	 * @return <code>null</code> if a symbol of the given name does not exist.
	 * @throws YAPLException
	 *             (Internal) if <code>name</code> is <code>null</code>.
	 */
	@SuppressWarnings("unchecked")
	public Symbol lookup(String name) throws YAPLException {		
		if(name == null)
			throw new YAPLException(CompilerError.IdentNotDecl);		
		
		if(this.stack.peek().containsKey(name))
			return stack.peek().get(name);
		else{			
			this.tempStack = (Stack<Scope>) this.stack.clone();
			return lookupRecursive(name);
		}
	}
		
	public Symbol lookupRecursive(String name) throws YAPLException {			
		if(tempStack.empty())
			return null;
		else{
			if(tempStack.peek().containsKey(name))
				return tempStack.peek().get(name);
			else{
				tempStack.pop();
				return this.lookupRecursive(name);
			}
		}		
	}

	/**
	 * Looks up an element in the current scope
	 * for overriding variables in parent scopes
	 * @param name
	 * @return
	 */
	public Symbol lookupCurrentScope(String name){			
		return this.stack.peek().get(name);
	}
	
	/**
	 * Set the parent symbol of the current scope. If a parent symbol has
	 * already be set, it will be overwritten.
	 * 
	 * @param sym
	 *            the parent symbol (eg. procedure symbol).
	 */
	public void setParentSymbol(Symbol sym) {		
		this.stack.peek().setParent(sym);
	}

	/**
	 * Return the nearest parent symbol of the given kind in the stack of
	 * scopes.
	 * 
	 * @param kind
	 *            one of the <em>kind</em> constants defined by the
	 *            {@link Symbol} interface.
	 * @return <code>null</code> if no appropriate symbol can be found.
	 */
	@SuppressWarnings("unchecked")
	public Symbol getNearestParentSymbol(int kind) {
		this.tempStack = (Stack<Scope>) this.stack.clone();
		return this.getNearestParentSymbolRecursive(kind);
	}

	private Symbol getNearestParentSymbolRecursive(int kind){		
		if(tempStack.empty())
			return null;
		else{
			if(tempStack.peek().getParent().getKind() == kind){										
				return tempStack.peek().getParent();
			}
			else{				
				tempStack.pop();				
				return this.getNearestParentSymbol(kind);
			}
		}
	}
	
	/** Enable/disable debugging output for symbol table operations. */
	public void setDebug(boolean on) {
		this.debugging = on;

	}

}
