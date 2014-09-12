package org.esmerilprogramming.cloverx.server;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class UnzipJar {

  public void unzipJar(String destinationDir, String jarPath) throws IOException {
    try {
      CloverXConfiguration configuration = ConfigurationHandler.getInstance().getConfiguration();
      
      File file = new File(jarPath);
      JarFile jar = new JarFile(file);
      
      Enumeration<JarEntry> enums = jar.entries();
      while (enums.hasMoreElements()) {
        JarEntry entry = enums.nextElement();
        
        if (entry.getName().startsWith( configuration.getStaticRootPath() ) || entry.getName().startsWith( configuration.getTemplateRootPath() ) ) {
          File toWrite = new File(destinationDir + "/" + entry.getName());
          System.out.println( toWrite );
          if (entry.isDirectory()) {
            toWrite.mkdirs();
            continue;
          }
          InputStream in = new BufferedInputStream(jar.getInputStream(entry));
          OutputStream out = new BufferedOutputStream(new FileOutputStream(toWrite));
          byte[] buffer = new byte[2048];
          for (;;) {
            int nBytes = in.read(buffer);
            if (nBytes <= 0) {
              break;
            }
            out.write(buffer, 0, nBytes);
          }
          out.flush();
          out.close();
          in.close();
        }
      }
    } catch (IOException ex) {
        ex.printStackTrace();
    }
    
  }
}
