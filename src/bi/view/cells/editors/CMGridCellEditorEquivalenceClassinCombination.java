package bi.view.cells.editors;



import java.awt.Component;

import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JOptionPane;
import javax.swing.event.ChangeEvent;

import model.BusinessRules;
import model.Combination;
import model.Dependency;
import model.EquivalenceClass;
import bi.controller.CombinationManager;
import bi.view.actions.CMAction;
import bi.view.cells.CMCellCombination;
import bi.view.cells.CMCellEquivalenceClassInCombination;
import bi.view.dependencycombinationviews.CMCombinationViews;
import bi.view.dependencycombinationviews.CMDependencyElementViews;
import bi.view.lang.CMMessages;
import bi.view.mainframeviews.CMApplication;
import bi.view.mainframeviews.CMFrameView;

import com.eliad.model.GridContext;
import com.eliad.model.defaults.DefaultGridCellEditor;

public class CMGridCellEditorEquivalenceClassinCombination extends DefaultGridCellEditor {
	private Object m_value;
	private JCheckBox m_checkBox = new JCheckBox();
	private GridContext m_context;
	private Dependency selectedDependency = null;
		public CMGridCellEditorEquivalenceClassinCombination(JCheckBox checkBox) {
		super(checkBox);
		m_checkBox = checkBox;
		//this.addCellEditorListener(listener);
		//this.getComponent().addMouseListener(mouseListener);
	//	((DefaultGridCellEditor)getCellEditorValue()).getComponent().addMouseListener(mouseListener);

	}

	public void eventEditingStopped(ChangeEvent e) {
		Combination selectedCombination = ((CMCellCombination)m_value).getCombination();
		((CMCombinationViews)m_context.getComponent()).setSelectedCombination(selectedCombination);

		EquivalenceClass selectedEquivalenceClass = ((CMCellEquivalenceClassInCombination)m_value).getEquivalenceClass();
		((CMCombinationViews)m_context.getComponent()).setSelectedEquivalenceClass(selectedEquivalenceClass);


		CombinationManager combinationManager = CMApplication.frame.getTreeWorkspaceView().getM_WorkspaceManager().getM_SessionManager().getCombinationManager();

		if( selectedCombination.getOriginType() == null) {
          selectedCombination.setOriginType(Combination.Origin.PERMUTATION);
        }
        if( selectedCombination.getOriginType().equals(Combination.Origin.MANUAL)) {
            if( selectedCombination.contains(selectedEquivalenceClass) ){
//               cancelEquivalenceClassAssignment(selectedEquivalenceClass, selectedCombination);
            	combinationManager.deleteEquivalenceFromCombination(selectedEquivalenceClass, selectedCombination);
            }
            else {
              selectedDependency = ((CMDependencyElementViews)m_context).getCmPanelDependencies().getM_CMCombinationViews().getSelectedDependency();
              ((CMCombinationViews)m_context.getComponent()).setSelectedDependency(selectedDependency);

              if( !combinationManager.isThereAnEquivalenceClassInTheCombinationThatBelongsToTheSameElement(selectedCombination, selectedEquivalenceClass) ) {
                //assignEquivalenceClassToCombination(selectedEquivalenceClass, selectedCombination);
            	  combinationManager.addEquivalenceClassToCombination(selectedEquivalenceClass, selectedCombination);
            	  m_checkBox.setIcon(new ImageIcon(CMFrameView.class.getResource(BusinessRules.EQUIVALENCECLASS_NOT_IN_COMBINATION)));
                if( combinationManager.isCombinationComplete(selectedCombination, selectedDependency) ) {
                  if( combinationManager.isCombinationInDependency(selectedCombination, selectedDependency) ) {
			          JOptionPane.showMessageDialog(CMApplication.frame,CMMessages.getString("INFO_COMBINATION_ALREADY_EXISTS")); //$NON-NLS-1$
                    //  cancelEquivalenceClassAssignment(selectedEquivalenceClass, selectedCombination);
			          combinationManager.deleteEquivalenceFromCombination(selectedEquivalenceClass, selectedCombination);
                  }
                }
              }
            }
        }
        else {
			if( selectedCombination.contains(selectedEquivalenceClass)) {
				CMAction.COMBINATION_UNMERGE.getAction().actionPerformed(null);
		    }
			else {
				CMAction.COMBINATION_MERGE.getAction().actionPerformed(null);
			}
        }
    }

	public Component getComponent(Object value, boolean isSelected, int row, int column, GridContext context) {
		Component c = super.getComponent(value, isSelected, row, column, context);
		setM_context(context);
		m_value = value;
		m_checkBox.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
		return c;
	}

	public GridContext getM_context() {
		return m_context;
	}

	public void setM_context(GridContext m_context) {
		this.m_context = m_context;
	}

}

