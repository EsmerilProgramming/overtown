package org.esmerilprogramming.cloverx.view;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.net.URL;
import java.nio.file.Path;

import org.esmerilprogramming.cloverx.server.CloverXConfiguration;
import org.esmerilprogramming.cloverx.server.ConfigurationHandler;
import org.esmerilprogramming.cloverx.server.ConfigurationHandler.DeployType;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class ViewParser {

  private Configuration cfg = new Configuration();
  private static final String TEMPLATES_FOLDER = "/templates";

  public ViewParser() throws IOException {
    try {
      ConfigurationHandler instance = ConfigurationHandler.getInstance();
      CloverXConfiguration configuration = instance.getConfiguration();
      String classPathDir = instance.getClassPathDir();

      File file = null;
      if( instance.getDeployType() == DeployType.JAR ){
        if( classPathDir.endsWith( File.separator ) )
          file = new File( classPathDir + "templates" );
        else  
          file = new File( classPathDir +  File.separator + "templates" );
      }else{
        URL url = this.getClass().getResource( classPathDir + "templates" );
        file = new File( url.getPath() );
      }
      cfg.setDirectoryForTemplateLoading( file );
      cfg.setObjectWrapper(new DefaultObjectWrapper());
    } catch (Exception e) {
      e.printStackTrace();
    }
//    File file = new File( this.getClass().getResource(TEMPLATES_FOLDER).getPath() );
    
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
