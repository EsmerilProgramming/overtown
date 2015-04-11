package org.esmerilprogramming.overtown.view;

import java.io.File;
import java.io.IOException;

import org.esmerilprogramming.overtown.server.ConfigurationHolder;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.TemplateException;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ViewParserTest {

  public ViewParserTest() {}
  
  @Mock ConfigurationHolder configHolder;
  
  @Before
  public void setUp() throws IOException{
     Configuration cfg = new Configuration();
     cfg.setDirectoryForTemplateLoading( new File( this.getClass().getResource("/templates").getPath() ) );
     cfg.setObjectWrapper( new DefaultObjectWrapper() );
     when(configHolder.getFreemarkerConfig() ).thenReturn( cfg );
  }

  @Test
  public void doesParseTheTemplateUsingFreemarker() throws TemplateException, IOException {
    String helloMessage = "Hi i'm clover";
    
    ViewAttributes att = new ViewAttributes();
    att.add("hello", "Hi i'm clover");

    String parseTemplate = new ViewParser(configHolder).parse(att, "teste.ftl");

    assertNotNull(parseTemplate);
    assertTrue("Should contains helloMessage", parseTemplate.contains(helloMessage));
  }
  
  @Test(expected = IOException.class)
  public void doesThrowParseExceptionIfDoesNotFindTheTemplate() throws TemplateException, IOException{
    
    String parseTemplate = new ViewParser(configHolder).parse( new ViewAttributes(), "NOT_A_TEMPLATE.ftl");
    
  }

}
