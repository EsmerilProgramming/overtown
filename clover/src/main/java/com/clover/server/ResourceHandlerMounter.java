package com.clover.server;

import io.undertow.Handlers;
import io.undertow.server.handlers.resource.FileResourceManager;
import io.undertow.server.handlers.resource.ResourceHandler;

import java.io.File;
import java.net.URL;

public class ResourceHandlerMounter {

	public ResourceHandler mount() {
		URL url = this.getClass().getResource("/static");
		ResourceHandler resource = Handlers.resource(new FileResourceManager(
				new File(url.getPath()), 1));
		return resource;
	}
}
