package org.esmerilprogramming.overtown.http.converter;

import org.esmerilprogramming.overtown.http.OvertownRequest;

/**
 * @author efraimgentil (efraim.gentil@gmail.com)
 */
public interface ParameterConverter {
	
	public abstract <T> T translate( Class<T> clazz , String parameterName , OvertownRequest overtownRequest);

}
