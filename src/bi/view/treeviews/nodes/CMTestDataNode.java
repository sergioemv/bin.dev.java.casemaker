/**
 * This Software has been developed by Business Software Innovations  .
 * Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved. 
 */
package bi.view.treeviews.nodes;

import model.TDStructure;
import model.TDStructureReference;
import model.TestObject;
import model.TestObjectReference;
import bi.view.lang.CMMessages;

public class CMTestDataNode{
 //grueda22082004_begin
  public CMTestDataNode(TestObjectReference p_TestObjectReference, TestObject p_TestObject, TDStructureReference p_TDStructureReference,  TDStructure p_TDStructure){
    m_TestObject = p_TestObject;
    m_TestObjectReference = p_TestObjectReference;
    //m_TDStructureReference=(TDStructureReference) p_TestObject.getM_TDSTructureReference().firstElement();
  //grueda22082004_end
    m_TDStructureReference = p_TDStructureReference;
    m_TDStructure=p_TDStructure;

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
    return CMMessages.getString("NODE_LABEL_TEST_DATA"); //$NON-NLS-1$
  }
  public void setM_TDStructure(TDStructure p_TDStructure) // new  TD file harold
  {
    m_TDStructure=p_TDStructure;
  }
  public void setM_TDStructureReference(TDStructureReference p_TDStructureReference) // new  TD file harold
  {
    m_TDStructureReference= p_TDStructureReference;
  }
  public TDStructure getM_TDStructure() // new  TD file harold
  {
    return m_TDStructure;
  }
  public TDStructureReference getM_TDStructureReference() // new  TD file harold
  {
    return m_TDStructureReference;
  }

  private TestObject m_TestObject = null;
  private TestObjectReference m_TestObjectReference = null;
  private TDStructure m_TDStructure = null; // new  TD file harold
  private TDStructureReference m_TDStructureReference=null; // new  TD file harold
}