package com.bussolalabs.zero.database;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import com.bussolalabs.zero.datamodel.ZeroEntityDataModel;

/**
 * Created by alessio on 24/04/16.
 */
public class ZeroEntityDbHelper extends ZeroDbHelper {

    private final String TAG = ZeroEntityDbHelper.class.getSimpleName();

    public ZeroEntityDbHelper(Context context) {
        super(context);
    }

    public long insert(){

        ContentValues contentValues = ZeroContract.MessageEntry.getValues();
        Uri uri = mContext.getContentResolver().insert(ZeroContract.MessageEntry.CONTENT_URI, contentValues);

        long newRowId = ContentUris.parseId(uri);
        ZeroContract.MessageEntry.getValues().put(ZeroContract.MessageEntry._ID, newRowId);

        return newRowId;
    }

    public ZeroEntityDataModel retrieveById() {
        ZeroEntityDataModel messageDataModel = new ZeroEntityDataModel();

        String where = ZeroContract.MessageEntry._ID + " = ?";

        Cursor cursor = mContext.getContentResolver().query(
                ZeroContract.MessageEntry.CONTENT_URI,
                null,
                where,
                new String[] {""+ ZeroContract.MessageEntry.getValues().getAsLong(ZeroContract.MessageEntry._ID)},
                null
        );

        if (cursor.moveToFirst()) {
            return fillDM(cursor);
        }

        return messageDataModel;
    }

    public boolean update() {
        long rowId = ZeroContract.MessageEntry.getValues().getAsLong(ZeroContract.MessageEntry._ID);

        ContentValues values = new ContentValues();
        values.put(ZeroContract.MessageEntry.COLUMN_NAME_STRING_FIELD, ZeroContract.MessageEntry.getValues().getAsString(ZeroContract.MessageEntry.COLUMN_NAME_STRING_FIELD));
        values.put(ZeroContract.MessageEntry.COLUMN_NAME_BOOLEAN_FIELD, ZeroContract.MessageEntry.getValues().getAsInteger(ZeroContract.MessageEntry.COLUMN_NAME_BOOLEAN_FIELD));

        int updated = mContext.getContentResolver().update(
                ZeroContract.MessageEntry.CONTENT_URI,
                values,
                ZeroContract.MessageEntry._ID + " = ? ",
                new String[]{rowId + ""}
        );
        return updated == 1;
    }

    public void deleteAll() {
        mContext.getContentResolver().delete(
                ZeroContract.MessageEntry.CONTENT_URI,
                null,
                null
        );
    }

    public void insertNewMessage(String lang, int gender, String header, String core, String footer) {

        ZeroContract.MessageEntry.getValues().clear();
        ZeroContract.MessageEntry.getValues().put(ZeroContract.MessageEntry.COLUMN_NAME_STRING_FIELD, 0);
        ZeroContract.MessageEntry.getValues().put(ZeroContract.MessageEntry.COLUMN_NAME_BOOLEAN_FIELD , lang);

        insert();
    }

    private ZeroEntityDataModel fillDM(Cursor cursor) {
        ZeroEntityDataModel messageDataModel = new ZeroEntityDataModel();
        messageDataModel.setId(cursor.getLong(cursor.getColumnIndex(ZeroContract.MessageEntry._ID)));
        messageDataModel.setStringField(cursor.getString(cursor.getColumnIndex(ZeroContract.MessageEntry.COLUMN_NAME_STRING_FIELD)));
        messageDataModel.setBooleanField(cursor.getInt(cursor.getColumnIndex(ZeroContract.MessageEntry.COLUMN_NAME_BOOLEAN_FIELD)) == 1);
        return messageDataModel;
    }

    public void putInCache(ZeroEntityDataModel messageDataModel) {
        ZeroContract.MessageEntry.getValues().clear();
        ZeroContract.MessageEntry.getValues().put(ZeroContract.MessageEntry._ID, messageDataModel.getId());
        ZeroContract.MessageEntry.getValues().put(ZeroContract.MessageEntry.COLUMN_NAME_STRING_FIELD, messageDataModel.getStringField());
        ZeroContract.MessageEntry.getValues().put(ZeroContract.MessageEntry.COLUMN_NAME_BOOLEAN_FIELD , messageDataModel.isBooleanField());
    }
}
