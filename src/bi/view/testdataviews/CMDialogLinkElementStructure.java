//svonborries_01112005_begin
package bi.view.testdataviews;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import model.ITypeData;
import model.StructureTestData;
import model.TDStructure;
import model.edit.CMModelEditFactory;
import bi.controller.TDStructureManager;
import bi.controller.utils.CMLocalReferenceConvert;
import bi.view.lang.CMMessages;
import bi.view.mainframeviews.CMApplication;
import bi.view.mainframeviews.CMFrameView;
import bi.view.undomanagementviews.CMCompoundEdit;
import bi.view.utils.JCustomDialog;

public class CMDialogLinkElementStructure extends JCustomDialog{
/**
	 * 24/11/2006
	 * svonborries
	 */
	private static final long serialVersionUID = 1L;
	//variables
	private CMGridTDStructure m_GridTDStructure;
	private TDStructure m_TDStructure;
	private JPanel jPanelStructure = new JPanel();
	private JPanel jPanelField = new JPanel();
	private JLabel jLabelField = new JLabel();
	private JTextArea jTextAreaField = new JTextArea();
	private JButton jButtonOk = new JButton();
	private JButton jButtonCancel = new JButton();
	private JScrollPane jScrollPaneStructure = new JScrollPane();
	private JScrollPane jScrollPaneField = new JScrollPane();
	private JList jListStructure = new JList();
	private JList jListField = new JList();
	private ITypeData m_TypeData = null;//new TypeData();
	private StructureTestData m_StructureTestData = new StructureTestData();
	private String m_StringFormulaField;
	private CMFrameView m_Frame;
	private boolean openInDialogFormulas=false;
	private CMDialogFormulasValues m_DialogFormulasValue;
//	HCanedo_23022006_begin
	private HashMap<ITypeData,CMLocalReferenceConvert> localReferences;
	private boolean localSelectedInFormula;
//	HCanedo_23022006_end





	public CMDialogLinkElementStructure (){
		super(CMApplication.frame);
//		HCanedo_17112005_begin
		m_Frame=CMApplication.frame;
		if(m_Frame.isIsPanelTestDataSelected())
			m_GridTDStructure = m_Frame.getPanelTestDataView().getM_CMGridTDStructure();
		else
			m_GridTDStructure = m_Frame.getGridTDStructure();
		if(m_GridTDStructure == null){
			m_GridTDStructure= m_Frame.getGridTDStructure();
		}
//		HCanedo_17112005_end
		
		m_TDStructure = m_GridTDStructure.getTDStructure();
		
		try {
			initGUI();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	void initGUI() throws Exception{
		this.setModal(true);
		getContentPane().setLayout(null);
		getContentPane().add(jPanelStructure);
		getContentPane().add(jPanelField);
		getContentPane().add(jLabelField);
		getContentPane().add(jTextAreaField);
		getContentPane().add(jButtonOk);
		getContentPane().add(jButtonCancel);
		getContentPane().setSize(new Dimension(475,323));
		setTitle(CMMessages.getString("TESTDATA_TITLE_DIALOG_LINK_ELEMENT"));
		setBounds(new Rectangle(0, 0, 486, 354));
		setResizable(false);
		getRootPane().setDefaultButton(jButtonOk);


//panel jPanelStructure
		this.jPanelStructure.setBounds(new Rectangle(26, 15, 191, 181));
		this.jPanelStructure.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(),
	            CMMessages.getString("TESTDATA_LINKELEMENT_STRUCTURE"), TitledBorder.LEADING, TitledBorder.TOP,
	            new Font("SansSerif", 0, 11), new Color(60, 60, 60)));
		this.jPanelStructure.setLayout(new BorderLayout());
		this.jPanelStructure.add(jScrollPaneStructure,BorderLayout.CENTER);
		this.jScrollPaneStructure.getViewport().add(jListStructure);
		this.jListStructure.setBounds(new Rectangle(74, 50, 32, 32));
		this.jListStructure.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		this.jListStructure.addListSelectionListener(new ListSelectionListener(){
			public void valueChanged(ListSelectionEvent arg0) {
				jListStructureValueChanged(arg0);
			}});


		//this.selectedValue = jListStructure.getSelectedIndex();

//panel jPanelField
		this.jPanelField.setBounds(new Rectangle(248, 14, 198, 181));
		this.jPanelField.setLayout(new BorderLayout());
		this.jPanelField.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(),
	            CMMessages.getString("TESTDATA_LINKELEMENT_FIELD"), TitledBorder.LEADING, TitledBorder.TOP,
	            new Font("SansSerif", 0, 11), new Color(60, 60, 60)));
		this.jPanelField.add(jScrollPaneField,BorderLayout.CENTER);
		this.jScrollPaneField.getViewport().add(jListField);
		this.jListField.setBounds(new Rectangle(62, 55, 32, 32));
		this.jListField.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		this.jListField.addListSelectionListener(new ListSelectionListener(){
			public void valueChanged(ListSelectionEvent arg0) {
				jListFieldValueChanged(arg0);
			}});


//button jButtonOk
		jButtonOk.setText(CMMessages.getString("BUTTON_OK"));
		jButtonOk.setBounds(new Rectangle(282, 281, 78, 27));

		jButtonOk.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				jButtonOKActionPerformed(arg0);
			}});

//button jButtonCancel
		jButtonCancel.setText(CMMessages.getString("BUTTON_CANCEL"));
		jButtonCancel.setBounds(new Rectangle(374, 281, 78, 27));

		jButtonCancel.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				jButtonCancelActionPerformed(arg0);
			}
		});

//label jLabelField
		jLabelField.setBounds(new Rectangle(28, 195, 414, 24));
		jLabelField.setFont(new Font("SansSerif", Font.BOLD, 14));
		jLabelField.setText(CMMessages.getString("TESTDATA_LINKVALUELABEL"));

//textarea jTextAreaField
		jTextAreaField.setBounds(new Rectangle(28, 224, 418, 43));
		jTextAreaField.setCaretColor(new Color(212, 208, 200));
		jTextAreaField.setBackground(this.getBackground());
		jTextAreaField.setForeground(this.getForeground());
		jTextAreaField.setDisabledTextColor(new Color(212, 208, 200));
		jTextAreaField.setLineWrap(true);
		jTextAreaField.setWrapStyleWord(true);
		jTextAreaField.setEditable(false);
		jTextAreaField.setFont(new Font("SansSerif", java.awt.Font.PLAIN, 12));

		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension dlgSize = this.getPreferredSize();
        //Point loc = getLocation();
        this.setLocation((screenSize.width - dlgSize.width) / 2 /*+ loc.x*/,
            (screenSize.height - dlgSize.height) / 2 /*+ loc.y*/);

		this.fillStructureList();
		this.jListStructure.setSelectedIndex(0);
		this.fillFieldList(this.jListStructure.getSelectedIndex());
		this.jListField.setSelectedIndex(0);

	}//end initGUI

	protected void processWindowEvent(WindowEvent e) {
        if (e.getID() == WindowEvent.WINDOW_CLOSING) {

            cancel();
        }
        super.processWindowEvent(e);
    }

    void cancel() {
        dispose();
    }

    private void fillLabelValue(int p_FieldIndex, int p_StructureIndex){
    	m_TypeData = null;

    	if (p_FieldIndex >=0 && p_StructureIndex >=0){
    		m_StructureTestData = (StructureTestData) m_TDStructure.getM_StructureTestData().get(p_StructureIndex);
    		m_TypeData = (ITypeData)m_StructureTestData.getTypeData().get(p_FieldIndex);
    		m_StringFormulaField = "$"+m_StructureTestData.getName()+"."+m_TypeData.getField();
    		//this.jTextAreaField.setText(m_TypeData.getStringValue());
    		this.jTextAreaField.setText(m_TypeData.getFormattedValue());
    	}
    	else{
    		this.jTextAreaField.setText("");
    	}

    }


	private void fillFieldList(int p_StructureIndex) {
		StructureTestData l_StructureTestData = (StructureTestData)m_TDStructure.getM_StructureTestData().get(p_StructureIndex);
		List<String> l_TypeData = new Vector<String>();
		for (Iterator iter = l_StructureTestData.getTypeData().iterator(); iter.hasNext();) {
			l_TypeData.add(((ITypeData)iter.next()).getField());
		}
		this.jListField.setListData((Vector)l_TypeData);
	}

	private void fillStructureList(){
		List<String> l_Structure = new Vector<String>();
		for (Iterator iter = m_TDStructure.getM_StructureTestData().iterator(); iter.hasNext();) {
			l_Structure.add(((StructureTestData)iter.next()).getName());
		}
		this.jListStructure.setListData((Vector)l_Structure);
	}

	protected List getOrder() {
		List focusOrder = new ArrayList();
		focusOrder.add(this.jListStructure);
		focusOrder.add(this.jListField);
		focusOrder.add(this.jButtonOk);
		focusOrder.add(this.jButtonCancel);
		return focusOrder;
	}

	protected void PressJButtonCancel(Object object) {
		this.jButtonCancelActionPerformed(null);

	}

	protected void PressJButtonOk(Object object) {
		this.jButtonOKActionPerformed(null);

	}

	public void jButtonOKActionPerformed(ActionEvent e) {
		CMCompoundEdit ce= new CMCompoundEdit();
		if(!isOpenInDialogFormulas()){
		if (this.m_TypeData!=null){
			ITypeData observer=null;
			if(m_TypeDataForInsertField != null){
				observer= m_TypeDataForInsertField;
			}
			else{
			 observer=m_GridTDStructure.getSelectedTypeData();
			}
			if(!existCircularReferences(observer, m_TypeData)){
				boolean localValues=false;
				if(isReferenceInTheSameStructure(observer, m_TypeData)){
					CMDialogChooseGlobalOrLocalReference cmd= new CMDialogChooseGlobalOrLocalReference(m_Frame);
					cmd.setVisible(true);
					localValues= (cmd.isLocalSelected()&& cmd.isOkSelected());
				}
				ce.addEdit(CMModelEditFactory.INSTANCE.createAddObserverInTypeDataModelEdit(observer,getM_TypeData(),getM_StringFormulaField()));
				getM_TypeData().addObserver(observer);
				observer.addSubject(getM_StringFormulaField(),getM_TypeData());
				if(!isOpenInDialogFormulas()){
					/*ce.addEdit(CMModelEditFactory.INSTANCE.createChangeisLinkElementInTypeDataModelEdit(observer,true));
					observer.setLinkValue(true);*/
					if(m_TypeDataForInsertField == null){
						if(localValues){
							ce.addEdit(TDStructureManager.setTypeDataReferenceForLocalStructure(m_Frame, observer, getM_TypeData(), m_StringFormulaField, true));
							ce.addEdit(CMModelEditFactory.INSTANCE.createChangeGlobalReferenceInTypeDataModelEdit(observer,""));
							observer.setGlobal("");
						}
						else{
							ce.addEdit(TDStructureManager.getTypeDataReferenceForGlobalStructure(m_Frame, observer, getM_TypeData(), m_StringFormulaField, true));
						}
					}
					//ACA TENES Q CREAR EL COMANDO DE VISTA Q HACE ESTO, NO PUEDE SER EL MISMO DE FORMULA, UNO NUEVO
					m_GridTDStructure.setVariable(getM_StringFormulaField(),getTypeDataValueString(),observer.getTypeName(),observer.getFormat(),observer.getFormatter(),null,ce);
					/*m_Frame.getM_CMUndoMediator().addLinkElementEdit(m_GridTDStructure,ce,m_Frame.getm_TabbedPaneView(),
		        			CMMessages.getString("TESTDATA_ADD_LINKELEMENT"));
					m_Frame.getPanelTDStructureView().ScrollPaneStructureDescriptionView.update(CMIndexTDStructureUpdate.getInstance().getIndex());
					m_Frame.getGridTDStructure().update(CMIndexTDStructureUpdate.getInstance().getIndex());*/
				}
				else{
					/*ce.addEdit(CMModelEditFactory.INSTANCE.createChangeisLinkElementInTypeDataModelEdit(observer,false));
					observer.setLinkValue(false);*/
					if(m_TypeDataForInsertField == null){
						if(localValues){
							ce.addEdit(TDStructureManager.setFormulasTypeDataReferenceForLocalStructure(m_Frame, observer, getM_TypeData(), m_StringFormulaField, false,localReferences));
						}
						else{
							ce.addEdit(TDStructureManager.getTypeDataReferenceForGlobalStructure(m_Frame, observer, getM_TypeData(), m_StringFormulaField, false));
						}
					}
					getM_DialogFormulasValue().setTypeDataRefered(observer);
					getM_DialogFormulasValue().setTypeDataReferences(observer.getM_Subjects());
				}
				this.cancel();
			}
			else{
				JOptionPane.showMessageDialog(m_Frame, CMMessages.getString("TESTDATA_LINK_ELEMENTS_ERROR_CIRCULAR_REFERENCES"),
                        CMMessages.getString("TESTDATA_IMPORT_TITLE_ERROR"), JOptionPane.ERROR_MESSAGE); //$NON-NLS-1$ //$NON-NLS-2$
			}
		}
		}
		else{
			if (this.m_TypeData!=null){
				ITypeData observer=null;
				if(m_TypeDataForInsertField != null){
					observer= m_TypeDataForInsertField;
				}
				else{
				 observer=m_GridTDStructure.getSelectedTypeData();
				}
				if(!existCircularReferences(observer, m_TypeData)){
					boolean localValues=false;
					if(isReferenceInTheSameStructure(observer, m_TypeData)){
						CMDialogChooseGlobalOrLocalReference cmd= new CMDialogChooseGlobalOrLocalReference(m_Frame);
						cmd.setVisible(true);
						localValues= (cmd.isLocalSelected()&& cmd.isOkSelected());
					}
					this.setLocalSelectedInFormula(localValues);
					getM_DialogFormulasValue().addDialogLinkElementToCalculeFormula(this);
					this.cancel();
				}
			else{
				JOptionPane.showMessageDialog(m_Frame, CMMessages.getString("TESTDATA_LINK_ELEMENTS_ERROR_CIRCULAR_REFERENCES"),
                        CMMessages.getString("TESTDATA_IMPORT_TITLE_ERROR"), JOptionPane.ERROR_MESSAGE);
			}
			}	
		}
	}

	private boolean isReferenceInTheSameStructure(ITypeData observer, ITypeData typeData) {
		StructureTestData observerStructure=observer.getStructureTestData();
		StructureTestData typeDataStructure=typeData.getStructureTestData();
		if(observerStructure == typeDataStructure)
			return true;
		return false;
	}


	public void jButtonCancelActionPerformed(ActionEvent arg0) {
		this.m_GridTDStructure=null;
		this.m_StructureTestData = null;
		this.m_TDStructure = null;
		this.m_TypeData = null;
		this.m_StringFormulaField = "";
        cancel();
	}

	public void jListStructureValueChanged(ListSelectionEvent arg0) {
		this.fillFieldList(this.jListStructure.getSelectedIndex());
		this.jListField.setSelectedIndex(0);
		this.fillLabelValue(this.jListField.getSelectedIndex(),this.jListStructure.getSelectedIndex());


	}

	public void jListFieldValueChanged(ListSelectionEvent arg0) {
		this.fillLabelValue(this.jListField.getSelectedIndex(),this.jListStructure.getSelectedIndex());
	}


	public ITypeData getM_TypeData() {
		return m_TypeData;
	}


	public void setM_TypeData(ITypeData typeData) {
		m_TypeData = typeData;
	}

	public String getTypeDataValueString(){
		if(this.m_TypeData != null){
			//return this.m_TypeData.getStringValue();
			return this.m_TypeData.getFormattedValue();
		}
		return("");
	}


	public StructureTestData getM_StructureTestData() {
		return m_StructureTestData;
	}


	public void setM_StructureTestData(StructureTestData structureTestData) {
		m_StructureTestData = structureTestData;
	}


	public String getM_StringFormulaField() {
		return m_StringFormulaField;
	}


	public void setM_StringFormulaField(String stringFormulaField) {
		m_StringFormulaField = stringFormulaField;
	}

	/**
	 * @return Returns the openInDialogFormulas.
	 */
	public boolean isOpenInDialogFormulas() {
		return openInDialogFormulas;
	}


	/**
	 * @param openInDialogFormulas The openInDialogFormulas to set.
	 */
	public void setOpenInDialogFormulas(boolean openInDialogFormulas) {
		this.openInDialogFormulas = openInDialogFormulas;
	}
	/**
	 * @return Returns the m_DialogFormulasValue.
	 */
	private CMDialogFormulasValues getM_DialogFormulasValue() {
		return m_DialogFormulasValue;
	}


	/**
	 * @param dialogFormulasValue The m_DialogFormulasValue to set.
	 */
	public void setM_DialogFormulasValue(CMDialogFormulasValues dialogFormulasValue) {
		m_DialogFormulasValue = dialogFormulasValue;
	}
	private boolean existCircularReferences(ITypeData observer, ITypeData subject){
		//DelegateObservable observable=observer.getM_References();
		Vector observers=/*observable*/observer.getObservers();
		for (Iterator iter = observers.iterator(); iter.hasNext();) {
			ITypeData typedata = (ITypeData) iter.next();
			boolean result= existCircularReferences(typedata,subject);
			if(result){
				return result;
			}
		}
		if(observer == subject)
			return true;
		else
			return false;
	}


	public void setTypeDataForInsertField(ITypeData typeDataForInsertField) {
		m_TypeDataForInsertField=typeDataForInsertField;
	}
	private ITypeData m_TypeDataForInsertField;



//	HCanedo_23022006_begin
	/**
	 * @return Returns the localReferences.
	 */
	public HashMap<ITypeData, CMLocalReferenceConvert> getLocalReferences() {
		return localReferences;
	}


	/**
	 * @param localReferences The localReferences to set.
	 */
	public void setLocalReferences(
			HashMap<ITypeData, CMLocalReferenceConvert> localReferences) {
		this.localReferences = localReferences;
	}
	public void referenceValuesAfterFormulasIsSelected(boolean localValues){
	//	if (this.m_TypeData!=null){
		CMCompoundEdit ce = new CMCompoundEdit();
			ITypeData observer=null;
			if(m_TypeDataForInsertField != null){
				observer= m_TypeDataForInsertField;
			}
			else{
			 observer=m_GridTDStructure.getSelectedTypeData();
			}
			/*if(!existCircularReferences(observer, m_TypeData)){
				boolean localValues=false;
				if(isReferenceInTheSameStructure(observer, m_TypeData)){
					CMDialogChooseGlobalOrLocalReference cmd= new CMDialogChooseGlobalOrLocalReference(m_Frame);
					cmd.setVisible(true);
					localValues= cmd.isLocalSelected();
				}*/
				ce.addEdit(CMModelEditFactory.INSTANCE.createAddObserverInTypeDataModelEdit(observer,getM_TypeData(),getM_StringFormulaField()));
				getM_TypeData().addObserver(observer);
				observer.addSubject(getM_StringFormulaField(),getM_TypeData());
				if(!isOpenInDialogFormulas()){
					/*ce.addEdit(CMModelEditFactory.INSTANCE.createChangeisLinkElementInTypeDataModelEdit(observer,true));
					observer.setLinkValue(true);*/
					if(m_TypeDataForInsertField == null){
						if(localValues){
							ce.addEdit(TDStructureManager.setTypeDataReferenceForLocalStructure(m_Frame, observer, getM_TypeData(), m_StringFormulaField, true));
						}
						else{
							ce.addEdit(TDStructureManager.getTypeDataReferenceForGlobalStructure(m_Frame, observer, getM_TypeData(), m_StringFormulaField, true));
						}
					}
					m_GridTDStructure.setVariable(getM_StringFormulaField(),getTypeDataValueString(),observer.getTypeName(),observer.getFormat(),observer.getFormatter(),null,ce);
				}
				else{
					/*ce.addEdit(CMModelEditFactory.INSTANCE.createChangeisLinkElementInTypeDataModelEdit(observer,false));
					observer.setLinkValue(false);*/
					if(m_TypeDataForInsertField == null){
						if(localValues){
							ce.addEdit(TDStructureManager.setFormulasTypeDataReferenceForLocalStructure(m_Frame, observer, getM_TypeData(), m_StringFormulaField, false,localReferences));
						}
						else{
							ce.addEdit(TDStructureManager.getTypeDataReferenceForGlobalStructure(m_Frame, observer, getM_TypeData(), m_StringFormulaField, false));
						}
					}
					getM_DialogFormulasValue().setTypeDataRefered(observer);
					getM_DialogFormulasValue().setTypeDataReferences(observer.getM_Subjects());
				}
				this.cancel();
		//	}

		}
	//}
//	HCanedo_23022006_end


	/**
	 * @return Returns the localSelectedInFormula.
	 */
	public boolean isLocalSelectedInFormula() {
		return localSelectedInFormula;
	}


	/**
	 * @param localSelectedInFormula The localSelectedInFormula to set.
	 */
	public void setLocalSelectedInFormula(boolean localSelectedInFormula) {
		this.localSelectedInFormula = localSelectedInFormula;
	}
}
//svonborries_01112005_end
