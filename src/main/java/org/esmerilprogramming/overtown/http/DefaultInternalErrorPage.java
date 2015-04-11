package org.esmerilprogramming.overtown.http;

import io.undertow.io.Sender;
import io.undertow.server.HttpServerExchange;

/**
 * Created by efraimgentil<efraimgentil@gmail.com> on 02/03/15.
 */
public class DefaultInternalErrorPage implements ErrorHandler {

  @Override
  public void handleError(CloverXRequest request) {
    HttpServerExchange exchange = request.getExchange();
    exchange.setResponseCode(StatusError.METHOD_NOT_ALLOWED.getCode());
    StringBuilder sb = new StringBuilder();
    sb.append("<html>");
    sb.append("<head>");
    sb.append("<title>500 Internal server error</title>");
    sb.append("</head>");
    sb.append("<body>");
    sb.append("<h1>500 Internal server error</h1>");
    sb.append("<p>An unexpected error occurred</p>");
    sb.append("</body>");
    sb.append("</html>");

    Sender rs = exchange.getResponseSender();
    rs.send( sb.toString() );
    rs.close();
  }

}
