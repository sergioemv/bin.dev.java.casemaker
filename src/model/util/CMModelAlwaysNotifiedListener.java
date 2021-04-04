package model.util;

import java.util.List;

/**
 * @author Sergio Moreno
 *  Tag classes that are always notified by the eventhandler. also stores the events received and makes them public
 *
 */
public interface CMModelAlwaysNotifiedListener extends CMModelListener {
	public List<CMModelEvent> getEvents();
	public void clearEvents();
}
