package org.esmerilprogramming.cloverx.server;

import io.undertow.Undertow;
import io.undertow.Undertow.Builder;

import java.util.logging.Level;
import java.util.logging.Logger;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import org.esmerilprogramming.cloverx.scanner.PackageScanner;
import org.esmerilprogramming.cloverx.scanner.ScannerResult;
import org.esmerilprogramming.cloverx.scanner.exception.PackageNotFoundException;

public class CloverX {

  private String host = "localhost";
  private int port = 8080;
  private boolean debugMode = false;

  public CloverX() {

  }

  public CloverX(boolean debugMode) {
    this.debugMode = debugMode;
  }

  public CloverX(int port) {
    this.port = port;
  }

  public CloverX(String host) {
    this.host = host;
  }

  public CloverX(int port, String host) {
    this.port = port;
    this.host = host;
  }

  public CloverX(int port, String host, boolean debugMode) {
    this.port = port;
    this.host = host;
    this.debugMode = debugMode;
  }

  private Undertow server;

  public void start() throws PackageNotFoundException, IOException, NoSuchMethodException,
      SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException,
      InvocationTargetException {
    ClassLoader classLoader = this.getClass().getClassLoader();
    ScannerResult scan = new PackageScanner().scan("", classLoader);

    Builder builder = Undertow.builder();
    builder.addHttpListener(port, host);

    if (!scan.getHandlers().isEmpty()) {
      PathHandlerMounter mounter = new PathHandlerMounter();
      builder.setHandler(mounter.mount(scan.getHandlers()));
    }

    server = builder.build();
    server.start();
    Logger.getLogger(CloverX.class.getName()).log(Level.INFO,
        "Enjoy it! http://" + host + ":" + port);
  }

  public Undertow getServer() {
    return server;
  }

  public void setServer(Undertow server) {
    this.server = server;
  }

  public static void main(String[] args) throws Exception {
    new CloverX().start();
  }

}
