package org.esmerilprogramming.cloverx.server.handlers;

import com.thoughtworks.paranamer.Paranamer;
import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;
import org.esmerilprogramming.cloverx.annotation.JSONResponse;
import org.esmerilprogramming.cloverx.annotation.Page;
import org.esmerilprogramming.cloverx.http.CloverXRequest;
import org.esmerilprogramming.cloverx.http.HttpResponse;
import org.esmerilprogramming.cloverx.http.ParameterConverterMounter;
import org.esmerilprogramming.cloverx.http.Response;
import org.esmerilprogramming.cloverx.http.converter.GenericConverter;
import org.esmerilprogramming.cloverx.http.converter.ParameterConverter;
import org.esmerilprogramming.cloverx.http.converter.ParametersConverter;
import org.esmerilprogramming.cloverx.server.injection.CoreClassInjector;
import org.esmerilprogramming.cloverx.server.injection.CoreClassInjectorImpl;
import org.esmerilprogramming.cloverx.server.mounters.ConverterMounterImpl;
import org.jboss.logging.Logger;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Set;

/**
 * Created by efraimgentil<efraimgentil@gmail.com> on 28/01/15.
 */
public class MainHttpHandler implements HttpHandler {

  private final Logger LOGGER;

  private Class<?> controller;
  private Method method;
  private final String[] parameterNames;
  private Set<Method> beforeTranslationMethods;
  private Map<String, GenericConverter<?>> converterMap;
  private Map<String, ParameterConverter> paramConverterMap;
  private String responseTemplate;
  private boolean isJsonResponse;

  public MainHttpHandler(ControllerMapping controller , PathMapping pathMapping, String[] parameterNames){
    this.controller = controller.getControllerClass();
    this.beforeTranslationMethods = controller.getBeforeTranslationMethods();
    this.method = pathMapping.getMethod();
    this.responseTemplate = pathMapping.getTemplate();
    this.parameterNames = parameterNames;
    this.isJsonResponse = pathMapping.isJsonResponse();

    this.converterMap = new ConverterMounterImpl().mountParameterConveters(method, parameterNames);
    this.paramConverterMap = new ParameterConverterMounter().identifyParametersTranslators(parameterNames, method.getParameterTypes());
    LOGGER = Logger.getLogger(this.controller);
  }

  @Override
  public void handleRequest(HttpServerExchange exchange) throws Exception {
    Object newInstance = controller.getConstructor().newInstance();
    try {
      Class<?>[] parameterTypes = method.getParameterTypes();
      CloverXRequest request = new CloverXRequest(exchange);

      for (Method method : beforeTranslationMethods) {
        method.invoke(newInstance, request);
      }
      request.addConverters(converterMap);
      ParametersConverter translator = new ParametersConverter(paramConverterMap);

      Object[] parameters =
              translator.translateParameters(parameterNames, parameterTypes, request);
      CoreClassInjector injector = new CoreClassInjectorImpl();
      parameters = injector.injectCoreInstances(parameterNames, parameters, parameterTypes, request);

      method.invoke(newInstance, parameters);

      finishResponse( request );
    } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
      e.printStackTrace();
      LOGGER.error(e.getMessage());
    }
  }

  public void finishResponse(CloverXRequest request){
    if(isJsonResponse){
      request.respondAsJson();
    }
    Response response = request.getResponse();
    if (!Page.NO_TEMPLATE.equals(responseTemplate) && !response.isResponseSend()) {
      request.respondAsHttp();
      request.addAttribute("request" , request );
      request.addAttribute("contextPath" , request.getExchange().getResolvedPath() );
              ((HttpResponse) request.getResponse()).fowardTo(responseTemplate);
    } else {
      if (!response.isResponseSend()) {
        response.finishResponse();
      }
    }
  }

}
