package model.util;

import java.util.ArrayList;
import java.util.List;

public class CMModelAlwaysNotifiedListenerImpl implements
        CMModelAlwaysNotifiedListener {
    private List<CMModelEvent> events;
    public void handleCMModelChange(CMModelEvent evt) {
        // TODO Auto-generated method stub
        for (CMModelEvent event : getEvents())
            if (evt.getSource().equals(event.getSource())
               && evt.getChangedField().equals(event.getChangedField()))
            	return;
        getEvents().add(evt);
    }
    public List<CMModelEvent> getEvents() {
        if (events == null)
            events = new ArrayList<CMModelEvent>();
        return events;
    }
	public void clearEvents() {
		events = null;
	}
}
