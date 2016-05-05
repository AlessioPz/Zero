package com.bussolalabs.zero.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by alessio on 23/04/16.
 */
public class ZeroDbHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "Zero.db";

    private static final String TEXT_TYPE = " TEXT";
    private static final String INTEGER_TYPE = " INTEGER";
    private static final String COMMA_SEP = ", ";
    private static final String DEFAULT_0 = " DEFAULT 0";

    final Context mContext;

    public ZeroDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ZERO_ENTITY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        /*
        Database versioning:
         - +1 to DATABASE_VERSION value
         - manage content updates here
         */
    }

    private final static String SQL_CREATE_ZERO_ENTITY =
            "CREATE TABLE " + ZeroContract.ZeroEntityEntry.TABLE_NAME + " (" +
                    ZeroContract.ZeroEntityEntry._ID + INTEGER_TYPE + " PRIMARY KEY " + COMMA_SEP +
                    ZeroContract.ZeroEntityEntry._COUNT + INTEGER_TYPE + COMMA_SEP +
                    ZeroContract.ZeroEntityEntry.COLUMN_NAME_STRING_FIELD + TEXT_TYPE  + COMMA_SEP +
                    ZeroContract.ZeroEntityEntry.COLUMN_NAME_BOOLEAN_FIELD + INTEGER_TYPE + DEFAULT_0 + COMMA_SEP +
                    ") ";
}
