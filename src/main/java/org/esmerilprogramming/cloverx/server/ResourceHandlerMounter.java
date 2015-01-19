package org.esmerilprogramming.cloverx.server;

import io.undertow.Handlers;
import io.undertow.server.handlers.resource.ClassPathResourceManager;
import io.undertow.server.handlers.resource.FileResourceManager;
import io.undertow.server.handlers.resource.ResourceHandler;
import io.undertow.server.handlers.resource.ResourceManager;

import java.io.File;

public class ResourceHandlerMounter {

  public ResourceHandler mount() {
    CloverXConfiguration configuration = ConfigurationHolder.getInstance().getConfiguration();
    ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();
    ResourceManager resourceManager = new ClassPathResourceManager(contextClassLoader, configuration.getStaticRootPath() + "/");
    return Handlers.resource( resourceManager );
  }

}
