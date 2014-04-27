package com.clover.http.parameter;

import com.clover.http.CloverRequest;

/**
 * @author efraimgentil (efraim.gentil@gmail.com)
 */
public class CommonsParamTranslator implements ParameterTranslator {

	@Override
	public <T> T translate(Class<T> clazz, String parameterName,
			CloverRequest cloverRequest) {
		
		Object attribute = cloverRequest.getAttribute(parameterName);
		if(attribute == null)
			return null;
		
		ParameterTranslator translator = getTranslator(clazz);
		return translator.translate(clazz, parameterName, cloverRequest);
	}
	
	public ParameterTranslator getTranslator( Class<?> clazz ){
		ParameterTranslator translator = new EmptyParamTranslator();
		
		if(isString(clazz))
			translator = new StringTranslator();
		if(isInteger(clazz))
			translator = new IntegerTranslator();
		if(isDouble(clazz))
			translator = new DoubleTranslator();
		if(isLong(clazz))
			translator = new LongTranslator();
		if(isBoolean(clazz))
			translator = new BooleanTranslator();
		
		return translator;
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
	
	private static boolean isBoolean( Class<?> clazz){
		return Boolean.class.equals(clazz) || boolean.class.equals(clazz);
	}

}