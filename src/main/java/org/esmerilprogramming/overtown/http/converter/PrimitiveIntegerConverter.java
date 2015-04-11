package org.esmerilprogramming.overtown.http.converter;

import org.esmerilprogramming.overtown.http.CloverXRequest;
import org.jboss.logging.Logger;

public class PrimitiveIntegerConverter implements ParameterConverter {
  
  private static final Logger LOGGER = Logger.getLogger(PrimitiveIntegerConverter.class);

  @SuppressWarnings("unchecked")
  @Override
  public <T> T translate(Class<T> clazz, String parameterName, CloverXRequest cloverRequest) {
    Integer val = 0;

    Object attribute = cloverRequest.getParameter(parameterName);
    if (attribute != null) {
      try {
        String strVal = String.valueOf(attribute);
        Integer i = Integer.parseInt(strVal);
        return (T) i;
      } catch (NumberFormatException nfe) {
        LOGGER.error(nfe.getMessage());
      }
    }

    return (T) val;
  }

  public static boolean isPrimitiveInteger(Class<?> clazz) {
    return int.class.equals(clazz);
  }

}
