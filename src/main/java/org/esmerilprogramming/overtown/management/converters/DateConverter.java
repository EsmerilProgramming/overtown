package org.esmerilprogramming.overtown.management.converters;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.esmerilprogramming.overtown.http.converter.GenericConverter;

public class DateConverter extends GenericConverter<Date> {

  @Override
  public Date convert(String value) {
    if(value != null){
      SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
      try {
        return sdf.parse(value);
      } catch (ParseException e) {
        e.printStackTrace();
      }
    }
    return null;
  }

}
