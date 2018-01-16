package com.red_beard.android.knots;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class SourceActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.source_activity);
    }

    public static Intent newIntent (Context packageContext){
        Intent intent = new Intent(packageContext, SourceActivity.class);
        return intent;
    }
}
