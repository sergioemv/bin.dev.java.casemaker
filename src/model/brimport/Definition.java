package model.brimport;


public class Definition extends SimpleCondition {

	private String label="";

	private BRDocument document;

	public Definition(String string, String string2) {
		super(string, string2);
	}

	public Definition() {
	}

	public void setOperator(BRKeyword operator) throws Exception {
			throw new Exception("Cannot assign an operator to a definition!");
	}

	public BRDocument getDocument() {
		return document;
	}

	public void setDocument(BRDocument document) {
		this.document = document;
	}


	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		if (getLabel()!="")
			builder.append("// " + getLabel() + "\n");
		//TODO remove the // when the definition activity is finished
		builder.append("//"+super.toString()+"\n");
		return builder.toString();
	}
	@Override
	public BRKeyword getOperator() {
		return BRKeyword.EQUAL;
	}
	@Override
	public BRLanguaje getLanguaje() {
		return getDocument().getLanguaje();
	}
}