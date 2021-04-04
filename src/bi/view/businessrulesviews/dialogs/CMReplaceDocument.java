package bi.view.businessrulesviews.dialogs;


import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;


final class CMReplaceDocument extends PlainDocument
{

    CMReplaceDocument(CMFindReplacePanel findreplacepanel)
    {
        m_CMFindReplacePanel = findreplacepanel;
    }

    public final void remove(int i, int j)
        throws BadLocationException
    {
        super.remove(i, j);
        m_CMFindReplacePanel.jButtonReplaceAll.setEnabled(m_CMFindReplacePanel.m_findDocument.getLength() > 0);
    }

    public final void insertString(int i, String s, AttributeSet attributeset)
        throws BadLocationException
    {
        super.insertString(i, s, attributeset);
        m_CMFindReplacePanel.jButtonReplaceAll.setEnabled(m_CMFindReplacePanel.m_findDocument.getLength() > 0);
    }

    final CMFindReplacePanel m_CMFindReplacePanel;
}