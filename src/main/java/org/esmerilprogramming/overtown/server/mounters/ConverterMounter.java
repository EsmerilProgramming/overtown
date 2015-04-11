package org.esmerilprogramming.overtown.server.mounters;

import java.lang.reflect.Method;
import java.util.Map;

import org.esmerilprogramming.overtown.http.converter.GenericConverter;

public interface ConverterMounter {
  
  public  Map<String, GenericConverter<?>> mountParameterConveters(Method method, String[] parameterNames);
  
}
