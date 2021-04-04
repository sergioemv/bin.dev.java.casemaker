package bi.view.businessrulesviews;

import java.awt.Dimension;

import javax.swing.JToolBar;

import com.jidesoft.action.CommandBar;
import com.jidesoft.action.DockableBarContext;

import bi.view.actions.CMAction;
import bi.view.utils.CMToolBarButton;

public class CMToolBarBusinessRules extends CommandBar {

	/**
	 *
	 */
	public CMToolBarBusinessRules() {
		super();
		initialize();
	}

	public CMToolBarBusinessRules(String string) {
		super(string);
		initialize();
	}

	private void initialize() {
		
		setInitSide(DockableBarContext.DOCK_SIDE_NORTH);
		setInitMode(DockableBarContext.STATE_HORI_DOCKED);
		setInitIndex(1);
		setInitSubindex(2);
         add(new CMToolBarButton(CMAction.BI_BUSINESS_RULES_IMPORT.getAction()));
         addSeparator(new Dimension(8,20));
         add(new CMToolBarButton(CMAction.BI_BUSINESS_RULES_SAVE_AS.getAction()));
         addSeparator(new Dimension(8,20));
         add(new CMToolBarButton(CMAction.FINDREPLACE.getAction()));
         add(new CMToolBarButton(CMAction.BI_BUSINESS_RULES_CLEAR_ALL.getAction()));
	}

}
