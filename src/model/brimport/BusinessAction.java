package model.brimport;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class BusinessAction implements BRPart{

	private IFPart ifPart;
	private BRKeyword state = BRKeyword.POSITIVE;
	private String body;
	private List<String> requirements;
	private Map<String,String> expectedResults;
	private int riskLevel = 0;


	public BusinessAction(String string) {
		body = string;
	}

	public BusinessAction() {
		// TODO Auto-generated constructor stub
	}

	public IFPart getIfPart() {
		return ifPart;
	}

	void setIfPart(IFPart part) {
		this.ifPart = part;
	}

	public BRLanguaje getLanguaje() {
		return getIfPart().getLanguaje();
	}

	public BRKeyword getState() {
		return state;
	}

	public void setState(BRKeyword state) throws Exception {
		if (!state.isStateKeyword())
			throw new Exception("This is not a valid state");
		this.state = state;
	}

	public String getBody() {
		if (body==null)
			body = "";
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public int getRiskLevel() {
		return riskLevel;
	}

	public void setRiskLevel(int riskLevel) throws Exception {
		if (riskLevel>10 || riskLevel<0)
			throw new Exception("Invalid risk level");
		this.riskLevel = riskLevel;
	}

	public Map<String, String> getExpectedResults() {
		if (expectedResults==null)
			expectedResults = new HashMap<String, String>();
		return expectedResults;
	}

	public List<String> getRequirements() {
		if (requirements == null)
			requirements = new ArrayList<String>();
		return requirements;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		if (getState()!=BRKeyword.POSITIVE)
			builder.append(getState().get(getLanguaje())+" ");
		builder.append(BRKeyword.BUSINESS_ACTION.get(getLanguaje()));
		builder.append(" {" + getBody()+"}");
		if (((getRequirements().size() + getExpectedResults().size())>0) || getRiskLevel()>0){
			builder.append(" "+BRKeyword.WITH.get(getLanguaje())+" ");
			if (getRiskLevel()>0)
				builder.append(" "+BRKeyword.RISKLEVEL.get(getLanguaje()) +"=\""+getRiskLevel()+"\", ");
			if (getExpectedResults().size()>0){
				builder.append(BRKeyword.EXPECTEDRESULT.get(getLanguaje())+" { ");
				for (String key : getExpectedResults().keySet())
					builder.append(key+"=\""+getExpectedResults().get(key)+"\""+", ");
				builder.deleteCharAt(builder.lastIndexOf(","));
				builder.append("},");
			}
			if (getRequirements().size()>0){
				builder.append(BRKeyword.REQUIREMENT.get(getLanguaje())+" { ");
				for (String req : getRequirements())
					builder.append("\""+req+"\""+", ");
				builder.deleteCharAt(builder.lastIndexOf(","));
				builder.append("},");
			}
			builder.deleteCharAt(builder.lastIndexOf(","));
		}
		return builder.toString();
	}

	public Set<String> getInvalidIdentifiers() {
		//anything is allowed in a Business action body
		return new HashSet<String>();
	}
}
