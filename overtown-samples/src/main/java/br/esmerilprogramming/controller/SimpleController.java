package br.esmerilprogramming.controller;

import org.esmerilprogramming.overtown.annotation.Controller;
import org.esmerilprogramming.overtown.annotation.Page;

@Controller
public class SimpleController {
  
  @Page("helloworld")
  public void helloWorld(){
    System.out.println("Say hello world for me");
  }
  
}
