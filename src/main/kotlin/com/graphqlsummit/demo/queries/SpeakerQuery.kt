package com.graphqlsummit.demo.queries

import com.expediagroup.graphql.annotations.GraphQLContext
import com.expediagroup.graphql.annotations.GraphQLDescription
import com.expediagroup.graphql.annotations.GraphQLIgnore
import com.expediagroup.graphql.annotations.GraphQLName
import com.expediagroup.graphql.spring.operations.Query
import com.graphqlsummit.demo.context.CustomContext
import com.graphqlsummit.demo.enums.Title
import com.graphqlsummit.demo.enums.Type
import com.graphqlsummit.demo.models.Speaker
import org.springframework.stereotype.Component

@Component
@GraphQLDescription("Query Example at the GraphQL Summit 2019")
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