package org.esmerilprogramming.overtown.server;

import org.esmerilprogramming.overtown.server.exception.ConfigurationException;

public class ConfigurationBuilder {

  private String host = "127.0.0.1";
  private Integer port = 8080;
  private String appContext = "";
  private String staticRootPath = "static";
  private String templateRootPath = "templates";
  private String packageToScan = "";
  private Boolean runManagement = false;
  private int maxSessionTime = 30;
  
  
  public ConfigurationBuilder withHost(String host){
    this.host = host;
    return this; 
  }
  
  public ConfigurationBuilder withPort(Integer port){
    this.port = port;
    return this; 
  }
  
  public ConfigurationBuilder withAppContext(String appContext){
    if(appContext == null ){
      throw new ConfigurationException("The appContext can't be null");
    }
    if(appContext.equalsIgnoreCase(staticRootPath)){
      throw new ConfigurationException("The appContext can't be the same of the staticRootPath");
    }
    if(appContext.length() > 0) {
      if (appContext.startsWith("/")) {
        appContext = appContext.substring(1, appContext.length() - 1);
      }
      if (appContext.endsWith("/")) {
        appContext = appContext.substring(0, appContext.length() - 2);
      }
    }
    this.appContext = appContext;
    return this; 
  }

  public ConfigurationBuilder withMaxSessionTime(int sessionTimeInMinutes){
    this.maxSessionTime = sessionTimeInMinutes * 60;
    return this;
  }

  public ConfigurationBuilder withPackageToScan(String packageToScan){
    this.packageToScan = packageToScan;
    return this;
  }

  public ConfigurationBuilder shouldRunManagement(boolean shouldRun){
    this.runManagement = shouldRun;
    return this;
  }
  
  /**
   * This configuration doesn't change the physical location of the static resources in the project ( by convention is in the "CLASSPATH:/static" folder
   * This is just a logical reference to be used when trying to access the static resource, by default is "/static"
   * example: http://localhost:8080/static/myStaticResource.js 
   * @param staticRootPath
   * @return
   */
  public ConfigurationBuilder withStaticRootPath(String staticRootPath){
    if(staticRootPath.equalsIgnoreCase(appContext)){
      throw new ConfigurationException("The staticRootPath can't be the same of the appContext");
    }
    if(staticRootPath.length() > 0) {
      if (staticRootPath.startsWith("/")) {
        staticRootPath = staticRootPath.substring(1, staticRootPath.length() - 1);
      }
      if (staticRootPath.endsWith("/")) {
        staticRootPath = staticRootPath.substring(0, staticRootPath.length() - 2);
      }
    }
    this.staticRootPath = staticRootPath;
    return this; 
  }
  
  public Configuration build(){
    Configuration config = new Configuration();
    config.setHost(host);
    config.setPort(port);
    config.setAppContext(appContext);
    config.setStaticRootPath(staticRootPath);
    config.setTemplateRootPath(templateRootPath);
    config.setPackageToSkan(packageToScan);
    config.setRunManagement(runManagement);
    config.setMaxSessionTime(maxSessionTime);
    return config;
  }
  
  protected Configuration defaultConfiguration(){
    return withAppContext("").withHost("127.0.0.1").withPort(8080).build();
  }
  
}
