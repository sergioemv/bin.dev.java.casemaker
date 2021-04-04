package bi.view.actions.testcase;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListSelectionModel;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

import bi.view.icons.CMIcon;
import bi.view.lang.CMMessages;
import bi.view.utils.CMDnDJList;
import bi.view.utils.CMTwinBox;

public class CMPanelStdCombinationsAssigment extends JPanel {

	private static ImageIcon errorIcon = CMIcon.STDCOMBINATION.getImageIcon();  //  @jve:decl-index=0:
	private static final long serialVersionUID = 1L;
	private CMTwinBox twinBox = null;
	private JPanel jPanelBottom = null;
	private JPanel jPanelLeft = null;
	private JPanel jPanelRight = null;

	/**
	 * This is the default constructor
	 */
	public CMPanelStdCombinationsAssigment() {
		super();
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(372, 226);
		this.setLayout(new BorderLayout());
		this.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED), CMMessages.getString("LABEL_ASSIGNED_STD_COMBINATIONS"), TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, new Font("Tahoma", Font.PLAIN, 11), SystemColor.activeCaption));
		this.add(getJPanelBottom(), BorderLayout.SOUTH);
		this.add(getTwinBox(), BorderLayout.CENTER);
		this.add(getJPanelLeft(), BorderLayout.EAST);
		this.add(getJPanelRight(), BorderLayout.WEST);
	}

	/**
	 * This method initializes twinBox	
	 * 	
	 * @return bi.view.utils.CMTwinBox	
	 */
	private CMTwinBox getTwinBox() {
		if (twinBox == null) {
			twinBox = new CMTwinBox();
			twinBox.setPreferredSize(new Dimension(250, 200));
			((CMDnDJList)twinBox.getJListAssigned()).setLeftIcon(errorIcon);
			((CMDnDJList)twinBox.getJListAvailable()).setLeftIcon(errorIcon);
			((CMDnDJList)twinBox.getJListAvailable()).setDragEnabled(false);
			((CMDnDJList)twinBox.getJListAssigned()).setDragEnabled(false);
			((CMDnDJList)twinBox.getJListAvailable()).setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			((CMDnDJList)twinBox.getJListAssigned()).setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			twinBox.getJButtonAllLtoR().setVisible(false);
			twinBox.getJButtonAllRtoL().setVisible(false);
			MouseListener listener = new MouseAdapter(){
						@Override
							public void mouseReleased(MouseEvent arg0) {
							if (twinBox.getJListAssigned().getModel().getSize()>0)
								twinBox.getJListAvailable().setVisible(false);
							else
								twinBox.getJListAvailable().setVisible(true);
						}
			};
			twinBox.getJButtonLtoR().addMouseListener(listener);
			twinBox.getJButtonRtoL().addMouseListener(listener);
		}
		return twinBox;
	}

	/**
	 * This method initializes jPanelBottom	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanelBottom() {
		if (jPanelBottom == null) {
			jPanelBottom = new JPanel();
			jPanelBottom.setLayout(new GridBagLayout());
			jPanelBottom.setPreferredSize(new Dimension(20, 10));
		}
		return jPanelBottom;
	}

	/**
	 * This method initializes jPanelLeft	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanelLeft() {
		if (jPanelLeft == null) {
			jPanelLeft = new JPanel();
			jPanelLeft.setLayout(new GridBagLayout());
			jPanelLeft.setPreferredSize(new Dimension(10, 10));
		}
		return jPanelLeft;
	}

	/**
	 * This method initializes jPanelRight	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanelRight() {
		if (jPanelRight == null) {
			jPanelRight = new JPanel();
			jPanelRight.setLayout(new GridBagLayout());
			jPanelRight.setPreferredSize(new Dimension(10, 0));
		}
		return jPanelRight;
	}

	public JList getJListLeft() {
		return twinBox.getJListAssigned();
	}
	public JList getJListRight() {
		return twinBox.getJListAvailable();
	}


	public List getAssignedList() {
		return twinBox.getAssignedList();
	}
	
	public List getAvailableList(){
		return twinBox.getAvailableList();
	}

	public void clearAllowedList() {
		twinBox.clearAvailableList();
		
	}
	public void clearAssignedList(){
		twinBox.clearAssignedList();
	}

	public void setAssignedList(List p_v) {
		twinBox.setAssignedList(p_v);
	}

	public void setAvailableList(List p_v) {
		twinBox.setAvailableList(p_v);
	}
	
}  //  @jve:decl-index=0:visual-constraint="10,10"
