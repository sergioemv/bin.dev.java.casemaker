package bi.view.businessrulesviews.editor.editorKit;



public class CMSyntaxTokenManager
{

    public CMSyntaxTokenManager()
    {
        ignoreCase = false;
    }

    public CMSyntaxTokenManager(boolean p_ignoreCase, CMSyntaxToken p_CMSyntaxToken)
    {
        ignoreCase = false;
        ignoreCase = p_ignoreCase;
        if(!p_CMSyntaxToken.isEndOfToken())
            registerCMSyntaxToken(p_CMSyntaxToken);
        m_CMSyntaxToken = p_CMSyntaxToken;
    }

    public  CMSyntaxToken getM_CMSyntaxToken()
    {
        return m_CMSyntaxToken;
    }

    public  boolean isIdentifyToken()
    {
        if(m_CMSyntaxToken == null)
            return false;
        else
            return m_CMSyntaxToken.isEndOfToken();
    }

    public  void registerCMSyntaxToken(CMSyntaxToken p_CMSyntaxToken)
    {
        m_CMSyntaxToken = p_CMSyntaxToken;
        if(m_AllToMatchTokens == null)
            m_AllToMatchTokens = new CMSyntaxTokenManager[255][];
        char ac[] = p_CMSyntaxToken.getTokenValue();
        int size = p_CMSyntaxToken.getLength();
        char currentChar;
        if((currentChar = ac[size]) > '\376')
            return;
        
        CMSyntaxTokenManager c3 = null;
        c3 = new CMSyntaxTokenManager(ignoreCase, new CMSyntaxToken(p_CMSyntaxToken, size + 1));
        if(m_AllToMatchTokens[currentChar] == null)
        {
            m_AllToMatchTokens[currentChar] = new CMSyntaxTokenManager[1];
            m_AllToMatchTokens[currentChar][0] = c3;
            return;
        } else
        {
            CMSyntaxTokenManager ac1[] = new CMSyntaxTokenManager[m_AllToMatchTokens[currentChar].length + 1];
            System.arraycopy(m_AllToMatchTokens[currentChar], 0, ac1, 0, m_AllToMatchTokens[currentChar].length);
            ac1[ac1.length - 1] = c3;
            m_AllToMatchTokens[currentChar] = ac1;
            return;
        }
    }

    public  String toString()
    {
        StringBuffer stringbuffer = new StringBuffer("tm:");
        boolean flag = false;
        if(m_AllToMatchTokens != null)
        {
            for(int i = 0; i < m_AllToMatchTokens.length; i++)
                if(m_AllToMatchTokens[i] != null)
                {
                    if(flag)
                        stringbuffer.append(",");
                    stringbuffer.append((char)i);
                    flag = true;
                }

        }
        return stringbuffer.toString();
    }

    public  void setIgnoreCase(boolean p_ignoreCase)
    {
        ignoreCase = p_ignoreCase;
    }

    public  CMSyntaxTokenManager[] getPossibleMatchTokens(char currentChar)
    {
        if(m_AllToMatchTokens == null)
            return null;
        if(currentChar > '\377')
            return null;
        try{
        	CMSyntaxTokenManager result[] = m_AllToMatchTokens[currentChar];
        
        if(ignoreCase)
            if(result == null)
            {
                if(currentChar >= 'a' && currentChar <= 'z')
                    result = m_AllToMatchTokens[currentChar + -32];
                else
                if(currentChar >= 'A' && currentChar <= 'Z')
                    result = m_AllToMatchTokens[currentChar - -32];
            } else
            {
                CMSyntaxTokenManager parcialResult[] = (CMSyntaxTokenManager[])null;
                if(currentChar >= 'a' && currentChar <= 'z')
                    parcialResult = m_AllToMatchTokens[currentChar + -32];
                else
                if(currentChar >= 'A' && currentChar <= 'Z')
                    parcialResult = m_AllToMatchTokens[currentChar - -32];
                if(parcialResult != null)
                {
                    CMSyntaxTokenManager parcialResult2[] = new CMSyntaxTokenManager[result.length + parcialResult.length];
                    for(int i = 0; i < result.length; i++)
                        parcialResult2[i] = result[i];

                    for(int j = 0; j < parcialResult.length; j++)
                        parcialResult2[j + result.length] = parcialResult[j];

                    result = parcialResult2;
                }
            }
        return result;
        }
        catch(Exception ex){
        	return null;
        }
    }

    private CMSyntaxTokenManager m_AllToMatchTokens[][];
    protected CMSyntaxToken m_CMSyntaxToken;
    private boolean ignoreCase;
}