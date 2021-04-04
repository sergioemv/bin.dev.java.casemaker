package bi.view.businessrulesviews.dialogs;



import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.text.JTextComponent;

import bi.view.lang.CMMessages;
import bi.view.utils.CMBaseJDialog;

@SuppressWarnings("serial")
public class CMFindReplaceDialog extends CMBaseJDialog {
	private CMFindReplacePanel m_CMFindReplacePanel;
	private JTextComponent m_EditorArea;
	public CMFindReplaceDialog(JFrame frame,JTextComponent p_EditorArea ) {
		super(frame);
		m_EditorArea=p_EditorArea;
		initialize();
	}


	


	public CMFindReplaceDialog(JTextComponent p_Editor) {
		super();
		m_EditorArea=p_Editor;
		initialize();
	}
	
	
	private void initialize() {
		setTitle( CMMessages.getString("BUSINESSRULES_FIND_REPLACE_DIALOG_TITLE") ); //$NON-NLS-1$
		m_CMFindReplacePanel=new CMFindReplacePanel(m_EditorArea); 
		getContentPane().add(m_CMFindReplacePanel);
		setSize( 300, 400 );
		
	}
	@Override
	public JButton getDefaultButton() {
		return m_CMFindReplacePanel.jButtonFind;
	}

	@SuppressWarnings("unchecked")
	@Override
	protected List getOrder() {
		List focusOrder= new ArrayList();
		focusOrder.add(m_CMFindReplacePanel.jComboBoxFind);
		focusOrder.add(m_CMFindReplacePanel.jComboBoxReplaceWith);
		focusOrder.add(m_CMFindReplacePanel.jRadioButtonForward);
		focusOrder.add(m_CMFindReplacePanel.jRadioButtonBackward);
		focusOrder.add(m_CMFindReplacePanel.jRadioButtonAll);
		focusOrder.add(m_CMFindReplacePanel.jRadioButtonSelectedlines);
		focusOrder.add(m_CMFindReplacePanel.jCheckBoxCaseSensitive);
		focusOrder.add(m_CMFindReplacePanel.jCheckBoxWholeWord);
		focusOrder.add(m_CMFindReplacePanel.jCheckBoxRegularExpresion);
		focusOrder.add(m_CMFindReplacePanel.jCheckBoxWrapSearch);
		focusOrder.add(m_CMFindReplacePanel.jCheckboxIncremental);
		focusOrder.add(m_CMFindReplacePanel.jButtonFind);
		focusOrder.add(m_CMFindReplacePanel.jButtonFindAndReplace);
		focusOrder.add(m_CMFindReplacePanel.jButtonReplace);
		focusOrder.add(m_CMFindReplacePanel.jButtonReplaceAll);
		return focusOrder;
	}

	@Override
	protected void fireButtonOk() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void fireButtonCancel() {
		// TODO Auto-generated method stub

	}

}
