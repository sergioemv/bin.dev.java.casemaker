package bi.view.businessrulesviews.brimport.wizardpane;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.SystemColor;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import bi.view.cells.CMBaseCell;
import bi.view.cells.editors.CMBaseGridCellEditor;
import bi.view.cells.headers.CMCellHeaderDefault;
import bi.view.grids.CMDefaultJSmartGrid;
import bi.view.lang.CMMessages;

import com.eliad.model.AbstractGridModel;
import com.eliad.model.GridContext;
import com.eliad.model.GridModel;
import com.eliad.model.defaults.DefaultGridCellRenderer;
import com.eliad.model.defaults.DefaultStyleModel;
import com.eliad.swing.JSmartGrid;
import com.eliad.swing.JSmartGridHeader;

public class CMJRulesNativeIdentifiersReplacePanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private JScrollPane jScrollPaneGrid = null;
	private CMDefaultJSmartGrid replaceGrid = null;
	private JSmartGridHeader jSmartGridHeader1 = null;
	/**
	 * This is the default constructor
	 */
	public CMJRulesNativeIdentifiersReplacePanel() {
		super();
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
		gridBagConstraints1.fill = GridBagConstraints.BOTH;
		gridBagConstraints1.gridy = 1;
		gridBagConstraints1.weightx = 1.0;
		gridBagConstraints1.weighty = 1.0;
		gridBagConstraints1.ipadx = 0;
		gridBagConstraints1.ipady = 0;
		gridBagConstraints1.insets = new Insets(11, 11, 11, 11);
		gridBagConstraints1.gridx = 0;
		this.setSize(339, 262);
		this.setLayout(new GridBagLayout());
		this.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED), CMMessages.getString("LABEL_CHOOSE_REPLACEMENT_FOR_EXPRESSIONS"), TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, new Font("Tahoma", Font.PLAIN, 11), SystemColor.activeCaption));
		this.add(getJScrollPaneGrid(), gridBagConstraints1);
	}

	/**
	 * This method initializes jScrollPaneGrid	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */
	private JScrollPane getJScrollPaneGrid() {
		if (jScrollPaneGrid == null) {
			jScrollPaneGrid = new JScrollPane(getReplaceGrid());

			jScrollPaneGrid.setColumnHeaderView(getJSmartGridHeader1());
			
		}
		return jScrollPaneGrid;
	}

	/**
	 * This method initializes replaceGrid	
	 * 	
	 * @return com.eliad.swing.JSmartGrid	
	 */
	public CMDefaultJSmartGrid getReplaceGrid() {
		if (replaceGrid == null) {
			replaceGrid = new CMDefaultJSmartGrid() {
				@Override
				protected HashMap getCellClasses() {
					
					HashMap hashMap = new HashMap();
					hashMap.put(CMCellHeaderDefault.class,null);
					hashMap.put(String.class,null);
					hashMap.put(CMBaseCell.class,new JTextField());
					return hashMap;
				}
				@Override
				public void setGridContents(Map map) {
					Map<String,CMBaseCell> l_map = new HashMap<String, CMBaseCell>();
					for (Object ob : map.keySet())
						l_map.put(ob.toString(), new CMBaseCell(replaceGrid, map.get(ob)));
					super.setGridContents(l_map);
				}
				@Override
				public boolean isCellEditable(int arg0, int arg1, boolean arg2) {
					// TODO Auto-generated method stub
					return arg1 != 0;
				}
			};
			replaceGrid.setOpaque(false);
			replaceGrid.setGridColor(Color.GRAY);
			replaceGrid.setSelectionPolicy(JSmartGrid.POLICY_SINGLE);
			replaceGrid.setSelectionUnit(JSmartGrid.UNIT_ROW);
			replaceGrid.setGridContents(new HashMap<String, String>());
			((DefaultStyleModel)replaceGrid.getStyleModel()).setEditor(CMBaseCell.class,new CMBaseGridCellEditor(new JTextField(),null) {
				@Override
				public boolean stopCellEditing() {
					String value = ((JTextField)this.getComponent()).getText();
					getCell().setModel(value);
					return super.stopCellEditing();					
				}
			});
			((DefaultStyleModel)replaceGrid.getStyleModel()).setRenderer(String.class, new DefaultGridCellRenderer() {
				public Component getComponent(Object value, boolean isSelected, boolean hasFocus, boolean isEditable, int row, int column, GridContext context) {
					Component c = super.getComponent(value, isSelected, hasFocus, isEditable, row, column, context);	      
						setBackground(Color.LIGHT_GRAY);
				        setForeground(Color.DARK_GRAY);
						setHorizontalAlignment(JLabel.LEFT);
						setFont(new Font("Dialog",Font.BOLD,11));
			        return c;
				}
			});
			
		}
		return replaceGrid;
	}  //  @jve:decl-index=0:visual-constraint="10,10"

public Map getGridContents() {
	Map map = new HashMap();
	for (int i =0; i<getReplaceGrid().getRowCount();i++)
		map.put(getReplaceGrid().getCellObjectAt(i, 0), ((CMBaseCell)getReplaceGrid().getCellObjectAt(i, 1)).getModel());
	return map;
}


/**
 * This method initializes jSmartGridHeader1	
 * 	
 * @return com.eliad.swing.JSmartGridHeader	
 */
private JSmartGridHeader getJSmartGridHeader1() {
	if (jSmartGridHeader1 == null) {
		jSmartGridHeader1 = new JSmartGridHeader(getReplaceGrid(),JSmartGrid.HORIZONTAL);
		GridModel columnHeaderModel;
		columnHeaderModel=new AbstractGridModel() {
	          public int getRowCount() {
	            return 1;
	          }
	          public int getColumnCount() {
	            return 2;
	          }
	          public Object getValueAt(int row, int column) {
	        	  if (column == 1)
	        		  return new CMCellHeaderDefault(getReplaceGrid(), CMMessages.getString("LABEL_REPLACEMENT"));
	        	  else
	        		  return new CMCellHeaderDefault(getReplaceGrid(), CMMessages.getString("LABEL_EXPRESSION"));
	          }
	        };
	    jSmartGridHeader1.setStyleModel(getReplaceGrid().getStyleModel());
	    jSmartGridHeader1.setModel(columnHeaderModel);
		jSmartGridHeader1.setColumnAutoResizeMode(JSmartGridHeader.AUTO_RESIZE_LAST);
		jSmartGridHeader1.setSelectionMode(JSmartGridHeader.SINGLE_ROW_SELECTION);
		jSmartGridHeader1.setOpaque(false);
		getReplaceGrid().setColumnHeader(jSmartGridHeader1);
		
	}
	return jSmartGridHeader1;
	
}

public List getTabOrder(){
	return Arrays.asList(replaceGrid);
}

/**
 * This method initializes jSmartGridHeader	
 * 	
 * @return com.eliad.swing.JSmartGridHeader	
 */

}