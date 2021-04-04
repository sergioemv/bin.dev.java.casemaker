package bi.view.businessrulesviews.editor;

import java.awt.Color;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.util.HashMap;

import javax.swing.Action;
import javax.swing.JComponent;
import javax.swing.JPopupMenu;
import javax.swing.JTextPane;
import javax.swing.KeyStroke;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Highlighter;
import javax.swing.text.JTextComponent;
import javax.swing.text.StyledDocument;

import bi.view.utils.CMKeyBindingFind;
import bi.view.utils.CMKeyBindingFind.FindResult;

@SuppressWarnings("serial")
public class CMCustomJTextPane extends JTextPane {

	private HashMap actions;
	private CMPopupMenuBusinessRuleEditor m_PopupMenu;
	private EditorHighlightPainter m_EditorHighLightPainter;
	public CMCustomJTextPane() {
		super();
		initialize();
	}

	public CMCustomJTextPane(StyledDocument doc) {
		super(doc);
		initialize();
	}

	private void initialize() {
		setText("");
		setCaret(new CMSelectionPreservingCaret());
		setFont(new Font("Dialog",Font.PLAIN,12));
		createActionTable(this);
		m_EditorHighLightPainter= new EditorHighlightPainter(Color.YELLOW);
		this.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent e) {
				mouseClickActionPerformed(e);
			}
		});
		disableKeyBindings("ctrl pressed H",this);
		disableKeyBindings("alt pressed NUMPAD4",this);
	}
	/*public class MyKeyListener extends KeyAdapter {
        public void keyTyped(KeyEvent evt) {
            JTextComponent c = (JTextComponent)evt.getSource();
            char ch = evt.getKeyChar();
    
            if (Character.isLowerCase(ch)) {
                try {
                    c.getDocument().insertString(
                        c.getCaretPosition(), ""+Character.toUpperCase(ch), null);
                    evt.consume();
                } catch (BadLocationException e) {
                }
            }
        }
    }*/
	public static void disableKeyBindings(String keybinding, JComponent comp){
		FindResult r = CMKeyBindingFind.find(KeyStroke.getKeyStroke(keybinding), comp);
		if(r != null){
			if(r.inputMap != null){
				r.inputMap.remove(KeyStroke.getKeyStroke(keybinding));
			}
			if(r.keymap!= null){
				r.keymap.removeKeyStrokeBinding(KeyStroke.getKeyStroke(keybinding));
			}
			if(r.defaultAction!= null){
				r.defaultAction.setEnabled(false);
			}
		}
	}

	protected void mouseClickActionPerformed(MouseEvent e) {
		if (e.getModifiers()== MouseEvent.META_MASK) {
			getM_PopupMenu().show(this,e.getX(),e.getY());
		}
		
	}

	protected JPopupMenu getM_PopupMenu() {
		if (m_PopupMenu== null) {
			m_PopupMenu= new CMPopupMenuBusinessRuleEditor(this);
		}
		return m_PopupMenu;
	}

	@SuppressWarnings("unchecked")
	private void createActionTable(JTextComponent textComponent) {
	    actions = new HashMap();
	    Action[] actionsArray = textComponent.getActions();
	    for (int i = 0; i < actionsArray.length; i++) {
	        Action a = actionsArray[i];
	        actions.put(a.getValue(Action.NAME), a);
	    }
	}
	
	public Action getActionByName(String name) {
	    return (Action)(actions.get(name));
	}

	public void showPopupMenu() {
		Rectangle caretCoords;
		try {
			caretCoords = this.modelToView(this.getCaretPosition());
			getM_PopupMenu().show(this,caretCoords.x,caretCoords.y);
		} catch (BadLocationException e) {
			e.printStackTrace();
		}
		
		
	}

	class EditorHighlightPainter extends DefaultHighlighter.DefaultHighlightPainter {
        public EditorHighlightPainter(Color color) {
            super(color);
        }
    }
//	 Creates highlights around all occurrences of pattern in textComp
    public void highlight(int inioffSet, int endoffSet) {
        // First remove all old highlights
        removeHighlights();
    
        try {
            Highlighter hilite = this.getHighlighter();
            hilite.addHighlight(inioffSet, endoffSet, m_EditorHighLightPainter);
        } catch (BadLocationException e) {
        	e.printStackTrace();
        }
    }
    
    // Removes only our private highlights
    public void removeHighlights() {
        Highlighter hilite = this.getHighlighter();
        Highlighter.Highlight[] hilites = hilite.getHighlights();
    
        for (int i=0; i<hilites.length; i++) {
            if (hilites[i].getPainter() instanceof EditorHighlightPainter) {
                hilite.removeHighlight(hilites[i]);
            }
        }
    }

	
    
	
	}
