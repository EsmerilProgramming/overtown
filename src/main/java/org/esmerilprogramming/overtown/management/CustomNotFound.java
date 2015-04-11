package org.esmerilprogramming.overtown.management;

import org.esmerilprogramming.overtown.annotation.path.Path;
import org.esmerilprogramming.overtown.http.OvertownRequest;
import org.esmerilprogramming.overtown.http.ErrorHandler;

/**
 * Created by efraimgentil<efraimgentil@gmail.com> on 04/03/15.
 */
//@NotFound
public class CustomNotFound implements ErrorHandler {

  @Path(template = "/errorPage.ftl")
  @Override
  public void handleError(OvertownRequest request) {
    System.out.println("LOL SERIOUS ??? ");
    //request.getResponse().sendError(StatusError.NOT_FOUND);
  }
}
