package org.esmerilprogramming.cloverx.http.converter;

import javax.json.JsonObject;

public interface ObjectToJsonConverter {
	
	JsonObject converter(Object value);
	
}
