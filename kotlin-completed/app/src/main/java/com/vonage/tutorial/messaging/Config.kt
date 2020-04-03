package com.vonage.tutorial.messaging

data class User(
    val name: String,
    val jwt: String
)

object Config {

    const val CONVERSATION_ID: String = "CON-58ea5d7c-d0db-494a-af82-823b5c7e8b82"

    val jane = User(
        "Jane",
        ""
    )
    val joe = User(
        "Joe",
        ""
    )
}