package org.esmerilprogramming.cloverx.management;

import org.esmerilprogramming.cloverx.annotation.path.NotFound;
import org.esmerilprogramming.cloverx.annotation.path.Path;
import org.esmerilprogramming.cloverx.http.CloverXRequest;
import org.esmerilprogramming.cloverx.http.ErrorHandler;
import org.esmerilprogramming.cloverx.http.StatusError;

/**
 * Created by efraimgentil<efraimgentil@gmail.com> on 04/03/15.
 */
@NotFound
public class CustomNotFound implements ErrorHandler {

  @Path(template = "/errorPage.ftl")
  @Override
  public void handleError(CloverXRequest request) {
    System.out.println("LOL SERIOUS ??? ");
    //request.getResponse().sendError(StatusError.NOT_FOUND);
  }
}
