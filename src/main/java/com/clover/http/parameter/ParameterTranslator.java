package com.clover.http.parameter;

import com.clover.http.CloverRequest;

/**
 * @author efraimgentil (efraim.gentil@gmail.com)
 */
public abstract class ParameterTranslator {
	
	private ParameterTranslator nextTranslator;
	
	protected abstract boolean shouldTranslate( Class<?> clazz );
	
	public abstract <T> T translate( Class<T> clazz , String parameterName , CloverRequest cloverRequest);

	public ParameterTranslator getNextTranslator() {
		return nextTranslator;
	}

	public void setNextTranslator(ParameterTranslator nextTranslator) {
		this.nextTranslator = nextTranslator;
	}
	
}