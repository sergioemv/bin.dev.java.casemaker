package bi.view.utils;

import java.awt.event.KeyEvent;

import javax.swing.Action;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.KeyStroke;
import javax.swing.text.JTextComponent;
import javax.swing.text.Keymap;

public class CMKeyBindingFind {
	public static class FindResult {
        // Non-null if the keystroke is in an inputmap
        public InputMap inputMap;
    
        // Non-null if the keystroke is in an keymap or default action
        public Keymap keymap;
    
        // Non-null if the keystroke is in a default action
        // The keymap field holds the keymap containing the default action
        public Action defaultAction;
    
        // If true, the keystroke is in the component's inputMap or keymap
        // and not in one of the inputMap's or keymap's parent.
        public boolean isLocal;
    
        public String toString() {
            StringBuffer b = new StringBuffer();
    
            b.append("inputmap="+inputMap+",keymap="+keymap
                     +",defaultAction="+defaultAction+",isLocal="+isLocal);
            return b.toString();
        }
    }
    
    // Returns null if not found
    public static FindResult find(KeyStroke k, JComponent c) {
    	FindResult result;
    
        result = find(k, c.getInputMap(JComponent.WHEN_FOCUSED));
        if (result != null) {
            return result;
        }
        result = find(k, c.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT));
        if (result != null) {
            return result;
        }
        result = find(k, c.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW));
        if (result != null) {
            return result;
        }
    
    
        // Check keymaps
        if (c instanceof JTextComponent) {
            JTextComponent tc = (JTextComponent)c;
            result = new FindResult();
    
            // Check local keymap
            Keymap kmap = tc.getKeymap();
            if (kmap.isLocallyDefined(k)) {
                result.keymap = kmap;
                result.isLocal = true;
                return result;
            }
    
            // Check parent keymaps
            kmap = kmap.getResolveParent();
            while (kmap != null) {
                if (kmap.isLocallyDefined(k)) {
                    result.keymap = kmap;
                    return result;
                }
                kmap = kmap.getResolveParent();
            }
    
            // Look for default action
            if (k.getKeyEventType() == KeyEvent.KEY_TYPED) {
                // Check local keymap
                kmap = tc.getKeymap();
                if (kmap.getDefaultAction() != null) {
                    result.keymap = kmap;
                    result.defaultAction = kmap.getDefaultAction();
                    result.isLocal = true;
                    return result;
                }
    
                // Check parent keymaps
                kmap = kmap.getResolveParent();
                while (kmap != null) {
                    if (kmap.getDefaultAction() != null) {
                        result.keymap = kmap;
                        result.defaultAction = kmap.getDefaultAction();
                        return result;
                    }
                    kmap = kmap.getResolveParent();
                }
            }
        }
        return null;
    }
    
    public static FindResult find(KeyStroke k, InputMap map) {
        // Check local inputmap
        KeyStroke[] keys = map.keys();
        for (int i=0; keys != null && i<keys.length; i++) {
            if (k.equals(keys[i])) {
                FindResult result = new FindResult();
                result.inputMap = map;
                result.isLocal = true;
                return result;
            }
        }
    
        // Check parent inputmap
        map = map.getParent();
        while (map != null) {
            keys = map.keys();
            for (int i=0; keys != null && i<keys.length; i++) {
                if (k.equals(keys[i])) {
                    FindResult result = new FindResult();
                    result.inputMap = map;
                    return result;
                }
            }
            map = map.getParent();
        }
        return null;
    }

}
