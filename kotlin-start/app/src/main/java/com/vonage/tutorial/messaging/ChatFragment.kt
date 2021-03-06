package com.vonage.tutorial.messaging

import android.os.Bundle
import android.view.View
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
import com.vonage.tutorial.messaging.extension.observe
import com.vonage.tutorial.messaging.extension.setText
import com.vonage.tutorial.messaging.extension.toast
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
        userNameTextView.setText(R.string.user_says, it)
        logoutButton.setText(R.string.logout, it)
    }

    private var conversationEvents = Observer<List<NexmoEvent>?> { events ->
        // TODO: React to new events
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (Config.CONVERSATION_ID.isBlank()) {
            activity?.toast("Please set Config.CONVERSATION_ID")
            activity?.onBackPressed()
            return
        }

        viewModel.onInit()

        observe(viewModel.errorMessage, errorMessageObserver)
        observe(viewModel.conversationEvents, conversationEvents)
        observe(viewModel.userName, userNameObserver)

        sendMessageButton.setOnClickListener {
            val message = messageEditText.text.toString()

            if (message.isNotBlank()) {
                viewModel.onSendMessage(messageEditText.text.toString())
                messageEditText.setText("")
            } else {
                activity?.toast("Message is blank")
            }
        }

        logoutButton.setOnClickListener {
            viewModel.onLogout()
            findNavController().popBackStack()
        }
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

    private fun getConversationLine(textEvent: NexmoTextEvent): String {
        val user = textEvent.fromMember.user.name
        return "$user said: ${textEvent.text}"
    }

    override fun onBackPressed() {
        viewModel.onBackPressed()
    }
}
