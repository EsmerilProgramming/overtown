package org.esmerilprogramming.overtown.http.converter;

import org.esmerilprogramming.overtown.http.CloverXRequest;
import org.jboss.logging.Logger;

public class PrimitiveLongConverter implements ParameterConverter {

  private static final Logger LOGGER = Logger.getLogger(PrimitiveLongConverter.class);

  @SuppressWarnings("unchecked")
  @Override
  public <T> T translate(Class<T> clazz, String parameterName, CloverXRequest cloverRequest) {
    Long defaultValue = 0l;

    Object attribute = cloverRequest.getParameter(parameterName);
    if (attribute != null) {
      try {
        String strVal = String.valueOf(attribute);
        Long val = Long.parseLong(strVal);
        return (T) val;
      } catch (NumberFormatException nfe) {
        LOGGER.error(nfe.getMessage());
      }
    }

    return (T) defaultValue;
  }

  public static boolean isPrimitiveLong(Class<?> clazz) {
    return long.class.equals(clazz);
  }

}
