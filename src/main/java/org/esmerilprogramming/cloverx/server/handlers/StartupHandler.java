package org.esmerilprogramming.cloverx.server.handlers;

import io.undertow.Undertow;
import io.undertow.server.HttpHandler;
import io.undertow.server.handlers.PathHandler;

import java.io.IOException;

import org.esmerilprogramming.cloverx.server.CloverXConfiguration;

public interface StartupHandler {

  Undertow prepareBuild(CloverXConfiguration configuration) throws IOException;
}
