/*******************************************************************************
Developed by BUSINESS SOFTWARE INNOVATIONS. .
Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved.
*******************************************************************************/ 

package model;
import bi.view.lang.CMMessages;

public class StdCombination extends Combination{
    public StdCombination() {
    }

    
    protected String generateName(int id) {
      StringBuffer idString = new StringBuffer();
      idString.append(id);
      int length = idString.length();
        for(int i = 0; i < BusinessRules.ID_LENGTH-length; i++) {
          idString.insert(0,BusinessRules.ID_FILLER_CHARACTER);
        }
      idString.insert(0,CMMessages.getString("STD_COMBINATION_PREFIX"));
      return  idString.toString();
    }
   @Override
public String toString() {
	// TODO Auto-generated method stub
	return this.getName() + " "+ this.getDescription();
}
    public Structure getM_Structure(){
            return m_Structure;
        }

    public void setM_Structure(Structure m_Structure){
            this.m_Structure = m_Structure;
        }
    public String getStateName() {
      return "";
    }
    @Override
    public Origin getOriginType() {
    	return Combination.Origin.STANDART;
    }
    private Structure m_Structure;
}
