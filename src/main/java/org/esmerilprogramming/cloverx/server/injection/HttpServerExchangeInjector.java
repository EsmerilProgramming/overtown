package org.esmerilprogramming.cloverx.server.injection;

import org.esmerilprogramming.cloverx.http.CloverXRequest;

/**
 * @author efraimgentil (efraim.gentil@gmail.com)
 */
public class HttpServerExchangeInjector implements CoreInjector {

	@SuppressWarnings("unchecked")
	@Override
	public <T> T inject(Class<T> clazz, String parameterName,
			CloverXRequest cloverRequest) {
			return (T) cloverRequest.getExchange();
	}

}
