package org.esmerilprogramming.overtown.server.injection;

import org.esmerilprogramming.overtown.http.CloverXRequest;

public interface CoreClassInjector {
  
  public Object[] injectCoreInstances( String[] parameterNames, Object[] parameters, Class<?>[] parameterTypes, CloverXRequest cloverRequest);
  
}
