package bi.view.preferences.general;

import java.awt.BorderLayout;
import java.util.Vector;

import javax.swing.JPanel;
import javax.swing.SpinnerNumberModel;

import model.ApplicationSetting;
import model.ExternalApplication;

import bi.view.preferences.CMPreferencesController;

public class CMGeneralPreferencesController implements CMPreferencesController {

    CMPanelGeneral view;
    JPanel panel;
    private CMPanelGeneral getView() {
        if (view == null)
            view = new CMPanelGeneral();
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
        setting.setM_MaxSizeofXMLFiles(((SpinnerNumberModel)  getView().getJSpinnerXMLSize().getModel()).getNumber().floatValue());
        setting.setM_MaxNumOfCombinations(Integer.parseInt( getView().getJSpinnerCombinations().getValue().toString()));
        setting.setM_MaxNumOfPositiveTestCases(Integer.parseInt( getView().getJSpinnerPositiveTestCases().getValue().toString()));
        setting.setM_MaxNumOfNegativeTestCases(Integer.parseInt( getView().getJSpinnerNegativeTestCases().getValue().toString()));
        setting.setM_MaxNumOfFaultyTestCases(Integer.parseInt( getView().getJSpinnerFaultyTestCases().getValue().toString()));

    }

    public void initData(ApplicationSetting 	setting) {
        getView().getJSpinnerCombinations().setValue(new Integer(setting.getM_MaxNumOfCombinations()));
        getView().getJSpinnerXMLSize().setValue(new Float(setting.getM_MaxSizeofXMLFiles()));
        getView().getJSpinnerPositiveTestCases().setValue(new Integer(setting.getM_MaxNumOfPositiveTestCases()));
        getView().getJSpinnerNegativeTestCases().setValue(new Integer(setting.getM_MaxNumOfNegativeTestCases()));
        getView().getJSpinnerFaultyTestCases().setValue(new Integer(setting.getM_MaxNumOfFaultyTestCases()));
    }
}
