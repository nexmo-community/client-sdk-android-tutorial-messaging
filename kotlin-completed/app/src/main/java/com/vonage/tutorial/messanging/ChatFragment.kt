package com.vonage.tutorial.messanging

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.nexmo.client.NexmoEvent
import com.nexmo.client.NexmoMemberEvent
import com.nexmo.client.NexmoMemberState
import com.nexmo.client.NexmoTextEvent
import com.vonage.tutorial.R
import com.vonage.tutorial.messanging.extension.observe
import kotlinx.android.synthetic.main.fragment_chat.*

class ChatFragment : Fragment(R.layout.fragment_chat) {

    private val viewModel by viewModels<ChatViewModel>()

    private var errorMessageObserver = Observer<String> {
        progressBar.isVisible = false
        errorTextView.text = it
        errorTextView.isVisible = it.isNotEmpty()
        chatContainer.isVisible = it.isEmpty()
    }

    private var userNameObserver = Observer<String> {
        userNameTextView.text = "$it:"
    }

    private var conversationMessages = Observer<List<NexmoEvent>?> { events ->
        val messages = events?.mapNotNull {
            when (it) {
                is NexmoMemberEvent -> getConversationLine(it)
                is NexmoTextEvent -> getConversationLine(it)
                else -> null
            }
        }

        // Production application should utilise RecyclerView to provide better UX
        conversationMessagesTextView.text = if (messages.isNullOrEmpty()) {
            "Conversation has No messages"
        } else {
            messages.joinToString(separator = "\n")
        }

        progressBar.isVisible = false
        chatContainer.isVisible = true
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.init()

        observe(viewModel.errorMessage, errorMessageObserver)
        observe(viewModel.conversationMessages, conversationMessages)
        observe(viewModel.userName, userNameObserver)

        sendMessageButton.setOnClickListener {
            val message = messageEditText.text.toString()

            if (message.isNotBlank()) {
                viewModel.sendMessage(messageEditText.text.toString())
                messageEditText.setText("")
            } else {
                Toast.makeText(activity, "Message is blank", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private fun getConversationLine(textEvent: NexmoTextEvent): String {
        val user = textEvent.fromMember.user.name
        return "$user said: ${textEvent.text}"
    }

    private fun getConversationLine(memberEvent: NexmoMemberEvent): String {
        val user = memberEvent.member.user.name

        return when (memberEvent.state) {
            NexmoMemberState.JOINED -> "$user joined"
            NexmoMemberState.INVITED -> "$user invited"
            NexmoMemberState.LEFT -> "$user left"
            else -> "Error: Unknown member event state"
        }
    }
}