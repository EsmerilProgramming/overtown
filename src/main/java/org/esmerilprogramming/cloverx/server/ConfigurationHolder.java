package org.esmerilprogramming.cloverx.server;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.security.CodeSource;
import java.util.Map;
import java.util.Properties;
import java.util.jar.Manifest;

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
  
  private CloverXConfiguration configuration;
  private Configuration freemarkerConfig;

  public void prepareConfiguration(CloverXConfiguration configuration) throws IOException{
    this.configuration = configuration;
    prepareFremarker();
  }

  private void prepareFremarker() throws IOException{
    freemarkerConfig = new Configuration();
    freemarkerConfig.setClassForTemplateLoading( this.getClass() ,  "/templates/" );
    freemarkerConfig.setObjectWrapper( new DefaultObjectWrapper() );
  }

  public CloverXConfiguration getConfiguration() {
    return configuration;
  }
  public Configuration getFreemarkerConfig() {
    return freemarkerConfig;
  }
  
}