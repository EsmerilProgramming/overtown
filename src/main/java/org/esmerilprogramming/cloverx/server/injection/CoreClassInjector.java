package org.esmerilprogramming.cloverx.server.injection;

import org.esmerilprogramming.cloverx.http.CloverXRequest;

public interface CoreClassInjector {
  
  public Object[] injectCoreInstances( String[] parameterNames, Object[] parameters, Class<?>[] parameterTypes, CloverXRequest cloverRequest);
  
}
