package com.vonage.tutorial.messaging;

import android.Manifest;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import com.nexmo.client.request_listener.NexmoConnectionListener.ConnectionStatus;
import com.vonage.tutorial.R;
import com.vonage.tutorial.messaging.util.StringUtils;

public class LoginFragment extends Fragment {

    Button loginAsAliceButton;
    Button loginAsBobButton;
    ProgressBar progressBar;
    TextView connectionStatusTextView;
    private LoginViewModel viewModel;

    public LoginFragment() {
        super(R.layout.fragment_login);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(requireActivity()).get(LoginViewModel.class);

        viewModel._connectionStatusLiveData.observe(getViewLifecycleOwner(), connectionStatus -> {
            connectionStatusTextView.setText(connectionStatus.toString());

            if (connectionStatus == ConnectionStatus.DISCONNECTED) {
                setDataLoading(false);
            }
        });

        loginAsAliceButton = view.findViewById(R.id.loginAsAliceButton);
        loginAsBobButton = view.findViewById(R.id.loginAsBobButton);
        progressBar = view.findViewById(R.id.progressBar);
        connectionStatusTextView = view.findViewById(R.id.connectionStatusTextView);


        loginAsAliceButton.setOnClickListener(it -> loginUser(Config.getAlice()));

        loginAsBobButton.setOnClickListener(it -> loginUser(Config.getBob()));
    }

    private void setDataLoading(Boolean dataLoading) {
        loginAsAliceButton.setEnabled(!dataLoading);
        loginAsBobButton.setEnabled(!dataLoading);

        int visibility;

        if (dataLoading) {
            visibility = View.VISIBLE;
        } else {
            visibility = View.GONE;
        }

        progressBar.setVisibility(visibility);
    }

    private void loginUser(User user) {
        if (StringUtils.isBlank(user.jwt)) {
            Toast.makeText(getActivity(), "Error: Please set Config." + user.name + " jwt", Toast.LENGTH_SHORT).show();
        } else {
            viewModel.onLoginUser(user);
            setDataLoading(true);
        }
    }
}
