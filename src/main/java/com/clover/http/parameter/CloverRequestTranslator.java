package com.clover.http.parameter;

import com.clover.http.CloverRequest;

/**
 * @author efraimgentil (efraim.gentil@gmail.com)
 */
public class CloverRequestTranslator extends ParameterTranslator{

	@Override
	protected boolean shouldTranslate( Class<?> clazz ) {
		return clazz.equals( CloverRequest.class );
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T translate(Class<T> clazz, String parameterName, CloverRequest cloverRequest) {
		if( shouldTranslate(clazz) )
			return (T) cloverRequest;
		else
			return getNextTranslator().translate(clazz, parameterName, cloverRequest);
	}

}