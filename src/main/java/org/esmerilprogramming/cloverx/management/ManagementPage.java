package org.esmerilprogramming.cloverx.management;

import io.undertow.server.HttpServerExchange;
import io.undertow.util.Headers;

import java.util.Date;

import org.esmerilprogramming.cloverx.annotation.BeforeTranslate;
import org.esmerilprogramming.cloverx.annotation.Controller;
import org.esmerilprogramming.cloverx.annotation.Converter;
import org.esmerilprogramming.cloverx.annotation.Page;
import org.esmerilprogramming.cloverx.http.CloverXRequest;
import org.esmerilprogramming.cloverx.http.CloverXSession;
import org.esmerilprogramming.cloverx.http.JsonResponse;
import org.esmerilprogramming.cloverx.management.converters.DateConverter;
import org.esmerilprogramming.cloverx.management.converters.ServerStatusConverter;
import org.esmerilprogramming.cloverx.management.model.ServerStatus;
import org.esmerilprogramming.cloverx.view.ViewAttributes;

@Controller(path = "/management")
public class ManagementPage {

  public ManagementPage() {}

  @BeforeTranslate
  public void doSomething( CloverXRequest request ) {
    System.out.println("SOMETHING BEFORE TRANSLATE");
    request.addAttribute("name", "Efraim Gentil");
  }
  
  @Page(value = "ws" , responseTemplate = "form.ftl")
  public void wsTeste() {
    
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
  
  @Page("teste4")
  public void teste3(Integer x , HttpServerExchange exchange) {
    System.out.println( "Teste: " + x);
    exchange.getResponseSender().send("This is your x: " + x );
  }

  @Page(value = "testeTemplate", responseTemplate = "teste.ftl")
  public void testeTemplate(CloverXRequest request) {
//    request.addAttribute("name", "Efraim Gentil");
    CloverXSession session = request.getSession();
    System.out.println( session.getAttribute("nome") );
    
  }
  
  @Page(value = "nameInSession")
  public void testeTemplate(CloverXRequest request , String nome) {
    CloverXSession session = request.getSession();
    if(session.getAttribute("nome") == null ){  
      session.setAttribute("nome", nome );
      session.setAttribute("int", 10 );
    }else{
      System.out.println( session.getAttribute("nome") );
    }
  }
  
  @Page(value = "logout")
  public void logout(CloverXRequest request , String nome) {
    CloverXSession session = request.getSession();
    session.destroy();
  }
  
  
  @Page(value = "newSession")
  public void testeTemplateT(CloverXRequest request , String nome) {
    CloverXSession session = request.createSession();
    session.setAttribute("nome", "TESTE HIHE HE" );
    session.setAttribute("int", 9999 );
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
  
  @Page("testeResponse")
  public void dateConvert(HttpServerExchange exchange){
//    Response r = new Response(exchange) {
//      @Override
//      protected void sendAsResponse(ByteBuffer buffer) {
//        // TODO Auto-generated method stub
//      }
//    };
//    System.out.println(" LOL ");
//    r.sendRedirect("http://127.0.0.1:8080/management/testeTemplate");
  }
  
  @Page("notFound")
  public void asdasda(HttpServerExchange exchange){
//    Response r = new Response(exchange) {
//      @Override
//      protected void sendAsResponse(ByteBuffer buffer) {
//        // TODO Auto-generated method stub
//      }
//    };
//    r.sendError(StatusError.NOT_FOUND);
  }
  
  @Page("json")
  public void respondJson(CloverXRequest request){
    JsonResponse jsonResponse = request.getJsonResponse();
    jsonResponse.setCharset("UTF-8");
    jsonResponse.sendAsResponse("{ \"name\" : \"Efraim Gentil\" , \"blah\" : \"çãoéàè\" }");
  }
  
  @Page("jso2")
  public void respondJson(JsonResponse response){
    response.sendAsResponse("{ \"name\" : \"Efraim Gentil\" , \"blah\" : \"çãoéàè\" }");
  }
  
  @Page("json3")
  public void respondJson3(JsonResponse response){
    response.addAttribute("name", "Efraim Gentil");
    response.addAttribute("age", 26);
    response.addAttribute("blah", "çãoéàè");
  }

}

