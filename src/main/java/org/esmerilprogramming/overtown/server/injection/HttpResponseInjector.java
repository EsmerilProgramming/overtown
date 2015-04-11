package org.esmerilprogramming.overtown.server.injection;

import org.esmerilprogramming.overtown.http.CloverXRequest;

public class HttpResponseInjector implements CoreInjector {

  @SuppressWarnings("unchecked")
  @Override
  public <T> T inject(Class<T> clazz, String parameterName, CloverXRequest cloverRequest) {
    cloverRequest.respondAsHttp();
    return (T) cloverRequest.getResponse();
  }

}
