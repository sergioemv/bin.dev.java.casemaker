//svonborries_15112005_begin
package bi.view.testdataviews;

import java.awt.event.KeyEvent;

import model.CMDefaultValue;
import model.ITypeData;
import bi.view.actions.CMAction;
import bi.view.cells.CMBaseCell;
import bi.view.cells.CMCellHeaderTDStructureField;
import bi.view.cells.CMCellHeaderTDStructureFormat;
import bi.view.cells.CMCellHeaderTDStructureFormula;
import bi.view.cells.CMCellHeaderTDStructureGlobal;
import bi.view.cells.CMCellHeaderTDStructureKey;
import bi.view.cells.CMCellHeaderTDStructureLength;
import bi.view.cells.CMCellHeaderTDStructureName;
import bi.view.cells.CMCellHeaderTDStructureNewColumn;
import bi.view.cells.CMCellHeaderTDStructureObjectTypes;
import bi.view.cells.CMCellHeaderTDStructurePrefix;
import bi.view.cells.CMCellHeaderTDStructureSuffix;
import bi.view.cells.CMCellHeaderTDStructureType;
import bi.view.cells.CMCellHeaderTDStructureValue;
import bi.view.cells.CMCellTDStructureFormula;
import bi.view.mainframeviews.CMFrameView;

public class CMDeleteKeyInterceptor {

	private CMFrameView m_FrameView = null;
	private CMGridTDStructure m_GridTDStructure = null;
	private ITypeData m_TypeData = null;

	public CMDeleteKeyInterceptor(CMFrameView p_FrameView){
		this.m_FrameView = p_FrameView;
	}

	public boolean dispatchKeyEvent(KeyEvent e) {
		this.m_GridTDStructure = (CMGridTDStructure) e.getSource();
		int l_Row = m_GridTDStructure.getSelectionModel().getLeadRow();
		int l_Colum = m_GridTDStructure.getSelectionModel().getLeadColumn();
		//int l_Table = CMIndexTDStructureUpdate.getInstance().getnumOfTable();
		Object obj = this.m_GridTDStructure.getCellObjectAt(l_Row,l_Colum);

		if(obj instanceof CMCellTDStructureFormula){
			//m_TypeData = m_GridTDStructure.getTypeData(l_Table,l_Row,l_Colum);
			m_TypeData = (ITypeData) ((CMBaseCell)obj).getModel();
			//if(!m_TypeData.getStringFormula().equals("")){
			if(m_TypeData.getValue() == null || m_TypeData.getValue() instanceof CMDefaultValue){
				CMAction.TESTDATA_DELETE_ELEMENT.getAction().actionPerformed(null);
				return true;
			}
			else{
				if(m_TypeData.isFormula()){
					CMAction.TESTDATA_DELETE_FORMULA.getAction().actionPerformed(null);//svonborries_14032006
					return true;
				}
				else if(m_TypeData.isLinkValue()){					
					CMAction.TESTDATA_DELETE_LINK_ELEMENT.getAction().actionPerformed(null);//svonborries_15032006
					return true;
				}
				CMAction.TESTDATA_DELETE_VARIABLE.getAction().actionPerformed(null);//svonborries_14032006
				return true;
			}

				
//			if(!m_TypeData.getFormula().equals("")){
//				if(m_TypeData.isFormula()){
//					CMAction.TESTDATA_DELETE_FORMULA.getAction().actionPerformed(null);//svonborries_14032006
//					return true;
//				}
//				if(m_TypeData.isLinkValue()){
//					CMAction.TESTDATA_DELETE_LINK_ELEMENT.getAction().actionPerformed(null);//svonborries_15032006
//					return true;
//				}
//				CMAction.TESTDATA_DELETE_VARIABLE.getAction().actionPerformed(null);//svonborries_14032006
//				return true;
//			}
//			else{
//				if(!this.m_FrameView.isIsPanelTestDataSelected()&&!this.m_FrameView.isIsPanelResultComparationSelected()){
//					CMAction.TESTDATA_DELETE_ELEMENT.getAction().actionPerformed(null);
//					return true;
//				}
//			}
		}
		else
			{if(obj instanceof CMCellHeaderTDStructureKey || obj instanceof CMCellHeaderTDStructureGlobal ||
				obj instanceof CMCellHeaderTDStructureField || obj instanceof CMCellHeaderTDStructureName ||
				obj instanceof CMCellHeaderTDStructureObjectTypes || obj instanceof CMCellHeaderTDStructureType ||
				obj instanceof CMCellHeaderTDStructureLength || obj instanceof CMCellHeaderTDStructurePrefix ||
				obj instanceof CMCellHeaderTDStructureSuffix || obj instanceof CMCellHeaderTDStructureFormat ||
				obj instanceof CMCellHeaderTDStructureFormula || obj instanceof CMCellHeaderTDStructureValue){
			return true;
			}
			else
				if(obj instanceof CMCellHeaderTDStructureNewColumn){
					CMAction.TESTDATA_DELETE_COLUMN_ADDED.getAction().actionPerformed(null);
					return true;
				}
			
		}
		if(this.m_FrameView.isIsPanelTestDataSelected())
			return true;
		else{
			CMAction.TESTDATA_DELETE_ELEMENT.getAction().actionPerformed(null);
			return true;
		}
			}

}
//svonborries_15112005_end