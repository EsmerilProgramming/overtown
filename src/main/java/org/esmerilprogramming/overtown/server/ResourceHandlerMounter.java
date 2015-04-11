package org.esmerilprogramming.overtown.server;

import io.undertow.Handlers;
import io.undertow.server.handlers.resource.ClassPathResourceManager;
import io.undertow.server.handlers.resource.ResourceHandler;
import io.undertow.server.handlers.resource.ResourceManager;


public class ResourceHandlerMounter {

  public ResourceHandler mount() {
    Configuration configuration = ConfigurationHolder.getInstance().getConfiguration();
    ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();
    ResourceManager resourceManager = new ClassPathResourceManager(contextClassLoader, configuration.getStaticRootPath() + "/");
    return Handlers.resource( resourceManager );
  }

}
