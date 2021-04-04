package bi.view.actions.testcase;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.SystemColor;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.DefaultListCellRenderer;
import javax.swing.ImageIcon;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import model.Combination;

import bi.view.icons.CMIcon;
import bi.view.lang.CMMessages;
import bi.view.utils.CMDnDJList;
import bi.view.utils.CMTwinBox;
import bi.view.utils.CMDnDJList.CMDnDJListCellRenderer;

public class CMPanelCombinationsAssigment extends JPanel {

	private static ImageIcon combinationIcon = CMIcon.EQUIVALENCECLASS_IN_COMBINATION.getImageIcon();  //  @jve:decl-index=0:
	private static final long serialVersionUID = 1L;
	private CMTwinBox twinBox = null;
	private JPanel jPanelBottom = null;
	private JPanel jPanelLeft = null;
	private JPanel jPanelRight = null;

	/**
	 * This is the default constructor
	 */
	public CMPanelCombinationsAssigment() {
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
		this.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED), CMMessages.getString("LABEL_ASSIGNED_COMBINATIONS"), TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, new Font("Tahoma", Font.PLAIN, 11), SystemColor.activeCaption));
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
			DefaultListCellRenderer renderer = new DefaultListCellRenderer(){
				@Override
				public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
					// TODO Auto-generated method stub
					Component c =super.getListCellRendererComponent(list, value, index, isSelected,
							cellHasFocus);
					setIcon(combinationIcon);
					Combination combination = (Combination) value;
					setText((combination.isUsed()?"(Used) ":"")+combination.toString()+combination.getStateName()+" "+combination.getDescription());
					return c;
				}
			};
			((CMDnDJList)twinBox.getJListAssigned()).setCellRenderer(renderer);
			((CMDnDJList)twinBox.getJListAvailable()).setCellRenderer(renderer);
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
