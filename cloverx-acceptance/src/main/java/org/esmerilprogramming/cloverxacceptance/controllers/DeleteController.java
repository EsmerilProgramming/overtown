package org.esmerilprogramming.cloverxacceptance.controllers;

import io.undertow.io.Sender;
import org.esmerilprogramming.cloverx.annotation.Controller;
import org.esmerilprogramming.cloverx.annotation.path.Get;
import org.esmerilprogramming.cloverx.annotation.path.Post;
import org.esmerilprogramming.cloverx.annotation.path.Put;
import org.esmerilprogramming.cloverx.http.CloverXRequest;

/**
 * Created by efraimgentil<efraimgentil@gmail.com> on 14/03/15.
 */
@Controller
public class DeleteController {

  @Get
  public void index(CloverXRequest request){
    Sender sender = request.getExchange().getResponseSender();
    sender.send("GET - delete/index");
  }

  @Get(template = "index.ftl")
  public void indexWithTemplate(CloverXRequest request){

  }

  @Post
  public void index(String name , CloverXRequest request){
    Sender sender = request.getExchange().getResponseSender();
    sender.send("POST - delete/index - nome:" + name);
  }

  @Put
  public void put(String name , CloverXRequest request){
    Sender sender = request.getExchange().getResponseSender();
    sender.send("PUT - delete/put - nome:" + name);
  }


}
