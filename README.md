CloverX
=======


## Getting started

### Creating a controller

To create a controller is pretty simple, use the annotation @Controller and inform the path that this controller will listen, each controller can have many pages, each page is a method annotated with @Page in the controller, don't worry in which package, your controllers are, the CloverX will hunt it and prepare everything for you

```java
import org.esmerilprogramming.cloverx.annotoation.Controller;
import org.esmerilprogramming.cloverx.annotation.Page;

@Controller(path = "/myController")
public class MyController  {
  
  @Page("ola")
  public void hello(){
     //Say your hello here
  }

}
```

### Reading parameters from request

To read the parameters from a request, you can define a parameter in the method with the name of the attribute in the request, like

```java
@Page("read-name")
public void read(String name){
  System.out.println("My name: " + name);
}
```

You can put as many attributes as you want, or your Model types they will also be read and filler with the attributes in the request, but for that there is a convention, the request should send the attributes from the object with the prefix of the name of the object, example:

```java
@Page("car")
public void read(Car car){
  //The request need to send the attributes from the model parameter as
  // car.name, car.model, etc...
  System.out.println(car);
}
```

You can also access the request from the CloverXRequest class, you add it as a parameter in the method and it will be inject for you
and will contains all the request information you need.

```java
@Page("accessing-request")
public void read( CloverXRequest request ){
  String test = request.getAttribute("test");
}
```
### Working with json
To send back a json response you can use the JsonResponse class, just add as a parameter of your page method and use the sendAsResponse method to send back your json, by default the response charset it UTF-8.

```java
@Page("hello-json")
public void respondJson(JsonResponse response){
  String jsonAsString = YourJsonBuilder().buildJson();
  response.sendAsResponse( jsonAsString );
}
```
### Running your app

Declare a CloverX instance in your main method

```java
public static void main(String... args) {
  new CloverX(new ConfigurationBuilder()
  .withHost("127.0.0.1")
  .withPort(8080)
  .build());
}
```
