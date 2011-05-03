package yapl.lib;

import yapl.interfaces.CompilerError;

/** This class provides static methods for generating compiler messages
 * in a given specified format. The error message format specification is (EBNF):
 * <pre>
 * Message = "YAPL compilation: [" ProgName "] " ( "OK" | Error newLine DetailedMessage )
 * Error = "ERROR " number " (line " number ", column " number ")"
 * DetailedMessage = string
 * </pre>
 * 
 * @author Mario Taschwer
 * @version $Id: CompilerMessage.java 89 2009-03-06 22:38:20Z mt $
 */
public class CompilerMessage {
	private static final String prefix = "YAPL compilation: [";
	
	/** Print a compiler status OK message to the standard error stream.
	 * @param progName    the source program name.
	 */
	public static void printOK(String progName)
	{
		System.err.println(prefix + progName + "] OK"); 
	}
	
	/**
	 * Print a compiler error message to the standard error stream.
	 * For an internal compiler error ({@link CompilerError.Internal}), the
	 * stack trace will be printed, too, if possible.
	 * @param err         the Exception or Error object representing the cause
	 *                    and source location of the error.
	 * @param progName    the source program name.
	 */
	public static void printError(CompilerError err, String progName)
	{
		StringBuffer buf = new StringBuffer();
		buf.append(prefix);
		buf.append(progName);
		buf.append("] ERROR ");
		buf.append(err.errorNumber());
		buf.append(" (line ");
		buf.append(err.line());
		buf.append(", column ");
		buf.append(err.column());
		buf.append(")\n");
		buf.append(err.getMessage());
		System.err.println(buf.toString());
		if (err.errorNumber() == CompilerError.Internal && err instanceof Throwable)
			((Throwable) err).printStackTrace();
	}
}
