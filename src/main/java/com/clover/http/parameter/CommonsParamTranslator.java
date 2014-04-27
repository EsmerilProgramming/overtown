package com.clover.http.parameter;

import com.clover.http.CloverRequest;

public class CommonsParamTranslator implements ParameterTranslator {

	@Override
	public <T> T translate(Class<T> clazz, String parameterName,
			CloverRequest cloverRequest) {
		ParameterTranslator translator = new EmptyParamTranslator();
		
		Object attribute = cloverRequest.getAttribute(parameterName);
		if(attribute == null)
			return null;
		
		if(isString(clazz))
			translator = new StringTranslator();
		if(isInteger(clazz))
			translator = new IntegerTranslator();
		if(isDouble(clazz))
			translator = new DoubleTranslator();
		if(isLong(clazz))
			translator = new LongTranslator();
		
		return translator.translate(clazz, parameterName, cloverRequest);
	}
	
	public static boolean isCommonParam(Class<?> clazz){
		return (isString(clazz) || isInteger(clazz) || isLong(clazz) || isDouble(clazz) );
	}
	
	private static boolean isString( Class<?> clazz ){
		return String.class.equals(clazz);
	}
	
	private static boolean isInteger( Class<?> clazz ){
		return Integer.class.equals(clazz) || int.class.equals(clazz);
	}
	
	private static boolean isLong( Class<?> clazz ){
		return Long.class.equals(clazz) || long.class.equals(clazz);
	}
	
	private static boolean isDouble( Class<?> clazz ){
		return Double.class.equals(clazz) || double.class.equals(clazz);
	}

}