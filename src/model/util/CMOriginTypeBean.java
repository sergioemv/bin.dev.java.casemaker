/**
 * This Software has been developed by Business Software Innovations  .
 * Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved.
 */
package model.util;

/**
 * @author smoreno
 *
 */
public interface CMOriginTypeBean {

	public static enum Origin{
		AUTOMATIC ("automatic"),
		MANUAL ("Manual"),
		ALLPAIRS ("automatic by AllPairs"),
		PERMUTATION ("automatic by Permutation"),
		STANDART ("Standart Combination");

		private String label;
		Origin(String label)
		{
			this.label = label;
		}
		@Override
		public String toString() {
			return label;
		}
		public static Origin getByName(String name) {
			for (Origin o : values())
				if (o.toString().equalsIgnoreCase(name))
					return o;
			return null;
		}
		}
	public Origin getOriginType();
	public void setOriginType(Origin type);
}
