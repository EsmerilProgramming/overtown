package com.clover.http;

import io.undertow.server.HttpServerExchange;

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
		Deque<String> deque = queryParameters.get(name);
		
		if(deque != null)
			return deque.getFirst();
		
		return null;
	}

	public HttpServerExchange getExchange() {
		return exchange;
	}
	
}
