package org.esmerilprogramming.overtown.management.converters;

import org.esmerilprogramming.overtown.http.converter.GenericConverter;
import org.esmerilprogramming.overtown.management.model.ServerStatus;

public class ServerStatusConverter extends GenericConverter<ServerStatus> {

  @Override
  public ServerStatus convert(String value) {
    ServerStatus ss = new ServerStatus();
    ss.setHost("NAH QUALQUER");
    ss.setPort( value );
    return ss;
  }
  
}