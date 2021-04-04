package bi.view.report.data;



public interface CMReportData {

	
	public boolean isReportDataGenerated();	
	/**
	 *  Returns the file path where the XML was saved
	 * @return
	 */
	public String getFilePath();

	public boolean loadData();
	
	public String getName();	
	
	public String getReportFolder();
	
	public void setParameter(String key, Object ob);
	
	public Object getParameter(String key);
}
