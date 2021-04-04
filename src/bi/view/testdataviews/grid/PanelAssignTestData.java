/*
 * Revision:
 * Created: 15-02-2005
 * 
 * Developed by BUSINESS SOFTWARE INNOVATIONS.
 * Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved.
 *
 */
package bi.view.testdataviews.grid;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import bi.view.lang.CMMessages;

import com.eliad.model.AbstractGridModel;
import com.eliad.swing.JSmartGrid;
import com.eliad.swing.JSmartGridHeader;

/**
 * Panel to be used in a create edit test data dialog.
 * @author Franz Nava
 * @version Revision: Date: 15-02-2005 05:18:01 PM
 */
public class PanelAssignTestData extends JPanel {

    private AbstractGridModel modelAvalaibles = null;
    private AbstractGridModel modelSelecteds = null;
    private JSmartGrid gridAvailable = null;
    private JSmartGrid gridSelected = null;
    
    /**
     * If you use "scrollPaneAvailable.getViewport().add(gridAvailable)", the fucking header doesn't work.
     */
    private JScrollPane scrollPaneAvailable = null;
    private JScrollPane scrollPaneSelected = null;
    private JButton buttonAdd = null;
    private JButton buttonDelete = null;    
    
    private List availables = null;
    private List selecteds = null;
    
    /**
     * Construct a <code>PanelAssignTestData</code>.
     * @param availables, a list with the available test data
     * @param selecteds, a list with the selected test data
     */
    public PanelAssignTestData(List availables, List selecteds) {
        this.availables = availables;
        this.selecteds = selecteds;
        
        modelAvalaibles = new GridModelTestData(availables);
        modelSelecteds = new GridModelTestData(selecteds);
        
        gridAvailable = new GridTestData();
        gridAvailable.setModel(modelAvalaibles);
        gridSelected = new GridTestData();
        gridSelected.setModel(modelSelecteds);
        
        init();
    }

    /**
     * Build and initialize the GUI part of the panel.
     */
    private void init() {
        scrollPaneAvailable = new JScrollPane(gridAvailable);
        scrollPaneSelected = new JScrollPane(gridSelected);
        buttonAdd = new JButton();
        buttonDelete = new JButton();        
        
        setBounds(new java.awt.Rectangle(10, 178, 520, 211));	//svonborries_29122005																	
        setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), CMMessages.getString("TESTDATA_ASSIGN_TESTDATA_TO_TESTDATASET"), javax.swing.border.TitledBorder.LEADING, javax.swing.border.TitledBorder.TOP, new java.awt.Font("SansSerif", 0, 11), new java.awt.Color(60, 60, 60)));
        setLayout(null);
        
        gridAvailable.setAutoCreateColumnHeader(false);
        gridAvailable.setColumnHeader(createHeader(gridAvailable));        
        gridSelected.setAutoCreateColumnHeader(false);
        gridSelected.setColumnHeader(createHeader(gridSelected));        
        
        scrollPaneAvailable.setBounds(new java.awt.Rectangle(20, 26, 240, 165));//svonborries_29122005
        scrollPaneSelected.setBounds(new java.awt.Rectangle(340, 26, 240, 165));//svonborries_29122005

        buttonAdd.setText(">>");
        buttonAdd.setBounds(new java.awt.Rectangle(270, 65, 60, 27));//svonborries_29122005
        buttonDelete.setText("<<");
        buttonDelete.setBounds(new java.awt.Rectangle(270, 112, 60, 27));//svonborries_29122005
        buttonAdd.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                buttonAddActionPerformed(e);
            }
        });
        buttonDelete.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                buttonDeleteActionPerformed(e);
            }
        });
        
        add(scrollPaneAvailable);
        add(scrollPaneSelected);
        add(buttonAdd);
        add(buttonDelete);
        
    }
    
    /**
     * 
     */
    private JSmartGridHeader createHeader(JSmartGrid smartGrid) {
        JSmartGridHeader gridHeader = new JSmartGridHeader(smartGrid, JSmartGrid.HORIZONTAL, new ColumnHeaderModel(), null, null);
        gridHeader.addMouseListener(new ColumnHeaderListener());
  	  	gridHeader.setColumnWidth(0,70);
      	gridHeader.setColumnWidth(1,70);
        gridHeader.setColumnWidth(2,30);
        gridHeader.setColumnWidth(3,70);//svonborries_29122005
        gridHeader.setBackground(new Color(36,38,116));
      	gridHeader.setSelectionBackgroundColor(new Color(36,38,116));
      	gridHeader.setSelectionForegroundColor(new Color(252,254,252));
      	gridHeader.setGridColor(new Color(196,194,196));
      	gridHeader.setFont(new Font("Dialog",Font.PLAIN,12));
      	gridHeader.setForeground(new Color(252,254,252));
        gridHeader.setBorder(BorderFactory.createRaisedBevelBorder());
        gridHeader.setRowResizable(false);
        
        return gridHeader;
    }
    
    /**
     * event handler method of the buttonAddAction.
     * The crap <code>JSmartGrid.getSelectedRows</code> some times return an empty array or worst 
     * an array like {0} when there are no elements selected, one must be careful with this.
     * @param e, the event
     */
    private void buttonAddActionPerformed(ActionEvent e) {
        int[] selected = gridAvailable.getSelectedRows();
        if (availables.isEmpty() || (selected.length == 0)) {
            return;
        }        
        
        List toBeDeleted = new ArrayList();
        
        for (int i = 0; i < selected.length; i++) {
            Object testData = availables.get(selected[i]);
            toBeDeleted.add(testData);
            selecteds.add(testData);
        }
        
        availables.removeAll(toBeDeleted);
        
        modelAvalaibles.fireGridRowsDeleted(selected[0], selected.length);
        modelSelecteds.fireGridRowsInserted(selecteds.size() - 1, selected.length);
        
        gridAvailable.resizeAndRepaint();
        gridSelected.resizeAndRepaint();
    }

    /**
     * event handler method of the buttonDeleteAction
     * The crap <code>JSmartGrid.getSelectedRows</code> some times return an empty array or worst 
     * an array like {0} when there are no elements selected, one must be careful with this. 
     * @param e, the event
     */    
    private void buttonDeleteActionPerformed(ActionEvent e) {
        int[] selected = gridSelected.getSelectedRows();
        if (selecteds.isEmpty() || (selected.length == 0)) {
            return;
        }
        
        List toBeDeleted = new ArrayList();
        
        for (int i = 0; i < selected.length; i++) {
            Object testData = selecteds.get(selected[i]); 
            toBeDeleted.add(testData);
            availables.add(testData);
        }
        
        selecteds.removeAll(toBeDeleted);
        
        modelSelecteds.fireGridRowsDeleted(selected[0], selected.length);
        modelAvalaibles.fireGridRowsInserted(availables.size() - 1, selected.length);
        
        gridAvailable.resizeAndRepaint();
        gridSelected.resizeAndRepaint();
    }

	/**
	 * @return Returns the buttonAdd.
	 */
	public JButton getButtonAdd() {
		return buttonAdd;
	}

	/**
	 * @param buttonAdd The buttonAdd to set.
	 */
	public void setButtonAdd(JButton buttonAdd) {
		this.buttonAdd = buttonAdd;
	}

	/**
	 * @return Returns the buttonDelete.
	 */
	public JButton getButtonDelete() {
		return buttonDelete;
	}

	/**
	 * @param buttonDelete The buttonDelete to set.
	 */
	public void setButtonDelete(JButton buttonDelete) {
		this.buttonDelete = buttonDelete;
	}

	/**
	 * @return Returns the gridAvailable.
	 */
	public JSmartGrid getGridAvailable() {
		return gridAvailable;
	}

	/**
	 * @param gridAvailable The gridAvailable to set.
	 */
	public void setGridAvailable(JSmartGrid gridAvailable) {
		this.gridAvailable = gridAvailable;
	}

	/**
	 * @return Returns the gridSelected.
	 */
	public JSmartGrid getGridSelected() {
		return gridSelected;
	}

	/**
	 * @param gridSelected The gridSelected to set.
	 */
	public void setGridSelected(JSmartGrid gridSelected) {
		this.gridSelected = gridSelected;
	}
    

}

