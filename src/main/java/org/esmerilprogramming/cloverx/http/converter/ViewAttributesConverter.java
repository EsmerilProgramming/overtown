package org.esmerilprogramming.cloverx.http.converter;

import org.esmerilprogramming.cloverx.http.CloverXRequest;

public class ViewAttributesConverter implements ParameterConverter {

  @SuppressWarnings("unchecked")
  @Override
  public <T> T translate(Class<T> clazz, String parameterName, CloverXRequest cloverRequest) {
    return (T) cloverRequest.getViewAttributes();
  }

}
