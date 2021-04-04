package bi.view.preferences.toolvendors;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Event;
import java.awt.Font;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JViewport;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import model.BusinessRules;
import model.ExternalApplication;
import model.Project2;
import model.ProjectReference;
import model.TestObject;
import model.TestObjectReference;
import model.ToolVendor;
import model.Workspace2;

import bi.controller.SessionManager;
import bi.controller.WorkspaceManager;
import bi.view.grids.CMBaseJSmartGrid;
import bi.view.icons.CMIcon;
import bi.view.lang.CMMessages;
import bi.view.mainframeviews.CMApplication;
import bi.view.mainframeviews.CMFrameView;
import bi.view.preferences.externalapps.CMExternalApplicationsGridView.CMExternalApplicationNameView;
import bi.view.testobjectviews.CMPanelTestObjectView;
import bi.view.treeviews.CMTreeWorkspaceView;
import bi.view.utils.StripeSortableTableModel;

import com.eliad.model.GenericGridModel;
import com.eliad.model.GridContext;
import com.eliad.model.defaults.DefaultGridCellRenderer;
import com.eliad.model.defaults.DefaultStyleModel;
import com.eliad.swing.GridEvent;
import com.eliad.util.RulerConstants;
import com.jidesoft.grid.SortableTable;

public class CMToolVendorGridView extends SortableTable {
    public class CMToolVendorPathView extends CMToolVendorView {

        public CMToolVendorPathView(ToolVendor toolVendor) {
            super(toolVendor);
            // TODO Auto-generated constructor stub
        }
        @Override
        public String toString() {
            return BusinessRules.RESOURCE_FOLDER+BusinessRules.URL_SEPARATOR+this.getToolVendor().getM_FilePath();
        }
    }
    public class CMToolVendorNameView extends CMToolVendorView {

        public CMToolVendorNameView(ToolVendor toolVendor) {
            super(toolVendor);

        }
        @Override
        public String toString() {
            return this.getToolVendor().getM_Name();
        }
    }
    public class CMToolVendorView {
        private ToolVendor toolVendor;

        public CMToolVendorView(ToolVendor  toolVendor) {
            this.toolVendor = toolVendor;
        }
        public ToolVendor getToolVendor() {
            return toolVendor;
        }

        public void setToolVendor(ToolVendor toolVendor) {
            this.toolVendor = toolVendor;
        }


    }

    private DefaultTableModel tableModel;
    public CMToolVendorGridView() {
        super();
        this.setModel(new StripeSortableTableModel(getTableModel(),0));
        initGUI();
    }


    public void setM_ToolVendors(Vector p_ToolVendors) {
       getTableModel().setRowCount(0);
        addCMToolVendorViews(p_ToolVendors);
        selectCMToolVendorView(0);
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





    public void selectCMToolVendorView(int p_index) {
        if (p_index >= 0 && this.getRowCount() > 0) {
            this.changeSelection(p_index, 0, false, false);
        }
    }

    public Vector<CMToolVendorView> createCMToolVendorView(ToolVendor p_ToolVendor) {
        CMToolVendorNameView nameView = new CMToolVendorNameView(p_ToolVendor);
        CMToolVendorPathView pathView = new CMToolVendorPathView(p_ToolVendor);
        Vector<CMToolVendorView> toolVendorViews = new Vector<CMToolVendorView>();
		toolVendorViews .addElement(nameView);
        toolVendorViews.addElement(pathView);
        return toolVendorViews;
    }

    public Vector createCMCauseEffectViews(Vector p_ToolVendors) {
        int numOfToolVendor = p_ToolVendors.size();
        Vector v = new Vector(numOfToolVendor);
        ToolVendor extApp = null;
        for (int i = 0; i < numOfToolVendor; i++) {
            extApp = (ToolVendor)p_ToolVendors.elementAt(i);
            v.addElement(createCMToolVendorView(extApp));
        }
        return v;
    }

    public void addCMToolVendorView(Vector<CMToolVendorView> p_CMToolVendorViews) {
        getTableModel().addRow(p_CMToolVendorViews);
        int newSelectionIndex = this.getRowCount() - 1;
        selectCMToolVendorView(newSelectionIndex);
    }

    public void addCMToolVendorViewAt(int p_Index, Vector<CMToolVendorView> p_CMToolVendorViews) {
        int newSelectionIndex = 0;
        if (p_Index >= getRowCount()) {
           getTableModel().addRow(p_CMToolVendorViews);
            newSelectionIndex = this.getRowCount() - 1;
        }
        else {
            getTableModel().insertRow(p_Index, p_CMToolVendorViews);
            newSelectionIndex = p_Index;
        }
        selectCMToolVendorView(newSelectionIndex);
    }

    public void addCMToolVendorViews(Vector p_ToolVendors) {
        int numOfExtApp = p_ToolVendors.size();
        for (int i = 0; i < numOfExtApp; i++) {
            ToolVendor extApp = (ToolVendor)p_ToolVendors.elementAt(i);
            String tool = extApp.getM_Name();
            addCMToolVendorView(createCMToolVendorView(extApp));
        }
    }

    public int getTheNextCMToolVendorView(int p_index) {
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


    public int getToolVendorViewIndex(ToolVendor p_ToolVendor) {
        int numOfViews = this.getRowCount();
        CMToolVendorView ToolVendorView;
        for (int i = 0; i < numOfViews; i++) {
            ToolVendorView = getSelectedCMToolVendorView(i);
            if (p_ToolVendor == ToolVendorView.getToolVendor()) {
                return i;
            }
        }
        return -1;
    }

public void delete() {
  int index = getSelectedCMToolVendorView();
  if(index>=0){
      ToolVendor toolVendor = (this.getSelectedCMToolVendorView(index)).getToolVendor();
       if(isProtected(toolVendor)){
           JOptionPane.showMessageDialog(this,CMMessages.getString("LABEL_DEFAULT_APPLICATION_DELETE"),CMMessages.getString("TITLE_DEFAULT_APPLICATION_DELETE"),JOptionPane.ERROR_MESSAGE);
       }
      else {
      Vector testObjectsWichusesToolVendor = new Vector();
        Vector projectsWichusesToolVendor = new Vector();
        Vector workspacesWichusesToolVendor = new Vector();
        String toolvendor = toolVendor.getM_Name();
        whichWorkSpacesUsesActualToolVendor(toolvendor,testObjectsWichusesToolVendor,projectsWichusesToolVendor,workspacesWichusesToolVendor);
        int sizeprojectsWichusesToolVendor = projectsWichusesToolVendor.size();
        if (sizeprojectsWichusesToolVendor==0){
        int confirmation = JOptionPane.showConfirmDialog(CMApplication.frame,CMMessages.getString("QUESTION_DELETE_EXTERNAL_APPLICATION"),CMMessages.getString("TITLE_DELETE_EXTERNAL_APPLICATION"),JOptionPane.YES_NO_OPTION); //$NON-NLS-1$ //$NON-NLS-2$
        if( confirmation == JOptionPane.YES_OPTION) {
            deleteToolVendorAt(index);

        }
        }
        else{
          JOptionPane.showMessageDialog(this,CMMessages.getString("EXIST_TOOLVENDORS_USING"),CMMessages.getString("TITLE_DEFAULT_APPLICATION_DELETE"),JOptionPane.ERROR_MESSAGE);

        }

  }
  }
}


public void deleteToolVendorAt(int p_Index){
   CMToolVendorView selectedView = this.getSelectedCMToolVendorView(p_Index);
   this.deleteToolvendorView(p_Index, selectedView);
}

public void deleteToolvendorView(int p_index, CMToolVendorView p_CMToolVendorView){
    getTableModel().removeRow(p_index);
  int next = this.getTheNextCMToolVendorView(p_index);
  this.selectCMToolVendorView(next);
}
    public CMDialogToolVendor createCMToolVendor() {
        CMDialogToolVendor dlg = new CMDialogToolVendor(CMApplication.frame);
        Dimension dlgSize = dlg.getPreferredSize();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Point loc = getLocation();
        dlg.setLocation((screenSize.width - dlgSize.width) / 2 /*+ loc.x*/,
            (screenSize.height - dlgSize.height) / 2 /*+ loc.y*/);
        dlg.setModal(true);
        return dlg;
    }

    public void addToolVendorAt(int p_Index, ToolVendor p_ToolVendor) {
        Vector<CMToolVendorView> ToolVendorView = createCMToolVendorView(p_ToolVendor);
        addCMToolVendorView(ToolVendorView);
    }


    public void changeToolVendorPath(int p_Index, String p_Path, ToolVendor p_ToolVendor) {
        p_ToolVendor.setM_FilePath(p_Path);
    }
    public void changeToolVendorName(int p_Index,String newName,ToolVendor extApp){
        extApp.setM_Name(newName);
    }



    public void create() {
        CMDialogToolVendor dlg = createCMToolVendor();

        dlg.show();
        if (dlg.jButtonOKClicked()) {
            ToolVendor newToolVendor = dlg.getToolVendor();
            if(alreadyCreated(newToolVendor.getM_Name())){
                    JOptionPane.showMessageDialog(this,CMMessages.getString("LABEL_APPLICATION_ALREADY_CREATED"),CMMessages.getString("TITLE_APPLICATION_ALREADY_CREATED"),JOptionPane.ERROR_MESSAGE);
            }
            else{
            int index = getModel().getRowCount();
            this.addToolVendorAt(index,newToolVendor);


            }
        }

    }

    public boolean alreadyCreated(String newName){
    	for(int i=0;i< getRowCount();i++){
			CMToolVendorView extApp =(CMToolVendorView)this.getTableModel().getValueAt(i, 0);
            if(newName.equals(extApp.toString())){
                return true;
            }
        }
        return false;
    }

    public int getSelectedCMToolVendorView() {
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

    public CMToolVendorView  getSelectedCMToolVendorView(int p_index) {
        return  (CMToolVendorView) getModel().getValueAt(p_index, 0);
    }

    public void editCMToolVendorView(CMToolVendorView p_CMToolVendorView,int index) {
        CMDialogToolVendor dlg = createCMToolVendor();
        ToolVendor extApp = p_CMToolVendorView.getToolVendor();
        boolean defaultApplication = isProtected(extApp);
        dlg.setToolVendor(extApp,defaultApplication);
        dlg.show();
        if (dlg.jButtonOKClicked()) {
            index = this.getSelectedCMToolVendorView();
            if (index >= 0) {
                extApp = dlg.getToolVendor();
                String newPath = BusinessRules.RESOURCE_FOLDER+BusinessRules.URL_SEPARATOR+extApp.getM_FilePath();//svonborries_11012006
                String newName = extApp.getM_Name();
                changeToolVendorPath(index, newPath, extApp);
                changeToolVendorName(index,newName,extApp);
            }
        }
    }

    public boolean isProtected(ToolVendor extApp){
        String name = extApp.getM_Name();
        if(name.equals(BusinessRules.COMPUWARE)){
            return true;
        }
        return false;

    }
    public void edit() {
        int index = this.getSelectedCMToolVendorView();
        if (index >= 0) {
              ToolVendor extApp = getSelectedCMToolVendorView(index).getToolVendor();
              Vector testObjectsWichusesToolVendor = new Vector();
              Vector projectsWichusesToolVendor = new Vector();
              Vector workspacesWichusesToolVendor = new Vector();
              String toolvendor = extApp.getM_Name();
              whichWorkSpacesUsesActualToolVendor(toolvendor,testObjectsWichusesToolVendor,projectsWichusesToolVendor,workspacesWichusesToolVendor);
              int sizeprojectsWichusesToolVendor = projectsWichusesToolVendor.size();
              if (sizeprojectsWichusesToolVendor==0){

                CMToolVendorView selectedView = getSelectedCMToolVendorView(index);//....
                editCMToolVendorView(selectedView,index);  //...
              }
              else{
                JOptionPane.showMessageDialog(this,CMMessages.getString("EXIST_TOOLVENDORS_USING"),CMMessages.getString("TITLE_DEFAULT_APPLICATION_DELETE"),JOptionPane.ERROR_MESSAGE);

              }

        }
    }


    private void whichWorkSpacesUsesActualToolVendor(String toolVendorToRemove, Vector testObjectsWichusesToolVendor,
            Vector projectsWichusesToolVendor, Vector workspacesWichusesToolVendor){

        List workspaces = SessionManager.getCurrentSession().getM_Workspaces();
        int sizeworkspaces = workspaces.size();
        for (int i=0;i<sizeworkspaces;i++){
            Workspace2 workspace = (Workspace2)workspaces.get(i);
            List projects = workspace.getM_ProjectReferences();
            int sizeproyects = projects.size();
            for (int j=0; j<sizeproyects;j++){
                ProjectReference projectReference = (ProjectReference)projects.get(j);
                String nameProyect = projectReference.getName();
                Project2 project = WorkspaceManager.getInstance().readProject2ByReference(projectReference);
                Vector testObjects = project.getTestObjectReferences();
                int sizeTestObjects = testObjects.size();
                for (int k=0;k<sizeTestObjects;k++){
                    TestObjectReference testobjectref= (TestObjectReference)testObjects.elementAt(k);
                    TestObject testobject = (TestObject)WorkspaceManager.getInstance().readTestObjectByReference(testobjectref,projectReference,
                    		SessionManager.getCurrentSession());
                    if (testobject!=null){
                        String toolvendor = testobject.getToolVendor();
                        if (toolvendor.equals(toolVendorToRemove)){
                            testObjectsWichusesToolVendor.addElement(testobject);
                            projectsWichusesToolVendor.addElement(project);
                            workspacesWichusesToolVendor.addElement(workspace);
                        }
                    }
                    else{
                        //nada
                    }
                }
                }
        }

    }

	private DefaultTableModel getTableModel() {
		if (tableModel == null){
			tableModel = new DefaultTableModel(){
				@Override
				public boolean isCellEditable(int row, int column) {
					return false;
				}
			};
			tableModel.addColumn(CMMessages.getString("LABEL_ToolVendor"));
			tableModel.addColumn(CMMessages.getString("LABEL_TOOLTIP_FILE_PATH"));
		}
		return tableModel;
	}


	public Vector<ToolVendor> getToolVendors() {
		Vector<ToolVendor> vec = new Vector<ToolVendor>();
		  for(int i=0;i< getRowCount();i++){
				CMToolVendorView extApp =(CMToolVendorView)this.getTableModel().getValueAt(i, 0);
	            	vec.add(extApp.getToolVendor());
	            }
		return vec;
	}

    }














