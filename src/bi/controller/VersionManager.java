package bi.controller;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Vector;

import javax.swing.JOptionPane;

import model.BRulesReference;
import model.BusinessRules;
import model.Combination;
import model.DelegateObservable;
import model.Dependency;
import model.Effect;
import model.Element;
import model.ExpectedResult;
import model.ITypeData;
import model.LocalProjectReference;
import model.Project2;
import model.ProjectReference;
import model.Session2;
import model.State;
import model.StructureTestData;
import model.TDStructure;
import model.TDStructureReference;
import model.Technology;
import model.TestCase;
import model.TestData;
import model.TestDataCombinations;
import model.TestDataFormat;
import model.TestObject;
import model.TestObjectReference;
import model.ToolVendor;
import model.TypeData;
import model.TypeDataGlobal;
import model.TypeDataLocal;
import model.Variable;
import model.Variables;
import model.Workspace2;

import org.apache.log4j.Logger;

import bi.controller.utils.CMFileValidator;
import bi.view.lang.CMMessages;
import bi.view.mainframeviews.CMApplication;


public class VersionManager {
    public VersionManager() {
    }

    public boolean updateTestDataBasedOnVersion(TDStructure p_TDStructure, TestObject p_TestObject){

    	if( p_TDStructure.getVersion() == null) {
    		fixTestDataToolVendorOTAndColumns(p_TDStructure,p_TestObject);
    		correctIdStructureInTestDataOldVersion(p_TDStructure);
    		fixTypeDataToReduceFileSpace(p_TDStructure,p_TestObject);
    		fixFormulas(p_TDStructure);
    		p_TDStructure.setM_Version(BusinessRules.TESTDATA_FILE_VERSION);
    		return true;
    	}
    	else {
            if(!(p_TDStructure.getVersion().equals(BusinessRules.TESTDATA_FILE_VERSION))){

            	fixTestDataToolVendorOTAndColumns(p_TDStructure,p_TestObject);
            	correctIdStructureInTestDataOldVersion(p_TDStructure);
            	fixTypeDataToReduceFileSpace(p_TDStructure,p_TestObject);
            	fixFormulas(p_TDStructure);
            	fixTestDataToolVendorOTStates(p_TDStructure, p_TestObject);
            	p_TDStructure.setM_Version(BusinessRules.TESTDATA_FILE_VERSION);
            	return true;
            }
    	}
    	return false;



    }

	/**
	 * @param structure
	 * 29/08/2006
	 * svonborries
	 */
	private void fixFormulasInTypeData(TDStructure structure) {
		for (int i = 0; i < structure.getM_StructureTestData().size(); i++) {
			StructureTestData std = (StructureTestData) structure.getM_StructureTestData().elementAt(i);
			for(int l = 0; l< std.getTypeData().size(); l++){
				ITypeData type = (ITypeData) std.getTypeData().elementAt(l);
/*				String formula = type.getStringFormula();
				if(!formula.equalsIgnoreCase("")){
					String newFormula = replaceFormulaForNewEnhancement(type.getStringFormula());
					type.setFormula(newFormula);
				}*/
			}
		}
	}

	/**
	 * @param formula
	 * 30/08/2006
	 * svonborries
	 */
	private String replaceFormulaForNewEnhancement(String formula) {
		String newFormula = formula.trim();
		boolean sw = false;
		if(newFormula.contains("DateSum")){
			while (sw == false) {
				newFormula = newFormula.replace("DateSum", "AddDay");
				if(!newFormula.contains("DateSum")){
					sw = true;
				}
			}
		}
		sw = false;
		if(newFormula.contains("Remplace")){
			while (sw == false) {
				newFormula = newFormula.replace("Remplace", "Replace");
				if(!newFormula.contains("Remplace")){
					sw = true;
				}
			}

		}
		return newFormula;
	}

	/**
	 * @param structure
	 * 30/08/2006
	 * svonborries
	 */
	private void fixFormulas(TDStructure structure){

		try {

			if(structure != null){
				//for globals typedatas
				fixFormulasInTypeData(structure);
				//for locals typedatas
				TestDataCombinations tdc = structure.getTestDataCombination();
				if(tdc != null){
					for(int i = 0; i < tdc.getM_TestDatas().size(); i++){
						TestData td = (TestData) tdc.getM_TestDatas().elementAt(i);
						fixFormulasInTypeData(td.getM_TDStructure());
					}
				}
			}

		} catch (Exception e) {

		}

	}

	private void fixTypeDataToReduceFileSpace(TDStructure structure, TestObject p_TestObject) {
    	try{
		for (Iterator iter = structure.getM_StructureTestData().iterator(); iter.hasNext();) {
			StructureTestData structureTestData = (StructureTestData) iter.next();
			Vector newTypeDatas= new Vector();
			for (Iterator iterator = structureTestData.getTypeData().iterator(); iterator.hasNext();) {
				TypeData typeData = (TypeData) iterator.next();
				ITypeData structureTypeData= new TypeDataGlobal();
				structureTypeData.setField(typeData.getField());
				structureTypeData.setFormat(typeData.getFormat());
				structureTypeData.setFormula(typeData.getFormula());
				structureTypeData.setGlobal(typeData.getGlobal());
				structureTypeData.setisFormula(typeData.isFormula());
				structureTypeData.setKey(typeData.getKey());
				structureTypeData.setLength(typeData.getLength());
				structureTypeData.setLinkValue(typeData.isLinkValue());
				structureTypeData.setFormatter(typeData.getM_Formatter());
				structureTypeData.setM_Subjects(null);
				structureTypeData.setName(typeData.getName());
				structureTypeData.setNewColumn(typeData.getNewColumn());
				structureTypeData.setNewColumns(typeData.getNewColumns());
				structureTypeData.setPrefix(typeData.getPrefix());
				structureTypeData.setStructureTestData(typeData.getStructureTestData());
				structureTypeData.setSuffix(typeData.getSuffix());
				// ccastedo 27.09.06  structureTypeData.setToolVendorOT(typeData.getToolVendorOT());
				////////////////

				String objectType=typeData.getToolVendorOT();
				int index = getStateObjectTypesFromTestDataToFix(objectType,null,p_TestObject);
				structureTypeData.setStateOT(index);

				////////////////
				//structureTypeData.setStateOT(typeData.getStateOT());
				structureTypeData.setType(typeData.getType());
				structureTypeData.setStringValue(typeData.getValue());
				newTypeDatas.add(structureTypeData);
			}
			structureTestData.setTypeData(newTypeDatas);
			System.gc();
		}

		for (Iterator iter = structure.getTestDataCombination().getM_TestDatas().iterator(); iter.hasNext();) {
			TestData testData = (TestData) iter.next();
			for (Iterator iterator = testData.getM_TDStructure().getM_StructureTestData().iterator(); iterator.hasNext();) {
				StructureTestData structureTestData = (StructureTestData) iterator.next();
				Vector newTypedatas=new Vector();
				for (Iterator iter3 = structureTestData.getTypeData().iterator(); iter3
						.hasNext();) {
					TypeData typeData = (TypeData) iter3.next();
					ITypeData localTypeData= new TypeDataLocal();
					localTypeData.setFormula(typeData.getFormula());
					localTypeData.setGlobal(typeData.getGlobal());
					localTypeData.setisFormula(typeData.isFormula());
					localTypeData.setLinkValue(typeData.isLinkValue());
					localTypeData.setM_Subjects(null);
					localTypeData.setStructureTestData(typeData.getStructureTestData());
					localTypeData.setStringValue(typeData.getValue());
					newTypedatas.addElement(localTypeData);
				}
				structureTestData.setTypeData(newTypedatas);
				System.gc();
				int idGlobalStructure= structureTestData.getGlobalIndex();
				int idStructure=-1;
				int i =0;
				StructureTestData structureGlobal=null;
				while((idGlobalStructure != idStructure) && (i<structure.getM_StructureTestData().size())){
					structureGlobal=(StructureTestData) structure.getM_StructureTestData().elementAt(i);
					idStructure=structureGlobal.getGlobalIndex();
					i++;
				}
				if(idStructure >=0){
					setTypeDataReferences(structureTestData,structureGlobal);
				}
			}
		}
    	}
    	catch (Exception e) {
    		Logger.getLogger(this.getClass()).error("TypeData corrupted");
		}

	}

    private void setTypeDataReferences(StructureTestData stdPopulated, StructureTestData createStructure) {
		for(int i=0; i < stdPopulated.getTypeData().size();i++){
			ITypeData TypeDataRefered= (ITypeData) createStructure.getTypeData().elementAt(i);
			ITypeData toRefer=(ITypeData) stdPopulated.getTypeData().elementAt(i);
			((TypeDataLocal)toRefer).setM_ReferenceTypeData(TypeDataRefered);
		}

	}
	public void fixTestDataToolVendorOTAndColumns(TDStructure p_TDStructure, TestObject p_TestObject){
    	try{
    	ToolVendorManager toolvendormanager = ToolVendorManager.INSTANCE;

		String toolVendor = p_TestObject.getToolVendor();
		String technology = p_TestObject.getToolVendorTechnology();

		Technology m_Technology = toolvendormanager.findTechnologyByName(toolVendor,technology);

    	for (Iterator iter = p_TDStructure.getM_StructureTestData().iterator(); iter.hasNext();) {
			StructureTestData structure = (StructureTestData) iter.next();
			for (Iterator iterator = structure.getTypeData().iterator(); iterator.hasNext();) {
				TypeData typedata = (TypeData) iterator.next();

				if (typedata.getToolVendorOT()== null){
					String l_ObjectTypes = m_Technology.getM_ObjectTypesValue().get(0).toString();
					typedata.setToolVendorOT(l_ObjectTypes);
				}

				if (typedata.getNewColumns()==null){
					Vector newColumns=new Vector(5);
					typedata.setNewColumns(newColumns);
				}
				if(typedata.getStructureTestData()==null){
					typedata.setStructureTestData(structure);
				}
			}

		}
    	for (Iterator itera = p_TDStructure.getTestDataCombination().getM_TestDatas().iterator(); itera.hasNext();) {
			TestData testdata = (TestData) itera.next();
			for (Iterator iter = testdata.getM_TDStructure().getM_StructureTestData().iterator(); iter.hasNext();) {
				StructureTestData structure = (StructureTestData) iter.next();
				for (Iterator iterator = structure.getTypeData().iterator(); iterator.hasNext();) {
					TypeData typedata = (TypeData) iterator.next();
					if (typedata.getToolVendorOT()== null){
						String l_ObjectTypes = m_Technology.getM_ObjectTypesValue().get(0).toString();
						typedata.setToolVendorOT(l_ObjectTypes);
					}
					if (typedata.getNewColumns()==null){
						Vector newColumns=new Vector(5);
						typedata.setNewColumns(newColumns);
					}
			}
		}
    	}
    	}
    	catch (Exception e) {
    		Logger.getLogger(this.getClass()).error("File version incorrect");
		}
    }

	//ccastedo begins 29.09.06
	public void fixTestDataToolVendorOTStates(TDStructure p_TDStructure, TestObject p_TestObject){
    	try{
    	ToolVendorManager toolvendormanager = ToolVendorManager.INSTANCE;

		String toolVendor = p_TestObject.getToolVendor();
		String technology = p_TestObject.getToolVendorTechnology();

		Technology m_Technology = toolvendormanager.findTechnologyByName(toolVendor,technology);

    	for (Iterator iter = p_TDStructure.getM_StructureTestData().iterator(); iter.hasNext();) {
			StructureTestData structure = (StructureTestData) iter.next();
			for (Iterator iterator = structure.getTypeData().iterator(); iterator.hasNext();) {
				ITypeData typedata = (ITypeData) iterator.next();
				String objectType=typedata.getToolVendorOT(p_TestObject);
				int state = getStateObjectTypesFromTestDataToFix(objectType,m_Technology,p_TestObject);
				typedata.setStateOT(state);

			}

		}

    	}
    	catch (Exception e) {
    		Logger.getLogger(this.getClass()).error("Error StateOT");
		}
    }
	private int getStateObjectTypesFromTestDataToFix(String objectType,Technology tech, TestObject p_TestObject){
		if (tech==null){

			ToolVendorManager toolvendormanager = ToolVendorManager.INSTANCE;

			String toolVendor = p_TestObject.getToolVendor();
			String technology = p_TestObject.getToolVendorTechnology();

			tech = toolvendormanager.findTechnologyByName(toolVendor,technology);

		}
				int index=0;
		for (Iterator i = tech.getM_ObjectTypesValue().iterator();i.hasNext();){
			if (tech.getM_ObjectTypesValue().get(index).toString().equalsIgnoreCase(objectType))
				break;
			index++;
		}
		return index;

	}
	//ccastedo ends 29.09.06

    public boolean updateProjectBasedOnTheVersion(Project2 p_Project, String p_FilePath){//svonborries_09012006
      if( p_Project.getVersion() == null) {
        fixProjectForVersion(p_Project);
        fixFileSeparatorInTestObjectReferencesInProject2(p_Project);
       // fixVariablesFormatChange(p_Project);
        fixVariableObjects(p_Project);
        fixVariableTypeDataReference(p_Project);
        p_Project.setM_Version(BusinessRules.PROJECT_FILE_VERSION);
        return true;
      }
      else {
        if(!(p_Project.getVersion().equals(BusinessRules.PROJECT_FILE_VERSION))){
            fixFileSeparatorInTestObjectReferencesInProject2(p_Project);
          //  fixVariablesFormatChange(p_Project);
            fixVariableObjects(p_Project);
            fixVariableTypeDataReference(p_Project);
            changeVariablesFromProjectToTDStructure(p_Project,p_FilePath);//svonborries_06012006
            p_Project.setM_Version(BusinessRules.PROJECT_FILE_VERSION);
        	return true;
        }
        return false;
      }
    }

    private void fixVariableTypeDataReference(Project2 project) {
		Variables variables=project.getM_Variables();
		for (Iterator iter = variables.getVariables().iterator(); iter.hasNext();) {
			Variable variable = (Variable) iter.next();
			Vector observers=variable.getM_Observers().getObservers();
			try {
				if(observers.firstElement() instanceof TypeData){
					variable.setM_Observers(new DelegateObservable());
				}
			} catch (NoSuchElementException e) {
				// nothing to do
			}
		}

	}

	private void fixVariableObjects(Project2 project){
    	try {
			Variables p_Variables=project.getM_Variables();
			if(p_Variables.getVariables() == null){//svonborries_10022006
				p_Variables.setVariables(new Vector());
			if(p_Variables.getNames() != null){
				for(int i=0; i < p_Variables.getNames().size(); i++)
				{
					Variable variable= new Variable();
					variable.setM_Name(p_Variables.getNames().elementAt(i).toString());
					variable.setM_Description(p_Variables.getDescription().elementAt(i).toString());
					variable.setFormat(p_Variables.getFormat().elementAt(i).toString());
					variable.setM_Type(p_Variables.getType().elementAt(i).toString());
					variable.setM_StringValue(p_Variables.getValue().elementAt(i).toString());
					if((p_Variables.getFormatter() != null) && (i < p_Variables.getFormatter().size())){
						variable.setFormatter((TestDataFormat) p_Variables.getFormatter().elementAt(i));
					}
					else{
						variable.setFormat(BusinessRules.FORMULAS_FORMAT_STRING);
						variable.setFormatter(new TestDataFormat());
					}
					p_Variables.getVariables().addElement(variable);
				}
			/*	p_Variables.setDescription(null);
				p_Variables.setFormat(null);
				p_Variables.setFormatter(null);
				p_Variables.setNames(null);
				p_Variables.setType(null);
				p_Variables.setValue(null);*/
			}
    	}//svonborries_10022006
		}
    	catch (Exception e) {
    		Logger.getLogger(this.getClass()).error("Variables wasn?t needed the actualization");
		}
    }

/*
     private void fixVariablesFormatChange(Project2 project) {
		Variables p_Variables=project.getM_Variables();
		if(p_Variables.getFormatter() == null){
			p_Variables.setFormatter(new Vector());
			for(int i=0; i< p_Variables.getFormat().size();i++){
				p_Variables.getFormat().setElementAt(BusinessRules.FORMULAS_FORMAT_STRING,i);
				p_Variables.getFormatter().addElement(new TestDataFormat());
			}
		}
		else{
			if(project.getM_Version()==null ||project.getM_Version().equals("1.2"))
			p_Variables.setFormatter(new Vector());
			for(int i=0; i< p_Variables.getFormat().size();i++){
				p_Variables.getFormat().setElementAt(BusinessRules.FORMULAS_FORMAT_STRING,i);
				p_Variables.getFormatter().addElement(new TestDataFormat());
			}
		}


	}
*/
	public boolean updateSessionBasedOnTheVersion(Session2 p_Session2){
      if( p_Session2.getVersion() == null || !(p_Session2.getVersion().equals(BusinessRules.SESSION_FILE_VERSION)))
      {
        fixSessionForVersion(p_Session2);
        fixFileSeparatorInProjectReferecesInSession(p_Session2);
        fixTestCaseReportsInSession(p_Session2);
        fixToolVendorInSession(p_Session2);
        fixAbsolutePathInToolVendor(p_Session2);//svonborries_08022006
		p_Session2.setM_Version(BusinessRules.SESSION_FILE_VERSION);
        return true;
      }
      else {
        return false;
      }
    }
    /**
	 * @param session2
	 */
	private void fixTestCaseReportsInSession(Session2 session2) {
		/*ExternalXSLTReportFormat x = session2.getM_ApplicationSetting().getM_ExternalXSLTReportFormatOld();
		if (x == null);*/
			session2.getM_ApplicationSetting().createDefaultReports();
	}

	private void fixToolVendorInSession(Session2 session2) {
		if((session2.getM_ApplicationSetting().getM_ToolVendors() == null) ){
			session2.getM_ApplicationSetting().setM_ToolVendors(new Vector());
			ToolVendor compuware = new ToolVendor();

		   	 compuware.setM_Name(BusinessRules.COMPUWARE);
		   	 compuware.setM_FilePath(BusinessRules.PATH_TOOLVENDORS);//svonborries_11012006

			session2.getM_ApplicationSetting().getM_ToolVendors().addElement(compuware);

		}
	}
	public boolean updateTestObjectBasedOnTheVersion(TestObject p_TestObject){
      if( p_TestObject.getVersion() == null) {
        fixTestObjectForVersion(p_TestObject);
        fixTestObjectAccessStateAndUser(p_TestObject);
        fixFileSeparatorIntoTestObject(p_TestObject);
        fixTestObjectStateToolVendor(p_TestObject);
        fixExpectedResultsfromStrings(p_TestObject);
        p_TestObject.setVersion(BusinessRules.TESTOBJECT_FILE_VERSION);
        return true;
      }
      else {
        if(!p_TestObject.getVersion().equals(BusinessRules.TESTOBJECT_FILE_VERSION))
        {
            fixTestObjectAccessStateAndUser(p_TestObject);
            //My add
            fixTestObjectStateToolVendor(p_TestObject);

            fixFileSeparatorIntoTestObject(p_TestObject);
            //put the relation between the element and the structure
            fixElementParent(p_TestObject);
            //put the relation between the dependency and the structutre
            fixDependencyParent(p_TestObject);
            //put the relation between the effect and the structure
            fixEffectParent(p_TestObject);
            //check for duplicate element names
            fixDuplicateElementNames(p_TestObject);
            //migrateOldUserDescriptions(p_TestObject);
            fixExpectedResultsfromStrings(p_TestObject);
            //fix effect states
            fixEffectStates(p_TestObject);

            p_TestObject.setVersion(BusinessRules.TESTOBJECT_FILE_VERSION);
            return true;
        }
        else{
        	return false;
        }
      }
    }


	private void fixEffectStates(TestObject testObject) {
		for (Effect effect : testObject.getStructure().getEffects() )
			effect.setState(State.POSITIVE.intValue());

	}

	/**
	 * @param p_testObject
	 */
	private void fixEffectParent(TestObject p_testObject) {
		for (Effect effect : p_testObject.getStructure().getEffects())
		 if (effect.getLnkStructure()==null)
			 effect.setLnkStructure(p_testObject.getStructure());
	}

	private void migrateOldUserDescriptions(TestObject p_testObject)
    {
        Vector l_dependencies = p_testObject.getStructure().getLnkDependencies();
        for (int i=0;i<l_dependencies.size();i++)
        {
        	Dependency l_dependency = (Dependency)l_dependencies.get(i);
        	l_dependency.setDescriptionEditable(l_dependency.getDescriptionWithOutCheck());
        	Vector l_combinations = l_dependency.getLnkCombinations();
            for (int j=0;j<l_combinations.size();j++)
            {
            	Combination l_combination = (Combination)l_combinations.get(j);
            	l_combination.setDescriptionEditable(l_combination.getDescriptionWithOutCheck());
            }
        }
        Vector l_testCases = p_testObject.getStructure().getLnkTestCases();
        for (int i=0;i<l_testCases.size();i++)
        {
        	TestCase l_testCase = (TestCase)l_testCases.get(i);
        	l_testCase.setDescriptionEditable(l_testCase.getDescriptionWithOutCheck());
        }
    }
    /**
     * @author smoreno
     * Search the test object for null references from the dependency to the structure and fix it
     *  @param testObject
     */
    private void fixDependencyParent(TestObject testObject) {
//    	 find all dependencies for the related testobject structure
    	// that does not have a parent structure
    	for (Iterator i = testObject.getStructure().getLnkDependencies().iterator();i.hasNext();)
    	{
    		Dependency dependency = (Dependency) i.next();
    		if (dependency.getLnkStructure() == null)
    			dependency.setLnkStructure(testObject.getStructure());
    	}

	}



	/**
     * @author smoreno
     *  search the test object for duplicate names if it finds one put a suffix counter to the name
     *  if the name is empty put the default prefix
     * @param testObject
     * the test object to search in the structure
     */
    private void fixDuplicateElementNames(TestObject testObject) {
		// search the structure for duplicate element names
    	for (Iterator i = testObject.getStructure().getLnkElements().iterator();i.hasNext();)
    	{
    		Element originalElement = (Element) i.next();
    		for (Iterator j = testObject.getStructure().getLnkElements().iterator();j.hasNext();)
    		{

    			Element duplicateCandidateElement = (Element) j.next();
    			//if the elements arent the same
    			if (duplicateCandidateElement!=originalElement)
    			//if finds another element that has the same name starting from the begining
    			if (originalElement.getName().equalsIgnoreCase(duplicateCandidateElement.getName()))
    			{
    				//generate a new name for the element based on the old name
    				duplicateCandidateElement.setName(
    						ElementManager.INSTANCE.generateNewElementName(duplicateCandidateElement,duplicateCandidateElement.getLnkStructure()));

    			}
    		}
    	}



	}

	private void fixElementParent(TestObject testObject) {
		// find all elements for the related testobject structure
    	// that does not have a parent structure
    	for (Iterator i = testObject.getStructure().getLnkElements().iterator();i.hasNext();)
    	{
    		Element element = (Element) i.next();
    		if (element.getLnkStructure() == null)
    			element.setLnkStructure(testObject.getStructure());
    	}

	}

	

    
	private void fixProjectForVersion(Project2 p_Project) {
      int numOfTestObjectReferences = p_Project.getTestObjectReferences().size();
      TestObjectReference testObjectReference = null;
      for( int i = 0; i < numOfTestObjectReferences; i++) {
        testObjectReference = (TestObjectReference) p_Project.getTestObjectReferences().elementAt(i);
        fixTestObjectReference(testObjectReference);
      }
    }

    private void fixTestObjectForVersion(TestObject p_TestObject) {
      BRulesReference bRulesReference = p_TestObject.getBRulesReference();
      fixBRulesReference(bRulesReference);

      Vector tDStructureReferenceVector = p_TestObject.getTDSTructureReference();
      if( tDStructureReferenceVector != null) {
        if( tDStructureReferenceVector.firstElement() != null) {
          fixTDStructureReference((TDStructureReference)tDStructureReferenceVector.firstElement());
        }
      }
    }

    private void fixTestObjectReference(TestObjectReference p_TestObjectReference) {
      String oldName = p_TestObjectReference.getName();
     //    p_TestObjectReference.setM_Name(oldName);//Ccastedo 21-04-06
      p_TestObjectReference.setFileName(oldName); //Ccastedo 21-04-06
    }

    private void fixBRulesReference(BRulesReference p_BRulesReference) {
      if( p_BRulesReference != null) {
        p_BRulesReference.update();
      }
    }

    private void fixTDStructureReference(TDStructureReference p_TDStructureReference){
      if( p_TDStructureReference != null) {
        p_TDStructureReference.update();
      }
    }
    private void fixTestObjectAccessStateAndUser(TestObject p_TestObject)
    {
        if(p_TestObject.getAccessState()== null)
        {
            p_TestObject.setAccessState(BusinessRules.ACCESS_STATE_CHECKED_IN);
        }
        if(p_TestObject.getUser()== null)
        {
            p_TestObject.setUser(System.getProperty("user.name"));
        }
    }

    //My adds
    private void fixTestObjectStateToolVendor(TestObject p_TestObject)
    {
        if(p_TestObject.getToolVendor()== null)
        {
            p_TestObject.setToolVendor(BusinessRules.DEFAULT_ToolVendor);
        }
        if(p_TestObject.getToolVendorTechnology()== null)
        {
            p_TestObject.setToolVendorTechnology(BusinessRules.DEFAULT_ToolVendor_Technology);
        }
    }


    //My adds ends

	private void fixSessionForVersion(Session2 p_Session2)
    {

        	//ccastedo 07.11.06 p_Session2.getM_ApplicationSetting().setM_ExternalXSLTReportFormat(new ExternalXSLTReportFormat());

    }
    private void fixFileSeparatorInProjectReferecesInSession(Session2 p_Session2){
        if(p_Session2.getM_Workspaces() != null){
            List workspaces=p_Session2.getM_Workspaces();
        	for(int i=0; i<workspaces.size(); i++ ){
            	Workspace2 workspace= (Workspace2)workspaces.get(i);
                if(workspace.getM_ProjectReferences() != null){
					List projectReferences= workspace.getM_ProjectReferences();
                    for(int j=0; j < projectReferences.size();j++){
                        ProjectReference projectReference=(ProjectReference) projectReferences.get(j);
                        changeProjectReferenceFileSeparator(projectReference);
                    }
                }
        	}
        }
    }

	private void changeProjectReferenceFileSeparator(ProjectReference projectReference){
        if(projectReference.getPath() != null){
			String result=projectReference.getPath().replace('\\','/');
            projectReference.setPath(result);
        }
        if(projectReference.getM_LocalFilePath() != null){
            String result=projectReference.getM_LocalFilePath().replace('\\','/');
			projectReference.setM_LocalFilePath(result);
        }
        if(projectReference.getM_LocalProjectReference() != null){
            LocalProjectReference localProjectReference=projectReference.getM_LocalProjectReference();
            changeProjectReferenceFileSeparator(localProjectReference);
           /* if(localProjectReference.getM_ProjectReference() != null){
                changeProjectReferenceFileSeparator(localProjectReference.getM_ProjectReference());
            }*/
        }
    }

    private void fixFileSeparatorInTestObjectReferencesInProject2(Project2 p_Project){
		if(p_Project.getTestObjectReferences() != null){
            Vector testObjectReferences=p_Project.getTestObjectReferences();
            for(int i=0; i< testObjectReferences.size(); i++){
                TestObjectReference testObjectReference=(TestObjectReference) testObjectReferences.elementAt(i);
                changeTestObjectReferenceFileSeparator(testObjectReference);
            }
        }
    }
    private void  changeTestObjectReferenceFileSeparator(TestObjectReference testObjectReference){
        if(testObjectReference.getPath() != null){
			String result=testObjectReference.getPath().replace('\\','/');
			testObjectReference.setPath(result);
        }
        if(testObjectReference.getFilePath() != null){
            String result=testObjectReference.getFilePath().replace('\\','/');
          //  testObjectReference.setM_FilePath(result); //Ccastedo 21-04-06

        }
        if(testObjectReference.getM_ProjectPath() != null){
            String result=testObjectReference.getM_ProjectPath().replace('\\','/');
            testObjectReference.setM_ProjectPath(result);
        }
    }
	private void fixFileSeparatorIntoTestObject(TestObject p_TestObject){
        if(p_TestObject.getBRulesReference() != null){
            BRulesReference brulesReference =p_TestObject.getBRulesReference();
            changeBRulesReferenceFileSeparator(brulesReference);
        }
        if(p_TestObject.getTDSTructureReference() != null){
            Vector tdstrucutrereferences=p_TestObject.getTDSTructureReference();
            for(int i=0; i< tdstrucutrereferences.size();i ++){
                TDStructureReference tdStructureReference=(TDStructureReference)tdstrucutrereferences.elementAt(i);
                changeTDStructureReferenceFileSeparator(tdStructureReference);
            }
        }

    }
    private void changeBRulesReferenceFileSeparator(BRulesReference brulesReference){
        if(brulesReference.getFileLocation() != null){
            String result=brulesReference.getFileLocation().replace('\\','/');
			brulesReference.setFileLocation(result);
        }
        if(brulesReference.getFilePath() != null){
            String result=brulesReference.getFilePath().replace('\\','/');
            brulesReference.setFilePath(result);
        }
        if(brulesReference.getTestObjectsFolderPath() != null){
            String result=brulesReference.getTestObjectsFolderPath().replace('\\','/');
            brulesReference.setTestObjectsFolderPath(result);
        }
        if(brulesReference.getProjectLocation() != null){
            String result=brulesReference.getProjectLocation().replace('\\','/');
            brulesReference.setProjectLocation(result);
        }

    }

    private void changeTDStructureReferenceFileSeparator(TDStructureReference tdStructureReference){

        if(tdStructureReference.getM_FilePath() != null){
			String result=tdStructureReference.getM_FilePath().replace('\\','/');
			tdStructureReference.setM_FilePath(result);
        }
        if(tdStructureReference.getM_Path() != null){
            String result=tdStructureReference.getM_Path().replace('\\','/');
            tdStructureReference.setM_Path(result);
        }
        if(tdStructureReference.getM_TestObjectPath() != null){
            String result=tdStructureReference.getM_TestObjectPath().replace('\\','/');
            tdStructureReference.setM_TestObjectPath(result);
        }
    }
	private Vector getNamesOfGlobalsStructures(TDStructure p_TDStructure){
		Vector names= new Vector();
		Vector globalsStructures=p_TDStructure.getM_StructureTestData();
		for (Iterator iter = globalsStructures.iterator(); iter.hasNext();) {
			StructureTestData structureTestData = (StructureTestData) iter.next();
			names.addElement(structureTestData.getName());
		}
		return names;
	}
	private void correctIdStructureInTestDataOldVersion(TDStructure p_TDStructure){
		Vector globalStructures= getNamesOfGlobalsStructures(p_TDStructure);
		if(globalStructures.size() > 0){
			Vector testDatas=p_TDStructure.getTestDataCombination().getM_TestDatas();
			for (Iterator iter = testDatas.iterator(); iter.hasNext();) {
				TestData testData = (TestData) iter.next();
				for (Iterator iterator = globalStructures.iterator(); iterator
						.hasNext();) {
					String structureName = (String) iterator.next();
					correctIdTestDataOldVersion(testData,structureName);
				}
			}
		}
	}
	private void correctIdTestDataOldVersion(TestData p_testdata, String p_StructureName){
		Vector structuresTestData= p_testdata.getM_TDStructure().getM_StructureTestData();
		Vector onlySameStructureName= new Vector();

		for(int i=0; i<structuresTestData.size()+1;i++){
			onlySameStructureName.addElement(new Boolean(false));
		}
		for (Iterator iter = structuresTestData.iterator(); iter.hasNext();) {
			StructureTestData structureTestData = (StructureTestData) iter.next();
			if(structureTestData.getName().equals(p_StructureName)){
				if(structureTestData.getIdTestData()< onlySameStructureName.size()&&(onlySameStructureName.elementAt(structureTestData.getIdTestData()) instanceof Boolean))
					onlySameStructureName.setElementAt(structureTestData,structureTestData.getIdTestData());
				else{
					int i=0;
					while(i< onlySameStructureName.size() && (onlySameStructureName.elementAt(i) instanceof StructureTestData)){
						i++;
					}
					if(i >=onlySameStructureName.size()){

						structureTestData.setIdTestData(i);
						onlySameStructureName.insertElementAt(structureTestData,i);
					}
					else{
						structureTestData.setIdTestData(i);
						onlySameStructureName.setElementAt(structureTestData,i);
					}
				}
			}
		}
		if(onlySameStructureName.size()>1 && onlySameStructureName.firstElement() instanceof StructureTestData){
			Object element= onlySameStructureName.firstElement();
			int i=0;
			while(onlySameStructureName.size()>i && !(element instanceof Boolean)){
				i++;
				StructureTestData structure=(StructureTestData)element;
				structure.setIdTestData(structure.getIdTestData()+1);
				if(i < onlySameStructureName.size())
					element= onlySameStructureName.elementAt(i);
			}
		}
	}
	//svonborries_06012006_begin
	private void changeVariablesFromProjectToTDStructure(Project2 p_Project, String p_filePath){
		StringBuffer absoluteFilePath = null;
		Variables l_variables = p_Project.getM_Variables();
		p_Project = (Project2) (CMFileValidator.fixTestObjectReferences(p_Project,p_filePath)).clone();
		CMApplication.frame.getTreeWorkspaceView().getM_WorkspaceManager().writeProject2ToFile(p_Project,p_filePath);
		for (Iterator iter = p_Project.getTestObjectReferences().iterator(); iter.hasNext();) {
			TestObjectReference l_TestObjectReference = (TestObjectReference) iter.next();
			String l_testObjectPath = l_TestObjectReference.getFilePath();
			FileReader l_fileReader = null;
			TestObject l_testObject = null;
			try {
				absoluteFilePath = new StringBuffer();
				absoluteFilePath.append(VersionManager.fixProjectFilePath(p_filePath,p_Project.getName()));
				absoluteFilePath.append(l_testObjectPath);
				l_fileReader = new FileReader(absoluteFilePath.toString());
				JSX.ObjectReader in = new JSX.ObjectReader(l_fileReader);
				l_testObject = (TestObject) in.readObject();
				for (Iterator iterator = l_testObject.getTDSTructureReference().iterator(); iterator
						.hasNext();) {
					TDStructureReference l_TdStructureReference = (TDStructureReference) iterator.next();
					String l_TdStructurePath = BusinessRules.TEST_DATA_FOLDER+BusinessRules.URL_SEPARATOR+BusinessRules.TESTDATA_NAME_OBJECT_TESTDATA+BusinessRules.FILE_TESTDATA_EXTENSION;
					StringBuffer l_TDStructureFile = new StringBuffer();
					l_TDStructureFile.append(VersionManager.fixProjectFilePath(p_filePath,p_Project.getName()));
					l_TDStructureFile.append(BusinessRules.TEST_OBJECTS_FOLDER);
					l_TDStructureFile.append(BusinessRules.URL_SEPARATOR);
					l_TDStructureFile.append(l_testObject.getName());
					l_TDStructureFile.append(BusinessRules.URL_SEPARATOR);
					l_TDStructureFile.append(l_TdStructurePath);
					TDStructure l_TDStructure = readTestDataFile(l_TDStructureFile.toString());
					/*Vector vars = new Vector();
					vars.addAll((Collection) p_Project.getM_Variables());
					for (Iterator varIter = vars.iterator(); varIter
							.hasNext();) {
						Variables variables = (Variables) varIter.next();
						if (l_TDStructure.getM_Variables().equals(variables)) {
							//eliminate the var of the project, and then assign it to the tdstructure
						}
					}*/
					if (l_TDStructure != null){
						l_TDStructure.setM_Variables(l_variables);
						CMApplication.frame.getTreeWorkspaceView().getM_WorkspaceManager().writeTDStructureToFile(l_TDStructure,l_TDStructureFile.toString());
					}
					//svonborries_01022006_begin
					//if TDStructure don't exist, it create it with this part, and assign variables to testdata file
					else{
						l_TDStructure = CMApplication.frame.getTreeWorkspaceView().getM_WorkspaceManager().readTDStructure(l_TDStructureFile.toString());
						StringBuffer l_TDStructureDirs = new StringBuffer();
						l_TDStructureDirs.append(VersionManager.fixProjectFilePath(p_filePath,p_Project.getName()));
						l_TDStructureDirs.append(BusinessRules.TEST_OBJECTS_FOLDER);
						l_TDStructureDirs.append(BusinessRules.URL_SEPARATOR);
						l_TDStructureDirs.append(l_testObject.getName());
						l_TDStructureDirs.append(BusinessRules.URL_SEPARATOR);
						l_TDStructureDirs.append(BusinessRules.TEST_DATA_FOLDER);
						File file = new File(l_TDStructureDirs.toString());
					    file.mkdir();
						l_TDStructure.setM_Variables(l_variables);
						CMApplication.frame.getTreeWorkspaceView().getM_WorkspaceManager().writeTDStructureToFile(l_TDStructure,l_TDStructureFile.toString());
					}
					//svonborries_01022006_end
				}
				in.close();

			} catch (FileNotFoundException e) {
				Logger.getLogger(this.getClass()).error(e);
			} catch (IOException e) {
				Logger.getLogger(this.getClass()).error(e);
			} catch (ClassNotFoundException e) {
				Logger.getLogger(this.getClass()).error(e);
			}
	}
		if(!CMFileValidator.DELETED_TESTOBJECTREFERENCES.equalsIgnoreCase("")){
			JOptionPane.showMessageDialog(CMApplication.frame,CMMessages.getString("INFO_DELETED_TESTOBJECT_CORRUPTED_1")+
					"(" + p_Project.getName() + ")\n" + CMMessages.getString("INFO_DELETED_TESTOBJECT_CORRUPTED_2")
					+ "\n" + "\n" + '"' + CMFileValidator.DELETED_TESTOBJECTREFERENCES + '"'
					,CMMessages.getString("INFO_DELETED_TESTOBJECT_TITLE"),JOptionPane.INFORMATION_MESSAGE);
//			svonborries_02022006_begin
        	CMFileValidator.DELETED_TESTOBJECTREFERENCES = "";
        	CMFileValidator.DELETED_TESTOBJECTREFERENCES_COUNT = 0;
        	//svonborries_02022006_end
		}
}
	private TDStructure readTestDataFile (String p_filePath){
		FileReader l_TDReader = null;
		TDStructure l_TDStructure = null;
		try {
			if (CMFileValidator.validateFileExist(p_filePath)){
				l_TDReader = new FileReader(p_filePath);
				JSX.ObjectReader in = new JSX.ObjectReader(l_TDReader);
				l_TDStructure = (TDStructure) in.readObject();
				in.close();
				return l_TDStructure;
			}
			else{return null;}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		return null;
	}
	//svonborries_06012006_end
//	svonborries_09012006_begin
	private static String fixProjectFilePath(String p_FilePath, String p_ProjectName){
		String l_filePath = p_FilePath;
		if (!l_filePath.equalsIgnoreCase("")){
			int l_ProjectCount = p_ProjectName.length();
			int l_StringCount = l_filePath.length() - 8;
			l_StringCount -= l_ProjectCount;
			String l_resultString = l_filePath.substring(0,l_StringCount);
			return l_resultString;
		}
		return null;
	}
	//svonborries_09012006_end
	//svonborries_08022006_begin
	private void fixAbsolutePathInToolVendor(Session2 p_Session){
		for (Iterator iter = p_Session.getM_ApplicationSetting().getM_ToolVendors().iterator(); iter.hasNext();) {
			ToolVendor toolVendor = (ToolVendor) iter.next();
			if(toolVendor.getM_FilePath().startsWith(BusinessRules.RESOURCE_FOLDER)){
				String resFolder = BusinessRules.RESOURCE_FOLDER+BusinessRules.URL_SEPARATOR;
				int resFolderCount = resFolder.length();
				String toolVendorPath = toolVendor.getM_FilePath().substring(resFolderCount);
				toolVendor.setM_FilePath(toolVendorPath);
			}
		}
	}
	//svonborries_08022006_end

	private void fixExpectedResultsfromStrings(TestObject p_testObject) {
		for (Effect effect : p_testObject.getStructure().getEffects())
			for (ExpectedResult expectedResult : effect.getLnkExpectedResults())
				if (expectedResult.getNumberValue() == null)
					expectedResult.setValue(expectedResult.getValue());

	}


}
