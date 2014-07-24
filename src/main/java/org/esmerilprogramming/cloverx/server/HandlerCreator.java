package org.esmerilprogramming.cloverx.server;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import org.esmerilprogramming.cloverx.annotation.Page;
import org.esmerilprogramming.cloverx.http.CloverXRequest;
import org.esmerilprogramming.cloverx.http.converter.ParametersConverter;
import org.esmerilprogramming.cloverx.server.mounters.ConverterMounter;
import org.esmerilprogramming.cloverx.server.mounters.ConverterMounterImpl;
import org.esmerilprogramming.cloverx.view.ViewParser;
import org.jboss.logging.Logger;

import com.thoughtworks.paranamer.BytecodeReadingParanamer;
import com.thoughtworks.paranamer.CachingParanamer;
import com.thoughtworks.paranamer.Paranamer;

import freemarker.template.TemplateException;
import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;

public class HandlerCreator {
  
  private Class<?> pageClass;
  private Method method;
  private List<Method> beforeExecutemethods;
  private Paranamer paranamer;
  
  public HandlerCreator() {
    paranamer = new CachingParanamer(new BytecodeReadingParanamer());
  }
  
  public HandlerCreator forPageClass(Class<?> pageClass){
    this.pageClass = pageClass;
    return this;
  }
  
  public HandlerCreator withPathMethod(Method method){
    this.method = method;
    return this;
  }
  
  public HandlerCreator withExecuteBeforeMethods(List<Method> methods){
    this.beforeExecutemethods = methods;
    return this;
  }
  
  public HttpHandler mount(){
    return createHandlerForPage(pageClass, method , beforeExecutemethods);
  }
  
  private HttpHandler createHandlerForPage(final Class<?> pageClass , final Method method , final List<Method> beforeExecutemethods){
    
    final ConverterMounter converterMounter = new ConverterMounterImpl();
    final String[] parameterNames = paranamer.lookupParameterNames(method);
    Page methodPagePath = method.getAnnotation(Page.class);
    final String responseTemplate = methodPagePath.responseTemplate();
    
    return new HttpHandler() {
      
      private final Logger LOGGER = Logger.getLogger(pageClass);
      
      public void handleRequest(HttpServerExchange exchange) throws Exception {
        Object newInstance;
        try {
          newInstance = pageClass.getConstructor().newInstance();
          try {
            Class<?>[] parameterTypes = method.getParameterTypes();
            CloverXRequest request = new CloverXRequest(exchange);
            //Call before methods
            for (Method method : beforeExecutemethods) {
              method.invoke(newInstance, request);
            }
            
            request = converterMounter.mountParameterConveters(method, parameterNames, request);
            
            ParametersConverter translator = new ParametersConverter();
            Object[] parameters =  translator.translateAllParameters(parameterNames, parameterTypes, request);
            method.invoke(newInstance, parameters);

            if (!Page.NO_TEMPLATE.equals(responseTemplate)) {
              try {
                String parsedTemplate =
                    new ViewParser().parse(request.getViewAttributes(), responseTemplate);
                exchange.getResponseSender().send(parsedTemplate);
              } catch (TemplateException e) {
                LOGGER.error(e.getMessage());
              }
            }
          } catch (IllegalAccessException | IllegalArgumentException
              | InvocationTargetException e) {
            LOGGER.error(e.getMessage());

          }
        } catch (InstantiationException | IllegalAccessException | IllegalArgumentException
            | InvocationTargetException | NoSuchMethodException | SecurityException e1) {
          LOGGER.error(e1.getMessage());
        }
      }
    };
  }
  
}
