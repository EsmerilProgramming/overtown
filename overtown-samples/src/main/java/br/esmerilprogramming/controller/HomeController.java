package br.esmerilprogramming.controller;

import org.esmerilprogramming.overtown.annotation.Controller;
import org.esmerilprogramming.overtown.annotation.Page;

@Controller
public class HomeController {

  @Page(value = "", responseTemplate = "home.ftl")
  public void index() {
    System.out.println("AA");
  }

}
