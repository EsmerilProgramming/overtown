package org.esmerilprogramming.overtown.management;

import org.esmerilprogramming.overtown.annotation.path.Path;
import org.esmerilprogramming.overtown.http.CloverXRequest;
import org.esmerilprogramming.overtown.http.ErrorHandler;

/**
 * Created by efraimgentil<efraimgentil@gmail.com> on 04/03/15.
 */
//@MethodNotAllowed
public class CustomMethodNotAllowed implements ErrorHandler {

  @Path(template = "/methodNotAllowed.ftl")
  @Override
  public void handleError(CloverXRequest request) {
    System.out.println("LOL SERIOUS ??? ERRORS HAPPENS ");
    //request.getResponse().sendError(StatusError.NOT_FOUND);
  }
}
