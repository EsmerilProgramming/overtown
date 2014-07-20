package org.esmerilprogramming.cloverx.server;

import io.undertow.server.HttpServerExchange;
import io.undertow.server.ServerConnection;
import io.undertow.server.handlers.PathHandler;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

import javax.xml.ws.spi.http.HttpExchange;
import javax.xml.ws.spi.http.HttpHandler;

import org.esmerilprogramming.cloverx.annotation.Page;
import org.esmerilprogramming.cloverx.http.CloverXRequest;
import org.esmerilprogramming.cloverx.management.ManagementPage;
import org.esmerilprogramming.cloverx.server.PathHandlerMounter;
import org.junit.Before;
import org.junit.Test;

import com.thoughtworks.paranamer.AnnotationParanamer;
import com.thoughtworks.paranamer.BytecodeReadingParanamer;
import com.thoughtworks.paranamer.CachingParanamer;
import com.thoughtworks.paranamer.Paranamer;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

public class PathHandlerMounterTest {

  private PathHandlerMounter mounter;

  @Before
  public void setUp() {
    mounter = new PathHandlerMounter();
  }

  // @Test
  // public void givenAEmptyListOfParameterTypesShouldDoNothing(){
  // CloverRequest request = mock(CloverRequest.class);
  // Class<?>[] parameterTypes = { };
  // Object[] parameters = new Object[ parameterTypes.length ];
  // String[] parameterNames = new String[ parameterTypes.length ];
  //
  // parameters = mounter.translateParameters( parameterNames, parameterTypes, request );
  //
  // assertSame( 0 , parameters.length );
  // }
  //
  // @Test
  // public void
  // givenAListOfParameterTypesThatContainsHttpServerExchangeShouldAddAHttpServerExchangeInstance(){
  // CloverRequest request = mock(CloverRequest.class);
  // HttpServerExchange exchange = new HttpServerExchange(null);
  // Class<?>[] parameterTypes = { String.class , HttpServerExchange.class };
  // Object[] parameters = new Object[ parameterTypes.length ];
  // String[] parameterNames = new String[ parameterTypes.length ];
  // when( request.getExchange() ).thenReturn(exchange);
  //
  // parameters = mounter.translateParameters( parameterNames, parameterTypes, request );
  //
  // assertEquals( null , parameters[0] );
  // assertEquals( HttpServerExchange.class , parameters[1].getClass() );
  // }
  //
  // @Test
  // public void
  // givenAListOfParameterTypesThatDoesNotContainsHttpServerExchangeShouldNotChangeTheParameters(){
  // CloverRequest request = mock(CloverRequest.class);
  // Class<?>[] parameterTypes = { String.class , String.class };
  // Object[] parameters = new Object[ parameterTypes.length ];
  // String[] parameterNames = new String[ parameterTypes.length ];
  //
  // parameters = mounter.translateParameters( parameterNames, parameterTypes, request );
  //
  // assertEquals( null , parameters[0] );
  // assertEquals( null , parameters[1] );
  // }
  //
  // @Test
  // public void
  // givenAListOfParameterTypesThatContainsCloverRequestShouldAddACloverInstanceToTheParameterArray(){
  // CloverRequest request = new CloverRequest( new HttpServerExchange( mock(ServerConnection.class)
  // ));
  // Class<?>[] parameterTypes = { String.class , CloverRequest.class , Object.class };
  // String[] parameterNames = new String[ parameterTypes.length ];
  //
  // Object[] parameters = mounter.translateParameters( parameterNames, parameterTypes, request );
  //
  // assertEquals( null , parameters[0] );
  // assertEquals( CloverRequest.class , parameters[1].getClass() );
  // }
  //
  // @Test
  // public void givenAListOfParameterTypesThatDoesNotContainsCloverRequestShouldDoNothing(){
  // CloverRequest request = mock(CloverRequest.class);
  // Class<?>[] parameterTypes = { String.class , String.class , String.class };
  // String[] parameterNames = new String[ parameterTypes.length ];
  //
  // Object[] parameters = mounter.translateParameters( parameterNames, parameterTypes , request);
  //
  // assertEquals( null , parameters[0] );
  // assertEquals( null , parameters[1] );
  // }

}
