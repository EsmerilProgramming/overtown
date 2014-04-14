package com.clover.http.parameter;

import io.undertow.server.HttpServerExchange;

import com.clover.http.CloverRequest;

/**
 * @author efraimgentil (efraim.gentil@gmail.com)
 */
public class HttpServerExchangeTranslator extends ParameterTranslator {

	@Override
	protected boolean shouldTranslate(Class<?> clazz) {
		return clazz.equals(HttpServerExchange.class);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T translate(Class<T> clazz, String parameterName,
			CloverRequest cloverRequest) {
		if( shouldTranslate(clazz) ){
			return (T) cloverRequest.getExchange();
		}else{
			return getNextTranslator().translate(clazz, parameterName, cloverRequest);
		}
	}

}
