package com.clover.http.parameter;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.clover.http.CloverRequest;

public class IntegerTranslatorTest {
	
	private IntegerTranslator translator;
	
	@Before
	public void setUp(){
		translator = new IntegerTranslator();
	}
	
	@Test
	public void givenAIntegerParameterIdShouldTranslateTheValueToLongObject(){
		String paramName = "id";
		CloverRequest cloverRequest = mock(CloverRequest.class);
		when(cloverRequest.getAttribute( paramName )).thenReturn("10");
		
		Integer value = translator.translate( Integer.class , paramName , cloverRequest);
		
		assertSame("Translated value should be a integer 10" , 10 , value );
	}
	
	@Test
	public void givenAIntegerParameterButWithANullValueInRequestShouldReturnNull(){
		String paramName = "id";
		CloverRequest cloverRequest = mock(CloverRequest.class);
		when(cloverRequest.getAttribute( paramName )).thenReturn( null );
		
		Integer value = translator.translate( Integer.class , paramName , cloverRequest);
		
		assertSame("Translated value should have returned null " , null , value );
	}
	
	@Test
	public void givenAIntegerParameterAndARequestWithWrongParameterTypeShouldReturnNull(){
		String paramName = "id";
		CloverRequest cloverRequest = mock(CloverRequest.class);
		when(cloverRequest.getAttribute( paramName )).thenReturn( "NOT A NUMBER" ); 
		
		Integer value = translator.translate( Integer.class , paramName , cloverRequest);
		
		assertSame("Translated value should have returned null " , null , value );
	}
	
}
