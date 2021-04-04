package bi.controller.utils;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import model.ITypeData;
import model.TestDataFormat;
import model.TypeDataLocal;
import bi.controller.TDStructureManager;

public class CMLocalReferenceConvert {

	private HashMap<String, String> keys;
	private ITypeData localTypeData; 
	private String format;
	private TestDataFormat m_Formatter;
	private String type;
	
	public void setKeys(String p_GlobalKey, String p_LocalKey){
		keys.put(p_GlobalKey,p_LocalKey);
	}
	/**
	 * @return Returns the format.
	 */
	public String getFormat() {
		return format;
	}

	/**
	 * @param format The format to set.
	 */
	public void setFormat(String format) {
		this.format = format;
	}

	/**
	 * @return Returns the m_Formatter.
	 */
	public TestDataFormat getM_Formatter() {
		return m_Formatter;
	}

	/**
	 * @param formatter The m_Formatter to set.
	 */
	public void setM_Formatter(TestDataFormat formatter) {
		m_Formatter = formatter;
	}

	/**
	 * @return Returns the type.
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type The type to set.
	 */
	public void setType(String type) {
		this.type = type;
	}
	///
	public CMLocalReferenceConvert(){
		super();
		localTypeData= new TypeDataLocal();
		keys= new HashMap<String,String>();	
	}
	
	public CMLocalReferenceConvert(ITypeData p_LocalTypeData, String p_GlobalKey, String p_LocalKey) {
		super();
		localTypeData=p_LocalTypeData;
	}
	
	/**
	 * @return Returns the localTypeData.
	 */
	public ITypeData getLocalTypeData() {
		return localTypeData;
	}
	/**
	 * @param localTypeData The localTypeData to set.
	 */
	public void setLocalTypeData(ITypeData localTypeData) {
		this.localTypeData = localTypeData;
	}
	
	public boolean convertFormulasToLocalReferenceFormulas(String p_ArmedFormula){
		boolean success= true;
		try {
			Set l_keys=keys.keySet();
			String converted=p_ArmedFormula;
			for (Iterator iter = l_keys.iterator(); iter.hasNext();) {
				String globalKey = (String) iter.next();
				String localKey= keys.get(globalKey);
				while(converted.contains(globalKey))
					converted=converted.replace(globalKey,localKey);
			}
			//this.localTypeData.setFormula(converted);
			//localTypeData.setisFormula(true);
			//localTypeData.setLinkValue(false);
			localTypeData.setFormat(format);
			localTypeData.setType(type);
			localTypeData.setFormatter(m_Formatter);
			localTypeData.setGlobal("");
			return success;
		} catch (Exception e) {
			return false;
		}
	}
	
	public boolean calculateLocalReferenceFormulas(){
		boolean success= true;
		try {
			TDStructureManager.updateTypeDataReferences(localTypeData.getM_Subjects(),localTypeData);
			return success;
		} catch (Exception e) {
			return false;
		}
	}
	public boolean convertAndCalculateLocalReferenceFormulas(String p_ArmedFormula){
		boolean isConverted=convertFormulasToLocalReferenceFormulas(p_ArmedFormula);
		if(isConverted){	
			boolean isCalculated=calculateLocalReferenceFormulas();
			return isCalculated;
		}
		return isConverted;
	}

	
}
