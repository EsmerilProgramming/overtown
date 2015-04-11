cloverx-samples
===============

### Running the samples

First download the project, use maven to run the project with the command in the current project directory

```shell
mvn exec:java
```

### Creating a archetype

If you want to create a maven archetype from this project:

```shell
$ git clone https://github.com/EsmerilProgramming/cloverx-samples.git
```

```shell
$ cd cloverx-samples
```

```shell
$ mvn archetype:create-from-project
```

```shell
$ cp -R target/generated-sources/archetype/ ..
```

```shell
$ cd ../archetype/
```

```shell
$ mvn clean install
```

Edit pom.xml change this:

```xml
<packaging>maven-archetype</packaging>
```

to

```xml
<packaging>pom</packaging>
```

```shell
$ mvn archetype:generate -DarchetypeCatalog=local
```


Or, just use this cloverx-samples changing for your needs ; )