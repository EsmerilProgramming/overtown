package org.esmerilprogramming.cloverx.server;

import io.undertow.Undertow;
import io.undertow.Undertow.Builder;
import io.undertow.server.handlers.PathHandler;

import java.io.IOException;

import org.esmerilprogramming.cloverx.scanner.PackageScanner;
import org.esmerilprogramming.cloverx.scanner.ScannerResult;
import org.esmerilprogramming.cloverx.scanner.exception.PackageNotFoundException;
import org.jboss.logging.Logger;

public final class CloverX {

  private static final Logger LOGGER = Logger.getLogger(CloverX.class);

  private Undertow server;
  private String host = "localhost";
  private int port = 8080;

  public CloverX() {
    start();
  }

  public CloverX(int port) {
    this.port = port;
    start();
  }

  public CloverX(String host) {
    this.host = host;
    start();
  }

  public CloverX(int port, String host) {
    this.port = port;
    this.host = host;
    start();
  }

  private void start() {
    
    LOGGER.info("ignition...");
    
    server = createBuilder();
    server.start();
    
    LOGGER.info("Enjoy it! http://" + host + ":" + port);
  }
  
  private Undertow createBuilder() {
    Builder builder = Undertow.builder();
    builder.addHttpListener(port, host);
    builder.setHandler(createHandler());
    return builder.build();
  }
  
  private PathHandler createHandler() {
    ScannerResult scan = createScanner();
    if (!scan.getHandlers().isEmpty()) {
      PathHandlerMounter mounter = new PathHandlerMounter();
      return mounter.mount(scan.getHandlers());
    }
    return null;
  }
  
  private ScannerResult createScanner() {
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

  public void setServer(Undertow server) {
    this.server = server;
  }

  public static void main(String[] args) {
    new CloverX();
  }

}
