package org.esmerilprogramming.clover.management.model;

import java.util.List;

public class ServerStatus {
	
	private String host;
	private String port;
	private List<String> registeredPaths;
	
	public ServerStatus() {
		
	}
	
	@Override
	public String toString() {
		return "ServerStatus [host=" + host + ", port=" + port
				+ ", registeredPaths=" + registeredPaths + "]";
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public List<String> getRegisteredPaths() {
		return registeredPaths;
	}

	public void setRegisteredPaths(List<String> registeredPaths) {
		this.registeredPaths = registeredPaths;
	}
	
	
	
}
