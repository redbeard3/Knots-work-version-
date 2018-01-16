package com.red_beard.android.knots.custom_class;

import android.content.Context;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.red_beard.android.knots.R;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by red beard on 12.11.2017.
 */

public class CustomCursorAdapter extends CursorAdapter {

    private LayoutInflater cursorInflater;

    public CustomCursorAdapter(Context context, Cursor cursor, int flags) {
        super(context, cursor, flags);
        cursorInflater = (LayoutInflater) context.getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);
    }

    public void bindView(View view, Context context, Cursor cursor) {
        ImageView knotImage = (ImageView) view.findViewById(R.id.knot_image);
        try {
            String name = cursor.getString(cursor.getColumnIndex("_id")) + "/0.jpg";
            InputStream inputStream = context.getAssets().open(name);
            Drawable drawable = Drawable.createFromStream(inputStream, null);
            knotImage.setImageDrawable(drawable);
        } catch (IOException ex) {
            return;
        }
        TextView knotName = (TextView) view.findViewById(R.id.knot_name_on_list);
        String title = cursor.getString(cursor.getColumnIndex("NAME"));
        knotName.setText(title);

    }

    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return cursorInflater.inflate(R.layout.knot_list_item, parent, false);
    }
}
