# Sample [GraphQL](https://graphql.github.io/) [Kotlin](https://kotlinlang.org/) Server
> Using the **AWESOME** [GraphQL Kotlin](https://expediagroup.github.io/graphql-kotlin/docs/getting-started.html) libraries :heart:

## How To
```
$ git clone https://github.com/rdbasu/demo.git
$ cd demo/
$ mvn clean install
$ mvn spring-boot:run
```

```
$ open http://localhost:8080/playground
```
:point_up: will launch the GraphQL Playground

And you can start playing with your new Sample _GraphQL_ **Kotlin** server :heart_eyes:

## Structure of the Project

```
++src
  ++main
    ++kotlin
      ++com.graphqlsummit.demo (this goes in application.properties, the packages which needs to be scanned)
        ++contexts
        ++enums (contains the enums)
        ++interfaces (contains the interfaces)
        ++models (contains the object types, input types, etc)
        ++mutations (contains the mutations)
        ++queries (contains the queries)
        ++DemoApplication.kt 
    ++resources
      ++application.properties
```

## Things to Note

### In _pom.xml_ (To use the [graphql-kotlin-spring-server](https://github.com/ExpediaGroup/graphql-kotlin/tree/master/graphql-kotlin-spring-server))
```
<dependency>
  <groupId>com.expediagroup</groupId>
  <artifactId>graphql-kotlin-spring-server</artifactId>
  <version>1.2.3-SNAPSHOT</version>
</dependency>
```

### In _application.properties_
```
graphql.packages=com.graphqlsummit.demo
```

### An object type example [code](https://github.com/rdbasu/demo/blob/master/src/main/kotlin/com/graphqlsummit/demo/models/Speaker.kt#L7)
```
data class Speaker(val name: String, val title: Title,
                   override val type: Type) : Attendee {
}
```

### An ENUM example [code](https://github.com/rdbasu/demo/blob/master/src/main/kotlin/com/graphqlsummit/demo/enums/Type.kt#L3)
```
enum class Type {
    SPEAKER,
    GUEST,
    ORGANIZER
}
```

### A QUERY example [code](https://github.com/rdbasu/demo/blob/master/src/main/kotlin/com/graphqlsummit/demo/queries/SpeakerQuery.kt#L21)
> Implement the _MARKER INTERFACE_ **Query**
```
class SpeakerQuery : Query {
    // A deprecated API, deprecated using Annotation: @Deprecated
    @Deprecated(message = "Forget this one", replaceWith = ReplaceWith("latestAndGreatest"))
    // Annotation @GraphQLDescription used to add description
    @GraphQLDescription("Sample Query for the GraphQL Summit 2019")
    fun getSpeaker(name: String): Speaker {
        return Speaker("Rohit Basu", Title.ARCHITECT, Type.SPEAKER)
    }

    @GraphQLDescription("The Latest & The Greatest")
    // Annotation @GraphQLName used to rename the API
    @GraphQLName("SomeRandomName")
    // Annotation @GraphQLIgnore used to ignore this API from the SCHEMA
    @GraphQLIgnore
    fun latestAndGreatest(name: String): Speaker {
        return Speaker("New & Improved Rohit", Title.ARCHITECT, Type.GUEST)
    }

    // A List Example
    @GraphQLName("Speakers")
    fun listSpeakers(): List<Speaker> {
        return listOf(Speaker("rohit basu", Title.ARCHITECT, Type.ORGANIZER),
                Speaker("rohit not so basu", Title.SLACKER, Type.GUEST),
                Speaker("rohit again basu", Title.SOFTWAREENGINEER, Type.SPEAKER))
    }

    // An example to show the usage of Custom Context
    @GraphQLName("RandomCustomContext")
    fun getCustomContext(@GraphQLContext context: CustomContext): Speaker? {
        println(context.custom)
        return null
    }
}
```    

### A DEPRECATED QUERY example [code](https://github.com/rdbasu/demo/blob/master/src/main/kotlin/com/graphqlsummit/demo/queries/SpeakerQuery.kt#L18)
> Annotation used **[@Deprecated](https://expediagroup.github.io/graphql-kotlin/docs/customizing-schemas/evolving-schema)**
```
// A deprecated API, deprecated using Annotation: @Deprecated
@Deprecated(message = "Forget this one", replaceWith = ReplaceWith("latestAndGreatest"))
// Annotation @GraphQLDescription used to add description
@GraphQLDescription("Sample Query for the GraphQL Summit 2019")
fun getSpeaker(name: String): Speaker {
    return Speaker("Rohit Basu", Title.ARCHITECT, Type.SPEAKER)
}
```

### An example of Description [code](https://github.com/rdbasu/demo/blob/master/src/main/kotlin/com/graphqlsummit/demo/queries/SpeakerQuery.kt#L20)
> Annotation used **[@GraphQLDescription](https://expediagroup.github.io/graphql-kotlin/docs/customizing-schemas/documenting-fields)**
```
@GraphQLDescription("Sample Query for the GraphQL Summit 2019")
```

### An example to IGNORE/exclude something from the SCHEMA [code](https://github.com/rdbasu/demo/blob/master/src/main/kotlin/com/graphqlsummit/demo/queries/SpeakerQuery.kt#L29)
> Annotation used **[@GraphQLIgnore](https://expediagroup.github.io/graphql-kotlin/docs/customizing-schemas/excluding-fields)**
```
@GraphQLIgnore
fun latestAndGreatest(name: String): Speaker {
    return Speaker("New & Improved Rohit", Title.ARCHITECT, Type.GUEST)
}
```

### An example to rename something [code](https://github.com/rdbasu/demo/blob/master/src/main/kotlin/com/graphqlsummit/demo/queries/SpeakerQuery.kt#L43)
> Annotation used **[@GraphQLName](https://expediagroup.github.io/graphql-kotlin/docs/customizing-schemas/renaming-fields)**
```
@GraphQLName("RandomCustomContext")
fun getCustomContext(@GraphQLContext context: CustomContext): Speaker? {
    println(context.custom)
    return null
}
```

### Create Custom Context [code](https://github.com/rdbasu/demo/tree/master/src/main/kotlin/com/graphqlsummit/demo/contexts)
- Custom Context
```
data class CustomContext(val custom: String)
```
- Custom Context Factory
```
@Component
class CustomGraphQLContextFactory : GraphQLContextFactory<CustomContext> {
    override suspend fun generateContext(request: ServerHttpRequest, response: ServerHttpResponse): CustomContext {
        return CustomContext("Just some Random Context")
    }
}
```
- Using the Custom Context
```
@GraphQLName("RandomCustomContext")
fun getCustomContext(@GraphQLContext context: CustomContext): Speaker? {
    println(context.custom)
    return null
}
```

### A MUTATION example [code](https://github.com/rdbasu/demo/blob/master/src/main/kotlin/com/graphqlsummit/demo/mutations/SpeakerMutation.kt#L12)
> Implement the _MARKER INTERFACE_ **Mutation**
```
class SpeakerMutation : Mutation {
    @GraphQLDescription("A Sample Mutation")
    fun addSpeaker(name: String, title: Title): Speaker {
        println("the name: $name")
        println("the title: $title")
        return Speaker(name, title, Type.GUEST)
    }

    @GraphQLDescription("Adding Speaker as an Input Type")
    fun addSpeakerAsInputType(speaker: Speaker): Speaker {
        return Speaker(speaker.name, speaker.title, speaker.type)
    }
}
```

This documentation (WIP) will be made better soon. :partly_sunny:
