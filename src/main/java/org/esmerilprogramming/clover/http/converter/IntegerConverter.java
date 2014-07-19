package org.esmerilprogramming.clover.http.converter;

import org.esmerilprogramming.clover.http.CloverRequest;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * 
 * @author efraimgentil (efraim.gentil@gmail.com)
 */
public class IntegerConverter implements ParameterConverter {
	@SuppressWarnings("unchecked")
	@Override
	public <T> T translate(Class<T> clazz, String parameterName,
			CloverRequest cloverRequest) {
		Object attribute = cloverRequest.getAttribute(parameterName);
		if(attribute != null){
			String strVal = String.valueOf(attribute);
			
			try{
				Integer i = Integer.parseInt(strVal);
				return (T) i; 
			}catch(NumberFormatException nfe){
				Logger.getLogger(IntegerConverter.class.getName()).log(Level.SEVERE, nfe.getMessage());
			}
		}
		return null;
	}

}
