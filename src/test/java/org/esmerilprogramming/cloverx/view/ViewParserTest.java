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
    
    ViewAttributes att = new ViewAttributes();
    att.add("hello", "Hi i'm clover");

    String parseTemplate = new ViewParser().parse(att, "teste.ftl");

    assertNotNull(parseTemplate);
    assertTrue("Should contains helloMessage", parseTemplate.contains(helloMessage));
  }
  
  @Test(expected = IOException.class)
  public void doesThrowParseExceptionIfDoesNotFindTheTemplate() throws TemplateException, IOException{
    
    String parseTemplate = new ViewParser().parse( new ViewAttributes(), "NOT_A_TEMPLATE.ftl");
    
  }

}
