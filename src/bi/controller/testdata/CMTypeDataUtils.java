/**
 * 05/01/2007
 * svonborries
 */
package bi.controller.testdata;

import java.util.ArrayList;
import java.util.List;

import javax.swing.undo.UndoableEdit;

import model.CMDefaultParameter;
import model.CMLinkElement;
import model.ICMFormula;
import model.ITypeData;

import model.StructureTestData;
import model.TestData;

import model.TypeDataGlobal;

import model.edit.CMModelEditFactory;
import bi.controller.testdata.formula.CMFormulaParameter;
import bi.view.lang.CMMessages;
import bi.view.mainframeviews.CMApplication;
import bi.view.testdataviews.CMGridTDStructure;
import bi.view.undomanagementviews.CMCompoundEdit;

/**
 * @author svonborries
 *
 */
public class CMTypeDataUtils {
	
	
	public static UndoableEdit updateTypeDataValueForLocalTypeDatas(ITypeData typeData) {
		CMCompoundEdit ce = new CMCompoundEdit();
		if(typeData instanceof TypeDataGlobal){
			if(typeData.getGlobal().equalsIgnoreCase("G")){
				int indexStructureTestdata = 0;
				StructureTestData std = typeData.getStructureTestData();
				indexStructureTestdata = std.getGlobalIndex();
				CMGridTDStructure gridLocal = CMApplication.frame.getPanelTestDataView().getM_CMGridTDStructure();
				for(Object testData: gridLocal.getTDStructure().getTestDataCombination().getM_TestDatas()){
					for(Object stdl:((TestData)testData).getM_TDStructure().getM_StructureTestData()){
						if(((StructureTestData)stdl).getGlobalIndex() == indexStructureTestdata){
							for(Object typeDataLocal: ((StructureTestData)stdl).getTypeData()){
								if(((ITypeData)typeDataLocal).getField().equalsIgnoreCase(typeData.getField())){
									if(((ITypeData)typeDataLocal).getGlobal().equalsIgnoreCase("G")){
										ce.addEdit(CMModelEditFactory.INSTANCE.createChangeValueInTypeDataModelEdit((ITypeData)typeDataLocal, typeData.getValue()));
										((ITypeData)typeDataLocal).setValue(typeData.getValue());
									}
								}
							}
						}
					}
				}
			}
		}
		return ce;
	}
	
	public static UndoableEdit updateTypeDataFormatForLocalTypeDatas(ITypeData typeData) {
		CMCompoundEdit ce = new CMCompoundEdit();
		if(typeData instanceof TypeDataGlobal){
			if(typeData.getGlobal().equalsIgnoreCase("G")){
				int indexStructureTestdata = 0;
				StructureTestData std = typeData.getStructureTestData();
				indexStructureTestdata = std.getGlobalIndex();
				CMGridTDStructure gridLocal = CMApplication.frame.getPanelTestDataView().getM_CMGridTDStructure();
				for(Object testData: gridLocal.getTDStructure().getTestDataCombination().getM_TestDatas()){
					for(Object stdl:((TestData)testData).getM_TDStructure().getM_StructureTestData()){
						if(((StructureTestData)stdl).getGlobalIndex() == indexStructureTestdata){
							for(Object typeDataLocal: ((StructureTestData)stdl).getTypeData()){
								if(((ITypeData)typeDataLocal).getField().equalsIgnoreCase(typeData.getField())){
									if(((ITypeData)typeDataLocal).getGlobal().equalsIgnoreCase("G")){
										ce.addEdit(CMModelEditFactory.INSTANCE.createChangeFormatterInTypeDataModelEdit((ITypeData)typeDataLocal, typeData.getFormatter()));
										((ITypeData)typeDataLocal).setFormatter(typeData.getFormatter());
										ce.addEdit(CMModelEditFactory.INSTANCE.createChangeFormatInTypeDataModelEdit((ITypeData)typeDataLocal, typeData.getFormat()));
										((ITypeData)typeDataLocal).setFormat(typeData.getFormat());
									}
								}
							}
						}
					}
				}
			}
		}
		return ce;
	}
	
	public static UndoableEdit updateTypeDataValueForLocalTypeDatasWhenFormulaHasLinkElement(ITypeData typeData) {
		CMCompoundEdit ce = new CMCompoundEdit();
		if(typeData instanceof TypeDataGlobal){
			if(typeData.getGlobal().equalsIgnoreCase("G")){
				int indexStructureTestdata = 0;
				StructureTestData std = typeData.getStructureTestData();
				indexStructureTestdata = std.getGlobalIndex();
				CMGridTDStructure gridLocal = CMApplication.frame.getPanelTestDataView().getM_CMGridTDStructure();
				for(Object testData: gridLocal.getTDStructure().getTestDataCombination().getM_TestDatas()){
					for(Object stdl:((TestData)testData).getM_TDStructure().getM_StructureTestData()){
						if(((StructureTestData)stdl).getGlobalIndex() == indexStructureTestdata){
							for(Object typeDataLocal: ((StructureTestData)stdl).getTypeData()){
								if(((ITypeData)typeDataLocal).getField().equalsIgnoreCase(typeData.getField())){
									if(((ITypeData)typeDataLocal).getGlobal().equalsIgnoreCase("G")){
										ICMFormula formulaGlobal = (ICMFormula) typeData.getValue();
										//if(!hasLinkElementLocal(formulaGlobal)){//svonborries_26062007_THIS METHOD IS IN OBSERVATION
										if(!hasLinkElementGlobal(formulaGlobal)){
											ICMFormula formulaLocal = (ICMFormula) formulaGlobal.clone();
											List<CMLinkElement> linkElementLocals = new ArrayList<CMLinkElement>();
											getAllCMLinkElementsLocals(formulaLocal, linkElementLocals);
											changeKeyForLocalLinkElement(linkElementLocals, (TestData)testData);
											ce.addEdit(CMModelEditFactory.INSTANCE.createChangeValueInTypeDataModelEdit((ITypeData)typeDataLocal, formulaLocal));
											((ITypeData)typeDataLocal).setValue(formulaLocal);
											ce.addEdit(CMModelEditFactory.INSTANCE.createChangeGlobalReferenceInTypeDataModelEdit((ITypeData)typeDataLocal, ""));
											((ITypeData)typeDataLocal).setGlobal("");
										}
										else{
											ce.addEdit(CMModelEditFactory.INSTANCE.createChangeValueInTypeDataModelEdit((ITypeData)typeDataLocal, typeData.getValue()));
											((ITypeData)typeDataLocal).setValue(typeData.getValue());
										}
									}
								}
							}
						}
					}
				}
			}
		}
		return ce;
	}
	
	private static void getAllCMLinkElementsLocals(ICMFormula formulaLocal, List<CMLinkElement> linkElementLocals) {
		List<CMFormulaParameter> allowedParameters = formulaLocal.getFormulaEnum().getAllowedParam();
		for(Object allowedParameter: allowedParameters){
			Object parameter = formulaLocal.getParameter((CMFormulaParameter) allowedParameter);
			try {
				if(((CMDefaultParameter)parameter).getObject() instanceof ICMFormula){
					getAllCMLinkElementsLocals((ICMFormula) ((CMDefaultParameter)parameter).getObject(), linkElementLocals);
				}
				else if(((CMDefaultParameter)parameter).getObject() instanceof CMLinkElement){
					boolean result = ((CMLinkElement)((CMDefaultParameter)parameter).getObject()).isGlobal();
					if(result != true)
						linkElementLocals.add((CMLinkElement)((CMDefaultParameter)parameter).getObject());
				}
			} catch (Exception e) {
			}
		}
		
	}

	public static boolean hasLinkElementGlobal(ICMFormula formulaGlobal) {
		List<CMFormulaParameter> allowedParameters = formulaGlobal.getFormulaEnum().getAllowedParam();
		for(Object allowedParameter: allowedParameters){
			Object parameter = formulaGlobal.getParameter((CMFormulaParameter) allowedParameter);
			try {
				if(((CMDefaultParameter)parameter).getObject() instanceof ICMFormula){
					boolean result =  hasLinkElementGlobal((ICMFormula) parameter);
					if(result != true)
						return result;
				}
				else if(((CMDefaultParameter)parameter).getObject() instanceof CMLinkElement){
					boolean result = ((CMLinkElement)((CMDefaultParameter)parameter).getObject()).isGlobal();
					if(result != true)
						return result;
				}
				else{return true;}
			} catch (Exception e) {
				
			}
		}
		return true;
	}

	public static UndoableEdit addLinkElementForLocalReferences(ITypeData typeData){
		CMCompoundEdit ce = new CMCompoundEdit();
		if(typeData instanceof TypeDataGlobal){
			if(typeData.getGlobal().equalsIgnoreCase("G")){
				ce.addEdit(CMModelEditFactory.INSTANCE.createChangeGlobalReferenceInTypeDataModelEdit(typeData, ""));
				typeData.setGlobal("");
				int indexStructureTestdata = 0;
				StructureTestData std = typeData.getStructureTestData();
				indexStructureTestdata = std.getGlobalIndex();
				CMGridTDStructure gridLocal = CMApplication.frame.getPanelTestDataView().getM_CMGridTDStructure();
				for(Object testData: gridLocal.getTDStructure().getTestDataCombination().getM_TestDatas()){
					for(Object stdl:((TestData)testData).getM_TDStructure().getM_StructureTestData()){
						if(((StructureTestData)stdl).getGlobalIndex() == indexStructureTestdata){
							for(Object typeDataLocal: ((StructureTestData)stdl).getTypeData()){
								if(((ITypeData)typeDataLocal).getField().equalsIgnoreCase(typeData.getField())){
									if(((ITypeData)typeDataLocal).getGlobal().equalsIgnoreCase("G")){
										String currentKey = ((CMLinkElement)typeData.getValue()).getLinkKey();
										if(currentKey.startsWith("$")){
											currentKey = ((TestData)testData).getName() + "."+currentKey.substring(1);
											currentKey = "$"+ currentKey;
										}
										else
											currentKey = ((TestData)testData).getName() + "."+currentKey;
										CMLinkElement linkElement = new CMLinkElement(currentKey);
										ce.addEdit(CMModelEditFactory.INSTANCE.createChangeValueInTypeDataModelEdit(((ITypeData)typeDataLocal), linkElement));
										((ITypeData)typeDataLocal).setValue(linkElement);
										ce.addEdit(CMModelEditFactory.INSTANCE.createChangeGlobalReferenceInTypeDataModelEdit(((ITypeData)typeDataLocal), ""));
										((ITypeData)typeDataLocal).setGlobal("");
									}
								}
							}
						}
					}
				}
			}
		}
		
		return ce;
	}
	
	public static void changeKeyForLocalLinkElement(List<CMLinkElement> localLinks, TestData testData){
		for(CMLinkElement linkElementLocal: localLinks){
			String currentKey = "";
			currentKey = linkElementLocal.getLinkKey();
			if(currentKey.startsWith("$")){
				currentKey = ((TestData)testData).getName() + "."+currentKey.substring(1);
				currentKey = "$"+ currentKey;
			}
			else
				currentKey = ((TestData)testData).getName() + "."+currentKey;
			
			linkElementLocal.setLinkKey(currentKey);
			linkElementLocal.setObjectLinked(null);
			linkElementLocal.setGlobal(false);
		}
//		CMCompoundEdit ce = new CMCompoundEdit();
//		for(CMLinkElement linkElementLocal: localLinks){
//			ITypeData typeData = (ITypeData) linkElementLocal.getObjectLinked();
//		if(typeData instanceof TypeDataGlobal){
//				int indexStructureTestdata = 0;
//				StructureTestData std = typeData.getStructureTestData();
//				indexStructureTestdata = std.getGlobalIndex();
//				CMGridTDStructure gridLocal = CMApplication.frame.getPanelTestDataView().getM_CMGridTDStructure();
//				String currentKey = "";
//				for(Object testData: gridLocal.getTDStructure().getTestDataCombination().getM_TestDatas()){
//					for(Object stdl:((TestData)testData).getM_TDStructure().getM_StructureTestData()){
//						if(((StructureTestData)stdl).getGlobalIndex() == indexStructureTestdata){
//							for(Object typeDataLocal: ((StructureTestData)stdl).getTypeData()){
//								if(((ITypeData)typeDataLocal).getField().equalsIgnoreCase(typeData.getField())){//veremos
//									if(currentKey.equalsIgnoreCase("")){
//										currentKey = linkElementLocal.getLinkKey();
//										if(currentKey.startsWith("$")){
//											currentKey = ((TestData)testData).getName() + "."+currentKey.substring(1);
//											currentKey = "$"+ currentKey;
//										}
//										else
//											currentKey = ((TestData)testData).getName() + "."+currentKey;
//										
//									}
//									linkElementLocal.setLinkKey(currentKey);
//									linkElementLocal.setObjectLinked(null);
//									linkElementLocal.setGlobal(true);
//								}
//							}
//						}
//					}
//				}
//		}
//	}
//		
//		return ce;
	}
	
	public static boolean checkIfTypeDataHasICMValue(ITypeData typeData){
		try {
			if(typeData.getValue() instanceof ICMFormula)
				return true;
			else if(typeData.getValue() instanceof CMLinkElement)
				return true;
		} catch (Exception e) {
			return false;
		}
		return false;
	}
	
	public static String getICMValueNameoftheSelectedTypeData(ITypeData typeData){
		if(typeData.isFormula())
			return CMMessages.getString("TESTDATA_MENU_FORMULA");
		else if(typeData.isLinkValue())
			return CMMessages.getString("TESTDATA_LINKELEMENT");
		return CMMessages.getString("TESTDATA_VARIABLE");
//		if(typeData.getValue() instanceof ICMFormula)
//			return CMMessages.getString("TESTDATA_MENU_FORMULA");
//		else if(typeData.getValue() instanceof CMLinkElement){
//			if(((CMLinkElement)typeData.getValue()).getObjectLinked() instanceof ITypeData)
//				return CMMessages.getString("TESTDATA_LINKELEMENT");
//			else if(((CMLinkElement)typeData.getValue()).getObjectLinked() instanceof Variable || ((CMLinkElement)typeData.getValue()).getObjectLinked() 
//					instanceof Project2 || ((CMLinkElement)typeData.getValue()).getObjectLinked() instanceof Workspace2 || 
//					((CMLinkElement)typeData.getValue()).getObjectLinked() instanceof StructureTestData || ((CMLinkElement)typeData.getValue()).getObjectLinked() 
//					instanceof TestData || ((CMLinkElement)typeData.getValue()).getObjectLinked() instanceof TestObject)
//				return CMMessages.getString("TESTDATA_VARIABLE");
//		}	
		//return "";
	}

}
