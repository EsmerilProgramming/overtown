package com.clover.scanner;

import java.io.IOException;
import java.net.URL;
import java.nio.file.FileVisitResult;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class ClassFileVisitor extends SimpleFileVisitor<Path> {

	private ScannerResult result;
	
	private ClassLoader classLoader;
	private Pattern classPatern = Pattern.compile("\\.class$");
	
	public ClassFileVisitor(ClassLoader classLoader) {
		this.classLoader = classLoader;
	}
	
	@Override
	public FileVisitResult visitFile(Path file, BasicFileAttributes attrs)
			throws IOException {
		if(result == null){
			result = new ScannerResult();
		}
		if( classPatern.matcher( file.getFileName().toString() ).find() ){
			System.out.println( asPackage(file) );
			try {
				Class<?> loadedClass = classLoader.loadClass( asPackage(file) );
				result.addClass(loadedClass);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		} 
			
//		Class<?> loadedClass = classLoader.loadClass(packageToSearch
//				+ "." + string.replace(".class", ""));
//		System.out.println( file );
		return super.visitFile(file, attrs);
	}
	
	public String asPackage(Path file){
		URL url = ClassFileVisitor.class.getResource("/");
		String strPackage = file.toString().replace( url.getPath() , ""); 
		return strPackage.replaceAll("\\/", ".").replace(".class", "");
	}

	public ScannerResult getResult() {
		return result;
	}
	
}
