package org.esmerilprogramming.overtown.http.converter;

import org.esmerilprogramming.overtown.http.OvertownRequest;

public class PrimitiveParamConverter implements ParameterConverter {

	@Override
	public <T> T translate(Class<T> clazz, String parameterName,
			OvertownRequest cloverRequest) {
		
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
	
	public ParameterConverter getConveter(Class<?> clazz){
	  ParameterConverter translator = null;
      if( PrimitiveIntegerConverter.isPrimitiveInteger(clazz) ){
          translator = new PrimitiveIntegerConverter();
      }else if ( PrimitiveLongConverter.isPrimitiveLong(clazz) ){
          translator = new PrimitiveLongConverter();
      }else if ( PrimitiveDoubleConverter.isPrimitiveDouble(clazz) ){
          translator = new PrimitiveDoubleConverter();
      }
      return translator;
	}

}
