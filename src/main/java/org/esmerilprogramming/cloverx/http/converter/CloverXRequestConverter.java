package org.esmerilprogramming.cloverx.http.converter;

import org.esmerilprogramming.cloverx.http.CloverXRequest;

/**
 * @author efraimgentil (efraim.gentil@gmail.com)
 */
public class CloverXRequestConverter implements ParameterConverter {

  @SuppressWarnings("unchecked")
  @Override
  public <T> T translate(Class<T> clazz, String parameterName, CloverXRequest cloverRequest) {
    return (T) cloverRequest;
  }

}
