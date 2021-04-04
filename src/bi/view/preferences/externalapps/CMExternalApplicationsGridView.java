package bi.view.preferences.externalapps;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Point;
import java.awt.Toolkit;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import model.BusinessRules;
import model.ExternalApplication;
import bi.view.icons.CMIcon;
import bi.view.lang.CMMessages;
import bi.view.mainframeviews.CMApplication;
import bi.view.mainframeviews.CMFrameView;
import bi.view.utils.StripeSortableTableModel;

import com.jidesoft.grid.SortableTable;

public class CMExternalApplicationsGridView extends SortableTable
{
	 public class CMExternalApplicationPathView
			extends
			CMExternalApplicationView {

		public CMExternalApplicationPathView(
				ExternalApplication externalApplication) {
			super(externalApplication);
		}
		@Override
		public String toString() {

			return getApp().getM_FilePath();
		}
	}


	public class CMExternalApplicationNameView
			extends
			CMExternalApplicationView {
		 public CMExternalApplicationNameView(
				ExternalApplication externalApplication) {
			super(externalApplication);
		}

		@Override
		public String toString() {

			return getApp().getM_Name();
		}
	}


	public class CMExternalApplicationView  {
		 private ExternalApplication app;

		public CMExternalApplicationView(ExternalApplication externalApplication) {
			this.app = externalApplication;
		}

		public ExternalApplication getApp() {
			return app;
		}

		public void setApp(ExternalApplication app) {
			this.app = app;
		}


	}


	private DefaultTableModel tableModel;
    public CMExternalApplicationsGridView()
    {
    	super();
        this.setModel(new StripeSortableTableModel(getTableModel(),0));
        initGUI();

    }


    public void setM_ExternalApplications(Vector p_ExternalApplications) {
        getTableModel().setRowCount(0);
        addCMExternalApplicationViews(p_ExternalApplications);
        selectCMExternalApplicationView(0);
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
    	getColumnModel().getColumn(1).setPreferredWidth(300);
    }



    public void selectCMExternalApplicationView(int p_index) {
        if (p_index >= 0 && this.getRowCount() > 0) {
            this.changeSelection(p_index, 0, false, false);
        }
    }

    public Vector<CMExternalApplicationView> createCMExternalApplicationView(ExternalApplication p_ExternalApplication) {

    	CMExternalApplicationNameView nameView = new CMExternalApplicationNameView(p_ExternalApplication);
		CMExternalApplicationPathView pathView = new CMExternalApplicationPathView(p_ExternalApplication);
		Vector<CMExternalApplicationView> list = new Vector<CMExternalApplicationView>();
		list.add(nameView);
		list.add(pathView);
        return list;
    }

    public void addCMExternalApplicationView(Vector<CMExternalApplicationView> p_CMExternalApplicationView) {
    	getTableModel().addRow(p_CMExternalApplicationView);
        int newSelectionIndex = this.getRowCount() - 1;
        selectCMExternalApplicationView(newSelectionIndex);
    }

    public void addCMExternalApplicationViewAt(int p_Index, Vector<CMExternalApplicationView> p_CMExternalApplicationView) {
        int newSelectionIndex = 0;
        if (p_Index >= getRowCount()) {
        	getTableModel().addRow(p_CMExternalApplicationView);
            newSelectionIndex = this.getRowCount() - 1;
        }
        else {
            getTableModel().insertRow(p_Index, p_CMExternalApplicationView);
            newSelectionIndex = p_Index;
        }
        selectCMExternalApplicationView(newSelectionIndex);
    }

    public void addCMExternalApplicationViews(Vector p_ExternalApplications) {
        int numOfExtApp = p_ExternalApplications.size();
        for (int i = 0; i < numOfExtApp; i++) {
            ExternalApplication extApp = (ExternalApplication)p_ExternalApplications.elementAt(i);
            addCMExternalApplicationView(createCMExternalApplicationView(extApp));
        }
    }

    public int getTheNextCMExternalApplicationView(int p_index) {
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

    public void fireEventExternalApplicationAdded(ExternalApplication p_ExternalApplication) {
        Vector<CMExternalApplicationView> v = createCMExternalApplicationView(p_ExternalApplication);
        this.addCMExternalApplicationView(v);
    }


    public int getExternalApplicationViewIndex(ExternalApplication p_ExternalApplication) {
        int numOfViews = this.getRowCount();
        CMExternalApplicationView externalApplicationView;
        for (int i = 0; i < numOfViews; i++) {
            externalApplicationView = getSelectedCMExternalApplicationView(i);
            if (p_ExternalApplication == externalApplicationView.getApp()) {
                return i;
            }
        }
        return -1;
    }

public void delete() {
  int index = getSelectedCMExternalApplicationView();
  if(index>=0){
  ExternalApplication extApp = (this.getSelectedCMExternalApplicationView(index)).getApp();
  if(isProtected(extApp)){
	JOptionPane.showMessageDialog(null,CMMessages.getString("LABEL_DEFAULT_APPLICATION_DELETE"),CMMessages.getString("TITLE_DEFAULT_APPLICATION_DELETE"),JOptionPane.ERROR_MESSAGE);
  }
  else {
		CMFrameView frame = CMApplication.frame;
      int confirmation = JOptionPane.showConfirmDialog(frame,CMMessages.getString("QUESTION_DELETE_EXTERNAL_APPLICATION"),CMMessages.getString("TITLE_DELETE_EXTERNAL_APPLICATION"),JOptionPane.YES_NO_OPTION); //$NON-NLS-1$ //$NON-NLS-2$
      if( confirmation == JOptionPane.YES_OPTION) {
		deleteExternalApplicationAt(index);
      }
  }
  }
}

public void deleteExternalApplicationAt(int p_Index){
   this.deleteCMExternalApplicationView(p_Index);
}

public void deleteCMExternalApplicationView(int p_index){

  this.getTableModel().removeRow(p_index);
  int next = this.getTheNextCMExternalApplicationView(p_index);
  this.selectCMExternalApplicationView(next);


}
    public CMDialogExternalApplication createCMDialogExternalApplication() {

        CMDialogExternalApplication dlg = new CMDialogExternalApplication();
        Dimension dlgSize = dlg.getPreferredSize();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Point loc = getLocation();
        dlg.setLocation((screenSize.width - dlgSize.width) / 2 /*+ loc.x*/,
            (screenSize.height - dlgSize.height) / 2 /*+ loc.y*/);
        dlg.setModal(true);
        return dlg;
    }

    public void addExternalApplicationAt(int p_Index, ExternalApplication p_ExternalApplication) {
        int newIndex;
        Vector<CMExternalApplicationView> externalApplicationView = createCMExternalApplicationView(p_ExternalApplication);
        addCMExternalApplicationView(externalApplicationView);
    }


    public void changeExternalApplicationPath(int p_Index, String p_Path, ExternalApplication p_ExternalApplication) {
        p_ExternalApplication.setM_FilePath(p_Path);
    }
    public void changeExternalApplicationName(int p_Index,String newName,ExternalApplication extApp){
        extApp.setM_Name(newName);
    }



    public void create() {
        CMDialogExternalApplication dlg = createCMDialogExternalApplication();
        dlg.show();
        if (dlg.jButtonOKClicked()) {
            ExternalApplication newExternalApplication = dlg.getExternalApplication();
            if(alreadyCreated(newExternalApplication.getM_Name())){
					JOptionPane.showMessageDialog(null,CMMessages.getString("LABEL_APPLICATION_ALREADY_CREATED"),CMMessages.getString("TITLE_APPLICATION_ALREADY_CREATED"),JOptionPane.ERROR_MESSAGE);
            }
            else{
            int index = getModel().getRowCount();
            this.addExternalApplicationAt(index,newExternalApplication);
            }
        }
    }

    public boolean alreadyCreated(String newName){
        for(int i=0;i< getRowCount();i++){
			CMExternalApplicationNameView extApp =(CMExternalApplicationNameView)this.getTableModel().getValueAt(i, 0);
            if(newName.equals(extApp.toString())){
                return true;
            }
        }
        return false;
    }

    public int getSelectedCMExternalApplicationView() {
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

    public CMExternalApplicationView getSelectedCMExternalApplicationView(int p_index) {
        return (CMExternalApplicationView) this.getModel().getValueAt(p_index, 0);
    }

    public void editCMExternalApplicationView(CMExternalApplicationView p_CMExternalApplicationView,int index) {

        CMDialogExternalApplication dlg = createCMDialogExternalApplication();
        ExternalApplication extApp = p_CMExternalApplicationView.getApp();
        boolean defaultApplication = isProtected(extApp);
        dlg.setExternalApplication(extApp,defaultApplication);
        dlg.show();
        if (dlg.jButtonOKClicked()) {
            index = this.getSelectedCMExternalApplicationView();
            if (index >= 0) {
                extApp = dlg.getExternalApplication();
                String newPath = extApp.getM_FilePath();
                String newName = extApp.getM_Name();
                changeExternalApplicationPath(index, newPath, extApp);
                changeExternalApplicationName(index,newName,extApp);
            }
        }
    }

    public boolean isProtected(ExternalApplication extApp){
        String name = extApp.getM_Name();
        if(name.equals(BusinessRules.INTERNET_APPLICATION_DENOMINATIVE)||name.equals(BusinessRules.CHART_APPLICATION_DENOMINATIVE)){
            return true;
        }
        return false;

    }
    public void edit() {
        int index = this.getSelectedCMExternalApplicationView();
        if (index >= 0) {
            CMExternalApplicationView selectedView = getSelectedCMExternalApplicationView(index);
            editCMExternalApplicationView(selectedView,index);
        }
    }


	public DefaultTableModel getTableModel() {
		if (tableModel == null){
			tableModel = new DefaultTableModel(){
				@Override
				public boolean isCellEditable(int row, int column) {
					return false;
				}
			};
			tableModel.addColumn(CMMessages.getString("LABEL_APPLICATION_NAME_COLUMN"));
			tableModel.addColumn(CMMessages.getString("LABEL_APPLICATION_PATH_COLUMN"));
		}
		return tableModel;
	}


	public Vector<ExternalApplication> getExternalApplications() {
		Vector<ExternalApplication> vec = new Vector<ExternalApplication>();
		  for(int i=0;i< getRowCount();i++){
				CMExternalApplicationNameView extApp =(CMExternalApplicationNameView)this.getTableModel().getValueAt(i, 0);
	            	vec.add(extApp.getApp());
	            }
		return vec;
	}


    //////////////////////////////////////////////////////////////////////////////
}
