package bi.view.undomanagementviews.undoviews;

import java.util.ArrayList;
import java.util.List;

import javax.swing.undo.UndoableEdit;

import model.edit.CMModelUndoableEdit;
import model.util.CMDelegate;
import model.util.CMModelAlwaysNotifiedListener;
import model.util.CMModelAlwaysNotifiedListenerImpl;
import model.util.CMModelEvent;
import model.util.CMModelEventHandler;
import model.util.CMModelSource;
import bi.view.undomanagementviews.CMCompoundEdit;

 class CMCompoundMassiveEdit extends CMCompoundEdit {

	private CMModelAlwaysNotifiedListener listener;

	public CMModelAlwaysNotifiedListener getListener() {
		if (listener == null)
			listener = new CMModelAlwaysNotifiedListenerImpl();
		return listener;
	}
	@Override
	public boolean addEdit(UndoableEdit anEdit) {

		if (anEdit instanceof CMCompoundEdit) {
		List<Object> modModels = new ArrayList<Object>();
		((CMCompoundEdit)anEdit).fillModifiedObjects(modModels);
				for(Object object : modModels)
					if (object instanceof CMModelSource)
						((CMModelSource)object).addModelListener(getListener());
		}
		else
			if (anEdit instanceof CMModelUndoableEdit && ((CMModelUndoableEdit)anEdit).getModifiedObject() instanceof CMModelSource)
				((CMModelSource)((CMModelUndoableEdit)anEdit).getModifiedObject()).addModelListener(getListener());
		CMCompoundEdit ce = new CMCompoundEdit();
//		 disable model notification in redo
		ce.addRedoDelegateEdit(CMModelEventHandler.stopUpdatingDelegate());
		//enable model notification and renotify the classes on the undo
		ce.addUndoDelegateEdit(new CMDelegate(){
			public void execute() {
				for (CMModelEvent evt : getListener().getEvents()){
						evt.getSource().fireModelEventHappen(evt.getChangedField());
				}
			}});
		ce.addUndoDelegateEdit(CMModelEventHandler.startUpdatingDelegate());
		//add the massive edit
		ce.addEdit(anEdit);
//		 enable model notification in redo and renotify
		ce.addRedoDelegateEdit(CMModelEventHandler.startUpdatingDelegate());
		ce.addRedoDelegateEdit(new CMDelegate(){
			public void execute() {
				for (CMModelEvent evt : getListener().getEvents()){
						evt.getSource().fireModelEventHappen(evt.getChangedField());
				}
			}});
		//disable notification for the undo
		ce.addUndoDelegateEdit(CMModelEventHandler.stopUpdatingDelegate());
		return super.addEdit(ce);
	}


}
