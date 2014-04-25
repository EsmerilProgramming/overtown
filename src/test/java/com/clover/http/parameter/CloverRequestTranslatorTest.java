package com.clover.http.parameter;

import org.junit.Before;
import org.junit.Test;

import com.clover.http.CloverRequest;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class CloverRequestTranslatorTest {
	
	private CloverRequestTranslator translator;
	
	@Before
	public void setUp(){
		translator = new CloverRequestTranslator();
	}
	
	@Test
	public void givenACloverRequestParameterShouldTranslateWithTheCurrentCloverRequest(){
		Class<CloverRequest> clazz = CloverRequest.class;
		String parameterName = "clover";
		CloverRequest cloverRequest = mock(CloverRequest.class);
		
		CloverRequest translated = translator.translate(clazz, parameterName, cloverRequest);
		
		assertNotNull(translated);
		assertEquals( cloverRequest , translated );
	}
	
}