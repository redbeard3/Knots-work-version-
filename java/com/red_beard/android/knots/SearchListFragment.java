package com.red_beard.android.knots;

import android.app.Activity;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.red_beard.android.knots.custom_class.CustomCursorAdapter;
import com.red_beard.android.knots.database_class.DBHelper;


public class SearchListFragment extends ListFragment {

    private DBHelper myDbHelper;
    private SQLiteDatabase db;
    private Cursor cursor;
    CustomCursorAdapter cursorAdapter;

    static interface SearchListListener{
        void itemClicked(long id);
    }

    private SearchListListener listener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search_list, container, false);
        return view;
    }

    @Override
    public void onStart(){
        super.onStart();
        String inputQuery = (String) getActivity().getIntent().getSerializableExtra(SearchHostActivity.SEARCH_MESSAGE);
        openDB();
        setOnDataBaseQuery(inputQuery);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.getSupportActionBar().setTitle("Результаты поиска: " + inputQuery);
    }

    private void setOnDataBaseQuery(String query){
        try {
            cursor = db.query("KNOTS",
                    new String[]{"_id", "NAME", "DESCRIPTION", "CLIMB", "SEA", "FISH", "OTHER", "TIE", "LACE", "TAGS"},
                    "("+"NAME"+" LIKE '%"+query+"%') " + "OR ("+"DESCRIPTION"+" LIKE '%" + query+"%')" +
                            " OR ("+"TAGS"+" LIKE '%" + query+"%')",
                    null, null, null, null);

            cursorAdapter = new CustomCursorAdapter(this.getActivity(),
                    cursor,
                    0);
            setListAdapter(cursorAdapter);
        } catch (SQLException e) {
            Toast toast = Toast.makeText(this.getActivity(), "Invalid database", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    @Override
    public void onAttach (Activity activity){
        super.onAttach(activity);
        this.listener = (SearchListListener) activity;
    }

    @Override
    public void onListItemClick(ListView listView, View view, int position, long id){
        if (listener != null){
            listener.itemClicked(id);
        }
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        cursor.close();
        db.close();
    }

    private void openDB(){
        try {
            myDbHelper = new DBHelper(this.getActivity());
            db = myDbHelper.getWritableDatabase();
        } catch (SQLException sqle) {
            throw sqle;
        }
    }
}
