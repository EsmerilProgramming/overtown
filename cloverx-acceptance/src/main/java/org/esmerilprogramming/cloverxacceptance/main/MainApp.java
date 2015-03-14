package org.esmerilprogramming.cloverxacceptance.main;

import org.esmerilprogramming.cloverx.server.CloverX;
import org.esmerilprogramming.cloverx.server.CloverXConfiguration;
import org.esmerilprogramming.cloverx.server.ConfigurationBuilder;

/**
 * Created by efraimgentil<efraimgentil@gmail.com> on 14/03/15.
 */
public class MainApp {

  private CloverX cloverx;


  public void start(){
    cloverx = new CloverX( configure() );
  }

  public CloverXConfiguration configure(){
    ConfigurationBuilder cb = new ConfigurationBuilder();
    cb.withPackageToScan("org.esmerilprogramming.cloverxacceptance")
            .shouldRunManagement(false);
    return cb.build();
  }

  public void stop(){
    cloverx.stop();
  }

  public static void main(String ... args){
    new MainApp().start();
  }

}
