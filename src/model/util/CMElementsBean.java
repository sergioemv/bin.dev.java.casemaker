/**
 * This Software has been developed by Business Software Innovations  .
 * Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved.
 */
package model.util;

import java.util.List;

import model.Element;

/**
 * @author smoreno
 *
 */
public interface CMElementsBean {

	public void addElement(Element p_Element);
	public void removeElement(Element p_Element);
	public List<Element> getElements();
	public void addElement(Element p_element, int p_oldPosition);
}
