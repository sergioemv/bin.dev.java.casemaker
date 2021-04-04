/**
 * 24/10/2006
 * svonborries
 */
package bi.controller.testdata.formula;

import java.awt.Container;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;


import model.ICMFormula;
import model.ICMParameter;
import model.util.CMModelEvent;
import model.util.CMModelListener;
import bi.view.lang.CMMessages;
import bi.view.mainframeviews.CMApplication;
import bi.view.utils.CMDefaultDialog;
import bi.view.utils.testdata.CMPanelFormulaParameter;
import bi.view.utils.testdata.CMPanelFormulaParameters;
/**
 * @author svonborries
 *
 */
public class CMFormulaEditController implements CMModelListener{

	private CMPanelFormulaParameters panelParameters = null;
	private ICMFormula formula = null;
	private List<CMParameterController> parameters = null;
	private CMFormulasEditController parent = null;

	public CMFormulaEditController(ICMFormula formula2){
		formula = formula2;
		//getPanelParameters();
	}

	public CMPanelFormulaParameters getPanelParameters() {
		if(panelParameters == null){
			panelParameters = new CMPanelFormulaParameters();
			panelParameters.getButtonAdd().addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					addFormulaAsParameter();
				}
			});
			panelParameters.getButtonDelete().addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					deleteFormula();
				}
			});
			parameters = new ArrayList<CMParameterController>();
			for(CMFormulaParameter formulaParameter: getFormula().getFormulaEnum().getAllowedParam()){
				//if(getFormula().getParameter(formulaParameter) instanceof String){
					if(getFormula().getParameter(formulaParameter) instanceof String){
						if(formulaParameter != CMFormulaParameter.NULLPARAM){
							CMParameterController parameter = new CMParameterController(formulaParameter);
							getFormula().addParameter(parameter.getICMParameter());
							parameters.add(parameter);
							parameter.setParent(this);
							parameter.getICMParameter().addModelListener(this);
							panelParameters.addPanelParameter(parameter.getPanelParameter());
						}
					}
				else{
					if(formulaParameter != CMFormulaParameter.NULLPARAM){
						CMParameterController parameter = null;
						parameter = new CMParameterController((ICMParameter)getFormula().getParameter(formulaParameter));
						parameters.add(parameter);
						parameter.setParent(this);
						parameter.getICMParameter().addModelListener(this);
						panelParameters.addPanelParameter(parameter.getPanelParameter());
					}
				}
			}
		}
		return panelParameters;
	}

	public ICMFormula getFormula() {
		return formula;
	}

	public void setFormula(ICMFormula formula) {
		this.formula = formula;
	}

	public List<CMParameterController> getParameters() {
		if(parameters == null)
			getPanelParameters();
		return parameters;
	}

	public void setParameters(List<CMParameterController> parameters) {
		this.parameters = parameters;
	}


	private void addFormulaAsParameter() {
		//validation of parameters
		String parametersError = null;
		for(CMParameterController param: getParameters()){
			//param.validateValue();//validate the parameter, and asign a TypeOfData in the ParameterController
			//getFormula().addParameter(param.getDefaultParameter());//add parameter to the map of parameters of Formula
			if(param.isValidTypeOfDasta() == false){//if is an invalid type of data
				if(parametersError == null){//fill errors in string
					parametersError = new String(param.getParameterEnum().getM_text());
				}
				else{
					parametersError = parametersError +", " + param.getParameterEnum().getM_text();
				}
			}
		}
		if(parametersError == null){//if not errors in String
			boolean sw = true;
			Object formulaParameter = getParent();
			int sw1 = 0;
			while(sw){//search until it finds the parameterController that will contains this formula as parameter
				if(formulaParameter instanceof CMFormulasEditController){
						formulaParameter = ((CMFormulasEditController)formulaParameter).getParent();
				}
				else if(formulaParameter instanceof CMFormulaEditController){
					formulaParameter = ((CMFormulaEditController)formulaParameter).getParent();
				}
				else if(formulaParameter instanceof CMParameterController){
					sw = false;
				}
			}
			getParent().getParent().setTextParam(getFormula().toString());
			((CMParameterController)formulaParameter).setValue(getFormula());//asign the formula to the parent that is a ParameterController
			//asign the formula as a parameter in the parent of the ParameterController that search the while above, this is necessary
			//to be done, because in this way we can display a correct formulaPreview
			//((CMParameterController)formulaParameter).getParent().getFormula().addParameter(getFormula());


			Container component = getParent().getPanelFormulas();
			sw = true;
			while(sw){//begin the search of the container dialog
				if(component instanceof CMDefaultDialog){
					sw = false;
				}
				else{
					component = component.getParent();
				}
			}
			sw = true;
			Object formulas = getParent();
			sw1 = 0;
			while(sw){//begin the search of the second CMFormulasEditController, that is the parent of all this structure
				if(formulas instanceof CMFormulasEditController){
					sw1+=1;
					if(sw1==2){
						sw = false;
					}
					else{
						formulas = ((CMFormulasEditController)formulas).getParent();
					}
				}
				else if(formulas instanceof CMFormulaEditController){
					formulas = ((CMFormulaEditController)formulas).getParent();
				}
				else if(formulas instanceof CMParameterController){
					formulas = ((CMParameterController)formulas).getParent();
				}
			}
			//all the process of the replacement of the panel that contains the CMDefaultDialog
			((CMDefaultDialog)component).getJPanelContained().setVisible(false);
			((CMDefaultDialog)component).setJPanelContained(((CMFormulasEditController)formulas).getPanelFormulas());
			//getParent().getParent().setTextParam(getFormula().toString());
			((CMFormulasEditController)formulas).getPanelFormulas().setVisible(true);
			sw = true;
			Object formulasPrev = getParent();
			while(sw){//find the first CMFormulasEditController that contains the first ICMFormula Object, then take the toString()
				//to get the formula structure, and set it to the current CMFormulasEditController
				if(formulasPrev instanceof CMFormulasEditController){
					if(((CMFormulasEditController)formulasPrev).getParent() == null){
						String formulaPreview = ((CMFormulasEditController)formulas).getChild().getFormula().toString() + "    = " + ((CMFormulasEditController)formulas).getChild().getFormula().getFormattedValueResult();
						((CMFormulasEditController)formulas).setTextAreaPreview(formulaPreview);
						sw = false;
					}
					else{
						formulasPrev = ((CMFormulasEditController)formulasPrev).getParent();
					}
				}
				else if(formulasPrev instanceof CMFormulaEditController){
					formulasPrev = ((CMFormulaEditController)formulasPrev).getParent();
				}
				else if(formulasPrev instanceof CMParameterController){
					formulasPrev = ((CMParameterController)formulasPrev).getParent();
				}
			}
		}
		else{
          JOptionPane.showMessageDialog(CMApplication.frame, CMMessages.getString("FORMULAS_ERROR_FIELDS_EMPTY")+parametersError,
          CMMessages.getString("TESTDATA_TITLE_MENSSAGE_ERROR"), JOptionPane.ERROR_MESSAGE);
		}
	}

	public void setParent(CMFormulasEditController controller) {
		parent  = controller;
	}

	public CMFormulasEditController getParent() {
		return parent;
	}

	public void handleCMModelChange(CMModelEvent evt) {
		if(evt.getSource() instanceof ICMParameter){
			boolean enable = false;
			for(CMParameterController param: getParameters()){
				if(((CMPanelFormulaParameter)param.getPanelParameter()).getTextParam().getText().equalsIgnoreCase("")){
					enable = false;
				}
				else if(((CMFormulasEditController)getParent()).getParent() != null)
					enable = true;
			}
			enableAddButton(enable);
			enableDeleteFormulaButton(enable);
		}

	}

	public void enableAddButton(boolean enable){
			getPanelParameters().getButtonAdd().setEnabled(enable);
	}

	public void enableDeleteFormulaButton(boolean enable){
		getPanelParameters().getButtonDelete().setEnabled(enable);
	}

	private void deleteFormula() {

		boolean sw = true;
		sw = true;
		Object formulasParentAllStructure = getParent();
		int sw1 = 0;
		while(sw){//begin the search of the second CMFormulasEditController, that is the parent of all this structure
			if(formulasParentAllStructure instanceof CMFormulasEditController){
				sw1+=1;
				if(sw1==2){
					sw = false;
				}
				else{
					formulasParentAllStructure = ((CMFormulasEditController)formulasParentAllStructure).getParent();
				}
			}
			else if(formulasParentAllStructure instanceof CMFormulaEditController){
				formulasParentAllStructure = ((CMFormulaEditController)formulasParentAllStructure).getParent();
			}
			else if(formulasParentAllStructure instanceof CMParameterController){
				formulasParentAllStructure = ((CMParameterController)formulasParentAllStructure).getParent();
			}
		}
//
//		for(CMParameterController paramControl: getParameters()){
//			paramControl.setValue(null);
//		}
//		setFormula(null);
		for(CMParameterController paramController: ((CMFormulasEditController)formulasParentAllStructure).getChild().getParameters()){
			if(paramController.getValue() == getFormula()){
				paramController.setValue(null);
				paramController.setTextParam("");
			}
		}
		//setParameters(null);

		Container component = getParent().getPanelFormulas();
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
		((CMDefaultDialog)component).setJPanelContained(((CMFormulasEditController)formulasParentAllStructure).getPanelFormulas());
		((CMFormulasEditController)formulasParentAllStructure).getPanelFormulas().setVisible(true);
		//((CMFormulasEditController)formulas).setChild(null);


		//((CMFormulasEditController)formulas).getPanelFormulas().getListCategories().setSelectedIndex(0);
		CMFormulasEditController.updatePanelPreview((CMFormulasEditController)formulasParentAllStructure);
	}

	public CMFormulasEditController searchFirstParentFormulasEditController(){
		Object formulasPrev = getParent();
		boolean sw = true;
		while(sw ){//find the first CMFormulasEditController that contains the first ICMFormula Object, then take the toString()
			//to get the formula structure, and set it to the current CMFormulasEditController
			if(formulasPrev instanceof CMFormulasEditController){
				if(((CMFormulasEditController)formulasPrev).getParent() == null){
					sw = false;
				}
				else{
					formulasPrev = ((CMFormulasEditController)formulasPrev).getParent();
				}
			}
			else if(formulasPrev instanceof CMFormulaEditController){
				formulasPrev = ((CMFormulaEditController)formulasPrev).getParent();
			}
			else if(formulasPrev instanceof CMParameterController){
				formulasPrev = ((CMParameterController)formulasPrev).getParent();
			}
		}
		return (CMFormulasEditController) formulasPrev;
	}

}
