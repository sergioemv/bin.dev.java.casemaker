

package bi.controller;

import javax.swing.JOptionPane;
import javax.swing.undo.UndoableEdit;

import model.Effect;
import model.Structure;
import model.edit.CMModelEditFactory;
import model.util.IdSet;

import org.apache.log4j.Logger;

import bi.controller.utils.CMClipBoard;
import bi.view.lang.CMMessages;
import bi.view.mainframeviews.CMApplication;
import bi.view.undomanagementviews.CMCompoundEdit;
import bi.view.undomanagementviews.undoviews.CMUndoMediator;

//HCanedo_30112004_End
public class EffectManager {
	public static final EffectManager INSTANCE = new EffectManager();
    public EffectManager() {
        }

    public void copyCauseEffect(){
    	Effect effect = getSelectedEffect();
    	if (effect != null)
    		CMClipBoard.getInstance().copy(effect);

    }

    public void cutCauseEffect(){
    	Effect effect = getSelectedEffect();
    	if (effect == null) return;
    	Structure structure = effect.getLnkStructure();
    	if (structure == null)
    	structure  = StructureManager.getSelectedStructure();
    		CMClipBoard.getInstance().copy(effect);
    		CMCompoundEdit ce = new CMCompoundEdit();
    		ce.addEdit(CMModelEditFactory.INSTANCE.createDeleteEffectModelEdit(structure, effect));
    		structure.removeEffect(effect);
    		CMUndoMediator.getInstance().doEdit(ce);

    }

    public void pasteCauseEffect(){
    	Effect newCauseEffect = CMClipBoard.getInstance().getEffect();
    	if (newCauseEffect == null) return;
    	Structure structure  = StructureManager.getSelectedStructure();
    	if (structure == null) return;

    	CMCompoundEdit ce = new CMCompoundEdit();
    	if(structure.getEffectbyName(newCauseEffect.getName())!=null)
    	{
    		JOptionPane.showMessageDialog(CMApplication.frame,CMMessages.getString("MESSAGE_CAUSEEFFECT_ID_DUPLICATE"),CMMessages.getString("TESTDATA_IMPORT_TITLE_INFORMATION"),JOptionPane.INFORMATION_MESSAGE);
    		int id  = EffectManager.INSTANCE.getNextId(structure);
    		ce.addEdit(CMModelEditFactory.INSTANCE.createChangeIdModelEdit(newCauseEffect, id));
    		newCauseEffect.setId(id);
    	}
    	ce.addEdit(CMModelEditFactory.INSTANCE.createAddEffectModelEdit(structure, newCauseEffect));
        structure.addEffect(newCauseEffect);
        CMUndoMediator.getInstance().doEdit(ce);
    }
    public Effect createEffect(Structure p_structure) {
        Effect ce = new Effect();
        ce.setId(this.getNextId(p_structure));
        return ce;
    }


    public int getNextId(Structure p_structure) {
        IdSet idSet = new IdSet();
        for (Effect effect : p_structure.getEffects())
        	idSet.registerId(effect.getId());
        return idSet.nextValidId();
    }

    public void delete(Effect p_Effect, Structure p_Structure) {
        //delete from the Structure
        if (p_Structure.getEffects().contains(p_Effect)) {
            p_Structure.removeEffect(p_Effect);
        }
        this.m_EquivalenceClassManager.deleteEffectOfEquivalenceClasses(p_Effect, p_Structure);
        this.m_CombinationManager.deleteEffectOfCombinations(p_Effect, p_Structure);
    }

    public EquivalenceClassManager getM_EquivalenceClassManager() {
        return m_EquivalenceClassManager;
    }

    public void setM_EquivalenceClassManager(EquivalenceClassManager m_EquivalenceClassManager) {
        this.m_EquivalenceClassManager = m_EquivalenceClassManager;
    }

    public CombinationManager getM_CombinationManager() {
        return m_CombinationManager;
    }

    public void setM_CombinationManager(CombinationManager m_CombinationManager) {
        this.m_CombinationManager = m_CombinationManager;
    }
    /** @directed */
    private EquivalenceClassManager m_EquivalenceClassManager;

    /** @directed */
    private CombinationManager m_CombinationManager;
	public UndoableEdit addEffect(Structure structure, Effect effect) {
		CMCompoundEdit ce = new CMCompoundEdit();
		ce.addEdit(CMModelEditFactory.INSTANCE.createAddEffectModelEdit(structure,effect));
		structure.addEffect(effect);
		return ce;
	}

	/**
	*@autor smoreno
	 */
	public static void reloadEffectGrid() {
		CMApplication.frame.getCauseEffectStructureView().getM_CMCauseEffectStructureGridView().setM_Structure(
				CMApplication.frame.getCauseEffectStructureView().getM_CMCauseEffectStructureGridView().getM_Structure());

	}

	/**
	 * creates an effect based on a valid CE** value
	*@autor smoreno
	 * @param p_structure
	 * @param p_stringValue
	 * @return
	 */
	public Effect createEffect(Structure p_structure, String p_name) {
		// TODO Auto-generated method stub
		Effect effect = new Effect();
		int id = 0;
		try
		{
			id = Integer.parseInt(p_name.substring(2));
		}
		catch (Exception e)
		{
			Logger.getLogger(this.getClass()).warn("Invalid id creating a new one");
			return createEffect(p_structure);
		}
		effect .setId(id);
		return effect;
	}

	public static Effect getSelectedEffect() {
		return CMApplication.frame.getCauseEffectStructureView().getM_CMCauseEffectStructureGridView().getSelectedEffect();
	}


}
