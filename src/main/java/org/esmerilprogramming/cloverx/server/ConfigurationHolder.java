package org.esmerilprogramming.cloverx.server;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.security.CodeSource;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;

public class ConfigurationHolder {
  
  private static ConfigurationHolder configurationHandler; 
  
  private ConfigurationHolder(){}
  
  public static ConfigurationHolder getInstance(){
    if(configurationHandler == null){
      configurationHandler = new ConfigurationHolder();
    }
    return configurationHandler;
  }
  
  public enum DeployType {
    JAR,
    FILE
  }
  
  private DeployType deployType;
  private String rootName = "";
  private String rootTemp = "";
  private String classPathDir = "/";
  private CloverXConfiguration configuration;
  
  private File templateDir;
  private File staticResourceDir;
  
  private Configuration freemarkerConfig;
  
  
  public void prepareConfiguration(CloverXConfiguration configuration) throws IOException{
      this.configuration = configuration; 
      @SuppressWarnings("rawtypes")
      Class thisClass = this.getClass();
      
      CodeSource src = this.getClass().getProtectionDomain().getCodeSource();
      URL source = src.getLocation();
      URL resource = thisClass.getResource("");
      if (resource.getProtocol().equals("file")) {
        deployType = DeployType.FILE;
      }
      if (resource.getProtocol().equals("jar")) {
        deployType = DeployType.JAR;
        rootTemp = System.getProperty("java.io.tmpdir");
        rootName = System.getProperty("java.class.path");
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
      System.out.println("IS JAR FILE");
      new UnzipJar().unzipJar( this , source.getPath() );
    }
    prepareFremarker() ;
  }
  
  private void prepareFremarker() throws IOException{
    freemarkerConfig = new Configuration();
    freemarkerConfig.setDirectoryForTemplateLoading( getTemplateDir() );
    freemarkerConfig.setObjectWrapper( new DefaultObjectWrapper() );
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

  public String getRootName() {
    return rootName;
  }

  public String getRootTemp() {
    return rootTemp;
  }
  
  public String getResourcePath(){
    File file = new File(rootTemp);
    return new File( file, rootName ).toPath().toString(); 
  }
  
  public String getResourceStaticPath(){
    File f = new File( getResourcePath() );
    return new File( f , getConfiguration().getStaticRootPath() ).getPath().toString();
  }
  
  public String getResourceTemplatesPath(){
    File f = new File( getResourcePath() );
    return new File( f , "templates" ).getPath().toString();
  }
  
  public boolean isJarDeployment(){
    return deployType == DeployType.JAR;
  }

  public File getTemplateDir(){
    if(templateDir == null){
      if ( isJarDeployment() ) {
        templateDir = new File( getResourceTemplatesPath() );
      } else {
        String classPathDir = getClassPathDir();
        URL url = this.getClass().getResource(classPathDir + "templates");
        templateDir = new File(url.getPath());
      }
    }
    return templateDir;
  }
  
  public File getStaticResourceDir(){
    if( isJarDeployment() ){
      staticResourceDir = new File( getResourceStaticPath() );
    }else{
      String staticRootPath = getConfiguration().getStaticRootPath();
      String classPathDir = getClassPathDir();
      URL url = this.getClass().getResource( classPathDir + staticRootPath );
      staticResourceDir = new File( url.getPath() );
    }
    return staticResourceDir;
  }

  public Configuration getFreemarkerConfig() {
    return freemarkerConfig;
  }
  
}