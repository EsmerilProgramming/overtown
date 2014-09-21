package org.esmerilprogramming.cloverx.management.session;

import io.undertow.server.session.SessionListener.SessionDestroyedReason;

import org.esmerilprogramming.cloverx.annotation.session.OnSessionCreate;
import org.esmerilprogramming.cloverx.annotation.session.OnSessionDestroy;
import org.esmerilprogramming.cloverx.annotation.session.SessionListener;
import org.esmerilprogramming.cloverx.http.CloverXSession;

@SessionListener
public class SessionListenerManagement {

  @OnSessionCreate
  public void initSession(){
    System.out.println( "CALL ON CREATION" );
  }
  
  @OnSessionDestroy
  public void destroyThis(){
    System.out.println( "CALL ON DESTRUCTION" );
  }
  
  @OnSessionDestroy
  public void destroyThis(CloverXSession session){
    System.out.println( "Bye bye: " + session.getAttribute("nome") );
  }
  
  @OnSessionDestroy
  public void destroyThis(SessionDestroyedReason reason){
    System.out.println( "Destruction reason: " + reason ); 
  }

}
