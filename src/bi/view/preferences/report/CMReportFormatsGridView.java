package bi.view.preferences.report;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import model.TestCaseExternalReports;

import org.apache.log4j.Logger;

import bi.view.icons.CMIcon;
import bi.view.lang.CMMessages;
import bi.view.mainframeviews.CMApplication;
import bi.view.report.data.EReportDataSource;
import bi.view.utils.CMModalResult;
import bi.view.utils.StripeSortableTableModel;

import com.jidesoft.grid.SortableTable;



public class CMReportFormatsGridView extends SortableTable {

	   public class CMReportFormatExtensionView extends CMReportFormatView {

		public CMReportFormatExtensionView(TestCaseExternalReports report) {
			super(report);
		}
		@Override
		public String toString() {
		 if (getReport()!=null)
			return getReport().getExtension();
		 else
			 return "";
		}

	}

	public class CMReportFormatNameView extends CMReportFormatView {

		public CMReportFormatNameView(TestCaseExternalReports report) {
			super(report);
		}
		@Override
		public String toString() {
			if (getReport()!=null)
				return getReport().getName();
			else
				return "";
		}

	}

	public class CMReportFormatView {
		   private TestCaseExternalReports report;
		   public CMReportFormatView(TestCaseExternalReports report) {
			   	this.report = report;
		}
		public TestCaseExternalReports getReport() {
			return report;
		}

	}

	private Logger logger = Logger.getLogger("bi.view.report.ui");

    private EReportDataSource filteringDataSource;

    private DefaultTableModel tableModel;

    public CMReportFormatsGridView() {
    	 super();
    	 this.setModel(new StripeSortableTableModel(getTableModel(),0));
        initGUI();
    }




    void filterBasedOnReportsource(EReportDataSource p_ds) {
    	if (p_ds!=filteringDataSource)
    	{
    	filteringDataSource = p_ds;
    	setM_TestCaseReportFormat(CMApplication.frame.getCmApplication().getSessionManager().getApplicationSettingManager().
    			getApplicationSetting().getTestCaseReports(p_ds.ordinal()));
    	}


}


    public void setM_TestCaseReportFormat(Vector<TestCaseExternalReports> p_TestCaseReportFormat) {
        addCMTestCaseReportsViews(p_TestCaseReportFormat);
        selectCMTestCaseReportsView(0);
    }


    public void initGUI() {

    	getTableHeader().setDefaultRenderer(new DefaultTableCellRenderer(){
			public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
				// TODO Auto-generated method stub
				Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
			        setFont(new Font("Dialog",Font.PLAIN,12)); //$NON-NLS-1$
					setBackground(new Color(36,38,116));
					setFont(new Font("Dialog",Font.PLAIN,12)); //$NON-NLS-1$
					setForeground(new Color(252,254,252));
					setBorder(BorderFactory.createRaisedBevelBorder());

			return c;
			}
		});
    	setDefaultCellRenderer(new DefaultTableCellRenderer(){

			public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
				// TODO Auto-generated method stub
				Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
				if (column<2){
			    	setIcon(null);
				}
				else{
					if (value.toString().equalsIgnoreCase("")){ //$NON-NLS-1$
						setIcon(CMIcon.WARNING2.getImageIcon());
					}
					setForeground(Color.BLACK);
					setBackground(Color.LIGHT_GRAY);
					setOpaque(true);

				}
			return c;
			}});
    	setRowHeight(22);
    	getColumnModel().getColumn(2).setPreferredWidth(300);

    }

    public void selectCMTestCaseReportsView(int p_index) {
        if (p_index >= 0 && this.getRowCount() > 0) {
            this.changeSelection(p_index, 0, false, false);
        }
    }

    public Vector<CMReportFormatView> createReportFormatView(TestCaseExternalReports p_testCaseReport) {
        CMReportFormatNameView nameView = new CMReportFormatNameView(p_testCaseReport);
        CMReportFormatExtensionView extView = new CMReportFormatExtensionView(p_testCaseReport);
		CMReportFormatView pathView = new CMReportFormatView(p_testCaseReport){
			@Override
			public String toString() {
				if (getReport()!=null)
				return getReport().getFilePath();
				else
					return "";
			}
		};
        Vector<CMReportFormatView> formatReportsView = new Vector<CMReportFormatView>();
		formatReportsView.addElement(nameView);
		formatReportsView.addElement(extView);
		formatReportsView.addElement(pathView);
        return formatReportsView;
    }


    public void addCMTestCaseReportsView(Vector<CMReportFormatView> p_CellFormatTestCaseReportsView) {
        getTableModel().addRow(p_CellFormatTestCaseReportsView);
        int newSelectionIndex = this.getRowCount() - 1;
        selectCMTestCaseReportsView(newSelectionIndex);
    }


    public void addCMTestCaseReportsViews(Vector<TestCaseExternalReports> p_TestCaseReportFormat) {
        getTableModel().setRowCount(0);
        int numOfFormatReport = p_TestCaseReportFormat.size();
        for (int i = 0; i < numOfFormatReport; i++) {
        	TestCaseExternalReports testcaseReport=(TestCaseExternalReports)p_TestCaseReportFormat.elementAt(i);
            addCMTestCaseReportsView(createReportFormatView(testcaseReport));
        }
    }

    public int getTheNextCMTestCaseReportsView(int p_index) {
        int next = 0;
        if (p_index > 0) {
            next = p_index - 1;
            return next;
        }
        else if (p_index == 0 && getModel().getRowCount() > 0) {
            next = 0;
            return next;
        }
        else {
            return -1;
        }
    }


public void delete() {
  int index = getSelectedReportFormatView();
  if(index>=0){
  TestCaseExternalReports testcaseReport = (this.getSelectedReportFormatView(index)).getReport();
  if(testcaseReport.isProtectedReport() || testcaseReport.isDefaultReport()){
	JOptionPane.showMessageDialog(this,CMMessages.getString("LABEL_DEFAULT_XSLT_DELETE"),CMMessages.getString("TITLE_DEFAULT_XSLT_DELETE"),JOptionPane.ERROR_MESSAGE);
  }
  else {

      int confirmation = JOptionPane.showConfirmDialog(this,CMMessages.getString("QUESTION_DELETE_EXTERNAL_XSLT"),CMMessages.getString("TITLE_DELETE_EXTERNAL_XSLT"),JOptionPane.YES_NO_OPTION); //$NON-NLS-1$ //$NON-NLS-2$
      if( confirmation == JOptionPane.YES_OPTION) {
		deleteFormatReportsAt(index);
      }
  }
  }
}

	public void deleteFormatReportsAt(int p_Index){
		CMReportFormatView selectedView = this.getSelectedReportFormatView(p_Index);
		this.deleteReportFormatView(p_Index, selectedView);
}

public void deleteReportFormatView(int p_index, CMReportFormatView p_CellFormatTestCaseReportsView){

  TestCaseExternalReports testcaseReport= p_CellFormatTestCaseReportsView.getReport();

  //Ccastedo begins 04-04-06
  CMApplication.frame.getCmApplication().getSessionManager().getApplicationSettingManager().getApplicationSetting().getTestCaseReports().removeElement(testcaseReport);
  CMApplication.frame.getCmApplication().getSessionManager().getM_WorkspaceManager().deleteFile(testcaseReport.getFilePath());
  //Ccastedo ends 04-04-06
  getTableModel().removeRow(p_index);
  int next = this.getTheNextCMTestCaseReportsView(p_index);
  this.selectCMTestCaseReportsView(next);

}

    public CMReportDialog createCMDialogTestCaseReports() {

        CMReportDialog cmd = new CMReportDialog(CMApplication.frame);
        return cmd;
    }

    public void create() {
        CMReportDialog dlg = createCMDialogTestCaseReports();
        dlg.setVisible(true);
        if (dlg.getModalResult()== CMModalResult.CANCEL)
        	return;
        EReportDataSource l_ReportDataSource = (EReportDataSource)dlg.getJcomboReportDS().getSelectedItem();
        
        if (dlg.getEditTestCaseReport().getReporDataSourceType().equals(l_ReportDataSource))
        		addCMTestCaseReportsView(createReportFormatView(dlg.getEditTestCaseReport()));
		if (filteringDataSource!=null)
			filterBasedOnReportsource(filteringDataSource);

	//	EReportDataSource l_ReportDataSource = (EReportDataSource)dlg.getJcomboReportDS().getSelectedItem();
	    //getM_CMReportDataSourceSplitPane().getM_CMDataSourceGridView().selectCMTestCaseReportsDataSourceView(l_ReportDataSource.ordinal());

    }

    public int getSelectedReportFormatView() {
        int selectedIndex = 0;
        int[] selectedRows = this.getSelectedRows();
        int numSelectedRows = this.getSelectedRowCount();
        if (numSelectedRows > 0) {
            return selectedRows[0];
        }
        else {
            return -1;
        }
    }

    public CMReportFormatView getSelectedReportFormatView(int p_index) {
        Object obj = getModel().getValueAt(p_index, 0);
        return (CMReportFormatView) obj;
    }

    public void editReportFormatView(CMReportFormatView p_ReportFormatView,int index) {

        CMReportDialog dlg = createCMDialogTestCaseReports();
        TestCaseExternalReports testcaseReport= p_ReportFormatView.getReport();
        boolean protectedXSLT = isProtected(testcaseReport);
        boolean defaultXSLT = isDefault(testcaseReport);
        if(!protectedXSLT)
        {
        	TestCaseExternalReports oldTestcaseReport = new TestCaseExternalReports(testcaseReport.getName(),testcaseReport.getFilePath(),testcaseReport.getExtension(),testcaseReport.getReportDataSourceName(),testcaseReport.getDefaultApp());
        	dlg.setTestCaseReportEdit(testcaseReport);
        	if (defaultXSLT){
            	dlg.setElementsDisabled();
            }
        	dlg.show();
        	int index1 = dlg.getEditTestCaseReport().getReporDataSourceType().ordinal();
        	if (dlg.getEditTestCaseReport()!= null){
//        		String s1 = dlg.getEditTestCaseReport().getReportDataSourceName();
//        		String s2 = oldTestcaseReport.getReportDataSourceName();
//        		if (!s1.equalsIgnoreCase(s2))
        		//	getM_CMReportDataSourceSplitPane().getM_CMDataSourceGridView().selectCMTestCaseReportsDataSourceView(index1);

        	}

        //	addCMTestCaseReportsViews(m_TestCaseReportFormat);
        //	getM_CMReportDataSourceSplitPane().getM_CMDataSourceGridView().selectCMTestCaseReportsDataSourceView(index1);
        }
        else
        {

            	JOptionPane.showMessageDialog(this, CMMessages.getString("XSLT_REPORTS_FORMAT_MENSSAGE_DEFAULT_NOT_EDITING"),
                        CMMessages.getString("TESTDATASETREPORTS_TITLE_INFORMATION"), JOptionPane.INFORMATION_MESSAGE);


        }
    }

    public boolean isProtected(TestCaseExternalReports testcaseReport){

        return testcaseReport.isProtectedReport();

    }

    public boolean isDefault(TestCaseExternalReports testcaseReport){

        return testcaseReport.isDefaultReport();

    }

    public void edit() {
    	int index = this.getSelectedReportFormatView();
        if (index >= 0) {
            CMReportFormatView selectedView = getSelectedReportFormatView(index);
            editReportFormatView(selectedView,index);
        }
    	if (filteringDataSource!=null)
			filterBasedOnReportsource(filteringDataSource);
    }

	private DefaultTableModel getTableModel() {
		if (tableModel == null){
			tableModel = new DefaultTableModel(){
				@Override
				public boolean isCellEditable(int row, int column) {
					return false;
				}
			};
			tableModel.addColumn(CMMessages.getString("LABEL_REPORT_FORMAT_NAME_COLUMN"));
			tableModel.addColumn(CMMessages.getString("LABEL_REPORT_FORMAT_EXT_COLUMN"));
			tableModel.addColumn(CMMessages.getString("LABEL_APPLICATION_PATH_COLUMN"));
		}
		return tableModel;
	}
}
