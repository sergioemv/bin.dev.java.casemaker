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

import model.CMLinkElement;
import model.ITypeData;
import model.Project2;
import model.StructureTestData;
import model.TestData;
import model.TestObject;
import model.Variable;
import model.Workspace2;
import model.edit.CMModelEditFactory;
import model.util.CMDelegate;


import bi.controller.testdata.CMTypeDataUtils;
import bi.controller.testdata.variable.CMVariableEditController;
import bi.view.actions.CMAction;
import bi.view.cells.CMCellTDStructureClassState;

import bi.view.cells.CMCellTDStructureFormula;

import bi.view.cells.CMCellTDStructureValue;
import bi.view.lang.CMMessages;
import bi.view.mainframeviews.CMApplication;

import bi.view.testdataviews.CMGridTDStructure;
import bi.view.testdataviews.CMIndexTDStructureUpdate;


import bi.view.undomanagementviews.CMCompoundEdit;
import bi.view.undomanagementviews.undoviews.CMComponentAwareEdit;
import bi.view.undomanagementviews.undoviews.CMUndoMediator;
import bi.view.utils.CMDefaultDialog;
import bi.view.utils.CMModalResult;

/**
 * @author svonborries
 *
 */
@SuppressWarnings("serial")
public class CMAddVariableAction extends AbstractAction implements Action {

	private CMGridTDStructure m_GridTDStructure;

	public CMAddVariableAction(){

		super(CMMessages.getString("TESTDATA_ADD_VARIABLE"));
		putValue(Action.SHORT_DESCRIPTION, CMMessages.getString("TESTDATA_ADD_VARIABLE"));
	    putValue(Action.SMALL_ICON, CMAction.TESTDATA_ADD_VARIABLE.getIcon());
	    putValue(Action.MNEMONIC_KEY,(int)CMMessages.getString("ADD_VARIABLE_MNEMONIC").charAt(0));
	    putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_V,Event.CTRL_MASK));

	}

	public void actionPerformed(ActionEvent e) {
		
		CMCompoundEdit ce = new CMCompoundEdit();
		
		
		if(CMApplication.frame.isIsPanelTestDataSelected()){
			m_GridTDStructure = CMApplication.frame.getPanelTestDataView().getM_CMGridTDStructure();
		}
		else{
			m_GridTDStructure = CMApplication.frame.getGridTDStructure();
		}
		
		ce.addUndoDelegateEdit(new CMDelegate(){
			public void execute() {
        		m_GridTDStructure.update(CMIndexTDStructureUpdate.getInstance().getIndex());
			}
		});
		ce.addEdit(new CMComponentAwareEdit());
		
    	if(CMTypeDataUtils.checkIfTypeDataHasICMValue(m_GridTDStructure.getSelectedTypeData())){
			String messageConfirm = CMMessages.getString("TESTDATA_MESSAGE_CONFIRMATION_PART1")+" (" + CMTypeDataUtils.getICMValueNameoftheSelectedTypeData
			(m_GridTDStructure.getSelectedTypeData())   +") " + CMMessages.getString("TESTDATA_MESSAGE_CONFIRMATION_PART2") ; 
			int confirm = JOptionPane.showConfirmDialog(CMApplication.frame, messageConfirm, CMMessages.getString("TESTDATA_MESSAGE_TITLE_CONFIRMATION"), JOptionPane.YES_NO_OPTION);
			if(confirm == JOptionPane.NO_OPTION){
				return;
			}
		}
		
		CMVariableEditController variableController = new CMVariableEditController();
		CMDefaultDialog dd = new CMDefaultDialog();
		dd.setJPanelContained(variableController.getPanelVariable());
		dd.setSize(491,394);
		dd.setTitle(m_GridTDStructure.getSelectedTypeData().getField()+" - "+this.getValue(Action.NAME));
		
		dd.setResizable(false);
		dd.setModal(true);
		dd.setVisible(true);
		if(dd.getModalResult() == CMModalResult.OK){
			CMLinkElement linkElement = (CMLinkElement) variableController.getLinkElement();
			ITypeData typeData = null;
			typeData = m_GridTDStructure.getSelectedTypeData();
			
			if(CMApplication.frame.isIsPanelTestDataSelected()){
				int confirmFormula = JOptionPane.showConfirmDialog(CMApplication.frame, CMMessages.getString("GLOBAL_REFERENCE_DELETE"),
	                    CMMessages.getString("TESTDATA_TITLE_ERROR"), JOptionPane.YES_NO_OPTION);
	    		if(!(confirmFormula == JOptionPane.YES_OPTION))
	    			return;
	    		else {
	    			ce.addEdit(CMModelEditFactory.INSTANCE.createChangeGlobalReferenceInTypeDataModelEdit(typeData, ""));
	    			typeData.setGlobal("");
	    		}
			}
			
			ce.addEdit(CMModelEditFactory.INSTANCE.createChangeValueInTypeDataModelEdit(typeData, linkElement));
			typeData.setValue(linkElement);
			
			m_GridTDStructure.getCmGridModel().setValueAt(new CMCellTDStructureValue(m_GridTDStructure, typeData), 
    				m_GridTDStructure.getRowSelected(), m_GridTDStructure.getColumnSelected()+1);
//    		set the viewmodel of the Comul Formula, with the toString of the formula
						
    		m_GridTDStructure.getCmGridModel().setValueAt(new CMCellTDStructureFormula(m_GridTDStructure, typeData), 
    				m_GridTDStructure.getRowSelected(), m_GridTDStructure.getColumnSelected());
    		if(linkElement.getObjectLinked() instanceof Variable){
    			Variable variable = (Variable) linkElement.getObjectLinked();
    			ce.addEdit(CMModelEditFactory.INSTANCE.createChangeFormatInTypeDataModelEdit(typeData, variable.getFormat()));
    			typeData.setFormat(variable.getFormat());
    			ce.addEdit(CMModelEditFactory.INSTANCE.createChangeFormatterInTypeDataModelEdit(typeData, variable.getFormatter()));
    			typeData.setFormatter(variable.getFormatter());
    			ce.addEdit(CMModelEditFactory.INSTANCE.createChangeTypeInTypeDataModelEdit(typeData, variable.getM_Type()));
    			typeData.setType(variable.getM_Type());
    			ce.addEdit(CMModelEditFactory.INSTANCE.createChangeLengthInTypeDataModelEdit(typeData, CMCellTDStructureClassState.getDefaultValue(typeData.getTypeName())));
    			typeData.setLength(CMCellTDStructureClassState.getDefaultValue(typeData.getTypeName()));

    			if(!CMApplication.frame.isIsPanelTestDataSelected())
    				ce.addEdit(CMTypeDataUtils.updateTypeDataValueForLocalTypeDatas(typeData));
    		}
    		else if( linkElement.getObjectLinked() instanceof Project2 || linkElement.getObjectLinked() instanceof Workspace2
    				|| linkElement.getObjectLinked() instanceof TestObject || linkElement.getObjectLinked() instanceof TestData
    				|| linkElement.getObjectLinked() instanceof StructureTestData){
//refresh the Cell changed in the Grid
    			
    			
    			if(!CMApplication.frame.isIsPanelTestDataSelected())
    				ce.addEdit(CMTypeDataUtils.updateTypeDataValueForLocalTypeDatas(typeData));
    		}
    		
    		ce.addUndoDelegateEdit(new CMDelegate(){
    			public void execute() {
            		m_GridTDStructure.update(CMIndexTDStructureUpdate.getInstance().getIndex());
    			}
    		});
    		
    		
    		m_GridTDStructure.update(CMIndexTDStructureUpdate.getInstance().getIndex());
    			
    		if(ce.hasEdits())
            	CMUndoMediator.getInstance().doEdit(ce);
		}
//		this.m_frame = CMApplication.frame;
//		this.m_GridTDStructure = m_frame.getGridTDStructure();
//		this.m_panelTestData = m_frame.getPanelTestDataView();
//
//        CMDialogAddVariable cmd;
//        if (m_frame.isIsPanelTestDataSelected()) {
//            cmd = new CMDialogAddVariable(m_frame, m_panelTestData.getM_CMTDStructure());
//        }
//        else {
//            cmd = new CMDialogAddVariable(m_frame, this.m_GridTDStructure);
//        }
//        //cmd.setM_Project(this.getTreeWorkspaceView().getCurrentProject());//this.gridTDStructure.getTDStructure().getM_TestObjectReference().getM_Project());
//        cmd.charge();
//        cmd.setVisible(true);

	}

}
