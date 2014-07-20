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

  private Undertow server;

  public static void main(String[] args) {

    CloverX clover = new CloverX();
    try {
      clover.start();
    } catch (NoSuchMethodException | SecurityException | InstantiationException
        | IllegalAccessException | IllegalArgumentException | InvocationTargetException
        | PackageNotFoundException | IOException e) {
      Logger.getLogger(CloverX.class.getName()).log(Level.SEVERE, e.getMessage());
    }

  }

  public void start() throws PackageNotFoundException, IOException, NoSuchMethodException,
      SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException,
      InvocationTargetException {
    ClassLoader classLoader = this.getClass().getClassLoader();
    ScannerResult scan = new PackageScanner().scan("", classLoader);

    Builder builder = Undertow.builder();
    builder.addHttpListener(8080, "localhost");

    if (!scan.getHandlers().isEmpty()) {
      PathHandlerMounter mounter = new PathHandlerMounter();
      builder.setHandler(mounter.mount(scan.getHandlers()));
    }

    server = builder.build();
    server.start();
    Logger.getLogger(CloverX.class.getName()).log(Level.INFO, "Enjoy http://localhost:8080");
  }

  public Undertow getServer() {
    return server;
  }

  public void setServer(Undertow server) {
    this.server = server;
  }

}
