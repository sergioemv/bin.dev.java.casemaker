/* Generated by Together */

package bi.view.testdataviews;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JScrollPane;

import model.TestDataCombinations;
import bi.view.mainframeviews.CMFrameView;

import com.eliad.swing.JSmartGrid;
import com.eliad.swing.JSmartGridHeader;

public class CMScrollPaneTestDataCombinationsDescription extends JScrollPane {
    public CMScrollPaneTestDataCombinationsDescription(CMFrameView p_Frame)
    {
        	m_Frame = p_Frame;
            initGUI();
    }

    public TestDataCombinations getM_TestDataCombinations(){ return m_TestDataCombinations; }

    public void setM_TestDataCombinations(TestDataCombinations m_TestDataCombinations)
    {
        this.m_TestDataCombinations = m_TestDataCombinations;
        this.m_CMTestDataCombinationView.setM_TestDataCombinations(m_TestDataCombinations);
    }

    public void initGUI()
    {
      m_CMTestDataCombinationView = new CMTestDataCombinationView(m_Frame);
      this.setViewportView(m_CMTestDataCombinationView);
     // m_CMTestDataCombinationView.getViewport1().setScrollMode(JViewport.BACKINGSTORE_SCROLL_MODE);//harold canedo Lopez
      m_CMTestDataCombinationHeader = new CMTestDataCombinationHeader( m_CMTestDataCombinationView);
      m_GridHeader = new JSmartGridHeader( m_CMTestDataCombinationView,JSmartGrid.HORIZONTAL,m_CMTestDataCombinationHeader,null,new CMStyleModelHeader());
//Hcanedo_13_09_2004_Begin
	  //m_GridHeader.setColumnAutoResizeMode(JSmartGridHeader.AUTO_RESIZE_LAST);

      m_GridHeader.setColumnWidth(0,75);
	  m_GridHeader.setColumnWidth(1,75);
      m_GridHeader.setColumnWidth(2,70);
      m_GridHeader.setColumnWidth(3,30);
      m_GridHeader.setColumnWidth(4,250);//530);//1030);
 	  m_GridHeader.setColumnAutoResizeMode(JSmartGridHeader.AUTO_RESIZE_LAST);
//Hcanedo_13_09_2004_End

      m_GridHeader.setBackground(new Color(36,38,116));
      m_GridHeader.setSelectionBackgroundColor(new Color(36,38,116));
      m_GridHeader.setSelectionForegroundColor(new Color(252,254,252));
      m_GridHeader.setGridColor(new Color(196,194,196));
      m_GridHeader.setFont(new Font("Dialog",Font.PLAIN,12)); //$NON-NLS-1$
      m_GridHeader.setForeground(new Color(252,254,252));
      m_GridHeader.setBorder(BorderFactory.createRaisedBevelBorder());
      m_GridHeader.setRowResizable(false);
      m_CMTestDataCombinationView.setColumnHeader(m_GridHeader);
     
//hcanedo_21_09_2004_begin
		m_GridHeader.addMouseListener(new MouseAdapter(){public void mouseClicked(MouseEvent e){thisMouseClicked(e);}});
      addMouseListener(new MouseAdapter(){public void mouseClicked(MouseEvent e){thisMouseClicked(e);}});
//hcanedo_21_09_2004_end
    }
	public void update()
    {
		m_CMTestDataCombinationView.update();
    }
   	public void update(int index)
    {
		m_CMTestDataCombinationView.update(index);
    }
	public void deleteAllViews()
    {
        m_CMTestDataCombinationView.deleteAllViews();
    }

//hcanedo_21_09_2004_begin
    public void thisMouseClicked(MouseEvent e) {
		m_Frame.eventMouseClicked(null);
    }
//hcanedo_21_09_2004_end

    private TestDataCombinations m_TestDataCombinations;
    private CMTestDataCombinationView m_CMTestDataCombinationView;
    private CMTestDataCombinationHeader m_CMTestDataCombinationHeader;
    private CMFrameView m_Frame;
    private JSmartGridHeader m_GridHeader;
	/**
	 * @return Returns the m_CMTestDataCombinationView.
	 */
	public CMTestDataCombinationView getM_CMTestDataCombinationView() {
		return m_CMTestDataCombinationView;
	}

	/**
	 * @param testDataCombinationView The m_CMTestDataCombinationView to set.
	 */
	public void setM_CMTestDataCombinationView(
			CMTestDataCombinationView testDataCombinationView) {
		m_CMTestDataCombinationView = testDataCombinationView;
	}
}
