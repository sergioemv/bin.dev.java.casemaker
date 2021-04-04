package bi.controller.editcontrol;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import javax.swing.DefaultListModel;
import bi.controller.EffectManager;
import bi.view.actions.effect.CMEffectsEditPanel;
import bi.view.undomanagementviews.CMCompoundEdit;
import model.Effect;
import model.Structure;
import model.edit.CMModelEditFactory;
import model.edit.effect.CMAddEffectNotifiedModelEdit;
import model.util.CMEffectsBean;

/**
 * class that control the edition of a group of effects from a given effect bean thru a panel 
 * @author smoreno
 *
 */
public class CMEffectsEditController extends CMDefaultEditController {

	private CMEffectsBean effectBean;
	private Structure structure;
	private CMEffectsEditPanel panel;
	private List<Effect> createdEffects;
	
	
	public void setVisible(boolean visible){
		getPanel().setVisible(false);
	}
	public CMEffectsEditController(CMEffectsBean p_bean, Structure structure) {
			this.effectBean = p_bean;
			this.structure = structure;
			initializePanel();
			
	}

	private void initializePanel() {
		
		if (this.effectBean == null || this.structure == null) return;
		Vector<CMEffectEditController> effectViews = new Vector<CMEffectEditController>();	
		Vector<CMEffectEditController> effectViewsAssigned = new Vector<CMEffectEditController>();

		//create a CMEffectEditView for each effect
		for (Effect effect : structure.getEffects())
		{
			CMEffectEditController editController = new CMEffectEditController(effect);
			editController.setShuttle(getPanel().getShuttleEffects());
			if (!effectBean.getEffects().contains(effect))
			{
				effectViews.add(editController);
			} else
			{
				effectViewsAssigned.add(editController);
			}
		} 
		getPanel().setAssignedList(effectViewsAssigned);
		getPanel().setAvailableList(effectViews);
//		panel.getShuttleEffects().setM_CompareData(new Comparator(){
//			public int compare(Object p_o1, Object p_o2) {
//				return ((CMEffectEditController)p_o1).getEffect().compareTo(((CMEffectEditController)p_o2).getEffect());
//			}});
		getPanel().initializePanelSync();
		getPanel().getJButtonCreateEffect().addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				getWarningMessages().clear();
				//create new effect
				Effect effect = createTempEffect();
				CMEffectEditController editController = new CMEffectEditController(effect);
				((DefaultListModel)getPanel().getJListAssigned().getModel()).addElement(editController);
				getPanel().getJListAssigned().setSelectedValue(editController, true);
				editController.getPanel().getJEditorPaneDescription().requestFocus();
				if (editController.getWarningMessages().size()>0)
					getWarningMessages().addAll(editController.getWarningMessages());
			}
		});
		
	}
		
	
	protected Effect createTempEffect() {
		Structure fakeStructure = new Structure();
		for (Effect effect : structure.getEffects())
			fakeStructure.addEffect(effect);
		for (Effect effect : getCreatedEffects())
			fakeStructure.addEffect(effect);
		Effect newEffect = EffectManager.INSTANCE.createEffect(fakeStructure);
		getCreatedEffects().add(newEffect);
		return newEffect;
	}

	private List<Effect> getCreatedEffects() {
		if (createdEffects == null)
			createdEffects = new ArrayList<Effect>();
		return createdEffects;
	}

	public  CMEffectsEditPanel getPanel() {
		if (this.panel == null){
			this.panel = new CMEffectsEditPanel();
		}
			
		return panel;
	}
	
	public CMCompoundEdit applyChanges(){
		CMCompoundEdit ce = new CMCompoundEdit();		
		if (this.effectBean == null || this.structure == null) return ce;
		if (!getPanel().isVisible())
			return ce;
		//		add the created effects to the structure
		for (Effect effect : getCreatedEffects()){
			ce.addEdit(new CMAddEffectNotifiedModelEdit(structure,effect));
			structure.addEffect(effect);
		}
		//remove all assigned effects of the bean
		for (int i = effectBean.getEffects().size()-1;i>=0;i--)
		{
			Effect effect = effectBean.getEffects().get(i);
			ce.addEdit(CMModelEditFactory.INSTANCE.createDeleteEffectModelEdit(effectBean,effect));
			effectBean.removeEffect(effect);
		}
		//create a list of assigned effects
		List<Effect> effectsAssigned = new ArrayList<Effect>();
		for (Object effectview : getPanel().getAssignedList())
			effectsAssigned.add(((CMEffectEditController) effectview).getEffect());
		//add all assigned effects to the bean
		for (Effect effect : structure.getEffects())
			if (effectsAssigned.contains(effect))
			{
				ce.addEdit(CMModelEditFactory.INSTANCE.createAddEffectModelEdit(effectBean,effect));
				effectBean.addEffect(effect);
			}
		//begin updating all modified effects
		List<CMEffectEditController> listEffectViews = new ArrayList<CMEffectEditController>();
		listEffectViews.addAll(getPanel().getAssignedList());
		listEffectViews.addAll(getPanel().getAvailableList());
		for (CMEffectEditController effectView : listEffectViews){
			/*if (effectView.isModified())  //ccastedo 05.10.06
			{*/				
				effectView.setStructure(structure);
				ce.addEdit(effectView.applyChanges());
				getWarningMessages().addAll(effectView.getWarningMessages());
		}		
		
		return ce;
	}
	
}

