package org.esmerilprogramming.overtown.server.injection;

import org.esmerilprogramming.overtown.http.OvertownRequest;

/**
 * @author efraimgentil (efraim.gentil@gmail.com)
 */
public class OvertownRequestInjector implements CoreInjector {

  @SuppressWarnings("unchecked")
  @Override
  public <T> T inject(Class<T> clazz, String parameterName, OvertownRequest cloverRequest) {
    return (T) cloverRequest;
  }

}
