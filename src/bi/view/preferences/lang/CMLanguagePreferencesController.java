package bi.view.preferences.lang;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.List;

import javax.swing.DefaultListCellRenderer;
import javax.swing.ImageIcon;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import model.ApplicationSetting;

import bi.view.icons.CMIcon;
import bi.view.lang.CMMessages;
import bi.view.lang.NamedPropertyResourceBundle;
import bi.view.preferences.CMPreferencesController;
import bi.view.utils.CMDefaultDialog;
import bi.view.utils.CMModalResult;

public class CMLanguagePreferencesController  implements CMPreferencesController {

    class LangView{
        private String name="";
        private ImageIcon icon;
        @Override
        public String toString() {
            // TODO Auto-generated method stub
            return name;
        }
        public String getName() {
            return name;
        }
        public void setName(String name) {
            this.name = name;
        }

        public ImageIcon getIcon() {
            return icon;
        }
        public LangView(String name, ImageIcon icon) {
            this.icon = icon;
            this.name = name;
        }
    }
    private CMLanguagesPanel panel;

    public CMLanguagesPanel getPanel() {
        if (panel==null){
            panel = new CMLanguagesPanel();
            panel.getJListLanguajes().setCellRenderer(new DefaultListCellRenderer(){
                @Override
                public Component getListCellRendererComponent(JList list,
                        Object value, int index, boolean isSelected,
                        boolean cellHasFocus) {

                    Component c = super.getListCellRendererComponent(list, value, index, isSelected,
                            cellHasFocus);
                      LangView val = (LangView) value;
                      if (val == null)
                    	  return c;
                      setText(val.toString());
                      if (val.getIcon()!=null)
                          setIcon(val.getIcon());
                      if (isSelected){
                          setBackground(Color.ORANGE);
                          setForeground(Color.black);
                      }
                      if (CMMessages.getDefaultLanguage().equalsIgnoreCase(val.getName())){
                          setForeground(Color.BLUE);
                          setFont(new Font("Arial",Font.BOLD,12)); //$NON-NLS-1$
                      }
                      return c;
                }
            });
            panel.getJListLanguajes().addListSelectionListener(new ListSelectionListener(){

                public void valueChanged(ListSelectionEvent e) {
                    LangView lv  = (LangView) panel.getJListLanguajes().getSelectedValue();
                    if (lv==null) return;
                    if (CMMessages.getDefaultLanguage().equalsIgnoreCase(lv.getName()))
                        panel.getJButtonSetAsDefault().setEnabled(false);
                    else
                        panel.getJButtonSetAsDefault().setEnabled(true);
                    if (lv.getName().equalsIgnoreCase("English") //$NON-NLS-1$
                            || lv.getName().equalsIgnoreCase("Spanish") //$NON-NLS-1$
                            || lv.getName().equalsIgnoreCase("German")) //$NON-NLS-1$
                        panel.getJButtonDeleteLanguage().setEnabled(false);
                    else
                        panel.getJButtonDeleteLanguage().setEnabled(true);
                }});
            panel.getJButtonSetAsDefault().addActionListener(new ActionListener(){

                public void actionPerformed(ActionEvent e) {
                    LangView lv  = (LangView) panel.getJListLanguajes().getSelectedValue();

                    try {
                        CMMessages.setDefaultLanguage(lv.getName());
                        //refresh the jlist
                        panel.getJListLanguajes().setVisible(false);
                        panel.getJListLanguajes().setVisible(true);
                        panel.getJButtonSetAsDefault().setEnabled(false);
                    } catch (IOException e1) {
                        JOptionPane.showMessageDialog(panel, CMMessages.getString("ERROR_CHANGE_LANGUAGE")+" \n" + e1.getLocalizedMessage(), "Error", JOptionPane.ERROR_MESSAGE); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
                    }
                }});
            init(panel);
            panel.getJButtonEditLanguage().addActionListener(new ActionListener(){

                public void actionPerformed(ActionEvent e) {
                    if (panel.getJListLanguajes().getSelectedValue()==null) return;
                    LangView lv  = (LangView) panel.getJListLanguajes().getSelectedValue();
                    CMLanguageEditController controller = new CMLanguageEditController(CMMessages.getLangBaseBundle(), (NamedPropertyResourceBundle) CMMessages.getLangBundle(lv.getName()));
                    CMDefaultDialog dlg = new CMDefaultDialog();
                    dlg.setSize(CMLanguageEditController.DEFAULT_DIALOG_SIZE);
                    dlg.setJPanelContained(controller.getPanel());
                    dlg.setTitle(CMMessages.getString("LABEL_EDIT_LANGUAGE")+" - "+lv.getName()); //$NON-NLS-1$ //$NON-NLS-2$
                    dlg.show();
                    if (dlg.getModalResult()==CMModalResult.OK)
                        controller.applyChanges();
}});
            panel.getJButtonAddLanguage().addActionListener(new ActionListener(){

                public void actionPerformed(ActionEvent e) {
                    CMLanguageImportCreateController controller = new CMLanguageImportCreateController();
                    controller.getWizard().setVisible(true);
                    //refresh the panel
                    init(panel);

                }});
            panel.getJButtonDeleteLanguage().addActionListener(new ActionListener(){

				public void actionPerformed(ActionEvent arg0) {
					LangView lv  = (LangView) panel.getJListLanguajes().getSelectedValue();
					 try {
	                        CMMessages.unRegisterLanguage(lv.getName());
	                        //refresh the jlist
	                        init(panel);

	                    } catch (Exception e1) {
	                        JOptionPane.showMessageDialog(panel, CMMessages.getString("ERROR_CHANGE_LANGUAGE")+" \n" + e1.getLocalizedMessage(), "Error", JOptionPane.ERROR_MESSAGE); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
	                    }
				}});
        }
        return panel;
    }

    private void init(CMLanguagesPanel panel){
        List<String> langs = CMMessages.getLanguages();
        panel.getLanguageListModel().clear();
        for(String lang : langs)
        {
            if (!lang.equalsIgnoreCase("Base")){ //$NON-NLS-1$
                ImageIcon icon = null;
//                if (lang.equalsIgnoreCase("English")) //$NON-NLS-1$
//                    icon = CMIcon.LANG_USA.getImageIcon();
//                if (lang.equalsIgnoreCase("German")) //$NON-NLS-1$
//                    icon = CMIcon.LANG_GERMANY.getImageIcon();
//                if (lang.equalsIgnoreCase("Spanish")) //$NON-NLS-1$
//                    icon = CMIcon.LANG_SPAIN.getImageIcon();
//                if (icon==null)
//                    icon = CMIcon.LANG_USER.getImageIcon();
                LangView lv = new LangView(lang,icon);
                panel.getLanguageListModel().addElement(lv);
            }
        }

    }

	public void applyChanges(ApplicationSetting setting) {
		// TODO Auto-generated method stub
	}

	public void initData(ApplicationSetting setting) {
		// TODO Auto-generated method stub

	}

}
