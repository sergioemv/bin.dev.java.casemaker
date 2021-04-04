package bi.controller.editcontrol;

import javax.swing.undo.UndoableEdit;

import model.edit.CMModelEditFactory;
import model.util.CMRiskLevelBean;
import bi.view.lang.CMMessages;
import bi.view.undomanagementviews.CMCompoundEdit;
import bi.view.utils.CMRiskLevelView;

public class CMRiskLevelEditController extends CMDefaultEditController{

	private CMRiskLevelView riskLevelView;
	private CMRiskLevelBean riskLevelBean;
	
	
	public void setVisible(boolean visible){
		getRiskLevelView().setVisible(visible);
	}

	public CMRiskLevelView getRiskLevelView() {
		
		return riskLevelView;
	}

	public void setRiskLevelView(CMRiskLevelView riskLevelView) {
		this.riskLevelView = riskLevelView;
		if (getRiskLevelBean()!=null)
			this.riskLevelView.setRisklevel(getRiskLevelBean().getRiskLevel());
	}

	public CMRiskLevelBean getRiskLevelBean() {
		return riskLevelBean;
	}

	public void setRiskLevelBean(CMRiskLevelBean riskLevelBean) {
		this.riskLevelBean = riskLevelBean;
		if (getRiskLevelView()!=null)
			getRiskLevelView().setRisklevel(this.riskLevelBean.getRiskLevel());
	}
	
	public UndoableEdit applyChanges(){
		CMCompoundEdit ce = new CMCompoundEdit();
		if ((getRiskLevelBean()==null)||(getRiskLevelView()==null))
			return ce;
		if (getRiskLevelView()!=null && !getRiskLevelView().isVisible())
			return ce;
		ce.addEdit(changeRiskLevel());
		ce.addEdit(changeRiskLevelinChildren(getRiskLevelBean()));
		return ce;
	}

	private UndoableEdit changeRiskLevel() {
		CMCompoundEdit ce = new CMCompoundEdit();
		if ((getRiskLevelBean()==null))
			return ce;
		boolean apply = true;
		int newRiskLevel = getRiskLevelView().getSelectedRisklevel();
		
		//check if is posible to assign the new risk level
		for (CMRiskLevelBean riskLevelBean : getRiskLevelBean().getParentRiskLevels())
			if (newRiskLevel<riskLevelBean.getRiskLevel()){
				apply = false;
				getWarningMessages().add(CMMessages.getString("RISKLEVEL_CANNOTCHANGE")+" "+getRiskLevelBean()+" "+CMMessages.getString("RISKLEVEL_TO")+" "+newRiskLevel+" "+CMMessages.getString("RISKLEVEL_BECAUSE")+" "+riskLevelBean+" "+CMMessages.getString("RISKLEVEL_HASGREATER")); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
				break;
			}
		if((apply)&&(newRiskLevel!=getRiskLevelBean().getRiskLevel())){
			ce.addEdit(CMModelEditFactory.INSTANCE.createChangeRiskLevelModelEdit(getRiskLevelBean(), newRiskLevel));
			getRiskLevelBean().setRiskLevel(newRiskLevel);	
		}
		//check if the current risk is valid if not change it
		int currentRiskLevel = getRiskLevelBean().getRiskLevel();
		CMRiskLevelBean parent=null;
		for (CMRiskLevelBean riskLevelBean : getRiskLevelBean().getParentRiskLevels())
			if (getRiskLevelBean().getRiskLevel()<riskLevelBean.getRiskLevel()){
				ce.addEdit(CMModelEditFactory.INSTANCE.createChangeRiskLevelModelEdit(getRiskLevelBean(), riskLevelBean.getRiskLevel()));
				getRiskLevelBean().setRiskLevel(riskLevelBean.getRiskLevel());
				parent = riskLevelBean;
			}
		if (currentRiskLevel!=getRiskLevelBean().getRiskLevel() && parent !=null)
			getWarningMessages().add(CMMessages.getString("RISKLEVEL_CHANGED_TO")+" "+getRiskLevelBean().getRiskLevel()+" "+CMMessages.getString("RISKLEVEL_IN")+" "+getRiskLevelBean()+" "+CMMessages.getString("RISKLEVEL_BECAUSE")+" "+parent); //$NON-NLS-1$ //$NON-NLS-2$
		return ce;
	}

	private UndoableEdit changeRiskLevelinChildren(CMRiskLevelBean p_riskLevelBean) {
		CMCompoundEdit ce = new CMCompoundEdit();
		if ((getRiskLevelBean()==null))
			return ce;
		for(CMRiskLevelBean riskLevelBean : p_riskLevelBean.getChildRiskLevels())
			if (riskLevelBean.getRiskLevel()<p_riskLevelBean.getRiskLevel()){
				ce.addEdit(CMModelEditFactory.INSTANCE.createChangeRiskLevelModelEdit(riskLevelBean, p_riskLevelBean.getRiskLevel()));
				riskLevelBean.setRiskLevel(p_riskLevelBean.getRiskLevel());
				getWarningMessages().add(CMMessages.getString("RISKLEVEL_CHANGED_TO")+" "+getRiskLevelBean().getRiskLevel()+" "+CMMessages.getString("RISKLEVEL_IN")+" "+riskLevelBean+" "+CMMessages.getString("RISKLEVEL_BECAUSE")+" "+p_riskLevelBean); //$NON-NLS-1$ //$NON-NLS-2$
				ce.addEdit(changeRiskLevelinChildren(riskLevelBean));
			}
				
		return ce;
	}
}
