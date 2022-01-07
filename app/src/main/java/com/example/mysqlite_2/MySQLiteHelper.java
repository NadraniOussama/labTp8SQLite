package com.example.mysqlite_2;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class MySQLiteHelper extends SQLiteOpenHelper {
    public static final String TABLE_CONTACT_LIST= "contactlist";
    public static final String COLUMN_ID="d_id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_NUMBER = "number";
    public static final String COLUMN_EMAIL = "email";
    public static final String DATABASE_NAME = "contactlist.db";
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_CREATE = "CREATE TABLE " + TABLE_CONTACT_LIST + " ("
            + COLUMN_ID + " integer PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_NAME + " text not null ,"
            + COLUMN_EMAIL + " text not null ,"
            + COLUMN_NUMBER + " text not null);";
    public MySQLiteHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public MySQLiteHelper(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        Log.w(MySQLiteHelper.class.getName(),"UPGRADING database from version " + oldVersion
                + "to " + newVersion +", which will destroy all ol data");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACT_LIST);
        onCreate(sqLiteDatabase);
    }

}
