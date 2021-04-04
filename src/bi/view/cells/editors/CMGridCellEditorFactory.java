package bi.view.cells.editors;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JTextField;
import bi.view.cells.CMCellElementDescription;
import bi.view.cells.CMCellElementGuiObject;
import bi.view.cells.CMCellElementName;
import bi.view.cells.CMCellEquivalenceClassDescription;
import bi.view.cells.CMCellEquivalenceClassState;
import bi.view.cells.CMCellEquivalenceClassValue;
import bi.view.cells.CMCellExpectedResultName;
import bi.view.cells.CMCellExpectedResultValue;
import bi.view.cells.CMCellSelectAllEquivalenceClassesOfElement;
import bi.view.cells.CMCellSelectEquivalenceClass;
import bi.view.cells.CMCellTDStructureClassState;
import bi.view.cells.CMCellTDStructureField;
import bi.view.cells.CMCellTDStructureLength;
import bi.view.cells.CMCellTDStructureName;
import bi.view.cells.CMCellTDStructureNewColumn;
import bi.view.cells.CMCellTDStructureObjectTypes;
import bi.view.cells.CMCellTDStructurePrefix;
import bi.view.cells.CMCellTDStructureSuffix;
import bi.view.cells.CMCellTDStructureValue;
import bi.view.cells.CMFilePathCell;
import bi.view.cells.testdatasetreports.editors.CMGridCellEditorPathTestDataSetReports;
import bi.view.utils.CMBaseJComboBox;

import com.eliad.model.defaults.DefaultGridCellEditor;

public interface CMGridCellEditorFactory {

	public static final CMGridCellEditorFactory  INSTANCE = new CMGridCellEditorFactory() {
		public DefaultGridCellEditor createEditor(Class cell, JComponent component) {

			if (cell == CMCellElementName.class)
				return new CMGridCellEditorElementName();

			if (cell == CMCellEquivalenceClassState.class)
					return new CMGridCellEditorEquivalenceClassState();

			if (cell == CMCellElementGuiObject.class)
				if (component!=null)
				   return new CMGridCellEditorElementGuiObject((CMBaseJComboBox)component);

			if (cell == CMCellElementDescription.class)
				return new CMGridCellEditorElementDescription();

			if (cell == CMCellEquivalenceClassValue.class)
				return new CMGridCellEditorEquivalenceClassValue();

			if (cell == CMCellEquivalenceClassDescription.class)
				return new CMGridCellEditorEquivalenceClassDescription();

			if (cell == CMCellSelectAllEquivalenceClassesOfElement.class)
				return new CMGridCellEditorSelectAllEquivalenceClasses();
			if (cell == CMCellSelectEquivalenceClass.class)
				return new CMGridCellEditorSelectEquivalenceClass();
			if (cell == CMCellExpectedResultName.class )
				return new CMGridCellEditorExpectedResultName((JTextField) component,null);
			if (cell == CMCellExpectedResultValue.class )
				return new CMGridCellEditorExpectedResultValue((JTextField) component,null);
			//ccastedo begins 31.08.06
			if (cell == CMFilePathCell.class)
				return new CMGridCellEditorPathTestDataSetReports((JTextField) component,null);			
			if (cell == CMCellTDStructureField.class)
				return new CMGridCellEditorTDStructureField();			
			if (cell == CMCellTDStructureName.class)
				return new CMGridCellEditorTDStructureName();
			
			if (cell == CMCellTDStructureClassState.class)				
					return new CMGridCellEditorTDStructureType();
			
			if (cell == CMCellTDStructureObjectTypes.class)
				if (component!=null)
					return new CMGridCellEditorTDStructureObjectTypes();
			if (cell == CMCellTDStructureNewColumn.class)
				return new CMGridCellEditorTDStructureNewColumn();
			if (cell == CMCellTDStructureLength.class)
				return new CMGridCellEditorTDStructureLength();
			if (cell == CMCellTDStructurePrefix.class)
				return new CMGridCellEditorTDStructurePreffix();
			if (cell == CMCellTDStructureSuffix.class)
				return new CMGridCellEditorTDStructureSuffix();
			if (cell == CMCellTDStructureValue.class)
				return new CMGridCellEditorTDStructureValue();
			//ccastedo ends 15.09.06
			if (component instanceof JComboBox)
				return new DefaultGridCellEditor((JComboBox)component);
			if (component instanceof JCheckBox)
				return new DefaultGridCellEditor((JCheckBox)component);
			if (component instanceof JTextField)
				return new DefaultGridCellEditor((JTextField)component);
			
			return null;

		}
	};

	public DefaultGridCellEditor createEditor(Class cell, JComponent component);


}
