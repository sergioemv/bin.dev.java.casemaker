

package  bi.controller;
import javax.swing.JOptionPane;
import javax.swing.undo.UndoableEdit;

import model.Combination;
import model.Dependency;
import model.Effect;
import model.Element;
import model.EquivalenceClass;
import model.Structure;
import model.TestCase;
import model.TestObject;
import model.edit.CMModelEditFactory;
import model.util.IdSet;
import bi.controller.utils.CMClipBoard;
import bi.view.actions.CMAction;
import bi.view.actions.equivalenceclass.CMEquivalenceClassDeleteAction;
import bi.view.lang.CMMessages;
import bi.view.mainframeviews.CMApplication;
import bi.view.undomanagementviews.CMCompoundEdit;
import bi.view.undomanagementviews.undoviews.CMUndoMediator;

public class EquivalenceClassManager {
    public static final EquivalenceClassManager INSTANCE = new EquivalenceClassManager();
    public EquivalenceClassManager() {

    }
    /**
    *
    */
   public void copyEquivalenceClass() {

   	EquivalenceClass original= getSelectedEquivalenceClass();
   	if(original != null){
   		CMClipBoard.getInstance().copy(original);
   	}
   }

   /**
    *
    */
   public void cutEquivalenceClass() {

   	EquivalenceClass original= getSelectedEquivalenceClass();
   	if(original != null){
   		CMClipBoard.getInstance().copy(original);
   		((CMEquivalenceClassDeleteAction)CMAction.EQUIVALENCECLASS_DELETE.getAction()).setAskUser(false);
   		CMAction.EQUIVALENCECLASS_DELETE.getAction().actionPerformed(null);
   		((CMEquivalenceClassDeleteAction)CMAction.EQUIVALENCECLASS_DELETE.getAction()).setAskUser(true);
   	}

   }

   public void pasteEquivalenceClass() {
   	CMCompoundEdit ce = new CMCompoundEdit();
   	Element currentElement=null;
   	if (CMClipBoard.getInstance().getEquivalenceClass() == null) return;
   	EquivalenceClass newEquivalenceClass= CMClipBoard.getInstance().getEquivalenceClass();
   	currentElement=ElementManager.getSelectedElement();
   	if (currentElement == null) return;
   	if(currentElement.getEquivalenceClassbyId(newEquivalenceClass.getId())!=null){
   		JOptionPane.showMessageDialog(CMApplication.frame,CMMessages.getString("MESSAGE_EQUIVALENCECLASS_ID_DUPLICATE"),CMMessages.getString("TESTDATA_IMPORT_TITLE_INFORMATION"),JOptionPane.INFORMATION_MESSAGE);
   		ce.addEdit(CMModelEditFactory.INSTANCE.createChangeIdModelEdit(newEquivalenceClass, EquivalenceClassManager.INSTANCE.getNextId(currentElement)));
   		newEquivalenceClass.setId(EquivalenceClassManager.INSTANCE.getNextId(currentElement));
   	}
   	ce.addEdit(CMModelEditFactory.INSTANCE.createAddEquivalenceClassModelEdit(currentElement, newEquivalenceClass));
   	currentElement.addEquivalenceClass(newEquivalenceClass);
   	CMUndoMediator.getInstance().doEdit(ce);

   }
    public EquivalenceClass createEquivalenceClass(Element p_element) {
      EquivalenceClass ec = new EquivalenceClass();
      ec.setId(this.getNextId(p_element));
      //ec.setId(this.generateId());
      return ec;
    }




 public UndoableEdit removeEquivalenceClass(EquivalenceClass p_equivalenceClass) throws Exception{

// verification of model structure
   Element element = p_equivalenceClass.getLnkElement();
   if (element == null) element = ElementManager.getSelectedElement();
   if (element == null || !element.getEquivalenceClasses().contains(p_equivalenceClass))
	   	throw new Exception("Parent Element not found.");

         return removeEquivalenceClass(p_equivalenceClass, element);
    }

 public void deleteEffectOfEquivalenceClasses(Effect p_Effect, Structure p_Structure) {
   int numOfElements = p_Structure.getLnkElements().size();
   int numOfEquivalenceClasses = 0;
   Element element = null;
   EquivalenceClass equivalenceClass = null;
   Effect effect = null;

   for( int i = 0; i < numOfElements; i++) {
     element = (Element) p_Structure.getLnkElements().elementAt(i);
     numOfEquivalenceClasses = element.getEquivalenceClasses().size();
     for( int j = 0; j < numOfEquivalenceClasses; j++) {
           equivalenceClass = (EquivalenceClass) element.getEquivalenceClasses().get(j);
       if( equivalenceClass.getEffects().contains(p_Effect)) {
           equivalenceClass.removeEffect(p_Effect);
       }
     }
   }

 }
     public int getNextId(Element p_element) {
    	 IdSet idSet = new IdSet();
  	   for (EquivalenceClass equivalenceClass : p_element.getEquivalenceClasses())
  		   idSet.registerId(equivalenceClass.getId());
         return idSet.nextValidId();
     }

 public UndoableEdit changeEquivalenceClassRiskLevelInTestCases(int p_NewRiskLevel, EquivalenceClass p_EquivalenceClass, TestObject p_TestObject) {
    CMCompoundEdit ce = new CMCompoundEdit();
   int numOfTestCases = p_TestObject.getStructure().getTestCases().size();
   TestCase testCase = null;

   for( int i = 0; i < numOfTestCases; i++) {
     testCase = (TestCase) p_TestObject.getStructure().getTestCases().get(i);
     if( testCase.getEquivalenceClasses().contains(p_EquivalenceClass)) {
        ce.addEdit(CMModelEditFactory.INSTANCE.createChangeRiskLevelModelEdit(testCase, p_NewRiskLevel));
       testCase.setRiskLevel(p_NewRiskLevel);
     }
   }
   return ce;
 }

 public DependencyManager getLnkDependencyManager(){
         return lnkDependencyManager;
     }

 public void setLnkDependencyManager(DependencyManager lnkDependencyManager){
         this.lnkDependencyManager = lnkDependencyManager;
     }

 public TestCaseManager getM_TestCaseManager(){
         return m_TestCaseManager;
     }

 public void setM_TestCaseManager(TestCaseManager m_TestCaseManager){
         this.m_TestCaseManager = m_TestCaseManager;
     }

 public CombinationManager getM_CombinationManager(){
         return m_CombinationManager;
     }

 public void setM_CombinationManager(CombinationManager m_CombinationManager){
         this.m_CombinationManager = m_CombinationManager;
     }

 public void changeEquivalenceClassValue(String p_Value, EquivalenceClass p_EquivalenceClass){
     p_EquivalenceClass.setValue(p_Value);
     //cmGridModel.setValueAt(new CMCellEquivalenceClassValue(this,p_EquivalenceClass),p_Row,p_Column);
  }


public void changeEquivalenceClassDescription(String p_Description, EquivalenceClass p_EquivalenceClass){
     p_EquivalenceClass.setDescription(p_Description);
     //cmGridModel.setValueAt(new CMCellEquivalenceClassDescription(this,p_EquivalenceClass),p_Row,p_Column);
  }


 /**
  * @clientCardinality 1
  * @supplierCardinality 1
  * @directed
  */
 private DependencyManager lnkDependencyManager;

 /**
  * @clientCardinality 1
  * @supplierCardinality 1
  * @directed
  */
 private TestCaseManager m_TestCaseManager;
 private CombinationManager m_CombinationManager;
/**
 * @return
 */
public static EquivalenceClass getSelectedEquivalenceClass() {

    return CMApplication.frame.getElementsGrid().getSelectedEquivalenceClass();
}

/**
 * @param p_equivalenceClass
 * @param p_p_element
 * @return
 */
public UndoableEdit removeEquivalenceClass(EquivalenceClass p_equivalenceClass, Element p_element) throws Exception{
    Structure structure = p_element.getLnkStructure();
    CMCompoundEdit ce = new CMCompoundEdit();

    //remove the equivalence class
    //from all dependencies
    for (Dependency dependency : structure.getDependencies())
    {
        if (dependency.getEquivalenceClasses().contains(p_equivalenceClass))
        {
            ce.addEdit(CMModelEditFactory.INSTANCE.createRemoveEquivalenceClassModelEdit(dependency, p_equivalenceClass));
            dependency.removeEquivalenceClass(p_equivalenceClass);
        }
        //delete related combinations
        for (Combination combination : dependency.getCombinations())
            if (combination.getEquivalenceClasses().contains(p_equivalenceClass))
                ce.addEdit(removeEquivalenceFromCombination(p_equivalenceClass,combination));
    }
    //from std Combinations
    for (Combination stdCombination : structure.getCombinations())
        if (stdCombination.getEquivalenceClasses().contains(p_equivalenceClass))
        {
            ce.addEdit(CMModelEditFactory.INSTANCE.createRemoveEquivalenceClassModelEdit(stdCombination,p_equivalenceClass));
            stdCombination.removeEquivalenceClass(p_equivalenceClass);
        }

    //from all Test Cases
    for (TestCase testCase : structure.getTestCases())
        if (testCase.getEquivalenceClasses().contains(p_equivalenceClass)){
            ce.addEdit(CMModelEditFactory.INSTANCE.createRemoveEquivalenceClassModelEdit(testCase,p_equivalenceClass));
            testCase.removeEquivalenceClass(p_equivalenceClass);
        }
    //from the element
    if (p_element.getEquivalenceClasses().contains(p_equivalenceClass))
    {
        ce.addEdit(CMModelEditFactory.INSTANCE.createRemoveEquivalenceClassModelEdit(p_element,p_equivalenceClass));
        p_element.removeEquivalenceClass(p_equivalenceClass);
    }

    return ce;
}

/**
 * @param p_equivalenceClass
 * @param p_combination
 * @return
 */
private UndoableEdit removeEquivalenceFromCombination(EquivalenceClass p_equivalenceClass, Combination p_combination) {
    CMCompoundEdit ce = new CMCompoundEdit();
    for (Combination combination : p_combination.getCombinations())
        if (combination.getEquivalenceClasses().contains(p_equivalenceClass))
            ce.addEdit(removeEquivalenceFromCombination(p_equivalenceClass, combination));
   
    if (p_combination.getEquivalenceClasses().contains(p_equivalenceClass)){
        ce.addEdit(CMModelEditFactory.INSTANCE.createRemoveEquivalenceClassModelEdit(p_combination,p_equivalenceClass));
        p_combination.removeEquivalenceClass(p_equivalenceClass);
        ce.addEdit(CombinationManager.INSTANCE.deleteCombination(p_combination, p_combination.getDependencyRoot()));
    }


    return ce;
}
}
