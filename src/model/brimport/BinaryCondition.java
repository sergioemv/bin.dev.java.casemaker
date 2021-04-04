package model.brimport;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class BinaryCondition extends Condition {

    private BRKeyword logicalOperator = BRKeyword.AND;
    private Condition leftcondition;
    private Condition rigthCondition;

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(getLeftcondition());
		builder.append(" "+getLogicalOperator().get(getLanguaje())+"\n");

		int count = -1;
		IFPart temp = this.getIfPart();
		while (temp!=null){
			count++;
			temp = temp.getParentIfPart();
		}
		char[] tabarray = new char[count];
		Arrays.fill(tabarray, '\t');
		builder.append(String.copyValueOf(tabarray)+"    "+getRigthCondition().toString());
		return builder.toString();
	}

	public Condition getLeftcondition() {
		if (leftcondition== null)
			leftcondition = new SimpleCondition();
		if (leftcondition.getIfPart()!= getIfPart())
			leftcondition.setIfPart(getIfPart());
		return leftcondition;
	}

	public void setLeftcondition(Condition leftcondition) {
		this.leftcondition = leftcondition;
	}

	public BRKeyword getLogicalOperator() {
		return logicalOperator;
	}

	public void setLogicalOperator(BRKeyword logicalOperator) throws Exception {
		if (!logicalOperator.isLogicalOperator())
			throw new Exception("The operator is not a valid logical operator");
		this.logicalOperator = logicalOperator;
	}

	public Condition getRigthCondition() {
		if (rigthCondition== null)
			rigthCondition = new SimpleCondition();
		if (rigthCondition.getIfPart()!= this.getIfPart())
			rigthCondition.setIfPart(getIfPart());
		return rigthCondition;
	}

	public void setRigthCondition(Condition rigthCondition) {
		this.rigthCondition = rigthCondition;
	}

	public Set<String> getInvalidIdentifiers() {
		Set<String> ids = new HashSet<String>();
		ids.addAll(getLeftcondition().getInvalidIdentifiers());
		ids.addAll(getRigthCondition().getInvalidIdentifiers());
		return ids;
	}
}