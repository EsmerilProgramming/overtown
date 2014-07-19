package org.esmerilprogramming.clover.http.converter;

import org.esmerilprogramming.clover.http.CloverRequest;

/**
 * @author efraimgentil (efraim.gentil@gmail.com)
 */
public class CommonsParamConverter implements ParameterConverter {

	@Override
	public <T> T translate(Class<T> clazz, String parameterName,
			CloverRequest cloverRequest) {
		
		Object attribute = cloverRequest.getAttribute(parameterName);
		if(attribute == null)
			return null;
		
		ParameterConverter translator = getTranslator(clazz);
		return translator.translate(clazz, parameterName, cloverRequest);
	}
	
	public ParameterConverter getTranslator( Class<?> clazz ){
		ParameterConverter translator = new EmptyParamConverter();
		
		if(isString(clazz))
			translator = new StringConverter();
		if(isInteger(clazz))
			translator = new IntegerConverter();
		if(isDouble(clazz))
			translator = new DoubleConverter();
		if(isLong(clazz))
			translator = new LongConverter();
		if(isBoolean(clazz))
			translator = new BooleanConverter();
		
		return translator;
	}
	
	public static boolean isCommonParam(Class<?> clazz){
		return (isString(clazz) || isInteger(clazz) || isLong(clazz) || isDouble(clazz) );
	}
	
	private static boolean isString( Class<?> clazz ){
		return String.class.equals(clazz);
	}
	
	private static boolean isInteger( Class<?> clazz ){
		return Integer.class.equals(clazz);
	}
	
	private static boolean isLong( Class<?> clazz ){
		return Long.class.equals(clazz);
	}
	
	private static boolean isDouble( Class<?> clazz ){
		return Double.class.equals(clazz);
	}
	
	private static boolean isBoolean( Class<?> clazz){
		return Boolean.class.equals(clazz);
	}

}