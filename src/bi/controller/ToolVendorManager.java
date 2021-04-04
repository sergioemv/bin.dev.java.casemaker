
package  bi.controller;

import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import model.ApplicationSetting;
import model.BusinessRules;
import model.ObjectTypes;
import model.Technology;
import model.ToolVendor;
import bi.controller.utils.CMBaseObjectReader;
import bi.view.preferences.toolvendors.CMToolVendorGridView;

//import JSX.writer.XMLWriter;

public class ToolVendorManager {

public static final ToolVendorManager INSTANCE = new ToolVendorManager();
	//	public static final ToolVendorManager INSTANCE = new ToolVendorManager();
    private ToolVendorManager() {
      ids = new Vector(BusinessRules.MAX_NUMBER_OF_IDS);
      for( int i = 0; i < BusinessRules.MAX_NUMBER_OF_IDS; i++) {
        ids.addElement(new Boolean(false));
      }

    }


       public boolean  existToolVendor(String toolVendor){
    	   boolean sw = false;
    	   Vector m_ToolVendors = new Vector(0);
    		 m_ToolVendors.addAll(ApplicationSettingManager.INSTANCE.getApplicationSetting().getM_ToolVendors());

    	   int numofToolVendors = m_ToolVendors.size();
    	   ToolVendor m_ToolVendorMatch;
  	    	for (int i=0; i < numofToolVendors; i++){
  	    		m_ToolVendorMatch = (ToolVendor)(m_ToolVendors.elementAt(i));
  		   		String cbItemToolVendor =  m_ToolVendorMatch.getM_Name();
  	    		if (cbItemToolVendor.equals(toolVendor)){
  	    			sw = true;
  	    			break;
  	    		}
  	    	}


   	    	return sw;
       }

       public ToolVendor  findToolVendorByName(String toolVendor){
    	   if (m_ToolVendor!=null)
    	   if (m_ToolVendor.getM_Name().equalsIgnoreCase(toolVendor))
    		   return m_ToolVendor;
    	   Vector<ToolVendor> m_ToolVendors = new Vector<ToolVendor>();
    	   m_ToolVendors .addAll(ApplicationSettingManager.INSTANCE.getApplicationSetting().getM_ToolVendors());
    	   int numofToolVendors = m_ToolVendors.size();
    	   ToolVendor m_ToolVendorMatch;
  	    	for (int i=0; i < numofToolVendors; i++){
  	    		m_ToolVendorMatch = (ToolVendor)(m_ToolVendors.elementAt(i));
  		   		String cbItemToolVendor =  m_ToolVendorMatch.getM_Name();
  	    		if (cbItemToolVendor.equalsIgnoreCase(toolVendor)){
  	    			String path=BusinessRules.RESOURCE_FOLDER+BusinessRules.URL_SEPARATOR+m_ToolVendorMatch.getM_FilePath();
  	    			m_ToolVendor = readToolVendorFromFile(path);
  	    			break;
  	    		}
  	    	}


   	    	return m_ToolVendor;
       }

       public Technology  findTechnologyByName(String toolVendor,String technology){
    	            m_ToolVendor = findToolVendorByName(toolVendor);
    	            if (m_ToolVendor == null)
    	            	return null;
    	            if (m_Technology!=null)
    	            	if (m_Technology.getM_Name().equalsIgnoreCase(technology))
    	            		return m_Technology;
  	    			int sizeTechnology = m_ToolVendor.getM_Technologies().size();

  	     	    	for (int j=0; j < sizeTechnology; j++){
  	     	    		m_Technology = (Technology)(m_ToolVendor.getM_Technologies().elementAt(j));
  	     		   		String cbItemTechnology =  m_Technology.getM_Name();
  	     	    		if (cbItemTechnology.equalsIgnoreCase(technology)){
  	     	    		 			break;
  	     	    		}
  	     	    	}

   	    	return m_Technology;
       }




    public ToolVendor readToolVendorFromFile(String p_Filename){
    Object obj = null;
    try {
    	//Ccastedo begins 25-01-06
        CMBaseObjectReader in = new CMBaseObjectReader(new FileReader(p_Filename),p_Filename);
      //  if (in.isvalidFile())
      	  obj = in.readObject();
         if( obj == null) {
         	    in.close();
         	    return null;
           }
         //Ccastedo ends 25-01-06

//      JSX.ObjectReader in = new JSX.ObjectReader(new FileReader(p_Filename));
//		 obj = in.readObject();
      in.close();
    } catch(Exception e1) {
      return null;
    }
    return (ToolVendor)obj;

}

    public void setM_Technologies(Vector m_Technologies){
        this.m_Technologies = m_Technologies;
    }

    public Vector getM_Technologies(){
        return m_Technologies;
    }





     public List strictDiff(List after, List before) {
         List diff = new ArrayList();
         int numOfAfter = after.size();
         int numOfBefore = before.size();
         int equals = 0;
         for (int i = 0; i < numOfAfter; i++) {
             ToolVendor ToolVendor = (ToolVendor) after.get(i);
             if (before.contains(ToolVendor)) {
                 equals++;
             } else {
                 diff.add(ToolVendor);
             }
         }

         return equals == before.size()? diff : null;
     }

     public List relaxedDiff(List first, List second) {
         List diff = new ArrayList();
         int numOfFirst = first.size();
         int numOfSecond = second.size();
         int equals = 0;
         for (int i = 0; i < numOfFirst; i++) {
        	 ToolVendor ToolVendor = (ToolVendor) first.get(i);
             if (!second.contains(ToolVendor)) {
                 diff.add(ToolVendor);
             }
         }

         return diff;
     }

     private Vector ids = null;
     private ToolVendor m_ToolVendor = null;
     private Technology m_Technology = null;
     private ObjectTypes m_ObjectTypes = null;
     private ToolVendor m_ToolVendorTechnologyMatch = null;
     private Vector m_Technologies = new Vector(0);
     private Vector m_ObjectTypes1 = new Vector(0);
     private Vector m_ObjectTypes2 = new Vector(0);
     private Vector m_ObjectTypes3 = new Vector(0);
     private Vector m_ObjectTypes4 = new Vector(0);
     private Vector m_ObjectTypes5 = new Vector(0);
     private Vector m_ObjectTypes6 = new Vector(0);
     private Vector m_ObjectTypes7 = new Vector(0);

     /**
      * @clientCardinality 1
      * @supplierCardinality 1
      * @directed
      */

     public void generateToolVendorsList(){//String toolvendor, Vector technologies) {
     	try {
         	ToolVendor compuware = new ToolVendor();

         	//cc m_ToolVendors.addElement(compuware);

         	 this.m_ToolVendor = compuware;

             StringBuffer sFileName = new StringBuffer();
             sFileName.append(BusinessRules.COMPUWARE);
             sFileName.append(BusinessRules.FILE_TOOLVENDOR_FILE_EXTENSION);
             String  m_FileName = sFileName.toString();

             compuware.setM_Name(BusinessRules.COMPUWARE);

             StringBuffer sPath = new StringBuffer();

             sPath.append(BusinessRules.TOOLVENDORS_FOLDER);
             String m_Path = sPath.toString();

             StringBuffer sFilePath = new StringBuffer();
             sFilePath.append(m_Path);
             sFilePath.append(BusinessRules.URL_SEPARATOR); //$NON-NLS-1$
             sFilePath.append(m_FileName);
             String m_FilePath = sFilePath.toString();

             //Hacer que el file path relativo.....
             //in future if someone use this method is necesary that change the next line, to a relative path svonborries_11102006
             compuware.setM_FilePath(m_FilePath);//BusinessRules.RESOURCE_FOLDER+BusinessRules.URL_SEPARATOR+BusinessRules.PATH_COMPUWARE);
          //   compuware.setM_Param("");

         JSX.ObjectWriter out = new JSX.ObjectWriter(new FileWriter( BusinessRules.RESOURCE_FOLDER +"compuware" + "ctv.xml"));


         m_ObjectTypes1.removeAllElements();
         m_Technologies.removeAllElements();

         Technology DOTNET = new Technology("DotNET");
         m_Technologies.addElement(DOTNET);
         ObjectTypes DotNET = new ObjectTypes(0,"DotNET");
         m_ObjectTypes1.addElement(DotNET);
         ObjectTypes DotNETButton = new ObjectTypes(1,"DotNETButton");
         m_ObjectTypes1.addElement(DotNETButton);
         ObjectTypes DotNETCalendarControl = new ObjectTypes(2,"DotNETCalendarControl");
         m_ObjectTypes1.addElement(DotNETCalendarControl);
         ObjectTypes DotNETCheckBox = new ObjectTypes(3,"DotNETCheckBox");
         m_ObjectTypes1.addElement(DotNETCheckBox);
         ObjectTypes DotNETComboBox = new ObjectTypes(4,"DotNETComboBox");
         m_ObjectTypes1.addElement(DotNETComboBox);
         ObjectTypes DotNETDataGrid = new ObjectTypes(5,"DotNETDataGrid");
         m_ObjectTypes1.addElement(DotNETDataGrid);
         ObjectTypes DotNETDateTimeControl = new ObjectTypes(6,"DotNETDateTimeControl");
         m_ObjectTypes1.addElement(DotNETDateTimeControl);
         ObjectTypes DotNETEditBox = new ObjectTypes(7,"DotNETEditBox");
         m_ObjectTypes1.addElement(DotNETEditBox);
         ObjectTypes DotNETForm = new ObjectTypes(8,"DotNETForm");
         m_ObjectTypes1.addElement(DotNETForm);
         ObjectTypes DotNETGroupBox = new ObjectTypes(9,"DotNETGroupBox");
         m_ObjectTypes1.addElement(DotNETGroupBox);
         ObjectTypes DotNETLabel = new ObjectTypes(10,"DotNETLabel");
         m_ObjectTypes1.addElement(DotNETLabel);
         ObjectTypes DotNETLinkLabel = new ObjectTypes(11,"DotNETLinkLabel");
         m_ObjectTypes1.addElement(DotNETLinkLabel);
         ObjectTypes DotNETListBox = new ObjectTypes(12,"DotNETListBox");
         m_ObjectTypes1.addElement(DotNETListBox);
         ObjectTypes DotNETListView = new ObjectTypes(13,"DotNETListView");
         m_ObjectTypes1.addElement(DotNETListView);
         ObjectTypes DotNETPictureBox = new ObjectTypes(14,"DotNETPictureBox");
         m_ObjectTypes1.addElement(DotNETPictureBox);
         ObjectTypes DotNETProgressBar = new ObjectTypes(15,"DotNETProgressBar");
         m_ObjectTypes1.addElement(DotNETProgressBar);
         ObjectTypes DotNETRadioButton = new ObjectTypes(16,"DotNETRadioButton");
         m_ObjectTypes1.addElement(DotNETRadioButton);
         ObjectTypes DotNETScrollBar = new ObjectTypes(17,"DotNETScrollBar");
         m_ObjectTypes1.addElement(DotNETScrollBar);
         ObjectTypes DotNETStatusBar = new ObjectTypes(18,"DotNETStatusBar");
         m_ObjectTypes1.addElement(DotNETStatusBar);
         ObjectTypes DotNETTabControl = new ObjectTypes(19,"DotNETTabControl");
         m_ObjectTypes1.addElement(DotNETTabControl);
         ObjectTypes DotNETToolbar = new ObjectTypes(20,"DotNETToolbar");
         m_ObjectTypes1.addElement(DotNETToolbar);
         ObjectTypes DotNETTrackBar = new ObjectTypes(21,"DotNETTrackBar");
         m_ObjectTypes1.addElement(DotNETTrackBar);
         ObjectTypes DotNETTreeView = new ObjectTypes(22,"DotNETTreeView");
         m_ObjectTypes1.addElement(DotNETTreeView);
         ObjectTypes DotNETUpDown = new ObjectTypes(23,"DotNETUpDown");
         m_ObjectTypes1.addElement(DotNETUpDown);
         DOTNET.setM_Name("DotNET");
         DOTNET.setToolVendorTechnology(DOTNET);
         DOTNET.setM_ObjectTypesValue(m_ObjectTypes1);


         m_ObjectTypes2.removeAllElements();
         Technology HTML = new Technology("HTML");
         m_Technologies.addElement(HTML);
         ObjectTypes HTMLAnchor = new ObjectTypes(0,"HTMLAnchor");
         m_ObjectTypes2.addElement(HTMLAnchor);
         ObjectTypes HTMLArea = new ObjectTypes(1,"HTMLArea");
         m_ObjectTypes2.addElement(HTMLArea);
         ObjectTypes HTMLBrowser = new ObjectTypes(2,"HTMLBrowser");
         m_ObjectTypes2.addElement(HTMLBrowser);
         ObjectTypes HTMLButton = new ObjectTypes(3,"HTMLButton");
         m_ObjectTypes2.addElement(HTMLButton);
         ObjectTypes HTMLCheckBox = new ObjectTypes(4,"HTMLCheckBox");
         m_ObjectTypes2.addElement(HTMLCheckBox);
         ObjectTypes HTMLComboBox = new ObjectTypes(5,"HTMLComboBox");
         m_ObjectTypes2.addElement(HTMLComboBox);
         ObjectTypes HTMLDiv = new ObjectTypes(6,"HTMLDiv");
         m_ObjectTypes2.addElement(HTMLDiv);
         ObjectTypes HTMLEditBox = new ObjectTypes(7,"HTMLEditBox");
         m_ObjectTypes2.addElement(HTMLEditBox);
         ObjectTypes HTMLFrame = new ObjectTypes(8,"HTMLFrame");
         m_ObjectTypes2.addElement(HTMLFrame);
         ObjectTypes HTMLImage = new ObjectTypes(9,"HTMLImage");
         m_ObjectTypes2.addElement(HTMLImage);
         ObjectTypes HTMLInputFileEditBox = new ObjectTypes(10,"HTMLInputFileEditBox");
         m_ObjectTypes2.addElement(HTMLInputFileEditBox);
         ObjectTypes HTMLLabel = new ObjectTypes(11,"HTMLLabel");
         m_ObjectTypes2.addElement(HTMLLabel);
         ObjectTypes HTMLListbox = new ObjectTypes(12,"HTMLListbox");
         m_ObjectTypes2.addElement(HTMLListbox);
         ObjectTypes HTMLRadioButton = new ObjectTypes(13,"HTMLRadioButton");
         m_ObjectTypes2.addElement(HTMLRadioButton);
         ObjectTypes HTMLSpan = new ObjectTypes(14,"HTMLSpan");
         m_ObjectTypes2.addElement(HTMLSpan);
         ObjectTypes HTMLTable = new ObjectTypes(15,"HTMLTable");
         m_ObjectTypes2.addElement(HTMLTable);
         ObjectTypes HTMLTD = new ObjectTypes(16,"HTMLTD");
         m_ObjectTypes2.addElement(HTMLTD);
         ObjectTypes HTMLTextArea = new ObjectTypes(17,"HTMLTextArea");
         m_ObjectTypes2.addElement(HTMLTextArea);
         ObjectTypes HTMLViewLink = new ObjectTypes(18,"HTMLViewLink");
         m_ObjectTypes2.addElement(HTMLViewLink);
         HTML.setToolVendorTechnology(HTML);
         HTML.setM_ObjectTypesValue(m_ObjectTypes2);


         m_ObjectTypes3.removeAllElements();
         Technology JAVA = new Technology("Java");
         m_Technologies.addElement(JAVA);

         ObjectTypes JavaButton = new ObjectTypes(0,"JavaButton");
         m_ObjectTypes3.addElement(JavaButton);
         ObjectTypes JavaCalendarControl = new ObjectTypes(1,"JavaCalendarControl");
         m_ObjectTypes3.addElement(JavaCalendarControl);
         ObjectTypes JavaCheckBox = new ObjectTypes(2,"JavaCheckBox");
         m_ObjectTypes3.addElement(JavaCheckBox);
         ObjectTypes JavaComboBox = new ObjectTypes(3,"JavaComboBox");
         m_ObjectTypes3.addElement(JavaComboBox);
         ObjectTypes JavaEditBox = new ObjectTypes(4,"JavaEditBox");
         m_ObjectTypes3.addElement(JavaEditBox);
         ObjectTypes JavaGroupBox = new ObjectTypes(5,"JavaGroupBox");
         m_ObjectTypes3.addElement(JavaGroupBox);
         ObjectTypes JavaLabel = new ObjectTypes(6,"JavaLabel");
         m_ObjectTypes3.addElement(JavaLabel);
         ObjectTypes JavaListBox = new ObjectTypes(7,"JavaListBox");
         m_ObjectTypes3.addElement(JavaListBox);
         ObjectTypes JavaMenu = new ObjectTypes(8,"JavaMenu");
         m_ObjectTypes3.addElement(JavaMenu);
         ObjectTypes JavaRadioButton = new ObjectTypes(9,"JavaRadioButton");
         m_ObjectTypes3.addElement(JavaRadioButton);
         ObjectTypes JavaScrollBar = new ObjectTypes(10,"JavaScrollBar");
         m_ObjectTypes3.addElement(JavaScrollBar);
         ObjectTypes JavaTabControl = new ObjectTypes(11,"JavaTabControl");
         m_ObjectTypes3.addElement(JavaTabControl);
         ObjectTypes JavaTable = new ObjectTypes(12,"JavaTable");
         m_ObjectTypes3.addElement(JavaTable);
         ObjectTypes JavaTreeView = new ObjectTypes(13,"JavaTreeView");
         m_ObjectTypes3.addElement(JavaTreeView);
         ObjectTypes JavaUpDown = new ObjectTypes(14,"JavaUpDown");
         m_ObjectTypes3.addElement(JavaUpDown);
         ObjectTypes JavaWindow = new ObjectTypes(15,"JavaWindow");
         m_ObjectTypes3.addElement(JavaWindow);
         JAVA.setToolVendorTechnology(JAVA);
         JAVA.setM_ObjectTypesValue(m_ObjectTypes3);


         m_ObjectTypes4.removeAllElements();
         Technology NAV = new Technology("NAV");
         m_Technologies.addElement(NAV);
         ObjectTypes NAVAssistEdit = new ObjectTypes(0,"NAVAssistEdit");
         m_ObjectTypes4.addElement(NAVAssistEdit);
         ObjectTypes NAVButton = new ObjectTypes(1,"NAVButton");
         m_ObjectTypes4.addElement(NAVButton);
         ObjectTypes NAVCheckBox = new ObjectTypes(2,"NAVCheckBox");
         m_ObjectTypes4.addElement(NAVCheckBox);
         ObjectTypes NAVForm = new ObjectTypes(3,"NAVForm");
         m_ObjectTypes4.addElement(NAVForm);
         ObjectTypes NAVFrame = new ObjectTypes(4,"NAVFrame");
         m_ObjectTypes4.addElement(NAVFrame);
         ObjectTypes NAVImage = new ObjectTypes(5,"NAVImage");
         m_ObjectTypes4.addElement(NAVImage);
         ObjectTypes NAVIndicator = new ObjectTypes(6,"NAVIndicator");
         m_ObjectTypes4.addElement(NAVIndicator);
         ObjectTypes NAVLabel = new ObjectTypes(7,"NAVLabel");
         m_ObjectTypes4.addElement(NAVLabel);
         ObjectTypes NAVMatrixBox = new ObjectTypes(8,"NAVMatrixBox");
         m_ObjectTypes4.addElement(NAVMatrixBox);
         ObjectTypes NAVMenuButton = new ObjectTypes(9,"NAVMenuButton");
         m_ObjectTypes4.addElement(NAVMenuButton);
         ObjectTypes NAVOptionButton = new ObjectTypes(10,"NAVOptionButton");
         m_ObjectTypes4.addElement(NAVOptionButton);
         ObjectTypes NAVPictureBox = new ObjectTypes(11,"NAVPictureBox");
         m_ObjectTypes4.addElement(NAVPictureBox);
         ObjectTypes NAVShape = new ObjectTypes(12,"NAVShape");
         m_ObjectTypes4.addElement(NAVShape);
         ObjectTypes NAVSubForm = new ObjectTypes(13,"NAVSubForm");
         m_ObjectTypes4.addElement(NAVSubForm);
         ObjectTypes NAVTabControl = new ObjectTypes(14,"NAVTabControl");
         m_ObjectTypes4.addElement(NAVTabControl);
         ObjectTypes NAVTableBox = new ObjectTypes(15,"NAVTableBox");
         m_ObjectTypes4.addElement(NAVTableBox);
         ObjectTypes NAVTextBox = new ObjectTypes(16,"NAVTextBox");
         m_ObjectTypes4.addElement(NAVTextBox);
         NAV.setToolVendorTechnology(NAV);
         NAV.setM_ObjectTypesValue(m_ObjectTypes4);


         m_ObjectTypes5.removeAllElements();
         Technology SAP = new Technology("SAP");
         m_Technologies.addElement(SAP);
         ObjectTypes SAPALVGrid = new ObjectTypes(0,"SAPALVGrid");
         m_ObjectTypes5.addElement(SAPALVGrid);
         ObjectTypes SAPButton = new ObjectTypes(1,"SAPButton");
         m_ObjectTypes5.addElement(SAPButton);
         ObjectTypes SAPCalendarControl = new ObjectTypes(2,"SAPCalendarControl");
         m_ObjectTypes5.addElement(SAPCalendarControl);
         ObjectTypes SAPCheckBox = new ObjectTypes(3,"SAPCheckBox");
         m_ObjectTypes5.addElement(SAPCheckBox);
         ObjectTypes SAPCheckEditBox = new ObjectTypes(4,"SAPCheckEditBox");
         m_ObjectTypes5.addElement(SAPCheckEditBox);
         ObjectTypes SAPComboBox = new ObjectTypes(5,"SAPComboBox");
         m_ObjectTypes5.addElement(SAPComboBox);
         ObjectTypes SAPEditBox = new ObjectTypes(6,"SAPEditBox");
         m_ObjectTypes5.addElement(SAPEditBox);
         ObjectTypes SAPLabel = new ObjectTypes(7,"SAPLabel");
         m_ObjectTypes5.addElement(SAPLabel);
         ObjectTypes SAPOkCodeComboBox = new ObjectTypes(8,"SAPOkCodeComboBox");
         m_ObjectTypes5.addElement(SAPOkCodeComboBox);
         ObjectTypes SAPPictureControl = new ObjectTypes(9,"SAPPictureControl");
         m_ObjectTypes5.addElement(SAPPictureControl);
         ObjectTypes SAPRadioButton = new ObjectTypes(10,"SAPRadioButton");
         m_ObjectTypes5.addElement(SAPRadioButton);
         ObjectTypes SAPScrollBar = new ObjectTypes(11,"SAPScrollBar");
         m_ObjectTypes5.addElement(SAPScrollBar);
         ObjectTypes SAPScrollContainer = new ObjectTypes(12,"SAPScrollContainer");
         m_ObjectTypes5.addElement(SAPScrollContainer);
         ObjectTypes SAPStatusBar = new ObjectTypes(13,"SAPStatusBar");
         m_ObjectTypes5.addElement(SAPStatusBar);
         ObjectTypes SAPTabControl = new ObjectTypes(14,"SAPTabControl");
         m_ObjectTypes5.addElement(SAPTabControl);
         ObjectTypes SAPTableControl = new ObjectTypes(15,"SAPTableControl");
         m_ObjectTypes5.addElement(SAPTableControl);
         ObjectTypes SAPTextEditControl = new ObjectTypes(16,"SAPTextEditControl");
         m_ObjectTypes5.addElement(SAPTextEditControl);
         ObjectTypes SAPTitleBar = new ObjectTypes(17,"SAPTitleBar");
         m_ObjectTypes5.addElement(SAPTitleBar);
         ObjectTypes SAPToolBar = new ObjectTypes(18,"SAPToolBar");
         m_ObjectTypes5.addElement(SAPToolBar);
         ObjectTypes SAPToolBarControl = new ObjectTypes(19,"SAPToolBarControl");
         m_ObjectTypes5.addElement(SAPToolBarControl);
         ObjectTypes SAPTreeView = new ObjectTypes(20,"SAPTreeView");
         m_ObjectTypes5.addElement(SAPTreeView);
         ObjectTypes SAPUserArea = new ObjectTypes(21,"SAPUserArea");
         m_ObjectTypes5.addElement(SAPUserArea);
         ObjectTypes SAPWebViewer2D = new ObjectTypes(22,"SAPWebViewer2D");
         m_ObjectTypes5.addElement(SAPWebViewer2D);
         ObjectTypes SAPWindow = new ObjectTypes(23,"SAPWindow");
         m_ObjectTypes5.addElement(SAPWindow);
         SAP.setToolVendorTechnology(SAP);
         SAP.setM_ObjectTypesValue(m_ObjectTypes5);


         m_ObjectTypes6.removeAllElements();
         Technology BORLAND = new Technology("Borland");
         m_Technologies.addElement(BORLAND);
         ObjectTypes ActiveX = new ObjectTypes(0,"ActiveX");
         m_ObjectTypes6.addElement(ActiveX);
         ObjectTypes Button = new ObjectTypes(1,"Button");
         m_ObjectTypes6.addElement(Button);
         ObjectTypes CalendarControl = new ObjectTypes(2,"CalendarControl");
         m_ObjectTypes6.addElement(CalendarControl);
         ObjectTypes CheckBox = new ObjectTypes(3,"CheckBox");
         m_ObjectTypes6.addElement(CheckBox);
         ObjectTypes ComboBox = new ObjectTypes(4,"ComboBox");
         m_ObjectTypes6.addElement(ComboBox);
         ObjectTypes ComboLBox = new ObjectTypes(5,"ComboLBox");
         m_ObjectTypes6.addElement(ComboLBox);
         ObjectTypes DateTimeControl = new ObjectTypes(6,"DateTimeControl");
         m_ObjectTypes6.addElement(DateTimeControl);
         ObjectTypes EditBox = new ObjectTypes(7,"EditBox");
         m_ObjectTypes6.addElement(EditBox);
         ObjectTypes GUIObject = new ObjectTypes(8,"GUIObject");
         m_ObjectTypes6.addElement(GUIObject);
         ObjectTypes Header = new ObjectTypes(9,"Header");
         m_ObjectTypes6.addElement(Header);
         ObjectTypes IPControl = new ObjectTypes(10,"IPControl");
         m_ObjectTypes6.addElement(IPControl);
         ObjectTypes Label = new ObjectTypes(11,"Label");
         m_ObjectTypes6.addElement(Label);
         ObjectTypes Listbox = new ObjectTypes(12,"Listbox");
         m_ObjectTypes6.addElement(Listbox);
         ObjectTypes ListView = new ObjectTypes(13,"ListView");
         m_ObjectTypes6.addElement(ListView);
         ObjectTypes Menu = new ObjectTypes(14,"Menu");
         m_ObjectTypes6.addElement(Menu);
         ObjectTypes MenuItem = new ObjectTypes(15,"MenuItem");
         m_ObjectTypes6.addElement(MenuItem);
         ObjectTypes Pager = new ObjectTypes(16,"Pager");
         m_ObjectTypes6.addElement(Pager);
         ObjectTypes RadioButton = new ObjectTypes(17,"RadioButton");
         m_ObjectTypes6.addElement(RadioButton);
         ObjectTypes ScrollBar = new ObjectTypes(18,"ScrollBar");
         m_ObjectTypes6.addElement(ScrollBar);
         ObjectTypes SysLink = new ObjectTypes(19,"SysLink");
         m_ObjectTypes6.addElement(SysLink);
         ObjectTypes TabControl = new ObjectTypes(20,"TabControl");
         m_ObjectTypes6.addElement(TabControl);
         ObjectTypes ToolBar = new ObjectTypes(21,"ToolBar");
         m_ObjectTypes6.addElement(ToolBar);
         ObjectTypes TreeView = new ObjectTypes(22,"TreeView");
         m_ObjectTypes6.addElement(TreeView);
         ObjectTypes UpDown = new ObjectTypes(23,"UpDown");
         m_ObjectTypes6.addElement(UpDown);
         ObjectTypes Window = new ObjectTypes(24,"Window");
         m_ObjectTypes6.addElement(Window);
         BORLAND.setToolVendorTechnology(BORLAND);
         BORLAND.setM_ObjectTypesValue(m_ObjectTypes6);


         m_ObjectTypes7.removeAllElements();
         Technology VB = new Technology("VB");
         m_Technologies.addElement(VB);
         ObjectTypes VBButton = new ObjectTypes(0,"VBButton");
         m_ObjectTypes7.addElement(VBButton);
         ObjectTypes VBCheckBox = new ObjectTypes(1,"VBCheckBox");
         m_ObjectTypes7.addElement(VBCheckBox);
         ObjectTypes VBComboBox = new ObjectTypes(2,"VBComboBox");
         m_ObjectTypes7.addElement(VBComboBox);
         ObjectTypes VBEditBox = new ObjectTypes(3,"VBEditBox");
         m_ObjectTypes7.addElement(VBEditBox);
         ObjectTypes VBImage = new ObjectTypes(4,"VBImage");
         m_ObjectTypes7.addElement(VBImage);
         ObjectTypes VBLabel = new ObjectTypes(5,"VBLabel");
         m_ObjectTypes7.addElement(VBLabel);
         ObjectTypes VBListBox = new ObjectTypes(6,"VBListBox");
         m_ObjectTypes7.addElement(VBListBox);
         ObjectTypes VBPictureBox = new ObjectTypes(7,"VBPictureBox");
         m_ObjectTypes7.addElement(VBPictureBox);
         ObjectTypes VBRadioButton = new ObjectTypes(8,"VBRadioButton");
         m_ObjectTypes7.addElement(VBRadioButton);
         ObjectTypes VBScrollBar = new ObjectTypes(9,"VBScrollBar");
         m_ObjectTypes7.addElement(VBScrollBar);
         ObjectTypes VBWindow = new ObjectTypes(10,"VBWindow");
         m_ObjectTypes7.addElement(VBWindow);
         VB.setM_ObjectTypesValue(m_ObjectTypes7);
         VB.setToolVendorTechnology(VB);

         compuware.setM_Technology(m_Technologies);

         setM_Technologies(m_Technologies);

         out.writeObject(compuware);

         out.close();
         } catch(java.io.IOException e) {
                System.out.println(e);
           }
     }

}

