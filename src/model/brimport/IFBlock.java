package model.brimport;

import java.util.HashSet;
import java.util.Set;

import bi.view.businessrulesviews.brimport.utilsimporter.CMBRStringUtils;

/**
 * @author smoreno
 *  represents a group of IF conditions within the BR definition
 */
public class IFBlock implements BRPart {

	private String name;
	private IFPart ifPart;
	private BRDocument document;

	public String getName() {
		if (name== null)
			name = "";
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public IFPart getIfPart() {
		if (ifPart == null)
			ifPart = new IFPart();
		if (ifPart.getIfBlock()  != this )
			ifPart.setIfBlock(this);
		return ifPart;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		//portiz_30102007_begin
		if (getName() != ""){
			String cad=CMBRStringUtils.getOrnamentsLines(getName());
			builder.append("\n///"+getName()+"\n");
			builder.append("///"+cad+"\n");
		}	
		//portiz_30102007_end
		//portiz_30102007_begin
		//the assignments are declared here
		builder.append(getIfPart().getAssignList());
		//portiz_30102007_end		
			builder.append(getIfPart().toString());
		return builder.toString();
	}

	public BRDocument getDocument() {
		return document;
	}

	public void setDocument(BRDocument importedFile) {
		this.document = importedFile;
	}

	public BRLanguaje getLanguaje() {
		return getDocument().getLanguaje();
	}

	public void setIfPart(IFPart ifPart) {
		this.ifPart = ifPart;
	}

	public Set<String> getInvalidIdentifiers() {
		Set<String> ids = new HashSet<String>();
		ids.addAll(getIfPart().getInvalidIdentifiers());
		return ids;
	}
}