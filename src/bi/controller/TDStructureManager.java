package bi.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.JOptionPane;
import javax.swing.undo.UndoableEdit;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import model.BusinessRules;
import model.Combination;
import model.Element;
import model.EquivalenceClass;
import model.ITypeData;
import model.Session2;
import model.StdCombination;
import model.Structure;
import model.StructureTestData;
import model.TDStructure;
import model.TestCase;
import model.TestCaseExternalReports;
import model.TestData;
import model.TestDataCombinations;
import model.TestDataSet;
import model.TestObject;
import model.edit.CMModelEditFactory;
import model.util.IdSet;

import org.apache.log4j.Logger;

import bi.controller.tdparser.american.AmericanParser;
import bi.controller.tdparser.european.EuropeanParser;
import bi.controller.testdata.CMTypeDataValueGenerator;
import bi.controller.utils.CMCharUtils;
import bi.controller.utils.CMLocalReferenceConvert;
import bi.controller.utils.CMNumberUtils;
import bi.view.icons.CMIcon;
import bi.view.lang.CMMessages;
import bi.view.mainframeviews.CMApplication;
import bi.view.mainframeviews.CMFrameView;
import bi.view.resultscomparationviews.CMResultsComparationPanelView;
import bi.view.testdataviews.CMDialogFormulasValues;
import bi.view.testdataviews.CMGridTDStructure;
import bi.view.testdataviews.CMIndexTDStructureUpdate;
import bi.view.undomanagementviews.CMCompoundEdit;

public class TDStructureManager {

	public static final TDStructureManager INSTANCE = new TDStructureManager();

    public TDStructureManager() {
        dataCombination = new Vector();
        idStructureVector = new IdSet();
        idTestDataSetVector = new IdSet();
        idTestDataVector = new IdSet();
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public String generateTypeDataForTDStructure(TestCase p_TestCase, Structure p_Structure) {
        StringBuffer contentBuffer = new StringBuffer();
        String testCaseLine;
        Vector equivalenceClassCombinations = null;
        Vector combination = null;
        equivalenceClassCombinations = generateEquivalenceClassCombinations(p_Structure, p_TestCase);
        int numOfEquivalenceClassCombinations = equivalenceClassCombinations.size();
        for (int i = 0; i < numOfEquivalenceClassCombinations; i++) {
            combination = (Vector)equivalenceClassCombinations.elementAt(i);
            testCaseLine = getTestCaseLine(combination, p_TestCase, p_Structure);
            contentBuffer.append(testCaseLine);
            contentBuffer.append(BusinessRules.NEW_LINE);
        }
        return contentBuffer.toString();
    }

    public String getTestCaseLine(Vector combination, TestCase p_TestCase, Structure p_Structure) {
        StringBuffer testCaseLine = new StringBuffer();
        int numOfElements = p_Structure.getLnkElements().size();
        Element element = null;
        EquivalenceClass equivalenceClass = null;
        testCaseLine.append(BusinessRules.REPORT_CSV_SEPARATOR);
        for (int i = 0; i < numOfElements; i++) {
            element = (Element)p_Structure.getLnkElements().elementAt(i);
            equivalenceClass = getTheEquivalenceClassThatBelongsToTheElement(element, combination);
            if (equivalenceClass != null) {
                if (equivalenceClass.getValue().equals(""))
                    testCaseLine.append(BusinessRules.TESTDATA_EMPTY_AT_EQUIVALENCE_CLASS_IN_TEST_CASE);
                else
                    testCaseLine.append(equivalenceClass.getValue());
                testCaseLine.append(BusinessRules.REPORT_CSV_SEPARATOR);
            }
            //svonborries_27072006 the else is commented because it cause the generation of aditional empty TD
            else {
                testCaseLine.append(BusinessRules.TESTDATA_EMPTY_AT_EQUIVALENCE_CLASS_IN_TEST_CASE);
                testCaseLine.append(BusinessRules.REPORT_CSV_SEPARATOR);
            }
        }
        return testCaseLine.toString();
    }

    public EquivalenceClass getTheEquivalenceClassThatBelongsToTheElement(Element p_Element, Vector p_Combination) {
        int numOfEquivalenceClasses = p_Combination.size();
        EquivalenceClass equivalenceClass = null;
        for (int i = 0; i < numOfEquivalenceClasses; i++) {
            equivalenceClass = (EquivalenceClass)p_Combination.elementAt(i);
            if (equivalenceClass.getLnkElement() == p_Element) {
                return equivalenceClass;
            }
        }
        return null;
    }

    public Vector getEquivalenceClassesOfElementInTestCase(TestCase p_TestCase, Element p_Element, Structure p_Structure) {
        Vector equivalenceClassesInTestCase = new Vector(0);
        int numOfEquivalenceClasses = p_Element.getEquivalenceClasses().size();
        EquivalenceClass equivalenceClass = null;
        for (int i = 0; i < numOfEquivalenceClasses; i++) {
            equivalenceClass = (EquivalenceClass)p_Element.getEquivalenceClasses().get(i);
            if (p_TestCase.getEquivalenceClasses().contains(equivalenceClass)) {
                equivalenceClassesInTestCase.addElement(equivalenceClass);
            }
            else if (m_CombinationManager.getCombinationsWithEquivalenceClassInTestCase(equivalenceClass, p_TestCase,
                p_Structure).size() > 0) {
                    equivalenceClassesInTestCase.addElement(equivalenceClass);
            }
            else if (getStdCombinationOfTestCaseAtTheEquivalenceClassInTestCase(equivalenceClass, p_TestCase) != null) {
                Vector visibleEquivalenceClasses = getVisibleEquivalenceClassesOfStdCombinationInTestCase(p_TestCase);
                if (visibleEquivalenceClasses.contains(equivalenceClass)) {
                    equivalenceClassesInTestCase.addElement(equivalenceClass);
                }
            }
        }
        return equivalenceClassesInTestCase;
    }

    public boolean isEquivalenceClassUsedInTestCase(EquivalenceClass equivalenceClass, TestCase p_TestCase,
        Structure p_Structure) {
            if (p_TestCase.getLnkEquivalenceClasses().contains(equivalenceClass)) {
                return true;
            }
            else if (m_CombinationManager.getCombinationsWithEquivalenceClassInTestCase(equivalenceClass, p_TestCase,
                p_Structure).size() > 0) {
                    return true;
            }
            else if (getStdCombinationOfTestCaseAtTheEquivalenceClassInTestCase(equivalenceClass, p_TestCase) != null) {
                Vector visibleEquivalenceClasses = getVisibleEquivalenceClassesOfStdCombinationInTestCase(p_TestCase);
                if (visibleEquivalenceClasses.contains(equivalenceClass)) {
                    return true;
                }
            }
            return false;
    }

    public Vector generateEquivalenceClassCombinations(Structure p_Structure, TestCase p_TestCase) {
        int numOfCombinations = 1;
        int numOfDependentElements = p_Structure.getLnkElements().size();
        int numOfDependentEquivalenceClasses = 0;
        Vector dependentElements = new Vector(0);
        Vector dependentEquivalenceClasses = new Vector(0);
        Vector dependentElement = null;
        Vector combinationEquivalenceClasses = null;
        for (int de = 0; de < numOfDependentElements; de++) {
            Element element = (Element)p_Structure.getLnkElements().elementAt(de);
            int numOfEquivalenceClasses = element.getEquivalenceClasses().size();
            dependentElement = new Vector(0);
            for (int ec = 0; ec < numOfEquivalenceClasses; ec++) {
                EquivalenceClass equivalenceClass = (EquivalenceClass)element.getEquivalenceClasses().get(ec);
                if (isEquivalenceClassUsedInTestCase(equivalenceClass, p_TestCase, p_Structure)) {
                    numOfDependentEquivalenceClasses++;
                    dependentElement.addElement(equivalenceClass);
                }
            }
            if (dependentElement.size() > 0) {
            	if(numOfCombinations < dependentElement.size())
            		numOfCombinations = dependentElement.size();//numOfCombinations * dependentElement.size();
                dependentElements.add(dependentElement);
            }
        }

        numOfDependentElements = dependentElements.size();
        Vector combinationElements = new Vector(0);
        int tempNumOfCombinations = numOfCombinations;
        int distance = 1;
        for (int de = 0; de < numOfDependentElements; de++) {
            dependentElement = (Vector)dependentElements.elementAt(de);
            numOfDependentEquivalenceClasses = dependentElement.size();
            combinationEquivalenceClasses = new Vector(0);
          /*  if (numOfDependentEquivalenceClasses > 0) {
                distance = tempNumOfCombinations / numOfDependentEquivalenceClasses;
            }*/
            Vector pattern = new Vector(0);
            for (int dec = 0; dec < numOfDependentEquivalenceClasses; dec++) {
                EquivalenceClass equivalenceClass = (EquivalenceClass)dependentElement.elementAt(dec);
               // for (int x = 0; x < distance; x++) { //build distance
                    pattern.addElement(equivalenceClass);
                //}
            }
            if(numOfCombinations > pattern.size()){
            	for(int x=pattern.size(); x<tempNumOfCombinations;x++){
            		EquivalenceClass equivalenceClass = (EquivalenceClass)dependentElement.firstElement();
            		pattern.addElement(equivalenceClass);
            	}
            }
            int countPattern = pattern.size();
            /*int numOfAddingPatterns = 0;
            if (countPattern > 0) {
                numOfAddingPatterns = numOfCombinations / countPattern;
            }*/
            //for (int p = 0; p < numOfAddingPatterns; p++) {
                for (int m = 0; m < countPattern; m++) {
                    EquivalenceClass equivalenceClass = (EquivalenceClass)pattern.elementAt(m);
                    combinationEquivalenceClasses.add(equivalenceClass);
                }
            //}
            /*if (numOfDependentEquivalenceClasses > 0) {
                tempNumOfCombinations = tempNumOfCombinations / numOfDependentEquivalenceClasses;
            }*/
            combinationElements.addElement(combinationEquivalenceClasses);
        }
        Vector combinations = new Vector(0);
        Vector combination = null;
        for (int i = 0; i < numOfCombinations; i++) {
            combination = new Vector(0);
            combinations.addElement(combination);
        }
        int numOfCombinationElements = combinationElements.size();
        for (int i = 0; i < numOfCombinationElements; i++) {
            combinationEquivalenceClasses = (Vector)combinationElements.elementAt(i);
            int numOfCombinationEquivalenceClasses = combinationEquivalenceClasses.size();
            for (int j = 0; j < numOfCombinationEquivalenceClasses; j++) {
                EquivalenceClass equivalenceClass = (EquivalenceClass)combinationEquivalenceClasses.elementAt(j);
                combination = (Vector)combinations.elementAt(j);
                combination.addElement(equivalenceClass);
            }
        }
        return combinations;
    }

    public Vector getVisibleEquivalenceClassesOfStdCombinationInTestCase(TestCase p_TestCase) {
        StdCombination stdCombination = getStdCombinationOfTestCase(p_TestCase);
        Vector visibleEquivalenceClasses = new Vector(0);
        if (stdCombination != null) {
            EquivalenceClass equivalenceClass;
            int numOfEquivalenceClasses = stdCombination.getEquivalenceClasses().size();
            for (int i = 0; i < numOfEquivalenceClasses; i++) {
                equivalenceClass = (EquivalenceClass)stdCombination.getEquivalenceClasses().get(i);
                if (!areEquivalenceClassesInTestCaseWithinTheSameElement(p_TestCase, equivalenceClass)) {
                    if (!areCombinationsInTestCaseWithinTheSameElement(p_TestCase, equivalenceClass)) {
                        visibleEquivalenceClasses.addElement(equivalenceClass);
                    }
                }
            }
            return visibleEquivalenceClasses;
        }
        else {
            return visibleEquivalenceClasses;
        }
    }

    public StdCombination getStdCombinationOfTestCaseAtTheEquivalenceClassInTestCase(EquivalenceClass
        p_EquivalenceClass, TestCase p_TestCase) {
            StdCombination stdCombination = this.getStdCombinationOfTestCase(p_TestCase);
            if (stdCombination != null) {
                if (stdCombination.getEquivalenceClasses().contains(p_EquivalenceClass)) {
                    return stdCombination;
                }
                else {
                    return null;
                }
            }
            else {
                return null;
            }
    }

    public StdCombination getStdCombinationOfTestCase(TestCase p_TestCase) {
        int numOfCombinations = p_TestCase.getLnkCombinations().size();
        Combination combination;
        for (int i = 0; i < numOfCombinations; i++) {
            combination = (Combination)p_TestCase.getLnkCombinations().elementAt(i);
            if (combination instanceof StdCombination) {
                return (StdCombination)combination;
            }
        }
        return null;
    }

    public boolean areEquivalenceClassesInTestCaseWithinTheSameElement(TestCase testCase, EquivalenceClass equivalenceClass) {
        Vector equivalenceClasses = testCase.getLnkEquivalenceClasses();
        int numOfEquivalenceClasses = testCase.getLnkEquivalenceClasses().size();
        Element element = equivalenceClass.getLnkElement();
        EquivalenceClass ec;
        for (int i = 0; i < numOfEquivalenceClasses; i++) {
            ec = (EquivalenceClass)testCase.getLnkEquivalenceClasses().elementAt(i);
            if (ec.getLnkElement() == element) {
                return true;
            }
        }
        return false;
    }

    public boolean areCombinationsInTestCaseWithinTheSameElement(TestCase testCase, EquivalenceClass equivalenceClass) {
        Element element = equivalenceClass.getLnkElement();
        int numOfCombinations = testCase.getLnkCombinations().size();
        Combination combination;
        for (int i = 0; i < numOfCombinations; i++) {
            combination = (Combination)testCase.getLnkCombinations().elementAt(i);
            if (!(combination instanceof StdCombination)) {
                if (isCombinationInElement(combination, element)) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean isCombinationInElement(Combination p_Combination, Element p_Element) {
        int numOfEquivalenceClasses = p_Combination.getEquivalenceClassesRecursiv().size();
        EquivalenceClass equivalenceClass = null;
        for (int i = 0; i < numOfEquivalenceClasses; i++) {
            equivalenceClass = (EquivalenceClass)p_Combination.getEquivalenceClassesRecursiv().get(i);
            if (equivalenceClass.getLnkElement() == p_Element) {
                return true;
            }
        }
        return false;
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public String generateTypeDataNameForTDStructure(Structure p_Structure) {
        StringBuffer contentBuffer = new StringBuffer();
        Element element;
        int numOfElements = p_Structure.getLnkElements().size();
        //  contentBuffer.append(BusinessRules.TESTCASE_PREFIX);
        contentBuffer.append(BusinessRules.TESTDATA_SEPARATOR);//svonborries_01092006
        for (int i = 0; i < numOfElements; i++) {
            element = (Element)p_Structure.getLnkElements().elementAt(i);
            if (element.getEquivalenceClasses().size() != 0) {
                if(element.getName().equals("")){
                	contentBuffer.append(BusinessRules.TESTDATA_EMPTY_AT_EQUIVALENCE_CLASS_IN_TEST_CASE+"NAME");
                }
                else{
                	contentBuffer.append(element.getName());
                }

                contentBuffer.append(BusinessRules.TESTDATA_SEPARATOR);//svonborries_01092006
            }
        }
        return contentBuffer.toString();
    }

    //my adds
    public String generateTypeDataObjectTypesForTDStructure(Structure p_Structure) {
        StringBuffer contentBuffer = new StringBuffer();
        Element element;
        int numOfElements = p_Structure.getLnkElements().size();
        contentBuffer.append(BusinessRules.TESTDATA_SEPARATOR);//svonborries_01092006
        for (int i = 0; i < numOfElements; i++) {
            element = (Element)p_Structure.getLnkElements().elementAt(i);
            if (element.getEquivalenceClasses().size() != 0) {
                contentBuffer.append(element.getGUIObjectName());
                contentBuffer.append(BusinessRules.TESTDATA_SEPARATOR);//svonborries_01092006
            }
        }
        return contentBuffer.toString();
    }

    public String generateTypeDataName(String names) {
        names = CMCharUtils.trimComaNames(names);
        String name = CMCharUtils.firstElement(names);
        if(name.equals(BusinessRules.TESTDATA_EMPTY_AT_EQUIVALENCE_CLASS_IN_TEST_CASE+"NAME")){
        	name="";
        }
        names = CMCharUtils.killFirstElement(names);
        return name;
    }

    //My add
    public String generateTypeDataObjectTypes(String names) {
        names = CMCharUtils.trimComa(names);
        String name = CMCharUtils.firstElement(names);
        names = CMCharUtils.killFirstElement(names);
        return name;
    }

    public int cantDecimal(float num) {
        String decimals = Float.toString(num);
        int cant = decimals.length() - 2;
        if (cant > 0)
            return cant;
        else
            return 0;
    }


    private String getSecondValue(String value) {
        String firstValue = CMNumberUtils.getFirstValue(value);
        String secondValue = value.substring(firstValue.length());
        return CMNumberUtils.getFirstValue(secondValue);
    }

    /**
     * @deprecated
     * @param value
     * @param p_TestObject
     * @return
     */
    public Vector calculateMathInterval(String value, TestObject p_TestObject) {
        Vector intervalValues = new Vector();
        String firstBracket;
        String secondBracket;
        String operLogic;
        String operLogic2;
        String number;
        String number2;
        value = value.trim();
        firstBracket = String.valueOf(value.charAt(0));
        secondBracket = String.valueOf(value.charAt(value.length() - 1));
        number = value.substring(1, value.indexOf(",")).trim();
        number2 = value.substring(value.indexOf(",") + 1, value.length() - 1).trim();
        Logger.getLogger(this.getClass()).info(firstBracket);
        Logger.getLogger(this.getClass()).info(secondBracket);
        Logger.getLogger(this.getClass()).info(number);
        Logger.getLogger(this.getClass()).info(number2);
        if (firstBracket.equals("["))
            operLogic = ">=";
        else
            operLogic = ">";
        if (secondBracket.equals("]"))
            operLogic2 = "<=";
        else
            operLogic2 = "<";
        String decimalSep = p_TestObject.getDecimalSeparator();
        StringReader reader = new StringReader(value);
        if (decimalSep.equals(BusinessRules.DEFAULT_DECIMAL_SEPARATOR)) {
            AmericanParser parse = new AmericanParser(reader);
            intervalValues.addElement(parse.calculateValueOfRange(operLogic, number));
            intervalValues.addElement(parse.calculateValueOfRange(operLogic2, number2));
        }
        else {
            EuropeanParser parse = new EuropeanParser(reader);
            intervalValues.addElement(parse.calculateValueOfRange(operLogic, number));
            intervalValues.addElement(parse.calculateValueOfRange(operLogic2, number2));
        }
        return intervalValues;
    }

    //hcanedo_21102004_begin
    public String getValue(String token) {
        int i = token.lastIndexOf(","); //indexOf(",");
        return token.substring(0, i);
    }
//My adds
  /*  public String getToolVendorOT(String token) {
        int i = token.lastIndexOf(","); //indexOf(",");
        return token.substring(i + 1);
    }
   //My adds end */
    public String getType(String token) {
        int i = token.lastIndexOf(","); //indexOf(",");
        return token.substring(i + 1); //I changed 1 to 2
    }

    //hcanedo_21102004_end
    public CombinationManager getM_CombinationManager() { return m_CombinationManager; }

    public void setM_CombinationManager(CombinationManager m_CombinationManager) {
        this.m_CombinationManager = m_CombinationManager;
    }

    public Vector getDataCombination() { return dataCombination; }

    public void setDataCombination(Vector dataCombination) { this.dataCombination = dataCombination; }

    //grueda15092004_begin
    //public void generateReportCSV(CMGridTDStructure gridTDStructure, CMFrameView p_Frame) {
    /**
     * @deprecated
     */
    public void generateReportCSV(CMGridTDStructure gridTDStructure, CMFrameView p_Frame, String p_AbsoluteTestObjectPath) {
        //grued15092004_end
        StringBuffer testDataFileName = new StringBuffer(gridTDStructure.getTDStructure().getM_Name());
        StringBuffer outputFileName = new StringBuffer(BusinessRules.TESTDATA_REPORT_ARCHIVE_NAME); //$NON-NLS-1$
        //grueda15092004_begin
        //String testObjectPath = gridTDStructure.getTDStructure().getM_TestObjectReference().getM_Path();
        //StringBuffer testDataPath = new StringBuffer(testObjectPath);
        StringBuffer testDataPath = new StringBuffer(p_AbsoluteTestObjectPath);
        //grueda15092004_end
        testDataPath.append(BusinessRules.URL_SEPARATOR); //$NON-NLS-1$
        testDataPath.append(gridTDStructure.getTDStructure().getM_TestObjectReference().getName()); //$NON-NLS-1$
        testDataPath.append(BusinessRules.URL_SEPARATOR); //$NON-NLS-1$
        testDataPath.append(BusinessRules.TEST_DATA_FOLDER);
        testDataPath.append(BusinessRules.URL_SEPARATOR); //$NON-NLS-1$
        testDataPath.append(BusinessRules.TESTDATA_REPORT_CARPET_NAME); //$NON-NLS-1$
        StringBuffer testDataFileNameAndExtension = new StringBuffer(outputFileName.toString());
        testDataFileNameAndExtension.append(BusinessRules.TESTDATA_REPORT_EXTENCION); //$NON-NLS-1$
        StringBuffer filePath = new StringBuffer(testDataPath.toString());
        filePath.append(BusinessRules.URL_SEPARATOR); //$NON-NLS-1$
        filePath.append(testDataFileNameAndExtension);
        File f = new File(testDataPath.toString());
        Logger.getLogger(this.getClass()).debug(f.mkdirs());
        try {
            JSX.ObjectWriter out = new JSX.ObjectWriter(new FileWriter(filePath.toString()));
            TestDataSet testdataset = (TestDataSet)gridTDStructure.getTDStructure().getM_TestDataSet().elementAt(CMIndexTDStructureUpdate.getInstance().getindexTestDataSet());
            out.writeObject(testdataset);
            out.close();
            StringBuffer outputPath = new StringBuffer(testDataPath.toString());
            outputPath.append(BusinessRules.URL_SEPARATOR);
            String nameoutput = generateNameTestDataReportCSV(testdataset);
            outputPath.append(nameoutput);
            File output = new File(outputPath.toString());
            if (output.isFile()) {
                Object[] options =
                    {CMMessages.getString("TESTDATA_MENSSAGE_BUTTON_NEW"),CMMessages.getString("TESTDATA_MENSSAGE_BUTTON_OPEN"),CMMessages.getString("TESTDATA_MENSSAGE_BUTTON_REWRITE")};
                int n = JOptionPane.showOptionDialog(p_Frame,
                    CMMessages.getString("TESTDATA_MENSSAGE_INIT_FILE") + nameoutput + CMMessages.getString("TESTDATA_MENSSAGE_SUFFIX_FILE"),
                    CMMessages.getString("TESTDATA_MENSSAGE_TITLE"),
                    JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[2]);
                if (n == JOptionPane.YES_OPTION) {
                    int i = 0;
                    while (output.exists()) {
                        i++;
                        String newName = generateNewName(outputPath.toString(), i);
                        output = new File(newName);
                    }
                    FileOutputStream fileOutPutStream = new FileOutputStream(output);
                    TransformerFactory tFactory = TransformerFactory.newInstance();
                    Transformer transformer = tFactory.newTransformer(
                        new StreamSource(BusinessRules.RESOURCE_FOLDER + "/" + BusinessRules.TESTDATA_REPORT_CSV_FILE));
                    transformer.transform(new StreamSource(filePath.toString()), new StreamResult(fileOutPutStream)); //new
                    fileOutPutStream.close();
                    StringBuffer filepathexcel = new StringBuffer();
                    filepathexcel.append('"');
                    filepathexcel.append(outputPath.toString());
                    filepathexcel.append('"');
                    viewFile2(BusinessRules.PATH_EXCEL_APPLICATION, filepathexcel.toString().replace('\\','/'), p_Frame);
                } else if (n == JOptionPane.NO_OPTION) {
                    StringBuffer filepathexcel = new StringBuffer();
                    filepathexcel.append('"');
                    filepathexcel.append(outputPath.toString());
                    filepathexcel.append('"');
                    viewFile2(BusinessRules.PATH_EXCEL_APPLICATION, filepathexcel.toString().replace('\\','/'), p_Frame);
                } else if (n == JOptionPane.CANCEL_OPTION) {
                    FileOutputStream fileOutPutStream = new FileOutputStream(output);
                    TransformerFactory tFactory = TransformerFactory.newInstance();
                    Transformer transformer = tFactory.newTransformer(
                        new StreamSource(BusinessRules.RESOURCE_FOLDER + "/" + BusinessRules.TESTDATA_REPORT_CSV_FILE));
                    transformer.transform(new StreamSource(filePath.toString()), new StreamResult(fileOutPutStream)); //new
                    fileOutPutStream.close();
                    StringBuffer filepathexcel = new StringBuffer();
                    filepathexcel.append('"');
                    filepathexcel.append(outputPath.toString());
                    filepathexcel.append('"');
                    viewFile2(BusinessRules.PATH_EXCEL_APPLICATION, filepathexcel.toString().replace('\\','/'), p_Frame);
                } else {
                    return;
                }
            }
            else {
                FileOutputStream fileOutPutStream = new FileOutputStream(output);
                TransformerFactory tFactory = TransformerFactory.newInstance();
                Transformer transformer = tFactory.newTransformer(
                    new StreamSource(BusinessRules.RESOURCE_FOLDER + "/" + BusinessRules.TESTDATA_REPORT_CSV_FILE));
                transformer.transform(new StreamSource(filePath.toString()), new StreamResult(fileOutPutStream)); //new FileOutputStream(output)));
                fileOutPutStream.close();
                StringBuffer filepathexcel = new StringBuffer();
                filepathexcel.append('"');
                filepathexcel.append(outputPath.toString());
                filepathexcel.append('"');
                viewFile2(BusinessRules.PATH_EXCEL_APPLICATION, filepathexcel.toString(), p_Frame); //outputPath.toString());
            }
        }
        catch (Exception ex) {
        	Logger.getLogger(this.getClass()).error(ex);
        }
    }

    public String generateNewName(String p_FileName, int p_index) {
        StringBuffer fileNameBuffer = new StringBuffer(p_FileName);
        int index = p_FileName.lastIndexOf("."); //$NON-NLS-1$
        StringBuffer sBuffer = new StringBuffer();
        sBuffer.append("_");
        sBuffer.append(p_index);
        fileNameBuffer.insert(index, sBuffer.toString());
        return fileNameBuffer.toString();
    }

    public String generateNameTestDataReportCSV(TestDataSet testdataset) {
        StringBuffer name = new StringBuffer(testdataset.getName());
        name.append(BusinessRules.FILE_REPORT_CSV_EXTENSION);
        return name.toString();
    }

    public void viewFile2(String p_Viewer, String p_FilePath, CMFrameView p_view) {
        StringBuffer completePath = new StringBuffer();
        completePath.append(p_Viewer);
        completePath.append(" "); //$NON-NLS-1$
        completePath.append(p_FilePath);
        Logger.getLogger(this.getClass()).debug(completePath.toString());
        try {
         Process theProcess = Runtime.getRuntime().exec(completePath.toString());
        }
        catch (IOException exception) {
     	   /*File app= new File(p_Viewer);
           JOptionPane.showMessageDialog(p_view,app.getName()+" "+CMMessages.getString("LABEL_APPLICATION_NOT_FOUND"),
             CMMessages.getString("TESTDATASETREPORTS_TITLE_INFORMATION"), JOptionPane.INFORMATION_MESSAGE);
             exception.printStackTrace();*///ccastedo 08.02.07
        	CMApplication.frame.runDefaultBrowser(p_FilePath);
        }




    }
    private void generateReportOverTestDataOriginalFileWithExternalXSLT(String p_filepath,TestDataSet p_TestDataSet, File p_OutPutFile, String p_TestObjectAbsolutePath, CMFrameView p_Frame){
		try {
			FileOutputStream fileOutPutStream = new FileOutputStream(p_OutPutFile);

        TransformerFactory tFactory = TransformerFactory.newInstance();
        Session2 auxSession2 = p_Frame.getTreeWorkspaceView().getM_Session2();
      //ccastedo 07.11.06   ExternalXSLTReportFormat auxXSLT = auxSession2.getM_ApplicationSetting().getM_ExternalXSLTReportFormat();

        String nameXSLT = p_TestDataSet.getM_ReportFormat();
       //ccastedo 07.11.06  int indexXSLT = auxXSLT.getM_Name().indexOf(nameXSLT);
        int indexXSLT = getIndexFromExternalXSLTReportFormat(nameXSLT,auxSession2);
        TestCaseExternalReports testdataSetReport = (TestCaseExternalReports)auxSession2.getM_ApplicationSetting().getTestCaseReports(4).elementAt(indexXSLT);
        //ccastedo 07.11.06  String pathReportFormat = auxXSLT.getM_FilePath().elementAt(indexXSLT).toString();

        String pathReportFormat = testdataSetReport.getFilePath();

        Transformer transformer = tFactory.newTransformer(new StreamSource(pathReportFormat)); //(CMFrameView.class.getResource(BusinessRules.TESTDATA_REPORT_CSV_FILE)).toString()));
       /* StringBuffer absolutePath= new StringBuffer(p_TestObjectAbsolutePath);
        TestObject p_TestObject= p_Frame.getTreeWorkspaceView().getCurrentTestObject();
        absolutePath.append(BusinessRules.URL_SEPARATOR);
        absolutePath.append(p_TestObject.getName());
        absolutePath.append(BusinessRules.URL_SEPARATOR);
        String path = ((TDStructureReference)p_TestObject.getTDSTructureReference().firstElement()).getM_Path();
	    absolutePath.append(path);
	    absolutePath.append(BusinessRules.URL_SEPARATOR);
	    absolutePath.append(BusinessRules.TESTDATA_NAME_OBJECT_TESTDATA);
	    absolutePath.append(BusinessRules.FILE_TESTDATA_EXTENSION);
        String pathTestDataFile=absolutePath.toString();*///ccastedo 09.10.06
        transformer.setParameter("filePath",p_OutPutFile);
        transformer.transform(new StreamSource(p_filepath/*pathTestDataFile*/), new StreamResult(fileOutPutStream)); //new
        deleteBlanksOutput(p_OutPutFile);//ccastedo 09.10.06
        fileOutPutStream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
    }

    //  ccastedo begins 07.11.06
 	private int getIndexFromExternalXSLTReportFormat(String searchName, Session2 p_session){
 		int index = 0;
 		Vector testDataSetReports= p_session.getM_ApplicationSetting().getTestCaseReports(4);
 		for(int i =0; i < testDataSetReports.size();i++){
			TestCaseExternalReports report= (TestCaseExternalReports)testDataSetReports.elementAt(i);
			if(report.getName().equalsIgnoreCase(searchName)){
				index = i;
				break;
			}
		}
 		return index;
 	}
 	//ccastedo ends 07.11.06

    //grueda15092004_begin
    public void generateReportCSV2(CMGridTDStructure gridTDStructure, CMFrameView p_Frame, String p_AbsoluteTestObjectPath) {
        //public void generateReportCSV2(CMGridTDStructure gridTDStructure, CMFrameView p_Frame) {
        //grueda15092004_end
        StringBuffer testDataFileName = new StringBuffer(gridTDStructure.getTDStructure().getM_Name());
        StringBuffer outputFileName = new StringBuffer(BusinessRules.TESTDATA_REPORT_ARCHIVE_NAME); //$NON-NLS-1$
        //grueda15092004_begin
        //String testObjectPath = gridTDStructure.getTDStructure().getM_TestObjectReference().getM_Path();
        //StringBuffer testDataPath = new StringBuffer(testObjectPath);
        StringBuffer testDataPath = new StringBuffer(p_AbsoluteTestObjectPath);
        //grueda15092004_end
        testDataPath.append(BusinessRules.URL_SEPARATOR); //$NON-NLS-1$
        testDataPath.append(gridTDStructure.getTDStructure().getM_TestObjectReference().getName()); //$NON-NLS-1$
        testDataPath.append(BusinessRules.URL_SEPARATOR); //$NON-NLS-1$
        testDataPath.append(BusinessRules.TEST_DATA_FOLDER);
        testDataPath.append(BusinessRules.URL_SEPARATOR); //$NON-NLS-1$
        testDataPath.append(BusinessRules.TESTDATA_REPORT_CARPET_NAME); //$NON-NLS-1$
        StringBuffer testDataFileNameAndExtension = new StringBuffer(outputFileName.toString());
        testDataFileNameAndExtension.append(BusinessRules.TESTDATA_REPORT_EXTENCION); //$NON-NLS-1$
        StringBuffer filePath = new StringBuffer(testDataPath.toString());
        filePath.append(BusinessRules.URL_SEPARATOR); //$NON-NLS-1$
        filePath.append(testDataFileNameAndExtension);
        File f = new File(testDataPath.toString());
        f.mkdirs();
        try {
            //          JSX.ObjectWriter out = new JSX.ObjectWriter(new FileWriter(filePath.toString()));
            TestDataSet testdataset = (TestDataSet)gridTDStructure.getTDStructure().getM_TestDataSet().elementAt(CMIndexTDStructureUpdate.getInstance().getindexTestDataSet());

            //           out.writeObject(testdataset);
            //            out.close();
            StringBuffer outputPath = new StringBuffer(testDataPath.toString());
            outputPath.append(BusinessRules.URL_SEPARATOR); //modificar para la creacion de archivo por estructura
            outputPath.append(testdataset.getName());
            File f1 = new File(outputPath.toString());
            f1.mkdirs();
            // hacer el for para la creacion de las ditintas estructuras
            Vector nameStructure = new Vector();
            Vector indexTestData = new Vector();
            Vector indexStructure = new Vector();
            estractStructureForTestDataSet(testdataset, nameStructure, indexTestData, indexStructure);
            for (int j = 0; j < nameStructure.size(); j++) {
                //hcanedo_20_09_2004_begin
                String content = createXMLFileForCSV(testdataset, indexTestData.elementAt(j).hashCode(),
                    indexStructure.elementAt(j).hashCode(),gridTDStructure.getTDStructure());
                FileWriter out = new FileWriter(filePath.toString());
                out.write(content);
                out.close();
                //hcanedo_20_09_2004_end
                String nameoutput = generateNameTestDataReportCSV2(nameStructure.elementAt(j), gridTDStructure, p_Frame);
                StringBuffer outputPath2 = new StringBuffer(outputPath.toString());
                outputPath2.append(BusinessRules.URL_SEPARATOR);
                outputPath2.append(nameoutput);
                File output = new File(outputPath2.toString());
                if (output.isFile()) {
                    Object[] options =
                        {CMMessages.getString("TESTDATA_MENSSAGE_BUTTON_NEW"),/*CMMessages.getString("TESTDATA_MENSSAGE_BUTTON_OPEN"),*/CMMessages.getString("TESTDATA_MENSSAGE_BUTTON_REWRITE")};
                    int n = JOptionPane.showOptionDialog(p_Frame,
                        CMMessages.getString("TESTDATA_MENSSAGE_INIT_FILE") + nameoutput + CMMessages.getString("TESTDATA_MENSSAGE_SUFFIX_FILE"),
                        CMMessages.getString("TESTDATA_MENSSAGE_TITLE"),
                        JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[1]);
                    if (n == JOptionPane.YES_OPTION) {
                        int i = 0;
                        while (output.exists()) {
                            i++;
                            String newName = generateNewName(outputPath2.toString(), i);
                            output = new File(newName);
                        }
                        if(!testdataset.getM_ReportFormat().equals(BusinessRules.REPORT_XSLT_DEFAULT)){
                        	p_Frame.getTreeWorkspaceView().save2();
                        	generateReportOverTestDataOriginalFileWithExternalXSLT(filePath.toString(),testdataset,output,p_AbsoluteTestObjectPath,p_Frame);
                        }
                        else{
                        FileOutputStream fileOutPutStream = new FileOutputStream(output);
                        TransformerFactory tFactory = TransformerFactory.newInstance();
                        Session2 auxSession2 = p_Frame.getTreeWorkspaceView().getM_Session2();
                        //ccastedo 07.11.06  ExternalXSLTReportFormat auxXSLT = auxSession2.getM_ApplicationSetting().getM_ExternalXSLTReportFormat();
                        String nameXSLT = testdataset.getM_ReportFormat();
                        int indexXSLT = getIndexFromExternalXSLTReportFormat(nameXSLT,auxSession2);
                        TestCaseExternalReports auxXSLT = (TestCaseExternalReports)auxSession2.getM_ApplicationSetting().getTestCaseReports(4).elementAt(indexXSLT);
                        String pathReportFormat = auxXSLT.getFilePath();
                        Transformer transformer = tFactory.newTransformer(new StreamSource(pathReportFormat)); //(CMFrameView.class.getResource(BusinessRules.TESTDATA_REPORT_CSV_FILE)).toString()));

 /*                       try{
                        	transformer.setParameter("paramStructure", nameStructure.elementAt(j)); // new 30.04.04
                        	transformer.setParameter("paramTestData", indexTestData.elementAt(j)); // new 30.04.04
                        	transformer.setParameter("paramTable", indexStructure.elementAt(j)); // new 30.04.04
                        }
                        catch(Exception ex)
                        {
                        }*/


                        transformer.transform(new StreamSource(filePath.toString()), new StreamResult(fileOutPutStream)); //new
                        // FileOutputStream(output)));
                        fileOutPutStream.close();
                        deleteBlanksOutput(output);
                        }
                    } else if (n == JOptionPane.NO_OPTION /*JOptionPane.CANCEL_OPTION*/) {
                    	if(!testdataset.getM_ReportFormat().equals(BusinessRules.REPORT_XSLT_DEFAULT)){
                    		p_Frame.getTreeWorkspaceView().save2();
                        	generateReportOverTestDataOriginalFileWithExternalXSLT(filePath.toString(),testdataset,output,p_AbsoluteTestObjectPath,p_Frame);
                        }
                        else{
                        FileOutputStream fileOutPutStream = new FileOutputStream(output);
                        TransformerFactory tFactory = TransformerFactory.newInstance();
                        Session2 auxSession2 = p_Frame.getTreeWorkspaceView().getM_Session2();
                        //ccastedo 07.11.06  ExternalXSLTReportFormat auxXSLT = auxSession2.getM_ApplicationSetting().getM_ExternalXSLTReportFormat();
                        String nameXSLT = testdataset.getM_ReportFormat();
                        int indexXSLT = getIndexFromExternalXSLTReportFormat(nameXSLT,auxSession2);
                        TestCaseExternalReports auxXSLT = (TestCaseExternalReports)auxSession2.getM_ApplicationSetting().getTestCaseReports(4).elementAt(indexXSLT);
                        //String pathReportFormat = auxXSLT.getM_FilePath().elementAt(indexXSLT).toString();
                        String pathReportFormat = auxXSLT.getFilePath();
                        Transformer transformer = tFactory.newTransformer(new StreamSource(pathReportFormat)); //(CMFrameView.class.getResource(BusinessRules.TESTDATA_REPORT_CSV_FILE)).toString()));

   /*                     try{
                        	transformer.setParameter("paramStructure", nameStructure.elementAt(j)); // new 30.04.04
                        	transformer.setParameter("paramTestData", indexTestData.elementAt(j)); // new 30.04.04
                        	transformer.setParameter("paramTable", indexStructure.elementAt(j)); // new 30.04.04
                        }
                        catch(Exception ex)
                        {
                        }*/

                        transformer.transform(new StreamSource(filePath.toString()), new StreamResult(fileOutPutStream)); //new
                        // FileOutputStream(output)));
                        fileOutPutStream.close();
                        deleteBlanksOutput(output);
                        }
                    } else {
                        return;
                    }
                }
                else {
                	if(!testdataset.getM_ReportFormat().equals(BusinessRules.REPORT_XSLT_DEFAULT)){
                		p_Frame.getTreeWorkspaceView().save2();
                		generateReportOverTestDataOriginalFileWithExternalXSLT(filePath.toString(),testdataset,output,p_AbsoluteTestObjectPath,p_Frame);
                    }
                    else{
                    FileOutputStream fileOutPutStream = new FileOutputStream(output);
                    TransformerFactory tFactory = TransformerFactory.newInstance();
                    Session2 auxSession2 = p_Frame.getTreeWorkspaceView().getM_Session2();
                    //ccastedo 07.11.06  ExternalXSLTReportFormat auxXSLT = auxSession2.getM_ApplicationSetting().getM_ExternalXSLTReportFormat();
                    String nameXSLT = testdataset.getM_ReportFormat();
                    int indexXSLT = getIndexFromExternalXSLTReportFormat(nameXSLT,auxSession2);
                    TestCaseExternalReports auxXSLT = (TestCaseExternalReports)auxSession2.getM_ApplicationSetting().getTestCaseReports(4).elementAt(indexXSLT);
                    String pathReportFormat = auxXSLT.getFilePath();
                    Transformer transformer = tFactory.newTransformer(new StreamSource(pathReportFormat)); //(CMFrameView.class.getResource(BusinessRules.TESTDATA_REPORT_CSV_FILE)).toString()));

  /*                      try{
                        	transformer.setParameter("paramStructure", nameStructure.elementAt(j)); // new 30.04.04
                        	transformer.setParameter("paramTestData", indexTestData.elementAt(j)); // new 30.04.04
                        	transformer.setParameter("paramTable", indexStructure.elementAt(j)); // new 30.04.04
                        }
                        catch(Exception ex)
                        {
                        }*/

                    transformer.transform(new StreamSource(filePath.toString()), new StreamResult(fileOutPutStream)); //new
                    // FileOutputStream(output)));
                    fileOutPutStream.close();
                    deleteBlanksOutput(output);
                    }
                }
            }
        }
        catch (Exception ex) {
        	Logger.getLogger(this.getClass()).error(ex);
        }
    }

    public void estractStructureForTestDataSet(TestDataSet testdataset, Vector nameStructure, Vector indexTestData,
        Vector indexStructure) {
            Vector p_testDatas = testdataset.getM_TestDataCombinations().getM_TestDatas();
            for (int i = 0; i < p_testDatas.size(); i++) {
                TestData p_testdata = (TestData)p_testDatas.elementAt(i);
                Vector p_StructureTestData = p_testdata.getM_TDStructure().getM_StructureTestData();
                for (int j = 0; j < p_StructureTestData.size(); j++) {
                    StructureTestData p_Structure = (StructureTestData)p_StructureTestData.elementAt(j);
                    String name = p_Structure.getName();
                    if (!nameStructure.contains(name)) {
                        nameStructure.addElement(name);
                        indexTestData.addElement(new Integer(i));
                        indexStructure.addElement(new Integer(j));
                    }
                }
            }
    }

    public String generateNameTestDataReportCSV2(Object element, CMGridTDStructure gridTDStructure, CMFrameView p_Frame) {
        StringBuffer name = new StringBuffer(element.toString());
        TestDataSet auxtds = (TestDataSet)gridTDStructure.getTDStructure().getM_TestDataSet().elementAt(CMIndexTDStructureUpdate.getInstance().getindexTestDataSet());
        Session2 auxsession2 = p_Frame.getTreeWorkspaceView().getM_Session2();
       //ccastedo 07.11.06  ExternalXSLTReportFormat auxxslt = auxsession2.getM_ApplicationSetting().getM_ExternalXSLTReportFormat();
        String namexslt = auxtds.getM_ReportFormat();
        int indexxslt = getIndexFromExternalXSLTReportFormat(namexslt,auxsession2);
        TestCaseExternalReports auxxslt =(TestCaseExternalReports)auxsession2.getM_ApplicationSetting().getTestCaseReports(4).elementAt(indexxslt);
        String extReportFormat = auxxslt.getExtension();
        if (extReportFormat.trim() != "") {
            name.append(".");
            name.append(extReportFormat);
        }
        // name.append(BusinessRules.FILE_REPORT_CSV_EXTENSION);
        return name.toString();
    }
    //////////////////////////////////////////////////////////////////////////////////////////////////////

    /**  */

/*nuevo codigo para eliminar las Ids del archivo 	20.05.04							  */

    /**
     * @throws CMInvalidIdException   */
    public void chargeVectorIds(TDStructure p_TDStructure)  {
        idStructureVector.deleteIds();
        idTestDataSetVector.deleteIds();
        idTestDataVector.deleteIds();
        for (int i = 0; i < p_TDStructure.getM_StructureTestData().size(); i++) {
            StructureTestData std = (StructureTestData)p_TDStructure.getM_StructureTestData().elementAt(i);
            if (std.getId() > 0)
                idStructureVector.registerId(std.getId());
            else {
                int id_std = getIdStructureName(std);
                std.setId(id_std);
                idStructureVector.registerId(id_std);
            }
        }
        for (int i = 0; i < p_TDStructure.getM_TestDataSet().size(); i++) {
            TestDataSet tds = (TestDataSet)p_TDStructure.getM_TestDataSet().elementAt(i);
            if (tds.getId() > 0)
                idTestDataSetVector.registerId(tds.getId());
            else {
                int id_tds = getIdTestDataSetName(tds);
                tds.setId(id_tds);
                idTestDataSetVector.registerId(id_tds);
            }
        }
        for (int i = 0; i < p_TDStructure.getTestDataCombination().getM_TestDatas().size(); i++) {
            TestData td = (TestData)p_TDStructure.getTestDataCombination().getM_TestDatas().elementAt(i);
            if (td.getId() > 0)
                idTestDataVector.registerId(td.getId());
            else {
                int id_td = getIdTestDataName(td);
                td.setId(id_td);
                idTestDataVector.registerId(id_td);
            }
        }
    }

    private int getIdTestDataName(TestData p_td) {
        String td_name = p_td.getName();
        return Integer.parseInt(td_name.substring(td_name.indexOf(BusinessRules.TESTDATA_PREFIX) + 2));
    }

    private int getIdTestDataSetName(TestDataSet p_tds) {
        String tds_name = p_tds.getName();
        return Integer.parseInt(tds_name.substring(tds_name.indexOf(BusinessRules.TESTDATASET_PREFIX) + 3));
    }

    private int getIdStructureName(StructureTestData p_std) {
        String std_name = p_std.getName();
        return Integer.parseInt(std_name.substring(std_name.indexOf(BusinessRules.STRUCTURE_PREFIX) + 1));
    }

    public IdSet idStructureVector;
    public IdSet idTestDataSetVector;
    public IdSet idTestDataVector;

    /**  */
    private void deleteBlanksOutput(File p_output) {
        try {
            BufferedReader input = new BufferedReader(new InputStreamReader(new FileInputStream(p_output)));
            StringBuffer content = new StringBuffer();
            String line = new String();
            while (line != null) {
                line = input.readLine();
                if (line != null) {
                    line = line.trim();
                    content.append(line);
                    content.append(System.getProperty("line.separator"));
                }
            }
            input.close();
            String contentTrim = new String(content);
            contentTrim = contentTrim.trim();
            saveFile(contentTrim, p_output);
        }
        catch (Exception ex) {
        	Logger.getLogger(this.getClass()).error("error en eliminar blanks");
        }
    }

    private void saveFile(String p_Content, File p_File) {
        try {
            FileWriter out = new FileWriter(p_File);
            out.write(p_Content);
            out.close();
        }
        catch (IOException e) {
        	Logger.getLogger(this.getClass()).error("fijarse el trim");
        }
    }

    //grueda15092004_begin
    //public void generateReportHTMLResultComparison(CMResultsComparationPanelView p_ResultComparation, CMFrameView p_Frame) {
    public void generateReportHTMLResultComparison(CMResultsComparationPanelView p_ResultComparation, CMFrameView p_Frame,
        String p_AbsoluteTestObjectPath) {
            //grueda15092004_end
            StringBuffer ResultComparationFileName = new StringBuffer("resultComparison.htm");
            StringBuffer outputFileName = new StringBuffer(BusinessRules.TESTDATA_REPORT_ARCHIVE_NAME); //$NON-NLS-1$
            //grueda15092004_begin
            //String testObjectPath = p_ResultComparation.getM_TDStructure().getM_TestObjectReference().getM_Path();
            //StringBuffer ResultComparationPath = new StringBuffer(testObjectPath);
            StringBuffer resultComparationPath = new StringBuffer(p_AbsoluteTestObjectPath);
            //grueda15092004_end
            resultComparationPath.append(BusinessRules.URL_SEPARATOR); //$NON-NLS-1$
            resultComparationPath.append(p_ResultComparation.getM_TDStructure().getM_TestObjectReference().getName()); //$NON-NLS-1$
            resultComparationPath.append(BusinessRules.URL_SEPARATOR); //$NON-NLS-1$
            resultComparationPath.append(BusinessRules.RESULT_COMPARATION_FOLDER);
            resultComparationPath.append(BusinessRules.URL_SEPARATOR); //$NON-NLS-1$
            StringBuffer RCFileNameAndExtension = new StringBuffer(outputFileName.toString());
            RCFileNameAndExtension.append(BusinessRules.TESTDATA_REPORT_EXTENCION); //$NON-NLS-1$
            StringBuffer filePath = new StringBuffer(resultComparationPath.toString().replace('\\','/'));
            filePath.append(BusinessRules.URL_SEPARATOR); //$NON-NLS-1$
            filePath.append(RCFileNameAndExtension);
            File f = new File(resultComparationPath.toString());
            f.mkdirs();

            SessionManager.transferImageFromJar(CMIcon.CASEMAKER_LOGO.getFilename(),
                resultComparationPath.toString() + "/" + BusinessRules.REPORT_IMAGEFLD + "/" +CMIcon.CASEMAKER_LOGO.getFilename());

            try {
                File fxml = new File(filePath.toString());
                PrintWriter output = new PrintWriter(new FileWriter(fxml));
                output.write(p_ResultComparation.ReportResultComparationXML());
                output.close();
                StringBuffer outputPath = new StringBuffer(resultComparationPath.toString());
                outputPath.append(BusinessRules.URL_SEPARATOR); //modificar para la creacion de archivo por estructura
                outputPath.append(ResultComparationFileName.toString().replace('\\','/'));
                File f1 = new File(outputPath.toString());
                // boolean b=f1.mkdirs();
                // hacer el for para la creacion de las ditintas estructuras
                int i = 0;
                if (f1.isFile())
                    while (f1.exists()) {
                        i++;
                        String newName = generateNewName(outputPath.toString(), i);
                        f1 = new File(newName);
                    }
                FileOutputStream fileOutPutStream = new FileOutputStream(f1);
                TransformerFactory tFactory = TransformerFactory.newInstance();
                Transformer transformer = tFactory.newTransformer(
                    new StreamSource(BusinessRules.RESOURCE_FOLDER + "/" +
                    BusinessRules.TESTDATA_REPORT_RESULT_COMPARISON_FILE));
                transformer.transform(new StreamSource(filePath.toString()), new StreamResult(fileOutPutStream)); //new
                // FileOutputStream(output)));
                fileOutPutStream.close();
                //grueda_15072004_begin
                Session2 session = p_Frame.getTreeWorkspaceView().getM_Session2();
                String appPath = p_Frame.getCmApplication().getSessionManager().getExternalApplicationFilePath(BusinessRules.INTERNET_APPLICATION_DENOMINATIVE,
                    session);
                viewFile2(appPath, "\"file:"+f1.toString().replace('\\','/')+"\"", p_Frame);
                //grueda_15072004_end
            }
            catch (Exception ex) {
            	Logger.getLogger(this.getClass()).error(ex);
            }
    }

    //hcanedo_20_09_2004_begin
    //hcanedo_20102004_begin
    public String createXMLFileForCSV(TestDataSet testdataset, int p_indexTD, int p_IndexStructure, TDStructure p_structure) {//svonborries_12012006
        StringBuffer contentBuffer = new StringBuffer();
        int numOfTestDatas = testdataset.getM_TestDataCombinations().getM_TestDatas().size();
        contentBuffer.append("<?xml version=" + '"' + "1.0" + '"' + " encoding=" + '"' + "iso-8859-1" + '"' + " ?>"); //$NON-NLS-1$
        //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
        contentBuffer.append(System.getProperty("line.separator")); //$NON-NLS-1$
        if (numOfTestDatas == 0) {
            contentBuffer.append("<reportCSV/> ");
        }
        else {
            contentBuffer.append("<reportCSV> ");
            contentBuffer.append(System.getProperty("line.separator"));
            contentBuffer.append("<lineReport   ");
            contentBuffer.append("nameTestDataSet=  ");
            contentBuffer.append('"');
            contentBuffer.append(testdataset.getName());
            contentBuffer.append('"');
            contentBuffer.append("  ");
            contentBuffer.append("nameTestCase=  ");
            contentBuffer.append('"');
            contentBuffer.append("TC");
            contentBuffer.append('"');
            contentBuffer.append("  ");
            contentBuffer.append(">");
            TestData testData = (TestData)testdataset.getM_TestDataCombinations().getM_TestDatas().elementAt(p_indexTD);
            StructureTestData stuc = (StructureTestData)testData.getM_TDStructure().getM_StructureTestData().elementAt(p_IndexStructure);
            for (int i = 0; i < stuc.getTypeData().size(); i++) {
                contentBuffer.append("<lineReport2 nameElement=  ");
                contentBuffer.append('"');
                contentBuffer.append(changeReservedCharacterInXML(((ITypeData)stuc.getTypeData().elementAt(i)).getName()));
                contentBuffer.append('"');
                contentBuffer.append("  ");
                contentBuffer.append("/>");
            }
            contentBuffer.append("</lineReport>");
            contentBuffer.append(System.getProperty("line.separator"));
            String nameStrucutre = stuc.getName();
            for (int i = 0; i < testdataset.getM_TestDataCombinations().getM_TestDatas().size(); i++) {
                TestData testData2 = (TestData)testdataset.getM_TestDataCombinations().getM_TestDatas().elementAt(i);
                for (int j = 0; j < testData2.getM_TDStructure().getM_StructureTestData().size(); j++) {
                    StructureTestData stuc2 = (StructureTestData)testData2.getM_TDStructure().getM_StructureTestData().elementAt(j);
                    if (nameStrucutre.equals(stuc2.getName())) {
                        contentBuffer.append("<lineReport   ");
                        contentBuffer.append("nameTestDataSet=  ");
                        contentBuffer.append('"');
                        contentBuffer.append(testData2.getName());
                        contentBuffer.append(".");
                        contentBuffer.append(stuc2.getName());
                        contentBuffer.append('"');
                        contentBuffer.append("  ");
                        contentBuffer.append("nameTestCase=  ");
                        contentBuffer.append('"');
                        contentBuffer.append(testData2.getM_TestCaseinTestData());
                        contentBuffer.append('"');
                        contentBuffer.append("  >");
                        for (int k = 0; k < stuc2.getTypeData().size(); k++) {
                            contentBuffer.append("<lineReport2 nameElement=  ");
                            contentBuffer.append('"');
                            String preffix = changeReservedCharacterInXML(((ITypeData)stuc2.getTypeData().elementAt(k)).getPrefix());
                            String value = changeReservedCharacterInXML(((ITypeData)stuc2.getTypeData().elementAt(k)).getFormattedValue());
                            //String value = changeReservedCharacterInXML(((ITypeData)stuc2.getTypeData().elementAt(k)).getStringValue());
                            String suffix = changeReservedCharacterInXML(((ITypeData)stuc2.getTypeData().elementAt(k)).getSuffix());
                            //svonborries_12012006_begin
                            String formulaPreffix = VariablesManager.returnImplicitExplicitVariable(preffix,p_structure);
                            if (!formulaPreffix.equalsIgnoreCase("")){
                            	preffix = formulaPreffix;
                            }
                            //String value = changeReservedCharacterInXML(((TypeData)stuc2.getTypeData().elementAt(k)).getValue());
                            //String suffix = changeReservedCharacterInXML(((TypeData)stuc2.getTypeData().elementAt(k)).getSuffix());
                            String formulaSuffix = VariablesManager.returnImplicitExplicitVariable(suffix,p_structure);
                            if(!formulaSuffix.equalsIgnoreCase("")){
                            	suffix = formulaSuffix;
                            }
                            //svonborries_12012006_end

                            contentBuffer.append(preffix + value + suffix);
                            contentBuffer.append('"');
                            contentBuffer.append("  />");
                        }
                        contentBuffer.append("</lineReport>");
                        contentBuffer.append(System.getProperty("line.separator"));
                    }
                }
            }
            contentBuffer.append("</reportCSV> ");
        }
        return contentBuffer.toString();
    }

    //hcanedo_2010_2004_end
    public String changeReservedCharacterInXML(String p_changeString) {
        String amp = "&amp;";
        String lt = "&lt;";
        String gt = "&gt;";
        String quot = "&quot;";
        String apos = "&apos;";
        String oldamp = "&";
        String oldlt = "<";
        String oldgt = ">";
        String oldquot = "\"";
        String oldapos = "'";
        String result = p_changeString.replaceAll(oldamp, amp);
        result = result.replaceAll(oldlt, lt);
        result = result.replaceAll(oldgt, gt);
        result = result.replaceAll(oldquot, quot);
        result = result.replaceAll(oldapos, apos);
        return result;
    }

    public void createReportFormatNotApply(String p_Value, CMGridTDStructure gridTDStructure, CMFrameView p_Frame, String p_AbsoluteTestObjectPath){
        StringBuffer formatsFileName = new StringBuffer("formats.htm");
        StringBuffer outputFileName = new StringBuffer(BusinessRules.TESTDATA_REPORT_ARCHIVE_NAME); //$NON-NLS-1$
        StringBuffer formatsPath = new StringBuffer(p_AbsoluteTestObjectPath);
        formatsPath.append(BusinessRules.URL_SEPARATOR); //$NON-NLS-1$
        formatsPath.append(gridTDStructure.getTDStructure().getM_TestObjectReference().getName()); //$NON-NLS-1$
        formatsPath.append(BusinessRules.URL_SEPARATOR); //$NON-NLS-1$
        formatsPath.append(BusinessRules.TEST_DATA_FOLDER);
        formatsPath.append(BusinessRules.URL_SEPARATOR);
        formatsPath.append(BusinessRules.TESTDATA_REPORT_CARPET_NAME);
        formatsPath.append(BusinessRules.URL_SEPARATOR); //$NON-NLS-1$
        StringBuffer RCFileNameAndExtension = new StringBuffer(outputFileName.toString());
        RCFileNameAndExtension.append(BusinessRules.TESTDATA_REPORT_EXTENCION); //$NON-NLS-1$
        StringBuffer filePath = new StringBuffer(formatsPath.toString().replace('\\','/'));
        filePath.append(BusinessRules.URL_SEPARATOR); //$NON-NLS-1$
        filePath.append(RCFileNameAndExtension);
        File f = new File(formatsPath.toString());
        f.mkdirs();

        SessionManager.transferImageFromJar(CMIcon.CASEMAKER_LOGO.getFilename(),
            formatsPath.toString() + "/" + BusinessRules.REPORT_IMAGEFLD + "/" + CMIcon.CASEMAKER_LOGO.getFilename());

        try {
            File fxml = new File(filePath.toString());
            PrintWriter output = new PrintWriter(new FileWriter(fxml));
            output.write(p_Value);
            output.close();
            StringBuffer outputPath = new StringBuffer(formatsPath.toString());
            outputPath.append(BusinessRules.URL_SEPARATOR); //modificar para la creacion de archivo por estructura
            outputPath.append(formatsFileName.toString().replace('\\','/'));
            File f1 = new File(outputPath.toString());
            int i = 0;
            if (f1.isFile())
                while (f1.exists()) {
                    i++;
                    String newName = generateNewName(outputPath.toString(), i);
                    f1 = new File(newName);
                }
            FileOutputStream fileOutPutStream = new FileOutputStream(f1);
            TransformerFactory tFactory = TransformerFactory.newInstance();
            Transformer transformer = tFactory.newTransformer(
                new StreamSource(BusinessRules.RESOURCE_FOLDER + "/" +
                BusinessRules.TESTDATA_REPORT_FORMATS_FILE));
            transformer.transform(new StreamSource(filePath.toString()), new StreamResult(fileOutPutStream)); //new
            fileOutPutStream.close();
            Session2 session = p_Frame.getTreeWorkspaceView().getM_Session2();
            String appPath = p_Frame.getCmApplication().getSessionManager().getExternalApplicationFilePath(BusinessRules.INTERNET_APPLICATION_DENOMINATIVE,
                session);
            viewFile2(appPath, "\"file:"+f1.toString().replace('\\','/')+"\"", p_Frame);
        }
        catch (Exception ex) {
        	Logger.getLogger(this.getClass()).error(ex);
        }

    }
    //hcanedo_20_09_2004_end
    private CombinationManager m_CombinationManager;
    private Vector dataCombination;

	@SuppressWarnings("unchecked")
	public UndoableEdit setTestDataIdentifierToStructure(StructureTestData p_std, TestData p_Testdata) {
		CMCompoundEdit ce = new CMCompoundEdit();
		Vector testdataStructure= p_Testdata.getM_TDStructure().getM_StructureTestData();
		Vector keys= new Vector(testdataStructure.size());
		for(int i=0; i<testdataStructure.size()+1;i++){
			keys.addElement(new Boolean(false));
		}
		keys.setElementAt(new Boolean(true),0);
		for (Iterator iter = testdataStructure.iterator(); iter.hasNext();) {
			StructureTestData structure = (StructureTestData) iter.next();
			if(structure.getName().equals(p_std.getName())){
				int idTestData= structure.getIdTestData();
				if(idTestData < keys.size())
					keys.setElementAt(new Boolean(true),idTestData);
				if(idTestData==0){
					ce.addEdit(CMModelEditFactory.INSTANCE.createChangeIdTestDataInTDStructureModelEdit(structure,1));
					structure.setIdTestData(1);
					if(keys.size()==1)
						keys.addElement(new Boolean(true));
					else
						keys.setElementAt(new Boolean(true),1);
				}
			}
		}
		for(int i=0; i < keys.size();i++){
			if(!((Boolean)keys.elementAt(i)).booleanValue()){
				ce.addEdit(CMModelEditFactory.INSTANCE.createChangeIdTestDataInTDStructureModelEdit(p_std,i));
				p_std.setIdTestData(i);
				return new CMCompoundEdit();
			}
		}
		ce.addEdit(CMModelEditFactory.INSTANCE.createChangeIdTestDataInTDStructureModelEdit(p_std,keys.size()));
		p_std.setIdTestData(keys.size());
		return ce;
	}

	public static void updateTypeDataReferences(HashMap subjects, ITypeData data) {
		try{
		CMFrameView frame= CMApplication.frame;
		frame.setWaitCursor(true);

/*		CMGridTDStructure gridTDStructure= frame.getGridTDStructure();
		if(frame.isIsPanelTestDataSelected())
			gridTDStructure= frame.getPanelTestDataView().getM_CMGridTDStructure();
		String formula = data.getStringFormula();*/
		CMDialogFormulasValues cmd= new CMDialogFormulasValues();
		cmd.setTypeDataReferences(subjects);
		cmd.setTypeDataRefered(data);
		cmd.setRecalculateFormula(true);
		cmd.jButtonOKActionPerformed(null);
		frame.setWaitCursor(false);
		}
		catch(Exception ex){
			CMFrameView frame= CMApplication.frame;
			frame.setWaitCursor(false);
		}
	}
	/**
	 * @param p_Frame
	 * @param p_Observer
	 * @param p_Subject
	 * @param p_Key
	 * @param p_linkValue
	 * @return
	 * @author svonborries
	 * Modify to be adapted to the new undo/redo model
	 */
	@SuppressWarnings("unchecked")
	public static UndoableEdit getTypeDataReferenceForGlobalStructure(CMFrameView p_Frame,ITypeData p_Observer, ITypeData p_Subject, String p_Key, boolean p_linkValue ) {
				Vector typeDatasReferences= new Vector();
				CMCompoundEdit ce = new CMCompoundEdit();
				TestDataCombinations p_testDataCombinations=p_Frame.getGridTDStructure().getTDStructure().getTestDataCombination();
				int p_indexGlobalStructure=p_Observer.getStructureTestData().getGlobalIndex();
				int p_numofTypedata=p_Observer.getStructureTestData().getTypeData().indexOf(p_Observer);
	            for (int i = 0; i < p_testDataCombinations.getM_TestDatas().size(); i++) {
	                TestData m_td = (TestData)p_testDataCombinations.getM_TestDatas().elementAt(i);
	                for (int j = 0; j < m_td.getM_TDStructure().getM_StructureTestData().size(); j++) {
	                    StructureTestData m_tds = (StructureTestData)m_td.getM_TDStructure().getM_StructureTestData().elementAt(j);
	                    if (m_tds.getGlobalIndex() == p_indexGlobalStructure) {
	                        ITypeData s = (ITypeData)m_tds.getTypeData().elementAt(p_numofTypedata);
	                        if (s.getGlobal().equals("G")) {
	                        	typeDatasReferences.add(s);
	                        }
	                    }
	                }
	            }
	            for (Iterator iter = typeDatasReferences.iterator(); iter
						.hasNext();) {
					ITypeData observer = (ITypeData) iter.next();
					ce.addEdit(CMModelEditFactory.INSTANCE.createAddObserverInTypeDataModelEdit(observer,p_Subject,p_Key));
					p_Subject.addObserver(observer);
					observer.addSubject(p_Key,p_Subject);
					/*ce.addEdit(CMModelEditFactory.INSTANCE.createChangeisLinkElementInTypeDataModelEdit(observer,p_linkValue));
					observer.setLinkValue(p_linkValue);*/
				}
	            return ce;
	}

	/**
	 * @deprecated nevermore used
	 * @author svonborries
	 * Modify to be adapted to the new undo/redo model
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static UndoableEdit deleteTypeDataReference() {
		CMCompoundEdit ce = new CMCompoundEdit();
		String l_key;
		if(CMApplication.frame.isIsPanelTestDataSelected()){
			ITypeData typ= CMApplication.frame.getPanelTestDataView().getM_CMGridTDStructure().getSelectedTypeData();
			//l_key = typ.getStringFormula();
			l_key = typ.getFormula();
			//ce.addEdit(CMModelEditFactory.INSTANCE.createChangeFormulaInTypeDataModelEdit(typ,""));
			//typ.setFormula("");
			/*ce.addEdit(CMModelEditFactory.INSTANCE.createChangeisLinkElementInTypeDataModelEdit(typ,false));
			typ.setLinkValue(false);*/
			Vector subjects= new Vector(typ.getM_Subjects().values());
			for (Iterator iter = subjects.iterator(); iter.hasNext();) {
				ITypeData typeData = (ITypeData) iter.next();
				ce.addEdit(CMModelEditFactory.INSTANCE.createDeleteObserverInTypeDataModelEdit(typ,typeData, l_key));
				typeData.deleteObserver(typ);
				typ.removeSubject(l_key);
			}
			typ.getM_Subjects().clear();
		//	CMApplication.frame.getPanelTestDataView().getM_CMGridTDStructure().update(CMIndexTDStructureUpdate.getInstance().getIndex());
		}
		else{
			ITypeData typ= CMApplication.frame.getGridTDStructure().getSelectedTypeData();
			/*l_key = typ.getStringFormula();
			ce.addEdit(CMModelEditFactory.INSTANCE.createChangeFormulaInTypeDataModelEdit(typ,""));
			typ.setFormula("");*/
			l_key = typ.getFormula();
			/*ce.addEdit(CMModelEditFactory.INSTANCE.createChangeisLinkElementInTypeDataModelEdit(typ,false));
			typ.setLinkValue(false);*/
			Vector subjects= new Vector(typ.getM_Subjects().values());
			for (Iterator iter = subjects.iterator(); iter.hasNext();) {
				ITypeData typeData = (ITypeData) iter.next();
				ce.addEdit(CMModelEditFactory.INSTANCE.createDeleteObserverInTypeDataModelEdit(typ,typeData, l_key));
				typeData.deleteObserver(typ);
				typ.removeSubject(l_key);
			}
			typ.getM_Subjects().clear();
			ce.addEdit(deleteTypeDataReferenceForGlobalStructure(CMApplication.frame,typ));
		//	CMApplication.frame.getGridTDStructure().update(CMIndexTDStructureUpdate.getInstance().getIndex());
		}
		return ce;
	}
	/**
	 * @param p_Frame
	 * @param p_Observer
	 * @author svonborries
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static UndoableEdit deleteTypeDataReferenceForGlobalStructure(CMFrameView p_Frame,ITypeData p_Observer) {
		String l_key;
		CMCompoundEdit ce = new CMCompoundEdit();
		Vector typeDatasReferences= new Vector();
		TestDataCombinations p_testDataCombinations=p_Frame.getGridTDStructure().getTDStructure().getTestDataCombination();
		int p_indexGlobalStructure=p_Observer.getStructureTestData().getGlobalIndex();
		int p_numofTypedata=p_Observer.getStructureTestData().getTypeData().indexOf(p_Observer);
        for (int i = 0; i < p_testDataCombinations.getM_TestDatas().size(); i++) {
            TestData m_td = (TestData)p_testDataCombinations.getM_TestDatas().elementAt(i);
            for (int j = 0; j < m_td.getM_TDStructure().getM_StructureTestData().size(); j++) {
                StructureTestData m_tds = (StructureTestData)m_td.getM_TDStructure().getM_StructureTestData().elementAt(j);
                if (m_tds.getGlobalIndex() == p_indexGlobalStructure) {
                    ITypeData s = (ITypeData)m_tds.getTypeData().elementAt(p_numofTypedata);
                    if (s.getGlobal().equals("G")) {
                    	typeDatasReferences.add(s);
                    }
                }
            }
        }
        for (Iterator iter = typeDatasReferences.iterator(); iter.hasNext();) {
			ITypeData typ = (ITypeData) iter.next();
/*			l_key = typ.getStringFormula();
			ce.addEdit(CMModelEditFactory.INSTANCE.createChangeFormulaInTypeDataModelEdit(typ,""));
			typ.setFormula("");*/
			l_key = typ.getFormula();
			/*ce.addEdit(CMModelEditFactory.INSTANCE.createChangeisLinkElementInTypeDataModelEdit(typ,false));
			typ.setLinkValue(false);*/
			Vector subjects= new Vector(typ.getM_Subjects().values());
			for (Iterator iter2 = subjects.iterator(); iter2.hasNext();) {
				ITypeData typeData = (ITypeData) iter2.next();
				ce.addEdit(CMModelEditFactory.INSTANCE.createDeleteObserverInTypeDataModelEdit(typ,typeData, l_key));
				typeData.deleteObserver(typ);
				typ.removeSubject(l_key);
			}
			typ.getM_Subjects().clear();
		}
        return ce;
}
	/**
	 * @author svonborries
	 * @return
	 */
	public static UndoableEdit deleteTypeDataReferenceInFormulas() {
		String l_key;
		CMCompoundEdit ce = new CMCompoundEdit();
		if(CMApplication.frame.isIsPanelTestDataSelected()){
			ITypeData typ= CMApplication.frame.getPanelTestDataView().getM_CMGridTDStructure().getSelectedTypeData();
			//l_key = typ.getStringFormula();
			l_key = typ.getFormula();
			Vector subjects= new Vector(typ.getM_Subjects().values());
			for (Iterator iter = subjects.iterator(); iter.hasNext();) {
				ITypeData typeData = (ITypeData) iter.next();
				ce.addEdit(CMModelEditFactory.INSTANCE.createDeleteObserverInTypeDataModelEdit(typ,typeData,l_key));
				typeData.deleteObserver(typ);
				typ.removeSubject(l_key);
			}
			typ.getM_Subjects().clear();
			//CMApplication.frame.getPanelTestDataView().getM_CMGridTDStructure().update(CMIndexTDStructureUpdate.getInstance().getIndex());
		}
		else{
			ITypeData typ= CMApplication.frame.getGridTDStructure().getSelectedTypeData();
			//l_key = typ.getStringFormula();
			l_key = typ.getFormula();
			Vector subjects= new Vector(typ.getM_Subjects().values());
			for (Iterator iter = subjects.iterator(); iter.hasNext();) {
				ITypeData typeData = (ITypeData) iter.next();
				ce.addEdit(CMModelEditFactory.INSTANCE.createDeleteObserverInTypeDataModelEdit(typ,typeData,l_key));
				typeData.deleteObserver(typ);
				typ.removeSubject(l_key);
			}
			typ.getM_Subjects().clear();
			ce.addEdit(deleteTypeDataReferenceForGlobalStructure(CMApplication.frame,typ));
		//	CMApplication.frame.getGridTDStructure().update(CMIndexTDStructureUpdate.getInstance().getIndex());
		}
		return ce;
	}
	/**
	 * @param p_TypeData
	 * @param deleteGlobals
	 * @author svonborries
	 * @return
	 */
	public static UndoableEdit deleteTypeDataReference(ITypeData p_TypeData, boolean deleteGlobals) {
		String l_key;
		CMCompoundEdit ce = new CMCompoundEdit();
		if(CMApplication.frame.isIsPanelTestDataSelected()){

			ITypeData typ= p_TypeData;
			/*l_key = typ.getStringFormula();
			ce.addEdit(CMModelEditFactory.INSTANCE.createChangeFormulaInTypeDataModelEdit(typ,""));
			typ.setFormula("");*/
			l_key = typ.getFormula();
			/*ce.addEdit(CMModelEditFactory.INSTANCE.createChangeisLinkElementInTypeDataModelEdit(typ,false));
			typ.setLinkValue(false);*/
			Vector subjects= new Vector(typ.getM_Subjects().values());
			for (Iterator iter = subjects.iterator(); iter.hasNext();) {
				ITypeData typeData = (ITypeData) iter.next();
				ce.addEdit(CMModelEditFactory.INSTANCE.createDeleteObserverInTypeDataModelEdit(typ,typeData,l_key));
				typeData.deleteObserver(typ);
				typ.removeSubject(l_key);
			}
			typ.getM_Subjects().clear();
		//	CMApplication.frame.getPanelTestDataView().getM_CMGridTDStructure().update(CMIndexTDStructureUpdate.getInstance().getIndex());
		}
		else{
			ITypeData typ= p_TypeData;
			/*l_key = typ.getStringFormula();
			ce.addEdit(CMModelEditFactory.INSTANCE.createChangeFormulaInTypeDataModelEdit(typ,""));
			typ.setFormula("");*/
			l_key = typ.getFormula();
			/*ce.addEdit(CMModelEditFactory.INSTANCE.createChangeisLinkElementInTypeDataModelEdit(typ,false));
			typ.setLinkValue(false);*/
			Vector subjects= new Vector(typ.getM_Subjects().values());
			for (Iterator iter = subjects.iterator(); iter.hasNext();) {
				ITypeData typeData = (ITypeData) iter.next();
				ce.addEdit(CMModelEditFactory.INSTANCE.createDeleteObserverInTypeDataModelEdit(typ,typeData,l_key));
				typeData.deleteObserver(typ);
				typ.removeSubject(l_key);
			}
			typ.getM_Subjects().clear();
			if(deleteGlobals)
				ce.addEdit(deleteTypeDataReferenceForGlobalStructure(CMApplication.frame,typ));
		//	CMApplication.frame.getGridTDStructure().update(CMIndexTDStructureUpdate.getInstance().getIndex());
		}
		return ce;
	}

//HCanedo_22022006_begin
	/**
	 * @param p_Frame
	 * @param p_Observer
	 * @param p_Subject
	 * @param p_Key
	 * @param p_linkValue
	 * @author svonborries
	 * @return
	 */
	public static UndoableEdit setTypeDataReferenceForLocalStructure(CMFrameView p_Frame,ITypeData p_Observer, ITypeData p_Subject, String p_Key, boolean p_linkValue ) {
		//Vector<ITypeData> localsTypeDatas=new Vector();
		CMCompoundEdit ce = new CMCompoundEdit();
		TestDataCombinations p_testDataCombinations=p_Frame.getGridTDStructure().getTDStructure().getTestDataCombination();
		int p_indexGlobalStructure=p_Observer.getStructureTestData().getGlobalIndex();
		int p_numofTypedata=p_Observer.getStructureTestData().getTypeData().indexOf(p_Observer);
        for (int i = 0; i < p_testDataCombinations.getM_TestDatas().size(); i++) {
            TestData m_td = (TestData)p_testDataCombinations.getM_TestDatas().elementAt(i);
            for (int j = 0; j < m_td.getM_TDStructure().getM_StructureTestData().size(); j++) {
                StructureTestData m_tds = (StructureTestData)m_td.getM_TDStructure().getM_StructureTestData().elementAt(j);
                if (m_tds.getGlobalIndex() == p_indexGlobalStructure) {
                    ITypeData s = (ITypeData)m_tds.getTypeData().elementAt(p_numofTypedata);
                    if (s.getGlobal().equals("G")) {
                    	int indexSubject=p_Subject.getStructureTestData().getTypeData().indexOf(p_Subject);
                    	String key=generateLocalKeyForLinkedElements(m_td,p_Key);
                    	ITypeData localSubject=(ITypeData)m_tds.getTypeData().elementAt(indexSubject);
                    	ce.addEdit(CMModelEditFactory.INSTANCE.createAddObserverInTypeDataModelEdit(s,localSubject,key));
                    	localSubject.addObserver(s);
            			s.addSubject(key,localSubject);
            			/*ce.addEdit(CMModelEditFactory.INSTANCE.createChangeisLinkElementInTypeDataModelEdit(s,p_linkValue));
            			s.setLinkValue(p_linkValue);*/
            			if(p_linkValue){
            				ce.addEdit(CMModelEditFactory.INSTANCE.createChangeGlobalReferenceInTypeDataModelEdit(s,""));
            				s.setGlobal("");
            				/*ce.addEdit(CMModelEditFactory.INSTANCE.createChangeFormulaInTypeDataModelEdit(s,key));
            				s.setFormula(key);*/
            				ce.addEdit(CMModelEditFactory.INSTANCE.createChangeValueInTypeDataModelEdit(s,localSubject.getValue()));
            				s.setValue(localSubject.getValue());
            				//s.setStringValue(localSubject.getStringValue());
            			}
            		//	localsTypeDatas.addElement(s);
                    }
                }
            }
        }
        //return localsTypeDatas;
        return ce;
	}
	public static UndoableEdit setFormulasTypeDataReferenceForLocalStructure(CMFrameView p_Frame,ITypeData p_Observer, ITypeData p_Subject, String p_Key, boolean p_linkValue,HashMap p_LocalReferences ) {
		CMCompoundEdit ce = new CMCompoundEdit();
		TestDataCombinations p_testDataCombinations=p_Frame.getGridTDStructure().getTDStructure().getTestDataCombination();
		int p_indexGlobalStructure=p_Observer.getStructureTestData().getGlobalIndex();
		int p_numofTypedata=p_Observer.getStructureTestData().getTypeData().indexOf(p_Observer);
        for (int i = 0; i < p_testDataCombinations.getM_TestDatas().size(); i++) {
            TestData m_td = (TestData)p_testDataCombinations.getM_TestDatas().elementAt(i);
            for (int j = 0; j < m_td.getM_TDStructure().getM_StructureTestData().size(); j++) {
                StructureTestData m_tds = (StructureTestData)m_td.getM_TDStructure().getM_StructureTestData().elementAt(j);
                if (m_tds.getGlobalIndex() == p_indexGlobalStructure) {
                    ITypeData s = (ITypeData)m_tds.getTypeData().elementAt(p_numofTypedata);
                    if (s.getGlobal().equals("G")) {
                    	int indexSubject=p_Subject.getStructureTestData().getTypeData().indexOf(p_Subject);
                    	ITypeData localSubject=(ITypeData)m_tds.getTypeData().elementAt(indexSubject);
                    	String key=generateLocalKeyForLinkedElements(m_td,p_Key);
                    	ce.addEdit(CMModelEditFactory.INSTANCE.createAddObserverInTypeDataModelEdit(s,localSubject,key));
                    	localSubject.addObserver(s);
            			s.addSubject(key,localSubject);
            			/*ce.addEdit(CMModelEditFactory.INSTANCE.createChangeisLinkElementInTypeDataModelEdit(s,p_linkValue));
            			s.setLinkValue(p_linkValue);*/
            			if(p_LocalReferences.containsKey(s)){
            				CMLocalReferenceConvert localReferece= (CMLocalReferenceConvert) p_LocalReferences.get(s);
            				localReferece.setKeys(p_Key,key);
            			}
            			else{
            				CMLocalReferenceConvert localReference=new CMLocalReferenceConvert();
            				localReference.setKeys(p_Key,key);
            				localReference.setLocalTypeData(s);
            				p_LocalReferences.put(s,localReference);
            			}

                    }
                }
            }
        }
        return ce;
	}
	private static String generateLocalKeyForLinkedElements(TestData p_TestData,String p_GlobalKey){
		StringBuffer result=new StringBuffer(p_GlobalKey);
		int indexofSign=result.indexOf("$");
		String tdName=p_TestData.getName()+".";
		result.insert(indexofSign+1,tdName);
		Logger.getLogger(TDStructureManager.class).debug(result);
		return result.toString();
	}

	@SuppressWarnings("unchecked")
	public Vector generateTypeDataValue(String values, TestObject p_TestObject, int p_AmountsOfValues) {
		CMTypeDataValueGenerator.generateTypeDataValue(values,p_TestObject,p_AmountsOfValues,dataCombination);
		//Vector temp = CMTypeDataValueGenerator.generateTypeDataValue(values,p_TestObject,p_AmountsOfValues);
/*        for (int i = temp.size()-1;i>=0; i--) {
            String result = temp.elementAt(i).toString();
            dataCombination.insertElementAt(result, 0);
        }*/
		return dataCombination;
	}

	//ccastedo begins 27.12.06
	/**
	 * @author smoreno Generates a new non repeating new name for the element
	 *         the element text depends on the languaje selected
	 * @param p_structure
	 *            to validate the existing names on this structure
	 * @return a unique name in the structure
	 */
	public String generateNewColumnName(StructureTestData p_structuretestdata) {
		if (p_structuretestdata == null)
			return "";
		// get the prefix according to the languaje
		String l_Prefix = CMMessages.getString("COLUMN_DEFAULT_PREFIX");
		// counter of how many "prefixed" elements are
		int l_Counter = 1;
		// search the last "prefixed" element
		for (int i = 0; i<p_structuretestdata.getNewColumnsHeader().size(); i++) {
			String l_Name = p_structuretestdata.getNewColumnsHeader().elementAt(i).toString();
			// if the name starts with the specified prefix
			if (l_Name.startsWith(l_Prefix)) {
				//get the rest of the string not  included in the prefix
				String candidateSufix = l_Name.substring(l_Prefix.length());

				int candidateSufixint;
				//try to convert the suffix to int
				try {
					candidateSufixint = Integer.parseInt(candidateSufix);
				} catch (Exception e) {
					// continue with the next element
					continue;
				}
				// if the converted to int sufix is greater than the counter assign it plus 1
				if (l_Counter <= candidateSufixint)
					l_Counter = Integer.parseInt(candidateSufix)+1;
			}
		}
		//return the concatenated string
		return l_Prefix+new Integer(l_Counter).toString();
	}
	//ccastedo ends 27.12.06
}