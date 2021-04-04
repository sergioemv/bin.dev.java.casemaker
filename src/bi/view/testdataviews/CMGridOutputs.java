package bi.view.testdataviews;

import java.awt.Color;
import java.awt.Component;
import java.awt.Event;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JViewport;

import model.ApplicationSetting;
import model.BusinessRules;
import model.ExternalApplication;
import model.ProjectReference;
import model.ReportRecord;
import model.Session2;
import model.TDStructure;
import model.TestDataFormat;
import model.TestObject;
import model.TestObjectReference;

import org.apache.log4j.Logger;

import bi.controller.TDStructureManager;
import bi.view.cells.CMDateCell;
import bi.view.cells.CMFilePathCell;
import bi.view.cells.CMOpenWithCell;
import bi.view.cells.CMReportNameCell;
import bi.view.cells.CMReportOutputView;
import bi.view.cells.CMReportParameterCell;
import bi.view.grids.CMBaseJSmartGrid;
import bi.view.lang.CMMessages;
import bi.view.mainframeviews.CMFrameView;
import bi.view.utils.CMBaseJComboBox;
import bi.view.utils.CMFormatFactory;

import com.eliad.model.GenericGridModel;
import com.eliad.model.GridContext;
import com.eliad.model.defaults.DefaultGridCellEditor;
import com.eliad.model.defaults.DefaultGridCellRenderer;
import com.eliad.model.defaults.DefaultStyleModel;
import com.eliad.swing.GridEditingEvent;
import com.eliad.swing.GridEvent;
import com.eliad.util.RulerConstants;

/**
 * Developed by BUSINESS SOFTWARE INNOVATIONS.
 * Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved.
 */
public class CMGridOutputs extends CMBaseJSmartGrid {
    
	public CMGridOutputs(CMScrollPaneOutput p_CMScrollPaneOutput) {
        m_CMScrollPaneOutput = p_CMScrollPaneOutput;
        m_CMFrameView = p_CMScrollPaneOutput.getM_CMFrameView();
        initGUI();
    }

    public void initGUI() {
        m_Session = m_CMFrameView.getTreeWorkspaceView().getM_Session2();
        m_ApplicationSetting = m_Session.getM_ApplicationSetting();        
        Vector externalApplications = m_ApplicationSetting.getM_ExternalApplications();
        if (externalApplications != null) {
            // if(externalApplications.size()==0)
            //   JOptionPane.showMessageDialog(m_CMFrameView,CMMessages.getString("LABEL_APPLICATION_NOT_FOUND"),CMMessages.getString("TESTDATASETREPORTS_TITLE_INFORMATION"),JOptionPane.INFORMATION_MESSAGE);
            for(int i=0; i<externalApplications.size(); i++)
            {
				ExternalApplication ex=((ExternalApplication)externalApplications.elementAt(i));
				 CMOpenWithCell cellOpenWith= new CMOpenWithCell();
        	   	cellOpenWith.setName(ex.getM_Name());
                jComboBox.addItem(cellOpenWith);
        	}
        }
        else {
             CMOpenWithCell cellOpenWith= new CMOpenWithCell();
        	 cellOpenWith.setName("");
             jComboBox.addItem(cellOpenWith);
        }
//hcanedo_21_09_2004_begin
        m_CMGridModel = new CMGridModel(0, 5);
//hcanedo_21_09_2004_end
        m_CMStyleModel = new CMStyleModel();
        
        m_CMStyleModel.setEditor(CMOpenWithCell.class, new DefaultGridCellEditor(jComboBox));
        setModels();
        setUIProperties();
        addEventListeners();
        setColumnAutoResizeMode(com.eliad.swing.JSmartGrid.AUTO_RESIZE_SUBSEQUENT);
        addKeyListener(new KeyAdapter(){public void keyPressed(KeyEvent e){thisKeyPressed(e);}});
        
        initializeCellEditors();
        initializeCellRenderers();
    }

    public String getNameReport(File p_file)
    {
		StringBuffer name= new StringBuffer();
        File parent= p_file.getParentFile();
        if(parent !=null)
        {
        	name.append(parent.getName());
            name.append(".");
        }
        String filename=p_file.getName();
        int index= filename.lastIndexOf(".");
        if(index >= 0)
        	filename=filename.substring(0,index);
        name.append(filename);
        return name.toString();
    }
    public String getDateModified(long p_time )
    {
        Date d= new Date(p_time);
        TestDataFormat formatter= new TestDataFormat();
        formatter.setRealFormat("dd-MMM-yyyy H:mm:ss");
        String dateMod=CMFormatFactory.formatDate(formatter,d,d.toString());
		return dateMod;
    } 
    
    public void listDirectoryFiles(File dir) {
        File[] fileArray = dir.listFiles();
        try {
            if (fileArray != null) {
                for (int i = 0; i < fileArray.length; i++) {
                    if (fileArray[i].isFile()) {
                        if(!fileArray[i].getPath().endsWith(BusinessRules.TESTDATA_REPORT_EXTENCION))
                        {
                        	ReportRecord record = new ReportRecord();
                        	record.setM_Name(getNameReport(fileArray[i]));
                        	record.setM_Path(fileArray[i].getPath());
                        	record.setM_Parameters("");
                        	record.setM_TimeStamp(getDateModified(fileArray[i].lastModified()));
                        	record.setM_Application((String) openReport.elementAt(1));
                        	record.setM_FileName(fileArray[i].getName());
                        	m_ReportRecords.addElement(record);
                        	/*namesReports.addElement(getNameReport(fileArray[i]));
							pathReports.addElement(fileArray[i].getPath());
//hcanedo_21_09_2004_begin
							parameterReport.addElement("");
//hcanedo_21_09_2004_end
                        	dateReports.addElement(getDateModified(fileArray[i].lastModified()));
                            applications.addElement("1");*/
                        }
                    }
                    else {
                    	if(!fileArray[i].getName().equalsIgnoreCase("images"))
                    		listDirectoryFiles(fileArray[i]);
                    }
                }
            }
        } catch (SecurityException ex) {
        	Logger.getLogger(this.getClass()).error(ex.getMessage());
        }
    }

    public void findNewReportsAndRegister()
    {
      /*  boolean isfirstNewFile=false;*/
        indexNewFile=new Vector();
        Vector registerReportRecords=m_TDstructure.getM_TestDataSetReportUnit().getM_ReportRecords();
  /*      Vector registerNames= m_TDstructure.getM_TestDataSetReportUnit().getReportName();
        Vector appForRegisterNames=m_TDstructure.getM_TestDataSetReportUnit().getApplicationName();
//hcanedo_21_09_2004_begin
        Vector namesReportsImport=m_TDstructure.getM_TestDataSetReportUnit().getNameReportsImport();
        Vector pathReportsImport=m_TDstructure.getM_TestDataSetReportUnit().getPathReportsImport();
        Vector registerParameterReports= m_TDstructure.getM_TestDataSetReportUnit().getParameterReport();*/
//hcanedo_21_09_2004_end
        //assignar a los archivos existentes las aplicaciones de apertura
        for(int i=0; i< m_ReportRecords.size();i++)//namesReports.size(); i++)
        {
            if(registerReportRecords.contains(m_ReportRecords.elementAt(i))) //registerNames.contains(namesReports.elementAt(i)))
            {
            	int indexRegisterReport= registerReportRecords.indexOf(m_ReportRecords.elementAt(i));
            	ReportRecord registerReportRecord=(ReportRecord) registerReportRecords.elementAt(indexRegisterReport);
               // int indexName= registerNames.indexOf(namesReports.elementAt(i));
             //   int index = openReport.indexOf(registerReportRecord.getM_Application());//appForRegisterNames.elementAt(indexName));
            	ReportRecord visualReport=(ReportRecord)m_ReportRecords.elementAt(i);
//HCanedo_29112005_Begin            	
            	if(openReport.contains(registerReportRecord.getM_Application()))
            		visualReport.setM_Application(registerReportRecord.getM_Application());
            	else{
            		visualReport.setM_Application((String) openReport.elementAt(0));
            		registerReportRecord.setM_Application((String) openReport.elementAt(0));
            	}
//HCanedo_29112005_End            	
            	visualReport.setM_Parameters(registerReportRecord.getM_Parameters());
             /*   if(index >=0)
					visualReport.setM_Application(Integer.toString(index));//applications.setElementAt(Integer.toString(index),i);
                else
                	visualReport.setM_Application(Integer.toString(1));//applications.setElementAt(Integer.toString(1),i);*/
//hcanedo_21_09_2004_begin
			/*	try{
				parameterReport.setElementAt(registerParameterReports.elementAt(indexName),i);
                }
                catch(Exception ex)
                {
                    registerParameterReports.addElement("");
                }*/
//hcanedo_21_09_2004_end

            }
        }
        //eliminar reportes borrados o deseparecidos
        for(int j=registerReportRecords.size()-1;j>=0;j--)//registerNames.size()-1; j>=0; j--)
        {
//hcanedo_21_09_2004_begin
            if(!m_ReportRecords.contains(registerReportRecords.elementAt(j)))//namesReports.contains(registerNames.elementAt(j))&& !namesReportsImport.contains(registerNames.elementAt(j)))
//hcanedo_21_09_2004_end
            {
            	registerReportRecords.remove(j);
				/*registerNames.remove(j);
                appForRegisterNames.remove(j);
//hcanedo_21_09_2004_begin
				if(j < registerParameterReports.size())
					registerParameterReports.remove(j);*/
//hcanedo_21_09_2004_end
            }
        }
        //Registar nuevos archivos aparecidos
        for(int k=0; k<m_ReportRecords.size();k++)//namesReports.size(); k++)
        {
            if(!registerReportRecords.contains(m_ReportRecords.elementAt(k)))//registerNames.contains(namesReports.elementAt(k)))
            {
            	registerReportRecords.addElement(m_ReportRecords.elementAt(k));
              /*  registerNames.addElement(namesReports.elementAt(k));
                appForRegisterNames.addElement(openReport.elementAt(1));
//hcanedo_21_09_2004_begin
				registerParameterReports.addElement(parameterReport.elementAt(k));*/
//hcanedo_21_09_2004_end
              /*  if(!isfirstNewFile)
                {*/
                     indexNewFile.addElement(new Integer(k));
                  //  isfirstNewFile=true;
               // }
            }
        }

    }
    public void update()
    {
        m_CMFrameView.setWaitCursor(true);//fcastro_20092004
        isjComboboxActualized=true;
     /*   namesReports= new Vector();
        pathReports= new Vector();
		dateReports= new Vector();
        applications= new Vector();
        parameterReport=new Vector();*/
        openReport= new Vector();
        m_ReportRecords= new Vector();
        jComboBox.removeAllItems();
        Vector externalApplications = m_ApplicationSetting.getM_ExternalApplications();
        if (externalApplications != null) {
            // if(externalApplications.size()==0)
            //   JOptionPane.showMessageDialog(m_CMFrameView,CMMessages.getString("LABEL_APPLICATION_NOT_FOUND"),CMMessages.getString("TESTDATASETREPORTS_TITLE_INFORMATION"),JOptionPane.INFORMATION_MESSAGE);
            for(int i=0; i<externalApplications.size(); i++)
            {
				ExternalApplication ex=((ExternalApplication)externalApplications.elementAt(i));
				 CMOpenWithCell cellOpenWith= new CMOpenWithCell();
        	   	cellOpenWith.setName(ex.getM_Name());
                openReport.addElement(ex.getM_Name());
                jComboBox.addItem(cellOpenWith);
        	}
        }
        else {
             CMOpenWithCell cellOpenWith= new CMOpenWithCell();
        	 cellOpenWith.setName("");
             jComboBox.addItem(cellOpenWith);
        }
        deleteAllViews();
        //grueda15092004_begin

         //TDStructureReference obj=(TDStructureReference)m_CMFrameView.getGridTDStructure().getTDStructure().getM_TestObject().getM_TDSTructureReference().firstElement();
        m_TDstructure=(TDStructure)m_CMFrameView.getGridTDStructure().getTDStructure();
        //StringBuffer path=new StringBuffer(obj.getM_Path());       
   
        String path= findAbsoluteTestDataReportsPath();
        //path.append(BusinessRules.URL_SEPARATOR);
        //path.append(BusinessRules.TESTDATA_REPORT_CARPET_NAME);
       	File p=new File(path);
        //grueda15092004_end
        listDirectoryFiles(p);
//hcanedo_21_09_2004_begin
		listImportFiles();
//hcanedo_21_09_2004_end
        findNewReportsAndRegister();
        for(int i=0; i<m_ReportRecords.size()/*namesReports.size()*/;i++)
        {
           	CMReportOutputView view=createCMReportOutputGroup(i);
            addCMReportView(view);
        }
        this.clearSelection();
       // Vector aux=m_TDstructure.getM_TestDataSetReportUnit().getM_ReportRecords();//getReportName();
      //  int indexSelection=aux.size()-1;
        if(indexNewFile.size() > 0)
        {
      //      int rowtoSelected= Integer.parseInt(indexNewFile.elementAt(0).toString());
        //   	this.changeSelection(rowtoSelected,0,false,false);
            for(int f=0; f<indexNewFile.size(); f++)
            {
                 int rowtoSelected= Integer.parseInt(indexNewFile.elementAt(f).toString());
            	this.changeSelection(rowtoSelected,0,true,false);

            }
            m_CMFrameView.statesMenusTestDataSetReports(true);
        //	this.getSelectionModel().addSelectionRange(indexFirstNewFile,0,indexSelection,3);
        }
        else if(m_ReportRecords/*namesReports*/.size()>0){
            	this.changeSelection(0,0,false,false);
                m_CMFrameView.statesMenusTestDataSetReports(true);
        }
        else{
			m_CMFrameView.statesMenusTestDataSetReports(false);
        }
        isjComboboxActualized=false;
        m_CMFrameView.setWaitCursor(false);//fcastro_20092004

    }
	public void addCMReportView(CMReportOutputView p_CMReportGroup)
    {
  		this.m_CMGridModel.addRow(p_CMReportGroup);
  	/*	int newSelectionIndex = CMIndexTDStructureUpdate.getInstance().getindexTestDataSet();//this.getRowCount()-1;
		selectCMTestDataSetView(newSelectionIndex);*/
	}
	void deleteAllViews()
   	{
     	int numOfRows = m_CMGridModel.getRowCount();
     	m_CMGridModel.removeRows(0,numOfRows);
 	}
	public CMReportOutputView createCMReportOutputGroup(int index)
    {
		CMReportOutputView reportGroup = new CMReportOutputView();

        CMReportNameCell cellName= new CMReportNameCell();
        cellName.setValue(((ReportRecord)m_ReportRecords/*namesReports*/.elementAt(index)).getM_Name()); 
        cellName.setM_CMReportOutputView(reportGroup);

        CMFilePathCell cellfilepath = new  CMFilePathCell(this,((ReportRecord)m_ReportRecords.elementAt(index)));
        cellfilepath.setModel((((ReportRecord)m_ReportRecords.elementAt(index))));//(pathReports.elementAt(index).toString());
        cellfilepath.setM_CMReportOutputView(reportGroup);       
               
        CMDateCell cellDate= new CMDateCell();
        cellDate.setValue((((ReportRecord)m_ReportRecords.elementAt(index)).getM_TimeStamp()));//(dateReports.elementAt(index).toString());
        cellDate.setM_CMReportOutputView(reportGroup);
//hcanedo_21_09_2004_begin
		CMReportParameterCell cellParameter= new CMReportParameterCell();
        cellParameter.setValue(((ReportRecord)m_ReportRecords.elementAt(index)).getM_Parameters());//(parameterReport.elementAt(index).toString());
        cellParameter.setM_CMReportOutputView(reportGroup);
//hcanedo_21_09_2004_end
        
        CMOpenWithCell cellOpenWith= (CMOpenWithCell)jComboBox.getItemAt(openReport.indexOf((((ReportRecord)m_ReportRecords.elementAt(index)).getM_Application())));//Integer.parseInt(applications.elementAt(index).toString()));
//HCanedo_29112005_Begin        
        if(cellOpenWith == null){
        	cellOpenWith=(CMOpenWithCell)jComboBox.getItemAt(0);
        }
//HCanedo_29112005_End
        cellOpenWith.setM_CMReportOutputView(reportGroup);
        reportGroup.setM_CMReportNameCell(cellName);
        reportGroup.setM_CMFilePathCell(cellfilepath);
        reportGroup.setM_CMDateCell(cellDate);
//hcanedo_21_09_2004_begin
		reportGroup.setM_CMReportParameterCell(cellParameter);
//hcanedo_21_09_2004_end
        reportGroup.setM_CMOpenWithCell(cellOpenWith);
        reportGroup.addElement(cellName);
        reportGroup.addElement(cellfilepath);
        reportGroup.addElement(cellDate);
//hcanedo_21_09_2004_begin
        reportGroup.addElement(cellParameter);
//hcanedo_21_09_2004_end
        reportGroup.addElement(cellOpenWith);
        return reportGroup;
    }

    public CMScrollPaneOutput getM_CMScrollPaneOutput() { return m_CMScrollPaneOutput; }

    public void setM_CMScrollPaneOutput(CMScrollPaneOutput m_CMScrollPaneOutput) {
        this.m_CMScrollPaneOutput = m_CMScrollPaneOutput;
    }

    public JViewport getViewport1() {
        return getViewport(); // getViewport is not public in JSmartGrid
    }

    public void setModels() {
        this.setStyleModel(m_CMStyleModel);
        this.setModel(m_CMGridModel);
    }

    public void setUIProperties() {
        this.setOpaque(false);
        this.setColumnResizable(true);
        this.setAutoResizeMode(RulerConstants.HORIZONTAL);
        this.setGridColor(new Color(127, 157, 185)); // Read Only Grid Color
        this.setSelectionCellBorder(BorderFactory.createLineBorder(Color.orange, 2));
        this.setFocusHighlightBorder(BorderFactory.createLineBorder(Color.orange, 2));
        this.setSelectionBackgroundColor(Color.orange);
        this.setSelectionForegroundColor(Color.black);
        this.setSelectionUnit(com.eliad.swing.JSmartGrid.UNIT_ROW);
        this.setSelectionPolicy(com.eliad.swing.JSmartGrid.POLICY_RANGE);            
        ////////////////////////////////////////////////////////
    }

    public void addEventListeners() {
        this.addGridListener(
            new com.eliad.swing.GridAdapter() {
                public void gridMouseClicked(GridEvent e) {
                    eventGridMouseClicked(e);
                }
            });
            this.addGridEditingListener(
            new com.eliad.swing.GridEditingListener() {
                public void editingStarted(GridEditingEvent e) {
                    eventEditingStarted(e);
                }
                public void editingStopped(GridEditingEvent e) {
                    eventEditingStopped(e);
                }
                public void editingCanceled(GridEditingEvent e) {
                    eventEditingCanceled(e);
                }
            });
           
    }

    void eventGridMouseClicked(GridEvent e) {
        MouseEvent mouseEvent = (MouseEvent)e.getSourceEvent();
        int row = e.getRow();
        int column = e.getColumn();
        if (mouseEvent.getClickCount() == 2)
        	openFiles();
        else {
            if (e.getSourceEvent().getModifiers() == Event.META_MASK) {
                if (row >= 0 && column >= 0) {
                    this.changeSelection(row, column, false, false);
                }
                m_CMFrameView.getJPopupMenuTestDataSetReports().show(this, mouseEvent.getX(), mouseEvent.getY());
            }
        }
    }

 public void eventEditingStarted(GridEditingEvent e) {
        int row = e.getRow();
        int column = e.getColumn();
        editingObject = m_CMGridModel.getCellObjectAt(row, column);
    }

    public void eventEditingStopped(GridEditingEvent e) {
        int row = e.getRow();
      //  int column = e.getColumn();
        if (editingObject == null) {
            return;
        }

   	if (editingObject instanceof CMOpenWithCell && !isjComboboxActualized) {

      //      int index= jComboBox.getSelectedIndex();
            ReportRecord visualRecord=(ReportRecord) m_ReportRecords.elementAt(row);
            CMOpenWithCell openwith=(CMOpenWithCell)jComboBox.getSelectedItem();
            String appname=openwith.getName();
            visualRecord.setM_Application(appname);
            //applications.setElementAt(Integer.toString(index),row);
           /* Vector app=m_TDstructure.getM_TestDataSetReportUnit().getApplicationName();
            Vector names=m_TDstructure.getM_TestDataSetReportUnit().getReportName();*/
            /*CMOpenWithCell openwith=(CMOpenWithCell)jComboBox.getSelectedItem();
            String appname=openwith.getName();*/
            /*String nameRow=namesReports.elementAt(row).toString();
            int indexAppRow=names.indexOf(nameRow);
            app.setElementAt(appname,indexAppRow);*/
            Vector registerReportRecords=m_TDstructure.getM_TestDataSetReportUnit().getM_ReportRecords();
            if(registerReportRecords.contains(visualRecord)){
            	int indexRegister=registerReportRecords.indexOf(visualRecord);
            	ReportRecord registerReport= (ReportRecord) registerReportRecords.elementAt(indexRegister);
            	registerReport.setM_Application(appname);
            }
   /* }
    catch(Exception ex)
    {
        	int index=1;
            applications.setElementAt(Integer.toString(index),row);
            Vector app=m_TDstructure.getM_TestDataSetReportUnit().getApplicationName();
            Vector names=m_TDstructure.getM_TestDataSetReportUnit().getReportName();
//            CMOpenWithCell openwith=(CMOpenWithCell)jComboBox.getItemAt(1);
            String appname=BusinessRules.CHART_APPLICATION_DENOMINATIVE;
            String nameRow=namesReports.elementAt(row).toString();
            int indexAppRow=names.indexOf(nameRow);
            app.setElementAt(appname,indexAppRow);
    }98*/
   		}
    }

    public void eventEditingCanceled(GridEditingEvent e) { }

	public void updateJComboBoxApplications()
    {
		isjComboboxActualized=true;
        this.changeSelection(0,0,false,false);
         openReport= new Vector();
        jComboBox.removeAllItems();
        Vector externalApplications = m_ApplicationSetting.getM_ExternalApplications();
        if (externalApplications != null) {
            // if(externalApplications.size()==0)
            //   JOptionPane.showMessageDialog(m_CMFrameView,CMMessages.getString("LABEL_APPLICATION_NOT_FOUND"),CMMessages.getString("TESTDATASETREPORTS_TITLE_INFORMATION"),JOptionPane.INFORMATION_MESSAGE);
            for(int i=0; i<externalApplications.size(); i++)
            {
				ExternalApplication ex=((ExternalApplication)externalApplications.elementAt(i));
				 CMOpenWithCell cellOpenWith= new CMOpenWithCell();
        	   	cellOpenWith.setName(ex.getM_Name());
                openReport.addElement(ex.getM_Name());
                jComboBox.addItem(cellOpenWith);
        	}
        }
        else{
             CMOpenWithCell cellOpenWith= new CMOpenWithCell();
        	 cellOpenWith.setName("");
             jComboBox.addItem(cellOpenWith);
        }
        try{
    //     Vector registerNames= m_TDstructure.getM_TestDataSetReportUnit().getReportName();
     //   Vector appForRegisterNames=m_TDstructure.getM_TestDataSetReportUnit().getApplicationName();
        	Vector registerReportRecord=m_TDstructure.getM_TestDataSetReportUnit().getM_ReportRecords();
        //assignar a los archivos existentes las aplicaciones de apertura
        for(int i=0; i< registerReportRecord.size();i++)//appForRegisterNames.size(); i++)
        {
        	ReportRecord registerReport=(ReportRecord)registerReportRecord.elementAt(i);
            if(!openReport.contains(registerReport.getM_Application()))
            {
            	registerReport.setM_Application((String) openReport.elementAt(1));
                //appForRegisterNames.setElementAt(openReport.elementAt(1),i);
            	int index=m_ReportRecords.indexOf(registerReport);
            	ReportRecord visualReport=(ReportRecord) m_ReportRecords.elementAt(index);
            	visualReport.setM_Application((String) openReport.elementAt(1));
                //applications.setElementAt("1",i);
             /*   CMOpenWithCell newCell= new CMOpenWithCell();
                newCell.setName(BusinessRules.CHART_APPLICATION_DENOMINATIVE);
                this.setValueAt(newCell,i,3);*/
            }
        }

		update();
        }
        catch(Exception ex)
        {
            isjComboboxActualized=false;
        }
         isjComboboxActualized=false;

    }

	public boolean deleteReportSystem()
    {
        try{
            boolean deleteSuccess=false;
        int[] forOpenFiles=this.getSelectedRows();
//hcanedo_21_09_2004_begin
		int firstIndexDeleted= forOpenFiles[0];
//hcanedo_21_09_2004_end
        for(int i=0; i< forOpenFiles.length; i++)
        {

            String path=((ReportRecord)m_ReportRecords.elementAt(forOpenFiles[i])).getM_Path();//pathReports.elementAt(forOpenFiles[i]).toString();
            ReportRecord visualReport=(ReportRecord)m_ReportRecords.elementAt(forOpenFiles[i]);
//hcanedo_21_09_2004_begin
			//String nameReport= namesReports.elementAt(forOpenFiles[i]).toString();
            /*File auxfile= new File(path);
            Vector fileArray = m_TDstructure.getM_TestDataSetReportUnit().getNameReportsImport();
        	Vector pathArray=m_TDstructure.getM_TestDataSetReportUnit().getPathReportsImport();*/
            Vector importRecords=m_TDstructure.getM_TestDataSetReportUnit().getM_ImportReportRecords();
            if(importRecords.contains(visualReport)){//pathArray.contains(path)

                int index = importRecords.indexOf(visualReport);//pathArray.indexOf(path);
                /*deleteSuccess=*/ importRecords.remove(index);//fileArray.remove(fileArray.elementAt(index));
                //pathArray.remove(index);
                deleteSuccess=true;
            }
            else{
//hcanedo_21_09_2004_end
            File deleteReport = new File(path);
            deleteSuccess=deleteReport.delete();

            }
            if(!deleteSuccess)
            {
                JOptionPane.showMessageDialog(m_CMFrameView,CMMessages.getString("TESTDATASETREPORTS_MENSSAGE_CANNOT_DELETE_REPORT"),CMMessages.getString("TESTDATASETREPORTS_TITLE_INFORMATION"),JOptionPane.INFORMATION_MESSAGE);
            }
//hcanedo_21_09_2004_begin
        }
//hcanedo_21_09_2004_end
        update();
//hcanedo_21_09_2004_begin
		int cantOfRows=this.getRowCount();
        if(firstIndexDeleted ==1)
        {
            if(firstIndexDeleted<cantOfRows)
            {
            	this.changeSelection(firstIndexDeleted,0,false,false);
            }
        }
        else{
            if(firstIndexDeleted<=cantOfRows)
            {
                this.changeSelection(firstIndexDeleted-1,0,false,false);
            }
        }
        return deleteSuccess;
//hcanedo_21_09_2004_end
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
			JOptionPane.showMessageDialog(m_CMFrameView,CMMessages.getString("TESTDATASETREPORTS_MENSSAGE_CANNOT_DELETE_REPORT"),CMMessages.getString("TESTDATASETREPORTS_TITLE_INFORMATION"),JOptionPane.INFORMATION_MESSAGE);
            return false;
        }
    }

    public void rename()
    {
        int[] changeNAmeFile=this.getSelectedRows();
        if(changeNAmeFile.length >1)
        {
            JOptionPane.showMessageDialog(m_CMFrameView,CMMessages.getString("TESTDATASETREPORTS_MENSSAGE_SELECT_ONLY_ONE"),CMMessages.getString("TESTDATASETREPORTS_TITLE_INFORMATION"),JOptionPane.INFORMATION_MESSAGE);
        }
        else
        {
        	ReportRecord visualReport=(ReportRecord) m_ReportRecords.elementAt(changeNAmeFile[0]);
            String app=visualReport.getM_Application();//applications.elementAt(changeNAmeFile[0]).toString();
			String path=visualReport.getM_Path();//pathReports.elementAt(changeNAmeFile[0]).toString();
//hcanedo_21_09_2004_begin
			boolean swImport=false;
			if(m_TDstructure.getM_TestDataSetReportUnit().getM_ImportReportRecords().contains(visualReport)){//getPathReportsImport().contains(path)){
//				JOptionPane.showMessageDialog(m_CMFrameView,CMMessages.getString("TESTDATASETREPORTS_MENSSAGE_EDIT_IMPORT_REPORTS"),CMMessages.getString("TESTDATASETREPORTS_TITLE_INFORMATION"),JOptionPane.INFORMATION_MESSAGE);
				swImport=true;
            }
  /*          else
            {*/
                String param=visualReport.getM_Parameters();//parameterReport.elementAt(changeNAmeFile[0]).toString();
                String date=visualReport.getM_TimeStamp();//dateReports.elementAt(changeNAmeFile[0]).toString();
                

			CMDialogRenameReport cmd= new CMDialogRenameReport(m_CMFrameView,path,param, date, jComboBox.getModel(), swImport);
			cmd.setSelected(openReport.indexOf(app));
//hcanedo_21_09_2004_end
            cmd.show();
            visualReport.setM_Application((String) openReport.elementAt(cmd.getSelected()));
           // applications.setElementAt(Integer.toString(cmd.getSelected()), changeNAmeFile[0]);
            jComboBox.setSelectedIndex(cmd.getSelected());
            app=visualReport.getM_Application();//applications.elementAt(changeNAmeFile[0]).toString();            
            if (cmd.isIsOKSelected()){
            	update();
                //String nameRow=cmd.getNameReportModified();
				int index=m_ReportRecords.indexOf(visualReport);// namesReports.indexOf(cmd.getNameReportModified());;
                //applications.setElementAt(app,index);
               /* Vector appRegister=m_TDstructure.getM_TestDataSetReportUnit().getApplicationName();
            	Vector namesRegister=m_TDstructure.getM_TestDataSetReportUnit().getReportName();*/
                CMOpenWithCell openwith=(CMOpenWithCell)jComboBox.getItemAt(openReport.indexOf(app));
				String appname=openwith.getName();
				ReportRecord reportRecord= null;
				if(swImport){
					int indexImport=m_TDstructure.getM_TestDataSetReportUnit().getM_ImportReportRecords().indexOf(visualReport);
					 reportRecord=(ReportRecord)m_TDstructure.getM_TestDataSetReportUnit().getM_ImportReportRecords().elementAt(indexImport);
				}
				else{
					if(m_TDstructure.getM_TestDataSetReportUnit().getM_ReportRecords().contains(visualReport)){
						int indexImport=m_TDstructure.getM_TestDataSetReportUnit().getM_ReportRecords().indexOf(visualReport);
						 reportRecord=(ReportRecord)m_TDstructure.getM_TestDataSetReportUnit().getM_ReportRecords().elementAt(indexImport);
					}

				}
				reportRecord.setM_Application(appname);
				reportRecord.setM_Parameters(cmd.getM_Parameter());
				visualReport.setM_Parameters(cmd.getM_Parameter());
               /* int indexRegister=namesRegister.indexOf(nameRow);
                appRegister.setElementAt(appname,indexRegister);*/
//hcanedo_21_09_2004_begin
         	/*	Vector parameterRegister=m_TDstructure.getM_TestDataSetReportUnit().getParameterReport();
                parameterRegister.setElementAt(cmd.getM_Parameter(),indexRegister);
                parameterReport.setElementAt(cmd.getM_Parameter(),index);*/
//hcanedo_21_09_2004_end
				update();
				jComboBox.setSelectedIndex(cmd.getSelected());
				
                this.changeSelection(index,0,false,false);
                jComboBox.repaint();
            }
//hcanedo_21_09_2004_begin
         //   }
//hcanedo_21_09_2004_end
        }
    }
    //////////////////////// Grid Model /////////////////////////////////////////
    public class CMGridModel extends GenericGridModel {
        public CMGridModel(int numRows, int numColumns) {
            super(numRows, numColumns);
        }

        public boolean isCellEditable(int row, int column) {
            Object obj = super.getValueAt(row, column);
            if (obj == null) {
                return false;
            }
            if(obj instanceof CMOpenWithCell || obj instanceof CMFilePathCell)
                return true;
            else
                return false;
        }

        
        public Object getCellObjectAt(int row, int column) {
            if (row >= 0 && column >= 0) {
                Object o=super.getValueAt(row, column);
                return o;
            }
            else {
                return null;
            }
        }
        
        public Object getValueAt(int row, int column) {
            Object obj = null;
            if (row >= 0 && column >= 0) {
                obj = super.getValueAt(row, column);
            }
            else {
                return null;
            }
            if (obj == null) { return null; }
            if (obj instanceof CMOpenWithCell) {
              //  CMOpenWithCell openWith = (CMOpenWithCell)obj;
                return jComboBox.getItemAt(openReport.indexOf(((ReportRecord)m_ReportRecords.elementAt(row)).getM_Application()));//Integer.parseInt(applications.elementAt(row).toString()));
            }
            else if (obj instanceof CMReportNameCell) {
                CMReportNameCell reportName = (CMReportNameCell)obj;
                return reportName.getValue();
            }
            else if (obj instanceof CMDateCell) {
                CMDateCell dateReport = (CMDateCell)obj;
                return dateReport.getValue();
            }
//hcanedo_21_09_2004_begin
            else if (obj instanceof CMReportParameterCell) {
                CMReportParameterCell parameterReport = (CMReportParameterCell)obj;
                return parameterReport.getValue();
            }
//hcanedo_21_09_2004_end
            else {
                return super.getValueAt(row, column);
            }
        }

        public void setValueAt(Object obj,int row, int column) {
            if (obj != null)
           /* if (obj instanceof CMOpenWithCell) {
                CMOpenWithCell openWith = (CMOpenWithCell)obj;
                openWith.setName(jComboBox.getSelectedItem().toString());
            }
            else*/ if (obj instanceof CMReportNameCell) {
                CMReportNameCell reportName = (CMReportNameCell)obj;
                reportName.setValue(reportName.getValue());
            }           
            else if (obj instanceof CMDateCell) {
                CMDateCell dateReport = (CMDateCell)obj;
                dateReport.setValue(dateReport.getValue());
            }
//hcanedo_21_09_2004_begin
            else if (obj instanceof CMReportParameterCell) {
                CMReportParameterCell parameterReport = (CMReportParameterCell)obj;
                parameterReport.setValue(parameterReport.getValue());
            }
//hcanedo_21_09_2004_end
            super.setValueAt(obj, row, column);

        }

    }


    /////////////////////////////Style Model /////////////////////////////////////
    public class CMStyleModel extends DefaultStyleModel {
        public CMStyleModel() {
            this.setRenderer(String.class, new CMCellRendererDependencies());
        }

        public class CMCellRendererDependencies extends DefaultGridCellRenderer {
            public CMCellRendererDependencies() {
            }

            public Component getComponent(Object value, boolean isSelected, boolean hasFocus, boolean isEditable, int row,
                int column, GridContext context) {
                    Component c = super.getComponent(value, isSelected, hasFocus, isEditable, row, column, context);
                    this.setHorizontalAlignment(JLabel.LEFT);
                    if (!isSelected) {
                        this.setBackground(new Color(235, 235, 228));
                    }
                    return c;
            }
        }
    }


    public CMGridModel getM_CMGridModel() { return m_CMGridModel; }

    public void setM_CMGridModel(CMGridModel m_CMGridModel) { this.m_CMGridModel = m_CMGridModel; }

    public CMStyleModel getM_CMStyleModel() { return m_CMStyleModel; }

    public void setM_CMStyleModel(CMStyleModel m_CMStyleModel) { this.m_CMStyleModel = m_CMStyleModel; }

    public ApplicationSetting getM_ApplicationSetting() { return m_ApplicationSetting; }

    public void setM_ApplicationSetting(ApplicationSetting m_ApplicationSetting) {
        this.m_ApplicationSetting = m_ApplicationSetting;
    }

    public CMFrameView getM_CMFrameView() { return m_CMFrameView; }

    public void setM_CMFrameView(CMFrameView m_CMFrameView) { this.m_CMFrameView = m_CMFrameView; }

    public Session2 getM_Session() { return m_Session; }

    public void setM_Session(Session2 m_Session) { this.m_Session = m_Session; }

	public void openFiles()
    {
		File app= new File("");
        try{
        int[] forOpenFiles=this.getSelectedRows();
        for(int i=0; i< forOpenFiles.length; i++)
        {
            TDStructureManager my_TDSManager=CMIndexTDStructureUpdate.getInstance().getTDStructureManager();
            ReportRecord visual=(ReportRecord)m_ReportRecords.elementAt(forOpenFiles[i]);
            String path='"'+visual.getM_Path()/*pathReports.elementAt(forOpenFiles[i]).toString()*/+'"';
            /*Object obj=applications.elementAt(forOpenFiles[i]);
            int indexApp=Integer.parseInt(obj.toString());*/
			ExternalApplication ex=(ExternalApplication)m_ApplicationSetting.getM_ExternalApplications().elementAt(openReport.indexOf(visual.getM_Application()));
			app= new File(ex.getM_FilePath());
//hcanedo_21_09_2004_begin
			String viewer;
			if(visual.getM_Parameters()/*parameterReport.elementAt(forOpenFiles[i])*/!= null){
            	 viewer=ex.getM_FilePath()+" "+ex.getM_Param()+" "+visual.getM_Parameters()/*parameterReport.elementAt(forOpenFiles[i]).toString()*/;
            }
            else{
//hcanedo_21_09_2004_end
				 viewer=ex.getM_FilePath()+" "+ex.getM_Param();
            }
            my_TDSManager.viewFile2(viewer,path,m_CMFrameView);
        }
        }
        catch(Exception ex)
        {
			JOptionPane.showMessageDialog(m_CMFrameView,app.getName()+" "+CMMessages.getString("LABEL_APPLICATION_NOT_FOUND"),CMMessages.getString("TESTDATASETREPORTS_TITLE_INFORMATION"),JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public void thisKeyPressed(KeyEvent e) {    	
       	if(e.getKeyCode()==KeyEvent.VK_DELETE)
            {
                deleteReportSystem();
            }
            if(e.getKeyCode()==KeyEvent.VK_ENTER)
            {
            	openFiles();
            }
      
    }
//Hcanedo_13_09_2004_Begin
	public void addReport()
    {
        //grueda20092004_begin
         m_TDstructure=(TDStructure)m_CMFrameView.getGridTDStructure().getTDStructure();
        /*
         TDStructureReference obj=(TDStructureReference)m_CMFrameView.getGridTDStructure().getTDStructure().getM_TestObject().getM_TDSTructureReference().firstElement();
         StringBuffer path=new StringBuffer(obj.getM_Path());
         path.append(BusinessRules.URL_SEPARATOR);
         path.append(BusinessRules.TESTDATA_REPORT_CARPET_NAME);
        */
        String path = this.findAbsoluteTestDataReportsPath();
        CMDialogAddReportTestDataSetReports cmd= new CMDialogAddReportTestDataSetReports(m_CMFrameView,path);
        //grueda20092004_end
        cmd.show();
//hcanedo_21_09_2004_begin
        if(cmd.isOKSelected){
        	ReportRecord importReport= new ReportRecord();
        	importReport.setM_Application((String) openReport.elementAt(0));
        	importReport.setM_Name(cmd.nameReportImport);
        	importReport.setM_Path(cmd.pathReportImport);
        	File aux= new File(cmd.pathReportImport);
        	importReport.setM_FileName(aux.getName());
        	importReport.setM_TimeStamp(getDateModified(aux.lastModified()));
        	importReport.setM_Parameters("");
        	m_TDstructure.getM_TestDataSetReportUnit().getM_ImportReportRecords().addElement(importReport);
	/*		m_TDstructure.getM_TestDataSetReportUnit().getNameReportsImport().addElement(cmd.nameReportImport);
            m_TDstructure.getM_TestDataSetReportUnit().getPathReportsImport().addElement(cmd.pathReportImport);
        */    update();
        }
//hcanedo_21_09_2004_end
    }
//Hcanedo_13_09_2004_End

    //grueda20092004_begin
    String findAbsoluteTestDataReportsPath() {
      ProjectReference projectReference = m_CMFrameView.getTreeWorkspaceView().getCurrentProjectReference();
      TestObjectReference testObjectReference = m_CMFrameView.getTreeWorkspaceView().getCurrentTestObjectReference();
      TestObject testObject=m_CMFrameView.getTreeWorkspaceView().getCurrentTestObject();
      //grueda06112004_begin
	  if( m_CMFrameView.getCmApplication().getSessionManager().getM_WorkspaceManager().isTestObjectCheckedOutLocalByTheSameUser(testObject, m_Session) ) {
	    projectReference = projectReference.getM_LocalProjectReference();
	  }
      //grueda06112004_end
      String absoluteTestDataReportsPath = m_CMFrameView.getCmApplication().getSessionManager().getM_WorkspaceManager().buildAbsoluteTestDataReportsPath(projectReference, testObjectReference);
      return absoluteTestDataReportsPath;
    }
    //grueda20092004_end

//hcanedo_21_09_2004_begin
    public void listImportFiles() {
    	Vector importReports=m_TDstructure.getM_TestDataSetReportUnit().getM_ImportReportRecords();
      /*  Vector fileArray = m_TDstructure.getM_TestDataSetReportUnit().getNameReportsImport();
        Vector pathArray=m_TDstructure.getM_TestDataSetReportUnit().getPathReportsImport();*/
        try {
            if (importReports/*fileArray*/ != null) {
                for (int i = 0; i < importReports/*fileArray*/.size(); i++) {
                			ReportRecord importRecord=(ReportRecord) importReports.elementAt(i);
                    		//File auxfile= new File(importRecord.getM_Path());//pathArray.elementAt(i).toString());
                    		ReportRecord visualRecord=new ReportRecord();
                    		visualRecord= importRecord;
                            /*namesReports.addElement(getNameReport(auxfile));
							pathReports.addElement(pathArray.elementAt(i).toString());
//hcanedo_21_09_2004_begin
							parameterReport.addElement("");
//hcanedo_21_09_2004_end
                        	dateReports.addElement(getDateModified(auxfile.lastModified()));
                            applications.addElement("1");*/
                    		m_ReportRecords.addElement(visualRecord);
                }
            }
        } catch (SecurityException ex) {
        	Logger.getLogger(this.getClass()).error(ex.getMessage());
        }
    }
//hcanedo_21_09_2004_end
    
    //ccastedo begins 31.08.06   
	public Vector getM_ReportRecords() {
		return m_ReportRecords;
	}
	
	protected HashMap getCellClasses() {
    	HashMap map = new HashMap();
    	map.put(CMFilePathCell.class,new JTextField());    	
    	return map;
    }
	//	ccastedo ends 31.08.06
	
    private CMGridModel m_CMGridModel;
    private CMStyleModel m_CMStyleModel;
    private CMScrollPaneOutput m_CMScrollPaneOutput;
    private CMBaseJComboBox jComboBox= new CMBaseJComboBox(this);
    private ApplicationSetting m_ApplicationSetting;
    private CMFrameView m_CMFrameView;
    private Session2 m_Session;
    /*private Vector namesReports;
    private Vector pathReports;
    private Vector dateReports;
    
    private Vector parameterReport;
    private Vector applications= new Vector();*/
    private Vector openReport;
    private Object editingObject;
    private TDStructure m_TDstructure;
    private Vector indexNewFile= new Vector();
    private boolean isjComboboxActualized=false;
    private Vector m_ReportRecords=new Vector(); 
//hcanedo_21_09_2004_begin
    

//hcanedo_21_09_2004_end
    
}
