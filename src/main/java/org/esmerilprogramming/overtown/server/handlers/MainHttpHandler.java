package org.esmerilprogramming.overtown.server.handlers;

import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;
import org.esmerilprogramming.overtown.annotation.Page;
import org.esmerilprogramming.overtown.http.*;
import org.esmerilprogramming.overtown.http.converter.GenericConverter;
import org.esmerilprogramming.overtown.http.converter.ParameterConverter;
import org.esmerilprogramming.overtown.http.converter.ParametersConverter;
import org.esmerilprogramming.overtown.server.injection.CoreClassInjector;
import org.esmerilprogramming.overtown.server.injection.CoreClassInjectorImpl;
import org.esmerilprogramming.overtown.server.mounters.ConverterMounterImpl;
import org.jboss.logging.Logger;

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
  private HttpHandler internalErrorHandler;

  public MainHttpHandler(ControllerMapping controller , PathMapping pathMapping, String[] parameterNames){
    this.controller = controller.getControllerClass();
    this.internalErrorHandler = controller.getInternalErrorHandler();
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
    OvertownRequest request = null;
    try {
      Class<?>[] parameterTypes = method.getParameterTypes();
      request = new OvertownRequest(exchange);


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
    } catch (Exception e) {
      if( request != null){
        try {
          if (internalErrorHandler != null) {

            exchange.putAttachment( request.EXCEPTION_ATTACHMENT_KEY , e);
            internalErrorHandler.handleRequest(exchange);
          }else{
            defaultErrorHandler(e, request);
          }
        }catch(Exception ex) {
          LOGGER.error("Your custom internal error handler throw a exception: " + ex.getMessage());
          defaultErrorHandler(ex, request);
        }
      }else{
        e.printStackTrace();
      }
    }
  }

  protected void defaultErrorHandler(Exception e , OvertownRequest request){
    request.addAttribute( ErrorHandler.ERROR_500 , e );
    new DefaultInternalErrorPage().handleError(request);
  }

  public void finishResponse(OvertownRequest request){
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
