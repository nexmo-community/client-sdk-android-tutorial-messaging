package com.vonage.tutorial.messaging

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nexmo.client.NexmoClient
import com.nexmo.client.request_listener.NexmoConnectionListener.ConnectionStatus
import com.vonage.tutorial.messaging.util.NavManager

class LoginViewModel : ViewModel() {

    private val navManager = NavManager

    private val _connectionStatus = MutableLiveData<ConnectionStatus>()
    val connectionStatus = _connectionStatus as LiveData<ConnectionStatus>

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
        if (user.jwt.isNotBlank()) {
            this.user = user
            client.login(user.jwt)
        }
    }
}
