package org.esmerilprogramming.overtownacceptance.withappcontext.controllers;

import org.esmerilprogramming.overtown.annotation.Controller;
import org.esmerilprogramming.overtown.annotation.path.Get;

/**
 * Created by efraimgentil<efraimgentil@gmail.com> on 07/04/15.
 */
@Controller
public class ServerErrorController {

  @Get
  public void throwError(){
    int i = 1/0; // BLOW UP THE PLANET
  }

}
