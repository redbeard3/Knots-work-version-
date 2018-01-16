package com.red_beard.android.knots;

import android.app.Activity;
import android.database.Cursor;
import android.database.SQLException;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.red_beard.android.knots.custom_class.CustomCursorAdapter;

import java.io.IOException;



public class KnotListFragment extends ListFragment {

    CustomCursorAdapter cursorAdapter;
    private int pos;
    private KnotDatabaseHelper myDbHelper;
    private Cursor cursor;

    static interface KnotListListener{
        void itemClicked(long id);
    }

    private KnotListListener listener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_knot_list, container, false);
        return view;
    }

    @Override
    public void onStart(){
        super.onStart();
        pos = (int) getActivity().getIntent().getSerializableExtra(HostActivityTwo.EXTRA_KNOT);     //Получаем, какой тип узлов нужно показать
        openDB();

        String subtitle = "";

        if (pos == 0) {
            openKnotList("CLIMB = 1");
            subtitle = getResources().getString(R.string.climb_top_list);
        }

        if (pos == 1) {
            openKnotList("SEA = 1");
            subtitle = getResources().getString(R.string.sea_top_list);
        }

        if (pos == 2) {
            openKnotList("FISH = 1");
            subtitle = getResources().getString(R.string.fish_top_list);
        }

        if (pos == 3) {
            openKnotList("OTHER = 1");
            subtitle = getResources().getString(R.string.mark_top_list);
        }

        if (pos == 4) {
            openKnotList("TIE = 1");
            subtitle = getResources().getString(R.string.tie_top_list);
        }

        if (pos == 5) {
            openKnotList("LACE = 1");
            subtitle = getResources().getString(R.string.lace_top_list);
        }

        if (pos == 6) {
            openKnotList("DECOR = 1");
            subtitle = getResources().getString(R.string.decor_top_list);
        }

        if (pos == 7) {
            openKnotList("FAVORITE = 1");
            subtitle = getResources().getString(R.string.favorite_top_list);
        }

        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.getSupportActionBar().setTitle(subtitle);
    }

    @Override
    public void onAttach (Activity activity){
        super.onAttach(activity);
        this.listener = (KnotListListener) activity;
    }
    @Override
    public void onDestroy(){
        super.onDestroy();
        cursor.close();
        myDbHelper.close();
    }

    @Override
    public void onListItemClick(ListView listView, View view, int position, long id){
        if (listener != null){
            listener.itemClicked(id);
        }
    }

    private void openKnotList(String typeKnotList){
        try {
            cursor = myDbHelper.query("KNOT",
                    new String[] {"_id", "NAME"},
                    typeKnotList,
                    null, null, null,
                    "NAME ASC");

            cursorAdapter = new CustomCursorAdapter(this.getActivity(),
                    cursor,
                    0);
            setListAdapter(cursorAdapter);
        } catch (SQLException e) {
            Toast toast = Toast.makeText(this.getActivity(), "Invalid database", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    private void openDB(){
        myDbHelper = new KnotDatabaseHelper(this.getActivity());
        try {
            myDbHelper.createDataBase();
        } catch (IOException ioe) {
            throw new Error("Unable to create database");
        }
        try {
            myDbHelper.openDataBase();
        } catch (SQLException sqle) {
            throw sqle;
        }
    }
}
