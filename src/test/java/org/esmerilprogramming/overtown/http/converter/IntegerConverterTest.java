package org.esmerilprogramming.overtown.http.converter;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

import org.esmerilprogramming.overtown.http.CloverXRequest;
import org.junit.Before;
import org.junit.Test;

public class IntegerConverterTest {

  private IntegerConverter translator;

  @Before
  public void setUp() {
    translator = new IntegerConverter();
  }

  @Test
  public void givenAIntegerParameterIdShouldTranslateTheValueToLongObject() {
    String paramName = "id";
    CloverXRequest cloverRequest = mock(CloverXRequest.class);
    when(cloverRequest.getParameter(paramName)).thenReturn("10");

    Integer value = translator.translate(Integer.class, paramName, cloverRequest);

    assertSame("Translated value should be a integer 10", 10, value);
  }

  @Test
  public void givenAIntegerParameterButWithANullValueInRequestShouldReturnNull() {
    String paramName = "id";
    CloverXRequest cloverRequest = mock(CloverXRequest.class);
    when(cloverRequest.getParameter(paramName)).thenReturn(null);

    Integer value = translator.translate(Integer.class, paramName, cloverRequest);

    assertSame("Translated value should have returned null ", null, value);
  }

  @Test
  public void givenAIntegerParameterAndARequestWithWrongParameterTypeShouldReturnNull() {
    String paramName = "id";
    CloverXRequest cloverRequest = mock(CloverXRequest.class);
    when(cloverRequest.getParameter(paramName)).thenReturn("NOT A NUMBER");

    Integer value = translator.translate(Integer.class, paramName, cloverRequest);

    assertSame("Translated value should have returned null ", null, value);
  }

}
