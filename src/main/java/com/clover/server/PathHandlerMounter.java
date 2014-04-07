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
import java.net.URL;
import java.util.List;

import javax.xml.ws.spi.http.HttpExchange;

import com.clover.annotation.Page;

public class PathHandlerMounter {
	
	public PathHandler mount( List<Class<? extends HttpHandler>> handlers ){
		
		PathHandler pathHandler = Handlers.path();
		try{
			for (Class<?> handlerClass : handlers ) {
				Page pageAnnotation = handlerClass.getAnnotation( Page.class );
				if (pageAnnotation != null ) {
					Constructor constructor = handlerClass.getConstructor();
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
				
				HttpHandler h = new HttpHandler()  {
					@Override
					public void handleRequest(HttpServerExchange exchange) throws IOException {
						Object newInstance;
						try {
							newInstance = handlerClass.getConstructor().newInstance();
							try {
								method.invoke( newInstance , exchange, null);
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
	
}