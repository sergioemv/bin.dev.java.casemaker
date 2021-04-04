package bi.controller.utils;

import model.Effect;
import model.Element;
import model.EquivalenceClass;
import model.util.CMCloneable;

public class CMClipBoard {

	private CMCloneable object;
	private static CMClipBoard instance;
	public static CMClipBoard getInstance(){
		if (instance == null)
			instance = new CMClipBoard();
		return instance;
	}
	public  void copy(CMCloneable original) {
		object = original.makeClone();
	}

	public EquivalenceClass getEquivalenceClass(){
		if (object!=null && object instanceof EquivalenceClass)
			return (EquivalenceClass) object.makeClone();
		return null;
	}
	public Element getElement(){
		if (object!=null && object instanceof Element)
			return (Element) object.makeClone();
		return null;
	}
	public Effect getEffect() {
		if (object!=null && object instanceof Effect)
			return (Effect) object.makeClone();
		return null;
	}
}
