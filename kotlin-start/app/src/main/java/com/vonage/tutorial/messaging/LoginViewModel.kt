package com.vonage.tutorial.messaging

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nexmo.client.NexmoClient
import com.nexmo.client.request_listener.NexmoConnectionListener.ConnectionStatus
import com.vonage.tutorial.messaging.extension.toLiveData
import com.vonage.tutorial.messaging.util.NavManager

class LoginViewModel : ViewModel() {

    private val navManager = NavManager

    private val _connectionStatus = MutableLiveData<ConnectionStatus>()
    val connectionStatus = _connectionStatus.toLiveData()

    private var user: User? = null

    private val client: NexmoClient = TODO("Retrieve NexmoClient instance")

    init {
        // TODO: Add client connection listener
    }

    private fun navigateToChatFragment() {
        val userName = checkNotNull(user?.name) { "user is null" }
        val navDirections = LoginFragmentDirections.actionLoginFragmentToChatFragment()
        navManager.navigate(navDirections)
    }

    fun onLoginUser(user: User) {
        // TODO: Login user
    }
}
