package com.clover.server;

import io.undertow.Handlers;
import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;
import io.undertow.server.handlers.PathHandler;
import io.undertow.server.handlers.resource.FileResourceManager;
import io.undertow.server.handlers.resource.Resource;
import io.undertow.server.handlers.resource.ResourceHandler;
import io.undertow.server.handlers.resource.ResourceManager;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

import javax.xml.ws.spi.http.HttpExchange;

import com.clover.annotation.Page;
import com.clover.http.CloverRequest;
import com.thoughtworks.paranamer.BytecodeReadingParanamer;
import com.thoughtworks.paranamer.CachingParanamer;
import com.thoughtworks.paranamer.Paranamer;

public class PathHandlerMounter {
	
	public PathHandler mount( List<Class<? extends HttpHandler>> handlers ){
		
		PathHandler pathHandler = Handlers.path();
		try{
			for (Class<?> handlerClass : handlers ) {
				Page pageAnnotation = handlerClass.getAnnotation( Page.class );
				if (pageAnnotation != null ) {
					Constructor<?> constructor = handlerClass.getConstructor();
					constructor.setAccessible(true);
					for (String path : pageAnnotation.value()) {
						HttpHandler handler = (HttpHandler) constructor.newInstance(); 
						pathHandler.addExactPath( path , handler );
					}
					
					mountMethods( pathHandler , handlerClass );
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return pathHandler;
	}
	
	protected PathHandler mountMethods(PathHandler pathHandler , final Class<?> handlerClass){
		Page pageAnnotation = handlerClass.getAnnotation( Page.class );
		
		Method[] methods = handlerClass.getMethods();
		
		for (final Method method : methods) {
			Page methodPagePath = method.getAnnotation(Page.class);
			if(methodPagePath != null){
				
				Paranamer paranamer = new CachingParanamer( new BytecodeReadingParanamer() );
				final String[] parameterNames = paranamer.lookupParameterNames(method);
				
				HttpHandler h = new HttpHandler()  {
					@Override
					public void handleRequest(HttpServerExchange exchange) throws IOException {
						Object newInstance;
						try {
							newInstance = handlerClass.getConstructor().newInstance();
							try {
								Class<?>[] parameterTypes = method.getParameterTypes();
								Object[] parameters = new Object[parameterTypes.length]; 
								
								translateParameters(parameters , parameterNames , parameterTypes , exchange);
								
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
				
				for ( String pageRoot : pageAnnotation.value() ) {
					for ( String methodRoot : methodPagePath.value()) {
						pathHandler.addExactPath( pageRoot + "/" + methodRoot , h );
					}
				}
				
			}
		}
		
		return pathHandler;
	}
	
	protected Object[] translateParameters( Object[] parameters , String[] parameterNames, Class<?>[] parameterTypes, HttpServerExchange exchange ){
		for (int i = 0 ; i < parameterTypes.length ; i++) {
			Class<?> clazz = parameterTypes[i];
			
			if( CloverRequest.class.equals( clazz ) )
				parameters[i] = new CloverRequest(exchange);
			else if( HttpServerExchange.class.equals( clazz ) )
				parameters[i] = exchange;
			else
				parameters[i] = setParamater( clazz, parameterNames[i] , new CloverRequest(exchange) );
			
		}
		
		return parameters;
	} 
	
	protected <T> T setParamater( Class<T> clazz, String parameterName , CloverRequest request ){
		if(String.class.equals(clazz))
			return (T) request.getAttribute(parameterName);
		
		return null;
	}
	
}