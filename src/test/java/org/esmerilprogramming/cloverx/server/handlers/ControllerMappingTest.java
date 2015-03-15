package org.esmerilprogramming.cloverx.server.handlers;

import org.esmerilprogramming.cloverx.http.HttpMethod;
import org.esmerilprogramming.cloverx.server.handlers.helpers.RestTestController;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;
import static org.junit.Assert.*;

/**
 * Created by efraimgentil<efraimgentil@gmail.com> on 28/01/15.
 */
public class ControllerMappingTest {

  ControllerMapping controllerMapping;

  @Before
  public void setUp(){
    controllerMapping = new ControllerMapping("does_not_matter_the_path" ,"does_not_matter_the_path");
  }

  @Test
  public void shouldAddAPathMapping() throws NoSuchMethodException {
    Method get = RestTestController.class.getMethod("get");
    Set<Method> methods = new LinkedHashSet<>(Arrays.asList(get) );

    controllerMapping.addPathMethods( methods );

    assertTrue( controllerMapping.getPathMappings().size() == 1 );
  }

  @Test
  public void shouldAddAPathMappingWithMethodNameIfNoPathIsSpecifiedInTheAnnotation() throws NoSuchMethodException {
    Method get = RestTestController.class.getMethod("get");
    Set<Method> methods = new LinkedHashSet<>(Arrays.asList(get) );

    controllerMapping.addPathMethods( methods );

    Set<PathMapping> pathMappings = controllerMapping.getPathMappings();
    PathMapping mapping = pathMappings.iterator().next();
    assertEquals( get.getName() , mapping.getPath() );
  }

  @Test
  public void shouldAddAPathMappingToTheSpecificHttpVerb() throws NoSuchMethodException {
    Method get = RestTestController.class.getMethod("get");
    Set<Method> methods = new LinkedHashSet<>(Arrays.asList(get) );

    controllerMapping.addPathMethods( methods );

    Set<PathMapping> pathMappings = controllerMapping.getPathMappings();
    for( PathMapping mapping : pathMappings ) {
      assertTrue("Should have created a PathMapping with GET method", HttpMethod.GET.equalsIgnoreCase( mapping.getHttpMethod() ) );
    }
  }

  @Test
  public void shouldAddAPathMappingToTheAllAnnotatedHttpVerb() throws NoSuchMethodException {
    Method getAndPost = RestTestController.class.getMethod("getAndPost");
    Set<Method> methods = new LinkedHashSet<>( Arrays.asList( getAndPost ) );

    controllerMapping.addPathMethods( methods );

    Set<PathMapping> pathMappings = controllerMapping.getPathMappings();
    assertTrue( controllerMapping.getPathMappings().size() == 2 );
    for( PathMapping mapping : pathMappings ) {
      assertEquals(getAndPost.getName(), mapping.getPath());
    }
  }

  @Test
  public void shouldAddAPathMappingGetAndPostByDefaultPathAnnotation() throws NoSuchMethodException {
    Method path = RestTestController.class.getMethod("path");
    Set<Method> methods = new LinkedHashSet<>( Arrays.asList( path ) );

    controllerMapping.addPathMethods( methods );

    Set<PathMapping> pathMappings = controllerMapping.getPathMappings();
    assertTrue( controllerMapping.getPathMappings().size() == 2 );
    for( PathMapping mapping : pathMappings ) {
      assertEquals(path.getName(), mapping.getPath());
    }
  }



}
