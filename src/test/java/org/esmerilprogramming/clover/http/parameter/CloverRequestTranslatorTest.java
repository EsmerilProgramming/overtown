package org.esmerilprogramming.clover.http.parameter;

import org.esmerilprogramming.cloverx.http.CloverRequest;
import org.esmerilprogramming.cloverx.http.converter.CloverRequestConverter;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class CloverRequestTranslatorTest {

  private CloverRequestConverter translator;

  @Before
  public void setUp() {
    translator = new CloverRequestConverter();
  }

  @Test
  public void givenACloverRequestParameterShouldTranslateWithTheCurrentCloverRequest() {
    Class<CloverRequest> clazz = CloverRequest.class;
    String parameterName = "clover";
    CloverRequest cloverRequest = mock(CloverRequest.class);

    CloverRequest translated = translator.translate(clazz, parameterName, cloverRequest);

    assertNotNull(translated);
    assertEquals(cloverRequest, translated);
  }

}
