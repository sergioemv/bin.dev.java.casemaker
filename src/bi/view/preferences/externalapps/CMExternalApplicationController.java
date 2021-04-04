package bi.view.preferences.externalapps;

import java.awt.BorderLayout;
import java.util.Vector;

import javax.swing.JPanel;

import model.ApplicationSetting;
import model.ExternalApplication;

import bi.view.preferences.CMPreferencesController;

public class CMExternalApplicationController implements CMPreferencesController {

	CMPanelExternalApplications view;
	JPanel panel;
	private CMPanelExternalApplications getView() {
		if (view == null)
			view = new CMPanelExternalApplications();
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
		Vector<ExternalApplication> externalApplications = new Vector<ExternalApplication>();

        Vector<ExternalApplication> gridApplications = getView().getExternalApplicationsGridView().getExternalApplications();

        for(int i =0;i<gridApplications.size();i++){
            externalApplications.addElement(gridApplications.elementAt(i));
        }
        setting.setM_ExternalApplications(externalApplications);

	}

	public void initData(ApplicationSetting setting) {
		getView().getExternalApplicationsGridView().setM_ExternalApplications(setting.getM_ExternalApplications());

	}
}
