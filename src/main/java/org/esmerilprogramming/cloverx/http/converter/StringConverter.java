package org.esmerilprogramming.cloverx.http.converter;

import org.esmerilprogramming.cloverx.http.CloverRequest;

/**
 * 
 * @author efraimgentil (efraim.gentil@gmail.com)
 */
public class StringConverter implements ParameterConverter {

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