package br.esmerilprogramming.controller;

import org.esmerilprogramming.cloverx.annotation.Controller;
import org.esmerilprogramming.cloverx.annotation.Page;

@Controller
public class HomeController {

  @Page(value = "", responseTemplate = "home.ftl")
  public void index() {
    System.out.println("AA");
  }

}
