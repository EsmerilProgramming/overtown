package org.esmerilprogramming.cloverx.view;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.Map;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class ViewParser {

  private Configuration cfg = new Configuration();
  private static final String TEMPLATES_FOLDER = "/templates";

  public ViewParser() {}

  public final String parse(Map map, String templateName) throws TemplateException, IOException {
    File file = new File(this.getClass().getResource(TEMPLATES_FOLDER).getPath());
    cfg.setDirectoryForTemplateLoading(file);
    cfg.setObjectWrapper(new DefaultObjectWrapper());

    // recupera o template
    Template t = cfg.getTemplate(templateName);
    StringWriter writer = new StringWriter();
    t.process(map, writer);
    writer.flush();
    writer.close();
    return writer.toString();
  }

}
