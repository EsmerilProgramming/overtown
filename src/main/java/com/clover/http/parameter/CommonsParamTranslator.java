package com.clover.http.parameter;

import com.clover.http.CloverRequest;

public class CommonsParamTranslator extends ParameterTranslator {

	@Override
	protected boolean shouldTranslate(Class<?> clazz) {
		return false;
	}

	@Override
	public <T> T translate(Class<T> clazz, String parameterName,
			CloverRequest cloverRequest) {
		return null;
	}

}
