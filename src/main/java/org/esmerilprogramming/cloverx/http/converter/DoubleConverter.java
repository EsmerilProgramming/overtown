package org.esmerilprogramming.cloverx.http.converter;

import org.esmerilprogramming.cloverx.http.CloverXRequest;
import org.jboss.logging.Logger;

/**
 * 
 * @author efraimgentil (efraim.gentil@gmail.com)
 */
public class DoubleConverter implements ParameterConverter {

  private static final Logger LOGGER = Logger.getLogger(DoubleConverter.class);

  @SuppressWarnings("unchecked")
  @Override
  public <T> T translate(Class<T> clazz, String parameterName, CloverXRequest cloverRequest) {
    Object attribute = cloverRequest.getParameter(parameterName);
    if (attribute == null)
      return null;

    String strVal = String.valueOf(attribute);

    try {
      Double val = Double.parseDouble(strVal);
      return (T) val;
    } catch (NumberFormatException nfe) {
      LOGGER.error(nfe.getMessage());
    }

    return null;
  }

}
