package bi.view.businessrulesviews.editor.editorKit;





public class CMDocumentManger
{

    CMDocumentManger()
    {
        delimiterChange = false;
    }

    public  void initialize(boolean p_ignoreCase, CMSyntaxToken p_ArrayCMSyntaxToken[])
    {

        m_DocumentChangeManager = new CMDocumentChangeManager();
        m_DocumentChangeManager.setIgnoreCase(p_ignoreCase);
        for(int i = 0; i < p_ArrayCMSyntaxToken.length; i++)
        {
            CMSyntaxToken l_CMSyntaxToken = p_ArrayCMSyntaxToken[i];
            m_DocumentChangeManager.registerCMSyntaxToken(l_CMSyntaxToken);
        }

    }

     boolean isDelimiterChange()
    {
        if(m_DocumentChangeManager == null)
            return false;
        else
            return delimiterChange;
    }

    public  int size()
    {
        return sizeTokensPerLine;
    }

    public  void setIniDelimiters(char p_IniDelimeters[])
    {
        iniDelimiters = p_IniDelimeters;
    }

    public  void setEndDelimiters(char p_EndDelimiters[])
    {
        endDelimiters = p_EndDelimiters;
    }

    public  CMSyntaxToken[] getCMSyntaxTokenPerLine(String p_LineValue, int p_LineNumber)
    {
    	
        sizeTokensPerLine = 1;
        if(m_DocumentChangeManager == null)
            return (new CMSyntaxToken[] {
                new CMSyntaxToken(p_LineValue.toCharArray())
            });
   /*     int j;
        if((j = c.indexOf(p_LineValue)) > -1)
            return (CMSyntaxToken[])c.get(j + 1);
        if(c.size() > 30)
        {
            c.remove(0);
            c.remove(0);
        }*/
        CMSyntaxToken arrayOfCMSyntaxTokens[] = m_DocumentChangeManager.getCMSyntaxTokenPerLine(p_LineValue, p_LineNumber);
        try{
        if(iniDelimiters != null || endDelimiters != null)
        {
            for(int k = 0; k < m_DocumentChangeManager.getNumberOfTokensPerLine(); k++)
            {
                CMSyntaxToken h1;
                if((h1 = arrayOfCMSyntaxTokens[k]).l() && !h1.isIgnoreTokenMatch())
                {
                    boolean flag = true;
                    
                    char ac[];
                    if(iniDelimiters != null && k > 0 && (ac = (arrayOfCMSyntaxTokens[k - 1]).getTokenValue()).length > 0)
                        if(iniDelimiters[ac[ac.length - 1]] == '\uFFFF')
                        {
                            flag = false;
                            CMSyntaxToken h3;
                            (h3 = h1.cloneToken()).a(true);
                            arrayOfCMSyntaxTokens[k] = h3;
                        } else
                        {
                            h1.a(false);
                        }
                    if(flag && endDelimiters != null && k < m_DocumentChangeManager.getNumberOfTokensPerLine() - 1 && (ac = (arrayOfCMSyntaxTokens[k + 1]).getTokenValue()).length > 0)
                        if(endDelimiters[ac[0]] == '\uFFFF')
                        {
                            CMSyntaxToken h4;
                            (h4 = h1.cloneToken()).a(true);
                            arrayOfCMSyntaxTokens[k] = h4;
                        } else
                        {
                            h1.a(false);
                        }
                }
            }

        }
        sizeTokensPerLine = m_DocumentChangeManager.getNumberOfTokensPerLine();
        delimiterChange = m_DocumentChangeManager.isDelimitersChange();
    	}
    	catch(Exception ex){
    		
    	}
        return arrayOfCMSyntaxTokens;
    }

    private CMDocumentChangeManager m_DocumentChangeManager;

    boolean delimiterChange;
    private int sizeTokensPerLine;
    private char iniDelimiters[];
    private char endDelimiters[];
}