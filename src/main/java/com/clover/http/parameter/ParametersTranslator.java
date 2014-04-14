package com.clover.http.parameter;

import com.clover.http.CloverRequest;

public class ParametersTranslator {
	
	private ParameterTranslator translator;
	
	public <T> T translateParameters( Class<T> clazz , String parameterName , CloverRequest cloverRequest){
		if(translator == null) {
			translator = mountTranslators();
		}
		
		return translator.translate( clazz , parameterName , cloverRequest );
	}
	
	protected ParameterTranslator mountTranslators(){
		 CloverRequestTranslator t1 = new CloverRequestTranslator();
		 HttpServerExchangeTranslator t2 = new HttpServerExchangeTranslator();
		 EmptyParamTranslator lastTranslator = new EmptyParamTranslator();
		 
		 t1.setNextTranslator(t2);
		 t2.setNextTranslator(lastTranslator);
		 
		 return  t1;
	}
	
	
}
