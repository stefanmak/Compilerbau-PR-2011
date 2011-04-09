package yapl.interfaces;

import yapl.lib.YAPLException;

/**
 * Interface to the symbol table of the YAPL compiler.
 * <p>
 * <b>Implementation note:</b> The symbol table should maintain a stack of
 * scopes, where each scope should be implemented by a data structure that
 * supports efficient searching for symbol names (eg. a Hashtable). The top
 * element of the scope stack represents the <em>current scope</em>. The
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
public interface Symboltable {
	/** Open a new scope inside the current scope. 
	 * @param isGlobal    set to <code>true</code> iff the new scope
	 *                    represents a global scope, i.e. all symbols
	 *                    declared in the new scope are visible within
	 *                    all following procedure declarations and within
	 *                    the main program.
	 */
	public void openScope(boolean isGlobal);

	/** Close the current scope. */
	public void closeScope();

	/**
	 * Add a new Symbol to the current scope. Sets the global symbol property 
	 * (see {@link Symbol#isGlobal()}) of the new symbol according to the
	 * current scope.
	 * 
	 * @param s    <code>s</code> and <code>s.getName()</code> must not be <code>null</code>.
	 * @throws YAPLException
	 *             (SymbolExists) if a symbol of the same name already exists in
	 *             the current scope.
	 * @throws YAPLException
	 *             (Internal) if the new symbol's name is <code>null</code>.
	 * @see #openScope(boolean)
	 */
	public void addSymbol(Symbol s) throws YAPLException;

	/**
	 * Lookup a symbol in the stack of scopes. Symbols in an inner scope hide
	 * symbols of the same name in an outer scope.
	 * 
	 * @param name     must not be <code>null</code>.
	 * @return <code>null</code> if a symbol of the given name does not exist.
	 * @throws YAPLException
	 *             (Internal) if <code>name</code> is <code>null</code>.
	 */
	public Symbol lookup(String name) throws YAPLException;

	/**
	 * Set the parent symbol of the current scope. If a parent symbol has
	 * already be set, it will be overwritten.
	 * 
	 * @param sym
	 *            the parent symbol (eg. procedure symbol).
	 */
	public void setParentSymbol(Symbol sym);

	/**
	 * Return the nearest parent symbol of the given kind in the stack of
	 * scopes.
	 * 
	 * @param kind
	 *            one of the <em>kind</em> constants defined by the
	 *            {@link Symbol} interface.
	 * @return <code>null</code> if no appropriate symbol can be found.
	 */
	public Symbol getNearestParentSymbol(int kind);

	/** Enable/disable debugging output for symbol table operations. */
	public void setDebug(boolean on);
}
