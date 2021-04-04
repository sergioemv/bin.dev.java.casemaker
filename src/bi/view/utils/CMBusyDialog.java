package bi.view.utils;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

public class CMBusyDialog extends CMBaseJDialog {

	private JPanel jPanelMain = null;
	private JLabel jLabelCurrentTask = null;
	private JProgressBar jProgressBarOne = null;
	private JButton jButtonCancel = null;

	/**
	 * This method initializes 
	 * 
	 */
	public CMBusyDialog() {
		super();
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 */
	private void initialize() {
        this.setSize(new Dimension(330, 111));
        this.setTitle("Something Processing ...");
        this.setContentPane(getJPanelMain());
        this.setModal(true);
        this.setResizable(false);
			
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
	 * This method initializes jPanelMain	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanelMain() {
		if (jPanelMain == null) {
			jLabelCurrentTask = new JLabel();
			jLabelCurrentTask.setText("JLabel");
			FlowLayout flowLayout = new FlowLayout();
			flowLayout.setAlignment(FlowLayout.LEFT);
			jPanelMain = new JPanel();
			jPanelMain.setLayout(flowLayout);
			jPanelMain.add(jLabelCurrentTask, null);
			jPanelMain.add(getJProgressBarOne(), null);
			jPanelMain.add(getJButtonCancel(), null);
		}
		return jPanelMain;
	}

	/**
	 * This method initializes jProgressBarOne	
	 * 	
	 * @return javax.swing.JProgressBar	
	 */
	private JProgressBar getJProgressBarOne() {
		if (jProgressBarOne == null) {
			jProgressBarOne = new JProgressBar();
		}
		return jProgressBarOne;
	}

	/**
	 * This method initializes jButtonCancel	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButtonCancel() {
		if (jButtonCancel == null) {
			jButtonCancel = new JButton();
			jButtonCancel.setText("Cancel Op");
		}
		return jButtonCancel;
	}

}  //  @jve:decl-index=0:visual-constraint="10,10"
