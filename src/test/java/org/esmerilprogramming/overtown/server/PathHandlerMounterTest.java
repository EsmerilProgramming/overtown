package org.esmerilprogramming.overtown.server;

import org.junit.Before;

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
