/* Generated by Together */

package bi.view.testdataviews;

import java.awt.Color;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JScrollPane;
import javax.swing.JViewport;

import model.TDStructure;
import bi.view.mainframeviews.CMFrameView;

import com.eliad.swing.JSmartGrid;
import com.eliad.swing.JSmartGridHeader;
public class CMScrollPaneTestDataSetDescription extends JScrollPane
{
    public CMScrollPaneTestDataSetDescription(CMFrameView p_CMFrameView)
    {
        super();
        m_CMFrameView = p_CMFrameView;
		initGUI();
    }
    public void initGUI()
    {
         m_CMTestDataSetView = new CMTestDataSetView(m_CMFrameView);
      this.setViewportView(m_CMTestDataSetView);
      m_CMTestDataSetView.getViewport1().setScrollMode(JViewport.BACKINGSTORE_SCROLL_MODE);//harold canedo Lopez
      m_CMTestDataSetHeader = new CMTestDataSetHeader( m_CMTestDataSetView);
      m_GridHeader = new JSmartGridHeader( m_CMTestDataSetView,JSmartGrid.HORIZONTAL,m_CMTestDataSetHeader,null,new CMStyleModelHeader());
	 // m_GridHeader.setColumnAutoResizeMode(JSmartGridHeader.AUTO_RESIZE_NEXT);
      m_GridHeader.setColumnWidth(0,70);
	  m_GridHeader.setColumnWidth(1,150);//280);
      m_GridHeader.setColumnWidth(2,70);//150);
	  m_GridHeader.setColumnWidth(3,100);//750);
      m_GridHeader.setColumnAutoResizeMode(JSmartGridHeader.AUTO_RESIZE_LAST);
      m_GridHeader.setBackground(new Color(36,38,116));
      m_GridHeader.setSelectionBackgroundColor(new Color(36,38,116));
      m_GridHeader.setSelectionForegroundColor(new Color(252,254,252));
      m_GridHeader.setGridColor(new Color(196,194,196));
      m_GridHeader.setFont(new Font("Dialog",Font.PLAIN,12)); //$NON-NLS-1$
      m_GridHeader.setForeground(new Color(252,254,252));
      m_GridHeader.setBorder(BorderFactory.createRaisedBevelBorder());
      m_GridHeader.setRowResizable(false);
      m_CMTestDataSetView.setColumnHeader(m_GridHeader);

    }

    public CMFrameView getM_CMFrameView()
    {
        return m_CMFrameView;
    }

    public void setM_CMFrameView(CMFrameView m_CMFrameView)
    {
        this.m_CMFrameView = m_CMFrameView;
    }

    public TDStructure getM_TDStructure()
    {
        return m_TDStructure;
    }

    public void setM_TDStructure(TDStructure m_TDStructure)
    {
        this.m_TDStructure = m_TDStructure;
        m_CMTestDataSetView.setM_TDStructure(m_TDStructure);
    }
	public void update()
    {
        m_CMTestDataSetView.update();
    }
    public void update(int index)
    {
        m_CMTestDataSetView.update(index);
    }
	public void updateJcomboxReports()
    {
        m_CMTestDataSetView.chargeJcomBoxXSL();
    }


    private CMFrameView m_CMFrameView;
    private TDStructure m_TDStructure;
    private CMTestDataSetView m_CMTestDataSetView;
    private CMTestDataSetHeader m_CMTestDataSetHeader;
    private JSmartGridHeader m_GridHeader;
	/**
	 * @return Returns the m_CMTestDataSetView.
	 */
	public CMTestDataSetView getM_CMTestDataSetView() {
		return m_CMTestDataSetView;
	}
	/**
	 * @param testDataSetView The m_CMTestDataSetView to set.
	 */
	public void setM_CMTestDataSetView(CMTestDataSetView testDataSetView) {
		m_CMTestDataSetView = testDataSetView;
	}
}
