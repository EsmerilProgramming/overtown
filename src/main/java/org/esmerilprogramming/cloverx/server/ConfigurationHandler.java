package org.esmerilprogramming.cloverx.server;

import java.io.IOException;
import java.net.URL;
import java.security.CodeSource;

public class ConfigurationHandler {
  
  private static ConfigurationHandler configurationHandler; 
  
  private ConfigurationHandler(){}
  
  public static ConfigurationHandler getInstance(){
    if(configurationHandler == null){
      configurationHandler = new ConfigurationHandler();
    }
    return configurationHandler;
  }
  
  public enum DeployType {
    JAR,
    FILE
  }
  
  private DeployType deployType;
  private String classPathDir = "/";
  private CloverXConfiguration configuration;
  
  public void prepareConfiguration(CloverXConfiguration configuration) throws IOException{
      this.configuration = configuration; 
      Class thisClass = this.getClass();
      
      CodeSource src = this.getClass().getProtectionDomain().getCodeSource();
      URL source = src.getLocation();
      URL resource = thisClass.getResource("");
      if (resource.getProtocol().equals("file")) {
        deployType = DeployType.FILE;
      }
      if (resource.getProtocol().equals("jar")) {
        deployType = DeployType.JAR;
        classPathDir = System.getProperty("user.dir");
        source = thisClass.getResource("/" + System.getProperty("java.class.path") );
        System.out.println("defined class path: " + classPathDir);
      }
      processDeploy(source);
  }

  private void processDeploy(URL source) throws IOException {
    System.out.println("Processing the deploy type: " + deployType);
    if(deployType == DeployType.JAR){
      //UNZIP RESOURCES;
      new UnzipJar().unzipJar( classPathDir , source.getPath() );
    }
  }

  public CloverXConfiguration getConfiguration() {
    return configuration;
  }

  public String getClassPathDir() {
    return classPathDir;
  }

  public DeployType getDeployType() {
    return deployType;
  }

}
