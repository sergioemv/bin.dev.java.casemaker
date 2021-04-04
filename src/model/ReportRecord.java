package model;

public class ReportRecord {

	public ReportRecord() {
		super();
		
	}
	
	private String m_Name;
	private String m_Parameters;
	private String m_Application;
	private String m_Path;
	private String m_FileName;
	private String m_TimeStamp;
	/**
	 * @return Returns the m_Application.
	 */
	public String getM_Application() {
		return m_Application;
	}
	/**
	 * @param application The m_Application to set.
	 */
	public void setM_Application(String application) {
		m_Application = application;
	}
	/**
	 * @return Returns the m_FileName.
	 */
	public String getM_FileName() {
		return m_FileName;
	}
	/**
	 * @param fileName The m_FileName to set.
	 */
	public void setM_FileName(String fileName) {
		m_FileName = fileName;
	}
	/**
	 * @return Returns the m_Name.
	 */
	public String getM_Name() {
		return m_Name;
	}
	/**
	 * @param name The m_Name to set.
	 */
	public void setM_Name(String name) {
		m_Name = name;
	}
	/**
	 * @return Returns the m_Parameters.
	 */
	public String getM_Parameters() {
		return m_Parameters;
	}
	/**
	 * @param parameters The m_Parameters to set.
	 */
	public void setM_Parameters(String parameters) {
		m_Parameters = parameters;
	}
	/**
	 * @return Returns the m_Path.
	 */
	public String getM_Path() {
		return m_Path;
	}
	/**
	 * @param path The m_Path to set.
	 */
	public void setM_Path(String path) {
		m_Path = path;
	}
	/**
	 * @return Returns the m_TimeStamp.
	 */
	public String getM_TimeStamp() {
		return m_TimeStamp;
	}
	/**
	 * @param timeStamp The m_TimeStamp to set.
	 */
	public void setM_TimeStamp(String timeStamp) {
		m_TimeStamp = timeStamp;
	}
	/**
	 * @param p_Application
	 * @param p_FileName
	 * @param p_Name
	 * @param p_Parameters
	 * @param p_Path
	 * @param p_TimeStamp
	 */
	public ReportRecord(String p_Application, String p_FileName, String p_Name, String p_Parameters, String p_Path, String p_TimeStamp) {
		super();
		m_Application = p_Application;
		m_FileName = p_FileName;
		m_Name = p_Name;
		m_Parameters = p_Parameters;
		m_Path = p_Path;
		m_TimeStamp = p_TimeStamp;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals(Object obj) {
		if (obj instanceof ReportRecord) {
			ReportRecord toCompare = (ReportRecord) obj;
			return this.m_Path.equals(toCompare.m_Path);
		}
		return false;
	}
}
 