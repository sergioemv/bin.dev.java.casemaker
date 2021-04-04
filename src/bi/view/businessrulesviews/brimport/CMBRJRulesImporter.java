package bi.view.businessrulesviews.brimport;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.swing.JOptionPane;

import model.brimport.BRDocument;
import model.brimport.BRKeyword;
import model.brimport.BRLanguaje;
import model.brimport.BRPackage;
import model.brimport.BinaryCondition;
import model.brimport.BusinessAction;
import model.brimport.Condition;
import model.brimport.Definition;
import model.brimport.ElsePart;
import model.brimport.IFBlock;
import model.brimport.IFPart;
import model.brimport.NAryCondition;

import org.apache.xmlbeans.XmlCursor;
import org.apache.xmlbeans.XmlObject;

import bi.view.lang.CMMessages;
import bi.view.mainframeviews.CMApplication;
import bi.view.utils.event.CMProgressEventHandler;
import bi.view.utils.event.CMProgressListener;
import bi.view.utils.event.CMProgressSource;

import com.ilog.products.xml.schemas.xrl20.RulesetDocument;
import com.ilog.products.xml.schemas.xrl20.ArgumentDocument.Argument;
import com.ilog.products.xml.schemas.xrl20.ArgumentsDocument.Arguments;
import com.ilog.products.xml.schemas.xrl20.AssignDocument.Assign;
import com.ilog.products.xml.schemas.xrl20.BinaryOpDocument.BinaryOp;
import com.ilog.products.xml.schemas.xrl20.BindDocument.Bind;
import com.ilog.products.xml.schemas.xrl20.CastDocument.Cast;
import com.ilog.products.xml.schemas.xrl20.ClassDocument.Class;
import com.ilog.products.xml.schemas.xrl20.CollectDocument.Collect;
import com.ilog.products.xml.schemas.xrl20.ConstantDocument.Constant;
import com.ilog.products.xml.schemas.xrl20.DefaultPackageDocument.DefaultPackage;
import com.ilog.products.xml.schemas.xrl20.ElementDocument.Element;
import com.ilog.products.xml.schemas.xrl20.ElseDocument.Else;
import com.ilog.products.xml.schemas.xrl20.EnumeratorDocument.Enumerator;
import com.ilog.products.xml.schemas.xrl20.EvaluateConditionDocument.EvaluateCondition;
import com.ilog.products.xml.schemas.xrl20.ExistsDocument.Exists;
import com.ilog.products.xml.schemas.xrl20.FieldDocument.Field;
import com.ilog.products.xml.schemas.xrl20.IntervalDocument.Interval;
import com.ilog.products.xml.schemas.xrl20.MethodDocument.Method;
import com.ilog.products.xml.schemas.xrl20.NaryOpDocument.NaryOp;
import com.ilog.products.xml.schemas.xrl20.NewArrayDocument.NewArray;
import com.ilog.products.xml.schemas.xrl20.NewDocument.New;
import com.ilog.products.xml.schemas.xrl20.PackageDocument.Package;
import com.ilog.products.xml.schemas.xrl20.RuleDocument.Rule;
import com.ilog.products.xml.schemas.xrl20.SimpleConditionDocument.SimpleCondition;
import com.ilog.products.xml.schemas.xrl20.ThenDocument.Then;
import com.ilog.products.xml.schemas.xrl20.UnaryOpDocument.UnaryOp;
import com.ilog.products.xml.schemas.xrl20.VariableDocument.Variable;
import com.ilog.products.xml.schemas.xrl20.WhenDocument.When;
//portiz_12102007_begin
import bi.view.businessrulesviews.brimport.utilsimporter.*;
//portiz_12102007_end


//portiz_24102007_begin
import bi.view.businessrulesviews.brimport.cmbomparse.*;
import bi.view.businessrulesviews.brimport.filereader.*;
//portiz_24102007_end
public class CMBRJRulesImporter implements ICMBRImporter, CMProgressSource {
		
	//portiz_24102007_begin
	private CMBomDocument bomDocument=new CMBomDocument();
	//portiz_24102007_end
	
	//portiz_12102007_begin
	private CMBRPlaceHolderReplace toReplacePlaceholders= new CMBRPlaceHolderReplace();
	//portiz_12102007_end
	
	private Map<String,String> nativeIdentifiers;
	//expressions that already had been replaced by the user
	private static Map<String, String> oldNativeIdentifiers = new HashMap<String, String>();
	private boolean printCasts = false;
	private BRDocument brDocument;

	private static Map<String, BRKeyword> operators = new HashMap <String, BRKeyword>();
	static {
		operators.put("and",BRKeyword.AND);
		operators.put("or",BRKeyword.OR);
		operators.put("in",BRKeyword.IN);
		operators.put("not in", BRKeyword.NOT_IN);
		operators.put("gt",BRKeyword.GREATER);
		operators.put("gte", BRKeyword.GREATER_EQUAL);
		operators.put("lt", BRKeyword.LOWER);
		operators.put("le",BRKeyword.LOWER_EQUAL);
		operators.put("==",BRKeyword.EQUAL);
		operators.put("+",BRKeyword.PLUS);
		operators.put("-",BRKeyword.MINUS);
		operators.put("*",BRKeyword.MULT);
		operators.put("/",BRKeyword.DIVIDE);

	}
	private CMProgressEventHandler handler;

	public String doImport(File p_importFile,File p_importFileBom, Map<String, Object> aditionalOptions) throws Exception {
		//initialize the string builder
		StringBuilder stringBuilder = new StringBuilder();

		getHandler().fireProgressEventHappen(40, CMMessages.getString("PROGRESS_PARSING_XRL"));

		//read the vocabulary file
		if (aditionalOptions.get("VOCABULARY_FILE")!=null)
			addKeywordsFromVocabulary((File)aditionalOptions.get("VOCABULARY_FILE"));
		//	load the xrl file
		RulesetDocument ruleDoc = RulesetDocument.Factory.parse(p_importFile);
		
		//portiz_24102007_begin
		//load the bom file
		if (p_importFileBom !=  null)
			bomDocument.bindBomFile(p_importFileBom.getPath());
		BRDocument doc=transformDocument(ruleDoc);
		doc.set_BomDocument(bomDocument);
		stringBuilder.append(doc);
		//portiz_24102007_end		
		
		// check if cast are allowed
		Object casts = aditionalOptions.get("IGNORE_CAST");
		if (casts == null)
			printCasts = false;
		else
			printCasts = !((Boolean) casts);

		return stringBuilder.toString();
	}
	
	private void addKeywordsFromVocabulary(File file) throws FileNotFoundException, IOException {
		Properties properties = new Properties();
		properties.load(new FileInputStream(file));
		for (Object ob : properties.keySet())
			if (ob.toString().endsWith("#concept.label")||ob.toString().endsWith("#instance.label"))
			getNativeIdentifiers().put(ob.toString().substring(0, ob.toString().indexOf('#')),properties.getProperty(ob.toString()) );
	}
	
	private BRDocument transformDocument(RulesetDocument ruleDoc) throws Exception {
		BRDocument document = new BRDocument();
	
		
		//document.setLanguaje(BRLanguaje.GERMAN);
		
		document.setName((CMMessages.getString("BR_JRULES_IMPORT_HEADER")+" = "+ruleDoc.getRuleset().getName()));
		//process the default package
		getHandler().fireProgressEventHappen(60, "Reading the ruleset parameters");
		if (ruleDoc.getRuleset().getRulesetParameters()!=null)
			for (Argument argument : ruleDoc.getRuleset().getRulesetParameters().getArgumentArray())
				document.addDefinition(transformRulesetParameters(argument));
		if (ruleDoc.getRuleset().getDefaultPackage()!=null){
			document.addBRPackage(transformDefaultPackage(ruleDoc.getRuleset().getDefaultPackage()));
		}
		//process all packages
		for (Package pac : ruleDoc.getRuleset().getPackageArray())
		{
			document.addBRPackage(transformPackage(pac));
		}
		return document;
	}
	
	private Definition transformRulesetParameters(Argument argument) {
		Definition definition = new Definition();
		definition.setIdentifier(argument.getName());
		definition.setValue(argument.getType());
		//portiz_24102007_begin
		bomDocument.addToListOfParameters(argument.getType());
		//portiz_24102007_end
		
		//difererce between in, out and in_out parms
		switch (argument.getModifier().charAt(0)) {
		case '3':
			definition.setLabel("IN parameter");
			break;
		case '5':
			definition.setLabel("OUT parameter");
		default:
			definition.setLabel("IN - OUT parameter");
			break;
		}
		return definition;
	}
	
	public Map<String,String> findNativeIdentifiers(File pFile,  Map<String, Object> aditionalOptions) throws Exception{

		//load the xrl file
		RulesetDocument ruleDoc = RulesetDocument.Factory.parse(pFile);
		BRDocument document = transformDocument(ruleDoc);
		
		clearReplacements();
		if (aditionalOptions.get("VOCABULARY_FILE")!=null)
			addKeywordsFromVocabulary((File)aditionalOptions.get("VOCABULARY_FILE"));

			int confirmation = -99;

			for (String key: document.getInvalidIdentifiers())
				if (getOldNativeIdentifiers().get(key)!=null)
				{
					if (confirmation ==-99)
						confirmation = JOptionPane.showConfirmDialog(CMApplication.frame, CMMessages.getString("JRULES_REPLACEALL_CONFIRMATION_MESSAGE"),
				                CMMessages.getString("JRULES_REPLACEALL_CONFIRMATION_TITLE"), JOptionPane.YES_NO_OPTION);
					if (confirmation == JOptionPane.NO_OPTION)
						getNativeIdentifiers().put(key,getOldNativeIdentifiers().get(key));
					else
						getNativeIdentifiers().put(key,key);
				}
				else{
					//TODO: PAULO_PATCH 
					//Patch: Change '.' for '_' 
					getNativeIdentifiers().put(key.replace(".", "_"),key.replace(".", "_"));
					//Original code commented
					//getNativeIdentifiers().put(key,key);
				}	


		document.getInvalidIdentifiers();
		//Map<String,String> replacements = new HashMap<String, String>();

		return getNativeIdentifiers();

	}
	
	public static Map<String, String> getOldNativeIdentifiers() {
		if (oldNativeIdentifiers == null)
			oldNativeIdentifiers = new HashMap<String, String>();
		return oldNativeIdentifiers;
	}
	/**
	 *  Clears all native replacements made
	 */

	public void clearReplacements(){
		//passes all replacements to the old replacements map
		for (String key: getNativeIdentifiers().keySet())
			getOldNativeIdentifiers().put(key,getNativeIdentifiers().get(key));
		//clears the replacement map
		getNativeIdentifiers().clear();
	}
	
	private BRPackage transformDefaultPackage(DefaultPackage defaultPackage) throws Exception {
		BRPackage package1 = new BRPackage();
		if (defaultPackage.getRuleArray().length>0) {
			package1.setName(CMMessages.getString("BR_JRULES_IMPORT_PACKAGE_NAME")+" = "+defaultPackage.getPkgName());
			//process all rules in the package
			for (Rule rule : defaultPackage.getRuleArray())
			    package1.addIfBlock( transformRule(rule));
		}
		return package1;
	}
	
	
	
	private BRPackage transformPackage(Package pac) throws Exception {
		BRPackage package1 = new BRPackage();
		if (pac.getRuleArray().length>0) {
			package1.setName((CMMessages.getString("BR_JRULES_IMPORT_PACKAGE_NAME")+" = "+pac.getPkgName()));
			//process all rules in the package
			for (Rule rule : pac.getRuleArray())
				package1.addIfBlock(( transformRule(rule)));
		}
		return package1;
	}
	
	private IFBlock transformRule(Rule rule) throws Exception {
		IFBlock block = transformWhen(rule.getWhen());
		block.setName((CMMessages.getString("BR_JRULES_IMPORT_RULE_NAME")+" = "+rule.getName()));
		
		//portiz_30102007_begin
		IFPart ifPart = block.getIfPart();
		if (rule.isSetElse()){
			ifPart.setElsePart(transformElse(rule.getElse()));
			//ifPart = ifPart.getChildIfPart();
		}
		//portiz_30102007_end
					
		/*
		//Original code commented
		//Go to the last nested if
		IFPart ifPart = block.getIfPart();
		while (ifPart.getChildIfPart()!=null){
			//attach the else part before if present
			if (rule.isSetElse())
				ifPart.setElsePart(transformElse(rule.getElse()));
			ifPart = ifPart.getChildIfPart();
		}
		*/

		//append the actions to the last If
		transformThen(ifPart,rule.getThen());
		
		//portiz_16102007_begin
		if (ifPart.getCondition()!=null)
			ifPart.getCondition().bIsLastIFBlock=true;
		//portiz_16102007_end		
		
		return block;
	}
	
	private ElsePart transformElse(Else else1) throws Exception {
		ElsePart elsePart = new ElsePart();
		for (Assign assign : else1.getAssignArray()) {
			BusinessAction businessAction = new BusinessAction();
			String cad=transformAssign(assign);
			cad=CMBRStringUtils.depureBusinessAction(cad);
			businessAction.setBody(cad);
			//origina code commented
			//businessAction.setBody(transformAssign(assign));
			elsePart.addBusinessAction(businessAction);
		}
		for (Method method : else1.getMethodArray()) {
			BusinessAction businessAction = new BusinessAction();
			//portiz_071107_begin
			String cad=transformMethod(method);
			cad=CMBRStringUtils.depureBusinessAction(cad);
			businessAction.setBody(cad);
			//portiz_071107_end			

			//origina code commented
			//businessAction.setBody(transformMethod(method));
			elsePart.addBusinessAction(businessAction);
		}
		return elsePart;
	}

	private void transformThen(IFPart ifPart, Then then) throws Exception {

		for (Assign assign : then.getAssignArray()) {
			BusinessAction businessAction = new BusinessAction();
			//portiz_07112007_begin	
			String cad=transformAssign(assign);
			cad=CMBRStringUtils.depureBusinessAction(cad);
			businessAction.setBody(cad);
			//portiz_07112007_end	
			//original code commented
			//businessAction.setBody(transformAssign(assign));
			ifPart.addBusinessAction(businessAction);
		}
		for (Method method : then.getMethodArray()) {
			BusinessAction businessAction = new BusinessAction();
			//portiz_07112007_begin
			String cad=transformMethod(method);
			cad=CMBRStringUtils.depureBusinessAction(cad);
			businessAction.setBody(cad);
			//original code commented
			//businessAction.setBody(transformMethod(method));
			//portiz_07112007_end
			ifPart.addBusinessAction(businessAction);
		}
	}
	
	private String transformAssign(Assign assign) throws Exception {
		StringBuilder stringBuilder = new StringBuilder();
		XmlCursor cursor=  assign.newCursor();
		cursor.toFirstChild();
		stringBuilder.append(transformAnyIdentifier(cursor.getObject(),null));
		if (operators.get(assign.getName())!=null)
			stringBuilder.append(" "+operators.get(assign.getName()).get(getBrDocument().getLanguaje())+" ");
		else
			stringBuilder.append(" "+assign.getName()+" ");
		XmlCursor cursor1=  assign.newCursor();
		cursor1.toLastChild();
		stringBuilder.append(transformAnyIdentifier(cursor1.getObject(),null));
		return stringBuilder.toString();
	}
	
	//portiz_30102007_begin
	private boolean isAssignemt(EvaluateCondition __toEvaluate){
		return (__toEvaluate.getBind()!=null);
	}
	//portiz_30102007_end	

	private String getSimpleConditionAssign(model.brimport.SimpleCondition __cond){
		
		if (__cond.getValue().indexOf(" from ")==-1)
			return __cond.toString();
		
		String values[]=__cond.getValue().split(" ");
		
		if (values.length < 3)
			return __cond.toString();
		
		String aux="";
		for (int h = 2; h < values.length; h++) {
			aux += values[h];
		}
		
		StringBuilder builder=new StringBuilder();
		builder.append(__cond.getIdentifier());
		builder.append(" " + __cond.getOperator().getEnglish() + " ");
		builder.append("(" + CMBRStringUtils.getSecondPartName(values[0]) + ") ");
		builder.append(aux);
		
		return builder.toString();
	}
	
	private IFBlock transformWhen( When when) throws Exception {
		IFBlock block = new IFBlock();
		XmlCursor cursor = when.newCursor();
		cursor.toFirstChild();
		IFPart ifPart = block.getIfPart();
		do{
				if(cursor.getObject() instanceof SimpleCondition){
					//portiz_30102007_begin
					Condition cond=transformSimpleCondition((SimpleCondition) cursor.getObject());
					if (cond!=null){
						//String cad=cond.toString();
						String cad2=getSimpleConditionAssign((model.brimport.SimpleCondition)cond);
						cad2= CMBRStringUtils.depureBusinessAction(cad2);
						ifPart.setAssignList(cad2);
						cond.bIsAssignment=true;
					}
					ifPart.setCondition(cond);
					//original code comented	
					//ifPart.setCondition(transformSimpleCondition((SimpleCondition) cursor.getObject()));
					//portiz_30102007_end
				}
				if(cursor.getObject() instanceof EvaluateCondition){
					//portiz_30102007_begin
					Condition cond=transformEvaluateCondition((EvaluateCondition)cursor.getObject());
					ifPart.setCondition(cond);
					if (cond!=null   ){
						//cond.bIsAssignment=true;
						if (isAssignemt((EvaluateCondition)cursor.getObject())){
							String cad=((model.brimport.SimpleCondition)cond).toString( BRLanguaje.ENGLISH);
							//it's not a business action, but the method makes the required
							cad= CMBRStringUtils.depureBusinessAction(cad);
							ifPart.setAssignList(cad);
						}
					}
					//ifPart.setCondition(transformEvaluateCondition((EvaluateCondition)cursor.getObject()));
					//portiz_30102007_end
				}
				if(cursor.getObject() instanceof Exists)
					ifPart.setCondition(transformExists((Exists)cursor.getObject()));
				if (cursor.getObject() instanceof Collect)
					ifPart.setCondition(transformCollect((Collect)cursor.getObject()));
				
				//Código origina comentado
				//ifPart.setChildIfPart(new IFPart());
				//ifPart = ifPart.getChildIfPart();
				
				//portiz_12102007_begin
				if(  ( ! (cursor.getObject() instanceof SimpleCondition))  && ( ! (cursor.getObject() instanceof EvaluateCondition ))) {
					ifPart.setChildIfPart(new IFPart());
					ifPart = ifPart.getChildIfPart();
				}
				//portiz_12102007_end					
			}
		while (cursor.toNextSibling());
		//remove last if
		if (ifPart.getParentIfPart()!=null){
			ifPart = ifPart.getParentIfPart();
			ifPart.setChildIfPart(null);
		}
		return block;
	}
	
	private Condition transformCollect(Collect collect) throws Exception {
		model.brimport.SimpleCondition condition = new model.brimport.SimpleCondition();
		//if is a definition
		if (collect.isSetBind()){
			condition.setIdentifier(collect.getBind());
			StringBuilder builder = new StringBuilder();
			builder.append("All ");
			if (collect.getElement()!=null){
				builder.append(transformElement(collect.getElement()));
			}
			condition.setValue(builder.toString());
		}
		return condition;
	}
	
	private String transformElement(Element element) throws Exception {
		if (element.getEnumerator()!=null)
			return element.getClassName() + " " +transformEnnumerator(element.getEnumerator());
		else
			return element.getClassName();
	}
	
	private Condition transformEvaluateCondition(EvaluateCondition evaluateCondition) throws Exception {
		//only one of the following
		//is is a definition
		if(evaluateCondition.getBind()!=null)
			return transformBind(evaluateCondition.getBind());
		if (evaluateCondition.isSetBinaryOp())
			return transformBinaryOp(evaluateCondition.getBinaryOp());
		if (evaluateCondition.getNaryOp()!=null)
			return transformNaryOp(evaluateCondition.getNaryOp());
		if (evaluateCondition.isSetUnaryOp()){
			model.brimport.SimpleCondition condition = new model.brimport.SimpleCondition();
			condition.setIdentifier(transformUnaryOp(evaluateCondition.getUnaryOp() ));
			condition.setValue("true");
			return condition;
		}
		//portiz_26102007_begin
		XmlCursor cursor=  evaluateCondition.newCursor();
		cursor.toFirstChild();
		model.brimport.SimpleCondition condition = new model.brimport.SimpleCondition();
		String resTemp=transformAnyIdentifier(cursor.getObject(),null );
		condition.setIdentifier(resTemp);
		if (!CMBRStringUtils.hasKnowedFunctions_equal_primitive(resTemp))
			condition.setValue("true");	
		else
			condition.bIsManagecondition=true;
		return condition;
		//portiz_26102007_end		
		
		/*Original code commented
		XmlCursor cursor=  evaluateCondition.newCursor();
		cursor.toFirstChild();
		model.brimport.SimpleCondition condition = new model.brimport.SimpleCondition();
		condition.setIdentifier(transformAnyIdentifier(cursor.getObject(),null ));
		condition.setValue("true");
		return condition;*/
	}

	private Condition transformBind(Bind bind) throws Exception {
		model.brimport.SimpleCondition condition = new model.brimport.SimpleCondition();
		condition.setIdentifier(bind.getName());
		try {
			condition.setOperator(BRKeyword.EQUAL);
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (bind.isSetConstant()){
			//portiz_16102007_begin
			condition.bIsAssignment =true;
			//portiz_16102007_end
			condition.setValue(transformConstant(bind.getConstant()));
		}	 
		else
		if (bind.isSetVariable())
			condition.setValue(transformVariable(bind.getVariable()));
		else
		if (bind.isSetField())
			condition.setValue(transformField(bind.getField()));
		else
		if (bind.isSetMethod()){
			condition.setValue(transformMethod(bind.getMethod()));
			//portiz_16102007_begin
			condition.bIsAssignment =true;
			//portiz_16102007_end
		}
		else
		if (bind.isSetCast())
			condition.setValue(transformCast(bind.getCast()));
		else
		if (bind.isSetBinaryOp())
			condition.setValue(transformBinaryOperation(bind.getBinaryOp()));
		else
		if (bind.isSetNewArray1())
			condition.setValue(transformNewArray(bind.getNewArray1()));
		else
			condition.setValue("NULL");
		return condition;
	}
	
	private Condition transformExists(Exists exists) throws Exception {
		model.brimport.SimpleCondition condition = new model.brimport.SimpleCondition();
	    condition.setIdentifier("EXISTS ("+transformEnnumerator(exists.getEnumerator())+")"+
	    		transformBinaryOperation(exists.getBinaryOp()));
	    condition.setValue("true");
		return condition;
	}

	private String transformUnaryOp(UnaryOp unaryOp) throws Exception {
		StringBuilder stringBuilder = new StringBuilder();
		//if is a negation of a Binary operator
		XmlCursor cursor=  unaryOp.newCursor();
		cursor.toFirstChild();
		//stringBuilder.append(transformAnyIdentifier(cursor.getObject(),null));
		
		//todo:paulo
		String theUnaryOP="";
		if (unaryOp.getName().equals("!"))
			theUnaryOP=" NOT ";
		stringBuilder.append(theUnaryOP + transformAnyIdentifier(cursor.getObject(),null));
		
		if (!(cursor.getObject() instanceof BinaryOp))
			if ((cursor.getObject() instanceof NaryOp) )
				stringBuilder.append(transformNaryOp((NaryOp) cursor.getObject()));
		return stringBuilder.toString();
	}

	//portiz_29102007_begin
	private Condition transformNaryOp(NaryOp naryOp) throws Exception {
		NAryCondition naryCondition = new NAryCondition();
		XmlCursor cursor=  naryOp.newCursor();
		//cursor.toFirstChild();
		Integer iNextChild=0;
		
		naryCondition .setLogicalOperator(operators.get(naryOp.getName()));
		
		while (cursor.toChild(iNextChild)){
			if (cursor.getObject() instanceof NaryOp)		
				naryCondition.addCondition(transformNaryOp((NaryOp) cursor.getObject()));
			else
			if (cursor.getObject() instanceof BinaryOp)
				naryCondition.addCondition(transformBinaryOp((BinaryOp) cursor.getObject()));
			else
			if ((cursor.getObject() instanceof UnaryOp) ){
				model.brimport.SimpleCondition condition = new model.brimport.SimpleCondition();
				condition.setIdentifier(transformUnaryOp((UnaryOp) cursor.getObject() ));
				//portiz_15102007_begin
				//Original code commented
				//condition.setValue("true");
				condition.bIsCompareTo=true;
				condition.setValue(null);
				//portiz_15102007_end
				naryCondition.addCondition(condition);
				}
			else{
				model.brimport.SimpleCondition condition = new model.brimport.SimpleCondition();
				//portiz_18102007_begin
				String strCondition=transformAnyIdentifier(cursor.getObject(),null );
				condition.setIdentifier(strCondition);
				if (!CMBRStringUtils.hasKnowedFunctions_equal_primitive(strCondition))
					condition.setValue("true");	
				else
					condition.bIsManagecondition=true;
				//portiz_18102007_end

				naryCondition.addCondition(condition);
				}
			iNextChild++;
			cursor=  naryOp.newCursor(); //reset my cursor
		}
		return naryCondition;
	}
	//portiz_29102007_end
	
	/* 
	 //Original Source code commented
	private Condition transformNaryOp(NaryOp naryOp) throws Exception {
		BinaryCondition binaryCondition = new BinaryCondition();
		XmlCursor cursor=  naryOp.newCursor();
		cursor.toFirstChild();
		if (cursor.getObject() instanceof NaryOp)
			binaryCondition.setLeftcondition(transformNaryOp((NaryOp) cursor.getObject()));
		else
		if (cursor.getObject() instanceof BinaryOp)
			binaryCondition.setLeftcondition(transformBinaryOp((BinaryOp) cursor.getObject()));
		else
		if ((cursor.getObject() instanceof UnaryOp) ){
			model.brimport.SimpleCondition condition = new model.brimport.SimpleCondition();
			condition.setIdentifier(transformUnaryOp((UnaryOp) cursor.getObject() ));
			condition.setValue("true");
			binaryCondition.setLeftcondition(condition);
			}
		else{
			model.brimport.SimpleCondition condition = new model.brimport.SimpleCondition();
			//portiz_18102007_begin
			String strCondition=transformAnyIdentifier(cursor.getObject(),null );
			condition.setIdentifier(strCondition);
			if (!CMBRStringUtils.hasKnowedFunctions_equal_primitive(strCondition))
				condition.setValue("true");	
			else
				condition.bIsManagecondition=true;
			//portiz_18102007_end
			
			binaryCondition.setLeftcondition(condition);
			}

		binaryCondition .setLogicalOperator(operators.get(naryOp.getName()));

		XmlCursor cursor1=  naryOp.newCursor();
	cursor1.toLastChild();
		
		//paulo test
		//cursor1.toChild(1);
		
		if (cursor1.getObject() instanceof NaryOp)
			binaryCondition.setRigthCondition(transformNaryOp((NaryOp) cursor1.getObject()));
		else
		if (cursor1.getObject() instanceof BinaryOp)
			binaryCondition.setRigthCondition(transformBinaryOp((BinaryOp) cursor1.getObject()));
		else
		if ((cursor1.getObject() instanceof UnaryOp) ){
			model.brimport.SimpleCondition condition = new model.brimport.SimpleCondition();
			condition.setIdentifier(transformUnaryOp((UnaryOp) cursor1.getObject() ));
			//portiz_15102007_begin
			//Original code commented
			//condition.setValue("true");
			condition.bIsCompareTo=true;
			condition.setValue(null);
			//portiz_15102007_end
			binaryCondition.setRigthCondition(condition);
			}
		else{
			model.brimport.SimpleCondition condition = new model.brimport.SimpleCondition();
			condition.setIdentifier(transformAnyIdentifier(cursor1.getObject(),null ));
			condition.setValue("true");
			binaryCondition.setRigthCondition(condition);
			}

		return binaryCondition;
	}
		*/
	
	
	private Condition transformBinaryOp(BinaryOp binaryOp) throws Exception {
		model.brimport.SimpleCondition condition = new model.brimport.SimpleCondition();
		XmlCursor cursor=  binaryOp.newCursor();
		cursor.toFirstChild();
				
		//portiz_12102007_begin
		String __strOperator="";
		if (operators.get(binaryOp.getName())!=null)
			__strOperator=operators.get(binaryOp.getName()).get(getBrDocument().getLanguaje());
		else
			__strOperator="";
		//portiz_12102007_end
		
		condition.setIdentifier(transformAnyIdentifier(cursor.getObject(),__strOperator));
		//check if is an interval
		if(binaryOp.isSetInterval()) {
				condition.setOperator(BRKeyword.EQUAL);
				condition.setValue(transformInterval(binaryOp.getInterval()));
				return condition;
				}
		if (operators.get(binaryOp.getName())!=null)
				condition.setOperator(operators.get(binaryOp.getName()));
		XmlCursor cursor1=  binaryOp.newCursor();
		cursor1.toLastChild();
		if (binaryOp.isSetNewArray1())
			transformNewArray(condition, binaryOp.getNewArray1());
		else{
			if (!isMethod_CompareTo(cursor.getObject()))
				condition.setValue(transformAnyIdentifier(cursor1.getObject(),__strOperator));
		}
			
		if (isMethod_CompareTo(cursor.getObject()))
			condition.bIsCompareTo=true;
		
		
		return condition;
	}

	private String transformInterval(Interval interval) throws Exception {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("\"");
	
		//portiz_09112007_begin
		//Original code commented
		/*if (interval.getLeftLimit().getValue())
			stringBuilder.append("[");
		else
			stringBuilder.append("]");*/
		if (interval.getLeftLimit().getValue())
			stringBuilder.append("]");
		else
			stringBuilder.append("[");
		//portiz_09112007_end		
		
		XmlCursor cursor=  interval.newCursor();
		cursor.toChild(2);
		stringBuilder.append(transformAnyIdentifier(cursor.getObject(),null));
		stringBuilder.append(",");
		XmlCursor cursor1=  interval.newCursor();
		cursor1.toChild(3);
		stringBuilder.append(transformAnyIdentifier(cursor1.getObject(),null));

		//portiz_09112007_begin
		//Original code commented
		/*if (interval.getRightLimit().getValue())
			stringBuilder.append("]");
		else
			stringBuilder.append("[");*/
		if (interval.getRightLimit().getValue())
			stringBuilder.append("[");
		else
			stringBuilder.append("]");
		//portiz_09112007_end
		
		
		stringBuilder.append("\"");
		return stringBuilder.toString();
	}
	
	//portiz_15102007_begin
	
	private boolean isMethod(XmlObject object){
		return  (object instanceof Method);
	}
	
	private boolean isMethod_CompareTo(XmlObject object){
		if 	(object instanceof Method){
			Method myMethod=(Method)object;
			if (myMethod.getName().toLowerCase().equals("compareto"))
				return true;
		}
		return false ;
	}
	
	
	private boolean isComparison(String __strOperator){
		__strOperator=__strOperator.toLowerCase();
		return __strOperator.equals("lt") ||
			   __strOperator.equals("lte") ||
			   __strOperator.equals("gt") ||
			   __strOperator.equals("gte") ||
			   __strOperator.equals("="); 
	}
	
	
	//portiz_15102007_end
	
	
	private String transformAnyIdentifier(XmlObject object,String __strOperator) throws Exception {
		StringBuilder stringBuilder = new StringBuilder();
			
		//portiz_12102007_begin
		if (object instanceof Variable){
			String strPlaceholder= toReplacePlaceholders.replacePlaceHolder(transformVariable((Variable) object));
			stringBuilder.append(strPlaceholder);
		}
		//portiz_12102007_end
		
		//Original code commented
		/*if (object instanceof Variable)
			stringBuilder.append(transformVariable((Variable) object));
		*/
	
		if (object instanceof Constant)
			stringBuilder.append(transformConstant((Constant) object));
		if (object instanceof BinaryOp)
			stringBuilder.append(transformBinaryOperation((BinaryOp) object));
		if (object instanceof Field){
			stringBuilder.append(transformField((Field) object));
			//portiz_24102007_begin
			bomDocument.addToListOfUsedAttribures(stringBuilder.toString());
			//portiz_24102007_end			
		}
		if (object instanceof Cast)
			stringBuilder.append(transformCast((Cast) object));
		
		//Código origina comentado
		/*if (object instanceof Method)
			stringBuilder.append(transformMethod((Method) object));*/
	
		//portiz_12102007_begin
		if (object instanceof Method)
		{	
			String strCondition=transformMethod((Method) object);
			//String strAuxComparer=strCondition;
			strCondition=CMBRStringUtils.DepureCompareToNumber(strCondition,__strOperator);

			//portiz_24102007_begin
			bomDocument.addToListOfUsedAttribures(CMBRStringUtils.depureConditionals(strCondition));
			//portiz_24102007_end			
			
			//if ( strAuxComparer.equals(strCondition) )
			//	strCondition=" NATIVE[ " + strCondition + " ]";
			
			if (
					(__strOperator==null) &&
					(CMBRStringUtils.hasKnowedFunctions(strCondition))
				)
					//strCondition=" NATIVE[ " + strCondition + " ]";
					//strCondition=" \"" + strCondition + "\"";
				;
			if (
					(__strOperator==null) &&
					(!CMBRStringUtils.hasKnowedFunctions(strCondition))
				)
					strCondition=" \"" + strCondition + "\" ";
					//strCondition=" \"" + strCondition + "\"";
			stringBuilder.append(strCondition);
		}		
		//portiz_12102007_end		
		
		if (object instanceof NewArray)
			stringBuilder.append(" ( "+transformNewArray((NewArray) object)+" ) ");
		if (object instanceof New)
			stringBuilder.append(transformNew1((New) object));

		if (getNativeIdentifiers().get(stringBuilder.toString())!=null)
			return getNativeIdentifiers().get(stringBuilder.toString());
		else
			return stringBuilder.toString();
	}
	private String transformBinaryOperation(BinaryOp op) throws Exception {
		// transform an operation
		StringBuilder stringBuilder = new StringBuilder();
		XmlCursor cursor=  op.newCursor();
		cursor.toFirstChild();

		//portiz_12102007_begin
		String __strOperator="";
		if (operators.get(op.getName())!=null)
			__strOperator=operators.get(op.getName()).get(getBrDocument().getLanguaje());
		else
			__strOperator="";
		//portiz_12102007_end
						
		stringBuilder.append(transformAnyIdentifier(cursor.getObject(),__strOperator));
		//check if is an interval
		if(op.isSetInterval()) {
			stringBuilder.append(transformInterval(op.getInterval()));
			return stringBuilder.toString();
		}

		//portiz_15102007_begin
		if (isComparison(op.getName()))
			return stringBuilder.toString();
		//portiz_15102007_end		

		if (operators.get(op.getName())!=null)
			stringBuilder.append(" "+operators.get(op.getName()).get(getBrDocument().getLanguaje())+" ");
		else
			stringBuilder.append(" " +op.getName()+" ");
		XmlCursor cursor1=  op.newCursor();
		cursor1.toLastChild();
		stringBuilder.append(transformAnyIdentifier(cursor1.getObject(),__strOperator));

		return stringBuilder.toString();
	}
	private String transformNewArray(NewArray array) {
		StringBuilder stringBuilder = new StringBuilder();
		for(Variable variable : array.getInitValues().getVariableArray())
			stringBuilder.append(";"+transformVariable(variable));
		for (Constant constant :array.getInitValues().getConstantArray())
			stringBuilder.append(";"+transformConstant(constant));
		for (Field field : array.getInitValues().getFieldArray())
				stringBuilder.append(";"+transformField(field));
		for (Cast cast : array.getInitValues().getCastArray())
			stringBuilder.append(";"+transformCast(cast));
		if (stringBuilder.toString().startsWith(";"))
			return stringBuilder.substring(1);
		else
			return stringBuilder.toString();
	}
	private void transformNewArray(model.brimport.SimpleCondition condition, NewArray array) {
		for (Constant constant :array.getInitValues().getConstantArray())
			condition.getArrayValues().add(transformConstant(constant));
		for (Field field : array.getInitValues().getFieldArray())
				condition.getArrayValues().add(transformField(field));
	}
	private Condition transformSimpleCondition(SimpleCondition simpleCondition) throws Exception{
		
		model.brimport.SimpleCondition condition = new model.brimport.SimpleCondition();
		StringBuilder builder = new StringBuilder();
		// Transform the SET
		if (simpleCondition.getBind()!=null){
			//portiz_12102007_begin
			String strClassName=CMBRStringUtils.getSecondPartName(simpleCondition.getClassName());
			toReplacePlaceholders.setPlaceHolder(simpleCondition.getBind(), strClassName);
			//portiz_12102007_end
			
			condition.setIdentifier(simpleCondition.getBind());
			builder.append(simpleCondition.getClassName());
			if (simpleCondition.getEnumerator()!=null)
				builder.append(" "+ transformEnnumerator(simpleCondition.getEnumerator() ));
			else{
				XmlCursor cursor=  simpleCondition.newCursor();
				if (cursor.toLastChild())
					builder.append(" where "+transformAnyIdentifier(cursor.getObject(),null));
			}

			if (builder.toString()=="")
					condition.setValue("TRUE");
			condition.setValue(builder.toString());
			return condition;
		}
		else
		//portiz_12102007_begin 
		if (simpleCondition.getClassName()!=null ||simpleCondition.getVariable()!=null ){		
			if (simpleCondition.getEnumerator()!=null){
				String strClassName=CMBRStringUtils.getSecondPartName(simpleCondition.getClassName());
				String strEnumerator =transformEnnumeratorWithoutFrom(simpleCondition.getEnumerator());
				toReplacePlaceholders.setPlaceHolder(strEnumerator, strClassName);				
			}
		 }
		//portiz_12102007_end			 
		
		//process the ennumerator
			return null;
	}
	
	private String transformEnnumerator(Enumerator enumerator) throws Exception {
		StringBuilder stringBuilder = new StringBuilder();
		XmlCursor cursor=  enumerator.newCursor();
		cursor.toFirstChild();
		stringBuilder.append(enumerator.getName()+" "+transformAnyIdentifier(cursor.getObject(),null));

		return stringBuilder.toString();
	}
	
	private String transformEnnumeratorWithoutFrom(Enumerator enumerator) throws Exception {
		StringBuilder stringBuilder = new StringBuilder();
		XmlCursor cursor=  enumerator.newCursor();
		cursor.toFirstChild();
		stringBuilder.append(transformAnyIdentifier(cursor.getObject(),null));
		return stringBuilder.toString();
	}
	
	private String transformMethod(Method method) {
		StringBuilder stringBuilder = new StringBuilder();
		if (method.getVariable()!=null)
			stringBuilder.append(transformVariable(method.getVariable()));
		if (method.getMethod()!=null)
			stringBuilder.append(transformMethod(method.getMethod()));
		if (method.getField()!=null)
			stringBuilder.append(transformField(method.getField()));
		if (method.getConstant() !=null)
			stringBuilder.append(transformConstant(method.getConstant()));
		if (method.getClass1()!=null)
			stringBuilder.append(transformClass1(method.getClass1()));
		if (method.getCast()!=null)
			stringBuilder.append(transformCast(method.getCast()));
		
		//portiz_06112007_begin	
		String cad="."+method.getName()+"(";
		//cad=CMBRStringUtils.DepureCompareToNumber(cad,null);
		stringBuilder.append(cad);
		//portiz_06112007_end
		

		//Original source code commented
		//stringBuilder.append("."+method.getName()+"(");
		if (method.getArguments()!=null)
			stringBuilder.append(transformArguments(method.getArguments()));
		stringBuilder.append(")");
		if (stringBuilder.toString().startsWith("."))
			return stringBuilder.substring(1);
		else
			return stringBuilder.toString();
	}
	private String transformArguments(Arguments arguments) {
		StringBuilder stringBuilder = new StringBuilder();
		//constants
		for(Constant constant : arguments.getConstantArray()) {
				stringBuilder.append(",");
				stringBuilder.append( transformConstant(constant));
			}
		//fields
		for (Field field : arguments.getFieldArray()){
			stringBuilder.append(",");
			stringBuilder.append( transformField(field));
		}
		//variables
		for (Variable variable : arguments.getVariableArray()){
			stringBuilder.append(",");
			stringBuilder.append( transformVariable(variable));
		}
		//methods
		for (Method method : arguments.getMethodArray()){
			stringBuilder.append(",");
			stringBuilder.append( transformMethod(method));
		}
		//new instances
		for (New new1 : arguments.getNewArray()) {
			stringBuilder.append(",");
			stringBuilder.append( transformNew1(new1));
		}
		//casts
		for (Cast cast : arguments.getCastArray()){
			stringBuilder.append(",");
			stringBuilder.append( transformCast(cast));
		}
		if (stringBuilder.toString().startsWith(","))
			return stringBuilder.substring(1);
		else
			return stringBuilder.toString();
	}
	private String transformCast(Cast cast) {
		StringBuilder stringBuilder = new StringBuilder();
		if (isPrintCasts())
			stringBuilder.append("( "+cast.getType()+" )");
		if (cast.getVariable()!=null)
			stringBuilder.append(transformVariable(cast.getVariable()));
		if (cast.getMethod()!=null)
			stringBuilder.append(transformMethod(cast.getMethod()));
		if (cast.getField()!=null)
			stringBuilder.append(transformField(cast.getField()));
		if (cast.getConstant() !=null)
			stringBuilder.append(transformConstant(cast.getConstant()));
		if (cast.getCast()!=null)
			stringBuilder.append(transformCast(cast.getCast()));

		if (getNativeIdentifiers().get(stringBuilder.toString())!=null)
			return getNativeIdentifiers().get(stringBuilder.toString());
		else
				return stringBuilder.toString();

	}
	private String transformNew1(New new1) {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(" new "+new1.getClassName()+"(");
		for (Arguments arguments : new1.getArgumentsArray())
			stringBuilder.append(transformArguments(arguments)+",");
		if (stringBuilder.toString().endsWith(","))
			stringBuilder.deleteCharAt(stringBuilder.length()-1	);
		stringBuilder.append(")");

		if (getNativeIdentifiers().get(stringBuilder.toString())!=null)
			return getNativeIdentifiers().get(stringBuilder.toString());
		else
			return stringBuilder.toString();

	}
	private String transformConstant(Constant constant) {
		StringBuilder stringBuilder = new StringBuilder();
		if (constant.getType().equalsIgnoreCase("string"))
			stringBuilder.append("'");
		stringBuilder.append(constant.getValue());
		if (constant.getType().equalsIgnoreCase("string"))
				stringBuilder.append("'");

		if (getNativeIdentifiers().get(stringBuilder.toString())!=null)
			return getNativeIdentifiers().get(stringBuilder.toString());
		else
				return stringBuilder.toString();
	}
	private String transformField(Field field) {
			
		StringBuilder stringBuilder = new StringBuilder();

		//portiz_12102007_begin
		String placeholder;
		if (field.getVariable()!=null){
			placeholder= toReplacePlaceholders.replacePlaceHolder(transformVariable(field.getVariable()));
			stringBuilder.append(placeholder );
		}
		//portiz_12102007_end
		/* Código origina comentado
		  if (field.getVariable()!=null)
			stringBuilder.append( transformVariable(field.getVariable()));*/			
		if (field.getMethod()!=null)
			stringBuilder.append(transformMethod(field.getMethod()));
		if (field.getField()!=null)
			stringBuilder.append(transformField(field.getField()));
		if (field.getClass1()!=null)
			stringBuilder.append(transformClass1(field.getClass1()));
		if (field.isSetCast())
			stringBuilder.append(transformCast(field.getCast()));
		stringBuilder.append("."+field.getName());

		if (getNativeIdentifiers().get(stringBuilder.toString())!=null)
			return getNativeIdentifiers().get(stringBuilder.toString());
		else
				return stringBuilder.toString();
	}
	private String transformClass1(Class class1) {
		return class1.getClassName();
	}
	private String transformVariable(Variable variable) {
		return variable.getName();
	}
//Proxy to the document
	public void setLanguaje(BRLanguaje languaje) {
		getBrDocument().setLanguaje(languaje);

	}
	public boolean isPrintCasts() {
		return printCasts;
	}
	public void setPrintCasts(boolean printCasts) {
		this.printCasts = printCasts;
	}
	public Map<String, String> getNativeIdentifiers() {
		if (nativeIdentifiers == null)
			nativeIdentifiers = new HashMap<String, String>();
		return nativeIdentifiers;
	}
	public void addProgressListener(CMProgressListener rl) {
		getHandler().addProgressListener(rl);

	}

	public void removeProgressListener(CMProgressListener rl) {
		getHandler().removeProgressListener(rl);

	}

	public CMProgressEventHandler getHandler() {
		if (handler == null)
			handler = new CMProgressEventHandler();
		return handler;
	}
	public String getWarningMessages() {
		// TODO Auto-generated method stub
		return "";
	}
	private BRDocument getBrDocument() {
		if (brDocument== null)
			brDocument = new BRDocument();
		return brDocument;
	}
}