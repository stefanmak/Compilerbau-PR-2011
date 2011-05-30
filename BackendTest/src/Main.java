import java.io.FileOutputStream;
import java.io.PrintStream;

import impl.BackendMIPS;
import interfaces.BackendAsmRM;

/**
 * BackendAsmRM test: printing a string constant.
 * @author Mario Taschwer
 * @version $Id: Test1.java 170 2011-05-11 07:15:13Z mt $
 */
public class Main
{
    /**
     * Usage: java yapl.test.backend.rm.Test1 [asm_file]
     */
    public static void main(String[] args) throws Exception
    {
        PrintStream out = (args.length > 0) 
                ? new PrintStream(new FileOutputStream(args[0])) : System.out;
        BackendAsmRM backend = new BackendMIPS(out);
        backend.enterMain();
        int addr = backend.allocStringConstant("Hello world!");
        backend.writeString(addr);
        backend.exitMain("main_end");
    }
}
