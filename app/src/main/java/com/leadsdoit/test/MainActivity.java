package com.leadsdoit.test;

import android.view.View;
import android.widget.LinearLayout;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import com.facebook.applinks.AppLinkData;
import com.leadsdoit.test.fragment.SlotFragment;
import com.leadsdoit.test.fragment.WebviewFragment;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;

public class MainActivity extends AppCompatActivity {
    private Fragment webviewFragment;
    private SlotFragment slotFragment;
    private LinearLayout progressBar;
    private AppEventsLogger logger;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        logger = AppEventsLogger.newLogger(this);
        setContentView(R.layout.activity_main);
        progressBar = findViewById(R.id.main_progress_bar);
        slotFragment = new SlotFragment();
        FacebookSdk.setAutoInitEnabled(true);
        FacebookSdk.fullyInitialize();
        webviewFragment = new WebviewFragment(() -> setFragment(slotFragment),
                AppLinkData.createFromAlApplinkData(getIntent()));
        setFragment(webviewFragment);
        setFragment(webviewFragment);
    }

    private void setFragment(Fragment fragment) {
        progressBar.setVisibility(View.INVISIBLE);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main_container, fragment);
        fragmentTransaction.commit();
    }
}
