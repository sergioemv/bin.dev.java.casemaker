package bi.view.actions.typedata;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JOptionPane;
import model.ITypeData;
import model.StructureTestData;
import model.TestData;
import model.edit.CMModelEditFactory;
import model.util.CMDelegate;
import bi.view.actions.CMAction;
import bi.view.lang.CMMessages;
import bi.view.mainframeviews.CMApplication;
import bi.view.mainframeviews.CMFrameView;
import bi.view.testdataviews.CMGridTDStructure;
import bi.view.testdataviews.CMIndexTDStructureUpdate;
import bi.view.testdataviews.CMPanelTestDataView;
import bi.view.undomanagementviews.CMCompoundEdit;
import bi.view.undomanagementviews.undoviews.CMComponentAwareEdit;
import bi.view.undomanagementviews.undoviews.CMUndoMediator;

/**
 * @author svonborries
 *
 */
@SuppressWarnings("serial")
public class CMCancelGlobalValueReferenceAction extends
		AbstractAction implements Action {

	private CMFrameView m_frame;
	private CMGridTDStructure m_gridTDStructure;
	private CMCompoundEdit m_ce;
	private CMPanelTestDataView m_panelTestDataView;

	public CMCancelGlobalValueReferenceAction(){
		super(CMMessages.getString("TESTDATA_CANCEL_GLOBAL_REFERENCE"));
		putValue(Action.SHORT_DESCRIPTION, CMMessages.getString("TESTDATA_CANCEL_GLOBAL_REFERENCE"));
	    putValue(Action.SMALL_ICON, CMAction.TESTDATA_CANCEL_GLOBAL_REFERENCE.getIcon());
	    putValue(Action.MNEMONIC_KEY,(int)CMMessages.getString("CANCEL_GLOBAL_REFERENCE_MNEMONIC").charAt(0));
	}


	public void actionPerformed(ActionEvent e) {

		this.m_frame = CMApplication.frame;
		this.m_panelTestDataView = this.m_frame.getPanelTestDataView();
		this.m_gridTDStructure = m_frame.getGridTDStructure();
		m_ce = new CMCompoundEdit();

		if(this.m_frame.isIsPanelTestDataSelected()){
			this.cancelInTestDataTab();
		}
	    else{
	    	this.cancelInStructureTab();
	        }

	}

	@SuppressWarnings("unchecked")
	private void cancelInStructureTab (){

		int confirmation;
		confirmation = JOptionPane.showConfirmDialog(this.m_frame, CMMessages.getString("TESTDATA_MESSAGE_DELETE_GLOBAL_REFERENCE"),
	            CMMessages.getString("TESTDATA_TITLE_MENSSAGE_DELETE_STRUCTURE"), JOptionPane.YES_NO_OPTION);
	        if (confirmation == JOptionPane.YES_OPTION) {
	        	int row = this.m_gridTDStructure.getRowSelected();
	            int column = this.m_gridTDStructure.getColumnSelected();
	            if (row >= 0 && column > 0) {

	        		m_ce.addDelegateEdit(new CMDelegate(){

	        			public void execute() {
	        	        	m_gridTDStructure.update(CMIndexTDStructureUpdate.getInstance().getIndex());
	        			}

	        		});
	        		m_ce.addEdit(new CMComponentAwareEdit());
	                StructureTestData std = (StructureTestData)this.m_gridTDStructure.getTDStructure().getM_StructureTestData().elementAt(CMIndexTDStructureUpdate.getInstance().getIndex());
	                ITypeData s = (ITypeData)std.getTypeData().elementAt(this.m_gridTDStructure.getCmGridModel().numOfCell(row));
	                //Vector oldReferenceInTestData = new Vector();
	                this.m_ce.addEdit(CMModelEditFactory.INSTANCE.createChangeGlobalReferenceInTypeDataModelEdit(s,""));
	                s.setGlobal("");
	                int globalindex = std.getGlobalIndex();
	                for (int i = 0; i < this.m_gridTDStructure.getTDStructure().getTestDataCombination().getM_TestDatas().size(); i++) {
	                    TestData td = (TestData)this.m_gridTDStructure.getTDStructure().getTestDataCombination().getM_TestDatas().elementAt(i);
	                    for (int j = 0; j < td.getM_TDStructure().getM_StructureTestData().size(); j++) {
	                        int globalindex2 = ((StructureTestData)td.getM_TDStructure().getM_StructureTestData().elementAt(j)).getGlobalIndex();
	                        if (globalindex == globalindex2) {
	                            StructureTestData tds = ((StructureTestData)td.getM_TDStructure().getM_StructureTestData().elementAt(j));
	                            ITypeData typ = (ITypeData)tds.getTypeData().elementAt(this.m_gridTDStructure.getCmGridModel().numOfCell(row));
	                            if (typ.getGlobal().equals("G")) {
//	                                String valuei = Integer.toString(i);
//	                                String valuej = Integer.toString(j);
//	                                oldReferenceInTestData.addElement(valuei + valuej);
	                            	this.m_ce.addEdit(CMModelEditFactory.INSTANCE.createChangeGlobalReferenceInTypeDataModelEdit(typ,""));
		                            typ.setGlobal("");
	                            }
	                            
	                        }
	                    }
	                }

	                m_ce.addDelegateEdit(new CMDelegate(){

	        			public void execute() {
	        	        	m_gridTDStructure.update(CMIndexTDStructureUpdate.getInstance().getIndex());
	        			}

	        		});
	                
	                m_gridTDStructure.update(CMIndexTDStructureUpdate.getInstance().getIndex());

					if(m_ce.hasEdits())
			        	CMUndoMediator.getInstance().doEdit(m_ce);

	                /*this.m_frame.getM_CMUndoMediator().cancelAssignGlobalReferenceInTDStructure(this.m_gridTDStructure, CMIndexTDStructureUpdate.getInstance().getIndex(),
	                    row, column, oldReferenceInTestData, this.m_frame.getm_TabbedPaneView(),
	                    CMMessages.getString("TESTDATA_TDSTRUCTURE"),m_ce);*/
	            }


	        	
	    }
	}

	private void cancelInTestDataTab (){

		m_ce.addDelegateEdit(new CMDelegate(){

			public void execute() {
	        	m_gridTDStructure.update();
	        	m_panelTestDataView.update();
			}

		});
		m_ce.addEdit(new CMComponentAwareEdit());


		int confirmation= JOptionPane.YES_OPTION;
		confirmation = JOptionPane.showConfirmDialog(this.m_frame, CMMessages.getString("TESTDATA_MESSAGE_DELETE_GLOBAL_REFERENCE"),
	            CMMessages.getString("TESTDATA_TITLE_MENSSAGE_DELETE_STRUCTURE"), JOptionPane.YES_NO_OPTION); //$NON-NLS-1$ //$NON-NLS-2$
	        if (confirmation == JOptionPane.YES_OPTION) {

	        	int globalindex = this.cancelGlobalValueReferenceInTestData(m_ce);
	        	int row = CMIndexTDStructureUpdate.getInstance().getnumOfRow();
	        	m_ce.addEdit(m_gridTDStructure.ifcancelReferenceInTestDataCancelinStructure(globalindex, row));

	    		m_ce.addDelegateEdit(new CMDelegate(){

	    			public void execute() {
	    	        	m_gridTDStructure.update();
	    	        	m_panelTestDataView.update();
	    			}

	    		});

	        	m_gridTDStructure.update();
	        	m_panelTestDataView.update();

				if(m_ce.hasEdits())
		        	CMUndoMediator.getInstance().doEdit(m_ce);

	        }

	}

	private int cancelGlobalValueReferenceInTestData(CMCompoundEdit p_ce){
		int row = this.m_panelTestDataView.getM_CMGridTDStructure().getRowSelected();
        //int column = this.m_panelTestDataView.getM_CMGridTDStructure().getColumnSelected();
        StructureTestData std = (StructureTestData)this.m_panelTestDataView.getM_CMGridTDStructure().getTDStructure().getM_StructureTestData().elementAt(CMIndexTDStructureUpdate.getInstance().getIndex());
        ITypeData s = (ITypeData)std.getTypeData().elementAt(this.m_panelTestDataView.getM_CMGridTDStructure().getCmGridModel().numOfCell(row));
        //int indexTestData = CMIndexTDStructureUpdate.getInstance().getindexTestData();
        //int indexStructure = CMIndexTDStructureUpdate.getInstance().getIndex();
        //int numofTable = CMIndexTDStructureUpdate.getInstance().getnumOfTable();
        //int numofRow = CMIndexTDStructureUpdate.getInstance().getnumOfRow();
        /*this.m_frame.getM_CMUndoMediator().cancelAssignGlobalReferenceinTDStructureinTestDataEdit(this.m_frame.getPanelTestDataView(),
        		this.m_frame.getGridTDStructure(), indexTestData, indexStructure, (ITypeData)s.clone(),
            numofTable, numofRow, row, column, this.m_frame.getm_TabbedPaneView(), CMMessages.getString("TESTDATA_TESTDATA"));*/
        p_ce.addEdit(CMModelEditFactory.INSTANCE.createChangeGlobalReferenceInTypeDataModelEdit(s,""));
        s.setGlobal("");
        return std.getGlobalIndex();
	}


}
