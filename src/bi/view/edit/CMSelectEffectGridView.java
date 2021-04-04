/**
 * This Software has been developed by Business Software Innovations  .
 * Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved. 
 */
package bi.view.edit;

import javax.swing.undo.AbstractUndoableEdit;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;
import javax.swing.undo.UndoableEdit;

import bi.view.causeeffectviews.CMCauseEffectStructureGridView;
import bi.view.mainframeviews.CMApplication;

import model.Effect;

/**
 * @author smoreno
 *
 */
public class CMSelectEffectGridView extends AbstractUndoableEdit implements
		UndoableEdit {

	private Effect selectedEffect;
	private Effect oldSelectedEffect;
	private CMCauseEffectStructureGridView grid;
	public CMSelectEffectGridView(Effect p_effect) {
		super();
		this.selectedEffect = p_effect;
		grid = CMApplication.frame.getCauseEffectStructureView().getM_CMCauseEffectStructureGridView();
		oldSelectedEffect = grid.getSelectedEffect();
	}
	/* (non-Javadoc)
	 * @see javax.swing.undo.AbstractUndoableEdit#undo()
	 */
	@Override
	public void undo() throws CannotUndoException {
		super.undo();
	//	selectedEffect = grid.getSelectedEffect();
		if (oldSelectedEffect!=null)
			grid.selectCMCauseEffectView(oldSelectedEffect);
	}
	/* (non-Javadoc)
	 * @see javax.swing.undo.AbstractUndoableEdit#redo()
	 */
	@Override
	public void redo() throws CannotRedoException {
		super.redo();
		//oldSelectedEffect = grid.getSelectedEffect();
		grid.selectCMCauseEffectView(selectedEffect);
	}
}
