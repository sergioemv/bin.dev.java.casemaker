/**
 * 20/10/2006
 * svonborries
 */
package bi.controller.testdata.formula;

import java.awt.Component;
import java.awt.Graphics;

import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import model.ICMFormula;

import bi.view.lang.CMMessages;
import bi.view.mainframeviews.CMApplication;
import bi.view.utils.testdata.CMPanelFormulasSelect;

/**
 * @author svonborries
 *
 */
public class CMFormulasEditController {
	
	private CMPanelFormulasSelect panelFormulas = null;
	private CMFormulas formulaSelected;
	private CMFormulaEditController formulaEditController = null;
	private CMParameterController parent = null;
	private ICMFormula icmformulaForEdition = null;
	private boolean isEditable = true;
	private boolean isSecond = true;
	
//	public CMFormulasEditController(){
//		createPanelFormulas();
//		panelFormulas.getListFormulas().setSelectedIndex(0);
//	}
	public CMFormulasEditController() {
	}
	/**
	 * @param formula, this Constructor, MUST be USED for EDITION porpouses.
	 * 11/12/2006
	 * svonborries
	 */
	public CMFormulasEditController(ICMFormula formula){
		icmformulaForEdition = formula;
		getPanelFormulas().getListCategories().setSelectedValue(formula.getCategory(), true);
		getPanelFormulas().getListFormulas().setSelectedValue(formula.getFormulaEnum(), true);
		
	}
	
	
	public CMPanelFormulasSelect createPanelFormulas(){
		if(panelFormulas == null){
			panelFormulas = new CMPanelFormulasSelect(){/**
				 * 01/11/2006
				 * svonborries
				 */
				private static final long serialVersionUID = 1L;

			public void paintComponent(Graphics g){
				super.paintComponent(g);
				for(Component comp: panelFormulas.getComponents()){
					comp.repaint();
				}
			}};
			panelFormulas.getListFormulas().addListSelectionListener(
	                new ListSelectionListener() {
	                    public void valueChanged(ListSelectionEvent e) { jListFormulasValueChanged(e); }
	                });
			panelFormulas.getListCategories().addListSelectionListener(
	                new ListSelectionListener() {
	                    public void valueChanged(ListSelectionEvent e) { jListFunctionValueChanged(e); }
	                });
			panelFormulas.getListCategories().setListData(loadCategories());
			panelFormulas.getListCategories().setSelectedIndex(0);
		}
		//panelFormulas.getListFormulas().setSelectedIndex(0);
		return panelFormulas;
	}
	
    private Object[] loadCategories() {
		return CMFormulaCategory.values();
	}
	
	private void jListFunctionValueChanged(ListSelectionEvent e) {
    	CMFormulaCategory category = (CMFormulaCategory) panelFormulas.getListCategories().getSelectedValue();
    	((DefaultListModel)panelFormulas.getListFormulas().getModel()).clear();
    	for (CMFormulas formula: CMFormulas.values()){
    		if(formula.getCategory() == category){
    			((DefaultListModel)panelFormulas.getListFormulas().getModel()).addElement(formula);
    		}
    	}
    	panelFormulas.getListFormulas().setSelectedIndex(0);
	}
	
	private void jListFormulasValueChanged(ListSelectionEvent e) {
    	CMFormulas formula = (CMFormulas) panelFormulas.getListFormulas().getSelectedValue();
    	if(formula != null)
    	{
    		panelFormulas.getLabelCanonicalName().setText(formula.getCanonicalFormula());
    		panelFormulas.getJTextAreaDescription().setText(formula.getDescription());
    		setFormulaSelected(formula);
    		ICMFormula formulaSelected = getFormulaSelected().getFormula();
//    		if(!getIcmformulaForEdition().equals(formulaSelected)){
//    			formulaEditController = new CMFormulaEditController(formulaSelected);
//    		}
//    		else{
//    			formulaEditController = new CMFormulaEditController(getIcmformulaForEdition());
//    		}
    		if(getIcmformulaForEdition() != null){
    			if(!getIcmformulaForEdition().getFormulaEnum().equals(formulaSelected.getFormulaEnum())){
    				if(isSecond  == true){
    					formulaEditController = new CMFormulaEditController(getIcmformulaForEdition());
    					isSecond = false;
    				}
    					
    				else
    					formulaEditController = new CMFormulaEditController(formulaSelected);
    			}
    			else{
    				String messageReplace = CMMessages.getString("TESTDATA_EDITFORMULA_REPLACE_PART1") + getIcmformulaForEdition().toString()
    				+ CMMessages.getString("TESTDATA_EDITFORMULA_REPLACE_PART2") + formulaSelected.toString() + ")?";
    				if(isEditable  == true){
        				formulaEditController = new CMFormulaEditController(getIcmformulaForEdition());
        				isEditable = false;
        			}
    				else if(JOptionPane.showConfirmDialog(CMApplication.frame, messageReplace, CMMessages.getString("TESTDATA_MESSAGE_TITLE_CONFIRMATION"), JOptionPane.YES_NO_OPTION) == JOptionPane.OK_OPTION){
        				formulaEditController = new CMFormulaEditController(formulaSelected);
        			}
    				else{
        				formulaEditController = new CMFormulaEditController(getIcmformulaForEdition());
        			}
        		}
    				
    		}
    		else{
    			formulaEditController = new CMFormulaEditController(formulaSelected);
    		}
    		
    		
    		
//    		if(getIcmformulaForEdition() != null ){
//    			String messageReplace = CMMessages.getString("TESTDATA_EDITFORMULA_REPLACE_PART1") + getIcmformulaForEdition().toString()
//				+ CMMessages.getString("TESTDATA_EDITFORMULA_REPLACE_PART2") + formulaSelected.toString() + ")?";
//    			if(isEditable  == true){
//    				formulaEditController = new CMFormulaEditController(getIcmformulaForEdition());
//    				isEditable = false;
//    			}
//    			else if(JOptionPane.showConfirmDialog(CMApplication.frame, messageReplace, CMMessages.getString("TESTDATA_MESSAGE_TITLE_CONFIRMATION"), JOptionPane.YES_NO_OPTION) == JOptionPane.OK_OPTION){
//    				formulaEditController = new CMFormulaEditController(getIcmformulaForEdition());
//    			}
//    			else{
//    				formulaEditController = new CMFormulaEditController(formulaSelected);
//    			}
//    		}
//    		else{
//    			formulaEditController = new CMFormulaEditController(formulaSelected);
//    		}

    		formulaEditController.setParent(this);
    		if(getParent() != null){
    			((CMParameterController)getParent()).setValue(formulaSelected);
    		}
    		updatePanelPreview(this);
//    		boolean sw = true;
//    		Object parentOfAllFormulasEditController = getParent();
//    		while(sw){
//    			if(parentOfAllFormulasEditController == null){
//    				sw = false;
//    				parentOfAllFormulasEditController = this;
//    			}
//    			else{
//    				if(parentOfAllFormulasEditController instanceof CMFormulasEditController){
//    					if(((CMFormulasEditController)parentOfAllFormulasEditController).getParent() == null)
//    						sw = false;
//    					else
//    						parentOfAllFormulasEditController = ((CMFormulasEditController)parentOfAllFormulasEditController).getParent();
//    				}
//    				else if (parentOfAllFormulasEditController instanceof CMFormulaEditController){
//    					parentOfAllFormulasEditController = ((CMFormulaEditController)parentOfAllFormulasEditController).getParent();
//    				} 
//    				else if (parentOfAllFormulasEditController instanceof CMParameterController){
//    					parentOfAllFormulasEditController = ((CMParameterController)parentOfAllFormulasEditController).getParent();
//    				}
//    			}
//    		}
    		getPanelFormulas().getJSplitPane1().setBottomComponent(formulaEditController.getPanelParameters());
//    		String formulaPreview = ((CMFormulasEditController)parentOfAllFormulasEditController).getChild().getFormula().toString() 
//    			+ "    = " + ((CMFormulasEditController)parentOfAllFormulasEditController).getChild().getFormula().getFormattedValueResult();
//    		this.setTextAreaPreview(formulaPreview);
    	}
		
	}
	
	/**
	 * @param formulasEditController, this param have to be a CMFormulasEditController, it don't care if this isn't the root of the
	 * model, because it will search until it find it, it will calculate the formula, with this CMFormulasEditController that it
	 * finds, then will update the panel.
	 * 20/11/2006
	 * svonborries
	 */
	public static void updatePanelPreview(CMFormulasEditController formulasEditController){
		boolean sw = true;
		Object parentOfAllFormulasEditController = formulasEditController.getParent();
		while(sw){
			if(parentOfAllFormulasEditController == null){
				sw = false;
				parentOfAllFormulasEditController = formulasEditController;
			}
			else{
				if(parentOfAllFormulasEditController instanceof CMFormulasEditController){
					if(((CMFormulasEditController)parentOfAllFormulasEditController).getParent() == null)
						sw = false;
					else
						parentOfAllFormulasEditController = ((CMFormulasEditController)parentOfAllFormulasEditController).getParent();
				}
				else if (parentOfAllFormulasEditController instanceof CMFormulaEditController){
					parentOfAllFormulasEditController = ((CMFormulaEditController)parentOfAllFormulasEditController).getParent();
				} 
				else if (parentOfAllFormulasEditController instanceof CMParameterController){
					parentOfAllFormulasEditController = ((CMParameterController)parentOfAllFormulasEditController).getParent();
				}
			}
		}
		String formulaPreview = ((CMFormulasEditController)parentOfAllFormulasEditController).getChild().getFormula().toString() 
		+ "    = " + ((CMFormulasEditController)parentOfAllFormulasEditController).getChild().getFormula().getFormattedValueResult();
		formulasEditController.setTextAreaPreview(formulaPreview);
		
	}

	private void setFormulaSelected(CMFormulas formula) {
		formulaSelected = formula;
	}


	public CMFormulas getFormulaSelected(){
		return formulaSelected;
	}


	public CMPanelFormulasSelect getPanelFormulas() {
		if(panelFormulas == null)
			createPanelFormulas();
		return panelFormulas;
	}


	public CMFormulaEditController getChild() {
		return formulaEditController;
	}


	public void setChild(
			CMFormulaEditController formulaEditController) {
		this.formulaEditController = formulaEditController;
	}

	public void setParent(CMParameterController controller) {
		parent = controller;
	}
	
	public CMParameterController getParent(){
		return parent;
	}
	
	public void setTextAreaPreview(String preview){
		getPanelFormulas().getJTextAreaPreview().setText(preview);
	}

	/**
	 * @return the icmformulaForEdition
	 * 11/12/2006
	 * svonborries
	 */
	public ICMFormula getIcmformulaForEdition() {
		return icmformulaForEdition;
	}
	
}
