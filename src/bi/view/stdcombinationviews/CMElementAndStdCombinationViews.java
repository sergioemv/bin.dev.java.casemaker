/* Developed by BUSINESS SOFTWARE INNOVATIONS.  .. Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved..*/

package bi.view.stdcombinationviews;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JViewport;

import model.Structure;
import bi.view.elementviews.CMElementViews;
import bi.view.mainframeviews.CMFrameView;
import bi.view.utils.CMAdjustmentListener;
import bi.view.utils.ScrollBarSynchronizer;

public class CMElementAndStdCombinationViews extends JSplitPane {
    private CMFrameView m_Frame;
    public CMElementAndStdCombinationViews(CMFrameView p_CMFrame, CMStdCombinationStructureView p_CMStdCombinationStructureView) {
      //super(JSplitPane.HORIZONTAL_SPLIT);
      this.m_CMStdCombinationStructureView = p_CMStdCombinationStructureView;
      m_Frame = p_CMFrame;
      initGUI();
    }

    public void initGUI() {
      // CREATE THE LEFT BOTTOM COMPONENT
      JScrollPane jScrollPane8 = new JScrollPane();
      lnkCMElementViews = new CMElementViews(m_Frame);
      //fcastro_20092004_begin
      m_Frame.getKeyEventIntercepter().setM_StdCombinationsElementViews(lnkCMElementViews);
      //fcastro_20092004_end
      jScrollPane8.setViewportView(lnkCMElementViews);
  

      // CREATE THE RIGHT BOTTOM COMPONENT
      JScrollPane jScrollPane7 = new JScrollPane();
      lnkCMStdCombinationViews = new CMStdCombinationViews(m_Frame,lnkCMElementViews,this);
       //fcastro_20092004_begin
      lnkCMElementViews.setM_RightSideComponent(lnkCMStdCombinationViews);
      //fcastro_20092004_end
      jScrollPane7.setViewportView(lnkCMStdCombinationViews);
  
//    Ccastedo begins 20-02-06
      lnkCMStdCombinationViews.getViewport1().setScrollMode(JViewport.BLIT_SCROLL_MODE);
      lnkCMElementViews.getViewport1().setScrollMode(JViewport.BLIT_SCROLL_MODE);      
     //Ccastedo begins 20-02-06
      
      jScrollPane8.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
      jScrollPane7.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
      jScrollPane8.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);

      JScrollBar jScrollBarLeft = jScrollPane8.getVerticalScrollBar();
      JScrollBar jScrollBarRight = jScrollPane7.getVerticalScrollBar();
      jScrollBarRight.getModel().addChangeListener(new ScrollBarSynchronizer(jScrollBarLeft));
      jScrollBarLeft.getModel().addChangeListener(new ScrollBarSynchronizer(jScrollBarRight));
     
      //Ccastedo begins 21-02-06
      jScrollPane8.getVerticalScrollBar().addAdjustmentListener(new CMAdjustmentListener(this,jScrollPane8,jScrollPane7));
      jScrollPane7.getVerticalScrollBar().addAdjustmentListener(new CMAdjustmentListener(this,jScrollPane8,jScrollPane7));
      //Ccastedo ends 21-02-06
      
      setDividerSize(8);
      setOneTouchExpandable(true);
      setDividerLocation(this.lnkCMElementViews.getM_Width() + getDividerSize());
      add(jScrollPane8, "left"); //$NON-NLS-1$
      add(jScrollPane7, "right"); //$NON-NLS-1$
    }
//fcastro_19102004_begin
	public void updateScrolls(){
        JScrollPane rightPane = (JScrollPane)this.getRightComponent();
        JScrollBar barRight = rightPane.getVerticalScrollBar();
        JScrollPane leftPane = (JScrollPane)this.getLeftComponent();
        barRight.setValue(leftPane.getVerticalScrollBar().getValue());
    }
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
      lnkCMStdCombinationViews.update();
    }

    public void setM_Structure(Structure p_Structure) {
      lnkCMElementViews.setM_Structure(p_Structure);
      lnkCMStdCombinationViews.setM_Structure(p_Structure);
    }

    public Structure getM_Structure() {
      return lnkCMElementViews.getM_Structure();
    }

    public JScrollPane getM_CMElementViewsScrollPane(){ return m_CMElementViewsScrollPane; }

    public void setM_CMElementViewsScrollPane(JScrollPane m_CMElementViewsScrollPane){ this.m_CMElementViewsScrollPane = m_CMElementViewsScrollPane; }

    public JScrollPane getM_CMStdCombinationViewsScrollPane(){ return m_CMStdCombinationViewsScrollPane; }

    public void setM_CMStdCombinationViewsScrollPane(JScrollPane m_CMStdCombinationViewsScrollPane){ this.m_CMStdCombinationViewsScrollPane = m_CMStdCombinationViewsScrollPane; }

    public CMStdCombinationViews getLnkCMStdCombinationViews(){
            return lnkCMStdCombinationViews;
        }

    public void setLnkCMStdCombinationViews(CMStdCombinationViews lnkCMStdCombinationViews){
            this.lnkCMStdCombinationViews = lnkCMStdCombinationViews;
        }

    public CMElementViews getLnkCMElementViews(){
            return lnkCMElementViews;
        }

    public void setLnkCMElementViews(CMElementViews lnkCMElementViews){
            this.lnkCMElementViews = lnkCMElementViews;
        }

    public CMStdCombinationStructureView getM_CMStdCombinationStructureView(){
            return m_CMStdCombinationStructureView;
        }

    public void setM_CMStdCombinationStructureView(CMStdCombinationStructureView m_CMStdCombinationStructureView){
            this.m_CMStdCombinationStructureView = m_CMStdCombinationStructureView;
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
    private CMStdCombinationViews lnkCMStdCombinationViews;
    private JScrollPane m_CMElementViewsScrollPane;
    private JScrollPane m_CMStdCombinationViewsScrollPane;
    private CMStdCombinationStructureView m_CMStdCombinationStructureView;
}
