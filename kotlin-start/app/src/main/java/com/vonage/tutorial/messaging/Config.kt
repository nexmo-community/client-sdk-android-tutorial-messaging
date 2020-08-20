package com.vonage.tutorial.messaging

data class User(
    val name: String,
    val jwt: String
)

object Config {

    const val CONVERSATION_ID: String = "" // TODO: set conversation Id

    val alice = User(
        "Alice",
        "" // TODO: "set Alice JWT token"
    )
    val bob = User(
        "Bob",
        "" // TODO: set Bob JWT token"
    )
}