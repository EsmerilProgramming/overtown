package org.esmerilprogramming.cloverx.management.converters;

import org.esmerilprogramming.cloverx.http.converter.GenericConverter;
import org.esmerilprogramming.cloverx.management.model.ServerStatus;

public class ServerStatusConverter extends GenericConverter<ServerStatus> {

  @Override
  public ServerStatus convert(String value) {
    ServerStatus ss = new ServerStatus();
    ss.setHost("NAH QUALQUER");
    ss.setPort( value );
    return ss;
  }
  
}