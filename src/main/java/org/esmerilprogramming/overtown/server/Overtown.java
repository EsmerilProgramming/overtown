package org.esmerilprogramming.overtown.server;


import io.undertow.Undertow;

import java.io.IOException;

import javax.servlet.ServletException;

import org.esmerilprogramming.overtown.server.handlers.StartupHandler;
import org.esmerilprogramming.overtown.server.handlers.StartupHandlerImpl;
import org.jboss.logging.Logger;

public final class Overtown {

  private static final Logger LOGGER = Logger.getLogger(Overtown.class);

  private Undertow server;
  
  public Overtown(Configuration configuration){
    try{
      start(configuration);
    }catch(RuntimeException e){
      LOGGER.error("Error on startup");
      LOGGER.error( e.getMessage() );
      e.printStackTrace();
    }
  }

  public Overtown() {
    this( new ConfigurationBuilder().defaultConfiguration() );
  }

  private void start( Configuration configuration ) throws RuntimeException {
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
  
  public void stop(){
    server.stop();
  }

  private Undertow buildServer( Configuration configuration ) throws ServletException, IOException {
    StartupHandler startupHandler = new StartupHandlerImpl();
    return startupHandler.prepareBuild(configuration);
  }

  public Undertow getServer() {
    return server;
  }

  public static void main(String[] args) {
    new Overtown(new ConfigurationBuilder()
      .withPackageToScan("org.esmerilprogramming.overtown.management").shouldRunManagement(true)
      .withHost("0.0.0.0")
      .withAppContext("app")
      .withPort(8080)
            .withMaxSessionTime(1)
      .build());
  }

}