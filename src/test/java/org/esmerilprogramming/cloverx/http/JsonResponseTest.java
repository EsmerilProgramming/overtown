package org.esmerilprogramming.cloverx.http;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.Map;

import io.undertow.server.HttpServerExchange;
import io.undertow.server.ServerConnection;

import org.esmerilprogramming.cloverx.http.converter.DefaultObjectToJsonConverter;
import org.esmerilprogramming.cloverx.http.converter.ObjectToJsonConverter;
import org.esmerilprogramming.cloverx.http.converter.TestModel;
import org.esmerilprogramming.cloverx.view.ViewAttributes;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class JsonResponseTest {

  JsonResponse jsonResponse;
  @Mock
  ServerConnection connection;
  HttpServerExchange exchange;
  ViewAttributes viewAttributes;

  @Before
  public void setUp() {
    exchange = new HttpServerExchange(connection);
    viewAttributes = new ViewAttributes();
    jsonResponse = new JsonResponse(exchange, viewAttributes);
  }

  @Test(expected = IllegalStateException.class)
  public void doesThrowIllegalStateExceptionWhenTryToRedirectWithAJsonResponse() {
    jsonResponse.sendRedirect("/NO_NO_NO_NO_DO_NOT_DO_THAT");
  }

  @Test(expected = IllegalStateException.class)
  public void doesThrowIllegalStateExceptionWhenTryToChangeTheContentTypeOfAJsonResponse() {
    jsonResponse.setContentType("application/pdf");
  }

  @Test
  public void doesAddAttributeToViewAttributes() {

    jsonResponse.addAttribute("hello", "world");

    assertTrue("Should contains the hello attribute key", viewAttributes.attributeExists("hello"));
  }
  
  //Will not add the default convert, on the other hand in the moment of the response will use
  // the defaultConveter if none is set
  @Ignore  
  @Test
  public void doesAddToConvertesTheDefaultConverterToAnyAddedAttribute() {
    jsonResponse.addAttribute("hello", "world");

    Map<String, ObjectToJsonConverter> converters = jsonResponse.getConverters();
    assertTrue("Should contains a converter to the added attribute",
        converters.containsKey("hello"));
    assertTrue("Should be a instance of DefaultConverter",
        converters.get("hello") instanceof DefaultObjectToJsonConverter);
  }

  @Test
  public void doesReturnAddedStringAttributesAsJsonString() {
    jsonResponse.addAttribute("hello", "world");

    String generateStringResponse = jsonResponse.generateStringResponse();

    assertEquals("{\"hello\":\"world\"}", generateStringResponse);
  }

  @Test
  public void doesReturnAddedPrimitiveIntegerAttributesAsJsonString() {
    jsonResponse.addAttribute("age", 26);

    String generateStringResponse = jsonResponse.generateStringResponse();

    assertEquals("{\"age\":26}", generateStringResponse);
  }

  @Test
  public void doesReturnAddedIntegerAttributesAsJsonString() {
    jsonResponse.addAttribute("age", new Integer(26));

    String generateStringResponse = jsonResponse.generateStringResponse();

    assertEquals("{\"age\":26}", generateStringResponse);
  }

  @Test
  public void doesReturnAddedPrimitiveDoubleAttributesAsJsonString() {
    jsonResponse.addAttribute("salary", 100.99d);

    String generateStringResponse = jsonResponse.generateStringResponse();

    assertEquals("{\"salary\":100.99}", generateStringResponse);
  }

  @Test
  public void doesReturnAddedDoubleAttributesAsJsonString() {
    jsonResponse.addAttribute("salary", new Double(100.99));

    String generateStringResponse = jsonResponse.generateStringResponse();

    assertEquals("{\"salary\":100.99}", generateStringResponse);
  }

  @Test
  public void doesReturnPrimitiveFloatAttributesAsJsonString() {
    jsonResponse.addAttribute("salary", 100.99f);

    String generateStringResponse = jsonResponse.generateStringResponse();
    // float value includes many decimal values
    assertTrue(generateStringResponse.startsWith("{\"salary\":100.9"));
  }

  @Test
  public void doesReturnFloatAttributesAsJsonString() {
    jsonResponse.addAttribute("salary", new Float(100.99));

    String generateStringResponse = jsonResponse.generateStringResponse();
    // float value includes many decimal values
    assertTrue(generateStringResponse.startsWith("{\"salary\":100.9"));
  }

  @Test
  public void doesReturnPrimitiveLongAttributesAsJson() {
    jsonResponse.addAttribute("taxes", 1000000000000l);

    String generateStringResponse = jsonResponse.generateStringResponse();

    assertEquals("{\"taxes\":1000000000000}", generateStringResponse);
  }

  @Test
  public void doesReturnLongAttributesAsJson() {
    jsonResponse.addAttribute("taxes", new Long(1000000000000l));

    String generateStringResponse = jsonResponse.generateStringResponse();

    assertEquals("{\"taxes\":1000000000000}", generateStringResponse);
  }

  @Test
  public void doesReturnPrimitiveBooleanAttributesAsJson() {
    jsonResponse.addAttribute("pool", true);

    String generateStringResponse = jsonResponse.generateStringResponse();

    assertEquals("{\"pool\":true}", generateStringResponse);
  }

  @Test
  public void doesReturnBooleanAttributesAsJson() {
    jsonResponse.addAttribute("pool", new Boolean(true));

    String generateStringResponse = jsonResponse.generateStringResponse();

    assertEquals("{\"pool\":true}", generateStringResponse);
  }

  @Test
  public void doesReturnBigDecimalAttributesAsJson() {
    jsonResponse.addAttribute("over", new BigDecimal(9999.99).setScale(2, RoundingMode.HALF_UP));

    String generateStringResponse = jsonResponse.generateStringResponse();

    assertEquals("{\"over\":9999.99}", generateStringResponse);
  }

  @Test
  public void doesReturnBigIntegerAttributesAsJson() {
    jsonResponse.addAttribute("over", new BigInteger("99"));

    String generateStringResponse = jsonResponse.generateStringResponse();

    assertEquals("{\"over\":99}", generateStringResponse);
  }
  
  @Test
  public void doesReturnComplexJson(){
    jsonResponse.addAttribute("bool", false);
    jsonResponse.addAttribute("over", new BigInteger("99"));
    jsonResponse.addAttribute("model", new TestModel() );
    
    String response = jsonResponse.generateStringResponse();
    
    assertTrue( response.contains( "\"over\":99" ) );
    assertTrue( response.contains( "\"bool\":false" ) );
    assertTrue( response.contains( "\"model\":{\"name\":\"null\",\"age\":\"null\"}" ) );
  }

}
