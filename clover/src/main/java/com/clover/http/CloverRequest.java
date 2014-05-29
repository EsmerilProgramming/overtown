package com.clover.http;

import io.undertow.server.HttpServerExchange;
import io.undertow.server.handlers.form.FormData;
import io.undertow.server.handlers.form.FormData.FormValue;
import io.undertow.server.handlers.form.FormDataParser;
import io.undertow.server.handlers.form.FormEncodedDataDefinition;

import java.io.IOException;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;

import com.clover.http.parameter.ParameterTranslator;

/**
 * @author efraimgentil (efraim.gentil@gmail.com)
 */
public class CloverRequest {
	
	private HttpServerExchange exchange;
	private Map<String, Deque<String>> queryParameters;
	private Map<String, ParameterTranslator > parameterTranslators;
	
	public CloverRequest( HttpServerExchange exchange ) {
		this.exchange = exchange;
		this.queryParameters = exchange.getQueryParameters();
		this.parameterTranslators = new HashMap<>();
	}
	
	public Object getAttribute(String name){
		if( isPostRequest() ) {
			return getFromFormData(name);
		}
		return getFromParameters( name );
	}
	
	protected boolean isPostRequest(){
		return "POST".equalsIgnoreCase( exchange.getRequestMethod().toString() );
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
	
	protected Object getFromParameters(String parameterName){
		Deque<String> deque = queryParameters.get( parameterName );
		if(deque != null){
			return deque.getLast();
		}
		return null;
	}

	public HttpServerExchange getExchange() {
		return exchange;
	}
	
	public void setParameterTranslator( String parameterName , ParameterTranslator parameterTranslator ){
		parameterTranslators.put(parameterName, parameterTranslator);
	} 
	
	public boolean shouldTranslateParameter(String parameterName){
		return parameterTranslators.containsKey(parameterName);
	}

	public ParameterTranslator getTranslator(String parameterName) {
		return parameterTranslators.get(parameterName);
	}
	
	
	
}
