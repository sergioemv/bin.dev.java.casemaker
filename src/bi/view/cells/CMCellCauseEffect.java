package bi.view.cells;

import model.Effect;

import com.eliad.swing.JSmartGrid;

public class CMCellCauseEffect extends CMBaseCell{
	public CMCellCauseEffect(JSmartGrid p_grid, Object p_model) {
		super(p_grid, p_model);
		// TODO Auto-generated constructor stub
	}
	public Effect getEffect() {
		return (Effect) getModel();
	}
	public void setEffect(Effect effect) {
		setModel(effect);
	}

}
