package org.esmerilprogramming.cloverx.management;

import org.esmerilprogramming.cloverx.annotation.Controller;
import org.esmerilprogramming.cloverx.annotation.JSONResponse;
import org.esmerilprogramming.cloverx.annotation.Page;
import org.esmerilprogramming.cloverx.http.CloverXRequest;
import org.jboss.logging.Logger;


@Controller(path = "/json-test")
public class JsonTestPage {

  private final Logger logger = Logger.getLogger(JsonTestPage.class);

  @JSONResponse
  @Page("testOne")
  public void testOne(CloverXRequest request){
    logger.debug("Called test one");
    request.addAttribute("name" , "Efraim Gentil");
    request.addAttribute("email" , "efraimgentil@gmail.com");
  }

}
