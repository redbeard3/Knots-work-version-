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

    private static final String DB_NAME = "knotdb";
    private static final int DB_VERSION = 2;
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

    private void parseTable(SQLiteDatabase db){

        Resources resources = myContext.getResources();
        XmlResourceParser xmlResourceParser = resources.getXml(R.xml.knots_db);
        try{
            int eventType = xmlResourceParser.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT){
                if ((eventType == XmlPullParser.START_TAG) && (xmlResourceParser.getName().equals("record"))) {
                    String name = xmlResourceParser.getAttributeValue(null, "name");
                        Log.e(KNOTDATABASE, "name = " + name);
                    String description = xmlResourceParser.getAttributeValue(null, "desctription");
                        Log.e(KNOTDATABASE, "description = " + description);
                    String climb = xmlResourceParser.getAttributeValue(null, "climb");
                        Log.e(KNOTDATABASE, "climb = " + climb);
                    String sea = xmlResourceParser.getAttributeValue(null, "sea");
                        Log.e(KNOTDATABASE, "sea = " + sea);
                    String fish = xmlResourceParser.getAttributeValue(null, "fish");
                        Log.e(KNOTDATABASE, "fish = " + fish);
                    String lace = xmlResourceParser.getAttributeValue(null, "lace");
                        Log.e(KNOTDATABASE, "lace = " + lace);
                    String tie = xmlResourceParser.getAttributeValue(null, "tie");
                        Log.e(KNOTDATABASE, "tie = " + tie);
                    String other = xmlResourceParser.getAttributeValue(null, "other");
                        Log.e(KNOTDATABASE, "other = " + other);
                    String decor = xmlResourceParser.getAttributeValue(null, "decor");
                        Log.e(KNOTDATABASE, "decor = " + decor);
                    String favorite = xmlResourceParser.getAttributeValue(null, "favorite");
                         Log.e(KNOTDATABASE, "favorite = " + favorite);
                    String tags = xmlResourceParser.getAttributeValue(null, "tags");
                        Log.e(KNOTDATABASE, "tags = " + tags);
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

    private void updateData(SQLiteDatabase db, int oldVersion, int newVersion){

        if (oldVersion < 1) {
            Log.d(KNOTDATABASE, "Create new DB");
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
            parseTable(db);
        }

        if (oldVersion < newVersion){     // УТОЧНИТЬ НАЗВАНИЕ ТАБЛИЦЫ В СТАРОЙ ВЕРСИИ ПРОГИ
            Log.d(KNOTDATABASE, "update exist DB");
            db.execSQL("CREATE TABLE KNOTS_TMP AS SELECT * FROM KNOTS;");
            db.execSQL("DROP TABLE KNOTS;");
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
            parseTable(db);
            Log.e(KNOTDATABASE, "end parse");
            db.execSQL("UPDATE KNOTS SET FAVORITE = (SELECT FAVORITE FROM KNOTS_TMP WHERE KNOTS_TMP._id = KNOTS._id);");
            db.execSQL("DROP TABLE KNOTS_TMP;");
        }
    }

}

