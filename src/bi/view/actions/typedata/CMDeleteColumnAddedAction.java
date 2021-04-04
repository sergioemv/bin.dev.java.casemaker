/**
 * Developed by BUSINESS SOFTWARE INNOVATIONS.
 * Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved.
 */
package bi.view.actions.typedata;


import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JOptionPane;

import bi.view.actions.CMAction;
import bi.view.cells.CMCellHeaderTDStructureNewColumn;
import bi.view.cells.CMCellTDStructureNewColumn;
import bi.view.lang.CMMessages;
import bi.view.mainframeviews.CMApplication;
import bi.view.mainframeviews.CMFrameView;
import bi.view.testdataviews.CMGridTDStructure;
import bi.view.testdataviews.CMIndexTDStructureUpdate;

/**
 * @author svonborries
 *
 */
@SuppressWarnings("serial")
public class CMDeleteColumnAddedAction extends AbstractAction implements Action {

	private CMFrameView m_frame;
	private CMGridTDStructure m_gridTDStructure;

	public CMDeleteColumnAddedAction(){
		super(CMMessages.getString("TESTDATA_STRUCTURE_ROW_DELETE_COLUMN"));
		putValue(Action.SHORT_DESCRIPTION, CMMessages.getString("TESTDATA_STRUCTURE_ROW_DELETE_COLUMN"));
	    putValue(Action.SMALL_ICON, CMAction.TESTDATA_DELETE_COLUMN_ADDED.getIcon());
	    putValue(Action.MNEMONIC_KEY,(int)CMMessages.getString("DELETE_COLUMN_ADDED_MNEMONIC").charAt(0));
	}

	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent e) {
		this.m_frame = CMApplication.frame;
		this.m_gridTDStructure = m_frame.getGridTDStructure();

		deleteNewColumnAddedTDStructure();

	}

    public void deleteNewColumnAddedTDStructure(){
    	int confirmation = JOptionPane.YES_OPTION;
        confirmation = JOptionPane.showConfirmDialog(m_frame, CMMessages.getString("TESTDATA_MESSAGE_DELETE_COLUMN_ADDED"),
            CMMessages.getString("TESTDATA_STRUCTURE_ROW_DELETE_COLUMN"), JOptionPane.YES_NO_OPTION);
        if (confirmation == JOptionPane.YES_OPTION) {
        	int columnSelected = 0;
        	int column = this.m_gridTDStructure.getColumnSelected();
        	int row = this.m_gridTDStructure.getRowSelected();
        	Object obj = null;
        	if (row!=-1 && column != -1){
        		 obj = this.m_gridTDStructure.getCellObjectAt(row, column);
        		 columnSelected = column;
        	}
        	else{
        		columnSelected = this.m_gridTDStructure.getM_GridHeader().getSelectionModel().getLeadColumn();
        		if (columnSelected != -1)
        			obj = this.m_gridTDStructure.getM_CMHeaderGridTDStructure().getGroupHeaderTDStructure().elementAt(columnSelected);    		
        	}
        	
        	//Object obj = this.m_gridTDStructure.getCellObjectAt(this.m_gridTDStructure.getRowSelected(),this.m_gridTDStructure.getColumnSelected());
        	if(obj !=null && (obj instanceof CMCellHeaderTDStructureNewColumn) || (obj instanceof CMCellTDStructureNewColumn)){
        		int index = CMIndexTDStructureUpdate.getInstance().getIndex();
        		this.m_gridTDStructure.deleteNewColumnTDStructure(false,columnSelected,index);
            }
        }

    	
    }

}
