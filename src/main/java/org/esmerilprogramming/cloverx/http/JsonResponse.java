package org.esmerilprogramming.cloverx.http;

import java.io.StringWriter;
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
	  String attrName =	entry.getKey();
	  ObjectToJsonConverter objectToJsonConverter = converters.get(attrName);
	  Object value = entry.getValue();
	  if(isSimpleValue(value)){
		  generator = writeSimpleValue( attrName , value , generator);
	  }else{
		  JsonObject jsonObject = objectToJsonConverter.converter( value );
		  generator.write( attrName , jsonObject );
	  }
    }
	generator.writeEnd();
	generator.flush();
	return stringWriter.toString();
  }
  
  private boolean isSimpleValue(Object value) {
	Class<? extends Object> clazz = value.getClass();
	return clazz.isAssignableFrom( String.class ) || clazz.isAssignableFrom( Double.class ) || clazz.isAssignableFrom( Integer.class )
			|| clazz.isAssignableFrom( Boolean.class );
  }
  
  protected JsonGenerator writeSimpleValue(String attrName , Object value , JsonGenerator generator){
	  Class<? extends Object> clazz = value.getClass();
	  if( clazz.isAssignableFrom( String.class ) ){
		  generator.write( attrName , value.toString() );
	  }else if( clazz.isAssignableFrom( Double.class ) ){
		  generator.write( attrName , (Double) value );
	  }else if( clazz.isAssignableFrom( Integer.class ) ){
		  generator.write( attrName , (Integer) value );
	  }else if(value.getClass().isAssignableFrom(Boolean.class)){
		  generator.write( attrName , new Boolean( value.toString() ) );
	  }else{
		  generator.write( attrName , value.toString() );
	  }
	  return generator;
  }
  
  public Map<String, ObjectToJsonConverter> getConverters() {
	return converters;
  }
  
}