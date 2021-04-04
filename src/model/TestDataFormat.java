package model;

import java.util.Locale;

import bi.view.utils.CMFormatFactory;

public class TestDataFormat {
	private String realFormat;
	private String visualFormatter;
	private Locale formatLocale;
	private String originalValue;
	private String originalFormatter;
	private boolean visualFormat;
	
	public TestDataFormat(){
		realFormat= BusinessRules.FORMULAS_FORMAT_STRING;
		visualFormatter=BusinessRules.FORMULAS_FORMAT_STRING;
		originalFormatter=BusinessRules.FORMULAS_FORMAT_STRING;
		formatLocale=CMFormatFactory.getTestObejctLocale();//new Locale("en", "US"); 
		visualFormat=false;
	}
	public TestDataFormat(String p_Value, String p_RealFormat, String p_VisualFromat, Locale p_Locale, boolean p_visualFormat){
		realFormat=p_RealFormat;
		visualFormatter=p_VisualFromat;
		formatLocale=p_Locale;
		originalValue=p_Value;
		visualFormat=p_visualFormat;
	}
	public Object clone()  {
		
		TestDataFormat clon= new TestDataFormat();
		try {
			TestDataFormat aux=this;//(TestDataFormat)super.clone();
			clon.setRealFormat(new String(aux.getRealFormat()));
			clon.setFormatLocale(new Locale(aux.getFormatLocale().getLanguage(),aux.getFormatLocale().getCountry()));
			clon.setVisualFormatter(new String(aux.getVisualFormatter()));
			clon.setValue(aux.getValue());
			clon.setOriginalFormatter(new String(aux.getOriginalFormatter()));//svonborries
			clon.setVisualFormat(aux.isVisualFormat());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return clon;
	}

	public String getRealFormat() {
		if(realFormat == null)
			realFormat = "";
		return realFormat;
	}
	public void setRealFormat(String realFormat) {
		this.realFormat = realFormat;
	}
	
	public Locale getFormatLocale() {
		return formatLocale;
	}
	public void setFormatLocale(Locale formatLocale) {
		this.formatLocale = formatLocale;
	}
	
	public String getVisualFormatter() {
		if(visualFormatter == null)
			visualFormatter = "";
		return visualFormatter;
	}
	public void setVisualFormatter(String visualFormatter) {
		this.visualFormatter = visualFormatter;
	}

	public String getValue() {
		if(originalValue == null)
			originalValue = "";
		return originalValue;
	}

	public void setValue(String originalValue) {
		this.originalValue = originalValue;
	}
	public String getOriginalFormatter() {
		if(originalFormatter == null)
			originalFormatter = "";
		return originalFormatter;
	}
	public void setOriginalFormatter(String originalFormatter) {
		this.originalFormatter = originalFormatter;
	}
	public boolean isVisualFormat() {
		return visualFormat;
	}
	public void setVisualFormat(boolean visualFormat) {
		this.visualFormat = visualFormat;
	}
	
	
}
