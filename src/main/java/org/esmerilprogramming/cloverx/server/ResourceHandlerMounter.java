package org.esmerilprogramming.cloverx.server;

import io.undertow.Handlers;
import io.undertow.server.handlers.resource.FileResourceManager;
import io.undertow.server.handlers.resource.ResourceHandler;

import java.io.File;
import java.net.URL;

import org.esmerilprogramming.cloverx.server.ConfigurationHandler.DeployType;

public class ResourceHandlerMounter {

  public ResourceHandler mount() {
    ConfigurationHandler instance = ConfigurationHandler.getInstance();
    String staticRootPath = instance.getConfiguration().getStaticRootPath();
    String classPathDir = instance.getClassPathDir();
    File f  = null;
    if( instance.getDeployType() == DeployType.JAR ){
      if( classPathDir.endsWith( File.separator ) )
        f  = new File( classPathDir + staticRootPath );
      else  
        f  = new File( classPathDir +  File.separator + staticRootPath );
    }else{
      URL url = this.getClass().getResource( classPathDir + staticRootPath );
      f = new File( url.getPath() );
    }
    ResourceHandler resource =
        Handlers.resource( new FileResourceManager( f , 1) );
    return resource;
  }
}
