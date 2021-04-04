package bi.view.businessrulesviews.editor;



import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Properties;

import javax.swing.Action;
import javax.swing.JPanel;
import javax.swing.text.DefaultEditorKit;
import javax.swing.text.JTextComponent;

import bi.view.businessrulesviews.dialogs.CMFindReplaceDialog;
import bi.view.businessrulesviews.editor.editorKit.CMSyntaxEditorKit;
import bi.view.lang.CMMessages;
import bi.view.mainframeviews.CMFrameView;

public class CMBusinessRuleEditor extends JPanel {
	

	private CMLineNumberScrollPane jScrollPaneEditor = null;
	private CMCustomJTextPane jTextPaneEditor = null;
	private HashMap actions;
	private CMFrameView m_Frame;

	public CMBusinessRuleEditor(CMFrameView p_Frame) {
		super();
		m_Frame=p_Frame;
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 */
	private void initialize() {
        this.setLayout(new BorderLayout());
        this.setSize(new java.awt.Dimension(329,133));
        this.add(getJScrollPaneEditor(), java.awt.BorderLayout.CENTER);
        m_Frame.getM_CMUndoMediator().registerDocument(jTextPaneEditor.getStyledDocument(),jTextPaneEditor,m_Frame.getContentTabbedPane(),CMMessages.getString("TAB_LABEL_BUSINESS_RULES"));
	}

	/**
	 * This method initializes jScrollPaneEditor	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */
	private CMLineNumberScrollPane getJScrollPaneEditor() {
		if (jScrollPaneEditor == null) {
			getJTextPaneEditor();
			jScrollPaneEditor = new CMLineNumberScrollPane(getJTextPaneEditor());
			jScrollPaneEditor.setViewportView(getJTextPaneEditor());
			jScrollPaneEditor.setPreferredSize(new java.awt.Dimension(485, 244));
			jScrollPaneEditor.setToolTipText("");
			jScrollPaneEditor.setVerticalScrollBarPolicy(javax.swing.JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
			jScrollPaneEditor.setHorizontalScrollBarPolicy(javax.swing.JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

		}
		return jScrollPaneEditor;
	}

	/**
	 * This method initializes jTextPaneEditor	
	 * 	
	 * @return javax.swing.JTextPane	
	 */
	public CMCustomJTextPane  getJTextPaneEditor() {
		if (jTextPaneEditor == null) {
			jTextPaneEditor = new CMCustomJTextPane();
			createActionTable(jTextPaneEditor);
			jTextPaneEditor.setEditorKit(new CMSyntaxEditorKit());
			try {
				Properties properties;
		        properties = null;
				try
		        {
		            properties = new Properties();
		            InputStream inputstream = this.getClass().getResourceAsStream(CMSyntaxEditorKit.SYNTAXFILE_NAME);
		            try
		            {
		                properties.load(inputstream);
		                ((CMSyntaxEditorKit)jTextPaneEditor.getEditorKit()).setSyntaxColorDescriptor(properties);
		            }
		            finally
		            {
		                inputstream.close();
		            }
		        }
		        catch(IOException ioexception)
		        {
		            
		        }
			} catch (Exception e) {
				
				e.printStackTrace();
			}
		}
		return jTextPaneEditor;
	}

	private void createActionTable(JTextComponent textComponent) {
	    actions = new HashMap();
	    Action[] actionsArray = textComponent.getActions();
	    for (int i = 0; i < actionsArray.length; i++) {
	        Action a = actionsArray[i];
	        actions.put(a.getValue(Action.NAME), a);
	    }
	} 
	private Action getActionByName(String name) {
	    return (Action)(actions.get(name));
	}
	public void enablePasteAction(){
		if(getActionByName(DefaultEditorKit.pasteAction).isEnabled()){
			m_Frame.statesMenuPaste(true);
		}
	}
	public void copy(ActionEvent p_e){	
		if(jTextPaneEditor.getSelectedText()!= null){
			getActionByName(DefaultEditorKit.copyAction).actionPerformed(p_e);
			//jTextPaneEditor.updateSelectedLines();
			m_Frame.statesMenuPaste(true);
		}
	}
	public void cut(ActionEvent p_e){
		if(jTextPaneEditor.getSelectedText()!= null){
			m_Frame.statesMenuPaste(true);	
			getActionByName(DefaultEditorKit.cutAction).actionPerformed(p_e);
		}
	}
	public void paste(ActionEvent p_e){
		getActionByName(DefaultEditorKit.pasteAction).actionPerformed(p_e);
	}
	public void selectAll(ActionEvent e) {
		getActionByName(DefaultEditorKit.selectAllAction).actionPerformed(e);
	}
	
	public void changeFont(Font p_Font){
		jTextPaneEditor.setFont(p_Font);
	}
	public void enableViews(boolean value){
		jTextPaneEditor.setEnabled(value);
	}

	public void findReplace(ActionEvent e) {
		CMFindReplaceDialog cmd= new CMFindReplaceDialog(this.jTextPaneEditor);
	//	jTextPaneEditor.updateSelectedLines();
		cmd.setVisible(true);
	}

	

	public void clear() {
		jTextPaneEditor.setText("");
		jTextPaneEditor.grabFocus();
		
	}

	public String getText() {
		return jTextPaneEditor.getText();
	}

	public void setText(String fileContent) {
		jTextPaneEditor.setText(fileContent);
		jTextPaneEditor.setCaretPosition(0);
		
	}


	public Font getjTextPaneFont() {
		return jTextPaneEditor.getFont();
	}


	public void setjTextPaneFont(Font font) {
		jTextPaneEditor.setFont(font);
		jScrollPaneEditor.updateAfterChangeFont(font);
	}

	public void updateLinesNumbers() {
		getJScrollPaneEditor().updateLineNumbers();
	}

	public void highlightLine(int line) {
		getJScrollPaneEditor().highlightLine(line);
		
	}

	
}  //  @jve:decl-index=0:visual-constraint="10,10"
