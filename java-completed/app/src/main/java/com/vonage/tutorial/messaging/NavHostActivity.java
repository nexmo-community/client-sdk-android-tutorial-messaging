package com.vonage.tutorial.messaging;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import com.vonage.tutorial.R;
import com.vonage.tutorial.messaging.util.NavManager;

public class NavHostActivity extends AppCompatActivity implements BackPressHandler {

    private NavManager navManager = NavManager.getInstance();
    private Fragment navHostFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_nav_host);
        navManager.init(getNavController());

        navHostFragment = getSupportFragmentManager().findFragmentById(R.id.navHostFragment);
    }

    private NavController getNavController() {
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.navHostFragment);
        return NavHostFragment.findNavController(navHostFragment);
    }

    @Override
    public void onBackPressed() {
        BackPressHandler currentNavigationFragment =
                (BackPressHandler) getSupportFragmentManager().getPrimaryNavigationFragment().getChildFragmentManager().getFragments().get(0);
        currentNavigationFragment.onBackPressed();

        super.onBackPressed();
    }
}
