/* Developed by BUSINESS SOFTWARE INNOVATIONS.  .. Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved..*/

package  bi.view.cells;

public class CMErrorNameCellView extends Object {
    public CMErrorNameCellView() {
    }

    public CMErrorCellView getM_CMErrorCellView(){
            return m_CMErrorCellView;
        }

    public void setM_CMErrorCellView(CMErrorCellView p_CMErrorCellView){
            m_CMErrorCellView = p_CMErrorCellView;
        }

    /**
     * @directed 
     */
    private CMErrorCellView m_CMErrorCellView;
}
