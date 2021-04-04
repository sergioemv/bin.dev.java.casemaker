package model.edit;

import javax.swing.undo.AbstractUndoableEdit;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;
import javax.swing.undo.UndoableEdit;

import model.CMError;
import model.util.CMErrorsBean;

public class CMRemoveErrorModelEdit extends AbstractUndoableEdit implements
UndoableEdit {

CMErrorsBean parent;
CMError error;
public CMRemoveErrorModelEdit(CMErrorsBean parent, CMError error) {
this.parent = parent;
this.error = error;
}
@Override
public void redo() throws CannotRedoException {
this.parent.removeError(error);
super.redo();
}
@Override
public boolean canRedo() {

return this.parent.getErrors().contains(error);
}
@Override
public void undo() throws CannotUndoException {
// TODO Auto-generated method stub
this.parent.addError(error);
super.undo();
}
@Override
public boolean canUndo() {
return !this.parent.getErrors().contains(error);
}
}
