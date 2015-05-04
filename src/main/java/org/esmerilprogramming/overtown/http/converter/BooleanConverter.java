package org.esmerilprogramming.overtown.http.converter;

import org.esmerilprogramming.overtown.http.OvertownRequest;

/**
 * @author efraimgentil (efraim.gentil@gmail.com)
 */
public class BooleanConverter implements ParameterConverter {

  @SuppressWarnings("unchecked")
  @Override
  public <T> T translate(Class<T> clazz, String parameterName, OvertownRequest overtownRequest) {

    Object attribute = overtownRequest.getParameter(parameterName);

    if (attribute != null) {
      return (T) new Boolean(attribute.toString());
    }

    return null;
  }

}
