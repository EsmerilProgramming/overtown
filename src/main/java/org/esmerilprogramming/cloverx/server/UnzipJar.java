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

@Deprecated
public class UnzipJar {


  public void unzipJar(ConfigurationHolder configHandler, String jarPath) throws IOException {
    throw new RuntimeException("You should not use this class anymore.");
    /*try {
      String destinationDir = configHandler.getRootTemp() + File.separator + configHandler.getRootName();
      File file = new File(jarPath);
      try (JarFile jar = new JarFile(file)) {
        Enumeration<JarEntry> enums = jar.entries();
        while (enums.hasMoreElements()) {
          JarEntry entry = enums.nextElement();
          if( isResource(entry, configHandler) ) {
            File toWrite = new File( destinationDir + "/" + entry.getName() );
            System.out.println(toWrite);
            if (entry.isDirectory()) {
              toWrite.mkdirs();
              continue;
            }
            writeToFile( jar , entry , toWrite );
          }
        }
      }
    } catch (IOException ex) {
      ex.printStackTrace();
    }*/
  }
  
  protected boolean isResource(JarEntry entry , ConfigurationHolder configHandler){
    CloverXConfiguration configuration = configHandler.getConfiguration();
    String fileName = entry.getName();
    return fileName.startsWith( configuration.getStaticRootPath() )
              || fileName.startsWith( configuration.getTemplateRootPath()) ;
  }
  
  public void writeToFile(JarFile jFile , JarEntry jEntry , File toWrite ) throws IOException{
    try( InputStream in = new BufferedInputStream(jFile.getInputStream(jEntry));
         OutputStream out = new BufferedOutputStream(new FileOutputStream(toWrite)) ){
      byte[] buffer = new byte[2048];
      for (;;) {
        int nBytes = in.read(buffer);
        if (nBytes <= 0) {
          break;
        }
        out.write(buffer, 0, nBytes);
      }
    }
  }
  
}
