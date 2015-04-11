package org.esmerilprogramming.overtown.server.mounters;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.esmerilprogramming.overtown.annotation.Converter;
import org.esmerilprogramming.overtown.http.converter.GenericConverter;
import org.jboss.logging.Logger;

public class ConverterMounterImpl implements ConverterMounter {
  
  private static final Logger LOGGER = Logger.getLogger(ConverterMounterImpl.class);
  
  /**
   * Verify the method annotations and the parameter methods annotations to find any @Conveter
   * if found will add the custom converter to the CloverXRequest to be used in the parameter conversion 
   */
  @Override
  public  Map<String, GenericConverter<?>> mountParameterConveters(Method method, String[] parameterNames) {
    Map<String, GenericConverter<?>> converterMap = new HashMap<>();
    try {
      converterMap = mountFromMethodConverterAnnotation(method, parameterNames, converterMap);
      converterMap = mountFromParamConveterAnnotation(method, parameterNames, converterMap);
    } catch (InstantiationException e) {
      LOGGER.error("There is a problem on instantiating the converter, verify if the converter have a default constructor");
      e.printStackTrace();
    } catch (IllegalAccessException e) {
      e.printStackTrace();
    }
    return converterMap;
  }
  
  protected Map<String, GenericConverter<?>> mountFromMethodConverterAnnotation(Method method, String[] parameterNames , Map<String, GenericConverter<?>> converterMap) throws InstantiationException, IllegalAccessException{
    Annotation[] annotations = method.getAnnotations();
    for (Annotation annotation : annotations) {
      if(annotation instanceof Converter){
        Converter c = (Converter) annotation;
        String paramName = c.paramName();
        if( "".equals(paramName) ){
          LOGGER.warn("No paramName specified, the @Converter annotation will be ignored,"
              + " when using @Converter to annotating a method you should specify"
              + " the parameter name that will be converted");
        }else{
          Class<? extends GenericConverter<?>> converterClass = c.value();
          converterMap.put(paramName, converterClass.newInstance() );
        }
      }
    }
    return converterMap;
  }
  
  protected Map<String, GenericConverter<?>> mountFromParamConveterAnnotation(Method method, String[] parameterNames , Map<String, GenericConverter<?>> converterMap) throws InstantiationException, IllegalAccessException{
    Annotation[][] parameterAnnotations = method.getParameterAnnotations();
    for( int i = 0 ; i < parameterNames.length ; i++){
      String parameterName = parameterNames[i];
      Annotation[] ann = parameterAnnotations[i];
      for (Annotation annotation : ann) {
        if(annotation instanceof Converter){
          Class<? extends GenericConverter<?>> value = ((Converter) annotation).value();
          converterMap.put( parameterName ,  value.newInstance() );
        }
      }
    }
    return converterMap;
  }
  
}
