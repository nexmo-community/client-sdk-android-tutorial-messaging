package com.vonage.tutorial.messaging;

import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import com.nexmo.client.NexmoEvent;
import com.nexmo.client.NexmoMemberEvent;
import com.nexmo.client.NexmoTextEvent;
import com.vonage.tutorial.R;
import com.vonage.tutorial.messaging.util.StringUtils;

import java.util.ArrayList;

public class ChatFragment extends Fragment implements BackPressHandler {

    ChatViewModel viewModel;

    ProgressBar progressBar;
    TextView errorTextView;
    ConstraintLayout chatContainer;
    Button logoutButton;
    Button sendMessageButton;
    TextView userNameTextView;
    EditText messageEditText;
    TextView conversationEventsTextView;

    private Observer<String> errorMessageObserver = it -> {
        progressBar.setVisibility(View.GONE);
        errorTextView.setText(it);

        if (it.equals("")) {
            errorTextView.setVisibility(View.GONE);
            chatContainer.setVisibility(View.VISIBLE);
        } else {
            errorTextView.setVisibility(View.VISIBLE);
            chatContainer.setVisibility(View.GONE);
        }
    };

    private Observer<String> userNameObserver = it -> {
        userNameTextView.setText(getResources().getString(R.string.user_says, it));
        logoutButton.setText(getResources().getString(R.string.logout, it));
    };

    private Observer<ArrayList<NexmoEvent>> conversationEvents = events -> {

        ArrayList<String> lines = new ArrayList<>();

        for (NexmoEvent event : events) {
            if (event == null) {
                continue;
            }

            String line = "";

            if (event instanceof NexmoMemberEvent) {
                line = getConversationLine((NexmoMemberEvent) event);
            } else if (event instanceof NexmoTextEvent) {
                line = getConversationLine((NexmoTextEvent) event);
            }

            lines.add(line);
        }

        // Production application should utilise RecyclerView to provide better UX
        if (events.isEmpty()) {
            conversationEventsTextView.setText("Conversation has no events");
        } else {

            String conversationEvents = "";

            for (String line : lines) {
                conversationEvents += line + "\n";
            }

            conversationEventsTextView.setText(conversationEvents);
        }

        progressBar.setVisibility(View.GONE);
        chatContainer.setVisibility(View.VISIBLE

        );
    };

    public ChatFragment() {
        super(R.layout.fragment_chat);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        progressBar = view.findViewById(R.id.progressBar);
        errorTextView = view.findViewById(R.id.errorTextView);
        chatContainer = view.findViewById(R.id.chatContainer);
        logoutButton = view.findViewById(R.id.logoutButton);
        sendMessageButton = view.findViewById(R.id.sendMessageButton);
        userNameTextView = view.findViewById(R.id.userNameTextView);
        messageEditText = view.findViewById(R.id.messageEditText);
        conversationEventsTextView = view.findViewById(R.id.conversationEventsTextView);

        viewModel = new ViewModelProvider(requireActivity()).get(ChatViewModel.class);

        if (StringUtils.isBlank(Config.CONVERSATION_ID)) {
            Toast.makeText(requireActivity(), "Please set Config.CONVERSATION_ID", Toast.LENGTH_SHORT).show();
            onBackPressed();
            return;
        }

        viewModel.onInit();

        viewModel.errorMessage.observe(getViewLifecycleOwner(), errorMessageObserver);
        viewModel.conversationEvents.observe(getViewLifecycleOwner(), conversationEvents);
        viewModel.userName.observe(getViewLifecycleOwner(), userNameObserver);

        sendMessageButton.setOnClickListener(it -> {
            String message = messageEditText.getText().toString();

            if (!StringUtils.isBlank(message)) {
                viewModel.onSendMessage(messageEditText.getText().toString());
                messageEditText.setText("");
            } else {
                Toast.makeText(requireActivity(), "Message is blank", Toast.LENGTH_SHORT).show();
            }
        });

        logoutButton.setOnClickListener(it -> {
            viewModel.onLogout();
            NavHostFragment.findNavController(this).popBackStack();
        });
    }

    private String getConversationLine(NexmoTextEvent textEvent) {
        String user = textEvent.getFromMember().getUser().getName();
        return user + "  said: " + textEvent.getText();
    }

    private String getConversationLine(NexmoMemberEvent memberEvent) {
        String user = memberEvent.getMember().getUser().getName();

        switch (memberEvent.getState()) {
            case JOINED:
                return user + " joined";
            case INVITED:
                return user + " invited";
            case LEFT:
                return user + " left";
            case UNKNOWN:
                return "Error: Unknown member event state";
        }

        return "";
    }

    @Override
    public void onBackPressed() {
        viewModel.onBackPressed();
    }
}

