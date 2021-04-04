package model.brimport;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import bi.view.businessrulesviews.brimport.utilsimporter.CMBRStringUtils;

public class BRPackage implements BRPart {

	private String name;
	private BRDocument document;
	private List<IFBlock> ifBlocks;
	public List<IFBlock> getIfBlocks() {
		if (ifBlocks == null)
			ifBlocks = new ArrayList<IFBlock>();
		for (IFBlock block : ifBlocks )
			if (block.getDocument() != this.getDocument()  )
				block.setDocument(this.getDocument());
		return Collections.unmodifiableList(ifBlocks);
	}
	public void addIfBlock(IFBlock block){
		getIfBlocks();
		block.setDocument(this.getDocument());
		ifBlocks.add(block);
	}
	public BRLanguaje getLanguaje() {
		return getDocument().getLanguaje();
	}
	public String getName() {
		if (name==null)
			name = "";
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public BRDocument getDocument() {
		return document;
	}
	public void setDocument(BRDocument document) {
		this.document = document;
	}
	@Override
	public String toString() {
		//portiz_31102007_begin
		StringBuilder builder = new StringBuilder();
		if (getName()!=""){
			String strSeparator= "//" + CMBRStringUtils.getOrnamentsAsterics(getName());
			builder.append("\n"+strSeparator+"\n");
			builder.append("//"+getName().toUpperCase()+"\n");
			builder.append(strSeparator+"\n");
		}
		for (IFBlock block : getIfBlocks())
			builder.append(block.toString());
		return builder.toString();
		//portiz_31102007_end		
	}
	public Set<String> getInvalidIdentifiers() {
		Set<String> ids = new HashSet<String>();
		for (IFBlock block : getIfBlocks())
			ids.addAll(block.getInvalidIdentifiers());
		return ids;
	}
}