/**
 * This Software has been developed by Business Software Innovations  .
 * Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved.
 */
package bi.view.treeviews;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreeNode;

import model.Session2;
import model.TestObject;
import model.TestObjectReference;
import model.Workspace2;
import bi.controller.WorkspaceManager;
import bi.view.icons.CMIcon;
import bi.view.lang.CMMessages;
import bi.view.treeviews.nodes.CMBusinessRulesNode;
import bi.view.treeviews.nodes.CMProjectNode;
import bi.view.treeviews.nodes.CMResultsComparationNode;
import bi.view.treeviews.nodes.CMTestCasesNode;
import bi.view.treeviews.nodes.CMTestDataNode;
import bi.view.treeviews.nodes.CMTestManagementNode;
import bi.view.treeviews.nodes.CMTestObjectNode;
import bi.view.treeviews.nodes.CMWorkspaceNode;

class CMDefaultNodeRenderer extends DefaultTreeCellRenderer {
    /**
	 *
	 */
	private final CMTreeWorkspaceView m_renderer;
	//grueda09112004_begin
    private ImageIcon m_WorkspacesIcon;
    private ImageIcon m_WorkspaceIcon;
    private ImageIcon m_ProjectIcon;
    private ImageIcon m_TestObjectBusinessRulesIcon;
    private ImageIcon m_TestObjectTestCasesIcon;
    private ImageIcon m_TestObjectTestDataIcon;
    private ImageIcon m_TestObjectResultsComparationIcon;
    private ImageIcon m_TestObjectTestManagementIcon;
    private ImageIcon m_TestObjectIcon;
    private ImageIcon m_TestObjectCheckedOutByAnother;
    private ImageIcon m_TestObjectCheckedOutByTheSameUser;
    private ImageIcon m_TestObjectCheckedOutLocalByAnother;
    private ImageIcon m_TestObjectCheckedOutLocalByTheSameUser;
    private CMTreeWorkspaceView m_CMTreeWorkspaceView;
    private boolean isTargetNode;
    private boolean isTargetNodeLeaf;
	private boolean isLastItem;
	private int	BOTTOM_PAD = 30;
	private static Image dragImage ;
  /* (non-Javadoc)
 * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
 */
@Override
protected void paintComponent(Graphics p_g) {
	// TODO Auto-generated method stub
	super.paintComponent(p_g);
	if (isTargetNode) {
		p_g.setColor(Color.green);
		p_g.drawImage(dragImage,getSize().width-20,0,16,16,null);
        p_g.drawLine (16,15, getSize().width-16,15);
	}

}
    public CMDefaultNodeRenderer(CMTreeWorkspaceView p_view, CMTreeWorkspaceView p_CMTreeWorkspaceView) {

        this.m_renderer = p_view;
       dragImage =CMIcon.UP.getImageIcon().getImage() ;
		m_CMTreeWorkspaceView = p_CMTreeWorkspaceView;
        m_WorkspacesIcon = CMIcon.WORKSPACES.getImageIcon();
        m_WorkspaceIcon = CMIcon.WORKSPACE.getImageIcon();
        m_ProjectIcon = CMIcon.PROJECT.getImageIcon();
        m_TestObjectBusinessRulesIcon = CMIcon.BUSINESS_RULE.getImageIcon();
        m_TestObjectTestCasesIcon = CMIcon.TESTCASE.getImageIcon();
        m_TestObjectTestDataIcon = CMIcon.TESTDATA_STRUCTURE.getImageIcon();
        m_TestObjectResultsComparationIcon = CMIcon.RESULT.getImageIcon();
        m_TestObjectTestManagementIcon = CMIcon.ERROR_MANAGMENT.getImageIcon();
        m_TestObjectIcon = CMIcon.TESTOBJECT.getImageIcon();
        m_TestObjectCheckedOutByAnother = CMIcon.TESTOBJECT_CHECKOUT_ANOTHER.getImageIcon();
        m_TestObjectCheckedOutByTheSameUser = CMIcon.TESTOBJECT_CHECKOUT_SAME.getImageIcon();
        m_TestObjectCheckedOutLocalByAnother = CMIcon.TESTOBJECT_CHECKOUT_ANOTHERLOCAL.getImageIcon();
        m_TestObjectCheckedOutLocalByTheSameUser =CMIcon.TESTOBJECT_CHECKOUT_SAMELOCAL.getImageIcon();
    }

    //grueda09112004_end
    public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded, boolean leaf,
        int row, boolean hasFocus) {




            isTargetNode = ((value == m_renderer.getDropTargetNode())&&value!=null);
            isTargetNodeLeaf = (isTargetNode &&((TreeNode)value).isLeaf());
            boolean showSelected = sel &&
			(m_renderer.getDropTargetNode() == null);
            super.getTreeCellRendererComponent(tree, value, showSelected, expanded, leaf, row, hasFocus);

            if (isWorkspace(value)) {
                setIcon(m_WorkspaceIcon);
                DefaultMutableTreeNode node = (DefaultMutableTreeNode)value;
                Object nodeInfo = node.getUserObject();
                CMWorkspaceNode temp = (CMWorkspaceNode)nodeInfo;
                Workspace2 w = temp.getM_Workspace();
                this.m_renderer.setToolTipText(w.getName());
            }
            else if (isProject(value)) {
                setIcon(m_ProjectIcon);
//                CMProjectNode temp = (CMProjectNode)value;
//                Project2 p = temp.getM_Project();
//                if (p!=null)
//                this.m_renderer.setToolTipText(p.getName());
//                else
//                	this.m_renderer.setToolTipText(temp.toString());
            }
            //grueda09112004_begin
            else if (isTestObject(value)) {
                Session2 session = m_CMTreeWorkspaceView.getM_Session2();
                WorkspaceManager workspaceManager = m_CMTreeWorkspaceView.getM_WorkspaceManager();
                TestObjectReference testObjectReference = m_CMTreeWorkspaceView.deriveTestObjectReference(value);
                //grueda26122004_begin
                TestObjectReference localTestObjectReference = m_CMTreeWorkspaceView.deriveLocalTestObjectReference(value);
                //grueda26122004_end
                TestObject testObject = m_CMTreeWorkspaceView.deriveTestObject(value);
                if (workspaceManager.isTestObjectCheckedOutByAnother(testObject, session)) {
                    setIcon(m_TestObjectCheckedOutByAnother);
                }
                //grueda26122004_begin
                else if (workspaceManager.isTestObjectCheckedOutByTheSameUser(testObject, session) &&
                    !this.m_renderer.m_WorkspaceManager.isTestObjectReferenceCheckOutLocal(testObject)) {
                        setIcon(m_TestObjectCheckedOutByTheSameUser);
                }
                else if (workspaceManager.isTestObjectCheckedOutLocalByAnotherUser(testObject, session)) {
                        setIcon(m_TestObjectCheckedOutLocalByAnother);
                }
                else if (workspaceManager.isTestObjectCheckedOutLocalByTheSameUser( testObject, session)
                    || this.m_renderer.m_WorkspaceManager.isTestObjectReferenceCheckOutLocal( testObject)) {
                        setIcon(m_TestObjectCheckedOutLocalByTheSameUser);
                }
                //grueda26122004_end
                else {
                    setIcon(m_TestObjectIcon);
                }
                DefaultMutableTreeNode node = (DefaultMutableTreeNode)value;
                Object nodeInfo = node.getUserObject();
                CMTestObjectNode temp = (CMTestObjectNode)nodeInfo;
                TestObject t = temp.getTestObject();
                this.m_renderer.setToolTipText(t.getName());
            }
            //grueda09112004_end
            else if (isTestObjectBusinessRules(value)) {
                setIcon(m_TestObjectBusinessRulesIcon);
                DefaultMutableTreeNode node = (DefaultMutableTreeNode)value;
                Object nodeInfo = node.getUserObject();
                CMBusinessRulesNode temp = (CMBusinessRulesNode)nodeInfo;
                TestObject t = temp.getM_TestObject();
                this.m_renderer.setToolTipText(node.toString());
            }
            else if (isTestObjectTestCases(value)) {
                setIcon(m_TestObjectTestCasesIcon);
                DefaultMutableTreeNode node = (DefaultMutableTreeNode)value;
                Object nodeInfo = node.getUserObject();
                CMTestCasesNode temp = (CMTestCasesNode)nodeInfo;
                TestObject t = temp.getM_TestObject();
                this.m_renderer.setToolTipText(node.toString());
            }
            else if (isTestObjectTestData(value)) {
                setIcon(m_TestObjectTestDataIcon);
                DefaultMutableTreeNode node = (DefaultMutableTreeNode)value;
                Object nodeInfo = node.getUserObject();
                CMTestDataNode temp = (CMTestDataNode)nodeInfo;
                TestObject t = temp.getM_TestObject();
                this.m_renderer.setToolTipText(node.toString());
            }
            else if (isTestObjectResultsComparation(value)) {
                setIcon(m_TestObjectResultsComparationIcon);
                DefaultMutableTreeNode node = (DefaultMutableTreeNode)value;
                Object nodeInfo = node.getUserObject();
                CMResultsComparationNode temp = (CMResultsComparationNode)nodeInfo;
                TestObject t = temp.getM_TestObject();
                this.m_renderer.setToolTipText(node.toString());
            }
            else if (isTestObjectTestManagement(value)) {
                setIcon(m_TestObjectTestManagementIcon);
                DefaultMutableTreeNode node = (DefaultMutableTreeNode)value;
                Object nodeInfo = node.getUserObject();
                CMTestManagementNode temp = (CMTestManagementNode)nodeInfo;
                TestObject t = temp.getM_TestObject();
                this.m_renderer.setToolTipText(node.toString());
            }
            else {
                setIcon(m_WorkspacesIcon);
                this.m_renderer.setToolTipText(CMMessages.getString("TOOLTIP_WORKSPACES")); //$NON-NLS-1$
            }
            return this;
    }

    protected boolean isWorkspace(Object value) {
        DefaultMutableTreeNode node = (DefaultMutableTreeNode)value;
        if (node != null) {
            Object nodeInfo = node.getUserObject();
            if (nodeInfo != null) {
                if (nodeInfo instanceof CMWorkspaceNode) {
                    return true;
                }
                else {
                    return false;
                }
            }
            else {
                return false;
            }
        }
        else {
            return false;
        }
    }

    protected boolean isProject(Object value) {
        DefaultMutableTreeNode node = (DefaultMutableTreeNode)value;
        if (node != null) {
                if (node instanceof CMProjectNode) {
                    return true;
                }
                else {
                    return false;
                }
            }
            else {
                return false;
            }

    }

    protected boolean isTestObject(Object value) {
        DefaultMutableTreeNode node = (DefaultMutableTreeNode)value;
        if (node != null) {
            Object nodeInfo = node.getUserObject();
            if (nodeInfo != null) {
                if (nodeInfo instanceof CMTestObjectNode) {
                    return true;
                }
                else {
                    return false;
                }
            }
            else {
                return false;
            }
        }
        else {
            return false;
        }
    }

    protected boolean isTestObjectBusinessRules(Object value) {
        DefaultMutableTreeNode node = (DefaultMutableTreeNode)value;
        if (node != null) {
            Object nodeInfo = node.getUserObject();
            if (nodeInfo != null) {
                if (nodeInfo instanceof CMBusinessRulesNode) {
                    return true;
                }
                else {
                    return false;
                }
            }
            else {
                return false;
            }
        }
        else {
            return false;
        }
    }

    protected boolean isTestObjectTestCases(Object value) {
        DefaultMutableTreeNode node = (DefaultMutableTreeNode)value;
        if (node != null) {
            Object nodeInfo = node.getUserObject();
            if (nodeInfo != null) {
                if (nodeInfo instanceof CMTestCasesNode) {
                    return true;
                }
                else {
                    return false;
                }
            }
            else {
                return false;
            }
        }
        else {
            return false;
        }
    }

    protected boolean isTestObjectTestData(Object value) {
        DefaultMutableTreeNode node = (DefaultMutableTreeNode)value;
        if (node != null) {
            Object nodeInfo = node.getUserObject();
            if (nodeInfo != null) {
                if (nodeInfo instanceof CMTestDataNode) {
                    return true;
                }
                else {
                    return false;
                }
            }
            else {
                return false;
            }
        }
        else {
            return false;
        }
    }

    protected boolean isTestObjectResultsComparation(Object value) {
        DefaultMutableTreeNode node = (DefaultMutableTreeNode)value;
        if (node != null) {
            Object nodeInfo = node.getUserObject();
            if (nodeInfo != null) {
                if (nodeInfo instanceof CMResultsComparationNode) {
                    return true;
                }
                else {
                    return false;
                }
            }
            else {
                return false;
            }
        }
        else {
            return false;
        }
    }

    protected boolean isTestObjectTestManagement(Object value) {
        DefaultMutableTreeNode node = (DefaultMutableTreeNode)value;
        if (node != null) {
            Object nodeInfo = node.getUserObject();
            if (nodeInfo != null) {
                if (nodeInfo instanceof CMTestManagementNode) {
                    return true;
                }
                else {
                    return false;
                }
            }
            else {
                return false;
            }
        }
        else {
            return false;
        }
    }
}