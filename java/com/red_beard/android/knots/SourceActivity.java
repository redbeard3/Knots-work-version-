package com.red_beard.android.knots;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class SourceActivity extends AppCompatActivity {

    TextView versionName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.source_activity);
        versionName = findViewById(R.id.versionName);
        versionName.setText("Версия " + BuildConfig.VERSION_NAME);
    }

    public static Intent newIntent (Context packageContext){
        Intent intent = new Intent(packageContext, SourceActivity.class);
        return intent;
    }
}
