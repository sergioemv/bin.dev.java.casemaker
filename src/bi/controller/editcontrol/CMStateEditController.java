package bi.controller.editcontrol;

import javax.swing.undo.UndoableEdit;

import model.Combination;
import model.EquivalenceClass;
import model.State;
import model.edit.CMModelEditFactory;
import model.util.CMStateBean;
import bi.controller.CombinationManager;
import bi.view.lang.CMMessages;
import bi.view.undomanagementviews.CMCompoundEdit;
import bi.view.undomanagementviews.undoviews.CMComponentAwareEdit;
import bi.view.utils.CMStateView;

public class CMStateEditController extends CMDefaultEditController{

	private CMStateView stateView;
	private CMStateBean stateBean;

	public UndoableEdit applyChanges(){
		getWarningMessages().clear();
		CMCompoundEdit ce = new CMCompoundEdit();
		if ((getStateBean()==null)||(getStateView()==null))
			return ce;
		if (getStateView()!=null && !getStateView().isVisible())
			return ce;
		ce.addEdit(new CMComponentAwareEdit());
		ce.addEdit(changeState());
		ce.addEdit(changeStateinChildren(getStateBean()));
		return ce;
	}

	public void setVisible(boolean visible){
		getStateView().setVisible(visible);
	}
	private UndoableEdit changeState() {
		CMCompoundEdit ce = new CMCompoundEdit();
		if ((getStateBean()==null))
			return ce;
		//check if the new state can apply to the statebean
		boolean apply = true;
		int newState = getStateView().getSelectedState();
		for (CMStateBean stateBean : getStateBean().getParentStates())
			if (State.compare(stateBean.getState(), newState)>0){
				apply = false;
				getWarningMessages().add(CMMessages.getString("STATE_CANNOT_CHANGE")+" "+getStateBean()+" "+CMMessages.getString("RISKLEVEL_TO")+" \""+State.values()[newState].toString()+"\" "+CMMessages.getString("RISKLEVEL_BECAUSE")+" "+stateBean+" "+CMMessages.getString("STATE_HAS_GREATER")+stateBean.getStateName()+")"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
				break;
			}
//		check if the current state of the bean is correct
		  int currentState = getStateBean().getState();
		  String ext = getStateBean().getStateName();
		  CMStateBean parent=null;
		  for (CMStateBean parentStateBean : getStateBean().getParentStates())
		  {
//		    if a combination has different than positive child equivalence classes it must be deleted
		    if ((getStateBean() instanceof Combination)&&(parentStateBean instanceof EquivalenceClass)){
		     if (parentStateBean.getState()!=State.POSITIVE.intValue()){
		    	 //delete the child combinations
		    	 ce.addEdit(propagateStateChangeInCombination(getStateBean(),parentStateBean));
		     }
		    }else {
		     if (State.compare(getStateBean().getState(),parentStateBean.getState())<0){
		    	 ce.addEdit(CMModelEditFactory.INSTANCE.createChangeStateModelEdit(getStateBean(), State.values()[parentStateBean.getState()]));
		    	 getStateBean().setState(State.values()[parentStateBean.getState()]);
		    	 parent = parentStateBean;
		    }
		   }
		    if (currentState!=getStateBean().getState() && parent !=null) {

				   getWarningMessages().add(CMMessages.getString("STATE_CHANGED_TO")+" \""+getStateBean().getStateName()+"\" "+CMMessages.getString("RISKLEVEL_IN")+" "+getStateBean()+" "+CMMessages.getString("RISKLEVEL_BECAUSE")+" "+parent); //$NON-NLS-1$ //$NON-NLS-2$
				  }
		  }

		if((apply)&&(newState!=getStateBean().getState())){
			ce.addEdit(CMModelEditFactory.INSTANCE.createChangeStateModelEdit(getStateBean(), State.values()[newState]));
			getStateBean().setState(newState);
		}

		return ce;
	}

	private UndoableEdit changeStateinChildren(final CMStateBean p_StateBean) {
		CMCompoundEdit ce = new CMCompoundEdit();
		if ((getStateBean()==null))
			return ce;
		for(CMStateBean childStateBean : p_StateBean.getChildStates())
				//if a combination has different than positive child equivalence classes it must be deleted
				if ((childStateBean instanceof Combination)&&(p_StateBean instanceof EquivalenceClass)){
					if (p_StateBean.getState()!=State.POSITIVE.intValue()){
						ce.addEdit(propagateStateChangeInCombination(childStateBean,p_StateBean));
					}
				}else
				{
					if (State.compare(p_StateBean.getState(),childStateBean.getState())>0){
						ce.addEdit(CMModelEditFactory.INSTANCE.createChangeStateModelEdit(childStateBean, State.values()[p_StateBean.getState()]));
						childStateBean.setState(p_StateBean.getState());
						getWarningMessages().add(CMMessages.getString("STATE_CHANGED_TO")+" \""+p_StateBean.getStateName()+"\" "+CMMessages.getString("RISKLEVEL_IN")+" "+childStateBean); //$NON-NLS-1$ //$NON-NLS-2$
						ce.addEdit(changeStateinChildren(childStateBean));
					}
				}
		return ce;
	}

	/**
	 * The combination must be deleted because the state of some child state was deleted
	 * @param p_StateBean
	 * @return
	 */
	private UndoableEdit propagateStateChangeInCombination(CMStateBean target, final CMStateBean p_StateBeanOrigin) {
		CMCompoundEdit ce = new CMCompoundEdit();
		Combination combination = (Combination) target;
		final EquivalenceClass equivalenceClass = (EquivalenceClass) p_StateBeanOrigin;
		//if combination is manual or other it must be deleted
//		if (combination.getOriginType().equalsIgnoreCase(BusinessRules.COMBINATION_ORIGIN_TYPE_MANUAL)||
//				combination.getOriginType().equalsIgnoreCase(BusinessRules.COMBINATION_ORIGIN_TYPE_BY_OTHER)	) {
			getWarningMessages().add(CMMessages.getString("LABEL_COMBINATION")+" "+combination+" "+CMMessages.getString("STATE_WAS_DELETED")); //$NON-NLS-1$ //$NON-NLS-2$
			if (combination.getDependencyRoot() != null){
			ce.addEdit(CMModelEditFactory.INSTANCE.createRemoveEquivalenceClassModelEdit(combination.getDependencyRoot(), equivalenceClass));
			combination.getDependencyRoot().removeEquivalenceClass(equivalenceClass);
			ce.addEdit(CombinationManager.INSTANCE.deleteCombination(combination, combination.getDependencyRoot()));
			}

		return ce;
	}

	public CMStateView getStateView() {
		return stateView;
	}

	public void setStateView(CMStateView stateView) {
		this.stateView = stateView;
		if (getStateBean()!=null)
			this.stateView.setState(getStateBean().getState());
	}

	public CMStateBean getStateBean() {
		return stateBean;
	}

	public void setStateBean(CMStateBean stateBean) {
		this.stateBean = stateBean;
		if (getStateView() != null)
			getStateView().setState(this.stateBean.getState());
	}
}
