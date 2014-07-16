package org.esmerilprogramming.clover.http.parameter;

import org.esmerilprogramming.clover.http.CloverRequest;

/**
 * 
 * @author efraimgentil (efraim.gentil@gmail.com)
 */
public class DoubleTranslator implements ParameterTranslator {

	@SuppressWarnings("unchecked")
	@Override
	public <T> T translate(Class<T> clazz, String parameterName,
			CloverRequest cloverRequest) {
		Object attribute = cloverRequest.getAttribute(parameterName);
		if(attribute == null)
			return null;
		
		String strVal = String.valueOf(attribute);
		
		try{
			Double val = Double.parseDouble( strVal );
			return (T) val; 
		}catch(NumberFormatException nfe){
			nfe.printStackTrace();
		}
		
		return null;
	}

}
