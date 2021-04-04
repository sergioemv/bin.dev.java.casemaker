package model;

import bi.view.utils.CMFormatFactory;

public class Variable {
	public static final int VARIABLES_NAME=1;
	public static final int VARIABLES_TYPE=2;
	public static final int VARIABLES_FORMAT=3;
	public static final int VARIABLES_VALUE=4;
	public static final int VARIABLES_DESCRIPTION=5;
	private transient String m_Value;
    private String m_Name;
    private String m_Description;
    private String m_Type;
    private String m_Format;
    private TestDataFormat m_Formatter;
    private ICMValue icmvalue = null;
    
    private transient DelegateObservable m_Observers= new DelegateObservable();
	private transient int m_fieldChanged=0;
	public Variable(String p_name, ICMValue p_value, String p_Description, String p_type, String p_format, TestDataFormat p_formatter) {
		m_Name=p_name;
		//m_Value=p_value;
		icmvalue = p_value;
		m_Description=p_Description;
		m_Type=p_type;
		m_Format=p_format;
		m_Formatter=p_formatter;
		m_Observers= new DelegateObservable();
	}
	public Variable() {
		m_Observers= new DelegateObservable();
	}
	/**
	 * @return Returns the m_Description.
	 */
	public String getM_Description() {
		return m_Description;
	}
	/**
	 * @param description The m_Description to set.
	 */
	public void setM_Description(String description) {
		//m_fieldChanged=5;
		m_Description = description;
	}
	/**
	 * @return Returns the m_Format.
	 */
	public String getFormat() {
		return m_Format;
	}
	/**
	 * @param format The m_Format to set.
	 */
	public void setFormat(String format) {
		//m_fieldChanged=3;
		m_Format = format;
	}
	/**
	 * @return Returns the m_Formatter.
	 */
	public TestDataFormat getFormatter() {
		return m_Formatter;
	}
	/**
	 * @param formatter The m_Formatter to set.
	 */
	public void setFormatter(TestDataFormat formatter) {
		m_Formatter = formatter;
	}
	/**
	 * @return Returns the m_Names.
	 */
	public String getM_Name() {
		return m_Name;
	}
	/**
	 * @param names The m_Names to set.
	 */
	public void setM_Name(String names) {
		//String oldname=m_Name;
		//m_fieldChanged=1;
		m_Name = names;
		//m_Observers.setChanged();
		//Vector variableChanged=new Vector();
		//variableChanged.addElement(oldname);
		//variableChanged.addElement(this);
		//m_Observers.notifyObservers(variableChanged);
	}
	/**
	 * @deprecated nevermore needed
	 * @return Returns the m_Observers.
	 */
	public DelegateObservable getM_Observers() {
		if(m_Observers == null)
			m_Observers= new DelegateObservable();
		return m_Observers;
	}
	/**
	 * @deprecated nevermore needed
	 * @param observers The m_Observers to set.
	 */
	public void setM_Observers(DelegateObservable observers) {
		m_Observers = observers;
	}
	/**
	 * @return Returns the m_Type.
	 */
	public String getM_Type() {
		return m_Type;
	}
	/**
	 * @param type The m_Type to set.
	 */
	public void setM_Type(String type) {
		//m_fieldChanged=2;
		m_Type = type;
	}
	/**
	 * @deprecated instead use getM_Value()
	 * @return Returns the m_Value.
	 */
	public String getM_StringValue() {
		return m_Value;
	}
	/**
	 * @deprecated instead use setM_Value();
	 * @param value The m_Value to set.
	 */
	public void setM_StringValue(String value) {
		//m_fieldChanged=4;
		m_Value = value;
//		m_Observers.setChanged();
//		m_Observers.notifyObservers(this);
	}
	
	/**
	 * @deprecated
	 * 14/12/2006
	 * svonborries
	 */
	public int fieldChanged(){
		return m_fieldChanged;
	}
	
	public ICMValue getM_Value(){
		return icmvalue;
	}
	
	public String getFormattedValue() {
		if(getM_Value() != null){
			return CMFormatFactory.applyFormatToICMValue(getM_Value(), getFormatter());
		}
		else{
			return CMFormatFactory.applyAnyFormat(getFormatter(), 
					"", new TestDataFormat());
		}
	}
	
	public void setM_Value(ICMValue p_value){
		icmvalue = p_value;
	}
}
