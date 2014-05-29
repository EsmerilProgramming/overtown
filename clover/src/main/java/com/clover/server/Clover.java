package com.clover.server;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import com.clover.annotation.Page;
import com.clover.scanner.PackageScanner;
import com.clover.scanner.ScannerResult;
import com.clover.scanner.exception.PackageNotFoundException;

import io.undertow.Handlers;
import io.undertow.Undertow;
import io.undertow.Undertow.Builder;
import io.undertow.server.HttpHandler;
import io.undertow.server.handlers.PathHandler;
import io.undertow.server.handlers.resource.FileResourceManager;

public class Clover {
	
	private Undertow server;
	
	public static void main(String[] args) {
		
		Clover clover = new Clover();
		try {
			clover.start();
		} catch (NoSuchMethodException | SecurityException
				| InstantiationException | IllegalAccessException
				| IllegalArgumentException | InvocationTargetException
				| PackageNotFoundException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void start() throws PackageNotFoundException, IOException, NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
		ClassLoader classLoader = this.getClass().getClassLoader();
		ScannerResult scan = new PackageScanner().scan("", classLoader);
		
		Builder builder = Undertow.builder();
		builder.addHttpListener(8080, "localhost");
		
		if( !scan.getHandlers().isEmpty() ){
			PathHandlerMounter mounter = new PathHandlerMounter();
			builder.setHandler( mounter.mount( scan.getHandlers() ) );
		}
		
		server = builder.build();
		server.start();
	}
	
	public Undertow getServer() {
		return server;
	}

	public void setServer(Undertow server) {
		this.server = server;
	}
	
}
