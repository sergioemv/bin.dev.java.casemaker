/**
 * This Software has been developed by Business Software Innovations  .
 * Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved.
 */
package model.util;

import java.util.List;

import model.Effect;

/**
 * @author smoreno
 *
 */
public interface CMEffectsBean {

	//TODO delete when the notification model is ready
	public List<Effect> getEffects();
	public void addEffect(Effect effect);
	public void removeEffect(Effect effect);
}
