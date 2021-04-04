/* Generated by Together */

package bi.view.testdataviews;

import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;

import model.TDStructure;
import bi.view.mainframeviews.CMFrameView;

public class CMPanelTDStructureView extends JPanel {
	 /** Creates new form CMPanelTDStructureView */
    public CMPanelTDStructureView(CMFrameView p_Frame) {
        m_Frame = p_Frame;
        ScrollPaneStructureDescriptionView = new CMScrollpaneStructureDescription(m_Frame);       
        initGUI();
    }

    /** This method is called from within the constructor to initialize the form. */
    private void initGUI() {
        setLayout(new java.awt.BorderLayout());
        setBounds(new java.awt.Rectangle(0, 0, 616, 328));
        setBorder(null);
        add(SplitPaneTDStructurePanel, java.awt.BorderLayout.CENTER);
        SplitPaneTDStructurePanel.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
        SplitPaneTDStructurePanel.setDividerLocation(200);
        
        
        ScrollPaneTDStructureView.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        ScrollPaneTDStructureView.setViewportView(m_CMGridTDStructure);
        
        SplitPaneTDStructurePanel.add(ScrollPaneTDStructureView, javax.swing.JSplitPane.BOTTOM);    
        
        SplitPaneTDStructurePanel.add(ScrollPaneStructureDescriptionView, javax.swing.JSplitPane.TOP);

      //ccastedo 08.01.07  ScrollPaneTDStructureView.addMouseListener(new MouseAdapter(){public void mouseClicked(MouseEvent e){ScrollPaneTDStructureViewMouseClicked(e);}});

        
           
        
	}


    public TDStructure getTDStructure(){ return testData; }
	public void update()
    {
        ScrollPaneStructureDescriptionView.update();
      //  m_Frame.updateGridTDStructure();
       // m_CMGridTDStructure.update(CMIndexTDStructureUpdate.getInstance().getIndex());
    }
    public void updateAll()
    {
        ScrollPaneStructureDescriptionView.updateAll();
    }
    public void setTDStructure(TDStructure testData){
        this.testData = testData;
        ScrollPaneStructureDescriptionView.setM_TDStructure(testData);
    }
//hcanedo_21_09_2004_begin
    public void ScrollPaneTDStructureViewMouseClicked(MouseEvent e) {
		m_Frame.eventMouseClicked(null);
    }
//hcanedo_21_09_2004_end
	public void setAccessRights(boolean right)
    {
        this.ScrollPaneStructureDescriptionView.setAccessRights(right);
    }
	public void setGridTDStructure(CMGridTDStructure p_CMGridTDStructure)
    {
        m_CMGridTDStructure=p_CMGridTDStructure;
      //  ScrollPaneTDStructureView.setViewportView(p_CMGridTDStructure);
      //  ScrollPaneTDStructureView.setBorder(null);
    }
    private CMFrameView m_Frame;
    private JSplitPane SplitPaneTDStructurePanel = new JSplitPane();
    public JScrollPane ScrollPaneTDStructureView = new JScrollPane();
 
    public JScrollPane ScrollPaneTDStructureViewBottom = new JScrollPane();
	public CMScrollpaneStructureDescription ScrollPaneStructureDescriptionView;
   // private CMGridTDStructure gridTDStructure = new CMGridTDStructure(m_Frame);
    private TDStructure testData;
    private CMGridTDStructure m_CMGridTDStructure;
	protected List getOrder() {
		List focusOrder= new ArrayList();
		focusOrder.add(ScrollPaneStructureDescriptionView.getM_cmStructureView());
		focusOrder.add(m_CMGridTDStructure);
		focusOrder.add(m_Frame.getTreeWorkspaceView());
		return focusOrder;
	}

	/**
	 * @return Returns the scrollPaneStructureDescriptionView.
	 */
	public CMScrollpaneStructureDescription getScrollPaneStructureDescriptionView() {
		return ScrollPaneStructureDescriptionView;
	}

	/**
	 * @param scrollPaneStructureDescriptionView The scrollPaneStructureDescriptionView to set.
	 */
	public void setScrollPaneStructureDescriptionView(
			CMScrollpaneStructureDescription scrollPaneStructureDescriptionView) {
		ScrollPaneStructureDescriptionView = scrollPaneStructureDescriptionView;
	}
}


