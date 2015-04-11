package org.esmerilprogramming.overtown.http.converter;

import javax.json.JsonObject;

public interface ObjectToJsonConverter {
	
	JsonObject converter(Object value);
	
}
