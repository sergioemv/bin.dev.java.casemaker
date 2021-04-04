/**
 * 28/11/2006
 * svonborries
 */
package bi.controller.testdata.linkelement;

import java.util.Iterator;
import java.util.List;
import java.util.Vector;


import model.ITypeData;
import model.StructureTestData;
import model.TDStructure;
import model.TestData;

import bi.view.lang.CMMessages;
import bi.view.mainframeviews.CMApplication;
import bi.view.mainframeviews.CMFrameView;
import bi.view.testdataviews.CMGridTDStructure;
import bi.view.utils.testdata.CMPanelLinkElementTestData;

/**
 * @author svonborries
 *
 */
public class CMLinkElementTestDataEditController {
	
	private CMPanelLinkElementTestData panelLinkElementTestData = null;
	private CMFrameView m_Frame = null;
	private CMGridTDStructure m_GridTDStructure = null;
	private TDStructure m_TDStructure = null;
	private ITypeData m_destinationITypeData = null;
	private TDStructure m_TDStructureStructure = null;
	private TestData m_TestData = null;
	private ITypeData m_TypeData = null;
	private StructureTestData m_StructureTestData = null;
	private String m_StringFormulaField = null;

	
	public CMLinkElementTestDataEditController(){
		m_Frame =CMApplication.frame;
		if(m_Frame .isIsPanelTestDataSelected()){
			m_GridTDStructure   = m_Frame.getPanelTestDataView().getM_CMGridTDStructure();
		}
		else
			m_GridTDStructure = m_Frame.getGridTDStructure();
		if(m_GridTDStructure == null){
			m_GridTDStructure= m_Frame.getGridTDStructure();
		}
		m_TDStructure   = m_GridTDStructure.getTDStructure();
		m_TDStructureStructure  = m_Frame.getGridTDStructure().getTDStructure();
		setM_DestinationTypeData(m_GridTDStructure.getSelectedTypeData());
	}
	
	
	private void setM_DestinationTypeData(ITypeData selectedTypeData) {
		m_destinationITypeData = selectedTypeData;
	}
	
	public ITypeData getM_DestinationTypeData() {
		return m_destinationITypeData;
	}
	
	
	/**
	 * @return the panelLinkElementTestData
	 * 28/11/2006
	 * svonborries
	 */
	public CMPanelLinkElementTestData getPanelLinkElementTestData() {
		if(panelLinkElementTestData == null){
			panelLinkElementTestData = new CMPanelLinkElementTestData();
			panelLinkElementTestData.getJListTestDatas()
			.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
				public void valueChanged(javax.swing.event.ListSelectionEvent e) {
					jListTestDatasActionPerformer();
				}
			});
			
			panelLinkElementTestData.getJListStructures()
			.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
				public void valueChanged(javax.swing.event.ListSelectionEvent e) {
					jListStructureActionPerformer();
				}
			});
			
			panelLinkElementTestData.getJListElements()
			.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
				public void valueChanged(javax.swing.event.ListSelectionEvent e) {
					jListElementActionPerformer();
				}
			});
			fillGlobalList();
			panelLinkElementTestData.getJListTestDatas().setSelectedIndex(0);
		}
		return panelLinkElementTestData;
	}
	
    private void fillGlobalList(){
    	List<String> l_Global = new Vector<String>();
    	l_Global.add(CMMessages.getString("TESTDATA_LINKELEMENT_GLOBAL"));

    	for (Iterator iter = m_TDStructure.getTestDataCombination().getM_TestDatas().iterator(); iter.hasNext();) {
    		l_Global.add(((TestData)iter.next()).getName());
		}
    	 getPanelLinkElementTestData().getJListTestDatas().setListData((Vector)l_Global);
    }
    

	private void jListTestDatasActionPerformer() {
		fillStructureList(getPanelLinkElementTestData().getJListTestDatas().getSelectedIndex());
		getPanelLinkElementTestData().getJListStructures().setSelectedIndex(0);
	}
	

	private void jListStructureActionPerformer() {
		fillFieldList(getPanelLinkElementTestData().getJListStructures().getSelectedIndex());
		getPanelLinkElementTestData().getJListElements().setSelectedIndex(0);
	}
	

	private void jListElementActionPerformer() {
		fillLabelValue(getPanelLinkElementTestData().getJListElements().getSelectedIndex(), 
				getPanelLinkElementTestData().getJListStructures().getSelectedIndex(), 
				getPanelLinkElementTestData().getJListTestDatas().getSelectedIndex());
	}
	
	
    private void fillStructureList(int p_GlobalIndex){
    	List<String> l_Structure = new Vector<String>();

    	if(p_GlobalIndex == 0){
    		for (Iterator iter = this.m_TDStructureStructure.getM_StructureTestData().iterator(); iter.hasNext();) {
    			l_Structure.add(((StructureTestData)iter.next()).getName());
    		}
    		getPanelLinkElementTestData().getJListStructures().setListData((Vector)l_Structure);
    	}
    	else{
    		m_TestData  = (TestData) m_TDStructure.getTestDataCombination().getM_TestDatas().get(p_GlobalIndex-1);
    		for (Iterator iter = m_TestData.getM_TDStructure().getM_StructureTestData().iterator(); iter.hasNext();) {
    			StructureTestData std=((StructureTestData)iter.next());
				l_Structure.add(std.getName()+std.getTestDataIdentification());
			}
    		getPanelLinkElementTestData().getJListStructures().setListData((Vector)l_Structure);
    	}
	}
    
    private void fillFieldList(int p_StructureIndex) {
    	List<String> l_TypeData = new Vector<String>();
    	if (p_StructureIndex == -1 ){
    		p_StructureIndex = 0;
    		}
    	if(getPanelLinkElementTestData().getJListTestDatas().getSelectedIndex() == 0){
    		StructureTestData l_StructureTestData1 = (StructureTestData)m_TDStructureStructure.getM_StructureTestData().get(p_StructureIndex);
    		for (Iterator iter = l_StructureTestData1.getTypeData().iterator(); iter.hasNext();) {
    			l_TypeData.add(((ITypeData)iter.next()).getField());
    		}
    		getPanelLinkElementTestData().getJListElements().setListData((Vector)l_TypeData);
    	}
    	else {
    		StructureTestData l_StructureTestData2 = (StructureTestData)m_TestData.getM_TDStructure().getM_StructureTestData().get(p_StructureIndex);
    		for (Iterator iter = l_StructureTestData2.getTypeData().iterator(); iter.hasNext();) {
    			l_TypeData.add(((ITypeData)iter.next()).getField());
    		}
    		getPanelLinkElementTestData().getJListElements().setListData((Vector)l_TypeData);
    	}
	}
    
    private void fillLabelValue(int p_FieldIndex, int p_StructureIndex, int p_GlobalIndex){
    	m_TypeData  = null;
    	if(p_GlobalIndex == 0){
    		if (p_FieldIndex >=0 && p_StructureIndex >=0){
        		m_StructureTestData = (StructureTestData) m_TDStructureStructure.getM_StructureTestData().get(p_StructureIndex);
        		m_TypeData = (ITypeData)m_StructureTestData.getTypeData().get(p_FieldIndex);
        		m_StringFormulaField = "$"+m_StructureTestData.getName()+"."+m_TypeData.getField();
        		getPanelLinkElementTestData().getLabelPreviewValue().setText(m_TypeData.getFormattedValue());
        		//getPanelLinkElementTestData().getLabelPreviewValue().setText(m_TypeData.getStringValue());
        	}
        	else{
        		getPanelLinkElementTestData().getLabelPreviewValue().setText("");
        	}
    	}
    	else
    	{
    		if(p_FieldIndex >=0 && p_StructureIndex >=0){
    			m_StructureTestData = (StructureTestData)m_TestData.getM_TDStructure().getM_StructureTestData().get(p_StructureIndex);
    			m_TypeData = (ITypeData)m_StructureTestData.getTypeData().get(p_FieldIndex);
    			m_StringFormulaField = "$"+m_TestData.getName()+"."+m_StructureTestData.getName()+m_StructureTestData.getTestDataIdentification()+"."+m_TypeData.getField();
    			getPanelLinkElementTestData().getLabelPreviewValue().setText(m_TypeData.getFormattedValue());
    			//getPanelLinkElementTestData().getLabelPreviewValue().setText(m_TypeData.getStringValue());
    		}
    		else{
    			getPanelLinkElementTestData().getLabelPreviewValue().setText("");
    		}
    	}


    }


	/**
	 * @return the m_StringFormulaField
	 * 29/11/2006
	 * svonborries
	 */
	public String getM_StringFormulaField() {
		return m_StringFormulaField;
	}


	/**
	 * @param stringFormulaField the m_StringFormulaField to set
	 * 29/11/2006
	 * svonborries
	 */
	public void setM_StringFormulaField(String stringFormulaField) {
		m_StringFormulaField = stringFormulaField;
	}
	
	

}
