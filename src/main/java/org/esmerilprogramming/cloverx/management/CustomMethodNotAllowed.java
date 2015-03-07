package org.esmerilprogramming.cloverx.management;

import org.esmerilprogramming.cloverx.annotation.path.MethodNotAllowed;
import org.esmerilprogramming.cloverx.annotation.path.NotFound;
import org.esmerilprogramming.cloverx.annotation.path.Path;
import org.esmerilprogramming.cloverx.http.CloverXRequest;
import org.esmerilprogramming.cloverx.http.ErrorHandler;

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
