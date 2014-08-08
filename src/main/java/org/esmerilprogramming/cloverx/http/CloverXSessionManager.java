package org.esmerilprogramming.cloverx.http;

import io.undertow.server.HttpServerExchange;
import io.undertow.server.session.InMemorySessionManager;
import io.undertow.server.session.Session;
import io.undertow.server.session.SessionConfig;
import io.undertow.server.session.SessionCookieConfig;

public class CloverXSessionManager {
  
  private final InMemorySessionManager sessionManager;
  private final SessionConfig sessionConfig;
  
  private static CloverXSessionManager manager;
  
  public static CloverXSessionManager getInstance(){
    if( manager == null){
      manager = new CloverXSessionManager();
    }
    return manager;
  }
  
  private CloverXSessionManager() {
    sessionManager = new InMemorySessionManager("CLOVERX");
    sessionConfig = new SessionCookieConfig();
  }
  
  private Session getUndertowSession(HttpServerExchange exchange){
    return getSessionManager().getSession(exchange, getSessionConfig() );
  }
  
  public CloverXSession getSession(HttpServerExchange exchange){
    Session session = getUndertowSession(exchange);
    if(session != null){
      return new CloverXSession(exchange, session);
    }else{
      return null;
    }
  }
  
  public CloverXSession getOrCreateSession(HttpServerExchange exchange){
    Session session = getUndertowSession(exchange);
    if(session == null){
      session = getSessionManager().createSession(exchange, getSessionConfig() ); 
    }
    return new CloverXSession(exchange, session);
  }
  
  //TODO
  //Needs exchange to operate in the session
  public CloverXSession getSessionById(String id){
    Session session = getSessionManager().getSession(id);
    if(session != null){
      return new CloverXSession( session );
    }else{
      return null;
    }
  }

  public SessionConfig getSessionConfig() {
    return sessionConfig;
  }

  public InMemorySessionManager getSessionManager() {
    return sessionManager;
  }
  
}
