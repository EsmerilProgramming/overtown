package org.esmerilprogramming.cloverx.server.handlers;

import io.undertow.server.handlers.PathHandler;
import io.undertow.server.session.InMemorySessionManager;
import io.undertow.server.session.SessionListener;
import io.undertow.server.session.SessionManager;

import java.io.IOException;
import java.util.List;

import org.esmerilprogramming.cloverx.http.CloverXSessionManager;
import org.esmerilprogramming.cloverx.scanner.PackageScanner;
import org.esmerilprogramming.cloverx.scanner.ScannerResult;
import org.esmerilprogramming.cloverx.scanner.exception.PackageNotFoundException;
import org.esmerilprogramming.cloverx.server.CloverXConfiguration;
import org.esmerilprogramming.cloverx.server.ConfigurationHolder;
import org.esmerilprogramming.cloverx.server.PathHandlerMounter;
import org.esmerilprogramming.cloverx.server.exception.NoControllerException;
import org.esmerilprogramming.cloverx.server.mounters.SessionListenerMounter;
import org.esmerilprogramming.cloverx.server.mounters.SessionListenerMounterImpl;
import org.jboss.logging.Logger;

public class PreBuildHandlerImpl implements PreBuildHandler {
  
  private static final Logger LOGGER = Logger.getLogger(PreBuildHandlerImpl.class);
  
  private ScannerResult scannerResult;
  
  @Override
  public void prepareBuild(CloverXConfiguration configuration) throws IOException {
    ConfigurationHolder.getInstance().prepareConfiguration(configuration);
    scannerResult = identifyEligibleClasses();
    
    CloverXSessionManager instance = CloverXSessionManager.getInstance();
    InMemorySessionManager sessionManager = instance.getSessionManager();
    configureSessionManager(sessionManager, scannerResult.getSessionListeners() );
  }

  public ScannerResult identifyEligibleClasses() {
    ClassLoader classLoader = this.getClass().getClassLoader();
    ScannerResult scan = null;
    try {
      scan = new PackageScanner().scan("", classLoader);
    } catch (PackageNotFoundException | IOException e) {
      e.printStackTrace();
      LOGGER.error(e.getMessage());
    }
    return scan;
  }
  
  protected void configureSessionManager(SessionManager sessionManager , List<Class<?>> annotatedSessionListeners ){
    SessionListenerMounter mounter = new SessionListenerMounterImpl();
    for (Class<?> annotatedClass : annotatedSessionListeners) {
      SessionListener sl = mounter.mount(annotatedClass);
      if(sl != null){
        sessionManager.registerSessionListener( sl);
      }
    }
  }
  
  @Override
  public PathHandler createAppHandlers(){
    if( !scannerResult.getHandlers().isEmpty() ) {
      PathHandlerMounter mounter = new PathHandlerMounter();
      return mounter.mount( scannerResult );
    }
    throw new NoControllerException("You should specify at least one controller. See https://github.com/EsmerilProgramming/cloverx for more info");
  }

}
