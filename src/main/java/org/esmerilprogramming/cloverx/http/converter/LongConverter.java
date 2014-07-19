package org.esmerilprogramming.cloverx.http.converter;

import org.esmerilprogramming.cloverx.http.CloverRequest;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * 
 * @author efraimgentil (efraim.gentil@gmail.com)
 */
public class LongConverter implements ParameterConverter{

	@SuppressWarnings("unchecked")
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
			Logger.getLogger(LongConverter.class.getName()).log(Level.SEVERE, nfe.getMessage());
		}
		
		return null;
	}

}

