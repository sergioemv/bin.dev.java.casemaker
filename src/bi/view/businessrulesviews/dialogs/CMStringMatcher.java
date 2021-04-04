package bi.view.businessrulesviews.dialogs;


import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import javax.swing.text.JTextComponent;



final class CMStringMatcher
{
 
    CMStringMatcher()
    {
        forwardOrientation = true;
        allScope = true;
        caseSensitive = false;
        wholeWord = false;
        regularExpresion = false;
        wrapSearch = false;
        stringToFind = null;
        startScopeIndex = -1;
        endScopeIndex = -1;
        caretPosition = -1;
        currentCharIndex = -1;
        currentCharToSearchIndex = -1;
        lenghtMatchWord = -1;
    }

    final void initializeIndex()
    {
        caretPosition = -1;
        currentCharToSearchIndex = -1;
        startScopeIndex = -1;
        endScopeIndex = -1;
        lenghtMatchWord = -1;
    }

    final int lengthOfWord()
    {
        if(!regularExpresion)
            return stringToFind.length;
        else
            return lenghtMatchWord;
    }

    final int indexOfFindWord(JTextComponent jtextcomponent)
    {
        if(jtextcomponent.getCaretPosition() < caretPosition)
            initializeIndex();
        if(stringToFind == null || stringToFind.length == 0)
            return -1;
        if(currentCharToSearchIndex == 0 && !forwardOrientation)
            currentCharToSearchIndex = -1;
        if(currentCharToSearchIndex == stringToFind.length)
            currentCharToSearchIndex = -1;
        if(currentCharToSearchIndex >= stringToFind.length)
            currentCharToSearchIndex = stringToFind.length - 2;
        if(currentCharToSearchIndex <= -1 || currentCharToSearchIndex >= stringToFind.length)
            initializeIndexSearchWord();
        if(startScopeIndex == -1)
            if(allScope)
                startScopeIndex = 0;
            else
                startScopeIndex = jtextcomponent.getSelectionStart();
        if(endScopeIndex == -1)
            if(allScope)
                endScopeIndex = jtextcomponent.getDocument().getLength();
            else
                endScopeIndex = jtextcomponent.getSelectionEnd() + 1;
        if(endScopeIndex >= jtextcomponent.getDocument().getLength())
            endScopeIndex = jtextcomponent.getDocument().getLength();
        if(caretPosition <= -1)
        {
            caretPosition = allScope ? jtextcomponent.getCaretPosition() : forwardOrientation ? startScopeIndex : endScopeIndex - 1;
            if(caretPosition >= endScopeIndex)
                caretPosition--;
        }
        currentCharIndex = caretPosition;
        int i1;
        if((i1 = endScopeIndex) == 0)
            return -1;
        boolean flag = caretPosition < startScopeIndex || caretPosition >= endScopeIndex;
        char ac[] = jtextcomponent.getText().toCharArray();
        if(flag && allScope)
        {
            validatePosition(endScopeIndex);
            flag = caretPosition < startScopeIndex || caretPosition >= endScopeIndex;
        }
        boolean flag1 = false;
        while(!flag) 
        {
            if(!regularExpresion)
            {
                int j1;
                if((j1 = getValidPosition(ac)) > -1)
                    return j1;
                moveCurrentChar();
            } else
            {
                try
                {
                    Pattern pattern = null;
                    if(!caseSensitive)
                        pattern = Pattern.compile(new String(stringToFind), 2);
                    else
                        pattern = Pattern.compile(new String(stringToFind));
                    Matcher matcher;
                    if((matcher = pattern.matcher(jtextcomponent.getText())).find(currentCharIndex))
                    {
                        currentCharIndex = matcher.end();
                        lenghtMatchWord = matcher.end() - matcher.start();
                        if(matcher.end() > endScopeIndex)
                            currentCharIndex = endScopeIndex;
                        else
                            return matcher.start();
                    } else
                    {
                        currentCharIndex = endScopeIndex;
                    }
                }
                catch(PatternSyntaxException _ex)
                {
                    return -1;
                }
            }
            if((flag = currentCharIndex < startScopeIndex || currentCharIndex >= endScopeIndex) && (wrapSearch && allScope))
            {
                validatePosition(endScopeIndex);
                if(!flag1)
                    flag = currentCharIndex < startScopeIndex || currentCharIndex >= endScopeIndex;
                flag1 = true;
            }
        }
        return -1;
    }

    final int getValidPosition(char ac[])
    {
        if(equalsChars(ac[currentCharIndex], stringToFind[currentCharToSearchIndex]))
        {
            moveIndexSearchWord();
            if(isValidCurrentChatToSeachPosition())
            {
                boolean flag = true;
                moveCurrentChar();
                if(wholeWord)
                {
                    flag = false;
                    if(isValidWord(forwardOrientation ? currentCharIndex : currentCharIndex + (stringToFind.length + 1), ac) && isValidWord(forwardOrientation ? currentCharIndex - stringToFind.length - 1 : currentCharIndex, ac))
                        flag = true;
                }
                if(flag)
                {
                    int i1 = forwardOrientation ? currentCharIndex - stringToFind.length : currentCharIndex + 1;
                    if(currentCharIndex < 0)
                        currentCharIndex = 0;
                    return i1;
                }
                initializeIndexSearchWord();
            }
        } else
        {
            initializeIndexSearchWord();
        }
        return -1;
    }

    final void validatePosition(int i1)
    {
        if(wrapSearch)
        {
            if(currentCharIndex < 0)
            {
                currentCharIndex = i1 - 1;
                return;
            }
            currentCharIndex = 0;
        }
    }

    final boolean isValidWord(int i1, char ac[])
    {
        if(i1 < 0)
            return true;
        if(i1 >= ac.length - 1)
            return true;
        char c1;
        if((c1 = ac[i1]) == ':' || c1 == '!' || c1 == ',' || c1 == '.' || c1 == ';' || c1 == '[' || c1 == ']' || c1 == '(' || c1 == ')' || c1 == '{' || c1 == '}')
            return true;
        else
            return Character.isWhitespace(c1);
    }

    final boolean isValidCurrentChatToSeachPosition()
    {
        if(forwardOrientation)
            return currentCharToSearchIndex == stringToFind.length;
        return currentCharToSearchIndex == -1;
    }

    final void initializeIndexSearchWord()
    {
        currentCharToSearchIndex = forwardOrientation ? 0 : stringToFind.length - 1;
    }

    final void moveCurrentChar()
    {
        currentCharIndex = forwardOrientation ? currentCharIndex + 1 : currentCharIndex - 1;
    }

    final void moveIndexSearchWord()
    {
        currentCharToSearchIndex = forwardOrientation ? currentCharToSearchIndex + 1 : currentCharToSearchIndex - 1;
    }

    final boolean equalsChars(char c1, char c2)
    {
        if(caseSensitive)
            return c1 == c2;
        return c1 + -32 == c2 || c2 + -32 == c1 || c1 == c2;
    }

    boolean forwardOrientation;
    boolean allScope;
    boolean caseSensitive;
    boolean wholeWord;
    boolean regularExpresion;
    boolean wrapSearch;
    char stringToFind[];
    int startScopeIndex;
    int endScopeIndex;
    int caretPosition;
    int currentCharIndex;
    int currentCharToSearchIndex;
    int lenghtMatchWord;

    
}