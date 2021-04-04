/**
 * This Software has been developed by Business Software Innovations  .
 * Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved.
 */
package bi.view.actions;


/**
 * @author smoreno
 *
 */
public class CMActionEnabler extends Thread {
private int counter = 0;
/* (non-Javadoc)
 * @see java.lang.Thread#run()
 */
	/**
	 *
	 */
	public CMActionEnabler() {
		this.setName("Action Enabler / Disabler");
	}
@Override
public void run() {

	while (!isInterrupted())
	{
		for (CMAction action : CMAction.values())
		{
			try {
				sleep(3);
				if (action.getAction() instanceof CMEnabledAction)
				{
					boolean isShowing = true;
//					for (PropertyChangeListener listener :((AbstractAction)action.getAction()).getPropertyChangeListeners())
//						if (listener instanceof Component)
//							isShowing = ((Component)listener).isShowing();
					//TODO verify if the component is showing
					action.setEnabled(isShowing&&((CMEnabledAction)action.getAction()).calculateEnabled());
					counter++;
					if (counter == 100)
					{
					counter = 0;
					System.gc();
					//Logger.getLogger(this.getClass()).info("Garbage Collector called");
					}
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				continue;
			}

		}
	}
}
}
