package org.esmerilprogramming.cloverx.http.converter;

import static org.junit.Assert.*;

import javax.json.JsonObject;

import org.junit.Before;
import org.junit.Test;

public class DefaultObjectToJsonConverterTest {
	
	DefaultObjectToJsonConverter converter;
	
	@Before
	public void setUp(){
		converter = new DefaultObjectToJsonConverter();
	}
	
	@Test
	public void givenAObjectShouldTranslateToJsonObject(){
		Object value = new TestModel();
		
		JsonObject jsonObject = converter.converter(value);
		
		assertTrue( jsonObject.containsKey("name") );
		assertTrue( jsonObject.containsKey("age") ); 
	}
	
	@Test
	public void givenAObjectShouldTranslateToJsonObjectWithTheValuesContainedInTheObject(){
		TestModel value = new TestModel();
		value.setName("efraim gentil");
		value.setAge(26);
		
		JsonObject jsonObject = converter.converter(value);
		
		assertEquals( value.getName() ,  jsonObject.get("name").toString() );
		assertEquals( value.getAge().toString() ,  jsonObject.get("age").toString() );
	}
	
	
}
