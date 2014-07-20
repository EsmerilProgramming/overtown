package org.esmerilprogramming.cloverx.view;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import freemarker.template.TemplateException;
import static org.junit.Assert.*;

public class ViewParserTest {

  public ViewParserTest() {}

  @Test
  public void doesParseTheTemplateUsingFreemarker() throws TemplateException, IOException {
    String helloMessage = "Hi i'm clover";
    Map<String, Object> map = new HashMap<>();
    map.put("hello", "Hi i'm clover");

    String parseTemplate = new ViewParser().parse(map, "teste.ftl");

    assertNotNull(parseTemplate);
    assertTrue("Should contains helloMessage", parseTemplate.contains(helloMessage));
  }
  
  @Test
  public void doesThrowParseExceptionIfDoesNotFindTheTemplate() throws TemplateException, IOException{
    
    String parseTemplate = new ViewParser().parse( new HashMap<>(), "NOT_A_TEMPLATE.ftl");
    
  }

}
