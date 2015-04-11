package org.esmerilprogramming.overtown.server.mounters;

import io.undertow.server.session.SessionListener;

public interface SessionListenerMounter {
  
  SessionListener mount(Class<?> annotatedClass);
  
}
