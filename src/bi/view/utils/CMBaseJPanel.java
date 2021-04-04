package bi.view.utils;

import java.awt.Component;
import java.awt.Container;
import java.awt.FocusTraversalPolicy;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyListener;
import java.util.List;

import javax.swing.JPanel;

public abstract class CMBaseJPanel extends JPanel {
	public CMBaseJPanel()
	{
		super();
		this.setFocusTraversalPolicy(this.policy);
		this.setFocusCycleRoot(true);
	}
	private KeyListener defaultKeyListener = new KeyAdapter()
	{                   
/*
		public void keyPressed(KeyEvent e) {
			if (e.getKeyCode()==KeyEvent.VK_ENTER)
				if (!(e.getSource() instanceof JEditorPane))
				fireButtonOk();
			
			if (e.getKeyCode()==KeyEvent.VK_ESCAPE)
				fireButtonCancel();
			
		}
*/
  };
//Define the component order here   
    public abstract List getOrder();
    FocusTraversalPolicy policy = new FocusTraversalPolicy() {
	  
	  public Component getFirstComponent(Container focusCycleRoot) {
		  if ((getOrder()!=null)&&(getOrder().size()>0))
		       if (((Component)getOrder().get(0)).isFocusable())
			  	  return (Component)getOrder().get(0);
		       else
		          return null;
		  else 
			  return null;
	  }
	  public Component getLastComponent(Container focusCycleRoot) 
	  {
		  if ((getOrder()!=null)&&(getOrder().size()>0))
			  if (((Component)getOrder().get(getOrder().size()-1)).isFocusable())
			    return (Component)getOrder().get(getOrder().size()-1);
			  else
				return null;
		  else 
			  return null;
	  }
	  public Component getComponentAfter(Container focusCycleRoot,
	  Component aComponent) {
		  	int index = getOrder().indexOf(aComponent);
		  	if (((Component)getOrder().get((index + 1) % getOrder().size())).isFocusable())
		  	  return (Component)getOrder().get((index + 1) % getOrder().size());
		  	else
		  	  return null;
	  }
	  public Component getComponentBefore(Container focusCycleRoot,
	  Component aComponent) {
		  int index = getOrder().indexOf(aComponent);
		  if (((Component)getOrder().get((index - 1 + getOrder().size()) % getOrder().size())).isFocusable())
		    return (Component)getOrder().get((index - 1 + getOrder().size()) % getOrder().size());
		  else
		    return null;
	  }
	  public Component getDefaultComponent(Container focusCycleRoot) {
	  return getFirstComponent(focusCycleRoot);
	  }
	  };
  //smoreno_051005 end
   
    
  protected void addKeyListenertoAll(Component comp, KeyListener listener) {
  	  if (((comp instanceof Container))
  			  &&(((Container)comp).getComponentCount()>0))
  			  {
  			  for (int i = 0;i<((Container)comp).getComponentCount();i++)
  				  addKeyListenertoAll(((Container)comp).getComponent(i),listener);
  			  comp.addKeyListener(listener);
  			  }
  	  else 
  			  comp.addKeyListener(listener);
  	  
}
public KeyListener getDefaultKeyListener() {
	return defaultKeyListener;
}
public void setDefaultKeyListener(KeyListener listener) {
	this.defaultKeyListener = listener;
}

}

