/* Developed by BUSINESS SOFTWARE INNOVATIONS.  .. Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved..*/

package  bi.view.cells;

import model.Element;
import bi.view.cells.renderers.CMCellHeaderElementRenderer;

import com.eliad.model.defaults.DefaultGridCellRenderer;
import com.eliad.swing.JSmartGrid;
public class CMCellElement extends CMBaseCell {
   
    public CMCellElement(JSmartGrid p_grid, Object p_model) {
		super(p_grid, p_model);
		
	}

    //delete this static variables
	//public static final DefaultGridCellEditor defaultEditor = new DefaultGridCellEditor(new JTextField()); 
    
    //public static final CMBaseGridCellRenderer defaultRenderer = new CMBaseGridCellRenderer();
    public static final DefaultGridCellRenderer defaultGridHeaderRenderer = new CMCellHeaderElementRenderer();
    
    public Element getElement(){ return (Element)getModel(); }

    public void setElement(Element element){ setModel(element); }

    //protected Element element;

	}
