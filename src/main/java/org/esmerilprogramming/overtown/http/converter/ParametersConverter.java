package org.esmerilprogramming.overtown.http.converter;

import java.util.Map;

import org.esmerilprogramming.overtown.http.OvertownRequest;

public class ParametersConverter {

  private Map<String, ParameterConverter> paramConverterMap;

  public ParametersConverter() {}

  public ParametersConverter(Map<String, ParameterConverter> paramConverterMap) {
    super();
    this.paramConverterMap = paramConverterMap;
  }

  @Deprecated
  public Object[] translateAllParameters(String[] parameterNames, Class<?>[] parameterTypes,
      OvertownRequest cloverRequest) {
    Object[] parameters = new Object[parameterTypes.length];
    for (int i = 0; i < parameterTypes.length; i++) {
      Class<?> clazz = parameterTypes[i];
      parameters[i] = translateParameter(clazz, parameterNames[i], cloverRequest);
    }
    return parameters;
  }

  @Deprecated
  public <T> T translateParameter(Class<T> clazz, String parameterName, OvertownRequest cloverRequest) {
    boolean shouldTranslateParameter = cloverRequest.shouldConvertParameter(parameterName);
    ParameterConverter translator;
    if (shouldTranslateParameter) {
      translator = cloverRequest.getTranslator(parameterName);
    } else {
      translator = getTranslator(clazz);
    }

    return translator.translate(clazz, parameterName, cloverRequest);
  }

  public Object[] translateParameters(String[] parameterNames, Class<?>[] parameterTypes,
      OvertownRequest cloverRequest) {
    Object[] parameters = new Object[parameterTypes.length];
    for (int i = 0; i < parameterTypes.length; i++) {
      Class<?> clazz = parameterTypes[i];
      parameters[i] = translateParam(clazz, parameterNames[i], cloverRequest);
    }
    return parameters;
  }

  protected <T> T translateParam(Class<T> clazz, String parameterName, OvertownRequest cloverRequest) {
    boolean shouldTranslateParameter = cloverRequest.shouldConvertParameter(parameterName);
    ParameterConverter translator;
    if (shouldTranslateParameter) {
      translator = cloverRequest.getTranslator(parameterName);
    } else {
      translator = paramConverterMap.get(parameterName);
    }
    return translator.translate(clazz, parameterName, cloverRequest);
  }

  @Deprecated
  public ParameterConverter getTranslator(Class<?> clazz) {
    if (PrimitiveParamConverter.isPrimitive(clazz))
      return new PrimitiveParamConverter();
    if (CommonsParamConverter.isCommonParam(clazz))
      return new CommonsParamConverter();
    else
      return new ModelConverter();
  }

}
