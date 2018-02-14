package com.red_beard.android.knots.database_class;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.database.Cursor;
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

    private void parseTable(SQLiteDatabase db){

        Resources resources = myContext.getResources();
        XmlResourceParser xmlResourceParser = resources.getXml(R.xml.knots_db);
        try{
            int eventType = xmlResourceParser.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT){
                if ((eventType == XmlPullParser.START_TAG) && (xmlResourceParser.getName().equals("record"))) {
                    String name = xmlResourceParser.getAttributeValue(null, "name");
                    String description = xmlResourceParser.getAttributeValue(null, "desctription");
                    String climb = xmlResourceParser.getAttributeValue(null, "climb");
                    String sea = xmlResourceParser.getAttributeValue(null, "sea");
                    String fish = xmlResourceParser.getAttributeValue(null, "fish");
                    String lace = xmlResourceParser.getAttributeValue(null, "lase");
                    String tie = xmlResourceParser.getAttributeValue(null, "tie");
                    String other = xmlResourceParser.getAttributeValue(null, "other");
                    String decor = xmlResourceParser.getAttributeValue(null, "decor");
                    String favorite = xmlResourceParser.getAttributeValue(null, "favorite");
                    String tags = xmlResourceParser.getAttributeValue(null, "tags");
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
            Log.d(KNOTDATABASE, "end parse table");
        }
    }

    private void updateData(SQLiteDatabase db, int oldVersion, int newVersion){

        if (oldVersion < 1) {
            Log.d(KNOTDATABASE, "Create new DB");
            if (doesTableExist(db, "KNOT")){
                Log.d(KNOTDATABASE, "table KNOT exists and updates from KNOT");
                sqlUpdateExistsTable(db, "KNOT");
            }else {
                Log.d(KNOTDATABASE, "table KNOT doesn't exist and create new table KNOTS");
                sqlCreateTable(db, "KNOTS");
            }
        }

        if (newVersion > 1){
                Log.d(KNOTDATABASE, "update exist DB KNOTS");
                sqlUpdateExistsTable(db, "KNOTS");
        }
    }

    private boolean doesTableExist(SQLiteDatabase db, String tableName) {
        Cursor cursor = db.rawQuery("select DISTINCT tbl_name from sqlite_master where tbl_name = '" + tableName + "'", null);

        if (cursor != null) {
            if (cursor.getCount() > 0) {
                cursor.close();
                return true;
            }
            cursor.close();
        }
        return false;
    }

    private void sqlCreateTable(SQLiteDatabase db, String tableName){
        db.execSQL("CREATE TABLE '"+ tableName+"' (" +
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

    private void sqlUpdateExistsTable(SQLiteDatabase db, String tableName){
        db.execSQL("CREATE TABLE KNOTS_TMP AS SELECT * FROM '"+ tableName +"';");
        db.execSQL("DROP TABLE '"+ tableName +"';");
        sqlCreateTable(db,"KNOTS");
        db.execSQL("UPDATE KNOTS SET FAVORITE = (SELECT FAVORITE FROM KNOTS_TMP WHERE KNOTS_TMP._id = KNOTS._id);");
        db.execSQL("DROP TABLE KNOTS_TMP;");
    }

}

