package bi.view.businessrulesviews.dialogs;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Window;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JWindow;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.text.JTextComponent;

import bi.view.lang.CMMessages;


@SuppressWarnings("serial")
public class CMFindReplacePanel extends JPanel
{
 
    public CMFindReplacePanel(JTextComponent jtextcomponent)
    {
        jLabelFind = new JLabel();
        jComboBoxFind = new JComboBox();
        jLabelReplaceWith = new JLabel();
        jComboBoxReplaceWith = new JComboBox();
        jPanelDirection = new JPanel();
        new ButtonGroup();
        jRadioButtonForward = new JRadioButton();
        jRadioButtonBackward = new JRadioButton();
        jPanelScope = new JPanel();
        jRadioButtonAll = new JRadioButton();
        jRadioButtonSelectedlines = new JRadioButton();
        jPanelOptions = new JPanel();
        jCheckBoxCaseSensitive = new JCheckBox();
        jCheckBoxWholeWord = new JCheckBox();
        jCheckBoxRegularExpresion = new JCheckBox();
        jCheckBoxWrapSearch = new JCheckBox();
        jCheckboxIncremental = new JCheckBox();
        globalLayout = new GridBagLayout();
        jPanelButtons = new JPanel();
        jButtonFind = new JButton(new CMFindAction(this));
        jButtonReplace = new JButton();
        jButtonFindAndReplace = new JButton();
        jButtonReplaceAll = new JButton();
        buttonPaneLayout = new GridBagLayout();
        jTextComponentToFind = null;
        replaceMode = true;
        m_CMCustomActionListener = new CMCustomActionListener(this);
        panelDirectionLayout = new GridBagLayout();
        panelScopeLayout = new GridBagLayout();
        panelOptionsLayout = new GridBagLayout();
        m_Matcher = null;
        jLabelFound = new JLabel();
        startSearch = -1;
        endSearch = -1;
        m_findDocument = new CMFindDocument(this);
        m_ReplaceDocument = new CMReplaceDocument(this);
        if(jtextcomponent == null)
        {
            throw new RuntimeException("Can't use a null textComponent !");
        } else
        {
            initialize();
            jTextComponentToFind = jtextcomponent;
            return;
        }
    }

    public void updateTextComponent(JTextComponent jtextcomponent)
    {
        jTextComponentToFind = jtextcomponent;
        m_Matcher = null;
    }

    public void setReplaceMode(boolean flag)
    {
        replaceMode = flag;
        jComboBoxReplaceWith.setEnabled(flag);
    }

    public boolean isReplaceMode()
    {
        return replaceMode;
    }

    public CMFindReplacePanel(String s1, JTextComponent jtextcomponent)
    {
        this(jtextcomponent);
        jComboBoxFind.setSelectedItem(s1);
    }

    private void initialize()
    {
        jBorderDirection = BorderFactory.createEtchedBorder();//LineBorder(SystemColor.controlText, 1);
        jTitleBorderDirection = new TitledBorder(jBorderDirection, CMMessages.getString("BUSINESSULES_FINDREPLACE_DIALOG_DIRECTION_LABEL")); //$NON-NLS-1$
        jTitleBorderDirection.setTitleColor(Color.BLACK);
        jBorderScope = BorderFactory.createEtchedBorder();//createLineBorder(SystemColor.controlText, 1);
        jTitleBorderScope = new TitledBorder(jBorderScope, CMMessages.getString("BUSINESSULES_FINDREPLACE_DIALOG_SCOPE_LABEL")); //$NON-NLS-1$
        jTitleBorderScope.setTitleColor(Color.BLACK);
        jBorderOptions = BorderFactory.createEtchedBorder();//createLineBorder(SystemColor.controlText, 1);
        jTitleBorderOptions = new TitledBorder(jBorderOptions, CMMessages.getString("BUSINESSULES_FINDREPLACE_DIALOG_OPTION_LABEL")); //$NON-NLS-1$
        jTitleBorderOptions.setTitleColor(Color.BLACK);
        jLabelReplaceWith.setDoubleBuffered(false);
        jLabelReplaceWith.setDisplayedMnemonic(CMMessages.getString("BUSINESSULES_FINDREPLACE_DIALOG_LABEL_REPLACE_WITH_MNEMONIC").charAt(0));
        jLabelReplaceWith.setLabelFor(jComboBoxReplaceWith);
        jLabelReplaceWith.setText(CMMessages.getString("BUSINESSULES_FINDREPLACE_DIALOG_REPLACE_WITH_LABEL")); //$NON-NLS-1$
        jLabelFind.setDisplayedMnemonic(CMMessages.getString("BUSINESSULES_FINDREPLACE_DIALOG_LABEL_FIND_MNEMONIC").charAt(0));
        jLabelFind.setLabelFor(jComboBoxFind);
        jLabelFind.setText(CMMessages.getString("BUSINESSULES_FINDREPLACE_DIALOG_FIND_LABEL")); //$NON-NLS-1$
        setLayout(globalLayout);
        jComboBoxFind.setEnabled(true);
        jComboBoxFind.setEditable(true);
        jComboBoxReplaceWith.setEditable(true);
        jPanelDirection.setBorder(jTitleBorderDirection);
        jPanelDirection.setLayout(panelDirectionLayout);
        jRadioButtonForward.setMnemonic(CMMessages.getString("BUSINESSULES_FINDREPLACE_DIALOG_FORWARD_MNEMONIC").charAt(0));
        jRadioButtonForward.setSelected(true);
        jRadioButtonForward.setText(CMMessages.getString("BUSINESSULES_FINDREPLACE_DIALOG_FORWARD_LABEL")); //$NON-NLS-1$
        jRadioButtonBackward.setMnemonic(CMMessages.getString("BUSINESSULES_FINDREPLACE_DIALOG_BACKWARD_MNEMONIC").charAt(0));
        jRadioButtonBackward.setText(CMMessages.getString("BUSINESSULES_FINDREPLACE_DIALOG_BACKWARD_LABEL")); //$NON-NLS-1$
        jPanelScope.setBorder(jTitleBorderScope);
        jPanelScope.setMaximumSize(new Dimension(32767, 32767));
        jPanelScope.setLayout(panelScopeLayout);
        jRadioButtonAll.setMnemonic(CMMessages.getString("BUSINESSULES_FINDREPLACE_DIALOG_ALL_MNEMONIC").charAt(0));
        jRadioButtonAll.setSelected(true);
        jRadioButtonAll.setText(CMMessages.getString("BUSINESSULES_FINDREPLACE_DIALOG_ALL_LABEL")); //$NON-NLS-1$
        jRadioButtonSelectedlines.setMnemonic(CMMessages.getString("BUSINESSULES_FINDREPLACE_DIALOG_SELECTED_LINES_MNEMONIC").charAt(0));
        jRadioButtonSelectedlines.setText(CMMessages.getString("BUSINESSULES_FINDREPLACE_DIALOG_SELECTED_LINES_LABEL")); //$NON-NLS-1$
        jPanelOptions.setLayout(panelOptionsLayout);
        jPanelOptions.setBorder(jTitleBorderOptions);
        jCheckBoxCaseSensitive.setMnemonic(CMMessages.getString("BUSINESSULES_FINDREPLACE_DIALOG_CASE_SENSITIVE_MNEMONIC").charAt(0));
        jCheckBoxCaseSensitive.setText(CMMessages.getString("BUSINESSULES_FINDREPLACE_DIALOG_CASE_SENSITIVE_LABEL")); //$NON-NLS-1$
        jCheckBoxWholeWord.setMnemonic(CMMessages.getString("BUSINESSULES_FINDREPLACE_DIALOG_WHOLE_WORD_MNEMONIC").charAt(0));
        jCheckBoxWholeWord.setText(CMMessages.getString("BUSINESSULES_FINDREPLACE_DIALOG_WHOLE_WORD_LABEL")); //$NON-NLS-1$
        jCheckBoxRegularExpresion.setMnemonic(CMMessages.getString("BUSINESSULES_FINDREPLACE_DIALOG_REGULAR_EXPRESION_MNEMONIC").charAt(0));
        jCheckBoxRegularExpresion.setText(CMMessages.getString("BUSINESSULES_FINDREPLACE_DIALOG_REGULAR_EXPRESSIONS_LABEL")); //$NON-NLS-1$
        jCheckBoxWrapSearch.setMnemonic(CMMessages.getString("BUSINESSULES_FINDREPLACE_DIALOG_WRAP_SEARCH_MNEMONIC").charAt(0));
        jCheckBoxWrapSearch.setText(CMMessages.getString("BUSINESSULES_FINDREPLACE_DIALOG_WRAP_SEARCH_LABEL")); //$NON-NLS-1$
        jCheckboxIncremental.setMnemonic(CMMessages.getString("BUSINESSULES_FINDREPLACE_DIALOG_INCREMENTAL_MNEMONIC").charAt(0));
        jCheckboxIncremental.setText(CMMessages.getString("BUSINESSULES_FINDREPLACE_DIALOG_INCREMENTAL_LABEL")); //$NON-NLS-1$
        jPanelButtons.setLayout(buttonPaneLayout);
        jButtonFind.setMnemonic(CMMessages.getString("BUSINESSULES_FINDREPLACE_DIALOG_FIND_BUTTON_MNEMONIC").charAt(0));
        jButtonFind.setText(CMMessages.getString("BUSINESSULES_FINDREPLACE_DIALOG_FIND_BUTTON")); //$NON-NLS-1$
        jButtonReplace.setMnemonic(CMMessages.getString("BUSINESSULES_FINDREPLACE_DIALOG_REPLACE_BUTTON_MNEMONIC").charAt(0));
        jButtonReplace.setText(CMMessages.getString("BUSINESSULES_FINDREPLACE_DIALOG_REPLACE_BUTTON")); //$NON-NLS-1$
        jButtonFindAndReplace.setMnemonic(CMMessages.getString("BUSINESSULES_FINDREPLACE_DIALOG_FINDREPLACE_MNEMONIC").charAt(0));
        jButtonFindAndReplace.setText(CMMessages.getString("BUSINESSULES_FINDREPLACE_DIALOG_FINDREPLACE_BUTTON")); //$NON-NLS-1$
        jButtonReplaceAll.setMnemonic(CMMessages.getString("BUSINESSULES_FINDREPLACE_DIALOG_REPLACEALL_MNEMONIC").charAt(0));
        jButtonReplaceAll.setText(CMMessages.getString("BUSINESSULES_FINDREPLACE_DIALOG_REPLACEALL_BUTTON")); //$NON-NLS-1$
        jLabelFound.setText("");
        add(jComboBoxFind, new GridBagConstraints(1, 0, 2, 1, 1.0D, 0.0D, 10, 2, new Insets(5, 0, 0, 7), 59, 0));
        add(jLabelFind, new GridBagConstraints(0, 0, 1, 1, 0.0D, 0.0D, 17, 0, new Insets(5, 10, 0, 54), 0, 0));
        add(jComboBoxReplaceWith, new GridBagConstraints(1, 1, 2, 1, 1.0D, 0.0D, 10, 2, new Insets(5, 0, 0, 7), 59, 0));
        add(jLabelReplaceWith, new GridBagConstraints(0, 1, 1, 1, 0.0D, 0.0D, 17, 0, new Insets(5, 10, 0, 0), 0, 0));
        
        add(jPanelDirection, new GridBagConstraints(0, 2, 2, 1, 1.0D, 1.0D, 17, 1, new Insets(1, 1, 0, 1), 90, 5));
        jPanelDirection.add(jRadioButtonForward, new GridBagConstraints(0, 0, 1, 1, 0.0D, 0.0D, 17, 0, new Insets(0, 0, 0, 0), 0, 0));
        jPanelDirection.add(jRadioButtonBackward, new GridBagConstraints(0, 1, 1, 1, 0.0D, 0.0D, 17, 0, new Insets(0, 0, 0, 0), 0, 0));
        add(jPanelScope, new GridBagConstraints(2, 2, 1, 1, 1.0D, 1.0D, 13, 1, new Insets(1, 1, 1, 1), 14, 5));
        jPanelScope.add(jRadioButtonAll, new GridBagConstraints(0, 0, 1, 1, 0.0D, 0.0D, 17, 0, new Insets(0, 0, 0, 0), 0, 0));
        jPanelScope.add(jRadioButtonSelectedlines, new GridBagConstraints(0, 1, 1, 1, 0.0D, 0.0D, 17, 0, new Insets(0, 0, 0, 0), 0, 0));
        add(jPanelOptions, new GridBagConstraints(0, 3, 3, 1, 1.0D, 1.0D, 10, 1, new Insets(1, 1, 1, 1), 11, 8));
        jPanelOptions.add(jCheckBoxCaseSensitive, new GridBagConstraints(0, 0, 1, 1, 0.0D, 0.0D, 17, 0, new Insets(0, 0, 0, 0), 0, 0));
        jPanelOptions.add(jCheckBoxWholeWord, new GridBagConstraints(0, 1, 1, 1, 0.0D, 0.0D, 17, 0, new Insets(0, 0, 0, 0), 0, 0));
        jPanelOptions.add(jCheckBoxRegularExpresion, new GridBagConstraints(0, 2, 1, 1, 0.0D, 0.0D, 10, 0, new Insets(0, 0, 0, 0), 0, 0));
        jPanelOptions.add(jCheckBoxWrapSearch, new GridBagConstraints(1, 0, 1, 1, 0.0D, 0.0D, 17, 0, new Insets(0, 0, 0, 0), 0, 0));
        jPanelOptions.add(jCheckboxIncremental, new GridBagConstraints(1, 1, 1, 1, 0.0D, 0.0D, 17, 0, new Insets(0, 0, 0, 0), 0, 0));
        add(jPanelButtons, new GridBagConstraints(0, 4, 3, 1, 0.0D, 0.0D, 15, 2, new Insets(5, 20, 5, 20), 0, 0));
        jPanelButtons.setLayout(new GridLayout(2, 2, 2, 2));
        jPanelButtons.add(jButtonFind);
        jPanelButtons.add(jButtonFindAndReplace);
        jPanelButtons.add(jButtonReplace);
        jPanelButtons.add(jButtonReplaceAll);
        add(jLabelFound, new GridBagConstraints(0, 5, 3, 1, 0.0D, 0.0D, 10, 2, new Insets(0, 0, 0, 0), 2, 0));
        jButtonFind.setEnabled(false);
        jButtonReplace.setEnabled(false);
        jButtonReplaceAll.setEnabled(false);
        jButtonFindAndReplace.setEnabled(false);
        ((JTextComponent)jComboBoxFind.getEditor().getEditorComponent()).setDocument(m_findDocument);
        ((JTextComponent)jComboBoxReplaceWith.getEditor().getEditorComponent()).setDocument(m_ReplaceDocument);
        ButtonGroup buttongroup;
        (buttongroup = new ButtonGroup()).add(jRadioButtonForward);
        buttongroup.add(jRadioButtonBackward);
        ButtonGroup buttongroup1;
        (buttongroup1 = new ButtonGroup()).add(jRadioButtonAll);
        buttongroup1.add(jRadioButtonSelectedlines);
    }

    public void addNotify()
    {
        super.addNotify();
        jButtonReplace.addActionListener(m_CMCustomActionListener);
        jButtonReplaceAll.addActionListener(m_CMCustomActionListener);
        jButtonFindAndReplace.addActionListener(m_CMCustomActionListener);
        jRadioButtonForward.addActionListener(m_CMCustomActionListener);
        jRadioButtonBackward.addActionListener(m_CMCustomActionListener);
        jRadioButtonAll.addActionListener(m_CMCustomActionListener);
        jRadioButtonSelectedlines.addActionListener(m_CMCustomActionListener);
        jCheckboxIncremental.addActionListener(m_CMCustomActionListener);
        jCheckBoxRegularExpresion.addActionListener(m_CMCustomActionListener);
        if(getStringtoFind().length > 0)
            jButtonFind.requestFocus();
        else
            jComboBoxFind.getEditor().getEditorComponent().requestFocus();
        reInitialize();
        Window window;
        if((window = SwingUtilities.getWindowAncestor(this)) != null && (window instanceof JWindow))
            ((JWindow)window).getRootPane().setDefaultButton(jButtonFind);
    }

    public void removeNotify()
    {
        super.removeNotify();
        jButtonReplace.removeActionListener(m_CMCustomActionListener);
        jButtonReplaceAll.removeActionListener(m_CMCustomActionListener);
        jButtonFindAndReplace.removeActionListener(m_CMCustomActionListener);
        jRadioButtonForward.removeActionListener(m_CMCustomActionListener);
        jRadioButtonBackward.removeActionListener(m_CMCustomActionListener);
        jRadioButtonAll.removeActionListener(m_CMCustomActionListener);
        jRadioButtonSelectedlines.removeActionListener(m_CMCustomActionListener);
        jCheckboxIncremental.removeActionListener(m_CMCustomActionListener);
        jCheckBoxRegularExpresion.removeActionListener(m_CMCustomActionListener);
    }

    private void initializeMatcher()
    {
        if(m_Matcher == null)
            m_Matcher = new CMStringMatcher();
        m_Matcher.caseSensitive = jCheckBoxCaseSensitive.isSelected();
        m_Matcher.forwardOrientation = jRadioButtonForward.isSelected();
        m_Matcher.stringToFind = getStringtoFind();
        m_Matcher.regularExpresion = jCheckBoxRegularExpresion.isSelected();
        m_Matcher.allScope = jRadioButtonAll.isSelected();
        m_Matcher.wholeWord = jCheckBoxWholeWord.isSelected();
        m_Matcher.wrapSearch = jCheckBoxWrapSearch.isSelected();
    }

    private void reInitialize()
    {
        initializeMatcher();
        m_Matcher.initializeIndex();
    }

    private char[] getStringtoFind()
    {
        if(jComboBoxFind.getSelectedItem() == null)
            return new char[0];
        else
            return ((JTextComponent)jComboBoxFind.getEditor().getEditorComponent()).getText().toCharArray();
    }

    public boolean find()
    {
        return existNext(-1);
    }

    private boolean existNext(int i1)
    {
        if(i1 == -1)
            initializeMatcher();
        m_Matcher.currentCharToSearchIndex = -1;
        startSearch = jTextComponentToFind.getSelectionStart();
        endSearch = jTextComponentToFind.getSelectionEnd();
        int indexFindWord;
        if((indexFindWord = m_Matcher.indexOfFindWord(jTextComponentToFind)) <= -1)
        {
            jLabelFound.setText(CMMessages.getString("BUSINESSULES_FINDREPLACE_DIALOG_STRING_NOT_fOUND_MESSAGE")); //$NON-NLS-1$
            if(i1 == -1)
                m_Matcher.initializeIndex();
            jButtonReplace.setEnabled(false);
            jButtonFindAndReplace.setEnabled(false);
            return false;
        } else
        {
            m_Matcher.caretPosition = m_Matcher.currentCharIndex;
            jLabelFound.setText(null);
            findRequestFocus();
            jTextComponentToFind.select(indexFindWord, indexFindWord + m_Matcher.lengthOfWord());
            jButtonReplace.setEnabled(replaceMode);
            jButtonFindAndReplace.setEnabled(replaceMode);
            addItemsToFindOrReplace(jComboBoxFind, (String)jComboBoxFind.getSelectedItem());
            return true;
        }
    }

    private void addItemsToFindOrReplace(JComboBox jcombobox, String s1)
    {
        if(s1 == null || "".equals(s1))
            return;
        for(int i1 = 0; i1 < jcombobox.getItemCount(); i1++)
            if(jcombobox.getItemAt(i1).equals(s1))
                return;

        jcombobox.addItem(s1);
    }

    public void replace()
    {
        if(jTextComponentToFind.getSelectionStart() < jTextComponentToFind.getSelectionEnd())
        {
            int i1 = jTextComponentToFind.getSelectedText().length();
            String s1 = jComboBoxReplaceWith.getSelectedItem() != null ? (String)jComboBoxReplaceWith.getSelectedItem() : "";
            int j1 = m_Matcher.caretPosition;
            jTextComponentToFind.replaceSelection(s1);
            addItemsToFindOrReplace(jComboBoxReplaceWith, s1);
            m_Matcher.initializeIndex();
            m_Matcher.caretPosition = j1 + (s1.length() - i1);
            endSearch += s1.length() - i1;
            if(jRadioButtonSelectedlines.isSelected() && startSearch > -1 && endSearch > -1)
            {
                jTextComponentToFind.setSelectionStart(startSearch);
                jTextComponentToFind.setSelectionEnd(endSearch);
            }
            jButtonReplace.setEnabled(false);
            jButtonFindAndReplace.setEnabled(false);
            return;
        } else
        {
            jButtonReplace.setEnabled(false);
            jButtonFindAndReplace.setEnabled(false);
            return;
        }
    }

    public void replaceAll()
    {
        initializeMatcher();
        int i1 = 0;
        m_Matcher.forwardOrientation = true;
        m_Matcher.wrapSearch = false;
        if(jRadioButtonSelectedlines.isSelected())
            m_Matcher.caretPosition = -1;
        else
            m_Matcher.caretPosition = 0;
        for(boolean flag = existNext(1); flag; flag = replace(1))
            i1++;

        if(i1 == 0)
        {
            jLabelFound.setText("String Not found");
        } else
        {
            addItemsToFindOrReplace(jComboBoxReplaceWith, (String)jComboBoxReplaceWith.getSelectedItem());
            jLabelFound.setText(i1 + CMMessages.getString("BUSINESSULES_FINDREPLACE_DIALOG_MATCH_REPLACED_MESSAGE")); //$NON-NLS-1$
        }
        if(i1 > 0 && jRadioButtonSelectedlines.isSelected())
            jRadioButtonAll.setSelected(true);
    }

    public boolean replaceFind()
    {
        return replace(-1);
    }

    private boolean replace(int i1)
    {
        replace();
        if(existNext(i1))
        {
            jButtonFindAndReplace.setEnabled(true);
            return true;
        } else
        {
            return false;
        }
    }

    private void findRequestFocus()
    {
        Window window;
        if((window = SwingUtilities.getWindowAncestor(jTextComponentToFind)) != null)
            window.toFront();
        jTextComponentToFind.requestFocus();
    }

    private void incrementalRequestFocus()
    {
        SwingUtilities.invokeLater(new FindIncrementalRequestFocus(this));
    }

    public void dispose()
    {
        jTextComponentToFind = null;
    }

    static final boolean isReplaceMode(CMFindReplacePanel findreplacepanel)
    {
        return findreplacepanel.replaceMode;
    }

    static final void initializeMatcher(CMFindReplacePanel findreplacepanel)
    {
        findreplacepanel.initializeMatcher();
    }

    static final CMStringMatcher getM_Matcher(CMFindReplacePanel findreplacepanel)
    {
        return findreplacepanel.m_Matcher;
    }

    static final boolean existNext(CMFindReplacePanel findreplacepanel, int i1)
    {
        return findreplacepanel.existNext(i1);
    }

    static final void incrementalRequestFocus(CMFindReplacePanel findreplacepanel)
    {
        findreplacepanel.incrementalRequestFocus();
    }

    JLabel jLabelFind;
    JComboBox jComboBoxFind;
    JLabel jLabelReplaceWith;
    JComboBox jComboBoxReplaceWith;
    JPanel jPanelDirection;
    Border jBorderDirection;
    TitledBorder jTitleBorderDirection;
    JRadioButton jRadioButtonForward;
    JRadioButton jRadioButtonBackward;
    JPanel jPanelScope;
    Border jBorderScope;
    TitledBorder jTitleBorderScope;
    JRadioButton jRadioButtonAll;
    JRadioButton jRadioButtonSelectedlines;
    JPanel jPanelOptions;
    Border jBorderOptions;
    TitledBorder jTitleBorderOptions;
    JCheckBox jCheckBoxCaseSensitive;
    JCheckBox jCheckBoxWholeWord;
    JCheckBox jCheckBoxRegularExpresion;
    JCheckBox jCheckBoxWrapSearch;
    JCheckBox jCheckboxIncremental;
    GridBagLayout globalLayout;
    JPanel jPanelButtons;
    JButton jButtonFind;
    JButton jButtonReplace;
    JButton jButtonFindAndReplace;
    JButton jButtonReplaceAll;
    GridBagLayout buttonPaneLayout;
    JTextComponent jTextComponentToFind;
    private boolean replaceMode;
    private CMCustomActionListener m_CMCustomActionListener;
    GridBagLayout panelDirectionLayout;
    GridBagLayout panelScopeLayout;
    GridBagLayout panelOptionsLayout;
    private CMStringMatcher m_Matcher;
    JLabel jLabelFound;
    int startSearch;
    int endSearch;
    CMFindDocument m_findDocument;
    CMReplaceDocument m_ReplaceDocument;

    

    
}