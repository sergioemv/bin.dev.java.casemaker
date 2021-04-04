/*******************************************************************************
Developed by BUSINESS SOFTWARE INNOVATIONS. .
Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved.
*******************************************************************************/ 

package model;
import java.util.Vector;

public class TestCaseGroup {
    public TestCaseGroup() {
    }

    public Vector getM_Dependencies(){
            return m_Dependencies;
        }

    public void setM_Dependencies(Vector m_Dependencies){
            this.m_Dependencies = m_Dependencies;
        }

    private Vector m_Dependencies = new Vector(0);
}
