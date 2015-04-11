package br.esmerilprogramming;

import br.esmerilprogramming.model.Car;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.ConvertUtilsBean;
import org.apache.commons.beanutils.ConvertUtilsBean2;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.beanutils.converters.AbstractConverter;
import org.apache.commons.beanutils.converters.IntegerConverter;
import org.esmerilprogramming.cloverx.server.CloverX;
import org.esmerilprogramming.cloverx.server.ConfigurationBuilder;

import java.lang.reflect.InvocationTargetException;

public class AppMain {

  public static void main(String[] args) {

    new CloverX(new ConfigurationBuilder()
            .withPackageToScan("br.esmerilprogramming")
            .withHost("localhost")
            .withAppContext("cloverx-samples")
    .build());

    System.out.println("Sample form : http:localhost:8080/car/form");
    System.out.println("Sample JSON response : http:localhost:8080/car/json");
    System.out.println("Image: http:localhost:8080/static/img/dummy.png");
    System.out.println("Static css: http:localhost:8080/static/css/cloverx.css");
    System.out.println("Static js: http:localhost:8080/static/js/cloverx.js");

  }

}
