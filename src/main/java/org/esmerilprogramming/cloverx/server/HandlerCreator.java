package org.esmerilprogramming.cloverx.server;

import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;
import io.undertow.server.session.SessionAttachmentHandler;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

import org.esmerilprogramming.cloverx.annotation.Page;
import org.esmerilprogramming.cloverx.http.CloverXRequest;
import org.esmerilprogramming.cloverx.http.CloverXSessionManager;
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

import com.thoughtworks.paranamer.BytecodeReadingParanamer;
import com.thoughtworks.paranamer.CachingParanamer;
import com.thoughtworks.paranamer.Paranamer;

public class HandlerCreator {

  private Class<?> pageClass;
  private Method method;
  private List<Method> beforeExecutemethods;
  private Paranamer paranamer;

  public HandlerCreator() {
    paranamer = new CachingParanamer(new BytecodeReadingParanamer());
  }

  public HandlerCreator forPageClass(Class<?> pageClass) {
    this.pageClass = pageClass;
    return this;
  }

  public HandlerCreator withPathMethod(Method method) {
    this.method = method;
    return this;
  }

  public HandlerCreator withExecuteBeforeMethods(List<Method> methods) {
    this.beforeExecutemethods = methods;
    return this;
  }

  public HttpHandler mount() {
    return createHandlerForPage(pageClass, method, beforeExecutemethods);
  }

  private HttpHandler createHandlerForPage(final Class<?> pageClass, final Method method,
      final List<Method> beforeExecutemethods) {
    final String[] parameterNames = paranamer.lookupParameterNames(method);
    Page methodPagePath = method.getAnnotation(Page.class);
    final String responseTemplate = methodPagePath.responseTemplate();

    HttpHandler handler = null;
    handler = new HttpHandler() {
      private final Logger LOGGER = Logger.getLogger(pageClass);

      // private final Object newInstance = pageClass.getConstructor().newInstance();
      final Map<String, GenericConverter<?>> converterMap = new ConverterMounterImpl()
          .mountParameterConveters(method, parameterNames);
      final Map<String, ParameterConverter> paramConverterMap = new ParameterConverterMounter()
          .identifyParametersTranslators(parameterNames, method.getParameterTypes());

      public void handleRequest(HttpServerExchange exchange) throws Exception {
        Object newInstance = pageClass.getConstructor().newInstance();
        try {
          Class<?>[] parameterTypes = method.getParameterTypes();
          CloverXRequest request = new CloverXRequest(exchange);

          for (Method method : beforeExecutemethods) {
            method.invoke(newInstance, request);
          }
          request.addConverters(converterMap);
          ParametersConverter translator = new ParametersConverter(paramConverterMap);

          Object[] parameters =
              translator.translateParameters(parameterNames, parameterTypes, request);
          CoreClassInjector injector = new CoreClassInjectorImpl();
          parameters =
              injector.injectCoreInstances(parameterNames, parameters, parameterTypes, request);

          method.invoke(newInstance, parameters);

          Response response = request.getResponse();
          if (!Page.NO_TEMPLATE.equals(responseTemplate) && !response.isResponseSend()) {
            request.respondAsHttp();
            ((HttpResponse) request.getResponse()).fowardTo(responseTemplate);
          } else {
            // TODO should check json or xml response
            if (!response.isResponseSend()) {
              response.close();
            }
          }
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
          e.printStackTrace();
          LOGGER.error(e.getMessage());
        }
      }
    };

    CloverXSessionManager sessionManager = CloverXSessionManager.getInstance();
    return new SessionAttachmentHandler(handler, sessionManager.getSessionManager(),
        sessionManager.getSessionConfig());
  }

}
