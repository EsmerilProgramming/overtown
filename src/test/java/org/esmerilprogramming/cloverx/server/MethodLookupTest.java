package org.esmerilprogramming.cloverx.server;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import org.esmerilprogramming.cloverx.annotation.Converter;
import org.esmerilprogramming.cloverx.http.CloverXRequest;
import org.esmerilprogramming.cloverx.http.converter.GenericConverter;
import org.junit.Test;

import com.thoughtworks.paranamer.BytecodeReadingParanamer;
import com.thoughtworks.paranamer.CachingParanamer;
import com.thoughtworks.paranamer.Paranamer;

public class MethodLookupTest {

  public MethodLookupTest() {
    // TODO Auto-generated constructor stub
  }
  
  
  class StringLowerConverter extends GenericConverter<String>{

    @Override
    public String convert(String value) {
      return value.toLowerCase();
    }
    
  }
  
  public void teste( @Converter(StringLowerConverter.class) String name , String nah ){
    
  }
  
  @Test
  public void t(){
    
    Class<MethodLookupTest> clazz = MethodLookupTest.class;
    for(final Method method: clazz.getMethods()) {
      if( "teste".equalsIgnoreCase( method.getName() )){
        Paranamer paranamer = new CachingParanamer(new BytecodeReadingParanamer());
        final String[] parameterNames = paranamer.lookupParameterNames(method);
        Class<?>[] parameterTypes = method.getParameterTypes();
        
        Annotation[][] parameterAnnotations = method.getParameterAnnotations();
        for( int i = 0 ; i < parameterNames.length ; i++){
          String parameterName = parameterNames[i];
          Annotation[] ann = parameterAnnotations[i];
          for (Annotation annotation : ann) {
            System.out.println(parameterName + ": "  + annotation.toString() );
          }
        }
        
        
        for (Annotation[] ann : parameterAnnotations ) {
          
        }
        
      }
      
//      final String responseTemplate = methodPagePath.responseTemplate();
      
      
//      CloverXRequest request = new CloverXRequest(exchange);
//      for (Method method : beforeTranslationMethods) {
//        method.invoke( newInstance , request );
//      }
      
    }
    

    
  }
  
  
}
