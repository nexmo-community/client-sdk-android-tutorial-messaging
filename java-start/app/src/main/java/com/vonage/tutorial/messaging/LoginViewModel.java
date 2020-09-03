package com.vonage.tutorial.messaging;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.navigation.NavDirections;
import com.nexmo.client.NexmoClient;
import com.nexmo.client.request_listener.NexmoConnectionListener;
import com.nexmo.client.request_listener.NexmoConnectionListener.ConnectionStatus;
import com.vonage.tutorial.messaging.util.NavManager;
import com.vonage.tutorial.messaging.util.StringUtils;

public class LoginViewModel extends ViewModel {

    private NexmoClient client; // TODO: Retrieve NexmoClient instance

    NavManager navManager = NavManager.getInstance();
    private MutableLiveData<ConnectionStatus> connectionStatusMutableLiveData = new MutableLiveData<ConnectionStatus>();
    public LiveData<ConnectionStatus> _connectionStatusLiveData = connectionStatusMutableLiveData;

    private User user;

    public LoginViewModel() {
        // TODO: Add client connection listener
    }

    private void navigate() {
        String userName = user.getName();

        if (userName == null) {
            throw new RuntimeException("user name is null");
        }

        NavDirections navDirections = LoginFragmentDirections.actionLoginFragmentToChatFragment();
        navManager.navigate(navDirections);
    }

    void onLoginUser(User user) {
        // TODO: Login user
    }
}
