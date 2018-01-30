package com.red_beard.android.knots;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.red_beard.android.knots.custom_class.DynamicImageView;
import com.red_beard.android.knots.database_class.DBHelper;

import java.io.IOException;
import java.io.InputStream;


public class KnotDetailFragment extends android.support.v4.app.Fragment {

    private long knotId;
    private DBHelper myDbHelper;
    private static final String EXTRA_KNOT_ID = "knot_id";
    private boolean isCheckFavorite;
    private TextView knotNameTextView;
    private TextView knotDescriptionTextView;
    private CheckBox favoriteCheckBox;
    private Cursor cursor;
    private LinearLayout linearLayout;
    private SQLiteDatabase db;

    public static KnotDetailFragment newInstance(long id){
        Bundle args = new Bundle();
        args.putSerializable(EXTRA_KNOT_ID, id);

        KnotDetailFragment fragment = new KnotDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (savedInstanceState != null){
            knotId = savedInstanceState.getLong(EXTRA_KNOT_ID);
        }
        View view = inflater.inflate(R.layout.fragment_knot_detail, container, false);

        return view;
    }

    @Override
    public void onStart(){
        super.onStart();
        openDB();
        View view = getView();
        if (view != null) {
            knotNameTextView = (TextView) view.findViewById(R.id.knotTextView);
            knotDescriptionTextView = (TextView) view.findViewById(R.id.descriptionKnotTextView);

            linearLayout = (LinearLayout) view.findViewById(R.id.linearLayoutOnKnotDetail);

            favoriteCheckBox = (CheckBox) view.findViewById(R.id.favoriteCheckBox);
            favoriteCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    openDB();
                    isCheckFavorite = favoriteCheckBox.isChecked();
                    ContentValues favoriteValues = new ContentValues();
                    favoriteValues.put("FAVORITE", isCheckFavorite);
                    try{
                        db.update("KNOTS", favoriteValues,
                                "_id = ?", new String[] {Integer.toString((int)knotId)});
                        db.close();
                    } catch (SQLException ex){

                    }
                }
            });
            openKnotDetail("_id = ?");
        }
    }

    public int getItemCount(){
        return 14;
    }

    public void setKnotId(long id){
        this.knotId = id;
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState){
        savedInstanceState.putLong(EXTRA_KNOT_ID, knotId);
    }

    private void openKnotDetail(String condition){
        try {
            cursor = db.query("KNOTS",
                    new String[]{"NAME", "DESCRIPTION", "FAVORITE"},
                    condition,
                    new String[]{Integer.toString((int) knotId)},
                    null, null, null);
            if (cursor.moveToFirst()) {
                knotNameTextView.setText(cursor.getString(0));
                knotDescriptionTextView.setText(cursor.getString(1));
                favoriteCheckBox.setChecked(cursor.getInt(2) == 1);
                String subtitle = cursor.getString(0);
                AppCompatActivity activity = (AppCompatActivity) getActivity();
                activity.getSupportActionBar().setTitle(subtitle);
            }

            String stringKnotId = Integer.toString((int) knotId);

            for (int i=0;i<=25;i++) {
                String stringI = Integer.toString(i);
                String imageName = stringKnotId + "/" + stringI + ".jpg";
                try {
                    InputStream inputStream = getActivity().getAssets().open(imageName);
                    Drawable drawable = Drawable.createFromStream(inputStream, null);
                    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT);
                    layoutParams.setMargins(15, 15,15,5);
                    DynamicImageView imageView = new DynamicImageView(getContext(), null);
                    imageView.setImageDrawable(drawable);
                    imageView.setLayoutParams(layoutParams);
                    linearLayout.addView(imageView);
                    TextView numberImage = new TextView(getContext());
                    String number = Integer.toString(i+1);
                    numberImage.setText(number);
                    numberImage.setLayoutParams(layoutParams);
                    linearLayout.addView(numberImage);
                } catch (IOException ex) {
                    return;
                }
            }

            cursor.close();
            db.close();
        } catch (SQLException ex){
            Toast toastEx = Toast.makeText(this.getActivity(), "DataBase unavailable", Toast.LENGTH_SHORT);
            toastEx.show();
        }
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
