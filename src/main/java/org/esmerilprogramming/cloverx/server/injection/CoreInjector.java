package org.esmerilprogramming.cloverx.server.injection;

import org.esmerilprogramming.cloverx.http.CloverXRequest;

public interface CoreInjector {
  
  public <T> T inject(Class<T> clazz, String parameterName, CloverXRequest cloverRequest);
  
}
