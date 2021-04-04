package model.brmodel;


/**
 ****************************************************************************** Developed by BUSINESS SOFTWARE INNOVATIONS.
 * Copyright (c)2003 D�az und Hilterscheid Unternehmensberatung. All rights reserved.
 */
public class PosRelationalPair implements RelationalPair{
    public PosRelationalPair() {
    }

    public PosRelationalPair(BusinessObject obj, String value) {
        this.theObject=obj;
        this.theValue=value;
    }

    public BusinessObject getTheObject(){ return theObject; }

    public void setTheObject(BusinessObject theObject){ this.theObject = theObject; }

    public String getTheValue(){ return theValue; }

    public void setTheValue(String theValue){ this.theValue = theValue; }

    public NegRelationalPair getNegClone() {
        String value = this.getTheValue();
        BusinessObject obj = this.getTheObject();
        return new NegRelationalPair(obj,value);
    }
    public String getClassName(){
        return this.className;
    }

    private BusinessObject theObject=null;
    private String theValue;
    private final String className="PosRelationalPair";
}
