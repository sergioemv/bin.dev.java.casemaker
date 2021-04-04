package bi.view.cells;

public class CMGridRowColumn{
	private int row,column=0;
	public CMGridRowColumn(int row, int column){
		this.row = row;
		this.column = column;
	}
	public int getColumn() {
		return column;
	}
	public void setColumn(int column) {
		this.column = column;
	}
	public int getRow() {
		return row;
	}
	public void setRow(int row) {
		this.row = row;
	}
}
