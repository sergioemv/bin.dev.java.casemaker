package model.visioimport;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;

public class WorkFlowObject {
	
	private static final int MAX_STATES=1000;
	private Vector<FlowObject> flowObjects;
	private List<ConstraintObject> constraints;
	private List<RelationObject> relations;
	private StateObject[] stateObjects;
	private int[] stateObjectsIndex;
	private String name;
	private int index=0; 
	private String logOfErrors;
	private boolean german= false;
	public String getLogOfErrors() {
		return logOfErrors;
	}

	public void setLogOfErrors(String logOfErrors) {
		this.logOfErrors = logOfErrors;
	}
	public void addConstraint(ConstraintObject co)
	{
		this.getConstraints().add(co);
	}
	public void addRelation(RelationObject ro){
		this.getRelations().add(ro);
	}
	public WorkFlowObject(String p_Name, boolean p_GermanSyntax) {
		name=p_Name;
		flowObjects= new Vector<FlowObject>();
		stateObjects= new StateObject[MAX_STATES];
		stateObjectsIndex= new int[MAX_STATES];
		logOfErrors= new String();
		german= p_GermanSyntax;
	}

	public void addFlowObject(FlowObject p_FlowObject){
		flowObjects.addElement(p_FlowObject);
	}
	
	public void addStateObject(StateObject p_StateObject, int p_Index){
		stateObjects[p_Index]= p_StateObject;
		stateObjectsIndex[index++]=p_Index;
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
			result.append("/**\n");
			result.append("*Arbeitsfluss :");
			result.append(name);
			result.append("\n*/\n");
		}
		else{
			result.append("/**\n");
			result.append("*WorkFlow :");
			result.append(name);
			result.append("\n*/\n");
		}
		return result.toString();
	}

	public Vector<FlowObject> getFlowObjects() {
		return flowObjects;
	}

	public void setFlowObjects(Vector<FlowObject> flowObjects) {
		this.flowObjects = flowObjects;
	}

	public StateObject[] getStateObjects() {
		return stateObjects;
	}

	public void setStateObjects(StateObject[] stateObjects) {
		this.stateObjects = stateObjects;
	}

	public int[] getStateObjectsIndex() {
		int[] l_stateObjectsIndex= new int[index];
		for (int i = 0; i < index; i++) {
			l_stateObjectsIndex[i]=stateObjectsIndex[i];
		}
		Arrays.sort(l_stateObjectsIndex);
		return l_stateObjectsIndex;
	}

	public int getIndex() {
		return index;
	}

	public boolean isGerman() {
		return german;
	}

	public void setGerman(boolean german) {
		this.german = german;
	}

	public List<ConstraintObject> getConstraints() {
		if (constraints==null)
			constraints=new ArrayList<ConstraintObject>();
		return constraints;
	}

	public List<RelationObject> getRelations() {
		if (relations == null)
			relations = new ArrayList<RelationObject>();
		return relations;
	}

	public ConstraintObject getConstraint(int id) {
		for (ConstraintObject constraint: getConstraints())
			if (constraint!=null)
			if (constraint.getId()==id)
				return constraint;
		return null;
		
	}

	public StateObject getState(int id) {
		for (StateObject state: getStateObjects())
			if (state!=null)
			if (state.getId()==id)
				return state;
		return null;
	}

	public void appendLog(String msg) {
		setLogOfErrors(this.getLogOfErrors()+"\n"+msg);
		
	}
}
