/**
 * 24/05/2007
 * svonborries
 */
package bi.controller.testdata.format;

import java.util.Date;


import model.BusinessRules;

import model.ITypeData;
import model.StructureTestData;
import model.TestData;
import model.TestDataCombinations;
import model.TestDataFormat;
import bi.controller.TDStructureManager;
import bi.view.icons.CMIcon;
import bi.view.lang.CMMessages;
import bi.view.mainframeviews.CMApplication;

/**
 * @author svonborries
 *
 */
public class CMFormatUtils {
	
	
    public static void openReportFormatError(TestDataCombinations p_testDataCombinations, int p_indexGlobalStructure,
            int p_numofTypedata, TestDataFormat p_Formatter, String p_AboslutePath){
    	boolean showReport= false;
    	TDStructureManager testDataManager = CMApplication.frame.getCmApplication().getSessionManager().getTDStructureManager();

    	StringBuffer testDatasNotFormatter= new StringBuffer();
    	testDatasNotFormatter.append("<?xml version=" + '"' + "1.0" + '"' + " encoding=" + '"' + "iso-8859-1" + '"' + " ?>");
    	testDatasNotFormatter.append(System.getProperty("line.separator"));
    	testDatasNotFormatter.append("<reportFormats> ");
    	testDatasNotFormatter.append(System.getProperty("line.separator"));
    	testDatasNotFormatter.append(BusinessRules.REPORT_HEAD_TITLE_BEGIN);
    	testDatasNotFormatter.append(CMMessages.getString("REPORT_HEAD_TITLE_FORMATS")+" - "+model.BusinessRules.APPLICATIONNAME+" "+model.BusinessRules.APPLICATIONVERSION);
    	testDatasNotFormatter.append(BusinessRules.REPORT_HEAD_TITLE_END);
    	testDatasNotFormatter.append(System.getProperty("line.separator"));
    	testDatasNotFormatter.append(BusinessRules.REPORT_TITLE_BEGIN);
    	testDatasNotFormatter.append(CMMessages.getString("REPORT_HEAD_TITLE_FORMATS"));
        testDatasNotFormatter.append(BusinessRules.REPORT_TITLE_END);
        testDatasNotFormatter.append(System.getProperty("line.separator"));
    	testDatasNotFormatter.append("<HeaderImg>");
    	testDatasNotFormatter.append(BusinessRules.REPORT_IMAGEFLD+"/"+CMIcon.CASEMAKER_LOGO.getFilename());
    	testDatasNotFormatter.append("</HeaderImg>");
    	testDatasNotFormatter.append(System.getProperty("line.separator"));
    	testDatasNotFormatter.append(BusinessRules.REPORT_USER_LABEL_BEGIN);
    	testDatasNotFormatter.append(CMMessages.getString("REPORT_USER_LABEL"));
    	testDatasNotFormatter.append(BusinessRules.REPORT_USER_LABEL_END);
    	testDatasNotFormatter.append(BusinessRules.REPORT_USER_VALUE_BEGIN);
        String userName = System.getProperty("user.name");
        if (userName != null) {
        	testDatasNotFormatter.append(testDataManager.changeReservedCharacterInXML(userName));
        }
        testDatasNotFormatter.append(BusinessRules.REPORT_USER_VALUE_END);
        testDatasNotFormatter.append(System.getProperty("line.separator"));
        testDatasNotFormatter.append(BusinessRules.REPORT_DATE_LABEL_BEGIN);
        testDatasNotFormatter.append(CMMessages.getString("REPORT_DATE_LABEL"));
        testDatasNotFormatter.append(BusinessRules.REPORT_DATE_LABEL_END);
    	testDatasNotFormatter.append("<ActualDate>");
        Date date = new java.util.Date();
        testDatasNotFormatter.append(date.toString());
        testDatasNotFormatter.append("</ActualDate>");
        testDatasNotFormatter.append(System.getProperty("line.separator"));
        testDatasNotFormatter.append("<TitleFormatLine TDName=\""+CMMessages.getString("REPORT_FORMAT_LABEL_TDNAME")+
        		"\" SName=\""+CMMessages.getString("REPORT_FORMAT_LABEL_SNAME")+"\" SNumber=\""+
        		CMMessages.getString("REPORT_FORMAT_LABEL_SNUMBER")+"\" SValue=\""+
        		CMMessages.getString("REPORT_FORMAT_LABEL_SVALUE")+"\" SFormat=\""+
        		CMMessages.getString("REPORT_FORMAT_LABEL_SFORMAT")+"\" />");
        testDatasNotFormatter.append(System.getProperty("line.separator"));
    	for (int i = 0; i < p_testDataCombinations.getM_TestDatas().size(); i++) {
            TestData m_td = (TestData)p_testDataCombinations.getM_TestDatas().elementAt(i);
            for (int j = 0; j < m_td.getM_TDStructure().getM_StructureTestData().size(); j++) {
                StructureTestData m_tds = (StructureTestData)m_td.getM_TDStructure().getM_StructureTestData().elementAt(j);
                if (m_tds.getGlobalIndex() == p_indexGlobalStructure) {
                    ITypeData s = (ITypeData)m_tds.getTypeData().elementAt(p_numofTypedata);
                    //String valueresult=CMFormatFactory.applyAnyFormat(p_Formatter,s.getStringValue(),s.getM_Formatter());
                    //String valueresult=s.getFormattedValue();
                    //if(!CMFormatFactory.isSuccessFormated()){
                    	showReport=true;
                    	//testDatasNotFormatter.append("<FormatLine TDName=\""+m_td.getName()+"\" SName=\""+m_tds.getName()+"\" SNumber=\""+(j+1)+"\" SValue=\""+s.getStringValue()+"\" SFormat=\""+p_Formatter.getVisualFormatter()+"\" />");
                    	testDatasNotFormatter.append("<FormatLine TDName=\""+m_td.getName()+"\" SName=\""+m_tds.getName()
                    			+"\" SNumber=\""+(j+1)+"\" SValue=\""+s.getFormattedValue()+"\" SFormat=\""
                    			+p_Formatter.getVisualFormatter()+"\" />");
                    	testDatasNotFormatter.append(System.getProperty("line.separator"));
                    //}
                }
            }
    	}
    	testDatasNotFormatter.append("</reportFormats> ");
    	if(showReport)
    	    testDataManager.createReportFormatNotApply(testDatasNotFormatter.toString(), CMApplication.frame.getGridTDStructure(), CMApplication.frame, p_AboslutePath);
    }

}
