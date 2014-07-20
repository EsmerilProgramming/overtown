package org.esmerilprogramming.cloverx.http.parameter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import io.undertow.server.HttpServerExchange;

import org.esmerilprogramming.cloverx.http.CloverRequest;
import org.esmerilprogramming.cloverx.http.converter.HttpServerExchangeConverter;
import org.junit.Before;
import org.junit.Test;

public class HttpServerExchangeTranslatorTest {

  private HttpServerExchangeConverter translator;

  @Before
  public void setUp() {
    translator = new HttpServerExchangeConverter();
  }

  @Test
  public void givenAHttpServerExchangeParameterShouldTranslateWithTheCurrentCloverRequest() {
    Class<HttpServerExchange> clazz = HttpServerExchange.class;
    HttpServerExchange exchange = new HttpServerExchange(null);
    String parameterName = "exchange";
    CloverRequest cloverRequest = mock(CloverRequest.class);
    when(cloverRequest.getExchange()).thenReturn(exchange);

    HttpServerExchange translated = translator.translate(clazz, parameterName, cloverRequest);

    assertNotNull(translated);
    assertEquals(exchange, translated);
  }

}
