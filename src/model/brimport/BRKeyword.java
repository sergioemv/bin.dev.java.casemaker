package model.brimport;

/**
 * @author smoreno
 *  ennumerates all the JRules operators and the match for BR
 */
public enum BRKeyword{
	AND("AND","UND"),
	OR("OR","ODER"),
	GREATER(">",">"),
	GREATER_EQUAL(">=",">="),
	IN("IS ANY OF","IST IRGENDEIN VON"),
	NOT_IN("IS NOT ANY OF","IST KEIN VON"),
	LOWER("<","<"),
	LOWER_EQUAL("<=","<="),
	IF("IF","WENN"),
	THEN("THEN","DANN"),
	ELSE("ELSE","SONST"),
	ENDIF("ENDIF","WENNENDE"),
	NATIVE("NATIVE","EINGEBOREN"),
	BUSINESS_ACTION("BUSINESS ACTION","GESCHAEFTSAKTION"),
	EQUAL("=","="),
	NEGATIVE("NEGATIVE","NEGATIVE"),
	POSITIVE("POSITIVE","POSITIVE"),
	FAULTY("FAULTY", "FEHLERHAFTE"),
	IRRELEVANT("IRRELEVANT", "IRRELEVANTE"),
	WITH("WITH","MIT"),
	RISKLEVEL("RISK LEVEL","RISIKO-LEVEL"),
	EXPECTEDRESULT("RESULT","ERGEBNIS"),
	REQUIREMENT("REQUIREMENTS","ANFORDERUNGEN"),
	NOBUSINESSACTION("NO BUSINESS ACTION","KEINE GESCHAEFTSAKTION"),
	PLUS("+","+"),
	MINUS("-","-"),
	MULT("*","*"),
	DIVIDE("/","/"),
	NOT("NOT","NO"),
	DISTINCT("!=","!=");



	BRKeyword(String p_english, String p_german) {
		english= p_english;
		german = p_german;
	}

	public boolean isComparationOperator() {

		if (this ==  GREATER ||
			this == GREATER_EQUAL ||
			this == LOWER ||
			this == LOWER_EQUAL ||
			this == EQUAL ||
			this == NOT_IN||
			this == IN ||
			this == DISTINCT)
			return true;
		return false;
		}

	public boolean isLogicalOperator(){
		if (this == AND ||
			this == OR)
			return true;
		return false;
	}

	public boolean isStateKeyword(){
		if (this == NEGATIVE||
				this == POSITIVE ||
				this == FAULTY||
				this == IRRELEVANT)
				return true;
		return false;
	}
	private String german;
	private String english;



	public String getEnglish() {
		return english;
	}
	public String getGerman() {
		return german;
	}
	public String get(BRLanguaje languaje) {
		if (languaje == BRLanguaje.ENGLISH)
			return getEnglish();
		if (languaje == BRLanguaje.GERMAN)
			return getGerman();
		return "";
	}

	public BRKeyword getOpposite(){
		switch (this) {
		case DISTINCT:
			return EQUAL;
		case EQUAL:
			return DISTINCT;
		case LOWER:
			return GREATER_EQUAL;
		case LOWER_EQUAL:
			return GREATER;
		case GREATER_EQUAL:
			return LOWER;
		case GREATER:
			return LOWER_EQUAL;
		case IN:
			return NOT_IN;
		case NOT_IN:
			return IN;
		default:
			break;
		}
		return null;

	}

}