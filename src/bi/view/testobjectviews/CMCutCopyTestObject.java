package bi.view.testobjectviews;
import bi.view.mainframeviews.CMFrameView;
import bi.view.treeviews.nodes.CMProjectNode;

import javax.swing.tree.DefaultMutableTreeNode;

/**
 ****************************************************************************** Developed by BUSINESS SOFTWARE INNOVATIONS.
 * Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved.
 */
public class CMCutCopyTestObject {

protected CMCutCopyTestObject(){
	this.isCutTestObject=false;
    this.pathTestObject="";
}

    public static CMCutCopyTestObject getInstance(){
            if (instance == null) {
                instance = new CMCutCopyTestObject();
            }
            return instance;
        }

    public String getpathTestObject(){
            return pathTestObject;
        }

    public void setpathTestObject(String pathTestObject){
            this.pathTestObject = pathTestObject;
        }

    public boolean getisCutTestObject(){
            return isCutTestObject;
        }

    public void setisCutTestObject(boolean isCutTestObject){
            this.isCutTestObject = isCutTestObject;
        }
     public CMFrameView getM_Frame(){
        return m_Frame;
     }

	public void setM_Frame(CMFrameView p_Frame){
            this.m_Frame = p_Frame;
        }
    public boolean existTestObjectToPaste()
    {
        if(!pathTestObject.equals(""))
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    public void setTestObjectNode(DefaultMutableTreeNode testObjectNode)
    {
        this.testObjectNode=testObjectNode;
    }
    public DefaultMutableTreeNode getTestObjectNode()
    {
        return this.testObjectNode;
    }
    /**
     * @link
     * @shapeType PatternLink
     * @pattern Singleton
     * @supplierRole Singleton factory 
     */
	/*# private CMCutCopyTestObject _CMCutCopyTestObject; */
    private static CMCutCopyTestObject instance = null;
    private String pathTestObject;
    private boolean isCutTestObject;
    private CMFrameView m_Frame=null;
    private DefaultMutableTreeNode testObjectNode=null;
    private CMProjectNode m_ProjectInfoNode=null;
	/**
	 * @return Returns the m_ProjectInfoNode.
	 */
	public CMProjectNode getM_ProjectInfoNode() {
		return m_ProjectInfoNode;
	}

	/**
	 * @param projectInfoNode The m_ProjectInfoNode to set.
	 */
	public void setM_ProjectInfoNode(CMProjectNode projectInfoNode) {
		m_ProjectInfoNode = projectInfoNode;
	}
}
