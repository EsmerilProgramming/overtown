package org.esmerilprogramming.cloverx.scanner;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.esmerilprogramming.cloverx.scanner.exception.PackageNotFoundException;
import org.jboss.logging.Logger;

public class ConfigScanner {

  private static final Logger LOGGER = Logger.getLogger(ConfigScanner.class);

  public List<Class<?>> getPageClasses(String packageToSearch, ClassLoader classLoader)
      throws PackageNotFoundException {
    List<Class<?>> pageClasses = new ArrayList<>();
    String[] list = getFileList(packageToSearch);
    try {
      for (String s : list) {

        LOGGER.info(s);

        Class<?> loadedClass =
            classLoader.loadClass(packageToSearch + "." + s.replace(".properties", ""));
        pageClasses.add(loadedClass);
      }
    } catch (ClassNotFoundException e) {
      LOGGER.error(e.getMessage());
    }
    return pageClasses;
  }

  public String[] getFileList(String packageToSearch) throws PackageNotFoundException {
    URL systemResource =
        PackageScanner.class.getResource("/" + packageToSearch.replaceAll("\\.", "/"));
    if (systemResource == null)
      throw new PackageNotFoundException();
    File file = new File(systemResource.getPath());
    return file.list();
  }

}
