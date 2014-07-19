package org.esmerilprogramming.clover.http;

import io.undertow.server.HttpServerExchange;
import io.undertow.server.handlers.form.FormData;
import io.undertow.server.handlers.form.FormData.FormValue;
import io.undertow.server.handlers.form.FormDataParser;
import io.undertow.server.handlers.form.FormEncodedDataDefinition;

import java.io.IOException;
import java.util.Deque;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.esmerilprogramming.clover.http.converter.ParameterConverter;

/**
 * @author efraimgentil (efraim.gentil@gmail.com)
 */
public class CloverRequest {

	private HttpServerExchange exchange;
	private Map<String, Deque<String>> queryParameters;
	private Map<String, ParameterConverter> parameterTranslators;
	private FormData formData;

	public CloverRequest() {
	}

	public CloverRequest(HttpServerExchange exchange) {
		this.exchange = exchange;
		this.queryParameters = exchange.getQueryParameters();
		this.parameterTranslators = new HashMap<>();
		if (isPostRequest()) {
			FormDataParser create = new FormEncodedDataDefinition()
					.create(exchange);
			try {
				formData = create.parseBlocking();
			} catch (IOException ioe) {
				Logger.getLogger(CloverRequest.class.getName()).log(Level.SEVERE, ioe.getMessage());
			}
		}
	}

	public Object getAttribute(String name) {
		Object value = null;
		if (isPostRequest()) {
			value = getFromFormData(name);
		}
		if (value == null) {
			value = getFromParameters(name);
		}
		return value;
	}

	public boolean containsAttributeStartingWith(String parameterName) {
		boolean contains = false;
		Set<String> keySet = queryParameters.keySet();
		String parameterPrefix = parameterName + ".";
//		for (String string : keySet) {
			if (keySet.contains( parameterPrefix )) {
				contains = true;
//				break;
			}
//		}
		if(isPostRequest() && contains == false ){
			Iterator<String> iterator = formData.iterator();
			while(iterator.hasNext()){
				String next = iterator.next();
				if(next.contains( parameterPrefix )){
					contains = true;
					break;
				}
			}
		}
		return contains;
		
	}

	protected boolean isPostRequest() {
		return "POST".equalsIgnoreCase(exchange.getRequestMethod().toString());
	}

	protected Object getFromFormData(String name) {
		if(formData != null){
			Deque<FormValue> dequeVal = formData.get(name);
			if (dequeVal != null) {
				return dequeVal.getLast().getValue();
			}
		}
		return null;
	}

	protected Object getFromParameters(String parameterName) {
		Deque<String> deque = queryParameters.get(parameterName);
		if (deque != null) {
			return deque.getLast();
		}
		return null;
	}

	public HttpServerExchange getExchange() {
		return exchange;
	}

	public void setParameterTranslator(String parameterName,
			ParameterConverter parameterTranslator) {
		parameterTranslators.put(parameterName, parameterTranslator);
	}

	public boolean shouldTranslateParameter(String parameterName) {
		return parameterTranslators.containsKey(parameterName);
	}

	public ParameterConverter getTranslator(String parameterName) {
		return parameterTranslators.get(parameterName);
	}

	protected void setQueryParameters(Map<String, Deque<String>> queryParameters) {
		this.queryParameters = queryParameters;
	}

}
