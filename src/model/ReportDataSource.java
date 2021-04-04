package model;


public class ReportDataSource {

	private int id;
	private String schema;
	private String name;
	
	/*public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		if (name.equals(CMMessages.getString("OLD_REPORT_DATA_TEST_CASE_LIST_1"))){
			return CMMessages.getString("REPORT_DATA_TEST_CASE_LIST_1");
		}
		else
			if (name.equals(CMMessages.getString("OLD_REPORT_DATA_TEST_CASE_LIST_2"))){
				return CMMessages.getString("REPORT_DATA_TEST_CASE_LIST_2");
			}
		
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getSchema() {
		return schema;
	}
	
	public String getAbsoluteSchema()
	{
		return	BusinessRules.TESTCASE_XSLTREPORTS_CARPET + BusinessRules.URL_SEPARATOR+getSchema();	
	}
	public void setSchema(String schema) {
		this.schema = schema;
	}
	public ReportDataSource(int id, String schema, String name) {
		super();
	
		this.id = id;
		this.schema = schema;
		this.name = name;
	}
public String toString() {
	 
	return getName();
}*/
	public ReportDataSource() {
		super();
	
		this.id = id;
		this.schema = schema;
		this.name = name;
	}
}
