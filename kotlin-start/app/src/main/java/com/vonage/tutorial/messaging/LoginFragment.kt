package com.vonage.tutorial.messaging

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.nexmo.client.request_listener.NexmoConnectionListener.ConnectionStatus
import com.vonage.tutorial.R
import com.vonage.tutorial.messaging.extension.observe
import com.vonage.tutorial.messaging.extension.toast
import kotlinx.android.synthetic.main.fragment_login.*
import kotlin.properties.Delegates

class LoginFragment : Fragment(R.layout.fragment_login) {

    private val viewModel by viewModels<LoginViewModel>()

    private var dataLoading: Boolean by Delegates.observable(false) { _, _, newValue ->
        loginAsAliceButton.isEnabled = !newValue
        loginAsBobButton.isEnabled = !newValue
        progressBar.isVisible = newValue
    }

    private val stateObserver = Observer<ConnectionStatus> {
        // TODO: React for connection state change
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observe(viewModel.connectionStatus, stateObserver)

        loginAsAliceButton.setOnClickListener {
            loginUser(Config.alice)
        }

        loginAsBobButton.setOnClickListener {
            loginUser(Config.bob)
        }
    }

    private fun loginUser(user: User) {
        if (user.jwt.isBlank()) {
            activity?.toast("Error: Please set Config.${user.name.toLowerCase()}.jwt")
        } else {
            viewModel.onLoginUser(user)
            dataLoading = true
        }
    }
}
