/*******************************************************************************
Developed by BUSINESS SOFTWARE INNOVATIONS. .
Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved.
*******************************************************************************/
 

package  bi.view.utils;

import java.io.File;

import javax.swing.filechooser.FileFilter;

import bi.view.lang.CMMessages;

	/**
	 * @author smoreno
	 * This enum holds all posible filters for the CM Files
	 */
	public enum CMFileFilter{
	CPR(".cpr.xml",CMMessages.getString("LABEL_JUST_PROJECT_FILES")),
	CTO(".cto.xml",CMMessages.getString("LABEL_JUST_TEST_OBJECT_FILES")),
	CSV(".csv",CMMessages.getString("LABEL_JUST_CSV_FILES")),
	XSL(".xsl",CMMessages.getString("LABEL_JUST_XSL_FILES")),
	TXT(".txt",CMMessages.getString("LABEL_JUST_BUSINESS_RULES_FILES")),
	CTV(".ctv.xml",CMMessages.getString("LABEL_JUST_TOOLVENDOR_FILES")),
	VDX(".vdx",CMMessages.getString("LABEL_JUST_XML_VISIO_FILES")),
	XRL(".xrl",CMMessages.getString("LABEL_JUST_XRL_FILES")), 
	VOC(".voc",CMMessages.getString("LABEL_JUST_VOC_FILES")),
	//portiz_25102007_begin
	BOM(".bom",CMMessages.getString("LABEL_JUST_BOM_FILES"));
	//portiz_25102007_begin	
	
	private String extension;
	private String description;
	private FileFilter filter;
        CMFileFilter(String ext,String p_description) {
		    extension = ext;	
		    description = p_description;
		}
		public String getExtension(File f) {
            StringBuffer ext = new StringBuffer();
			//String ext = null;
			String s = f.getName();
			//int i = s.lastIndexOf('.');
            int i = s.indexOf('.');
	
			if (i > 0 &&  i < s.length() - 1) {
				//ext = s.substring(i+1).toLowerCase();
                ext.append(s.substring(i+1).toLowerCase());
			}
            ext.insert(0,"."); //$NON-NLS-1$
			return ext.toString();
		}
	
	public String getExtension() {
		return extension;
	}
	public FileFilter getFilter(){
		if (filter==null) {
			filter = new FileFilter() {

				@Override
				public boolean accept(File f) {
					if (f.isDirectory()) {
						return true;
					}
					String new_extension = getExtension(f);
					if (new_extension != null) {
						if (extension.equalsIgnoreCase(new_extension)) {
								return true;
						} else {
							return false;
						}
					}
					return false;

				}

				@Override
				public String getDescription() {
					return description;
				}};
		}
		return filter;
	}
	
	}
