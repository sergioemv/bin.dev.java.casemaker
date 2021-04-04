package bi.controller.editcontrol;

import java.util.ArrayList;
import java.util.List;

import javax.swing.undo.UndoableEdit;

public abstract class CMDefaultEditController {
	private List<String> warningMessages;

	public final List<String> getWarningMessages() {
		if (warningMessages == null)
			warningMessages = new ArrayList<String>();
		return warningMessages;
	}

	public abstract UndoableEdit applyChanges() throws Exception;
}
