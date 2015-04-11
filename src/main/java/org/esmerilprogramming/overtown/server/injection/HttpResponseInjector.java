package org.esmerilprogramming.overtown.server.injection;

import org.esmerilprogramming.overtown.http.OvertownRequest;

public class HttpResponseInjector implements CoreInjector {

  @SuppressWarnings("unchecked")
  @Override
  public <T> T inject(Class<T> clazz, String parameterName, OvertownRequest cloverRequest) {
    cloverRequest.respondAsHttp();
    return (T) cloverRequest.getResponse();
  }

}
