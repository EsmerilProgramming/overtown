package org.esmerilprogramming.clover.http.parameter;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

import org.esmerilprogramming.cloverx.http.CloverRequest;
import org.esmerilprogramming.cloverx.http.converter.IntegerConverter;
import org.junit.Before;
import org.junit.Test;

public class IntegerTranslatorTest {

  private IntegerConverter translator;

  @Before
  public void setUp() {
    translator = new IntegerConverter();
  }

  @Test
  public void givenAIntegerParameterIdShouldTranslateTheValueToLongObject() {
    String paramName = "id";
    CloverRequest cloverRequest = mock(CloverRequest.class);
    when(cloverRequest.getAttribute(paramName)).thenReturn("10");

    Integer value = translator.translate(Integer.class, paramName, cloverRequest);

    assertSame("Translated value should be a integer 10", 10, value);
  }

  @Test
  public void givenAIntegerParameterButWithANullValueInRequestShouldReturnNull() {
    String paramName = "id";
    CloverRequest cloverRequest = mock(CloverRequest.class);
    when(cloverRequest.getAttribute(paramName)).thenReturn(null);

    Integer value = translator.translate(Integer.class, paramName, cloverRequest);

    assertSame("Translated value should have returned null ", null, value);
  }

  @Test
  public void givenAIntegerParameterAndARequestWithWrongParameterTypeShouldReturnNull() {
    String paramName = "id";
    CloverRequest cloverRequest = mock(CloverRequest.class);
    when(cloverRequest.getAttribute(paramName)).thenReturn("NOT A NUMBER");

    Integer value = translator.translate(Integer.class, paramName, cloverRequest);

    assertSame("Translated value should have returned null ", null, value);
  }

}
