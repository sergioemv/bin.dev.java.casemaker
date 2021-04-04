/* Developed by BUSINESS SOFTWARE INNOVATIONS.  .. Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved..*/

package bi.view.stdcombinationviews;
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

public class CMStdCombinationStructureView extends JSplitPane {
    public CMStdCombinationStructureView(CMFrameView p_CMFrame) {
      //super(JSplitPane.VERTICAL_SPLIT);
      m_CMFrame = p_CMFrame;
      initGUI();
    }

    public void initGUI(){
      this.setOrientation(0);
      // CREATE THE TOP COMPONENT
      JScrollPane jScrollPane6 = new JScrollPane();
      m_CMDescriptionStdCombinationViews = new CMDescriptionStdCombinationViews(m_CMFrame,this);
      jScrollPane6.setViewportView(m_CMDescriptionStdCombinationViews);
      m_CMDescriptionStdCombinationViews.getViewport1().setScrollMode(JViewport.BACKINGSTORE_SCROLL_MODE);

      m_CMStdCombinationHeaderView = new CMStdCombinationHeaderView(m_CMDescriptionStdCombinationViews);
      m_GridHeader = new JSmartGridHeader(m_CMDescriptionStdCombinationViews,
        JSmartGrid.HORIZONTAL,m_CMStdCombinationHeaderView,null,new CMStyleModelHeader());
      m_GridHeader.setColumnWidth(0,200);
      m_GridHeader.setColumnWidth(1,600);
      m_GridHeader.setColumnAutoResizeMode(JSmartGridHeader.AUTO_RESIZE_LAST);
      m_GridHeader.setBackground(new Color(36,38,116));
      m_GridHeader.setSelectionBackgroundColor(new Color(36,38,116));
      m_GridHeader.setSelectionForegroundColor(new Color(252,254,252));
      m_GridHeader.setGridColor(new Color(196,194,196));
      m_GridHeader.setFont(new Font("Dialog",Font.PLAIN,12)); //$NON-NLS-1$
      m_GridHeader.setForeground(new Color(252,254,252));
      m_GridHeader.setBorder(BorderFactory.createRaisedBevelBorder());
      m_GridHeader.setRowResizable(false);
      m_CMDescriptionStdCombinationViews.setColumnHeader(m_GridHeader);


      // CREATE THE LEFT BOTTOM COMPONENT
        //JScrollPane jScrollPane8 = new JScrollPane();
        //jScrollPane8.getViewport().setLayout(new BorderLayout());
        //jScrollPane8.getViewport().add(jTable7, "West");


      // CREATE THE RIGHT BOTTOM COMPONENT
        //JScrollPane jScrollPane7 = new JScrollPane();
        //jScrollPane7.getViewport().setLayout(new BorderLayout());
        //jScrollPane7.getViewport().add(jTable6, "Center");


      // CREATE THE BOTTOM COMPONENT
      m_CMElementAndStdCombinationViews = new CMElementAndStdCombinationViews(m_CMFrame,this);
      //JSplitPane jSplitPane5 = new JSplitPane();
      //jSplitPane5.add(jScrollPane8, "left");
      //jSplitPane5.add(jScrollPane7, "right");


      // CREATE THE OVERALL SPLITPANE

      setDividerSize(8);
      setOneTouchExpandable(true);
      setDividerLocation(200);
      add(m_CMElementAndStdCombinationViews, "bottom"); //$NON-NLS-1$
      add(jScrollPane6, "top"); //$NON-NLS-1$

	  //this.m_CMDescriptionStdCombinationScrollPane = new JScrollPane(m_CMDescriptionStdCombinationViews);




      //this.setTopComponent(m_CMDescriptionStdCombinationScrollPane);
      //this.setBottomComponent(m_CMElementAndTestCaseViews);
      //this.setOneTouchExpandable(true);

    }

    public void setM_Structure(Structure p_Structure) {
      m_CMDescriptionStdCombinationViews.setM_Structure(p_Structure);
      m_CMElementAndStdCombinationViews.setM_Structure(p_Structure);
    }

    public Structure getM_Structure() {
      return  m_CMDescriptionStdCombinationViews.getM_Structure();
    }

    public CMElementAndStdCombinationViews getM_CMElementAndStdCombinationViews(){
            return m_CMElementAndStdCombinationViews;
        }

    public void setM_CMElementAndStdCombinationViews(CMElementAndStdCombinationViews m_CMElementAndStdCombinationViews){
            this.m_CMElementAndStdCombinationViews = m_CMElementAndStdCombinationViews;
        }

    public CMDescriptionStdCombinationViews getM_CMDescriptionStdCombinationViews(){
            return m_CMDescriptionStdCombinationViews;
        }

    public void setM_CMDescriptionStdCombinationViews(CMDescriptionStdCombinationViews m_CMDescriptionStdCombinationViews){
            this.m_CMDescriptionStdCombinationViews = m_CMDescriptionStdCombinationViews;
        }

    public JScrollPane getM_CMDescriptionStdCombinationScrollPane(){
            return m_CMDescriptionStdCombinationScrollPane;
        }

    public void setM_CMDescriptionStdCombinationScrollPane(JScrollPane m_CMDescriptionStdCombinationScrollPane){
            this.m_CMDescriptionStdCombinationScrollPane = m_CMDescriptionStdCombinationScrollPane;
        }

    /**
     * @link aggregationByValue
     * @directed 
     */
    private CMDescriptionStdCombinationViews m_CMDescriptionStdCombinationViews;

    /**
     * @link aggregationByValue
     * @directed 
     */
    private CMElementAndStdCombinationViews m_CMElementAndStdCombinationViews;
    private JScrollPane m_CMDescriptionStdCombinationScrollPane;
  	private CMStdCombinationHeaderView m_CMStdCombinationHeaderView;
    private JSmartGridHeader m_GridHeader;
    private CMFrameView m_CMFrame;

}
