package com.clover.http.parameter;

import io.undertow.server.HttpServerExchange;

import com.clover.http.CloverRequest;

public class ParametersTranslator {
	
	public <T> T translateParameters( Class<T> clazz , String parameterName , CloverRequest cloverRequest){
		boolean shouldTranslateParameter = cloverRequest.shouldTranslateParameter(parameterName);
		ParameterTranslator translator;
		if(shouldTranslateParameter){
			translator = cloverRequest.getTranslator(parameterName);
		}else{
			translator = getTranslator(clazz);
		}
		
		return translator.translate( clazz , parameterName , cloverRequest );
	}
	
	public ParameterTranslator getTranslator( Class<?> clazz ){
		if(CloverRequest.class.equals(clazz))
			return new CloverRequestTranslator();
		if(HttpServerExchange.class.equals(clazz))
			return new HttpServerExchangeTranslator();
		if(PrimitiveParamTranslator.isPrimitive(clazz))
			return new PrimitiveParamTranslator();
		if(CommonsParamTranslator.isCommonParam(clazz))
			return new CommonsParamTranslator();
		else
			return new ModelTranslator();
	}

}
