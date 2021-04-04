package bi.view.preferences;

import javax.swing.JPanel;

import model.ApplicationSetting;

public interface CMPreferencesController {

	public  JPanel getPanel();

	public  void applyChanges(ApplicationSetting setting);

	public void initData(ApplicationSetting setting);

}