/**
 * 18/10/2006
 * svonborries
 */
package bi.view.utils.testdata;

import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.BorderFactory;
import javax.swing.border.EtchedBorder;
import javax.swing.BoxLayout;
import javax.swing.JScrollPane;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.BorderLayout;
import javax.swing.JButton;
import java.awt.ComponentOrientation;
import java.util.ArrayList;
import java.util.List;

import javax.swing.SwingConstants;

import bi.view.actions.CMAction;
import bi.view.lang.CMMessages;

/**
 * @author svonborries
 *
 */
public class CMPanelFormulaParameters extends JPanel {

	private static final long serialVersionUID = 1L;
	private JScrollPane jScrollPane = null;
	private JPanel jPanel = null;
	private JPanel panelButtons = null;
	private JButton buttonAdd = null;
	private JButton buttonDelete = null;
	private FlowLayout flowLayout = null;  //  @jve:decl-index=0:
	/**
	 * This is the default constructor
	 */
	public CMPanelFormulaParameters() {
		super();
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(629, 131);
		this.setLayout(new BorderLayout());
		this.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
		this.setMaximumSize(new Dimension(629, 131));
		this.setMinimumSize(new Dimension(629, 131));
		this.setPreferredSize(new Dimension(629, 131));
		this.add(getJScrollPane(), BorderLayout.CENTER);
		this.add(getPanelButtons(), BorderLayout.SOUTH);
	}

	/**
	 * This method initializes jScrollPane	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */
	private JScrollPane getJScrollPane() {
		if (jScrollPane == null) {
			jScrollPane = new JScrollPane();
			jScrollPane.setPreferredSize(new Dimension(535, 117));
			jScrollPane.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
			jScrollPane.setViewportView(getJPanel());
		}
		return jScrollPane;
	}
	
	private FlowLayout getLayoutParameter(){
		if(flowLayout == null){
			flowLayout = new FlowLayout();
			flowLayout.setAlignment(FlowLayout.CENTER);
			flowLayout.setVgap(5);
		}
		return flowLayout;
	}

	public void addPanelParameter(JPanel panelParameter) {
		panelParameter.setLayout(getLayoutParameter());
		getJPanel().add(panelParameter);
		initialize();
	}

	/**
	 * This method initializes jPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			jPanel = new JPanel();
			jPanel.setLayout(new BoxLayout(getJPanel(), BoxLayout.Y_AXIS));
			jPanel.setSize(new Dimension(0, 0));
			jPanel.setPreferredSize(new Dimension(0, 0));
		}
		return jPanel;
	}

	/**
	 * This method initializes panelButtons	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getPanelButtons() {
		if (panelButtons == null) {
			FlowLayout flowLayout1 = new FlowLayout();
			flowLayout1.setAlignment(java.awt.FlowLayout.RIGHT);
			panelButtons = new JPanel();
			panelButtons.setPreferredSize(new Dimension(89, 27));
			panelButtons.setLayout(flowLayout1);
			panelButtons.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
			panelButtons.add(getButtonAdd(), null);
			panelButtons.add(getButtonDelete(), null);
		}
		return panelButtons;
	}

	/**
	 * This method initializes buttonAdd	
	 * 	
	 * @return javax.swing.JButton	
	 */
	public JButton getButtonAdd() {
		if (buttonAdd == null) {
			buttonAdd = new JButton();
			buttonAdd.setIcon(new ImageIcon(getClass().getResource("/bi/view/icons/add_formula_as_param.gif")));
			buttonAdd.setPreferredSize(new Dimension(23, 23));
			buttonAdd.setEnabled(false);
			buttonAdd.setHorizontalAlignment(SwingConstants.CENTER);
			buttonAdd.setToolTipText(CMMessages.getString("TESTDATA_ADDFORMULA_TO0LTIPTEXT"));
		}
		return buttonAdd;
	}

	/**
	 * This method initializes buttonDelete	
	 * 	
	 * @return javax.swing.JButton	
	 */
	public JButton getButtonDelete() {
		if (buttonDelete == null) {
			buttonDelete = new JButton();
			buttonDelete.setIcon(CMAction.TESTDATA_DELETE_FORMULA.getIcon());
			buttonDelete.setEnabled(false);
			buttonDelete.setPreferredSize(new Dimension(23, 23));
			buttonDelete.setToolTipText(CMMessages.getString("TESTDATA_DELETEFORMULA_TO0LTIPTEXT"));
		}
		return buttonDelete;
	}
	
	public List getTabOrder(){
		List<JComponent> focusOrder= new ArrayList<JComponent>();
		focusOrder.add(buttonAdd);
		focusOrder.add(buttonDelete);
		return focusOrder;
	}
	
	

}  //  @jve:decl-index=0:visual-constraint="10,10"
