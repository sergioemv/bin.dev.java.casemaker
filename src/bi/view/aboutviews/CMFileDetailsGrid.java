package bi.view.aboutviews;

import java.awt.Color;
import java.awt.Component;
import java.util.HashMap;

import javax.swing.BorderFactory;
import javax.swing.JLabel;

import model.BusinessRules;
import bi.view.grids.CMBaseJSmartGrid;
import bi.view.lang.CMMessages;

import com.eliad.model.GenericGridModel;
import com.eliad.model.GridContext;
import com.eliad.model.defaults.DefaultGridCellRenderer;
import com.eliad.model.defaults.DefaultStyleModel;
import com.eliad.util.RulerConstants;

@SuppressWarnings("serial")
public class CMFileDetailsGrid extends CMBaseJSmartGrid {

	private CMGridModel m_CMGridModel;
	private CMStyleModel m_CMStyleModel;
	private String[][] content={{CMMessages.getString("SESSION_NAME"),BusinessRules.SESSION_FILE_VERSION},
								{CMMessages.getString("PROJECT_NAME"),BusinessRules.PROJECT_FILE_VERSION},
								{CMMessages.getString("TESTOBJECT_NAME"),BusinessRules.TESTOBJECT_FILE_VERSION},
								{CMMessages.getString("TESTDATA_FILENAME"),BusinessRules.TESTDATA_FILE_VERSION}};
	public CMFileDetailsGrid() {
		super();
		initialize();
	}

	private void initialize() {

		m_CMGridModel = new CMGridModel(4,2);
		m_CMStyleModel = new CMStyleModel();
		setModels();
		setUIProperties();
		setContents();
	}
	private void setUIProperties() {
		this.setOpaque(false);
		this.setColumnResizable(true);
		this.setAutoResizeMode(RulerConstants.HORIZONTAL);
		this.setGridColor(new Color(127,157,185));
		this.setSelectionCellBorder(BorderFactory.createLineBorder(Color.orange,2));
		this.setFocusHighlightBorder(BorderFactory.createLineBorder(Color.orange,2));
		this.setSelectionBackgroundColor(Color.orange);
		this.setSelectionForegroundColor(Color.black);
		this.setSelectionUnit(com.eliad.swing.JSmartGrid.UNIT_ROW);
		this.setSelectionPolicy(com.eliad.swing.JSmartGrid.POLICY_SINGLE);
	}

	private void setModels() {
		this.setStyleModel(m_CMStyleModel);
		this.setModel(m_CMGridModel);

	}

	private void setContents(){
		for(int i=0;i<4;i++)
			for(int j=0;j<2; j++){
				setValueAt(content[i][j], i, j);
			}
	}

	private class CMGridModel extends GenericGridModel {

		public CMGridModel(int numRows, int numColumns) {
		      super(numRows, numColumns);

		    }
		@Override
		public boolean isCellEditable(int arg0, int arg1) {
			return false;
		}

	}
	private class CMStyleModel extends DefaultStyleModel {


		public CMStyleModel() {
			super();
			this.setRenderer(String.class, new CMCellRendererDetails());
		}
		public class CMCellRendererDetails extends DefaultGridCellRenderer {
            public CMCellRendererDetails() {
            	super();
            }

            public Component getComponent(Object value, boolean isSelected, boolean hasFocus, boolean isEditable, int row,
                int column, GridContext context) {
                    Component c = super.getComponent(value, isSelected, hasFocus, isEditable, row, column, context);
                    this.setHorizontalAlignment(JLabel.LEFT);
                    return c;
            }
        }
	}
	@Override
	protected HashMap<Class, Component> getCellClasses() {
		// TODO Auto-generated method stub
		return null;
	}




}
