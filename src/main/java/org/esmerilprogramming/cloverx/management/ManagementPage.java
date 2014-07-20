package org.esmerilprogramming.cloverx.management;

import org.esmerilprogramming.cloverx.annotation.BeforeTranslate;
import org.esmerilprogramming.cloverx.annotation.Controller;
import org.esmerilprogramming.cloverx.annotation.Page;
import org.esmerilprogramming.cloverx.http.CloverRequest;
import org.esmerilprogramming.cloverx.http.converter.ParameterConverter;
import org.esmerilprogramming.cloverx.management.model.ServerStatus;
import org.esmerilprogramming.cloverx.view.ViewAttributes;

import io.undertow.server.HttpServerExchange;
import io.undertow.util.Headers;

@Controller(path = "/management")
public class ManagementPage {

  public ManagementPage() {}

  @BeforeTranslate
  public void doSomething( CloverRequest request ) {
    System.out.println("SOMETHING BEFORE TRANSLATE");
    request.addAttribute("name", "Efraim Gentil");
  }

  @Page("teste")
  public void teste(String nomeDaString, CloverRequest request) {
    System.out.println(nomeDaString);
  }

  @Page("testeInteger")
  public void teste(Integer id, CloverRequest request) {
    System.out.println("Teste integer");
    System.out.println(id);
  }

  @Page("testeDouble")
  public void teste(Double val) {
    System.out.println("Teste double");
    System.out.println(val);
  }

  @Page("testeLong")
  public void teste(Long val) {
    System.out.println("Teste Long");
    System.out.println(val);
  }

  @Page("teste2")
  public void teste2(HttpServerExchange exchange, ServerStatus serverStatus) {
    exchange.getResponseHeaders().put(Headers.CONTENT_TYPE, "text/html");

    System.out.println(serverStatus);

    exchange.getResponseSender().send(
        "<form method='post' >" + "<input name='serverStatus.host' />"
            + "<input name='serverStatus.port' />" + "<button type='submit'>Submit</button>"
            + "</form>");
  }

  @Page("teste3")
  public void teste3(HttpServerExchange exchange, ServerStatus server) {
    exchange.getResponseHeaders().put(Headers.CONTENT_TYPE, "text/html");

    System.out.println(server);

    exchange.getResponseSender().send(
        "<form method='post' >" + "<input name='serverStatus.host' />"
            + "<input name='serverStatus.port' />" + "<button type='submit'>Submit</button>"
            + "</form>");
  }

  @Page(value = "testeTemplate", responseTemplate = "teste.ftl")
  public void testeTemplate(CloverRequest request) {
//    request.addAttribute("name", "Efraim Gentil");
  }
  
  @Page(value = "testeTemplate2", responseTemplate = "teste.ftl")
  public void testeTemplate(ViewAttributes attributes) {
    attributes.add("name", "Efraim Gentil");
  }

  @Page("testePrimitivo")
  public void testePrimitivo(int i, long l) {
    System.out.println(i);
    System.out.println(l);
  }

}
