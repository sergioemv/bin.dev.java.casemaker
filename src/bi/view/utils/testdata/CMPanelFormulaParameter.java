package bi.view.utils.testdata;

import javax.swing.JPanel;
import java.awt.Dimension;

import javax.swing.JComponent;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.FlowLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;

import bi.view.actions.CMAction;
import bi.view.icons.CMIcon;
import bi.view.lang.CMMessages;

public class CMPanelFormulaParameter extends JPanel {

	private static final long serialVersionUID = 1L;
	private JTextField textParam = null;
	private JLabel laberParam = null;
	private JButton formula = null;
	private JButton linkElement = null;
	private String textLabel = null;  //  @jve:decl-index=0:
	private JButton buttonEdit = null;
	private JButton buttonDelete = null;
	private JButton buttonAddVariable = null;
	/**
	 * This is the default constructor
	 */
	public CMPanelFormulaParameter() {
		super();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		FlowLayout flowLayout = new FlowLayout();
		flowLayout.setAlignment(FlowLayout.LEFT);
		flowLayout.setVgap(2);
		flowLayout.setHgap(10);
		laberParam = new JLabel();
		laberParam.setText(getTextLabel());
		laberParam.setPreferredSize(new Dimension(130, 16));
		this.setLayout(flowLayout);
		this.setSize(562, 28);
		this.setMinimumSize(new Dimension(598, 27));
		this.setMaximumSize(new Dimension(630, 27));
		this.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		this.add(getTextParam(), null);
		this.add(laberParam, null);
		this.add(getButtonFormula(), null);
		this.add(getButtonEdit(), null);
		this.add(getButtonDelete(), null);
		this.add(getButtonLinkElement(), null);
		this.add(getButtonAddVariable(), null);
	}

	/**
	 * This method initializes textParam	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	public JTextField getTextParam() {
		if (textParam == null) {
			textParam = new JTextField();
			textParam.setPreferredSize(new Dimension(240, 20));
		}
		return textParam;
	}

	/**
	 * This method initializes formula	
	 * 	
	 * @return javax.swing.JButton	
	 */
	public JButton getButtonFormula() {
		if (formula == null) {
			formula = new JButton();
			formula.setIcon(CMAction.TESTDATA_ADD_FORMULA.getIcon());
			formula.setPreferredSize(new Dimension(23, 23));
			formula.setMinimumSize(new Dimension(23, 23));
			formula.setToolTipText(CMMessages.getString("TESTDATA_ADDFORMULAPARAMETER_TO0LTIPTEXT"));
		}
		return formula;
	}

	/**
	 * This method initializes linkElement	
	 * 	
	 * @return javax.swing.JButton	
	 */
	public JButton getButtonLinkElement() {
		if (linkElement == null) {
			linkElement = new JButton();
			linkElement.setEnabled(true);
			linkElement.setPreferredSize(new Dimension(23, 23));
			linkElement.setIcon(CMIcon.TESTDATA_ADD_LINK_ELEMENT.getImageIcon());
			linkElement.setMinimumSize(new Dimension(23, 23));
			linkElement.setToolTipText(CMMessages.getString("TESTDATA_LINKBUTTON_TO0LTIPTEXT"));
		}
		return linkElement;
	}

	public String getTextLabel() {
		if(textLabel == null)
			textLabel = "";
		return textLabel;
	}

	public void setTextLabel(String text) {
		this.textLabel = text;
		initialize();
	}

	public void setTextParam(String textParam) {
		this.textParam.setText(textParam);
	}

	/**
	 * This method initializes buttonEdit	
	 * 	
	 * @return javax.swing.JButton	
	 */
	public JButton getButtonEdit() {
		if (buttonEdit == null) {
			buttonEdit = new JButton();
			buttonEdit.setPreferredSize(new Dimension(23, 23));
			buttonEdit.setEnabled(false);
			buttonEdit.setIcon(CMAction.TESTDATA_EDIT_FORMULA.getIcon());
			buttonEdit.setToolTipText(CMMessages.getString("TESTDATA_EDITPARAMETER_TO0LTIPTEXT"));
		}
		return buttonEdit;
	}

	/**
	 * This method initializes buttonDelete	
	 * 	
	 * @return javax.swing.JButton	
	 */
	public JButton getButtonDelete() {
		if (buttonDelete == null) {
			buttonDelete = new JButton();
			buttonDelete.setEnabled(false);
			buttonDelete.setIcon(CMAction.TESTDATA_DELETE_FORMULA.getIcon());
			buttonDelete.setPreferredSize(new Dimension(23, 23));
			buttonDelete.setToolTipText(CMMessages.getString("TESTDATA_DELETEPARAMETER_TO0LTIPTEXT"));
		}
		return buttonDelete;
	}

	/**
	 * This method initializes buttonAddVariable	
	 * 	
	 * @return javax.swing.JButton	
	 */
	public JButton getButtonAddVariable() {
		if (buttonAddVariable == null) {
			buttonAddVariable = new JButton();
			buttonAddVariable.setPreferredSize(new Dimension(23, 23));
			buttonAddVariable.setIcon(CMAction.TESTDATA_ADD_VARIABLE.getIcon());
			buttonAddVariable.setToolTipText(CMMessages.getString("TESTDATA_ADD_VARIABLE"));
		}
		return buttonAddVariable;
	}
	
	public List getTabOrder(){
		List<JComponent> focusOrder= new ArrayList<JComponent>();
		focusOrder.add(textParam);
		focusOrder.add(formula);
		focusOrder.add(buttonEdit);
		focusOrder.add(buttonDelete);
		focusOrder.add(linkElement);
		focusOrder.add(buttonAddVariable);
		return focusOrder;
	}

}  //  @jve:decl-index=0:visual-constraint="10,10"
