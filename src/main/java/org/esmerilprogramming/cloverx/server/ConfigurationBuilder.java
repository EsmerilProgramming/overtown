package org.esmerilprogramming.cloverx.server;

import org.esmerilprogramming.cloverx.server.exception.ConfigurationException;

public class ConfigurationBuilder {

  private String host = "127.0.0.1";
  private Integer port = 8080;
  private String appContext = "";
  private String staticRootPath = "static";
  private String templateRootPath = "templates";
  private String packageToScan = "";
  
  
  public ConfigurationBuilder withHost(String host){
    this.host = host;
    return this; 
  }
  
  public ConfigurationBuilder withPort(Integer port){
    this.port = port;
    return this; 
  }
  
  public ConfigurationBuilder withAppContext(String appContext){
    if(appContext.equalsIgnoreCase(staticRootPath)){
      throw new ConfigurationException("The appContext can't be the same of the staticRootPath");
    }
    this.appContext = appContext;
    return this; 
  }

  public ConfigurationBuilder withPackageToScan(String packageToScan){
    this.packageToScan = packageToScan;
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
    this.staticRootPath = staticRootPath;
    return this; 
  }
  
  public CloverXConfiguration build(){
    CloverXConfiguration config = new CloverXConfiguration();
    config.setHost(host);
    config.setPort(port);
    config.setAppContext(appContext);
    config.setStaticRootPath(staticRootPath);
    config.setTemplateRootPath(templateRootPath);
    config.setPackageToSkan(packageToScan);
    return config;
  }
  
  protected CloverXConfiguration defaultConfiguration(){
    CloverXConfiguration config = new CloverXConfiguration();
    config.setHost("127.0.0.1");
    config.setPort(8080);
    config.setAppContext("");
    config.setStaticRootPath(staticRootPath);
    config.setTemplateRootPath(templateRootPath);
    config.setPackageToSkan(packageToScan);
    return config;
  }
  
}
