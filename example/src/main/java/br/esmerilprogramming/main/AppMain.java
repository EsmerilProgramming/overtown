package br.esmerilprogramming.main;

import java.util.logging.Level;
import java.util.logging.Logger;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import com.clover.scanner.exception.PackageNotFoundException;
import com.clover.server.Clover;

public class AppMain {
  
  public static void main(String[] args) {
    try {
      new Clover().start();
    } catch (Exception e){
      Logger.getLogger(AppMain.class.getName()).log(Level.SEVERE, e.getMessage());
    }
  }
  
}
