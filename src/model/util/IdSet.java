package model.util;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 ****************************************************************************** Developed by BUSINESS SOFTWARE INNOVATIONS.
 * Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved.
 */
public class IdSet implements Cloneable {
	private TreeSet<Integer> ids;
    public Object clone() {
      
      IdSet newid;
	try {
		newid = (IdSet) super.clone();
		newid.getInternalIds().addAll(this.getInternalIds());
	    return newid;
	} catch (CloneNotSupportedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
    return null;  
	 
    }

//    public int generateIds(UndoableEdit p_ce)
//    {
//    	CMCompoundEdit ce;
//    	if(p_ce!=null)
//    		ce = (CMCompoundEdit) p_ce;
//    	else
//    		ce = new CMCompoundEdit();
//        boolean sw= false;
//        int i=0;
//		while(!sw && i < getIds().size())
//        {
//			if(((Boolean)getIds().elementAt(i)).booleanValue())
//            {
//                i++;
//            }
//            else
//            {
//                sw = true;
//
//                ce.addEdit(CMModelEditFactory.INSTANCE.createAddElementToidSet(this,i));
//                getIds().setElementAt(new Boolean(true),i);
//            }
//        }
//        if(sw){
//            i=i+1;
//            return i;
//        }
//        else
//            return -1;
//    }
    @SuppressWarnings("unchecked")
	public void deleteId(int x)
    {
    	//CMCompoundEdit ce = new CMCompoundEdit();
    	//ce.addEdit(CMModelEditFactory.INSTANCE.createDeleteElementToidSet(this,x-1));
    	if (getInternalIds().contains(x))
    		ids.remove(x);
		//return ce;
    }
    public void deleteIds()//UndoableEdit p_ce)
    {
    	/*CMCompoundEdit ce;
    	if(p_ce==null)
    		ce = new CMCompoundEdit();
    	else
    		ce = (CMCompoundEdit) p_ce;*/
        /*for( int i = 0; i < max; i++)
        {
        	if ((Boolean)ids.get(i)!=false)
        	{
        		ce.addEdit(CMModelEditFactory.INSTANCE.createDeleteElementToidSet(this,i));
        		ids.setElementAt(new Boolean(false),i);
        	}
		}*/
    	//ce.addEdit(CMModelEditFactory.INSTANCE.createChangeIdsIdSet(this,null));
    	getInternalIds().clear();
    }
    /**
	 * @param p_object
	 */


	public void registerId(int pos)
    {
		if (pos>=0)
			getInternalIds().add(pos);
    }

	public void registerIds(List<Integer> p_ids){
		getInternalIds().addAll(p_ids);
	}
    public int nextValidId()
    {
    	int i = 1;
    	for (Integer id : getInternalIds()){
    		if (id!=i) break;
    		i++;
    	}
    	return i;
//		boolean sw= false;
//        int i=0;
//		while(!sw && i < getIds().size())
//        {
//			if(((Boolean)getIds().elementAt(i)).booleanValue())
//            {
//                i++;
//            }
//            else
//            {
//                sw = true;
//            }
//        }
//        if(sw){
//            i=i+1;
//            return i;
//        }
//        else
//            return -1;
    }

    public int nextValidId(int pos)
    {

    	int i = pos;
    	if (i<=0) i=1;
    	for (Integer id : getInternalIds()){
    		if (id<i) continue;
    		if (id!=i)  break;
    		i++;
    	}
    	return i;

    }

    public boolean idExist(int p_id)
    {
        return this.getInternalIds().contains(p_id);
    }

	private Set<Integer> getInternalIds() {
		if (ids== null ){
			ids = new TreeSet<Integer>();
		}
		return this.ids;
	}

	public int registerNextValidId() {
		int id = nextValidId();
		registerId(id);
		return id;
	}
	public int size(){
		return getInternalIds().size();
	}
	//the internal set is readonly for outsiders!
	public Set<Integer> getIds(){
		return Collections.unmodifiableSet(getInternalIds());
	}
}
