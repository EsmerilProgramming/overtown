package org.esmerilprogramming.overtown.management.session;

import io.undertow.server.session.SessionListener.SessionDestroyedReason;

import org.esmerilprogramming.overtown.annotation.session.OnSessionCreate;
import org.esmerilprogramming.overtown.annotation.session.OnSessionDestroy;
import org.esmerilprogramming.overtown.annotation.session.SessionListener;
import org.esmerilprogramming.overtown.http.OvertownSession;

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
  public void destroyThis(OvertownSession session){
    System.out.println( "Bye bye: " + session.getAttribute("nome") );
  }
  
  @OnSessionDestroy
  public void destroyThis(SessionDestroyedReason reason){
    System.out.println( "Destruction reason: " + reason ); 
  }

}
