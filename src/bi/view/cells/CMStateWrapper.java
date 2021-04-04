package bi.view.cells;

import model.State;

/**
 * @author smoreno
 * this class holds the state of an equivalence class or a combination, its a model class
 * 	
 */
public class CMStateWrapper
{
	
	State state;
	public CMStateWrapper(State state)
	{
		this.state = state;
	}
	public int getIntValue() {
		return this.state.intValue();
	}
	public String toString() {
		
		return this.state.toString();
	}
	public String getName() {
		return this.state.name();
	}
	
}