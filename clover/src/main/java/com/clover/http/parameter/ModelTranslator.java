package com.clover.http.parameter;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

import com.clover.http.CloverRequest;

/**
 * @author efraimgentil (efraim.gentil@gmail.com)
 */
public class ModelTranslator implements ParameterTranslator{

	@Override
	public <T> T translate(Class<T> clazz, String parameterName,
			CloverRequest cloverRequest) {
		
		try {
			Constructor<T> construtor = clazz.getConstructor();
			T retorno = construtor.newInstance();
			Field[] campos = clazz.getDeclaredFields();
			for (Field field : campos) {
				String fullParameterName = parameterName + "." + field.getName();
				Object paramValue = cloverRequest.getAttribute( parameterName + "." + field.getName() );
				if(paramValue != null){
					field.setAccessible(true);
					ParametersTranslator translator = new ParametersTranslator();
					field.set( retorno, translator.translateParameter( field.getType(), fullParameterName, cloverRequest ) );
					field.setAccessible(false);
				}
			}
			return retorno;
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
//	protected Date getDateValue(String dataAsString){
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//		try {
//			return sdf.parse(dataAsString);
//		} catch (ParseException e) {
//			try {
//				sdf = new SimpleDateFormat("yyyy-MM-dd");
//				return sdf.parse(dataAsString);
//			} catch (ParseException e1) {
//				try {
//					sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
//					return sdf.parse(dataAsString);
//				} catch (ParseException e2) {
//					sdf = new SimpleDateFormat("dd/MM/yyyy");
//					try {
//						return sdf.parse(dataAsString);
//					} catch (ParseException e3) {
//					}
//				}
//			}
//		}
//		return null;
//	}
	
}