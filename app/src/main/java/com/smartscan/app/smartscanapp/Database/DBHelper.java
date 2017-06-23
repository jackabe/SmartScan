package com.smartscan.app.smartscanapp.Database;

/**
 * Created by Jack_Allcock on 22/06/2017.
 */

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by c1673107 on 17/03/2017.
 */
public class DBHelper extends SQLiteOpenHelper{

    public static final String DATABASE_NAME = "templates.db";
    public static final int DATABASE_VERSION = 1;
    public static final String TABLE_TEMPLATES = "tableTemplates";

    public static final String TEMPLATE_ID = "templateId";
    public static final String TEMPLATE_NAME = "templateName";
    public static final String TEMPLATE_DESCRIPTION = "templateDescription";
    public static final String POWER_SETTING = "templatePowerSetting";
    public static final String ENABLED_SETTING = "templateEnabledSetting";

    private static final String CREATE_TEMPLATE_DATABASE = "create table "
            + TABLE_TEMPLATES + " (" + TEMPLATE_ID
            + " integer primary key autoincrement, " + TEMPLATE_NAME
            + " text not null, " + TEMPLATE_DESCRIPTION + " text not null, " + POWER_SETTING + " integer, " + ENABLED_SETTING + " integer);";

    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {

        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TEMPLATES);
        db.execSQL(CREATE_TEMPLATE_DATABASE);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TEMPLATES);

        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }


    public void createDatabase(SQLiteDatabase db) {
        db.execSQL(CREATE_TEMPLATE_DATABASE);
    }

}
