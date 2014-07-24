package org.esmerilprogramming.cloverx.server.mounters;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import org.esmerilprogramming.cloverx.annotation.Converter;
import org.esmerilprogramming.cloverx.http.CloverXRequest;
import org.esmerilprogramming.cloverx.http.converter.GenericConverter;
import org.jboss.logging.Logger;

public class ConverterMounterImpl implements ConverterMounter {
  
  private static final Logger LOGGER = Logger.getLogger(ConverterMounterImpl.class);
  
  /**
   * Verify the method annotations and the parameter methods annotations to find any @Conveter
   * if found will add the custom converter to the CloverXRequest to be used in the parameter conversion 
   */
  @Override
  public CloverXRequest mountParameterConveters(Method method, String[] parameterNames,
      CloverXRequest request) {
    try {
      request = mountFromMethodConverterAnnotation(method, parameterNames, request);
      request = mountFromParamConveterAnnotation(method, parameterNames, request);
    } catch (InstantiationException e) {
      LOGGER.error("There is a problem on instantiating the converter, verify if the converter have a default constructor");
      e.printStackTrace();
    } catch (IllegalAccessException e) {
      e.printStackTrace();
    }
    return request;
  }
  
  private CloverXRequest mountFromMethodConverterAnnotation(Method method, String[] parameterNames , CloverXRequest request) throws InstantiationException, IllegalAccessException{
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
          request.addConverter(paramName, converterClass.newInstance() );
        }
      }
    }
    return request;
  }
  
  private CloverXRequest mountFromParamConveterAnnotation(Method method, String[] parameterNames , CloverXRequest request) throws InstantiationException, IllegalAccessException{
    Annotation[][] parameterAnnotations = method.getParameterAnnotations();
    for( int i = 0 ; i < parameterNames.length ; i++){
      String parameterName = parameterNames[i];
      Annotation[] ann = parameterAnnotations[i];
      for (Annotation annotation : ann) {
        if(annotation instanceof Converter){
          Class<? extends GenericConverter<?>> value = ((Converter) annotation).value();
          request.addConverter( parameterName ,  value.newInstance() );
        }
      }
    }
    return request;
  }
  
 
  
  
}
