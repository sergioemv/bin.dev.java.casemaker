package bi.view.grids;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import bi.view.cells.headers.CMCellHeaderDefault;

import com.eliad.model.GenericGridModel;
import com.eliad.model.defaults.DefaultStyleModel;

public class CMDefaultJSmartGrid extends CMBaseJSmartGrid {
private DefaultStyleModel defaultStyleModel;
private GenericGridModel genericGridModel;

public CMDefaultJSmartGrid() {
	initGUI();
	setModel(getGenericGridModel());
}

private void initGUI() {
	// TODO Auto-generated method stub
	setSelectionCellBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.orange,1));
	setFocusHighlightBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.orange,1));
	setColumnMargin(0);
	setStyleModel(getDefaultStyleModel());
	setGridNavigationPolicy(new CMDefaultGridNavigationPolicy(this));
	initializeCellRenderers();
    initializeCellEditors();
}
private DefaultStyleModel getDefaultStyleModel() {
	if (defaultStyleModel == null) {
		defaultStyleModel = new DefaultStyleModel();

	}
	return defaultStyleModel;
}
@Override
protected HashMap getCellClasses() {
	HashMap map = new HashMap();
	map.put(CMCellHeaderDefault.class,null);
	//map.put()
	return map;
}
public GenericGridModel getGenericGridModel() {
	if (genericGridModel == null)
		genericGridModel = new GenericGridModel();
	return genericGridModel;
}
public void setGridContents(Map map) {
	getGenericGridModel().setRowCount(0);
	getGenericGridModel().setColumnCount(2);
	for (Object key : map.keySet())
		getGenericGridModel().addRow(Arrays.asList(key,map.get(key)).toArray());
}
}
