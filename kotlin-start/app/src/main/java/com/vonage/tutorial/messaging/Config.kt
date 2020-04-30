package com.vonage.tutorial.messaging

data class User(
    val name: String,
    val jwt: String
)

object Config {

    const val CONVERSATION_ID: String = "" // TODO: set conversation Id

    val user1 = User(
        "USER1",
        "" // TODO: "set USER1 JWT token"
    )
    val user2 = User(
        "USER2",
        "" // TODO: set USER2 JWT token"
    )
}