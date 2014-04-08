package com.clover.scanner.testpackage;

import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;

public class ContainsHandleButNothHandler {
	
	public void nothingToBeSeen(){
		HttpHandler handler = new HttpHandler() {
			
			@Override
			public void handleRequest(HttpServerExchange arg0) throws Exception {
				// TODO Auto-generated method stub
				
			}
		};
		
	}
	
}
