package bi.view.resultscomparationviews;

import java.awt.Event;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Date;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;

import model.BusinessRules;
import model.ITypeData;
import model.ResultComparation;
import model.Session2;
import model.StructureTestData;
import model.TDStructure;
import model.TestData;
import model.TestDataCombinations;
import model.TestDataSet;
import model.TestObject;
import model.TestObjectReference;
import bi.view.icons.CMIcon;
import bi.view.lang.CMMessages;
import bi.view.mainframeviews.CMFrameView;
import bi.view.testdataviews.CMGridTDStructure;
import bi.view.testdataviews.CMIndexTDStructureUpdate;
import bi.view.testdataviews.CMScrollPaneTestDataCombinationsDescription;
import bi.view.testdataviews.CMScrollPaneTestDataSetDescription;
import bi.view.testdataviews.CMScrollpaneStructureDescription;
import bi.view.utils.ScrollBarSynchronizer;

/**
 * Developed by BUSINESS SOFTWARE INNOVATIONS.
 * Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved.
 */
public class CMResultsComparationPanelView extends JPanel {
    /** Creates new form CMResultComparationPanelView */
    public CMResultsComparationPanelView(CMFrameView p_Frame) {
        m_Frame = p_Frame;
        jScrollPaneTestDataSetTarget = new CMScrollPaneTestDataSetDescription(m_Frame);
        jScrollPaneTestDataSetActual = new CMScrollPaneTestDataSetDescription(m_Frame);
        jScrollPaneTestDataTarget = new CMScrollPaneTestDataCombinationsDescription(m_Frame);
        jScrollPaneTestDataActual = new CMScrollPaneTestDataCombinationsDescription(m_Frame);
        jScrollPaneStructureHeaderTarget = new CMScrollpaneStructureDescription(m_Frame);
        jScrollPaneStructureHeaderActual = new CMScrollpaneStructureDescription(m_Frame);
       
        gridTDStructureTarget = new CMGridTDStructure(m_Frame);
        gridTDStructureActual = new CMGridTDStructure(m_Frame);
              
        initGUI();
    }

    /** This method is called from within the constructor to initialize the form. */
    private void initGUI() {
        jSplitPane1.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
        jSplitPane1.setResizeWeight(0.15d);
        jSplitPane1.add(jSplitPane2, javax.swing.JSplitPane.BOTTOM);
        jSplitPane1.add(jSplitPaneTestDataSet, javax.swing.JSplitPane.TOP);
        setLayout(new java.awt.BorderLayout());
        add(jSplitPane5, java.awt.BorderLayout.NORTH);
        add(jSplitPane1, java.awt.BorderLayout.CENTER);
        jSplitPane2.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
        jSplitPane2.setResizeWeight(0.2d);
        jSplitPane2.add(jSplitPane3, javax.swing.JSplitPane.BOTTOM);
        jSplitPane2.add(jSplitPaneTestData, javax.swing.JSplitPane.TOP);
        jSplitPane3.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
        jSplitPane3.setResizeWeight(0.3d);
        jSplitPane3.add(jSplitPane4, javax.swing.JSplitPane.BOTTOM);
        jSplitPane3.add(jSplitPaneStructureHeader, javax.swing.JSplitPane.TOP);
        jSplitPane4.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
        jSplitPane4.setResizeWeight(0.5d);
        jSplitPane4.add(jSplitPaneStructure, javax.swing.JSplitPane.TOP);
        jSplitPane4.add(jScrollPaneReport, javax.swing.JSplitPane.BOTTOM);
        jSplitPaneTestDataSet.setResizeWeight(0.5d);
        jSplitPaneTestDataSet.add(jScrollPaneTestDataSetTarget, javax.swing.JSplitPane.LEFT);
        jSplitPaneTestDataSet.add(jScrollPaneTestDataSetActual, javax.swing.JSplitPane.RIGHT);
        jSplitPaneTestData.setResizeWeight(0.5d);
        jSplitPaneTestData.add(jScrollPaneTestDataTarget, javax.swing.JSplitPane.LEFT);
        jSplitPaneTestData.add(jScrollPaneTestDataActual, javax.swing.JSplitPane.RIGHT);
        jSplitPaneStructureHeader.setResizeWeight(0.5d);
        jSplitPaneStructureHeader.add(jScrollPaneStructureHeaderTarget, javax.swing.JSplitPane.LEFT);
        jSplitPaneStructureHeader.add(jScrollPaneStructureHeaderActual, javax.swing.JSplitPane.RIGHT);
        jSplitPaneStructure.setResizeWeight(0.5d);
        jSplitPaneStructure.add(paneGridTDStructureTarget, javax.swing.JSplitPane.LEFT);
        jSplitPaneStructure.add(paneGridTDStructureActual, javax.swing.JSplitPane.RIGHT);
        jEditorPane1.setText(""); //$NON-NLS-1$
        jEditorPane1.setBounds(new java.awt.Rectangle(358, 76, 6, 22));
        jEditorPane1.setEditable(false);
        jEditorPane1.setContentType("text/pain"); //$NON-NLS-1$
        jScrollPaneReport.getViewport().add(jEditorPane1);
        
        paneGridTDStructureActual.setViewportView(gridTDStructureActual);
        paneGridTDStructureTarget.setViewportView(gridTDStructureTarget);        
        
        addHorizontalScrollBarSynchronizer(paneGridTDStructureActual,paneGridTDStructureTarget);
        addVerticalScrollBarSynchronizer(paneGridTDStructureActual,paneGridTDStructureTarget);
        addHorizontalScrollBarSynchronizer(paneGridTDStructureTarget,paneGridTDStructureActual);
        addVerticalScrollBarSynchronizer(paneGridTDStructureTarget,paneGridTDStructureActual);

        jButton1.setText(CMMessages.getString("RESULT_COMPARISON_EXPECTED")); //$NON-NLS-1$
        //jButton1.setBackground(new java.awt.Color(252,254,252));
        //jButton1.setForeground(new java.awt.Color(255,255,255));
        jButton1.setFont(new java.awt.Font("SansSerif", java.awt.Font.BOLD, 14)); //$NON-NLS-1$
        jSplitPane5.setResizeWeight(0.5d);
        jSplitPane5.add(jButton1, javax.swing.JSplitPane.LEFT);
        jSplitPane5.add(jButton2, javax.swing.JSplitPane.RIGHT);
        jButton2.setText(CMMessages.getString("RESULT_COMPARISON_ACTUAL")); //$NON-NLS-1$
        jButton2.setFont(new java.awt.Font("SansSerif", java.awt.Font.BOLD, 14)); //$NON-NLS-1$
        jSplitPane1.setResizeWeight(0.15d);
        jSplitPaneTestDataSet.addPropertyChangeListener(
            new PropertyChangeListener() {
                public void propertyChange(PropertyChangeEvent e) { jSplitPaneTestDataSetPropertyChange(e); }
            });
        jSplitPane5.addPropertyChangeListener(
            new PropertyChangeListener() {
                public void propertyChange(PropertyChangeEvent e) { jSplitPane5PropertyChange(e); }
            });
        jSplitPaneTestData.addPropertyChangeListener(
            new PropertyChangeListener() {
                public void propertyChange(PropertyChangeEvent e) { jSplitPaneTestDataPropertyChange(e); }
            });
        jSplitPaneStructureHeader.addPropertyChangeListener(
            new PropertyChangeListener() {
                public void propertyChange(PropertyChangeEvent e) { jSplitPaneStructureHeaderPropertyChange(e); }
            });
        jSplitPaneStructure.addPropertyChangeListener(
            new PropertyChangeListener() {
                public void propertyChange(PropertyChangeEvent e) { jSplitPaneStructurePropertyChange(e); }
            });
//hcanedo_18_10_2004_begin
        jEditorPane1.addMouseListener(new MouseAdapter(){public void mouseClicked(MouseEvent e){jEditorPane1MouseClicked(e);}});
//hcanedo_18_10_2004_end
    }

    public TestObject getM_TestObject() {
        return m_TestObject;
    }

    public void setM_TestObject(TestObject p_TestObject, TestObjectReference p_TestObjectReference, Session2 p_Session) {
        m_Session = p_Session;
        m_TestObject = p_TestObject;
        m_TestObjectReference = p_TestObjectReference;
    }

    public ResultComparation getM_ResultComparation() { return m_ResultComparation; }

    public void setM_ResultComparation(ResultComparation m_ResultComparation) {
        this.m_ResultComparation = m_ResultComparation;
        covertVectorsToTDStructures();
        chargeData();
    }

    public void covertVectorsToTDStructures() {
        Vector target = m_ResultComparation.getTestDataSetTarget();
        Vector actual = m_ResultComparation.getTestDataSetActual();
        m_TDStructureTarget.setM_TestDataSet(new Vector());
        m_TDStructureTarget.setSwActualTarget(true);
        m_TDStructureActual.setM_TestDataSet(new Vector());
        for (int i = 0; i < target.size(); i++) {
            TestDataSet obj = searchTestDataSet((String)target.elementAt(i));
            m_TDStructureTarget.getM_TestDataSet().addElement(obj);
        }
        for (int i = 0; i < actual.size(); i++) {
            TestDataSet obj = searchTestDataSet((String)actual.elementAt(i));
            m_TDStructureActual.getM_TestDataSet().addElement(obj);
        }
    }

    public void chargeData() {
        jScrollPaneTestDataSetTarget.setM_TDStructure(m_TDStructureTarget);
        jScrollPaneTestDataSetActual.setM_TDStructure(m_TDStructureActual);
        TestDataCombinations testDataTarget;
        TestDataCombinations testDataActual;
        if (m_TDStructureTarget.getM_TestDataSet().size() == 0)
            testDataTarget = new TestDataCombinations();
        else
            testDataTarget = ((TestDataSet)m_TDStructureTarget.getM_TestDataSet().elementAt(CMIndexTDStructureUpdate.getInstance().getindexTestDataSetTarget())).getM_TestDataCombinations();
        if (m_TDStructureActual.getM_TestDataSet().size() == 0)
            testDataActual = new TestDataCombinations();
        else
            testDataActual = ((TestDataSet)m_TDStructureActual.getM_TestDataSet().elementAt(CMIndexTDStructureUpdate.getInstance().getindexTestDataSetActual())).getM_TestDataCombinations();
        testDataTarget.setM_swTargetActual(true);
        jScrollPaneTestDataTarget.setM_TestDataCombinations(testDataTarget);
        jScrollPaneTestDataActual.setM_TestDataCombinations(testDataActual);
        TDStructure structureTarget;
        TDStructure structureActual;
        if (testDataTarget.getM_TestDatas().size() == 0)
            structureTarget = new TDStructure();
        else
            structureTarget = ((TestData)testDataTarget.getM_TestDatas().elementAt(CMIndexTDStructureUpdate.getInstance().getindexTestDataTarget())).getM_TDStructure();
        if (testDataActual.getM_TestDatas().size() == 0)
            structureActual = new TDStructure();
        else
            structureActual = ((TestData)testDataActual.getM_TestDatas().elementAt(CMIndexTDStructureUpdate.getInstance().getindexTestDataActual())).getM_TDStructure();
        structureTarget.setSwActualTarget(true);
        gridTDStructureTarget.setTDStructure(structureTarget);
        gridTDStructureActual.setTDStructure(structureActual);
        jScrollPaneStructureHeaderTarget.setM_TDStructure(structureTarget);
        jScrollPaneStructureHeaderActual.setM_TDStructure(structureActual);
        jScrollPaneStructureHeaderActual.update();
        jScrollPaneStructureHeaderTarget.update();
        jScrollPaneTestDataActual.update();
        jScrollPaneTestDataTarget.update();
        jScrollPaneTestDataSetActual.update();
        jScrollPaneTestDataSetTarget.update();
        gridTDStructureTarget.update(CMIndexTDStructureUpdate.getInstance().getindexTarget());
        gridTDStructureActual.update(CMIndexTDStructureUpdate.getInstance().getindexActual());
        jEditorPane1.setText(""); //$NON-NLS-1$
        ReportResultComparation();
    }

    public void update() {
        jScrollPaneTestDataSetTarget.setM_TDStructure(m_TDStructureTarget);
        jScrollPaneTestDataSetActual.setM_TDStructure(m_TDStructureActual);
        TestDataCombinations testDataTarget;
        TestDataCombinations testDataActual;
        if (m_TDStructureTarget.getM_TestDataSet().size() == 0)
            testDataTarget = new TestDataCombinations();
        else
            testDataTarget = ((TestDataSet)m_TDStructureTarget.getM_TestDataSet().elementAt(CMIndexTDStructureUpdate.getInstance().getindexTestDataSetTarget())).getM_TestDataCombinations();
        if (m_TDStructureActual.getM_TestDataSet().size() == 0)
            testDataActual = new TestDataCombinations();
        else
            testDataActual = ((TestDataSet)m_TDStructureActual.getM_TestDataSet().elementAt(CMIndexTDStructureUpdate.getInstance().getindexTestDataSetActual())).getM_TestDataCombinations();
        testDataTarget.setM_swTargetActual(true);
        jScrollPaneTestDataTarget.setM_TestDataCombinations(testDataTarget);
        jScrollPaneTestDataActual.setM_TestDataCombinations(testDataActual);
        TDStructure structureTarget;
        TDStructure structureActual;
        if (testDataTarget.getM_TestDatas().size() == 0)
            structureTarget = new TDStructure();
        else
            structureTarget = ((TestData)testDataTarget.getM_TestDatas().elementAt(CMIndexTDStructureUpdate.getInstance().getindexTestDataTarget())).getM_TDStructure();
        if (testDataActual.getM_TestDatas().size() == 0)
            structureActual = new TDStructure();
        else
            structureActual = ((TestData)testDataActual.getM_TestDatas().elementAt(CMIndexTDStructureUpdate.getInstance().getindexTestDataActual())).getM_TDStructure();
        structureTarget.setSwActualTarget(true);
        gridTDStructureTarget.setTDStructure(structureTarget);
        gridTDStructureActual.setTDStructure(structureActual);
        jScrollPaneStructureHeaderTarget.setM_TDStructure(structureTarget);
        jScrollPaneStructureHeaderActual.setM_TDStructure(structureActual);
        jScrollPaneStructureHeaderActual.update(CMIndexTDStructureUpdate.getInstance().getindexActual());
        jScrollPaneStructureHeaderTarget.update(CMIndexTDStructureUpdate.getInstance().getindexTarget());
        jScrollPaneTestDataActual.update(CMIndexTDStructureUpdate.getInstance().getindexTestDataActual());
        jScrollPaneTestDataTarget.update(CMIndexTDStructureUpdate.getInstance().getindexTestDataTarget());
        jScrollPaneTestDataSetActual.update(CMIndexTDStructureUpdate.getInstance().getindexTestDataSetActual());
        jScrollPaneTestDataSetTarget.update(CMIndexTDStructureUpdate.getInstance().getindexTestDataSetTarget());
        gridTDStructureTarget.update(CMIndexTDStructureUpdate.getInstance().getindexTarget());
        gridTDStructureActual.update(CMIndexTDStructureUpdate.getInstance().getindexActual());
        jEditorPane1.setText(""); //$NON-NLS-1$
        ReportResultComparation();
        jEditorPane1.setCaretPosition(0);
    }
	 public void updateStructureGridActual() {
		 gridTDStructureActual.update(CMIndexTDStructureUpdate.getInstance().getindexActual());
    }
  	 public void updateStructureGridTarget() {
  		gridTDStructureTarget.update(CMIndexTDStructureUpdate.getInstance().getindexTarget());
    }
	 public void updateTestDataActual() {
        	
        jScrollPaneTestDataSetActual.setM_TDStructure(m_TDStructureActual);
        
        TestDataCombinations testDataActual;
        if (m_TDStructureActual.getM_TestDataSet().size() == 0)
            testDataActual = new TestDataCombinations();
        else
            testDataActual = ((TestDataSet)m_TDStructureActual.getM_TestDataSet().elementAt(CMIndexTDStructureUpdate.getInstance().getindexTestDataSetActual())).getM_TestDataCombinations();
        jScrollPaneTestDataActual.setM_TestDataCombinations(testDataActual);
        TDStructure structureActual;
        if (testDataActual.getM_TestDatas().size() == 0)
            structureActual = new TDStructure();
        else
            structureActual = ((TestData)testDataActual.getM_TestDatas().elementAt(CMIndexTDStructureUpdate.getInstance().getindexTestDataActual())).getM_TDStructure();
        gridTDStructureActual.setTDStructure(structureActual);
        jScrollPaneStructureHeaderActual.setM_TDStructure(structureActual);
        jScrollPaneStructureHeaderActual.update(CMIndexTDStructureUpdate.getInstance().getindexActual());
        jScrollPaneTestDataActual.update(CMIndexTDStructureUpdate.getInstance().getindexTestDataActual());
        jScrollPaneTestDataSetActual.update(CMIndexTDStructureUpdate.getInstance().getindexTestDataSetActual());
        gridTDStructureActual.update(CMIndexTDStructureUpdate.getInstance().getindexActual());
        jEditorPane1.setText(""); //$NON-NLS-1$
        ReportResultComparation();
        jEditorPane1.setCaretPosition(0);
        /*
        jScrollPaneTestDataSetActual.update(CMIndexTDStructureUpdate.getInstance().getindexTestDataSetActual());
        jScrollPaneStructureHeaderActual.update(CMIndexTDStructureUpdate.getInstance().getindexActual());
        updateStructureGridActual();*/
    }
  	 public void updateTestDataTarget() {
      	jScrollPaneTestDataSetTarget.setM_TDStructure(m_TDStructureTarget);
        TestDataCombinations testDataTarget;

        if (m_TDStructureTarget.getM_TestDataSet().size() == 0)
            testDataTarget = new TestDataCombinations();
        else
            testDataTarget = ((TestDataSet)m_TDStructureTarget.getM_TestDataSet().elementAt(CMIndexTDStructureUpdate.getInstance().getindexTestDataSetTarget())).getM_TestDataCombinations();
        testDataTarget.setM_swTargetActual(true);
        jScrollPaneTestDataTarget.setM_TestDataCombinations(testDataTarget);
        TDStructure structureTarget;
        if (testDataTarget.getM_TestDatas().size() == 0)
            structureTarget = new TDStructure();
        else
            structureTarget = ((TestData)testDataTarget.getM_TestDatas().elementAt(CMIndexTDStructureUpdate.getInstance().getindexTestDataTarget())).getM_TDStructure();
                structureTarget.setSwActualTarget(true);
        gridTDStructureTarget.setTDStructure(structureTarget);
        jScrollPaneStructureHeaderTarget.setM_TDStructure(structureTarget);
        jScrollPaneStructureHeaderTarget.update(CMIndexTDStructureUpdate.getInstance().getindexTarget());
        jScrollPaneTestDataTarget.update(CMIndexTDStructureUpdate.getInstance().getindexTestDataTarget());
        jScrollPaneTestDataSetTarget.update(CMIndexTDStructureUpdate.getInstance().getindexTestDataSetTarget());
        gridTDStructureTarget.update(CMIndexTDStructureUpdate.getInstance().getindexTarget());
        jEditorPane1.setText(""); //$NON-NLS-1$
        ReportResultComparation();
        jEditorPane1.setCaretPosition(0);
    /*
       jScrollPaneTestDataSetTarget.update(CMIndexTDStructureUpdate.getInstance().getindexTestDataSetTarget());
       jScrollPaneStructureHeaderTarget.update(CMIndexTDStructureUpdate.getInstance().getindexTarget());
       updateStructureGridTarget();*/
    }
    public TestDataSet searchTestDataSet(String p_name) {
        TestDataSet returnTDS = new TestDataSet(new TDStructure());
        for (int j = 0; j < m_TDStructure.getM_TestDataSet().size(); j++) {
            TestDataSet tds = (TestDataSet)m_TDStructure.getM_TestDataSet().elementAt(j);
            if (tds.getName().equals(p_name))
                returnTDS = tds;
        }
        return returnTDS;
    }

    public TDStructure getM_TDStructure() { return m_TDStructure; }

    public void setM_TDStructure(TDStructure m_TDStructure) {
        this.m_TDStructure = m_TDStructure;
        setM_ResultComparation(m_TDStructure.getM_ResultComparation());
    }

    public Vector findKey(StructureTestData p_StructureTestData) {
        Vector result = new Vector();
        for (int i = 0; i < p_StructureTestData.getTypeData().size(); i++) {
            ITypeData typ = (ITypeData)p_StructureTestData.getTypeData().elementAt(i);
            if (typ.getKey().equals("K")) {
                //result.add(typ.getStringValue().toUpperCase());
            	result.add(typ.getFormattedValue().toUpperCase());
            }
        }
        return result;
    }

    public String stringKey(StructureTestData p_StructureTestData) {
        Vector keys = findKey(p_StructureTestData);
        StringBuffer result = new StringBuffer(CMMessages.getString("RESULT_COMPARISON_STRING_KEY_OPEN"));
        for (int i = 0; i < keys.size() - 1; i++) {
            result.append(keys.elementAt(i).toString());
            result.append(",");
        }
        if (keys.size() != 0)
            result.append(keys.lastElement().toString());
        result.append(CMMessages.getString("RESULT_COMPARISON_STRING_KEY_CLOSE"));
        return result.toString();
    }

    public Vector findFieldKey(StructureTestData p_StructureTestData) {
        Vector result = new Vector();
        for (int i = 0; i < p_StructureTestData.getTypeData().size(); i++) {
            ITypeData typ = (ITypeData)p_StructureTestData.getTypeData().elementAt(i);
            if (typ.getKey().equals("K")) {
                result.add(typ.getField().toUpperCase());
            }
        }
        return result;
    }

    public boolean compareKeys(StructureTestData m_Target, StructureTestData m_Actual) {
        // String targetkey= findKey(m_Target);
        //  String actualkey= findKey(m_Actual);
        Vector targetFieldkey = findFieldKey(m_Target);
        Vector actualFieldkey = findFieldKey(m_Actual);
        if (/*targetkey.equalsIgnoreCase(actualkey) &&*/ targetFieldkey.containsAll(actualFieldkey) &&
            actualFieldkey.containsAll(targetFieldkey))
                return true;
        else
            return false;
    }

    public Vector findFields(StructureTestData p_StructureTestData) {
        Vector result = new Vector();
        for (int i = 0; i < p_StructureTestData.getTypeData().size(); i++) {
            ITypeData typ = (ITypeData)p_StructureTestData.getTypeData().elementAt(i);
            result.add(typ.getField().toUpperCase());
        }
        return result;
    }

    public String menssageFieldsMissing(StructureTestData p_Target, StructureTestData p_Actual, String p_NameTarget,
        String p_NameActual, boolean swXML) {
            StringBuffer menssage = new StringBuffer();
            Vector targetFields = findFields(p_Target);
            Vector actualFields = findFields(p_Actual);
            Vector missingFields = missingFields(targetFields, actualFields);
            for (int i = 0; i < missingFields.size(); i++) {
                if (swXML) {
                    menssage.append("<ResultComparisonStructureLines>");
                }
                menssage.append("	");
                menssage.append(p_NameActual);
                menssage.append(".");
                menssage.append(missingFields.elementAt(i).toString());
                menssage.append(" ");
                menssage.append(CMMessages.getString("RESULT_COMPARISON_IS_MISSING_IN_EXPECTED"));
                if (swXML) {
                    menssage.append("</ResultComparisonStructureLines>");
                }
                else {
                    menssage.append("\n");
                }
            }
            missingFields = missingFields(actualFields, targetFields);
            for (int i = 0; i < missingFields.size(); i++) {
                if (swXML) {
                    menssage.append("<ResultComparisonStructureLines>");
                }
                menssage.append("	");
                menssage.append(p_NameTarget);
                menssage.append(".");
                menssage.append(missingFields.elementAt(i).toString());
                menssage.append(" ");
                menssage.append(CMMessages.getString("RESULT_COMPARISON_IS_MISSING_IN_ACTUAL"));
                if (swXML) {
                    menssage.append("</ResultComparisonStructureLines>");
                }
                else {
                    menssage.append("\n");
                }
            }
            return menssage.toString();
    }

    public String menssageStructureNotEquals(StructureTestData p_Target, StructureTestData p_Actual, String p_NameTarget,
        String p_NameActual, boolean swXML) {
            StringBuffer menssage = new StringBuffer();
            Vector targetFieldkey = findFieldKey(p_Target);
            Vector actualFieldkey = findFieldKey(p_Actual);
            Vector missingFields = missingFields(targetFieldkey, actualFieldkey);
            for (int i = 0; i < missingFields.size(); i++) {
                if (swXML) {
                    menssage.append("<ResultComparisonStructureLines>");
                }
                menssage.append("	");
                menssage.append(p_NameActual);
                menssage.append(".");
                menssage.append(missingFields.elementAt(i).toString());
                menssage.append(" ");
                menssage.append(CMMessages.getString("RESULT_COMPARISON_IS_MISSING_IN_EXPECTED"));
                if (swXML) {
                    menssage.append("</ResultComparisonStructureLines>");
                }
                else {
                    menssage.append("\n");
                }
            }
            missingFields = missingFields(actualFieldkey, targetFieldkey);
            for (int i = 0; i < missingFields.size(); i++) {
                if (swXML) {
                    menssage.append("<ResultComparisonStructureLines>");
                }
                menssage.append("	");
                menssage.append(p_NameTarget);
                menssage.append(".");
                menssage.append(missingFields.elementAt(i).toString());
                menssage.append(" ");
                menssage.append(CMMessages.getString("RESULT_COMPARISON_IS_MISSING_IN_ACTUAL"));
                if (swXML) {
                    menssage.append("</ResultComparisonStructureLines>");
                }
                else {
                    menssage.append("\n");
                }
            }
            return menssage.toString();
    }

    private Vector missingFields(Vector p_original, Vector p_Copia) {
        Vector result = new Vector();
        for (int i = 0; i < p_Copia.size(); i++) {
            if (!p_original.contains(p_Copia.elementAt(i))) {
                result.addElement(p_Copia.elementAt(i));
            }
        }
        return result;
    }

    private String menssageForRepeatAndMissingKey(TestDataSet p_target, TestDataSet p_Actual, boolean swXML) {
        Vector keysTarget = new Vector();
        Vector keysActual = new Vector();
        Vector nameTarget = new Vector();
        Vector nameActual = new Vector();
        StringBuffer result = new StringBuffer();
        for (int i = 0; i < p_target.getM_TestDataCombinations().getM_TestDatas().size(); i++) {
            TestData testData = (TestData)p_target.getM_TestDataCombinations().getM_TestDatas().elementAt(i);
            for (int j = 0; j < testData.getM_TDStructure().getM_StructureTestData().size(); j++) {
                StructureTestData std = (StructureTestData)testData.getM_TDStructure().getM_StructureTestData().elementAt(j);
                if (!structureMissingAllKey(std))
                {
                keysTarget.addElement(stringKey(std));
                String name = p_target.getName() + "." + testData.getName() + "." + std.getName();
                nameTarget.addElement(name);
                }
            }
        }
        for (int i = 0; i < p_Actual.getM_TestDataCombinations().getM_TestDatas().size(); i++) {
            TestData testData = (TestData)p_Actual.getM_TestDataCombinations().getM_TestDatas().elementAt(i);
            for (int j = 0; j < testData.getM_TDStructure().getM_StructureTestData().size(); j++) {
                StructureTestData std = (StructureTestData)testData.getM_TDStructure().getM_StructureTestData().elementAt(j);
				if (!structureMissingAllKey(std))
                {
                keysActual.addElement(stringKey(std));
                String name = p_Actual.getName() + "." + testData.getName() + "." + std.getName();
                nameActual.addElement(name);
                }
            }
        }
        result.append(findEqualsKeys(keysTarget, nameTarget, false, swXML));
        result.append(findEqualsKeys(keysActual, nameActual, true, swXML));
     //   result.append(missingStructureInActual(keysTarget, nameTarget, keysActual, nameActual, swXML));
        return result.toString();
    }

    private String findEqualsKeys(Vector p_Keys, Vector names, boolean isActual, boolean swXML) {
        StringBuffer result = new StringBuffer();
        int i = 0;
        while (i < p_Keys.size()) {
            int count = 0;
            Vector repeatName = new Vector();
            for (int j = 0; j < p_Keys.size(); j++) {
                if (p_Keys.elementAt(i).toString().equalsIgnoreCase(p_Keys.elementAt(j).toString())) {
                    repeatName.addElement(names.elementAt(j));
                    count++;
                }
            }
            if (count > 1) {
                if (swXML) {
                    result.append("<ResultComparisonFoot>"); //$NON-NLS-1$
                }
                result.append(p_Keys.elementAt(i).toString());
                result.append(" ");
                result.append(CMMessages.getString("RESULT_COMPARISON_KEY_REPEAT_PREFIX"));
                result.append(" ");
                result.append(count);
                result.append(" ");
                if (isActual) {
                    result.append(CMMessages.getString("RESULT_COMPARISON_KEY_REPEAT_ACTUAL_SUFFIX"));
                }
                else {
                    result.append(CMMessages.getString("RESULT_COMPARISON_KEY_REPEAT_EXPECTED_SUFFIX"));
                }
                if (swXML) {
                    result.append(System.getProperty("line.separator")); //$NON-NLS-1$ //$NON-NLS-2$
                    result.append(System.getProperty("line.separator")); //$NON-NLS-1$ //$NON-NLS-2$
                    result.append("</ResultComparisonFoot>");
                    for (int k = 0; k < repeatName.size(); k++) {
                        result.append("<ResultComparisonDetailFoot>");
                        result.append(repeatName.elementAt(k).toString());
                        result.append(System.getProperty("line.separator")); //$NON-NLS-1$ //$NON-NLS-2$
                        result.append("</ResultComparisonDetailFoot>");
                    }
                    result.append(System.getProperty("line.separator")); //$NON-NLS-1$ //$NON-NLS-2$
                }
                else {
                    result.append("\n");
                    for (int k = 0; k < repeatName.size(); k++) {
                        result.append("	" + repeatName.elementAt(k).toString() + "\n");
                    }
                    result.append("\n");
                }
                Object obj = p_Keys.elementAt(i);
                boolean erase = p_Keys.remove(obj);
                while (erase) {
                    erase = p_Keys.remove(obj);
                    i = -1;
                }
            }
            i++;
        }
        return result.toString();
    }

    private String missingStructureInActual(Vector p_keysTarget, Vector p_NameTarget, Vector p_keysActual,
        Vector p_NameActual, boolean swXML) {
            StringBuffer result = new StringBuffer();
            for (int i = 0; i < p_keysTarget.size(); i++) {
                if (!p_keysActual.contains(p_keysTarget.elementAt(i))) {
                    if (swXML) {
                        result.append("<ResultComparisonFoot>"); //$NON-NLS-1$
                    }
                    result.append(p_NameTarget.elementAt(i));
                    result.append(" ");
                    result.append(p_keysTarget.elementAt(i));
                    result.append(" ");
                    result.append(CMMessages.getString("RESULT_COMPARISON_IS_MISSING_IN_ACTUAL"));
                    if (swXML) {
                        result.append(System.getProperty("line.separator")); //$NON-NLS-1$ //$NON-NLS-2$
                        result.append("</ResultComparisonFoot>");
                        result.append(System.getProperty("line.separator")); //$NON-NLS-1$ //$NON-NLS-2$
                    }
                    else {
                        result.append("\n");
                    }
                }
            }
            return result.toString();
    }

    private boolean structureMissingAllKey(StructureTestData p_StructureTestData) {
        int count=0;
        for (int i = 0; i < p_StructureTestData.getTypeData().size(); i++) {
            ITypeData typ = (ITypeData)p_StructureTestData.getTypeData().elementAt(i);
            if (typ.getKey().equals("K")) {
                count++;
            }
        }
        if (count==0)
            return true;
        else
            return false;
    }

    public void ReportResultComparation2() {
        try {
            desigualTestData = 0;
            desigualstructure = 0;
            StringBuffer resultcomparationReport = new StringBuffer();
            TestDataSet my_target = (TestDataSet)m_TDStructureTarget.getM_TestDataSet().elementAt(CMIndexTDStructureUpdate.getInstance().getindexTestDataSetTarget());
            TestDataSet my_Actual = (TestDataSet)m_TDStructureActual.getM_TestDataSet().elementAt(CMIndexTDStructureUpdate.getInstance().getindexTestDataSetActual());
            resultcomparationReport.append(CMMessages.getString("RESULT_COMPARISON_HEADER_TESTDATASET_1")); //$NON-NLS-1$
            resultcomparationReport.append(my_Actual.getName());
            resultcomparationReport.append(CMMessages.getString("RESULT_COMPARISON_HEADER_TESTDATASET_2")); //$NON-NLS-1$
            resultcomparationReport.append(my_target.getName());
            resultcomparationReport.append("\n"); //$NON-NLS-1$
            boolean swMenssage = true;
            for (int i = 0; i < my_target.getM_TestDataCombinations().getM_TestDatas().size(); i++)
                for (int j = 0; j < my_Actual.getM_TestDataCombinations().getM_TestDatas().size(); j++) {
                    if (swcontTestData) {
                        desigualTestData++;
                        swcontTestData = false;
                    }
                    TestData my_testDataTarget = (TestData)my_target.getM_TestDataCombinations().getM_TestDatas().elementAt(i);
                    TestData my_testDataActual = (TestData)my_Actual.getM_TestDataCombinations().getM_TestDatas().elementAt(j);
                    if (swMenssage) {
                        resultcomparationReport.append(CMMessages.getString("RESULT_COMPARISON_HEADER_TESTDATA_1")); //$NON-NLS-1$
                        resultcomparationReport.append(my_Actual.getM_TestDataCombinations().getM_TestDatas().size());
                        resultcomparationReport.append("\n"); //$NON-NLS-1$
                        resultcomparationReport.append(CMMessages.getString("RESULT_COMPARISON_HEADER_TESTDATA_2")); //$NON-NLS-1$
                        resultcomparationReport.append(my_target.getM_TestDataCombinations().getM_TestDatas().size());
                        resultcomparationReport.append("\n"); //$NON-NLS-1$
                        int numActual = my_Actual.getM_TestDataCombinations().getM_TestDatas().size();
                        int numTarget = my_target.getM_TestDataCombinations().getM_TestDatas().size();
                        resultcomparationReport.append(CMMessages.getString("RESULT_COMPARISON_HEADER_TESTDATA_3")); //$NON-NLS-1$
                        if (numActual > numTarget)
                            resultcomparationReport.append(numActual - numTarget);
                        else
                            resultcomparationReport.append(0);
                        resultcomparationReport.append("\n"); //$NON-NLS-1$
                        resultcomparationReport.append(CMMessages.getString("RESULT_COMPARISON_HEADER_TESTDATA_4")); //$NON-NLS-1$
                        if (numTarget > numActual)
                            resultcomparationReport.append(numTarget - numActual);
                        else
                            resultcomparationReport.append(0);
                        resultcomparationReport.append("\n"); //$NON-NLS-1$
                        swMenssage = false;
                        /**
                         * cambios nuevos llave
                         * hcanedo_10_08_2004_Begin
                         * */
                        resultcomparationReport.append("\n"); //$NON-NLS-1$
						resultcomparationReport.append(returnListOfKeyMissing(my_target,my_Actual,false));
                    }
                    resultcomparationReport.append("\n"); //$NON-NLS-1$
                    resultcomparationReport.append(CMMessages.getString("RESULT_COMPARISON_HEADER_TESTDATA_5")); //$NON-NLS-1$
                    resultcomparationReport.append(my_testDataActual.getName());
                    resultcomparationReport.append(CMMessages.getString("RESULT_COMPARISON_HEADER_TESTDATA_6")); //$NON-NLS-1$
                    resultcomparationReport.append(my_testDataTarget.getName());
                    resultcomparationReport.append("\n"); //$NON-NLS-1$
                    for (int k = 0; k < my_testDataTarget.getM_TDStructure().getM_StructureTestData().size(); k++)
                        for (int h = 0; h < my_testDataActual.getM_TDStructure().getM_StructureTestData().size(); h++) {
                            StructureTestData my_StructureTestDataTarget =
                                (StructureTestData)my_testDataTarget.getM_TDStructure().getM_StructureTestData().elementAt(k);
                            StructureTestData my_StructureTestDataActual =
                                (StructureTestData)my_testDataActual.getM_TDStructure().getM_StructureTestData().elementAt(h);
                            resultcomparationReport.append(CMMessages.getString("RESULT_COMPARISON_HEADER_STRUCTURE_1")); //$NON-NLS-1$
                            resultcomparationReport.append(my_StructureTestDataActual.getName());
                            resultcomparationReport.append(CMMessages.getString("RESULT_COMPARISON_HEADER_STRUCTURE_2")); //$NON-NLS-1$
                            resultcomparationReport.append(my_StructureTestDataTarget.getName());
                            resultcomparationReport.append("\n"); //$NON-NLS-1$
                            String nameTarget = my_target.getName() + "." + my_testDataTarget.getName() + "." +
                                my_StructureTestDataTarget.getName(); //$NON-NLS-1$ //$NON-NLS-2$
                            String nameActual = my_Actual.getName() + "." + my_testDataActual.getName() + "." +
                                my_StructureTestDataActual.getName(); //$NON-NLS-1$ //$NON-NLS-2$
                            if (structureMissingAllKey(my_StructureTestDataTarget) ||
                                structureMissingAllKey(my_StructureTestDataActual)) {

                                    if(structureMissingAllKey(my_StructureTestDataTarget) && structureMissingAllKey(my_StructureTestDataActual))
                                    {
                                        resultcomparationReport.append("	");
                                        resultcomparationReport.append(CMMessages.getString("RESULT_COMPARISON_MISSING_ALL_KEYS"));
                                    	resultcomparationReport.append(" ");
                                        resultcomparationReport.append(nameTarget);
                                        resultcomparationReport.append("\n");
                                        resultcomparationReport.append("	");
                                        resultcomparationReport.append(CMMessages.getString("RESULT_COMPARISON_MISSING_ALL_KEYS"));
	                                    resultcomparationReport.append(" ");
                                        resultcomparationReport.append(nameActual);
                                        resultcomparationReport.append("\n");
                                    }
                                    else
                                    {
                                        resultcomparationReport.append("	");
                                        resultcomparationReport.append(CMMessages.getString("RESULT_COMPARISON_MISSING_ALL_KEYS"));
                                    resultcomparationReport.append(" ");
                                    if (structureMissingAllKey(my_StructureTestDataTarget)) {
                                        resultcomparationReport.append(nameTarget);
                                    }
                                    else {
                                        resultcomparationReport.append(nameActual);
                                    }
                                    resultcomparationReport.append("\n");
                                    }
                            }
                            else if (compareKeys(my_StructureTestDataTarget, my_StructureTestDataActual)) {
                                resultcomparationReport.append(compareStructureTestData(my_StructureTestDataTarget, nameTarget,
                                    my_StructureTestDataActual, nameActual));
                            }
                            else {
                                resultcomparationReport.append(menssageStructureNotEquals(my_StructureTestDataTarget,
                                    my_StructureTestDataActual, nameTarget, nameActual, false));
                            }
                            resultcomparationReport.append("\n");
                        }
                }
            resultcomparationReport.append(menssageForRepeatAndMissingKey(my_target, my_Actual, false));
            resultcomparationReport.append(CMMessages.getString("RESULT_COMPARISON_FOOT_TESTDATA")+" "+ Integer.toString(desigualTestData)+ "\n"); //$NON-NLS-1$
            //$NON-NLS-2$
            resultcomparationReport.append(CMMessages.getString("RESULT_COMPARISON_FOOT_STRUCTURE")+" "+ Integer.toString(desigualstructure) + "\n"); //$NON-NLS-1$
            //$NON-NLS-2$
            jEditorPane1.setText(resultcomparationReport.toString());
        }
        catch (Exception ex) {
        }
    }

    public String compareStructureTestData(StructureTestData p_Target, String nameTarget,
        StructureTestData p_Actual, String nameActual) {
            boolean swdistint = false;
            boolean swcont = true;
            boolean swMenorActual = true;
            StringBuffer resultcompareStructure = new StringBuffer();
            int actualsize = p_Actual.getTypeData().size();
            int targetsize = p_Target.getTypeData().size();
            int forindex = 0;
            int contOK = 0;
            Vector fieldsActual = findFields(p_Actual);
            Vector fieldsTarget = findFields(p_Target);
            String stringKeyActual = stringKey(p_Actual);
            String stringKeyTarget = stringKey(p_Target);
            if (actualsize < targetsize) {
                String mis = Integer.toString(targetsize - actualsize);
                //	resultcompareStructure.append("
                // "+nameActual+CMMessages.getString("RESULT_COMPARISON_MISSING")+mis+CMMessages.getString("RESULT_COMPARISON_ROWS")); //$NON-NLS-1$
                //$NON-NLS-2$ //$NON-NLS-3$
                forindex = actualsize;
            }
            else {
                if (actualsize > targetsize) {
                    String mis = Integer.toString(actualsize - targetsize);
                    //	resultcompareStructure.append("
                    // "+nameActual+CMMessages.getString("RESULT_COMPARISON_UNNECESSARY")+mis+CMMessages.getString("RESULT_COMPARISON_ROWS")); //$NON-NLS-1$
                    //$NON-NLS-2$ //$NON-NLS-3$
                    forindex = targetsize;
                    swMenorActual = false;
                }
                else {
                    forindex = actualsize;
                }
            }
            int i = 0;
            boolean swMenssage = true;
            while (i < forindex) {
                /** Nuevo codigo para ver si las estructuras tiene campos diferentes */
                if (swMenssage) {
                    resultcompareStructure.append(menssageFieldsMissing(p_Target, p_Actual, nameTarget, nameActual, false));
                    swMenssage = false;
                }
                //////////////////////////////////////////////////////////////////
                ITypeData my_Actual;
                ITypeData my_Target;
                if (swMenorActual) {
                    my_Actual = (ITypeData)p_Actual.getTypeData().elementAt(i);
                    int index = fieldsTarget.indexOf(fieldsActual.elementAt(i));
                    while (index < 0) {
                        try {
                            i++;
                            my_Actual = (ITypeData)p_Actual.getTypeData().elementAt(i);
                            index = fieldsTarget.indexOf(fieldsActual.elementAt(i));
                        }
                        catch (Exception ex) {
                            if (swdistint) {
                                resultcompareStructure.append("	" + nameTarget + "." + stringKeyTarget +
                                    CMMessages.getString("RESULT_COMPARISON_IS_MISSING_IN_ACTUAL")); //$NON-NLS-1$ //$NON-NLS-2$
                                resultcompareStructure.append("	" + nameActual + "." + stringKeyActual +
                                    CMMessages.getString("RESULT_COMPARISON_IS_MISSING_IN_EXPECTED") +
                                    CMMessages.getString("RESULT_COMPARISON_NEW")); //$NON-NLS-1$ //$NON-NLS-2$
                            }
                            if (contOK == forindex) {
                                resultcompareStructure.append("	" + nameActual + CMMessages.getString("RESULT_COMPARISON_OK")); //$NON-NLS-1$
                                //$NON-NLS-2$
                            }
                            return resultcompareStructure.toString();
                        }
                    }
                    my_Target = (ITypeData)p_Target.getTypeData().elementAt(index);
                }
                else {
                    my_Target = (ITypeData)p_Target.getTypeData().elementAt(i);
                    int index = fieldsActual.indexOf(fieldsTarget.elementAt(i));
                    while (index < 0) {
                        try {
                            i++;
                            my_Target = (ITypeData)p_Target.getTypeData().elementAt(i);
                            index = fieldsActual.indexOf(fieldsTarget.elementAt(i));
                        }
                        catch (Exception ex) {
                            if (swdistint) {
                                resultcompareStructure.append("	" + nameTarget + "." + stringKeyTarget +
                                    CMMessages.getString("RESULT_COMPARISON_IS_MISSING_IN_ACTUAL")); //$NON-NLS-1$ //$NON-NLS-2$
                                resultcompareStructure.append("	" + nameActual + "." + stringKeyActual +
                                    CMMessages.getString("RESULT_COMPARISON_IS_MISSING_IN_EXPECTED") +
                                    CMMessages.getString("RESULT_COMPARISON_NEW")); //$NON-NLS-1$ //$NON-NLS-2$
                            }
                            if (contOK == forindex) {
                                resultcompareStructure.append("	" + nameActual + CMMessages.getString("RESULT_COMPARISON_OK")); //$NON-NLS-1$
                                //$NON-NLS-2$
                            }
                            return resultcompareStructure.toString();
                        }
                    }
                    my_Actual = (ITypeData)p_Actual.getTypeData().elementAt(index);
                }
                if (my_Actual.getKey().equals(my_Target.getKey())) {
                    //if (my_Actual.getStringValue().equalsIgnoreCase(my_Target.getStringValue())) {
                	if (my_Actual.getValue().equals(my_Target.getValue())) {
                        contOK++;
                    }
                    else {
                        if (!stringKeyTarget.equalsIgnoreCase(stringKeyActual))
                            swdistint = true;
                        if (!swdistint) {
                            /*resultcompareStructure.append("	" + nameActual + "." + stringKeyActual + "." +
                                my_Actual.getField() + "= " + my_Actual.getStringValue() + "\n");*/
                        	resultcompareStructure.append("	" + nameActual + "." + stringKeyActual + "." +
                                    my_Actual.getField() + "= " + my_Actual.getFormattedValue() + "\n");
                            /*resultcompareStructure.append("	" + nameTarget + "." + stringKeyTarget + "." +
                                my_Target.getField() + "= " + my_Target.getStringValue() + "\n");*/
                        	resultcompareStructure.append("	" + nameTarget + "." + stringKeyTarget + "." +
                                    my_Target.getField() + "= " + my_Target.getFormattedValue() + "\n");
                            resultcompareStructure.append("\n"); //$NON-NLS-1$
                        }
                        swcontTestData = true;
                        if (swcont) {
                            desigualstructure++;
                            swcont = false;
                        }
                    }
                }

		/*			else{
						swdistint= true;
						swcontTestData=true;
					//	resultcompareStructure.append(CMMessages.getString("RESULT_COMPARISON_ERROR_KEY")+nameActual+CMMessages.getString("RESULT_COMPARISON_ROW")+Integer.toString(i+1)+"\n"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
						if(!my_Actual.getValue().equalsIgnoreCase(my_Target.getValue()))
						{
							resultcompareStructure.append("	"+nameActual+"."+stringKeyActual +"."+my_Actual.getField()+"= "+my_Actual.getValue()+"\n"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
							resultcompareStructure.append("	"+nameTarget+"."+stringKeyTarget+"."+my_Target.getField()+"= "+my_Target.getValue()+"\n"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
						}
						if(swcont){
								desigualstructure++;
								swcont=false;
						}
					}*/

                i++;
            }
            if (swdistint) {
                //resultcompareStructure.append("Error in KEY "+nameActual+" ROW: "+Integer.toString(i+1)+"\n");
                resultcompareStructure.append("	" + nameTarget + "." + stringKeyTarget +
                    CMMessages.getString("RESULT_COMPARISON_IS_MISSING_IN_ACTUAL") + "\n"); //$NON-NLS-1$ //$NON-NLS-2$
                resultcompareStructure.append("	" + nameActual + "." + stringKeyActual +
                    CMMessages.getString("RESULT_COMPARISON_IS_MISSING_IN_EXPECTED") + CMMessages.getString("RESULT_COMPARISON_NEW")); //$NON-NLS-1$
                //$NON-NLS-2$
            }
            if (contOK == forindex) {
                resultcompareStructure.append("	" + nameActual + CMMessages.getString("RESULT_COMPARISON_OK")); //$NON-NLS-1$ //$NON-NLS-2$
            }
            return resultcompareStructure.toString();
    }

    public String ReportResultComparationXML2() {
        try {
            desigualTestData = 0;
            desigualstructure = 0;
            StringBuffer resultcomparationReport = new StringBuffer();
            TestDataSet my_target = (TestDataSet)m_TDStructureTarget.getM_TestDataSet().elementAt(CMIndexTDStructureUpdate.getInstance().getindexTestDataSetTarget());
            TestDataSet my_Actual = (TestDataSet)m_TDStructureActual.getM_TestDataSet().elementAt(CMIndexTDStructureUpdate.getInstance().getindexTestDataSetActual());
            resultcomparationReport.append("<?xml version=" + '"' + "1.0" + '"' + " encoding=" + '"' + "iso-8859-1" + '"' + " ?>"); //$NON-NLS-1$
            //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
            resultcomparationReport.append(System.getProperty("line.separator")); //$NON-NLS-1$
            resultcomparationReport.append("<ResultComparison>"); //$NON-NLS-1$
            resultcomparationReport.append(System.getProperty("line.separator")); //$NON-NLS-1$

            /** nuevo codigo para la cabezera de los reportes en html */
		resultcomparationReport.append("<HeaderImg>"); //$NON-NLS-1$
        resultcomparationReport.append(BusinessRules.REPORT_IMAGEFLD+"/"+CMIcon.CASEMAKER_LOGO.getFilename());
        resultcomparationReport.append("</HeaderImg>"); //$NON-NLS-1$
        resultcomparationReport.append(System.getProperty("line.separator")); //$NON-NLS-1$
         resultcomparationReport.append(System.getProperty("line.separator")); //$NON-NLS-1$*/

            resultcomparationReport.append("<ProjectName>"); //$NON-NLS-1$
            resultcomparationReport.append(m_Frame.getTreeWorkspaceView().getCurrentProject().getName());//m_TestObjectReference.getM_Project().getM_Name());
            resultcomparationReport.append("</ProjectName>"); //$NON-NLS-1$
            resultcomparationReport.append(System.getProperty("line.separator")); //$NON-NLS-1$
            resultcomparationReport.append("<TestObjectName>"); //$NON-NLS-1$
            resultcomparationReport.append(m_TestObject.getName());
            resultcomparationReport.append("</TestObjectName>"); //$NON-NLS-1$
            resultcomparationReport.append(System.getProperty("line.separator")); //$NON-NLS-1$
            resultcomparationReport.append("<TestObjectDescription>"); //$NON-NLS-1$
            resultcomparationReport.append(m_TestObject.getDescription());
            resultcomparationReport.append("</TestObjectDescription>"); //$NON-NLS-1$
            resultcomparationReport.append(System.getProperty("line.separator")); //$NON-NLS-1$
            resultcomparationReport.append("<ActualDate>"); //$NON-NLS-1$
            Date date = new java.util.Date();
            resultcomparationReport.append(date.toString());
            resultcomparationReport.append("</ActualDate>"); //$NON-NLS-1$
            resultcomparationReport.append(System.getProperty("line.separator")); //$NON-NLS-1$
            resultcomparationReport.append("<UserName>"); //$NON-NLS-1$
            String userName = System.getProperty("user.name"); //$NON-NLS-1$
            if (userName != null) {
                resultcomparationReport.append(userName);
            }
            else {
                resultcomparationReport.append("");
            }
            resultcomparationReport.append("</UserName>"); //$NON-NLS-1$
            resultcomparationReport.append(System.getProperty("line.separator")); //$NON-NLS-1$*/
            ///////////////////////////////////////////////////////////////////////////////////
            resultcomparationReport.append("<ResultComparisonHeader1>"); //$NON-NLS-1$
            resultcomparationReport.append(CMMessages.getString("RESULT_COMPARISON_HEADER_TESTDATASET_1")); //$NON-NLS-1$
            resultcomparationReport.append(my_Actual.getName());
            resultcomparationReport.append(CMMessages.getString("RESULT_COMPARISON_HEADER_TESTDATASET_2")); //$NON-NLS-1$
            resultcomparationReport.append(my_target.getName());
            resultcomparationReport.append(System.getProperty("line.separator")); //$NON-NLS-1$
            resultcomparationReport.append("</ResultComparisonHeader1>"); //$NON-NLS-1$
            resultcomparationReport.append(System.getProperty("line.separator")); //$NON-NLS-1$
            boolean swMenssage = true;
            for (int i = 0; i < my_target.getM_TestDataCombinations().getM_TestDatas().size(); i++)
                for (int j = 0; j < my_Actual.getM_TestDataCombinations().getM_TestDatas().size(); j++) {
                    if (swcontTestData) {
                        desigualTestData++;
                        swcontTestData = false;
                    }
                    TestData my_testDataTarget = (TestData)my_target.getM_TestDataCombinations().getM_TestDatas().elementAt(i);
                    TestData my_testDataActual = (TestData)my_Actual.getM_TestDataCombinations().getM_TestDatas().elementAt(j);
                    if (swMenssage) {
                        resultcomparationReport.append("<ResultComparisonHeader1>"); //$NON-NLS-1$
                        resultcomparationReport.append(CMMessages.getString("RESULT_COMPARISON_HEADER_TESTDATA_1")); //$NON-NLS-1$
                        resultcomparationReport.append(my_Actual.getM_TestDataCombinations().getM_TestDatas().size());
                        resultcomparationReport.append(System.getProperty("line.separator")); //$NON-NLS-1$
                        resultcomparationReport.append("</ResultComparisonHeader1>"); //$NON-NLS-1$
                        resultcomparationReport.append(System.getProperty("line.separator")); //$NON-NLS-1$
                        resultcomparationReport.append("<ResultComparisonHeader1>"); //$NON-NLS-1$
                        resultcomparationReport.append(CMMessages.getString("RESULT_COMPARISON_HEADER_TESTDATA_2")); //$NON-NLS-1$
                        resultcomparationReport.append(my_target.getM_TestDataCombinations().getM_TestDatas().size());
                        resultcomparationReport.append(System.getProperty("line.separator")); //$NON-NLS-1$
                        resultcomparationReport.append("</ResultComparisonHeader1>"); //$NON-NLS-1$
                        resultcomparationReport.append(System.getProperty("line.separator")); //$NON-NLS-1$
                        int numActual = my_Actual.getM_TestDataCombinations().getM_TestDatas().size();
                        int numTarget = my_target.getM_TestDataCombinations().getM_TestDatas().size();
                        resultcomparationReport.append("<ResultComparisonHeader1>"); //$NON-NLS-1$
                        resultcomparationReport.append(CMMessages.getString("RESULT_COMPARISON_HEADER_TESTDATA_3")); //$NON-NLS-1$
                        if (numActual > numTarget)
                            resultcomparationReport.append(numActual - numTarget);
                        else
                            resultcomparationReport.append(0);
                        resultcomparationReport.append(System.getProperty("line.separator")); //$NON-NLS-1$
                        resultcomparationReport.append("</ResultComparisonHeader1>"); //$NON-NLS-1$
                        resultcomparationReport.append(System.getProperty("line.separator")); //$NON-NLS-1$
                        resultcomparationReport.append("<ResultComparisonHeader1>"); //$NON-NLS-1$
                        resultcomparationReport.append(CMMessages.getString("RESULT_COMPARISON_HEADER_TESTDATA_4")); //$NON-NLS-1$
                        if (numTarget > numActual)
                            resultcomparationReport.append(numTarget - numActual);
                        else
                            resultcomparationReport.append(0);
                        resultcomparationReport.append(System.getProperty("line.separator")); //$NON-NLS-1$
                        resultcomparationReport.append("</ResultComparisonHeader1>"); //$NON-NLS-1$
                        swMenssage = false;
                        /**
                         * cambios nuevos llave
                         * hcanedo_10_08_2004_Begin
                         * */
                           resultcomparationReport.append(System.getProperty("line.separator")); //$NON-NLS-1$
						resultcomparationReport.append(returnListOfKeyMissing(my_target,my_Actual,true));

                    }
                    resultcomparationReport.append(System.getProperty("line.separator")); //$NON-NLS-1$
                    resultcomparationReport.append("<ResultComparisonTestDataHeader>"); //$NON-NLS-1$
                    resultcomparationReport.append(System.getProperty("line.separator")); //$NON-NLS-1$
                    resultcomparationReport.append("<ResultComparisonTestDataHeaderLines>"); //$NON-NLS-1$
                    resultcomparationReport.append(CMMessages.getString("RESULT_COMPARISON_HEADER_TESTDATA_5")); //$NON-NLS-1$
                    resultcomparationReport.append(my_testDataActual.getName());
                    resultcomparationReport.append(CMMessages.getString("RESULT_COMPARISON_HEADER_TESTDATA_6")); //$NON-NLS-1$
                    resultcomparationReport.append(my_testDataTarget.getName());
                    resultcomparationReport.append(System.getProperty("line.separator")); //$NON-NLS-1$
                    resultcomparationReport.append("</ResultComparisonTestDataHeaderLines>"); //$NON-NLS-1$
                    resultcomparationReport.append(System.getProperty("line.separator")); //$NON-NLS-1$
                    for (int k = 0; k < my_testDataTarget.getM_TDStructure().getM_StructureTestData().size(); k++)
                        for (int h = 0; h < my_testDataActual.getM_TDStructure().getM_StructureTestData().size(); h++) {
                            StructureTestData my_StructureTestDataTarget =
                                (StructureTestData)my_testDataTarget.getM_TDStructure().getM_StructureTestData().elementAt(k);
                            StructureTestData my_StructureTestDataActual =
                                (StructureTestData)my_testDataActual.getM_TDStructure().getM_StructureTestData().elementAt(h);
                            resultcomparationReport.append("<ResultComparisonStructureHeader>"); //$NON-NLS-1$
                            resultcomparationReport.append(System.getProperty("line.separator")); //$NON-NLS-1$
                            resultcomparationReport.append("<ResultComparisonStructureHeaderLines>"); //$NON-NLS-1$
                            resultcomparationReport.append(CMMessages.getString("RESULT_COMPARISON_HEADER_STRUCTURE_1")); //$NON-NLS-1$
                            resultcomparationReport.append(my_StructureTestDataActual.getName());
                            resultcomparationReport.append(CMMessages.getString("RESULT_COMPARISON_HEADER_STRUCTURE_2")); //$NON-NLS-1$
                            resultcomparationReport.append(my_StructureTestDataTarget.getName());
                            resultcomparationReport.append(System.getProperty("line.separator")); //$NON-NLS-1$
                            resultcomparationReport.append("</ResultComparisonStructureHeaderLines>"); //$NON-NLS-1$
                            resultcomparationReport.append(System.getProperty("line.separator")); //$NON-NLS-1$
                            String nameTarget = my_target.getName() + "." + my_testDataTarget.getName() + "." +
                                my_StructureTestDataTarget.getName(); //$NON-NLS-1$ //$NON-NLS-2$
                            String nameActual = my_Actual.getName() + "." + my_testDataActual.getName() + "." +
                                my_StructureTestDataActual.getName(); //$NON-NLS-1$ //$NON-NLS-2$
                            if (structureMissingAllKey(my_StructureTestDataTarget) || structureMissingAllKey(my_StructureTestDataActual)) {
                                if(structureMissingAllKey(my_StructureTestDataTarget) && structureMissingAllKey(my_StructureTestDataActual))
                                    {
                                         resultcomparationReport.append("<ResultComparisonStructureBody>"); //$NON-NLS-1$
										resultcomparationReport.append("<ResultComparisonStructureLines>");
                                        resultcomparationReport.append(CMMessages.getString("RESULT_COMPARISON_MISSING_ALL_KEYS"));
                                    	resultcomparationReport.append(" ");
                                        resultcomparationReport.append(nameTarget);
										resultcomparationReport.append("</ResultComparisonStructureLines>");
                                        resultcomparationReport.append("<ResultComparisonStructureLines>");
                                        resultcomparationReport.append(CMMessages.getString("RESULT_COMPARISON_MISSING_ALL_KEYS"));
	                                    resultcomparationReport.append(" ");
    	                                resultcomparationReport.append(nameActual);
                                        resultcomparationReport.append("</ResultComparisonStructureLines>");
                                        resultcomparationReport.append(System.getProperty("line.separator")); //$NON-NLS-1$
        	                            resultcomparationReport.append("</ResultComparisonStructureBody>"); //$NON-NLS-1$
            	                        resultcomparationReport.append(System.getProperty("line.separator")); //$NON-NLS-1$
                	                    resultcomparationReport.append("</ResultComparisonStructureHeader>"); //$NON-NLS-1$
                    	                resultcomparationReport.append(System.getProperty("line.separator")); //$NON-NLS-1$
                                    }
                                    else
                                    {
                                    resultcomparationReport.append("<ResultComparisonStructureBody>"); //$NON-NLS-1$
									resultcomparationReport.append("<ResultComparisonStructureLines>");
                                    resultcomparationReport.append(CMMessages.getString("RESULT_COMPARISON_MISSING_ALL_KEYS"));
                                    resultcomparationReport.append(" ");
                                    if (structureMissingAllKey(my_StructureTestDataTarget)) {
                                        resultcomparationReport.append(nameTarget);
                                    }
                                    else {
                                        resultcomparationReport.append(nameActual);
                                    }
									resultcomparationReport.append("</ResultComparisonStructureLines>");
                                    resultcomparationReport.append(System.getProperty("line.separator")); //$NON-NLS-1$
                                    resultcomparationReport.append("</ResultComparisonStructureBody>"); //$NON-NLS-1$
                                    resultcomparationReport.append(System.getProperty("line.separator")); //$NON-NLS-1$
                                    resultcomparationReport.append("</ResultComparisonStructureHeader>"); //$NON-NLS-1$
                                    resultcomparationReport.append(System.getProperty("line.separator")); //$NON-NLS-1$
                                    }
                            }
                            else if (compareKeys(my_StructureTestDataTarget, my_StructureTestDataActual)) {
                                resultcomparationReport.append("<ResultComparisonStructureBody>"); //$NON-NLS-1$
                                resultcomparationReport.append(compareStructureTestDataXML(my_StructureTestDataTarget,
                                    nameTarget, my_StructureTestDataActual, nameActual));
                                resultcomparationReport.append(System.getProperty("line.separator")); //$NON-NLS-1$
                                resultcomparationReport.append("</ResultComparisonStructureBody>"); //$NON-NLS-1$
                                resultcomparationReport.append(System.getProperty("line.separator")); //$NON-NLS-1$
                                resultcomparationReport.append("</ResultComparisonStructureHeader>"); //$NON-NLS-1$
                                resultcomparationReport.append(System.getProperty("line.separator")); //$NON-NLS-1$
                            }
                            else {
                                resultcomparationReport.append("<ResultComparisonStructureBody>"); //$NON-NLS-1$
                                resultcomparationReport.append(menssageStructureNotEquals(my_StructureTestDataTarget,
                                    my_StructureTestDataActual, nameTarget, nameActual, true));
                                resultcomparationReport.append(System.getProperty("line.separator")); //$NON-NLS-1$
                                resultcomparationReport.append("</ResultComparisonStructureBody>"); //$NON-NLS-1$
                                resultcomparationReport.append(System.getProperty("line.separator")); //$NON-NLS-1$
                                resultcomparationReport.append("</ResultComparisonStructureHeader>"); //$NON-NLS-1$
                                resultcomparationReport.append(System.getProperty("line.separator")); //$NON-NLS-1$
                            }

/*                        resultcomparationReport.append("<ResultComparisonStructureBody>"); //$NON-NLS-1$
                        resultcomparationReport.append(compareStructureTestDataXML(my_StructureTestDataTarget,nameTarget, my_StructureTestDataActual,nameActual));
                        resultcomparationReport.append(System.getProperty("line.separator")); //$NON-NLS-1$
                        resultcomparationReport.append("</ResultComparisonStructureBody>"); //$NON-NLS-1$
                        resultcomparationReport.append(System.getProperty("line.separator")); //$NON-NLS-1$
						resultcomparationReport.append("</ResultComparisonStructureHeader>"); //$NON-NLS-1$*/

                            resultcomparationReport.append(System.getProperty("line.separator")); //$NON-NLS-1$
                        }
                    resultcomparationReport.append(System.getProperty("line.separator")); //$NON-NLS-1$
                    resultcomparationReport.append("</ResultComparisonTestDataHeader>"); //$NON-NLS-1$
                }
            resultcomparationReport.append(menssageForRepeatAndMissingKey(my_target, my_Actual, true));
            resultcomparationReport.append("<ResultComparisonFoot>"); //$NON-NLS-1$
            resultcomparationReport.append(CMMessages.getString("RESULT_COMPARISON_FOOT_TESTDATA") +" "+
                Integer.toString(desigualTestData) + System.getProperty("line.separator")); //$NON-NLS-1$ //$NON-NLS-2$
            resultcomparationReport.append("</ResultComparisonFoot>"); //$NON-NLS-1$
            resultcomparationReport.append("<ResultComparisonFoot>"); //$NON-NLS-1$
            resultcomparationReport.append(CMMessages.getString("RESULT_COMPARISON_FOOT_STRUCTURE") +" "+
                Integer.toString(desigualstructure) + System.getProperty("line.separator")); //$NON-NLS-1$ //$NON-NLS-2$
            resultcomparationReport.append("</ResultComparisonFoot>"); //$NON-NLS-1$
            resultcomparationReport.append("</ResultComparison>"); //$NON-NLS-1$
            return (resultcomparationReport.toString());
        }
        catch (Exception ex) {
            StringBuffer resultcomparationReport = new StringBuffer();
            resultcomparationReport.append("<?xml version=" + '"' + "1.0" + '"' + " encoding=" + '"' + "iso-8859-1" + '"' + " ?>"); //$NON-NLS-1$
            resultcomparationReport.append("<ResultComparison>"); //$NON-NLS-1$
//HCanedo_30112004_Begin
		resultcomparationReport.append("<HeaderImg>"); //$NON-NLS-1$
        resultcomparationReport.append(BusinessRules.REPORT_IMAGEFLD+"/"+CMIcon.CASEMAKER_LOGO.getFilename());
        resultcomparationReport.append("</HeaderImg>"); //$NON-NLS-1$
//HCanedo_30112004_End
            resultcomparationReport.append("<ProjectName>"); //$NON-NLS-1$
            resultcomparationReport.append(m_Frame.getTreeWorkspaceView().getCurrentProject().getName());//m_TestObjectReference.getM_Project().getM_Name());
            resultcomparationReport.append("</ProjectName>"); //$NON-NLS-1$
            resultcomparationReport.append(System.getProperty("line.separator")); //$NON-NLS-1$
            resultcomparationReport.append("<TestObjectName>"); //$NON-NLS-1$
            resultcomparationReport.append(m_TestObject.getName());
            resultcomparationReport.append("</TestObjectName>"); //$NON-NLS-1$
            resultcomparationReport.append(System.getProperty("line.separator")); //$NON-NLS-1$
            resultcomparationReport.append("<TestObjectDescription>"); //$NON-NLS-1$
            resultcomparationReport.append(m_TestObject.getDescription());
            resultcomparationReport.append("</TestObjectDescription>"); //$NON-NLS-1$
            resultcomparationReport.append(System.getProperty("line.separator")); //$NON-NLS-1$
            resultcomparationReport.append("<ActualDate>"); //$NON-NLS-1$
            Date date = new java.util.Date();
            resultcomparationReport.append(date.toString());
            resultcomparationReport.append("</ActualDate>"); //$NON-NLS-1$
            resultcomparationReport.append(System.getProperty("line.separator")); //$NON-NLS-1$
            resultcomparationReport.append("<UserName>"); //$NON-NLS-1$
            String userName = System.getProperty("user.name"); //$NON-NLS-1$
            if (userName != null) {
                resultcomparationReport.append(userName);
            }
            else {
                resultcomparationReport.append("");
            }
            resultcomparationReport.append("</UserName>"); //$NON-NLS-1$
            resultcomparationReport.append(System.getProperty("line.separator")); //$NON-NLS-1$*/
            resultcomparationReport.append("</ResultComparison>"); //$NON-NLS-1$
            return (resultcomparationReport.toString());
        }
    }

    public String compareStructureTestDataXML(StructureTestData p_Target, String nameTarget,
        StructureTestData p_Actual, String nameActual) {
            boolean swdistint = false;
            boolean swcont = true;
            boolean swMenorActual = true;
            StringBuffer resultcompareStructure = new StringBuffer();
            int actualsize = p_Actual.getTypeData().size();
            int targetsize = p_Target.getTypeData().size();
            int forindex = 0;
            int contOK = 0;
            Vector fieldsActual = findFields(p_Actual);
            Vector fieldsTarget = findFields(p_Target);
            String stringKeyActual = stringKey(p_Actual);
            String stringKeyTarget = stringKey(p_Target);
            if (actualsize < targetsize) {
                String mis = Integer.toString(targetsize - actualsize);

        /*    resultcompareStructure.append("<ResultComparisonStructureLines>"); //$NON-NLS-1$
            resultcompareStructure.append("	"+nameActual+CMMessages.getString("RESULT_COMPARISON_MISSING")+mis+CMMessages.getString("RESULT_COMPARISON_ROWS_1")+System.getProperty("line.separator")); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
            resultcompareStructure.append("</ResultComparisonStructureLines>"); //$NON-NLS-1$*/

                forindex = actualsize;
            }
            else {
                if (actualsize > targetsize) {
                    String mis = Integer.toString(actualsize - targetsize);

               /* resultcompareStructure.append("<ResultComparisonStructureLines>"); //$NON-NLS-1$
                resultcompareStructure.append("	"+nameActual+CMMessages.getString("RESULT_COMPARISON_UNNECESSARY")+mis+CMMessages.getString("RESULT_COMPARISON_ROWS_2")+System.getProperty("line.separator")); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
                resultcompareStructure.append("</ResultComparisonStructureLines>"); //$NON-NLS-1$*/

                    forindex = targetsize;
                    swMenorActual = false;
                }
                else {
                    forindex = actualsize;
                }
            }
            int i = 0;
            boolean swMenssage = true;
            while (i < forindex) {
                /** Nuevo codigo para ver si las estructuras tiene campos diferentes */
                if (swMenssage) {
                    resultcompareStructure.append(menssageFieldsMissing(p_Target, p_Actual, nameTarget, nameActual, true));
                    swMenssage = false;
                }
                //////////////////////////////////////////////////////////////////
                ITypeData my_Actual;
                ITypeData my_Target;
                if (swMenorActual) {
                    my_Actual = (ITypeData)p_Actual.getTypeData().elementAt(i);
                    int index = fieldsTarget.indexOf(fieldsActual.elementAt(i));
                    while (index < 0) {
                        try {
                            i++;
                            my_Actual = (ITypeData)p_Actual.getTypeData().elementAt(i);
                            index = fieldsTarget.indexOf(fieldsActual.elementAt(i));
                        }
                        catch (Exception ex) {
                            if (swdistint) {
                                //resultcompareStructure.append("Error in KEY "+nameActual+" ROW: "+Integer.toString(i+1)+"\n");
                                resultcompareStructure.append("<ResultComparisonStructureLines>"); //$NON-NLS-1$
                                resultcompareStructure.append("	" + nameTarget + "." + stringKeyTarget +
                                    CMMessages.getString("RESULT_COMPARISON_IS_MISSING_IN_ACTUAL") + System.getProperty("line.separator")); //$NON-NLS-1$
                                //$NON-NLS-2$ //$NON-NLS-3$
                                resultcompareStructure.append("</ResultComparisonStructureLines>"); //$NON-NLS-1$
                                resultcompareStructure.append("<ResultComparisonStructureLines>"); //$NON-NLS-1$
                                resultcompareStructure.append("	" + nameActual + "." + stringKeyActual +
                                    CMMessages.getString("RESULT_COMPARISON_IS_MISSING_IN_EXPECTED") +
                                    CMMessages.getString("RESULT_COMPARISON_NEW_1") + System.getProperty("line.separator")); //$NON-NLS-1$
                                //$NON-NLS-2$ //$NON-NLS-3$
                                resultcompareStructure.append("</ResultComparisonStructureLines>"); //$NON-NLS-1$
                            }
                            if (contOK == forindex) {
                                resultcompareStructure.append("<ResultComparisonStructureLines>"); //$NON-NLS-1$
                                resultcompareStructure.append("	" + nameActual +
                                    CMMessages.getString("RESULT_COMPARISON_OK_1") + System.getProperty("line.separator")); //$NON-NLS-1$
                                //$NON-NLS-2$ //$NON-NLS-3$
                                resultcompareStructure.append("</ResultComparisonStructureLines>"); //$NON-NLS-1$
                            }
                            return resultcompareStructure.toString();
                        }
                    }
                    my_Target = (ITypeData)p_Target.getTypeData().elementAt(index);
                }
                else {
                    my_Target = (ITypeData)p_Target.getTypeData().elementAt(i);
                    int index = fieldsActual.indexOf(fieldsTarget.elementAt(i));
                    while (index < 0) {
                        try {
                            i++;
                            my_Target = (ITypeData)p_Target.getTypeData().elementAt(i);
                            index = fieldsActual.indexOf(fieldsTarget.elementAt(i));
                        }
                        catch (Exception ex) {
                            if (swdistint) {
                                resultcompareStructure.append("<ResultComparisonStructureLines>"); //$NON-NLS-1$
                                resultcompareStructure.append("	" + nameTarget + "." + stringKeyTarget +
                                    CMMessages.getString("RESULT_COMPARISON_IS_MISSING_IN_ACTUAL") + System.getProperty("line.separator")); //$NON-NLS-1$
                                //$NON-NLS-2$ //$NON-NLS-3$
                                resultcompareStructure.append("</ResultComparisonStructureLines>"); //$NON-NLS-1$
                                resultcompareStructure.append("<ResultComparisonStructureLines>"); //$NON-NLS-1$
                                resultcompareStructure.append("	" + nameActual + "." + stringKeyActual +
                                    CMMessages.getString("RESULT_COMPARISON_IS_MISSING_IN_EXPECTED") +
                                    CMMessages.getString("RESULT_COMPARISON_NEW_1") + System.getProperty("line.separator")); //$NON-NLS-1$
                                //$NON-NLS-2$ //$NON-NLS-3$
                                resultcompareStructure.append("</ResultComparisonStructureLines>"); //$NON-NLS-1$
                            }
                            if (contOK == forindex) {
                                resultcompareStructure.append("<ResultComparisonStructureLines>"); //$NON-NLS-1$
                                resultcompareStructure.append("	" + nameActual +
                                    CMMessages.getString("RESULT_COMPARISON_OK_1") + System.getProperty("line.separator")); //$NON-NLS-1$
                                //$NON-NLS-2$ //$NON-NLS-3$
                                resultcompareStructure.append("</ResultComparisonStructureLines>"); //$NON-NLS-1$
                            }
                            return resultcompareStructure.toString();
                        }
                    }
                    my_Actual = (ITypeData)p_Actual.getTypeData().elementAt(index);
                }

            /*	TypeData my_Actual=(TypeData)p_Actual.getTypeData().elementAt(i);
                TypeData my_Target=(TypeData)p_Target.getTypeData().elementAt(i);*/

                if (my_Actual.getKey().equals(my_Target.getKey())) {
                    //if (my_Actual.getStringValue().equalsIgnoreCase(my_Target.getStringValue())) {
                	if (my_Actual.getValue().equals(my_Target.getValue())) {
                        contOK++;
                    }
                    else {
                        if (!stringKeyTarget.equalsIgnoreCase(stringKeyActual))
                            swdistint = true;
                        if (!swdistint) {
                            resultcompareStructure.append("<ResultComparisonStructureLines>"); //$NON-NLS-1$
                            /*resultcompareStructure.append("	" + nameActual + "." + stringKeyActual + "." +
                                my_Actual.getField() + "= " + my_Actual.getStringValue() + System.getProperty("line.separator"));*/
                            resultcompareStructure.append("	" + nameActual + "." + stringKeyActual + "." +
                                    my_Actual.getField() + "= " + my_Actual.getFormattedValue() + System.getProperty("line.separator"));
                            resultcompareStructure.append("</ResultComparisonStructureLines>"); //$NON-NLS-1$
                            resultcompareStructure.append("<ResultComparisonStructureLines>"); //$NON-NLS-1$
                            /*resultcompareStructure.append("	" + nameTarget + "." + stringKeyTarget + "." +
                                my_Target.getField() + "= " + my_Target.getStringValue() + System.getProperty("line.separator"));*/
                            resultcompareStructure.append("	" + nameTarget + "." + stringKeyTarget + "." +
                                    my_Target.getField() + "= " + my_Target.getFormattedValue() + System.getProperty("line.separator"));
                            resultcompareStructure.append("</ResultComparisonStructureLines>"); //$NON-NLS-1$
                            resultcompareStructure.append("<ResultComparisonDupla>"); //$NON-NLS-1$
                            resultcompareStructure.append("  "); //$NON-NLS-1$
                            resultcompareStructure.append("</ResultComparisonDupla>"); //$NON-NLS-1$
                            resultcompareStructure.append(System.getProperty("line.separator")); //$NON-NLS-1$
                        }
                        swcontTestData = true;
                        if (swcont) {
                            desigualstructure++;
                            swcont = false;
                        }
                    }
                }

              /*  else{
                    swdistint= true;
                    swcontTestData=true;
                    resultcompareStructure.append("<ResultComparisonStructureLines>"); //$NON-NLS-1$
                    resultcompareStructure.append(CMMessages.getString("RESULT_COMPARISON_ERROR_KEY")+nameActual+CMMessages.getString("RESULT_COMPARISON_ROW")+Integer.toString(i+1)+System.getProperty("line.separator")); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
                    resultcompareStructure.append("</ResultComparisonStructureLines>"); //$NON-NLS-1$
                    if(!my_Actual.getValue().equalsIgnoreCase(my_Target.getValue()))
                    {

                        resultcompareStructure.append("<ResultComparisonStructureLines>"); //$NON-NLS-1$
                        resultcompareStructure.append("	"+nameActual+"= "+my_Actual.getValue()+System.getProperty("line.separator")); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
                        resultcompareStructure.append("</ResultComparisonStructureLines>"); //$NON-NLS-1$
                        resultcompareStructure.append("<ResultComparisonStructureLines>"); //$NON-NLS-1$
                        resultcompareStructure.append("	"+nameTarget+"= "+my_Target.getValue()+System.getProperty("line.separator")); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
                        resultcompareStructure.append("</ResultComparisonStructureLines>"); //$NON-NLS-1$
                        resultcompareStructure.append("<ResultComparisonDupla>"); //$NON-NLS-1$
						resultcompareStructure.append("  "); //$NON-NLS-1$
                        resultcompareStructure.append("</ResultComparisonDupla>"); //$NON-NLS-1$
                    }
                    if(swcont){
                            desigualstructure++;
                            swcont=false;
                    }
                }*/

                i++;
            }
            if (swdistint) {
                resultcompareStructure.append("<ResultComparisonStructureLines>"); //$NON-NLS-1$
                resultcompareStructure.append("	" + nameTarget + "." + stringKeyTarget +
                    CMMessages.getString("RESULT_COMPARISON_IS_MISSING_IN_ACTUAL") + System.getProperty("line.separator")); //$NON-NLS-1$
                //$NON-NLS-2$ //$NON-NLS-3$
                resultcompareStructure.append("</ResultComparisonStructureLines>"); //$NON-NLS-1$
                resultcompareStructure.append("<ResultComparisonStructureLines>"); //$NON-NLS-1$
                resultcompareStructure.append("	" + nameActual + "." + stringKeyActual +
                    CMMessages.getString("RESULT_COMPARISON_IS_MISSING_IN_EXPECTED") +
                    CMMessages.getString("RESULT_COMPARISON_NEW_1") + System.getProperty("line.separator")); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
                resultcompareStructure.append("</ResultComparisonStructureLines>"); //$NON-NLS-1$
            }
            if (contOK == forindex) {
                resultcompareStructure.append("<ResultComparisonStructureLines>"); //$NON-NLS-1$
                resultcompareStructure.append("	" + nameActual + CMMessages.getString("RESULT_COMPARISON_OK_1") +
                    System.getProperty("line.separator")); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
                resultcompareStructure.append("</ResultComparisonStructureLines>"); //$NON-NLS-1$
            }
            return resultcompareStructure.toString();
    }

    public void jSplitPaneTestDataSetPropertyChange(PropertyChangeEvent e) {
        int newLocation = jSplitPaneTestDataSet.getDividerLocation();
        jSplitPane5.setDividerLocation(newLocation);
        jSplitPaneTestData.setDividerLocation(newLocation);
        jSplitPaneStructureHeader.setDividerLocation(newLocation);
        jSplitPaneStructure.setDividerLocation(newLocation);
    }

    public void jSplitPane5PropertyChange(PropertyChangeEvent e) {
        int newLocation = jSplitPane5.getDividerLocation();
        jSplitPaneTestDataSet.setDividerLocation(newLocation);
        jSplitPaneTestData.setDividerLocation(newLocation);
        jSplitPaneStructureHeader.setDividerLocation(newLocation);
        jSplitPaneStructure.setDividerLocation(newLocation);
    }

    public void jSplitPaneTestDataPropertyChange(PropertyChangeEvent e) {
        int newLocation = jSplitPaneTestData.getDividerLocation();
        jSplitPaneTestDataSet.setDividerLocation(newLocation);
        jSplitPane5.setDividerLocation(newLocation);
        jSplitPaneStructureHeader.setDividerLocation(newLocation);
        jSplitPaneStructure.setDividerLocation(newLocation);
    }

    public void jSplitPaneStructureHeaderPropertyChange(PropertyChangeEvent e) {
        int newLocation = jSplitPaneStructureHeader.getDividerLocation();
        jSplitPaneTestDataSet.setDividerLocation(newLocation);
        jSplitPaneTestData.setDividerLocation(newLocation);
        jSplitPane5.setDividerLocation(newLocation);
        jSplitPaneStructure.setDividerLocation(newLocation);
    }

    public void jSplitPaneStructurePropertyChange(PropertyChangeEvent e) {
        int newLocation = jSplitPaneStructure.getDividerLocation();
        jSplitPaneTestDataSet.setDividerLocation(newLocation);
        jSplitPaneTestData.setDividerLocation(newLocation);
        jSplitPane5.setDividerLocation(newLocation);
        jSplitPaneStructureHeader.setDividerLocation(newLocation);
    }

    public String fieldKeyInString(StructureTestData p_StructureTestData)
    {
        Vector fieldsInVector= findFieldKey(p_StructureTestData);
        StringBuffer result= new StringBuffer("[");
        for(int i =0; i<fieldsInVector.size()-1; i++)
        {
			result.append(fieldsInVector.elementAt(i).toString().toUpperCase());
            result.append(",");
        }
        if(fieldsInVector.size()>0)
            result.append(fieldsInVector.lastElement().toString());
        result.append("]");
        return result.toString();

    }
    public String returnListOfKeyMissing(TestDataSet p_target, TestDataSet p_Actual, boolean swXML)
    {
        StringBuffer result= new StringBuffer();
        Vector fieldKeyInActual= new Vector();
        Vector fieldKeyInTarget= new Vector();
        Vector valueKeyInActual= new Vector();
		Vector valueKeyInTarget = new Vector();
        Vector namesInActual = new Vector();
        Vector namesInTarget = new Vector();
        String nameTestDataSetActual=p_Actual.getName();
        String nameTestDataSetTarget=p_target.getName();
        Vector indexStructuresInActual=new Vector();
        Vector indexStructuresInTarget= new Vector();
        Vector indexTestDataInActual=new Vector();
        Vector indexTestDataInTarget= new Vector();
        Vector testdatasinActual=p_Actual.getM_TestDataCombinations().getM_TestDatas();
        Vector testdatasinTarget= p_target.getM_TestDataCombinations().getM_TestDatas();
        for(int i=0; i<testdatasinActual.size();  i++)
        {
            TestData y_TestData=(TestData)testdatasinActual.elementAt(i);
            String nameTestData= y_TestData.getName();
            TDStructure x_TDStructure=y_TestData.getM_TDStructure();
			Vector structures=x_TDStructure.getM_StructureTestData();
            for(int k=0; k<structures.size();k++)
            {
                StructureTestData x_structureTestData=(StructureTestData)structures.elementAt(k);
                String nameStructure= x_structureTestData.getName();
                if(structureMissingAllKey(x_structureTestData))
                {
                    String menssageMissingKey=CMMessages.getString("RESULT_COMPARISON_MISSING_ALL_KEYS")+" "+nameTestDataSetActual+"."+nameTestData+"."+nameStructure;
                    if(swXML)
                    {
                        result.append("<ResultComparisonStructureLines>");
                        result.append(menssageMissingKey);
                        result.append("</ResultComparisonStructureLines>");
                        result.append(System.getProperty("line.separator")); //$NON-NLS-1$
                    }
                    else
                    {
                        result.append(menssageMissingKey);
                        result.append(System.getProperty("line.separator")); //$NON-NLS-1$
                    }
                }
                else
                {
					fieldKeyInActual.addElement(fieldKeyInString(x_structureTestData));
                	valueKeyInActual.addElement(stringKey(x_structureTestData));
                	namesInActual.addElement(nameTestDataSetActual+"."+nameTestData+"."+nameStructure);
                    indexStructuresInActual.addElement(new Integer(k));
                    indexTestDataInActual.addElement(new Integer(i));
                }
            }
        }
        for(int j=0; j <testdatasinTarget.size(); j++)
        {
            TestData y_TestData=(TestData)testdatasinTarget.elementAt(j);
            String nameTestData= y_TestData.getName();
            TDStructure x_TDStructure=y_TestData.getM_TDStructure();
			Vector structures=x_TDStructure.getM_StructureTestData();
            for(int k=0; k<structures.size();k++)
            {
                StructureTestData x_structureTestData=(StructureTestData)structures.elementAt(k);
                String nameStructure= x_structureTestData.getName();
                 if(structureMissingAllKey(x_structureTestData))
                {
                    String menssageMissingKey=CMMessages.getString("RESULT_COMPARISON_MISSING_ALL_KEYS")+" "+nameTestDataSetTarget+"."+nameTestData+"."+nameStructure;
                    if(swXML)
                    {
                        result.append("<ResultComparisonStructureLines>");
                        result.append(menssageMissingKey);
                        result.append("</ResultComparisonStructureLines>");
                        result.append(System.getProperty("line.separator")); //$NON-NLS-1$
                    }
                    else
                    {
                        result.append(menssageMissingKey);
                        result.append(System.getProperty("line.separator")); //$NON-NLS-1$
                    }
                }
                else
                {
					fieldKeyInTarget.addElement(fieldKeyInString(x_structureTestData));
                	valueKeyInTarget.addElement(stringKey(x_structureTestData));
                	namesInTarget.addElement(nameTestDataSetTarget+"."+nameTestData+"."+nameStructure);
                    indexStructuresInTarget.addElement(new Integer(k));
                    indexTestDataInTarget.addElement(new Integer(j));
                }
            }
        }
        /**
         * estructuras en expected que no estan en Actual
         * */
        for(int n= fieldKeyInTarget.size()-1;n>=0;n--)
        {
            if(!fieldKeyInActual.contains(fieldKeyInTarget.elementAt(n)))
            {
                String nameAndKey= namesInTarget.elementAt(n).toString()+"."+valueKeyInTarget.elementAt(n).toString()+CMMessages.getString("RESULT_COMPARISON_IS_MISSING_IN_ACTUAL"); //$NON-NLS-1$ //$NON-NLS-2$;
                if(swXML)
                {
                    result.append("<ResultComparisonStructureLines>"); //$NON-NLS-1$
                    result.append(nameAndKey);
                    result.append("</ResultComparisonStructureLines>"); //$NON-NLS-1$
                    result.append(System.getProperty("line.separator")); //$NON-NLS-1$
                }
                else
                {
                    result.append(nameAndKey);
                    result.append(System.getProperty("line.separator")); //$NON-NLS-1$
                }
				fieldKeyInTarget.remove(n);
                namesInTarget.remove(n);
                valueKeyInTarget.remove(n);
                indexStructuresInTarget.remove(n);
                indexTestDataInTarget.remove(n);
            }
        }
        for(int n= valueKeyInTarget.size()-1;n>=0;n--)
        {
            if(!valueKeyInActual.contains(valueKeyInTarget.elementAt(n)))
            {
                String nameAndKey= namesInTarget.elementAt(n).toString()+"."+valueKeyInTarget.elementAt(n).toString()+CMMessages.getString("RESULT_COMPARISON_IS_MISSING_IN_ACTUAL"); //$NON-NLS-1$ //$NON-NLS-2$;
                if(swXML)
                {
                    result.append("<ResultComparisonStructureLines>"); //$NON-NLS-1$
                    result.append(nameAndKey);
                    result.append("</ResultComparisonStructureLines>"); //$NON-NLS-1$
                    result.append(System.getProperty("line.separator")); //$NON-NLS-1$
                }
                else
                {
                    result.append(nameAndKey);
                    result.append(System.getProperty("line.separator")); //$NON-NLS-1$
                }
				fieldKeyInTarget.remove(n);
                namesInTarget.remove(n);
                valueKeyInTarget.remove(n);
                indexStructuresInTarget.remove(n);
                indexTestDataInTarget.remove(n);
            }
        }
		/**
         * Estructras que estan en actual y no en expected
         * */
		for(int n= fieldKeyInActual.size()-1;n>=0;n--)
        {
            if(!fieldKeyInTarget.contains(fieldKeyInActual.elementAt(n)))
            {
                String nameAndKey= namesInActual.elementAt(n).toString()+"."+valueKeyInActual.elementAt(n).toString()+CMMessages.getString("RESULT_COMPARISON_IS_MISSING_IN_EXPECTED") +
                                    CMMessages.getString("RESULT_COMPARISON_NEW");
                if(swXML)
                {
                    result.append("<ResultComparisonStructureLines>"); //$NON-NLS-1$
                    result.append(nameAndKey);
                    result.append("</ResultComparisonStructureLines>"); //$NON-NLS-1$
                    result.append(System.getProperty("line.separator")); //$NON-NLS-1$
                }
                else
                {
                    result.append(nameAndKey);
                    result.append(System.getProperty("line.separator")); //$NON-NLS-1$
                }
				fieldKeyInActual.remove(n);
                namesInActual.remove(n);
                valueKeyInActual.remove(n);
                indexStructuresInActual.remove(n);
				indexTestDataInActual.remove(n);
            }
        }
        for(int n= valueKeyInActual.size()-1;n>=0;n--)
        {
            if(!valueKeyInTarget.contains(valueKeyInActual.elementAt(n)))
            {
                String nameAndKey= namesInActual.elementAt(n).toString()+"."+valueKeyInActual.elementAt(n).toString()+CMMessages.getString("RESULT_COMPARISON_IS_MISSING_IN_EXPECTED") +
                                    CMMessages.getString("RESULT_COMPARISON_NEW");
                if(swXML)
                {
                    result.append("<ResultComparisonStructureLines>"); //$NON-NLS-1$
                    result.append(nameAndKey);
                    result.append("</ResultComparisonStructureLines>"); //$NON-NLS-1$
                    result.append(System.getProperty("line.separator")); //$NON-NLS-1$
                }
                else
                {
                    result.append(nameAndKey);
                    result.append(System.getProperty("line.separator")); //$NON-NLS-1$
                }
				fieldKeyInActual.remove(n);
                namesInActual.remove(n);
                valueKeyInActual.remove(n);
                indexStructuresInActual.remove(n);
                indexTestDataInActual.remove(n);
            }

        }
        for(int l=0; l<fieldKeyInActual.size();l++)
                {
                    String fieldsinActual=fieldKeyInActual.elementAt(l).toString();
                    String keyinActual=valueKeyInActual.elementAt(l).toString();
                    for(int f=0;f<fieldKeyInTarget.size();f++)
                    {
						String fieldsinTarget=fieldKeyInTarget.elementAt(f).toString();
                        String keyinTarget= valueKeyInTarget.elementAt(f).toString();
                        if(fieldsinActual.equalsIgnoreCase(fieldsinTarget) && keyinActual.equalsIgnoreCase(keyinTarget) )
                        {
                            int indexTestDataTarget=Integer.parseInt(indexTestDataInTarget.elementAt(f).toString());
                            int indexTestDataActual= Integer.parseInt(indexTestDataInActual.elementAt(l).toString());
                            TestData testdatatarget=(TestData)p_target.getM_TestDataCombinations().getM_TestDatas().elementAt(indexTestDataTarget);
                            TestData testdataactual=(TestData)p_Actual.getM_TestDataCombinations().getM_TestDatas().elementAt(indexTestDataActual);
                            int indexstructuretarget=Integer.parseInt(indexStructuresInTarget.elementAt(f).toString());
                            int indexstructureactual=Integer.parseInt(indexStructuresInActual.elementAt(l).toString());
							StructureTestData structureinTarget=(StructureTestData)testdatatarget.getM_TDStructure().getM_StructureTestData().elementAt(indexstructuretarget);
                            StructureTestData structureinactual=(StructureTestData)testdataactual.getM_TDStructure().getM_StructureTestData().elementAt(indexstructureactual);
                            if(swXML)
                            {
                                result.append(System.getProperty("line.separator")); //$NON-NLS-1$
     	               			result.append("<ResultComparisonTestDataHeader>"); //$NON-NLS-1$
			       	            result.append(System.getProperty("line.separator")); //$NON-NLS-1$
            			        result.append("<ResultComparisonTestDataHeaderLines>"); //$NON-NLS-1$
		                	    result.append(CMMessages.getString("RESULT_COMPARISON_HEADER_TESTDATA_5")); //$NON-NLS-1$
        		            	result.append(testdataactual.getName());
                		   	 	result.append(CMMessages.getString("RESULT_COMPARISON_HEADER_TESTDATA_6")); //$NON-NLS-1$
			                    result.append(testdatatarget.getName());
			                    result.append(System.getProperty("line.separator")); //$NON-NLS-1$
			                    result.append("</ResultComparisonTestDataHeaderLines>"); //$NON-NLS-1$
			                    result.append(System.getProperty("line.separator")); //$NON-NLS-1$
                                result.append("<ResultComparisonStructureHeader>"); //$NON-NLS-1$
                            	result.append(System.getProperty("line.separator")); //$NON-NLS-1$
                            	result.append("<ResultComparisonStructureHeaderLines>"); //$NON-NLS-1$
	                            result.append(CMMessages.getString("RESULT_COMPARISON_HEADER_STRUCTURE_1")); //$NON-NLS-1$
    	                        result.append(structureinactual.getName());
            	                result.append(CMMessages.getString("RESULT_COMPARISON_HEADER_STRUCTURE_2")); //$NON-NLS-1$
        	                    result.append(structureinTarget.getName());
                	            result.append(System.getProperty("line.separator")); //$NON-NLS-1$
                    	        result.append("</ResultComparisonStructureHeaderLines>"); //$NON-NLS-1$
                        	    result.append(System.getProperty("line.separator")); //$NON-NLS-1$
								result.append("<ResultComparisonStructureBody>"); //$NON-NLS-1$
                               result.append(compareStructureTestDataXML(structureinTarget,namesInTarget.elementAt(f).toString(),structureinactual,namesInActual.elementAt(l).toString()));
                                result.append(System.getProperty("line.separator")); //$NON-NLS-1$
                                result.append("</ResultComparisonStructureBody>"); //$NON-NLS-1$
                                result.append(System.getProperty("line.separator")); //$NON-NLS-1$
                                result.append("</ResultComparisonStructureHeader>"); //$NON-NLS-1$
                                result.append(System.getProperty("line.separator")); //$NON-NLS-1$
                                result.append(System.getProperty("line.separator")); //$NON-NLS-1$
			                    result.append("</ResultComparisonTestDataHeader>"); //$NON-NLS-1$
                            }
                            else
                            {
                                result.append("\n"); //$NON-NLS-1$
                    			result.append(CMMessages.getString("RESULT_COMPARISON_HEADER_TESTDATA_5")); //$NON-NLS-1$
                			    result.append(testdataactual.getName());
                    			result.append(CMMessages.getString("RESULT_COMPARISON_HEADER_TESTDATA_6")); //$NON-NLS-1$
                    			result.append(testdatatarget.getName());
			                    result.append("\n"); //$NON-NLS-1$
                                result.append(CMMessages.getString("RESULT_COMPARISON_HEADER_STRUCTURE_1")); //$NON-NLS-1$
                            	result.append(structureinactual.getName());
                            	result.append(CMMessages.getString("RESULT_COMPARISON_HEADER_STRUCTURE_2")); //$NON-NLS-1$
                            	result.append(structureinTarget.getName());
                            	result.append("\n"); //$NON-NLS-1$
                                result.append(compareStructureTestData(structureinTarget,namesInTarget.elementAt(f).toString(),structureinactual,namesInActual.elementAt(l).toString()));
                            }
                        }
                    }
                }
        return result.toString();
    }

      public void ReportResultComparation() {
        try {
            desigualTestData = 0;
            desigualstructure = 0;
            StringBuffer resultcomparationReport = new StringBuffer();
            TestDataSet my_target = (TestDataSet)m_TDStructureTarget.getM_TestDataSet().elementAt(CMIndexTDStructureUpdate.getInstance().getindexTestDataSetTarget());
            TestDataSet my_Actual = (TestDataSet)m_TDStructureActual.getM_TestDataSet().elementAt(CMIndexTDStructureUpdate.getInstance().getindexTestDataSetActual());
            resultcomparationReport.append(CMMessages.getString("RESULT_COMPARISON_HEADER_TESTDATASET_1")); //$NON-NLS-1$
            resultcomparationReport.append(my_Actual.getName());
            resultcomparationReport.append(CMMessages.getString("RESULT_COMPARISON_HEADER_TESTDATASET_2")); //$NON-NLS-1$
            resultcomparationReport.append(my_target.getName());
            resultcomparationReport.append("\n"); //$NON-NLS-1$
            boolean swMenssage = true;
            for (int i = 0; i < my_target.getM_TestDataCombinations().getM_TestDatas().size(); i++)
                for (int j = 0; j < my_Actual.getM_TestDataCombinations().getM_TestDatas().size(); j++) {
                    if (swcontTestData) {
                        desigualTestData++;
                        swcontTestData = false;
                    }
                    TestData my_testDataTarget = (TestData)my_target.getM_TestDataCombinations().getM_TestDatas().elementAt(i);
                    TestData my_testDataActual = (TestData)my_Actual.getM_TestDataCombinations().getM_TestDatas().elementAt(j);
                    if (swMenssage) {
                        resultcomparationReport.append(CMMessages.getString("RESULT_COMPARISON_HEADER_TESTDATA_1")); //$NON-NLS-1$
                        resultcomparationReport.append(my_Actual.getM_TestDataCombinations().getM_TestDatas().size());
                        resultcomparationReport.append("\n"); //$NON-NLS-1$
                        resultcomparationReport.append(CMMessages.getString("RESULT_COMPARISON_HEADER_TESTDATA_2")); //$NON-NLS-1$
                        resultcomparationReport.append(my_target.getM_TestDataCombinations().getM_TestDatas().size());
                        resultcomparationReport.append("\n"); //$NON-NLS-1$
                        int numActual = my_Actual.getM_TestDataCombinations().getM_TestDatas().size();
                        int numTarget = my_target.getM_TestDataCombinations().getM_TestDatas().size();
                        resultcomparationReport.append(CMMessages.getString("RESULT_COMPARISON_HEADER_TESTDATA_3")); //$NON-NLS-1$
                       /* if (numActual > numTarget)
                            resultcomparationReport.append(numActual - numTarget);
                        else
                            resultcomparationReport.append(0);*/
                        resultcomparationReport.append(findTestDataLostinQuest(my_Actual,my_target));//new line modifcada
                        resultcomparationReport.append("\n"); //$NON-NLS-1$
                        resultcomparationReport.append(CMMessages.getString("RESULT_COMPARISON_HEADER_TESTDATA_4")); //$NON-NLS-1$
                       /* if (numTarget > numActual)
                            resultcomparationReport.append(numTarget - numActual);
                        else
                            resultcomparationReport.append(0);*/
                        resultcomparationReport.append(findTestDataLostinQuest(my_target,my_Actual));//new line modifcada
                        resultcomparationReport.append("\n"); //$NON-NLS-1$
                        swMenssage = false;
                        /**
                         * cambios nuevos llave
                         * hcanedo_10_08_2004_Begin
                         * */
                        resultcomparationReport.append("\n"); //$NON-NLS-1$
						resultcomparationReport.append(returnListOfKeyMissing(my_target,my_Actual,false));
                    }
            }
            resultcomparationReport.append(menssageForRepeatAndMissingKey(my_target, my_Actual, false));
         /*   resultcomparationReport.append(CMMessages.getString("RESULT_COMPARISON_FOOT_TESTDATA")+" "+ Integer.toString(desigualTestData)+ "\n"); //$NON-NLS-1$
            //$NON-NLS-2$
            resultcomparationReport.append(CMMessages.getString("RESULT_COMPARISON_FOOT_STRUCTURE")+" "+ Integer.toString(desigualstructure) + "\n"); //$NON-NLS-1$
            //$NON-NLS-2$*/
            jEditorPane1.setText(resultcomparationReport.toString());
        }
        catch (Exception ex) {
        }
    }

  public String ReportResultComparationXML() {
        try {
            desigualTestData = 0;
            desigualstructure = 0;
            StringBuffer resultcomparationReport = new StringBuffer();
            TestDataSet my_target = (TestDataSet)m_TDStructureTarget.getM_TestDataSet().elementAt(CMIndexTDStructureUpdate.getInstance().getindexTestDataSetTarget());
            TestDataSet my_Actual = (TestDataSet)m_TDStructureActual.getM_TestDataSet().elementAt(CMIndexTDStructureUpdate.getInstance().getindexTestDataSetActual());
            resultcomparationReport.append("<?xml version=" + '"' + "1.0" + '"' + " encoding=" + '"' + "iso-8859-1" + '"' + " ?>"); //$NON-NLS-1$
            //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
            resultcomparationReport.append(System.getProperty("line.separator")); //$NON-NLS-1$
            resultcomparationReport.append("<ResultComparison>"); //$NON-NLS-1$
            resultcomparationReport.append(System.getProperty("line.separator")); //$NON-NLS-1$

            /** nuevo codigo para la cabezera de los reportes en html */
		resultcomparationReport.append("<HeaderImg>"); //$NON-NLS-1$
        resultcomparationReport.append(BusinessRules.REPORT_IMAGEFLD+"/"+CMIcon.CASEMAKER_LOGO.getFilename());
        resultcomparationReport.append("</HeaderImg>"); //$NON-NLS-1$
        resultcomparationReport.append(System.getProperty("line.separator")); //$NON-NLS-1$
         resultcomparationReport.append(System.getProperty("line.separator")); //$NON-NLS-1$*/

            resultcomparationReport.append("<ProjectName>"); //$NON-NLS-1$
            resultcomparationReport.append(m_Frame.getTreeWorkspaceView().getCurrentProject().getName());//m_TestObjectReference.getM_Project().getM_Name());
            resultcomparationReport.append("</ProjectName>"); //$NON-NLS-1$
            resultcomparationReport.append(System.getProperty("line.separator")); //$NON-NLS-1$
            resultcomparationReport.append("<TestObjectName>"); //$NON-NLS-1$
            resultcomparationReport.append(m_TestObject.getName());
            resultcomparationReport.append("</TestObjectName>"); //$NON-NLS-1$
            resultcomparationReport.append(System.getProperty("line.separator")); //$NON-NLS-1$
            resultcomparationReport.append("<TestObjectDescription>"); //$NON-NLS-1$
            resultcomparationReport.append(m_TestObject.getDescription());
            resultcomparationReport.append("</TestObjectDescription>"); //$NON-NLS-1$
            resultcomparationReport.append(System.getProperty("line.separator")); //$NON-NLS-1$
            resultcomparationReport.append("<ActualDate>"); //$NON-NLS-1$
            Date date = new java.util.Date();
            resultcomparationReport.append(date.toString());
            resultcomparationReport.append("</ActualDate>"); //$NON-NLS-1$
            resultcomparationReport.append(System.getProperty("line.separator")); //$NON-NLS-1$
            resultcomparationReport.append("<UserName>"); //$NON-NLS-1$
            String userName = System.getProperty("user.name"); //$NON-NLS-1$
            if (userName != null) {
                resultcomparationReport.append(userName);
            }
            else {
                resultcomparationReport.append("");
            }
            resultcomparationReport.append("</UserName>"); //$NON-NLS-1$
            resultcomparationReport.append(System.getProperty("line.separator")); //$NON-NLS-1$*/
            ///////////////////////////////////////////////////////////////////////////////////
            resultcomparationReport.append("<ResultComparisonHeader1>"); //$NON-NLS-1$
            resultcomparationReport.append(CMMessages.getString("RESULT_COMPARISON_HEADER_TESTDATASET_1")); //$NON-NLS-1$
            resultcomparationReport.append(my_Actual.getName());
            resultcomparationReport.append(CMMessages.getString("RESULT_COMPARISON_HEADER_TESTDATASET_2")); //$NON-NLS-1$
            resultcomparationReport.append(my_target.getName());
            resultcomparationReport.append(System.getProperty("line.separator")); //$NON-NLS-1$
            resultcomparationReport.append("</ResultComparisonHeader1>"); //$NON-NLS-1$
            resultcomparationReport.append(System.getProperty("line.separator")); //$NON-NLS-1$
            boolean swMenssage = true;
            for (int i = 0; i < my_target.getM_TestDataCombinations().getM_TestDatas().size(); i++)
                for (int j = 0; j < my_Actual.getM_TestDataCombinations().getM_TestDatas().size(); j++) {
                    if (swcontTestData) {
                        desigualTestData++;
                        swcontTestData = false;
                    }
                    TestData my_testDataTarget = (TestData)my_target.getM_TestDataCombinations().getM_TestDatas().elementAt(i);
                    TestData my_testDataActual = (TestData)my_Actual.getM_TestDataCombinations().getM_TestDatas().elementAt(j);
                    if (swMenssage) {
                        resultcomparationReport.append("<ResultComparisonHeader1>"); //$NON-NLS-1$
                        resultcomparationReport.append(CMMessages.getString("RESULT_COMPARISON_HEADER_TESTDATA_1")); //$NON-NLS-1$
                        resultcomparationReport.append(my_Actual.getM_TestDataCombinations().getM_TestDatas().size());
                        resultcomparationReport.append(System.getProperty("line.separator")); //$NON-NLS-1$
                        resultcomparationReport.append("</ResultComparisonHeader1>"); //$NON-NLS-1$
                        resultcomparationReport.append(System.getProperty("line.separator")); //$NON-NLS-1$
                        resultcomparationReport.append("<ResultComparisonHeader1>"); //$NON-NLS-1$
                        resultcomparationReport.append(CMMessages.getString("RESULT_COMPARISON_HEADER_TESTDATA_2")); //$NON-NLS-1$
                        resultcomparationReport.append(my_target.getM_TestDataCombinations().getM_TestDatas().size());
                        resultcomparationReport.append(System.getProperty("line.separator")); //$NON-NLS-1$
                        resultcomparationReport.append("</ResultComparisonHeader1>"); //$NON-NLS-1$
                        resultcomparationReport.append(System.getProperty("line.separator")); //$NON-NLS-1$
                        int numActual = my_Actual.getM_TestDataCombinations().getM_TestDatas().size();
                        int numTarget = my_target.getM_TestDataCombinations().getM_TestDatas().size();
                        resultcomparationReport.append("<ResultComparisonHeader1>"); //$NON-NLS-1$
                        resultcomparationReport.append(CMMessages.getString("RESULT_COMPARISON_HEADER_TESTDATA_3")); //$NON-NLS-1$
                       /* if (numActual > numTarget)
                            resultcomparationReport.append(numActual - numTarget);
                        else
                            resultcomparationReport.append(0);*/
						resultcomparationReport.append(findTestDataLostinQuest(my_Actual,my_target));//new line modifcada
                        resultcomparationReport.append(System.getProperty("line.separator")); //$NON-NLS-1$
                        resultcomparationReport.append("</ResultComparisonHeader1>"); //$NON-NLS-1$
                        resultcomparationReport.append(System.getProperty("line.separator")); //$NON-NLS-1$
                        resultcomparationReport.append("<ResultComparisonHeader1>"); //$NON-NLS-1$
                        resultcomparationReport.append(CMMessages.getString("RESULT_COMPARISON_HEADER_TESTDATA_4")); //$NON-NLS-1$
                        /*if (numTarget > numActual)
                            resultcomparationReport.append(numTarget - numActual);
                        else
                            resultcomparationReport.append(0);*/
                        resultcomparationReport.append(findTestDataLostinQuest(my_target,my_Actual));//new line modifcada
                        resultcomparationReport.append(System.getProperty("line.separator")); //$NON-NLS-1$
                        resultcomparationReport.append("</ResultComparisonHeader1>"); //$NON-NLS-1$
                        swMenssage = false;
                        /**
                         * cambios nuevos llave
                         * hcanedo_10_08_2004_Begin
                         * */
                           resultcomparationReport.append(System.getProperty("line.separator")); //$NON-NLS-1$
						resultcomparationReport.append(returnListOfKeyMissing(my_target,my_Actual,true));

                    }
                }
            resultcomparationReport.append(menssageForRepeatAndMissingKey(my_target, my_Actual, true));
            /*resultcomparationReport.append("<ResultComparisonFoot>"); //$NON-NLS-1$
            resultcomparationReport.append(CMMessages.getString("RESULT_COMPARISON_FOOT_TESTDATA") +" "+
                Integer.toString(desigualTestData) + System.getProperty("line.separator")); //$NON-NLS-1$ //$NON-NLS-2$
            resultcomparationReport.append("</ResultComparisonFoot>"); //$NON-NLS-1$
            resultcomparationReport.append("<ResultComparisonFoot>"); //$NON-NLS-1$
            resultcomparationReport.append(CMMessages.getString("RESULT_COMPARISON_FOOT_STRUCTURE") +" "+
                Integer.toString(desigualstructure) + System.getProperty("line.separator")); //$NON-NLS-1$ //$NON-NLS-2$
            resultcomparationReport.append("</ResultComparisonFoot>"); //$NON-NLS-1$*/
            resultcomparationReport.append("</ResultComparison>"); //$NON-NLS-1$
            return (resultcomparationReport.toString());
        }
        catch (Exception ex) {
            StringBuffer resultcomparationReport = new StringBuffer();
            resultcomparationReport.append("<?xml version=" + '"' + "1.0" + '"' + " encoding=" + '"' + "iso-8859-1" + '"' + " ?>"); //$NON-NLS-1$
            resultcomparationReport.append("<ResultComparison>"); //$NON-NLS-1$
//HCanedo_30112004_Begin
		resultcomparationReport.append("<HeaderImg>"); //$NON-NLS-1$
        resultcomparationReport.append(BusinessRules.REPORT_IMAGEFLD+"/"+CMIcon.CASEMAKER_LOGO.getFilename());
        resultcomparationReport.append("</HeaderImg>"); //$NON-NLS-1$

//HCanedo_30112004_End
            resultcomparationReport.append("<ProjectName>"); //$NON-NLS-1$
            resultcomparationReport.append(m_Frame.getTreeWorkspaceView().getCurrentProject().getName());//m_TestObjectReference.getM_Project().getM_Name());
            resultcomparationReport.append("</ProjectName>"); //$NON-NLS-1$
            resultcomparationReport.append(System.getProperty("line.separator")); //$NON-NLS-1$
            resultcomparationReport.append("<TestObjectName>"); //$NON-NLS-1$
            resultcomparationReport.append(m_TestObject.getName());
            resultcomparationReport.append("</TestObjectName>"); //$NON-NLS-1$
            resultcomparationReport.append(System.getProperty("line.separator")); //$NON-NLS-1$
            resultcomparationReport.append("<TestObjectDescription>"); //$NON-NLS-1$
            resultcomparationReport.append(m_TestObject.getDescription());
            resultcomparationReport.append("</TestObjectDescription>"); //$NON-NLS-1$
            resultcomparationReport.append(System.getProperty("line.separator")); //$NON-NLS-1$
            resultcomparationReport.append("<ActualDate>"); //$NON-NLS-1$
            Date date = new java.util.Date();
            resultcomparationReport.append(date.toString());
            resultcomparationReport.append("</ActualDate>"); //$NON-NLS-1$
            resultcomparationReport.append(System.getProperty("line.separator")); //$NON-NLS-1$
            resultcomparationReport.append("<UserName>"); //$NON-NLS-1$
            String userName = System.getProperty("user.name"); //$NON-NLS-1$
            if (userName != null) {
                resultcomparationReport.append(userName);
            }
            else {
                resultcomparationReport.append("");
            }
            resultcomparationReport.append("</UserName>"); //$NON-NLS-1$
            resultcomparationReport.append(System.getProperty("line.separator")); //$NON-NLS-1$*/
            resultcomparationReport.append("</ResultComparison>"); //$NON-NLS-1$
            return (resultcomparationReport.toString());
        }
    }

  /**
   * Metodo que devuelve la cantidad de TestDatas perdidos de losts(TestDataSet) en Quest(TestDataSet)
   * donde lost es el testdataset del que se quiere saber que testdatas no estan presentes en Quest
   * donde Quest es el TestDataSet donde se buscara los testdatas que faltan en losts
   * @param lost de tipo TestDataSet es el TestDataSet del que se quiere saber que testdatas faltan
   * @parma quest de tipo TestDataSetes el TestDataSet donde se relaizara la busqueda
   * @return cant de tipo int que refleja la cantidad de TestDatas presentes en lost y flatantes en Quest
   */
  public int findTestDataLostinQuest(TestDataSet lost, TestDataSet quest)
  {
    int cant=0;

    TestDataCombinations lostTestDataCombination =lost.getM_TestDataCombinations();
    TestDataCombinations questTestDataCombination =quest.getM_TestDataCombinations();
    for (int i = 0; i < lostTestDataCombination.getM_TestDatas().size(); i++) {
        boolean swExistTestData=false;
		TestData lostTestData=(TestData)lostTestDataCombination.getM_TestDatas().elementAt(i);
        Vector lostFieldKey= new Vector();
        Vector lostValueKey=new Vector();
       for(int k=0;k<lostTestData.getM_TDStructure().getM_StructureTestData().size(); k++)
       {
            StructureTestData lostStructure=(StructureTestData)lostTestData.getM_TDStructure().getM_StructureTestData().elementAt(k);
            lostFieldKey.addElement(fieldKeyInString(lostStructure));
            lostValueKey.addElement(stringKey(lostStructure));
       }
		for (int j = 0; j < questTestDataCombination.getM_TestDatas().size(); j++) {
			TestData questTestData=(TestData)questTestDataCombination.getM_TestDatas().elementAt(j);
            Vector questFieldKey= new Vector();
            Vector questValueKey=new Vector();
            for(int k=0;k<questTestData.getM_TDStructure().getM_StructureTestData().size(); k++)
            {
                StructureTestData questStructure=(StructureTestData)questTestData.getM_TDStructure().getM_StructureTestData().elementAt(k);
                questFieldKey.addElement(fieldKeyInString(questStructure));
                questValueKey.addElement(stringKey(questStructure));
            }
            if(!swExistTestData)
            {
				if(questFieldKey.size()==lostFieldKey.size())
                {
					boolean swoldinQuest=true;
                    int cantStructureinLost=lostFieldKey.size();
                    while(swoldinQuest && cantStructureinLost>0)
                    {
                        int indexKey= questFieldKey.indexOf(lostFieldKey.elementAt(cantStructureinLost-1));
                        if(indexKey==-1)
                        {
                            swoldinQuest=false;
                        }
                        else{
                            if(questValueKey.elementAt(indexKey).equals(lostValueKey.elementAt(cantStructureinLost-1)))
                                cantStructureinLost--;
                            else
                                swoldinQuest=false;
                        }
                    }
                    if(swoldinQuest)
                    {
						swExistTestData=true;
                    }
                }
            }

		}
        if(!swExistTestData)
            cant++;
        
    }
    return cant;
  }
//hcanedo_18_10_2004_begin
    public void jEditorPane1MouseClicked(MouseEvent e) {
		if(	e.getModifiers()==Event.META_MASK)
        {
            m_Frame.jPopupMenuResultComparison.show(jEditorPane1,e.getX(),e.getY());
        }

    }
//hcanedo_18_10_2004_end
    private Session2 m_Session;
    private TestObject m_TestObject;
    private TestObjectReference m_TestObjectReference;
    private CMFrameView m_Frame;
    private JSplitPane jSplitPane1 = new JSplitPane();
    private JSplitPane jSplitPane2 = new JSplitPane();
    private JSplitPane jSplitPane3 = new JSplitPane();
    private JSplitPane jSplitPane4 = new JSplitPane();
    private JSplitPane jSplitPaneTestDataSet = new JSplitPane();
    private JSplitPane jSplitPaneTestData = new JSplitPane();
    private JSplitPane jSplitPaneStructureHeader = new JSplitPane();
    private JSplitPane jSplitPaneStructure = new JSplitPane();
    private CMScrollPaneTestDataSetDescription jScrollPaneTestDataSetTarget; // = new CMScrollPaneTestDataSetDescription(m_Frame);
    private CMScrollPaneTestDataSetDescription jScrollPaneTestDataSetActual; // = new CMScrollPaneTestDataSetDescription(m_Frame);
    private CMScrollPaneTestDataCombinationsDescription jScrollPaneTestDataTarget; // = new CMScrollPaneTestDataCombinationsDescription(m_Frame);
    private CMScrollPaneTestDataCombinationsDescription jScrollPaneTestDataActual; // = new CMScrollPaneTestDataCombinationsDescription(m_Frame);
   /* private JScrollPane jScrollPaneStructureTarget = new JScrollPane();
    private JScrollPane jScrollPaneStructureActual = new JScrollPane();*/
    private CMScrollpaneStructureDescription jScrollPaneStructureHeaderTarget; // = new CMScrollpaneStructureDescription(m_Frame);
    private CMScrollpaneStructureDescription jScrollPaneStructureHeaderActual; // = new CMScrollpaneStructureDescription(m_Frame);
    private JScrollPane jScrollPaneReport = new JScrollPane();
    private JEditorPane jEditorPane1 = new JEditorPane();
    private CMGridTDStructure gridTDStructureTarget; 
    private CMGridTDStructure gridTDStructureActual;
   /* private CMScrollpaneGridTDStructure paneGridTDStructureTarget;
    private CMScrollpaneGridTDStructure paneGridTDStructureActual;
    */
    private JScrollPane paneGridTDStructureTarget = new JScrollPane();
    private JScrollPane paneGridTDStructureActual = new JScrollPane();
    
    private ResultComparation m_ResultComparation;
    private TDStructure m_TDStructureTarget = new TDStructure();
    private TDStructure m_TDStructureActual = new TDStructure();
    private TDStructure m_TDStructure;
    private JSplitPane jSplitPane5 = new JSplitPane();
    private JButton jButton1 = new JButton();
    private JButton jButton2 = new JButton();
    private int desigualTestData = 0;
    private int desigualstructure = 0;
    private boolean swcontTestData = false;
	

	/**
	 * @param gridTDStructureActual The gridTDStructureActual to set.
	 */
	/*public void setGridTDStructureActual(CMGridTDStructure gridTDStructureActual) {
		this.paneGridTDStructureActual.getCMGridTDStructure() = gridTDStructureActual;
	}
*/ //ccastedo comments 05.01.07
	/**
	 * @return Returns the gridTDStructureTarget.
	 */
	public CMGridTDStructure getGridTDStructureTarget() {
		return gridTDStructureTarget;
	}

	/**
	 * @param gridTDStructureTarget The gridTDStructureTarget to set.
	 */
	/*public void setGridTDStructureTarget(CMGridTDStructure gridTDStructureTarget) {
		this.gridTDStructureTarget = gridTDStructureTarget;
	}*/  //ccastedo comments 05.01.07

	/**
	 * @return Returns the jEditorPane1.
	 */
	public JEditorPane getJEditorPane1() {
		return jEditorPane1;
	}

	/**
	 * @param editorPane1 The jEditorPane1 to set.
	 */
	public void setJEditorPane1(JEditorPane editorPane1) {
		jEditorPane1 = editorPane1;
	}

	/**
	 * @return Returns the jScrollPaneTestDataActual.
	 */
	public CMScrollPaneTestDataCombinationsDescription getJScrollPaneTestDataActual() {
		return jScrollPaneTestDataActual;
	}

	/**
	 * @param scrollPaneTestDataActual The jScrollPaneTestDataActual to set.
	 */
	public void setJScrollPaneTestDataActual(
			CMScrollPaneTestDataCombinationsDescription scrollPaneTestDataActual) {
		jScrollPaneTestDataActual = scrollPaneTestDataActual;
	}

	/**
	 * @return Returns the jScrollPaneTestDataSetActual.
	 */
	public CMScrollPaneTestDataSetDescription getJScrollPaneTestDataSetActual() {
		return jScrollPaneTestDataSetActual;
	}

	/**
	 * @param scrollPaneTestDataSetActual The jScrollPaneTestDataSetActual to set.
	 */
	public void setJScrollPaneTestDataSetActual(
			CMScrollPaneTestDataSetDescription scrollPaneTestDataSetActual) {
		jScrollPaneTestDataSetActual = scrollPaneTestDataSetActual;
	}

	/**
	 * @return Returns the jScrollPaneTestDataSetTarget.
	 */
	public CMScrollPaneTestDataSetDescription getJScrollPaneTestDataSetTarget() {
		return jScrollPaneTestDataSetTarget;
	}

	/**
	 * @param scrollPaneTestDataSetTarget The jScrollPaneTestDataSetTarget to set.
	 */
	public void setJScrollPaneTestDataSetTarget(
			CMScrollPaneTestDataSetDescription scrollPaneTestDataSetTarget) {
		jScrollPaneTestDataSetTarget = scrollPaneTestDataSetTarget;
	}

	/**
	 * @return Returns the jScrollPaneTestDataTarget.
	 */
	public CMScrollPaneTestDataCombinationsDescription getJScrollPaneTestDataTarget() {
		return jScrollPaneTestDataTarget;
	}

	/**
	 * @param scrollPaneTestDataTarget The jScrollPaneTestDataTarget to set.
	 */
	public void setJScrollPaneTestDataTarget(
			CMScrollPaneTestDataCombinationsDescription scrollPaneTestDataTarget) {
		jScrollPaneTestDataTarget = scrollPaneTestDataTarget;
	}

	/**
	 * @return Returns the jScrollPaneStructureHeaderActual.
	 */
	public CMScrollpaneStructureDescription getJScrollPaneStructureHeaderActual() {
		return jScrollPaneStructureHeaderActual;
	}

	/**
	 * @param scrollPaneStructureHeaderActual The jScrollPaneStructureHeaderActual to set.
	 */
	public void setJScrollPaneStructureHeaderActual(
			CMScrollpaneStructureDescription scrollPaneStructureHeaderActual) {
		jScrollPaneStructureHeaderActual = scrollPaneStructureHeaderActual;
	}

	/**
	 * @return Returns the jScrollPaneStructureHeaderTarget.
	 */
	public CMScrollpaneStructureDescription getJScrollPaneStructureHeaderTarget() {
		return jScrollPaneStructureHeaderTarget;
	}

	/**
	 * @param scrollPaneStructureHeaderTarget The jScrollPaneStructureHeaderTarget to set.
	 */
	public void setJScrollPaneStructureHeaderTarget(
			CMScrollpaneStructureDescription scrollPaneStructureHeaderTarget) {
		jScrollPaneStructureHeaderTarget = scrollPaneStructureHeaderTarget;
	}
	public void addHorizontalScrollBarSynchronizer(JScrollPane first, JScrollPane second){
		JScrollBar  sb1 = first.getHorizontalScrollBar();
        JScrollBar  sb2 = second.getHorizontalScrollBar();
        
        sb2.getModel().addChangeListener( new ScrollBarSynchronizer( sb1 ) );
	}
	public void addVerticalScrollBarSynchronizer(JScrollPane first, JScrollPane second){
		JScrollBar  sb1 = first.getVerticalScrollBar();
        JScrollBar  sb2 = second.getVerticalScrollBar();
        
        sb2.getModel().addChangeListener( new ScrollBarSynchronizer( sb1 ) );
	}

	public CMGridTDStructure getGridTDStructureActual() {
		return gridTDStructureActual;
	}
}
