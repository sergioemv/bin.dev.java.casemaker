package bi.view.undomanagementviews;

import java.util.ArrayList;
import java.util.List;

import javax.swing.undo.AbstractUndoableEdit;
import javax.swing.undo.CannotUndoException;
import javax.swing.undo.CompoundEdit;
import javax.swing.undo.UndoableEdit;

import model.edit.CMModelUndoableEdit;
import model.util.CMDelegate;

import org.apache.log4j.Logger;


@SuppressWarnings("serial")
public class CMCompoundEdit extends CompoundEdit {

	private Logger logger = Logger.getLogger(this.getClass());
	private boolean isDone;

	/* (non-Javadoc)
	 * @see javax.swing.undo.CompoundEdit#addEdit(javax.swing.undo.UndoableEdit)
	 */
	@Override
	public boolean addEdit(UndoableEdit anEdit) {
		boolean canDo = false;
		try {
			if(isDone == true)
				canDo = super.addEdit(anEdit);
		} catch (StackOverflowError e) {
			setDone(false);
			canDo = false;
		}
		return canDo;
	}
	/**
	 *
	 */
	public CMCompoundEdit() {
		super();
		//setLimit(200);
		setDone(true);
	}
	public CMCompoundEdit(int limit) {
		super();
		//setLimit(limit);
		setDone(true);
	}

	public void endAll()
	{
		this.end();
		List< UndoableEdit> edits = new ArrayList<UndoableEdit>();
		edits.addAll(getEdits());
		if (edits.size()==0) return;
	   for (int i = 0;i< edits.size(); i++)
	   {
		   UndoableEdit edit = edits.get(i);
		   if (edit instanceof CMCompoundEdit)
			   ((CMCompoundEdit)edit).endAll();
	   }
	}

public boolean canRedo() {
	boolean b = super.canRedo();
	if ((b==false))
		Logger.getLogger(this.getClass()).debug("Cannot redo "+getPresentationName());
	return b;
}

@Override
public synchronized boolean canUndo() {
	boolean b = super.canUndo();
	if (!b)
		Logger.getLogger(this.getClass()).debug("Cannot  undo "+getPresentationName());
	return b;
}
/**
*@autor smoreno
 * @return
 */
public boolean hasEdits() {

	return this.edits.size()>0;
}

public List<UndoableEdit> getEdits(){
	return this.edits;
}
/**
*@autor smoreno
 * @return all models modified by the group of edits
 */
public void fillModifiedObjects(List<Object> models){
	if (models == null) return;
	List< UndoableEdit> edits = new ArrayList<UndoableEdit>();
	edits.addAll(getEdits());
	if (edits.size()==0) return;
	for (int i = 0; i<edits.size();i++){
		UndoableEdit edit = edits.get(i);
	//for (UndoableEdit edit : getEdits())
		if (edit instanceof CMCompoundEdit)
			((CMCompoundEdit)edit).fillModifiedObjects(models);
		else
			if (edit instanceof CMModelUndoableEdit && !models.contains(((CMModelUndoableEdit) edit).getModifiedObject()))
				models.add(((CMModelUndoableEdit) edit).getModifiedObject());
	}
}
/**
 * @param p_delegate
 */
public void addDelegateEdit(final CMDelegate p_delegate) {
	this.addEdit(new AbstractUndoableEdit(){
		/**
		 *
		 */
		private static final long serialVersionUID = 1L;
		/* (non-Javadoc)
		 * @see javax.swing.undo.AbstractUndoableEdit#undo()
		 */
		@Override
		public void undo() throws CannotUndoException {
			// TODO Auto-generated method stub
			super.undo();
			p_delegate.execute();
		}
		public void redo() throws javax.swing.undo.CannotRedoException {
			super.redo();
			p_delegate.execute();
		};
	});

}



/**
 * this will only execute the action on redo
 * @param p_delegate
 */
public void addRedoDelegateEdit(final CMDelegate p_delegate) {
	// TODO Auto-generated method stub
	this.addEdit(new AbstractUndoableEdit(){

		/**
		 *
		 */
		private static final long serialVersionUID = 1L;

		public void redo() throws javax.swing.undo.CannotRedoException {
			super.redo();
			p_delegate.execute();
		};
	});
}


/**
 * @param p_delegate
 */
public void addUndoDelegateEdit(final CMDelegate p_delegate) {
	this.addEdit(new AbstractUndoableEdit(){
		/**
		 *
		 */
		private static final long serialVersionUID = 1L;

		/* (non-Javadoc)
		 * @see javax.swing.undo.AbstractUndoableEdit#undo()
		 */
		@Override
		public void undo() throws CannotUndoException {
			// TODO Auto-generated method stub
			super.undo();
			p_delegate.execute();
		}
	});

}
/**
 * @return Returns the isDone.
 * svonborries
 */
public boolean isDone() {
	return isDone;
}
/**
 * @param isDone The isDone to set.
 * svonborries
 */
public void setDone(boolean isDone) {
	this.isDone = isDone;
}

/**
 * @return Number of Edits
 * 07/05/2007
 * svonborries
 */
public int getSize(){
	return edits.size();
}

}