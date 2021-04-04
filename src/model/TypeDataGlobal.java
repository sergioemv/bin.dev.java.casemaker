/* Generated by Together */

package model;

import java.util.HashMap;
import java.util.Locale;
import java.util.Vector;

import model.util.CMModelListener;
import model.util.CMTypeBean;

import org.apache.log4j.Logger;

import bi.controller.TDStructureManager;
import bi.controller.ToolVendorManager;
import bi.view.cells.CMCellTDStructureClassState;
import bi.view.utils.CMFormatFactory;


public class TypeDataGlobal  implements ITypeData
{
	private String key;
    private String global;
    private String field;
	private String name;
	private String stateNameOT="";
	private int stateOT=-1;
	private String newColumn;
	private Vector newColumns = new Vector(5);
	private String type;
	private String length;
	private String prefix;
	private String suffix;
    private String format;
	private String formula;
	private String value;
    private StructureTestData structureTestData;
    private boolean isFormula;

    private HashMap m_Subjects;
    private Vector linkElements;

    private String realFormat;
	private String visualFormatter;
	private String languaje;
	private String originalFormatter;
	private boolean visualFormat;
	private ICMValue icmvalue;

    private transient boolean changed = false;
   // private transient TestDataFormat m_Formatter;

    public TypeDataGlobal()
     {
			key=""; //$NON-NLS-1$
            global="";
			field="";//new //$NON-NLS-1$
 			name =""; //$NON-NLS-1$
 			newColumn="";
 			newColumns = new Vector(0);
        	type =BusinessRules.TESTDATA_STATE_BINARY; //$NON-NLS-1$
        	length= "10"; //$NON-NLS-1$
        	prefix = ""; //$NON-NLS-1$
        	suffix =""; //$NON-NLS-1$
            format="";//new //$NON-NLS-1$
        	formula =""; //$NON-NLS-1$
        	value =""; //$NON-NLS-1$
           	isFormula=false;
           linkElements= new Vector();
    	}


	@SuppressWarnings("unchecked")
	public Object clone() {
      Object b = null;
      TypeDataGlobal clon= new TypeDataGlobal();
      try {
       b = super.clone();
		TypeDataGlobal aux=(TypeDataGlobal)b;
        clon.setField(new String(aux.getField()));
        clon.setFormat(new String(aux.getFormat()));
        clon.setFormatter((TestDataFormat)aux.getFormatter().clone());
        //clon.setFormula(new String(aux.getStringFormula()));
        clon.setGlobal(new String(aux.getGlobal()));
        clon.setKey(new String(aux.getKey()));
        clon.setLength(new String(aux.getLength()));
        clon.setName(new String(aux.getName()));
     //ccastedo 27.09.06   clon.setToolVendorOT(new String(aux.getToolVendorOT()));
        clon.setStateOT(aux.getStateOT());
        clon.setNewColumns(new Vector(aux.getNewColumns()));
        clon.setPrefix(new String(aux.getPrefix()));
        clon.setSuffix(new String(aux.getSuffix()));
        clon.setType(new String(aux.getTypeName()));
        ICMValue value = (ICMValue) aux.getValue().clone();
        clon.setValue(value);
        //clon.setStringValue(new String(aux.getStringValue()));
        //clon.setisFormula(aux.isFormula());
        //clon.setLinkValue(aux.isLinkValue());
        clon.setStructureTestData(aux.getStructureTestData());


      } catch(CloneNotSupportedException e) {
        e.printStackTrace();
      }
      return clon;
    }
        public String getKey()
        {
        	if(key == null)
        		key = "";
            return key;
        }

        public void setKey(String key)
        {
            this.key = new String(key);
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
        	if(field == null)
        		field = "";
            return field;
        }

        public void setField(String field)
        {
            this.field = new String(field);
        }

   	 	public String getName()
   		{
   	 		if(name == null)
   	 			name = "";
    		return name;
   		}

    	public void setName(String name)
    	{
        	this.name = new String(name);
    	}
     	public String getToolVendorOT(TestObject p_TestObject)
   		{
     		if(stateNameOT == null)
     			stateNameOT = "";
    		Technology tech = ToolVendorManager.INSTANCE.findTechnologyByName(p_TestObject.getToolVendor(),p_TestObject.getToolVendorTechnology());
        	if (tech!=null)
        		try{
        		    tech.getM_ObjectTypesValue().get(this.getStateOT());
        		} catch (ArrayIndexOutOfBoundsException e) {
        			Logger.getLogger(this.getClass()).error("No Object Type for "+this.getStateOT()+" Setting default value");
        			this.setStateOT(0);
    			}
        		this.stateNameOT =  tech.getM_ObjectTypesValue().get(this.getStateOT()).toString();
        	return stateNameOT;
   		}

     	public String getOldToolVendorOT()
   		{
     		return stateNameOT;
   		}

     	 public int getStateOT(){ return stateOT; }

         public void setStateOT(int stateOT){
         	this.stateOT = stateOT;

//			.getToolVendorTechnology();
//         	Technology tech = ToolVendorManager.INSTANCE.findTechnologyByName(toolVendor,technology);
//         	if (tech!=null)
//         		this.stateNameOT =  tech.getM_ObjectTypesValue().get(this.getStateOT()).toString();
         }
         //ccastedo ends 26.09.06



    	public String getNewColumn()
   		{
    		if(newColumn == null)
    			newColumn = "";
    		return newColumn;
   		}

    	public void setNewColumn(String newColumn)
    	{
        	this.newColumn =  newColumn; //VER
    	}

    	public Vector getNewColumns()
   		{
    		if(newColumns == null)
    			newColumns = new Vector();
    		return newColumns;
   		}

    	public void setNewColumns(Vector newColumns)
    	{

        	this.newColumns=newColumns; //VER
    	}

     	public String getTypeName()
    	{
     		if(type == null)
     			type = "";
        	return type;
    	}

    	public void setType(String type)
    	{
        	this.type = new String(type);
    	}

    	public String getLength()
    	{
    		if(length == null)
    			length = "";
        	return length;
        }

    	public void setLength(String length)
    	{
        	try{
        	if(Double.parseDouble(length)<=0)
                this.length= new String("1");
        	else
        		this.length = new String(length);
        	}
        	catch(Exception ex)
        	{
				this.length= new String("1");
        	}

    	}

	    public String getPrefix()
        {
	    	if(prefix == null)
	    		prefix = "";
            return prefix;
        }

    	public void setPrefix(String prefix)
    	{
        	this.prefix =new String(prefix);
    	}

	    public String getSuffix()
        {
	    	if(suffix == null)
	    		suffix = "";
            return suffix;
        }

    	public void setSuffix(String suffix)
    	{
        	this.suffix = new String(suffix);
    	}

        public String getFormat()
        {
        	if(format == null)
        		//format = "";
        		format = BusinessRules.FORMULAS_FORMAT_STRING;//svonborries_17072007
            return format;
        }

        public void setFormat(String format)
        {
            this.format = new String(format);
        }

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
		 * @deprecated Use {@link #getValue()} instead
		 */
		public String getStringValue()
        {
	    	if(value == null)
	    		value = "";
            return value;
        }

		public void setValue(ICMValue p_icmvalue){
			icmvalue = p_icmvalue;
		}

	    /**
		 * @deprecated Use {@link #setValue()} instead
		 */
    	public void setStringValue(String value)
    	{
        	this.value =new String( value);
        	this.setChanged();
        	this.notifyObservers(value);
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
//TODO: change the way work that		
		public TestDataFormat getFormatter() {
			//if(m_Formatter == null){
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
		public void setFormatter(TestDataFormat formatter) {
			/*if(m_Formatter == null){
				m_Formatter=new TestDataFormat();
			}*/
			this.originalFormatter=new String(formatter.getOriginalFormatter());
			this.realFormat=new String(formatter.getRealFormat());
			this.visualFormat=formatter.isVisualFormat();
			this.visualFormatter=new String(formatter.getVisualFormatter());
			this.languaje=new String(formatter.getFormatLocale().getLanguage());
			//m_Formatter = formatter;
		}

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
								//setName(variable.getM_Name()); //svonborries_30122005
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

		public int getM_Size(){
			return 12+newColumns.size();
		}

		/**
		 * @param subjects The m_Subjects to set.
		 */
		public void setM_Subjects(HashMap subjects) {
			m_Subjects = subjects;
		}

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
			type = Type.values()[p_type].toString();
		}


		public void setType(Type p_type) {
			type = p_type.toString();

		}


		public int getTypeIndex() {
			return Type.getTypeByName(type).intValue();
		}


		public void fireModelEventHappen(CMField field) {
//			 TODO Auto-generated method stub

		}

}
