package com.red_beard.android.knots;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class SearchResultsHostActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        SearchListFragment.SearchListListener {

    public final static String SEARCH_MESSAGE= "SEARCH_MESSAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_host_search);

        Toolbar toolbar = (Toolbar) findViewById(R.id.activity_host_search_toolbar);
        setSupportActionBar(toolbar);

        toolbar.setNavigationIcon(R.mipmap.ic_arrow_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.activity_host_search_drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.activity_host_search_nav_view);
        navigationView.setNavigationItemSelectedListener(this);

    }

    @Override
    public void itemClicked(long id){
        View fragmentContainer = findViewById(R.id.search_frag_container);
        if (fragmentContainer != null) {
            FragmentManager fm = getSupportFragmentManager();
            KnotDetailFragment fragment = new KnotDetailFragment();
            fragment.setKnotId(id);
            fm.beginTransaction().replace(R.id.search_frag_container, fragment).addToBackStack(null).commit();
        } else {
            Intent intent = new Intent(this, DetailActivity.class);
            intent.putExtra(DetailActivity.EXTRA_KNOT_ID, (int) id);
            startActivity(intent);
        }
    }

    public static Intent newSearchIntent (Context packageContext, String query) {
        Intent intent = new Intent(packageContext, SearchResultsHostActivity.class);
        intent.putExtra(SEARCH_MESSAGE, query);
        return intent;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.activity_host_search_drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.host_activity_search, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.home) {
            startActivity(new Intent(this, Top_Level_Activity.class));
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_climb) {
            Intent intent = HostActivityTwo.newIntent(SearchResultsHostActivity.this, 0);
            startActivity(intent);
        } else if (id == R.id.nav_sea) {
            Intent intent = HostActivityTwo.newIntent(SearchResultsHostActivity.this, 1);
            startActivity(intent);
        } else if (id == R.id.nav_fish) {
            Intent intent = HostActivityTwo.newIntent(SearchResultsHostActivity.this, 2);
            startActivity(intent);
        } else if (id == R.id.nav_mark) {
            Intent intent = HostActivityTwo.newIntent(SearchResultsHostActivity.this, 3);
            startActivity(intent);
            //} else if (id == R.id.nav_tie) {
            //    Intent intent = HostActivityTwo.newIntent(SearchResultsHostActivity.this, 4);
            //    startActivity(intent);
        } else if (id == R.id.nav_lace) {
            Intent intent = HostActivityTwo.newIntent(SearchResultsHostActivity.this, 5);
            startActivity(intent);
        } else if (id == R.id.nav_dekor) {
            Intent intent = HostActivityTwo.newIntent(SearchResultsHostActivity.this, 6);
            startActivity(intent);
        } else if (id == R.id.nav_favorite) {
            Intent intent = HostActivityTwo.newIntent(SearchResultsHostActivity.this, 7);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.activity_host_search_drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
