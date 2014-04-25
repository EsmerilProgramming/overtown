package com.clover.http.parameter;

import io.undertow.server.HttpServerExchange;

import com.clover.http.CloverRequest;

public class ParametersTranslator {
	
	public <T> T translateParameters( Class<T> clazz , String parameterName , CloverRequest cloverRequest){
		ParameterTranslator translator = new EmptyParamTranslator();
		
		if(CloverRequest.class.equals(clazz))
			translator = new CloverRequestTranslator();
		if(HttpServerExchange.class.equals(clazz))
			translator = new HttpServerExchangeTranslator();
		if(CommonsParamTranslator.isCommonParam(clazz))
			translator = new CommonsParamTranslator();
		
		return translator.translate( clazz , parameterName , cloverRequest );
	}
	
}
