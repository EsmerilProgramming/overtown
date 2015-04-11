package org.esmerilprogramming.overtown.http.converter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.esmerilprogramming.overtown.http.OvertownRequest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class GenericConverterTest {

  @Mock OvertownRequest request;

  class MyObject{

  }

  class MyObjectConverter extends GenericConverter<MyObject>{
    @Override
    public MyObject convert(String value) {
      if(value != null){
        return new MyObject();
      }
      return null;
    }

  }

  class UpperStringConverter extends GenericConverter<String>{
    @Override
    public String convert(String value) {
      return value.toUpperCase();
    }
  }

  class MyDateFormatConverter extends GenericConverter<Date>{
    @Override
    public Date convert(String value) {
      SimpleDateFormat sdf = new SimpleDateFormat("MM/yyyy");
      try {
        return sdf.parse(value);
      } catch (ParseException e) {
        e.printStackTrace();
      }
      return null;
    }
  }

  @Test
  public void doesCOnvertAParameterStringTOUpperString(){
     ParameterConverter converter = new UpperStringConverter();
     when( request.getParameter("name")).thenReturn("Efraim Gentil");
     String translate = converter.translate(String.class , "name", request);
     System.out.println(translate);
  }

  @Test
  public void doesConvertAStringParameterToDateADate(){
    ParameterConverter converter = new MyDateFormatConverter();
    when(request.getParameter("myDate")).thenReturn("02/2014");
    Date translate = converter.translate(Date.class , "myDate", request);
    System.out.println(translate);
  }
}
