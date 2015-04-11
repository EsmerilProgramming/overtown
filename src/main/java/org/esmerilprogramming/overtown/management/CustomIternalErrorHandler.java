package org.esmerilprogramming.overtown.management;

import io.undertow.io.Sender;
import io.undertow.server.HttpServerExchange;
import org.esmerilprogramming.overtown.annotation.path.InternalError;
import org.esmerilprogramming.overtown.http.OvertownRequest;
import org.esmerilprogramming.overtown.http.ErrorHandler;
import org.esmerilprogramming.overtown.http.StatusError;

/**
 * Created by efraimgentil<efraimgentil@gmail.com> on 02/03/15.
 */
@InternalError
public class CustomIternalErrorHandler implements ErrorHandler {

  @Override
  public void handleError(OvertownRequest request) {
    HttpServerExchange exchange = request.getExchange();
    exchange.setResponseCode(StatusError.METHOD_NOT_ALLOWED.getCode());
    StringBuilder sb = new StringBuilder();
    sb.append("<html>");
    sb.append("<head>");
    sb.append("<title>500 MOTHER FUCKER</title>");
    sb.append("</head>");
    sb.append("<body>");
    sb.append("<p>Error " +  request.getAttribute( ErrorHandler.ERROR_500 ) + "</p>");
    sb.append("</body>");
    sb.append("</html>");

    Sender rs = exchange.getResponseSender();
    rs.send( sb.toString() );
    rs.close();
  }
}
