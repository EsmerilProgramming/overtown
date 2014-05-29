package com.clover.scanner;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;

import com.clover.scanner.exception.PackageNotFoundException;

/**
 * 
 * @author efraimgentil (efraim.gentil@gmail.com)
 */
public class PackageScanner {

	public ScannerResult scan(String packageToSearch,
			ClassLoader classLoader) throws PackageNotFoundException, IOException {
		ClassFileVisitor visitor = new ClassFileVisitor(classLoader);
		return scanPackage(packageToSearch, visitor);
	}

	protected ScannerResult scanPackage(String packageToSearch, ClassFileVisitor visitor)
			throws PackageNotFoundException, IOException {
		URL systemResource = PackageScanner.class.getResource("/"
				+ packageToSearch.replaceAll("\\.", "/"));
		if (systemResource == null)
			throw new PackageNotFoundException();

		Files.walkFileTree(Paths.get(systemResource.getPath()), visitor);

		return visitor.getResult();
	}

}
