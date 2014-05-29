package com.clover.scanner;

import static org.junit.Assert.*;
import io.undertow.server.HttpHandler;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServlet;

import org.junit.Before;
import org.junit.Test;

import com.clover.scanner.exception.PackageNotFoundException;
import com.clover.scanner.testpackage.First;
import com.clover.scanner.testpackage.Third;
import com.clover.scanner.testpackage.subpack.Fifth;
import com.clover.scanner.testpackage.subpack.Fourth;

public class PackageScannerTest {
	
	private PackageScanner scanner;
	
	@Before
	public void setUp(){
		scanner = new PackageScanner();
	}
	
	@Test(expected = PackageNotFoundException.class)
	public void givenANonExistentPackageShouldThrowException() throws PackageNotFoundException, IOException{
		ClassLoader classLoader = PackageScanner.class.getClassLoader();
		
		scanner.scan( "com.wrong.package" , classLoader);
	}
	
	@Test
	public void givenAPackagedShouldFindAllHttpHandlerClassesInThisPackageAndSubPackages() throws PackageNotFoundException, IOException{
		ClassLoader classLoader = PackageScanner.class.getClassLoader();
		
		ScannerResult pageClasses = scanner.scan( "com.clover.scanner.testpackage" , classLoader);
		
		List<Class<? extends HttpHandler>> handlers = pageClasses.getHandlers();
		assertSame( 2 , handlers.size() );
		assertTrue("Should have found the First.class" ,  handlers.contains( First.class ) );
		assertTrue("Should have found the Fifth.class" ,  handlers.contains( Fifth.class ) );
	}
	
	@Test
	public void givenAPackagedShouldFindAllHttpServletClassesInThisPackageAndSubPackages() throws PackageNotFoundException, IOException{
		ClassLoader classLoader = PackageScanner.class.getClassLoader();
		
		ScannerResult pageClasses = scanner.scan( "com.clover.scanner.testpackage" , classLoader);
		
		List<Class<? extends HttpServlet>> servlets = pageClasses.getServlets();
		assertSame( 2 , servlets.size() );
		assertTrue("Should have found the Third.class" ,  servlets.contains( Third.class ) );
		assertTrue("Should have found the Fourth.class" ,  servlets.contains( Fourth.class ) );
	}
	
}
