/**
 * This Software has been developed by Business Software Innovations  .
 * Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved.
 */
package model.util;

import java.util.ArrayList;
import java.util.List;

import model.CMField;


/**
 * @author smoreno
 *
 */
public class CMModelEventHandler {

	private static boolean notifyEnabled=true;
	private List<CMModelListener> list = new ArrayList<CMModelListener>();

	public void addModelListener(CMModelListener listener)
	{
		if (!this.list.contains(listener))
			this.list.add(listener);
	}
	public void removeModelListener(CMModelListener listener)
	{
		this.list.remove(listener);
	}
	public void fireModelEventHappen(CMModelSource source, CMField field)
	{
		CMModelEvent evt = new CMModelEvent(source,field);
		int numberOfListeners = list.size();
		for (int i = numberOfListeners-1;i>=0;i--) {
			if (list.get(i)!=null) {
				if (notifyEnabled){
					CMModelListener listener = list.get(i);
					listener.handleCMModelChange(evt);
				}else
					if(list.get(i) instanceof CMModelAlwaysNotifiedListener)
						list.get(i).handleCMModelChange(evt);

			}
		}


	}
	/**
	 * @return Returns the notifyEnabled.
	 */
	public static boolean isNotifyEnabled() {
		return notifyEnabled;
	}
	/**
	 * @param p_notifyEnabled The notifyEnabled to set.
	 */
	public static void setNotifyEnabled(boolean p_notifyEnabled) {
		notifyEnabled = p_notifyEnabled;
	}

	public static CMDelegate startUpdatingDelegate(){
		return new CMDelegate(){

			public void execute() {
				setNotifyEnabled(true);
			}};
	}
	public static CMDelegate stopUpdatingDelegate(){
		return new CMDelegate(){

			public void execute() {
				setNotifyEnabled(false);
			}};
	}
}
