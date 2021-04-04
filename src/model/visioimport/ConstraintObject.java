package model.visioimport;


public class ConstraintObject {

	private int id=-1;
	private boolean german=false;
	private String state;
	private int riskLevel=-1;
	private String reqs;
	private String results;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public boolean isGerman() {
		return german;
	}
	public void setGerman(boolean german) {
		this.german = german;
	}
	public void setState(String state) {
		this.state = state;
	}
	public void setReqs(String reqs) {
		this.reqs = reqs;
	}
	public void setResults(String results) {
		this.results = results;
	}
	public void setRiskLevel(int riskLevel) {
		this.riskLevel = riskLevel;
	}
	public String getReqs() {
		if (reqs==null)
			reqs="";
		return reqs;
	}
	public String getResults() {
		if (results==null)
			results="";
		return results;
	}
	public int getRiskLevel() {
		return riskLevel;
	}
	public String getState() {
		if (state==null)
			state="";
		return state;
	}
	public String getStatePrefix() {
		if (!isGerman())
			return getState()+" ";
		else{
			if (getState().equalsIgnoreCase("POSITIVE"))
				return "POSITIVE ";
			if (getState().equalsIgnoreCase("NEGATIVE"))
				return "NEGATIVE ";
			if (getState().equalsIgnoreCase("FAULTY"))
				return "FEHLERHAFTE ";
			if (getState().equalsIgnoreCase("IRRELEVANT"))
				return "IRRELEVANTE ";
			return "";
		}
	}
		public String getStateSuffix() {
		StringBuilder sb = new StringBuilder();
		if (!hasSuffix())
			return "\n";
		sb.append(" "+getWithToken()+" ");
		if (getRiskLevel()!=-1){
			sb.append("\n\t");
			sb.append(getRiskLevelToken()+"=\"");
			sb.append(getRiskLevel()+"\"");
		}
		if (!getReqs().equalsIgnoreCase("")){
			if (getRiskLevel()!=-1)
				sb.append(",");
			sb.append("\n\t");
			sb.append(getRequirementsToken()+" ");
			sb.append(getReqs());
		}
		if (!getResults().equalsIgnoreCase("")){
			if ((getRiskLevel()!=-1)||(!getReqs().equalsIgnoreCase("")))
				sb.append(",");
			sb.append("\n\t");
			sb.append(getResultsToken()+" ");
			sb.append(getResults());
		}
		sb.append("\n");
		return sb.toString();
	}
		private String getResultsToken() {
			if (isGerman())
				return "ERGEBNIS";
			else
				return "RESULT";
		}
		private String getRequirementsToken() {
			if (isGerman())
				return "ANFORDERUNGEN";
			else
				return "REQUIREMENTS";
		}
		private String getRiskLevelToken() {
			if (isGerman())
				return "RISIKO-LEVEL";
			else
				return "RISK LEVEL";
		}
		private String getWithToken() {
			if (isGerman())
				return "MIT";
			else
				return "WITH";
		}
		private boolean hasSuffix() {
		
			return (getRiskLevel()!=-1)||!getReqs().equalsIgnoreCase("")||!getResults().equalsIgnoreCase("");
		}
	
}
