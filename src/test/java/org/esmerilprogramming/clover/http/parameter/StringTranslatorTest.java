package org.esmerilprogramming.clover.http.parameter;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.esmerilprogramming.cloverx.http.CloverRequest;
import org.esmerilprogramming.cloverx.http.converter.StringConverter;
import org.junit.Before;
import org.junit.Test;

public class StringTranslatorTest {


  private StringConverter translator;

  @Before
  public void setUp() {
    translator = new StringConverter();
  }

  @Test
  public void givenAParameterShouldTranslateToStringValue() {
    String parameterName = "name";
    String expectedVal = "SOME IMPORTANT VALUE";
    CloverRequest cloverRequest = mock(CloverRequest.class);
    when(cloverRequest.getAttribute(parameterName)).thenReturn(expectedVal);

    String val = translator.translate(String.class, parameterName, cloverRequest);

    assertEquals(expectedVal, val);
  }

  @Test
  public void givenAParameterButWIthoutValueInRequestShouldReturnNull() {
    String parameterName = "name";
    String expectedVal = null;
    CloverRequest cloverRequest = mock(CloverRequest.class);
    when(cloverRequest.getAttribute(parameterName)).thenReturn(expectedVal);

    String val = translator.translate(String.class, parameterName, cloverRequest);

    assertEquals(expectedVal, val);
  }

}
