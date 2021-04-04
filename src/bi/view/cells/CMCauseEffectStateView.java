package bi.view.cells;

import com.eliad.swing.JSmartGrid;

/**
 * @author hcanedo
 *
 */
public class CMCauseEffectStateView extends CMCellCauseEffect {

	public CMCauseEffectStateView(JSmartGrid p_grid, Object p_model) {
		super(p_grid, p_model);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {

		return getEffect().getStateName();
	}

}
