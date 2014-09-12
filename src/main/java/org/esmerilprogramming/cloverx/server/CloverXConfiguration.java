package org.esmerilprogramming.cloverx.server;

public class CloverXConfiguration {
  
  private String host;
  private Integer port;
  private String appContext;
  private String staticRootPath;
  private String templateRootPath;
  
  public String getHost() {
    return host;
  }
  public void setHost(String host) {
    this.host = host;
  }
  public Integer getPort() {
    return port;
  }
  public void setPort(Integer port) {
    this.port = port;
  }
  public String getAppContext() {
    return appContext;
  }
  public void setAppContext(String appContext) {
    this.appContext = appContext;
  }
  public String getStaticRootPath() {
    return staticRootPath;
  }
  public void setStaticRootPath(String staticRootPath) {
    this.staticRootPath = staticRootPath;
  }
  public String getTemplateRootPath() {
    return templateRootPath;
  }
  public void setTemplateRootPath(String templateRootPath) {
    this.templateRootPath = templateRootPath;
  }
  
}