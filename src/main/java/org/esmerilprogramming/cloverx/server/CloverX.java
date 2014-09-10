package org.esmerilprogramming.cloverx.server;


import static io.undertow.Handlers.path;
import io.undertow.Undertow;
import io.undertow.server.handlers.PathHandler;
import io.undertow.server.handlers.resource.ClassPathResourceManager;
import io.undertow.servlet.api.DeploymentInfo;
import io.undertow.servlet.api.DeploymentManager;
import io.undertow.servlet.api.ServletContainer;
import io.undertow.websockets.jsr.WebSocketDeploymentInfo;

import java.io.IOException;

import javax.servlet.ServletException;

import org.esmerilprogramming.cloverx.management.JsrChatWebSocketEndpoint;
import org.esmerilprogramming.cloverx.scanner.PackageScanner;
import org.esmerilprogramming.cloverx.scanner.ScannerResult;
import org.esmerilprogramming.cloverx.scanner.exception.PackageNotFoundException;
import org.esmerilprogramming.cloverx.server.exception.NoControllerException;
import org.jboss.logging.Logger;
import org.xnio.ByteBufferSlicePool;

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
    } catch (ServletException e) {
      e.printStackTrace();
    }
    server.start();
    LOGGER.info("Enjoy it! http://" + configuration.getHost()
        + ":" + configuration.getPort()
        + "/" + configuration.getAppContext() );
  }

  private Undertow buildServer( CloverXConfiguration configuration ) throws ServletException {
    return Undertow.builder()
        .addHttpListener( configuration.getPort() ,  configuration.getHost() )
        .setHandler(
            path()
            .addPrefixPath("/" + configuration.getAppContext() , createHandler())
            .addPrefixPath("/" + configuration.getStaticRootPath() , new ResourceHandlerMounter()
            .mount()))
        .build();
  }

  private PathHandler createHandler() {
    ScannerResult scan = scanPackagesForHandlers();
    if (!scan.getHandlers().isEmpty()) {
      PathHandlerMounter mounter = new PathHandlerMounter();
      return mounter.mount( scan );
    }
    throw new NoControllerException("You should specify at least one controller. See https://github.com/EsmerilProgramming/cloverx for more info");
  }

  private ScannerResult scanPackagesForHandlers() {
    ClassLoader classLoader = this.getClass().getClassLoader();
    ScannerResult scan = null;
    try {
      scan = new PackageScanner().scan("", classLoader);
    } catch (PackageNotFoundException | IOException e) {
      LOGGER.error(e.getMessage());
    }
    return scan;
  }

  public Undertow getServer() {
    return server;
  }

  public static void main(String[] args) {
    new CloverX(new ConfigurationBuilder()
    .withHost("127.0.0.1")
    .withPort(8080)
    .build());
  }

}