/* Developed by BUSINESS SOFTWARE INNOVATIONS.  .. Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved..*/

package  bi.view.cells;
import bi.view.lang.CMMessages;
import model.TestCase;
import model.util.CMStateBean;

public class CMCellHeaderTestCase extends CMBaseCell {
    public CMCellHeaderTestCase(TestCase testCase) {
      super(testCase);
    }    
    public String toString() {    
    	//TestCase testCase = ((CMCellTestCase)obj).getTestCase();
		String s1 = ((TestCase)getModel()).getName();
		String s2 = CMStateBean.STATE_POSITIVE_LABEL;
		int    state = ((TestCase)getModel()).getState();
		if( state == CMStateBean.STATE_FAULTY) {
		 s2 = CMMessages.getString("STATE_FAULTY_LABEL");
		} else if( state == CMStateBean.STATE_IRRELEVANT) {
		  	s2 = CMMessages.getString("STATE_IRRELEVANT_LABEL");
			} else if( state == CMStateBean.STATE_NEGATIVE) {
		  	s2 = CMStateBean.STATE_NEGATIVE_LABEL;
			} else if ( state == CMStateBean.STATE_POSITIVE) {
		  	s2 = CMStateBean.STATE_POSITIVE_LABEL;
			}
		StringBuffer sb = new StringBuffer(s1);
		sb.append(s2);
		return sb.toString();
    //	return this.testCase.getName();
    }
}
