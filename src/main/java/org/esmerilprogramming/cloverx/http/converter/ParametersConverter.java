package org.esmerilprogramming.cloverx.http.converter;

import org.esmerilprogramming.cloverx.http.CloverXRequest;
import org.esmerilprogramming.cloverx.view.ViewAttributes;

import io.undertow.server.HttpServerExchange;

public class ParametersConverter {
	
	public Object[] translateAllParameters( String[] parameterNames, Class<?>[] parameterTypes, CloverXRequest cloverRequest){
		Object[] parameters = new Object[parameterTypes.length];
		for (int i = 0 ; i < parameterTypes.length ; i++) {
			Class<?> clazz = parameterTypes[i];
			parameters[i] = translateParameter( clazz, parameterNames[i] , cloverRequest );
		}
		return parameters;
	}
	
	public <T> T translateParameter( Class<T> clazz , String parameterName , CloverXRequest cloverRequest){
		boolean shouldTranslateParameter = cloverRequest.shouldConvertParameter(parameterName);
		ParameterConverter translator;
		if(shouldTranslateParameter){
			translator = cloverRequest.getTranslator(parameterName);
		}else{
			translator = getTranslator(clazz);
		}
		
		return translator.translate( clazz , parameterName , cloverRequest );
	}
	
	public ParameterConverter getTranslator( Class<?> clazz ){
		if(CloverXRequest.class.equals(clazz))
			return new CloverXRequestConverter();
		if(ViewAttributes.class.equals(clazz))
          return new ViewAttributesConverter();
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
