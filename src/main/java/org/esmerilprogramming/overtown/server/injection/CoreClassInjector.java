package org.esmerilprogramming.overtown.server.injection;

import org.esmerilprogramming.overtown.http.OvertownRequest;

public interface CoreClassInjector {
  
  public Object[] injectCoreInstances( String[] parameterNames, Object[] parameters, Class<?>[] parameterTypes, OvertownRequest cloverRequest);
  
}
