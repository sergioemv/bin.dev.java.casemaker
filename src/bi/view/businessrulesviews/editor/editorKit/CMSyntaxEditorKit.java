package bi.view.businessrulesviews.editor.editorKit;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Properties;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.Document;
import javax.swing.text.StyledEditorKit;
import javax.swing.text.ViewFactory;


@SuppressWarnings("serial")
public class CMSyntaxEditorKit extends StyledEditorKit
{

    public CMSyntaxEditorKit()
    {
        m_CMViewFactory = new CMViewFactory();
        m_DocumentManager = new CMDocumentManger();
        m_CMViewFactory.setDocumentManager(m_DocumentManager);
    }

    public CMSyntaxEditorKit(String p_FilePath)
        throws FileNotFoundException
    {
        this();
        readSyntaxColorDescriptor(p_FilePath);
    }

    public CMSyntaxEditorKit(Properties properties)
    {
        this();
        setSyntaxColorDescriptor(properties);
    }

    public Document createDefaultDocument()
    {
        return new DefaultStyledDocument();
    }

    public void readSyntaxColorDescriptor(URL url)
        throws FileNotFoundException
    {
        chargeSyntaxProperties(getProperties(url));
    }

    private Properties getProperties(URL url)
        throws FileNotFoundException
    {
        Properties properties;
        properties = null;
        try
        {
            properties = new Properties();
            InputStream inputstream = url.openStream();
            try
            {
                properties.load(inputstream);
            }
            finally
            {
                inputstream.close();
            }
        }
        catch(IOException ioexception)
        {
            throw new FileNotFoundException(ioexception.getMessage());
        }
        return properties;
    }

    public void readSyntaxColorDescriptor(String p_FilePath)
        throws FileNotFoundException
    {
        Object obj;
        URL url = null;
        if(p_FilePath.indexOf("://") > -1)
            try
            {
                url = new URL(p_FilePath);
            }
            catch(Throwable _ex) { }
        else
            try
            {
                url = ((ClassLoader) (obj = ClassLoader.getSystemClassLoader())).getResource(p_FilePath);
            }
            catch(Throwable _ex) { }
        obj = null;
        if(url != null)
            obj = getProperties(url);
       // if(obj != null)
          //  break;// MISSING_BLOCK_LABEL_134;
        File file = new File(p_FilePath);
        obj = new Properties();
        try
        {
            FileInputStream fileinputstream = new FileInputStream(file);
            try
            {
                ((Properties) (obj)).load(fileinputstream);
            }
            finally
            {
                fileinputstream.close();
            }
        }
        catch(IOException ioexception)
        {
            throw new FileNotFoundException(ioexception.getMessage());
        }
        chargeSyntaxProperties(((Properties) (obj)));
        return;
    }

    public void setSyntaxColorDescriptor(Properties properties)
    {
        chargeSyntaxProperties(properties);
    }

    @SuppressWarnings("unchecked")
	private CMSyntaxToken getSimpleCMSyntaxToken(Hashtable p_TokenTable, String p_TokenName, String p_TokenValue)
    {
        Vector vector;
        if((vector = (Vector)p_TokenTable.get(p_TokenName)) == null)
        {
            vector = new Vector();
            p_TokenTable.put(p_TokenName, vector);
        }
        CMSyntaxToken h1 = null;
        int i;
        int j;
        int k;
        if((i = p_TokenValue.indexOf("[[")) > -1 && (j = p_TokenValue.indexOf(']', i + 2)) > -1 && (k = p_TokenValue.indexOf(']', j + 1)) > -1)
            try
            {
                String s2 = p_TokenValue.substring(i + 2, j);
                String s3 = p_TokenValue.substring(j + 1, k);
                int l = (new Integer(s3)).intValue();
                String s4 = "";
                if(i > 0)
                    s4 = p_TokenValue.substring(0, i);
                String s5 = "";
                if(k < p_TokenValue.length() - 1)
                    s5 = p_TokenValue.substring(k + 1);
                for(int i1 = 0; i1 < l; i1++)
                {
                    for(int j1 = 0; j1 < s2.length(); j1++)
                    {
                        String s6 =new String();
                        s6=s6+ s2.charAt(j1);
                        for(int k1 = 0; k1 < i1; k1++)
                            s6 = s6 + s2.charAt(j1);

                        String s7 = s4 + s6 + s5;
                        h1 = new CMSyntaxToken(s7.toCharArray());
                        vector.addElement(h1);
                    }

                }

            }
            catch(Throwable _ex) { }
        p_TokenValue = getTokenValue(p_TokenValue);
        vector.addElement(h1 = new CMSyntaxToken(p_TokenValue.toCharArray()));
        return h1;
    }

    private String getCharValue(String p_Value)
    {
        try
        {
            return Character.toString((char)(Integer.parseInt(p_Value)));
        }
        catch(NumberFormatException _ex) { }
        if(p_Value.equals("_DD_"))
            return ":";
        else
            return p_Value;
    }

    private String getTokenValue(String p_TokenValue)
    {
        StringBuffer stringbuffer = new StringBuffer();
        for(int i = 0; i < p_TokenValue.length(); i++)
        {
            char c1;
            if((c1 = p_TokenValue.charAt(i)) == '$')
            {
                if(i + 1 < p_TokenValue.length())
                {
                    if((p_TokenValue.charAt(i + 1)) == '{')
                    {
                        StringBuffer stringbuffer1 = new StringBuffer();
                        boolean flag = false;
                        int j = 0;
                        for(int k = i + 2; k < p_TokenValue.length(); k++)
                        {
                            char c3;
                            if((c3 = p_TokenValue.charAt(k)) == '}')
                            {
                                stringbuffer.append(getCharValue(stringbuffer1.toString()));
                                flag = true;
                                j = k;
                                break;
                            }
                            stringbuffer1.append(c3);
                        }

                        if(flag)
                            i = j;
                        else
                            stringbuffer.append(c1);
                    } else
                    {
                        stringbuffer.append(c1);
                    }
                } else
                {
                    stringbuffer.append(c1);
                }
            } else
            {
                stringbuffer.append(c1);
            }
        }

        return stringbuffer.toString();
    }

    @SuppressWarnings("unchecked")
	private CMSyntaxToken getDelimiterCMSyntaxToken(Hashtable p_TokenTable, String p_TokenName, String p_InitValue, String p_EndValue)
    {
        Vector vector;
        if((vector = (Vector)p_TokenTable.get(p_TokenName)) == null)
        {
            vector = new Vector();
            p_TokenTable.put(p_TokenName, vector);
        }
        CMSyntaxToken l_syntaxToken = new CMSyntaxToken(p_InitValue.toCharArray());
        CMSyntaxToken l_delimiterTokenEnd = new CMSyntaxToken(p_EndValue.toCharArray());
        int i;
        if(p_EndValue.endsWith("}}") && (i = p_EndValue.indexOf("{{")) > -1)
            try
            {
                String s3 = p_EndValue.substring(i + 2, p_EndValue.length() - 2);
                (l_delimiterTokenEnd = new CMSyntaxToken(p_EndValue.substring(0, i).toCharArray())).setM_CharacterException(s3.charAt(0));
            }
            catch(Throwable _ex)
            {
                System.err.println("Invalid token " + p_TokenName);
            }
        l_syntaxToken.setDelimiterTokenEnd(l_delimiterTokenEnd);
        vector.add(l_syntaxToken);
        return l_syntaxToken;
    }

    private String getValidToken(String p_TokenId)
    {
        if("_DD_".equals(p_TokenId))
            return ":";
        if("_RC_".equals(p_TokenId))
            return "\n";
        else
            return p_TokenId;
    }

    private CMSyntaxToken getCMSyntaxToken(Hashtable p_tokenTable, String p_tokenName, String p_TokenValue)
    {
        String s2;
        int i;
        if((i = (s2 = p_TokenValue.substring(p_TokenValue.indexOf("[") + 1, p_TokenValue.lastIndexOf("]"))).indexOf(";")) > -1)
            return getDelimiterCMSyntaxToken(p_tokenTable, p_tokenName, s2.substring(0, i), getValidToken(s2.substring(i + 1)));
        else
            return getSimpleCMSyntaxToken(p_tokenTable, p_tokenName, getValidToken(p_TokenValue));
    }

    private void registerCMSyntaxToken(boolean ignoreTokenMatch, Hashtable p_tokenTable, String p_TokenName, String p_TokenValues)
    {
        CMSyntaxToken l_SyntaxToken = null;
        if(p_TokenValues.startsWith("[") && p_TokenValues.endsWith("]"))
        {
            (l_SyntaxToken = getCMSyntaxToken(p_tokenTable, p_TokenName, p_TokenValues)).setIgnoreTokenMach(ignoreTokenMatch);
            if(l_SyntaxToken.getDelimiterTokenEnd() != null)
            {
                l_SyntaxToken.getDelimiterTokenEnd().setIgnoreTokenMach(ignoreTokenMatch);
                return;
            }
        } else
        {
            for(StringTokenizer stringtokenizer = new StringTokenizer(p_TokenValues, ":", false); stringtokenizer.hasMoreTokens();)
            {
                String s2 = stringtokenizer.nextToken();
                if((s2 = getValidToken(s2)).startsWith("[") && s2.endsWith("]"))
                    l_SyntaxToken = getCMSyntaxToken(p_tokenTable, p_TokenName, s2);
                else
                if(s2.startsWith("(") && s2.endsWith(")"))
                {
                    String s3 = s2.substring(s2.indexOf("(") + 1, s2.lastIndexOf(")"));
                    String s4;
                    
                    for(StringTokenizer stringtokenizer1 = new StringTokenizer(s3, ";"); stringtokenizer1.hasMoreTokens(); (getSimpleCMSyntaxToken(p_tokenTable, p_TokenName, s4)).setCollectionName(p_TokenName))
                        s4 = getValidToken(stringtokenizer1.nextToken());

                } else
                {
                    l_SyntaxToken = getSimpleCMSyntaxToken(p_tokenTable, p_TokenName, s2);
                }
                if(l_SyntaxToken != null)
                {
                    l_SyntaxToken.setIgnoreTokenMach(ignoreTokenMatch);
                    if(l_SyntaxToken.getDelimiterTokenEnd() != null)
                        l_SyntaxToken.getDelimiterTokenEnd().setIgnoreTokenMach(ignoreTokenMatch);
                }
            }

        }
    }

    private void setTokenAtributeValue(Hashtable p_TokenTable, String p_TokenName, String p_TokenAtribute, String p_TokenAtributeValue)
    {
        Vector vector;
        if((vector = (Vector)p_TokenTable.get(p_TokenName)) == null)
            throw new RuntimeException("Unknown token " + p_TokenName + " for color description");
        for(int i = 0; i < vector.size(); i++)
        {
            ((CMSyntaxToken)vector.get(i)).setAtributeByName(p_TokenAtribute, p_TokenAtributeValue);
        }

    }

    @SuppressWarnings("unchecked")
	private void chargeSyntaxProperties(Properties properties)
    {
        m_DocumentManager = new CMDocumentManger();
        m_CMViewFactory.setDocumentManager(m_DocumentManager);
        Hashtable l_TokenTable = new Hashtable();
        if(properties.containsKey("tokenMatchIni"))
        {
            String s = properties.getProperty("tokenMatchIni");
            char ac[] = new char[255];
            for(int i = 0; i < ac.length; i++)
                ac[i] = '\uFFFF';

            for(int j = 0; j < s.length(); j++)
            {
                char c1;
                if((c1 = s.charAt(j)) < '\377')
                    ac[c1] = c1;
            }

            m_DocumentManager.setIniDelimiters(ac);
        }
        if(properties.containsKey("tokenMatchEnd"))
        {
            String s1 = properties.getProperty("tokenMatchEnd");
            char ac1[] = new char[255];
            for(int k = 0; k < ac1.length; k++)
                ac1[k] = '\uFFFF';

            for(int l = 0; l < s1.length(); l++)
            {
                char c2;
                if((c2 = s1.charAt(l)) < '\377')
                    ac1[c2] = c2;
            }

            m_DocumentManager.setEndDelimiters(ac1);
        }
        String s2;
        for(Enumeration enumeration = properties.keys(); enumeration.hasMoreElements();)
            if((s2 = (String)enumeration.nextElement()).startsWith("token_"))
            {
                String tokenName = s2.substring(6);
                String tokenValues = properties.getProperty(s2);
                registerCMSyntaxToken("true".equals(properties.getProperty("ignoreTokenMatch_" + tokenName, "false")), l_TokenTable, tokenName, tokenValues);
            }

        String s3;
        int i1;
        for(Enumeration enumeration1 = properties.keys(); enumeration1.hasMoreElements();)
            if(!(s3 = (String)enumeration1.nextElement()).startsWith("token_") && (i1 = s3.indexOf("_")) > -1)
            {
                String l_tokenType = s3.substring(0, i1);
                String l_tokenName = s3.substring(i1 + 1);
                String l_Value = properties.getProperty(s3);
                setTokenAtributeValue(l_TokenTable, l_tokenName, l_tokenType, l_Value);
            }

        Vector vector = new Vector();
        for(Enumeration enumeration2 = l_TokenTable.elements(); enumeration2.hasMoreElements();)
        {
            Vector vector1 = (Vector)enumeration2.nextElement();
            for(int k1 = 0; k1 < vector1.size(); k1++)
            {
                CMSyntaxToken l_CMSyntaxToken = (CMSyntaxToken)vector1.get(k1);
                vector.addElement(l_CMSyntaxToken);
            }

        }

        CMSyntaxToken arrayOfTokens[] = new CMSyntaxToken[vector.size()];
        for(int j1 = 0; j1 < vector.size(); j1++)
            arrayOfTokens[j1] = (CMSyntaxToken)vector.get(j1);

        boolean l_IgnoreCase = "true".equals(properties.getProperty("ignoreCase"));
        m_DocumentManager.initialize(l_IgnoreCase, arrayOfTokens);
    }

    public ViewFactory getViewFactory()
    {
        return m_CMViewFactory;
    }

    private CMDocumentManger m_DocumentManager;
    CMViewFactory m_CMViewFactory;
    public static final String SYNTAXFILE_NAME = "editorKit/syntaxBusinessRules.properties"; //$NON-NLS-1$
    public static final String SYNTAXFILE_NAME_GERMAN = "editorKit/syntaxBusinessRulesGerman.properties"; //$NON-NLS-1$
}