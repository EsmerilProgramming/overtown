package org.esmerilprogramming.overtown.server.exception;

public class NoControllerException extends RuntimeException {

  private static final long serialVersionUID = 1380136846913488750L;
  
  public NoControllerException(String message) {
    super(message);
  }
  
}
