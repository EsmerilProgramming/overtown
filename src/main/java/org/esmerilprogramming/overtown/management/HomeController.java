package org.esmerilprogramming.overtown.management;

import org.esmerilprogramming.overtown.annotation.Controller;
import org.esmerilprogramming.overtown.annotation.path.Get;


@Controller(path = "/")
public class HomeController {

  @Get(value = { "" , "/" } , template = "index.ftl")
  public void index(){
    System.out.println( " AAAAA #### ");
  }

}

