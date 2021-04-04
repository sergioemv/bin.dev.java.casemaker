/**
 * This Software has been developed by Business Software Innovations  .
 * Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved. 
 */
package model.util;

import java.util.List;

/**
 * @author smoreno
 *
 */
public interface CMRiskLevelBean extends CMNameBean{

	public void setRiskLevel(int level);
	public int getRiskLevel();
	public List<? extends CMRiskLevelBean> getChildRiskLevels();
	public List<? extends CMRiskLevelBean> getParentRiskLevels();
}
