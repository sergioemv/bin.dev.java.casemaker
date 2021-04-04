package bi.controller.editcontrol;


import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;

import javax.swing.undo.UndoableEdit;

import model.Dependency;
import model.Element;
import model.EquivalenceClass;
import model.Structure;
import model.edit.CMModelEditFactory;
import bi.controller.CombinationManager;
import bi.view.actions.dependency.CMPanelDependency;
import bi.view.lang.CMMessages;
import bi.view.undomanagementviews.CMCompoundEdit;

public class CMDependencyEditController extends CMDefaultEditController {

	private Dependency dependency;
	private CMPanelDependency panel;
	private Structure structure;
	public static final Dimension DEFAULT_DIALOG_SIZE = new Dimension(621, 530);

	public CMDependencyEditController(Structure structure, Dependency p_Dependency) {
		this.dependency = p_Dependency;
		this.structure = structure;
	}

	/**
	 * fill the properties of the equivalence class in the panel
	 */
	private final void loadPanelProperties() {

		if (dependency==null)return;
		getPanel().getJTextAreaGeneratedDescription().setText(dependency.getDescription());
		getPanel().getJTextAreaDescription().setText(dependency.getDescriptionEditable());
		if (dependency.getElements().size()== 0)
			getPanel().getJPanelGeneratedDescription().setVisible(false);
		fillAllowedElementList();
	    fillAssignedElementList();

	}

	public CMPanelDependency getPanel() {
		if (panel == null){
			panel = new CMPanelDependency();
			loadPanelProperties();

		}
		return panel;
	}

	public void setPanel(CMPanelDependency panel) {
		this.panel = panel;
	}


	public UndoableEdit applyChanges() throws Exception{
		CMCompoundEdit ce = new CMCompoundEdit();
		if (getStructure()== null || dependency == null) return ce;
		ce.addEdit(updateElementAssigmentWithPanel());
		if (ce.hasEdits() && dependency.getCombinations().size()>0){
				CombinationManager.INSTANCE.updateCombinationsGeneration(dependency, ce);
		}
		ce.addEdit(CMModelEditFactory.INSTANCE.createChangeDescriptionEditableModelEdit(dependency,getPanel().getJTextAreaDescription().getText()));
		dependency.setDescriptionEditable(getPanel().getJTextAreaDescription().getText());

		return ce;
	}

	public UndoableEdit updateElementAssigmentWithPanel() throws Exception{
		CMCompoundEdit ce = new CMCompoundEdit();
		List<Element> newElementsAssigned = getPanel().getShuttleElements().getAssignedList();
		if (newElementsAssigned.size()<=1)
			throw new Exception(CMMessages.getString("INFO_NUMBER_OF_DEPENDENT_ELEMENTS"));
		  // remove no longer assigned elements
	        for (Element element : dependency.getElements())
	        	if (!newElementsAssigned.contains(element)){
	        		ce.addEdit(CMModelEditFactory.INSTANCE.createRemoveElementModelEdit(dependency,element));
	        		dependency.removeElement(element);
	        		// remove also the equivalence classes
	        for (EquivalenceClass equivalenceClass : element.getEquivalenceClasses())
	        	if (dependency.getEquivalenceClasses().contains(equivalenceClass)){
	        		ce.addEdit(CMModelEditFactory.INSTANCE.createRemoveEquivalenceClassModelEdit(dependency,equivalenceClass));
	        		dependency.removeEquivalenceClass(equivalenceClass);
	        		}
	        	}
	        // assign ne elements

	        for (Element element : newElementsAssigned)
	        	if (!dependency.getElements().contains(element)){
	        		ce.addEdit(CMModelEditFactory.INSTANCE.createAddElementModelEdit(dependency, element));
	        		dependency.addElement(element);
	        		for (EquivalenceClass equivalenceClass : element.getEquivalenceClasses())
	    	        	if (!dependency.getEquivalenceClasses().contains(equivalenceClass) && equivalenceClass.getStateName().equalsIgnoreCase("+")){
	    	        		ce.addEdit(CMModelEditFactory.INSTANCE.createAddEquivalenceClassModelEdit(dependency,equivalenceClass));
	    	        		dependency.addEquivalenceClass(equivalenceClass);
	    	        		}
	        	}

	    return ce;
	}

	public void fillAllowedElementList() {
		List<Element>  allowedElements = new ArrayList<Element>();
	    getPanel().getShuttleElements().clearAvailableList();
		for (Element  element : getStructure().getNonEmptyElements())
	      if( !dependency.getElements().contains(element)) {
	        	if( !allowedElements.contains(element)) {
	        		allowedElements.add(element);
	            }
	          }
	    getPanel().getShuttleElements().setAvailableList(allowedElements);
	  }

	  public void fillAssignedElementList() {
		  List<Element>  assignedElements = new ArrayList<Element>();
		    getPanel().getShuttleElements().clearAssignedList();
			for (Element  element : getStructure().getNonEmptyElements())
		      if( dependency.getElements().contains(element)) {
		        	if( !assignedElements.contains(element)) {
		        		assignedElements.add(element);
		            }
		          }
	    getPanel().getShuttleElements().setAssignedList(assignedElements);

	  }

	public Structure getStructure() {
		if (structure == null)
			if (dependency !=null)
				structure = dependency.getLnkStructure();
		return structure;
	}

	public Dependency getDependency() {
		return dependency;
	}




}
