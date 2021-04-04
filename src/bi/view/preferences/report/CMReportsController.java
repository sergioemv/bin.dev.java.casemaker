package bi.view.preferences.report;

import java.awt.BorderLayout;

import javax.swing.JPanel;

import model.ApplicationSetting;
import bi.view.preferences.CMPreferencesController;

public class CMReportsController implements CMPreferencesController {

	CMPanelReports view;
	JPanel panel;
	private CMPanelReports getView() {
		if (view == null)
			view = new CMPanelReports();
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
		//esta en los dialogos de

	}

	public void initData(ApplicationSetting setting) {

		getView().getReportDataSourceGridView().setM_TestCaseReportDataSource(setting.getM_ReportDataSources());


	}
}
