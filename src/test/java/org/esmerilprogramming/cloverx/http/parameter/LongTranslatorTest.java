package org.esmerilprogramming.cloverx.http.parameter;

import org.esmerilprogramming.cloverx.http.CloverRequest;
import org.esmerilprogramming.cloverx.http.converter.LongConverter;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

public class LongTranslatorTest {

  private LongConverter translator;

  @Before
  public void setUp() {
    translator = new LongConverter();
  }

  @Test
  public void givenALongParameterIdShouldTranslateTheValueToLongObject() {
    String paramName = "id";
    CloverRequest cloverRequest = mock(CloverRequest.class);
    when(cloverRequest.getAttribute(paramName)).thenReturn("10");

    Long value = translator.translate(Long.class, paramName, cloverRequest);

    assertSame("Translated value should be a long 10", 10l, value);
  }

  @Test
  public void givenALongParameterButWithANullValueInRequestShouldReturnNull() {
    String paramName = "id";
    CloverRequest cloverRequest = mock(CloverRequest.class);
    when(cloverRequest.getAttribute(paramName)).thenReturn(null);

    Long value = translator.translate(Long.class, paramName, cloverRequest);

    assertSame("Translated value should have returned null ", null, value);
  }

  @Test
  public void givenALongParameterAndARequestWithWrongParameterTypeShouldReturnNull() {
    String paramName = "id";
    CloverRequest cloverRequest = mock(CloverRequest.class);
    when(cloverRequest.getAttribute(paramName)).thenReturn("NOT A NUMBER");

    Long value = translator.translate(Long.class, paramName, cloverRequest);

    assertSame("Translated value should have returned null ", null, value);
  }

}
