package org.esmerilprogramming.cloverx.http;

import java.util.Map;

import io.undertow.server.HttpServerExchange;
import io.undertow.server.ServerConnection;

import org.esmerilprogramming.cloverx.http.converter.DefaultObjectToJsonConverter;
import org.esmerilprogramming.cloverx.http.converter.ObjectToJsonConverter;
import org.esmerilprogramming.cloverx.view.ViewAttributes;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class JsonResponseTest {

	JsonResponse jsonResponse;
	@Mock ServerConnection connection;
	HttpServerExchange exchange;
	ViewAttributes viewAttributes;
	
	@Before
	public void setUp(){
		exchange =  new HttpServerExchange(connection);
		viewAttributes = new ViewAttributes();
		jsonResponse = new JsonResponse( exchange , viewAttributes );
	}
	
	@Test(expected = IllegalStateException.class)
	public void doesThrowIllegalStateExceptionWhenTryToRedirectWithAJsonResponse(){
		jsonResponse.sendRedirect("/NO_NO_NO_NO_DO_NOT_DO_THAT");
	}
	
	@Test(expected = IllegalStateException.class)
	public void doesThrowIllegalStateExceptionWhenTryToChangeTheContentTypeOfAJsonResponse(){
		jsonResponse.setContentType("application/pdf");
	}
	
	@Test
	public void doesAddAttributeToViewAttributes(){
		
		jsonResponse.addAttribute("hello", "world");
		
		assertTrue("Should contains the hello attribute key" , viewAttributes.attributeExists("hello") );
	}
	
	@Test
	public void doesAddToConvertesTheDefaultConverterToAnyAddedAttribute(){
		jsonResponse.addAttribute("hello", "world");
		
		Map<String, ObjectToJsonConverter> converters = jsonResponse.getConverters();
		assertTrue( "Should contains a converter to the added attribute" , converters.containsKey("hello") );
		assertTrue( "Should be a instance of DefaultConverter" , converters.get("hello") instanceof DefaultObjectToJsonConverter );
	}
	
	@Test
	public void doesReturnAddedStringAttributesAsJsonString(){
		jsonResponse.addAttribute("hello", "world");
		
		String generateStringResponse = jsonResponse.generateStringResponse();
	
		assertEquals( "{\"hello\":\"world\"}", generateStringResponse );
	}
	
	@Test
	public void doesReturnAddedDoubleAttributesAsJsonString(){
		jsonResponse.addAttribute("d1", new Double( 10.0 ) );
		jsonResponse.addAttribute("d2",  15.55 );
		
		String generateStringResponse = jsonResponse.generateStringResponse();
		
		assertNotNull(generateStringResponse);
		assertTrue( generateStringResponse.contains("\"d1\":10.0") );
		assertTrue( generateStringResponse.contains("\"d2\":15.55") );
	}
	
	@Test
	public void doesReturnAddedIntegerAttributesAsJsonString(){
		jsonResponse.addAttribute("i1", new Integer(10) );
		jsonResponse.addAttribute("i2",  120 );
		
		String generateStringResponse = jsonResponse.generateStringResponse();
		
		assertNotNull(generateStringResponse);
		assertTrue( generateStringResponse.contains("\"i1\":10") );
		assertTrue( generateStringResponse.contains("\"i2\":120") );
	}
	
	@Test
	public void doesReturnAddedBooleanAttributesAsJsonString(){
		jsonResponse.addAttribute("b1", new Boolean(false) );
		jsonResponse.addAttribute("b2",  true );
		
		String generateStringResponse = jsonResponse.generateStringResponse();
		
		
		System.out.println( generateStringResponse );
		
		assertNotNull(generateStringResponse);
		assertTrue( generateStringResponse.contains("\"b1\":false") );
		assertTrue( generateStringResponse.contains("\"b2\":true") );
	}
	
}
