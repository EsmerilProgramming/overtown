package com.clover.http.parameter;

import org.junit.Before;
import org.junit.Test;

import com.clover.http.CloverRequest;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

public class ModelTranslatorTest {
	
	private ModelTranslator translator;
	private CloverRequest cloverRequest;
	
	@Before
	public void setUp(){
		translator = new ModelTranslator();
		cloverRequest = mock(CloverRequest.class);
	}
	
	@Test
	public void doesTranslateTheModelWithTheRequestAttributes(){
		
		String parameterName = "testModel";
		
		TestModel testModel = translator.translate( TestModel.class , parameterName , cloverRequest);
		
		assertNotNull(testModel);
	}
	
}
