package  bi.view.cells;

import bi.view.lang.CMMessages;

import com.eliad.swing.JSmartGrid;

public class CMCauseEffectUsedView extends CMCellCauseEffect {

	public CMCauseEffectUsedView(JSmartGrid p_grid, Object p_model) {
		super(p_grid, p_model);
		// TODO Auto-generated constructor stub
	}
   @Override
public String toString() {
	if (getEffect().isUsed())
	   return CMMessages.getString("LABEL_YES");
	else
		return CMMessages.getString("LABEL_NO");
}
}
