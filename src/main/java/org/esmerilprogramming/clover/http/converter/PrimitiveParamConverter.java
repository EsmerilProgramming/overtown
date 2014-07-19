package org.esmerilprogramming.clover.http.converter;

import org.esmerilprogramming.clover.http.CloverRequest;

public class PrimitiveParamConverter implements ParameterConverter {

	@Override
	public <T> T translate(Class<T> clazz, String parameterName,
			CloverRequest cloverRequest) {
		
		ParameterConverter translator = null;
		if( PrimitiveIntegerConverter.isPrimitiveInteger(clazz) ){
			translator = new PrimitiveIntegerConverter();
		}else if ( PrimitiveLongConverter.isPrimitiveLong(clazz) ){
			translator = new PrimitiveLongConverter();
		}else if ( PrimitiveDoubleConverter.isPrimitiveDouble(clazz) ){
			translator = new PrimitiveDoubleConverter();
		}
		
		return translator.translate(clazz, parameterName, cloverRequest);
	}
	
	public static boolean isPrimitive(Class<?> clazz){
		return PrimitiveIntegerConverter.isPrimitiveInteger(clazz)
				|| PrimitiveDoubleConverter.isPrimitiveDouble(clazz)
				|| PrimitiveLongConverter.isPrimitiveLong(clazz);
	}

}
