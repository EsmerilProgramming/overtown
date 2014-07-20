package org.esmerilprogramming.cloverx.http.converter;

import org.esmerilprogramming.cloverx.http.CloverXRequest;

/**
 * @author efraimgentil (efraim.gentil@gmail.com)
 */
public interface ParameterConverter {
	
	public abstract <T> T translate( Class<T> clazz , String parameterName , CloverXRequest cloverRequest);

}