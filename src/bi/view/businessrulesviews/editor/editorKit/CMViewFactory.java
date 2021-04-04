package bi.view.businessrulesviews.editor.editorKit;


import javax.swing.text.Element;
import javax.swing.text.View;
import javax.swing.text.ViewFactory;


public class CMViewFactory
    implements ViewFactory
{

    public CMViewFactory()
    {
    }

    public  void setDocumentManager(CMDocumentManger p_DocumentManager)
    {
        m_DocumentManager = p_DocumentManager;
        if(m_CMCustomPlainView != null)
            m_CMCustomPlainView.setM_DocumentManager(p_DocumentManager);
    }

    public  View create(Element element)
    {
        return m_CMCustomPlainView = new CMCustomPlainView(element, m_DocumentManager);
    }

    private CMDocumentManger m_DocumentManager;
    private CMCustomPlainView m_CMCustomPlainView;
}