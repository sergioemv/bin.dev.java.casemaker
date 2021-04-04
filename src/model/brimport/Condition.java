package model.brimport;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

public abstract class Condition implements BRPart{
	protected IFPart ifPart;
	public static final Collection<Character> forbiddenChars = new ArrayList<Character>(Arrays.asList('.',',','(',')','{','}','[',']','=','<','>',' ','!','-','/',';'));
	public static final Collection<Character> forbiddenInitialChars = new ArrayList<Character>(Arrays.asList('_','0','1','2','3','4','5','6','7','8','9','#'));
	
	//portiz_16102007_begin
	public Boolean bIsAssignment=false;
	public Boolean bIsLastIFBlock=false;
	//portiz_16102007_end
	
	
	public IFPart getIfPart() {
		return ifPart;
	}

	void setIfPart(IFPart part) {
		this.ifPart = part;
	}

	public BRLanguaje getLanguaje() {
		if (getIfPart() != null)
			return getIfPart().getLanguaje();
		return null;
	}
	public abstract String toString() ;

}