package org.esmerilprogramming.overtown.server;

import java.io.IOException;

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
  
  private Configuration configuration;
  private freemarker.template.Configuration freemarkerConfig;

  public void prepareConfiguration(Configuration configuration) throws IOException{
    this.configuration = configuration;
    prepareFremarker();
  }

  private void prepareFremarker() throws IOException{
    freemarkerConfig = new freemarker.template.Configuration();
    freemarkerConfig.setClassForTemplateLoading( this.getClass() ,  "/templates/" );
    freemarkerConfig.setObjectWrapper( new DefaultObjectWrapper() );
  }

  public Configuration getConfiguration() {
    return configuration;
  }
  public freemarker.template.Configuration getFreemarkerConfig() {
    return freemarkerConfig;
  }
  
}