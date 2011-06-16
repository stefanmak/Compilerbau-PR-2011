import java.io.FileOutputStream;
import java.io.PrintStream;

import impl.BackendMIPS;
import interfaces.BackendAsmRM;

/**
 * BackendAsmRM test: printing a string constant.
 * 
 * @author Mario Taschwer
 * @version $Id: Test1.java 170 2011-05-11 07:15:13Z mt $
 */
public class Main {

private static BackendAsmRM backend;
    
    /**
     * Emit single-argument procedure call code.
     * @param name   procedure name.
     * @param reg    argument register, will be deallocated.
     */
    private static void callProc(String name, byte reg)
    {
        backend.prepareProcCall(1);
        backend.passArg(0, reg);
        backend.freeReg(reg);
        backend.callProc((byte) -1, name);
    }
    
    /**
     * Emit single-argument function call code.
     * @param name    function name.
     * @param reg     argument register.
     * @return        register holding the function result;
     *                is same register as <code>reg</code> here.
     */
    private static byte callFunc(String name, byte reg)
    {
        backend.prepareProcCall(1);
        backend.passArg(0, reg);
        backend.callProc(reg, name);
        return reg;
    }
    
    /**
     * Usage: java yapl.test.backend.rm.Test4 [asm_file]
     */
    public static void main(String[] args) throws Exception
    {
        PrintStream out = (args.length > 0) 
                ? new PrintStream(new FileOutputStream(args[0])) : System.out;
        backend = new BackendMIPS(out);
        int wordSize = backend.wordSize();

        int global = backend.allocStaticData(wordSize, "global variable");
        int addrSeparator = backend.allocStringConstant(" : ");
        
        // procedure int func(int x): returns global - x*x
        backend.enterProc("func", 1);
        byte r1 = backend.allocReg();
        backend.loadWord(r1, global, true);                   // load global variable
        byte r2 = backend.allocReg();
        backend.loadWord(r2, backend.paramOffset(0), false);  // load parameter 0 from stack frame
        byte r3 = backend.allocReg();
        backend.loadWord(r3, backend.paramOffset(0), false);  // load parameter 0 from stack frame
        backend.mul(r2, r2, r3);
        backend.freeReg(r3);
        backend.sub(r1, r1, r2);
        backend.freeReg(r2);
        backend.returnFromProc("func_end", r1);
        backend.freeReg(r1);
        backend.exitProc("func_end");
        
        // main program
        backend.enterMain();
        int local = backend.allocStack(wordSize, "local variable");

        // global := 17
        r1 = backend.allocReg();
        backend.loadConst(r1, 17);
        backend.storeWord(r1, global, true);
        backend.freeReg(r1);
        
        // local := func(3)
        r1 = backend.allocReg();
        backend.loadConst(r1, 3);
        r1 = callFunc("func", r1);
        backend.storeWord(r1, local, false);
        backend.freeReg(r1);
        
        // writeint(local) -> result: 8
        r1 = backend.allocReg();
        backend.loadWord(r1, local, false);
        callProc("writeint", r1);
        
        backend.writeString(addrSeparator);
        
        // writeint(func(local)) -> result: -47
        r1 = backend.allocReg();
        backend.loadWord(r1, local, false);
        r1 = callFunc("func", r1);
        callProc("writeint", r1);

        backend.exitMain("main_end");
    }
}
