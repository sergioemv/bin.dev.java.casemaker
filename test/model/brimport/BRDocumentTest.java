package model.brimport;

import java.io.StringReader;
import java.util.Set;

import org.apache.log4j.PropertyConfigurator;

import bi.controller.BRParseProcessManager;
import bi.view.mainframeviews.CMApplication;
import junit.framework.TestCase;


public class BRDocumentTest extends TestCase {

	private BRDocument importedFile;
	private BRDocument document,document2;
	private  BRParseProcessManager parseProcessManager;
	@Override
	protected void setUp() throws Exception {
		PropertyConfigurator.configure(CMApplication.getInstallationDirectory()+"/log.properties");
		importedFile = new BRDocument();
		importedFile.setName("Testing BR import model");
		//importedFile.addDefinition(new Definition("Name","Value"));
		//importedFile.getDefinitions().get(0).setLabel("Test label to explain the variable");
		importedFile.setLanguaje(BRLanguaje.ENGLISH);
		importedFile.addBRPackage(new BRPackage());
		importedFile.getBRPackages().get(0).setName("Package 1");
		importedFile.getBRPackages().get(0).addIfBlock(new IFBlock());
		importedFile.getBRPackages().get(0).getIfBlocks().get(0).setName("group of conditions 1");
		importedFile.getBRPackages().get(0).getIfBlocks().get(0).setIfPart(new IFPart());
		importedFile.getBRPackages().get(0).getIfBlocks().get(0).getIfPart().setCondition(new BinaryCondition());
		((BinaryCondition)importedFile.getBRPackages().get(0).getIfBlocks().get(0).getIfPart().getCondition()).setLeftcondition(new SimpleCondition());
		((SimpleCondition)((BinaryCondition)importedFile.getBRPackages().get(0).getIfBlocks().get(0).getIfPart().getCondition()).getLeftcondition()).setIdentifier("test Element");
		((SimpleCondition)((BinaryCondition)importedFile.getBRPackages().get(0).getIfBlocks().get(0).getIfPart().getCondition()).getLeftcondition()).setOperator(BRKeyword.GREATER);
		((SimpleCondition)((BinaryCondition)importedFile.getBRPackages().get(0).getIfBlocks().get(0).getIfPart().getCondition()).getLeftcondition()).setValue("test Value");
		((BinaryCondition)importedFile.getBRPackages().get(0).getIfBlocks().get(0).getIfPart().getCondition()).setRigthCondition(new SimpleCondition());
		((SimpleCondition)((BinaryCondition)importedFile.getBRPackages().get(0).getIfBlocks().get(0).getIfPart().getCondition()).getRigthCondition()).setIdentifier("testElement2");
		((SimpleCondition)((BinaryCondition)importedFile.getBRPackages().get(0).getIfBlocks().get(0).getIfPart().getCondition()).getRigthCondition()).setOperator(BRKeyword.LOWER_EQUAL );
		((SimpleCondition)((BinaryCondition)importedFile.getBRPackages().get(0).getIfBlocks().get(0).getIfPart().getCondition()).getRigthCondition()).setValue("testValue3");
		importedFile.getBRPackages().get(0).getIfBlocks().get(0).getIfPart().setChildIfPart(new IFPart());
		importedFile.getBRPackages().get(0).getIfBlocks().get(0).getIfPart().getChildIfPart().setCondition(new SimpleCondition());
		((SimpleCondition)(importedFile.getBRPackages().get(0).getIfBlocks().get(0).getIfPart().getChildIfPart().getCondition())).setIdentifier("This is an invalid identifier!");
		((SimpleCondition)(importedFile.getBRPackages().get(0).getIfBlocks().get(0).getIfPart().getChildIfPart().getCondition())).setValue(">>");
		((SimpleCondition)(importedFile.getBRPackages().get(0).getIfBlocks().get(0).getIfPart().getChildIfPart().getCondition())).setOperator(BRKeyword.LOWER);
		importedFile.getBRPackages().get(0).getIfBlocks().get(0).getIfPart().getChildIfPart().addBusinessAction(new BusinessAction());
		importedFile.getBRPackages().get(0).getIfBlocks().get(0).getIfPart().getChildIfPart().getBusinessActions().get(0).setState(BRKeyword.FAULTY);
		importedFile.getBRPackages().get(0).getIfBlocks().get(0).getIfPart().getChildIfPart().getBusinessActions().get(0).setBody("Example content of a business rule");
		importedFile.getBRPackages().get(0).getIfBlocks().get(0).getIfPart().getChildIfPart().getBusinessActions().get(0).getRequirements().add("MUST be litle");
		importedFile.getBRPackages().get(0).getIfBlocks().get(0).getIfPart().getChildIfPart().getBusinessActions().get(0).getRequirements().add("AnD nice");
		importedFile.getBRPackages().get(0).getIfBlocks().get(0).getIfPart().getChildIfPart().getBusinessActions().get(0).getExpectedResults().put("A","124542");
		importedFile.getBRPackages().get(0).getIfBlocks().get(0).getIfPart().getChildIfPart().getBusinessActions().get(0).getExpectedResults().put("Z","newValue");
		importedFile.getBRPackages().get(0).getIfBlocks().get(0).getIfPart().getChildIfPart().getBusinessActions().get(0).setRiskLevel(9);
		importedFile.getBRPackages().get(0).getIfBlocks().get(0).getIfPart().getChildIfPart().setElsePart(new ElsePart());
		importedFile.getBRPackages().get(0).getIfBlocks().get(0).getIfPart().getChildIfPart().getElsePart().addBusinessAction(new BusinessAction("Die!"));

		document = new BRDocument();
		document.setName("Testing BR import model");
		document.setLanguaje(BRLanguaje.GERMAN);
		document.addIfBlock(new IFBlock());
		document.getIfBlocks().get(0).setName("group of conditions 1");
		document.getIfBlocks().get(0).setIfPart(new IFPart());
		document.getIfBlocks().get(0).getIfPart().setCondition(new BinaryCondition());
		((BinaryCondition)document.getIfBlocks().get(0).getIfPart().getCondition()).setLeftcondition(new SimpleCondition());
		((SimpleCondition)((BinaryCondition)document.getIfBlocks().get(0).getIfPart().getCondition()).getLeftcondition()).setIdentifier("testElement");
		((SimpleCondition)((BinaryCondition)document.getIfBlocks().get(0).getIfPart().getCondition()).getLeftcondition()).setOperator(BRKeyword.GREATER);
		((SimpleCondition)((BinaryCondition)document.getIfBlocks().get(0).getIfPart().getCondition()).getLeftcondition()).setValue("testValue");
		((BinaryCondition)document.getIfBlocks().get(0).getIfPart().getCondition()).setRigthCondition(new SimpleCondition());
		((SimpleCondition)((BinaryCondition)document.getIfBlocks().get(0).getIfPart().getCondition()).getRigthCondition()).setIdentifier("testElement2");
		((SimpleCondition)((BinaryCondition)document.getIfBlocks().get(0).getIfPart().getCondition()).getRigthCondition()).setOperator(BRKeyword.LOWER_EQUAL );
		((SimpleCondition)((BinaryCondition)document.getIfBlocks().get(0).getIfPart().getCondition()).getRigthCondition()).setValue("testValue3");
		document.getIfBlocks().get(0).getIfPart().setChildIfPart(new IFPart());
		document.getIfBlocks().get(0).getIfPart().getChildIfPart().setCondition(new SimpleCondition());
		((SimpleCondition)(document.getIfBlocks().get(0).getIfPart().getChildIfPart().getCondition())).setIdentifier("This is an invalid identifier!");
		((SimpleCondition)(document.getIfBlocks().get(0).getIfPart().getChildIfPart().getCondition())).setValue(">>");
		((SimpleCondition)(document.getIfBlocks().get(0).getIfPart().getChildIfPart().getCondition())).setOperator(BRKeyword.LOWER);
		document.getIfBlocks().get(0).getIfPart().getChildIfPart().addBusinessAction(new BusinessAction());
		document.getIfBlocks().get(0).getIfPart().getChildIfPart().getBusinessActions().get(0).setState(BRKeyword.FAULTY);
		document.getIfBlocks().get(0).getIfPart().getChildIfPart().getBusinessActions().get(0).setBody("Example content of a business rule");
		document.getIfBlocks().get(0).getIfPart().getChildIfPart().getBusinessActions().get(0).getRequirements().add("MUST be litle");
		document.getIfBlocks().get(0).getIfPart().getChildIfPart().getBusinessActions().get(0).getRequirements().add("AnD nice");
		document.getIfBlocks().get(0).getIfPart().getChildIfPart().getBusinessActions().get(0).getExpectedResults().put("A","124542");
		document.getIfBlocks().get(0).getIfPart().getChildIfPart().getBusinessActions().get(0).getExpectedResults().put("Z","newValue");
		document.getIfBlocks().get(0).getIfPart().getChildIfPart().getBusinessActions().get(0).setRiskLevel(9);


		document2 = new BRDocument();
		document2.setName("Testing BR import model");
		document2.setLanguaje(BRLanguaje.ENGLISH);
		document2.addIfBlock(new IFBlock());
		document2.getIfBlocks().get(0).setName("group of conditions 1");
		document2.getIfBlocks().get(0).setIfPart(new IFPart());
		document2.getIfBlocks().get(0).getIfPart().setCondition(new BinaryCondition());
		((BinaryCondition)document2.getIfBlocks().get(0).getIfPart().getCondition()).setLeftcondition(new SimpleCondition());
		((SimpleCondition)((BinaryCondition)document2.getIfBlocks().get(0).getIfPart().getCondition()).getLeftcondition()).setIdentifier("testElement");
		((SimpleCondition)((BinaryCondition)document2.getIfBlocks().get(0).getIfPart().getCondition()).getLeftcondition()).setOperator(BRKeyword.GREATER);
		((SimpleCondition)((BinaryCondition)document2.getIfBlocks().get(0).getIfPart().getCondition()).getLeftcondition()).setValue("testValue");
		((BinaryCondition)document2.getIfBlocks().get(0).getIfPart().getCondition()).setRigthCondition(new SimpleCondition());
		((SimpleCondition)((BinaryCondition)document2.getIfBlocks().get(0).getIfPart().getCondition()).getRigthCondition()).setIdentifier("testElement2");
		((SimpleCondition)((BinaryCondition)document2.getIfBlocks().get(0).getIfPart().getCondition()).getRigthCondition()).setOperator(BRKeyword.LOWER_EQUAL );
		((SimpleCondition)((BinaryCondition)document2.getIfBlocks().get(0).getIfPart().getCondition()).getRigthCondition()).setValue("testValue3");
		document2.getIfBlocks().get(0).getIfPart().setChildIfPart(new IFPart());
		document2.getIfBlocks().get(0).getIfPart().getChildIfPart().addBusinessAction(new BusinessAction());
		document2.getIfBlocks().get(0).getIfPart().getChildIfPart().getBusinessActions().get(0).setState(BRKeyword.FAULTY);
		document2.getIfBlocks().get(0).getIfPart().getChildIfPart().getBusinessActions().get(0).setBody("Example content of a business rule");
		document2.getIfBlocks().get(0).getIfPart().getChildIfPart().getBusinessActions().get(0).getRequirements().add("MUST be litle");
		document2.getIfBlocks().get(0).getIfPart().getChildIfPart().getBusinessActions().get(0).getRequirements().add("AnD nice");
		document2.getIfBlocks().get(0).getIfPart().getChildIfPart().getBusinessActions().get(0).getExpectedResults().put("A","124542");
		document2.getIfBlocks().get(0).getIfPart().getChildIfPart().getBusinessActions().get(0).getExpectedResults().put("Z","newValue");
		document2.getIfBlocks().get(0).getIfPart().getChildIfPart().getBusinessActions().get(0).setRiskLevel(9);

		parseProcessManager = new BRParseProcessManager();
	}
	public void testToString() {
		System.out.println(importedFile.toString());
		assertEquals(parseProcessManager.doCheck(new StringReader(importedFile.toString()),0,true),"ok");
		System.out.println(document.toString());
		assertEquals(parseProcessManager.doCheck(new StringReader(document.toString()),1,true),"ok");
		System.out.println(document2.toString());
		assertEquals(parseProcessManager.doCheck(new StringReader(document2.toString()),0,true),"ok");
	}

	public void testGetInvalidIdentifiers() {
		Set<String> invalidIdentifiers = importedFile.getInvalidIdentifiers();
		assertTrue(invalidIdentifiers.contains("test Value"));
		assertTrue(invalidIdentifiers.contains("test Element"));
		assertTrue(document.getInvalidIdentifiers().contains("This is an invalid identifier!"));

	}

}