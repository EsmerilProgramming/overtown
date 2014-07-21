package org.esmerilprogramming.cloverx.management;

import java.util.Date;

import org.esmerilprogramming.cloverx.annotation.BeforeTranslate;
import org.esmerilprogramming.cloverx.annotation.Controller;
import org.esmerilprogramming.cloverx.annotation.Converter;
import org.esmerilprogramming.cloverx.annotation.Page;
import org.esmerilprogramming.cloverx.http.CloverXRequest;
import org.esmerilprogramming.cloverx.http.converter.ParameterConverter;
import org.esmerilprogramming.cloverx.management.converters.DateConverter;
import org.esmerilprogramming.cloverx.management.converters.ServerStatusConverter;
import org.esmerilprogramming.cloverx.management.model.ServerStatus;
import org.esmerilprogramming.cloverx.view.ViewAttributes;

import io.undertow.server.HttpServerExchange;
import io.undertow.util.Headers;

@Controller(path = "/management")
public class ManagementPage {

  public ManagementPage() {}

  @BeforeTranslate
  public void doSomething( CloverXRequest request ) {
    System.out.println("SOMETHING BEFORE TRANSLATE");
    request.addAttribute("name", "Efraim Gentil");
  }

  @Page("teste")
  public void teste(String nomeDaString, CloverXRequest request) {
    System.out.println(nomeDaString);
  }

  @Page("testeInteger")
  public void teste(Integer id, CloverXRequest request) {
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
  public void testeTemplate(CloverXRequest request) {
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
  
  @Page("dateConvert")
  public void dateConvert(@Converter(DateConverter.class) Date dataPagina){
    System.out.println( dataPagina );
  }
  
  @Page("objectConvert")
  public void objectConvert(@Converter(ServerStatusConverter.class) ServerStatus serverStatus ){
    System.out.println( serverStatus );
  }
  
  @Converter(value = ServerStatusConverter.class)
  @Page("objectConvert2")
  public void objectConvert2(ServerStatus serverStatus ){
    System.out.println( serverStatus );
  }

}
