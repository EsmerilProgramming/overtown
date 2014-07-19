package org.esmerilprogramming.cloverx.http.converter;

import org.esmerilprogramming.cloverx.http.CloverRequest;

/**
 * @author efraimgentil (efraim.gentil@gmail.com)
 */
public interface ParameterConverter {
	
	public abstract <T> T translate( Class<T> clazz , String parameterName , CloverRequest cloverRequest);

}