package model;

/**
 ****************************************************************************** Developed by BUSINESS SOFTWARE INNOVATIONS.
 * Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved.
 */
public class LocalProjectReference extends ProjectReference {
    public ProjectReference getM_ProjectReference(){
            return m_ProjectReference;
        }

    public void setM_ProjectReference(ProjectReference m_ProjectReference){
            this.m_ProjectReference = m_ProjectReference;
        }

    /**
     * @directed 
     */
    private ProjectReference m_ProjectReference;
    
    //The Local project reference does not have a local project reference
    /* (non-Javadoc)
     * @see model.ProjectReference#getM_LocalProjectReference()
     */
    @Override
    public LocalProjectReference getM_LocalProjectReference() {
    	
    	return null;
    }
    //the Local project reference does not have a local project
    /* (non-Javadoc)
     * @see model.ProjectReference#getLocalProject()
     */
    @Override
    public Project2 getLocalProject() {
    	
    	return null;
    }
}
