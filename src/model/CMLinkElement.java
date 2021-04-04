/**
 * 23/11/2006
 * svonborries
 */
package model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;

import javax.swing.JOptionPane;

import model.util.CMModelEvent;
import model.util.CMModelListener;
import bi.controller.ProjectManager;
import bi.view.lang.CMMessages;
import bi.view.mainframeviews.CMApplication;
import bi.view.testdataviews.CMGridTDStructure;
import bi.view.testdataviews.CMIndexTDStructureUpdate;
import bi.view.utils.CMFormatFactory;

/**
 * @author svonborries
 *
 */
public class CMLinkElement implements ICMValue, CMModelListener, Cloneable {

	//private transient ITypeData typeData = null;
	private String linkKey = null;
	private transient Object value = null;
	private transient TestDataFormat testDataFormat = null;
	private transient boolean isGlobal = true;
	
	public CMLinkElement (String p_key){
		linkKey = p_key;
		try {
			//typeData = findParentTypeData(p_key);
			value = findParentTypeData(p_key);
		} catch (Exception e) {
			//variable = findParentVariable(p_key);
			value = findParentVariable(p_key);
		}
		
	}
	
	private Object findParentVariable(String p_key) {
		Object result = p_key;
		//svonborries_18072007_begin
		if(p_key.startsWith("$"))
			p_key = p_key.substring(p_key.indexOf("$") + 1, p_key.length());
		//svonborries_18072007_end
        Project2 actualProject = ProjectManager.getSelectedProject();
        //svonborries_06012006_begin
        TDStructure l_Structure = CMApplication.frame.getGridTDStructure().getTDStructure();
        if (getVariablesNames(l_Structure.getM_Variables()).contains(p_key)){
            int index = getVariablesNames(l_Structure.getM_Variables()).indexOf(p_key);
            Variable value=((Variable)l_Structure.getM_Variables().getVariables().elementAt(index));
            return value;
        }
        else {
            String nom_Variables_Case = p_key.toUpperCase();
            CMGridTDStructure cmgrid = CMApplication.frame.getGridTDStructure();
            if(CMApplication.frame.isIsPanelTestDataSelected())
            	cmgrid = CMApplication.frame.getPanelTestDataView().getM_CMGridTDStructure();
            if (Arrays.binarySearch(BusinessRules.TESTDATA_IMPICIT_VARIABLES, nom_Variables_Case) >= 0) {
                int indexImplicitVariables = Arrays.binarySearch(BusinessRules.TESTDATA_IMPICIT_VARIABLES,
                    nom_Variables_Case);
                if (indexImplicitVariables == 0) {
                    result = actualProject;
                }
                else {
                    if (indexImplicitVariables == 1) {
						result = ((StructureTestData)cmgrid.getTDStructure().getM_StructureTestData().elementAt(CMIndexTDStructureUpdate.getInstance().getIndex()));
                    }
                    else {
                        if (indexImplicitVariables == 2) {
                            if (CMApplication.frame.isIsPanelTestDataSelected()) {
                                result = ((TestData)cmgrid.getTDStructure().getTestDataCombination().getM_TestDatas().
                                		elementAt(CMIndexTDStructureUpdate.getInstance().getindexTestData()));
                            }
                            else {
                                JOptionPane.showMessageDialog(CMApplication.frame, CMMessages.getString("FORMULAS_ERROR_NO_ALLOWED_VARIABLE"),
                                    CMMessages.getString("TESTDATA_TITLE_MENSSAGE_ERROR"), JOptionPane.ERROR_MESSAGE);

                            }
                        }
                        else {
                            if (indexImplicitVariables == 3) {
                                result = CMApplication.frame.getGridTDStructure().getTDStructure().getM_TestObject();
                            }
                            else {
                                result = CMApplication.frame.getTreeWorkspaceView().getCurrentProjectReference().getM_Workspace();
                            }
                        }
                    }
                }
            }
        }
        return result;
	}
	
    public String convertVariableToStandarFormat(String p_VariableValue, TestDataFormat p_FormatVariable){
    	TestDataFormat newDefaultFormat=new TestDataFormat();
    	newDefaultFormat.setRealFormat(p_FormatVariable.getRealFormat());
    	newDefaultFormat.setOriginalFormatter(p_FormatVariable.getOriginalFormatter());
    	return CMFormatFactory.applyAnyFormat(newDefaultFormat,p_VariableValue,p_FormatVariable);
    }
	
	private Vector getVariablesNames(Variables p_Variables){
    	Vector<String> result= new Vector<String>();
    	for(int i =0; i< p_Variables.getVariables().size();i++){
    		result.addElement(((Variable)p_Variables.getVariables().elementAt(i)).getM_Name());
    	}
    	return result;
    }

	/* (non-Javadoc)
	 * @see model.ICMValue#getValue()
	 * @return
	 * @throws Exception
	 * 23/11/2006
	 * svonborries
	 */
	public Object getValue() throws Exception {
		value = getObjectLinked();
		if(value!=null){
			if(value instanceof ITypeData){
				//if(!existCircularReference(null)){
					return ((ITypeData)getObjectLinked()).getValue().getValue();
				//}
			}
			if(value instanceof Variable){
				return ((Variable)value).getM_Value().getValue();
			}
			if(value instanceof Project2){
				return ((Project2)value).getName();
			}
			if(value instanceof StructureTestData){
				return ((StructureTestData)value).getName();
			}
			if(value instanceof TestData)
				return ((TestData)value).getName();
			if(value instanceof TestObject)
				return ((TestObject)value).getName();
			if(value instanceof Workspace2)
				return  ((Workspace2)value).getName();
		}
		return null;
	}

	public void handleCMModelChange(CMModelEvent evt) {
		// TODO Auto-generated method stub
		
	}
	
	public Object getObjectLinked(){
		if(value == null){
			try {
				value = findParentTypeData(getLinkKey());
				return value;
			} catch (Exception e) {
				value = findParentVariable(getLinkKey());
				return value;
			}
		}
		return value;
	}

//	public ITypeData getTypeData() {
//		if(value == null)
//			value =  findParentTypeData(getLinkKey());
//		return (ITypeData) value;
//	}
	
	public void setObjectLinked(Object value){
		this.value = value;
	}

//	public void setTypeData(ITypeData typeData) {
//		this.value = typeData;
//	}

	public String getLinkKey() {
		return linkKey;
	}
	
	public boolean existCircularReference(List<String> p_list){
		if(p_list == null){
			ICMValue link = ((ITypeData)findParentTypeData(this.getLinkKey())).getValue();
			try {
				CMGridTDStructure grid;
				if(CMApplication.frame.isIsPanelTestDataSelected())
					grid = CMApplication.frame.getPanelTestDataView().getM_CMGridTDStructure();
				else
					grid = CMApplication.frame.getGridTDStructure();
				if(grid.getSelectedTypeData().equals(getObjectLinked()))
					return true;
			} catch (Exception e1) {
			}
			if(link instanceof CMLinkElement){
				try {
					if(((CMLinkElement)link).getObjectLinked() instanceof ITypeData){
						p_list = new ArrayList<String>();
						p_list.add(this.getLinkKey());
						return ((CMLinkElement)link).existCircularReference(p_list);
					}
				} catch (Exception e) {
				}
			}
		}
		else{
			for(String linkElement: p_list){
				if(getLinkKey().equalsIgnoreCase(linkElement)){
					return true;
				}
			}
			//else{
				ICMValue link = ((ITypeData)findParentTypeData(this.getLinkKey())).getValue();
				if(link instanceof CMLinkElement){
					p_list.add(((CMLinkElement)this).getLinkKey());
					return ((CMLinkElement)link).existCircularReference(p_list);
				}
			//}
//					
//				ICMValue link = ((ITypeData)findParentTypeData(linkElement)).getValue();
//				if(link instanceof CMLinkElement){
//					
//					p_list.add(((CMLinkElement)link).getLinkKey());
//					return existCircularReference(p_list);
//				}
//			if(linkElement.getLinkKey().equalsIgnoreCase(this.getLinkKey())){
//				
//			}
		}
		
		return false;
	}
	
	
	private ITypeData findParentTypeData(String p_key){
		TDStructure structure;
		//crear un check pa ver por ande empezamos
		if(startInStructure(p_key)){
			structure = CMApplication.frame.getGridTDStructure().getTDStructure();
			return cutStringUntilStructureITypeData(p_key, structure);
		}
		else{
			structure = CMApplication.frame.getPanelTestDataView().getM_CMGridTDStructure().getTDStructure();
			return cutStringUntilTestDataITypeData(p_key, structure);
		}
		
	}

	private boolean startInStructure(String p_key) {
		if(p_key.startsWith("$")){
			p_key = p_key.substring(1);
		}
		if(p_key.startsWith("T"))
			return false;
		else if(p_key.startsWith("S"))
			return true;
		return false;
	}
	
	private ITypeData cutStringUntilTestDataITypeData(String p_key, TDStructure p_structure) {
		if(p_key.startsWith("$")){
			p_key = p_key.substring(1);
		}
		//find TestData
		TestData td = null;
		if((p_key.contains("."))){
			int pointLocation = p_key.indexOf(".");
			String testData = p_key.substring(0, pointLocation);
			p_key = p_key.substring(pointLocation);
			p_key = p_key.substring(1);
			for(Object teData: p_structure.getTestDataCombination().getM_TestDatas()){
				if(((TestData)teData).getName().equalsIgnoreCase(testData))
					td =  ((TestData)teData);
			}
		}
		
		//Find StructureTestData
		StructureTestData structureTD = null;
		if((p_key.contains("."))){
			int pointLocation = p_key.indexOf(".");
			String std = p_key.substring(0, pointLocation);
			//svonborries_28062007_begin
			int idTestData = 0;
			if(hasBrackets(std)){
				idTestData = getIdTestData(std);
				std = std.substring(0, std.indexOf("["));
			}
			p_key = p_key.substring(pointLocation);
			p_key = p_key.substring(1);
			if(idTestData == 0){
				for(Object structure: td.getM_TDStructure().getM_StructureTestData()){
					if(((StructureTestData)structure).getName().equalsIgnoreCase(std)){
						 structureTD = ((StructureTestData)structure);
					}
				}
			}
			else{
				for(Object structure: td.getM_TDStructure().getM_StructureTestData()){
					if(((StructureTestData)structure).getName().equalsIgnoreCase(std) && ((StructureTestData)structure).getIdTestData() == idTestData){
						 structureTD = ((StructureTestData)structure);
					}
				}
			}
			//svonborries_28062007_end
		}
		
		//find the ITypeData
			String typeData = p_key;
			for(Object tyData: structureTD.getTypeData()){
				if(((ITypeData)tyData).getField().equalsIgnoreCase(typeData)){
					setTestDataFormat(((ITypeData)tyData).getFormatter());
					return ((ITypeData)tyData);
				}
					
			}
		return null;
	}
//svonborries_28062007_begin
	/**
	 * @author svonborries
	 * @param std
	 * @return
	 */
	private boolean hasBrackets(String std) {
		if((std.indexOf("[") != -1) && (std.indexOf("]") != -1))
			return true;
		return false;
	}

	/**
	 * @author svonborries
	 * @param std
	 * @return
	 */
	private int getIdTestData(String std) {
		String indexStr = std.substring(std.indexOf("[")+1, std.indexOf("]"));
		return Integer.parseInt(indexStr);
	}
//svonborries_28062007_end
	private ITypeData cutStringUntilStructureITypeData(String p_key, TDStructure p_structure) {
		if(p_key.startsWith("$")){
			p_key = p_key.substring(1);
		}
		//Find StructureTestData
		StructureTestData structureTD = null;
		if((p_key.contains("."))){
			int pointLocation = p_key.indexOf(".");
			String std = p_key.substring(0, pointLocation);
			p_key = p_key.substring(pointLocation);
			p_key = p_key.substring(1);
			for(Object structure: p_structure.getM_StructureTestData()){
				if(((StructureTestData)structure).getName().equalsIgnoreCase(std)){
					 structureTD = ((StructureTestData)structure);
				}
				
			}
		}
//		find the ITypeData
			String typeData = p_key;
			for(Object tyData: structureTD.getTypeData()){
				if(((ITypeData)tyData).getField().equalsIgnoreCase(typeData)){
					setTestDataFormat(((ITypeData)tyData).getFormatter());
					return ((ITypeData)tyData);
				}
			}
		return null;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#clone()
	 * @return
	 * 08/12/2006
	 * svonborries
	 */
	@Override
	public Object clone() {
		Object b = null;
		CMLinkElement clon = null;
		try {
			b = super.clone();
			CMLinkElement aux = (CMLinkElement) b;
			clon = new CMLinkElement(aux.getLinkKey());
			clon.setObjectLinked(aux.getObjectLinked());
			boolean global = aux.isGlobal();
			clon.setGlobal(global);
			if(aux.getTestDataFormat() != null){
				TestDataFormat tdFormat = new TestDataFormat();
				tdFormat.setOriginalFormatter(aux.getTestDataFormat().getOriginalFormatter());
				tdFormat.setRealFormat(aux.getTestDataFormat().getRealFormat());
				tdFormat.setVisualFormatter(aux.getTestDataFormat().getVisualFormatter());
				clon.setTestDataFormat(tdFormat);
			}
		} catch (CloneNotSupportedException e) {
		}
		return clon;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 * @return
	 * 07/12/2006
	 * svonborries
	 */
	@Override
	public String toString() {
		return getLinkKey();
	}

	/**
	 * @return the testDataFormat
	 * 17/05/2007
	 * svonborries
	 */
	public TestDataFormat getTestDataFormat() {
		return testDataFormat;
	}

	/**
	 * @param testDataFormat the testDataFormat to set
	 * 17/05/2007
	 * svonborries
	 */
	public void setTestDataFormat(TestDataFormat testDataFormat) {
		this.testDataFormat = testDataFormat;
	}

	/**
	 * @return the isGlobal
	 * 22/05/2007
	 * svonborries
	 */
	public boolean isGlobal() {
		return isGlobal;
	}

	/**
	 * @param isGlobal the isGlobal to set
	 * 22/05/2007
	 * svonborries
	 */
	public void setGlobal(boolean isGlobal) {
		this.isGlobal = isGlobal;
	}

	/**
	 * @param linkKey the linkKey to set
	 * 23/05/2007
	 * svonborries
	 */
	public void setLinkKey(String linkKey) {
		this.linkKey = linkKey;
	}

	/**
	 * @return the variable
	 * 13/12/2006
	 * svonborries
	 */
//	public Object getVariable() {
//		if(value == null)
//			value = findParentVariable(getLinkKey());
//		return value;
//	}

	/**
	 * @param variable the variable to set
	 * 13/12/2006
	 * svonborries
	 */
//	public void setVariable(Object variable) {
//		this.value = variable;
//	}

}
