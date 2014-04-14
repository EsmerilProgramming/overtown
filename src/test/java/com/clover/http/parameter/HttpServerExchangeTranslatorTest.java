package com.clover.http.parameter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import io.undertow.server.HttpServerExchange;

import org.junit.Before;
import org.junit.Test;

import com.clover.http.CloverRequest;

import static org.mockito.Mockito.*;

public class HttpServerExchangeTranslatorTest {
	
	private HttpServerExchangeTranslator translator;
	
	@Before
	public void setUp(){
		translator = new HttpServerExchangeTranslator();
	}
	
	@Test
	public void givenAHttpServerExchangeClassObjectShouldReturnTrue(){
		boolean shouldTranslate = translator.shouldTranslate( HttpServerExchange.class );
		
		assertTrue("When is a HttpServerExchange class should be true" , shouldTranslate );
	}
	
	@Test
	public void givenAStringClassShoudReturnFalse(){
		boolean shouldTranslate = translator.shouldTranslate( String.class );
		
		assertFalse("When is a diferent class than HttpServerExchange class should be false" , shouldTranslate );
	}
	
	@Test
	public void givenAHttpServerExchangeParameterShouldTranslateWithTheCurrentCloverRequest(){
		Class<HttpServerExchange> clazz = HttpServerExchange.class;
		HttpServerExchange exchange = new HttpServerExchange(null);
		String parameterName = "exchange";
		CloverRequest cloverRequest = mock(CloverRequest.class);
		when( cloverRequest.getExchange() ).thenReturn(exchange);
		
		HttpServerExchange translated = translator.translate( clazz, parameterName, cloverRequest );
		
		assertNotNull(translated);
		assertEquals( exchange , translated );
	}
	
	@Test
	public void givenANonHttpServerExchangeClassShouldCallTheNextTranslatorInTheChain(){
		Class<String> clazz = String.class;
		String parameterName = "exchange";
		CloverRequest cloverRequest = mock(CloverRequest.class);
		EmptyParamTranslator translator2 = mock(EmptyParamTranslator.class);
		translator.setNextTranslator(translator2);
		
		translator.translate(clazz, parameterName, cloverRequest);
		
		verify( translator2 , times(1) ).translate(clazz, parameterName, cloverRequest);
	}
	
}
