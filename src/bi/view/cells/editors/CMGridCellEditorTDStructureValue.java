package bi.view.cells.editors;

import javax.swing.JOptionPane;
import javax.swing.JTextField;
import model.BusinessRules;
import model.CMDefaultValue;
import model.ITypeData;
import model.StructureTestData;
import model.TestDataFormat;
import model.edit.CMModelEditFactory;
import bi.controller.TDStructureManager;
import bi.view.cells.CMCellTDStructureClassState;
import bi.view.cells.CMCellTDStructureGlobal;
import bi.view.cells.CMCellTDStructureValue;
import bi.view.edit.CMViewEditFactory;
import bi.view.lang.CMMessages;
import bi.view.mainframeviews.CMApplication;
import bi.view.mainframeviews.CMFrameView;
import bi.view.testdataviews.CMGridTDStructure;
import bi.view.testdataviews.CMIndexTDStructureUpdate;
import bi.view.undomanagementviews.CMCompoundEdit;
import bi.view.undomanagementviews.undoviews.CMComponentAwareEdit;
import bi.view.undomanagementviews.undoviews.CMUndoMediator;
/**
 * @author ccastedo 
 * 	
 */
@SuppressWarnings("serial")
public class CMGridCellEditorTDStructureValue extends CMBaseGridCellEditor {

	
	
	public CMGridCellEditorTDStructureValue() {
		super(new JTextField(),null);
		
	}
	
	@SuppressWarnings("static-access")
	public boolean stopCellEditing() {
		super.stopCellEditing();
		if (getCell()!=null)
		{
			CMCompoundEdit ce = new CMCompoundEdit();
			ce.addEdit(new CMComponentAwareEdit());
			CMFrameView cmFrame = CMApplication.frame;
			boolean isCancelEditValue = false;
			boolean isTestDataPanelSelected = CMApplication.frame.isIsPanelTestDataSelected();
			
			CMGridTDStructure gridTDStructure = ((CMGridTDStructure) getCell().getGrid());
			
		  	int row =  gridTDStructure.getSelectionModel().getLeadRow();
			int column =  gridTDStructure.getSelectionModel().getLeadColumn();
			gridTDStructure.setRowSelected(row);
			gridTDStructure.setColumnSelected(column);
			
			
			StructureTestData std = (StructureTestData)gridTDStructure.getTDStructure().getM_StructureTestData().elementAt(CMIndexTDStructureUpdate.getInstance().getIndex()); 
	        ITypeData s = (ITypeData)std.getTypeData().elementAt(gridTDStructure.getCmGridModel().numOfCell(gridTDStructure.getSelectionModel().getLeadRow()));
	        
	        ce.addEdit(CMViewEditFactory.INSTANCE.createChangeIndexInCMIndexTDStructureUpdate(CMIndexTDStructureUpdate.getInstance(),CMIndexTDStructureUpdate.getInstance().getIndex()));
	    		    	
	    	Object value = ((JTextField)this.getComponent()).getText();
	    	
	    	if (isTestDataPanelSelected && (s.getGlobal().equals("G") || !s.getFormula().trim().equals("")) &&
	           
	        		!s.getFormattedValue().equals(value)) {
	                int confirmFormula = JOptionPane.YES_OPTION;
	                int confirmGlobal = JOptionPane.YES_OPTION;
	                
	                if (!s.getFormula().trim().equals("")) {
	                    confirmFormula = JOptionPane.showConfirmDialog(cmFrame, CMMessages.getString("FORMULA_INCONSISTENT_DELETE"),
	                        CMMessages.getString("TESTDATA_TITLE_ERROR"), JOptionPane.YES_NO_OPTION);
	                }
	                if (confirmFormula == JOptionPane.YES_OPTION && s.getGlobal().equals("G")) {
	                    confirmGlobal = JOptionPane.showConfirmDialog(cmFrame, CMMessages.getString("GLOBAL_REFERENCE_DELETE"),
	                        CMMessages.getString("TESTDATA_TITLE_ERROR"), JOptionPane.YES_NO_OPTION);
	                }
	                if (confirmFormula == JOptionPane.YES_OPTION && confirmGlobal == JOptionPane.YES_OPTION) {                   
	                	if (!s.getFormula().trim().equals("")) {
	                		gridTDStructure.setColumnSelected(gridTDStructure.getColumnSelected()-1);
	                		
	                		    gridTDStructure.setValueAt( new CMCellTDStructureValue(gridTDStructure, s), row, column);
	                		
	                        	TDStructureManager.deleteTypeDataReferenceInFormulas();
	                        	ce.addEdit(gridTDStructure.deleteAllTypeDataInTestDataToObserverVariable());

	                        	gridTDStructure.setFormulaEditValue("", (String)value ,
	                        			CMMessages.getString("TESTDATA_CHAR"), BusinessRules.FORMULAS_FORMAT_STRING, new TestDataFormat(),ce);

	                        	gridTDStructure.setColumnSelected(gridTDStructure.getColumnSelected()+1);
	                    }
	                    else {
	                        ITypeData oldTypeData = (ITypeData)s.clone();
	                        ITypeData newTypeData = (ITypeData)s.clone();
	                        ce.addEdit(CMModelEditFactory.INSTANCE.createChangeGlobalReferenceInTypeDataModelEdit(newTypeData,""));
	                        newTypeData.setGlobal("");
	                        
	                        CMDefaultValue defaultValue = new CMDefaultValue((String)value);
	                        ce.addEdit(CMModelEditFactory.INSTANCE.createChangeValueInTypeDataModelEdit(newTypeData,defaultValue));
	                        newTypeData.setValue(defaultValue);
	                       
	                        //////////////////////////////////////////////////////////////////
	                        ce.addEdit(CMModelEditFactory.INSTANCE.createChangeTypeInTypeDataModelEdit(newTypeData,CMMessages.getString("TESTDATA_CHAR")));
	                        newTypeData.setType(CMMessages.getString("TESTDATA_CHAR"));
	                       
	                        ce.addEdit(CMModelEditFactory.INSTANCE.createChangeToolVendorOTInTypeDataModelEdit(newTypeData,newTypeData.getStateOT()/*ccastedo 27.09.06 newTypeData.getToolVendorOT()*/));
	                       //ccastedo 26.09.06 newTypeData.setToolVendorOT(/*selectedItemOT.getDefaultValue(*/newTypeData.getToolVendorOT()/*)*/);
	                        newTypeData.setStateOT(newTypeData.getStateOT());
	                        ce.addEdit(CMModelEditFactory.INSTANCE.createChangeNewColumnsInTypeDataModelEdit(newTypeData,newTypeData.getNewColumns()));
	                        newTypeData.setNewColumns(newTypeData.getNewColumns());
	        
	                        ce.addEdit(CMModelEditFactory.INSTANCE.createChangeFormatInTypeDataModelEdit(newTypeData,BusinessRules.FORMULAS_FORMAT_STRING));
	                        newTypeData.setFormat(BusinessRules.FORMULAS_FORMAT_STRING);
	                        TestDataFormat format = new TestDataFormat();
	                        ce.addEdit(CMModelEditFactory.INSTANCE.createChangeFormatterInTypeDataModelEdit(newTypeData,format));
	                        newTypeData.setFormatter(format);
	                      
	                        ce.addEdit(CMModelEditFactory.INSTANCE.createChangeValueInTestDataFormatModelEdit(newTypeData.getFormatter(),newTypeData.getFormattedValue()));
	                        newTypeData.getFormatter().setValue(newTypeData.getFormattedValue());
	                        CMCellTDStructureClassState selectedItem =
	                            (CMCellTDStructureClassState)gridTDStructure.getCellObjectAt(row, column - 6);
	                        ce.addEdit(CMModelEditFactory.INSTANCE.createChangeLengthInTypeDataModelEdit(newTypeData,selectedItem.getDefaultValue(newTypeData.getTypeName())));
	                        newTypeData.setLength(selectedItem.getDefaultValue(newTypeData.getTypeName()));

	                        int indexTestData = CMIndexTDStructureUpdate.getInstance().getindexTestData();
	                        int indexStructure = CMIndexTDStructureUpdate.getInstance().getIndex();
	                        int numofTable = CMIndexTDStructureUpdate.getInstance().getnumOfTable();
	                        int numofRow = CMIndexTDStructureUpdate.getInstance().getnumOfRow();
	                        cmFrame.getM_CMUndoMediator().editInValidGlobalReferenceAndFormulainTestData(cmFrame.getPanelTestDataView(),
	                            cmFrame.getGridTDStructure(),
	                            indexTestData, indexStructure, oldTypeData, newTypeData, numofTable, numofRow, row, column,
	                            cmFrame.getContentTabbedPane(), CMMessages.getString("TESTDATA_TESTDATA"),ce);
	                       
	                    }
	                    CMDefaultValue defaultValue = new CMDefaultValue((String)value);
	                    ce.addEdit(CMModelEditFactory.INSTANCE.createChangeValueInTypeDataModelEdit(s,defaultValue));
	                    s.setValue(defaultValue);
	                  
	                    gridTDStructure.setValueAt(new CMCellTDStructureValue(gridTDStructure,s), row, column);
	                    ce.addEdit(CMModelEditFactory.INSTANCE.createChangeGlobalReferenceInTypeDataModelEdit(s,""));
	                    s.setGlobal("");  
	                    
	                   
	                    gridTDStructure.setValueAt(new CMCellTDStructureGlobal(gridTDStructure, s), row, 1);
	                    ce.addEdit(cmFrame.getGridTDStructure().ifcancelReferenceInTestDataCancelinStructure(std.getGlobalIndex(), row - 1));
	                }
	                else {
	                    
	                	gridTDStructure.setValueAt(new CMCellTDStructureValue(gridTDStructure,s), row, column);
	                }
	        }
	        else {
	        	
	        	if (!s.getFormula().trim().equals("") && !s.getFormattedValue().equals(value)) {
	        		
	        		int confirmFormula = JOptionPane.showConfirmDialog(cmFrame,CMMessages.getString("FORMULA_INCONSISTENT_DELETE"),
	                		CMMessages.getString("TESTDATA_TITLE_ERROR"),JOptionPane.YES_NO_OPTION);
	                if (confirmFormula == JOptionPane.YES_OPTION) {
	                	gridTDStructure.setColumnSelected(gridTDStructure.getColumnSelected()-1);
	                	                   	
	                	gridTDStructure.setValueAt(new CMCellTDStructureValue(gridTDStructure,s), row, column);
	                   
	                    ce.addEdit(TDStructureManager.deleteTypeDataReferenceInFormulas());
	                	ce.addEdit(gridTDStructure.deleteAllTypeDataInTestDataToObserverVariable());
	                	gridTDStructure.setFormula("", (String)value, CMMessages.getString("TESTDATA_CHAR"), BusinessRules.FORMULAS_FORMAT_STRING, new TestDataFormat(),ce); //$NON-NLS-1$
	                	gridTDStructure.setColumnSelected(gridTDStructure.getColumnSelected()+1);
	                	
	                }
	                else {
	                    isCancelEditValue = true;
	                      
	                    gridTDStructure.setValueAt(new CMCellTDStructureValue(gridTDStructure,s), row, column);                       
	                }
	            }
	            else {
	            	
	            	if(s.getFormattedValue().equalsIgnoreCase((String) value))
	            		return super.stopCellEditing();
	            	
	            	
	            	CMDefaultValue defaultValue = new CMDefaultValue((String)value);
	            	ce.addEdit(CMModelEditFactory.INSTANCE.createChangeValueInTypeDataModelEdit(s,defaultValue));
	                s.setValue(defaultValue);
	                gridTDStructure.setValueAt(new CMCellTDStructureValue(gridTDStructure,s), row, column);
	                	            	
	            }
	        }
	        if (!cmFrame.isIsPanelTestDataSelected() && !isCancelEditValue) {
	        	if(ce == null)
	        		ce = new CMCompoundEdit();
	            int indexGlobalStructure = std.getGlobalIndex();
	            int numofTypeData = gridTDStructure.getCmGridModel().numOfCell(row);
	        
	            Object editingObject = gridTDStructure.getCellObjectAt(row, column);
	            ce.addEdit(gridTDStructure.setReferenceValuesForGlobalStructure(gridTDStructure.getTDStructure().getTestDataCombination(),
	                editingObject, indexGlobalStructure, numofTypeData, value,null, row, column));
	           	            
	        }
	        
	        CMUndoMediator.getInstance().doEdit(ce);	        
	     
		}
		
			
		return super.stopCellEditing();
	}
	

}
