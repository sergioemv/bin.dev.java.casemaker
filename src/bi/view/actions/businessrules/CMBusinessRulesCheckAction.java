/**
 *
 */
package bi.view.actions.businessrules;

import java.awt.Event;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;

import bi.controller.BRParseProcessManager;
import bi.view.actions.CMAction;
import bi.view.lang.CMMessages;
import bi.view.mainframeviews.CMApplication;

/**
 * @author hcanedo
 *
 */
@SuppressWarnings("serial")
public class CMBusinessRulesCheckAction extends AbstractAction implements Action {

	/**
	 *
	 */
	public CMBusinessRulesCheckAction() {
		super(CMMessages.getString("MENU_ITEM_BUSINESS_RULES_CHECK")); //$NON-NLS-1$
	    putValue(Action.SHORT_DESCRIPTION, CMMessages.getString("MENU_ITEM_BUSINESS_RULES_CHECK"));
	    putValue(Action.SMALL_ICON, CMAction.BUSINESSRULES_CHECK.getIcon());
	    putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_K, Event.CTRL_MASK));
		putValue(Action.MNEMONIC_KEY,(int)CMMessages.getString("MENU_ITEM_BUSINESS_RULES_CHECK_MNEMONIC").charAt(0));
	}

	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent e) {
	  if (checkSyntax()) 
		  JOptionPane.showMessageDialog(CMApplication.frame, CMMessages.getString("LABEL_CHECK_SUCCESSFUL"), CMMessages.getString("TESTDATASETREPORTS_TITLE_INFORMATION"),
			        JOptionPane.INFORMATION_MESSAGE);
	}
	public boolean checkSyntax(){
		 BRParseProcessManager ppm = BRParseProcessManager.INSTANCE;
			CMApplication.frame.getBusinessRulesPanelView().saveFile();
		        String msg="";
		        String absoluteFilePath = CMApplication.frame.findAbsoluteBRReportsPath();
		        File brFile = new File(absoluteFilePath);

		        CMApplication.frame.setWaitCursor(true);//fcastro_20092004
					try {
						msg = ppm.doCheck(new FileReader(brFile),CMApplication.frame.getBusinessRulesPanelView().getM_BRulesReference().getFileSyntax(),true);
					} catch (FileNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
						JOptionPane.showMessageDialog(CMApplication.frame, CMMessages.getString("LABEL_MESSAGE_FILE_ERROR"), CMMessages.getString("TITLE_FILE_ERROR"),
				        JOptionPane.ERROR_MESSAGE);
						CMApplication.frame.setWaitCursor(false);
						return false;

					}
					CMApplication.frame.setWaitCursor(false);//fcastro_20092004
		   		    if(msg.equals("ok")){
		   		    	CMApplication.frame.getBusinessRulesPanelView().setChecked(true);
		                return true;
		        	}
		   			else{
		    			if(msg.equals("1")){
		    				CMApplication.frame.getBusinessRulesPanelView().setChecked(false);
		        			JOptionPane.showMessageDialog(CMApplication.frame, CMMessages.getString("LABEL_SYNTAX_NOT_SUPPORTED"),CMMessages.getString("TITLE_SYNTAX_NOT_SUPPORTED"),
		            	    JOptionPane.ERROR_MESSAGE);
		        			return false;
		   				}
		   				else{
		   					CMApplication.frame.getBusinessRulesPanelView().setChecked(false);
		                    //fcastro_13092004_begin
							int errorLine = ppm.getErrorLine();
		        			JOptionPane.showMessageDialog(CMApplication.frame, CMMessages.getString("LABEL_SYNTAX_ERROR") +"\n"+ msg,CMMessages.getString("TITLE_SYNTAX_ERROR"),
		            	    JOptionPane.ERROR_MESSAGE);
		        			CMApplication.frame.getBusinessRulesPanelView().jumpToLine(errorLine);
		        			return false;
		        			//fcastro_13092004_end
		   				}
		   			}

	}

}
