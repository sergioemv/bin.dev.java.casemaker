//svonborries_07102005_begin
package bi.view.utils;

import java.awt.event.KeyEvent;

import bi.view.actions.CMAction;
import bi.view.businessrulesviews.editor.CMCustomJTextPane;
import bi.view.causeeffectviews.CMCauseEffectStructureGridView;
import bi.view.cells.CMCellDependencyDescription;
import bi.view.cells.CMCellDependencyName;
import bi.view.cells.CMCellEffectsInCombination;
import bi.view.cells.CMCellEquivalenceClassInCombination;
import bi.view.cells.CMCellEquivalenceClassInStdCombination;
import bi.view.cells.CMCellTDStructureClassState;
import bi.view.cells.CMCellTDStructureFormula;
import bi.view.cells.CMCellTDStructureObjectTypes;
import bi.view.cells.CMCellTDStructureValue;
import bi.view.cells.headers.CMCellHeaderCombination;
import bi.view.cells.headers.CMCellHeaderDependencyName;
import bi.view.cells.headers.CMCellHeaderEffectsInCombination;
import bi.view.cells.headers.CMCellHeaderStdCombination;
import bi.view.dependencycombinationviews.CMCombinationViews;
import bi.view.dependencycombinationviews.CMDependencyElementViews;
import bi.view.dependencycombinationviews.CMGridDependencies;
import bi.view.errorviews.CMErrorGridView;
import bi.view.mainframeviews.CMFrameView;
import bi.view.stdcombinationviews.CMStdCombinationViews;
import bi.view.testdataviews.CMGridOutputs;
import bi.view.testdataviews.CMGridTDStructure;
import bi.view.testdataviews.CMPanelTestDataView;
import bi.view.testdataviews.CMStructureView;
import bi.view.testdataviews.CMTestDataCombinationView;
import bi.view.testdataviews.CMTestDataSetView;

public class CMPopupMenuKeyIntercepter {

	private CMFrameView m_FrameView = null;
	private CMStructureView m_StructureView = null;
	private CMGridTDStructure m_TDStructure = null;
	private CMTestDataCombinationView m_TDCombinationV = null;
	private CMTestDataSetView m_TestDataSetView = null;
	private CMGridOutputs m_GridOutputs = null;
	// Ccastedo begin 10-10-05
	private CMCauseEffectStructureGridView m_CMCauseEffectStructureGridView = null;
//	Ccastedo ends 10-10-05
	//Ccastedo begin 11-10-05
	private CMStdCombinationViews m_CMStdCombinationViews = null;
	private CMGridDependencies m_gridDependencies = null;
	private CMCombinationViews m_combinationViews = null;
	private CMDependencyElementViews m_DependenciesElementViews = null;
	private Object editingObject= null;
	//Ccastedo ends 11-10-05
	private CMErrorGridView m_ErrorGridOutputs = null;

	public CMPopupMenuKeyIntercepter(CMFrameView p_FrameView){
		this.m_FrameView = p_FrameView;
	}

	public boolean dispatchKeyEvent(KeyEvent e) {
		if(e.getSource() instanceof CMStructureView){
			this.m_StructureView = (CMStructureView) e.getSource();
			int l_Row = this.m_StructureView.getSelectionModel().getLeadRow();
			int l_Colum = this.m_StructureView.getSelectionModel().getLeadColumn();
			int l_RowPosition = this.m_StructureView.getRowPosition(l_Row);
			int l_ColumPosition = this.m_StructureView.getColumnPosition(l_Colum);
			Object variable = this.m_StructureView.getParent().getParent().getParent().getParent().getParent();
			if(!m_FrameView.isIsPanelResultComparationSelected()){
			if(!(variable instanceof CMPanelTestDataView)){
				this.m_FrameView.getJPopupMenuEditTDStructure().show(m_StructureView,l_ColumPosition,l_RowPosition);
				return true;}
			else{

				this.m_FrameView.getJPopupMenuAssignGlobalTDStructure().show(this.m_StructureView,l_ColumPosition,l_RowPosition);
				return true;

			}
			}
		}
		else if(e.getSource() instanceof CMGridTDStructure){
			this.m_TDStructure = (CMGridTDStructure) e.getSource();
			int l_Row = this.m_TDStructure.getSelectionModel().getLeadRow();
			int l_Colum = this.m_TDStructure.getSelectionModel().getLeadColumn();
			int l_RowPosition = this.m_TDStructure.getRowPosition(l_Row);
			int l_ColumPosition = this.m_TDStructure.getColumnPosition(l_Colum);
			Object obj = this.m_TDStructure.getCellObjectAt(l_Row,l_Colum);
			if(!m_FrameView.isIsPanelResultComparationSelected()){
			if(!m_FrameView.isIsPanelResultComparationSelected() && !m_FrameView.isIsPanelTestDataSelected()){
				//svonborries_27122005_begin
				if (!(obj instanceof CMCellTDStructureFormula) && !(obj instanceof CMCellTDStructureObjectTypes)
					&&!(obj instanceof CMCellTDStructureClassState)){
				//svonborries_27122005_end
						this.m_TDStructure.revalidateMenusAfterAnyAction();
						this.m_TDStructure.enablePopUpMenuItemsInGridTDStructure(obj,l_Row);
						this.m_FrameView.jPopupMenuFields().show(this.m_TDStructure,l_ColumPosition,l_RowPosition);
						return true;
				}
				else{
					if((obj instanceof CMCellTDStructureFormula)){
//svonborries_12102005_begin
						/*m_FrameView.statesMenusFormulas(true);
						m_FrameView.statesMenusVariables(true);*/
						this.m_TDStructure.revalidateMenusAfterAnyAction();
						this.m_TDStructure.enablePopUpMenuItemsInGridTDStructure(obj,l_Row);
/*						m_FrameView.jMenuItemAssignGlobalReference.setVisible(false);
                   	 	m_FrameView.jMenuItemAssignGlobalValue.setVisible(false);
                   	 	m_FrameView.jMenuItemCancelGlobalValueReferenceinStructure2.setVisible(false);*/
						this.m_FrameView.getJPopupMenuTDStructureFormula().show(this.m_TDStructure,l_ColumPosition,l_RowPosition);
						return true;
//svonborries_12102005_end
					}
				}
			}
			else{
				if (obj instanceof CMCellTDStructureValue){
					this.m_TDStructure.revalidateMenusAfterAnyAction();
					this.m_FrameView.getJPopupMenuAssignGlobalValue().show(this.m_TDStructure,l_ColumPosition,l_RowPosition);
					return true;
				}
				if (obj instanceof CMCellTDStructureFormula){
					//this.m_TDStructure.revalidateMenusAfterAnyAction();
					 if (m_FrameView.isIsPanelTestDataSelected()) {
						/* m_FrameView.jMenuItemAssignGlobalReference.setVisible(true);
						 m_FrameView.jMenuItemAssignGlobalValue.setVisible(true);
						 m_FrameView.jMenuItemCancelGlobalValueReferenceinStructure2.setVisible(true);*/
						 this.m_TDStructure.enablePopUpMenuItemsInGridTDStructure(obj,l_Row);
							this.m_FrameView.getJPopupMenuTDStructureFormula().show(this.m_TDStructure,l_ColumPosition,l_RowPosition);
							return true;
                     }
                     else {
                    	 /*m_FrameView.jMenuItemAssignGlobalReference.setVisible(false);
                    	 m_FrameView.jMenuItemAssignGlobalValue.setVisible(false);
                    	 m_FrameView.jMenuItemCancelGlobalValueReferenceinStructure2.setVisible(false);*/
                    	 this.m_TDStructure.enablePopUpMenuItemsInGridTDStructure(obj,l_Row);
     					this.m_FrameView.getJPopupMenuTDStructureFormula().show(this.m_TDStructure,l_ColumPosition,l_RowPosition);
     					return true;
                     }

				}
				/*this.m_TDStructure = (CMGridTDStructure) e.getSource();
				this.m_TDStructure.requestFocus();*/

			}
			}

		}
		else if (e.getSource() instanceof CMTestDataCombinationView){
			this.m_TDCombinationV = (CMTestDataCombinationView) e.getSource();
			int l_Row = this.m_TDCombinationV.getSelectionModel().getLeadRow();
			int l_Colum = this.m_TDCombinationV.getSelectionModel().getLeadColumn();
			int l_RowPosition = this.m_TDCombinationV.getRowPosition(l_Row);
			int l_ColumPosition = this.m_TDCombinationV.getColumnPosition(l_Colum);

			if (m_FrameView.isIsPanelTestDataSelected()){
				this.m_FrameView.getJPopupMenuTestData().show(this.m_TDCombinationV,l_ColumPosition,l_RowPosition);
				return true;
			}
		}
		else if (e.getSource()instanceof CMTestDataSetView){
			this.m_TestDataSetView = (CMTestDataSetView) e.getSource();
			int l_Row = this.m_TestDataSetView.getSelectionModel().getLeadRow();
			int l_Colum = this.m_TestDataSetView.getSelectionModel().getLeadColumn();
			int l_RowPosition = this.m_TestDataSetView.getRowPosition(l_Row);
			int l_ColumPosition = this.m_TestDataSetView.getColumnPosition(l_Colum);
			if(!m_FrameView.isIsPanelResultComparationSelected()){
				this.m_FrameView.getJPopupMenuTestDataSet().show(this.m_TestDataSetView,l_ColumPosition,l_RowPosition);
				return true;
			}
		}
		else if (e.getSource() instanceof CMGridOutputs){
			this.m_GridOutputs = (CMGridOutputs) e.getSource();
			int l_Row = this.m_GridOutputs.getSelectionModel().getLeadRow();
			int l_Colum = this.m_GridOutputs.getSelectionModel().getLeadColumn();
			int l_RowPosition = this.m_GridOutputs.getRowPosition(l_Row);
			int l_ColumPosition = this.m_GridOutputs.getColumnPosition(l_Colum);

			this.m_FrameView.getJPopupMenuTestDataSetReports().show(this.m_GridOutputs,l_ColumPosition,l_RowPosition);
			return true;
		}
		//Ccastedo begin 10-10-05
		else if(e.getSource() instanceof CMCauseEffectStructureGridView){
			this.m_CMCauseEffectStructureGridView = (CMCauseEffectStructureGridView) e.getSource();
			int row = m_CMCauseEffectStructureGridView.getSelectionModel().getLeadRow();
    		int column = m_CMCauseEffectStructureGridView.getSelectionModel().getLeadColumn();

    		int r = m_CMCauseEffectStructureGridView.getRowPosition(row);
    		int c = m_CMCauseEffectStructureGridView.getColumnPosition(column);
    		m_FrameView.getJPopupMenuCauseEffects().show(m_CMCauseEffectStructureGridView,c,r);

        	return true;
        }
		else if(e.getSource() instanceof CMDependencyElementViews || e.getSource() instanceof CMCombinationViews
        		|| e.getSource()instanceof CMGridDependencies)
        		{
			showpopupDependenciesCombinations(e);
			return true;
		}
		else if(e.getSource() instanceof CMStdCombinationViews){
			this.m_CMStdCombinationViews = (CMStdCombinationViews) e.getSource();
			int row = m_CMStdCombinationViews.getSelectionModel().getLeadRow();
    		int column = m_CMStdCombinationViews.getSelectionModel().getLeadColumn();

    		int r = m_CMStdCombinationViews.getRowPosition(row);
			int c = m_CMStdCombinationViews.getColumnPosition(column);

        	Object obj = m_CMStdCombinationViews.getCmGridModel().getCellObjectAt(row, column);
            if (obj instanceof CMCellHeaderStdCombination || obj instanceof CMCellEquivalenceClassInStdCombination) {

                this.m_FrameView.getJPopupMenuStdCombinations().show(m_CMStdCombinationViews, c, r);
                return true;
            }

        }
//		Ccastedo ends 11-10-05
		//return false;
		//Ccastedo begins 12-10-05
		else if(e.getSource() instanceof CMErrorGridView){
			this.m_ErrorGridOutputs = (CMErrorGridView) e.getSource();
			int l_Row = this.m_ErrorGridOutputs.getSelectionModel().getLeadRow();
			int l_Colum = this.m_ErrorGridOutputs.getSelectionModel().getLeadColumn();
			int l_RowPosition = this.m_ErrorGridOutputs.getRowPosition(l_Row);
			int l_ColumPosition = this.m_ErrorGridOutputs.getColumnPosition(l_Colum);
			this.m_FrameView.getJPopupMenuErrors().show(this.m_ErrorGridOutputs,l_ColumPosition,l_RowPosition);
			return true;
		}
		else if(e.getSource()instanceof CMCustomJTextPane){
			CMCustomJTextPane pane=(CMCustomJTextPane)e.getSource();
			pane.showPopupMenu();
			return true;
			
		}
//		Ccastedo ends 12-10-05
		return false;
	}



//	Ccastedo begins 11-10-05
	public void showpopupDependenciesCombinations(KeyEvent e){
		int row,column,r = 0,c=0;

	    if (e.getSource()instanceof CMGridDependencies){
	    	this.m_gridDependencies = (CMGridDependencies) e.getSource();
	    	row = m_gridDependencies.getSelectionModel().getLeadRow();
			column = m_gridDependencies.getSelectionModel().getLeadColumn();

			r = m_gridDependencies.getRowPosition(row);
			c = m_gridDependencies.getColumnPosition(column);
			editingObject = m_gridDependencies.getCmGridModel().getCellObjectAt(row,column);
	    }
	    else if(e.getSource()instanceof CMCombinationViews){
	    	this.m_combinationViews = (CMCombinationViews) e.getSource();
	    	row = m_combinationViews.getSelectionModel().getLeadRow();
			column = m_combinationViews.getSelectionModel().getLeadColumn();

			r = m_combinationViews.getRowPosition(row);
			c = m_combinationViews.getColumnPosition(column);
			editingObject = m_combinationViews.getCmGridModel().getCellObjectAt(row,column);
	    }


		if(editingObject instanceof CMCellDependencyName || editingObject instanceof CMCellDependencyDescription
        ){
			CMAction.DEPENDENCY_GENERATE_COMB.setEnabled(true);
		//	m_FrameView.getJPopupMenuItemDependencyCreate().setEnabled(true);
			m_FrameView.getPopupMenuDependency().show(m_gridDependencies, c, r);

		}
		else if(editingObject instanceof CMCellEquivalenceClassInCombination)
		{
			/*m_FrameView.getJPopupMenuItemCombinationEdit().setEnabled(true);
			m_FrameView.getJPopupMenuItemCombinationAssignCauseEffects().setEnabled(true);
			m_FrameView.getJPopupMenuItemCombinationAssignCombiToTestCases().setEnabled(true);
			m_FrameView.getJPopupMenuItemCombinationMerge().setEnabled(true);
			m_FrameView.getJPopupMenuItemCombinationSeparateMerge().setEnabled(true);*/
			m_combinationViews.updateCommandos();
			m_FrameView.getJPopupMenuCombinations().show(m_combinationViews, c, r);

		}
		else if( editingObject instanceof CMCellHeaderCombination || editingObject instanceof CMCellHeaderEffectsInCombination || editingObject instanceof CMCellEffectsInCombination)
		{
			/*m_FrameView.getJPopupMenuItemCombinationMerge().setEnabled(false);
			m_FrameView.getJPopupMenuItemCombinationSeparateMerge().setEnabled(false);*/
			m_combinationViews.updateCommandos();
			m_FrameView.getJPopupMenuCombinations().show(m_combinationViews, c, r);

		}
		else if( e.getSource()instanceof CMDependencyElementViews){
			this.m_DependenciesElementViews = (CMDependencyElementViews) e.getSource();
			row =m_DependenciesElementViews.getSelectionModel().getLeadRow();
			column = m_DependenciesElementViews.getSelectionModel().getLeadColumn();

			r = m_DependenciesElementViews.getRowPosition(row);
			c = m_DependenciesElementViews.getColumnPosition(column);
			editingObject = m_DependenciesElementViews.getCmGridModel().getCellObjectAt(row,column);
			if (editingObject instanceof CMCellHeaderDependencyName){
				CMAction.DEPENDENCY_GENERATE_COMB.setEnabled(false);
				CMAction.DEPENDENCY_CREATE.setEnabled(false);
				m_FrameView.getPopupMenuDependency().show(m_DependenciesElementViews, c, r);
			}




		}


	}
}
//svonborries_07102005_end