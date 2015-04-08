package br.esmerilprogramming.controller;

import org.esmerilprogramming.cloverx.annotation.Controller;
import org.esmerilprogramming.cloverx.annotation.Page;

@Controller
public class SimpleController {
  
  @Page("helloworld")
  public void helloWorld(){
    System.out.println("Say hello world for me");
  }
  
}
