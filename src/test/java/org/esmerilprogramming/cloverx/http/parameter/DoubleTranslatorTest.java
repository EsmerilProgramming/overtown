package org.esmerilprogramming.cloverx.http.parameter;

import static org.mockito.Mockito.mock;

import org.esmerilprogramming.cloverx.http.CloverRequest;
import org.esmerilprogramming.cloverx.http.converter.DoubleConverter;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class DoubleTranslatorTest {

  private DoubleConverter translator;

  @Before
  public void setUp() {
    translator = new DoubleConverter();
  }

  @Test
  public void givenADoubleParameterShouldTranslateTheStringValueFromTheRequestToDoubleValue() {
    String parameterName = "double";
    String stringValue = "10.00";
    Double expectedValue = 10.0;
    CloverRequest cloverRequest = mock(CloverRequest.class);
    when(cloverRequest.getAttribute(parameterName)).thenReturn(stringValue);

    Double val = translator.translate(Double.class, parameterName, cloverRequest);

    assertEquals(expectedValue, val, 0.0001);
  }

  @Test
  public void givenADoubleParameterWithWrongStringValueShouldShouldReturnNull() {
    String parameterName = "double";
    String stringValue = "10,00";
    Double expectedValue = null;
    CloverRequest cloverRequest = mock(CloverRequest.class);
    when(cloverRequest.getAttribute(parameterName)).thenReturn(stringValue);

    Double val = translator.translate(Double.class, parameterName, cloverRequest);

    assertEquals(expectedValue, val);
  }


  @Test
  public void givenADoubleParameterWithNullValueShouldShouldReturnNull() {
    String parameterName = "double";
    String stringValue = null;
    Double expectedValue = null;
    CloverRequest cloverRequest = mock(CloverRequest.class);
    when(cloverRequest.getAttribute(parameterName)).thenReturn(stringValue);

    Double val = translator.translate(Double.class, parameterName, cloverRequest);

    assertEquals(expectedValue, val);
  }


}
