package org.esmerilprogramming.cloverx.http.converter;

import org.esmerilprogramming.cloverx.http.CloverRequest;

public class ViewAttributesRequestConverter implements ParameterConverter {

  @SuppressWarnings("unchecked")
  @Override
  public <T> T translate(Class<T> clazz, String parameterName, CloverRequest cloverRequest) {
    return (T) cloverRequest.getViewAttributes();
  }

}
