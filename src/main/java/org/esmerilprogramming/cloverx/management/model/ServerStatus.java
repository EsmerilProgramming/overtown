package org.esmerilprogramming.cloverx.management.model;

import java.util.List;

public class ServerStatus {

	private Integer id;
	private String host;
	private String port;

	private List<String> registeredPaths;
	
	public ServerStatus() {}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("ServerStatus{");
		sb.append("id=").append(id);
		sb.append(", host='").append(host).append('\'');
		sb.append(", port='").append(port).append('\'');
		sb.append(", registeredPaths=").append(registeredPaths);
		sb.append('}');
		return sb.toString();
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

