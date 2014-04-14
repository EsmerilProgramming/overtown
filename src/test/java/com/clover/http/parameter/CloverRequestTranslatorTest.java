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
	public void givenACloverRquestClassObjectShouldReturnTrue(){
		boolean shouldTranslate = translator.shouldTranslate( CloverRequest.class );
		
		assertTrue("When is a CloverRequest class should be true" , shouldTranslate );
	}
	
	@Test
	public void givenAStringClassShoudReturnFalse(){
		boolean shouldTranslate = translator.shouldTranslate( String.class );
		
		assertFalse("When is a diferent class than CloverRequest class should be false" , shouldTranslate );
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
	
	@Test
	public void givenANonCloverRequestClassShouldCallTheNextTranslatorInTheChain(){
		Class<String> clazz = String.class;
		String parameterName = "clover";
		CloverRequest cloverRequest = mock(CloverRequest.class);
		EmptyParamTranslator translator2 = mock(EmptyParamTranslator.class);
		translator.setNextTranslator(translator2);
		
		translator.translate(clazz, parameterName, cloverRequest);
		
		verify( translator2 , times(1) ).translate(clazz, parameterName, cloverRequest);
	}
	
}