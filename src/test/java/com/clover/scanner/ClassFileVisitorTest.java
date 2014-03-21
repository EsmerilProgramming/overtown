package com.clover.scanner;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.Test;

public class ClassFileVisitorTest {

	@Test
	public void t() throws IOException{
		ClassLoader classLoader = this.getClass().getClassLoader();
		ClassFileVisitor visitor = new ClassFileVisitor( classLoader );
		
		URL url =  ClassFileVisitorTest.class.getResource("/");
		
		Files.walkFileTree( Paths.get( url.getPath() + "/com/clover/scanner/testpackage")  , visitor);
		
		ScannerResult result = visitor.getResult();
		
		System.out.println( result.getHandlers() );
		System.out.println( result.getServlets() );
		
	}
	
}
