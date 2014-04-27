package com.clover.http.parameter;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

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
				Object paramValue = cloverRequest.getAttribute( parameterName + "." + field.getName() );
				if(paramValue != null){
					field.setAccessible(true);
					field.set( retorno, retornaParametroComTipo( field.getType() , paramValue ) );
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
	
	protected Object retornaParametroComTipo(Class tipo, Object paramValue ){
		if(tipo == String.class)
			return String.valueOf( paramValue );
		if(tipo == Integer.class){
			String paramVal = String.valueOf( paramValue );
			if(paramVal != null && !paramVal.trim().isEmpty() )
				return Integer.parseInt( paramVal );
			else
				return null;
		}
		if(tipo == BigDecimal.class){
			String paramVal = String.valueOf( paramValue );
			if(paramVal != null && !paramVal.trim().isEmpty() )
				return new BigDecimal( paramVal );
			else
				return null;
		}
		if(tipo == boolean.class || tipo == Boolean.class)
			return Boolean.parseBoolean( String.valueOf( paramValue ) );
		if(tipo == Date.class)
			return getDateValue( String.valueOf(paramValue) );
		return null;
	} 
	
	protected Date getDateValue(String dataAsString){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			return sdf.parse(dataAsString);
		} catch (ParseException e) {
			try {
				sdf = new SimpleDateFormat("yyyy-MM-dd");
				return sdf.parse(dataAsString);
			} catch (ParseException e1) {
				try {
					sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
					return sdf.parse(dataAsString);
				} catch (ParseException e2) {
					sdf = new SimpleDateFormat("dd/MM/yyyy");
					try {
						return sdf.parse(dataAsString);
					} catch (ParseException e3) {
					}
				}
			}
		}
		return null;
	}
	
}