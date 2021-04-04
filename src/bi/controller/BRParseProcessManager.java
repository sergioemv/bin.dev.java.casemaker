package bi.controller;

import java.io.BufferedReader;
import java.io.Reader;

import model.brmodel.FormalPolicy;

import org.apache.log4j.Logger;

import bi.controller.brparser.english.EnglishParser;
import bi.controller.brparser.german.GermanParser;




public class BRParseProcessManager {
	
	public static final BRParseProcessManager INSTANCE = new BRParseProcessManager();
    public BRParseProcessManager() {
        m_EnglishParser = null;
        m_GermanParser = null;
        englishInitialized = false;
        germanInitialized = false;

    }

    private void initializeParser(Reader reader,int fileSyntax) {
        
        Reader bufferedReader = null;
		switch(fileSyntax){
        	case 0:{
            		bufferedReader = new BufferedReader(reader);
            		if (englishInitialized) {
               			m_EnglishParser.ReInit(bufferedReader);
                        this.setErrorLine(-1);//fcastro_13092004

            		}
            		else {
               			m_EnglishParser = new EnglishParser(bufferedReader);
                        this.setErrorLine(-1);//fcastro_13092004
               			englishInitialized = true;
            		}
        		
        		break;
        	}
        	case 1:{
					bufferedReader = new BufferedReader(reader);
            		if (germanInitialized) {
               			m_GermanParser.ReInit(bufferedReader);
                      
            		}
            		else {
               			m_GermanParser = new GermanParser(bufferedReader);
               			germanInitialized = true;
            		}
        		break;
        	}
        	default:{
        		Logger.getLogger(this.getClass()).error("Invalid syntax value");
        	}
        }
    }

    public String doCheck(Reader reader,int fileSyntax,boolean check){
        initializeParser(reader,fileSyntax);
        switch(fileSyntax){
        	case 0:{
        		try {
            		try {
                		m_EnglishParser.setCheck(check);
                		m_EnglishParser.start();
                		} catch (bi.controller.brparser.english.ParseException e) {
							this.setErrorLine(e.currentToken.next.beginLine);//fcastro_13092004
                    		return e.getMessage();
                		}
                		
        		} catch (bi.controller.brparser.english.TokenMgrError t) {
						this.setErrorLine(t.m_ErrorLine);//fcastro_13092004						
            			return "Lexical Error, Expecting: \" , { , }, [ , ]";//cc t.getMessage();
        		}
        		
        		m_EnglishParser.getM_FormalPolicy().setSourceSyntax(0);
        		return "ok";
        	}
        	case 1:{
				try {
            		try {
                		m_GermanParser.setCheck(check);
                		m_GermanParser.start();
                		} catch (bi.controller.brparser.german.ParseException e) {
							this.setErrorLine(e.currentToken.next.beginLine);//fcastro_13092004
                    		return e.getMessage();
                		}
        		} catch (bi.controller.brparser.german.TokenMgrError t) {
						this.setErrorLine(t.m_ErrorLine);//fcastro_13092004
            			return t.getMessage();
        		}
        		m_GermanParser.getM_FormalPolicy().setSourceSyntax(1);
        		return "ok";
        	}
        	default:{return " ";}
        }


    }
    public FormalPolicy getEnglishFormalPolicy(){
        return this.getM_EnglishParser().getM_FormalPolicy();
    }
    public FormalPolicy getGermanFormalPolicy(){
        return this.getM_GermanParser().getM_FormalPolicy();
    }

    public EnglishParser getM_EnglishParser(){ return m_EnglishParser; }

    public void setM_EnglishParser(EnglishParser m_EnglishParser){ this.m_EnglishParser = m_EnglishParser; }

    public GermanParser getM_GermanParser(){
            return m_GermanParser;
        }

    public void setM_GermanParser(GermanParser m_GermanParser){
            this.m_GermanParser = m_GermanParser;
        }
//fcastro_13092004_begin
	public  int getErrorLine(){
		return currentLine;
	}
	public  void setErrorLine(int line){
		currentLine = line;
	}
    private int currentLine=-1;
    //fcastro_13092004_end

    private EnglishParser m_EnglishParser;

    private GermanParser m_GermanParser;

    private boolean englishInitialized=false;

    private boolean germanInitialized = false;


}
