package org.esmerilprogramming.cloverx.http.parameter;

import org.esmerilprogramming.cloverx.http.CloverXRequest;
import org.esmerilprogramming.cloverx.http.converter.CloverXRequestConverter;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class CloverXRequestTranslatorTest {

  private CloverXRequestConverter translator;

  @Before
  public void setUp() {
    translator = new CloverXRequestConverter();
  }

  @Test
  public void givenACloverRequestParameterShouldTranslateWithTheCurrentCloverRequest() {
    Class<CloverXRequest> clazz = CloverXRequest.class;
    String parameterName = "clover";
    CloverXRequest cloverRequest = mock(CloverXRequest.class);

    CloverXRequest translated = translator.translate(clazz, parameterName, cloverRequest);

    assertNotNull(translated);
    assertEquals(cloverRequest, translated);
  }

}
