package br.esmerilprogramming.controller;

import com.clover.annotation.Controller;
import com.clover.annotation.Page;

@Controller
public class SimpleController {
  
  @Page("helloworld")
  public void helloWorld(){
    System.out.println("Say hello world for me");
  }
  
}
