package org.esmerilprogramming.overtown.http.converter;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.esmerilprogramming.overtown.http.OvertownRequest;
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
    OvertownRequest request = mock(OvertownRequest.class);
    when(request.getParameter(parameterName)).thenReturn(expectedVal);

    String val = translator.translate(String.class, parameterName, request);
    assertEquals(expectedVal, val);
  }

  @Test
  public void givenAParameterButWIthoutValueInRequestShouldReturnNull() {
    String parameterName = "name";
    String expectedVal = null;
    OvertownRequest = mock(OvertownRequest.class);
    when(cloverRequest.getParameter(parameterName)).thenReturn(expectedVal);

    String val = translator.translate(String.class, parameterName, cloverRequest);

    assertEquals(expectedVal, val);
  }

}
