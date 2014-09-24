package org.esmerilprogramming.cloverx.server;


import static io.undertow.Handlers.path;
import io.undertow.Undertow;

import java.io.IOException;

import javax.servlet.ServletException;

import org.esmerilprogramming.cloverx.server.handlers.PreBuildHandler;
import org.esmerilprogramming.cloverx.server.handlers.PreBuildHandlerImpl;
import org.jboss.logging.Logger;

public final class CloverX {

  private static final Logger LOGGER = Logger.getLogger(CloverX.class);

  private Undertow server;
  
  public CloverX( CloverXConfiguration configuration ){
    start(configuration);
  }

  public CloverX() {
    this( new ConfigurationBuilder().defaultConfiguration() );
  }

  private void start( CloverXConfiguration configuration ) {
    LOGGER.info("ignition...");
    try {
      server = buildServer( configuration );
    } catch (ServletException | IOException e) {
      e.printStackTrace();
    }
    server.start();
    LOGGER.info("Enjoy it! http://" + configuration.getHost()
        + ":" + configuration.getPort()
        + "/" + configuration.getAppContext() );
  }

  private Undertow buildServer( CloverXConfiguration configuration ) throws ServletException, IOException {
    PreBuildHandler preBuildHandler = new PreBuildHandlerImpl();
    preBuildHandler.prepareBuild(configuration);
    return Undertow.builder()
        .addHttpListener( configuration.getPort() ,  configuration.getHost() )
        .setHandler(
            path()
            .addPrefixPath("/" + configuration.getAppContext() , preBuildHandler.createAppHandlers() )
            .addPrefixPath("/" + configuration.getStaticRootPath() , new ResourceHandlerMounter()
            .mount()))
        .build();
  }
  
  public Undertow getServer() {
    return server;
  }

  public static void main(String[] args) {
    new CloverX(new ConfigurationBuilder()
    .withHost("0.0.0.0")
    .withPort(8080)
    .build());
  }

}