package com.vonage.tutorial.messaging

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nexmo.client.NexmoAttachmentEvent
import com.nexmo.client.NexmoClient
import com.nexmo.client.NexmoConversation
import com.nexmo.client.NexmoDeletedEvent
import com.nexmo.client.NexmoDeliveredEvent
import com.nexmo.client.NexmoEvent
import com.nexmo.client.NexmoMessageEventListener
import com.nexmo.client.NexmoSeenEvent
import com.nexmo.client.NexmoTextEvent
import com.nexmo.client.NexmoTypingEvent
import com.vonage.tutorial.messaging.extension.toLiveData

class ChatViewModel : ViewModel() {

    private val client: NexmoClient = NexmoClient.get()

    private var conversation: NexmoConversation? = null

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage = _errorMessage.toLiveData()

    private val _userName = MutableLiveData<String>()
    val userName = _userName.toLiveData()

    private val _conversationEvents = MutableLiveData<List<NexmoEvent>?>()
    val conversationEvents = _conversationEvents.toLiveData()

    private val messageListener = object : NexmoMessageEventListener {
        override fun onTypingEvent(typingEvent: NexmoTypingEvent) {}

        override fun onAttachmentEvent(attachmentEvent: NexmoAttachmentEvent) {}

        override fun onTextEvent(textEvent: NexmoTextEvent) {
            // TODO: Update the conversation
        }

        override fun onSeenReceipt(seenEvent: NexmoSeenEvent) {}

        override fun onEventDeleted(deletedEvent: NexmoDeletedEvent) {}

        override fun onDeliveredReceipt(deliveredEvent: NexmoDeliveredEvent) {}
    }

    fun onInit() {
        getConversation()
        _userName.postValue(client.user.name)
    }

    private fun getConversation() {
        TODO("Fetch the conversation")
    }

    private fun getConversationEvents(conversation: NexmoConversation) {
        TODO("Fetch the conversation events")
    }

    private fun updateConversation(textEvent: NexmoEvent) {
        val events = _conversationEvents.value?.toMutableList() ?: mutableListOf()
        events.add(textEvent)
        _conversationEvents.postValue(events)
    }

    fun onSendMessage(message: String) {
        TODO("Send new message to client SDK")
    }

    fun onBackPressed() {
        client.logout()
    }

    fun onLogout() {
        client.logout()
    }

    override fun onCleared() {
        TODO("Unregister message listener")
    }
}
