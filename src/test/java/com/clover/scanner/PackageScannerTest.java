package com.clover.scanner;

import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.nio.file.Files;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.clover.scanner.exception.PackageNotFoundException;
import com.clover.scanner.testpackage.First;
import com.clover.scanner.testpackage.Second;
import com.clover.scanner.testpackage.Third;

public class PackageScannerTest {
	
	private PackageScanner scanner;
	
	@Before
	public void setUp(){
		scanner = new PackageScanner();
	}
	
	@Test(expected = PackageNotFoundException.class)
	public void givenANonExistentPackageShouldThrowException() throws PackageNotFoundException{
		ClassLoader classLoader = PackageScanner.class.getClassLoader();
		
		List<Class<?>> packageClasses = scanner.getPageClasses( "com.wrong.package" , classLoader);
	}
	
	@Test
	public void givenAPackgedShouldFindAllClassesInThisPackage() throws PackageNotFoundException{
		ClassLoader classLoader = PackageScanner.class.getClassLoader();
		
		List<Class<?>> packageClasses = scanner.getPageClasses( "com.clover.scanner.testpackage" , classLoader);
		
		
		assertSame( 3 , packageClasses.size() );
		assertTrue("Should have found the First.class" , packageClasses.contains( First.class ) );
		assertTrue("Should have found the Second.class" , packageClasses.contains( Second.class ) );
		assertTrue("Should have found the Third.class" , packageClasses.contains( Third.class ) );
	}
	
}
