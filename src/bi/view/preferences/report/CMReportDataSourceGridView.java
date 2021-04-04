package bi.view.preferences.report;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import bi.view.icons.CMIcon;
import bi.view.lang.CMMessages;
import bi.view.report.data.EReportDataSource;
import bi.view.utils.StripeSortableTableModel;

import com.jidesoft.grid.SortableTable;


public class CMReportDataSourceGridView extends SortableTable {

	   public class CMReportDataSourceView {
		   private EReportDataSource dataSource;
		public CMReportDataSourceView(EReportDataSource reportDataSource) {
			dataSource = reportDataSource;
		}
		public EReportDataSource getDataSource() {
			return dataSource;
		}

	}

	private CMReportFormatsGridView m_FormatsTable;
	private DefaultTableModel defaultTableModel;


       public CMReportDataSourceGridView(CMReportFormatsGridView detail) {
    	 super();
    	 m_FormatsTable = detail;
    	 this.setModel(new StripeSortableTableModel(getTableModel(),0));
    	 initGUI();
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
    	getColumnModel().getColumn(1).setPreferredWidth(250);
    }

@Override
public void changeSelection(int arg0, int arg1, boolean arg2, boolean arg3) {
	// TODO Auto-generated method stub
	super.changeSelection(arg0, arg1, arg2, arg3);
	selectRespectiveFormatGrid(arg0);
	this.repaint();
}
    public void selectRespectiveFormatGrid(int p_row){
    	if (p_row!=-1)
    	{
    		Object obj = getValueAt(p_row,0);
    		//ccastedo 14.11.06 ReportDataSource ds = ((CellFormatTestCaseReportsAvailableDSView)obj).getM_CellFormatTestCaseReportsView().getTestCaseReport();
    		EReportDataSource ds = ((CMReportDataSourceView)obj).getDataSource();

    		if (ds.toString().equalsIgnoreCase(CMMessages.getString("REPORT_DATA_OLD_TEST_DATA"))){
    			//((CMPreferences)this.getParent().getParent()).jButtonTestCaseReportsEdit.setEnabled(false);
    		}
    		m_FormatsTable.filterBasedOnReportsource(ds);
    	}
    }

    public void setM_TestCaseReportDataSource(Vector<EReportDataSource> p_TestCaseReportDataSource) {
        addCMTestCaseReportsDataSourceViews(p_TestCaseReportDataSource);
        selectCMTestCaseReportsDataSourceView(0);
    }

    public void addCMTestCaseReportsDataSourceViews(Vector<EReportDataSource> p_TestCaseReportDataSource) {
        getTableModel().setRowCount(0);
        int l_numOfDataSourceReport = p_TestCaseReportDataSource.size();
        for (int i = 0; i < l_numOfDataSourceReport; i++) {
			    EReportDataSource l_ReportDataSource=(EReportDataSource)p_TestCaseReportDataSource.elementAt(i);
	            addCMTestCaseReportsDataSourceView(createCMTestCaseReportsDataSourceView(l_ReportDataSource));
		}
    }

    private void addCMTestCaseReportsDataSourceView(Vector<CMReportDataSourceView> p_CellFormatTestCaseReportsDSView) {
       getTableModel().addRow(p_CellFormatTestCaseReportsDSView);
        int newSelectionIndex = this.getRowCount() - 1;
        selectCMTestCaseReportsDataSourceView(newSelectionIndex);
    }

    public Vector<CMReportDataSourceView> createCMTestCaseReportsDataSourceView(EReportDataSource p_ReportDataSource) {


        CMReportDataSourceView l_AvailableDSView = new CMReportDataSourceView(p_ReportDataSource){
        	@Override
        	public String toString() {
        		return getDataSource().toString();
        	}
        };
        CMReportDataSourceView l_SchemaDSView = new CMReportDataSourceView(p_ReportDataSource){
        	@Override
        	public String toString() {
        		return getDataSource().getAbsoluteSchema();
        	}
        };

        Vector<CMReportDataSourceView> l_FormatReportsDataSourceView = new Vector<CMReportDataSourceView>();
		l_FormatReportsDataSourceView .addElement(l_AvailableDSView);
		l_FormatReportsDataSourceView.addElement(l_SchemaDSView);

        return l_FormatReportsDataSourceView;
    }

    public void selectCMTestCaseReportsDataSourceView(int p_index) {
        if (p_index >= 0 && this.getRowCount() > 0) {
        	if (p_index == 0 && this.getRowCount()>1)
        		this.changeSelection(1, 0, false, false);
            this.changeSelection(p_index, 0, false, false);
        }
    }



	private DefaultTableModel getTableModel() {
		if (defaultTableModel == null){
			defaultTableModel = new DefaultTableModel(){
				@Override
				public boolean isCellEditable(int row, int column) {
					return false;
				}
			};
			defaultTableModel.addColumn(CMMessages.getString("REPORT_UI_DATASOURCE_NAME_COL_HEADER"));
			defaultTableModel.addColumn(CMMessages.getString("REPORT_UI_DATASOURCE_SCHEMA_COL_HEADER"));
		}
		return defaultTableModel;
	}




}
