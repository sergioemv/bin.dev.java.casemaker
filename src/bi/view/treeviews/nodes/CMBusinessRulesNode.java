/**
 * This Software has been developed by Business Software Innovations  .
 * Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved. 
 */
package bi.view.treeviews.nodes;

import model.BRulesReference;
import model.TestObject;
import model.TestObjectReference;
import bi.view.lang.CMMessages;

public class CMBusinessRulesNode{
//grueda22082004_begin
  public CMBusinessRulesNode(TestObjectReference p_TestObjectReference, TestObject p_TestObject, BRulesReference p_BRulesReference, String p_BusinessRules){
//grueda22082004_end
    m_TestObject = p_TestObject;
    m_TestObjectReference = p_TestObjectReference;
    //grueda22082004_begin
    m_BRulesReference = p_BRulesReference;
    //grueda22082004_end
    m_BusinessRules = p_BusinessRules;
  }

  public void setM_TestObject(TestObject p_TestObject) {
    m_TestObject = p_TestObject;
  }
  public TestObject getM_TestObject() {
    return m_TestObject;
  }

  public TestObjectReference getM_TestObjectReference(){
    return m_TestObjectReference;
  }
  public void setM_TestObjectReference(TestObjectReference p_TestObjectReference) {
    m_TestObjectReference = p_TestObjectReference;
  }

  public void setM_BusinessRules(String p_BusinessRules) {
    m_BusinessRules = p_BusinessRules;
  }

  public String getM_BusinessRules() {
    return m_BusinessRules;
  }

  public BRulesReference getM_BRulesReference(){
    return m_BRulesReference;
  }
  public void setM_BRulesReference(BRulesReference p_BRulesReference) {
    m_BRulesReference = p_BRulesReference;
  }

  public String toString() {
    return CMMessages.getString("NODE_LABEL_BUSINESS_RULES"); //$NON-NLS-1$
  }
  private TestObjectReference m_TestObjectReference = null;
  private TestObject m_TestObject = null;
  //grueda22082004_begin
  private BRulesReference m_BRulesReference = null;
  //grueda22082004_end
  private String m_BusinessRules = null;
}