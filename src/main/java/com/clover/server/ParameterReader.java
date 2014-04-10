package com.clover.server;

import java.lang.reflect.Method;

import com.thoughtworks.paranamer.BytecodeReadingParanamer;
import com.thoughtworks.paranamer.CachingParanamer;
import com.thoughtworks.paranamer.Paranamer;

public class ParameterReader {

	public ParameterReader(Method method) {
		// TODO Auto-generated constructor stub
		Class<?>[] parameterTypes = method.getParameterTypes();
		Object[] parameters = new Object[parameterTypes.length]; 
		
		Paranamer paranamer = new CachingParanamer( new BytecodeReadingParanamer() );
		final String[] parameterNames = paranamer.lookupParameterNames(method);
		
	}
	
}
