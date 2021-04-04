package model.brimport;

import java.util.Arrays;

public class ElsePart extends IFPart {

	//portiz_30102007_begin
	public Boolean bIsAssignment=false;
	//portiz_30102007_begin	
	
	@Override
	public void setCondition(Condition condition) {
		throw new UnsupportedOperationException("Cannot assign a condition to an else part");
	}
	@Override
	public Condition getCondition() {
		//no conditions for else parts
		return null;
	}
	@Override
	public void setElsePart(ElsePart elsePart) {
		throw new UnsupportedOperationException("Cannot assign a else part to an else part");
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		
		//portiz_30102007_begin
		String strComment="";
		if (this.bIsAssignment)
			strComment="//";
		//portiz_30102007_end		
		
		int count = -2;
		IFPart temp = this;
		while (temp!=null){
			count++;
			temp = temp.getParentIfPart();
		}
		char[] tabarray = new char[count];
		Arrays.fill(tabarray, '\t');
		//tabs
		builder.append(String.copyValueOf(tabarray));
		
		//portiz_30102007_begin
		if (!this.bIsAssignment)
			builder.append(BRKeyword.ELSE.get(getLanguaje())+"\n");
		
		for (BusinessAction businessAction : getBusinessActions())
			builder.append( strComment + String.copyValueOf(tabarray)+"\t"+businessAction.toString()+"\n");
		//portiz_30102007_end
		
		if (getChildIfPart()!=null)
			builder.append(getChildIfPart().toString());
		return builder.toString();
	}
}