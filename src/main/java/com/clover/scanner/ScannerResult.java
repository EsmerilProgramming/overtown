package com.clover.scanner;

import io.undertow.server.HttpHandler;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServlet;

public class ScannerResult {
	
	
	private List<Class<? extends HttpHandler>> handlers;
	private List<Class<? extends HttpServlet>> servlets;
	
	public ScannerResult() {
		handlers = new ArrayList<>();
		servlets = new ArrayList<>();
	}
	
	public void addClass(Class<?> unkownClass){
		if(unkownClass.isInstance( HttpHandler.class ) )
			addHandlerClass( ( Class<? extends HttpHandler>) unkownClass );
		if(unkownClass.isInstance( HttpServlet.class ) )
			addServletClass( (Class<? extends HttpServlet>) unkownClass );
	}
	
	public void addHandlerClass(Class<? extends HttpHandler> handlerClass){
		handlers.add(handlerClass);
	}
	
	public void addServletClass(Class<? extends HttpServlet> servletClass){
		getServlets().add(servletClass);
	}

	public List<Class<? extends HttpServlet>> getServlets() {
		return servlets;
	}

	public List<Class<? extends HttpHandler>> getHandlers() {
		return handlers;
	}

	
	
	
}
