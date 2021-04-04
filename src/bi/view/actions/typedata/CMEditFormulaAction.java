/**
 * Developed by BUSINESS SOFTWARE INNOVATIONS.
 * Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved.
 */
package bi.view.actions.typedata;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.Action;

import model.ICMFormula;
import model.ITypeData;
import model.TestDataFormat;
import model.edit.CMModelEditFactory;
import model.util.CMDelegate;

import bi.controller.testdata.formula.CMFormulasEditController;
import bi.view.actions.CMAction;
import bi.view.lang.CMMessages;
import bi.view.mainframeviews.CMApplication;
import bi.view.mainframeviews.CMFrameView;

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
public class CMEditFormulaAction extends AbstractAction implements Action {

	private CMFrameView m_frame = null;
	private CMGridTDStructure m_gridTDStructure = null;

	public CMEditFormulaAction(){
		super(CMMessages.getString("TESTDATA_EDIT_FORMULA"));
		putValue(Action.SHORT_DESCRIPTION, CMMessages.getString("BUTTON_TOOLTIP_TESTDATA_EDIT_FORMULA"));
	    putValue(Action.SMALL_ICON, CMAction.TESTDATA_EDIT_FORMULA.getIcon());
	    putValue(Action.MNEMONIC_KEY,(int)CMMessages.getString("EDIT_FORMULA_MNEMONIC").charAt(0));
	}
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent e) {

		this.m_frame = CMApplication.frame;

        if (this.m_frame.isIsPanelTestDataSelected()) {
			m_gridTDStructure = m_frame.getPanelTestDataView().getM_CMGridTDStructure();
			editFormula();
        }
        else {
        	m_gridTDStructure = m_frame.getGridTDStructure();
			editFormula();
        }
	}

    private void editFormula() {
    	CMCompoundEdit ce = new CMCompoundEdit();
    	
    	final ITypeData selectedTypeData = m_gridTDStructure.getSelectedTypeData();
    	
    	ce.addUndoDelegateEdit(new CMDelegate(){
			public void execute() {
//				m_gridTDStructure.getCmGridModel().setValueAt(new CMCellTDStructureValue(m_gridTDStructure, selectedTypeData), 
//        				m_gridTDStructure.getRowSelected(), m_gridTDStructure.getColumnSelected()+1);
////        		set the viewmodel of the Comul Formula, with the toString of the formula
//        		
//        		m_gridTDStructure.getCmGridModel().setValueAt(new CMCellTDStructureFormula(m_gridTDStructure, selectedTypeData), 
//        				m_gridTDStructure.getRowSelected(), m_gridTDStructure.getColumnSelected());
				m_gridTDStructure.update(CMIndexTDStructureUpdate.getInstance().getIndex());
			}
		});
		ce.addEdit(new CMComponentAwareEdit());
    	
    	
    	
    	
    	CMDefaultDialog cm = new CMDefaultDialog();
    	ICMFormula toCompare = (ICMFormula) ((ICMFormula) selectedTypeData.getValue()).clone();
    	CMFormulasEditController formulasEditController = new CMFormulasEditController((ICMFormula) selectedTypeData.getValue());
    	cm.setJPanelContained(formulasEditController.getPanelFormulas());
    	cm.setTitle(m_gridTDStructure.getSelectedTypeData().getField()+" - "+this.getValue(Action.NAME));
        cm.setSize(new java.awt.Dimension(655,560));
        cm.setResizable(false);
        cm.setVisible(true);
        if(cm.getModalResult() == CMModalResult.OK){
        	ICMFormula formula = formulasEditController.getChild().getFormula();
        	if(!formula.equals(toCompare)){
        		selectedTypeData.setValue(toCompare);
        		ce.addEdit(CMModelEditFactory.INSTANCE.createChangeValueInTypeDataModelEdit(selectedTypeData, formula));
        		selectedTypeData.setValue(formula);
        		//svonborries_28062007_begin
        		TestDataFormat tdFormat = new TestDataFormat();
        		tdFormat.setOriginalFormatter(formula.getFormulaEnum().getDefaultPattern());
        		tdFormat.setRealFormat(formula.getFormulaEnum().getDefaultPattern());
        		tdFormat.setVisualFormatter(formula.getFormulaEnum().getDefaultPattern());
        		ce.addEdit(CMModelEditFactory.INSTANCE.createChangeFormatterInTypeDataModelEdit(selectedTypeData, tdFormat));
        		selectedTypeData.setFormatter(tdFormat);
        		ce.addEdit(CMModelEditFactory.INSTANCE.createChangeFormatInTypeDataModelEdit(selectedTypeData, formula.getFormulaEnum().getDefaultPattern()));
        		selectedTypeData.setFormat(formula.getFormulaEnum().getDefaultPattern());
        		//svonborries_28062007_end
        		
        		ce.addUndoDelegateEdit(new CMDelegate(){
        			public void execute() {
//        				m_gridTDStructure.getCmGridModel().setValueAt(new CMCellTDStructureValue(m_gridTDStructure, selectedTypeData), 
//                				m_gridTDStructure.getRowSelected(), m_gridTDStructure.getColumnSelected()+1);
////                		set the viewmodel of the Comul Formula, with the toString of the formula
//                		
//                		m_gridTDStructure.getCmGridModel().setValueAt(new CMCellTDStructureFormula(m_gridTDStructure, selectedTypeData), 
//                				m_gridTDStructure.getRowSelected(), m_gridTDStructure.getColumnSelected());
        				m_gridTDStructure.update(CMIndexTDStructureUpdate.getInstance().getIndex());
        			}
        		});
        		
//        		m_gridTDStructure.getCmGridModel().setValueAt(new CMCellTDStructureValue(m_gridTDStructure, selectedTypeData), 
//        				m_gridTDStructure.getRowSelected(), m_gridTDStructure.getColumnSelected()+1);
////        		set the viewmodel of the Comul Formula, with the toString of the formula
//        		
//        		m_gridTDStructure.getCmGridModel().setValueAt(new CMCellTDStructureFormula(m_gridTDStructure, selectedTypeData), 
//        				m_gridTDStructure.getRowSelected(), m_gridTDStructure.getColumnSelected());
        		m_gridTDStructure.update(CMIndexTDStructureUpdate.getInstance().getIndex());
        	}
        	else{
        		ce.addEdit(CMModelEditFactory.INSTANCE.createChangeValueInTypeDataModelEdit(selectedTypeData, toCompare));
        		selectedTypeData.setValue(toCompare);
//        		svonborries_28062007_begin
        		TestDataFormat tdFormat = new TestDataFormat();
        		tdFormat.setOriginalFormatter(toCompare.getFormulaEnum().getDefaultPattern());
        		tdFormat.setRealFormat(toCompare.getFormulaEnum().getDefaultPattern());
        		tdFormat.setVisualFormatter(toCompare.getFormulaEnum().getDefaultPattern());
        		ce.addEdit(CMModelEditFactory.INSTANCE.createChangeFormatterInTypeDataModelEdit(selectedTypeData, tdFormat));
        		selectedTypeData.setFormatter(tdFormat);
        		ce.addEdit(CMModelEditFactory.INSTANCE.createChangeFormatInTypeDataModelEdit(selectedTypeData, toCompare.getFormulaEnum().getDefaultPattern()));
        		selectedTypeData.setFormat(toCompare.getFormulaEnum().getDefaultPattern());
        		//svonborries_28062007_end
        		
        		ce.addUndoDelegateEdit(new CMDelegate(){
        			public void execute() {
//        				m_gridTDStructure.getCmGridModel().setValueAt(new CMCellTDStructureValue(m_gridTDStructure, selectedTypeData), 
//                				m_gridTDStructure.getRowSelected(), m_gridTDStructure.getColumnSelected()+1);
////                		set the viewmodel of the Comul Formula, with the toString of the formula
//                		
//                		m_gridTDStructure.getCmGridModel().setValueAt(new CMCellTDStructureFormula(m_gridTDStructure, selectedTypeData), 
//                				m_gridTDStructure.getRowSelected(), m_gridTDStructure.getColumnSelected());
        				m_gridTDStructure.update(CMIndexTDStructureUpdate.getInstance().getIndex());
        			}
        		});
        		
//        		m_gridTDStructure.getCmGridModel().setValueAt(new CMCellTDStructureValue(m_gridTDStructure, selectedTypeData), 
//        				m_gridTDStructure.getRowSelected(), m_gridTDStructure.getColumnSelected()+1);
////        		set the viewmodel of the Comul Formula, with the toString of the formula
//        		
//        		m_gridTDStructure.getCmGridModel().setValueAt(new CMCellTDStructureFormula(m_gridTDStructure, selectedTypeData), 
//        				m_gridTDStructure.getRowSelected(), m_gridTDStructure.getColumnSelected());
        		m_gridTDStructure.update(CMIndexTDStructureUpdate.getInstance().getIndex());
        	}
        }
        else{
    		selectedTypeData.setValue(toCompare);
    		
//    		m_gridTDStructure.getCmGridModel().setValueAt(new CMCellTDStructureValue(m_gridTDStructure, selectedTypeData), 
//    				m_gridTDStructure.getRowSelected(), m_gridTDStructure.getColumnSelected()+1);
////    		set the viewmodel of the Comul Formula, with the toString of the formula
//    		
//    		m_gridTDStructure.getCmGridModel().setValueAt(new CMCellTDStructureFormula(m_gridTDStructure, selectedTypeData), 
//    				m_gridTDStructure.getRowSelected(), m_gridTDStructure.getColumnSelected());
    		m_gridTDStructure.update(CMIndexTDStructureUpdate.getInstance().getIndex());
        }
        
        if(ce.hasEdits())
        	CMUndoMediator.getInstance().doEdit(ce);
        
        
/*        String editFormula = "";
        editFormula.trim();
        StructureTestData stdaux = (StructureTestData)this.m_gridTDStructure.getTDStructure().
        getM_StructureTestData().elementAt(CMIndexTDStructureUpdate.getInstance().getIndex());
        ITypeData s = (ITypeData)stdaux.getTypeData().elementAt(this.m_gridTDStructure.getCmGridModel().
        		numOfCell(this.m_gridTDStructure.getRowSelected()));
        //editFormula = s.getStringFormula();
        editFormula = s.getFormula();
        if (!editFormula.equals("") && s.isFormula()) {
            CMDialogFormulasValues cmd = new CMDialogFormulasValues(m_frame, editFormula, "",this.m_gridTDStructure,
            		s.getM_Formatter(), true);
            cmd.setTypeDataReferences(s.getM_Subjects());
    		cmd.setTypeDataRefered(s);
            if (cmd.cantParam(editFormula) == 0) {
                cmd.inCaseNotParam();
            }
            else {
                cmd.setVisible(true);
            }
        }*/
    }
}
