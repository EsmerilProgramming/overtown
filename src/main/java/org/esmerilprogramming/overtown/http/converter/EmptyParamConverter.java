package org.esmerilprogramming.overtown.http.converter;

import org.esmerilprogramming.overtown.http.OvertownRequest;

/**
 * @author efraimgentil (efraim.gentil@gmail.com)
 */
public class EmptyParamConverter implements ParameterConverter {

  @Override
  public <T> T translate(Class<T> clazz, String parameterName,
      OvertownRequest overtownRequest) {
    return null;
  }

}
