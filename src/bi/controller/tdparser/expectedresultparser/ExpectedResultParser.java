/* Generated By:JavaCC: Do not edit this line. ExpectedResultParser.java */
package bi.controller.tdparser.expectedresultparser;

import java.util.List;
import java.util.ArrayList;

public class ExpectedResultParser implements ExpectedResultParserConstants {

        private List<String> columnHeader;
        private List<String> columnValue;

        public List<String> getColumnHeader(){
                if(columnHeader == null)
                        columnHeader = new ArrayList<String>();
                return columnHeader;
        }

        public List<String> getColumnValue(){
                if(columnValue == null)
                        columnValue = new ArrayList<String>();
                return columnValue;
        }

  final public void start() throws ParseException {
                columnValue = new ArrayList<String>();
                columnHeader = new ArrayList<String>();
    formalPolicy();
    jj_consume_token(0);
  }

  final public void formalPolicy() throws ParseException {
    setValues();
  }

  final public void setValues() throws ParseException {
    set();
  }

  final public void set() throws ParseException {
        Token t;
    t = jj_consume_token(STRINGS);
         columnHeader.add(t.image);
    jj_consume_token(EQUAL);
    t = jj_consume_token(STRINGS);
         columnValue.add(t.image);
    label_1:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case SEPARATOR:
        ;
        break;
      default:
        jj_la1[0] = jj_gen;
        break label_1;
      }
      moreElements();
    }
  }

  final public void moreElements() throws ParseException {
        Token t;
    jj_consume_token(SEPARATOR);
    t = jj_consume_token(STRINGS);
         columnHeader.add(t.image);
    jj_consume_token(EQUAL);
    t = jj_consume_token(STRINGS);
         columnValue.add(t.image);
  }

  public ExpectedResultParserTokenManager token_source;
  SimpleCharStream jj_input_stream;
  public Token token, jj_nt;
  private int jj_ntk;
  private int jj_gen;
  final private int[] jj_la1 = new int[1];
  static private int[] jj_la1_0;
  static {
      jj_la1_0();
   }
   private static void jj_la1_0() {
      jj_la1_0 = new int[] {0x80,};
   }

  public ExpectedResultParser(java.io.InputStream stream) {
     this(stream, null);
  }
  public ExpectedResultParser(java.io.InputStream stream, String encoding) {
    try { jj_input_stream = new SimpleCharStream(stream, encoding, 1, 1); } catch(java.io.UnsupportedEncodingException e) { throw new RuntimeException(e); }
    token_source = new ExpectedResultParserTokenManager(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 1; i++) jj_la1[i] = -1;
  }

  public void ReInit(java.io.InputStream stream) {
     ReInit(stream, null);
  }
  public void ReInit(java.io.InputStream stream, String encoding) {
    try { jj_input_stream.ReInit(stream, encoding, 1, 1); } catch(java.io.UnsupportedEncodingException e) { throw new RuntimeException(e); }
    token_source.ReInit(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 1; i++) jj_la1[i] = -1;
  }

  public ExpectedResultParser(java.io.Reader stream) {
    jj_input_stream = new SimpleCharStream(stream, 1, 1);
    token_source = new ExpectedResultParserTokenManager(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 1; i++) jj_la1[i] = -1;
  }

  public void ReInit(java.io.Reader stream) {
    jj_input_stream.ReInit(stream, 1, 1);
    token_source.ReInit(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 1; i++) jj_la1[i] = -1;
  }

  public ExpectedResultParser(ExpectedResultParserTokenManager tm) {
    token_source = tm;
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 1; i++) jj_la1[i] = -1;
  }

  public void ReInit(ExpectedResultParserTokenManager tm) {
    token_source = tm;
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 1; i++) jj_la1[i] = -1;
  }

  final private Token jj_consume_token(int kind) throws ParseException {
    Token oldToken;
    if ((oldToken = token).next != null) token = token.next;
    else token = token.next = token_source.getNextToken();
    jj_ntk = -1;
    if (token.kind == kind) {
      jj_gen++;
      return token;
    }
    token = oldToken;
    jj_kind = kind;
    throw generateParseException();
  }

  final public Token getNextToken() {
    if (token.next != null) token = token.next;
    else token = token.next = token_source.getNextToken();
    jj_ntk = -1;
    jj_gen++;
    return token;
  }

  final public Token getToken(int index) {
    Token t = token;
    for (int i = 0; i < index; i++) {
      if (t.next != null) t = t.next;
      else t = t.next = token_source.getNextToken();
    }
    return t;
  }

  final private int jj_ntk() {
    if ((jj_nt=token.next) == null)
      return (jj_ntk = (token.next=token_source.getNextToken()).kind);
    else
      return (jj_ntk = jj_nt.kind);
  }

  private java.util.Vector jj_expentries = new java.util.Vector();
  private int[] jj_expentry;
  private int jj_kind = -1;

  public ParseException generateParseException() {
    jj_expentries.removeAllElements();
    boolean[] la1tokens = new boolean[10];
    for (int i = 0; i < 10; i++) {
      la1tokens[i] = false;
    }
    if (jj_kind >= 0) {
      la1tokens[jj_kind] = true;
      jj_kind = -1;
    }
    for (int i = 0; i < 1; i++) {
      if (jj_la1[i] == jj_gen) {
        for (int j = 0; j < 32; j++) {
          if ((jj_la1_0[i] & (1<<j)) != 0) {
            la1tokens[j] = true;
          }
        }
      }
    }
    for (int i = 0; i < 10; i++) {
      if (la1tokens[i]) {
        jj_expentry = new int[1];
        jj_expentry[0] = i;
        jj_expentries.addElement(jj_expentry);
      }
    }
    int[][] exptokseq = new int[jj_expentries.size()][];
    for (int i = 0; i < jj_expentries.size(); i++) {
      exptokseq[i] = (int[])jj_expentries.elementAt(i);
    }
    return new ParseException(token, exptokseq, tokenImage);
  }

  final public void enable_tracing() {
  }

  final public void disable_tracing() {
  }

}
