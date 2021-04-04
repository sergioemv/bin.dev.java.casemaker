/**
 * 24/11/2006
 * svonborries
 */
package bi.controller.testdata.linkelement;

import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import model.ITypeData;
import model.StructureTestData;
import model.TDStructure;
import bi.view.mainframeviews.CMApplication;
import bi.view.mainframeviews.CMFrameView;
import bi.view.testdataviews.CMGridTDStructure;
import bi.view.utils.testdata.CMPanelLinkElementStructure;

/**
 * @author svonborries
 *
 */
public class CMLinkElementStructureEditController {
	
	private CMPanelLinkElementStructure panelLinkElementStructure = null;
	private CMFrameView m_Frame = null;
	private CMGridTDStructure m_GridTDStructure = null;
	private TDStructure m_TDStructure = null;
	private ITypeData m_TypeData = null;
	private ITypeData m_DestinationTypeData = null;
	private StructureTestData m_StructureTestData = null;
	private String m_StringFormulaField = null;
	
	public CMLinkElementStructureEditController(){
		m_Frame=CMApplication.frame;
		if(m_Frame .isIsPanelTestDataSelected()){
			m_GridTDStructure  = m_Frame.getPanelTestDataView().getM_CMGridTDStructure();
		}
		else
			m_GridTDStructure = m_Frame.getGridTDStructure();
		if(m_GridTDStructure == null){
			m_GridTDStructure= m_Frame.getGridTDStructure();
		}
		m_TDStructure  = m_GridTDStructure.getTDStructure();
		setM_DestinationTypeData(m_GridTDStructure.getSelectedTypeData());
	}
	
	

	public CMPanelLinkElementStructure getPanelLinkElementStructure() {
		if(panelLinkElementStructure == null){
			panelLinkElementStructure = new CMPanelLinkElementStructure();
			panelLinkElementStructure.getJListStructures()
			.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
				public void valueChanged(javax.swing.event.ListSelectionEvent e) {
					jListStructureValueChanged();
				}
			});
			panelLinkElementStructure.getJListElements()
			.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
				public void valueChanged(javax.swing.event.ListSelectionEvent e) {
					jListFieldValueChanged();
				}
			});
			fillStructureList();
			panelLinkElementStructure.getJListStructures().setSelectedIndex(0);
		}
		return panelLinkElementStructure;
	}

	public void setPanelLinkElementStructure(
			CMPanelLinkElementStructure panelLinkElementStructure) {
		this.panelLinkElementStructure = panelLinkElementStructure;
	}
	
	private void fillStructureList(){
		List<String> l_Structure = new Vector<String>();
		for (Iterator iter = m_TDStructure.getM_StructureTestData().iterator(); iter.hasNext();) {
			l_Structure.add(((StructureTestData)iter.next()).getName());
		}
		this.getPanelLinkElementStructure().getJListStructures().setListData((Vector)l_Structure);
	}
	
	private void fillFieldList(int p_StructureIndex) {
		StructureTestData l_StructureTestData = (StructureTestData)m_TDStructure.getM_StructureTestData().get(p_StructureIndex);
		List<String> l_TypeData = new Vector<String>();
		for (Iterator iter = l_StructureTestData.getTypeData().iterator(); iter.hasNext();) {
			l_TypeData.add(((ITypeData)iter.next()).getField());
		}
		getPanelLinkElementStructure().getJListElements().setListData((Vector)l_TypeData);
	}
	
    private void fillLabelValue(int p_FieldIndex, int p_StructureIndex){
    	m_TypeData  = null;

    	if (p_FieldIndex >=0 && p_StructureIndex >=0){
    		m_StructureTestData = (StructureTestData) m_TDStructure.getM_StructureTestData().get(p_StructureIndex);
    		m_TypeData = (ITypeData)m_StructureTestData.getTypeData().get(p_FieldIndex);
    		m_StringFormulaField = "$"+m_StructureTestData.getName()+"."+m_TypeData.getField();
    		getPanelLinkElementStructure().getJLabelPreviewValue().setText(m_TypeData.getFormattedValue());
    		//getPanelLinkElementStructure().getJLabelPreviewValue().setText(m_TypeData.getStringValue());
    	}
    	else{
    		getPanelLinkElementStructure().getJLabelPreviewValue().setText("");
    	}
    }

	private void jListStructureValueChanged() {
		fillFieldList(getPanelLinkElementStructure().getJListStructures().getSelectedIndex());
		getPanelLinkElementStructure().getJListElements().setSelectedIndex(0);
	}
	

	private void jListFieldValueChanged() {
		fillLabelValue(getPanelLinkElementStructure().getJListElements().getSelectedIndex(), 
				getPanelLinkElementStructure().getJListStructures().getSelectedIndex());
	}



	public String getM_StringFormulaField() {
		if(m_StringFormulaField == null)
			m_StringFormulaField = "";
		return m_StringFormulaField;
	}



	public void setM_StringFormulaField(String stringFormulaField) {
		m_StringFormulaField = stringFormulaField;
	}
	
	@SuppressWarnings("unused")
	private boolean isReferenceInTheSameStructure(ITypeData observer, ITypeData typeData) {
		StructureTestData observerStructure=observer.getStructureTestData();
		StructureTestData typeDataStructure=typeData.getStructureTestData();
		if(observerStructure == typeDataStructure)
			return true;
		return false;
	}



	public ITypeData getM_DestinationTypeData() {
		return m_DestinationTypeData;
	}



	public void setM_DestinationTypeData(ITypeData destinationTypeData) {
		m_DestinationTypeData = destinationTypeData;
	}


}
