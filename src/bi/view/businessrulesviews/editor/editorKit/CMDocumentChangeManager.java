package bi.view.businessrulesviews.editor.editorKit;


import java.util.Hashtable;
import java.util.Vector;



public class CMDocumentChangeManager
{
    

    public CMDocumentChangeManager()
    {
        ignoreCase = false;
        m_CMSyntaxToken = new CMSyntaxToken[200];
        numberOfTokensPerLine = 0;
        f = new CMSyntaxTokenManager[200];
        m_currentIndex = 0;
        delimitersChange = false;
        delimiterContents = new Hashtable();
    }

    public  void setIgnoreCase(boolean p_ignoreCase)
    {
        ignoreCase = p_ignoreCase;
    }

    public  CMSyntaxToken registerCMSyntaxToken(CMSyntaxToken p_CMSyntaxToken)
    {
        if(m_CMSyntaxTokenManager == null)
        {
            m_CMSyntaxTokenManager = new CMSyntaxTokenManager();
            m_CMSyntaxTokenManager.setIgnoreCase(ignoreCase);
            m_ListofAllTokensToMatch = (new CMSyntaxTokenManager[] {
                m_CMSyntaxTokenManager
            });
        }
        m_CMSyntaxTokenManager.registerCMSyntaxToken(p_CMSyntaxToken);
        return p_CMSyntaxToken;
    }

    public  int getNumberOfTokensPerLine()
    {
        return numberOfTokensPerLine;
    }

    private CMSyntaxTokenManager getCMSyntaxTokenManager(char ac[], int k, CMSyntaxTokenManager ac1[], int l)
    {
        CMSyntaxTokenManager c1 = null;
        for(int i1 = k; i1 < ac.length; i1++)
        {
            boolean flag = true;
            for(int j1 = 0; j1 < ac1.length; j1++)
            {
                CMSyntaxTokenManager ac2[];
                if(j1 != l && (ac2 = ac1[j1].getPossibleMatchTokens(ac[i1])) != null)
                {
                    flag = false;
                    for(int k1 = 0; k1 < ac2.length; k1++)
                        if(ac2[k1].isIdentifyToken())
                        {
                            if(c1 == null)
                                c1 = ac2[k1];
                            else
                            if(c1.getM_CMSyntaxToken().getTokenValue().length < ac2[k1].getM_CMSyntaxToken().getTokenValue().length)
                                c1 = ac2[k1];
                        } else
                        {
                            CMSyntaxTokenManager c2 = getCMSyntaxTokenManager(ac, i1 + 1, ac2, -1);
                            if(c1 == null)
                                c1 = c2;
                            else
                            if(c2 != null && c2.getM_CMSyntaxToken().getTokenValue().length > c1.getM_CMSyntaxToken().getTokenValue().length)
                                c1 = c2;
                        }

                }
            }

            if(flag)
                break;
        }

        return c1;
    }

    @SuppressWarnings("unchecked")
	public  CMSyntaxToken[] getCMSyntaxTokenPerLine(String p_LineValue, int p_LineNumber)
    {
        delimitersChange = false;
        char l_tokenValue[] = p_LineValue.toCharArray();
        if(m_CMSyntaxTokenManager == null)
            return (new CMSyntaxToken[] {
                new CMSyntaxToken(l_tokenValue)
            });
        numberOfTokensPerLine = 0;
        int l = 0;
        CMSyntaxToken h1 = (CMSyntaxToken)delimiterContents.get(new Integer(p_LineNumber - 1));
        CMSyntaxTokenManager ac2[] = (CMSyntaxTokenManager[])null;
        boolean flag1 = false;
        CMSyntaxTokenManager possiblestokensToMatch[];
        if(h1 == null)
        {
            possiblestokensToMatch = m_ListofAllTokensToMatch;
        } else
        {
            flag1 = true;
            ac2 = possiblestokensToMatch = (new CMSyntaxTokenManager[] {
                new CMSyntaxTokenManager(ignoreCase, h1)
            });
        }
        int indexCurrentChar = 0;
label0:
        while(indexCurrentChar < l_tokenValue.length) 
        {
            char currentChar = l_tokenValue[indexCurrentChar];
            m_currentIndex = 0;
            int l2 = 0;
            char copyCurrentChar = currentChar;
label1:
            while(l2 < possiblestokensToMatch.length) 
            {
                CMSyntaxTokenManager ac4[];
                if((ac4 = possiblestokensToMatch[l2].getPossibleMatchTokens(currentChar)) == null && !flag1)
                {
                    if(l2 < possiblestokensToMatch.length - 1)
                    {
                        for(int l3 = l2 + 1; l3 < possiblestokensToMatch.length; l3++)
                        {
                            if(possiblestokensToMatch[l3].getPossibleMatchTokens(currentChar) == null)
                                continue;
                            l2 = l3;
                            continue label1;
                        }

                    }
                    ac4 = m_CMSyntaxTokenManager.getPossibleMatchTokens(currentChar);
                }
                if(ac4 != null)
                {
                    for(int k3 = 0; k3 < ac4.length; k3++)
                    {
                        CMSyntaxTokenManager identifyToken;
                        boolean flag5;
                        if((flag5 = (identifyToken = ac4[k3]).isIdentifyToken()) && ac2 != null && ac2[0].getM_CMSyntaxToken().getM_CharacterException() > 0)
                        {
                            char c6 = ac2[0].getM_CMSyntaxToken().getM_CharacterException();
                            if(indexCurrentChar > 1 && l_tokenValue[indexCurrentChar - 1] == c6 && l_tokenValue[indexCurrentChar - 2] != c6)
                            {
                                indexCurrentChar++;
                                continue label0;
                            }
                        }
                        if(flag5)
                        {
                            boolean flag6 = identifyToken.getM_CMSyntaxToken().getDelimiterTokenEnd() != null;
                            CMSyntaxTokenManager c7 = null;
                            if(!flag6)
                                c7 = getCMSyntaxTokenManager(l_tokenValue, indexCurrentChar, possiblestokensToMatch, possiblestokensToMatch.length != 1 ? k3 : -1);
                            if(c7 != null && c7.getM_CMSyntaxToken().getTokenValue().length > identifyToken.getM_CMSyntaxToken().getTokenValue().length)
                            {
                                indexCurrentChar += c7.getM_CMSyntaxToken().getTokenValue().length - identifyToken.getM_CMSyntaxToken().getTokenValue().length;
                                int k4 = 0;
                                if((k4 = (new String(c7.getM_CMSyntaxToken().getTokenValue())).indexOf(new String(identifyToken.getM_CMSyntaxToken().getTokenValue()))) > -1)
                                    indexCurrentChar -= k4;
                                identifyToken = c7;
                            }
                            if((indexCurrentChar + 1) - identifyToken.getM_CMSyntaxToken().getLength() - l > 0)
                            {
                                CMSyntaxToken h7;
                                m_CMSyntaxToken[numberOfTokensPerLine++] = h7 = new CMSyntaxToken(p_LineValue.substring(l, (indexCurrentChar + 1) - identifyToken.getM_CMSyntaxToken().getLength()).toCharArray());
                                if(h1 != null)
                                    h7.setAttributes(h1);
                                h7.a(false);
                            }
                            l = indexCurrentChar + 1;
                            m_CMSyntaxToken[numberOfTokensPerLine++] = identifyToken.getM_CMSyntaxToken();
                            if(identifyToken.getM_CMSyntaxToken().getDelimiterTokenEnd() != null)
                            {
                                flag1 = true;
                                h1 = identifyToken.getM_CMSyntaxToken().getDelimiterTokenEnd();
                                ac2 = possiblestokensToMatch = (new CMSyntaxTokenManager[] {
                                    new CMSyntaxTokenManager(ignoreCase, identifyToken.getM_CMSyntaxToken().getDelimiterTokenEnd())
                                });
                            } else
                            {
                                h1 = null;
                                flag1 = false;
                                possiblestokensToMatch = m_ListofAllTokensToMatch;
                                m_currentIndex = 0;
                            }
                            break label1;
                        }
                        boolean flag7 = false;
                        for(int j4 = 0; j4 < m_currentIndex; j4++)
                        {
                            if(f[j4] != identifyToken)
                                continue;
                            flag7 = true;
                            break;
                        }

                        if(!flag7)
                            f[m_currentIndex++] = identifyToken;
                    }

                }
                l2++;
            }
            CMSyntaxTokenManager ac5[];
            if(m_currentIndex == 0)
            {
                if(!flag1)
                    possiblestokensToMatch = m_ListofAllTokensToMatch;
                else
                if(ac2 != null && (possiblestokensToMatch = ac2).length == 1 && possiblestokensToMatch[0].getM_CMSyntaxToken().getTokenValue().length > 1 && (ac5 = possiblestokensToMatch[0].getPossibleMatchTokens(copyCurrentChar)) != null)
                    possiblestokensToMatch = ac5;
            } else
            if(!flag1 || ac2[0].getM_CMSyntaxToken().getTokenValue().length != 1)
            {
                possiblestokensToMatch = new CMSyntaxTokenManager[m_currentIndex];
                for(int i3 = 0; i3 < m_currentIndex; i3++)
                    possiblestokensToMatch[i3] = f[i3];

            }
            indexCurrentChar++;
        }
        if(l > 0 || m_currentIndex > 0)
        {
            int j2 = p_LineValue.length();
            CMSyntaxToken h3 = null;
            if(m_currentIndex > 0)
            {
                for(int j1 = 0; j1 < m_currentIndex; j1++)
                {
                    CMSyntaxTokenManager c3;
                    if(!(c3 = f[j1]).isIdentifyToken())
                        continue;
                    j2 -= c3.getM_CMSyntaxToken().getTokenValue().length;
                    h3 = c3.getM_CMSyntaxToken();
                    l += h3.getTokenValue().length;
                    break;
                }

            }
            if(l < p_LineValue.length())
            {
                CMSyntaxToken h4;
                m_CMSyntaxToken[numberOfTokensPerLine++] = h4 = new CMSyntaxToken(p_LineValue.substring(l, j2).toCharArray());
                if(h1 != null)
                    h4.setAttributes(h1);
            }
            if(h3 != null)
                m_CMSyntaxToken[numberOfTokensPerLine++] = h3;
        }
        if(flag1)
        {
            for(int k2 = 0; k2 < possiblestokensToMatch.length; k2++)
            {
                CMSyntaxTokenManager ac3[];
                if((ac3 = possiblestokensToMatch[k2].getPossibleMatchTokens('\n')) != null)
                {
                    for(int k1 = 0; k1 < ac3.length; k1++)
                    {
                        CMSyntaxTokenManager c4;
                        if(!(c4 = ac3[k1]).isIdentifyToken())
                            continue;
                        if((k1 + 1) - c4.getM_CMSyntaxToken().getLength() - l > 0)
                        {
                            CMSyntaxToken h5;
                            m_CMSyntaxToken[numberOfTokensPerLine++] = h5 = new CMSyntaxToken(p_LineValue.substring(l, (k1 + 1) - c4.getM_CMSyntaxToken().getLength()).toCharArray());
                            if(h1 != null)
                                h5.setAttributes(h1);
                            h5.a(false);
                        }
                        l = k1 + 1;
                        if(c4.getM_CMSyntaxToken().getTokenValue().length != 1 || c4.getM_CMSyntaxToken().getTokenValue()[0] != '\n')
                            m_CMSyntaxToken[numberOfTokensPerLine++] = c4.getM_CMSyntaxToken();
                        if(c4.getM_CMSyntaxToken().getDelimiterTokenEnd() != null)
                        {
                            h1 = c4.getM_CMSyntaxToken().getDelimiterTokenEnd();
                            possiblestokensToMatch = (new CMSyntaxTokenManager[] {
                                new CMSyntaxTokenManager(ignoreCase, c4.getM_CMSyntaxToken().getDelimiterTokenEnd())
                            });
                            break;
                        }
                        h1 = null;
                        possiblestokensToMatch = m_ListofAllTokensToMatch;
                        m_currentIndex = 0;
                    }

                }
            }

        }
        if(numberOfTokensPerLine == 0)
        {
            CMSyntaxToken h2;
            m_CMSyntaxToken[numberOfTokensPerLine++] = h2 = new CMSyntaxToken(p_LineValue.toCharArray());
            if(h1 != null)
                h2.setAttributes(h1);
        }
        if(h1 != null)
        {
            if(!delimiterContents.containsKey(new Integer(p_LineNumber)))
                delimitersChange = true;
            delimiterContents.put(new Integer(p_LineNumber), h1);
        } else
        {
            Integer integer = new Integer(p_LineNumber);
            if(delimiterContents.containsKey(integer))
            {
                delimiterContents.remove(integer);
                delimitersChange = true;
            }
        }
        boolean flag3 = false;
        for(int l1 = 0; l1 < numberOfTokensPerLine; l1++)
        {
            if(!m_CMSyntaxToken[l1].hasCollectionName())
                continue;
            flag3 = true;
            break;
        }

        if(flag3)
        {
            /*if(j == null)
                j = new Vector();
            else
                j.removeAllElements();*/
        	Vector l_Tokens= new Vector();
            for(int i2 = 0; i2 < numberOfTokensPerLine; i2++)
                if(m_CMSyntaxToken[i2].hasCollectionName())
                {
                    String s1 = m_CMSyntaxToken[i2].getM_CollectionName();
                    StringBuffer stringbuffer = null;
                    int i4;
                    for(i4 = i2 + 1; i4 < numberOfTokensPerLine; i4++)
                    {
                        if(!m_CMSyntaxToken[i4].hasCollectionName() || !m_CMSyntaxToken[i4].getM_CollectionName().equals(s1))
                            break;
                        if(stringbuffer == null)
                            (stringbuffer = new StringBuffer()).append(m_CMSyntaxToken[i2].getTokenValue());
                        stringbuffer.append(m_CMSyntaxToken[i4].getTokenValue());
                    }

                    if(stringbuffer != null)
                    {
                        CMSyntaxToken h6;
                        l_Tokens.add(h6 = new CMSyntaxToken(stringbuffer.toString().toCharArray()));
                        h6.setAttributes(m_CMSyntaxToken[i2]);
                        i2 = i4 - 1;
                    } else
                    {
                        l_Tokens.add(m_CMSyntaxToken[i2]);
                    }
                } else
                {
                    l_Tokens.add(m_CMSyntaxToken[i2]);
                }

            numberOfTokensPerLine = l_Tokens.size();
            for(int j3 = 0; j3 < l_Tokens.size(); j3++)
                m_CMSyntaxToken[j3] = (CMSyntaxToken)l_Tokens.get(j3);

        }
        return m_CMSyntaxToken;
    }

    public  boolean isDelimitersChange()
    {
        return delimitersChange;
    }

    CMSyntaxTokenManager m_CMSyntaxTokenManager;
    Hashtable delimiterContents;
    private boolean ignoreCase;
    CMSyntaxToken m_CMSyntaxToken[];
    int numberOfTokensPerLine;
    CMSyntaxTokenManager m_ListofAllTokensToMatch[];
    CMSyntaxTokenManager f[];
    int m_currentIndex;
    private boolean delimitersChange;

}