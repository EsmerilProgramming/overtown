package br.esmerilprogramming.model;

/**
 * Created by efraimgentil on 19/01/15.
 */
public class Contact {

  private Integer id;
  private String name;
  private String email;
  private String celphone;

  /*
   * Needed for the default json converter
   */
  public Contact() {}

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder("Contact{");
    sb.append("id=").append(id);
    sb.append(", name='").append(name).append('\'');
    sb.append(", email='").append(email).append('\'');
    sb.append(", celphone='").append(celphone).append('\'');
    sb.append('}');
    return sb.toString();
  }

  public String getCelphone() {
    return celphone;
  }
  public void setCelphone(String celphone) {
    this.celphone = celphone;
  }
  public String getEmail() {
    return email;
  }
  public void setEmail(String email) {
    this.email = email;
  }
  public String getName() {
    return name;
  }
  public void setName(String name) {
    this.name = name;
  }
  public Integer getId() {
    return id;
  }
  public void setId(Integer id) {
    this.id = id;
  }

}
