package org.esmerilprogramming.cloverx.http.parameter;

import org.esmerilprogramming.cloverx.http.CloverRequest;
import org.esmerilprogramming.cloverx.http.converter.ModelConverter;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

public class ModelTranslatorTest {
	
	private ModelConverter translator;
	private CloverRequest cloverRequest;
	
	@Before
	public void setUp(){
		translator = new ModelConverter();
		cloverRequest = mock(CloverRequest.class);
	}
	
	@Test
	public void doesTranslateTheModelWithTheRequestAttributes(){
		
		String parameterName = "testModel";
		
		TestModel testModel = translator.translate( TestModel.class , parameterName , cloverRequest);
		
		assertNotNull(testModel);
	}
	
}
