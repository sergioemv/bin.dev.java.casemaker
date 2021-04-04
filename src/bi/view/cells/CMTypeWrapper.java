package bi.view.cells;

import model.Type;

/**
 * @author ccastedo
 * this class holds the type of a typedata, its a model class
 * 	
 */
public class CMTypeWrapper
{
	
	Type type;
	public CMTypeWrapper(Type type)
	{
		this.type = type;
	}
	public int getIntValue() {
		return this.type.intValue();
	}
	public String toString() {
		
		return this.type.toString();
	}
	public String getName() {
		return this.type.name();
	}
	
}