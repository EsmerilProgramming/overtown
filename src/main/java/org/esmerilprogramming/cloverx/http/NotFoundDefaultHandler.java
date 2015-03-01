package org.esmerilprogramming.cloverx.http;

import io.undertow.io.Sender;
import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;

/**
 * Created by efraimgentil<efraimgentil@gmail.com> on 01/03/15.
 */
public class NotFoundDefaultHandler implements HttpHandler {
  @Override
  public void handleRequest(HttpServerExchange exchange) throws Exception {
    System.out.println("NOT FOUND ?");
    Sender rs = exchange.getResponseSender();
    StringBuilder sb = new StringBuilder("<html>");
    sb.append("<html>");
    sb.append("<head>");
    sb.append("<title>404 Not Found</title>");
    sb.append("</head>");
    sb.append("<body>");
    sb.append("<h1>404 - Not Found</h1>");
    sb.append("<p>This is not the page you are looking for</p>");
    sb.append("</body>");
    sb.append("</html>");
    rs.send( sb.toString() );
    rs.close();
  }
}

