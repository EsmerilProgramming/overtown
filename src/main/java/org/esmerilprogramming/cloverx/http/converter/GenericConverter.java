package org.esmerilprogramming.cloverx.http.converter;

import org.esmerilprogramming.cloverx.http.CloverXRequest;

public abstract class GenericConverter<ToType> implements ParameterConverter {

  public GenericConverter() {
  }
  
  public abstract ToType convert(String value);

  @SuppressWarnings({"unchecked", "hiding"})
  @Override
  public final <ToType> ToType translate(Class<ToType> clazz, String parameterName, CloverXRequest cloverRequest) {
    return (ToType) convert( String.valueOf( cloverRequest.getParameter(parameterName) ) );
  }

}
