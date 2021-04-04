/**
 * This Software has been developed by Business Software Innovations  .
 * Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved. 
 */
package bi.view.utils;


/**
 * Shows the Busy dialog and refreshes the screen reglarily
 * @author smoreno
 *
 */
public class CMBusyThread extends Thread {
	
	/**
	 * This filed controls if the busy dialog is showed or not
	 * 
	 */
	private static boolean showing;
	private static CMBusyDialog dialog;
/* (non-Javadoc)
 * @see java.lang.Thread#run()
 */
@Override
public void run() {
	// TODO Auto-generated method stub
	super.run();
	while (true)
	{
		try {
			sleep(50);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (isShowing())
			if (getDialog().isShowing())
			getDialog().repaint();
			else
				getDialog().show();
		else
			getDialog().setVisible(false);
		
	}
}
public static boolean isShowing() {
	return showing;
}
public static void setShowing(boolean p_showing) {
	showing = p_showing;
}
public static CMBusyDialog getDialog() {
	if (dialog == null)
		dialog = new CMBusyDialog();
	return dialog;
}
}
