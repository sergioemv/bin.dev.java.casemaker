/* Developed by BUSINESS SOFTWARE INNOVATIONS.  .. Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved..*/

package bi.view.errorviews;

import java.awt.Color;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JScrollPane;

import model.Structure;
import bi.view.mainframeviews.CMFrameView;
import bi.view.testdataviews.CMStyleModelHeader;

import com.eliad.swing.JSmartGrid;
import com.eliad.swing.JSmartGridHeader;


public class CMErrorScrollView extends JScrollPane {
    public CMErrorScrollView(CMFrameView p_cmFrame) {
      m_CMFrameView = p_cmFrame;
      initGUI();
    }

    public void setM_Structure(Structure p_Structure) {
	  m_CMErrorGridView.setM_Structure(p_Structure);
    }

    public Structure getM_Structure() {
       return m_CMErrorGridView.getM_Structure();
    }
    public void initGUI() {
      m_CMErrorGridView = new CMErrorGridView(this);

      m_CMErrorGridHeaderView = new CMErrorGridHeaderView(m_CMErrorGridView);
      setViewportView(m_CMErrorGridView);
      m_GridHeader = new JSmartGridHeader(m_CMErrorGridView,JSmartGrid.HORIZONTAL,
        m_CMErrorGridHeaderView,null,new CMStyleModelHeader());
      m_GridHeader.addMouseListener(new CMErrorGridViewColumnHeaderListener());
      m_GridHeader.setColumnWidth(0,80);
      m_GridHeader.setColumnWidth(1,80);
      m_GridHeader.setColumnWidth(2,80);
      m_GridHeader.setColumnWidth(3,80);
      m_GridHeader.setColumnWidth(4,80);
      m_GridHeader.setColumnWidth(5,80);
      m_GridHeader.setColumnWidth(6,80);
      m_GridHeader.setColumnWidth(7,80);
      m_GridHeader.setColumnWidth(8,80);
      m_GridHeader.setColumnWidth(9,80);
//HCanedo_30112004_Begin
	  m_GridHeader.setColumnWidth(10,80);
      m_GridHeader.setColumnWidth(11,80);
      m_GridHeader.setColumnWidth(12,80);
      //m_GridHeader.setColumnAutoResizeMode(JSmartGridHeader.AUTO_RESIZE_LAST);
//HCanedo_30112004_End
      m_GridHeader.setBackground(new Color(36,38,116));
      m_GridHeader.setSelectionBackgroundColor(new Color(36,38,116));
      m_GridHeader.setSelectionForegroundColor(new Color(252,254,252));
      m_GridHeader.setGridColor(new Color(196,194,196));
      m_GridHeader.setFont(new Font("Dialog",Font.PLAIN,12));
      m_GridHeader.setForeground(new Color(252,254,252));
      m_GridHeader.setBorder(BorderFactory.createRaisedBevelBorder());
      m_GridHeader.setRowResizable(false);
      m_CMErrorGridView.setColumnHeader(m_GridHeader);
      // Disable autoCreateColumnsFromModel otherwise all the column customizations
      // and adjustments will be lost when the model data is sorted
      m_CMErrorGridView.setAutoCreateColumnHeader(false);
    }

    public CMErrorGridView getM_CMErrorGridView(){
            return m_CMErrorGridView;
        }

    public void setM_CMErrorGridView(CMErrorGridView m_CMErrorGridView){
            this.m_CMErrorGridView = m_CMErrorGridView;
        }

    public CMFrameView getM_CMFrame(){ return m_CMFrameView; }

    public void setM_CMFrame(CMFrameView m_CMFrame){ this.m_CMFrameView = m_CMFrame; }

  	public CMErrorGridHeaderView getM_CMErrorGridHeaderView(){ return m_CMErrorGridHeaderView; }

    public void setM_CMErrorGridHeaderView(CMErrorGridHeaderView m_CMErrorGridHeaderView){ this.m_CMErrorGridHeaderView = m_CMErrorGridHeaderView; }

    private CMErrorGridView m_CMErrorGridView;
    private CMFrameView m_CMFrameView;
  	private CMErrorGridHeaderView m_CMErrorGridHeaderView;
    private JSmartGridHeader m_GridHeader;

}
