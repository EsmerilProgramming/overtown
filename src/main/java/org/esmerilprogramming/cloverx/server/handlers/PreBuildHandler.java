package org.esmerilprogramming.cloverx.server.handlers;

import io.undertow.server.handlers.PathHandler;

import java.io.IOException;

import org.esmerilprogramming.cloverx.server.CloverXConfiguration;

public interface PreBuildHandler {
  
  void prepareBuild(CloverXConfiguration configuration) throws IOException;
  
  PathHandler createAppHandlers();
  
}
