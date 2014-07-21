package org.esmerilprogramming.cloverx.http.converter;

import org.esmerilprogramming.cloverx.http.CloverXRequest;
import org.esmerilprogramming.cloverx.http.converter.ModelConverter;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

public class ModelConverterTest {

  private ModelConverter translator;
  private CloverXRequest cloverRequest;

  @Before
  public void setUp() {
    translator = new ModelConverter();
    cloverRequest = mock(CloverXRequest.class);
  }

  @Test
  public void doesTranslateTheModelWithTheRequestAttributes() {

    String parameterName = "testModel";

    TestModel testModel = translator.translate(TestModel.class, parameterName, cloverRequest);

    assertNotNull(testModel);
  }

}
