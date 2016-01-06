# Drools Usage Patterns
There are 3 ways to use the Drools platform, this project showcases the differences:

1. Embedded/Generate
  * Inspect the `drools-usage-patterns-embedded` project to see how to use rules that are on your classpath
2. Fetch 
  * Inspect the `drools-usage-patterns-fetch` project to see how to use rules that are fetched with maven, potentially changing without updating your code!
3. Remote 
  * Inspect the `drools-usage-patterns-remote` project to see how to create a client for the realtime execution server, introduced in the latest version of Drools.


## Updating Rules in the Fetch Strategy

First ensure that the project is build by executing the following command in the top directory of this repository :

```shell
mvn clean install
```

In the `drools-usage-patterns-fetch` project a `KieScanner` is created that will poll your local maven repository every second for updates to the `drools-usage-patterns-kjar`. You can test this out yourself by navigating to the `drools-usage-patterns-fetch` directory and running :

```shell
mvn exec:java -Dexec.mainClass="com.rhc.drools.example.Runner"
```

Alternatively you can use the script `run.sh` in this repository. This command will create a `Person` object, insert it into the working memory, fire all rules, print the `Person` object, and dispose the `KieSession` every second. Your ouput should look like this :

```shell
...
Person [name=Sal, age=55]
Person [name=Sal, age=55]
Person [name=Sal, age=55]
Person [name=Sal, age=55]
Person [name=Sal, age=55]
...
```

Next, navigate to the `drools-usage-patterns-kjar` and alter the `name.drl` file to set Sal's age to 66. The file should look like this now:

```drl
package com.rhc.drools.example
import com.rhc.drools.example.model.Person;

rule "Sal's age"
when
        $sal : Person(name == "Sal", age != 66) 
then
        $sal.setAge(66);
end
```

Now compile the kjar and install it into your local maven repository with this command, in the `drools-usage-patterns-kjar`:

```shell
mvn clean install
```

After that completes check the output of our `exec:java` command earlier. It will switch to using the latest version of the rules like so :

```shell
...
Person [name=Sal, age=55]
Person [name=Sal, age=55]
Person [name=Sal, age=55]
Person [name=Sal, age=66]
Person [name=Sal, age=66]
Person [name=Sal, age=66]
Person [name=Sal, age=66]
...
```
