package com.vonage.tutorial.messaging

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nexmo.client.NexmoClient
import com.nexmo.client.request_listener.NexmoConnectionListener.ConnectionStatus
import com.vonage.tutorial.messaging.extension.toLiveData

class LoginViewModel : ViewModel() {

    private val _connectionStatus = MutableLiveData<ConnectionStatus>()
    val connectionStatus = _connectionStatus.toLiveData()

    private var client: NexmoClient = TODO("Retrieve NexmoClient instance")

    init {
        // TODO: Add client connection listener
    }

    fun onLoginUser(user: User) {
        // TODO: Login user
    }
}
