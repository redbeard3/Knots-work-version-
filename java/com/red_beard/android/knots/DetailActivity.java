package com.red_beard.android.knots;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_KNOT_ID = "knot_id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Toolbar toolbar = (Toolbar) findViewById(R.id.detail_activity_toolbar);
        setSupportActionBar(toolbar);

        toolbar.setNavigationIcon(R.mipmap.ic_arrow_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        KnotDetailFragment knotDetailFragment = (KnotDetailFragment) getSupportFragmentManager().findFragmentById(R.id.content_detail);
        int knotId = (int) getIntent().getExtras().get(EXTRA_KNOT_ID);
        knotDetailFragment.setKnotId(knotId);
    }

    @Override
    public void onBackPressed() {

        super.onBackPressed();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

}
