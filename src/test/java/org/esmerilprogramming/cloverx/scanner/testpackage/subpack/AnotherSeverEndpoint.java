package org.esmerilprogramming.cloverx.scanner.testpackage.subpack;

import javax.websocket.OnMessage;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint("testWS")
public class AnotherSeverEndpoint {
    
  @OnMessage
  public void message(String message){
    
  }
  
}
