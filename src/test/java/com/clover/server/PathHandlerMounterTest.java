package com.clover.server;

import io.undertow.server.handlers.PathHandler;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

import javax.xml.ws.spi.http.HttpExchange;
import javax.xml.ws.spi.http.HttpHandler;

import org.junit.Before;
import org.junit.Test;

import com.clover.annotation.Page;
import com.clover.management.ManagementPage;
import com.thoughtworks.paranamer.AnnotationParanamer;
import com.thoughtworks.paranamer.BytecodeReadingParanamer;
import com.thoughtworks.paranamer.CachingParanamer;
import com.thoughtworks.paranamer.Paranamer;

public class PathHandlerMounterTest {
	
	private PathHandlerMounter mounter;
	
	
	@Before
	public void setUp(){
		mounter = new PathHandlerMounter();
	}
	
	@Test
	public void t(){
		Class clazz = ManagementPage.class;
		
		
		 Paranamer paranamer = new CachingParanamer(new BytecodeReadingParanamer());
	 // throws ParameterNamesNotFoundException if not found

//		parameterNames = paranamer.lookupParameterNames(method, false)
		
		Method[] methods = clazz.getMethods();
		for (Method method : methods) {
			System.out.println( method.getName() );
			String[] parameterNames = paranamer.lookupParameterNames(method , false);
			System.out.println( Arrays.asList( parameterNames ) );
//			Type[] genericParameterTypes = method.getGenericParameterTypes();
//			for (Type type : genericParameterTypes) {
//				System.out.println( type );
//			}
		}
		
	}
	
	@Test
	public void identifyMethods(){
		final Class<?> clazz = ManagementPage.class;
		
		Page annotation = clazz.getAnnotation(Page.class);
		String[] initialPagePath = annotation.value();
		
		System.out.println( initialPagePath[0] );
		
		Method[] methods = clazz.getMethods();
		for (final Method method : methods) {
			Page methodPagePath = method.getAnnotation(Page.class);
			if(methodPagePath != null){
				System.out.println( methodPagePath.value()[0] );
				
				HttpHandler h = new HttpHandler() {
					@Override
					public void handle(HttpExchange exchange) throws IOException {
						Object newInstance;
						try {
							newInstance = clazz.getConstructor().newInstance();
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
				System.out.println( methodPagePath.value()[0] +    h );
			}
		}
		
	}
	
	
}
