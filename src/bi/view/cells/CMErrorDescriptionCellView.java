/* Developed by BUSINESS SOFTWARE INNOVATIONS.  .. Copyright (c)2003 D�az und Hilterscheid Unternehmensberatung. All rights reserved..*/

package  bi.view.cells;

public class CMErrorDescriptionCellView extends Object {
    public CMErrorDescriptionCellView() {
    }

    public CMErrorCellView getM_CMErrorCellView(){
            return m_CMErrorCellView;
        }

    public void setM_CMErrorCellView(CMErrorCellView m_CMErrorCellView){
            this.m_CMErrorCellView = m_CMErrorCellView;
        }

    /**
     * @directed 
     */
    private CMErrorCellView m_CMErrorCellView;
}
