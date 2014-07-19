package org.esmerilprogramming.cloverx.http.converter;

import org.esmerilprogramming.cloverx.http.CloverRequest;

import java.util.logging.Level;
import java.util.logging.Logger;

public class PrimitiveLongConverter implements ParameterConverter {
	
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
				Logger.getLogger(PrimitiveLongConverter.class.getName()).log(Level.SEVERE, nfe.getMessage());
			}
		}
		
		return (T) defaultValue;
	}
	
	public static boolean isPrimitiveLong(Class<?> clazz){
		return long.class.equals(clazz);
	}
	
}
