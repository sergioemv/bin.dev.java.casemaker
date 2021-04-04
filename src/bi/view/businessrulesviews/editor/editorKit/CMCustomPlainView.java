package bi.view.businessrulesviews.editor.editorKit;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;

import javax.swing.SwingUtilities;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.Element;
import javax.swing.text.JTextComponent;
import javax.swing.text.PlainView;
import javax.swing.text.Segment;
import javax.swing.text.Utilities;

import org.apache.log4j.Logger;


public class CMCustomPlainView extends PlainView
    implements Runnable
{

    public CMCustomPlainView(Element element, CMDocumentManger p_CMDocumentManager)
    {
        super(element);
        m_Segment = new Segment();
        m_DocumentManger = p_CMDocumentManager;
    }

    public void setM_DocumentManager(CMDocumentManger p_DocumentManager)
    {
        m_DocumentManger = p_DocumentManager;
    }

    public void drawLine(int i, Graphics g, int x, int y)
    {
        Document document = getDocument();
        Color color = getForeground();
        JTextComponent jtextcomponent;
        int l = (jtextcomponent = (JTextComponent)getContainer()).getSelectionStart();
        int i1 = jtextcomponent.getSelectionEnd();
        boolean flag = false;
        try
        {
            Element element;
            int j1 = (element = getElement().getElement(i)).getStartOffset();
            int k1 = element.getEndOffset();
            document.getText(j1, k1 - (j1 + 1), m_Segment);
            int l1 = 0;
            CMSyntaxToken ah[] = m_DocumentManger.getCMSyntaxTokenPerLine(document.getText(j1, k1 - (j1 + 1)), i);
            if(m_DocumentManger.isDelimiterChange())
                flag = true;
            if(ah == null){
                return;
            }
            FontMetrics fontmetrics;
            int i2 = (fontmetrics = g.getFontMetrics()).getHeight();
            for(int j2 = 0; j2 < m_DocumentManger.size(); j2++)
            {
                CMSyntaxToken h1;
                int k2 = (h1 = ah[j2]).getTokenValue().length;
                m_Segment.count = k2;
                Color color1;
                Color color2 = color1 = h1.getM_Color();
                if(h1.f() || color1 == null){
                    color2 = color1 = color;
                }
                int l2 = 0;
                int i3 = 0;
                if(l < i1)
                {
                    int j3 = getElement().getElementIndex(l);
                    int i4 = getElement().getElementIndex(i1);
                    int j4 = j3;
                    if(i > j3 && i < i4)
                    {
                        color1 = jtextcomponent.getSelectedTextColor();
                    } else
                    {
                        int k4 = getElement().getElementIndex(i1);
                        Element element1 = getElement().getElement(j3);
                        int l4 = l - element1.getStartOffset();
                        element1 = getElement().getElement(i4);
                        int i5 = i1 - element1.getStartOffset();
                        if(i == j4 && i == k4)
                        {
                            if(l1 >= l4 && l1 + m_Segment.count <= i5)
                                color1 = jtextcomponent.getSelectedTextColor();
                            else
                            if(l4 >= l1 && i5 <= l1 + m_Segment.count)
                            {
                                i3 = (l1 + k2) - i5;
                                m_Segment.count = l4 - l1;
                                k2 = m_Segment.count;
                                l2 = i5 - l4;
                            } else
                            if(l1 > l4 && l1 + m_Segment.count < i5)
                                color1 = jtextcomponent.getSelectedTextColor();
                            else
                            if(l1 + m_Segment.count >= l4 && l1 <= l4)
                            {
                                l2 = (l1 + k2) - l4;
                                m_Segment.count = l4 - l1;
                                k2 = m_Segment.count;
                            } else
                            if(l1 + m_Segment.count > i5 && l1 < i5)
                            {
                                k2 = (l1 + m_Segment.count) - i5;
                                m_Segment.count = i5 - l1;
                                g.setColor(jtextcomponent.getSelectedTextColor());
                                x = Utilities.drawTabbedText(m_Segment, x, y, g, this, l1);
                                l1 += m_Segment.count;
                                m_Segment.offset += m_Segment.count;
                                m_Segment.count = k2;
                            }
                        } else
                        if(i == j3)
                        {
                            if(l1 >= l4)
                                color1 = jtextcomponent.getSelectedTextColor();
                            else
                            if(l1 + m_Segment.count >= l4 && l1 <= l4)
                            {
                                l2 = (l1 + k2) - l4;
                                m_Segment.count = l4 - l1;
                                k2 = m_Segment.count;
                            }
                        } else
                        if(i == i4 && l1 <= i5){
                            if(l1 + m_Segment.count <= i5){
                                color1 = jtextcomponent.getSelectedTextColor();
                            }
                            else{
                            	if(l1 + m_Segment.count > i5)
                            	{
                            		k2 = (l1 + m_Segment.count) - i5;
                            		m_Segment.count = i5 - l1;
                            		g.setColor(jtextcomponent.getSelectedTextColor());
                            		x = Utilities.drawTabbedText(m_Segment, x, y, g, this, l1);
                            		l1 += m_Segment.count;
                            		m_Segment.offset += m_Segment.count;
                            		m_Segment.count = k2;
                            	}
                            }
                        }
                    }
                }
                g.setColor(color1);
                x = Utilities.drawTabbedText(m_Segment, x, y, g, this, l1);
                if(!h1.f()){
                    if(h1.isUnderline())
                    {
                        int k3 = fontmetrics.charsWidth(m_Segment.array, m_Segment.offset, m_Segment.count);
                        g.drawLine(x, y + 1, x - k3, y + 1);
                    } else
                    if(h1.isBorder())
                    {
                        int l3 = fontmetrics.charsWidth(m_Segment.array, m_Segment.offset, m_Segment.count);
                        g.drawRect(x - l3, (y - i2) + 4, l3 + 1, i2 - 1);
                    }
                }
                l1 += k2;
                m_Segment.offset += k2;
                if(l2 > 0)
                {
                    m_Segment.count = l2;
                    g.setColor(jtextcomponent.getSelectedTextColor());
                    x = Utilities.drawTabbedText(m_Segment, x, y, g, this, l1);
                    l1 += m_Segment.count;
                    m_Segment.offset += m_Segment.count;
                }
                if(i3 > 0)
                {
                    m_Segment.count = i3;
                    g.setColor(color2);
                    x = Utilities.drawTabbedText(m_Segment, x, y, g, this, l1);
                    l1 += m_Segment.count;
                    m_Segment.offset += m_Segment.count;
                }
            }

        }
        catch(BadLocationException ex) {
        	Logger.getLogger(this.getClass()).error(ex.getMessage());
        }
        if(flag){
            SwingUtilities.invokeLater(this);
        }
    }

    public Color getForeground()
    {
        return getContainer().getForeground();
    }

    public void run()
    {
        getContainer().repaint();
    }

    private CMDocumentManger m_DocumentManger;
    private Segment m_Segment;
}