package com.clover.http.parameter;

import com.clover.http.CloverRequest;

/**
 * @author efraimgentil (efraim.gentil@gmail.com)
 */
public class EmptyParamTranslator implements ParameterTranslator {
	
	@Override
	public <T> T translate(Class<T> clazz, String parameterName,
			CloverRequest cloverRequest) {
		return null;
	}

}
