package org.esmerilprogramming.cloverx.management;

import io.undertow.io.Sender;
import io.undertow.server.HttpServerExchange;
import org.esmerilprogramming.cloverx.annotation.JSONResponse;
import org.esmerilprogramming.cloverx.annotation.path.*;
import org.esmerilprogramming.cloverx.annotation.path.InternalError;
import org.esmerilprogramming.cloverx.http.CloverXRequest;
import org.esmerilprogramming.cloverx.http.ErrorHandler;
import org.esmerilprogramming.cloverx.http.StatusError;

/**
 * Created by efraimgentil<efraimgentil@gmail.com> on 02/03/15.
 */
@InternalError
public class CustomIternalErrorHandler implements ErrorHandler {

  @Override
  public void handleError(CloverXRequest request) {
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
