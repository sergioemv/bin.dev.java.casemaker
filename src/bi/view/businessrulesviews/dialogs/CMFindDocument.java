package bi.view.businessrulesviews.dialogs;


import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;



final class CMFindDocument extends PlainDocument
{

    CMFindDocument(CMFindReplacePanel findreplacepanel)
    {
        m_CMFindReplacePanel = findreplacepanel;
        m_CaretPosition = -1;
    }

    public final void remove(int i, int j)
        throws BadLocationException
    {
        super.remove(i, j);
        m_CMFindReplacePanel.jButtonFind.setEnabled(getLength() > 0);
        m_CMFindReplacePanel.jButtonReplaceAll.setEnabled(m_CMFindReplacePanel.m_findDocument.getLength() > 0 && CMFindReplacePanel.isReplaceMode(m_CMFindReplacePanel));
        if(m_CMFindReplacePanel.jCheckboxIncremental.isSelected())
            updateM_CaretPosition();
    }

   
    private void updateM_CaretPosition()
    {
        CMFindReplacePanel.getM_Matcher(m_CMFindReplacePanel).caretPosition = m_CaretPosition;
        CMFindReplacePanel.getM_Matcher(m_CMFindReplacePanel).currentCharToSearchIndex = -1;
        boolean flag;
        if(flag = CMFindReplacePanel.existNext(m_CMFindReplacePanel, -1))
        {
            CMFindReplacePanel.incrementalRequestFocus(m_CMFindReplacePanel);
            m_CaretPosition = CMFindReplacePanel.getM_Matcher(m_CMFindReplacePanel).forwardOrientation ? CMFindReplacePanel.getM_Matcher(m_CMFindReplacePanel).caretPosition - CMFindReplacePanel.getM_Matcher(m_CMFindReplacePanel).stringToFind.length : CMFindReplacePanel.getM_Matcher(m_CMFindReplacePanel).caretPosition + CMFindReplacePanel.getM_Matcher(m_CMFindReplacePanel).stringToFind.length;
        }
    }

    public final void insertString(int i, String s, AttributeSet attributeset)
        throws BadLocationException
    {
        super.insertString(i, s, attributeset);
        m_CMFindReplacePanel.jButtonFind.setEnabled(getLength() > 0);
        m_CMFindReplacePanel.jButtonReplaceAll.setEnabled(m_CMFindReplacePanel.m_findDocument.getLength() > 0 && CMFindReplacePanel.isReplaceMode(m_CMFindReplacePanel));
        if(m_CMFindReplacePanel.jCheckboxIncremental.isSelected())
            updateM_CaretPosition();
    }

    private int m_CaretPosition;
    final CMFindReplacePanel m_CMFindReplacePanel;
}