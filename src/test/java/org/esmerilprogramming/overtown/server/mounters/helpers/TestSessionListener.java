package org.esmerilprogramming.overtown.server.mounters.helpers;

import io.undertow.server.session.SessionListener.SessionDestroyedReason;

import org.esmerilprogramming.overtown.annotation.session.OnSessionCreate;
import org.esmerilprogramming.overtown.annotation.session.OnSessionDestroy;
import org.esmerilprogramming.overtown.annotation.session.SessionListener;
import org.esmerilprogramming.overtown.http.CloverXSession;

@SessionListener
public class TestSessionListener {
  
  public static Integer onCreateCallCounter = 0;
  public static Integer onDestroyCallCounter = 0;
  
  public TestSessionListener() {
    onCreateCallCounter = 0;
    onDestroyCallCounter = 0;
  }
  
  @OnSessionCreate
  public void onCreate(){
    onCreateCallCounter++;
  }
  
  @OnSessionDestroy
  public void onDestroy(){
    onDestroyCallCounter++;
  }
  
  public void onCreateMethodTest(){
  }
  
  public void onCreateMethodTest(CloverXSession session){
  }
  
  public void onCreateMethodTest(String test){
  }
  
  public void onDestroyMethodTest(){
  }
  
  public void onDestroyMethodTest(CloverXSession session){
  }
  
  public void onDestroyMethodTest(CloverXSession session , SessionDestroyedReason reason ){
  }
  
  public void onDestroyMethodTest(String test){
  }
  
}
