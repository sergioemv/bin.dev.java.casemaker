package bi.view.testdataviews;

import java.util.List;

import javax.swing.JButton;

import bi.controller.testdata.formula.CMFormulasEditController;
import bi.view.utils.CMBaseJDialog;
import java.awt.Dimension;
import javax.swing.JPanel;
import java.awt.GridBagLayout;
import bi.view.utils.CMOkCancelPanel;
import java.awt.BorderLayout;

public class CMDialogFormula extends CMBaseJDialog {

	/**
	 * 24/10/2006
	 * svonborries
	 */
	private static final long serialVersionUID = 1L;
	private JPanel panelDialog = null;
	private CMOkCancelPanel panelOkCancel = null;
	private JPanel panelFormulas = null;
	private JPanel panelParameters = null;
	private CMFormulasEditController formulaEditController = null;  //  @jve:decl-index=0:

	/**
	 * This method initializes 
	 * 
	 */
	public CMDialogFormula() {
		super();
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 */
	private void initialize() {
        this.setSize(new Dimension(613, 670));
        this.setContentPane(getPanelDialog());
			
	}

	@Override
	protected void fireButtonCancel() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void fireButtonOk() {
		// TODO Auto-generated method stub

	}

	@Override
	public JButton getDefaultButton() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected List getOrder() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * This method initializes panelDialog	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getPanelDialog() {
		if (panelDialog == null) {
			panelDialog = new JPanel();
			panelDialog.setLayout(new BorderLayout());
			panelDialog.add(getPanelOkCancel(), BorderLayout.SOUTH);
			panelDialog.add(getPanelFormulas(), BorderLayout.NORTH);
			panelDialog.add(getPanelParameters(), BorderLayout.CENTER);
		}
		return panelDialog;
	}

	/**
	 * This method initializes panelOkCancel	
	 * 	
	 * @return bi.view.utils.CMOkCancelPanel	
	 */
	private CMOkCancelPanel getPanelOkCancel() {
		if (panelOkCancel == null) {
			panelOkCancel = new CMOkCancelPanel(this);
		}
		return panelOkCancel;
	}

	/**
	 * This method initializes panelFormulas	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getPanelFormulas() {
		if (panelFormulas == null) {
			panelFormulas = new JPanel();
			panelFormulas.setLayout(new BorderLayout());
			panelFormulas.setPreferredSize(new Dimension(629, 270));
			formulaEditController = new CMFormulasEditController();
			panelFormulas.add(formulaEditController.getPanelFormulas());
		}
		return panelFormulas;
	}

	/**
	 * This method initializes panelParameters	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getPanelParameters() {
		if (panelParameters == null) {
			panelParameters = new JPanel();
			panelParameters.setLayout(new GridBagLayout());
		}
		return panelParameters;
	}

}  //  @jve:decl-index=0:visual-constraint="10,10"
