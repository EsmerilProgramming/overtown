package org.esmerilprogramming.overtown.http.converter;

import java.lang.reflect.Field;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;

public class DefaultObjectToJsonConverter implements ObjectToJsonConverter {

  public JsonObject converter(Object value) {
    try {
      Field[] declaredFields = getFields( value );
      JsonObjectBuilder builder = Json.createObjectBuilder();
      for (Field field : declaredFields) {
        field.setAccessible(true);
        builder.add( field.getName() , String.valueOf( field.get(value) ) );
        field.setAccessible(false);
      }
      return builder.build();
    } catch (IllegalArgumentException | IllegalAccessException e) {
      e.printStackTrace();
    }
    return null;
  }

  protected Field[] getFields( Object value ){
    Class<? extends Object> clazz = value.getClass();
    return clazz.getDeclaredFields();
  }

}
