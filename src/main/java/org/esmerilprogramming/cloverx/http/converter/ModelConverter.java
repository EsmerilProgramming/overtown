package org.esmerilprogramming.cloverx.http.converter;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

import org.esmerilprogramming.cloverx.http.CloverXRequest;
import org.jboss.logging.Logger;
import org.jboss.logging.Logger.Level;


/**
 * @author efraimgentil (efraim.gentil@gmail.com)
 */
public class ModelConverter implements ParameterConverter {
  
  private static final Logger LOGGER = Logger.getLogger(ModelConverter.class);

  @Override
  public <T> T translate(Class<T> clazz, String parameterName,
          CloverXRequest cloverRequest) {
      
      boolean shouldTranslate = cloverRequest.containsAttributeStartingWith(parameterName);
      
      if(shouldTranslate){
          try {
              Constructor<T> construtor = clazz.getConstructor();
              T retorno = construtor.newInstance();
              Field[] fields = clazz.getDeclaredFields();
              for (Field field : fields) {
                  String fullParameterName = parameterName + "." + field.getName();
                  Object paramValue = cloverRequest.getParameter( parameterName + "." + field.getName() );
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
              Logger.getLogger(ModelConverter.class.getName()).log(Level.FATAL, e.getMessage());
          }
      }
      
      return null;
  }

}