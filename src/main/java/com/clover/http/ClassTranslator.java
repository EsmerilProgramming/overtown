package com.clover.http;

import java.lang.reflect.Field;

public class ClassTranslator {
	
	
	public <T> T translate(CloverRequest request ,Class<T> clazz){
		Field[] declaredFields = clazz.getDeclaredFields();
		
		return null;
	}
	
}