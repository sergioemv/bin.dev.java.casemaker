/**
 * This Software has been developed by Business Software Innovations  .
 * Copyright (c)2003 D�az und Hilterscheid Unternehmensberatung. All rights reserved. 
 */
package bi.view.utils.event;

/**
 * @author smoreno
 *
 */
public interface CMProgressSource {
	
	public void addProgressListener(CMProgressListener rl);

    public void removeProgressListener(CMProgressListener rl);

}
