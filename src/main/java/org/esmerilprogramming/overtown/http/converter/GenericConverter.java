package org.esmerilprogramming.overtown.http.converter;

import org.esmerilprogramming.overtown.http.OvertownRequest;

public abstract class GenericConverter<ToType> implements ParameterConverter {

  public GenericConverter() {
  }

  public abstract ToType convert(String value);

  @SuppressWarnings({"unchecked", "hiding"})
  @Override
  public final <ToType> ToType translate(Class<ToType> clazz, String parameterName, OvertownRequest overtownRequest) {
    return (ToType) convert( String.valueOf(overtownRequest.getParameter(parameterName) ) );
  }

}
