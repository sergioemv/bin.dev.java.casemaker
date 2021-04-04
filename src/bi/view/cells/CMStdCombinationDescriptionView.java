/* Developed by BUSINESS SOFTWARE INNOVATIONS.  .. Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved..*/

package  bi.view.cells;

import model.StdCombination;
import java.util.Vector;

public class CMStdCombinationDescriptionView extends Vector {
    public CMStdCombinationDescriptionView(StdCombination p_StdCombination) {
      super(0);
      this.m_StdCombination = p_StdCombination;
    }

    public CMStdCombinationDescriptionCellView getM_CMStdCombinationDescriptionCellView(){
            return m_CMStdCombinationDescriptionCellView;
        }

    public void setM_CMStdCombinationDescriptionCellView(CMStdCombinationDescriptionCellView m_CMStdCombinationDescriptionCellView){
            this.m_CMStdCombinationDescriptionCellView = m_CMStdCombinationDescriptionCellView;
        }

    public CMStdCombinationNameView getM_CMStdCombinationNameView(){
            return m_CMStdCombinationNameView;
        }

    public void setM_CMStdCombinationNameView(CMStdCombinationNameView m_CMStdCombinationNameView){
            this.m_CMStdCombinationNameView = m_CMStdCombinationNameView;
        }

    public StdCombination getM_StdCombination(){
            return m_StdCombination;
        }

    public void setM_StdCombination(StdCombination m_StdCombination){
            this.m_StdCombination = m_StdCombination;
        }

    /**
     * @link aggregationByValue
     * @directed 
     */
    private CMStdCombinationNameView m_CMStdCombinationNameView;

    /**
     * @link aggregationByValue
     * @directed 
     */
    private CMStdCombinationDescriptionCellView m_CMStdCombinationDescriptionCellView;

    /**
     * @directed 
     */
    private StdCombination m_StdCombination;
}
