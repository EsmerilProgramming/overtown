package org.esmerilprogramming.cloverx.server;

import io.undertow.Handlers;
import io.undertow.server.handlers.resource.ClassPathResourceManager;
import io.undertow.server.handlers.resource.FileResourceManager;
import io.undertow.server.handlers.resource.ResourceHandler;
import io.undertow.server.handlers.resource.ResourceManager;

import java.io.File;

public class ResourceHandlerMounter {

  public ResourceHandler mount() {
    System.out.println("Static Resource" + ConfigurationHolder.getInstance().getStaticResourceDir() );
    File staticResourceDir = ConfigurationHolder.getInstance().getStaticResourceDir();
    ResourceManager resourceManager = new ClassPathResourceManager( Thread.currentThread().getContextClassLoader() , "static/"); //new FileResourceManager( staticResourceDir , 1 );
    return Handlers.resource( resourceManager );
  }
}
