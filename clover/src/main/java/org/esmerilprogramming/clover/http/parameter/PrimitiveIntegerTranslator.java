package org.esmerilprogramming.clover.http.parameter;

import org.esmerilprogramming.clover.http.CloverRequest;

public class PrimitiveIntegerTranslator implements ParameterTranslator {

	@SuppressWarnings("unchecked")
	@Override
	public <T> T translate(Class<T> clazz, String parameterName,
			CloverRequest cloverRequest) {
		Integer val = 0;
		
		Object attribute = cloverRequest.getAttribute(parameterName);
		if(attribute != null){
			try{
				String strVal = String.valueOf(attribute);
				Integer i = Integer.parseInt(strVal);
				return (T) i; 
			}catch(NumberFormatException nfe){
				nfe.printStackTrace();
			}
		}
		
		return (T) val;
	}
	
	public static boolean isPrimitiveInteger(Class<?> clazz){
		return int.class.equals(clazz);
	}

}
