package model.visioimport;

import java.util.Vector;

import bi.view.lang.CMMessages;

public class FlowObject {
	private String text;
	private String name="Event";
	private Vector<String> actions;
	private String event=CMMessages.getString("BR_DEFAULT_EVENT_VALUE");
	private String iniStateName;
	private String endStateName;
	private int iniState=-1;
	private int endState=-1;
	private boolean used;
	private boolean german= false;
	
	public boolean isUsed() {
		return used;
	}
	public void setUsed(boolean used) {
		this.used = used;
	}
	public FlowObject(String l_event, Vector<String> l_actions, String beginState, String endState2, boolean p_GermanSyntax) {
		 event=l_event;
		 actions=l_actions;
		 iniStateName=beginState;
		 endStateName=endState2;
		 german=p_GermanSyntax;
		 if(german){
			 name="Ereignis";
		 }
	}
	public void addAction(String p_Action){
		actions.addElement(p_Action);
	}
	public String removeAction(String p_Action){
		String l_Action=actions.elementAt(actions.indexOf(p_Action));
		actions.remove(p_Action);
		return l_Action;
	}
	public String removeAction(int index){
		return actions.remove(index);
	}
	
	public String getEndStateName() {
		return endStateName;
	}
	public void setEndStateName(String endState) {
		this.endStateName = endState;
	}
	public String getEvent() {
		return event;
	}
	public void setEvent(String event) {
		this.event = event;
	}
	
	public String getIniStateName() {
		return iniStateName;
	}
	public void setIniStateName(String iniState) {
		this.iniStateName = iniState;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String toString(){
		StringBuffer result= new StringBuffer();
		if(german){
			if(event !=null && !event.equals("")){
				result.append("WENN(");
				result.append(name);
				result.append("=\"");
				result.append(event);
				result.append("\")DANN\n");
				for (String element: actions) {
					result.append("GESCHAEFTSAKTION{");
					result.append(element);
					result.append("}\n");
				}
			}
			else{
				result.append("WENN(");
				result.append(name);
				result.append("=\"");
				result.append(CMMessages.getString("BR_DEFAULT_EVENT_VALUE"));
				result.append("\")DANN\n");
				for (String element: actions) {
					result.append("GESCHAEFTSAKTION{");
					result.append(element);
					result.append("}\n");
				}
			}
		}
		else{
			if(event !=null && !event.equals("")){
				result.append("IF(");
				result.append(name);
				result.append("=\"");
				result.append(event);
				result.append("\")THEN\n");
				for (String element: actions) {
					result.append("BUSINESS ACTION{");
					result.append(element);
					result.append("}\n");
				}
			}
			else{
				result.append("IF(");
				result.append(name);
				result.append("=\"");
				result.append(CMMessages.getString("BR_DEFAULT_EVENT_VALUE"));
				result.append("\")THEN\n");
				for (String element: actions) {
					result.append("BUSINESS ACTION{");
					result.append(element);
					result.append("}\n");
				}
			}
		}
		return result.toString();
	}
	public int getEndState() {
		return endState;
	}
	public void setEndState(int endState) {
		this.endState = endState;
	}
	public int getIniState() {
		return iniState;
	}
	public void setIniState(int iniState) {
		this.iniState = iniState;
	}
	public Vector<String> getActions() {
		return actions;
	}
	public boolean isGerman() {
		return german;
	}
	public void setGerman(boolean german) {
		this.german = german;
	}
	
}

	