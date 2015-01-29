package org.esmerilprogramming.cloverx.server.handlers.helpers;

import org.esmerilprogramming.cloverx.annotation.path.Get;
import org.esmerilprogramming.cloverx.annotation.path.Post;

/**
 * Created by efraimgentil<efraimgentil@gmail.com> on 28/01/15.
 */
public class RestTestController {

  @Get
  public void get(){

  }

  @Post
  public void post(){

  }

  @Get
  @Post
  public void getAndPost(){

  }

}
