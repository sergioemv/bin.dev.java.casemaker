/* Developed by BUSINESS SOFTWARE INNOVATIONS.  .. Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved..*/

package bi.view.testcaseviews;
import java.awt.Color;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JViewport;

import model.Structure;
import bi.view.mainframeviews.CMFrameView;
import bi.view.testdataviews.CMStyleModelHeader;

import com.eliad.swing.JSmartGrid;
import com.eliad.swing.JSmartGridHeader;

public class CMTestCaseStructureView extends JSplitPane {
    public CMTestCaseStructureView(CMFrameView p_CMFrame) {
      m_CMFrame = p_CMFrame;
      initGUI();
    }

    public void initGUI(){
      this.setOrientation(0);
      // CREATE THE TOP COMPONENT
      JScrollPane jScrollPane6 = new JScrollPane();
      m_CMDescriptionTestCaseViews = new CMDescriptionTestCaseViews(m_CMFrame,this);
      jScrollPane6.setViewportView(m_CMDescriptionTestCaseViews);
      m_CMDescriptionTestCaseViews.getViewport1().setScrollMode(JViewport.BACKINGSTORE_SCROLL_MODE);

      m_CMTestCaseHeaderView = new CMTestCaseHeaderView(m_CMDescriptionTestCaseViews);
      m_GridHeader = new JSmartGridHeader(m_CMDescriptionTestCaseViews,JSmartGrid.HORIZONTAL,
        m_CMTestCaseHeaderView,null,new CMStyleModelHeader());
      m_GridHeader.setColumnWidth(0,100);
      m_GridHeader.setColumnWidth(1,700);
      m_GridHeader.setColumnAutoResizeMode(JSmartGridHeader.AUTO_RESIZE_LAST);
      m_GridHeader.setBackground(new Color(36,38,116));
      m_GridHeader.setSelectionBackgroundColor(new Color(36,38,116));
      m_GridHeader.setSelectionForegroundColor(new Color(252,254,252));
      m_GridHeader.setGridColor(new Color(196,194,196));
      m_GridHeader.setFont(new Font("Dialog",Font.PLAIN,12)); //$NON-NLS-1$
      m_GridHeader.setForeground(new Color(252,254,252));
      m_GridHeader.setBorder(BorderFactory.createRaisedBevelBorder());
      m_GridHeader.setRowResizable(false);
      m_CMDescriptionTestCaseViews.setColumnHeader(m_GridHeader);


      m_CMElementAndTestCaseViews = new CMElementAndTestCaseViews(m_CMFrame,this);

      // CREATE THE OVERALL SPLITPANE
      
      setDividerSize(8);
      setDividerLocation(200);
      setOneTouchExpandable(true);
      add(m_CMElementAndTestCaseViews, "bottom"); //$NON-NLS-1$
      add(jScrollPane6, "top"); //$NON-NLS-1$
    }

    public void setM_Structure(Structure p_Structure) {
      m_CMDescriptionTestCaseViews.setM_Structure(p_Structure);
      m_CMElementAndTestCaseViews.setM_Structure(p_Structure);
    }

    public Structure getM_Structure() {
      return  m_CMDescriptionTestCaseViews.getM_Structure();
    }

    public CMElementAndTestCaseViews getM_CMElementAndTestCaseViews(){
            return m_CMElementAndTestCaseViews;
        }

    public void setM_CMElementAndTestCaseViews(CMElementAndTestCaseViews m_CMElementAndTestCaseViews){
            this.m_CMElementAndTestCaseViews = m_CMElementAndTestCaseViews;
        }

    public CMDescriptionTestCaseViews getM_CMDescriptionTestCaseViews(){
            return m_CMDescriptionTestCaseViews;
        }

    public void setM_CMDescriptionTestCaseViews(CMDescriptionTestCaseViews m_CMDescriptionTestCaseViews){
            this.m_CMDescriptionTestCaseViews = m_CMDescriptionTestCaseViews;
        }

    public JScrollPane getM_CMDescriptionTestCaseScrollPane(){
            return m_CMDescriptionTestCaseScrollPane;
        }

    public void setM_CMDescriptionTestCaseScrollPane(JScrollPane m_CMDescriptionTestCaseScrollPane){
            this.m_CMDescriptionTestCaseScrollPane = m_CMDescriptionTestCaseScrollPane;
        }
 
    /**
     * Update the CMElementViews grid elements. Nothing more.
     */
    public void updateElementsGrid() {
        m_CMElementAndTestCaseViews.getLnkCMElementViews().fireGridModelChanged();
    }

    /**
     * @link aggregationByValue
     * @directed 
     */
    private CMDescriptionTestCaseViews m_CMDescriptionTestCaseViews;

    /**
     * @link aggregationByValue
     * @directed 
     */
    private CMElementAndTestCaseViews m_CMElementAndTestCaseViews;
    private JScrollPane m_CMDescriptionTestCaseScrollPane;
  	private CMTestCaseHeaderView m_CMTestCaseHeaderView;
    private JSmartGridHeader m_GridHeader;
    private CMFrameView m_CMFrame;

}
