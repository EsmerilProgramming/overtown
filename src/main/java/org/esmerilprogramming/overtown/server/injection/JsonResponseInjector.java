package org.esmerilprogramming.overtown.server.injection;

import org.esmerilprogramming.overtown.http.OvertownRequest;

public class JsonResponseInjector implements CoreInjector {

  @SuppressWarnings("unchecked")
  @Override
  public <T> T inject(Class<T> clazz, String parameterName, OvertownRequest cloverRequest) {
    cloverRequest.respondAsJson();
    return (T) cloverRequest.getJsonResponse();
  }

}
