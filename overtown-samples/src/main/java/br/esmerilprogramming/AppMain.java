package br.esmerilprogramming;

import br.esmerilprogramming.model.Car;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.ConvertUtilsBean;
import org.apache.commons.beanutils.ConvertUtilsBean2;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.beanutils.converters.AbstractConverter;
import org.apache.commons.beanutils.converters.IntegerConverter;
import org.esmerilprogramming.overtown.server.Overtown;
import org.esmerilprogramming.overtown.server.ConfigurationBuilder;

public class AppMain {

  public static void main(String[] args) {

    new Overtown(new ConfigurationBuilder()
            .withPackageToScan("br.esmerilprogramming")
            .withHost("localhost")
            .withAppContext("overtown-samples")
    .build());

    System.out.println("Sample form : http:localhost:8080/car/form");
    System.out.println("Sample JSON response : http:localhost:8080/car/json");
    System.out.println("Image: http:localhost:8080/static/img/dummy.png");
    System.out.println("Static css: http:localhost:8080/static/css/overtown.css");
    System.out.println("Static js: http:localhost:8080/static/js/overtown.js");

  }

}
