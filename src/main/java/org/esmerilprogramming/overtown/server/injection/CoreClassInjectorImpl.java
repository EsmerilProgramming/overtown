package org.esmerilprogramming.overtown.server.injection;

import io.undertow.server.HttpServerExchange;

import org.esmerilprogramming.overtown.http.OvertownRequest;
import org.esmerilprogramming.overtown.http.HttpResponse;
import org.esmerilprogramming.overtown.http.JsonResponse;
import org.esmerilprogramming.overtown.view.ViewAttributes;

public class CoreClassInjectorImpl implements CoreClassInjector {

  
  @Override
  public Object[] injectCoreInstances(String[] parameterNames , Object[] parameters, Class<?>[] parameterTypes,
      OvertownRequest cloverRequest) {
    for (int i = 0 ; i < parameterTypes.length ; i++) {
        Class<?> clazz = parameterTypes[i];
        CoreInjector injector = getInjector( clazz );
        if(shouldInject(injector) ){
          parameters[i] = injector.inject(clazz, parameterNames[i] , cloverRequest);
        }
    }
    return parameters;
  }
  
  protected boolean shouldInject(CoreInjector injector){
    return injector != null;
  }
  
  protected CoreInjector getInjector(Class<?> clazz){
    if(OvertownRequest.class.equals(clazz))
      return new OvertownRequestInjector();
    if(ViewAttributes.class.equals(clazz))
      return new ViewAttributesInjector();
    if(HttpResponse.class.equals(clazz))
      return new HttpResponseInjector();
    if(JsonResponse.class.equals(clazz))
      return new JsonResponseInjector();
    if(HttpServerExchange.class.equals(clazz))
      return new HttpServerExchangeInjector();
    return null;
  }


}
