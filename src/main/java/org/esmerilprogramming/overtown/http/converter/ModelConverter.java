package org.esmerilprogramming.overtown.http.converter;

import org.esmerilprogramming.overtown.http.CloverXRequest;
import org.jboss.logging.Logger;
import org.jboss.logging.Logger.Level;

import java.lang.reflect.*;


/**
 * @author efraimgentil (efraim.gentil@gmail.com)
 */
public class ModelConverter implements ParameterConverter {

  private static final Logger LOGGER = Logger.getLogger(ModelConverter.class);

  @Override
  public <T> T translate(Class<T> clazz, String parameterName,
                         CloverXRequest cloverRequest) {

    boolean shouldTranslate = cloverRequest.containsAttributeStartingWith(parameterName);

    if (shouldTranslate) {
      try {
        Constructor<T> construtor = clazz.getConstructor();
        T retorno = construtor.newInstance();
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
          String fieldName = field.getName();
          String fullParameterName = parameterName + "." + fieldName;
          Object paramValue = cloverRequest.getParameter(parameterName + "." + fieldName);
          if (paramValue != null) {

            String setFieldName = Character.toString(fieldName.charAt(0)).toUpperCase() + fieldName.substring(1);
            try {
              Method method = clazz.getMethod("set" + setFieldName, field.getType());
              if(Modifier.isPublic(method.getModifiers())) {
                ParametersConverter translator = new ParametersConverter();
                method.invoke(retorno, translator.translateParameter(field.getType(), fullParameterName, cloverRequest));
              }
            }catch(NoSuchMethodException nsme){
              //DO nothing if doesn't have setMethod
            }
          }
        }
        return retorno;
      } catch (NoSuchMethodException | SecurityException | InstantiationException |
              IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
        LOGGER.log(Level.FATAL, e.getMessage());
      }
    }

    return null;
  }

}