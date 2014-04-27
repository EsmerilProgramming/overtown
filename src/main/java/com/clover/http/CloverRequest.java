package com.clover.http;

import io.undertow.server.HttpServerExchange;
import io.undertow.server.handlers.form.FormData;
import io.undertow.server.handlers.form.FormData.FormValue;
import io.undertow.server.handlers.form.FormDataParser;
import io.undertow.server.handlers.form.FormEncodedDataDefinition;

import java.io.IOException;
import java.util.Deque;
import java.util.Map;

public class CloverRequest {
	
	private HttpServerExchange exchange;
	private Map<String, Deque<String>> queryParameters;
	
	public CloverRequest( HttpServerExchange exchange ) {
		this.exchange = exchange;
		this.queryParameters = exchange.getQueryParameters();
	}
	
	public Object getAttribute(String name){
		if("POST".equalsIgnoreCase( exchange.getRequestMethod().toString() ) ){
			FormDataParser create = new FormEncodedDataDefinition().create(exchange);
			try {
				FormData parseBlocking = create.parseBlocking();
				Deque<FormValue> deque2 = parseBlocking.get(name);
				if(deque2 != null)
					return deque2.getLast().getValue();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		Deque<String> deque = queryParameters.get(name);
		
		return null;
	}

	public HttpServerExchange getExchange() {
		return exchange;
	}
	
}
