package com.vonage.tutorial.messaging;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.nexmo.client.*;
import com.nexmo.client.request_listener.NexmoApiError;
import com.nexmo.client.request_listener.NexmoRequestListener;

import java.util.ArrayList;

public class ChatViewModel extends ViewModel {

    private NexmoClient client = NexmoClient.get();

    private NexmoConversation conversation;

    private MutableLiveData<String> _errorMessage = new MutableLiveData<>();
    LiveData<String> errorMessage = _errorMessage;

    private MutableLiveData<String> _userName = new MutableLiveData<>();
    LiveData<String> userName = _userName;

    private MutableLiveData<ArrayList<NexmoEvent>> _conversationEvents = new MutableLiveData<>();
    ;
    LiveData<ArrayList<NexmoEvent>> conversationEvents = _conversationEvents;

    private NexmoMessageEventListener messageListener = new NexmoMessageEventListener() {
        @Override
        public void onTextEvent(@NonNull NexmoTextEvent textEvent) {
            updateConversation(textEvent);
        }

        @Override
        public void onAttachmentEvent(@NonNull NexmoAttachmentEvent attachmentEvent) {

        }

        @Override
        public void onEventDeleted(@NonNull NexmoDeletedEvent deletedEvent) {

        }

        @Override
        public void onSeenReceipt(@NonNull NexmoSeenEvent seenEvent) {

        }

        @Override
        public void onDeliveredReceipt(@NonNull NexmoDeliveredEvent deliveredEvent) {

        }

        @Override
        public void onTypingEvent(@NonNull NexmoTypingEvent typingEvent) {

        }
    };

    public void onInit() {
        getConversation();
        _userName.postValue(client.getUser().getName());
    }

    private void getConversation() {
        // TODO Fetch the conversation
    }


    private void getConversationEvents(NexmoConversation conversation) {
        // TODO: Fetch the conversation events
    }

    private void updateConversation(NexmoTextEvent textEvent) {
        ArrayList<NexmoEvent> events = _conversationEvents.getValue();

        if (events == null) {
            events = new ArrayList<>();
        }

        events.add(textEvent);
        _conversationEvents.postValue(events);
    }

    public void onSendMessage(String message) {
        // TODO: end new message to client SDK
    }

    public void onBackPressed() {
        client.logout();
    }

    public void onLogout() {
        client.logout();
    }

    @Override
    protected void onCleared() {
        // TODO: Unregister message listener
    }
}
