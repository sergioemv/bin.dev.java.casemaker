package bi.view.testdataviews;
import bi.view.mainframeviews.CMFrameView;

/**
 ****************************************************************************** Developed by BUSINESS SOFTWARE INNOVATIONS.
 * Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved.
 */
public class CMCutCopyPasteStructures {
protected CMCutCopyPasteStructures(){}

    public static CMCutCopyPasteStructures getInstance(){
            if (instance == null) {
                instance = new CMCutCopyPasteStructures();
            }
            return instance;
        }
        public Object getStructureCutOrCopy(){ return structureCutOrCopy; }

	    public void setStructureCutOrCopy(Object structureCutOrCopy){
            this.structureCutOrCopy = structureCutOrCopy;
            if(structureCutOrCopy== null)
                m_Frame.statesMenuPaste(false);
            else
            {
                m_Frame.statesMenuPaste(true);
            }
        }

	    public CMFrameView getM_Frame(){ return m_Frame; }

	    public void setM_Frame(CMFrameView p_Frame){ this.m_Frame = p_Frame; }

    private Object structureCutOrCopy=null;
    private CMFrameView m_Frame=null;

    /**
     * @link
     * @shapeType PatternLink
     * @pattern Singleton
     * @supplierRole Singleton factory 
     */
    /*# private CMCutCopyPasteStructures _cmCutCopyPasteStructures; */
    private static CMCutCopyPasteStructures instance = null;
}
