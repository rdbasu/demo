package com.graphqlsummit.demo.models

import com.graphqlsummit.demo.enums.Title
import com.graphqlsummit.demo.enums.Type
import com.graphqlsummit.demo.interfaces.Attendee

data class Speaker(val name: String, val title: Title,
                   override val type: Type) : Attendee {
}