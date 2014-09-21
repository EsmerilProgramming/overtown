package org.esmerilprogramming.cloverx.server.mounters;

import io.undertow.server.session.SessionListener;

public interface SessionListenerMounter {
  
  SessionListener mount(Class<?> annotatedClass);
  
}
