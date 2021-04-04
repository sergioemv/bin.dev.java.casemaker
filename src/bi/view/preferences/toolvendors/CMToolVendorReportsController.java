package bi.view.preferences.toolvendors;

import java.awt.BorderLayout;
import java.util.Vector;

import javax.swing.JPanel;

import model.ApplicationSetting;
import model.ExternalApplication;
import model.ToolVendor;

import bi.view.preferences.CMPreferencesController;

public class CMToolVendorReportsController implements CMPreferencesController {

	CMPanelToolVendors view;
	JPanel panel;
	private CMPanelToolVendors getView() {
		if (view == null)
			view = new CMPanelToolVendors();
		return view;
	}

	/* (non-Javadoc)
	 * @see bi.view.externalapplicationviews.CMPreferencesController#getPanel()
	 */
	public JPanel getPanel(){
	if (panel == null)
	{
		panel = new JPanel();
		panel.setLayout(new BorderLayout());
		panel.add(getView(),BorderLayout.CENTER );
	}
	return panel;
	}

	/* (non-Javadoc)
	 * @see bi.view.externalapplicationviews.CMPreferencesController#applyChanges(model.ApplicationSetting)
	 */
	public void applyChanges(ApplicationSetting setting){
		Vector<ToolVendor> toolVendors = new Vector<ToolVendor>();

        Vector<ToolVendor> gridToolVendors = getView().getToolVendorsGridView().getToolVendors();

        for(int i =0;i<gridToolVendors.size();i++){
        	toolVendors.addElement(gridToolVendors.elementAt(i));
        }
        setting.setM_ToolVendors(toolVendors);

	}

	public void initData(ApplicationSetting setting) {
		getView().getToolVendorsGridView().setM_ToolVendors(setting.getM_ToolVendors());

	}
}
