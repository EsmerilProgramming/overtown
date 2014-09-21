package org.esmerilprogramming.cloverx.server.mounters;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.esmerilprogramming.cloverx.annotation.session.IllegalSessionListenerException;
import org.esmerilprogramming.cloverx.annotation.session.OnSessionCreate;
import org.esmerilprogramming.cloverx.annotation.session.OnSessionDestroy;
import org.esmerilprogramming.cloverx.http.CloverXSession;

import io.undertow.server.HttpServerExchange;
import io.undertow.server.session.Session;
import io.undertow.server.session.SessionListener;
import io.undertow.server.session.SessionListener.SessionDestroyedReason;

public class SessionListenerMounterImpl implements SessionListenerMounter {

  @Override
  public SessionListener mount(Class<?> annotatedClass) {
    try {
      final Object newInstance = annotatedClass.newInstance();
      List<Method> allClassMethods = Arrays.asList( annotatedClass.getMethods() );
      
      final List<Method> createMethods = identifyOnCreateMethods( allClassMethods   );
      final List<Method> destroyMethods = identifyOnDestroyMethods( allClassMethods );
      
      SessionListener sl = new SessionListener() {
        private final Object instance = newInstance;
        @Override
        public void sessionCreated(Session session, HttpServerExchange exchange) {
          for (Method method : createMethods) {
            try {
              Object[] parameters = prepareParameterInjection(method, session, exchange, null);
              method.invoke( instance , parameters );
            } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
              e.printStackTrace();
            }
          }
        }

        @Override
        public void sessionDestroyed(Session session, HttpServerExchange exchange,
            SessionDestroyedReason reason) {
          for (Method method : destroyMethods) {
            try {
              Object[] parameters = prepareParameterInjection(method, session, exchange, reason);
              method.invoke( instance , parameters );
            } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
              e.printStackTrace();
            }
          }
        }

        public void attributeAdded(Session session, String name, Object value) {}
        public void attributeUpdated(Session session, String name, Object newValue, Object oldValue) {}
        public void attributeRemoved(Session session, String name, Object oldValue) {}
        public void sessionIdChanged(Session session, String oldSessionId) {}

      };
      return sl;
    } catch (InstantiationException | IllegalAccessException | IllegalSessionListenerException e) {
      e.printStackTrace();
    }
    return null;
  }
  
  protected Object[] prepareParameterInjection(Method method , Session session , HttpServerExchange exchange , SessionDestroyedReason reason){
    List<Object> parameters = new ArrayList<>();
    Class<?>[] parameterTypes = method.getParameterTypes();
    for (Class<?> class1 : parameterTypes) {
      if(class1.equals( CloverXSession.class ))
        parameters.add( new CloverXSession(exchange, session) );
      if(class1.equals( SessionDestroyedReason.class ))
        parameters.add( reason );
    }
    return parameters.toArray();
  }
  
  
  protected List<Method> identifyOnCreateMethods( List<Method> allClassMethods ){
    List<Method> methods = new ArrayList<>();
    for (Method method : allClassMethods) {
      if (method.getAnnotation(OnSessionCreate.class) != null && isValidOnCreateMethod(method)) {
        methods.add(method);
      }
    }
    return methods;
  }
  
  protected List<Method> identifyOnDestroyMethods( List<Method> allClassMethods ){
    List<Method> methods = new ArrayList<>();
    for (Method method : allClassMethods) {
      if (method.getAnnotation(OnSessionDestroy.class) != null && isValidOnDestroyMethod(method)) {
        methods.add(method);
      }
    }
    return methods;
  }
  
  protected boolean isValidOnCreateMethod(Method method){
    Class<?>[] parameterTypes = method.getParameterTypes();
    for (Class<?> class1 : parameterTypes) {
      if( class1.equals( CloverXSession.class ) )
        continue;
      else
        throw new IllegalSessionListenerException("OnSessionCreate annotated method can have only"
            + " parameter types CloverXSession or none parameter");
    }
    return true;
  }
  
  protected boolean isValidOnDestroyMethod(Method method){
    Class<?>[] parameterTypes = method.getParameterTypes();
    for (Class<?> class1 : parameterTypes) {
      if( class1.equals( CloverXSession.class ) || class1.equals( SessionDestroyedReason.class ) )
        continue;
      else
        throw new IllegalSessionListenerException("OnSessionDestroy annotated method can have only parameter types"
            + " of CloverXSession, SessionDestroyedReason or none parameter");
    }
    return true;
  }

}
