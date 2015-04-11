package org.esmerilprogramming.overtown.management;

import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint("/myapp")
public class JsrChatWebSocketEndpoint {
  
    @OnOpen
    public void t(){
      System.out.println("Connection in the WS");
    }
    
    @OnMessage
    public void message(String message, Session session) {
        for (Session s : session.getOpenSessions()) {
            s.getAsyncRemote().sendText(message);
        }
    }

}