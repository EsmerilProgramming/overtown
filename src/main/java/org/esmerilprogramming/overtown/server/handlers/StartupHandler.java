package org.esmerilprogramming.overtown.server.handlers;

import io.undertow.Undertow;

import java.io.IOException;

import org.esmerilprogramming.overtown.server.Configuration;

public interface StartupHandler {

  Undertow prepareBuild(Configuration configuration) throws IOException;
}
