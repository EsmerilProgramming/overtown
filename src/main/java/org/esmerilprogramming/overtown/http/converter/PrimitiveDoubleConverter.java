package org.esmerilprogramming.overtown.http.converter;

import org.esmerilprogramming.overtown.http.CloverXRequest;
import org.jboss.logging.Logger;

public class PrimitiveDoubleConverter implements ParameterConverter {

  private static final Logger LOGGER = Logger.getLogger(PrimitiveDoubleConverter.class);

  @SuppressWarnings("unchecked")
  @Override
  public <T> T translate(Class<T> clazz, String parameterName, CloverXRequest cloverRequest) {

    Object attribute = cloverRequest.getParameter(parameterName);
    if (attribute != null) {
      try {
        String strVal = String.valueOf(attribute);
        Double value = Double.parseDouble(strVal);
        return (T) value;
      } catch (NumberFormatException nfe) {
        LOGGER.error(nfe.getMessage());
      }
    }
    Double defaultValue = 0.0;
    return (T) defaultValue;
  }

  public static boolean isPrimitiveDouble(Class<?> clazz) {
    return double.class.equals(clazz);
  }

}
