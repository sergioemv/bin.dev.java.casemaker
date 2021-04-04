

package bi.controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.JOptionPane;
import javax.swing.undo.UndoableEdit;

import model.Dependency;
import model.Element;
import model.EquivalenceClass;
import model.Structure;
import model.edit.CMModelEditFactory;
import model.util.CMDelegate;
import model.util.CMUserOrderBean;
import model.util.IdSet;
import bi.controller.utils.CMClipBoard;
import bi.view.actions.CMAction;
import bi.view.actions.element.CMElementDeleteAction;
import bi.view.cells.CMCellElement;
import bi.view.lang.CMMessages;
import bi.view.mainframeviews.CMApplication;
import bi.view.undomanagementviews.CMCompoundEdit;
import bi.view.undomanagementviews.undoviews.CMComponentAwareEdit;
import bi.view.undomanagementviews.undoviews.CMUndoMediator;

public class ElementManager {

	// static instance of the element manager
	public static final ElementManager INSTANCE = new ElementManager();
	public ElementManager() {

	}
	  public void copyElement(){
		    Element original = getSelectedElement();
		    if (original!=null)
		    	CMClipBoard.getInstance().copy(original);
		  }
	public void pasteElement() {

		CMCompoundEdit ce = new CMCompoundEdit();
		Element selectedElement = getSelectedElement();
		if (selectedElement==null) return;
		Structure structure = selectedElement.getLnkStructure();
	    Element clone = CMClipBoard.getInstance().getElement();
	    if (clone == null || structure==null) return;
	       for(Element element:structure.getElements())
	        	if (element.getName().equalsIgnoreCase(clone.getName())){
	        		clone.setName(ElementManager.INSTANCE.generateNewElementName(clone, structure));
	        		break;
	        	}
	    ce.addEdit(CMModelEditFactory.INSTANCE.createChangeUserOrderModelEdit(clone, selectedElement.getUserOrder()));
	    clone.setUserOrder(selectedElement.getUserOrder());
	    ce.addEdit(CMModelEditFactory.INSTANCE.createAddElementModelEdit(structure, clone));
	    structure.addElement(clone);
	    CMUndoMediator.getInstance().doEdit(ce);
	    CMApplication.frame.getElementsGrid().requestFocus();
	    CMApplication.frame.getElementsGrid().selectCell(clone, CMCellElement.class);

	  }
	 public void cutElement(){
		 Element original = getSelectedElement();
		  if (original==null) return;
		  Structure structure = original.getLnkStructure();

		    for (Dependency dependency : structure.getDependencies())
		    	if (dependency.getElements().contains(original)){
		      		JOptionPane.showMessageDialog(CMApplication.frame,CMMessages.getString("EDIT_CUT_ELEMENT_HASREFERENCES"),CMMessages.getString("LABEL_CASEMAKER"),JOptionPane.ERROR_MESSAGE);
		      		return;
		    	}
		    	CMClipBoard.getInstance().copy(original);
		    	((CMElementDeleteAction)CMAction.ELEMENT_DELETE.getAction()).setAskUser(false);
		    	CMAction.ELEMENT_DELETE.getAction().actionPerformed(null);
		    	((CMElementDeleteAction)CMAction.ELEMENT_DELETE.getAction()).setAskUser(true);
		  }


	/**
	 * Factory method to create a new element (not binded to the structure yet)
	 *
	 * @param p_structure
	 * @return a initialized element
	 */
	public Element createElement(Structure p_structure) {
		// create the element
		Element e = new Element();
		// sets the unique id
		e.setId(this.getNextId(p_structure));
		// sets the default object type
		e.setGUIObject(0);

		e.setName(this.generateNewElementName(p_structure));

		return e;
	}

	/**
	 * Method to initialize an element with the default values and bind it to the structure
	 * @param p_structure
	 * @return a initialized element
	 */
	public UndoableEdit initializeElement(Element element,Structure p_structure) {
		CMCompoundEdit ce = new CMCompoundEdit();
		// sets the unique id
		int newId = this.getNextId(p_structure);
		ce.addEdit(CMModelEditFactory.INSTANCE.createChangeIdModelEdit(element,newId));
		element.setId(newId);

		String newName = this.generateNewElementName(p_structure);
		ce.addEdit(CMModelEditFactory.INSTANCE.createChangeNameModelEdit(element,newName));
		element.setName(newName);
		return ce;
	}

	/**
	 * @author smoreno Generates a new non repeating new name for the element
	 *         the element text depends on the languaje selected
	 * @param p_structure
	 *            to validate the existing names on this structure
	 * @return a unique name in the structure
	 */
	public String generateNewElementName(Structure p_structure) {
		// get the prefix according to the languaje
		String l_Prefix = CMMessages.getString("ELEMENT_DEFAULT_PREFIX");
		// counter of how many "prefixed" elements are
		int l_Counter = 1;
		// search the last "prefixed" element
		for (Iterator i = p_structure.getLnkElements().iterator(); i.hasNext();) {
			String l_Name = ((Element) i.next()).getName();
			// if the name starts with the specified prefix
			if (l_Name.startsWith(l_Prefix)) {
				//get the rest of the string not  included in the prefix
				String candidateSufix = l_Name.substring(l_Prefix.length());

				int candidateSufixint;
				//try to convert the suffix to int
				try {
					candidateSufixint = Integer.parseInt(candidateSufix);
				} catch (Exception e) {
					// continue with the next element
					continue;
				}
				// if the converted to int sufix is greater than the counter assign it plus 1
				if (l_Counter <= candidateSufixint)
					l_Counter = Integer.parseInt(candidateSufix)+1;
			}
		}
		//return the concatenated string
		return l_Prefix+new Integer(l_Counter).toString();
	}

	public void editElement() {
	}



	/**
	 * Deletes the
	 * @param p_element
	 * @return
	 * @throws Exception
	 */
	public  UndoableEdit deleteElement(Element p_element) throws Exception {
		CMCompoundEdit ce = new CMCompoundEdit();
		Structure structure = p_element.getLnkStructure();
		//delete all equivalence classes
		for (EquivalenceClass equivalenceClass : p_element.getEquivalenceClasses())
			ce.addEdit(EquivalenceClassManager.INSTANCE.removeEquivalenceClass(equivalenceClass,p_element));

		//	remove the element from each dependency
		for (Dependency dependency : structure.getDependencies())
			if (dependency.getElements().contains(p_element)){
			ce.addEdit(CMModelEditFactory.INSTANCE.createRemoveElementModelEdit(dependency,p_element));
			dependency.removeElement(p_element);
			// if the dependency size of elements is less than 1 then the dependency is also deleted
			if (dependency.getElements().size()<=1)
				ce.addEdit(DependencyManager.INSTANCE.deleteDependency(dependency,structure));
			else{
				CombinationManager.INSTANCE.updateCombinationsGeneration(dependency,ce);
			}
			}
		//remove the element from the structure
		if (structure.getElements().contains(p_element)){
			ce.addEdit(CMModelEditFactory.INSTANCE.createRemoveElementModelEdit(structure,p_element));
			structure.removeElement(p_element);
		}

		return ce;
	}

	public DependencyManager getLnkDependencyManager() {
		return lnkDependencyManager;
	}

	public void setLnkDependencyManager(DependencyManager lnkDependencyManager) {
		this.lnkDependencyManager = lnkDependencyManager;
	}

	public int getNextId(Structure p_structure) {
		 IdSet idSet = new IdSet();
  	   for (Element element : p_structure.getElements())
  		   idSet.registerId(element.getId());
         return idSet.nextValidId();
	}

	public boolean areElementsEqual(Vector p_before, Vector p_now) {
		int numOfBefores = p_before.size();
		Element elementBefore = null;
		for (int i = 0; i < numOfBefores; i++) {
			elementBefore = (Element) p_before.elementAt(i);
			if (!p_now.contains(elementBefore)) {
				return false;
			}
		}
		int numOfNows = p_now.size();
		Element elementNow = null;
		for (int i = 0; i < numOfNows; i++) {
			elementNow = (Element) p_now.elementAt(i);
			if (!p_before.contains(elementNow)) {
				return false;
			}
		}
		return true;
	}


	public TestCaseManager getM_TestCaseManager() {
		return m_TestCaseManager;
	}

	public void setM_TestCaseManager(TestCaseManager m_TestCaseManager) {
		this.m_TestCaseManager = m_TestCaseManager;
	}

	public CombinationManager getM_CombinationManager() {
		return m_CombinationManager;
	}

	public void setM_CombinationManager(CombinationManager m_CombinationManager) {
		this.m_CombinationManager = m_CombinationManager;
	}

	public EquivalenceClassManager getM_EquivalenceClassManager() {
		return m_EquivalenceClassManager;
	}

	public void setM_EquivalenceClassManager(
			EquivalenceClassManager m_EquivalenceClassManager) {
		this.m_EquivalenceClassManager = m_EquivalenceClassManager;
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

	private EquivalenceClassManager m_EquivalenceClassManager;


	/**
	 * @author smoreno
	 * search the structure for specific element name
	 * @param p_Structure
	 * the structure to search in
	 * @param p_name
	 * the element name to search in the structure
	 * @return
	 * if the element name exist in the structure
	 */
	public boolean elementNameExists(Structure p_Structure, String p_name) {

		for (Iterator i =  p_Structure.getLnkElements().iterator();i.hasNext();)
			if (((Element)i.next()).getName().equalsIgnoreCase(p_name))
				return true;
		return false;
	}

	/**
	 * @author smoreno
	 * generate a new element name based on the element passed avoiding duplication within the structure
	 * @param element
	 * the element name will be the base prefix for the name
	 * @return
	 * a new name for the element
	 */
	public String generateNewElementName(Element element, Structure structure) {
		int l_Counter = 1;
		// search the last "prefixed" element
		for (Iterator i = structure.getLnkElements().iterator(); i.hasNext();) {
			String l_Name = ((Element) i.next()).getName();
			// if the name starts with the specified name
			if (l_Name.startsWith(element.getName())) {
				//get the rest of the string not  included in the name
				String candidateSufix = l_Name.substring(element.getName().length());

				int candidateSufixint;
				//try to convert the suffix to int
				try {
					candidateSufixint = Integer.parseInt(candidateSufix);
				} catch (Exception e) {
					// continue with the next element
					continue;
				}
				// if the converted  sufix is greater than the counter assign it plus 1
				if (l_Counter <= candidateSufixint)
					l_Counter = Integer.parseInt(candidateSufix)+1;
			}
	}
		return element.getName()+new Integer(l_Counter).toString();
}

	/**
	*@autor smoreno
	 * @param p_elementName
	 * @return
	 */
	public String generateNewElementName(Structure structure,String p_elementName) {
		int l_Counter = 1;
		// search the last "prefixed" element
		for (Iterator i = structure.getLnkElements().iterator(); i.hasNext();) {
			String l_Name = ((Element) i.next()).getName();
			// if the name starts with the specified name
			if (l_Name.startsWith(p_elementName)) {
				//get the rest of the string not  included in the name
				String candidateSufix = l_Name.substring(p_elementName.length());

				int candidateSufixint;
				//try to convert the suffix to int
				try {
					candidateSufixint = Integer.parseInt(candidateSufix);
				} catch (Exception e) {
					// continue with the next element
					continue;
				}
				// if the converted  sufix is greater than the counter assign it plus 1
				if (l_Counter <= candidateSufixint)
					l_Counter = Integer.parseInt(candidateSufix)+1;
			}
	}
		return p_elementName+new Integer(l_Counter).toString();
	}

	/**
	*@autor smoreno
	 */
	public static void reloadElementViews() {
		CMApplication.frame.setWaitCursor(true);
		CMApplication.frame.getElementsGrid().setStructure(CMApplication.frame.getElementsGrid().getStructure());
		CMApplication.frame.getElementsGrid().updateViews();
		CMApplication.frame.setWaitCursor(false);
	}

	/**
	 * @param p_element
	 * @param p_element2
	 */
	public void moveElement(Element p_elementOrigin, Element p_elementDestination) {

		Structure structure = p_elementOrigin.getLnkStructure();
		CMCompoundEdit ce = new CMCompoundEdit();
		ce.addEdit(new CMComponentAwareEdit());
		ce.addUndoDelegateEdit(new CMDelegate(){
			public void execute() {
				CMApplication.frame.getElementsGrid().deleteAllElementViews();
				CMApplication.frame.getElementsGrid().addAllElementViews();
				TestCaseManager.INSTANCE.reloadTestCaseViews();
				CMApplication.frame.getCMStdCombinationViews().getM_CMElementAndStdCombinationViews().update();
			}});
		ArrayList<Element> listElems = new ArrayList<Element>();
		listElems.addAll(structure.getElements(CMUserOrderBean.COMPARATOR));
		int newPosition = listElems.indexOf(p_elementDestination);

		listElems.remove(p_elementOrigin);
		listElems.add(newPosition,p_elementOrigin);

		for (Element element : listElems){
				ce.addEdit(CMModelEditFactory.INSTANCE.createChangeUserOrderModelEdit(element,element.getUserOrder()+1));
				element.setUserOrder(listElems.indexOf(element));
			}
		ce.addRedoDelegateEdit(new CMDelegate(){
			public void execute() {
				CMApplication.frame.getElementsGrid().deleteAllElementViews();
				CMApplication.frame.getElementsGrid().addAllElementViews();
				TestCaseManager.INSTANCE.reloadTestCaseViews();
				CMApplication.frame.getCMStdCombinationViews().getM_CMElementAndStdCombinationViews().update();
			}

		});
		CMApplication.frame.getElementsGrid().deleteAllElementViews();
		CMApplication.frame.getElementsGrid().addAllElementViews();
		TestCaseManager.INSTANCE.reloadTestCaseViews();
		CMApplication.frame.getCMStdCombinationViews().getM_CMElementAndStdCombinationViews().update();
		if (ce.hasEdits())
			CMUndoMediator.getInstance().doEdit(ce);



	}

	public static Element getSelectedElement() {

		return CMApplication.frame.getElementsGrid().getCurrentElement();
	}


}
