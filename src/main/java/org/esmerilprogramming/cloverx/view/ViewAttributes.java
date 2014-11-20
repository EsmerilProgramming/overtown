package org.esmerilprogramming.cloverx.view;

import java.util.HashMap;
import java.util.Map;

public class ViewAttributes {

  Map<String, Object> attributes;
  
  public ViewAttributes() { 
    attributes = new HashMap<String, Object>();
  }
  
  public <T> void add(String name , T value){
    attributes.put(name, value);
  }
  
  public boolean attributeExists(String key){
	  return attributes.containsKey(key);
  }
  
  public Map<String, Object> getAsMap(){
    return attributes;
  }

}
