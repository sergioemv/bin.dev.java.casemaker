package bi.view.mainframeviews;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JToolBar;

import com.jidesoft.status.LabelStatusBarItem;
import com.jidesoft.status.MemoryStatusBarItem;
import com.jidesoft.status.OvrInsStatusBarItem;
import com.jidesoft.status.ResizeStatusBarItem;
import com.jidesoft.status.StatusBar;
import com.jidesoft.status.TimeStatusBarItem;
import com.jidesoft.swing.JideBoxLayout;

import bi.view.lang.CMMessages;

public class CMStatusBar extends StatusBar {

	private static final long serialVersionUID = 1L;
	private LabelStatusBarItem labelStatus;
	
	

	private LabelStatusBarItem fileNameLabel= null;
	private LabelStatusBarItem modifiedLabel= null;
	private LabelStatusBarItem checkedLabel=null;
	/**
	 * This is the default constructor
	 */
	public CMStatusBar() {
		super();
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.add(getLabelStatus(),JideBoxLayout.VARY);
		 final MemoryStatusBarItem gc = new MemoryStatusBarItem();
	        gc.setPreferredWidth(100);
	        this.add(gc, JideBoxLayout.FIX);
	}

	
	public LabelStatusBarItem getFileNameLabel() {
		if(fileNameLabel == null){
			fileNameLabel= new LabelStatusBarItem();
			fileNameLabel.setText("");
			fileNameLabel.setPreferredWidth(100);
		}
		return fileNameLabel;
	}

	public LabelStatusBarItem getModifiedLabel() {
		if(modifiedLabel== null){
			modifiedLabel= new LabelStatusBarItem();
			modifiedLabel.setPreferredWidth(100);
		}
		return modifiedLabel;
	}

	public LabelStatusBarItem getCheckedLabel() {
		if(checkedLabel==null){
			checkedLabel= new LabelStatusBarItem();
			checkedLabel.setPreferredWidth(100);

		}
		return checkedLabel;
	}
	public void setVisibleBusinessRulesStatusBar(boolean visible){
		if (visible){
			this.add(getFileNameLabel(),JideBoxLayout.FLEXIBLE,1);
			this.add(getModifiedLabel(),JideBoxLayout.FLEXIBLE,1);
			this.add(getCheckedLabel(),JideBoxLayout.FLEXIBLE,1);
		}else
		{
			this.remove(getFileNameLabel());
			this.remove(getModifiedLabel());
			this.remove(getCheckedLabel());
		}

	}

	
	public LabelStatusBarItem getLabelStatus() {
		if (labelStatus == null){
			labelStatus = new LabelStatusBarItem("Status");
			labelStatus.setText(CMMessages.getString("STATUS_BAR_READY"));
		}
		return labelStatus;
	}
}
