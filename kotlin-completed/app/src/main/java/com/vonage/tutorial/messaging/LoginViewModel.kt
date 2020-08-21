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

    private val client = NexmoClient.get()

    init {
        client.setConnectionListener { newConnectionStatus, _ ->
            if (newConnectionStatus == ConnectionStatus.CONNECTED) {
                navigate()
                return@setConnectionListener
            }

            _connectionStatus.postValue(newConnectionStatus)
        }
    }

    private fun navigate() {
        val navDirections = LoginFragmentDirections.actionLoginFragmentToChatFragment()
        navManager.navigate(navDirections)
    }

    fun onLoginUser(user: User) {
        if (!user.jwt.isBlank()) {
            this.user = user
            client.login(user.jwt)
        }
    }
}
