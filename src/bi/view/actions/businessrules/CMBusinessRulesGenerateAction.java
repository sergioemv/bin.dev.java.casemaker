/**
 *
 */
package bi.view.actions.businessrules;

import java.awt.Event;
import java.awt.event.ActionEvent;
import java.io.FileNotFoundException;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;

import model.ApplicationSetting;
import bi.controller.BRImportProcessManager;
import bi.view.actions.CMAction;
import bi.view.lang.CMMessages;
import bi.view.mainframeviews.CMApplication;

/**
 * @author hcanedo
 *
 */
@SuppressWarnings("serial")
public class CMBusinessRulesGenerateAction extends AbstractAction implements Action {

	/**
	 *
	 */
	public CMBusinessRulesGenerateAction() {
		super(CMMessages.getString("MENU_ITEM_BUSINESS_RULES_GENERATE_TEST_CASES")); //$NON-NLS-1$
	    putValue(Action.SHORT_DESCRIPTION, CMMessages.getString("MENU_ITEM_BUSINESS_RULES_GENERATE_TEST_CASES"));
	    putValue(Action.SMALL_ICON, CMAction.BUSINESSRULES_GENERATE.getIcon());
	    putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_G, Event.CTRL_MASK|Event.SHIFT_MASK));
		putValue(Action.MNEMONIC_KEY,(int)CMMessages.getString("MENU_ITEM_BUSINESS_RULES_GENERATE_TEST_CASES_MNEMONIC").charAt(0));

	}

	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent e) {
			BRImportProcessManager ipm = new BRImportProcessManager();
	         int confirmation = JOptionPane.showConfirmDialog(CMApplication.frame, CMMessages.getString("QUESTION_TESTOBJECT_STRUCTURE_WILL_DISSAPEAR"),
	                CMMessages.getString("TITLE_TESTOBJECT_STRUCTURE_WILL_DISSAPEAR"), JOptionPane.YES_NO_CANCEL_OPTION);
	            if(confirmation == JOptionPane.YES_OPTION){
	            	CMApplication.frame.setWaitCursor(true);//fcastro_20092004
	                //grueda_18072004_begin
	                ApplicationSetting appSetting = CMApplication.frame.getTreeWorkspaceView().getM_Session2().getM_ApplicationSetting();
	                //grueda13092004_begin
	                //grueda24112004_begin
					String absoluteBRulesFilePath = CMApplication.frame.findAbsoluteBRReportsPath();
	                //grueda24112004_end
					try {
						ipm.importObjects(CMApplication.frame.getBusinessRulesPanelView().getM_TestObject(), appSetting);
					} catch (FileNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
						JOptionPane.showMessageDialog(CMApplication.frame, CMMessages.getString("LABEL_MESSAGE_FILE_ERROR"), CMMessages.getString("TITLE_FILE_ERROR"),
				        JOptionPane.ERROR_MESSAGE);
						return;

					}
	                //grueda13092004_end
	                //grueda_18072004_end
					CMApplication.frame.setWaitCursor(false);//fcastro_20092004
	                	JOptionPane.showMessageDialog(CMApplication.frame, CMMessages.getString("LABEL_BR_ADDED_TO_STRUCTURE"), CMMessages.getString("TITLE_BR_ADDED_TO_STRUCTURE"),
	            	JOptionPane.INFORMATION_MESSAGE);
	                	CMApplication.frame.setWaitCursor(true);
	                	CMApplication.frame.getTreeWorkspaceView().selectNode(CMApplication.frame.getTreeWorkspaceView().getSelectedNode().getNextNode());
	                	CMApplication.frame.getContentTabbedPane().setSelectedIndex(3);
	                	CMApplication.frame.setWaitCursor(false);
	                	CMAction.TESTCASE_GENERATE.getAction().actionPerformed(null);
	            }
	            else{
	                return;
	            }

	        }
	}

