package bi.view.elementviews;

import javax.swing.JComboBox;

import model.ObjectTypes;
import model.Technology;
import bi.view.mainframeviews.CMFrameView;
import bi.view.utils.CMBaseJComboBox;

public class CMObjectTypeComboBox {
	
	String m_ToolVendorTech = "";
	CMFrameView cmFrame;
	CMBaseJComboBox  jCBTVT = null;
	Technology m_Tech = new Technology("");
	
	
	public CMObjectTypeComboBox(JComboBox jCBObjectType, Technology ToolVendorTechnology,CMFrameView frame) {
		
		this.m_Technology=ToolVendorTechnology;
		this.jCBTVT=(CMBaseJComboBox) jCBObjectType;
		this.cmFrame=frame;
	//	initialize(jCBTVT,m_Technology,cmFrame);		
	}
	
  
	   
	
   
  
   private Technology m_Technology = null;
   private ObjectTypes m_ObjectTypes = null;
   
}

