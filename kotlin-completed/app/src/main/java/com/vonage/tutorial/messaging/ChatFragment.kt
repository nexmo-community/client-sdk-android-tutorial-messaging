package com.vonage.tutorial.messaging

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.nexmo.client.NexmoEvent
import com.nexmo.client.NexmoMemberEvent
import com.nexmo.client.NexmoMemberState
import com.nexmo.client.NexmoTextEvent
import com.vonage.tutorial.R
import kotlinx.android.synthetic.main.fragment_chat.*

class ChatFragment : Fragment(R.layout.fragment_chat), BackPressHandler {

    private val viewModel by viewModels<ChatViewModel>()

    private var errorMessageObserver = Observer<String> {
        progressBar.isVisible = false
        errorTextView.text = it
        errorTextView.isVisible = it.isNotEmpty()
        chatContainer.isVisible = it.isEmpty()
    }

    private var userNameObserver = Observer<String> {
        userNameTextView.text = resources.getString(R.string.user_says, it)
        logoutButton.text = resources.getString(R.string.logout, it)
    }

    private var conversationEvents = Observer<List<NexmoEvent>?> {
        val events = it?.mapNotNull { event ->
            when (event) {
                is NexmoMemberEvent -> getConversationLine(event)
                is NexmoTextEvent -> getConversationLine(event)
                else -> null
            }
        }

        // Production application should utilise RecyclerView to provide better UX
        conversationEventsTextView.text = if (events.isNullOrEmpty()) {
            "Conversation has no events"
        } else {
            events.joinToString(separator = "\n")
        }

        progressBar.isVisible = false
        chatContainer.isVisible = true
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (Config.CONVERSATION_ID.isBlank()) {

            Toast.makeText(context, "Please set Config.CONVERSATION_ID", Toast.LENGTH_SHORT).show()
            activity?.onBackPressed()
            return
        }

        viewModel.onInit()

        viewModel.errorMessage.observe(viewLifecycleOwner, errorMessageObserver)
        viewModel.conversationEvents.observe(viewLifecycleOwner, conversationEvents)
        viewModel.userName.observe(viewLifecycleOwner, userNameObserver)

        sendMessageButton.setOnClickListener {
            val message = messageEditText.text.toString()

            if (message.isNotBlank()) {
                viewModel.onSendMessage(messageEditText.text.toString())
                messageEditText.setText("")
            } else {
                Toast.makeText(context, "Message is blank", Toast.LENGTH_SHORT).show()
            }
        }

        logoutButton.setOnClickListener {
            viewModel.onLogout()
            findNavController().popBackStack()
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

    override fun onBackPressed() {
        viewModel.onBackPressed()
    }
}
