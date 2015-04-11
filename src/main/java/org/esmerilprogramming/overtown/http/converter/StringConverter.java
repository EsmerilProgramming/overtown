package org.esmerilprogramming.overtown.http.converter;

import org.esmerilprogramming.overtown.http.OvertownRequest;

/**
 * 
 * @author efraimgentil (efraim.gentil@gmail.com)
 */
public class StringConverter implements ParameterConverter {

	@SuppressWarnings("unchecked")
	@Override
	public <T> T translate(Class<T> clazz, String parameterName,
			OvertownRequest cloverRequest) {
		Object attribute = cloverRequest.getParameter(parameterName);
		if(attribute == null)
			return null;
		
		return (T) String.valueOf(attribute);
	}
	
}