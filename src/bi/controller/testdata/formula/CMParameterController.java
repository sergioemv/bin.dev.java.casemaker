package bi.controller.testdata.formula;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


import javax.swing.Action;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import org.apache.log4j.Logger;

import model.BusinessRules;
import model.CMDefaultParameter;
import model.CMDefaultValue;
import model.CMLinkElement;
import model.ICMFormula;
import model.ICMParameter;
import model.ICMValue;
import model.TestDataCombinations;

import model.util.CMModelEvent;
import model.util.CMModelListener;
import bi.controller.StructureManager;
import bi.controller.testdata.linkelement.CMLinkElementChooserGlobalLocalEditController;
import bi.controller.testdata.linkelement.CMLinkElementStructureEditController;
import bi.controller.testdata.linkelement.CMLinkElementTestDataEditController;
import bi.controller.testdata.variable.CMVariableEditController;

import bi.view.actions.CMAction;
import bi.view.lang.CMMessages;
import bi.view.mainframeviews.CMApplication;
import bi.view.mainframeviews.CMFrameView;

import bi.view.utils.CMDefaultDialog;
import bi.view.utils.CMModalResult;
import bi.view.utils.testdata.CMPanelFormulaParameter;

public class CMParameterController implements CMModelListener{

	private transient JPanel panelParameter = null;
	private ICMParameter value = null;
	private CMFormulaParameter parameter = null;
	private CMFormulasEditController child = null;
	private boolean isValidTypeOfDasta = true; 
	private CMFormulaEditController parent = null;
	
	public CMParameterController(CMFormulaParameter parameter){
		this.parameter = parameter;
	}

	public CMParameterController(ICMParameter parameter2) {
		value = parameter2;
		parameter = parameter2.getTypeParameter();
	}

	public JPanel getPanelParameter() {
		if(panelParameter == null){
			//if is necesary the creation of other parameter panel, use here one switch if, elseif.
			panelParameter = new CMPanelFormulaParameter();
			((CMPanelFormulaParameter)panelParameter).getButtonFormula().addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					addFormulaAsParameterAction();
				}
			});
			
			((CMPanelFormulaParameter)panelParameter).getButtonEdit().addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					editFormulaActionPerformed();
				}
			});
			
			((CMPanelFormulaParameter)panelParameter).getButtonLinkElement().addActionListener(new java.awt.event.ActionListener() {   
				public void actionPerformed(java.awt.event.ActionEvent e) {    
					addLinkElementAsParameterAction();
				}
			});
			
			((CMPanelFormulaParameter)panelParameter).getButtonDelete().addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					
					deleteFormulaActionPerformed();
				}
			});
			

			((CMPanelFormulaParameter)panelParameter).getButtonAddVariable().addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					addVariableActionPerformed();
				}
			});
			
			((CMPanelFormulaParameter)panelParameter).getTextParam().addKeyListener(new java.awt.event.KeyAdapter() {
				public void keyReleased(java.awt.event.KeyEvent e) {
					//set the value of the parameter
					if((e.getKeyCode() != KeyEvent.VK_LEFT)&&(e.getKeyCode() != KeyEvent.VK_RIGHT)&&(e.getKeyCode() != KeyEvent.VK_UP) && (e.getKeyCode() != KeyEvent.VK_DOWN))
						validateValue();
				}
			});
			
			//if(getValue() !=null){
				//((CMPanelFormulaParameter)panelParameter).setTextParam(getValue().toString());//veremos
				((CMPanelFormulaParameter)panelParameter).setTextLabel(getParameterEnum().getM_text());
				((ICMParameter)getICMParameter()).addModelListener(this);
				if(getValue()!=null){
					Object value = getValue();
					if(value instanceof CMDefaultValue){
						if(parameter.equals(CMFormulaParameter.YEAR))
							((CMPanelFormulaParameter)panelParameter).setTextParam(((CMDefaultValue)value).getValueString("####"));
						else if(parameter.equals(CMFormulaParameter.HOUR))
							((CMPanelFormulaParameter)panelParameter).setTextParam(((CMDefaultValue)value).getValueString("HH:mm:ss"));
						else
							((CMPanelFormulaParameter)panelParameter).setTextParam(((CMDefaultValue)value).getValueString());
					}
					else
						((CMPanelFormulaParameter)panelParameter).setTextParam(value.toString());
				}
				if(getValue() instanceof ICMFormula){
					enableDeleteButton(true);
					enableEditButton(true);
				}
			//}
		}
		return panelParameter;
	}
	
	public void setTextParam(String value){
		((CMPanelFormulaParameter)getPanelParameter()).setTextParam(value);
	}

	public void setPanelParameter(JPanel panelParameter) {
		this.panelParameter = panelParameter;
	}

	public CMFormulaParameter getParameterEnum() {
		return parameter;
	}

	public void setParameterEnum(CMFormulaParameter parameter) {
		this.parameter = parameter;
	}

	public Object getValue() {
		if(value == null)
			value = new CMDefaultParameter(getParameterEnum());
		return value.getObject();
	}
	
	public CMFormulaParameter getType(){
		return value.getTypeParameter();
	}

	public void setValue(ICMValue value) {
		getICMParameter().setResult(value);
	}
	
	public ICMParameter getICMParameter(){
		if(value == null)
			value = new CMDefaultParameter(getParameterEnum());
		return value;
	}
	
//	private void linkElementActionListener(ActionEvent e) {
//	if(CMApplication.frame.isIsPanelTestDataSelected()){
//		CMDialogLinkElementTestData cmd = new CMDialogLinkElementTestData();
//		this.setVisible(false);
//		cmd.setOpenInDialogFormulas(true);
//		//cmd.setM_DialogFormulasValue(this);
//		//cmd.setTypeDataForInsertField(m_TypeDataForInsertField);
//		cmd.setVisible(true);
//		executeCaretInJText(textParam,cmd.getM_StringFormulaField());
//		this.setVisible(true);
//	}
//	else{
//		CMDialogLinkElementStructure cmd = new CMDialogLinkElementStructure();
//		this.setVisible(false);
//		//HCanedo_23022006_begin
//		//cmd.setLocalReferences(this.localReferenceValues);
//		//Hcanedo_23022006_end
//		cmd.setOpenInDialogFormulas(true);
//		//cmd.setM_DialogFormulasValue(this);
//		//cmd.setTypeDataForInsertField(m_TypeDataForInsertField);
//		cmd.setVisible(true);
//		executeCaretInJText(textParam,cmd.getM_StringFormulaField());
//		this.setVisible(true);
//
//	}
//	
//}
	
	private void addFormulaAsParameterAction() {
		CMFormulasEditController formulas = new CMFormulasEditController();
		formulas.setParent(this);
		setChild(formulas);
		boolean sw = true;
		Container component = getPanelParameter().getParent();
		while(sw){
			if(component instanceof CMDefaultDialog){
				sw = false;
			}
			else{
				component = component.getParent();
			}
		}
		//((CMDefaultDialog)component).getJPanelContained().removeAll();
		//((CMDefaultDialog)component).getJPanelContained().revalidate();
		getParent().getParent().getPanelFormulas().setVisible(false);
		//((CMDefaultDialog)component).getJPanelContained().setVisible(false);
		((CMDefaultDialog)component).setJPanelContained(formulas.getPanelFormulas());
		formulas.getPanelFormulas().setVisible(true);
		//((CMDefaultDialog)component).getJPanelContained().repaint();
		//((CMDefaultDialog)component).getJPanelContained().revalidate();
		//((CMDefaultDialog)component).setVisible(true);
	}

	public void setChild(CMFormulasEditController formulas) {
		child  = formulas;
		
	}
	public CMFormulasEditController getChild() {
		if(child == null){
			if(getValue() instanceof ICMFormula)
				child = new CMFormulasEditController((ICMFormula) getValue());
			else
				child = new CMFormulasEditController();	
			child.setParent(this);
			setChild(child);	
		}	
		return child;
	}
	
	public void validateValue(){
	try {
		setValidTypeOfDasta(true);
		if(getValue() instanceof ICMFormula){
			return;
		}
		else{
			String evaluateValue = ((CMPanelFormulaParameter)panelParameter).getTextParam().getText();
			if(getParameterEnum() == CMFormulaParameter.YEAR){
				validateNumber(evaluateValue);
				return;
			}
			if(getParameterEnum() == CMFormulaParameter.MONTH){
				validateNumber(evaluateValue);
				return;
			}
			if(getParameterEnum() == CMFormulaParameter.DAY){
				validateNumber(evaluateValue);
				return;
			}
			if(getParameterEnum() == CMFormulaParameter.DATE){
				validateDate(evaluateValue, "yyyy/MM/dd");
				return;
			}
			if(getParameterEnum() == CMFormulaParameter.DATE1){
				validateDate(evaluateValue,"yyyy/MM/dd");
				return;
			}
			if(getParameterEnum() == CMFormulaParameter.DATE2){
				validateDate(evaluateValue,"yyyy/MM/dd");
				return;
			}
			if(getParameterEnum() == CMFormulaParameter.HOUR){
				validateDate(evaluateValue, "HH:mm:ss");
				return;
			}
			if(getParameterEnum() == CMFormulaParameter.NUMBER){
				validateNumber(evaluateValue);
				return;
			}
			if(getParameterEnum() == CMFormulaParameter.NUMBERX){
				validateNumber(evaluateValue);
				return;
			}
			if(getParameterEnum() == CMFormulaParameter.NUMBERY){
				validateNumber(evaluateValue);
				return;
			}
			if(getParameterEnum() == CMFormulaParameter.DECIMAL){
				validateNumber(evaluateValue);
				return;
			}
			if(getParameterEnum() == CMFormulaParameter.BASE){
				validateNumber(evaluateValue);
				return;
			}
			if(getParameterEnum() == CMFormulaParameter.POWER){
				validateNumber(evaluateValue);
				return;
			}
			if(getParameterEnum() == CMFormulaParameter.LOCATIONSTRING){
				validateNumber(evaluateValue);
				return;
			}
			if(getParameterEnum() == CMFormulaParameter.ANGLE){
				validateNumber(evaluateValue);
				return;
			}
			if(getParameterEnum() == CMFormulaParameter.GRADE){
				validateNumber(evaluateValue);
				return;
			}
			if(getParameterEnum() != CMFormulaParameter.STRING ||getParameterEnum() != CMFormulaParameter.STRING1 ||
					getParameterEnum() != CMFormulaParameter.STRING2 || getParameterEnum() != CMFormulaParameter.OLDSTRING ||
					getParameterEnum() != CMFormulaParameter.NEWSTRING || getParameterEnum() != CMFormulaParameter.COMPARESTRING
					|| getParameterEnum() != CMFormulaParameter.NULLPARAM){
					CMDefaultValue defaultValue = new CMDefaultValue(evaluateValue);
					setValue(defaultValue);
					setValidTypeOfDasta(false);
				return;
//				JOptionPane.showMessageDialog(CMApplication.frame, CMMessages.getString("FORMULAS_ERROR_FIELDS_EMPTY"),
//	                    CMMessages.getString("TESTDATA_TITLE_MENSSAGE_ERROR"), JOptionPane.ERROR_MESSAGE);
			} 
			}
	}catch (Exception e) {
		setValue(null);
        setValidTypeOfDasta(false);
		}

	}
	
	private void validateDate(String p_value, String p_pattern){
	     try {
		     SimpleDateFormat sdf = new SimpleDateFormat(p_pattern);
		     sdf.setLenient(false);
			 Date dt2 = sdf.parse(p_value);
			 CMDefaultValue defaultValue = new CMDefaultValue(dt2);
			 setValue(defaultValue);
		} catch (ParseException e) {
			setValidTypeOfDasta(false);
			setValue(null);
		}
	}
	
	@SuppressWarnings("unused")
	private void validateDate(String p_value){
		   try {
			   DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT);
			   df.setLenient(false);  // this is important!
			   Date dt2 = df.parse(p_value);
			   CMDefaultValue defaultValue = new CMDefaultValue(dt2);
			   setValue(defaultValue);
		} catch (ParseException e) {
			setValidTypeOfDasta(false);
			setValue(null);
		}
	}
	
	private void validateNumber(String p_value){
		try {
			if(p_value.endsWith(",") || p_value.endsWith(".")){
				CMDefaultValue defaultValue = new CMDefaultValue(p_value);
		    	setValue(defaultValue);
		    	((CMPanelFormulaParameter)panelParameter).setTextParam(((CMDefaultValue)defaultValue).getValueString());
		    	return;
			}
			if(p_value.endsWith("0") && (p_value.indexOf(".") != -1) && (p_value.indexOf(",") != -1)){
				CMDefaultValue defaultValue = new CMDefaultValue(p_value);
				setValue(defaultValue);
				((CMPanelFormulaParameter)panelParameter).setTextParam(((CMDefaultValue)defaultValue).getValueString());
				return;
			}
	    	DecimalFormatSymbols symbols = new DecimalFormatSymbols();
	    	if(StructureManager.getSelectedStructure().getTestObject().isDefaultSeparator()){
	    		symbols.setDecimalSeparator('.');
	    		symbols.setGroupingSeparator(',');
	    	}
	    	else{
	    		symbols.setDecimalSeparator(StructureManager.getSelectedStructure().getTestObject().getDecimalSeparator().charAt(0));
	    		symbols.setGroupingSeparator(StructureManager.getSelectedStructure().getTestObject().getMilesSeparator().charAt(0));
	    	}
	    	DecimalFormat formatter;
	    	if(getParameterEnum() == CMFormulaParameter.YEAR)
	    		formatter = new DecimalFormat("####");
	    	else
	    		formatter = new DecimalFormat(BusinessRules.FORMULAS_FORMAT_DEFAULT_MATH_TRIGONOMETRY,symbols);
	    	Number value = formatter.parse(p_value);
	    	CMDefaultValue defaultValue = new CMDefaultValue(value);
	    	setValue(defaultValue);
	    	if(parameter.equals(CMFormulaParameter.YEAR))
	    		((CMPanelFormulaParameter)panelParameter).setTextParam(((CMDefaultValue)defaultValue).getValueString("####"));
	    	else 
	    		((CMPanelFormulaParameter)panelParameter).setTextParam(((CMDefaultValue)defaultValue).getValueString());
//			
//			
//			if(StructureManager.getSelectedStructure().getTestObject().getDecimalSeparator() == "."){
//				Locale locale = Locale.US; 
//				Number value = NumberFormat.getNumberInstance(locale).parse(p_value);
//				setValue(value);
//			}
//			else{
//				Locale locale = Locale.GERMAN;
//				Number value = NumberFormat.getNumberInstance(locale).parse(p_value);
//				setValue(value);
//			}
		} catch (ParseException e) {
			Logger.getLogger(this.getClass()).error(e);
			setValue(null);
			setValidTypeOfDasta(false);
		}

		
//		try {
//			setValue(Integer.parseInt((String) getValue()));
//			//setValue(value);
//			setValidTypeOfDasta(true);
//		} catch (Exception e) {
//			try {
//				Long value = Long.parseLong((String) getValue());
//				setValue(value);
//				setValidTypeOfDasta(true);
//			} catch (Exception e1) {
//				try {
//					Double value = Double.parseDouble((String) getValue());
//					setValue(value);
//					setValidTypeOfDasta(true);
//				} catch (Exception e2) {
//					try {
//						Float value = Float.parseFloat((String) getValue());
//						setValue(value);
//						setValidTypeOfDasta(true);
//					} catch (Exception e3) {
//						setValidTypeOfDasta(false);
//					}
//				}
//			}
//		}
	}

	public boolean isValidTypeOfDasta() {
		return isValidTypeOfDasta;
	}

	public void setValidTypeOfDasta(boolean isValidTypeOfDasta) {
		this.isValidTypeOfDasta = isValidTypeOfDasta;
	}

	public CMFormulaEditController getParent() {
		return parent;
	}

	public void setParent(CMFormulaEditController parent) {
		this.parent = parent;
	}

	public void handleCMModelChange(CMModelEvent evt) {
		boolean enable = false;
		if(evt.getSource() instanceof ICMParameter){
			if(getICMParameter().getObject() instanceof ICMFormula){
				enable = true;
				enableEditButton(enable);
				enableDeleteButton(enable);
				return;
			}
			boolean sw = true;
			Object parentOfThisStructtureofFormula = getParent();
			while(sw){
				if(parentOfThisStructtureofFormula instanceof CMFormulasEditController){
					sw = false;
				}
				else if(parentOfThisStructtureofFormula instanceof CMFormulaEditController){
					parentOfThisStructtureofFormula = ((CMFormulaEditController)parentOfThisStructtureofFormula).getParent();
				}
				else if(parentOfThisStructtureofFormula instanceof CMParameterController){
					parentOfThisStructtureofFormula = ((CMParameterController)parentOfThisStructtureofFormula).getParent();
				}	
			}
			CMFormulasEditController.updatePanelPreview(((CMFormulasEditController)parentOfThisStructtureofFormula));
		}
	}
	
	public void enableEditButton(boolean enable){
		((CMPanelFormulaParameter)getPanelParameter()).getButtonEdit().setEnabled(enable);
	}
	
	public void enableDeleteButton(boolean enable){
		((CMPanelFormulaParameter)getPanelParameter()).getButtonDelete().setEnabled(enable);
	}
	

	private void editFormulaActionPerformed() {
		boolean sw;
		Container component = getParent().getPanelParameters();
		sw = true;
		while(sw){//begin the search of the container dialog 
			if(component instanceof CMDefaultDialog){
				sw = false;
			}
			else{
				component = component.getParent();
			}
		}
		((CMDefaultDialog)component).getJPanelContained().setVisible(false);
		((CMDefaultDialog)component).setJPanelContained((getChild()).getPanelFormulas());
		(getChild()).getPanelFormulas().setVisible(true);
		getChild().getChild().enableAddButton(true);
		getChild().getChild().enableDeleteFormulaButton(true);
	}

	private void deleteFormulaActionPerformed() {
		setChild(null);
		setValue(null);
		setTextParam("");
		enableDeleteButton(false);
		enableEditButton(false);
		boolean sw = true;
		Object formulasEditController = getParent();
		while (sw){
			if(formulasEditController instanceof CMFormulasEditController)
				sw = false;
			else if(formulasEditController instanceof CMFormulaEditController)
				formulasEditController = ((CMFormulaEditController)formulasEditController).getParent();
			else if(formulasEditController instanceof CMParameterController)
				formulasEditController = ((CMParameterController)formulasEditController).getParent();
		}
		CMFormulasEditController.updatePanelPreview((CMFormulasEditController) formulasEditController);
	}
	

	private void addLinkElementAsParameterAction() {
		CMFrameView m_frame = CMApplication.frame;
		CMDefaultDialog dlg = new CMDefaultDialog();
		
		if(m_frame.isIsPanelTestDataSelected()){
			CMLinkElementTestDataEditController testdataEditController = new CMLinkElementTestDataEditController();
			dlg.setJPanelContained(testdataEditController.getPanelLinkElementTestData());
			dlg.setSize(new Dimension(653, 364));
			dlg.setTitle(CMMessages.getString("TESTDATA_TITLE_DIALOG_LINK_ELEMENT"));
			dlg.setVisible(true);
			boolean sw = true;
			while(sw){
			if(dlg.getModalResult() == CMModalResult.OK){

				CMLinkElement linkElement = new CMLinkElement(testdataEditController.getM_StringFormulaField());
				ICMValue tempValue = testdataEditController.getM_DestinationTypeData().getValue();
				testdataEditController.getM_DestinationTypeData().setValue(linkElement);

					if(linkElement.existCircularReference(null)){
						testdataEditController.getM_DestinationTypeData().setValue(null);
						JOptionPane.showMessageDialog(m_frame, CMMessages.getString("TESTDATA_LINK_ELEMENTS_ERROR_CIRCULAR_REFERENCES"),
	                        CMMessages.getString("TESTDATA_IMPORT_TITLE_ERROR"), JOptionPane.ERROR_MESSAGE);
						testdataEditController.getM_DestinationTypeData().setValue(tempValue);
						dlg.setVisible(true);
					}
					else{
						sw = false;
						setValue(linkElement);
						setTextParam(linkElement.toString());
						testdataEditController.getM_DestinationTypeData().setValue(null);
					}
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
			dlg.setTitle(CMMessages.getString("TESTDATA_TITLE_DIALOG_LINK_ELEMENT"));
			dlg.setVisible(true);
			boolean sw = true;
			while(sw){
			if(dlg.getModalResult() == CMModalResult.OK){

					CMLinkElement linkElement = new CMLinkElement(structureEditController.getM_StringFormulaField());
					ICMValue tempValue = structureEditController.getM_DestinationTypeData().getValue();
					structureEditController.getM_DestinationTypeData().setValue(linkElement);
					
					if(linkElement.existCircularReference(null)){
						
						structureEditController.getM_DestinationTypeData().setValue(null);
						JOptionPane.showMessageDialog(m_frame, CMMessages.getString("TESTDATA_LINK_ELEMENTS_ERROR_CIRCULAR_REFERENCES"),
								CMMessages.getString("TESTDATA_IMPORT_TITLE_ERROR"), JOptionPane.ERROR_MESSAGE);
						structureEditController.getM_DestinationTypeData().setValue(tempValue);
						dlg.setVisible(true);
					}
					else{
						if(canShowChooserDialog()){
							boolean sw1 = true;
							while(sw1){
							CMDefaultDialog dd = new CMDefaultDialog();
							CMLinkElementChooserGlobalLocalEditController controller = new CMLinkElementChooserGlobalLocalEditController();
							dd.setJPanelContained(controller.getPanelGlobalLocal());
							dd.setSize(280, 200);
							dd.setResizable(false);
							dd.setTitle(m_frame.getGridTDStructure().getSelectedTypeData().getField());
							dd.setVisible(true);
							if(dd.getModalResult() == CMModalResult.OK){
								if(controller.isGlobalSelected()){
									linkElement.setGlobal(controller.isGlobalSelected());
									sw1 = false;
								}
								else{
									int confirm = JOptionPane.OK_OPTION;
									confirm=JOptionPane.showConfirmDialog(m_frame,CMMessages.getString("LINK_ELEMENTS_VALUE_DIALOG_MESSAGE"),CMMessages.getString("LINK_ELEMENTS_VALUE_WARNING_TITLE"),JOptionPane.INFORMATION_MESSAGE,JOptionPane.YES_NO_CANCEL_OPTION);
									if(confirm == JOptionPane.YES_OPTION){
										linkElement.setGlobal(false);
										sw1 = false;
									}
									if(confirm == JOptionPane.NO_OPTION){
										linkElement.setGlobal(true);
										sw1 = false;
									}
								}
							}
							else if(dd.getModalResult() == CMModalResult.CANCEL){
								sw1 = false;
								structureEditController.getM_DestinationTypeData().setValue(null);
								setValue(null);
								return;
							}
						}
						}
						sw = false;
						setTextParam(linkElement.toString());
						setValue(linkElement);
						structureEditController.getM_DestinationTypeData().setValue(null);
					}
				}
			else if(dlg.getModalResult() == CMModalResult.CANCEL){
				dlg.dispose();
				sw = false;
			}
			}
		}
		dlg.dispose();
	}
	

	private void addVariableActionPerformed() {
		CMVariableEditController variableController = new CMVariableEditController();
		CMDefaultDialog dd = new CMDefaultDialog();
		dd.setJPanelContained(variableController.getPanelVariable());
		dd.setSize(491,394);
		
		dd.setResizable(false);
		dd.setModal(true);
		dd.setTitle(CMApplication.frame.getTreeWorkspaceView().getCurrentTestObject().getName()+" - "+CMAction.TESTDATA_ADD_VARIABLE.getAction().getValue(Action.NAME));
		dd.setVisible(true);
		if(dd.getModalResult() == CMModalResult.OK){
			CMLinkElement linkElement = (CMLinkElement) variableController.getLinkElement();
			setTextParam(linkElement.toString());
			setValue(linkElement);
			
			//ITypeData typeData = null;
			//CMGridTDStructure m_GridTDStructure;
//			if(CMApplication.frame.isIsPanelTestDataSelected()){
//				m_GridTDStructure = CMApplication.frame.getPanelTestDataView().getM_CMGridTDStructure();
//			}
//			else{
//				m_GridTDStructure = CMApplication.frame.getGridTDStructure();
//			}
			//typeData = m_GridTDStructure.getSelectedTypeData();
			//TODO add Compound here
			//typeData.setValue(linkElement);
			
//			m_GridTDStructure.getCmGridModel().setValueAt(new CMCellTDStructureValue(m_GridTDStructure.getTDStructure(),typeData.getFormattedValue()), 
//    				m_GridTDStructure.getRowSelected(), m_GridTDStructure.getColumnSelected()+1);
////    		set the viewmodel of the Comul Formula, with the toString of the formula
//    		m_GridTDStructure.getCmGridModel().setValueAt(new CMCellTDStructureFormula(m_GridTDStructure.getTDStructure(),typeData.getValue().toString()), 
//    				m_GridTDStructure.getRowSelected(), m_GridTDStructure.getColumnSelected());
//    		if(linkElement.getObjectLinked() instanceof Variable){
//    			Variable variable = (Variable) linkElement.getObjectLinked();
////    			TODO add Compound here
//    			typeData.setFormat(variable.getM_Format());
//    			typeData.setM_Formatter(variable.getM_Formatter());
//    			typeData.setType(variable.getM_Type());
//    			typeData.setLength(CMCellTDStructureClassState.getDefaultValue(typeData.getType()));
//    		
//    			//refresh the Cell changed in the Grid
//    			m_GridTDStructure.getCmGridModel().setValueAt(new CMCellTDStructureFormat(m_GridTDStructure.getTDStructure(),typeData.getFormat()), 
//        				m_GridTDStructure.getRowSelected(), m_GridTDStructure.getColumnSelected()-1);
//    			m_GridTDStructure.getCmGridModel().setValueAt(new CMCellTDStructureLength(m_GridTDStructure.getTDStructure(),typeData.getLength()), 
//        				m_GridTDStructure.getRowSelected(), m_GridTDStructure.getColumnSelected()-4);
//    			m_GridTDStructure.update(CMIndexTDStructureUpdate.getInstance().getIndex());
//    			
//    		}
    			
		}
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
