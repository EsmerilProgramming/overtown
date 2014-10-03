package org.esmerilprogramming.cloverx.server.mounters;

import java.lang.reflect.Method;
import java.util.Map;

import org.esmerilprogramming.cloverx.http.CloverXRequest;
import org.esmerilprogramming.cloverx.http.converter.GenericConverter;

public interface ConverterMounter {
  
  public  Map<String, GenericConverter<?>> mountParameterConveters(Method method, String[] parameterNames);
  
}
