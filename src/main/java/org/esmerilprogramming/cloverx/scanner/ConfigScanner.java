package org.esmerilprogramming.cloverx.scanner;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.esmerilprogramming.cloverx.scanner.exception.PackageNotFoundException;

public class ConfigScanner {
	
	public List<Class<?>> getPageClasses(String packageToSearch,
			ClassLoader classLoader) throws PackageNotFoundException {
		List<Class<?>> pageClasses = new ArrayList<>();
		String[] list = getFileList(packageToSearch);
		try {
			for (String string : list) {
				System.out.println(string);
				Class<?> loadedClass = classLoader.loadClass(packageToSearch
						+ "." + string.replace(".properties", ""));
				pageClasses.add(loadedClass);
			}
		} catch (ClassNotFoundException e) {
			Logger.getLogger(ConfigScanner.class.getName()).log(Level.SEVERE, e.getMessage());
		}
		return pageClasses;
	}

	public String[] getFileList(String packageToSearch)
			throws PackageNotFoundException {
		URL systemResource = PackageScanner.class.getResource("/"
				+ packageToSearch.replaceAll("\\.", "/") );
		if(systemResource == null)
			throw new PackageNotFoundException();
		File file = new File(systemResource.getPath());
		return file.list();
	}
	
}
