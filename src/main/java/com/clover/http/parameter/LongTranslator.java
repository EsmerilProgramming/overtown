package com.clover.http.parameter;

import com.clover.http.CloverRequest;

/**
 * 
 * @author efraimgentil (efraim.gentil@gmail.com)
 */
public class LongTranslator implements ParameterTranslator{

	@Override
	public <T> T translate(Class<T> clazz, String parameterName,
			CloverRequest cloverRequest) {
		Object attribute = cloverRequest.getAttribute(parameterName);
		if(attribute == null)
			return null;
		
		String strVal = String.valueOf(attribute);
		
		try{
			Long val = Long.parseLong( strVal );
			return (T) val; 
		}catch(NumberFormatException nfe){
			nfe.printStackTrace();
		}
		
		return null;
	}

}
