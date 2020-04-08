package com.vonage.tutorial.messaging

data class User(
    val name: String,
    val jwt: String
)

object Config {

    const val CONVERSATION_ID: String = "" // TODO: set conversation Id

    val jane = User(
        "Jane",
        "" // TODO: "set Jane's JWT token"
    )
    val joe = User(
        "Joe",
        "" // TODO: set Joe's JWT token"
    )
}