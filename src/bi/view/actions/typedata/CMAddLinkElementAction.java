/**
 * Developed by BUSINESS SOFTWARE INNOVATIONS.
 * Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved.
 */
package bi.view.actions.typedata;

import java.awt.Dimension;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JOptionPane;


import model.CMLinkElement;
import model.ICMValue;
import model.TestDataCombinations;
import model.TestDataFormat;
import model.edit.CMModelEditFactory;
import model.util.CMDelegate;

import bi.controller.testdata.CMTypeDataUtils;
import bi.controller.testdata.linkelement.CMLinkElementChooserGlobalLocalEditController;
import bi.controller.testdata.linkelement.CMLinkElementStructureEditController;
import bi.controller.testdata.linkelement.CMLinkElementTestDataEditController;
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
import bi.view.utils.CMModalResult;

/**
 * @author svonborries
 *
 */
@SuppressWarnings("serial")
public class CMAddLinkElementAction extends AbstractAction implements Action {

	private CMFrameView m_frame;
	private CMGridTDStructure m_gridTDStructure;

	public CMAddLinkElementAction(){

		super(CMMessages.getString("TESTDATA_ADD_LINKELEMENT"));
		putValue(Action.SHORT_DESCRIPTION, CMMessages.getString("TESTDATA_ADD_LINKELEMENT"));
	    putValue(Action.SMALL_ICON, CMAction.TESTDATA_ADD_LINK_ELEMENT.getIcon());
	    putValue(Action.MNEMONIC_KEY,(int)CMMessages.getString("TESTDATA_ADD_LINKELEMENT_MNENEMONIC").charAt(0));

	}

	public void actionPerformed(ActionEvent e) {
		CMCompoundEdit ce = new CMCompoundEdit();
		m_frame = CMApplication.frame;
		m_gridTDStructure = m_frame.getGridTDStructure();
		if(m_frame.isIsPanelTestDataSelected())
			m_gridTDStructure = m_frame.getPanelTestDataView().getM_CMGridTDStructure();
		
		ce.addUndoDelegateEdit(new CMDelegate(){
			public void execute() {
        		m_gridTDStructure.update(CMIndexTDStructureUpdate.getInstance().getIndex());
			}
		});
		ce.addEdit(new CMComponentAwareEdit());
		
		CMDefaultDialog dlg = new CMDefaultDialog();
		
		if(CMTypeDataUtils.checkIfTypeDataHasICMValue(m_gridTDStructure.getSelectedTypeData())){
			String messageConfirm = CMMessages.getString("TESTDATA_MESSAGE_CONFIRMATION_PART1")+" (" + CMTypeDataUtils.getICMValueNameoftheSelectedTypeData
			(m_gridTDStructure.getSelectedTypeData())   +") " + CMMessages.getString("TESTDATA_MESSAGE_CONFIRMATION_PART2") ; 
			int confirm = JOptionPane.showConfirmDialog(m_frame, messageConfirm, CMMessages.getString("TESTDATA_MESSAGE_TITLE_CONFIRMATION"), JOptionPane.YES_NO_OPTION);
			if(confirm == JOptionPane.NO_OPTION){
				return;
			}
		}
		if(m_frame.isIsPanelTestDataSelected()){
			CMLinkElementTestDataEditController testdataEditController = new CMLinkElementTestDataEditController();
			dlg.setJPanelContained(testdataEditController.getPanelLinkElementTestData());
			dlg.setSize(new Dimension(653, 364));
			dlg.setTitle(m_gridTDStructure.getSelectedTypeData().getField()+" - "+this.getValue(Action.NAME));
			dlg.setResizable(false);
			dlg.setVisible(true);
			boolean sw = true;
			while(sw){
			if(dlg.getModalResult() == CMModalResult.OK){

				CMLinkElement linkElement = new CMLinkElement(testdataEditController.getM_StringFormulaField());
				ICMValue tempValue = testdataEditController.getM_DestinationTypeData().getValue();
				TestDataFormat tdFormat = (TestDataFormat) linkElement.getTestDataFormat().clone();
				TestDataFormat tdFormatOld =  m_gridTDStructure.getSelectedTypeData().getFormatter();
				String oldFormat = m_gridTDStructure.getSelectedTypeData().getFormat();
				String newFormat = tdFormat.getOriginalFormatter();
				ce.addEdit(CMModelEditFactory.INSTANCE.createChangeValueInTypeDataModelEdit(testdataEditController.getM_DestinationTypeData(), linkElement));
				testdataEditController.getM_DestinationTypeData().setValue(linkElement);
				
				ce.addEdit(CMModelEditFactory.INSTANCE.createChangeFormatterInTypeDataModelEdit(testdataEditController.getM_DestinationTypeData(), tdFormat));
				testdataEditController.getM_DestinationTypeData().setFormatter(tdFormat);
				
				ce.addEdit(CMModelEditFactory.INSTANCE.createChangeFormatInTypeDataModelEdit(testdataEditController.getM_DestinationTypeData(), newFormat));
				testdataEditController.getM_DestinationTypeData().setFormat(newFormat);

					if(linkElement.existCircularReference(null)){
						testdataEditController.getM_DestinationTypeData().setValue(null);
						JOptionPane.showMessageDialog(m_frame, CMMessages.getString("TESTDATA_LINK_ELEMENTS_ERROR_CIRCULAR_REFERENCES"),
	                        CMMessages.getString("TESTDATA_IMPORT_TITLE_ERROR"), JOptionPane.ERROR_MESSAGE);
						ce.addEdit(CMModelEditFactory.INSTANCE.createChangeValueInTypeDataModelEdit(testdataEditController.getM_DestinationTypeData(), tempValue));
						testdataEditController.getM_DestinationTypeData().setValue(tempValue);
						ce.addEdit(CMModelEditFactory.INSTANCE.createChangeFormatterInTypeDataModelEdit(testdataEditController.getM_DestinationTypeData(), tdFormatOld));
						testdataEditController.getM_DestinationTypeData().setFormatter(tdFormatOld);
						
						ce.addEdit(CMModelEditFactory.INSTANCE.createChangeFormatInTypeDataModelEdit(testdataEditController.getM_DestinationTypeData(), oldFormat));
						testdataEditController.getM_DestinationTypeData().setFormat(oldFormat);
						dlg.setVisible(true);
					}
					else{
						int confirmFormula = JOptionPane.showConfirmDialog(m_frame, CMMessages.getString("GLOBAL_REFERENCE_DELETE"),
		                        CMMessages.getString("TESTDATA_TITLE_ERROR"), JOptionPane.YES_NO_OPTION);
		        		if(!(confirmFormula == JOptionPane.YES_OPTION)){
		        			ce.addEdit(CMModelEditFactory.INSTANCE.createChangeValueInTypeDataModelEdit(testdataEditController.getM_DestinationTypeData(), tempValue));
							testdataEditController.getM_DestinationTypeData().setValue(tempValue);
							ce.addEdit(CMModelEditFactory.INSTANCE.createChangeFormatterInTypeDataModelEdit(testdataEditController.getM_DestinationTypeData(), tdFormatOld));
							testdataEditController.getM_DestinationTypeData().setFormatter(tdFormatOld);
							
							ce.addEdit(CMModelEditFactory.INSTANCE.createChangeFormatInTypeDataModelEdit(testdataEditController.getM_DestinationTypeData(), oldFormat));
							testdataEditController.getM_DestinationTypeData().setFormat(oldFormat);
							return;
		        		}
		        		
		        		ce.addEdit(CMModelEditFactory.INSTANCE.createChangeGlobalReferenceInTypeDataModelEdit(testdataEditController.getM_DestinationTypeData(), ""));
		        		testdataEditController.getM_DestinationTypeData().setGlobal("");
		        			
						sw = false;
						m_gridTDStructure = m_frame.getPanelTestDataView().getM_CMGridTDStructure();
						//set the view model of the Colum Value
						
						m_gridTDStructure.getCmGridModel().setValueAt(new CMCellTDStructureValue(m_gridTDStructure,m_gridTDStructure.getSelectedTypeData()), m_gridTDStructure.getRowSelected(), 
								m_gridTDStructure.getColumnSelected()+1);
						//set the view model of the Column Formula
											
						m_gridTDStructure.getCmGridModel().setValueAt(new CMCellTDStructureFormula(m_gridTDStructure, m_gridTDStructure.getSelectedTypeData()), m_gridTDStructure.getRowSelected(), 
								m_gridTDStructure.getColumnSelected());
					}
					ce.addEdit(CMTypeDataUtils.updateTypeDataValueForLocalTypeDatas(testdataEditController.getM_DestinationTypeData()));
				}
			else if(dlg.getModalResult() == CMModalResult.CANCEL){
				dlg.dispose();
				sw = false;
			}
			}
		}
		else{
			CMLinkElementStructureEditController structureEditController = new CMLinkElementStructureEditController();
			dlg.setJPanelContained(structureEditController.getPanelLinkElementStructure());
			dlg.setSize(new Dimension(502,364));
			dlg.setTitle(m_gridTDStructure.getSelectedTypeData().getField()+" - "+this.getValue(Action.NAME));
			dlg.setResizable(false);
			dlg.setVisible(true);
			boolean sw = true;
			while(sw){
			if(dlg.getModalResult() == CMModalResult.OK){

					CMLinkElement linkElement = new CMLinkElement(structureEditController.getM_StringFormulaField());
					ICMValue tempValue = structureEditController.getM_DestinationTypeData().getValue();
					TestDataFormat tdFormat = (TestDataFormat) linkElement.getTestDataFormat().clone();
					TestDataFormat tdFormatOld = m_gridTDStructure.getSelectedTypeData().getFormatter();
					String oldFormat = m_gridTDStructure.getSelectedTypeData().getFormat();
					String newFormat = tdFormat.getOriginalFormatter();
					ce.addEdit(CMModelEditFactory.INSTANCE.createChangeValueInTypeDataModelEdit(structureEditController.getM_DestinationTypeData(), linkElement));
					structureEditController.getM_DestinationTypeData().setValue(linkElement);
					
					ce.addEdit(CMModelEditFactory.INSTANCE.createChangeFormatterInTypeDataModelEdit(structureEditController.getM_DestinationTypeData(), tdFormat));
					structureEditController.getM_DestinationTypeData().setFormatter(tdFormat);
					
					ce.addEdit(CMModelEditFactory.INSTANCE.createChangeFormatInTypeDataModelEdit(structureEditController.getM_DestinationTypeData(), newFormat));
					structureEditController.getM_DestinationTypeData().setFormat(newFormat);
					
					if(linkElement.existCircularReference(null)){
						//structureEditController.getM_DestinationTypeData().setValue(null);
						JOptionPane.showMessageDialog(m_frame, CMMessages.getString("TESTDATA_LINK_ELEMENTS_ERROR_CIRCULAR_REFERENCES"),
								CMMessages.getString("TESTDATA_IMPORT_TITLE_ERROR"), JOptionPane.ERROR_MESSAGE);
						ce.addEdit(CMModelEditFactory.INSTANCE.createChangeValueInTypeDataModelEdit(structureEditController.getM_DestinationTypeData(), tempValue));
						structureEditController.getM_DestinationTypeData().setValue(tempValue);
						ce.addEdit(CMModelEditFactory.INSTANCE.createChangeFormatterInTypeDataModelEdit(structureEditController.getM_DestinationTypeData(), tdFormatOld));
						structureEditController.getM_DestinationTypeData().setFormatter(tdFormatOld);
						
						ce.addEdit(CMModelEditFactory.INSTANCE.createChangeFormatInTypeDataModelEdit(structureEditController.getM_DestinationTypeData(), oldFormat));
						structureEditController.getM_DestinationTypeData().setFormat(oldFormat);
						dlg.setVisible(true);
					}
					else{
						if(canShowChooserDialog()){
							CMDefaultDialog dd = new CMDefaultDialog();
							CMLinkElementChooserGlobalLocalEditController controller = new CMLinkElementChooserGlobalLocalEditController();
							dd.setJPanelContained(controller.getPanelGlobalLocal());
							dd.setSize(280, 200);
							dd.setResizable(false);
							dd.setTitle(m_gridTDStructure.getSelectedTypeData().getField()+" - "+this.getValue(Action.NAME));
							dd.setVisible(true);
							if(dd.getModalResult() == CMModalResult.OK){
								if(controller.isGlobalSelected()){
									ce.addEdit(CMTypeDataUtils.updateTypeDataValueForLocalTypeDatas(structureEditController.getM_DestinationTypeData()));
								}
								else{
									int confirm = JOptionPane.OK_OPTION;
									confirm=JOptionPane.showConfirmDialog(m_frame,CMMessages.getString("LINK_ELEMENTS_VALUE_DIALOG_MESSAGE"),CMMessages.getString("LINK_ELEMENTS_VALUE_WARNING_TITLE"),JOptionPane.INFORMATION_MESSAGE,JOptionPane.YES_NO_CANCEL_OPTION);
									if(confirm == JOptionPane.YES_OPTION)
										ce.addEdit(CMTypeDataUtils.addLinkElementForLocalReferences(structureEditController.getM_DestinationTypeData()));
									else if(confirm == JOptionPane.NO_OPTION)
										ce.addEdit(CMTypeDataUtils.updateTypeDataValueForLocalTypeDatas(structureEditController.getM_DestinationTypeData()));
									else if(confirm == JOptionPane.CANCEL_OPTION){
										structureEditController.getM_DestinationTypeData().setValue(tempValue);
										structureEditController.getM_DestinationTypeData().setFormatter(tdFormatOld);
										structureEditController.getM_DestinationTypeData().setFormat(oldFormat);
										return;
									}
										
								}
							}
							else if (dd.getModalResult() == CMModalResult.CANCEL){
//								TODO agregar Compound pa guardar este cambio
								structureEditController.getM_DestinationTypeData().setValue(null);
								dd.dispose();
								dlg.dispose();
								return;
							}
						}
						sw = false;
						m_gridTDStructure = m_frame.getGridTDStructure();
						//set the view model of the Colum Value
						
						m_gridTDStructure.getCmGridModel().setValueAt(new CMCellTDStructureValue(m_gridTDStructure,m_gridTDStructure.getSelectedTypeData()), m_gridTDStructure.getRowSelected(), 
								m_gridTDStructure.getColumnSelected()+1);
						//set the view model of the Column Formula
						
						m_gridTDStructure.getCmGridModel().setValueAt(new CMCellTDStructureFormula(m_gridTDStructure, m_gridTDStructure.getSelectedTypeData()), m_gridTDStructure.getRowSelected(), 
						m_gridTDStructure.getColumnSelected());
					}
				}
			else if(dlg.getModalResult() == CMModalResult.CANCEL){
				dlg.dispose();
				sw = false;
			}
			}
		}
		
		ce.addUndoDelegateEdit(new CMDelegate(){
			public void execute() {
        		m_gridTDStructure.update(CMIndexTDStructureUpdate.getInstance().getIndex());
			}
		});
		
		m_gridTDStructure.update(CMIndexTDStructureUpdate.getInstance().getIndex());
		
		if(ce.hasEdits())
        	CMUndoMediator.getInstance().doEdit(ce);
		
		dlg.dispose();
	}
	
	private boolean canShowChooserDialog(){
		if(CMApplication.frame.getPanelTestDataView().getM_CMGridTDStructure().getTDStructure().getTestDataCombination() != null){
			TestDataCombinations combination = CMApplication.frame.getPanelTestDataView().getM_CMGridTDStructure().getTDStructure().getTestDataCombination();
			if(combination.getM_TestDatas() != null){
				return true;
			}
			else
				return false;
		}
		return false;
	}
}
