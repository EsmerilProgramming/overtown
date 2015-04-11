package org.esmerilprogramming.overtown.server.mounters;

import static org.junit.Assert.*;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

import io.undertow.server.HttpServerExchange;
import io.undertow.server.session.Session;
import io.undertow.server.session.SessionListener;
import io.undertow.server.session.SessionListener.SessionDestroyedReason;

import org.esmerilprogramming.overtown.annotation.session.IllegalSessionListenerException;
import org.esmerilprogramming.overtown.http.CloverXSession;
import org.esmerilprogramming.overtown.server.mounters.helpers.TestSessionListener;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.*;

public class SessionListenerMounterTest {

  SessionListenerMounterImpl mounter;
  HttpServerExchange NULL_EXCHANGE = null;

  @Before
  public void setUp(){
    mounter = new SessionListenerMounterImpl();
  }

  @Test
  public void doesCreateASessionListenerFromAGenericClass(){
     SessionListener sl = mounter.mount(TestSessionListener.class);

     assertNotNull(sl);
  }

  @Test
  public void doesCreateSessionListenerAndInvokeOnCreateAnnotatedMethodsOnSessionCreation(){
    SessionListener sl = mounter.mount(TestSessionListener.class);

    sl.sessionCreated( mock(Session.class) , NULL_EXCHANGE );

    assertSame( 1 , TestSessionListener.onCreateCallCounter );
    assertSame( 0 , TestSessionListener.onDestroyCallCounter );
  }

  @Test
  public void doesCreateSessionListenerAndInvokeOnDestroyAnnotatedMethodsOnSessionDestruction(){
    SessionListener sl = mounter.mount(TestSessionListener.class);

    sl.sessionDestroyed( mock(Session.class) , NULL_EXCHANGE , SessionDestroyedReason.TIMEOUT );

    assertSame( 1 , TestSessionListener.onDestroyCallCounter );
    assertSame( 0 , TestSessionListener.onCreateCallCounter );
  }

  @Test
  public void doesIdentifyAllOnCreateAnnotatedMethodsFromAllClassMethods(){
    Method[] methods = TestSessionListener.class.getMethods();
    List<Method> allClassMethods = Arrays.asList( methods );

    List<Method> identifyOnCreateMethods = ((SessionListenerMounterImpl) mounter).identifyOnCreateMethods( allClassMethods );

    assertSame(1 , identifyOnCreateMethods.size() );
    assertEquals("onCreate",  identifyOnCreateMethods.get(0).getName() );
  }

  @Test
  public void doesPrepareInstanceCloverXSessionToInjectIntoTheMethodInvocation() throws NoSuchMethodException, SecurityException{
    Method method = TestSessionListener.class.getMethod("onCreateMethodTest", CloverXSession.class);

    Object[] parameters = mounter.prepareParameterInjection(method, mock(Session.class), NULL_EXCHANGE , null );

    assertSame( 1 , parameters.length );
    assertNotNull( parameters[0] );
    assertEquals( CloverXSession.class , parameters[0].getClass() );
  }

  @Test
  public void doesPrepareInstanceCloverXSessionAndSessionDestroyedReasonToInjectIntoTheMethodInvocation() throws NoSuchMethodException, SecurityException{
    Method method = TestSessionListener.class.getMethod("onDestroyMethodTest", CloverXSession.class , SessionDestroyedReason.class);

    Object[] parameters = mounter.prepareParameterInjection(method, mock(Session.class), NULL_EXCHANGE , SessionDestroyedReason.INVALIDATED );

    assertSame( 2 , parameters.length );
    assertNotNull( parameters[0] );
    assertEquals( CloverXSession.class , parameters[0].getClass() );
    assertNotNull( parameters[1] );
    assertEquals( SessionDestroyedReason.class , parameters[1].getClass() );
  }

  @Test
  public void doesReturnEmptyParameterArrayWhenThereIsNothingToBeInjectedIntoTheParameterInvocation() throws NoSuchMethodException, SecurityException{
    Method method = TestSessionListener.class.getMethod("onDestroyMethodTest");

    Object[] parameters = mounter.prepareParameterInjection(method, mock(Session.class), NULL_EXCHANGE , SessionDestroyedReason.INVALIDATED );

    assertSame( 0 , parameters.length );
  }

  @Test(expected = IllegalSessionListenerException.class)
  public void doesThrowIllegalSessionListenerExceptionWhenTheOnCreateSessionMethodHavaANotAcceptedParameterType() throws NoSuchMethodException, SecurityException{
    Method method = TestSessionListener.class.getMethod("onCreateMethodTest", String.class);
    @SuppressWarnings("unused")
    boolean validOnCreateMethod = mounter.isValidOnCreateMethod( method );
  }

  @Test
  public void doesReturnTrueWhenIsAValidOnSessionCreateMethodWithOvertownSessionAsParameter() throws NoSuchMethodException, SecurityException{
    Method method = TestSessionListener.class.getMethod("onCreateMethodTest", OvertownSession.class);

    boolean validOnCreateMethod = mounter.isValidOnCreateMethod( method );

    assertTrue(validOnCreateMethod);
  }

  @Test
  public void doesReturnTrueWhenIsAValidOnSessionCreateMethodWithoutParameters() throws NoSuchMethodException, SecurityException{
    Method method = TestSessionListener.class.getMethod("onCreateMethodTest" );

    boolean validOnCreateMethod = mounter.isValidOnCreateMethod( method );

    assertTrue(validOnCreateMethod);
  }

  @Test(expected = IllegalSessionListenerException.class)
  public void doesThrowIllegalSessionListenerExceptionWhenTheOnSessionDestroyMethodHavaANotAcceptedParameterType() throws NoSuchMethodException, SecurityException{
    Method method = TestSessionListener.class.getMethod("onDestroyMethodTest", String.class);
    @SuppressWarnings("unused")
    boolean validOnDestroyMethod = mounter.isValidOnDestroyMethod( method );
  }

  @Test
  public void doesReturnTrueWhenIsAValidOnSessionDestroyMethodWithOvertownSessionAsParameter() throws NoSuchMethodException, SecurityException{
    Method method = TestSessionListener.class.getMethod("onDestroyMethodTest", OvertownXSession.class);

    boolean validOnDestroyMethod = mounter.isValidOnDestroyMethod( method );

    assertTrue(validOnDestroyMethod);
  }

  @Test
  public void doesReturnTrueWhenIsAValidOnSessionDestroyMethodWithOvwetownSessionAndSessionDestroyReasonAsParameter() throws NoSuchMethodException, SecurityException{
    Method method = TestSessionListener.class.getMethod("onDestroyMethodTest", OvertownSession.class , SessionDestroyedReason.class);

    boolean validOnDestroyMethod = mounter.isValidOnDestroyMethod( method );

    assertTrue(validOnDestroyMethod);
  }

  @Test
  public void doesReturnTrueWhenIsAValidOnSessionDestroyMethodWithoutParameters() throws NoSuchMethodException, SecurityException{
    Method method = TestSessionListener.class.getMethod("onDestroyMethodTest" );

    boolean validOnDestroyMethod = mounter.isValidOnDestroyMethod( method );

    assertTrue(validOnDestroyMethod);
  }



}
