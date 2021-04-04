//hmendez04102005begin
package bi.view.workspaceviews;
import java.awt.event.KeyEvent;

import bi.view.actions.CMAction;
import bi.view.lang.CMMessages;
import bi.view.mainframeviews.CMFrameView;
import bi.view.treeviews.CMTreeWorkspaceView;

public class CMKeyIntercepterWorkspaceView {
	private CMFrameView m_FrameView=null;
	public CMKeyIntercepterWorkspaceView(CMFrameView p_frameView) {
		 m_FrameView = p_frameView;
	}	
	 public boolean dispatchKeyEvent(KeyEvent e)
	{//LABEL_WORKSPACE,LABEL_PROJECT,TAB_LABEL_TEST_OBJECT
	  if (m_FrameView.getTabbedPane().isShowing())
	  {
  	    String l_tabTittle = m_FrameView.getTabbedPane().getTitleAt(0);		
        if (l_tabTittle.equalsIgnoreCase(CMMessages.getString("LABEL_WORKSPACE"))){
          if (e.getKeyCode() == KeyEvent.VK_DELETE)
            CMAction.DELETE.getAction().actionPerformed(null);
          	//Ccastedo begins 11-10-05
   	   		return true;
   	   		//Ccastedo ends 11-10-05
        }
        else if (l_tabTittle.equalsIgnoreCase(CMMessages.getString("LABEL_PROJECT")))
        {
      	  //System.out.println(CMMessages.getString("LABEL_PROJECT"));        	
          //Does nothing at the moment		  
        }
        else if (l_tabTittle.equalsIgnoreCase(CMMessages.getString("TAB_LABEL_TEST_OBJECT")))
        {
        	if ((e.getKeyCode() == KeyEvent.VK_F10)&& (e.isShiftDown()))
            {
        	   m_FrameView.getJPopupMenuFile().show((CMTreeWorkspaceView)e.getSource(),90,56);
        	   //Ccastedo begins 11-10-05
        	   return true;
        	   //Ccastedo ends 11-10-05
            }
        }        
	  }
	  return false;
	}
}
//hmendez04102005end