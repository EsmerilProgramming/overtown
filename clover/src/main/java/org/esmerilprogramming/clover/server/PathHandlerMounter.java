package org.esmerilprogramming.clover.server;

import io.undertow.Handlers;
import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;
import io.undertow.server.handlers.PathHandler;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.esmerilprogramming.clover.annotation.BeforeTranslate;
import org.esmerilprogramming.clover.annotation.Controller;
import org.esmerilprogramming.clover.annotation.Page;
import org.esmerilprogramming.clover.http.CloverRequest;
import org.esmerilprogramming.clover.http.parameter.ParametersTranslator;

import com.thoughtworks.paranamer.BytecodeReadingParanamer;
import com.thoughtworks.paranamer.CachingParanamer;
import com.thoughtworks.paranamer.Paranamer;

public class PathHandlerMounter {
	
	public PathHandler mount( List<Class<? extends HttpHandler>> handlers ){
		
		PathHandler pathHandler = Handlers.path();
		try{
			for (Class<?> handlerClass : handlers ) {
				Controller controllerAnnotation = handlerClass.getAnnotation( Controller.class );
				if (controllerAnnotation != null ) {
					Constructor<?> constructor = handlerClass.getConstructor();
					constructor.setAccessible(true);
					mountMethods( pathHandler , handlerClass );
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return pathHandler;
	}
	
	protected PathHandler mountMethods(PathHandler pathHandler , final Class<?> handlerClass){
		Controller controllerAnnotation = handlerClass.getAnnotation( Controller.class );
		
		Method[] methods = handlerClass.getMethods();
		
		final List<Method> beforeTranslationMethods =  identifyBeforeTranslationMethod( methods );
		for (final Method method : methods) {
			Page methodPagePath = method.getAnnotation(Page.class);
			if(methodPagePath != null){
				
				Paranamer paranamer = new CachingParanamer( new BytecodeReadingParanamer() );
				final String[] parameterNames = paranamer.lookupParameterNames(method);
				
				HttpHandler h = new HttpHandler()  {
					@Override
					public void handleRequest(final HttpServerExchange exchange) throws IOException {
						Object newInstance;
						try {
							newInstance = handlerClass.getConstructor().newInstance();
							try {
								Class<?>[] parameterTypes = method.getParameterTypes();
								CloverRequest request = new CloverRequest(exchange);
								for (Method method : beforeTranslationMethods) {
									method.invoke( newInstance );
								}
								ParametersTranslator translator = new ParametersTranslator();
								Object[] parameters  = translator.translateAllParameters(parameterNames, parameterTypes, request );
								method.invoke( newInstance , parameters );
							} catch (IllegalAccessException
									| IllegalArgumentException
									| InvocationTargetException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						} catch (InstantiationException
								| IllegalAccessException
								| IllegalArgumentException
								| InvocationTargetException
								| NoSuchMethodException | SecurityException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}

				};
				
				for ( String pageRoot : controllerAnnotation.path() ) {
					for ( String methodRoot : methodPagePath.value()) {
						pathHandler.addExactPath( pageRoot + "/" + methodRoot , h );
					}
				}
				
			}
		}
		
		return pathHandler;
	}
	
	protected List<Method> identifyBeforeTranslationMethod(Method[] methods){
		List<Method> beforeTranslationMethods = new ArrayList<>();
		for (Method method : methods) {
			List<Annotation> asList = Arrays.asList( method.getAnnotations() );
			for (Annotation annotation : asList) {
				if( annotation instanceof BeforeTranslate ){
					beforeTranslationMethods.add(method);
				}
			}
		}
		return beforeTranslationMethods;
	}
	
	@SuppressWarnings("unchecked")
	protected <T> T setParamater( Class<T> clazz, String parameterName , CloverRequest request ){
		if(String.class.equals(clazz)){
			return (T) request.getAttribute(parameterName);
		}
		return null;
	}
	
}