package de.psdev.licensesdialog.sample;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;

public class MainActivity extends FragmentActivity {

    // ==========================================================================================================================
    // Android Lifecycle
    // ==========================================================================================================================

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    // ==========================================================================================================================
    // Public API
    // ==========================================================================================================================

    public void onSampleClick(final View view) {
        startActivity(new Intent(this, SampleActivity.class));
    }

    public void onAppCompatClick(final View view) {
        startActivity(new Intent(this, AppCompatSampleActivity.class));
    }
}
