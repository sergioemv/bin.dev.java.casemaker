/**
 * This Software has been developed by Business Software Innovations  .
 * Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved. 
 */
package bi.view.utils.event;

import java.util.Iterator;
import java.util.Vector;

import org.apache.log4j.Logger;


/**
 * @author smoreno
 *
 */
public class CMProgressEventHandler {
	private Vector<CMProgressListener> listeners = new Vector<CMProgressListener>( );

    public void addProgressListener(CMProgressListener cl) {
    	if (!listeners.contains(cl))
    		listeners.add(cl);
    }
    public void removeProgressListener(CMProgressListener cl) {

        listeners.remove(cl);

    }

    public void fireProgressEventHappen(Object source, String progress)
    {
    	CMProgressEvent evt = new CMProgressEvent(source,progress);
    	for (Iterator<CMProgressListener> iter=listeners.iterator();iter.hasNext();) {
    		CMProgressListener l = iter.next();
    		try {
    			l.progressEventHappen(evt);
    		}
    		catch(RuntimeException e){
    			Logger.getLogger(this.getClass()).error("unexpected error in listener "+e);
    			iter.remove();
    		}
    	}
    }
    }
