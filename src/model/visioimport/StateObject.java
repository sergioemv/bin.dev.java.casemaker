package model.visioimport;

import java.util.Vector;

import bi.view.lang.CMMessages;

public class StateObject {

	private String type;
	private int id;
	private String action=CMMessages.getString("BR_ACTION_DEFAULT_VALUE");
	private Vector<FlowObject> exitFlows;
	private ConstraintObject constraint;
	private boolean german=false;
	public StateObject(String p_type, int p_id, String p_BusinessAction,boolean p_GermanSyntax) {
		type=p_type;
		id=p_id;
		action=p_BusinessAction;
		exitFlows=new Vector<FlowObject>();
		german=p_GermanSyntax;
	}
	public void addExitFlow(FlowObject p_FlowObject){
		exitFlows.addElement(p_FlowObject);
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String toString(){
		StringBuffer result= new StringBuffer();
		
		if(german){
			if(action != null && !action.equals("")){
				result.append(getConstraint().getStatePrefix());
				result.append("GESCHAEFTSAKTION{");
				result.append(action);
				result.append("}");
				result.append(getConstraint().getStateSuffix());
			}
			else{
				if(!type.equals("Final State")){
				result.append(getConstraint().getStatePrefix());
				result.append("GESCHAEFTSAKTION{");
				result.append(CMMessages.getString("BR_ACTION_DEFAULT_VALUE"));
				result.append("}");
				result.append(getConstraint().getStateSuffix());
				}
			}
		}
		else{
			if(action != null && !action.equals("")){
				result.append(getConstraint().getStatePrefix());
				result.append("BUSINESS ACTION{");
				result.append(action);
				result.append("}");
				result.append(getConstraint().getStateSuffix());
			}
			else{
				if(!type.equals("Final State")){
					result.append(getConstraint().getStatePrefix());
					result.append("BUSINESS ACTION{");
					result.append(CMMessages.getString("BR_ACTION_DEFAULT_VALUE"));
					result.append("}");
					result.append(getConstraint().getStateSuffix());
				}
			}
			
			
		}
		return result.toString();
	}
	public Vector<FlowObject> getExitFlows() {
		return exitFlows;
	}
	public void setExitFlows(Vector<FlowObject> exitFlows) {
		this.exitFlows = exitFlows;
	}
	public boolean isGerman() {
		return german;
	}
	public void setGerman(boolean german) {
		this.german = german;
	}
	public ConstraintObject getConstraint() {
		if (constraint ==null||getType().equalsIgnoreCase("Initial State"))
			constraint = new ConstraintObject();
		return constraint;
	}
	public void setConstraint(ConstraintObject constraint) {
		this.constraint = constraint;
	}
}
