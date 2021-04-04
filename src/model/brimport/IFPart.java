package model.brimport;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author smoreno
 * represents a IF ELSE END IF part
 */
public class IFPart implements BRPart {
		//the if part is composed of condition parts
	private Condition condition;
	private List<BusinessAction> businessActions;
	private IFBlock ifBlock;
	private IFPart childIfPart;
	private IFPart parentIfPart;
	private ElsePart elsePart;
	
	//portiz_30102007_begin
	private ArrayList<String> _AssignList=new ArrayList<String>();
	

	/**
	 *Return a format string of assignments. (Eg.- var=5) 
	 * @return
	 */
	public String getAssignList(){
		
		if (_AssignList.size()==0 )
			return "";
		StringBuilder builder=new StringBuilder();
		builder.append("/*Non valid assignemts for CaseMaker\n");
		for (String cad : _AssignList) 
			builder.append("        "+cad + "\n");
		builder.append("*/\n");
		
		return builder.toString();
	}
	//portiz_30102007_end
	
	public void setAssignList(String __assginMent){
		_AssignList.add(__assginMent);
	}
	
	
	/*
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		//if the condition is null then dont print the if and try with the child
		if (this.getCondition()!=null){
			int count = -1;
			IFPart temp = this;
			while (temp!=null){
			if (temp.getCondition()!=null)
				count++;
			temp = temp.getParentIfPart();
			}
		    //tabs
			char[] tabarray = new char[count];
			Arrays.fill(tabarray, '\t');
			builder.append(String.copyValueOf(tabarray));
			builder.append(BRKeyword.IF.get(getLanguaje())+" (");
			builder.append(getCondition().toString()+") ");
			builder.append(BRKeyword.THEN.get(getLanguaje())+"\n");
			for (BusinessAction businessAction : getBusinessActions())
				builder.append(String.copyValueOf(tabarray)+"\t"+businessAction.toString()+"\n");
			if (getChildIfPart()!=null)
				builder.append(getChildIfPart().toString());
			if (!hasChildBusinessActions())
				builder.append(String.copyValueOf(tabarray)+"\t"+BRKeyword.NOBUSINESSACTION.get(getLanguaje())+"\n");
			if (getElsePart()!=null)
				builder.append(getElsePart().toString());
			builder.append(String.copyValueOf(tabarray));
			builder.append(BRKeyword.ENDIF.get(getLanguaje())+"\n");
		}else // if condition is null
			if (getChildIfPart()!=null)
				builder.append(getChildIfPart().toString());
		return builder.toString();
	}
	*/
	
	public String toString() {
		StringBuilder builder = new StringBuilder();
		//if the condition is null then dont print the if and try with the child
		if (this.getCondition()!=null){
			int count = -1;
			IFPart temp = this;
			while (temp!=null){
			if (temp.getCondition()!=null)
				count++;
			temp = temp.getParentIfPart();
			}
		    //tabs
			char[] tabarray = new char[count];
			Arrays.fill(tabarray, '\t');
			builder.append(String.copyValueOf(tabarray));
			
			//portiz_24102007_begin
			if (! this.getCondition().bIsAssignment )
				builder.append(BRKeyword.IF.get(getLanguaje())+" (");
			
			//portiz_30102007_begin
			if (! this.getCondition().bIsAssignment )
				builder.append(getCondition().toString());
			//portiz_30102007_end			

			if (! this.getCondition().bIsAssignment )
				builder.append(") " + BRKeyword.THEN.get(getLanguaje())+"\n");
			else
				builder.append("\n");
			//portiz_24102007_end
			
			String strComment="";			
			//portiz_16102007_begin
			if ( (this.getCondition().bIsAssignment ) && (this.getCondition().bIsLastIFBlock  ) ){
				//builder.append(BRKeyword.IF.get(getLanguaje())+" ( true=true )");
				//builder.append(") " + BRKeyword.THEN.get(getLanguaje())+"\n");
				strComment="//";
			}
			//portiz_16102007_end
			
			for (BusinessAction businessAction : getBusinessActions())
				builder.append(strComment +  String.copyValueOf(tabarray)+"\t"+businessAction.toString()+"\n");
			if (getChildIfPart()!=null)
				builder.append(getChildIfPart().toString());
			if (!hasChildBusinessActions())
				builder.append(strComment +  String.copyValueOf(tabarray)+"\t"+BRKeyword.NOBUSINESSACTION.get(getLanguaje())+"\n");
			if (getElsePart()!=null)
				builder.append(getElsePart().toString());
			builder.append(String.copyValueOf(tabarray));

			//portiz_24102007_begin
			if (! this.getCondition().bIsAssignment )
				builder.append(BRKeyword.ENDIF.get(getLanguaje())+"\n");
			//portiz_24102007_end
			
			//portiz_16102007_begin
			/*if ( (this.getCondition().bIsAssignment ) && (this.getCondition().bIsLastIFBlock ) ){
				builder.append(BRKeyword.ENDIF.get(getLanguaje())+"\n");
			}*/
			//portiz_16102007_end
			
			
		}else // if condition is null
			if (getChildIfPart()!=null)
				builder.append(getChildIfPart().toString());
		return builder.toString();
	}
	
	
	
	private boolean hasChildBusinessActions() {
		 if (getBusinessActions().size()>0 && getCondition()!=null)
			 return true;
		 if ((getChildIfPart()==null) && (getBusinessActions().size()==0))
		 	return false;
		if  (getChildIfPart()!=null )
			return getChildIfPart().hasChildBusinessActions();
		return false;
	}
	public IFBlock getIfBlock() {
		return ifBlock;
	}
	void setIfBlock(IFBlock block) {
		this.ifBlock = block;
	}
	public BRLanguaje getLanguaje() {
		if (getParentIfPart()!=null)
			return getParentIfPart().getLanguaje();
		if (getIfBlock()!=null)
			return getIfBlock().getLanguaje() ;
		return null;
	}

	public Condition getCondition() {
		if (condition != null)
			if (condition.getIfPart() != this )
				condition.setIfPart(this);
		return condition;
	}
	public List<BusinessAction> getBusinessActions() {
		if (businessActions == null)
			businessActions = new ArrayList<BusinessAction>();
		for (BusinessAction businessAction : businessActions)
			if (businessAction.getIfPart() != this )
				businessAction.setIfPart(this);
		return Collections.unmodifiableList(businessActions);
	}
	public void setCondition(Condition condition) {
		this.condition = condition;
		if (condition!=null)
			condition.setIfPart(this);
	}
	public IFPart getChildIfPart() {
		if (childIfPart != null)
			if (childIfPart.getParentIfPart()!=this)
				childIfPart.setParentIfPart(this);
		return childIfPart;
	}
	public void setChildIfPart(IFPart childIfPart) {
		this.childIfPart = childIfPart;
		if (this.childIfPart!=null)
		this.childIfPart.setParentIfPart(this);
	}
	public IFPart getParentIfPart() {
		return parentIfPart;
	}
	public void setParentIfPart(IFPart parentIfPart) {
		this.parentIfPart = parentIfPart;
	}
	public void addBusinessAction(BusinessAction businessAction){
		getBusinessActions();
		businessAction.setIfPart(this);
		businessActions.add(businessAction);
	}
	public ElsePart getElsePart() {
		if (elsePart != null)
			if (elsePart.getParentIfPart()!=this)
				elsePart.setParentIfPart(this);
		//portiz_30102007_begin
		if (condition!=null && elsePart != null )
			elsePart.bIsAssignment=condition.bIsAssignment;
		//portiz_30102007_end		
		return elsePart;
	}
	
	public void setElsePart(ElsePart elsePart) {
		this.elsePart = elsePart;
		this.elsePart.setParentIfPart(this);
	}
	public Set<String> getInvalidIdentifiers() {
		Set<String> ids = new HashSet<String>();
		if (getCondition()!=null)
			ids.addAll(getCondition().getInvalidIdentifiers());
		for (BusinessAction businessAction : getBusinessActions())
			ids.addAll(businessAction.getInvalidIdentifiers());
		if (getChildIfPart()!=null)
			ids.addAll(getChildIfPart().getInvalidIdentifiers());
		if (getElsePart() != null)
			ids.addAll(getElsePart().getInvalidIdentifiers());
		return ids;
	}

}