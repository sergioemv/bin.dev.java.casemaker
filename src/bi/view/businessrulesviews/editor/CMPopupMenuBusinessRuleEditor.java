package bi.view.businessrulesviews.editor;

import javax.swing.JComponent;
import javax.swing.JPopupMenu;

import bi.view.actions.CMAction;
import bi.view.utils.CMBaseJMenuItem;

public class CMPopupMenuBusinessRuleEditor extends JPopupMenu {

	private CMBaseJMenuItem cut;
	private CMBaseJMenuItem copy;
	private CMBaseJMenuItem paste;
	private JComponent container;
	private CMBaseJMenuItem selectAll;
	private CMBaseJMenuItem findReplace; 
	public CMPopupMenuBusinessRuleEditor(JComponent p_Container) {
		super();
		container=p_Container;
		initialize();
	}


	private void initialize() {
		this.add(getMenuItemCut());
		this.add(getMenuItemCopy());
		this.add(getMenuItemPaste());
		this.addSeparator();
		this.add(getMenuItemSelectAll());
		this.addSeparator();
		this.add(getMenuItemFindReplace());
	}
	
	private CMBaseJMenuItem getMenuItemSelectAll() {
		if(selectAll==null){
			if (container instanceof CMCustomJTextPane) {
				selectAll=new CMBaseJMenuItem(CMAction.SELECTALL.getAction());
			}
		}
		return selectAll;
	}


	private CMBaseJMenuItem getMenuItemCut(){
		if(cut==null){
			if (container instanceof CMCustomJTextPane) {
				cut= new CMBaseJMenuItem(CMAction.CUT.getAction());
			}
		}
		return cut;
	}
	
	private CMBaseJMenuItem getMenuItemCopy(){
		if (copy== null) {
			if (container instanceof CMCustomJTextPane) {
				copy= new CMBaseJMenuItem(CMAction.COPY.getAction());
			}
		}
		return copy;
	}
	
	private CMBaseJMenuItem getMenuItemPaste(){
		if (paste==null) {
			if (container instanceof CMCustomJTextPane) {
				paste= new CMBaseJMenuItem(CMAction.PASTE.getAction());
			}
		}
		return paste;
	}

	private CMBaseJMenuItem getMenuItemFindReplace(){
		if(findReplace== null){
			findReplace= new CMBaseJMenuItem(CMAction.FINDREPLACE.getAction());
		}
		return findReplace;
	}
}
