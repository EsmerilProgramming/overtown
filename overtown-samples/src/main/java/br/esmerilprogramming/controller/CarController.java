package br.esmerilprogramming.controller;

import io.undertow.server.HttpServerExchange;
import io.undertow.util.Headers;

import org.esmerilprogramming.overtown.annotation.Controller;
import org.esmerilprogramming.overtown.annotation.Page;
import org.esmerilprogramming.overtown.http.OvertownRequest;
import org.jboss.logging.Logger;

import br.esmerilprogramming.model.Car;

import java.util.ArrayList;
import java.util.List;

@Controller(path = "/car")
public class CarController {

  private static final Logger LOGGER = Logger.getLogger(CarController.class);

  @Deprecated
  @Page("form")
  public void form(OvertownRequest request){
    HttpServerExchange exchange = request.getExchange();
    exchange.getResponseHeaders().put(Headers.CONTENT_TYPE, "text/html");
    exchange.getResponseSender().send("<form action='/car/read' method='post' >"
        + "<label>Year:</label><input name='car.year' />"
        + "<label>Model:</label><input name='car.model' />"
        + "<button type='submit'>Submit</button>"
        + "</form>");

  }

  @Page(value = "list" , responseTemplate = "cars/list.ftl")
  public void list(OvertownRequest request){
    request.addAttribute("cars" , getCars(request) );
  }

  @Page(value = "read" , responseTemplate = "cars/list.ftl")
  public void read(Car car, OvertownRequest request){
    LOGGER.info(car);
    List<Car> cars = getCars(request);
    cars.add(car);
    request.addAttribute("cars" , cars );
  }

  protected List<Car> getCars(OvertownRequest request){
    List<Car> cars = (List<Car>) request.getSession().getAttribute("carList");
    if(cars == null){
      cars = new ArrayList<>();
      request.getSession().setAttribute("carList" , cars);
    }
    return cars;
  }

}
