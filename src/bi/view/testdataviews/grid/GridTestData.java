/*
 * Revision:
 * Created: 17-02-2005
 *
 * Developed by BUSINESS SOFTWARE INNOVATIONS.
 * Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved.
 *
 */
package bi.view.testdataviews.grid;

import java.awt.Color;
import java.awt.Component;
import java.util.HashMap;

import javax.swing.BorderFactory;

import bi.view.grids.CMBaseJSmartGrid;

import com.eliad.util.RulerConstants;

/**
 * @author Franz Nava
 * @version Revision: Date: 17-02-2005 04:00:55 PM
 */
public class GridTestData extends CMBaseJSmartGrid {

    /**
     * Constructor
     */
    public GridTestData() {
        init();
    }

    /**
     * Build and initialize the grid.
     */
    private void init() {
		//set grid model
		//set grid style model
        //set event listeners

		setOpaque(false);
		setColumnResizable(true);
        setAutoResizeMode(RulerConstants.HORIZONTAL);
	    setGridColor(new Color(127,157,185));
		setSelectionCellBorder(BorderFactory.createLineBorder(Color.orange,2));
		setFocusHighlightBorder(BorderFactory.createLineBorder(Color.orange,2));
		setSelectionBackgroundColor(Color.orange);
		setSelectionForegroundColor(Color.black);
		setSelectionUnit(com.eliad.swing.JSmartGrid.UNIT_ROW);
		setSelectionPolicy(com.eliad.swing.JSmartGrid.POLICY_MULTIRANGE);
    }

	@Override
	protected HashMap<Class, Component> getCellClasses() {
		// TODO Auto-generated method stub
		return null;
	}
}
