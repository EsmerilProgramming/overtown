package com.clover.http.parameter;

import com.clover.http.CloverRequest;

/**
 * 
 * @author efraimgentil (efraim.gentil@gmail.com)
 */
public class StringTranslator implements ParameterTranslator {

	@SuppressWarnings("unchecked")
	@Override
	public <T> T translate(Class<T> clazz, String parameterName,
			CloverRequest cloverRequest) {
		Object attribute = cloverRequest.getAttribute(parameterName);
		if(attribute == null)
			return null;
		
		return (T) String.valueOf(attribute);
	}
	
}