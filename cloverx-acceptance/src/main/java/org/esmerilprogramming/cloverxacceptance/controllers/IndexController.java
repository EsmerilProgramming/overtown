package org.esmerilprogramming.cloverxacceptance.controllers;

import io.undertow.io.Sender;
import org.esmerilprogramming.cloverx.annotation.Controller;
import org.esmerilprogramming.cloverx.annotation.path.Get;
import org.esmerilprogramming.cloverx.annotation.path.Post;
import org.esmerilprogramming.cloverx.http.CloverXRequest;

/**
 * Created by efraimgentil<efraimgentil@gmail.com> on 14/03/15.
 */
@Controller
public class IndexController {

  @Get
  public void index(CloverXRequest request){
    Sender sender = request.getExchange().getResponseSender();
    sender.send("GET - index/index");
  }

  @Get(template = "index.ftl")
  public void indexWithTemplate(CloverXRequest request){

  }

  @Get(template = "/index.ftl")
  public void indexWithRootTemplate(CloverXRequest request){

  }

  @Post
  public void index(String name , CloverXRequest request){
    Sender sender = request.getExchange().getResponseSender();
    sender.send("POST - index/index - nome:" + name);
  }


}
