package com.clover.server;

import io.undertow.server.HttpServerExchange;
import io.undertow.server.ServerConnection;
import io.undertow.server.handlers.PathHandler;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

import javax.xml.ws.spi.http.HttpExchange;
import javax.xml.ws.spi.http.HttpHandler;

import org.junit.Before;
import org.junit.Test;

import com.clover.annotation.Page;
import com.clover.http.CloverRequest;
import com.clover.management.ManagementPage;
import com.thoughtworks.paranamer.AnnotationParanamer;
import com.thoughtworks.paranamer.BytecodeReadingParanamer;
import com.thoughtworks.paranamer.CachingParanamer;
import com.thoughtworks.paranamer.Paranamer;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

public class PathHandlerMounterTest {
	
	private PathHandlerMounter mounter;
	
	@Before
	public void setUp(){
		mounter = new PathHandlerMounter();
	}
	
	@Test
	public void givenAEmptyListOfParameterTypesShouldDoNothing(){
		HttpServerExchange exchange = new HttpServerExchange( mock(ServerConnection.class) );
		Class<?>[] parameterTypes = { };
		Object[] parameters = new Object[ parameterTypes.length ];
		String[] parameterNames = new String[ parameterTypes.length ];
		
		parameters = mounter.translateParameters(parameters, parameterNames, parameterTypes, exchange);
		
		assertSame(  0 , parameters.length );
	}
	
	@Test
	public void givenAListOfParameterTypesThatContainsHttpServerExchangeShouldAddAHttpServerExchangeInstance(){
		HttpServerExchange exchange = new HttpServerExchange( mock(ServerConnection.class) );
		Class<?>[] parameterTypes = { String.class , HttpServerExchange.class };
		Object[] parameters = new Object[ parameterTypes.length ];
		String[] parameterNames = new String[ parameterTypes.length ];
		
		parameters = mounter.translateParameters(parameters, parameterNames, parameterTypes, exchange);
		
		assertEquals( null , parameters[0] );
		assertEquals( HttpServerExchange.class , parameters[1].getClass() );
	}
	
	@Test
	public void givenAListOfParameterTypesThatDoesNotContainsHttpServerExchangeShouldNotChangeTheParameters(){
		HttpServerExchange exchange = new HttpServerExchange(null);
		Class<?>[] parameterTypes = { String.class , String.class };
		Object[] parameters = new Object[ parameterTypes.length ];
		String[] parameterNames = new String[ parameterTypes.length ];
		
		parameters = mounter.translateParameters(parameters, parameterNames, parameterTypes, exchange);
		
		assertEquals( null , parameters[0] );
		assertEquals( null , parameters[1] );
	}
	
	@Test
	public void givenAListOfParameterTypesThatContainsCloverRequestShouldAddACloverInstanceToTheParameterArray(){
		HttpServerExchange exchange = new HttpServerExchange(null);
		Class<?>[] parameterTypes = { String.class , CloverRequest.class , Object.class };
		Object[] parameters = new Object[ parameterTypes.length ];
		String[] parameterNames = new String[ parameterTypes.length ];
		
		parameters = mounter.translateParameters(parameters, parameterNames, parameterTypes, exchange);
		
		assertEquals( null , parameters[0] );
		assertEquals( CloverRequest.class , parameters[1].getClass() );
	}
	
	@Test
	public void givenAListOfParameterTypesThatDoesNotContainsCloverRequestShouldDoNothing(){
		HttpServerExchange exchange = new HttpServerExchange(null);
		Class<?>[] parameterTypes = { String.class , String.class , String.class };
		Object[] parameters = new Object[ parameterTypes.length ];
		String[] parameterNames = new String[ parameterTypes.length ];
		
		parameters = mounter.translateParameters(parameters, parameterNames, parameterTypes, exchange);
		
		assertEquals( null , parameters[0] );
		assertEquals( null , parameters[1] );
	}
	
}
