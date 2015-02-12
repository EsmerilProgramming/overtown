package org.esmerilprogramming.cloverx.management;

import io.undertow.server.HttpServerExchange;
import io.undertow.util.Headers;
import org.esmerilprogramming.cloverx.annotation.BeforeTranslate;
import org.esmerilprogramming.cloverx.annotation.Controller;
import org.esmerilprogramming.cloverx.annotation.Converter;
import org.esmerilprogramming.cloverx.annotation.Page;
import org.esmerilprogramming.cloverx.annotation.path.Get;
import org.esmerilprogramming.cloverx.annotation.path.Path;
import org.esmerilprogramming.cloverx.annotation.path.Post;
import org.esmerilprogramming.cloverx.http.CloverXRequest;
import org.esmerilprogramming.cloverx.http.CloverXSession;
import org.esmerilprogramming.cloverx.http.JsonResponse;
import org.esmerilprogramming.cloverx.management.converters.DateConverter;
import org.esmerilprogramming.cloverx.management.converters.ServerStatusConverter;
import org.esmerilprogramming.cloverx.management.model.ServerStatus;
import org.esmerilprogramming.cloverx.view.ViewAttributes;

import java.util.Date;

@Controller
public class RestController {

  public RestController() {}

  @Get( value ="form/" ,template = "rest/form.ftl")
  public void form(){
    System.out.println("With slash");
  }

  @Get( value ="form" ,template = "rest/form.ftl")
  public void formM(){
    System.out.println("SAME BUT WHITOUT SLASH");
  }

  @Post(template = "rest/result.ftl")
  public void form(String message , CloverXRequest request){
    request.addAttribute("message" , message);
  }

  @Path(template = "rest/form.ftl")
  public void getandpost(String message , CloverXRequest request){
    request.addAttribute("message" , message);
    System.out.println("message = " + message);
  }

}

