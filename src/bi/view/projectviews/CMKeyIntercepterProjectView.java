//hmendez_05102005_begin
package bi.view.projectviews;

import java.awt.event.KeyEvent;

import bi.view.actions.CMAction;
import bi.view.lang.CMMessages;
import bi.view.mainframeviews.CMFrameView;
import bi.view.treeviews.CMTreeWorkspaceView;

public class CMKeyIntercepterProjectView {
		private CMFrameView m_FrameView=null;
		public CMKeyIntercepterProjectView(CMFrameView p_frameView) {
			 m_FrameView = p_frameView;
		}	
		 public boolean dispatchKeyEvent(KeyEvent e)
		{
		  String l_lastSelectedPathComponent = ((CMTreeWorkspaceView)e.getSource()).getLastSelectedPathComponent().toString();
	      if (l_lastSelectedPathComponent.startsWith(CMMessages.getString("LABEL_WORKSPACE_UNTITLED")))
		  {
	    	if (e.getKeyCode() == KeyEvent.VK_DELETE)
	    	{
	          CMAction.DELETE.getAction().actionPerformed(null);
	    	}
		  }
		  return false;
		}

	}
//hmendez_05102005_end

