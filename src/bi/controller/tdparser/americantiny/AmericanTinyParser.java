/* Generated By:JavaCC: Do not edit this line. AmericanTinyParser.java */
package bi.controller.tdparser.americantiny;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.Vector;

import org.apache.log4j.Logger;

import bi.controller.VariablesManager;
import bi.view.mainframeviews.CMApplication;
import bi.view.utils.CMIntervalValue;

public class AmericanTinyParser implements AmericanTinyParserConstants {
        private int incrementValue=0;
        private StringBuffer stringAcumulator;
        private Vector intervalValues= new Vector();
        private Vector intervalToExtraValue= new Vector();
        private String writeFormat;

        public Vector getIntervalValues(){
                return intervalValues;
        }
        public Vector getIntervalsToExtraValue() {
                return intervalToExtraValue;
        }
        public String getstringAcumulator(){
                return stringAcumulator.toString();
        }
        public String calculateValueOfRange(String operLog,String number){
                int p=0;
                if(operLog.equals(">")|| operLog.equals("<>"))
                        p=1;
                else
                        if(operLog.equals("<"))
                                p=-1;
                if(p!=0){
                        String numberToFloat=convertNumberInFloat(number);
                        if(numberToFloat.indexOf(".")>=0){
                                float add=calculateNumOfDecimal(numberToFloat, p);
                                float  result=Float.parseFloat(numberToFloat);
                                result=result+add;
                                NumberFormat nf = NumberFormat.getNumberInstance(new Locale("de", "DE"));
                                DecimalFormat myFormatter = (DecimalFormat)nf;
                                myFormatter.applyPattern(writeFormat);
                                String output = myFormatter.format(result);
                                number=output;//Float.toString(result);
                                CMIntervalValue intervalValue= new CMIntervalValue();
                                intervalValue.setNumber(new Float(result));
                                intervalValue.setLocale(new Locale("de", "DE"));
                                intervalValue.setDiff(new Float(-add));
                                intervalValue.setWriteFormat(writeFormat);
                                intervalToExtraValue.addElement(intervalValue);
                        }
                        else{
                                int result=Integer.parseInt(numberToFloat);
                                result=result+p;
                                NumberFormat nf = NumberFormat.getNumberInstance(new Locale("de", "DE"));
                                DecimalFormat myFormatter = (DecimalFormat)nf;
                                myFormatter.applyPattern(writeFormat);
                                String output = myFormatter.format(result);
                                number=output;//Integer.toString(result);
                                CMIntervalValue intervalValue= new CMIntervalValue();
                                intervalValue.setNumber(new Integer(result));
                                intervalValue.setLocale(new Locale("de", "DE"));
                                intervalValue.setDiff(new Integer(-p));
                                intervalValue.setWriteFormat(writeFormat);
                                intervalToExtraValue.addElement(intervalValue);
                        }
                }
                else{
                        String numberToFloat=convertNumberInFloat(number);
                    if(numberToFloat.indexOf(".")>=0){
                        float  result=Float.parseFloat(numberToFloat);
                        float add=calculateNumOfDecimal(numberToFloat, 1);
                        CMIntervalValue intervalValue= new CMIntervalValue();
                        intervalValue.setNumber(new Float(result));
                        intervalValue.setLocale(Locale.US);
                        if(operLog.indexOf(">") >= 0){
                                intervalValue.setDiff(new Float(-1));
                        }
                        else{
                                intervalValue.setDiff(new Float(Math.abs(1)));
                        }
                        intervalValue.setWriteFormat(writeFormat);
                        intervalToExtraValue.addElement(intervalValue);
                    }
                    else{
                        int result=Integer.parseInt(numberToFloat);
                        CMIntervalValue intervalValue= new CMIntervalValue();
                        intervalValue.setNumber(new Integer(result));
                        intervalValue.setLocale(Locale.US);
                        if(operLog.indexOf(">") >= 0){
                                intervalValue.setDiff(new Integer(-1));
                        }
                        else{
                                intervalValue.setDiff(new Integer(Math.abs(1)));
                        }
                        intervalValue.setWriteFormat(writeFormat);
                        intervalToExtraValue.addElement(intervalValue);
                    }

                }
                return number;
        }
        public String convertNumberInFloat(String number){
                if(number.indexOf(".")>=0 ){
                        writeFormat="###,###.#";
                }
                else{
                        writeFormat="###.#";
                }
                while(number.indexOf(".")>=0){
            number=number.replace('.','#');
        }
        number = number.replaceAll("#", "");
                number=number.replaceAll(",",".");
                return number;

        }

        public float calculateNumOfDecimal(String numFloat, int dif)
    {

        String decimals= numFloat.substring(numFloat.indexOf(".")+1,numFloat.length());
        int cant= decimals.length();
        StringBuffer result;
        if(cant>0)
        {
            if(dif<0)
                                result=new StringBuffer("-0.");
            else
                result=new StringBuffer("0.");
            for(int i=1; i<cant;i++)
            {
                                result.append("0");
                                writeFormat=writeFormat+"#";
            }
            if(dif == 0)
                result.append("0");
            else
                result.append("1");
            Logger.getLogger(this.getClass()).debug(result.toString());
            return Float.parseFloat(result.toString());
        }
        else
        {
            if(dif<0)
                                return -1f;
            else
                return 1f;
        }
    }

//TOKEN: {<STRINGS: (["a"-"z","A"-"Z","0"-"9"," ","-","_"])*>}
  final public void start() throws ParseException {
                intervalValues= new Vector();
    formalPolicy();
    jj_consume_token(0);
  }

  final public void formalPolicy() throws ParseException {
    content();
  }

  final public void content() throws ParseException {
    mathInval();
  }

  final public void mathInval() throws ParseException {
        String firstBracket;
        String secondBracket;
        String operLogic="";
        String number;
        Token t;
        String var;
    firstBracket = brackets();
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case INTEGER:
      t = jj_consume_token(INTEGER);
                number=t.image;
                if(firstBracket.equals("["))
                        operLogic=">=";
                else
                        operLogic=">";
                intervalValues.addElement(calculateValueOfRange(operLogic,number));
      break;
    case Variable:
      var = variables();
        number=var;
                if(firstBracket.equals("["))
                        operLogic=">=";
                else
                        operLogic=">";
                intervalValues.addElement(calculateValueOfRange(operLogic,number));
      break;
    default:
      jj_la1[0] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
    jj_consume_token(COMA);
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case INTEGER:
      t = jj_consume_token(INTEGER);
                number=t.image;
      break;
    case Variable:
      var = variables();
                        number = var;
      break;
    default:
      jj_la1[1] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
    secondBracket = brackets();
                if(secondBracket.equals("]"))
                        operLogic="<=";
                else
                        operLogic="<";
                intervalValues.addElement(calculateValueOfRange(operLogic,number));
  }

  final public String brackets() throws ParseException {
        Token t;
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case BRACKETOPEN:
      t = jj_consume_token(BRACKETOPEN);
      break;
    case BRACKETCLOSE:
      t = jj_consume_token(BRACKETCLOSE);
      break;
    default:
      jj_la1[2] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
                {if (true) return t.image;}
    throw new Error("Missing return statement in function");
  }

  final public String variables() throws ParseException {
        String result;
        Token t;
        String temp;
    t = jj_consume_token(Variable);
                result = t.image;
    t = jj_consume_token(STRINGS);
    temp = text(t.image);
                result = result+temp;
                String tempResult = VariablesManager.returnImplicitExplicitVariable(result,CMApplication.frame.getGridTDStructure().getTDStructure());
                if(!tempResult.equalsIgnoreCase("")){
                        {if (true) return tempResult;}
                }
                else{
                        {if (true) return result;}
                }
    throw new Error("Missing return statement in function");
  }

  final public String text(String string) throws ParseException {
        StringBuffer result=new StringBuffer();
        Token t;
                result.append(string);
    label_1:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case STRINGS:
        ;
        break;
      default:
        jj_la1[3] = jj_gen;
        break label_1;
      }
      t = jj_consume_token(STRINGS);
                        result.append(t.image);
    }
                {if (true) return result.toString();}
    throw new Error("Missing return statement in function");
  }

  public AmericanTinyParserTokenManager token_source;
  SimpleCharStream jj_input_stream;
  public Token token, jj_nt;
  private int jj_ntk;
  private int jj_gen;
  final private int[] jj_la1 = new int[4];
  static private int[] jj_la1_0;
  static {
      jj_la1_0();
   }
   private static void jj_la1_0() {
      jj_la1_0 = new int[] {0x8400,0x8400,0x300,0x10000,};
   }

  public AmericanTinyParser(java.io.InputStream stream) {
     this(stream, null);
  }
  public AmericanTinyParser(java.io.InputStream stream, String encoding) {
    try { jj_input_stream = new SimpleCharStream(stream, encoding, 1, 1); } catch(java.io.UnsupportedEncodingException e) { throw new RuntimeException(e); }
    token_source = new AmericanTinyParserTokenManager(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 4; i++) jj_la1[i] = -1;
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
    for (int i = 0; i < 4; i++) jj_la1[i] = -1;
  }

  public AmericanTinyParser(java.io.Reader stream) {
    jj_input_stream = new SimpleCharStream(stream, 1, 1);
    token_source = new AmericanTinyParserTokenManager(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 4; i++) jj_la1[i] = -1;
  }

  public void ReInit(java.io.Reader stream) {
    jj_input_stream.ReInit(stream, 1, 1);
    token_source.ReInit(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 4; i++) jj_la1[i] = -1;
  }

  public AmericanTinyParser(AmericanTinyParserTokenManager tm) {
    token_source = tm;
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 4; i++) jj_la1[i] = -1;
  }

  public void ReInit(AmericanTinyParserTokenManager tm) {
    token_source = tm;
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 4; i++) jj_la1[i] = -1;
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
    boolean[] la1tokens = new boolean[17];
    for (int i = 0; i < 17; i++) {
      la1tokens[i] = false;
    }
    if (jj_kind >= 0) {
      la1tokens[jj_kind] = true;
      jj_kind = -1;
    }
    for (int i = 0; i < 4; i++) {
      if (jj_la1[i] == jj_gen) {
        for (int j = 0; j < 32; j++) {
          if ((jj_la1_0[i] & (1<<j)) != 0) {
            la1tokens[j] = true;
          }
        }
      }
    }
    for (int i = 0; i < 17; i++) {
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
