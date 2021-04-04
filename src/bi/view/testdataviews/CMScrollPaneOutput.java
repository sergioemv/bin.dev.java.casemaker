package bi.view.testdataviews;

import java.awt.Color;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JScrollPane;

import model.TDStructure;
import bi.view.mainframeviews.CMFrameView;

import com.eliad.swing.JSmartGrid;
import com.eliad.swing.JSmartGridHeader;
/**
 ****************************************************************************** Developed by BUSINESS SOFTWARE INNOVATIONS.
 * Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved.
 */
public class CMScrollPaneOutput extends JScrollPane {
    public CMScrollPaneOutput(CMFrameView p_Frame) {
        m_TDStructure=new TDStructure();
        m_CMFrameView=p_Frame;
        initGUI();
    }

    public void initGUI() {
      m_CMGridOutputs= new CMGridOutputs(this);
      m_CMHeaderGridOutputs = new CMHeaderGridOutputs(m_CMGridOutputs);
      setViewportView(m_CMGridOutputs);
      m_GridHeader = new JSmartGridHeader(m_CMGridOutputs,JSmartGrid.HORIZONTAL,m_CMHeaderGridOutputs,null,new CMStyleModelHeader());
      m_GridHeader.setColumnAutoResizeMode(JSmartGridHeader.AUTO_RESIZE_ALL);//AUTO_RESIZE_NEXT);
//hcanedo_21_09_2004_begin
      m_GridHeader.setColumnWidth(0,100);//150);
      m_GridHeader.setColumnWidth(1,140);///*300);*/400);
      m_GridHeader.setColumnWidth(2,80);
      m_GridHeader.setColumnWidth(3,50);//100);
      m_GridHeader.setColumnWidth(4,/*660);*/80);
      m_GridHeader.setColumnAutoResizeMode(JSmartGridHeader.AUTO_RESIZE_SUBSEQUENT);//AUTO_RESIZE_NEXT);
//      m_GridHeader.getStyleModel().tsetHorizontalAlignment(JLabel.LEFT);
//hcanedo_21_09_2004_end
      m_GridHeader.setBackground(new Color(36,38,116));
      m_GridHeader.setSelectionBackgroundColor(new Color(36,38,116));
      m_GridHeader.setSelectionForegroundColor(new Color(252,254,252));
      m_GridHeader.setGridColor(new Color(196,194,196));
      m_GridHeader.setFont(new Font("Dialog",Font.PLAIN,12));
      m_GridHeader.setForeground(new Color(252,254,252));
      m_GridHeader.setBorder(BorderFactory.createRaisedBevelBorder());
      m_GridHeader.setRowResizable(false);
      m_CMGridOutputs.setColumnHeader(m_GridHeader);

    }
    public void update()
    {
        m_CMGridOutputs.update();
    }
	public void deleteReports()
    {
        m_CMGridOutputs.deleteReportSystem();
    }
    public void openReports()
    {
        m_CMGridOutputs.openFiles();
    }
    public void updateApplications()
    {
        m_CMGridOutputs.updateJComboBoxApplications();
    }

    public CMGridOutputs getM_CMGridOutputs(){ return m_CMGridOutputs; }

    public void setM_CMGridOutputs(CMGridOutputs m_CMGridOutputs){ this.m_CMGridOutputs = m_CMGridOutputs; }

    public JSmartGridHeader getM_GridHeader(){ return m_GridHeader; }

    public void setM_GridHeader(JSmartGridHeader m_GridHeader){ this.m_GridHeader = m_GridHeader; }

    public CMHeaderGridOutputs getM_CMHeaderGridOutputs(){ return m_CMHeaderGridOutputs; }

    public void setM_CMHeaderGridOutputs(CMHeaderGridOutputs m_CMHeaderGridOutputs){ this.m_CMHeaderGridOutputs = m_CMHeaderGridOutputs; }

    public CMFrameView getM_CMFrameView(){ return m_CMFrameView; }

    public void setM_CMFrameView(CMFrameView m_CMFrameView){ this.m_CMFrameView = m_CMFrameView; }

    public TDStructure getM_TDStructure(){ return m_TDStructure; }

    public void setM_TDStructure(TDStructure m_TDStructure){ this.m_TDStructure = m_TDStructure; }

    public void renameReport()
    {
        m_CMGridOutputs.rename();
    }
//Hcanedo_13_09_2004_Begin
	public void addReport()
    {
        m_CMGridOutputs.addReport();
    }
//Hcanedo_13_09_2004_End
	
	
    private CMGridOutputs m_CMGridOutputs;
    private JSmartGridHeader m_GridHeader;
    private CMHeaderGridOutputs m_CMHeaderGridOutputs;
    private CMFrameView m_CMFrameView;   
    private TDStructure m_TDStructure;
}
