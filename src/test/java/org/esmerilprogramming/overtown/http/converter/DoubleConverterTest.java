package org.esmerilprogramming.overtown.http.converter;

import static org.mockito.Mockito.mock;

import org.esmerilprogramming.overtown.http.OvertownRequest;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class DoubleConverterTest {

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
    OvertownRequest cloverRequest = mock(OvertownRequest.class);
    when(cloverRequest.getParameter(parameterName)).thenReturn(stringValue);

    Double val = translator.translate(Double.class, parameterName, cloverRequest);

    assertEquals(expectedValue, val, 0.0001);
  }

  @Test
  public void givenADoubleParameterWithWrongStringValueShouldShouldReturnNull() {
    String parameterName = "double";
    String stringValue = "10,00";
    Double expectedValue = null;
    OvertownRequest cloverRequest = mock(OvertownRequest.class);
    when(cloverRequest.getParameter(parameterName)).thenReturn(stringValue);

    Double val = translator.translate(Double.class, parameterName, cloverRequest);

    assertEquals(expectedValue, val);
  }


  @Test
  public void givenADoubleParameterWithNullValueShouldShouldReturnNull() {
    String parameterName = "double";
    String stringValue = null;
    Double expectedValue = null;
    OvertownRequest cloverRequest = mock(OvertownRequest.class);
    when(cloverRequest.getParameter(parameterName)).thenReturn(stringValue);

    Double val = translator.translate(Double.class, parameterName, cloverRequest);

    assertEquals(expectedValue, val);
  }


}
