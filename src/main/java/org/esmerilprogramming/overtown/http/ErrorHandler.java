package org.esmerilprogramming.overtown.http;

/**
 * Created by efraimgentil<efraimgentil@gmail.com> on 02/03/15.
 */
public interface ErrorHandler {

  public static final String ERROR_500 = "CLOVERX_ERROR_500";

  public abstract void handleError(OvertownRequest request);

}
