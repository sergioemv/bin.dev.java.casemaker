/*
 * Revision:
 * Created: 27-12-2004
 *
 * Developed by BUSINESS SOFTWARE INNOVATIONS.
 * Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved.
 *
 */
package bi.view.undomanagementviews.undoviews;

import java.util.Enumeration;
import java.util.Vector;

import javax.swing.UIManager;
import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;
import javax.swing.undo.CompoundEdit;
import javax.swing.undo.UndoableEdit;

import bi.view.mainframeviews.CMApplication;

/**
 * The only purpose of this class is may to do several redos at a time, so don't game with this class,
 * and it's based in <code>javax.swing.undo.UndoManager</code>
 * @see #javax.swing.undo.UndoManager
 * @see #editToBeRedone
 * @author franz marcelo
 * @version Revision: Date: 27-12-2004 10:30:14 AM
 */
public class UndoManager extends CompoundEdit implements UndoableEditListener {

    int indexOfNextAdd;
    int limit;

    public UndoManager() {
        super();
        indexOfNextAdd = 0;
        limit = 200;
        edits.ensureCapacity(limit);
    }

    public Vector<UndoableEdit> getEdits() {return edits;}
    /**
     * Returns the maximum number of edits this UndoManager will
     * hold. Default value is 100.
     *
     * @see #addEdit
     * @see #setLimit
     */
    public synchronized int getLimit() {
        return limit;
    }

    /**
     * Empty the undo manager, sending each edit a die message
     * in the process.
     */
    public synchronized void discardAllEdits() {
        Enumeration cursor = edits.elements();
        while (cursor.hasMoreElements()) {
            UndoableEdit e = (UndoableEdit)cursor.nextElement();
            e.die();
        }
        edits = new Vector(limit);
        indexOfNextAdd = 0;
        // PENDING(rjrjr) when vector grows a removeRange() method
        // (expected in JDK 1.2), trimEdits() will be nice and
        // efficient, and this method can call that instead.
    }

    /**
     * Reduce the number of queued edits to a range of size limit,
     * centered on indexOfNextAdd.
     */
    protected void trimForLimit() {
        if (limit >= 0) {
            int size = edits.size();
//          System.out.print("limit: " + limit +
//                           " size: " + size +
//                           " indexOfNextAdd: " + indexOfNextAdd +
//                           "\n");

            if (size > limit) {
                int halfLimit = limit/2;
                int keepFrom = indexOfNextAdd - 1 - halfLimit;
                int keepTo   = indexOfNextAdd - 1 + halfLimit;

                // These are ints we're playing with, so dividing by two
                // rounds down for odd numbers, so make sure the limit was
                // honored properly. Note that the keep range is
                // inclusive.

                if (keepTo - keepFrom + 1 > limit) {
                    keepFrom++;
                }

                // The keep range is centered on indexOfNextAdd,
                // but odds are good that the actual edits Vector
                // isn't. Move the keep range to keep it legal.

                if (keepFrom < 0) {
                    keepTo -= keepFrom;
                    keepFrom = 0;
                }
                if (keepTo >= size) {
                    int delta = size - keepTo - 1;
                    keepTo += delta;
                    keepFrom += delta;
                }


                trimEdits(keepTo+1, size-1);
                trimEdits(0, keepFrom-1);
            }
        }
    }

    /**
     * Tell the edits in the given range (inclusive) to die, and
     * remove them from edits. from > to is a no-op.
     */
    protected void trimEdits(int from, int to) {
        if (from <= to) {
//          System.out.println("Trimming " + from + " " + to + " with index " +
//                           indexOfNextAdd);
            for (int i = to; from <= i; i--) {
                UndoableEdit e = (UndoableEdit)edits.elementAt(i);
//              System.out.println("JUM: Discarding " +
//                                 e.getUndoPresentationName());
                e.die();
                // PENDING(rjrjr) when Vector supports range deletion (JDK
                // 1.2) , we can optimize the next line considerably.
                edits.removeElementAt(i);
            }

            if (indexOfNextAdd > to) {
//              System.out.print("...right...");
                indexOfNextAdd -= to-from+1;
            } else if (indexOfNextAdd >= from) {
//              System.out.println("...mid...");
                indexOfNextAdd = from;
            }

//          System.out.println("new index " + indexOfNextAdd);
        }
    }

    /**
     * Set the maximum number of edits this UndoManager will hold. If
     * edits need to be discarded to shrink the limit, they will be
     * told to die in the reverse of the order that they were added.
     *
     * @see #addEdit
     * @see #getLimit
     */
    public synchronized void setLimit(int l) {
        if (!isInProgress()) throw new RuntimeException("Attempt to call UndoManager.setLimit() after UndoManager.end() has been called");
        limit = l;
        trimForLimit();
    }


    /**
     * Returns the the next significant edit to be undone if undo is
     * called. May return null
     */
    protected UndoableEdit editToBeUndone() {
        int i = indexOfNextAdd;
        while (i > 0) {
            UndoableEdit edit = (UndoableEdit)edits.elementAt(--i);//svonborries_01032006
            if (edit.isSignificant()) {
                return edit;
            }
        }

        return null;
    }

    /**
     * Returns the the next significant edit to be redone if redo is
     * called. May return null.
     * <b>Changes:</b> it return the last insignificant <code>Undoable</code> object, so
     * the <code>redo</code> method redo all <code>Undoable</code> objects up to <code>editToBeRedone</code>.
     */
    protected UndoableEdit editToBeRedone() {
        int count = edits.size();
        int i = indexOfNextAdd;

        while (i < count) {
        		UndoableEdit edit = (UndoableEdit)edits.elementAt(i++);
                if (i >= count ) {
                    return edit;
                }
                UndoableEdit nextEdit = (UndoableEdit)edits.elementAt(i);
                if (nextEdit.isSignificant()) {
                    return edit;
                }


        }

        return null;
    }

    /**
     * Undoes all changes from indexOfNextAdd to edit. Updates indexOfNextAdd accordingly.
     */
    protected void undoTo(UndoableEdit edit) throws CannotUndoException {
        boolean done = false;
        while (!done) {
            UndoableEdit next = (UndoableEdit)edits.elementAt(--indexOfNextAdd);
            next.undo();
            done = next == edit;
        }
    }

    /**
     * Redoes all changes from indexOfNextAdd to edit. Updates indexOfNextAdd accordingly.
     */
    protected void redoTo(UndoableEdit edit) throws CannotRedoException {
        boolean done = false;
        while (!done) {
            UndoableEdit next = (UndoableEdit)edits.elementAt(indexOfNextAdd++);
            next.redo();
            done = next == edit;
        }
    }

    /**
     * Undo or redo as appropriate. Suitable for binding to an action
     * that toggles between these two functions. Only makes sense
     * to send this if limit == 1.
     *
     * @see #canUndoOrRedo
     * @see #getUndoOrRedoPresentationName
     */
    public synchronized void undoOrRedo() throws CannotRedoException,
        CannotUndoException {
        if (indexOfNextAdd == edits.size()) {
            undo();
        } else {
            redo();
        }
    }

    /**
     * Return true if calling undoOrRedo will undo or redo. Suitable
     * for deciding to enable a command that toggles between the two
     * functions, which only makes sense to use if limit == 1.
     *
     * @see #undoOrRedo
     */
    public synchronized boolean canUndoOrRedo() {
        if (indexOfNextAdd == edits.size()) {
            return canUndo();
        } else {
            return canRedo();
        }
    }

    /**
     * If this UndoManager is inProgress, undo the last significant
     * UndoableEdit before indexOfNextAdd, and all insignificant edits back to
     * it. Updates indexOfNextAdd accordingly.
     *
     * <p>If not inProgress, indexOfNextAdd is ignored and super's routine is
     * called.</p>
     *
     * @see CompoundEdit#end
     */
    public synchronized void undo() throws CannotUndoException {
        if (isInProgress()) {
            UndoableEdit edit = editToBeUndone();
            if (edit == null) {
                throw new CannotUndoException();
            }
            undoTo(edit);
        } else {
            super.undo();
        }
    }

    /**
     * Overridden to preserve usual semantics: returns true if an undo
     * operation would be successful now, false otherwise
     */
    public synchronized boolean canUndo() {
        if (isInProgress()) {
            UndoableEdit edit = editToBeUndone();
            return edit != null && edit.canUndo();
        } else {
            return super.canUndo();
        }
    }

    /**
     * If this <code>UndoManager</code> is <code>inProgress</code>,
     * redoes the last significant <code>UndoableEdit</code> at
     * <code>indexOfNextAdd</code> or after, and all insignificant
     * edits up to it. Updates <code>indexOfNextAdd</code> accordingly.
     *
     * <p>If not <code>inProgress</code>, <code>indexOfNextAdd</code>
     * is ignored and super's routine is called.</p>
     *
     * @see CompoundEdit#end
     */
    public synchronized void redo() throws CannotRedoException {
        if (isInProgress()) {
            UndoableEdit edit = editToBeRedone();
            if (edit == null) {
                throw new CannotRedoException();
            }
            redoTo(edit);
        } else {
            super.redo();
        }
    }

    /**
     * Overridden to preserve usual semantics: returns true if a redo
     * operation would be successful now, false otherwise
     */
    public synchronized boolean canRedo() {
        if (isInProgress()) {
            UndoableEdit edit = editToBeRedone();
            return edit != null && edit.canRedo();
        } else {
            return super.canRedo();
        }
    }

    /**
     * If inProgress, inserts anEdit at indexOfNextAdd, and removes
     * any old edits that were at indexOfNextAdd or later. The die
     * method is called on each edit that is removed is sent, in the
     * reverse of the order the edits were added. Updates
     * indexOfNextAdd.
     *
     * <p>If not <code>inProgress</code>, acts as a
     * <code>CompoundEdit</code>.
     *
     * @param anEdit the edit to be added
     * @see CompoundEdit#end
     * @see CompoundEdit#addEdit
     */
    public synchronized boolean addEdit(UndoableEdit anEdit) {
        boolean retVal;

        // Trim from the indexOfNextAdd to the end, as we'll
        // never reach these edits once the new one is added.
        int sizeEdits = edits.size();
        trimEdits(indexOfNextAdd, sizeEdits-1);

        retVal = super.addEdit(anEdit);
	if (isInProgress()) {
	  retVal = true;
	}

        // Maybe super added this edit, maybe it didn't (perhaps
        // an in progress compound edit took it instead. Or perhaps
        // this UndoManager is no longer in progress). So make sure
        // the indexOfNextAdd is pointed at the right place.
        indexOfNextAdd = edits.size();

        // Enforce the limit
        trimForLimit();

        // after addin a edit the redos are blocked
        CMApplication.frame.getM_CMUndoMediator().setM_NumberOfRedoableEditObjects(0);

        return retVal;
    }


    /**
     * Sending end() to an UndoManager turns it into a plain old
     * (ended) CompoundEdit.
     *
     * <p> Calls super's end() method (making inProgress false), then
     * sends die() to the unreachable edits at indexOfNextAdd and
     * beyond, in the reverse of the order in which they were added.
     *
     * @see CompoundEdit#end
     */
    public synchronized void end() {
	super.end();
        this.trimEdits(indexOfNextAdd, edits.size()-1);
    }

    /**
     * Return the appropriate name for a command that toggles between
     * undo and redo.  Only makes sense to use such a command if limit
     * == 1 and we're not in progress.
     */
    public synchronized String getUndoOrRedoPresentationName() {
        if (indexOfNextAdd == edits.size()) {
            return getUndoPresentationName();
        } else {
            return getRedoPresentationName();
        }
    }

    /**
     * If inProgress, returns getUndoPresentationName of the
     * significant edit that will be undone when undo() is invoked.
     * If there is none, returns AbstractUndoableEdit.undoText from the
     * defaults table.
     *
     * <p>If not inProgress, acts as a CompoundEdit</p>
     *
     * @see     #undo
     * @see     CompoundEdit#getUndoPresentationName
     */
    public synchronized String getUndoPresentationName() {
        if (isInProgress()) {
            if (canUndo()) {
                return editToBeUndone().getUndoPresentationName();
            } else {
                return UIManager.getString("AbstractUndoableEdit.undoText");
            }
        } else {
            return super.getUndoPresentationName();
        }
    }

    /**
     * If inProgress, returns getRedoPresentationName of the
     * significant edit that will be redone when redo() is invoked.
     * If there is none, returns AbstractUndoableEdit.redoText from the
     * defaults table.
     *
     * <p>If not inProgress, acts as a CompoundEdit</p>
     *
     * @see     #redo
     * @see     CompoundEdit#getUndoPresentationName
     */
    public synchronized String getRedoPresentationName() {
        if (isInProgress()) {
            if (canRedo()) {
                return editToBeRedone().getRedoPresentationName();
            } else {
                return UIManager.getString("AbstractUndoableEdit.redoText");
            }
        } else {
            return super.getRedoPresentationName();
        }
    }

    /**
     * Called by the UndoabledEdit sources this UndoManager listens
     * to. Calls addEdit with e.getEdit().
     *
     * @see #addEdit
     */
    public void undoableEditHappened(UndoableEditEvent e) {
        addEdit(e.getEdit());
    }

    /**
     * Returns a string that displays and identifies this
     * object's properties.
     *
     * @return a String representation of this object
     */
    public String toString() {
        return super.toString() + " limit: " + limit +
            " indexOfNextAdd: " + indexOfNextAdd;
    }

	/**
	 * @param i
	 * @param j
	 */
	public void trimEditsNotDie(int from, int to) {
        if (from <= to) {
//          System.out.println("Trimming " + from + " " + to + " with index " +
//                           indexOfNextAdd);
            for (int i = to; from <= i; i--) {
                UndoableEdit e = (UndoableEdit)edits.elementAt(i);
//              System.out.println("JUM: Discarding " +
//                                 e.getUndoPresentationName());
              //  e.die();
                // PENDING(rjrjr) when Vector supports range deletion (JDK
                // 1.2) , we can optimize the next line considerably.
                edits.removeElementAt(i);
            }

            if (indexOfNextAdd > to) {
//              System.out.print("...right...");
                indexOfNextAdd -= to-from+1;
            } else if (indexOfNextAdd >= from) {
//              System.out.println("...mid...");
                indexOfNextAdd = from;
            }

//          System.out.println("new index " + indexOfNextAdd);
        }
    }

}
