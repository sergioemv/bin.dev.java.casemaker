package model.brimport;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import bi.view.lang.CMMessages;

//portiz_24102007_begin
import bi.view.businessrulesviews.brimport.cmbomparse.CMBomDocument;
//portiz_24102007_end
import bi.view.businessrulesviews.brimport.utilsimporter.CMBRStringUtils;

/**
 * @author smoreno
 * Represents the imported file and comments to it
 */
public class BRDocument implements BRPart  {

	private String name;
	private List<IFBlock> ifBlocks;
	private BRLanguaje  languaje;
	private List<BRPackage> packages;
	private List<Definition> definitions;

	//portiz_24102007_begin
	private CMBomDocument bomDocument=null;
	//portiz_24102007_end
	
	public void set_BomDocument(CMBomDocument __bomDocument){
		bomDocument = __bomDocument;
	}
	
	public String getName() {
		if (name==null)
			name = "";
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();

		//portiz_31102007_begin
		if (getName()!=""){
			
			String strCad=CMBRStringUtils.getOrnamentsAsterics(getName());
			builder.append("\n/*"+ strCad + "\n");
			builder.append( "/*"+getName()+"\n");
			builder.append( strCad + "*/ \n");
		}
		//portiz_31102007_end	

			//builder.append("/***********************************/\n"+
			//					"/*"+getName()+"\n"+
			//					"/*************************************/\n");
		
		
		if (getDefinitions().size()>0){
			builder.append("// " + CMMessages.getString("BRIMPORT_GLOBAL_DEFINITIONS_LABEL")+"\n" );
			for (Definition definition : getDefinitions())
					builder.append(definition.toString());
		}
		
		//portiz_24102007_begin
		//check if there are definitios to add
		if (bomDocument!=null){
			String res=bomDocument.parseNonUsedValues();
			if ( res!=null && !res.equals("")){
				builder.append("\n\n");
				builder.append("//Setting non-used attributes\n");
				builder.append("//------------------------------------------------\n");
				builder.append(res);
				builder.append("//------------------------------------------------\n");
				builder.append("//End of setting non-used attributes\n");
				builder.append("\n");
				
			}
		}
		//portiz_24102007_end		

		
		if (getBRPackages().size()>0)
			for (BRPackage package1 : getBRPackages())
				builder.append(package1.toString());
		else
			for (IFBlock block : getIfBlocks())
				builder.append(block.toString());
		
		//portiz_25102007_begin
		//TODO: PAULO_PATCH 
		//Patch: Change '.' for '_'
		String str=builder.toString().replace(".", "_");
		//portiz_25102007_begin
		
		return str;
	}

	public List<IFBlock> getIfBlocks() {
		if (ifBlocks == null)
			ifBlocks = new ArrayList<IFBlock>();
		for (IFBlock block : ifBlocks )
			if (block.getDocument() != this  )
				block.setDocument(this);
		return Collections.unmodifiableList(ifBlocks);
	}

	public void addIfBlock(IFBlock block){
		getIfBlocks();
		block.setDocument(this);
		ifBlocks.add(block);
	}
	public List<BRPackage> getBRPackages() {
		if (packages == null)
			packages = new ArrayList<BRPackage>();
		for (BRPackage package1 : packages )
			if (package1.getDocument() != this  )
				package1.setDocument(this);
		return Collections.unmodifiableList(packages);
	}
	public void addBRPackage(BRPackage package1){
		getBRPackages();
		package1.setDocument(this);
		packages.add(package1);
	}
	public BRLanguaje getLanguaje() {
		if (languaje == null)
			languaje = BRLanguaje.ENGLISH ;
		return languaje;
	}
	public void setLanguaje(BRLanguaje languaje) {
		this.languaje = languaje;
	}

	public Set<String> getInvalidIdentifiers() {
		Set<String> ids = new HashSet<String>();
		if (getBRPackages().size()>0)
			for (BRPackage package1 : getBRPackages())
				ids.addAll(package1.getInvalidIdentifiers());
		else
			for (IFBlock block : getIfBlocks())
				ids.addAll(block.getInvalidIdentifiers());

		return ids;
	}

	public List<Definition> getDefinitions() {
		if (definitions == null)
			definitions = new ArrayList<Definition>();
		for (Definition definition : definitions)
			if (definition.getDocument()!=this)
					definition.setDocument(this);
		return Collections.unmodifiableList(definitions);
	}


	public void addDefinition(Definition definition){
		getDefinitions();
		definition.setDocument(this);
		definitions.add(definition);
	}

}