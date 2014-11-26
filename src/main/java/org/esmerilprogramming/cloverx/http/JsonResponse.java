package org.esmerilprogramming.cloverx.http;

import java.io.StringWriter;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.stream.JsonGenerator;

import io.undertow.io.Sender;
import io.undertow.server.HttpServerExchange;

import org.esmerilprogramming.cloverx.http.converter.DefaultObjectToJsonConverter;
import org.esmerilprogramming.cloverx.http.converter.ObjectToJsonConverter;
import org.esmerilprogramming.cloverx.view.ViewAttributes;

public class JsonResponse extends Response {
  
  private Map<String, ObjectToJsonConverter> converters;
	
  public JsonResponse(HttpServerExchange exchange, ViewAttributes viewAttributes) {
    super(exchange, viewAttributes);
    super.setContentType( "application/json; charset=UTF-8;" );
  }
  
  @Override
  public void sendRedirect(String toPath) {
    throw new IllegalStateException("Json response does not suport redirect");
  }
  
  @Override
  public void setContentType(String contentType) {
    throw new IllegalStateException("Content-type can't be changed");
  }
  
  @Override
  public void setHeader(String headerName, String value) {
    if("content-type".equalsIgnoreCase(headerName)){
      throw new IllegalStateException("Content-type can't be changed");
    }else{
      super.setHeader(headerName, value);
    }
  }
  
  @Override
	public void addAttribute(String name, Object value) {
	  	addObjectToJsonConnveter(name , new DefaultObjectToJsonConverter() );
		super.addAttribute(name , value);
	}
  
  @Override
  public void sendAsResponse(String jsonAsString) {
	Sender responseSender = exchange.getResponseSender();
	responseSender.send(jsonAsString);
	responseSender.close();
  }
  
  public void addObjectToJsonConnveter(String attributeName , ObjectToJsonConverter conveter){
	 if(converters == null){
	   converters = new HashMap<>();
	 }
	 converters.put(attributeName , conveter );
  }
  
  @Override
  public void finishResponse() {
	 sendAsResponse( generateStringResponse() );
  }
  
  protected String generateStringResponse(){
	Set<Entry<String, Object>> entrySet = viewAttributes.getAsMap().entrySet();
	StringWriter stringWriter = new StringWriter();
	JsonGenerator generator = Json.createGenerator( stringWriter );
	generator.writeStartObject();
	for (Entry<String, Object> entry : entrySet) {
	  ObjectToJsonConverter objectToJsonConverter = converters.get( entry.getKey() );
	  Object value = entry.getValue();
	  if(isSimpleValue(value) ){
	    writeSimpleValieEntry( entry, generator );
	  }else{
	    JsonObject jsonObject = objectToJsonConverter.converter( value );
	    generator.write( entry.getKey() , jsonObject );
	  }
	  generator.writeEnd();
    }
	generator.flush();
	return stringWriter.toString();
  }
  
  private void writeSimpleValieEntry(Entry<String, Object> entry, JsonGenerator generator) {
    Object value = entry.getValue();
    if( value.getClass().isAssignableFrom( String.class ) ){
      generator.write( entry.getKey() , (String) value );
    }else if( value.getClass().isAssignableFrom( Integer.class ) || value.getClass().isAssignableFrom( int.class ) ){
      generator.write( entry.getKey() , (Integer) value );
    }else if ( value.getClass().isAssignableFrom( Double.class ) || value.getClass().isAssignableFrom( double.class ) ){
      generator.write( entry.getKey() , (Double) value );
    }else if ( value.getClass().isAssignableFrom( Long.class ) || value.getClass().isAssignableFrom( double.class ) ){
      generator.write( entry.getKey() , (Long) value );
    }else if ( value.getClass().isAssignableFrom( Float.class ) || value.getClass().isAssignableFrom( float.class ) ){
      generator.write( entry.getKey() , (Float) value );
    }else if ( value.getClass().isAssignableFrom( Boolean.class ) || value.getClass().isAssignableFrom( boolean.class ) ){
      generator.write( entry.getKey() , (Boolean) value );
    }else if ( value.getClass().isAssignableFrom( BigDecimal.class ) ){
      generator.write( entry.getKey() , (BigDecimal) value );
    }else if ( value.getClass().isAssignableFrom( BigInteger.class ) ){
      generator.write( entry.getKey() , (BigInteger) value );
    }
  }

  private boolean isSimpleValue(Object value) {
	return value.getClass().isAssignableFrom( String.class ) || value.getClass().isAssignableFrom( Integer.class ) || value.getClass().isAssignableFrom( int.class )
	    || value.getClass().isAssignableFrom( Double.class ) || value.getClass().isAssignableFrom( double.class )
	    || value.getClass().isAssignableFrom( Long.class ) || value.getClass().isAssignableFrom( double.class ) 
	    || value.getClass().isAssignableFrom( Float.class ) || value.getClass().isAssignableFrom( float.class )
	    || value.getClass().isAssignableFrom( Boolean.class ) || value.getClass().isAssignableFrom( boolean.class ) 
	    || value.getClass().isAssignableFrom( BigDecimal.class )
	    || value.getClass().isAssignableFrom( BigInteger.class );
  }

  public Map<String, ObjectToJsonConverter> getConverters() {
	return converters;
  }
  
}