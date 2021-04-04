/**
 * This Software has been developed by Business Software Innovations  .
 * Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved. 
 */
package bi.view.cells.headers;



import bi.view.cells.CMBaseCell;
import bi.view.cells.renderers.CMCellDefaultHeaderRenderer;

import com.eliad.model.defaults.DefaultGridCellRenderer;
import com.eliad.swing.JSmartGrid;

/**
 * @author smoreno
 *
 */
public class CMCellHeaderDefault extends CMBaseCell {

	/**
	 * @param p_grid
	 * @param p_model
	 */
	public static final DefaultGridCellRenderer defaultGridHeaderRenderer = new CMCellDefaultHeaderRenderer();
	private String text;
	public CMCellHeaderDefault(JSmartGrid p_grid, String text) {
		super(p_grid, null);
		this.setRenderer(defaultGridHeaderRenderer);
		this.setEditor(null);
		this.text = text; 
		// TODO Auto-generated constructor stub
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return text;
	}
	public String getText() {
		return this.text;
	}
	public void setText(String p_text) {
		this.text = p_text;
	}
}
