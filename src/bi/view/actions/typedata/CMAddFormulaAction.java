/**
 *
 */
package bi.view.actions.typedata;

import java.awt.Event;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;

import model.ICMFormula;
import model.ITypeData;
import model.TestDataFormat;
import model.edit.CMModelEditFactory;
import model.util.CMDelegate;


import bi.controller.testdata.CMTypeDataUtils;
import bi.controller.testdata.formula.CMFormulasEditController;
import bi.view.actions.CMAction;
import bi.view.cells.CMCellTDStructureFormula;
import bi.view.cells.CMCellTDStructureValue;
import bi.view.lang.CMMessages;
import bi.view.mainframeviews.CMApplication;
import bi.view.mainframeviews.CMFrameView;
import bi.view.testdataviews.CMGridTDStructure;
import bi.view.testdataviews.CMIndexTDStructureUpdate;
import bi.view.undomanagementviews.CMCompoundEdit;
import bi.view.undomanagementviews.undoviews.CMComponentAwareEdit;
import bi.view.undomanagementviews.undoviews.CMUndoMediator;
import bi.view.utils.CMDefaultDialog;
import bi.view.utils.CMFormatFactory;
import bi.view.utils.CMModalResult;

/**
 * @author svonborries
 *
 */
@SuppressWarnings("serial")
public class CMAddFormulaAction extends AbstractAction implements Action {
	
	private CMGridTDStructure grid = null;
	private CMFrameView m_Frame = null;
	private ITypeData typeData = null;

	public CMAddFormulaAction(){

		super(CMMessages.getString("TESTDATA_ADD_FORMULA"));
		putValue(Action.SHORT_DESCRIPTION, CMMessages.getString("TESTDATA_ADD_FORMULA"));
	    putValue(Action.SMALL_ICON, CMAction.TESTDATA_ADD_FORMULA.getIcon());
	    putValue(Action.MNEMONIC_KEY,(int)CMMessages.getString("ADD_FORMULA_MNEMONIC").charAt(0));
	    putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F, Event.CTRL_MASK));
	}

	public void actionPerformed(ActionEvent e) {

//		if (m_frame.isIsPanelTestDataSelected())
//            m_panelTestData.getM_CMTDStructure().generateFormula();
//        else
//            m_gridTDStructure.generateFormula();
		
		generateFormula();
	}
	
    private void generateFormula() {
    	
    	CMCompoundEdit ce = new CMCompoundEdit();
    	m_Frame = CMApplication.frame;
    	grid = m_Frame.getGridTDStructure();
    	if(m_Frame.isIsPanelTestDataSelected())
    		grid = CMApplication.frame.getPanelTestDataView().getM_CMGridTDStructure();
    	typeData = grid.getSelectedTypeData();
    	
    	ce.addUndoDelegateEdit(new CMDelegate(){
			public void execute() {
        		grid.update(CMIndexTDStructureUpdate.getInstance().getIndex());
			}
		});
		ce.addEdit(new CMComponentAwareEdit());
   	
    	if(CMTypeDataUtils.checkIfTypeDataHasICMValue(grid.getSelectedTypeData())){
			String messageConfirm = CMMessages.getString("TESTDATA_MESSAGE_CONFIRMATION_PART1")+" (" + CMTypeDataUtils.getICMValueNameoftheSelectedTypeData
			(grid.getSelectedTypeData())   +") " + CMMessages.getString("TESTDATA_MESSAGE_CONFIRMATION_PART2") ; 
			int confirm = JOptionPane.showConfirmDialog(CMApplication.frame, messageConfirm, CMMessages.getString("TESTDATA_MESSAGE_TITLE_CONFIRMATION"), JOptionPane.YES_NO_OPTION);
			if(confirm == JOptionPane.NO_OPTION){
				return;
			}
		}
    	
    	CMDefaultDialog cm = new CMDefaultDialog();
    	CMFormulasEditController formulasEditController = new CMFormulasEditController();

    	cm.setJPanelContained(formulasEditController.getPanelFormulas());
        cm.setSize(new java.awt.Dimension(655,560));
        cm.setTitle(grid.getSelectedTypeData().getField()+" - "+this.getValue(Action.NAME));
        cm.setResizable(false);
        cm.setVisible(true);
        if(cm.getModalResult() == CMModalResult.OK){
        	if(CMApplication.frame.isIsPanelTestDataSelected()){
        		//svonborries_26062007_begin
        		if(typeData.getGlobal().equalsIgnoreCase("G")){
        			int confirmFormula = JOptionPane.showConfirmDialog(m_Frame, CMMessages.getString("GLOBAL_REFERENCE_DELETE"),
                            CMMessages.getString("TESTDATA_TITLE_ERROR"), JOptionPane.YES_NO_OPTION);
            		if(!(confirmFormula == JOptionPane.YES_OPTION))
            			return;
        		}
        		//svonborries_26062007_end
        		ce.addEdit(CMModelEditFactory.INSTANCE.createChangeValueInTypeDataModelEdit(typeData, formulasEditController.getChild().getFormula()));
        		typeData.setValue(formulasEditController.getChild().getFormula());
        		
        		ce.addEdit(CMModelEditFactory.INSTANCE.createChangeGlobalReferenceInTypeDataModelEdit(typeData, ""));
        		typeData.setGlobal("");
        		
        		ce.addUndoDelegateEdit(new CMDelegate(){
        			public void execute() {
        				grid.update(CMIndexTDStructureUpdate.getInstance().getIndex());
        			}
        		});
        		
        		//set the viewmodel of the Comul Value, with the result of the formula
        		
        		grid.getCmGridModel().setValueAt(new CMCellTDStructureValue(grid,typeData), 
        				grid.getRowSelected(), grid.getColumnSelected()+1);
//        		set the viewmodel of the Comul Formula, with the toString of the formula
        		
        		grid.getCmGridModel().setValueAt(new CMCellTDStructureFormula(grid, typeData), 
        				grid.getRowSelected(), grid.getColumnSelected());
        		
        		}
        	else{
        		ce.addEdit(CMModelEditFactory.INSTANCE.createChangeValueInTypeDataModelEdit(typeData, formulasEditController.getChild().getFormula()));
        		typeData.setValue(formulasEditController.getChild().getFormula());
        		
        		TestDataFormat testDataFormat = new TestDataFormat();
        		String pattern = formulasEditController.getChild().getFormula().getFormulaEnum().getDefaultPattern();
        		if(pattern.indexOf("#")!= -1)
        			pattern = CMFormatFactory.updatePatternToCurrentTestObject(pattern);
        		
        		testDataFormat.setVisualFormatter(pattern);
        		testDataFormat.setOriginalFormatter(pattern);
        		testDataFormat.setRealFormat(pattern);
        		
        		ce.addEdit(CMModelEditFactory.INSTANCE.createChangeFormatterInTypeDataModelEdit(typeData, testDataFormat));
        		typeData.setFormatter(testDataFormat);
        		
        		ce.addEdit(CMModelEditFactory.INSTANCE.createChangeFormatInTypeDataModelEdit(typeData, pattern));
        		typeData.setFormat(pattern);
        		
        		ce.addEdit(CMTypeDataUtils.updateTypeDataValueForLocalTypeDatasWhenFormulaHasLinkElement(typeData));
        		
        		//if(!CMTypeDataUtils.hasLinkElementLocal((ICMFormula) typeData.getValue())){//svonborries_26062007_THIS METHOD IS IN OBSERVATION
        		if(!CMTypeDataUtils.hasLinkElementGlobal((ICMFormula) typeData.getValue())){
        			ce.addEdit(CMModelEditFactory.INSTANCE.createChangeGlobalReferenceInTypeDataModelEdit(typeData, ""));
        			typeData.setGlobal("");
        		}
        		
        		ce.addUndoDelegateEdit(new CMDelegate(){
        			public void execute() {
        				grid.update(CMIndexTDStructureUpdate.getInstance().getIndex());
        			}
        		});
        		
        		//set the viewmodel of the Comul Value, with the result of the formula
        		
        		grid.getCmGridModel().setValueAt(new CMCellTDStructureValue(grid,typeData), 
        				grid.getRowSelected(), grid.getColumnSelected()+1);
//        		set the viewmodel of the Comul Formula, with the toString of the formula
        		
        		grid.getCmGridModel().setValueAt(new CMCellTDStructureFormula(grid, typeData), 
        				grid.getRowSelected(), grid.getColumnSelected());
        		
        	}	
        	
        	if(ce.hasEdits())
            	CMUndoMediator.getInstance().doEdit(ce);
        }
    }
}
