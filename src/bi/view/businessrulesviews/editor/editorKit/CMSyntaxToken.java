package bi.view.businessrulesviews.editor.editorKit;

import java.awt.Color;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;

public class CMSyntaxToken
{

    public CMSyntaxToken()
    {
        length = 0;
      
        f = false;
        m_characterException = '\0';
        m_CollectionName = null;
        i = false;
    }

    public CMSyntaxToken cloneToken()
    {
        CMSyntaxToken l_CMSyntaxToken;
        (l_CMSyntaxToken = new CMSyntaxToken()).m_Color = m_Color;
        l_CMSyntaxToken.tokenValue = tokenValue;
        l_CMSyntaxToken.delimiterTokenEnd = delimiterTokenEnd;
        l_CMSyntaxToken.length = length;

        l_CMSyntaxToken.f = f;
        l_CMSyntaxToken.m_CollectionName = m_CollectionName;
        l_CMSyntaxToken.i = i;
        l_CMSyntaxToken.border = border;
        l_CMSyntaxToken.underline = underline;
        l_CMSyntaxToken.m_characterException = m_characterException;
        return l_CMSyntaxToken;
    }

    public void setM_CharacterException(char p_characterException)
    {
        m_characterException = p_characterException;
    }

    public char getM_CharacterException()
    {
        return m_characterException;
    }

    public CMSyntaxToken(char p_TokenValue[])
    {
        length = 0;

        f = false;
        m_characterException = '\0';
        m_CollectionName = null;
        i = false;
        setTokenValue(p_TokenValue);
        a(true);
    }

    public CMSyntaxToken(CMSyntaxToken p_CMSyntaxToken, int p_Length)
    {
        length = 0;

        f = false;
        m_characterException = '\0';
        m_CollectionName = null;
        i = false;
        setTokenValue(p_CMSyntaxToken.getTokenValue());
        setAttributes(p_CMSyntaxToken);
        setDelimiterTokenEnd(p_CMSyntaxToken.getDelimiterTokenEnd());
        length = p_Length;
        m_CollectionName = p_CMSyntaxToken.getM_CollectionName();
    }

    public void setCollectionName(String p_CollectionName)
    {
        m_CollectionName = p_CollectionName;
    }

    public String getM_CollectionName()
    {
        return m_CollectionName;
    }

    public boolean hasCollectionName()
    {
        return m_CollectionName != null;
    }

    public boolean isEndOfToken()
    {
        return length >= tokenValue.length;
    }

    public void a(boolean flag)
    {
        i = flag;
    }

    public boolean f()
    {
        return i;
    }

    public  int getLength()
    {
        return length;
    }

    public void setIgnoreTokenMach(boolean flag)
    {
        ignoreTokenMatch = flag;
    }

    public boolean isIgnoreTokenMatch()
    {
        return ignoreTokenMatch;
    }

    public void setColor(Color color)
    {
        m_Color = color;
        if(delimiterTokenEnd != null)
            delimiterTokenEnd.setColor(color);
    }

    public Color getM_Color()
    {
        return m_Color;
    }

    public void setUnderline(boolean flag)
    {
        underline = flag;
    }

    public boolean isUnderline()
    {
        return underline;
    }

    public void setBorder(boolean flag)
    {
        border = flag;
    }

    public  boolean isBorder()
    {
        return border;
    }

    public  void setAtributeByName(String s, String s1)
    {
        if("color".equals(s))
        {
            setColor(getColorByName(s1));
            return;
        }
        if("underline".equals(s))
        {
            setUnderline("true".equals(s1));
            return;
        }
        if("border".equals(s))
            setBorder("true".equals(s1));
    }

    public void setAttributes(CMSyntaxToken p_SyntaxToken)
    {
        setColor(p_SyntaxToken.getM_Color());
        setUnderline(p_SyntaxToken.isUnderline());
        setBorder(p_SyntaxToken.isBorder());
        setIgnoreTokenMach(p_SyntaxToken.isIgnoreTokenMatch());
        a(false);
        f = true;
    }

    public boolean l()
    {
        return f;
    }

    private Color getColorByName(String s)
    {
        Color color = null;
        if("white".equals(s))
            color = Color.white;
        else
        if("lightGray".equals(s))
            color = Color.lightGray;
        else
        if("gray".equals(s))
            color = Color.gray;
        else
        if("darkGray".equals(s))
            color = Color.darkGray;
        else
        if("black".equals(s))
            color = Color.black;
        else
        if("red".equals(s))
            color = Color.red;
        else
        if("pink".equals(s))
            color = Color.pink;
        else
        if("orange".equals(s))
            color = Color.orange;
        else
        if("yellow".equals(s))
            color = Color.yellow;
        else
        if("green".equals(s))
            color = Color.green;
        else
        if("magenta".equals(s))
            color = Color.magenta;
        else
        if("cyan".equals(s))
            color = Color.cyan;
        else
        if("blue".equals(s))
            color = Color.blue;
        else
            try
            {
                StringTokenizer stringtokenizer = new StringTokenizer(s, ":", false);
                int i1 = (new Integer(stringtokenizer.nextToken())).intValue();
                int j1 = (new Integer(stringtokenizer.nextToken())).intValue();
                int k1 = (new Integer(stringtokenizer.nextToken())).intValue();
                color = new Color(i1, j1, k1);
            }
            catch(NoSuchElementException _ex) { }
        return color;
    }

    public void setTokenValue(char p_TokenValue[])
    {
        tokenValue = p_TokenValue;
    }

    public char[] getTokenValue()
    {
        return tokenValue;
    }

    public void setDelimiterTokenEnd(CMSyntaxToken delimiterToken)
    {
        delimiterTokenEnd = delimiterToken;
        if(delimiterToken != null)
            delimiterToken.setColor(getM_Color());
    }

    public CMSyntaxToken getDelimiterTokenEnd()
    {
        return delimiterTokenEnd;
    }

    public String toString()
    {
        return "token:" + (new String(tokenValue)).substring(length);
    }

    private Color m_Color;
    private char tokenValue[];
    private CMSyntaxToken delimiterTokenEnd;
    private int length;

    private boolean f;
    private char m_characterException;
    private String m_CollectionName;
    private boolean i;
    private boolean ignoreTokenMatch;
    private boolean underline;
    private boolean border;
}