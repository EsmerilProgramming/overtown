package org.esmerilprogramming.cloverx.server;

import io.undertow.Handlers;
import io.undertow.server.handlers.resource.FileResourceManager;
import io.undertow.server.handlers.resource.ResourceHandler;

import java.io.File;

public class ResourceHandlerMounter {

  public ResourceHandler mount() {
    File staticResourceDir = ConfigurationHolder.getInstance().getStaticResourceDir();
    FileResourceManager fileResourceManager = new FileResourceManager( staticResourceDir , 1 );
    return Handlers.resource( fileResourceManager );
  }
}
