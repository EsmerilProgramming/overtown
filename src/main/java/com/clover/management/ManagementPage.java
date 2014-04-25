package com.clover.management;

import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;
import io.undertow.util.Headers;

import com.clover.annotation.Page;
import com.clover.http.CloverRequest;
import com.clover.management.model.ServerStatus;

@Page("/management")
public class ManagementPage implements HttpHandler {
	
	public ManagementPage() {
	}
	
	@Override
	public void handleRequest(HttpServerExchange exchange) throws Exception {
		exchange.getResponseHeaders().put(Headers.CONTENT_TYPE, "text/plain");
		
		System.out.println( exchange  );
		
        exchange.getResponseSender().send("Hello World");
	}
	
	@Page("teste")
	public  void teste(String nomeDaString , CloverRequest request ){
		System.out.println( nomeDaString );
	}
	
	@Page("testeInteger")
	public  void teste(Integer id , CloverRequest request ){
		System.out.println("Teste integer");
		System.out.println( id );
	}
	
	@Page("testeDouble")
	public  void teste( Double val  ){
		System.out.println("Teste double");
		System.out.println( val );
	}
	
	@Page("testeLong")
	public  void teste( Long val  ){
		System.out.println("Teste Long");
		System.out.println( val );
	}
	
	@Page("teste2")
	public  void teste2(HttpServerExchange exchange , ServerStatus serverStatus ){
		exchange.getResponseHeaders().put(Headers.CONTENT_TYPE, "text/html");
		
		System.out.println( serverStatus  );
		
        exchange.getResponseSender().send("<form method='post' >"
        		+ "<input name='serverStatus.host' />"
        		+ "<input name='serverStatus.port' />"
        		+ "<button type='submit'>Submit</button>"
        		+ "</form>");
	}
	
}
