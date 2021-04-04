package bi.view.utils;

import java.awt.Component;
import java.awt.Container;
import java.awt.FocusTraversalPolicy;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.List;

import javax.swing.JTabbedPane;

import com.jidesoft.swing.JideTabbedPane;

public abstract class CMBaseJTabbedPane extends JideTabbedPane {
	public CMBaseJTabbedPane()
	{
		super();
		this.setFocusTraversalPolicy(this.policy);
		this.setFocusCycleRoot(true);
		this.setBoldActiveTab(true);
		//this.setTabColorProvider(JideTabbedPane.ONENOTE_COLOR_PROVIDER);
		this.setTabShape(JideTabbedPane.SHAPE_OFFICE2003 );
		this.setColorTheme(JideTabbedPane.COLOR_THEME_OFFICE2003);
//hmendez_14102005_begin		
		this.addKeyListener(this.getDefaultKeyListener());
	}
	private KeyListener defaultKeyListener = new KeyAdapter()
	{                   

		public void keyPressed(KeyEvent e) {
			if ((e.getKeyCode()==KeyEvent.VK_N)&&(e.isControlDown()))
			{
			   policy.getFirstComponent((Container)e.getSource()).requestFocus();
			}
			
		}

  };
//hmendez_14102005_end  
//Define the component order here   
    protected abstract List getOrder();
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
		    //System.out.println(focusCycleRoot.getClass().toString()+","+aComponent.getClass().toString());
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
	      Component l_component = getFirstComponent(focusCycleRoot);
		  return l_component;
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

