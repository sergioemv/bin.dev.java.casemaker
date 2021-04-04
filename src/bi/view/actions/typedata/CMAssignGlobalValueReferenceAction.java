package bi.view.actions.typedata;

import java.awt.Event;
import java.awt.event.ActionEvent;
import java.util.Vector;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.KeyStroke;
import javax.swing.undo.UndoableEdit;
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
public class CMAssignGlobalValueReferenceAction extends AbstractAction
		implements Action{

	private CMFrameView m_frame;
	private CMGridTDStructure m_gridTdStructure;
	private CMPanelTestDataView m_panelTestDataView;

	public CMAssignGlobalValueReferenceAction(){
		super(CMMessages.getString("TESTDATA_ASSIGN_GLOBAL_REFERENCE"));
		putValue(Action.SHORT_DESCRIPTION, CMMessages.getString("TESTDATA_ASSIGN_GLOBAL_REFERENCE"));
	    putValue(Action.SMALL_ICON, CMAction.TESTDATA_ASSIGN_GLOBAL_REFERENCE.getIcon());
	    putValue(Action.MNEMONIC_KEY,(int)CMMessages.getString("REFERENCE_VALUES_FROM_THE_GLOBAL_REFERENCE_MNEMONIC").charAt(0));
	    putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_R, Event.CTRL_MASK));
	}

	public void actionPerformed(ActionEvent e) {

	    this.m_frame = CMApplication.frame;
	    this.m_panelTestDataView = this.m_frame.getPanelTestDataView();
	    this.m_gridTdStructure = this.m_frame.getGridTDStructure();
	    CMCompoundEdit ce = new CMCompoundEdit();

	    if(m_frame.isIsPanelTestDataSelected()){
	    	this.testDataAssignGlobalReference(ce);
	    }
	    else{
	    	this.structureAssignGlobalReference(ce);
	    }

	}

	private void testDataAssignGlobalReference(UndoableEdit p_ce){
		CMCompoundEdit ce;
		if(p_ce == null)
			ce = new CMCompoundEdit();
		else
			ce = (CMCompoundEdit)p_ce;

		ce.addDelegateEdit(new CMDelegate(){

			public void execute() {
				m_panelTestDataView.update();
			}

		});
		ce.addEdit(new CMComponentAwareEdit());

        int table = CMIndexTDStructureUpdate.getInstance().getnumOfTable();
        int row = CMIndexTDStructureUpdate.getInstance().getnumOfRow();
        ITypeData m_typ=m_gridTdStructure.setGlobalReferenceStructure(table, row, p_ce);
        p_ce.addEdit(setGlobalReferenceValuesinTestData(m_typ));

		ce.addDelegateEdit(new CMDelegate(){

			public void execute() {
				m_panelTestDataView.update();
			}

		});


        m_panelTestDataView.update();

		if(ce.hasEdits())
        	CMUndoMediator.getInstance().doEdit(ce);

	}

	private void structureAssignGlobalReference(UndoableEdit p_ce){
		CMCompoundEdit ce;
		if(p_ce == null)
			ce = new CMCompoundEdit();
		else
			ce = (CMCompoundEdit)p_ce;

		ce.addDelegateEdit(new CMDelegate(){

			public void execute() {
		        m_gridTdStructure.update(CMIndexTDStructureUpdate.getInstance().getIndex());
			}

		});
		ce.addEdit(new CMComponentAwareEdit());

		ce.addEdit(this.assignGlobalValueReferenceInStructure());

		ce.addDelegateEdit(new CMDelegate(){

			public void execute() {
		        m_gridTdStructure.update(CMIndexTDStructureUpdate.getInstance().getIndex());
			}

		});
		ce.addEdit(new CMComponentAwareEdit());

        m_gridTdStructure.update(CMIndexTDStructureUpdate.getInstance().getIndex());

		if(ce.hasEdits())
        	CMUndoMediator.getInstance().doEdit(ce);
	}

	public UndoableEdit setGlobalReferenceValuesinTestData(ITypeData p_typ) {

		CMCompoundEdit ce = new CMCompoundEdit();
		this.m_gridTdStructure = this.m_panelTestDataView.getM_CMGridTDStructure();
        int row = this.m_gridTdStructure.getRowSelected();
        //int column = this.m_gridTdStructure.getColumnSelected();
        StructureTestData std = (StructureTestData)this.m_gridTdStructure.getTDStructure().getM_StructureTestData().elementAt(CMIndexTDStructureUpdate.getInstance().getIndex());
        ITypeData s = (ITypeData)std.getTypeData().elementAt(this.m_gridTdStructure.getCmGridModel().numOfCell(row));
        //int indexTestData = CMIndexTDStructureUpdate.getInstance().getindexTestData();
        //int indexStructure = CMIndexTDStructureUpdate.getInstance().getIndex();
        //int numofTable = CMIndexTDStructureUpdate.getInstance().getnumOfTable();
        //int numofRow = CMIndexTDStructureUpdate.getInstance().getnumOfRow();

        ce.addEdit(CMModelEditFactory.INSTANCE.createChangeGlobalReferenceInTypeDataModelEdit(s,"G"));
        s.setGlobal("G");
        ce.addEdit(CMModelEditFactory.INSTANCE.createChangeFieldInTypeDataModelEdit(s,p_typ.getField()));
        s.setField(p_typ.getField());
        ce.addEdit(CMModelEditFactory.INSTANCE.createChangeFormatInTypeDataModelEdit(s,p_typ.getFormat()));
        s.setFormat(p_typ.getFormat());
        ce.addEdit(CMModelEditFactory.INSTANCE.createChangeFormatterInTypeDataModelEdit(s,p_typ.getFormatter()));
        s.setFormatter(p_typ.getFormatter());
        /*ce.addEdit(CMModelEditFactory.INSTANCE.createChangeFormulaInTypeDataModelEdit(s,p_typ.getStringFormula()));
        s.setFormula(p_typ.getStringFormula());*/
        ce.addEdit(CMModelEditFactory.INSTANCE.createChangeKeyInTypeDataModelEdit(s,p_typ.getKey()));
        s.setKey(p_typ.getKey());
        ce.addEdit(CMModelEditFactory.INSTANCE.createChangeLengthInTypeDataModelEdit(s,p_typ.getLength()));
        s.setLength(p_typ.getLength());
        ce.addEdit(CMModelEditFactory.INSTANCE.createChangeNameInTypeDataModelEdit(s,p_typ.getName()));
        s.setName(p_typ.getName());
        /*ce.addEdit(CMModelEditFactory.INSTANCE.createChangeToolVendorOTInTypeDataModelEdit(s,p_typ.getToolVendorOT()));
        s.setToolVendorOT(p_typ.getToolVendorOT());*///ccastedo 27.09.06
        ce.addEdit(CMModelEditFactory.INSTANCE.createChangeToolVendorOTInTypeDataModelEdit(s,p_typ.getStateOT()));
        s.setStateOT(p_typ.getStateOT());
        ce.addEdit(CMModelEditFactory.INSTANCE.createChangeNewColumnsInTypeDataModelEdit(s,p_typ.getNewColumns()));
        s.setNewColumns(p_typ.getNewColumns());
        ce.addEdit(CMModelEditFactory.INSTANCE.createChangePrefixInTypeDataModelEdit(s,p_typ.getPrefix()));
        s.setPrefix(p_typ.getPrefix());
        ce.addEdit(CMModelEditFactory.INSTANCE.createChangeSuffixInTypeDataModelEdit(s,p_typ.getSuffix()));
        s.setSuffix(p_typ.getSuffix());
        ce.addEdit(CMModelEditFactory.INSTANCE.createChangeTypeInTypeDataModelEdit(s,p_typ.getTypeName()));
        s.setType(p_typ.getTypeName());
        ce.addEdit(CMModelEditFactory.INSTANCE.createChangeValueInTypeDataModelEdit(s,p_typ.getValue()));
        s.setValue(p_typ.getValue());
        //s.setStringValue(p_typ.getStringValue());
        //CMCellTDStructureGlobal cellGlobal = new CMCellTDStructureGlobal(m_gridTdStructure.getTDStructure(),s.getGlobal());
        //ce.addEdit(CMViewEditFactory.INSTANCE.createChangeCellGlobalObjectInTDstructureViewEdit(m_gridTdStructure.getCmGridModel(),cellGlobal,row,column));
        //m_gridTdStructure.getCmGridModel().setValueAt(cellGlobal,row,column);


        /*m_frame.getM_CMUndoMediator().assignGlobalReferenceinTDStructureinTestDataEdit(m_frame.getPanelTestDataView(),
            	m_frame.getGridTDStructure(), indexTestData, indexStructure, (ITypeData)s.clone(),
                numofTable, numofRow, row, column, m_frame.getm_TabbedPaneView(), CMMessages.getString("TESTDATA_TESTDATA"),m_ce);*/

	return ce;
	}

    @SuppressWarnings("unchecked")
	public UndoableEdit assignGlobalValueReferenceInStructure() {

    	CMCompoundEdit ce = new CMCompoundEdit();
        int row = this.m_gridTdStructure.getRowSelected();
        int column = this.m_gridTdStructure.getColumnSelected();

        if (row >= 0 && column > 0) {
            StructureTestData std = (StructureTestData)this.m_gridTdStructure.getTDStructure().getM_StructureTestData().elementAt(CMIndexTDStructureUpdate.getInstance().getIndex());
            ITypeData s = (ITypeData)std.getTypeData().elementAt(this.m_gridTdStructure.getCmGridModel().numOfCell(row));
            Vector oldReferenceInTestData = new Vector();
            ce.addEdit(CMModelEditFactory.INSTANCE.createChangeGlobalReferenceInTypeDataModelEdit(s,"G"));
            s.setGlobal("G");
            int globalindex = std.getGlobalIndex();
            for (int i = 0; i < this.m_gridTdStructure.getTDStructure().getTestDataCombination().getM_TestDatas().size(); i++) {
                TestData td = (TestData)this.m_gridTdStructure.getTDStructure().getTestDataCombination().getM_TestDatas().elementAt(i);
                for (int j = 0; j < td.getM_TDStructure().getM_StructureTestData().size(); j++) {
                    int globalindex2 = ((StructureTestData)td.getM_TDStructure().getM_StructureTestData().elementAt(j)).getGlobalIndex();
                    if (globalindex == globalindex2) {
                        StructureTestData tds = ((StructureTestData)td.getM_TDStructure().getM_StructureTestData().elementAt(j));
                        ITypeData typ = (ITypeData)tds.getTypeData().elementAt(this.m_gridTdStructure.getCmGridModel().numOfCell(row));
                        if (!typ.getGlobal().equals("G")) {
                            String valuei = Integer.toString(i);
                            String valuej = Integer.toString(j);
                            oldReferenceInTestData.addElement(valuei + valuej);
                        }
                        ce.addEdit(CMModelEditFactory.INSTANCE.createChangeGlobalReferenceInTypeDataModelEdit(typ,"G"));
                        typ.setGlobal("G");
                    }
                }
            }
            /*m_frame.getM_CMUndoMediator().assignGlobalReferenceInTDStructure(this.m_gridTdStructure, CMIndexTDStructureUpdate.getInstance().getIndex(),
                row, column, oldReferenceInTestData, m_frame.getm_TabbedPaneView(),
                CMMessages.getString("TESTDATA_TDSTRUCTURE"),ce);*/
        }
        return ce;
    }


}
