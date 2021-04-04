package bi.view.businessrulesviews.brimport;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.Map;
import java.util.Vector;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import model.BusinessRules;
import model.brimport.BRLanguaje;
import model.visioimport.ConstraintObject;
import model.visioimport.FlowObject;
import model.visioimport.RelationObject;
import model.visioimport.StateObject;
import model.visioimport.WorkFlowObject;

import org.apache.log4j.Logger;
import org.apache.xmlbeans.XmlException;

import bi.view.businessrulesviews.brimport.visioparser.TokenMgrError;
import bi.view.businessrulesviews.brimport.visioparser.VisioImport;
import bi.view.lang.CMMessages;
import bi.view.mainframeviews.CMApplication;
import bi.view.utils.event.CMProgressEventHandler;
import bi.view.utils.event.CMProgressListener;
import bi.view.utils.event.CMProgressSource;

public class CMBRVisioImporter implements ICMBRImporter,CMProgressSource {

	private CMProgressEventHandler handler;
	private Vector<WorkFlowObject> workFlowObjects;
	public static final String XSLT_VISIO_IMPORT_PATH = CMApplication.getInstallationDirectory() + "/res/visio.xsl";
	private WorkFlowObject currentWorkFlow;
	private BRLanguaje languaje;

	//portiz_26102007_begin
	//File p_importFileBOM new parametes
	//portiz_26102007_end
	public String doImport(File p_importFile,File p_importFileBOM,
			Map<String, Object> aditionalOptions) throws IOException,
			XmlException, Exception {
		File trasformedVisio= transform(p_importFile);
		if(trasformedVisio != null){
			workFlowObjects = new Vector<WorkFlowObject>();
			currentWorkFlow = null;
			String result=generateBusinessRules(trasformedVisio);
			if(result != null){
				return result;
			}
		}
		return""; //$NON-NLS-1$

	}
	private File transform(File visioFile) throws Exception{
		try {
		String visioPath=visioFile.getAbsolutePath ();
		File output= new File(visioPath+".txt"); //$NON-NLS-1$
		FileOutputStream fileOutPutStream = new FileOutputStream(output);
	    TransformerFactory tFactory = TransformerFactory.newInstance();
	    Transformer transformer = tFactory.newTransformer(new StreamSource(XSLT_VISIO_IMPORT_PATH));
	    transformer.transform(new StreamSource(visioFile), new StreamResult(fileOutPutStream));
	    fileOutPutStream.close();
	    getHandler().fireProgressEventHappen(75, "Transorming Visio File...");
	    return output;
		}
		catch (Exception e) {
			//TestObject selectedTestObject= CMApplication.frame.getTreeWorkspaceView().getCurrentTestObject();
			//JOptionPane.showMessageDialog(CMApplication.frame, CMMessages.getString("LABEL_TRANSFORM_ERROR") +"\n"+ e.getMessage(),selectedTestObject.getName()+" - "+CMMessages.getString("TITLE_TRANSFORM_ERROR"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
	        //	    JOptionPane.ERROR_MESSAGE);
			//errorReport();
			throw new Exception(CMMessages.getString("LABEL_TRANSFORM_ERROR")+":  "+e.getMessage());
		}
		
	}
	private String generateBusinessRules(File trasformedVisio) throws Exception{
		FileReader fileReader;
		String result=null;
		try {
			fileReader = new FileReader(trasformedVisio);
			Reader reader = new BufferedReader(fileReader);
			VisioImport visioImport= new VisioImport(reader);
			visioImport.setImporter(this);
			visioImport.start();
			setOutGoingFlowForEachState();
	        result=getBusinessRulesFromWorkFlowObject();
	        //log= getWarningMessages();
	        //showDialogOfProblems(log);
	        trasformedVisio.delete();
		} catch (Exception e) {

//			errorReport();
			throw new Exception(CMMessages.getString("BR_VISIO_IMPORT_ERROR_IMPORT"));
		}
		catch(TokenMgrError e){
//			errorReport();
			throw new Exception(CMMessages.getString("BR_VISIO_IMPORT_CHAR_ERROR_IMPORT"));
		}
		return result;

	}
	public void setLanguaje(BRLanguaje languaje) {
		this.languaje = languaje;
	}
	public void addProgressListener(CMProgressListener rl) {
		getHandler().addProgressListener(rl);
	}
	public void removeProgressListener(CMProgressListener rl) {
		getHandler().removeProgressListener(rl);
	}
	private CMProgressEventHandler getHandler() {
		if (handler == null)
			handler = new CMProgressEventHandler();
		return handler;
	}
public CMBRVisioImporter() {
	workFlowObjects= new Vector<WorkFlowObject>();
	
}
	private boolean german= false; 
	public boolean isGerman() {
		return german;
	}
	public void setGerman(boolean german) {
		this.german = german;
	}
	
	public WorkFlowObject getCurrentWorkFlow() {
		return currentWorkFlow;
	}
	public void setCurrentWorkFlow(WorkFlowObject currentWorkFlow) {
		this.currentWorkFlow = currentWorkFlow;
	}
	public WorkFlowObject createWorkFlowObject(String name){
		return new WorkFlowObject(name,german);
	}
	public StateObject createStateObject(String p_Type, int p_Id, String p_BusinessAction){
		p_BusinessAction=deleteQuotes(p_BusinessAction);
		p_Type=deleteQuotes(p_Type);
		return new StateObject(p_Type,p_Id, p_BusinessAction,german);
	}
	public String deleteQuotes(String businessAction) {
		String result;
		try{
			int firstQuote= businessAction.indexOf("\"");
			int lastQuote=businessAction.lastIndexOf("\"");
			if(firstQuote==lastQuote){
				result=businessAction.substring(firstQuote+1).trim();
			}
			result=businessAction.substring(firstQuote+1,lastQuote).trim();
		}
		catch(Exception e){
			return businessAction;
		}
		return result;
	}
	public FlowObject createFlowObject(String p_transition, String beginState, String endState){
		String l_event= getEventFromTransition(deleteQuotes(p_transition));
		Vector<String> l_actions= getActionsFromTransition(deleteQuotes(p_transition));
		FlowObject l_FlowObject= new FlowObject(l_event,l_actions,deleteQuotes(beginState),deleteQuotes(endState),german);
		return l_FlowObject;
	}

	private Vector<String> getActionsFromTransition(String p_transition) {
		Vector<String> result= new Vector<String>();
		int indexAcions=p_transition.indexOf("/");
		if(indexAcions >=0){
			String onlyActions=p_transition.substring(indexAcions+1);
			while(onlyActions.length()>0){

				int index = onlyActions.indexOf(",");
				int i=0;
				if(index>=0){
					if (i==0){
						result.addElement(onlyActions.substring(0,index));
						onlyActions=onlyActions.substring(index+1,onlyActions.length());
						i++;
					}
					else{
						result.addElement(onlyActions.substring(0,index));
						onlyActions=onlyActions.substring(0,index+1);
					}					
				}
				else{
					result.addElement(onlyActions);
					onlyActions=onlyActions.substring(0,index+1);
				}
			
				/*int index = onlyActions.indexOf(",");
				if(index>=0){
					result.addElement(onlyActions.substring(0,index));
					onlyActions=onlyActions.substring(0,index+1);
				}
				else{
					result.addElement(onlyActions);
					onlyActions=onlyActions.substring(0,index+1);
				}*///ccastedo comments this 04.05.07
			}
		}
		return result;
	}
	private String getEventFromTransition(String p_transition) {
		int indexAcions=p_transition.indexOf("/");
		if(indexAcions >=0){
			return p_transition.substring(0,indexAcions);
		}
		return p_transition;
	}
	
	public void addStateObjectToCurrentDiagram(StateObject p_StateObject){
		currentWorkFlow.addStateObject(p_StateObject,p_StateObject.getId());
	}
	
	public void addFlowObjectToCurrentDiagram(FlowObject p_FlowObject){
		int index= currentWorkFlow.getFlowObjects().size();
		currentWorkFlow.addFlowObject(p_FlowObject);
		setFlowName(p_FlowObject,index);
	}
	
	public void addWorkFlowObject(WorkFlowObject p_WorFlowObject){
		workFlowObjects.addElement(p_WorFlowObject);
	}

	public void setOutGoingFlowForEachState(){
		for(WorkFlowObject workFlowObject: workFlowObjects){
			Vector<FlowObject> l_flowObjects= workFlowObject.getFlowObjects();
			StateObject[] l_StateObjects= workFlowObject.getStateObjects();
			for(FlowObject l_Flowobject: l_flowObjects){
				String iniState=l_Flowobject.getIniStateName();
				String endState=l_Flowobject.getEndStateName();
				int iniIndexState=getIndexStateObject(iniState,l_StateObjects,workFlowObject);
				int endIndexState=getIndexStateObject(endState,l_StateObjects,workFlowObject);
				l_Flowobject.setIniState(iniIndexState);
				l_Flowobject.setEndState(endIndexState);
				l_StateObjects[iniIndexState].addExitFlow(l_Flowobject);
			}
		}
		//add the constraints also
		setStatesConstraint();
	}
	private int getIndexStateObject(String iniState, StateObject[] stateObjects, WorkFlowObject workFlowObject) {
		int indexDot=iniState.indexOf(".");
		if(indexDot<0){
			for (int i = 0; i < workFlowObject.getIndex(); i++) {
				int index=workFlowObject.getStateObjectsIndex()[i];
				StateObject stateObject = stateObjects[index];
				if(stateObject != null && stateObject.getType().trim().equals(iniState.trim())){
					return index;
				}
			}
		}
		else{
			return new Integer(iniState.substring(indexDot+1)).intValue();
		}
		return 0;
	}
	
	public String getBusinessRulesFromWorkFlowObject(){
		StringBuffer result= new StringBuffer();
		for(WorkFlowObject l_WorkFlowObject: workFlowObjects ){
			result.append(l_WorkFlowObject);
			Vector<StateObject> initialObjects=findInitialObjects(l_WorkFlowObject);
			for(StateObject l_InitialStateObject: initialObjects){
				result.append(addTabs(findStatePaths(l_InitialStateObject, l_WorkFlowObject)));
			}
			logStateWarning(l_WorkFlowObject);
		}
		return result.toString();
	}
	private void logStateWarning(WorkFlowObject workFlowObject) {
		int[] index=workFlowObject.getStateObjectsIndex();
		int count=0;
		for (int i = 0; i < index.length; i++) {
			StateObject state= workFlowObject.getStateObjects()[index[i]];
			if(state.getType().equals("State")){
				if(state.getAction()== null ||state.getAction().trim().equals("")||state.getAction().equals(CMMessages.getString("BR_ACTION_DEFAULT_VALUE"))){
					count ++;
				}
			}
		}
		String log=workFlowObject.getLogOfErrors();
		if(count >0){
			log= log+CMMessages.getString("BR_VISIOIMPORT_LOG_EXIST")+count+CMMessages.getString("BR_VISIOIMPORT_LOG_STATE")+"\n";
			workFlowObject.setLogOfErrors(log);
		}
	}
	private String findStatePaths(StateObject p_StateObject, WorkFlowObject p_WorkFlowObject) {
		//CASO BASE
		if(p_StateObject.getType().equals("Final State")){
			Logger.getLogger(this.getClass()).info("final sate id:" + p_StateObject.getId());
			StringBuffer result=new StringBuffer();
			if (p_StateObject.getConstraint()!=null)
				result.append(p_StateObject.getConstraint().getStatePrefix());
			if(p_StateObject.isGerman()){
				result.append("GESCHAEFTSAKTION{");
			}
			else{
				result.append("BUSINESS ACTION{");
			}
			result.append(p_StateObject.getType());
			result.append("}");
			if (p_StateObject.getConstraint()!=null)
				result.append(" "+p_StateObject.getConstraint().getStateSuffix());
			else
				result.append("\n");
			return result.toString();
		}
		else{
			StringBuffer result= new StringBuffer();
			Vector<String> flows= new Vector<String>();
		//	result.append(p_StateObject);
			Vector<FlowObject> flowObjects= p_StateObject.getExitFlows();
			Logger.getLogger(this.getClass()).info("not final id:" + p_StateObject.getId() + " exit flows "+p_StateObject.getExitFlows().size());
			flowObjects=priorizeFlows(flowObjects);
			for(FlowObject l_FlowObject: flowObjects){
				if(!l_FlowObject.isUsed()){
					logWarnings(l_FlowObject,p_WorkFlowObject);
					result.append(l_FlowObject);
					int indexNextState=l_FlowObject.getEndState();
					//if(indexNextState== p_StateObject.getId()){
						l_FlowObject.setUsed(true);
					//}
					StateObject[] l_StateObjects=p_WorkFlowObject.getStateObjects();
					StateObject l_StateObject= l_StateObjects[indexNextState];
					result.append(l_StateObject);
					result.append(findStatePaths(l_StateObject,p_WorkFlowObject));
					if(!result.toString().trim().equals(""))
						flows.add(result.toString());
					result= new StringBuffer();
					l_FlowObject.setUsed(false);
				}
			}
			result= getFormedBusinessRules(flows);
			return result.toString();
		}
	}
	private void logWarnings(FlowObject flowObject, WorkFlowObject p_WorkFlowObject) {
		StringBuffer message= new StringBuffer();
		if(flowObject.getEvent()==null || flowObject.getEvent().equals("")){
			//if(!flowObject.getEndStateName().trim().startsWith("Final State")){
				int iniState=flowObject.getIniState();
				int endState=flowObject.getEndState();
				StateObject[] l_StateObjects=p_WorkFlowObject.getStateObjects();
				StateObject l_IniStateObject= l_StateObjects[iniState];
				StateObject l_EndStateObject=l_StateObjects[endState];
			//	message.append(CMMessages.getString("BR_VISIOIMPORT_LOG_BETWEEN"));
			//	message.append("\n");
				if(l_IniStateObject.getAction() != null){
					message.append(l_IniStateObject.getAction());
				}
				else{
					message.append(l_IniStateObject.getType());
				}
				message.append(CMMessages.getString("BR_VISIOIMPORT_LOG_AND"));
				message.append(" ");
				if(l_EndStateObject.getAction() != null){
					message.append(l_EndStateObject.getAction());
				}
				else{
					message.append(l_EndStateObject.getType());
				}
				message.append(CMMessages.getString("BR_VISIOIMPORT_EVENT"));
			//}
			
		}
		
		if(message!= null && message.toString().trim().length() >0 )
			if(p_WorkFlowObject.getLogOfErrors().indexOf(message.toString())<0)
				p_WorkFlowObject.setLogOfErrors(p_WorkFlowObject.getLogOfErrors()+"\n"+message.toString());
	}
	private Vector<FlowObject> priorizeFlows(Vector<FlowObject> flowObjects) {
		Vector<FlowObject> result= new Vector<FlowObject>();
		for(FlowObject flow: flowObjects){
			if(flow.getIniState()== flow.getEndState()){
				result.insertElementAt(flow,0);
			}
			else{
				result.addElement(flow);
			}
		}
		return result;		
	}
	private StringBuffer getFormedBusinessRules(Vector<String> flows) {
		StringBuffer result= new StringBuffer();
		if(flows.size()<=0){
			return result;
		}
		if(flows.size()==1){
			result.append(flows.firstElement());
			if(flows.firstElement().trim().startsWith("IF"))
				result.append("ENDIF\n");
			else if(flows.firstElement().trim().startsWith("WENN"))
					result.append("WENNENDE\n");
		}
		else{
			String first=flows.firstElement();
			result.append(first);
			if(first.trim().startsWith("IF"))
				result.append("ELSE\n");
			else if(first.trim().startsWith("WENN"))
					result.append("SONST\n");
			Vector<String> l_Flows= (Vector<String>) flows.clone();
			l_Flows.remove(first);
			String flowsReturned=getFormedBusinessRules(l_Flows).toString();
			result.append(/*addTabs(*/flowsReturned)/*)*/;
			if(first.trim().startsWith("IF"))
				result.append("ENDIF\n");
			else if(first.trim().startsWith("WENN"))
				 	result.append("WENNENDE\n");
		}
		return result;
	}
	private String addTabs(String flowsReturned) {
		BufferedReader in  = new BufferedReader(new StringReader(flowsReturned));
		StringBuffer result= new StringBuffer();
		int cantOfTabs=0;
		try {
			String line=in.readLine();
			while(line != null){
				if(line.startsWith("ENDIF")||line.startsWith("WENNENDE")){
					cantOfTabs--;
					StringBuffer tabs= new StringBuffer();
					for(int i=0; i< cantOfTabs;i++){
						tabs.append("\t");
					}
					result.append(tabs);
					result.append(line+"\n");
				}
				
				else if(line.startsWith("ELSE")||line.startsWith("SONST")){
					StringBuffer tabs= new StringBuffer();
					for(int i=1; i< cantOfTabs;i++){
						tabs.append("\t");
					}
					result.append(tabs);
					result.append(line+"\n");
				}
				else if(line.startsWith("IF")||line.startsWith("WENN")){
					StringBuffer tabs= new StringBuffer();
					for(int i=0; i< cantOfTabs;i++){
						tabs.append("\t");
					}
					result.append(tabs);
					result.append(line+"\n");
					cantOfTabs++;
				}
				else{
					StringBuffer tabs= new StringBuffer();
					for(int i=0; i< cantOfTabs;i++){
						tabs.append("\t");
					}
					result.append(tabs);
					result.append(line+"\n");
				}
				line = in.readLine();
			}
		} catch (IOException e) {
			System.out.println("Error Tabs");
			return flowsReturned;
		}
		return result.toString();
	}
	private Vector<StateObject> findInitialObjects(WorkFlowObject workFlowObject) {
		Vector<StateObject> result= new Vector<StateObject>();
		int[] stateObjectindex= workFlowObject.getStateObjectsIndex();
		for (int i = 0; i < workFlowObject.getIndex(); i++) {
			int index= stateObjectindex[i];
			StateObject[] l_StateObjects=workFlowObject.getStateObjects();
			StateObject l_StateObject=l_StateObjects[index];
			if(l_StateObject.getType().equals("Initial State")){
				result.addElement(l_StateObject);
			}
		}
		return result;
	}
	public String getWarningMessages(){
		StringBuffer result= new StringBuffer();
		for(WorkFlowObject flow: workFlowObjects){
			if(flow.getLogOfErrors()!= null && flow.getLogOfErrors().length()>0){
				result.append(flow.getName());
				result.append(flow.getLogOfErrors());
				result.append("\n");
				result.append("\n");
			}
		}
		return result.toString();
	}
	public void setFlowName(FlowObject p_FlowObject, int index){
		StringBuffer idString = new StringBuffer();
	      idString.append(index);
	      int length = idString.length();
	        for(int i = 0; i < BusinessRules.ID_LENGTH+1-length; i++) {
	          idString.insert(0,BusinessRules.ID_FILLER_CHARACTER);
	        }
	      idString.insert(0,p_FlowObject.getName());
	      p_FlowObject.setName(idString.toString());		
	}
	public RelationObject createRelationObject(String startPoint, String endPoint){
		RelationObject relationObject = new RelationObject();
		relationObject.setStartPointName(deleteQuotes(startPoint));
		relationObject.setEndPointName(deleteQuotes(endPoint));
		return relationObject;
	}
	public void addRelationToCurrentDiagram(RelationObject relationObject)
	{
		getCurrentWorkFlow().addRelation(relationObject);
	}
	
	public ConstraintObject createConstraintObject(int p_id,String p_state,int p_riskLevel,String p_reqs,String p_values){
		ConstraintObject co = new ConstraintObject();
		co.setId(p_id);
		co.setState(p_state);
		co.setRiskLevel(p_riskLevel);
		co.setReqs(p_reqs);
		co.setResults(p_values);
		co.setGerman(isGerman());
		return co;
		
	}
	public void addConstraintObjectToCurrentDiagram(ConstraintObject co){
		getCurrentWorkFlow().addConstraint(co);
	}
	public void setStatesConstraint()
	{
		for (RelationObject relation : getCurrentWorkFlow().getRelations()){
			ConstraintObject co = null;
			StateObject state = null;
				if (getCurrentWorkFlow().getState(idFromDotId(relation.getEndPointName()))!=null)
					state=getCurrentWorkFlow().getState(idFromDotId(relation.getEndPointName()));
				if (getCurrentWorkFlow().getState(idFromDotId(relation.getStartPointName()))!=null)
					state=getCurrentWorkFlow().getState(idFromDotId(relation.getStartPointName()));
				if (getCurrentWorkFlow().getConstraint(idFromDotId(relation.getEndPointName()))!=null)
					co=getCurrentWorkFlow().getConstraint(idFromDotId(relation.getEndPointName()));
				if (getCurrentWorkFlow().getConstraint(idFromDotId(relation.getStartPointName()))!=null)
					co=getCurrentWorkFlow().getConstraint(idFromDotId(relation.getStartPointName()));
				
				if (state!=null&&co!=null){
					if (state.getConstraint().getId()==-1)
						state.setConstraint(co);
					else
						getCurrentWorkFlow().appendLog(CMMessages.getString("BR_VISIOIMPORT_THESTATE")+state.getAction()+
								CMMessages.getString("BR_VISIOIMPORT_MORETHANONECONSTRAINT"));
					
				}
			}
			
	}
	private int idFromDotId(String dotId){
		return Integer.parseInt(dotId.replace('.', '0'));
	}
}