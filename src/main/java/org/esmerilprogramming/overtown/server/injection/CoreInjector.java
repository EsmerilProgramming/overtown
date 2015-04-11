package org.esmerilprogramming.overtown.server.injection;

import org.esmerilprogramming.overtown.http.OvertownRequest;

public interface CoreInjector {
  
  public <T> T inject(Class<T> clazz, String parameterName, OvertownRequest cloverRequest);
  
}
