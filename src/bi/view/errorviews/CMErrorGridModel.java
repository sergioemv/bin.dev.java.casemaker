package bi.view.errorviews;
import com.eliad.model.GenericGridModel;
import bi.view.cells.CMErrorNameCellView;
import bi.view.cells.CMErrorDescriptionCellView;
import bi.view.cells.CMErrorStateCellView;
import model.BusinessRules;
import java.util.Vector;
import model.TestCase;
import bi.view.cells.CMErrorTestCasesCellView;
import bi.view.cells.CMErrorClosedByCellView;
import bi.view.cells.CMErrorClosingDateCellView;
import bi.view.cells.CMErrorIssuedByCellView;
import bi.view.cells.CMErrorIssueDateCellView;
import bi.view.cells.CMErrorPriorityCellView;
import bi.view.cells.CMErrorErrorClassCellView;
import model.CMError;
//HCanedo_30112004_Begin
import bi.view.cells.CMErrorAssignDateCellView;
import bi.view.cells.CMErrorAssignToCellView;
import bi.view.cells.CMErrorRiskLevelCellView;
//HCanedo_30112004_End
/**
 ****************************************************************************** Developed by BUSINESS SOFTWARE INNOVATIONS.
 * Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved.
 */
  //////////////////////// Grid Model /////////////////////////////////////////
  public class CMErrorGridModel extends GenericGridModel {
    public CMErrorGridModel(int numRows, int numColumns) {
      super(numRows, numColumns);
    }
    public boolean isCellEditable(int row, int column) {
      return false;
    }

    public Object getCellObjectAt(int row, int column) {
      if( row >= 0 && column >= 0) {
        return super.getValueAt(row,column);
      }
      else {
        return null;
      }
    }

    public Object getValueAt(int row, int column) {
      Object obj = null;
      if( row >= 0 && column >= 0) {
        obj = super.getValueAt(row,column);
      }
      else {
        return null;
      }
      if( obj == null) { return null; }
      if( obj instanceof CMErrorNameCellView) {
        CMErrorNameCellView nameView = (CMErrorNameCellView) obj;
		return nameView.getM_CMErrorCellView().getM_CMError().getM_Name();
      }
      else if( obj instanceof CMErrorDescriptionCellView) {
	    CMErrorDescriptionCellView descriptionView = (CMErrorDescriptionCellView) obj;
	    return descriptionView.getM_CMErrorCellView().getM_CMError().getM_Description();
	  }
      else if( obj instanceof CMErrorStateCellView) {
	    CMErrorStateCellView stateView = (CMErrorStateCellView) obj;
	    return stateView.getM_CMErrorCellView().getM_CMError().getM_State();
      }
      else if( obj instanceof CMErrorErrorClassCellView) {
	    CMErrorErrorClassCellView errorClassView = (CMErrorErrorClassCellView) obj;
	    return errorClassView.getM_CMErrorCellView().getM_CMError().getM_ErrorClass();
      }
      else if( obj instanceof CMErrorPriorityCellView) {
	    CMErrorPriorityCellView priorityView = (CMErrorPriorityCellView) obj;
	    return priorityView.getM_CMErrorCellView().getM_CMError().getM_Priority();
      }
      else if( obj instanceof CMErrorIssueDateCellView) {
	    CMErrorIssueDateCellView issueDateView = (CMErrorIssueDateCellView) obj;
	    return issueDateView.getM_CMErrorCellView().getM_CMError().getM_IssueDate();
      }
      else if( obj instanceof CMErrorIssuedByCellView) {
	    CMErrorIssuedByCellView issuedByView = (CMErrorIssuedByCellView) obj;
	    return issuedByView.getM_CMErrorCellView().getM_CMError().getM_IssuedBy();
      }
      else if( obj instanceof CMErrorClosingDateCellView) {
	    CMErrorClosingDateCellView closingDateView = (CMErrorClosingDateCellView) obj;
	    return closingDateView.getM_CMErrorCellView().getM_CMError().getM_ClosingDate();
      }
      else if( obj instanceof CMErrorClosedByCellView) {
	    CMErrorClosedByCellView closedByView = (CMErrorClosedByCellView) obj;
	    return closedByView.getM_CMErrorCellView().getM_CMError().getM_ClosedBy();
      }
      else if( obj instanceof CMErrorTestCasesCellView) {
	    CMErrorTestCasesCellView testCasesView = (CMErrorTestCasesCellView) obj;
        Vector testCases = testCasesView.getM_CMErrorCellView().getM_CMError().getM_TestCases();
	    return constructStringListOfTestCases(testCases);
      }
//HCanedo_30112004_Begin
	  else if( obj instanceof CMErrorAssignDateCellView) {
	    CMErrorAssignDateCellView assignDateView = (CMErrorAssignDateCellView) obj;
	    return assignDateView.getM_CMErrorCellView().getM_CMError().getM_AssignDate();
      }
      else if( obj instanceof CMErrorAssignToCellView) {
	    CMErrorAssignToCellView assignToView = (CMErrorAssignToCellView) obj;
	    return assignToView.getM_CMErrorCellView().getM_CMError().getM_AssignTo();
      }
      else if( obj instanceof CMErrorRiskLevelCellView) {
	    CMErrorRiskLevelCellView riskLevelView = (CMErrorRiskLevelCellView) obj;
        Vector testCases = riskLevelView.getM_CMErrorCellView().getM_CMError().getM_TestCases();
	    return constructStringListOfRiskLevel(testCases);
      }
//HCanedo_30112004_End
      else {
        return null;
      }
    }
//HCanedo_30112004_Begin
    public static String constructStringListOfRiskLevel(Vector p_TestCases) {
      StringBuffer stringListOfRiskLevel = new StringBuffer();
      int numOfTestCases = p_TestCases.size();
      TestCase testCase = null;
      for( int i = 0; i < numOfTestCases; i++) {
	    testCase = (TestCase) p_TestCases.elementAt(i);
		stringListOfRiskLevel.append(testCase.getRiskLevel());
        if( i != (numOfTestCases - 1)) {
          stringListOfRiskLevel.append(BusinessRules.COMMA);
          stringListOfRiskLevel.append(BusinessRules.SPACE);
        }
      }
      return stringListOfRiskLevel.toString();
    }
//HCanedo_30112004_End
    public static String constructStringListOfTestCases(Vector p_TestCases) {
      StringBuffer stringListOfTestCases = new StringBuffer();
      int numOfTestCases = p_TestCases.size();
      TestCase testCase = null;
      for( int i = 0; i < numOfTestCases; i++) {
	    testCase = (TestCase) p_TestCases.elementAt(i);
		stringListOfTestCases.append(testCase.getName());
        stringListOfTestCases.append(testCase.getStateName());
        if( i != (numOfTestCases - 1)) {
          stringListOfTestCases.append(BusinessRules.COMMA);
          stringListOfTestCases.append(BusinessRules.SPACE);
        }
      }
      return stringListOfTestCases.toString();
    }

    public Vector getDataVector() {
                Vector rows = new Vector(0);
                int numOfRows = this.getRowCount();
                int numOfColumns = this.getColumnCount();
                for( int i = 0; i < numOfRows; i++) {
                  Vector row = new Vector(0);
                  for( int j = 0; j < numOfColumns; j++) {
                    row.addElement(this.getCellObjectAt(i,j));
                  }
                  rows.addElement(row);
                }
                return rows;
     }

    public Vector getDataObjects() {
                Vector dataObjects = new Vector(0);
                int numOfRows = this.getRowCount();
                int numOfColumns = this.getColumnCount();
                if( numOfColumns > 0 ){
					for( int i = 0; i < numOfRows; i++) {
					  Vector row = new Vector(0);
					  Object obj = this.getCellObjectAt(i,0);
					  CMError cmError = this.getDataObject(obj);
					  dataObjects.addElement(cmError);
					}
    		   }
			   return dataObjects;
    }

    public CMError getDataObject(Object p_Obj) {
      if( p_Obj == null) { return null; }
      if( p_Obj instanceof CMErrorNameCellView) {
        CMErrorNameCellView nameView = (CMErrorNameCellView) p_Obj;
		return nameView.getM_CMErrorCellView().getM_CMError();
      }
      else if( p_Obj instanceof CMErrorDescriptionCellView) {
	    CMErrorDescriptionCellView descriptionView = (CMErrorDescriptionCellView) p_Obj;
	    return descriptionView.getM_CMErrorCellView().getM_CMError();
	  }
      else if( p_Obj instanceof CMErrorStateCellView) {
	    CMErrorStateCellView stateView = (CMErrorStateCellView) p_Obj;
	    return stateView.getM_CMErrorCellView().getM_CMError();
      }
      else if( p_Obj instanceof CMErrorErrorClassCellView) {
	    CMErrorErrorClassCellView errorClassView = (CMErrorErrorClassCellView) p_Obj;
	    return errorClassView.getM_CMErrorCellView().getM_CMError();
      }
      else if( p_Obj instanceof CMErrorPriorityCellView) {
	    CMErrorPriorityCellView priorityView = (CMErrorPriorityCellView) p_Obj;
	    return priorityView.getM_CMErrorCellView().getM_CMError();
      }
      else if( p_Obj instanceof CMErrorIssueDateCellView) {
	    CMErrorIssueDateCellView issueDateView = (CMErrorIssueDateCellView) p_Obj;
	    return issueDateView.getM_CMErrorCellView().getM_CMError();
      }
      else if( p_Obj instanceof CMErrorIssuedByCellView) {
	    CMErrorIssuedByCellView issuedByView = (CMErrorIssuedByCellView) p_Obj;
	    return issuedByView.getM_CMErrorCellView().getM_CMError();
      }
      else if( p_Obj instanceof CMErrorClosingDateCellView) {
	    CMErrorClosingDateCellView closingDateView = (CMErrorClosingDateCellView) p_Obj;
	    return closingDateView.getM_CMErrorCellView().getM_CMError();
      }
      else if( p_Obj instanceof CMErrorClosedByCellView) {
	    CMErrorClosedByCellView closedByView = (CMErrorClosedByCellView) p_Obj;
	    return closedByView.getM_CMErrorCellView().getM_CMError();
      }
      else if( p_Obj instanceof CMErrorTestCasesCellView) {
	    CMErrorTestCasesCellView testCasesView = (CMErrorTestCasesCellView) p_Obj;
        return testCasesView.getM_CMErrorCellView().getM_CMError();
      }
      else {
        return null;
      }

    }

  }