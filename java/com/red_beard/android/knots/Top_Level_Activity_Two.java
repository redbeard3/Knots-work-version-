package com.red_beard.android.knots;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class Top_Level_Activity_Two extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{

    private RecyclerView topRecyclerView;
    private KindAdapter kindAdapter;
    private static final String DIALOG_ABOUT_KNOTS = "DialogAboutKnots";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        Toolbar toolbar = (Toolbar) findViewById(R.id.top_level_toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.top_level_drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.top_level_nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        topRecyclerView = (RecyclerView) findViewById(R.id.new_top_list);
        topRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        List<KnotListView> topList = new ArrayList<>();
        topList.add(new KnotListView(R.string.climb_top_list, R.drawable.climb,0));
        topList.add(new KnotListView(R.string.sea_top_list, R.drawable.sea,1));
        topList.add(new KnotListView(R.string.fish_top_list, R.drawable.fish,2));
        topList.add(new KnotListView(R.string.mark_top_list, R.drawable.mark,3));
        //topList.add(new KnotListView(R.string.tie_top_list, R.drawable.tie,4));
        topList.add(new KnotListView(R.string.lace_top_list, R.drawable.lace,5));
        topList.add(new KnotListView(R.string.decor_top_list, R.drawable.dekor,6));
        topList.add(new KnotListView(R.string.favorite_top_list, R.drawable.favorite,7));

        kindAdapter = new KindAdapter(topList);
        topRecyclerView.setAdapter(kindAdapter);
    }

    private class KnotListView {
        private int name;
        private int knotImageID;
        private int Id;

        public KnotListView(int n, int imID, int ID){
            this.name = n;
            this.knotImageID = imID;
            this.Id = ID;
        }

        public int getId(){
            return Id;
        }

        public int getName() {
            return name;
        }

        public int getKnotImageID() {
            return knotImageID;
        }
    }

    private class KindHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView kindKnotTV;
        public ImageView kindKnotIV;
        private KnotListView knotListView;

        public KindHolder(View itemView){
            super(itemView);
            itemView.setOnClickListener(this);
            kindKnotIV = (ImageView) itemView.findViewById(R.id.kind_knot_image);
            kindKnotTV = (TextView) itemView.findViewById(R.id.kind_knot);
        }

        public void bindKind(KnotListView knot){
            knotListView = knot;
            kindKnotIV.setImageResource(knotListView.getKnotImageID());
            kindKnotTV.setText(knotListView.getName());
        }

        @Override
        public void onClick (View view){
            Intent intent = HostActivityTwo.newIntent(Top_Level_Activity_Two.this, knotListView.getId());
            startActivity(intent);
        }
    }

    private class KindAdapter extends RecyclerView.Adapter<KindHolder>{
        private List<KnotListView> kindknot;

        public KindAdapter(List<KnotListView> kinds){
            kindknot = kinds;
        }

        @Override
        public KindHolder onCreateViewHolder(ViewGroup parent, int viewType){
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            View view = layoutInflater.inflate(R.layout.top_list_item,parent,false);
            return new KindHolder(view);
        }

        @Override
        public void onBindViewHolder(KindHolder holder, int position){
            KnotListView knotListView = kindknot.get(position);
            holder.bindKind(knotListView);
        }

        @Override
        public int getItemCount(){
            return kindknot.size();
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.top_level_drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.top__level_, menu);
        MenuItem searchItem = menu.findItem(R.id.menu_item_search);
        final SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Intent searchIntent = SearchResultsHostActivity.newSearchIntent(Top_Level_Activity_Two.this, query);
                startActivity(searchIntent);
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.nav_send) {
            Intent mailIntent = SimpleEMail.newIntent(Top_Level_Activity_Two.this);
            startActivity(mailIntent);
        }
        else if (id == R.id.nav_source) {
            Intent sourceIntent = SourceActivity.newIntent(Top_Level_Activity_Two.this);
            startActivity(sourceIntent);
        } else if (id == R.id.nav_estimate){
            final String appPackageName = getApplicationContext().getPackageName();
            try {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
            } catch (android.content.ActivityNotFoundException anfe) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
            }
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.top_level_drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
