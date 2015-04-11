package org.esmerilprogramming.overtown.http.converter;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.esmerilprogramming.overtown.http.CloverXRequest;
import org.junit.Before;
import org.junit.Test;

public class StringConverterTest {


  private StringConverter translator;

  @Before
  public void setUp() {
    translator = new StringConverter();
  }

  @Test
  public void givenAParameterShouldTranslateToStringValue() {
    String parameterName = "name";
    String expectedVal = "SOME IMPORTANT VALUE";
    CloverXRequest cloverRequest = mock(CloverXRequest.class);
    when(cloverRequest.getParameter(parameterName)).thenReturn(expectedVal);

    String val = translator.translate(String.class, parameterName, cloverRequest);

    assertEquals(expectedVal, val);
  }

  @Test
  public void givenAParameterButWIthoutValueInRequestShouldReturnNull() {
    String parameterName = "name";
    String expectedVal = null;
    CloverXRequest cloverRequest = mock(CloverXRequest.class);
    when(cloverRequest.getParameter(parameterName)).thenReturn(expectedVal);

    String val = translator.translate(String.class, parameterName, cloverRequest);

    assertEquals(expectedVal, val);
  }

}
