package org.esmerilprogramming.overtown.server.injection;

import org.esmerilprogramming.overtown.http.CloverXRequest;

public interface CoreInjector {
  
  public <T> T inject(Class<T> clazz, String parameterName, CloverXRequest cloverRequest);
  
}
