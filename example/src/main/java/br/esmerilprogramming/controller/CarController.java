package br.esmerilprogramming.controller;

import io.undertow.server.HttpServerExchange;
import io.undertow.util.Headers;
import br.esmerilprogramming.model.Car;

import com.clover.annotation.Controller;
import com.clover.annotation.Page;
import com.clover.http.CloverRequest;

@Controller(path = "/car")
public class CarController {

  @Page("form")
  public void form(CloverRequest request){
    HttpServerExchange exchange = request.getExchange();
    exchange.getResponseHeaders().put(Headers.CONTENT_TYPE, "text/html");
    exchange.getResponseSender().send("<form action='/car/read' method='post' >"
        + "<label>Year:</label><input name='car.year' />"
        + "<label>Model:</label><input name='car.model' />"
        + "<button type='submit'>Submit</button>"
        + "</form>");
  }
  
  @Page("read")
  public void read(Car car){
    System.out.println( car );
  }
  
}
