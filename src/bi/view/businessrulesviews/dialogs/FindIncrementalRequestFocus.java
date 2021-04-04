package bi.view.businessrulesviews.dialogs;


import java.awt.Window;

import javax.swing.SwingUtilities;



final class FindIncrementalRequestFocus
    implements Runnable
{

    FindIncrementalRequestFocus(CMFindReplacePanel findreplacepanel)
    {
        m_CMFindReplacePanel = findreplacepanel;
    }

    public final void run()
    {
        Window window;
        if((window = SwingUtilities.getWindowAncestor(m_CMFindReplacePanel.jComboBoxFind)) != null)
            window.toFront();
        m_CMFindReplacePanel.jComboBoxFind.getEditor().getEditorComponent().requestFocus();
    }

    final CMFindReplacePanel m_CMFindReplacePanel;
}