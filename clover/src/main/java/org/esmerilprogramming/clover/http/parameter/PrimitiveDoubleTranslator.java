package org.esmerilprogramming.clover.http.parameter;

import org.esmerilprogramming.clover.http.CloverRequest;

public class PrimitiveDoubleTranslator implements ParameterTranslator {
	
	@SuppressWarnings("unchecked")
	@Override
	public <T> T translate(Class<T> clazz, String parameterName,
			CloverRequest cloverRequest) {
		
		Object attribute = cloverRequest.getAttribute(parameterName);
		if(attribute != null){
			try{
				String strVal = String.valueOf(attribute);
				Double value = Double.parseDouble(strVal);
				return (T) value; 
			}catch(NumberFormatException nfe){
				nfe.printStackTrace();
			}
		}
		Double defaultValue = 0.0;
		return (T) defaultValue;
	}
	
	public static boolean isPrimitiveDouble(Class<?> clazz){
		return double.class.equals(clazz);
	}
	
}
