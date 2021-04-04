package bi.controller;

import javax.swing.undo.UndoableEdit;

import model.CMDefaultValue;
import model.CMLinkElement;
import model.ICMFormula;
import model.ICMValue;
import model.ITypeData;
import model.TestDataFormat;
import model.Variable;
import model.edit.CMModelEditFactory;
import bi.view.mainframeviews.CMApplication;
import bi.view.testdataviews.CMGridTDStructure;
import bi.view.undomanagementviews.CMCompoundEdit;

public class FormulaManager {
	

	private static CMFormulaCalculator calculator;

	public static UndoableEdit calculateFormulasOrVariablesInGenerationOfTestData(String param,ITypeData typeData,CMGridTDStructure gridtdstructure){
		CMCompoundEdit ce = new CMCompoundEdit();
		
        if(param.startsWith("$")){
        	String valueString = VariablesManager.returnImplicitExplicitVariable(param,CMApplication.frame.getGridTDStructure().getTDStructure());
        	if(valueString.equalsIgnoreCase("")){
        		//CMDefaultValue defaultValue = new CMDefaultValue(param);
        		//ce.addEdit(CMModelEditFactory.INSTANCE.createChangeValueInTypeDataModelEdit(typeData,defaultValue));
                //typeData.setValue(defaultValue);
        		//svonborries_18072007_begin
        		Variable variable = VariablesManager.getVariable(param);
        		CMLinkElement linkElement = new CMLinkElement(param);
        		ce.addEdit(CMModelEditFactory.INSTANCE.createChangeValueInTypeDataModelEdit(typeData, linkElement));
        		typeData.setValue(linkElement);
        		TestDataFormat tdFormat = variable.getFormatter();
        		ce.addEdit(CMModelEditFactory.INSTANCE.createChangeFormatterInTypeDataModelEdit(typeData, tdFormat));
        		typeData.setFormatter(tdFormat);
        		ce.addEdit(CMModelEditFactory.INSTANCE.createChangeFormatInTypeDataModelEdit(typeData, tdFormat.getVisualFormatter()));
        		typeData.setFormat(tdFormat.getVisualFormatter());
        		//svonborries_18072007_end
        	}
        	else{
        		CMDefaultValue defaultValue = new CMDefaultValue(valueString);
        		ce.addEdit(CMModelEditFactory.INSTANCE.createChangeValueInTypeDataModelEdit(typeData,defaultValue));
                typeData.setValue(defaultValue);
/*                String formulaValue = param.substring(1);
                ce.addEdit(CMModelEditFactory.INSTANCE.createChangeFormulaInTypeDataModelEdit(typeData, formulaValue));
                typeData.setFormula(formulaValue);*/
        	}
        }
        else{
        	calculator = new CMFormulaCalculator(gridtdstructure);
        	ICMValue result = calculator.calculeCompexFormula(param);
        	if((result.toString()).equalsIgnoreCase(param)){
        		//CMDefaultValue defaultValue = new CMDefaultValue(param);
                //ce.addEdit(CMModelEditFactory.INSTANCE.createChangeValueInTypeDataModelEdit(typeData,defaultValue));
                //typeData.setValue(defaultValue);
        		ce.addEdit(CMModelEditFactory.INSTANCE.createChangeValueInTypeDataModelEdit(typeData,result));
        		typeData.setValue(result);
        	}
        	else{
        		//CMDefaultValue defaultValue = new CMDefaultValue(result);
        		if(result instanceof ICMFormula){
        			ce.addEdit(CMModelEditFactory.INSTANCE.createChangeValueInTypeDataModelEdit(typeData,result));
                    typeData.setValue(result);
                    //svonborries_17072007_begin
                    TestDataFormat tdFormat = new TestDataFormat();
                    tdFormat.setOriginalFormatter(((ICMFormula)result).getFormulaEnum().getDefaultPattern());
                    tdFormat.setRealFormat(((ICMFormula)result).getFormulaEnum().getDefaultPattern());
                    tdFormat.setVisualFormatter(((ICMFormula)result).getFormulaEnum().getDefaultPattern());
                    ce.addEdit(CMModelEditFactory.INSTANCE.createChangeFormatterInTypeDataModelEdit(typeData, tdFormat));
                    typeData.setFormatter(tdFormat);
                    ce.addEdit(CMModelEditFactory.INSTANCE.createChangeFormatInTypeDataModelEdit(typeData, tdFormat.getVisualFormatter()));
                    typeData.setFormat(tdFormat.getVisualFormatter());
                    //svonborries_17072007_end
        		}
        		else{
        			//CMDefaultValue defaultValue = new CMDefaultValue(result);
        			ce.addEdit(CMModelEditFactory.INSTANCE.createChangeValueInTypeDataModelEdit(typeData,result));
                    typeData.setValue(result);
        		}
        		
/*                ce.addEdit(CMModelEditFactory.INSTANCE.createChangeFormulaInTypeDataModelEdit(typeData, param));
                typeData.setFormula(param);
                ce.addEdit(CMModelEditFactory.INSTANCE.createChangeisFormulaInTypeDataModelEdit(typeData, true));
                typeData.setisFormula(true);*/
        	}
        }
		return ce;
	}

}
