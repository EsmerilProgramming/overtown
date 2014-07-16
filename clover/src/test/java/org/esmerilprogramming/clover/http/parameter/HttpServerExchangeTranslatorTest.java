package org.esmerilprogramming.clover.http.parameter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import io.undertow.server.HttpServerExchange;

import org.esmerilprogramming.clover.http.CloverRequest;
import org.esmerilprogramming.clover.http.parameter.HttpServerExchangeTranslator;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.*;

public class HttpServerExchangeTranslatorTest {
	
	private HttpServerExchangeTranslator translator;
	
	@Before
	public void setUp(){
		translator = new HttpServerExchangeTranslator();
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
	
}