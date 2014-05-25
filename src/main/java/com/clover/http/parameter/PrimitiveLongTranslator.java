package com.clover.http.parameter;

import com.clover.http.CloverRequest;

public class PrimitiveLongTranslator implements ParameterTranslator {
	
	@SuppressWarnings("unchecked")
	@Override
	public <T> T translate(Class<T> clazz, String parameterName,
			CloverRequest cloverRequest) {
		Long defaultValue = 0l;
		
		Object attribute = cloverRequest.getAttribute(parameterName);
		if(attribute != null){
			try{
				String strVal = String.valueOf(attribute);
				Long val = Long.parseLong( strVal );
				return (T) val; 
			}catch(NumberFormatException nfe){
				nfe.printStackTrace();
			}
		}
		
		return (T) defaultValue;
	}
	
	public static boolean isPrimitiveLong(Class<?> clazz){
		return long.class.equals(clazz);
	}
	
}
