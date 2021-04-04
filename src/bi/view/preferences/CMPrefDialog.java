package bi.view.preferences;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.JButton;

import bi.view.icons.CMIcon;
import bi.view.mainframeviews.CMFrameView;

import com.jidesoft.dialog.ButtonNames;
import com.jidesoft.dialog.ButtonPanel;
import com.jidesoft.dialog.MultiplePageDialog;
import com.jidesoft.plaf.UIDefaultsLookup;

public class CMPrefDialog extends MultiplePageDialog {

	  public CMPrefDialog(Frame owner, String title) throws HeadlessException {
	        super(owner, title);
	    }
	
	protected void initComponents() {
	        super.initComponents();
	        getContentPanel().setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10));
	        getIndexPanel().setBackground(Color.white);
	        getButtonPanel().setBorder(BorderFactory.createEmptyBorder(20, 10, 10, 10));
	        getPagesPanel().setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
	    }
	  public ButtonPanel createButtonPanel() {
	        ButtonPanel buttonPanel = super.createButtonPanel();
	        AbstractAction okAction = new AbstractAction(UIDefaultsLookup.getString("OptionPane.okButtonText")) {
	            public void actionPerformed(ActionEvent e) {
	                setDialogResult(RESULT_AFFIRMED);
	                setVisible(false);
	                dispose();
	            }
	        };
	        AbstractAction cancelAction = new AbstractAction(UIDefaultsLookup.getString("OptionPane.cancelButtonText")) {
	            public void actionPerformed(ActionEvent e) {
	                setDialogResult(RESULT_CANCELLED);
	                setVisible(false);
	                dispose();
	            }
	        };
	        ((JButton) buttonPanel.getButtonByName(ButtonNames.OK)).setAction(okAction);
	        ((JButton) buttonPanel.getButtonByName(ButtonNames.OK)).setIcon(CMIcon.OK.getImageIcon());
	        ((JButton) buttonPanel.getButtonByName(ButtonNames.CANCEL)).setAction(cancelAction);
	        ((JButton) buttonPanel.getButtonByName(ButtonNames.CANCEL)).setIcon(CMIcon.CANCEL.getImageIcon());
	        ((JButton) buttonPanel.getButtonByName(ButtonNames.APPLY)).setVisible(false);
	        setDefaultCancelAction(cancelAction);
	        setDefaultAction(okAction);
	        return buttonPanel;
	    }
	  public Dimension getPreferredSize() {
	        return new Dimension(750, 400);
	    }
}
