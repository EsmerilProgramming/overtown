package org.esmerilprogramming.overtownacceptance.main;

import org.esmerilprogramming.overtown.server.Overtown;
import org.esmerilprogramming.overtown.server.Configuration;
import org.esmerilprogramming.overtown.server.ConfigurationBuilder;

/**
 * Created by efraimgentil<efraimgentil@gmail.com> on 14/03/15.
 */
public class MainApp {

  private Overtown overtown;


  public void start(Configuration configuration){
    overtown = new Overtown( configuration );
  }

  public void start(){
    start(configure());
  }

  public Configuration configure(){
    ConfigurationBuilder cb = new ConfigurationBuilder();
    cb.withPackageToScan("org.esmerilprogramming.overtownacceptance")
            .shouldRunManagement(false)
            .withAppContext("acceptance");
    return cb.build();
  }

  public void stop(){
    overtown.stop();
  }

  public static void main(String ... args){
    new MainApp().start();
  }

}
