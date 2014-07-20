package org.esmerilprogramming.cloverx.http.converter;

import org.esmerilprogramming.cloverx.http.CloverXRequest;

/**
 * @author efraimgentil (efraim.gentil@gmail.com)
 */
public class CommonsParamConverter implements ParameterConverter {

  @Override
  public <T> T translate(Class<T> clazz, String parameterName, CloverXRequest cloverRequest) {

    Object attribute = cloverRequest.getParameter(parameterName);
    if (attribute == null)
      return null;

    ParameterConverter translator = getTranslator(clazz);
    return translator.translate(clazz, parameterName, cloverRequest);
  }

  public ParameterConverter getTranslator(Class<?> clazz) {
    ParameterConverter translator = new EmptyParamConverter();

    if (isString(clazz))
      translator = new StringConverter();
    else if (isInteger(clazz))
      translator = new IntegerConverter();
    else if (isDouble(clazz))
      translator = new DoubleConverter();
    else if (isLong(clazz))
      translator = new LongConverter();
    else if (isBoolean(clazz))
      translator = new BooleanConverter();

    return translator;
  }

  public static boolean isCommonParam(Class<?> clazz) {
    return (isString(clazz) || isInteger(clazz) || isLong(clazz) || isDouble(clazz));
  }

  private static boolean isString(Class<?> clazz) {
    return String.class.equals(clazz);
  }

  private static boolean isInteger(Class<?> clazz) {
    return Integer.class.equals(clazz);
  }

  private static boolean isLong(Class<?> clazz) {
    return Long.class.equals(clazz);
  }

  private static boolean isDouble(Class<?> clazz) {
    return Double.class.equals(clazz);
  }

  private static boolean isBoolean(Class<?> clazz) {
    return Boolean.class.equals(clazz);
  }

}
