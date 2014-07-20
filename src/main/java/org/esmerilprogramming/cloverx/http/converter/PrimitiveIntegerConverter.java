package org.esmerilprogramming.cloverx.http.converter;

import org.esmerilprogramming.cloverx.http.CloverRequest;

import java.util.logging.Level;
import java.util.logging.Logger;

public class PrimitiveIntegerConverter implements ParameterConverter {

  @SuppressWarnings("unchecked")
  @Override
  public <T> T translate(Class<T> clazz, String parameterName, CloverRequest cloverRequest) {
    Integer val = 0;

    Object attribute = cloverRequest.getParameter(parameterName);
    if (attribute != null) {
      try {
        String strVal = String.valueOf(attribute);
        Integer i = Integer.parseInt(strVal);
        return (T) i;
      } catch (NumberFormatException nfe) {
        Logger.getLogger(PrimitiveIntegerConverter.class.getName()).log(Level.SEVERE,
            nfe.getMessage());
      }
    }

    return (T) val;
  }

  public static boolean isPrimitiveInteger(Class<?> clazz) {
    return int.class.equals(clazz);
  }

}
