package bi.view.businessrulesviews.dialogs;


import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;


final class CMFindAction extends AbstractAction
{

    public CMFindAction(CMFindReplacePanel findreplacepanel)
    {
        m_CMFindReplacePanel = findreplacepanel;
        putValue("Name", "Find");
    }

    public final void actionPerformed(ActionEvent actionevent)
    {
        m_CMFindReplacePanel.find();
    }

    final CMFindReplacePanel m_CMFindReplacePanel;
}