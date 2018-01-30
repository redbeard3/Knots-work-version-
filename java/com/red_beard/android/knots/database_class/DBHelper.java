package com.red_beard.android.knots.database_class;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.red_beard.android.knots.R;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

/**
 * Created by red beard on 25.01.2018.
 */

public class DBHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "knots";
    private static final int DB_VERSION = 1;
    private final Context myContext;

    private static final String KNOTDATABASE = "databaseLog";

    public DBHelper(Context context){
        super(context, DB_NAME, null, DB_VERSION);
        this.myContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        Log.d(KNOTDATABASE, "onCreate DB");
        updateData(db, 0, DB_VERSION);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        Log.d(KNOTDATABASE, "onUpgrade DB");
        updateData(db, oldVersion, newVersion);
    }

    private static void insertData(SQLiteDatabase db, String name, String description, String climb, String sea, String fish, String lace, String tie, String other, String decor, String favorite, String tags){
        ContentValues knotData = new ContentValues();
        knotData.put("NAME", name);
        knotData.put("DESCRIPTION", description);
        knotData.put("CLIMB", climb);
        knotData.put("SEA", sea);
        knotData.put("FISH", fish);
        knotData.put("LACE", lace);
        knotData.put("TIE", tie);
        knotData.put("OTHER", other);
        knotData.put("DECOR", decor);
        knotData.put("FAVORITE", favorite);
        knotData.put("TAGS", tags);
        db.insert("KNOTS", null, knotData);
    }

    private void updateData(SQLiteDatabase db, int oldVersion, int newVersion){
        if (oldVersion < 1) {
            db.execSQL("CREATE TABLE KNOTS (" +
                    "    _id         INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "    NAME        TEXT," +
                    "    DESCRIPTION TEXT," +
                    "    CLIMB       TEXT," +
                    "    SEA         TEXT," +
                    "    FISH        TEXT," +
                    "    LACE        TEXT," +
                    "    TIE         TEXT," +
                    "    OTHER       TEXT," +
                    "    DECOR       TEXT," +
                    "    FAVORITE    TEXT," +
                    "    TAGS        TEXT" +
                    ");");
            Resources resources = myContext.getResources();
            XmlResourceParser xmlResourceParser = resources.getXml(R.xml.knots_db);
            try{
                int eventType = xmlResourceParser.getEventType();
                while (eventType != XmlPullParser.END_DOCUMENT){
                    if ((eventType == XmlPullParser.START_TAG) && (xmlResourceParser.getName().equals("record"))) {
                        String name = xmlResourceParser.getAttributeValue(0);
                        String description = xmlResourceParser.getAttributeValue(1);
                        String climb = xmlResourceParser.getAttributeValue(2);
                        String sea = xmlResourceParser.getAttributeValue(3);
                        String fish = xmlResourceParser.getAttributeValue(4);
                        String lace = xmlResourceParser.getAttributeValue(5);
                        String tie = xmlResourceParser.getAttributeValue(6);
                        String other = xmlResourceParser.getAttributeValue(7);
                        String decor = xmlResourceParser.getAttributeValue(8);
                        String favorite = xmlResourceParser.getAttributeValue(9);
                        String tags = xmlResourceParser.getAttributeValue(10);
                        insertData(db, name, description, climb, sea, fish, lace, tie, other, decor, favorite, tags);
                    }
                    eventType = xmlResourceParser.next();
                }
            } catch (XmlPullParserException ex) {
                Log.e("Parser", ex.getMessage(), ex);
            } catch (IOException ex){
                Log.e("IOExcepion", ex.getMessage(), ex);
            } finally {
                xmlResourceParser.close();
            }
        }

        if (oldVersion < newVersion){

        }

    }

}
