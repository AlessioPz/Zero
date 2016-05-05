package com.bussolalabs.zero.database;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by alessio on 23/04/16.
 */
public class ZeroContract {

    public static final String CONTENT_AUTHORITY = "com.bussolalabs.zero";
    private static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final String PATH_ZERO_ENTITY = "zero_entity";

    public static abstract class MessageEntry implements BaseColumns {

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_ZERO_ENTITY).build();

        public static final String CONTENT_DIR_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_ZERO_ENTITY;
        public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_ZERO_ENTITY;

        public static final String TABLE_NAME = "ZeroEntity";

        public static final String COLUMN_NAME_STRING_FIELD = "string_field";
        public static final String COLUMN_NAME_BOOLEAN_FIELD = "boolean_field";

        public static Uri buildUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

        private static ContentValues values = new ContentValues();
        public static ContentValues getValues(){return values;}
    }

}
