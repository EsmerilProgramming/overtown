package org.esmerilprogramming.cloverx.http.parameter;

import static org.junit.Assert.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.esmerilprogramming.cloverx.http.CloverXRequest;
import org.esmerilprogramming.cloverx.http.converter.GenericConverter;
import org.esmerilprogramming.cloverx.http.converter.ParameterConverter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class GenericConverterTest {
  
  @Mock CloverXRequest cloverXRequest;
  
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
     when( cloverXRequest.getParameter("name")).thenReturn("Efraim Gentil");
     
     String translate = converter.translate(String.class , "name", cloverXRequest);
     
     System.out.println( translate);
  }
  
  @Test
  public void doesConvertAStringParameterToDateADate(){
    ParameterConverter converter = new MyDateFormatConverter();
    when( cloverXRequest.getParameter("myDate")).thenReturn("02/2014");
    
    Date translate = converter.translate(Date.class , "myDate", cloverXRequest);
    
    System.out.println( translate);
  }

  
  
}
