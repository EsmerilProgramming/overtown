package com.clover.http.parameter;

import com.clover.http.CloverRequest;

public class PrimitiveParamTranslator implements ParameterTranslator {

	@Override
	public <T> T translate(Class<T> clazz, String parameterName,
			CloverRequest cloverRequest) {
		
		ParameterTranslator translator = null;
		if( PrimitiveIntegerTranslator.isPrimitiveInteger(clazz) ){
			translator = new PrimitiveIntegerTranslator();
		}else if ( PrimitiveLongTranslator.isPrimitiveLong(clazz) ){
			translator = new PrimitiveLongTranslator();
		}else if ( PrimitiveDoubleTranslator.isPrimitiveDouble(clazz) ){
			translator = new PrimitiveDoubleTranslator();
		}
		
		return translator.translate(clazz, parameterName, cloverRequest);
	}
	
	public static boolean isPrimitive(Class<?> clazz){
		return PrimitiveIntegerTranslator.isPrimitiveInteger(clazz)
				|| PrimitiveDoubleTranslator.isPrimitiveDouble(clazz)
				|| PrimitiveLongTranslator.isPrimitiveLong(clazz);
	}

}
