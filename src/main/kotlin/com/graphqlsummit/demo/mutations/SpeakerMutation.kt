package com.graphqlsummit.demo.mutations

import com.expediagroup.graphql.annotations.GraphQLDescription
import com.expediagroup.graphql.spring.operations.Mutation
import com.graphqlsummit.demo.enums.Title
import com.graphqlsummit.demo.enums.Type
import com.graphqlsummit.demo.models.Speaker
import org.springframework.stereotype.Component

@Component
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