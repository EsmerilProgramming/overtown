package org.esmerilprogramming.overtown.http;

import io.undertow.server.HttpServerExchange;
import io.undertow.server.session.InMemorySessionManager;
import io.undertow.server.session.Session;
import io.undertow.server.session.SessionConfig;
import io.undertow.server.session.SessionCookieConfig;

public class OvertownSessionManager {
  
  private final InMemorySessionManager sessionManager;
  private final SessionConfig sessionConfig;
  
  private static OvertownSessionManager manager;
  
  public static OvertownSessionManager getInstance(){
    if( manager == null){
      manager = new OvertownSessionManager();
    }
    return manager;
  }
  
  private OvertownSessionManager() {
    sessionManager = new InMemorySessionManager("CLOVERX");
    sessionConfig = new SessionCookieConfig();
  }
  
  private Session getUndertowSession(HttpServerExchange exchange){
    return getSessionManager().getSession(exchange, getSessionConfig() );
  }
  
  public OvertownSession createNewSession(HttpServerExchange exchange ){
    Session undertowSession = getUndertowSession(exchange);
    if(undertowSession != null ){
      undertowSession.invalidate(exchange);
    }
    return getSession(exchange);
  }
  
  public OvertownSession getSession(HttpServerExchange exchange){
    Session session = getUndertowSession(exchange);
    if(session == null){
      session = getSessionManager().createSession(exchange, getSessionConfig() ); 
    }
    return new OvertownSession(exchange, session);
  }
  
  //TODO
  //Needs exchange to operate in the session
  public OvertownSession getSessionById(String id , HttpServerExchange exchange ){
    Session session = getSessionManager().getSession(id);
    if(session != null){
      return new OvertownSession( exchange , session );
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
