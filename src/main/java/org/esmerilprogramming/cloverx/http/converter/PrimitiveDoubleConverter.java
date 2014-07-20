package org.esmerilprogramming.cloverx.http.converter;


import java.util.logging.Level;
import java.util.logging.Logger;

import org.esmerilprogramming.cloverx.http.CloverXRequest;

public class PrimitiveDoubleConverter implements ParameterConverter {

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
        Logger.getLogger(PrimitiveDoubleConverter.class.getName()).log(Level.SEVERE,
            nfe.getMessage());
      }
    }
    Double defaultValue = 0.0;
    return (T) defaultValue;
  }

  public static boolean isPrimitiveDouble(Class<?> clazz) {
    return double.class.equals(clazz);
  }

}
