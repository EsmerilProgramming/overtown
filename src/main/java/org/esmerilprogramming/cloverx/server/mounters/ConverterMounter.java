package org.esmerilprogramming.cloverx.server.mounters;

import java.lang.reflect.Method;

import org.esmerilprogramming.cloverx.http.CloverXRequest;

public interface ConverterMounter {
  
  public CloverXRequest mountParameterConveters(Method method, String[] parameterNames , CloverXRequest request);
  
}
