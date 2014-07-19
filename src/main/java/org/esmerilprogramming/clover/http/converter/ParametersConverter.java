package org.esmerilprogramming.clover.http.converter;

import org.esmerilprogramming.clover.http.CloverRequest;

import io.undertow.server.HttpServerExchange;

public class ParametersConverter {
	
	
	public Object[] translateAllParameters( String[] parameterNames, Class<?>[] parameterTypes, CloverRequest cloverRequest){
		Object[] parameters = new Object[parameterTypes.length];
		for (int i = 0 ; i < parameterTypes.length ; i++) {
			Class<?> clazz = parameterTypes[i];
			parameters[i] = translateParameter( clazz, parameterNames[i] , cloverRequest );
		}
		return parameters;
	}
	
	public <T> T translateParameter( Class<T> clazz , String parameterName , CloverRequest cloverRequest){
		boolean shouldTranslateParameter = cloverRequest.shouldTranslateParameter(parameterName);
		ParameterConverter translator;
		if(shouldTranslateParameter){
			translator = cloverRequest.getTranslator(parameterName);
		}else{
			translator = getTranslator(clazz);
		}
		
		return translator.translate( clazz , parameterName , cloverRequest );
	}
	
	public ParameterConverter getTranslator( Class<?> clazz ){
		if(CloverRequest.class.equals(clazz))
			return new CloverRequestConverter();
		if(HttpServerExchange.class.equals(clazz))
			return new HttpServerExchangeConverter();
		if(PrimitiveParamConverter.isPrimitive(clazz))
			return new PrimitiveParamConverter();
		if(CommonsParamConverter.isCommonParam(clazz))
			return new CommonsParamConverter();
		else
			return new ModelConverter();
	}

}
