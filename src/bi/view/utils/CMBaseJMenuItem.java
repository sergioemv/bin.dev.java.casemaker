package bi.view.utils;

import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

public class CMBaseJMenuItem extends JMenuItem {

	//if the menu gets disabled then is not visible
	private boolean hideable = false;
	private CMBaseJMenuItemUI baseMenuItemUI = new CMBaseJMenuItemUI();
	public CMBaseJMenuItem() {
		super();
		initialize();
	}

	public CMBaseJMenuItem(String text) {
		super(text);
		initialize();

	}

	public CMBaseJMenuItem(String text, int mnemonic) {
		super(text, mnemonic);
		initialize();
	}

	public CMBaseJMenuItem(Action a) {
		super(a);
		initialize();
	}
	public CMBaseJMenuItem(Action a,boolean hideable) {
		super(a);
		setHideable(hideable);
		if (hideable)
			this.setVisible(this.isEnabled());
		initialize();
	}

	public CMBaseJMenuItem(Icon icon) {
		super(icon);
		initialize();
	}

public void setAccelerator(KeyStroke keyStroke) {
		// TODO Auto-generated method stub
		super.setAccelerator(keyStroke);
	}
	private void initialize() {
		// TODO Auto-generated method stub
		this.setIcon(null);
		this.setToolTipText(null);
	//	this.setUI(baseMenuItemUI);

	}

	public CMBaseJMenuItem(String text, Icon icon) {
		super(text, icon);
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see javax.swing.JMenuItem#setEnabled(boolean)
	 */
	@Override
	public void setEnabled(boolean p_b) {
		// TODO Auto-generated method stub
		super.setEnabled(p_b);
		if (hideable){
			setVisible(p_b);
			this.repaint();
		}
//		if (this.getAction()!=null)
//		{
//			System.out.println(this.getAction()+" - hideable "+hideable);
//			if (!p_b)
//				setAccelerator(null);
//			else
//				if (this.getAction().getValue(Action.ACCELERATOR_KEY)!=null)
//				setAccelerator((KeyStroke)this.getAction().getValue(Action.ACCELERATOR_KEY));
//		}
	
		
		
	
	}

	public boolean isHideable() {
		return this.hideable;
	}

	public void setHideable(boolean p_hideable) {
		this.hideable = p_hideable;
	}

}
