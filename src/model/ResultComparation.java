package model;
import java.util.Vector;

/**
 ****************************************************************************** Developed by BUSINESS SOFTWARE INNOVATIONS.
 * Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved.
 */
public class ResultComparation implements Cloneable{
    public ResultComparation() {
        testDataSetActual= new Vector();
        testDataSetTarget= new Vector();
    }
   	public Object clone() {
      Object b = null;
      try {
       b = super.clone();
      } catch(CloneNotSupportedException e) {
        e.printStackTrace();
      }
      return b;
    }
	public ResultComparation cloneResultComparation()
    {
         Object b = null;

       		b = clone();
            ResultComparation newResultComparation=(ResultComparation) b;
			Vector newtestDataSetActual= new Vector();
           	Vector newtestDataSetTarget= new Vector();
             for(int i=0; i< newResultComparation.getTestDataSetActual().size(); i++)
            {
                String tds=new String(newResultComparation.getTestDataSetActual().elementAt(i).toString());
                newtestDataSetActual.addElement(tds);
            }
             newResultComparation.setTestDataSetActual(newtestDataSetActual);
            for(int i=0; i< newResultComparation.getTestDataSetTarget().size(); i++)
            {
                 String tds=new String(newResultComparation.getTestDataSetTarget().elementAt(i).toString());
                newtestDataSetTarget.addElement(tds);
            }
             newResultComparation.setTestDataSetTarget(newtestDataSetTarget);
      return (ResultComparation)b;
    }
    public Vector getTestDataSetTarget(){ 
    	if(testDataSetTarget == null)
    		testDataSetTarget = new Vector();
    	return testDataSetTarget; }

    public void setTestDataSetTarget(Vector testDataSetTarget){ this.testDataSetTarget = testDataSetTarget; }

    public Vector getTestDataSetActual(){ 
    	if(testDataSetActual == null)
    		testDataSetActual = new Vector();
    	return testDataSetActual; }

    public void setTestDataSetActual(Vector testDataSetActual){ this.testDataSetActual = testDataSetActual; }

    private Vector testDataSetTarget;
    private Vector testDataSetActual;
}
