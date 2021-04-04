package model.util;

import java.util.List;

import model.CMError;

public interface CMErrorsBean {
	
	public List<CMError> getErrors();
	public void addError(CMError error);
	public void removeError(CMError error);

}
