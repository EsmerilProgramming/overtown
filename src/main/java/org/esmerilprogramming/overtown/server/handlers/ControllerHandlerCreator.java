package org.esmerilprogramming.overtown.server.handlers;

import com.thoughtworks.paranamer.BytecodeReadingParanamer;
import com.thoughtworks.paranamer.CachingParanamer;
import com.thoughtworks.paranamer.Paranamer;
import io.undertow.server.RoutingHandler;


/**
 * Created by efraimgentil<efraimgentil@gmail.com> on 31/01/15.
 */
public class ControllerHandlerCreator {

  private Paranamer paranamer;

  public ControllerHandlerCreator(){
    paranamer = new CachingParanamer(new BytecodeReadingParanamer());
  }

  public RoutingHandler createHandler(ControllerMapping mapping , RoutingHandler routing){
    for( PathMapping pm: mapping.getPathMappings()){
      routing.add( pm.getHttpMethod() ,
               pm.getFinalPath( mapping.getPath() ) ,
              new MainHttpHandler( mapping , pm  , paranamer.lookupParameterNames( pm.getMethod() ) ) );
    }
    return routing;
  }

}
