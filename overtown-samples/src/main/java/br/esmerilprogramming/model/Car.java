package br.esmerilprogramming.model;

public class Car {
  
  private int    year;
  private String model;
  
  public Car(int year, String model) {
    super();
    this.year = year;
    this.model = model;
  }

  public Car() {
  }
  
  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("Car [year=").append(year).append(", model=").append(model).append("]");
    return builder.toString();
  }
  
  public int getYear() {
    return year;
  }
  public void setYear(int year) {
    this.year = year;
  }
  public String getModel() {
    return model;
  }
  public void setModel(String model) {
    this.model = model;
  }

}