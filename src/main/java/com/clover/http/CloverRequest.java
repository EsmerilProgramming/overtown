package com.clover.http;

import io.undertow.server.HttpServerExchange;
import io.undertow.server.handlers.form.FormData;
import io.undertow.server.handlers.form.FormData.FormValue;
import io.undertow.server.handlers.form.FormDataParser;
import io.undertow.server.handlers.form.FormEncodedDataDefinition;

import java.io.IOException;
import java.util.Deque;
import java.util.Map;

/**
 * @author efraimgentil (efraim.gentil@gmail.com)
 */
public class CloverRequest {
	
	private HttpServerExchange exchange;
	private Map<String, Deque<String>> queryParameters;
	
	public CloverRequest( HttpServerExchange exchange ) {
		this.exchange = exchange;
		this.queryParameters = exchange.getQueryParameters();
	}
	
	public Object getAttribute(String name){
		if("POST".equalsIgnoreCase( exchange.getRequestMethod().toString() ) ){
			return getFromFormData(name);
		}
		
		Deque<String> deque = queryParameters.get(name);
		if(deque != null)
			return deque.getLast();
		
		return null;
	}
	
	protected Object getFromFormData(String name){
		FormDataParser create = new FormEncodedDataDefinition().create(exchange);
		try {
			FormData formData = create.parseBlocking();
			Deque<FormValue> dequeVal = formData.get(name);
			if(dequeVal != null)
				return dequeVal.getLast().getValue();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public HttpServerExchange getExchange() {
		return exchange;
	}
	
	
	
}
