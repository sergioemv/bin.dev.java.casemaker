package model.brmodel;
import java.util.Vector;

/**
 ****************************************************************************** Developed by BUSINESS SOFTWARE INNOVATIONS.
 * Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved.
 */
public class RawDependency {
    public RawDependency(CombinationsBAPair combiPair) {
		RelationalPair relPair;
        combiPairs=new Vector();
        businessObjectsInvolved =new Vector();
		objectCount=combiPair.getDifferentObjectsCount();
        this.setBusinessObjectsInvolved((Vector)combiPair.getDifferentObjectsInvolved().clone());
        combiPairs.addElement(combiPair);

    }
    public RawDependency(){
		combiPairs=new Vector();
        businessObjectsInvolved =new Vector();
    }

    public boolean sameObjects(CombinationsBAPair combiPair){
        boolean same=true;
        boolean found=false;
        RelationalPair relPair;
        BusinessObject obj;
        for(int i=0;i<combiPair.getDifferentObjectsCount();i++){
			obj = (BusinessObject)combiPair.getDifferentObjectsInvolved().elementAt(i);
            found = false;
            for(int j=0;j<objectCount;j++){
                if(((BusinessObject)businessObjectsInvolved.elementAt(j)).getName().equals(obj.getName())){
                    found=true;
                }
            }
            if(!found){
                same=false;
            }
        }
        return same;
    }

    public Vector getBusinessObjectsInvolved(){ return businessObjectsInvolved; }

    public void setBusinessObjectsInvolved(Vector businessObjectsInvolved){ this.businessObjectsInvolved = businessObjectsInvolved; }

    public Vector getCombiPairs(){ return combiPairs; }

    public void setCombiPairs(Vector combiPairs){ this.combiPairs = combiPairs; }

    public int getObjectCount(){
            return objectCount;
        }

    public void setObjectCount(int objectCount){
            this.objectCount = objectCount;
        }

    private Vector businessObjectsInvolved;
    private Vector combiPairs;
    

    private int objectCount=0;
}
