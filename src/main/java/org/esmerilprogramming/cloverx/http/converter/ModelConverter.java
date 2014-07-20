package org.esmerilprogramming.cloverx.http.converter;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

import org.esmerilprogramming.cloverx.http.CloverRequest;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author efraimgentil (efraim.gentil@gmail.com)
 */
public class ModelConverter implements ParameterConverter{

	@Override
	public <T> T translate(Class<T> clazz, String parameterName,
			CloverRequest cloverRequest) {
		
		boolean shouldTranslate = cloverRequest.containsAttributeStartingWith(parameterName);
		
		if(shouldTranslate){
			try {
				Constructor<T> construtor = clazz.getConstructor();
				T retorno = construtor.newInstance();
				Field[] fields = clazz.getDeclaredFields();
				for (Field field : fields) {
					String fullParameterName = parameterName + "." + field.getName();
					Object paramValue = cloverRequest.getAttribute( parameterName + "." + field.getName() );
					if(paramValue != null){
						field.setAccessible(true);
						ParametersConverter translator = new ParametersConverter();
						field.set( retorno, translator.translateParameter( field.getType(), fullParameterName, cloverRequest ) );
						field.setAccessible(false);
					}
				}
				return retorno;
			} catch (NoSuchMethodException | SecurityException | InstantiationException | 
				IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
				Logger.getLogger(ModelConverter.class.getName()).log(Level.SEVERE, e.getMessage());
			}
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
