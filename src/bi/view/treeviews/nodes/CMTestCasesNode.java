/**
 * This Software has been developed by Business Software Innovations  .
 * Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved. 
 */
package bi.view.treeviews.nodes;

import model.TestObject;
import model.TestObjectReference;
import bi.view.lang.CMMessages;

public class CMTestCasesNode{
  public CMTestCasesNode(TestObjectReference p_TestObjectReference, TestObject p_TestObject){
    m_TestObject = p_TestObject;
    m_TestObjectReference = p_TestObjectReference;

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

  public String toString() {
    return CMMessages.getString("NODE_LABEL_TEST_CASES"); //$NON-NLS-1$
  }
  private TestObjectReference m_TestObjectReference = null;
  private TestObject m_TestObject = null;
}