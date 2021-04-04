package bi.view.grids;

import java.awt.Component;

public class CMDefaultGridNavigationPolicy implements CMGridNavigationPolicy{
	private CMBaseJSmartGrid m_parent = null;

	public CMDefaultGridNavigationPolicy(CMBaseJSmartGrid p_Parent){
		this.m_parent = p_Parent;
		//setDefaultGridNav(this);
	}

    public Component getParent(){
    	return m_parent;
    }

    public void setParent(CMBaseJSmartGrid p_parent){
    	this.m_parent =  p_parent;
    }


    public void goToNextCell(int row, int column){
    	if (row < m_parent.getRowCount()){
    		if(column < m_parent.getColumnCount()-1){
    			m_parent.changeSelection(row,column+1,false,false);
	    	}

	    	else{
	    		if (row < m_parent.getRowCount()-1)
	    			m_parent.changeSelection(row+1,0,false,false);
	    		else
	    			m_parent.changeSelection(0,0,false,false);
	    	}
    	}
    	else{
    		m_parent.changeSelection(0,0,false,false);
    	}

    }



}
