package bi.view.businessrulesviews.dialogs;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.text.JTextComponent;


final class CMCustomActionListener
    implements ActionListener
{

    CMCustomActionListener(CMFindReplacePanel findreplacepanel)
    {
        m_CMFindReplacePanel = findreplacepanel;
    }

    public final void actionPerformed(ActionEvent actionevent)
    {
        if(actionevent.getSource() == m_CMFindReplacePanel.jButtonFind)
        {
            m_CMFindReplacePanel.find();
            return;
        }
        if(actionevent.getSource() == m_CMFindReplacePanel.jButtonReplace)
        {
            m_CMFindReplacePanel.replace();
            return;
        }
        if(actionevent.getSource() == m_CMFindReplacePanel.jButtonReplaceAll)
        {
            m_CMFindReplacePanel.replaceAll();
            return;
        }
        if(actionevent.getSource() == m_CMFindReplacePanel.jButtonFindAndReplace)
        {
            m_CMFindReplacePanel.replaceFind();
            return;
        }
        if(actionevent.getSource() == m_CMFindReplacePanel.jCheckboxIncremental)
        {
            int i = ((JTextComponent)m_CMFindReplacePanel.jComboBoxReplaceWith.getEditor().getEditorComponent()).getDocument().getLength();
            int j = ((JTextComponent)m_CMFindReplacePanel.jComboBoxFind.getEditor().getEditorComponent()).getDocument().getLength();
            m_CMFindReplacePanel.jButtonFind.setEnabled(i > 0);
            m_CMFindReplacePanel.jButtonReplace.setEnabled(i > 0 && j > 0);
            m_CMFindReplacePanel.jButtonFindAndReplace.setEnabled(i > 0 && j > 0);
            m_CMFindReplacePanel.jButtonReplaceAll.setEnabled(i > 0 && j > 0 && CMFindReplacePanel.isReplaceMode(m_CMFindReplacePanel));
            boolean flag1 = m_CMFindReplacePanel.jCheckboxIncremental.isSelected();
            m_CMFindReplacePanel.jCheckBoxRegularExpresion.setEnabled(!flag1);
            return;
        }
        if(actionevent.getSource() == m_CMFindReplacePanel.jCheckBoxRegularExpresion)
        {
            boolean flag = m_CMFindReplacePanel.jCheckBoxRegularExpresion.isSelected();
            m_CMFindReplacePanel.jRadioButtonBackward.setEnabled(!flag);
            m_CMFindReplacePanel.jRadioButtonForward.setEnabled(!flag);
            m_CMFindReplacePanel.jCheckboxIncremental.setEnabled(!flag);
            m_CMFindReplacePanel.jCheckBoxWholeWord.setEnabled(!flag);
            return;
        } else
        {
            CMFindReplacePanel.initializeMatcher(m_CMFindReplacePanel);
            CMFindReplacePanel.getM_Matcher(m_CMFindReplacePanel).initializeIndex();
            return;
        }
    }

    final CMFindReplacePanel m_CMFindReplacePanel;
}