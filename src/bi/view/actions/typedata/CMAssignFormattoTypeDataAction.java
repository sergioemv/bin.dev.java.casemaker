/**
 * 08/02/2007
 * svonborries
 */
package bi.view.actions.typedata;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JOptionPane;

import model.ITypeData;
import model.TestDataFormat;
import model.edit.CMModelEditFactory;
import model.util.CMDelegate;

import bi.controller.StructureManager;
import bi.controller.testdata.CMTypeDataUtils;
import bi.controller.testdata.format.CMAssignFormatEditController;
import bi.controller.testdata.format.CMFormatUtils;
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
public class CMAssignFormattoTypeDataAction extends AbstractAction implements
		Action {
	
	private CMGridTDStructure gridTDStructure;
	private CMFrameView frame;
	
	public CMAssignFormattoTypeDataAction(){
		super(CMMessages.getString("TESTDATA_CONTEXMENU_FORMAT"));
		putValue(Action.SHORT_DESCRIPTION, CMMessages.getString("TESTDATA_CONTEXMENU_FORMAT"));
		putValue(Action.MNEMONIC_KEY,(int)CMMessages.getString("ASSIGN_FORMAT_MNEMONIC").charAt(0));
	}

	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 * @param e
	 * 08/02/2007
	 * svonborries
	 */
	public void actionPerformed(ActionEvent e) {
		frame = CMApplication.frame;
		gridTDStructure = frame.getGridTDStructure();
		
		assignFormatToTypeDataGlobal();
	}
	
	private void assignFormatToTypeDataGlobal(){
    	CMCompoundEdit ce = new CMCompoundEdit();
    	
    	frame = CMApplication.frame;
    	if(frame.isIsPanelTestDataSelected())
    		gridTDStructure = frame.getPanelTestDataView().getM_CMGridTDStructure();
    	else
    		gridTDStructure = frame.getGridTDStructure();
    	
    	ce.addUndoDelegateEdit(new CMDelegate(){
			public void execute() {
        		gridTDStructure.update(CMIndexTDStructureUpdate.getInstance().getIndex());
			}
		});
		ce.addEdit(new CMComponentAwareEdit());
    	
    	CMAssignFormatEditController formatEditController = new CMAssignFormatEditController();
    	CMDefaultDialog cmd = new CMDefaultDialog();
    	cmd.setJPanelContained(formatEditController.getPanelAssignFormat());
    	cmd.setSize(435, 464);
    	cmd.setTitle(gridTDStructure.getSelectedTypeData().getField() + " - " + CMMessages.getString("TESTDATA_CONTEXMENU_FORMAT"));
    	cmd.setResizable(false);
    	cmd.setVisible(true);
    	if(cmd.getModalResult() == CMModalResult.OK){
    		//create the object TestDataFormat, with the info extracted from the Dialog of Formats
    		TestDataFormat format = new TestDataFormat();
    		format.setVisualFormatter(formatEditController.getFormatResult());
    		format.setOriginalFormatter(formatEditController.getFormatResult());
    		format.setRealFormat(formatEditController.getFormatResult());
    		
    		//take the selected typedata that we want to add Format, then apply the format to the correspondent ICMValue
    		ITypeData typeData = gridTDStructure.getSelectedTypeData();
    		CMFormatFactory.applyFormatToICMValue(typeData.getValue(), format);
    		
    		if(!CMApplication.frame.isIsPanelTestDataSelected()){
//    			check if the format is appyable to the TypeData 
        		if(CMFormatFactory.isSuccessFormated()){
        			int confirmation = JOptionPane.showConfirmDialog(CMApplication.frame, CMMessages.getString("QUESTION_TESTDATA_ASSIGN_FORMAT"),
                            CMMessages.getString("TESTDATASETREPORTS_TITLE_INFORMATION"), JOptionPane.YES_NO_OPTION);
        			//if the customer want to apply this format to all the locals
        			if(confirmation == JOptionPane.YES_OPTION){
        				ce.addEdit(CMModelEditFactory.INSTANCE.createChangeFormatterInTypeDataModelEdit(typeData, format));
                		typeData.setFormatter(format);
                		ce.addEdit(CMModelEditFactory.INSTANCE.createChangeFormatInTypeDataModelEdit(typeData, formatEditController.getFormatResult()));
                		typeData.setFormat(formatEditController.getFormatResult());
                		
                		//update all the formats in the testdata node
                		ce.addEdit(CMTypeDataUtils.updateTypeDataFormatForLocalTypeDatas(typeData));
                		
//                		int row = CMIndexTDStructureUpdate.getInstance().getnumOfRow();
//                		String absoluteTestObjectPath = StructureManager.getSelectedTestObjectPath();
//                		CMFormatUtils.openReportFormatError(gridTDStructure.getTDStructure().getTestDataCombination(),
//                				typeData.getStructureTestData().getGlobalIndex(), row, format, absoluteTestObjectPath);
        			}
        			else if(confirmation == JOptionPane.NO_OPTION){
        				ce.addEdit(CMModelEditFactory.INSTANCE.createChangeFormatterInTypeDataModelEdit(typeData, format));
                		typeData.setFormatter(format);
                		ce.addEdit(CMModelEditFactory.INSTANCE.createChangeFormatInTypeDataModelEdit(typeData, formatEditController.getFormatResult()));
                		typeData.setFormat(formatEditController.getFormatResult());
        			}
        			else if(confirmation == JOptionPane.CANCEL_OPTION){
        				return;
        			}
        			
        		}
        		else{
        			int confirmation = JOptionPane.showConfirmDialog(CMApplication.frame, CMMessages.getString("QUESTION_TESTDATA_ASSIGN_FORMAT"),
                            CMMessages.getString("TESTDATA_ASSIGN_FORMAT_TITLE_ERROR"), JOptionPane.YES_NO_OPTION);
        			if(confirmation == JOptionPane.YES_OPTION){
        				ce.addEdit(CMModelEditFactory.INSTANCE.createChangeFormatterInTypeDataModelEdit(typeData, format));
                		typeData.setFormatter(format);
                		ce.addEdit(CMModelEditFactory.INSTANCE.createChangeFormatInTypeDataModelEdit(typeData, formatEditController.getFormatResult()));
                		typeData.setFormat(formatEditController.getFormatResult());
                		
//                		update all the formats in the testdata node
                		ce.addEdit(CMTypeDataUtils.updateTypeDataFormatForLocalTypeDatas(typeData));
                		
                		int row = CMIndexTDStructureUpdate.getInstance().getnumOfRow();
                		String absoluteTestObjectPath = StructureManager.getSelectedTestObjectPath();
                		CMFormatUtils.openReportFormatError(gridTDStructure.getTDStructure().getTestDataCombination(),
                				typeData.getStructureTestData().getGlobalIndex(), row, format, absoluteTestObjectPath);
        			}
        			
        			else if(confirmation == JOptionPane.NO_OPTION){
        				ce.addEdit(CMModelEditFactory.INSTANCE.createChangeFormatterInTypeDataModelEdit(typeData, format));
                		typeData.setFormatter(format);
                		ce.addEdit(CMModelEditFactory.INSTANCE.createChangeFormatInTypeDataModelEdit(typeData, formatEditController.getFormatResult()));
                		typeData.setFormat(formatEditController.getFormatResult());
        			}
        			else if(confirmation == JOptionPane.CANCEL_OPTION){
        				return;
        			}
        		}
    		}
    		else{
    			ce.addEdit(CMModelEditFactory.INSTANCE.createChangeFormatterInTypeDataModelEdit(typeData, format));
        		typeData.setFormatter(format);
        		ce.addEdit(CMModelEditFactory.INSTANCE.createChangeFormatInTypeDataModelEdit(typeData, formatEditController.getFormatResult()));
        		typeData.setFormat(formatEditController.getFormatResult());
    		}
    		
//    		//check if the format is appyable to the TypeData 
//    		if(CMFormatFactory.isSuccessFormated()){
//    			int confirmation = JOptionPane.showConfirmDialog(CMApplication.frame, CMMessages.getString("QUESTION_TESTDATA_ASSIGN_FORMAT"),
//                        CMMessages.getString("TESTDATASETREPORTS_TITLE_INFORMATION"), JOptionPane.YES_NO_OPTION);
//    			//if the customer want to apply this format to all the locals
//    			if(confirmation == JOptionPane.YES_OPTION){
//    				ce.addEdit(CMModelEditFactory.INSTANCE.createChangeFormatterInTypeDataModelEdit(typeData, format));
//            		typeData.setFormatter(format);
//            		ce.addEdit(CMModelEditFactory.INSTANCE.createChangeFormatInTypeDataModelEdit(typeData, formatEditController.getFormatResult()));
//            		typeData.setFormat(formatEditController.getFormatResult());
//            		
//            		//update all the formats in the testdata node
//            		ce.addEdit(CMTypeDataUtils.updateTypeDataFormatForLocalTypeDatas(typeData));
//            		
//            		int row = CMIndexTDStructureUpdate.getInstance().getnumOfRow();
//            		String absoluteTestObjectPath = StructureManager.getSelectedTestObjectPath();
//            		CMFormatUtils.openReportFormatError(gridTDStructure.getTDStructure().getTestDataCombination(),
//            				typeData.getStructureTestData().getGlobalIndex(), row, format, absoluteTestObjectPath);
//    			}
//    			else if(confirmation == JOptionPane.NO_OPTION){
//    				ce.addEdit(CMModelEditFactory.INSTANCE.createChangeFormatterInTypeDataModelEdit(typeData, format));
//            		typeData.setFormatter(format);
//            		ce.addEdit(CMModelEditFactory.INSTANCE.createChangeFormatInTypeDataModelEdit(typeData, formatEditController.getFormatResult()));
//            		typeData.setFormat(formatEditController.getFormatResult());
//    			}
//    			else if(confirmation == JOptionPane.CANCEL_OPTION){
//    				return;
//    			}
//    			
//    		}
//    		else{
//    			int confirmation = JOptionPane.showConfirmDialog(CMApplication.frame, CMMessages.getString("QUESTION_TESTDATA_ASSIGN_FORMAT"),
//                        CMMessages.getString("TESTDATA_ASSIGN_FORMAT_TITLE_ERROR"), JOptionPane.YES_NO_OPTION);
//    			if(confirmation == JOptionPane.YES_OPTION){
//    				ce.addEdit(CMModelEditFactory.INSTANCE.createChangeFormatterInTypeDataModelEdit(typeData, format));
//            		typeData.setFormatter(format);
//            		ce.addEdit(CMModelEditFactory.INSTANCE.createChangeFormatInTypeDataModelEdit(typeData, formatEditController.getFormatResult()));
//            		typeData.setFormat(formatEditController.getFormatResult());
////            		int row = CMIndexTDStructureUpdate.getInstance().getnumOfRow();
////            		String absoluteTestObjectPath = StructureManager.getSelectedTestObjectPath();
////            		CMFormatUtils.openReportFormatError(gridTDStructure.getTDStructure().getTestDataCombination(),
////            				typeData.getStructureTestData().getGlobalIndex(), row, format, absoluteTestObjectPath);
//    			}
//    		}
    		
    		
    		ce.addUndoDelegateEdit(new CMDelegate(){
    			public void execute() {
    				gridTDStructure.update(CMIndexTDStructureUpdate.getInstance().getIndex());
    			}
    		});
    		//ce.addEdit(CMTypeDataUtils.updateTypeDataValueForLocalTypeDatas(typeData));
    	}
    	if(ce.hasEdits())
        	CMUndoMediator.getInstance().doEdit(ce);
//    	int table = CMIndexTDStructureUpdate.getInstance().getIndex();
//    	int row = CMIndexTDStructureUpdate.getInstance().getnumOfRow();
//    	int col = CMIndexTDStructureUpdate.getInstance().getnumOfCol();
//		StructureTestData std =(StructureTestData)gridTDStructure.getTDStructure().getM_StructureTestData().elementAt(table);
//        int globalIndex= std.getGlobalIndex();
//        ITypeData typ=(ITypeData)std.getTypeData().elementAt(row);
//    CMDialogFormat cmd = new CMDialogFormat(frame,typ.getM_Formatter());
//    cmd.setVisible(true);/
//    if(cmd.buttonOkclicked)
//    {
//    	boolean applyFormats=gridTDStructure.isPosibleApplyFormatInAllTestData(gridTDStructure.getTDStructure().getTestDataCombination(),globalIndex,row,cmd.getFormatterResult());
//    	if(applyFormats){
//    		String valueTemp = "";
//    		try {
//				valueTemp = typ.getValue().getValue().toString();
//			} catch (Exception e1) {
//				e1.printStackTrace();
//			}
//			String valueresult=CMFormatFactory.getInstance().applyAnyFormat(cmd.getFormatterResult(),valueTemp,typ.getM_Formatter());
//    		//String valueresult=CMFormatFactory.getInstance().applyAnyFormat(cmd.getFormatterResult(),typ.getStringValue(),typ.getM_Formatter());
//    		int rowofgrid=CMIndexTDStructureUpdate.getInstance().getRowOfGrid();
//    		ce.addEdit(CMModelEditFactory.INSTANCE.createChangeFormatInTypeDataModelEdit(typ,cmd.getFormatResult()));
//    		typ.setFormat(cmd.getFormatResult());
//    		ce.addEdit(CMModelEditFactory.INSTANCE.createChangeFormatterInTypeDataModelEdit(typ,cmd.getFormatterResult()));
//    		typ.setM_Formatter(cmd.getFormatterResult());
//    		CMDefaultValue defaultValue = new CMDefaultValue(valueresult);
//    		ce.addEdit(CMModelEditFactory.INSTANCE.createChangeValueInTypeDataModelEdit(typ,defaultValue));
//    		typ.setValue(defaultValue);
//    		//typ.setStringValue(valueresult);
//    		ProjectReference projectReference = frame.getTreeWorkspaceView().getCurrentProjectReference();
//    	       TestObjectReference testObjectReference = frame.getTreeWorkspaceView().getCurrentTestObjectReference();
//    	       TestObject testObject= frame.getTreeWorkspaceView().getCurrentTestObject();
//
//    		   if( frame.getCmApplication().getSessionManager().getM_WorkspaceManager().isTestObjectCheckedOutLocalByTheSameUser(testObject, frame.getTreeWorkspaceView().getM_Session2()) ) {
//    		     projectReference = projectReference.getM_LocalProjectReference();
//    	  	   }
//    	       String absoluteTestObjectPath = frame.getCmApplication().getSessionManager().getM_WorkspaceManager().buildAbsoluteTestObjectPath(projectReference, testObjectReference);
//    	       ce.addEdit(gridTDStructure.applyFormatInAllTestData(gridTDStructure.getTDStructure().getTestDataCombination(),globalIndex,row,cmd.getFormatterResult(), absoluteTestObjectPath));
//    	       gridTDStructure.setUndoRedoFormatChange(rowofgrid,col,ce);
//    	}
//    	gridTDStructure.update(table);
//    }
	}

}
