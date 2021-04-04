/* Developed by BUSINESS SOFTWARE INNOVATIONS.  .. Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved..*/

package bi.view.testcaseviews;
import java.awt.Adjustable;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;

import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JViewport;

import model.Structure;
import bi.view.elementviews.CMElementViews;
import bi.view.mainframeviews.CMFrameView;
import bi.view.utils.ScrollBarSynchronizer;

public class CMElementAndTestCaseViews extends JSplitPane {
    private CMFrameView m_Frame;
    private JScrollPane leftPane = new JScrollPane();
    private JScrollPane rightPane = new JScrollPane();
    public CMElementAndTestCaseViews(CMFrameView p_CMFrame, CMTestCaseStructureView p_CMTestCaseStructureView) {
      this.m_CMTestCaseStructureView = p_CMTestCaseStructureView;
      m_Frame = p_CMFrame;
      initGUI();
    }

    public void initGUI() {



      lnkCMElementViews = new CMElementViews(m_Frame);
      //fcastr0_20092004_begin
	  m_Frame.getKeyEventIntercepter().setM_TestCasesElementViews(lnkCMElementViews);
      //fcastro_20092004_end

      leftPane.setViewportView(lnkCMElementViews);
      //grueda10112004_begin
      //lnkCMElementViews.getViewport1().setScrollMode(JViewport.BACKINGSTORE_SCROLL_MODE);//fcastro_19102004
  //   lnkCMElementViews.getViewport1().setScrollMode(JViewport.SIMPLE_SCROLL_MODE);//fcastro_19102004
	  //grueda10112004_end
      // CREATE THE RIGHT BOTTOM COMPONENT

      lnkCMTestCaseViews = new CMTestCaseViews(m_Frame,lnkCMElementViews,this);
      //fcastro_20092004_begin
      lnkCMElementViews.setM_RightSideComponent(lnkCMTestCaseViews);
      //fcastro_20092004_end
      rightPane.setViewportView(lnkCMTestCaseViews);
      //grueda10112004_begin

      //Ccastedo begins 20-02-06
      lnkCMTestCaseViews.getViewport1().setScrollMode(JViewport.BLIT_SCROLL_MODE);
      lnkCMElementViews.getViewport1().setScrollMode(JViewport.BLIT_SCROLL_MODE);
     //Ccastedo begins 20-02-06

      leftPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
      rightPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
      leftPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
      JScrollBar jScrollBarLeft = leftPane.getVerticalScrollBar();
      JScrollBar jScrollBarRight = rightPane.getVerticalScrollBar();
      jScrollBarRight.getModel().addChangeListener(new ScrollBarSynchronizer(jScrollBarLeft));
//      jScrollBarLeft.getModel().addChangeListener(new ScrollBarSynchronizer(jScrollBarRight));
      leftPane.getVerticalScrollBar().addAdjustmentListener(new CMAdjustmentListener());
      rightPane.getVerticalScrollBar().addAdjustmentListener(new CMAdjustmentListener());
//
      setDividerSize(8);
      setOneTouchExpandable(true);
      setDividerLocation(lnkCMElementViews.getM_Width() + getDividerSize());
      add(leftPane, "left"); //$NON-NLS-1$
      add(rightPane, "right"); //$NON-NLS-1$

    }
//fcastro_19102004_begin
//	public void updateScrolls(){
//        JScrollPane rightPane = (JScrollPane)this.getRightComponent();
//        JScrollBar barRight = rightPane.getVerticalScrollBar();
//        JScrollPane leftPane = (JScrollPane)this.getLeftComponent();
//        barRight.setValue(leftPane.getVerticalScrollBar().getValue());
//
//    }

    public int getRightHorizontalScrollValue(){
        JScrollPane rightPane = (JScrollPane)this.getRightComponent();
        return rightPane.getHorizontalScrollBar().getValue();
    }
    public void setRightHorizontalScroll(int value){
		JScrollPane rightPane = (JScrollPane)this.getRightComponent();
        JScrollBar bar =rightPane.getHorizontalScrollBar();
        bar.setValue(value);
    }
    //fcastro_19102004_end

    public void update() {
      lnkCMElementViews.update();
      lnkCMTestCaseViews.update();
    }

    public void setM_Structure(Structure p_Structure) {
      lnkCMElementViews.setM_Structure(p_Structure);
      lnkCMTestCaseViews.setM_Structure(p_Structure);
    }

    public Structure getM_Structure() {
      return lnkCMElementViews.getM_Structure();
    }

    public JScrollPane getM_CMElementViewsScrollPane(){ return m_CMElementViewsScrollPane; }

    public void setM_CMElementViewsScrollPane(JScrollPane m_CMElementViewsScrollPane){ this.m_CMElementViewsScrollPane = m_CMElementViewsScrollPane; }

    public JScrollPane getM_CMTestCaseViewsScrollPane(){ return m_CMTestCaseViewsScrollPane; }

    public void setM_CMTestCaseViewsScrollPane(JScrollPane m_CMTestCaseViewsScrollPane){ this.m_CMTestCaseViewsScrollPane = m_CMTestCaseViewsScrollPane; }

    public CMTestCaseViews getLnkCMTestCaseViews(){
            return lnkCMTestCaseViews;
        }

    public void setLnkCMTestCaseViews(CMTestCaseViews lnkCMTestCaseViews){
            this.lnkCMTestCaseViews = lnkCMTestCaseViews;
        }

    public CMElementViews getLnkCMElementViews(){
            return lnkCMElementViews;
        }

    public void setLnkCMElementViews(CMElementViews lnkCMElementViews){
            this.lnkCMElementViews = lnkCMElementViews;
        }

    public CMTestCaseStructureView getM_CMTestCaseStructureView(){
            return m_CMTestCaseStructureView;
        }

    public void setM_CMTestCaseStructureView(CMTestCaseStructureView m_CMTestCaseStructureView){
            this.m_CMTestCaseStructureView = m_CMTestCaseStructureView;
        }

    /**
     * @link aggregationByValue
     * @directed
     */
    private CMElementViews lnkCMElementViews;

    /**
     * @link aggregationByValue
     * @directed
     */
    private CMTestCaseViews lnkCMTestCaseViews;
    private JScrollPane m_CMElementViewsScrollPane;
    private JScrollPane m_CMTestCaseViewsScrollPane;
    private CMTestCaseStructureView m_CMTestCaseStructureView;

    class CMAdjustmentListener implements AdjustmentListener {
        // This method is called whenever the value of a scrollbar is changed,
        // either by the user or programmatically.
        public void adjustmentValueChanged(AdjustmentEvent evt) {
            Adjustable source = evt.getAdjustable();

            // getValueIsAdjusting() returns true if the user is currently
            // dragging the scrollbar's knob and has not picked a final value
            if (evt.getValueIsAdjusting()) {
            	rightPane.repaint();
            	leftPane.repaint();
                return;
            }

            // Determine which scrollbar fired the event
            int orient = source.getOrientation();
            if (orient == Adjustable.HORIZONTAL) {
                // Event from horizontal scrollbar
            } else {
                // Event from vertical scrollbar
            	if (source == leftPane.getVerticalScrollBar())
            	{
            		//rightPane.setVisible(false);//invalidate();
            		//rightPane.getVerticalScrollBar().setValue(0);//leftPane.getVerticalScrollBar().getValue()+1);


            		rightPane.getVerticalScrollBar().setValue(leftPane.getVerticalScrollBar().getValue());
            		rightPane.repaint();
//            		Robot robot=null;
//    				try {
//    					robot = new Robot();
//    					int col = lnkCMElementViews.getFirstSelectedColumn();
//    					robot.keyPress(KeyEvent.VK_RIGHT);
//    					lnkCMElementViews.changeSelection(lnkCMElementViews.getFirstSelectedRow(),col,false,false);
//
//    				} catch (AWTException e1) {
//    					// TODO Auto-generated catch block
//    					e1.printStackTrace();
//    				}
//
            	}
            	else
            	{
            		leftPane.invalidate();
            		leftPane.getVerticalScrollBar().setValue(rightPane.getVerticalScrollBar().getValue());
            		leftPane.repaint();
            	}
            	repaint();
            }

            // Determine the type of event
            int type = evt.getAdjustmentType();
            switch (type) {
              case AdjustmentEvent.UNIT_INCREMENT:
                  // Scrollbar was increased by one unit
                  break;
              case AdjustmentEvent.UNIT_DECREMENT:
                  // Scrollbar was decreased by one unit
                  break;
              case AdjustmentEvent.BLOCK_INCREMENT:
                  // Scrollbar was increased by one block
                  break;
              case AdjustmentEvent.BLOCK_DECREMENT:
                  // Scrollbar was decreased by one block
                  break;
              case AdjustmentEvent.TRACK:
                  // The knob on the scrollbar was dragged
                  break;
            }

            // Get current value
            int value = evt.getValue();
        }
    }

}
