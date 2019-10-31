package com.graphqlsummit.demo.context

import com.expediagroup.graphql.spring.execution.GraphQLContextFactory
import org.springframework.http.server.reactive.ServerHttpRequest
import org.springframework.http.server.reactive.ServerHttpResponse
import org.springframework.stereotype.Component

@Component
class CustomGraphQLContextFactory : GraphQLContextFactory<CustomContext> {
    override suspend fun generateContext(request: ServerHttpRequest, response: ServerHttpResponse): CustomContext {
        return CustomContext("Just some Random Context")
    }
}
