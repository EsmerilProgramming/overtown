package com.clover.management;

import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;
import io.undertow.util.Headers;

import com.clover.annotation.Page;

@Page("/management")
public class ManagementPage implements HttpHandler {
	
	public ManagementPage() {
	}
	
	@Override
	public void handleRequest(HttpServerExchange exchange) throws Exception {
		exchange.getResponseHeaders().put(Headers.CONTENT_TYPE, "text/plain");
        exchange.getResponseSender().send("Hello World");
	}

}
