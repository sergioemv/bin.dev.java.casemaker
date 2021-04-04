/**
 * This Software has been developed by Business Software Innovations  .
 * Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved. 
 */
package model;

import model.util.CMNameBean;

/**
 * @author smoreno
 *
 */
public class Requirement implements CMNameBean, Cloneable {
	private String name;
	private Structure parentStructure;
	
	public Requirement(String p_name, Structure p_Structure)
	{
		this.name = p_name;
		this.parentStructure = p_Structure;
	}
	

	/* (non-Javadoc)
	 * @see model.util.CMNameBean#getName()
	 */
	public String getName() {
		if (name == null)
			name = ""; //$NON-NLS-1$
		return name;
	}

	/* (non-Javadoc)
	 * @see model.util.CMNameBean#setName(java.lang.String)
	 */
	public void setName(String p_name) {
		this.name = p_name;

	}

	public boolean isUsed()
	{
		for(Effect effect : this.getParentStructure().getEffects())
			if (effect.getRequirements().contains(this))
				return true;
		return false;
	}
	public Structure getParentStructure() {
		return this.parentStructure;
	}
	public void setParentStructure(Structure p_parent) {
		this.parentStructure = p_parent;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return getName();
	}


	/**
	 * @return
	 */
	public Requirement makeClone() {
		
		try {
			return (Requirement) this.clone();
		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
}
