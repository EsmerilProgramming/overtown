package org.esmerilprogramming.cloverx.view;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class ViewParser {

  private Configuration cfg = new Configuration();
  private static final String TEMPLATES_FOLDER = "/templates";

  public ViewParser() throws IOException {
    File file = new File(this.getClass().getResource(TEMPLATES_FOLDER).getPath());
    cfg.setDirectoryForTemplateLoading(file);
    cfg.setObjectWrapper(new DefaultObjectWrapper());
  }

  public final String parse( ViewAttributes viewAttributes , String templateName) throws TemplateException, IOException {
    Template t = cfg.getTemplate(templateName);
    
    StringWriter writer = new StringWriter();
    t.process( viewAttributes.getAsMap() , writer);
    writer.flush();
    writer.close();
    return writer.toString();
  }

}
