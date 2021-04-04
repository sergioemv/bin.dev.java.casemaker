/**
 * This Software has been developed by Business Software Innovations  .
 * Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved. 
 */
package bi.controller.cmimport.ui;

import javax.swing.JScrollPane;

/**
 * @author smoreno
 *
 */
public class CMCSVImportPreviewScrollPane extends JScrollPane {

	private CMCVSImportPreviewGrid jTablePreview = new CMCVSImportPreviewGrid();
	private CMCSVImportOptionsDialog dialog;

	/**
	 * 
	 */
	public CMCSVImportPreviewScrollPane(CMCSVImportOptionsDialog parentDialog) {
		// TODO Auto-generated constructor stub
		super(new CMCVSImportPreviewGrid());
		dialog = parentDialog;
		jTablePreview = (CMCVSImportPreviewGrid) this.getViewport().getView();
		jTablePreview.setDialog(dialog);
		initialize();
		
	}

	/**
	*@autor smoreno
	 */
	private void initialize() {
		setPreferredSize(new java.awt.Dimension(350,180));
		setViewportView(getJTablePreview());
		//parent
	}

	/**
	*@autor smoreno
	 * @return
	 */
	/**
	 * This method initializes jTablePreview	
	 * 	
	 * @return javax.swing.JTable	
	 */
	 public CMCVSImportPreviewGrid getJTablePreview() {
		
		return jTablePreview;
	}
	
}  //  @jve:decl-index=0:visual-constraint="12,44"
