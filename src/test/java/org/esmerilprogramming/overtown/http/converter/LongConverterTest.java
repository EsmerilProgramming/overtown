package org.esmerilprogramming.overtown.http.converter;

import org.esmerilprogramming.overtown.http.CloverXRequest;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

public class LongConverterTest {

  private LongConverter translator;

  @Before
  public void setUp() {
    translator = new LongConverter();
  }

  @Test
  public void givenALongParameterIdShouldTranslateTheValueToLongObject() {
    String paramName = "id";
    CloverXRequest cloverRequest = mock(CloverXRequest.class);
    when(cloverRequest.getParameter(paramName)).thenReturn("10");

    Long value = translator.translate(Long.class, paramName, cloverRequest);

    assertSame("Translated value should be a long 10", 10l, value);
  }

  @Test
  public void givenALongParameterButWithANullValueInRequestShouldReturnNull() {
    String paramName = "id";
    CloverXRequest cloverRequest = mock(CloverXRequest.class);
    when(cloverRequest.getParameter(paramName)).thenReturn(null);

    Long value = translator.translate(Long.class, paramName, cloverRequest);

    assertSame("Translated value should have returned null ", null, value);
  }

  @Test
  public void givenALongParameterAndARequestWithWrongParameterTypeShouldReturnNull() {
    String paramName = "id";
    CloverXRequest cloverRequest = mock(CloverXRequest.class);
    when(cloverRequest.getParameter(paramName)).thenReturn("NOT A NUMBER");

    Long value = translator.translate(Long.class, paramName, cloverRequest);

    assertSame("Translated value should have returned null ", null, value);
  }

}
