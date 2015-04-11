package org.esmerilprogramming.overtown.management;

import org.esmerilprogramming.overtown.annotation.Controller;
import org.esmerilprogramming.overtown.annotation.JSONResponse;
import org.esmerilprogramming.overtown.annotation.Page;
import org.esmerilprogramming.overtown.http.CloverXRequest;
import org.jboss.logging.Logger;


@Controller(path = "/json-test")
public class JsonTestPage {

  private final Logger logger = Logger.getLogger(JsonTestPage.class);

  @JSONResponse
  @Page("testOne")
  public void testOne(CloverXRequest request){
    logger.info("Called test one");
    request.addAttribute("name" , "Efraim Gentil");
    request.addAttribute("email" , "efraimgentil@gmail.com");
  }

  @Page("testOne/")
  public void testOneSlash(CloverXRequest request){
    logger.info("Called test one");
    request.addAttribute("name" , "Efraim Gentil");
    request.addAttribute("email" , "efraimgentil@gmail.com");
  }

}
