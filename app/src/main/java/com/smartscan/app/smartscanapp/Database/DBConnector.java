package com.smartscan.app.smartscanapp.Database;

/**
 * Created by Jack_Allcock on 22/06/2017.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class DBConnector {

    public static final String DATABASE_NAME = "MY_DATABASE";
    public static final int DATABASE_VERSION = 1;
    SQLiteDatabase sqlDatabase;
    DBHelper dbHelper;

    public DBConnector(Context context) {

        dbHelper = new DBHelper(context, DATABASE_NAME, null,
                DATABASE_VERSION);
        sqlDatabase = dbHelper.getWritableDatabase();
    }

    public void executeQuery(String query) {
        try {

            if (sqlDatabase.isOpen()) {
                sqlDatabase.close();
            }

            sqlDatabase = dbHelper.getWritableDatabase();
            sqlDatabase.execSQL(query);

        } catch (Exception e) {

            System.out.println("DATABASE ERROR " + e);
        }

    }

    public Cursor selectQuery(String query) {
        Cursor c1 = null;
        try {

            if (sqlDatabase.isOpen()) {
                sqlDatabase.close();

            }
            sqlDatabase = dbHelper.getWritableDatabase();
            c1 = sqlDatabase.rawQuery(query, null);

        } catch (Exception e) {

            System.out.println("DATABASE ERROR " + e);

        }
        return c1;

    }

    public void CreateThis() {
        dbHelper.createDatabase(sqlDatabase);
    }

    public void updateQuery(String name, int power, int enable) {
        SQLiteDatabase d = dbHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("templatePowerSetting", power);
        cv.put("templateEnabledSetting", enable);
        d.update(DBHelper.TABLE_TEMPLATES, cv, "templateName = ?", new String[]{name});
    }



}
