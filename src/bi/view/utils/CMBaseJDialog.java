package bi.view.utils;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FocusTraversalPolicy;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;

import bi.view.mainframeviews.CMApplication;

public abstract class CMBaseJDialog extends JDialog {

   protected CMModalResult modalResult= CMModalResult.CANCEL;

	public CMBaseJDialog(JFrame frame)
	{
		super(frame,null,true);
		initialize();

	}
	public abstract JButton getDefaultButton() ;
	public CMBaseJDialog(JFrame frame,boolean installDefaultFocusTraversalPolicy)
	{
		super(frame,null,true);
		initialize();
	}
	/**
	 *
	 */
	public CMBaseJDialog() {
		// TODO Auto-generated constructor stub

		super(CMApplication.frame);
		initialize();
	}
	private KeyListener defaultKeyListener = new KeyAdapter()
	{

		public void keyPressed(KeyEvent e) {
//			hmendez_11112005_begin

//			if (e.getKeyCode()==KeyEvent.VK_ENTER)
//			{

//				if (e.getSource() instanceof JFormattedTextField)
//				{
//					String l_text = ((JFormattedTextField)e.getSource()).getText();
//					AbstractFormatter l_AbstractFormatter = ((JFormattedTextField)e.getSource()).getFormatter();
//
//					try {
//						//Manual Parsing of the text in the JFormattedTextField
//					  l_AbstractFormatter.stringToValue(l_text);
					  //fireButtonOk();
//					}
//					catch (ParseException pe){
//						System.out.println("Invalid Field "+pe);
//						((JFormattedTextField)e.getSource()).setText("");
//					}
//				}

//				if (!(e.getSource() instanceof JEditorPane)&&!(e.getSource() instanceof JButton))
//				{
//					if (e.getSource() instanceof JComboBox)
//					{
//						if (!((JComboBox)e.getSource()).isPopupVisible())
//							fireButtonOk();
//					}
//					else
//						{
//						    fireButtonOk();
//						}
//				}
//			}
//hmendez_11112005_end
			if (e.getKeyCode()==KeyEvent.VK_ESCAPE)
				if (e.getSource() instanceof JComboBox)
				{
					if (!((JComboBox)e.getSource()).isPopupVisible())
						fireButtonCancel();
				}
				else
					     fireButtonCancel();

		}

  };
//Define the component order here

    protected abstract List getOrder();
    FocusTraversalPolicy focusTraversalPolicy = new FocusTraversalPolicy() {

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
  //smoreno_051005 end
	protected abstract void fireButtonOk();
    protected abstract void fireButtonCancel() ;


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
/* (non-Javadoc)
 * @see java.awt.Dialog#show()
 */
public void show() {
	// by default all this dialogs are showed centered on the screen
	//get the size
	Dimension dlgSize = getSize();
	//get the size of the screen
	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	//set the new centered location
	setLocation((screenSize.width - dlgSize.width) / 2, (screenSize.height - dlgSize.height) / 2);
	// show the dialog
	super.show();
}
public CMModalResult getModalResult() {
	return this.modalResult;
}
public void setModalResult(CMModalResult p_modalResult) {
	this.modalResult = p_modalResult;
}
@Override
protected void dialogInit() {
	// TODO Auto-generated method stub
	super.dialogInit();
}
private void initialize()
{
	this.setFocusTraversalPolicy(this.focusTraversalPolicy);
	this.setFocusCycleRoot(true);
	//this.addFocusListener(listener );
	addWindowListener(
            new java.awt.event.WindowAdapter() {
            public void windowActivated(java.awt.event.WindowEvent e) {
            		getRootPane().setDefaultButton(getDefaultButton());
            };
            });
}
}
