package bi.view.utils;

import java.awt.Component;
import java.awt.Container;
import java.awt.FocusTraversalPolicy;
import java.awt.Frame;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JRootPane;
import javax.swing.KeyStroke;

public abstract class JCustomDialog extends JDialog {
	/** 
	 * @throws HeadlessException
	 */
	public JCustomDialog() throws HeadlessException {
		super();
		this.setFocusTraversalPolicy(this.policy);
		this.setFocusCycleRoot(true);
	}

	/**
	 * @param arg0
	 * @param arg1
	 * @throws HeadlessException
	 * If the second setPolicy is true then the default policy is change to custom policy
	 * otherwise the default policy is setted
	 */
	public JCustomDialog(Frame frame, boolean setPolicy) throws HeadlessException {
		super(frame, true);
		if(setPolicy)
			this.setFocusTraversalPolicy(this.policy);
		this.setFocusCycleRoot(true);
	}

	/**
	 * @param arg0
	 * @throws HeadlessException
	 */
	public JCustomDialog(Frame arg0) throws HeadlessException {
		super(arg0, true);
		this.setFocusTraversalPolicy(this.policy);
		this.setFocusCycleRoot(true);
	}

	protected JRootPane createRootPane()
	    {
	       JRootPane pane = super.createRootPane();
	  
	       pane.getInputMap( JComponent.WHEN_IN_FOCUSED_WINDOW ).
	          put( KeyStroke.getKeyStroke( KeyEvent.VK_ESCAPE, 0 ), "escPressed" );
	  
	       pane.getActionMap().put( "escPressed",
	          new AbstractAction( "escPressed" )
	          {
	             public void actionPerformed( ActionEvent actionEvent )
	             {
	                PressJButtonCancel(null);
	             }
	          }
	       );
	 
	   /* pane.getInputMap( JComponent.WHEN_IN_FOCUSED_WINDOW ).
	    put( KeyStroke.getKeyStroke( KeyEvent.VK_ENTER, 0 ), "enterPressed" );

	    pane.getActionMap().put( "enterPressed",
	       new AbstractAction( "enterPressed" )
	       {
	          public void actionPerformed( ActionEvent actionEvent )
	          {
	        		  PressJButtonOk(null);
	          }

			
	       }
	    );*/
	       return pane;
	    }
	  
	    FocusTraversalPolicy policy = new FocusTraversalPolicy() {
		  
		  public Component getFirstComponent(Container focusCycleRoot) {
			  if ((getOrder()!=null)&&(getOrder().size()>0))
				  	return (Component)getOrder().get(0);
			  else 
				  return null;
		  }
		  public Component getLastComponent(Container focusCycleRoot) 
		  {
			  if ((getOrder()!=null)&&(getOrder().size()>0))
				  return (Component)getOrder().get(getOrder().size()-1);
			  else 
				  return null;
		  }
		  public Component getComponentAfter(Container focusCycleRoot,
		  Component aComponent) {
			  	int index = getOrder().indexOf(aComponent);
			  	return (Component)getOrder().get((index + 1) % getOrder().size());
		  }
		  public Component getComponentBefore(Container focusCycleRoot,
		  Component aComponent) {
			  int index = getOrder().indexOf(aComponent);
			  return (Component)getOrder().get((index - 1 + getOrder().size()) % getOrder().size());
		  }
		  public Component getDefaultComponent(Container focusCycleRoot) {
		  return getFirstComponent(focusCycleRoot);
		  }
		  };
	   protected abstract List getOrder();
	   protected abstract void PressJButtonCancel(Object object);
	   protected abstract void PressJButtonOk(Object object);
}
