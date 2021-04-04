package bi.view.cells;




import com.eliad.model.defaults.DefaultGridCellEditor;
import com.eliad.model.defaults.DefaultGridCellRenderer;
import com.eliad.model.defaults.DefaultStyleModel;
import com.eliad.swing.JSmartGrid;

public class CMBaseCell {

	private JSmartGrid grid;
	private Object model;


	public CMBaseCell(Object p_model) {
		model = p_model;
	}

	public CMBaseCell(JSmartGrid p_grid,Object p_model) {

		grid = p_grid;
		model = p_model;

	}

	public JSmartGrid getGrid() {
		return grid;
	}

	public void setGrid(JSmartGrid grid) {
		this.grid = grid;
	}

	public Object getModel() {
		return model;
	}

	public void setModel(Object model) {
		this.model = model;
	}

	public void setRenderer(DefaultGridCellRenderer p_renderer)
	{
		if (grid!=null)
		((DefaultStyleModel)this.grid.getStyleModel()).setRenderer(this.getClass(),p_renderer);
	}


	public void setEditor(DefaultGridCellEditor p_editor)
	{
		if (grid!=null)
		((DefaultStyleModel)this.grid.getStyleModel()).setEditor(this.getClass(),p_editor);
	}
	@Override
	public String toString() {
		if (getModel()!=null)
			return getModel().toString();
		return super.toString();
	}
}
