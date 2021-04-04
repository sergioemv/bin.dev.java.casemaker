package bi.view.cells.renderers;


import bi.view.cells.CMCellDependency;
import bi.view.cells.CMCellElementGuiObject;
import bi.view.cells.CMCellElementName;
import bi.view.cells.CMCellEquivalenceClassDescription;
import bi.view.cells.CMCellEquivalenceClassState;
import bi.view.cells.CMCellEquivalenceClassValue;
import bi.view.cells.CMCellExpectedResultValue;
import bi.view.cells.CMCellSelectAllEquivalenceClassesOfElement;
import bi.view.cells.CMCellSelectEquivalenceClass;
import bi.view.cells.CMCellTDStructureClassState;
import bi.view.cells.CMCellTDStructureField;
import bi.view.cells.CMCellTDStructureGlobal;
import bi.view.cells.CMCellTDStructureKey;
import bi.view.cells.CMCellTDStructureLength;
import bi.view.cells.CMCellTDStructureName;
import bi.view.cells.CMCellTDStructureNewColumn;
import bi.view.cells.CMCellTDStructureObjectTypes;
import bi.view.cells.CMCellTDStructurePrefix;
import bi.view.cells.CMCellTDStructureSuffix;
import bi.view.cells.CMFilePathCell;
import bi.view.cells.CMStdCombinationDescriptionCellView;
import bi.view.cells.headers.CMCellHeaderDefault;
import bi.view.cells.testdatasetreports.renderers.CMTestDataSetReportsPathCellRenderer;

import com.eliad.model.GridCellRenderer;
import com.eliad.model.defaults.DefaultGridCellRenderer;


public interface CMCellRendererFactory {

	public static final CMCellRendererFactory INSTANCE = new CMCellRendererFactory() {

		public GridCellRenderer createRenderer(Class cell) {

			if (cell == CMCellElementName.class)
				return new CMElementNameCellRenderer();
			if  (cell == CMCellHeaderDefault.class)
				return CMCellHeaderDefault.defaultGridHeaderRenderer;
			if (cell == CMCellElementGuiObject.class )
				return new DefaultGridCellRenderer();

			if ((cell== CMCellEquivalenceClassValue.class)||(cell== CMCellEquivalenceClassState.class)
					|| (cell ==CMCellEquivalenceClassDescription.class))
				return new CMEquivalenceClassCellRenderer();
			if (cell==CMCellDependency.class || cell == CMStdCombinationDescriptionCellView.class  )
				return new CMCellDependencyRenderer();
			if ((cell == CMCellSelectAllEquivalenceClassesOfElement.class)||(cell==CMCellSelectEquivalenceClass.class))
				return new CMCheckBoxRenderer();
			if (cell == CMCellExpectedResultValue.class)
				return new CMCellExpectedResultRenderer();
			//ccastedo begins 01.09.06
			if (cell == CMFilePathCell.class)
				return new CMTestDataSetReportsPathCellRenderer();
			//ccastedo ends 01.09.06
			//ccastedo begins 15.09.06
			if (cell == CMCellTDStructureKey.class || cell == CMCellTDStructureGlobal.class ||
				cell == CMCellTDStructureField.class || cell == CMCellTDStructureName.class ||
				cell == CMCellTDStructureObjectTypes.class || cell == CMCellTDStructureNewColumn.class ||
				cell == CMCellTDStructureClassState.class || cell == CMCellTDStructureLength.class ||
				cell == CMCellTDStructurePrefix.class || cell == CMCellTDStructureSuffix.class /*||
				cell == CMCellTDStructureFormat.class*/)	{//svonborries_19072007
			//	return new CMBaseGridCellRenderer();
						return new CMGridTDStructureRenderer();
			}




			//ccastedo ends 15.09.06
			return new CMBaseGridCellRenderer();
		}

	};
	GridCellRenderer createRenderer(Class cell);

}
