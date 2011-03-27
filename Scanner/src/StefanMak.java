/* Generated By:JavaCC: Do not edit this line. StefanMak.java */
import yapl.interfaces.*;
import yapl.lib.*;
import java.io.FileInputStream;
import java.io.File;
import java.io.IOException;

/** Scanner for Compiler Milestone 1 */
public class StefanMak implements StefanMakConstants {

  /** Program Name for failure and debugging*/
  private static String program_name;

  /** Main entry point. */
  public static void main(String args []) throws ParseException, TokenMgrError
  {

        /** Declare variables for program read*/
        File file = null;
        FileInputStream fis = null;

        /** Read YAPL File from file */
        if(args.length != 0)
        {
                try
                {
                        file = new File(args[0]);
                        fis = new FileInputStream(file);
                }catch(IOException ex)
                {
                  System.err.println(ex);
                }
        }else
        {
          System.out.println("YAPL Program as first parameter needed...");
          System.out.println("System exit...");
          System.exit(0);
        }

        /** Give my Parser the InputStream to start the work */
    StefanMak parser = new StefanMak(fis);
        try
            {
              parser.Start();
              /** Parsing was correct and complete */
              CompilerMessage.printOK(StefanMak.program_name);
            }
            catch (TokenMgrError ex)
            {
              /** Lexical Error etc. occured */
              CompilerMessage.printError(ex,StefanMak.program_name);
            }
            catch (ParseException ex)
            {
              /** Parse Error occured */
              CompilerMessage.printError(ex,StefanMak.program_name);
            }
          }

/* Expressions */
  static final public void LITERAL() throws ParseException {
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case TRUE:
      jj_consume_token(TRUE);
      break;
    case FALSE:
      jj_consume_token(FALSE);
      break;
    case NUMBER:
      jj_consume_token(NUMBER);
      break;
    default:
      jj_la1[0] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
  }

  static final public void SELECTOR() throws ParseException {
    jj_consume_token(LBRACKET);
    EXPR();
    jj_consume_token(RBRACKET);
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case LBRACKET:
      SELECTOR();
      break;
    default:
      jj_la1[1] = jj_gen;
      ;
    }
  }

  static final public void ARRAYLEN() throws ParseException {
    jj_consume_token(BLANK);
    jj_consume_token(IDENT);
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case LBRACKET:
      SELECTOR();
      break;
    default:
      jj_la1[2] = jj_gen;
      ;
    }
  }

  static final public void PRIMARYEXPR() throws ParseException {
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case TRUE:
    case FALSE:
    case NUMBER:
      LITERAL();
      break;
    case LPAR:
      jj_consume_token(LPAR);
      EXPR();
      jj_consume_token(RPAR);
      break;
    default:
      jj_la1[4] = jj_gen;
      if (jj_2_1(2)) {
        PROCEDURECALL();
      } else {
        switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
        case IDENT:
          jj_consume_token(IDENT);
          switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
          case LBRACKET:
            SELECTOR();
            break;
          default:
            jj_la1[3] = jj_gen;
            ;
          }
          break;
        case BLANK:
          ARRAYLEN();
          break;
        default:
          jj_la1[5] = jj_gen;
          jj_consume_token(-1);
          throw new ParseException();
        }
      }
    }
  }

  static final public void UNARYEXPR() throws ParseException {
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case ADDOP:
      jj_consume_token(ADDOP);
      break;
    default:
      jj_la1[6] = jj_gen;
      ;
    }
    PRIMARYEXPR();
  }

  static final public void MULEXPR() throws ParseException {
    UNARYEXPR();
    label_1:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case MULOP:
        ;
        break;
      default:
        jj_la1[7] = jj_gen;
        break label_1;
      }
      jj_consume_token(MULOP);
      UNARYEXPR();
    }
  }

  static final public void ADDEXPR() throws ParseException {
    MULEXPR();
    label_2:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case ADDOP:
        ;
        break;
      default:
        jj_la1[8] = jj_gen;
        break label_2;
      }
      jj_consume_token(ADDOP);
      MULEXPR();
    }
  }

  static final public void RELEXPR() throws ParseException {
    ADDEXPR();
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case RELOP:
      jj_consume_token(RELOP);
      ADDEXPR();
      break;
    default:
      jj_la1[9] = jj_gen;
      ;
    }
  }

  static final public void EQUALEXPR() throws ParseException {
    RELEXPR();
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case EQUALOP:
      jj_consume_token(EQUALOP);
      RELEXPR();
      break;
    default:
      jj_la1[10] = jj_gen;
      ;
    }
  }

  static final public void CONDANDEXPR() throws ParseException {
    EQUALEXPR();
    label_3:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case AND:
        ;
        break;
      default:
        jj_la1[11] = jj_gen;
        break label_3;
      }
      jj_consume_token(AND);
      EQUALEXPR();
    }
  }

  static final public void CREATIONEXPR() throws ParseException {
    jj_consume_token(NEW);
    PRIMTYPE();
    jj_consume_token(LBRACKET);
    EXPR();
    jj_consume_token(RBRACKET);
    label_4:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case LBRACKET:
        ;
        break;
      default:
        jj_la1[12] = jj_gen;
        break label_4;
      }
      jj_consume_token(LBRACKET);
      EXPR();
      jj_consume_token(RBRACKET);
    }
  }

  static final public void EXPR() throws ParseException {
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case LPAR:
    case TRUE:
    case FALSE:
    case BLANK:
    case ADDOP:
    case NUMBER:
    case IDENT:
      CONDANDEXPR();
      label_5:
      while (true) {
        switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
        case OR:
          ;
          break;
        default:
          jj_la1[13] = jj_gen;
          break label_5;
        }
        jj_consume_token(OR);
        CONDANDEXPR();
      }
      break;
    case NEW:
      CREATIONEXPR();
      break;
    default:
      jj_la1[14] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
  }

  static final public void ARGUMENTLIST() throws ParseException {
    EXPR();
    label_6:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case COMMA:
        ;
        break;
      default:
        jj_la1[15] = jj_gen;
        break label_6;
      }
      jj_consume_token(COMMA);
      EXPR();
    }
  }

  static final public void PROCEDURECALL() throws ParseException {
    jj_consume_token(IDENT);
    jj_consume_token(LPAR);
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case LPAR:
    case TRUE:
    case FALSE:
    case NEW:
    case BLANK:
    case ADDOP:
    case NUMBER:
    case IDENT:
      ARGUMENTLIST();
      break;
    default:
      jj_la1[16] = jj_gen;
      ;
    }
    jj_consume_token(RPAR);
  }

  static final public void ASSIGNMENT() throws ParseException {
    jj_consume_token(IDENT);
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case LBRACKET:
      SELECTOR();
      break;
    default:
      jj_la1[17] = jj_gen;
      ;
    }
    jj_consume_token(50);
    EXPR();
  }

  static final public void IFSTATEMENT() throws ParseException {
    jj_consume_token(IF);
    EXPR();
    jj_consume_token(THEN);
    STATEMENTLIST();
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case ELSE:
      jj_consume_token(ELSE);
      STATEMENTLIST();
      break;
    default:
      jj_la1[18] = jj_gen;
      ;
    }
    jj_consume_token(ENDIF);
  }

  static final public void WHILESTATEMENT() throws ParseException {
    jj_consume_token(WHILE);
    EXPR();
    jj_consume_token(DO);
    STATEMENTLIST();
    jj_consume_token(ENDWHILE);
  }

  static final public void RETURNSTATEMENT() throws ParseException {
    jj_consume_token(RETURN);
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case LPAR:
    case TRUE:
    case FALSE:
    case NEW:
    case BLANK:
    case ADDOP:
    case NUMBER:
    case IDENT:
      EXPR();
      break;
    default:
      jj_la1[19] = jj_gen;
      ;
    }
  }

  static final public void WRITESTATEMENT() throws ParseException {
    jj_consume_token(WRITE);
    jj_consume_token(STRING);
  }

  static final public void STATEMENT() throws ParseException {
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case IF:
      IFSTATEMENT();
      break;
    case WHILE:
      WHILESTATEMENT();
      break;
    case RETURN:
      RETURNSTATEMENT();
      break;
    case WRITE:
      WRITESTATEMENT();
      break;
    default:
      jj_la1[20] = jj_gen;
      if (jj_2_2(2)) {
        ASSIGNMENT();
      } else {
        switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
        case IDENT:
          PROCEDURECALL();
          break;
        case BEGIN:
        case DECLARE:
          BLOCK();
          break;
        default:
          jj_la1[21] = jj_gen;
          jj_consume_token(-1);
          throw new ParseException();
        }
      }
    }
  }

  static final public void STATEMENTLIST() throws ParseException {
    label_7:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case IF:
      case WHILE:
      case RETURN:
      case WRITE:
      case BEGIN:
      case DECLARE:
      case IDENT:
        ;
        break;
      default:
        jj_la1[22] = jj_gen;
        break label_7;
      }
      STATEMENT();
      jj_consume_token(SEMICOLON);
    }
  }

  static final public void BLOCK() throws ParseException {
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case DECLARE:
      DECL();
      break;
    default:
      jj_la1[23] = jj_gen;
      ;
    }
    jj_consume_token(BEGIN);
    STATEMENTLIST();
    jj_consume_token(END);
  }

  static final public void PRIMTYPE() throws ParseException {
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case INTEGER:
      jj_consume_token(INTEGER);
      break;
    case BOOLEAN:
      jj_consume_token(BOOLEAN);
      break;
    default:
      jj_la1[24] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
  }

  static final public void TYPE() throws ParseException {
    PRIMTYPE();
    label_8:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case LBRACKET:
        ;
        break;
      default:
        jj_la1[25] = jj_gen;
        break label_8;
      }
      jj_consume_token(LBRACKET);
      jj_consume_token(RBRACKET);
    }
  }

  static final public void RETURNTYPE() throws ParseException {
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case VOID:
      jj_consume_token(VOID);
      break;
    case INTEGER:
    case BOOLEAN:
      TYPE();
      break;
    default:
      jj_la1[26] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
  }

  static final public void CONSTDECL() throws ParseException {
    jj_consume_token(CONST);
    jj_consume_token(IDENT);
    jj_consume_token(IS);
    LITERAL();
    jj_consume_token(SEMICOLON);
  }

  static final public void VARDECL() throws ParseException {
    TYPE();
    jj_consume_token(IDENT);
    label_9:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case COMMA:
        ;
        break;
      default:
        jj_la1[27] = jj_gen;
        break label_9;
      }
      jj_consume_token(COMMA);
      jj_consume_token(IDENT);
    }
    jj_consume_token(SEMICOLON);
  }

  static final public void DECL() throws ParseException {
    jj_consume_token(DECLARE);
    label_10:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case INTEGER:
      case BOOLEAN:
      case CONST:
        ;
        break;
      default:
        jj_la1[28] = jj_gen;
        break label_10;
      }
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case CONST:
        CONSTDECL();
        break;
      case INTEGER:
      case BOOLEAN:
        VARDECL();
        break;
      default:
        jj_la1[29] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
    }
  }

  static final public void FORMALPARAM() throws ParseException {
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case READONLY:
      jj_consume_token(READONLY);
      break;
    default:
      jj_la1[30] = jj_gen;
      ;
    }
    TYPE();
    jj_consume_token(IDENT);
  }

  static final public void FORMALPARAMLIST() throws ParseException {
    FORMALPARAM();
    label_11:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case COMMA:
        ;
        break;
      default:
        jj_la1[31] = jj_gen;
        break label_11;
      }
      jj_consume_token(COMMA);
      FORMALPARAM();
    }
  }

  static final public void PROCEDURE() throws ParseException {
    jj_consume_token(PROCEDURE);
    RETURNTYPE();
    jj_consume_token(IDENT);
    jj_consume_token(LPAR);
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case INTEGER:
    case BOOLEAN:
    case READONLY:
      FORMALPARAMLIST();
      break;
    default:
      jj_la1[32] = jj_gen;
      ;
    }
    jj_consume_token(RPAR);
    BLOCK();
    jj_consume_token(IDENT);
    jj_consume_token(SEMICOLON);
  }

  static final public void PROGRAM() throws ParseException {
 Token t;
    jj_consume_token(PROGRAM);
    t = jj_consume_token(IDENT);
   program_name = t.image;
    label_12:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case PROCEDURE:
      case DECLARE:
        ;
        break;
      default:
        jj_la1[33] = jj_gen;
        break label_12;
      }
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case DECLARE:
        DECL();
        break;
      case PROCEDURE:
        PROCEDURE();
        break;
      default:
        jj_la1[34] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
    }
    jj_consume_token(BEGIN);
    STATEMENTLIST();
    jj_consume_token(END);
    jj_consume_token(IDENT);
    jj_consume_token(DOT);
  }

/** Root node for production */
  static final public void Start() throws ParseException {
    PROGRAM();
  }

  static private boolean jj_2_1(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_1(); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(0, xla); }
  }

  static private boolean jj_2_2(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_2(); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(1, xla); }
  }

  static private boolean jj_3_1() {
    if (jj_3R_13()) return true;
    return false;
  }

  static private boolean jj_3R_15() {
    if (jj_3R_16()) return true;
    return false;
  }

  static private boolean jj_3_2() {
    if (jj_3R_14()) return true;
    return false;
  }

  static private boolean jj_3R_14() {
    if (jj_scan_token(IDENT)) return true;
    Token xsp;
    xsp = jj_scanpos;
    if (jj_3R_15()) jj_scanpos = xsp;
    if (jj_scan_token(50)) return true;
    return false;
  }

  static private boolean jj_3R_13() {
    if (jj_scan_token(IDENT)) return true;
    if (jj_scan_token(LPAR)) return true;
    return false;
  }

  static private boolean jj_3R_16() {
    if (jj_scan_token(LBRACKET)) return true;
    return false;
  }

  static private boolean jj_initialized_once = false;
  /** Generated Token Manager. */
  static public StefanMakTokenManager token_source;
  static SimpleCharStream jj_input_stream;
  /** Current token. */
  static public Token token;
  /** Next token. */
  static public Token jj_nt;
  static private int jj_ntk;
  static private Token jj_scanpos, jj_lastpos;
  static private int jj_la;
  static private int jj_gen;
  static final private int[] jj_la1 = new int[35];
  static private int[] jj_la1_0;
  static private int[] jj_la1_1;
  static {
      jj_la1_init_0();
      jj_la1_init_1();
   }
   private static void jj_la1_init_0() {
      jj_la1_0 = new int[] {0xc00000,0x100,0x100,0x100,0xc00040,0x0,0x0,0x0,0x0,0x0,0x0,0x200000,0x100,0x80000,0xc00040,0x400,0xc00040,0x100,0x40000,0xc00040,0x19008000,0x20000000,0x39008000,0x0,0x0,0x100,0x100000,0x400,0x0,0x0,0x0,0x400,0x0,0x80000000,0x80000000,};
   }
   private static void jj_la1_init_1() {
      jj_la1_1 = new int[] {0x1000,0x0,0x0,0x0,0x1000,0x4080,0x400,0x800,0x400,0x100,0x200,0x0,0x0,0x0,0x54c0,0x0,0x54c0,0x0,0x0,0x54c0,0x0,0x4010,0x4010,0x10,0x6,0x0,0x6,0x0,0xe,0xe,0x20,0x0,0x26,0x10,0x10,};
   }
  static final private JJCalls[] jj_2_rtns = new JJCalls[2];
  static private boolean jj_rescan = false;
  static private int jj_gc = 0;

  /** Constructor with InputStream. */
  public StefanMak(java.io.InputStream stream) {
     this(stream, null);
  }
  /** Constructor with InputStream and supplied encoding */
  public StefanMak(java.io.InputStream stream, String encoding) {
    if (jj_initialized_once) {
      System.out.println("ERROR: Second call to constructor of static parser.  ");
      System.out.println("       You must either use ReInit() or set the JavaCC option STATIC to false");
      System.out.println("       during parser generation.");
      throw new Error();
    }
    jj_initialized_once = true;
    try { jj_input_stream = new SimpleCharStream(stream, encoding, 1, 1); } catch(java.io.UnsupportedEncodingException e) { throw new RuntimeException(e); }
    token_source = new StefanMakTokenManager(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 35; i++) jj_la1[i] = -1;
    for (int i = 0; i < jj_2_rtns.length; i++) jj_2_rtns[i] = new JJCalls();
  }

  /** Reinitialise. */
  static public void ReInit(java.io.InputStream stream) {
     ReInit(stream, null);
  }
  /** Reinitialise. */
  static public void ReInit(java.io.InputStream stream, String encoding) {
    try { jj_input_stream.ReInit(stream, encoding, 1, 1); } catch(java.io.UnsupportedEncodingException e) { throw new RuntimeException(e); }
    token_source.ReInit(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 35; i++) jj_la1[i] = -1;
    for (int i = 0; i < jj_2_rtns.length; i++) jj_2_rtns[i] = new JJCalls();
  }

  /** Constructor. */
  public StefanMak(java.io.Reader stream) {
    if (jj_initialized_once) {
      System.out.println("ERROR: Second call to constructor of static parser. ");
      System.out.println("       You must either use ReInit() or set the JavaCC option STATIC to false");
      System.out.println("       during parser generation.");
      throw new Error();
    }
    jj_initialized_once = true;
    jj_input_stream = new SimpleCharStream(stream, 1, 1);
    token_source = new StefanMakTokenManager(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 35; i++) jj_la1[i] = -1;
    for (int i = 0; i < jj_2_rtns.length; i++) jj_2_rtns[i] = new JJCalls();
  }

  /** Reinitialise. */
  static public void ReInit(java.io.Reader stream) {
    jj_input_stream.ReInit(stream, 1, 1);
    token_source.ReInit(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 35; i++) jj_la1[i] = -1;
    for (int i = 0; i < jj_2_rtns.length; i++) jj_2_rtns[i] = new JJCalls();
  }

  /** Constructor with generated Token Manager. */
  public StefanMak(StefanMakTokenManager tm) {
    if (jj_initialized_once) {
      System.out.println("ERROR: Second call to constructor of static parser. ");
      System.out.println("       You must either use ReInit() or set the JavaCC option STATIC to false");
      System.out.println("       during parser generation.");
      throw new Error();
    }
    jj_initialized_once = true;
    token_source = tm;
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 35; i++) jj_la1[i] = -1;
    for (int i = 0; i < jj_2_rtns.length; i++) jj_2_rtns[i] = new JJCalls();
  }

  /** Reinitialise. */
  public void ReInit(StefanMakTokenManager tm) {
    token_source = tm;
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 35; i++) jj_la1[i] = -1;
    for (int i = 0; i < jj_2_rtns.length; i++) jj_2_rtns[i] = new JJCalls();
  }

  static private Token jj_consume_token(int kind) throws ParseException {
    Token oldToken;
    if ((oldToken = token).next != null) token = token.next;
    else token = token.next = token_source.getNextToken();
    jj_ntk = -1;
    if (token.kind == kind) {
      jj_gen++;
      if (++jj_gc > 100) {
        jj_gc = 0;
        for (int i = 0; i < jj_2_rtns.length; i++) {
          JJCalls c = jj_2_rtns[i];
          while (c != null) {
            if (c.gen < jj_gen) c.first = null;
            c = c.next;
          }
        }
      }
      return token;
    }
    token = oldToken;
    jj_kind = kind;
    throw generateParseException();
  }

  static private final class LookaheadSuccess extends java.lang.Error { }
  static final private LookaheadSuccess jj_ls = new LookaheadSuccess();
  static private boolean jj_scan_token(int kind) {
    if (jj_scanpos == jj_lastpos) {
      jj_la--;
      if (jj_scanpos.next == null) {
        jj_lastpos = jj_scanpos = jj_scanpos.next = token_source.getNextToken();
      } else {
        jj_lastpos = jj_scanpos = jj_scanpos.next;
      }
    } else {
      jj_scanpos = jj_scanpos.next;
    }
    if (jj_rescan) {
      int i = 0; Token tok = token;
      while (tok != null && tok != jj_scanpos) { i++; tok = tok.next; }
      if (tok != null) jj_add_error_token(kind, i);
    }
    if (jj_scanpos.kind != kind) return true;
    if (jj_la == 0 && jj_scanpos == jj_lastpos) throw jj_ls;
    return false;
  }


/** Get the next Token. */
  static final public Token getNextToken() {
    if (token.next != null) token = token.next;
    else token = token.next = token_source.getNextToken();
    jj_ntk = -1;
    jj_gen++;
    return token;
  }

/** Get the specific Token. */
  static final public Token getToken(int index) {
    Token t = token;
    for (int i = 0; i < index; i++) {
      if (t.next != null) t = t.next;
      else t = t.next = token_source.getNextToken();
    }
    return t;
  }

  static private int jj_ntk() {
    if ((jj_nt=token.next) == null)
      return (jj_ntk = (token.next=token_source.getNextToken()).kind);
    else
      return (jj_ntk = jj_nt.kind);
  }

  static private java.util.List<int[]> jj_expentries = new java.util.ArrayList<int[]>();
  static private int[] jj_expentry;
  static private int jj_kind = -1;
  static private int[] jj_lasttokens = new int[100];
  static private int jj_endpos;

  static private void jj_add_error_token(int kind, int pos) {
    if (pos >= 100) return;
    if (pos == jj_endpos + 1) {
      jj_lasttokens[jj_endpos++] = kind;
    } else if (jj_endpos != 0) {
      jj_expentry = new int[jj_endpos];
      for (int i = 0; i < jj_endpos; i++) {
        jj_expentry[i] = jj_lasttokens[i];
      }
      jj_entries_loop: for (java.util.Iterator<?> it = jj_expentries.iterator(); it.hasNext();) {
        int[] oldentry = (int[])(it.next());
        if (oldentry.length == jj_expentry.length) {
          for (int i = 0; i < jj_expentry.length; i++) {
            if (oldentry[i] != jj_expentry[i]) {
              continue jj_entries_loop;
            }
          }
          jj_expentries.add(jj_expentry);
          break jj_entries_loop;
        }
      }
      if (pos != 0) jj_lasttokens[(jj_endpos = pos) - 1] = kind;
    }
  }

  /** Generate ParseException. */
  static public ParseException generateParseException() {
    jj_expentries.clear();
    boolean[] la1tokens = new boolean[51];
    if (jj_kind >= 0) {
      la1tokens[jj_kind] = true;
      jj_kind = -1;
    }
    for (int i = 0; i < 35; i++) {
      if (jj_la1[i] == jj_gen) {
        for (int j = 0; j < 32; j++) {
          if ((jj_la1_0[i] & (1<<j)) != 0) {
            la1tokens[j] = true;
          }
          if ((jj_la1_1[i] & (1<<j)) != 0) {
            la1tokens[32+j] = true;
          }
        }
      }
    }
    for (int i = 0; i < 51; i++) {
      if (la1tokens[i]) {
        jj_expentry = new int[1];
        jj_expentry[0] = i;
        jj_expentries.add(jj_expentry);
      }
    }
    jj_endpos = 0;
    jj_rescan_token();
    jj_add_error_token(0, 0);
    int[][] exptokseq = new int[jj_expentries.size()][];
    for (int i = 0; i < jj_expentries.size(); i++) {
      exptokseq[i] = jj_expentries.get(i);
    }
    return new ParseException(token, exptokseq, tokenImage);
  }

  /** Enable tracing. */
  static final public void enable_tracing() {
  }

  /** Disable tracing. */
  static final public void disable_tracing() {
  }

  static private void jj_rescan_token() {
    jj_rescan = true;
    for (int i = 0; i < 2; i++) {
    try {
      JJCalls p = jj_2_rtns[i];
      do {
        if (p.gen > jj_gen) {
          jj_la = p.arg; jj_lastpos = jj_scanpos = p.first;
          switch (i) {
            case 0: jj_3_1(); break;
            case 1: jj_3_2(); break;
          }
        }
        p = p.next;
      } while (p != null);
      } catch(LookaheadSuccess ls) { }
    }
    jj_rescan = false;
  }

  static private void jj_save(int index, int xla) {
    JJCalls p = jj_2_rtns[index];
    while (p.gen > jj_gen) {
      if (p.next == null) { p = p.next = new JJCalls(); break; }
      p = p.next;
    }
    p.gen = jj_gen + xla - jj_la; p.first = token; p.arg = xla;
  }

  static final class JJCalls {
    int gen;
    Token first;
    int arg;
    JJCalls next;
  }

}
