package org.esmerilprogramming.overtown.http.converter;

import org.esmerilprogramming.overtown.http.OvertownRequest;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

public class ModelConverterTest {

  private ModelConverter translator;
  private OvertownRequest request;

  @Before
  public void setUp() {
    translator = new ModelConverter();
    request = mock(OvertownRequest.class);
  }

  @Test
  public void doesTranslateTheModelWithTheRequestAttributes() {
    String parameterName = "testModel";
    when(cloverRequest.containsAttributeStartingWith(parameterName)).thenReturn(true);
    when(cloverRequest.getParameter( parameterName + ".name") ).thenReturn("TESTE NAME");
    TestModel testModel = translator.translate(TestModel.class, parameterName, cloverRequest);

    assertNotNull(testModel);
  }

  @Test
  public void doesNotTranslateTheAttributeIfThereIsNoParameterStartingWithTheAttributeName(){
    String parameterName = "testModel";
    when(cloverRequest.containsAttributeStartingWith(parameterName)).thenReturn(false);

    TestModel testModel = translator.translate(TestModel.class, parameterName, cloverRequest);

    assertNull(testModel);
  }

}
