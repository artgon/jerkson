Jerkson
-------

Forked from Coda Hale's Jerkson, which has now been abandoned. Has some minor bug fixes and such but mostly the same. 

Updated to Scala 2.10 (and soon 2.11)

Requirements
------------

* Scala 2.10.x
* Jackson 1.9.x


Setting Up Your Project
-----------------------

Go ahead and add Jerkson as a dependency:

```xml
<repositories>
  <repository>
    <id>repo.codahale.com</id>
    <url>http://repo.codahale.com</url>
  </repository>
</repositories>

<dependencies>
  <dependency>
    <groupId>com.codahale</groupId>
    <artifactId>jerkson_${scala.version}</artifactId>
    <version>0.5.0</version>
  </dependency>
</dependencies>
```


Parsing JSON
------------

```scala
import com.codahale.jerkson.Json._

// Parse JSON arrays
parse[List[Int]]("[1,2,3]") //=> List(1,2,3)

// Parse JSON objects
parse[Map[String, Int]]("""{"one":1,"two":2}""") //=> Map("one"->1,"two"->2)

// Parse JSON objects as case classes
// (Parsing case classes isn't supported in the REPL.)
case class Person(id: Long, name: String)
parse[Person]("""{"id":1,"name":"Coda"}""") //=> Person(1,"Coda")

// Parse streaming arrays of things
for (person <- stream[Person](inputStream)) {
  println("New person: " + person)
}
```

For more examples, check out the [specs](https://github.com/codahale/jerkson/blob/master/src/test/scala/com/codahale/jerkson/tests/).


Generating JSON
---------------

```scala
// Generate JSON arrays
generate(List(1, 2, 3)) //=> [1,2,3]

// Generate JSON objects
generate(Map("one"->1, "two"->"dos")) //=> {"one":1,"two":"dos"}
```

For more examples, check out the [specs](https://github.com/codahale/jerkson/blob/master/src/test/scala/com/codahale/jerkson/tests/).


Handling `snake_case` Field Names
=================================

```scala
case class Person(firstName: String, lastName: String)

@JsonSnakeCase
case class Snake(firstName: String, lastName: String)

generate(Person("Coda", "Hale"))   //=> {"firstName": "Coda","lastName":"Hale"}
generate(Snake("Windey", "Mover")) //=> {"first_name": "Windey","last_name":"Mover"}
```


License
-------

Copyright (c) 2010-2011 Coda Hale

Published under The MIT License, see LICENSE
