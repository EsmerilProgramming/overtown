package org.esmerilprogramming.cloverx.view;

import java.io.IOException;
import java.io.StringWriter;

import org.esmerilprogramming.cloverx.server.ConfigurationHolder;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class ViewParser {

  private Configuration cfg = new Configuration();

  public ViewParser( ConfigurationHolder configHandler ) throws IOException {
      cfg = configHandler.getFreemarkerConfig();
  }

  public final String parse(ViewAttributes viewAttributes, String templateName)
      throws TemplateException, IOException {
    Template t = cfg.getTemplate(templateName);
    try( StringWriter writer = new StringWriter() ){
      t.process(viewAttributes.getAsMap(), writer);
      writer.flush();
      return writer.toString();
    }
  }

}
