package com.clover.scanner;

import io.undertow.server.HttpHandler;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServlet;

/**
 * 
 * @author efraimgentil (efraim.gentil@gmail.com)
 */
public class ScannerResult {
	
	private List<Class<? extends HttpHandler>> handlers;
	private List<Class<? extends HttpServlet>> servlets;
	
	public ScannerResult() {
		handlers = new ArrayList<>();
		servlets = new ArrayList<>();
	}
	
	@SuppressWarnings("unchecked")
	public void addClass(Class<?> unkownClass){
		
		if( HttpHandler.class.isAssignableFrom(  unkownClass ) )
			addHandlerClass( ( Class<? extends HttpHandler>) unkownClass );
		if( HttpServlet.class.isAssignableFrom( unkownClass ) )
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
