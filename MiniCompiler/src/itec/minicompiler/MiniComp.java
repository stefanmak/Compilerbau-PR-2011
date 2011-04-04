//  
//	reimplementation of the minicompiler for the sw-praktikum
//	author: Stefan Leitner, Stefan Schauer
//	last change: 29.09.2004


package itec.minicompiler;

import java.io.InputStream;
import java.io.FileInputStream;
import java.io.BufferedInputStream;
import java.io.IOException;

/**
 * The <code>MiniComp</code> class is the executable part of the MiniCompiler, including
 * the <code>main</code>-method where the {@link Scanner}, the {@link Parser} and the 
 * {@link ErrorHandler} are initialised and the source file is read.
 * <p>
 * The source file is read into a String what implies some limitations regarding to the 
 * length of the source code. But as the MiniCompiler isn't meant to handler big
 * programs this is the most comfortable way. To make it easier for the {@link Scanner}
 * the end of the source code is additionally marked with a <code>\0</code>.
 * 
 * @author Stefan Leitner
 * @author Stefan Schauer
 * @version 1.0 (29.09.2004)
 */
public class MiniComp {
	
	/**
	 * Read characters from a <code>sourceFile</code> into a String object.
	 * 
	 * @param sourceFile	The file which should be read 
	 * @return				The String object created by the characters read
	 * 						from the <code>sourceFile</code>
	 */
	private static String readFile (String sourceFile) {
		
		InputStream s;
		byte [] buffer;
		int bufferLength;
		int n;
		
		
		buffer = null;
		try {
			s = new FileInputStream (sourceFile);
			bufferLength = s.available ();
			s = new BufferedInputStream (s, bufferLength);
			buffer = new byte [bufferLength];
			n = s.read (buffer);
		} // try
		catch (IOException e) {
			System.out.println("-- cannot open file " + sourceFile);
			System.exit(0);
		} // catch
		
		return new String (buffer);
		
	} // readFile
	
	/**
	 * The main method of <code>MiniComp</code>. Gets the filename of a source file 
	 * as first argument. Calls the {@link Parser} and reports the errors occured
	 * during parsing, if any.
	 * 
	 * @param args	List of arguments specified by the user
	 */
	public static void main (String [] args) {
		
		Scanner scanner;
		Parser parser;
		ErrorHandler errorhandler;
		String sourceFile;
		String sourceCode;
		
		
		if (args.length == 0) {
			System.out.println ("-- no sourcefile specified");
		} // if no argument given
		else {
			sourceFile = args [0];
			sourceCode = readFile (sourceFile);
		
			System.out.println("\n\nMiniCompiler (JavaImplementation)");
			System.out.println("Working on file " + sourceFile);
			System.out.println("Starting the process...\n");
		
			errorhandler = new ErrorHandler ();
		
			scanner = new Scanner (sourceCode + "\0", errorhandler);
		
			parser = new Parser (errorhandler, scanner);
		
			parser.parse ();
		
			errorhandler.printErrors();
		} // else
		
	} // main
	
} // class MiniComp