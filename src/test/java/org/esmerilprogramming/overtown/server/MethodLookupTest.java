package org.esmerilprogramming.overtown.server;

import com.thoughtworks.paranamer.BytecodeReadingParanamer;
import com.thoughtworks.paranamer.CachingParanamer;
import com.thoughtworks.paranamer.Paranamer;
import io.undertow.Undertow;
import org.esmerilprogramming.overtown.annotation.Converter;
import org.esmerilprogramming.overtown.annotation.path.Get;
import org.esmerilprogramming.overtown.annotation.path.Post;
import org.esmerilprogramming.overtown.http.converter.GenericConverter;
import org.junit.Test;
import org.reflections.ReflectionUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

public class MethodLookupTest {

  public MethodLookupTest() {
  }

  class StringLowerConverter extends GenericConverter<String>{
    @Override
    public String convert(String value) {
      return value.toLowerCase();
    }
  }

  public static void main(String ... a){
    Undertow.Builder builder = Undertow.builder();
  }

  public void teste( @Converter(StringLowerConverter.class) String name , String nah ){

  }

  @Post
  @Get
  public void lol(){

  }

  @Test
  public void should_get_all_get_and_post(){
    Class<MethodLookupTest> clazz = MethodLookupTest.class;
    System.out.println( ReflectionUtils.getAllMethods(clazz, ReflectionUtils.withAnnotation(Get.class)) );
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

    }
  }
}
