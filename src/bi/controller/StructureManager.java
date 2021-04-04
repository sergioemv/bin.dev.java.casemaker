

package  bi.controller;

import java.util.Vector;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.undo.UndoableEdit;

import model.BusinessRules;
import model.Element;
import model.Structure;
import model.edit.CMModelEditFactory;
import bi.view.mainframeviews.CMApplication;
import bi.view.treeviews.nodes.CMTestObjectNode;

public class StructureManager {


	public static final StructureManager INSTANCE = new StructureManager();

    public StructureManager() {
      ids = new Vector(BusinessRules.MAX_NUMBER_OF_IDS);
      for( int i = 0; i < BusinessRules.MAX_NUMBER_OF_IDS; i++) {
        ids.addElement(new Boolean(false));
      }

    }

    public Structure createStructure() {
      Structure s = new Structure();
      s.setId(this.generateId());
      return s;
    }

    private int generateId() {
      Boolean obj = new Boolean(false);
      for(int i = 0; i < BusinessRules.MAX_NUMBER_OF_IDS; i++) {
        obj = (Boolean) ids.elementAt(i);
        if( obj.booleanValue() == false) {
          ids.setElementAt(new Boolean(true), i);
          return i;
        }
      }
      return -1;

    }
    /**
     * @author smoreno
     * Binds an element with an structure
     * @param p_Structure
     * @param p_Element
     */
    public UndoableEdit addElementToStructure(Structure p_Structure, Element p_Element) {
		p_Structure.addElement(p_Element);
		p_Element.setLnkStructure(p_Structure);
		return CMModelEditFactory.INSTANCE.createAddElementModelEdit(p_Structure,p_Element);
	  }

    public static Structure getSelectedStructure()
    {
    	if (CMApplication.frame.getTreeWorkspaceView()==null)
    		return null;

    DefaultMutableTreeNode node = CMApplication.frame.getTreeWorkspaceView().getSelectedNode();
	   DefaultMutableTreeNode parentNode;
	   if( node != null) {
		   if (node instanceof CMTestObjectNode)
			   return ((CMTestObjectNode)node).getTestObject().getStructure();
	     parentNode = (DefaultMutableTreeNode) node.getParent();
	     if( parentNode != null){
	    	 if (parentNode instanceof CMTestObjectNode)
	    		 return ((CMTestObjectNode)parentNode).getTestObject().getStructure();
	         Object nodeInfo = parentNode.getUserObject();
	         if( nodeInfo != null) {
			   if( nodeInfo instanceof CMTestObjectNode) {
			     CMTestObjectNode temp = (CMTestObjectNode) nodeInfo;
				 return temp.getTestObject().getStructure();
			   }
	         }
	       }
	     
	     }

	   return null;
}
public static String getSelectedTestObjectPath() {
	StringBuffer absoluteFilePath = new StringBuffer();
	absoluteFilePath.append(ProjectManager.getSelectedProjectReference().getPath());
	absoluteFilePath.append(BusinessRules.URL_SEPARATOR);
	absoluteFilePath.append(BusinessRules.TEST_OBJECTS_FOLDER);
	absoluteFilePath.append(BusinessRules.URL_SEPARATOR);
	absoluteFilePath.append(StructureManager.getSelectedStructure().getTestObject().getName());
	absoluteFilePath.append(BusinessRules.URL_SEPARATOR);
	return absoluteFilePath.toString();
}
	 private Vector ids = null;
}
