package bi.view.businessrulesviews;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Vector;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JViewport;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;

import model.BRulesReference;
import model.BusinessRules;
import model.ProjectReference;
import model.Session2;
import model.TestObject;
import model.TestObjectReference;
import bi.controller.BREditorManager;
import bi.view.actions.CMAction;
import bi.view.businessrulesviews.editor.CMBusinessRuleEditor;
import bi.view.businessrulesviews.editor.editorKit.CMSyntaxEditorKit;
import bi.view.lang.CMMessages;
import bi.view.mainframeviews.CMApplication;
import bi.view.mainframeviews.CMFrameView;
import bi.view.utils.CMBaseJComboBox;
import bi.view.utils.CMBaseJPanel;
import bi.view.utils.CMCustomComboBoxUI;
import bi.view.utils.CMFileFilter;

/**
 * @author hcanedo
 * *****************************************************************************
 * Developed by BUSINESS SOFTWARE INNOVATIONS. Copyright (c)2003 Díaz und
 * Hilterscheid Unternehmensberatung. All rights reserved.
 */
@SuppressWarnings("serial")
public class CMBusinessRulesPanelView extends CMBaseJPanel {

	private boolean checkout;
	private JPanel combosBarPane = new JPanel();
	private File currentBRFile;
	private CMBaseJComboBox fileFormatCombo;
	private CMBaseJComboBox fontName;
	private CMBaseJComboBox fontSize;
	private JLabel fontTitle;
	private JLabel fontTitleSize;
	private JScrollPane jScrollPane = new JScrollPane();
	private JLabel labelFileSyntax = new JLabel();
	private Object lastSavedFile;
	private BRulesReference m_BRulesReference = null;
	private CMBusinessRuleEditor m_BusinessRuleEditor;
	private CMFrameView m_Frame = null;
	private ProjectReference m_ProjectReference = null;
	private Session2 m_Session;
	private TestObject m_TestObject;
	private TestObjectReference m_TestObjectReference;
	private boolean readOnly = false;
	private BREditorManager em;

	/**
	 * Creates new form CMBusinessRulesPanelView
	 *
	 * @param view
	 */
	public CMBusinessRulesPanelView(CMFrameView view) {
		m_Frame = view;
		initialize();
	}

	private BREditorManager getBREditorManager(){
		if (em== null)
			em = new BREditorManager();
		return em;
	}
	// Creates new reference for TestObject, Doen's load the file nor resets UI
	// indicators
	public void assignNewBRulesReference() {
		BRulesReference newBRulesReference = new BRulesReference();
		this.m_TestObject.setBRulesReference(newBRulesReference);
		
		m_Frame.getCmApplication().getSessionManager().getM_WorkspaceManager()
				.saveBRulesReference(newBRulesReference, m_TestObjectReference,
						m_ProjectReference);
		this.m_BRulesReference = newBRulesReference;
	}

	private void changeSyntaxColorAccordingToGrammarLanguaje() {
		try {
			Properties properties;
			String colorSyntaxFile = "editor/"
					+ CMSyntaxEditorKit.SYNTAXFILE_NAME;
			try {
				if (isGermanSyntaxSelected()) {
					colorSyntaxFile = "editor/"
							+ CMSyntaxEditorKit.SYNTAXFILE_NAME_GERMAN;
				}

				properties = new Properties();
				InputStream inputstream = this.getClass().getResourceAsStream(
						colorSyntaxFile);
				try {
					properties.load(inputstream);
					CMSyntaxEditorKit editorkit = ((CMSyntaxEditorKit) m_BusinessRuleEditor
							.getJTextPaneEditor().getEditorKit());
					editorkit.setSyntaxColorDescriptor(properties);
					// viewVisibleText();
					m_BusinessRuleEditor.getJTextPaneEditor().requestFocus();
					// int
					// caretPosition=m_BusinessRuleEditor.getJTextPaneEditor().getCaretPosition();
					m_BusinessRuleEditor.getJTextPaneEditor().setCaretPosition(
							0);
					JViewport viewPort = (JViewport) m_BusinessRuleEditor
							.getJTextPaneEditor().getParent();
					viewPort.setViewPosition(new Point(0, 0));
					m_BusinessRuleEditor.getJTextPaneEditor().revalidate();
					m_BusinessRuleEditor.getJTextPaneEditor().repaint();

				} finally {
					inputstream.close();
				}
			} catch (IOException ioexception) {

			}
		} catch (Exception e) {

			e.printStackTrace();
		}
	}


	public void enablesViews() {
		if (m_TestObject.getAccessState().equals(
				BusinessRules.ACCESS_STATE_CHECKED_OUT)
				|| m_TestObject.getAccessState().equals(
						BusinessRules.ACCESS_STATE_CHECKED_OUT_LOCAL)) {
			if (!m_Session.getM_User().equalsIgnoreCase(m_TestObject.getUser())) {
				this.enableViews(false);
				checkout = false;

			} else {
				this.enableViews(true);
				checkout = true;

			}
		} else {
			this.enableViews(false);
			checkout = false;
		}
		validateFileState();
	}

	public void enableViews(boolean value) {
		this.fileFormatCombo.setEnabled(value);
		m_BusinessRuleEditor.enableViews(value);
		this.m_Frame.setMenuBusinessRulesEnabled(value);
		this.fontName.setEnabled(value);
		this.fontSize.setEnabled(value);
		m_Frame.statesMenusCutCopy(value);
		CMAction.FINDREPLACE.setEnabled(value);
		CMAction.BI_BUSINESS_RULES_CLEAR_ALL.setEnabled(value);
		CMAction.BI_BUSINESS_RULES_IMPORT.setEnabled(value);
		CMAction.BI_BUSINESS_RULES_SAVE_AS.setEnabled(value);
		if (!value) {
			m_Frame.statesMenuPaste(value);
		} else {
			getCMBusinessRuleEditor().enablePasteAction();
		}
	}


	public void fileFormatComboActionPerformed() {
			changeSyntaxColorAccordingToGrammarLanguaje();
			m_BusinessRuleEditor.getJTextPaneEditor().grabFocus();
			this.setModified(true);
	}

	protected void fontNameActionPerformed(ActionEvent e) {
		Font currentFont = this.m_BusinessRuleEditor.getjTextPaneFont();
		String name = getFontName().getSelectedItem().toString();
		int style = currentFont.getStyle();
		int size = currentFont.getSize();
		Font newFont = new Font(name, style, size);
		m_BusinessRuleEditor.setjTextPaneFont(newFont);

	}

	protected void fontSizeActionPerformed(ActionEvent e) {
		Font currentFont = m_BusinessRuleEditor.getjTextPaneFont();
		String name = currentFont.getName();
		int style = currentFont.getStyle();
		int size = ((Integer) getFontSize().getSelectedItem()).intValue();
		Font newFont = new Font(name, style, size);
		m_BusinessRuleEditor.setjTextPaneFont(newFont);

	}

	private Vector getAviablesFontNames() {
		Vector fonts = new Vector();
		Font font[] = GraphicsEnvironment.getLocalGraphicsEnvironment()
				.getAllFonts();
		for (int i = 0; i < font.length; i++) {
			fonts.addElement(font[i].getName());
		}
		return fonts;
	}

	public int getCharCountAtLine(int line) {
		String text = m_BusinessRuleEditor.getText();
		int textLength = text.length();

		for (int i = 0; i < textLength; i++) {
			char c = text.charAt(i);
			if (c == 13 || c == 10) {
				line--;
			}
			if (line == 1) {
				return i;
			}
		}
		return -1;

	}

	public CMBusinessRuleEditor getCMBusinessRuleEditor() {
		return getM_BusinessRuleEditor();
	}

	private Component getCombosBarPane() {

		combosBarPane.setLayout(new BoxLayout(combosBarPane, BoxLayout.X_AXIS));//new javax.swing.BoxLayout(combosBarPane,javax.swing.BoxLayout.X_AXIS));
		combosBarPane.setBorder(javax.swing.BorderFactory.createEtchedBorder());
		combosBarPane.add(getLabelFileSyntax());
		combosBarPane.add(Box.createRigidArea(new Dimension(5,0)));
		combosBarPane.add(getFileFormatCombo());
		combosBarPane.add(Box.createRigidArea(new Dimension(10,0)));
		combosBarPane.add(getFontTitle());
		combosBarPane.add(Box.createRigidArea(new Dimension(5,0)));
		combosBarPane.add(getFontName());
		combosBarPane.add(Box.createRigidArea(new Dimension(10,0)));
		combosBarPane.add(getFontTitleSize());
		combosBarPane.add(Box.createRigidArea(new Dimension(5,0)));
		combosBarPane.add(getFontSize());
		combosBarPane.add(Box.createHorizontalGlue());

		return combosBarPane;
	}

	public CMBaseJComboBox getFileFormatCombo() {
		if(fileFormatCombo== null){
			Vector fileFormats = new Vector();
			fileFormats.add(CMMessages.getString("BI_STRUCTURED_ENGLISH"));// "Business Innovations Structured English");
			fileFormats.add(CMMessages.getString("BI_STRUCTURED_GERMAN"));// "Business Innovations Structured German");
			fileFormatCombo = new CMBaseJComboBox(fileFormats);
			fileFormatCombo.setUI(new CMCustomComboBoxUI());
			fileFormatCombo.setPopupWidth (getPreferredSize().width);
			fileFormatCombo.setMinimumSize(new Dimension(15,25));
			fileFormatCombo.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
				//	fileFormatComboActionPerformed(e);
				}
			});
			fileFormatCombo.addPopupMenuListener(new PopupMenuListener(){
//				 This method is called just before the menu becomes visible
		        public void popupMenuWillBecomeVisible(PopupMenuEvent evt) {

		        }

		        // This method is called just before the menu becomes hidden
		        public void popupMenuWillBecomeInvisible(PopupMenuEvent evt) {
		        	fileFormatComboActionPerformed();
		        }

		        // This method is called when menu is hidden because the user cancelled it
		        public void popupMenuCanceled(PopupMenuEvent evt) {

		        }
			});
		}
		return fileFormatCombo;
	}

	/**
	 * @return Returns the fontName.
	 */
	public CMBaseJComboBox getFontName() {
		if (fontName == null) {
			Vector fontname = getAviablesFontNames();
			fontName = new CMBaseJComboBox(fontname);
			fontName.setUI(new CMCustomComboBoxUI());
			fontName.setPopupWidth (getPreferredSize().width);
			int index = 0;
			if (fontname.contains("Dialog.plain"))
				index = fontname.indexOf("Dialog.plain");
			fontName.setSelectedIndex(index);

			fontName.setMinimumSize(new Dimension(15,25));
			fontName.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					fontNameActionPerformed(e);
				}
			});
		}
		return fontName;
	}

	/**
	 * @return Returns the fontSize.
	 */
	public CMBaseJComboBox getFontSize() {
		if (fontSize == null) {
			int sizes[] = { 5, 6, 7, 8, 9, 10, 11, 12, 14, 16, 18, 20, 22, 24,
					26, 28, 34, 48, 72 };
			Vector size = new Vector();
			for (int i = 0; i < sizes.length; i++) {
				size.addElement(new Integer(sizes[i]));
			}
			fontSize = new CMBaseJComboBox(size);
			fontSize.setUI(new CMCustomComboBoxUI());
			fontSize.setPopupWidth (getPreferredSize().width);
			fontSize.setSelectedIndex(7);
			fontSize.setMinimumSize(new java.awt.Dimension(15, 25));
			//fontSize.setPreferredSize(new java.awt.Dimension(70, 25));
			fontSize.setMaximumSize(new java.awt.Dimension(100, 25));
			fontSize.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					fontSizeActionPerformed(e);
				}
			});
		}
		return fontSize;
	}
	/**
	 * @return Returns the fontTitle.
	 */
	public JLabel getFontTitle() {
		if (fontTitle == null) {
			fontTitle = new JLabel(CMMessages
					.getString("BUSINESS_RULES_FONT_NAME")); //$NON-NLS-1$
		}
		return fontTitle;
	}

	/**
	 * @return Returns the fontTitleSize.
	 */
	public JLabel getFontTitleSize() {
		if (fontTitleSize == null) {
			fontTitleSize = new JLabel(CMMessages
					.getString("BUSINESS_RULES_FONT_SIZE")); //$NON-NLS-1$
		}
		return fontTitleSize;
	}

	private Component getLabelFileSyntax() {
		labelFileSyntax.setText(" "
				+ CMMessages.getString("LABEL_BR_FILE_SYNTAX"));
		labelFileSyntax.setToolTipText("");
		return labelFileSyntax;
	}

	public BRulesReference getM_BRulesReference() {
		return m_BRulesReference;
	}

	/**
	 * @return Returns the m_BusinessRuleEditor.
	 */
	private CMBusinessRuleEditor getM_BusinessRuleEditor() {
		if (m_BusinessRuleEditor == null) {
			m_BusinessRuleEditor = new CMBusinessRuleEditor(m_Frame);
			m_BusinessRuleEditor.getJTextPaneEditor().addKeyListener(
					new KeyAdapter() {
						public void keyTyped(KeyEvent e) {
							jTextAreaKeyTyped(e);
						}
					});
			m_BusinessRuleEditor.getJTextPaneEditor().addCaretListener(
					new CaretListener() {

						public void caretUpdate(CaretEvent e) {
							jTextAreaKeyTyped(null);

						}
					});
			m_BusinessRuleEditor.getJTextPaneEditor().addFocusListener(
					new FocusListener() {

						public void focusGained(FocusEvent e) {
							validateFileState();
							m_BusinessRuleEditor.getJTextPaneEditor().revalidate();
						}

						public void focusLost(FocusEvent e) {
							// validateFileState();
						}
					});
		}
		return m_BusinessRuleEditor;
	}

	public TestObject getM_TestObject() {
		return m_TestObject;
	}


	public TestObjectReference getM_TestObjectReference() {
		return m_TestObjectReference;
	}

	public void importFile(int fileSyntax) {
		File fileToImport = getBREditorManager().selectBusinessRuleFile();
		if (fileToImport != null) {
			m_Frame.setWaitCursor(true);
			String fileContent = getBREditorManager().readContentFromFile(fileToImport);
			StringBuffer absoluteFilePath = new StringBuffer();
			absoluteFilePath.append(m_ProjectReference.getPath());
			absoluteFilePath.append(BusinessRules.URL_SEPARATOR);
			absoluteFilePath.append(BusinessRules.TEST_OBJECTS_FOLDER);
			absoluteFilePath.append(BusinessRules.URL_SEPARATOR);
			absoluteFilePath.append(m_TestObjectReference.getName());
			absoluteFilePath.append(BusinessRules.URL_SEPARATOR);
			absoluteFilePath.append(m_BRulesReference.getFilePath());
			getBREditorManager().saveStringToFile(fileContent, new File(absoluteFilePath.toString()));
			m_BRulesReference.setFileSyntax(fileSyntax);
			loadFileInEditorView(m_TestObjectReference);
			m_Frame.initializeTheUndoManager();
			m_Frame.setWaitCursor(false);// fcastro_20092004
		}
	}

	public String importFile(int fileSyntax, File importFile) {
		File fileToImport = importFile;// m_BREditorManager.selectBusinessRuleFile();
		if (fileToImport != null) {
			m_Frame.setWaitCursor(true);// fcastro_20092004
			String fileContent = getBREditorManager()
					.readContentFromFile(fileToImport);
			// grueda29082004_begin
			StringBuffer absoluteFilePath = new StringBuffer();
			absoluteFilePath.append(m_ProjectReference.getPath());
			absoluteFilePath.append(BusinessRules.URL_SEPARATOR);
			absoluteFilePath.append(BusinessRules.TEST_OBJECTS_FOLDER);
			absoluteFilePath.append(BusinessRules.URL_SEPARATOR);
			absoluteFilePath.append(m_TestObjectReference.getName());
			absoluteFilePath.append(BusinessRules.URL_SEPARATOR);
			absoluteFilePath.append(m_BRulesReference.getFilePath());
			getBREditorManager().saveStringToFile(fileContent, new File(
					absoluteFilePath.toString()));
			m_BRulesReference.setFileSyntax(fileSyntax);
			m_Frame.initializeTheUndoManager();
			m_Frame.setWaitCursor(false);// fcastro_20092004
			return fileContent;
		}
		return null;
	}

	public void importFileButtonActionPerformed(ActionEvent e) {
		int confirmation = JOptionPane.showConfirmDialog(this, CMMessages
				.getString("QUESTION_BEFORE_IMPORT_BUSINESS_RULE_FILE"),
				CMMessages.getString("LABEL_SAVE_BUSINESS_RULES_FILE"),
				JOptionPane.YES_NO_CANCEL_OPTION);
		if (confirmation == JOptionPane.YES_OPTION) {
			saveAsFile();
			importFile(0);

		} else if (confirmation == JOptionPane.NO_OPTION) {
			importFile(0);
		} else if (confirmation == JOptionPane.CANCEL_OPTION) {
			// m_BusinessRuleEditor.getJTextPaneEditor().updateSelectedLines();

		}
	}
	/** This method is called from within the constructor to initialize the form. */
	private void initialize() {
		setLayout(new java.awt.BorderLayout());
		add(getCombosBarPane(), java.awt.BorderLayout.NORTH);
		add(getM_BusinessRuleEditor(), java.awt.BorderLayout.CENTER);
	}



	public boolean isGermanSyntaxSelected() {
		int selected = this.fileFormatCombo.getSelectedIndex();
		switch (selected) {
		case 0:
			return false;
		case 1:
			return true;

		default:
			return false;
		}
	}

	public boolean isModified() {

		return this.getBREditorManager().isTextModified();
	}

	public boolean isReadOnly() {
		return readOnly;
	}

	public void jTextAreaKeyTyped(KeyEvent e) {
		if (!readOnly) {
			if (!this.m_BusinessRuleEditor.getJTextPaneEditor().getText()
					.equals(lastSavedFile)) {
				this.setModified(true);
				m_BusinessRuleEditor.getJTextPaneEditor().removeHighlights();
			} else
				this.setModified(false);
		}

	}

	public void jumpToLine(int line) {
		if (line > 0) {
			int position = getCharCountAtLine(line);
			if (position < m_BusinessRuleEditor.getText().length()) {
				m_BusinessRuleEditor.getJTextPaneEditor().requestFocus();
				m_BusinessRuleEditor.getJTextPaneEditor().setCaretPosition(
						position + 1);

				m_BusinessRuleEditor.highlightLine(line);
			}
		}
	}

	public void loadFileInEditorView(TestObjectReference p_TestObjectReference) {
		StringBuffer absoluteFilePath = new StringBuffer();
		absoluteFilePath.append(m_ProjectReference.getPath());
		absoluteFilePath.append(BusinessRules.URL_SEPARATOR);
		absoluteFilePath.append(BusinessRules.TEST_OBJECTS_FOLDER);
		absoluteFilePath.append(BusinessRules.URL_SEPARATOR);
		absoluteFilePath.append(p_TestObjectReference.getName());
		absoluteFilePath.append(BusinessRules.URL_SEPARATOR);
		absoluteFilePath.append(m_BRulesReference.getFilePath());
		File brFile = new File(absoluteFilePath.toString());
		if (!brFile.canRead()) {
			JOptionPane.showMessageDialog(this, CMMessages
					.getString("LABEL_MESSAGE_FILE_ERROR"), CMMessages
					.getString("TITLE_FILE_ERROR"), JOptionPane.ERROR_MESSAGE);
			this.assignNewBRulesReference();
			absoluteFilePath.append(m_ProjectReference.getPath());
			absoluteFilePath.append(BusinessRules.URL_SEPARATOR);
			absoluteFilePath.append(BusinessRules.TEST_OBJECTS_FOLDER);
			absoluteFilePath.append(BusinessRules.URL_SEPARATOR);
			absoluteFilePath.append(p_TestObjectReference.getName());
			absoluteFilePath.append(BusinessRules.URL_SEPARATOR);
			absoluteFilePath.append(m_BRulesReference.getFilePath());

			brFile = new File(absoluteFilePath.toString());
		}
		String fileContent = this.getBREditorManager().readContentFromFile(brFile);
		// HCanedo_24032006_begin
		m_BusinessRuleEditor.setText(fileContent);
		lastSavedFile = fileContent;
		// HCanedo_24032006_end
		this.selectFileSyntaxCombo(this.m_BRulesReference.getFileSyntax());
		changeSyntaxColorAccordingToGrammarLanguaje();
		this.resetStatusIndicators();
		currentBRFile = brFile;
		readOnlyStateValidation(brFile);
	}

	public void readOnlyStateValidation(File p_BRFile) {
		if (p_BRFile != null) {

			if (!checkout || !p_BRFile.canWrite()) {
				m_BusinessRuleEditor.getJTextPaneEditor().setEditable(false);
				CMApplication.frame.getStatusBar().getModifiedLabel().setText(" "
						+ CMMessages.getString("LABEL_FILE_READ_ONLY"));
				CMAction.BI_BUSINESS_RULES_CLEAR_ALL.setEnabled(false);
			} else {
				m_BusinessRuleEditor.getJTextPaneEditor().setEditable(true);
				jTextAreaKeyTyped(null);
				m_BusinessRuleEditor.getJTextPaneEditor().revalidate();
				CMAction.BI_BUSINESS_RULES_CLEAR_ALL.setEnabled(true);
			}
			readOnly = (!p_BRFile.canWrite());
		}
	}

	public void repaint() {
		super.repaint();
		validateFileState();
	}

	public void resetStatusIndicators() {
		this.setModified(false);
		this.setChecked(false);
	}


	public void saveAsFile() {
		JFileChooser fileChooser;
		File selectedFile;
		fileChooser = new JFileChooser();
		fileChooser.addChoosableFileFilter(CMFileFilter.TXT.getFilter());
		fileChooser.setAcceptAllFileFilterUsed(false);
		fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		int returnVal = fileChooser.showSaveDialog(this);
		if (returnVal == 0) {
			selectedFile = fileChooser.getSelectedFile();
			if (!selectedFile.getName().endsWith(CMFileFilter.TXT.getExtension())) {
				selectedFile = new File(selectedFile.getAbsolutePath()
						+ "." + CMFileFilter.TXT);//ccastedo adds "." 10.07.07
			}
			if (selectedFile != null) {
				m_Frame.setWaitCursor(true);// fcastro_20092004
				this.getBREditorManager().saveStringToFile(m_BusinessRuleEditor
						.getText(), selectedFile);
				// /////////////////////////////
				m_Frame.setWaitCursor(false);// fcastro_20092004
				saveFile();
			}
		}

	}

	public void saveAsFile(File visioFile, String content) {

		File selectedFile = visioFile;
		if (!selectedFile.getName().endsWith(CMFileFilter.TXT.getExtension())) {
			selectedFile = new File(selectedFile.getAbsolutePath()
					+ CMFileFilter.TXT);
		}
		if (selectedFile != null) {
			m_Frame.setWaitCursor(true);// fcastro_20092004
			getBREditorManager().saveStringToFile(content, selectedFile);
			// /////////////////////////////
			m_Frame.setWaitCursor(false);// fcastro_20092004
			saveFile();
		}
	}

	public void saveFile() {
		// grueda29082004_begin
		m_Frame.setWaitCursor(true);// fcastro_20092004
		StringBuffer absoluteFilePath = new StringBuffer();
		absoluteFilePath.append(m_ProjectReference.getPath());
		absoluteFilePath.append(BusinessRules.URL_SEPARATOR);
		absoluteFilePath.append(BusinessRules.TEST_OBJECTS_FOLDER);
		absoluteFilePath.append(BusinessRules.URL_SEPARATOR);
		absoluteFilePath.append(m_TestObjectReference.getName());
		absoluteFilePath.append(BusinessRules.URL_SEPARATOR);
		absoluteFilePath.append(m_BRulesReference.getFilePath());
		getBREditorManager().saveStringToFile(m_BusinessRuleEditor.getText(),
				new File(absoluteFilePath.toString()));
		this.m_BRulesReference.setFileSyntax(this.fileFormatCombo
				.getSelectedIndex());
		m_Frame.setWaitCursor(false);// fcastro_20092004
		lastSavedFile = m_BusinessRuleEditor.getText();
		setModified(false);

	}

	// Saves file on hard drive and information to BRulesReference
	public void saveFileButtonActionPerformed(ActionEvent e) {
		this.saveFile();
	}

	public void selectFileSyntaxCombo(int i) {
		if (i >= 0) {
			this.fileFormatCombo.setSelectedIndex(i);
		} else {
			this.fileFormatCombo.setSelectedIndex(0);
		}
	}

	public void setChecked(boolean checked) {
		if (checked) {
			CMApplication.frame.getStatusBar().getCheckedLabel().setText(" "
					+ CMMessages.getString("LABEL_CHECK_SUCCESSFUL"));
			getBREditorManager().setChecked(true);
			lastSavedFile = m_BusinessRuleEditor.getText();
			this.m_Frame.setMenuItemGenerateTestCases(true);
		} else {
			CMApplication.frame.getStatusBar().getCheckedLabel().setText("");
			getBREditorManager().setChecked(false);
			// lastSavedFile=m_BusinessRuleEditor.getText();
			this.m_Frame.setMenuItemGenerateTestCases(false);

		}
	}

	/**
	 * @param fontName
	 *            The fontName to set.
	 */
	public void setFontName(CMBaseJComboBox fontName) {
		this.fontName = fontName;
	}

	/**
	 * @param fontSize
	 *            The fontSize to set.
	 */
	public void setFontSize(CMBaseJComboBox fontSize) {
		this.fontSize = fontSize;
	}

	/**
	 * @param fontTitle
	 *            The fontTitle to set.
	 */
	public void setFontTitle(JLabel fontTitle) {
		this.fontTitle = fontTitle;
	}

	/**
	 * @param fontTitleSize
	 *            The fontTitleSize to set.
	 */
	public void setFontTitleSize(JLabel fontTitleSize) {
		this.fontTitleSize = fontTitleSize;
	}


	public void setM_BRulesReference(BRulesReference m_BRulesReference) {
		this.m_BRulesReference = m_BRulesReference;
	}

	public boolean setM_TestObject(TestObject p_TestObject,
			TestObjectReference p_TestObjectReference, Session2 p_Session,
			CMFrameView p_Frame, ProjectReference p_ProjectReference) {
		// grueda22082004_end
		boolean error = false;
		m_Session = p_Session;
		m_TestObject = p_TestObject;
		m_TestObjectReference = p_TestObjectReference;
		// grueda22082004_begin
		m_ProjectReference = p_ProjectReference;
		// grueda22082004_end
		this.m_Frame = p_Frame;
		// setting BR Reference to TestObjects created on previous versions
		BRulesReference p_BRulesReference = p_TestObject.getBRulesReference();
		if (p_BRulesReference == null) {
			BRulesReference newBRulesReference =new BRulesReference();

			p_TestObject.setBRulesReference(newBRulesReference);
		
			p_Frame.getCmApplication().getSessionManager()
					.getM_WorkspaceManager().saveBRulesReference(
							newBRulesReference, p_TestObjectReference,
							m_ProjectReference);
			// grueda22082004_end
			this.m_BRulesReference = newBRulesReference;
		} else {
			StringBuffer absoluteFileLocation = new StringBuffer();
			absoluteFileLocation.append(p_ProjectReference.getPath());
			absoluteFileLocation.append(BusinessRules.URL_SEPARATOR);
			absoluteFileLocation.append(BusinessRules.TEST_OBJECTS_FOLDER);
			absoluteFileLocation.append(BusinessRules.URL_SEPARATOR);
			absoluteFileLocation.append(p_TestObject.getName());
			absoluteFileLocation.append(BusinessRules.URL_SEPARATOR);
			absoluteFileLocation.append(p_TestObject.getBRulesReference()
					.getFilePath());

			File referencedFile = new File(absoluteFileLocation.toString());
			// grued22082004_end
			if (referencedFile.canRead()) {
				this.m_BRulesReference = p_TestObject.getBRulesReference();
			} else {
				error = true;
				BRulesReference newBRulesReference = new BRulesReference();
				p_TestObject.setBRulesReference(newBRulesReference);
			
				p_Frame.getCmApplication().getSessionManager()
						.getM_WorkspaceManager().saveBRulesReference(
								newBRulesReference, p_TestObjectReference,
								m_ProjectReference);
				// grued22082004_end
				this.m_BRulesReference = newBRulesReference;
			}

		}

		loadFileInEditorView(p_TestObjectReference);
		return error;
	}


	public void setModified(boolean modified) {
		if (modified) {
			int saveUnSave = m_Frame.getCmApplication().getSessionManager()
					.getM_Save_UnsaveVariable() + 1;
			m_Frame.getCmApplication().getSessionManager()
					.setM_Save_UnsaveVariable(saveUnSave);
			CMApplication.frame.getStatusBar().getModifiedLabel().setText(" "
					+ CMMessages.getString("LABEL_FILE_MODIFIED"));
			getBREditorManager().setTextModified(true);
			this.setChecked(false);
		} else {
			CMApplication.frame.getStatusBar().getModifiedLabel().setText("");
			getBREditorManager().setTextModified(false);
			m_Frame.getCmApplication().getSessionManager()
					.setM_Save_UnsaveVariable(0);

		}
	}

	public void setReadOnly(boolean readOnly) {
		this.readOnly = readOnly;
	}

	public void setVisible(boolean visible) {
		super.setVisible(visible);
		validateFileState();
	}

	protected void validateFileState() {
		readOnlyStateValidation(currentBRFile);
	}

	@Override
	public List getOrder() {
		List focusOrder=new ArrayList();
		focusOrder.add(getFileFormatCombo());
		focusOrder.add(getFontName());
		focusOrder.add(getFontSize());
		focusOrder.add(getCMBusinessRuleEditor().getJTextPaneEditor());
		focusOrder.add(m_Frame.getTreeWorkspaceView());
		return focusOrder;
	}


}
