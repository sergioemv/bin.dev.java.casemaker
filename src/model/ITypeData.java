package model;

import java.util.HashMap;
import java.util.Vector;
import model.util.CMModelSource;
import model.util.CMTypeBean;

public interface ITypeData extends Cloneable, IObserver, CMModelSource, CMTypeBean{

	public Object clone();

	public String getKey();

	public void setKey(String key);

	public String getGlobal();

	public void setGlobal(String global);

	public String getField();

	public void setField(String field);

	public String getName();

	public void setName(String name);

	public String getToolVendorOT(TestObject p_TestObject);
	
	public String getOldToolVendorOT();

	//public void setToolVendorOT(String stateNameOT);
	
	public void setStateOT(int stateOT);//Ccastedo 27.09.06

	public int getStateOT();//Ccastedo 27.09.06
	
	public String getNewColumn();

	public void setNewColumn(String newColumn);

	public Vector getNewColumns();

	public void setNewColumns(Vector newColumns);

	public String getTypeName();

	public CMTypeBean getType();
	
	public void setType(String type);

	public String getLength();

	public void setLength(String length);

	public String getPrefix();

	public void setPrefix(String prefix);

	public String getSuffix();

	public void setSuffix(String suffix);

	public String getFormat();

	public void setFormat(String format);

	/**
	 * @deprecated Use {@link #getFormula()} instead
	 */
	public String getStringFormula();
	
	/**
	 * @deprecated nevermore is necessary to set a aformula
	 */
	public void setFormula(String formula);


	public ICMValue getValue();
	/**
	 * @deprecated Use {@link #getStringValue()} instead
	 */
	public String getStringValue();

	/**
	 * @deprecated
	 * @param value
	 * 30/11/2006
	 * svonborries
	 */
	public void setStringValue(String value);

	public StructureTestData getStructureTestData();

	public void setStructureTestData(StructureTestData structureTestData);

	/**
	 * @return True if the value of the ICMValue return an instance of ICMFormula, otherwise return False.
	 * 08/12/2006
	 * svonborries
	 */
	public boolean isFormula();

	/**
	*@deprecated never more necessary 
	 */
	public void setisFormula(boolean p_isFormula);

	/**
	 * @return True if the value of the ICMValue return an instance of CMLinkElement, otherwise return False.
	 * 08/12/2006
	 * svonborries
	 */
	public boolean isLinkValue();

	/**
	*@deprecated never more necessary 
	 */
	public void setLinkValue(boolean linkValue);

	public void addSubject(String p_Key, Object p_Subject);

	public void removeSubject(String p_Key);

	public void removeAllSubject();

	public HashMap getM_Subjects();

//	public DelegateObservable getM_References();

	public TestDataFormat getFormatter();

	public void setFormatter(TestDataFormat formatter);

	//public void update(ITypeData arg0, Object arg1);

	public void addObserver(IObserver o);

	public void deleteObserver(IObserver o);

	public int getM_Size();

	//public void setM_References(DelegateObservable references);

	public void setM_Subjects(HashMap subjects);
    public void notifyObservers();
    public void notifyObservers(Object arg);
    public  void deleteObservers();
    public  boolean hasChanged();
    public  int countObservers();
    public Vector getObservers();
    
    public void setValue(ICMValue p_value);
    
    public String getFormattedValue();
    
    public String getFormula();
    
}
