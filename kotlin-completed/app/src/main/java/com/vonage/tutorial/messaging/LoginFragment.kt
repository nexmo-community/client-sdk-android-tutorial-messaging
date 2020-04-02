package com.vonage.tutorial.messaging

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.nexmo.client.request_listener.NexmoConnectionListener.ConnectionStatus
import com.vonage.tutorial.R
import com.vonage.tutorial.messaging.extension.observe
import kotlinx.android.synthetic.main.fragment_login.*
import kotlin.properties.Delegates

class LoginFragment : Fragment(R.layout.fragment_login) {

    private val viewModel by viewModels<LoginViewModel>()

    private var dataLoading: Boolean by Delegates.observable(false) { _, _, newValue ->
        loginAsJaneButton.isEnabled = !newValue
        loginAsJoeButton.isEnabled = !newValue
        progressBar.isVisible = newValue
    }

    private val stateObserver = Observer<ConnectionStatus> {
        connectionStatusTextView.text = it.toString()

        if (it == ConnectionStatus.CONNECTED) {
            val navDirections = LoginFragmentDirections.actionLoginFragmentToChatFragment()
            findNavController().navigate(navDirections)
        } else if (it == ConnectionStatus.DISCONNECTED) {
            dataLoading = false
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observe(viewModel.connectionStatus, stateObserver)

        loginAsJaneButton.setOnClickListener {
            viewModel.loginUser(Config.jane)
            dataLoading = true
        }

        loginAsJoeButton.setOnClickListener {
            viewModel.loginUser(Config.joe)
            dataLoading = true
        }
    }
}
