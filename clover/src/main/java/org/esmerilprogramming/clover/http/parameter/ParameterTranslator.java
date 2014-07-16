package org.esmerilprogramming.clover.http.parameter;

import org.esmerilprogramming.clover.http.CloverRequest;

/**
 * @author efraimgentil (efraim.gentil@gmail.com)
 */
public interface ParameterTranslator {
	
	public abstract <T> T translate( Class<T> clazz , String parameterName , CloverRequest cloverRequest);

}