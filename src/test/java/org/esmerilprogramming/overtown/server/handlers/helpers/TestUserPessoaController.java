package org.esmerilprogramming.overtown.server.handlers.helpers;

import org.esmerilprogramming.overtown.annotation.Controller;
import org.esmerilprogramming.overtown.annotation.path.Get;
import org.esmerilprogramming.overtown.annotation.path.Path;
import org.esmerilprogramming.overtown.annotation.path.Post;

/**
 * Created by efraimgentil<efraimgentil@gmail.com> on 28/01/15.
 */
@Controller(path = "/user/pessoa")
public class TestUserPessoaController {

  @Path
  public void path(){

  }

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
