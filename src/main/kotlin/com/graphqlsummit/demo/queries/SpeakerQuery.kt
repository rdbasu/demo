package com.graphqlsummit.demo.queries

import com.expediagroup.graphql.annotations.GraphQLContext
import com.expediagroup.graphql.annotations.GraphQLDescription
import com.expediagroup.graphql.annotations.GraphQLIgnore
import com.expediagroup.graphql.annotations.GraphQLName
import com.expediagroup.graphql.spring.operations.Query
import com.graphqlsummit.demo.context.CustomContext
import com.graphqlsummit.demo.enums.Title
import com.graphqlsummit.demo.models.Speaker
import org.springframework.stereotype.Component

@Component
@GraphQLDescription("Query Example at the GraphQL Summit 2019")
class SpeakerQuery : Query {
    @Deprecated(message = "Forget this one", replaceWith = ReplaceWith("latestAndGreatest"))
    @GraphQLDescription("Sample Query for the GraphQL Summit 2019")
    fun getSpeaker(name: String): Speaker {
        return Speaker("Rohit Basu", Title.ARCHITECT)
    }

    @GraphQLDescription("The Latest & The Greatest")
    @GraphQLName("SomeRandomName")
    @GraphQLIgnore
    fun latestAndGreatest(name: String): Speaker {
        return Speaker("New & Improved Rohit", Title.ARCHITECT)
    }

    @GraphQLName("Speakers")
    fun listSpeakers(): List<Speaker> {
        return listOf(Speaker("rohit basu", Title.ARCHITECT),
                Speaker("rohit not so basu", Title.SLACKER))
    }

    @GraphQLName("RandomCustomContext")
    fun getCustomContext(@GraphQLContext context: CustomContext): Speaker? {
        println(context.custom)
        return null
    }
}