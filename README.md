CloverX
=======


[![Build Status](https://travis-ci.org/EsmerilProgramming/cloverx.svg?branch=master)](https://travis-ci.org/EsmerilProgramming/cloverx)

## Getting started

Current version - 0.2.0-SNAPSHOT

### Getting the lib
#### Maven
  You need to configure the snapshot repository from Sonatype 
```xml
<repositories>
  <repository>
    <id>Sonatype-Snapshots</id>
    <url>https://oss.sonatype.org/content/groups/public</url>
  </repository>
</repositories>
```
 Add the dependency
```xml
<dependencies>
  <dependency>
    <groupId>com.github.esmerilprogramming</groupId>
    <artifactId>cloverx</artifactId>
    <version>0.1.0-SNAPSHOT</version>
  </dependency>
</dependencies>
```

#### Hardcore
 You can download from the repository https://oss.sonatype.org/content/groups/public/com/github/esmerilprogramming/cloverx/
 
 or do your own build
 
 ```shell
 $ git clone https://github.com/EsmerilProgramming/cloverx.git
 
 $ cd cloverx
 
 $ mvn clean install
 ``` 

### Creating a controller

To create a controller is pretty simple, just use the annotation @Controller and inform the path that this controller will listen, each controller can have many pages, each page is a method annotated with @Page in the controller, don't worry in which package, the CloverX will hunt it and prepare everything for you

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

To read the parameters from a request, you can define a parameter in the method with the name of the attribute in the request, like this:

```java
@Page("read-name")
public void read(String name){
  System.out.println("My name: " + name);
}
```

You can put as many attributes as you want, your Model types will also be read and filler with the attributes in the request, but for that there is a convention, the request should send the attributes from the object with the prefix of the name of the object, example:

```java
@Page("car")
public void read(Car car){
  //The request need to send the attributes from the model parameter as
  // car.name, car.model, etc...
  System.out.println(car);
}
```

You can also access the request from the CloverXRequest class, you add it as a parameter in the method and it will be inject for you and will contains all the request information you need.

```java
@Page("accessing-request")
public void read( CloverXRequest request ){
  String test = request.getAttribute("test");
}
```
### Working with JSON
To send back a JSON response you can use the JsonResponse class, just add as a parameter of your page method and use the 'sendAsResponse' method to send back your JSON, by default the response charset is UTF-8.

```java
@Page("hello-json")
public void respondJson(JsonResponse response){
  String jsonAsString = YourJsonBuilder().buildJson();
  response.sendAsResponse( jsonAsString );
}
```
### Running your app

Declare a CloverX instance in your main method:

```java
public static void main(String... args) {
  new CloverX(new ConfigurationBuilder()
  .withHost("127.0.0.1")
  .withPort(8080)
  .build());
}
```
