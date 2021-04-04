package model;

import java.util.HashMap;
import java.util.Locale;
import java.util.Vector;

import model.util.CMModelListener;
import model.util.CMTypeBean;

import bi.controller.TDStructureManager;
import bi.view.cells.CMCellTDStructureClassState;
import bi.view.utils.CMFormatFactory;

public class TypeDataLocal implements  ITypeData{

	private String formula="";
	private String value="";
    private StructureTestData structureTestData;
    private boolean isFormula=false;
   // private DelegateObservable m_References;
    private HashMap m_Subjects;
    private ITypeData m_ReferenceTypeData= null;
    private String global="";
    private ICMValue icmvalue;
	private boolean visualFormat;
	private String languaje;
	private String originalFormatter;
	private String realFormat;
	private String visualFormatter;
	private String format;
	/**
	 * @param formula
	 * @param value
	 */
	public TypeDataLocal(String formula, String value) {
		super();
		
		this.formula = formula;
		this.value = value;
		this.linkElements= new Vector();
	}
	public TypeDataLocal() {
		this.linkElements= new Vector();
	}
	public Object clone() {
      Object b = null; 
      TypeDataLocal clon= new TypeDataLocal();
      try {
       b = super.clone();
		TypeDataLocal aux=(TypeDataLocal)b;
        //clon.setFormula(new String(aux.getStringFormula()));
        ICMValue value = (ICMValue) aux.getValue().clone();
        clon.setValue(value);
        //clon.setStringValue(new String(aux.getStringValue()));
        //clon.setisFormula(aux.isFormula());
        //clon.setLinkValue(aux.isLinkValue());
        clon.setGlobal(new String(aux.getGlobal()));
        clon.setStructureTestData(aux.getStructureTestData());
        clon.setM_ReferenceTypeData(aux.getM_ReferenceTypeData());
        

      } catch(CloneNotSupportedException e) {
        e.printStackTrace();
      }
      return clon;
    }
        public String getKey()
        {
            return getM_ReferenceTypeData().getKey();
        }

        public void setKey(String key)
        {
            //m_ReferenceTypeData.setKey(key);
        }

        public String getGlobal()
        {
        	if(global == null)
        		global = "";
            return global;
        }

        public void setGlobal(String global)
        {
            this.global = new String(global);
        }

        public String getField()
        {
            return getM_ReferenceTypeData().getField();
        }

        public void setField(String field)
        {
           // m_ReferenceTypeData.setField(field);
        }
//end
   	 	public String getName()
   		{
    		return getM_ReferenceTypeData().getName();
   		}

    	public void setName(String name)
    	{
        	//m_ReferenceTypeData.setName(name);
    	}
    	public String getToolVendorOT(TestObject p_TestObject)
   		{
    		return getM_ReferenceTypeData().getToolVendorOT(p_TestObject);
   		}

    	public String getOldToolVendorOT()
   		{
    		return getM_ReferenceTypeData().getOldToolVendorOT();
   		}
    	
    	public void setToolVendorOT(String stateNameOT)
    	{
        //	m_ReferenceTypeData.setToolVendorOT(stateNameOT);
    	}
    	
     	 public int getStateOT(){ return getM_ReferenceTypeData().getStateOT(); }//ccastedo ends 26.09.06

     	public void setStateOT(int stateOT)
    	{    	}
    	public String getNewColumn()
   		{
    		return getM_ReferenceTypeData().getNewColumn();
   		}

    	public void setNewColumn(String newColumn)
    	{
        	//m_ReferenceTypeData.setNewColumn(newColumn);
    	}
    	
    	public Vector getNewColumns()
   		{
    		return getM_ReferenceTypeData().getNewColumns();
   		}

    	public void setNewColumns(Vector newColumns)
    	{
    		
        	//m_ReferenceTypeData.setNewColumns(newColumns);
    	}

    	public String getTypeName()
    	{
        	return getM_ReferenceTypeData().getTypeName();
    	}

    	public void setType(String type)
    	{
        	//m_ReferenceTypeData.setType(type);
    	}

    	public String getLength()
    	{
        	return getM_ReferenceTypeData().getLength();
        }

    	public void setLength(String length)
    	{
        	/*try{
        	if(Double.parseDouble(length)<=0)
                m_ReferenceTypeData.setLength("1");
        	else
        		m_ReferenceTypeData.setLength(length);
        	}
        	catch(Exception ex)
        	{
        		m_ReferenceTypeData.setLength("1");
        	}*/

    	}

	    public String getPrefix()
        {
            return getM_ReferenceTypeData().getPrefix();
        }

    	public void setPrefix(String prefix)
    	{
    		//m_ReferenceTypeData.setPrefix(prefix);
    	}

	    public String getSuffix()
        {
            return getM_ReferenceTypeData().getSuffix();
        }

    	public void setSuffix(String suffix)
    	{
        	//this.m_ReferenceTypeData.setSuffix(suffix);
    	}
//svonborries_17072007_begin
        public String getFormat()
        {
            //return getM_ReferenceTypeData().getFormat();
        	if(format == null)
        		//format = "";
        		format = BusinessRules.FORMULAS_FORMAT_STRING;//svonborries_17072007
            return format;
        }

        public void setFormat(String format)
        {
        	//m_ReferenceTypeData.setFormat(format);
        	this.format = format;
        }
//      svonborries_17072007_end
	    public String getStringFormula()
        {
	    	if(formula == null)
	    		formula = "";
            return formula;
        }

    	public void setFormula(String formula)
    	{
        	this.formula = new String(formula);
    	}


		public ICMValue getValue()
		{
			if(icmvalue == null)
				if(getStringValue().equalsIgnoreCase("")){
					icmvalue = new CMDefaultValue("");
				}
				else{
					icmvalue = new CMDefaultValue(getStringValue());
					setStringValue("");
				}
				
			return icmvalue;
		}
	    /**
		 * @deprecated Use {@link #getStringValue()} instead
		 */
		public String getStringValue()
        {
	    	if(value == null)
	    		value = "";
            return value;
        }

    	public void setStringValue(String value)
    	{
        	this.value =new String( value);
        	this.setChanged();
        	this.notifyObservers(value);
    	}
    	
		public void setValue(ICMValue p_icmvalue){
			icmvalue = p_icmvalue;
		}

        public StructureTestData getStructureTestData()
        {
        	if(structureTestData == null)
        		structureTestData = new StructureTestData();
            return structureTestData;
        }

        public void setStructureTestData(StructureTestData structureTestData)
        {
            this.structureTestData = structureTestData;
        }

        public boolean isFormula(){
        	if(getValue() != null){
        		try {
					if(getValue() instanceof ICMFormula)
						return true;
				} catch (Exception e) {
					return false;
				}
        	}
            return false;
            }

        public void setisFormula(boolean p_isFormula){
                this.isFormula = p_isFormula;
            }
		public boolean isLinkValue() {
        	if(getValue() != null){
        		try {
					if(getValue() instanceof CMLinkElement){
						//if(getValue().getValue() instanceof ITypeData)
						if(((CMLinkElement)getValue()).getObjectLinked() instanceof ITypeData)
						return true;
					}		
						
				} catch (Exception e) {
					return false;
				}
        	}
            return false;
		}
		public void setLinkValue(boolean linkValue) {
		}
        		
        @SuppressWarnings("unchecked")
		public void addSubject(String p_Key, Object p_Subject){
        	getM_Subjects().put(p_Key, p_Subject);
        }
        public void removeSubject(String p_Key){
        	getM_Subjects().remove(p_Key);
        }
        public  void removeAllSubject(){
        	getM_Subjects().clear();
        }
		/**
		 * @return Returns the m_Subjects.
		 */
		public HashMap getM_Subjects() {
			if(m_Subjects== null){
				m_Subjects= new HashMap();
			}
			return m_Subjects;
		}
		/**
		 * @return Returns the m_References.
		 */
	/*	public DelegateObservable getM_References() {
			if(m_References== null){
				m_References=new DelegateObservable();
			}
			return m_References;
		}*/
		
//svonborries_13072007_begin
		public TestDataFormat getFormatter() {
//			if(m_ReferenceTypeData.getFormatter() == null)
//				m_ReferenceTypeData.setFormatter(new TestDataFormat());
//			return m_ReferenceTypeData.getFormatter();
			TestDataFormat m_Formatter=new TestDataFormat();
			//}
			validateFormatFields();
			m_Formatter.setOriginalFormatter(this.originalFormatter);
			m_Formatter.setRealFormat(this.realFormat);
			m_Formatter.setVisualFormat(this.visualFormat);
			m_Formatter.setVisualFormatter(this.visualFormatter);
			if(languaje.equals("de"))
				m_Formatter.setFormatLocale(Locale.GERMANY);
			if(languaje.equals("en"))
				m_Formatter.setFormatLocale(Locale.US);
			return m_Formatter;
		}
		
		private void validateFormatFields(){
	    	visualFormat=false;
	    	if(languaje==null)
	    		languaje= CMFormatFactory.getTestObejctLocale().getLanguage();
	    	if(realFormat== null)
	    		realFormat= BusinessRules.FORMULAS_FORMAT_STRING;
	    	if(visualFormatter== null)
	    		visualFormatter= BusinessRules.FORMULAS_FORMAT_STRING;
	    	if(originalFormatter== null)
	    		originalFormatter= BusinessRules.FORMULAS_FORMAT_STRING;
	    }
//svonborries_13072007_end		
//		svonborries_16072007_begin			
		public void setFormatter(TestDataFormat formatter) {
			//m_ReferenceTypeData.setM_Formatter(formatter);
			this.originalFormatter=new String(formatter.getOriginalFormatter());
			this.realFormat=new String(formatter.getRealFormat());
			this.visualFormat=formatter.isVisualFormat();
			this.visualFormatter=new String(formatter.getVisualFormatter());
			this.languaje=new String(formatter.getFormatLocale().getLanguage());
		}
//		svonborries_16072007_end	
		@SuppressWarnings("deprecation")
		public void update(Object arg0, Object arg1) {
			if (arg1 instanceof Variable) {
				int fieldChange=((Variable)arg1).fieldChanged();
				Variable variable=(Variable)arg1;
				switch (fieldChange) {
					case Variable.VARIABLES_VALUE:
					{
						if(!isFormula){
							setFormat(variable.getFormat());
							setFormatter(variable.getFormatter());
							setStringValue(variable.getM_StringValue());
						}
						else
							TDStructureManager.updateTypeDataReferences(getM_Subjects(),this);
						break;
					}
					case Variable.VARIABLES_TYPE:
					{
						if(!isFormula){
							setType(variable.getM_Type());
							setLength(CMCellTDStructureClassState.getDefaultValue(variable.getM_Type()));
						}
						break;
					}
					default:
						break;
				}
			}
			else{
				if(arg1 instanceof Vector){
					Vector variableAndName=(Vector)arg1;
					String oldName=(String) variableAndName.firstElement();
					Variable variable=(Variable) variableAndName.elementAt(1);
					int fieldChange=variable.fieldChanged();
					switch (fieldChange) {
						case Variable.VARIABLES_NAME :
						{
							if(formula.equals(oldName)){
								setName(variable.getM_Name());
							}
							else if(formula.indexOf("$"+oldName)>=0){
								formula.replaceAll("$"+oldName,"$"+variable.getM_Name());
							}
							break;
						}
					}
				}
				else
					TDStructureManager.updateTypeDataReferences(getM_Subjects(),this);
			}
		}
	/*	public void addObserver(IObserver o) {
			getM_References().addObserver(o);
			}
		public void deleteObserver(IObserver o) {
			getM_References().deleteObserver(o);
		}*/
		public int getM_Size(){
			return getM_ReferenceTypeData().getM_Size();
		}
		/**
		 * @param references The m_References to set.
		 */
	/*	public void setM_References(DelegateObservable references) {
			m_References = references;
		}*/
		/**
		 * @param subjects The m_Subjects to set.
		 */
		public void setM_Subjects(HashMap subjects) {
				m_Subjects = subjects;
		}
		/**
		 * @return Returns the m_ReferenceTypeData.
		 */
		public ITypeData getM_ReferenceTypeData() {
			if(m_ReferenceTypeData == null)
				m_ReferenceTypeData = new TypeDataGlobal();
			return m_ReferenceTypeData;
		}
		/**
		 * @param referenceTypeData The m_ReferenceTypeData to set.
		 */
		public void setM_ReferenceTypeData(ITypeData referenceTypeData) {
			m_ReferenceTypeData = referenceTypeData;
		}
		private transient boolean changed = false;
	    private Vector linkElements;
	   
	    
		
	    

	  
	    @SuppressWarnings("unchecked")
		public synchronized void addObserver(IObserver o) {
	        if (o == null)
	            throw new NullPointerException();
		if (!linkElements.contains(o)) {
		    linkElements.addElement(o);
		}
	    }

	  
	    public synchronized void deleteObserver(IObserver o) {
	        linkElements.removeElement(o);
	    }

	    
	    public void notifyObservers() {
		notifyObservers(null);
	    }

	    
	    public void notifyObservers(Object arg) {
	        Object[] arrLocal;

		synchronized (this) {
		  if (!changed)
	                return;
	            arrLocal = linkElements.toArray();
	            clearChanged();
	        }

	        for (int i = arrLocal.length-1; i>=0; i--)
	            ((IObserver)arrLocal[i]).update(this, arg);
	    }

	    
	    public synchronized void deleteObservers() {
		linkElements.removeAllElements();
	    }

	    
	    protected synchronized void setChanged() {
		changed = true;
	    }

	    
	    protected synchronized void clearChanged() {
		changed = false;
	    }

	    
	    public synchronized boolean hasChanged() {
		return changed;
	    }

	    
	    public synchronized int countObservers() {
		return linkElements.size();
	    }
	    
	    public Vector getObservers(){
	    	return (Vector) linkElements.clone();
	    }
		public void addModelListener(CMModelListener listener) {
			// TODO Auto-generated method stub
			
		}
		public void removeModelListener(CMModelListener listener) {
			// TODO Auto-generated method stub
			
		}
		public String getFormattedValue() {
			if(getValue() != null){
				return CMFormatFactory.applyFormatToICMValue(getValue(), getFormatter());
			}
			else{
				return CMFormatFactory.applyAnyFormat(getFormatter(), 
						"", new TestDataFormat());
			}
		}
		public String getFormula() {
			if(getValue() == null)
				return "";
			return getValue().toString();
		}
		public CMTypeBean getType() {			
			return this;
		}
		public void setType(int p_type) {
			setType(p_type);
			
		}
		public void setType(Type p_type) {
			setType(p_type);
			
		}
		public int getTypeIndex() {		
			return Type.getTypeByName(getTypeName()).intValue();
		}
		public void fireModelEventHappen(CMField field) {
//			 TODO Auto-generated method stub

		}
}
