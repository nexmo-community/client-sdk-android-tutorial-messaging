package com.vonage.tutorial.messaging

data class User(
    val name: String,
    val jwt: String
)

object Config {

    const val CONVERSATION_ID: String = ""

    val jane = User("Jane", "")
    val joe = User("Joe", "")
}