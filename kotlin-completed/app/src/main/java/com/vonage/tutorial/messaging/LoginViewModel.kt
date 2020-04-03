package com.vonage.tutorial.messaging

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nexmo.client.NexmoClient
import com.nexmo.client.request_listener.NexmoConnectionListener.ConnectionStatus
import com.vonage.tutorial.messaging.extension.toLiveData

class LoginViewModel : ViewModel() {

    private val _connectionStatus = MutableLiveData<ConnectionStatus>()
    val connectionStatus = _connectionStatus.toLiveData()

    private var client: NexmoClient = NexmoClient.get()

    init {
        client.setConnectionListener { newConnectionStatus, _ ->
            _connectionStatus.postValue(newConnectionStatus)
        }
    }

    fun loginUser(user: User) {
        if (!user.jwt.isBlank()) {
            client.login(user.jwt)
        }
    }
}
