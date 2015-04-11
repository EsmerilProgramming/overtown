package org.esmerilprogramming.overtown.server.mounters.helpers;

import io.undertow.server.session.SessionListener.SessionDestroyedReason;

import org.esmerilprogramming.overtown.annotation.session.OnSessionCreate;
import org.esmerilprogramming.overtown.annotation.session.OnSessionDestroy;
import org.esmerilprogramming.overtown.annotation.session.SessionListener;
import org.esmerilprogramming.overtown.http.OvertownSession;

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

  public void onCreateMethodTest(OvertownSession session){
  }

  public void onCreateMethodTest(String test){
  }

  public void onDestroyMethodTest(){
  }

  public void onDestroyMethodTest(OvertownSession session){
  }

  public void onDestroyMethodTest(OvertownSession session , SessionDestroyedReason reason ){
  }

  public void onDestroyMethodTest(String test){
  }

}
