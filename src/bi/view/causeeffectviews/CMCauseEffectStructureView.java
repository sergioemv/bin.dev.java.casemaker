/* Developed by BUSINESS SOFTWARE INNOVATIONS.  .. Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved..*/

package bi.view.causeeffectviews;

import java.awt.Color;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JScrollPane;

import model.Structure;
import bi.view.mainframeviews.CMFrameView;
import bi.view.testdataviews.CMStyleModelHeader;

import com.eliad.swing.JSmartGrid;
import com.eliad.swing.JSmartGridHeader;

public class CMCauseEffectStructureView extends JScrollPane {
    public CMCauseEffectStructureView(CMFrameView p_cmFrame) {
      m_CMFrameView = p_cmFrame;
      initGUI();
    }

    public void setM_Structure(Structure p_Structure) {
		m_CMCauseEffectStructureGridView.setM_Structure(p_Structure);
    }

    public Structure getM_Structure() {
       return m_CMCauseEffectStructureGridView.getM_Structure();
    }
    public void initGUI() {
      m_CMCauseEffectStructureGridView = new CMCauseEffectStructureGridView(this);
      m_CMCauseEffectHeaderView = new CMCauseEffectHeaderView(m_CMCauseEffectStructureGridView);

      setViewportView(m_CMCauseEffectStructureGridView);
      m_GridHeader = new JSmartGridHeader(m_CMCauseEffectStructureGridView,
        JSmartGrid.HORIZONTAL,m_CMCauseEffectHeaderView,null,new CMStyleModelHeader());
	//fcastro_19102004_begin
      //m_GridHeader.setColumnAutoResizeMode(JSmartGridHeader.AUTO_RESIZE_NEXT);
      m_GridHeader.setColumnWidth(2,70);
//HCanedo_14032006_begin
      m_GridHeader.setColumnWidth(3,70);
      m_GridHeader.setColumnWidth(4,70);
//HCanedo_14032006_end
      //fcastro_19102004_end
      m_GridHeader.setColumnWidth(0,100);
      m_GridHeader.setColumnWidth(1,630);
	 m_GridHeader.setColumnAutoResizeMode(JSmartGridHeader.AUTO_RESIZE_LAST);
      m_GridHeader.setBackground(new Color(36,38,116));
      m_GridHeader.setSelectionBackgroundColor(new Color(36,38,116));
      m_GridHeader.setSelectionForegroundColor(new Color(252,254,252));
      m_GridHeader.setGridColor(new Color(196,194,196));
      m_GridHeader.setFont(new Font("Dialog",Font.PLAIN,12));
      m_GridHeader.setForeground(new Color(252,254,252));
      m_GridHeader.setBorder(BorderFactory.createRaisedBevelBorder());
      m_GridHeader.setRowResizable(false);

      m_CMCauseEffectStructureGridView.setColumnHeader(m_GridHeader);
    }
    public CMCauseEffectStructureGridView getM_CMCauseEffectStructureGridView(){
            return m_CMCauseEffectStructureGridView;
        }

    public void setM_CMCauseEffectStructureGridView(CMCauseEffectStructureGridView m_CMCauseEffectStructureGridView){
            this.m_CMCauseEffectStructureGridView = m_CMCauseEffectStructureGridView;
        }

     public CMFrameView getM_CMFrame(){ return m_CMFrameView; }

    public void setM_CMFrame(CMFrameView m_CMFrame){ this.m_CMFrameView = m_CMFrame; }

  	public CMCauseEffectHeaderView getM_CMCauseEffectHeaderView(){ return m_CMCauseEffectHeaderView; }

  public void setM_CMCauseEffectHeaderView(CMCauseEffectHeaderView m_CMCauseEffectHeaderView){ this.m_CMCauseEffectHeaderView = m_CMCauseEffectHeaderView; }

    private CMCauseEffectStructureGridView m_CMCauseEffectStructureGridView;
    private CMFrameView m_CMFrameView;
  	private CMCauseEffectHeaderView m_CMCauseEffectHeaderView;
    private JSmartGridHeader m_GridHeader;

}
